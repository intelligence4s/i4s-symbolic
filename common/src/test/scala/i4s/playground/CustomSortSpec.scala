package i4s.playground

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CustomSortSpec extends AnyWordSpec with Matchers {
  private val randomizer = scala.util.Random
  private val alphanumerics = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toArray

  case class User(first: String, last: String, age: Int, circumference: Double)

  private def randomString(length: Int): String = {
    val chars = (1 until length).toArray.map(_ => alphanumerics(randomizer.nextInt(alphanumerics.length)))
    new String(chars)
  }

  "a CustomSort implmentation" should {
    "sort a list of integers" in {
      val ints = (1 until 50).map(_ => randomizer.nextInt).toList

      val sorted: List[Int] = CustomSort.sortInts(ints)

      sorted.zip(sorted.tail).foreach { case (first,next) => first should be <= next }
    }

    "sort a list of strings" in {
      val strings = (1 until 50).toList.map( _ => randomString(25))

      val sorted: List[String] = CustomSort.sort(strings)

      sorted.zip(sorted.tail).foreach { case (first,next) => first should be <= next }
    }

    "sort a list of users by age" in {
      val users = (1 until 50).map(_ => User(randomString(8),randomString(12),randomizer.nextInt,randomizer.nextDouble))
      implicit val ord: Ordering[User] = Ordering.by[User, Int](user => user.age)

      // implement the sort
      val sortedR = CustomSort.sortR(users.toList)
      val sorted: List[User] = CustomSort.sort(users.toList)

      sorted.zip(sorted.tail).foreach { case (first,next) => first.age should be <= next.age }
    }

    "sort a list of users by last name" in {
      val users = (1 until 50).map(_ => User(randomString(8),randomString(12),randomizer.nextInt,randomizer.nextDouble))
      implicit val ord: Ordering[User] = Ordering.by[User, String](user => user.last)

      // implement the sort
      val sortedR = CustomSort.sortR(users.toList)
      val sorted: List[User] = CustomSort.sort(users.toList)

      sorted.zip(sorted.tail).foreach { case (first,next) => first.last should be <= next.last }
    }
  }

}
