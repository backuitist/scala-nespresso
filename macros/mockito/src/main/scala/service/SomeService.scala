package service

case class User(firstName: String, lastName: String)

trait PaymentService {

    def processPayment(user: User, amount: Long, time: Long)
}
