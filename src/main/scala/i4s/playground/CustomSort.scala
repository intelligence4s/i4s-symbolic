package i4s.playground

object CustomSort {
  def sortInts(ints: List[Int]): List[Int] = ints

  def sort[T](values: List[T])(implicit ord: Ordering[T]): List[T] = values

}
