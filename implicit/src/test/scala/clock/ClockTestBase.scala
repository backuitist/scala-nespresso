package clock

trait ClockTestBase {

    implicit val clock = Clock.CurrentTime

    def mockClock : MockClock = new MockClock
}

class MockClock extends Clock {

    private var _time : Long = System.currentTimeMillis()

    def now_=(time: Long) = {
        this._time = time
    }

    def now = _time
}
