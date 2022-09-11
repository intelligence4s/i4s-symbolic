package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable, Point}
import org.bytedeco.javacpp.indexer.{Indexer, IntIndexer}

trait PointMatable extends Matable[Point] {
  override def channels: Int = 2

  override def get(mat: Mat[Point], indices: Int*)(implicit indexer: Indexer): Point = {
    val longs = indices.map(_.toLong)

    Point(
      indexer.asInstanceOf[IntIndexer].get(longs :+ 0L :_*),
      indexer.asInstanceOf[IntIndexer].get(longs :+ 1L :_*)
    )
  }

  override def getN(mat: Mat[Point], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Point] = {
    val longs = indices.map(_.toLong)
    val xBuffer = new Array[Int](n)
    indexer.asInstanceOf[IntIndexer].get((longs :+ 0L).toArray,xBuffer)

    val yBuffer = new Array[Int](n)
    indexer.asInstanceOf[IntIndexer].get((longs :+ 1L).toArray,yBuffer)

    xBuffer.zip(yBuffer).map { case (x,y) => Point(x,y)}
  }

  override def put(mat: Mat[Point], indices: Seq[Int], value: Point)(implicit indexer: Indexer): Unit = {
    val longs = indices.map(_.toLong)
    indexer.asInstanceOf[IntIndexer].put((longs :+ 0L).toArray,value.x)
    indexer.asInstanceOf[IntIndexer].put((longs :+ 1L).toArray,value.y)
  }

  override def putN(mat: Mat[Point], indices: Seq[Int], values: Seq[Point])(implicit indexer: Indexer): Unit = {
    val longs = indices.map(_.toLong)

    val xBuffer = values.map(_.x).toArray
    val yBuffer = values.map(_.y).toArray

    indexer.asInstanceOf[IntIndexer].put((longs :+ 0L).toArray,xBuffer,0,xBuffer.length)
    indexer.asInstanceOf[IntIndexer].put((longs :+ 1L).toArray,yBuffer,0,yBuffer.length)
  }
}
