package user

import clock.Clock

class UserService(dao : UserDao)(implicit clock: Clock) {

    def createUser(name : String): User = {
        val time = clock.now
        println("Creating user at " + time)
        new User(123, name, time)
    }
}
