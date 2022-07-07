package i4s.symbolic.language.playground

object CustomSort {
  def sortIntsR(ints: List[Int]): List[Int] = {
    var sorted = List[Int]()
    for(i <- 0 until ints.length){
      val (less, greater) = sorted.span(_ < ints(i))
      sorted = less ::: (ints(i) :: greater)
    }
    sorted
  }

  def sortR[T](values: List[T])(implicit ord: Ordering[T]): List[T] = {
    var sorted = List[T]()
    for (i <- 0 until values.length) {
      val (less, greater) = sorted.span(ord.lt(_, values(i)))
      sorted = less ::: (values(i) :: greater)
    }
    sorted
  }

  def sortInts(ints: List[Int]): List[Int] = {
    if (ints.length == 1) return ints

    val halves = ints.grouped(ints.length/2)
    val firstHalf = halves.next()
    val secondHalf = halves.next()

    sortIntsMerge(sortInts(firstHalf), sortInts(secondHalf))
  }

  def sortIntsMerge(firstHalf: List[Int], secondHalf: List[Int]): List[Int] = {
    var first = firstHalf
    var second = secondHalf

    var sorted = List[Int]()
    while(first.nonEmpty && second.nonEmpty) {
      if (first.head < second.head) {
        sorted :+= first.head
        first = first.tail
      } else {
        sorted :+= second.head
        second = second.tail
      }
    }
    sorted ++ first ++ second
  }

  def sort[T](values: List[T])(implicit ord: Ordering[T]): List[T] = {
    if (values.length == 1) return values

    val halves = values.grouped(values.length/2)
    val firstHalf = halves.next()
    val secondHalf = halves.next()

    sortMerge(sort(firstHalf), sort(secondHalf))
  }

  def sortMerge[T](firstHalf: List[T], secondHalf: List[T])(implicit ord: Ordering[T]): List[T] = {
    var first = firstHalf
    var second = secondHalf

    var sorted = List[T]()
    while(first.nonEmpty && second.nonEmpty) {
      if (ord.lt(first.head, second.head)) {
        sorted :+= first.head
        first = first.tail
      } else {
        sorted :+= second.head
        second = second.tail
      }
    }
    sorted ++ first ++ second
  }
}
