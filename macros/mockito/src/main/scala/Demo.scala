
object Demo {

    def main(args: Array[String]) {
        s()
        f()
        json()
    }

    def s(): Unit = {

        // See http://docs.scala-lang.org/overviews/core/string-interpolation.html for more details.

        val name = "John"
        val age = 12

        println(s"Our test subject is $name, age $age")
    }

    def f(): Unit = {

        // See http://docs.scala-lang.org/overviews/core/string-interpolation.html for more details.

        val name = "John"
        val height = 1.9d
        println(f"$name%s is $height%2.2f meters tall") // John is 1.90 meters

        // try uncommenting the following
        //        println(f"$height%4d") // does not compile
    }

    def json(): Unit = {

        // Demo the jsonquote json string interpolator[1] with play JSON[2]
        //   [1] https://github.com/maffoo/jsonquote
        //   [2] https://www.playframework.com/documentation/2.3.x/ScalaJson

        import net.maffoo.jsonquote.play._
        import play.api.libs.json._

        val foo = "bar"

        println(Json.prettyPrint(
            json"{$foo: 123, name: $foo}"))

        // try uncommenting the following
//        json"{name: name: name}"
    }
}
