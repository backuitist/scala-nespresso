import utils.StringUtils.ApacheWrapper

object Demo {

    def main(args: Array[String]): Unit = {
        val host = "http://my-host:port/path".substringAfter("http://").substringBefore(":")
        println(host)

        // try to uncomment the following and hit Ctrl+Alt+Space within Intellij
        // println("haha".toBase64
    }
}
