package mock

import org.backuity.matchete.JunitMatchers
import org.junit.Test
import org.mockito.Mockito
import service.{User, PaymentService}

class MockitoUtilTest extends JunitMatchers with MockitoUtil {

    @Test
    def testPaymentServiceMock(): Unit = {
        val service : PaymentService = Mockito.mock(classOf[PaymentService])
        
        service.processPayment(User("john", "doe"), amount = 1000L, time = 12345L)

        val (user,amount,time) = captureAll[User :: Long :: Long :: HNil](
            service.processPayment(captor, captor, captor))

        user must_== User("john", "doe")
        amount must_== 1000L
        time must_== 12345L
    }
}
