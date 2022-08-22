package i4s.scalacv.image.constants

/** Imwrite PNG specific flags used to tune the compression algorithm.
 * These flags will be modify the way of PNG image compression and will be passed to the underlying zlib processing stage.
 * <p>
 * -   The effect of IMWRITE_PNG_STRATEGY_FILTERED is to force more Huffman coding and less string matching; it is somewhat intermediate between IMWRITE_PNG_STRATEGY_DEFAULT and IMWRITE_PNG_STRATEGY_HUFFMAN_ONLY.
 * -   IMWRITE_PNG_STRATEGY_RLE is designed to be almost as fast as IMWRITE_PNG_STRATEGY_HUFFMAN_ONLY, but give better compression for PNG image data.
 * -   The strategy parameter only affects the compression ratio but not the correctness of the compressed output even if it is not set appropriately.
 * -   IMWRITE_PNG_STRATEGY_FIXED prevents the use of dynamic Huffman codes, allowing for a simpler decoder for special applications.
 */
object ImageWritePngFlags extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type ImageReadFlags = Value

  import scala.language.implicitConversions

  implicit def valueToImageReadFlag(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** Use this value for normal data. */
  val Default: FlagVal = FlagVal(0)

  /** Use this value for data produced by a filter (or predictor).Filtered data consists mostly of small values with a somewhat random distribution. In this case, the compression algorithm is tuned to compress them better. */
  val Filtered: FlagVal = FlagVal(1)

  /** Use this value to force Huffman encoding only (no string match). */
  val HuffmanOnly: FlagVal = FlagVal(2)

  /** Use this value to limit match distances to one (run-length encoding). */
  val Rle: FlagVal = FlagVal(3)

  /** Using this value prevents the use of dynamic Huffman codes, allowing for a simpler decoder for special applications. */
  val Fixed: FlagVal = FlagVal(4)

}
