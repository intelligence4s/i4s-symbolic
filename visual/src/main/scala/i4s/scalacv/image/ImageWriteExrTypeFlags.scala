package i4s.scalacv.image

object ImageWriteExrTypeFlags extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val
  type ImageReadFlags = Value

  import scala.language.implicitConversions
  implicit def valueToImageReadFlag(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** no compression */
  val NoCompression: FlagVal = FlagVal(0)

  /** run length encoding */
  val Rle: FlagVal = FlagVal(1)

  /** zlib compression, one scan line at a time */
  val Zips: FlagVal = FlagVal(2)

  /** zlib compression, in blocks of 16 scan lines */
  val Zip: FlagVal = FlagVal(3)

  /** piz-based wavelet compression */
  val Piz: FlagVal = FlagVal(4)

  val Pxr24: FlagVal = FlagVal(5)

  /** lossy 4-by-4 pixel block compression, fixed compression rate */
  val B44: FlagVal = FlagVal(6)

  /** lossy 4-by-4 pixel block compression, flat fields are compressed more */
  val B44a:FlagVal = FlagVal(7)

  /** lossy DCT based compression, in blocks of 32 scanlines. More efficient for partial buffer access. Supported since OpenEXR 2.2.0. */
  val Dwaa: FlagVal = FlagVal(8)

  /** lossy DCT based compression, in blocks of 256 scanlines. More efficient space wise and faster to decode full frames than DWAA_COMPRESSION. Supported since OpenEXR 2.2.0. */
  val Dwab:FlagVal = FlagVal(9)

}
