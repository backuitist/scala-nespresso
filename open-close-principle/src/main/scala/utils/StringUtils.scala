package utils

import java.util.Base64

import org.apache.commons.lang3.{StringUtils => ApacheUtils}

object StringUtils {

    implicit class ApacheWrapper(val str: String) extends AnyVal {

        def substringBefore(before: String) : String = {
            ApacheUtils.substringBefore(str, before)
        }

        def substringAfter(after: String) : String = {
            ApacheUtils.substringAfter(str, after)
        }
    }

    implicit class Base64Wrapper(val str: String) extends AnyVal {

        def toBase64 : String = {
            Base64.getEncoder.encodeToString(str.getBytes("UTF-8"))
        }
    }
}
