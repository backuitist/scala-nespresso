package mock


import shapeless.HList
import shapeless.ops.hlist.Tupler

import scala.reflect.macros.blackbox
import scala.language.experimental.macros


// this is a test util but for simplicity sake I'll put it here
trait MockitoUtil {
    /**
     * Simplify capture of mock invocation. Same as captureAll but for a single argument.
     * @see [[MockitoUtil.captureAll]]
     */
    def capture[T](invocation: Any): T = macro MockitoUtil.captureImpl[T]

    /**
     * Simplify capture of mock invocation.
     *
     * Instead of writing the infamous ArgumentCaptor code such as:
     * {{{
     *      val captor1 = ArgumentCaptor.forClass(classOf[SomeType])
     *      val captor2 = ArgumentCaptor.forClass(classOf[SomeOtherType])
     *      org.mockito.Mockito.verify(myMock).myMockMethod(captor1.capture(),captor2.capture(),anyObject(), anyString())
     *      val someValue : SomeType = captor1.getValue
     *      val someOtherValue : SomeOtherType = captor2.getValue
     * }}}
     *
     * you simply write:
     * {{{
     *     val (someValue, someOtherValue) = captureAll[SomeType :: SomeOtherType :: HNil](
     *           myMock.myMockMethod(captor, captor, any, any))
     * }}}
     * Where the type parameter of captureAll is a [[shapeless.HList]].
     *
     * @see [[MockitoUtil.capture]] for single argument capture
     * @return the captured value of the argument captor, see [[org.mockito.ArgumentCaptor]]
     */
    def captureAll[L <: HList](invocation: Any)(implicit tupler : Tupler[L]) : tupler.Out = macro MockitoUtil.captureAllImpl[L]

    /**
     * Mark an argument of a mock method to be captured by [[MockitoUtil.capture]].
     * This should only be used within a [[MockitoUtil.capture]].
     */
    def captor[T] : T = sys.error("captor should only be used within a MockitoUtil.capture")

    def any[T] : T = sys.error("any should only be using within a MockitoUtil.capture")

    // type aliases to ease HList constructions

    type ::[+H, +T <: HList] = shapeless.::[H,T]
    type HNil = shapeless.HNil
}


object MockitoUtil extends MockitoUtil {

    def captureAllImpl[T: c.WeakTypeTag](c : blackbox.Context)(invocation : c.Tree)(tupler : c.Tree) : c.Tree = {
        import c.universe._
        val hlistTpe = weakTypeOf[T]

        def extractHListTypes(hlistTpe: Type) : List[Type] = {
            if( hlistTpe <:< typeOf[HList] ) {
                hlistTpe.typeArgs match {
                    case Nil => Nil
                    case tpe :: tail :: Nil => tpe :: extractHListTypes(tail)
                    case other => c.abort(c.enclosingPosition, "invalid HList params " + other )
                }
            } else {
                c.abort(c.enclosingPosition, hlistTpe + " is not an HList")
            }
        }

        val tpes = extractHListTypes(hlistTpe)

        // generate as many captors variable as types
        val captors = (1 to tpes.size).map( _ => TermName(c.freshName("captor"))).toList

        var argIdx = 0
        val q"$mock.$method(..$originalArgs)" = invocation

        val argsWithCapture = originalArgs.map {
            case q"$_.captor[$tpe]" =>
                if( argIdx >= captors.size ) {
                    c.abort(c.enclosingPosition, "too many captor detected, expected " +
                      captors.size + " but got " + (argIdx + 1))
                }
                val captorName = captors(argIdx)
                argIdx += 1
                q"$captorName.capture()"

            case q"$_.any[$_]" =>
                q"org.mockito.Matchers.anyObject()"

            case other =>
                println(showCode(other))
                other
        }

        if( argIdx < captors.size ) {
            c.abort(c.enclosingPosition, "too few captor detected, expected " +
              captors.size + " but got " + argIdx)
        }

        val captorsDecl = captors.zip(tpes).map { case (captor,tpe) =>
            q"val $captor = org.mockito.ArgumentCaptor.forClass(classOf[$tpe])"
        }
        val tupleClass = TermName("Tuple" + captors.size)
        val captureDecl = q"$tupleClass(..${captors.map( c => q"$c.getValue")})"

        q"""
        ..$captorsDecl
        org.mockito.Mockito.verify($mock).$method(..$argsWithCapture)
       $captureDecl
     """
    }

    def captureImpl[T: c.WeakTypeTag](c : blackbox.Context)(invocation : c.Tree) : c.Tree = {
        import c.universe._
        val tree = captureAllImpl[T :: HNil](c)(invocation)(null).asInstanceOf[c.Tree]
        q"$tree._1"
    }
}
