package i4s.playground

object CustomSort {
  def sortInts(ints: List[Int]): List[Int] = {
    var sorted = List[Int]()
    for(i <- 0 until ints.length){
      val (less, greater) = sorted.span(_ < ints(i))
      sorted = less ::: (ints(i) :: greater)
    }
    sorted
  }

  def sort[T](values: List[T])(implicit ord: Ordering[T]): List[T] = {
    var sorted = List[T]()
    for(i <- 0 until values.length){
      val (less, greater) = sorted.span(ord.lt(_, values(i)))
      sorted = less ::: (values(i) :: greater)
    }
    sorted
  }

}
