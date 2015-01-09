package user



trait UserDao {

    def findById(id: Long) : Option[User]
}


