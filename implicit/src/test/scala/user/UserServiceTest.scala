package user

import clock.ClockTestBase
import org.backuity.matchete.JunitMatchers
import org.junit.Test

class UserServiceTest extends JunitMatchers with ClockTestBase {

    val mockDao = new UserDao {
        override def findById(id: Long): Option[User] = ???
    }

    @Test
    def aNewUserMustHaveACreationTime(): Unit = {
        implicit val clock = mockClock

        val service = new UserService(mockDao)

        clock.now = 1234L

        service.createUser("toto").creationTime must_== 1234L
    }

    @Test
    def aNewUserMustHaveTheRightName(): Unit = {
        val service = new UserService(mockDao)

        service.createUser("toto").name must_== "toto"
    }
}
