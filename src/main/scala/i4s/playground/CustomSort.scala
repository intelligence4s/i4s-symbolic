package i4s.playground

object CustomSort {
  def sortInts(ints: List[Int]): List[Int] = ints

  def sort[T](values: List[T])(implicit ordered: Ordered[T]): List[T] = values

}
