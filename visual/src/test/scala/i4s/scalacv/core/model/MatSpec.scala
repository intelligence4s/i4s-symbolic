package i4s.scalacv.core.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MatSpec extends AnyWordSpec with Matchers {
  "Mat[Byte]" should {
    import syntax._

    "support a single dimenional array" in {
      val mat = Mat[Double](10, Scalar(0,0,0,0))

      val values = (1 to 10).map(_.toDouble)
      mat.putAll(0, values)

      mat.dims shouldBe 2
      mat.shape shouldBe Seq(10,1)

      mat.total shouldBe 10
      mat.total(1) shouldBe 1

      mat.getN(n = 5, 5) shouldBe (6 to 10)
      mat.get(5) shouldBe 6

      assertThrows[IndexOutOfBoundsException] {
        mat.get(10)
      }
    }

    "support a two dimensional array" in {
      val mat = Mat[Double](10,10, Scalar(0,0,0,0))

      val values = 1 to 100 map (_.toDouble)
      mat.putAll(0,values)

      mat.dims shouldBe 2
      mat.shape shouldBe Seq(10, 10)

      mat.total shouldBe 100
      mat.total(1) shouldBe 10

      mat.getN(n = 10, 1) shouldBe (11 to 20)
      mat.getN(n = 5, 9, 5) shouldBe (96 to 100)

      mat.get(5) shouldBe 51
      mat.getN(n = 5, 5) shouldBe (51 to 55)

      mat.get(5,5) shouldBe 56
      mat.getN(n = 5, 5, 5) shouldBe (56 to 60)
      mat.getN(n = 10, 5, 5) shouldBe (56 to 65)

      assertThrows[IndexOutOfBoundsException] {
        mat.get(10)
      }

      assertThrows[IndexOutOfBoundsException] {
        mat.get(9,10)
      }
    }

    "support a three dimentional array" in {
      val mat = Mat[Double](Scalar(0,0,0,0),2,10,10)

      val value = 1 to 200 map (_.toDouble)
      mat.putAll(0, value)

      mat.dims shouldBe 3
      mat.shape shouldBe Seq(2,10,10)

      mat.total shouldBe 200
      mat.total(1) shouldBe 100
      mat.total(2) shouldBe 10

      mat.getN(10,1,5) shouldBe (151 to 160)
      mat.getN(10,1,9,0) shouldBe (191 to 200)

      mat.getN(n = 10, 1) shouldBe (101 to 110)
      mat.getN(n = 5, 0, 5) shouldBe (51 to 55)

      mat.get(1) shouldBe 101

      mat.get(1, 5) shouldBe 151
      mat.getN(n = 5, 1, 5, 5) shouldBe (156 to 160)
      mat.getN(n = 10, 1, 5, 5) shouldBe (156 to 165)

      mat.get(1,5,9) shouldBe 160

      assertThrows[IndexOutOfBoundsException](mat.get(2))
      assertThrows[IndexOutOfBoundsException](mat.get(1, 10))
      assertThrows[IndexOutOfBoundsException](println(mat.get(1,9,10)))
    }

    "support multichannel matrix of Byte Scalars" in {
      val mat = Mat[Scalar4b](Some(3),Scalar(0,0,0,0),50, 50)

      val values = 0 until 50 map(_.toByte) flatMap(row => 0 until 50 map(_.toByte) map(col => Scalar4b(-128,row,col,0)))
      mat.putAll(values)

      mat.getN(n = 100,0) shouldBe values.take(100)
      mat.getN(n = 100,48) shouldBe values.takeRight(100)

      mat.get(0,0) shouldBe values.head
      mat.get(1,20) shouldBe values.drop(70).head
      mat.get(20,1) shouldBe values.drop(20 * 50 + 1).head
      mat.get(49,49) shouldBe values.last

      assertThrows[IndexOutOfBoundsException](mat.get(50))
      assertThrows[IndexOutOfBoundsException](mat.get(49,50))
      assertThrows[IndexOutOfBoundsException](mat.getN(n = 50,49,40))

    }
  }
}
