package i4s.scalacv.core.model

import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types.Type
import org.bytedeco.javacpp.indexer.Indexer

trait Matable[T] {
  def channels: Int = 1
  def depth: Type

  def indexer(mat: Mat[T]): Indexer = {
    assert(Types(mat.depth) == depth, s"Incompatible Mat depth - ${Types(mat.depth)}")
    mat.createIndexer()
  }

  def get(mat: Mat[T], indices: Int*)(implicit indexer: Indexer): T
  def getN(mat: Mat[T], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[T]

  def put(mat: Mat[T], indices: Seq[Int], value: T)(implicit indexer: Indexer): Unit
  def putN(mat: Mat[T], indices: Seq[Int], values: Seq[T])(implicit indexer: Indexer): Unit
}
