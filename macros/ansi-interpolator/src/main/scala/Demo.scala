
object Demo {

    def main(args: Array[String]) {
        import org.backuity.ansi.AnsiFormatter.FormattedHelper

        val text = "text"
        println(ansi"Text containing ansi tags such as %bold{bold $text} which %underline{can %bold{be %yellow{nested}}}")

        // try to uncomment the following line and compile
        // ansi"Please %underlin{underline this : xxx}"
    }
}
