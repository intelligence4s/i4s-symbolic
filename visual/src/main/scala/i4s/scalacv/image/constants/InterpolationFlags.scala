package i4s.scalacv.image.constants

object InterpolationFlags extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type InterpolationFlag = Value

  import scala.language.implicitConversions

  implicit def valueToInterpolationFlag(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** nearest neighbor interpolation */
  val Nearest: FlagVal = FlagVal(0)

  /** bilinear interpolation */
  val Linear: FlagVal = FlagVal(1)

  /** bicubic interpolation */
  val Cubic: FlagVal = FlagVal(2)

  /** resampling using pixel area relation. It may be a preferred method for image decimation, as
   * it gives moire'-free results. But when the image is zoomed, it is similar to the INTER_NEAREST
   * method. */
  val Area: FlagVal = FlagVal(3)

  /** Lanczos interpolation over 8x8 neighborhood */
  val Lanczos4: FlagVal = FlagVal(4)

  /** Bit exact bilinear interpolation */
  val LinearExact: FlagVal = FlagVal(5)

  /** Bit exact nearest neighbor interpolation. This will produce same results as
   * the nearest neighbor method in PIL, scikit-image or Matlab. */
  val NearestExact: FlagVal = FlagVal(6)

  /** mask for interpolation codes */
  val Max: FlagVal = FlagVal(7)

  /** flag, fills all of the destination image pixels. If some of them correspond to outliers in the
   * source image, they are set to zero */
  val WarpFillOutliers: FlagVal = FlagVal(8)

  /** flag, inverse transformation
   * <p>
   * For example, #linearPolar or #logPolar transforms:
   * - flag is __not__ set: <pre>dst( \rho , \phi ) &#61; src(x,y)</pre>
   * - flag is set: <pre>dst(x,y) &#61; src( \rho , \phi )</pre>
   */
  val WarpInverseMap: FlagVal = FlagVal(16)

  /** Remaps an image to/from polar space. */
  val WarpPolarLinear: FlagVal = FlagVal(0)

  /** Remaps an image to/from semilog-polar space. */
  val WarpPolarLog: FlagVal = FlagVal(256)
}
