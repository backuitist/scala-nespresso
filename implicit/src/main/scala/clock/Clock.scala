package clock

trait Clock {
    def now : Long
}

object Clock {

    object CurrentTime extends Clock {
        def now : Long = System.currentTimeMillis()
    }
}
