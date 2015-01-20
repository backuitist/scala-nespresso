trait Show[T] {
  def show(t : T) : String
}

object Show {
  implicit val IntShow : Show[Int] = new Show[Int] {
    def show(i : Int) = "int " + i
  }

  implicit val StringShow : Show[String] = new Show[String] {
    def show(s : String) = s
  }
}