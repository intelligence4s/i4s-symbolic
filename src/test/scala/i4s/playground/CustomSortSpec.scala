package i4s.playground

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CustomSortSpec extends AnyWordSpec with Matchers {

  "a CustomSort implmentation" should {
    "sort a list of integers" in {
      val randomizer = scala.util.Random

      val ints = (1 until 50).map(x => randomizer.nextInt).toList

      val sorted: List[Int] = CustomSort.sortInts(ints)

      sorted.zip(sorted.tail).foreach( t => t._1 should be <= t._2)
      sorted.zip(sorted.tail).foreach { case (first,next) => first should be <= next}
    }
  }

}
