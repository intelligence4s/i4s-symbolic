package i4s.scalacv.core.model.mats

import i4s.scalacv.core.model.{Point, Rect, Scalar}
import i4s.scalacv.core.types.{MatTypes, Types}
import org.bytedeco.javacpp.indexer.{IntIndexer, UByteIndexer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MappedMatSpec extends AnyWordSpec with Matchers {
  "MappedMat" should {

    import i4s.scalacv.core.model.mats.syntax._

    "wrap native Mat of UBytes with MappedMat[Scalar,Int] constructor" in {
      val wrapped = new org.bytedeco.opencv.opencv_core.Mat(150, 150, MatTypes.makeType(Types.Cv8U, 3), Scalar.Red)
      val mat = new MappedMat[Scalar,Int](wrapped)
      mat.get(0) shouldBe Scalar.Red

      val indexer = wrapped.createIndexer().asInstanceOf[UByteIndexer]
      indexer.put(0L, 128)
      mat.get(0) shouldBe Scalar(128,0,255,0)
    }

    "copy native Mat of UBytes with MappedMat[Scalar,Int] constructor" in {
      val copied = new org.bytedeco.opencv.opencv_core.Mat(150, 150, MatTypes.makeType(Types.Cv8U, 3), Scalar.Red)
      val mat = MappedMat[Scalar,Int](copied)
      mat.get(0) shouldBe Scalar.Red

      val indexer = copied.createIndexer().asInstanceOf[UByteIndexer]
      indexer.put(0L, 128)
      mat.get(0) shouldBe Scalar.Red
    }

    "wrap native Mat of Int with MappedMat[Point,Int] constructor" in {
      val wrapped = new org.bytedeco.opencv.opencv_core.Mat(150, 150, MatTypes.makeType(Types.Cv32S, 2), Scalar(128,128,128,128))
      val mat = new MappedMat[Point,Int](wrapped)
      mat.get(0,0) shouldBe Point(128,128)

      val indexer = wrapped.createIndexer().asInstanceOf[IntIndexer]
      indexer.put(Array(0L, 0L, 0L), 64)
      mat.get(0,0) shouldBe Point(64,128)
    }

    "copy native Mat of Int with MappedMat[Point,Int] constructor" in {
      val copied = new org.bytedeco.opencv.opencv_core.Mat(150, 150, MatTypes.makeType(Types.Cv32S, 2), Scalar(128,128,128,128))
      val mat = MappedMat[Point,Int](copied)
      mat.get(0,0) shouldBe Point(128,128)

      val indexer = copied.createIndexer().asInstanceOf[IntIndexer]
      indexer.put(Array(0L, 0L, 0L), 64)
      mat.get(0) shouldBe Point(128,128)
    }

    "wrap native Mat of Int with MappedMat[Rect,Int] constructor" in {
      val wrapped = new org.bytedeco.opencv.opencv_core.Mat(150, 150, MatTypes.makeType(Types.Cv32S, 4), Scalar(128,128,128,128))
      val mat = new MappedMat[Rect,Int](wrapped)
      mat.get(0,0) shouldBe Rect(128,128,128,128)

      val indexer = wrapped.createIndexer().asInstanceOf[IntIndexer]
      indexer.put(Array(0L, 0L, 0L), 64)
      mat.get(0,0) shouldBe Rect(64,128,128,128)
    }

    "copy native Mat of Int with MappedMat[Rect,Int] constructor" in {
      val copied = new org.bytedeco.opencv.opencv_core.Mat(150, 150, MatTypes.makeType(Types.Cv32S, 4), Scalar(128,128,128,128))
      val mat = MappedMat[Rect,Int](copied)
      mat.get(0,0) shouldBe Rect(128,128,128,128)

      val indexer = copied.createIndexer().asInstanceOf[IntIndexer]
      indexer.put(Array(0L, 0L, 0L), 64)
      mat.get(0) shouldBe Rect(128,128,128,128)
    }

    "Put/Get Scalar values to/from a Byte Map" in {
      val mat = MappedMat[Scalar,Int](50,50,Some(Types.Cv8U),Some(3))

    }

    "Put/Get Scalar values to/from a Double Map" in {
      val mat = MappedMat[Scalar,Double](50,50,Some(Types.Cv64F),Some(3))

    }
  }
}
