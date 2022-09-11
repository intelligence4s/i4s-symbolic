package i4s.common

import java.nio.{Buffer, ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer, ShortBuffer}
import scala.annotation.implicitNotFound
import scala.reflect.runtime.universe._

trait BufferSupport {

  trait BufferOf[V <: AnyVal] {
    protected def typed[T <: Buffer: TypeTag](buffer: Buffer): T = {
      buffer match {
        case b: T => b
        case _ =>
          buffer match {
            case x if typeOf[T] <:< typeOf[ByteBuffer] => x.byteBuffer.asInstanceOf[T]
            case x if typeOf[T] <:< typeOf[CharBuffer] => x.byteBuffer.asCharBuffer.asInstanceOf[T]
            case x if typeOf[T] <:< typeOf[DoubleBuffer] => x.byteBuffer.asDoubleBuffer.asInstanceOf[T]
            case x if typeOf[T] <:< typeOf[FloatBuffer] => x.byteBuffer.asFloatBuffer.asInstanceOf[T]
            case x if typeOf[T] <:< typeOf[IntBuffer] => x.byteBuffer.asIntBuffer.asInstanceOf[T]
            case x if typeOf[T] <:< typeOf[LongBuffer] => x.byteBuffer.asLongBuffer.asInstanceOf[T]
            case x if typeOf[T] <:< typeOf[ShortBuffer] => x.byteBuffer.asShortBuffer.asInstanceOf[T]
            case _ => buffer.asInstanceOf[T] // may throw for an unexpected subclass of Buffer...
          }
      }
    }

    def getNext(buffer: Buffer): V
    def size(buffer: Buffer): Int
  }

  @implicitNotFound("No member of type class NumberLike in scope for ${T}")
  implicit val bufferOfBytes: BufferOf[Byte] = new BufferOf[Byte] {
    override def getNext(buffer: Buffer): Byte = typed[ByteBuffer](buffer).get
    override def size(buffer: Buffer): Int = typed[ByteBuffer](buffer).capacity
  }

  implicit val bufferOfChars: BufferOf[Char] = new BufferOf[Char] {
    override def getNext(buffer: Buffer): Char = typed[CharBuffer](buffer).get
    override def size(buffer: Buffer): Int = typed[CharBuffer](buffer).capacity
  }

  implicit val bufferOfDoubles: BufferOf[Double] = new BufferOf[Double] {
    override def getNext(buffer: Buffer): Double = typed[DoubleBuffer](buffer).get
    override def size(buffer: Buffer): Int = typed[DoubleBuffer](buffer).capacity
  }

  implicit var BufferOfFloats: BufferOf[Float] = new BufferOf[Float] {
    override def getNext(buffer: Buffer): Float = typed[FloatBuffer](buffer).get
    override def size(buffer: Buffer): Int = typed[FloatBuffer](buffer).capacity
  }

  implicit val BufferOfInts: BufferOf[Int] = new BufferOf[Int] {
    override def getNext(buffer: Buffer): Int = typed[IntBuffer](buffer).get
    override def size(buffer: Buffer): Int = typed[IntBuffer](buffer).capacity
  }

  implicit val BufferOfLongs: BufferOf[Long] = new BufferOf[Long] {
    override def getNext(buffer: Buffer): Long = typed[LongBuffer](buffer).get
    override def size(buffer: Buffer): Int = typed[LongBuffer](buffer).capacity
  }

  implicit val BufferOfShorts: BufferOf[Short] = new BufferOf[Short] {
    override def getNext(buffer: Buffer): Short = typed[ShortBuffer](buffer).get
    override def size(buffer: Buffer): Int = typed[ShortBuffer](buffer).capacity
  }

  implicit class BufferOps(buffer: Buffer) {

    def byteBuffer: ByteBuffer = buffer.asInstanceOf[ByteBuffer]

    def lazyListOf[V <: AnyVal](implicit tb: BufferOf[V]): LazyList[V] = {
      def next(): LazyList[V] =
        if (buffer.hasRemaining) tb.getNext(buffer) #:: next()
        else LazyList.empty[V]

      next()
    }

    def sizeOf[V <: AnyVal](implicit tb: BufferOf[V]): Int = tb.size(buffer)

    def lazyList: LazyList[Byte] = {
      def next(): LazyList[Byte] =
        if (buffer.hasRemaining) byteBuffer.get() #:: next()
        else LazyList.empty[Byte]

      next()
    }
  }

}
