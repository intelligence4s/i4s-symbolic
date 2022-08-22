package i4s.scalacv.image

import i4s.scalacv.core.constants.{BorderTypes, DecompositionMethods}
import i4s.scalacv.core.constants.BorderTypes.BorderType
import i4s.scalacv.core.types.FlowTypes.FlowType
import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types.Type
import i4s.scalacv.core.constants.DecompositionMethods.DecompositionMethod
import i4s.scalacv.image.constants.DistanceLabelTypes.DistanceLabelType
import i4s.scalacv.image.constants.DistanceTypes.DistanceType
import i4s.scalacv.image.constants.InterpolationFlags.InterpolationFlag
import i4s.scalacv.image.constants.ThresholdMethods.ThresholdMethod
import i4s.scalacv.image.constants.ThresholdTypes.ThresholdType
import i4s.scalacv.image.constants.{DistanceLabelTypes, InterpolationFlags}
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_core.{GpuMat, Mat, Point, Point2f, Rect, Scalar, Size, UMat}

object Transforms extends Transforms

trait Transforms {

  /** Converts image transformation maps from one representation to another.
   * <p>
   * The function converts a pair of maps for remap from one representation to another. The following
   * options ( (map1.type(), map2.type()) <pre>\rightarrow</pre> (dstmap1.type(), dstmap2.type()) ) are
   * supported:
   * <p>
   * - <pre>\texttt{(CV_32FC1, CV_32FC1) \rightarrow \texttt{(CV_16SC2, CV_16UC1)}</pre>. This is the
   * most frequently used conversion operation, in which the original floating-point maps (see remap )
   * are converted to a more compact and much faster fixed-point representation. The first output array
   * contains the rounded coordinates and the second array (created only when nninterpolationfalse )
   * contains indices in the interpolation tables.
   * <p>
   * - <pre>\texttt{(CV_32FC2) \rightarrow \texttt{(CV_16SC2, CV_16UC1)}</pre>. The same as above but
   * the original maps are stored in one 2-channel matrix.
   * <p>
   * - Reverse conversion. Obviously, the reconstructed floating-point maps will not be exactly the same
   * as the originals.
   * <p>
   *
   * @param map1            The first input map of type CV_16SC2, CV_32FC1, or CV_32FC2 .
   * @param map2            The second input map of type CV_16UC1, CV_32FC1, or none (empty matrix),
   *                        respectively.
   * @param dstmap1         The first output map that has the type dstmap1type and the same size as src .
   * @param dstmap2         The second output map.
   * @param dstmap1type     Type of the first output map that should be CV_16SC2, CV_32FC1, or
   *                        CV_32FC2 .
   * @param nninterpolation Flag indicating whether the fixed-point maps are used for the
   *                        nearest-neighbor or for a more complex interpolation.
   *                        <p>
   * @see remap, undistort, initUndistortRectifyMap
   */
  def convertMaps(map1: Mat, map2: Mat, dstmap1: Mat, dstmap2: Mat, dstmap1type: FlowType, nninterpolation: Boolean): Unit =
    opencv_imgproc.convertMaps(map1,map2,dstmap1,dstmap2,dstmap1type.flag,nninterpolation)

  def convertMaps(map1: Mat, map2: Mat, dstmap1: Mat, dstmap2: Mat, dstmap1type: FlowType): Unit =
    convertMaps(map1,map2,dstmap1,dstmap2,dstmap1type,nninterpolation = true)

  /** \brief Calculates an affine matrix of 2D rotation.
   * <p>
   * The function calculates the following matrix:
   * <p>
   * <pre>\[\begin{bmatrix} \alpha &  \beta & (1- \alpha )  \cdot \texttt{center.x} -  \beta \cdot \texttt{center.y} \\ - \beta &  \alpha &  \beta \cdot \texttt{center.x} + (1- \alpha )  \cdot \texttt{center.y} \end{bmatrix}\]</pre>
   * <p>
   * where
   * <p>
   * <pre>\[\begin{array}{l} \alpha =  \texttt{scale} \cdot \cos \texttt{angle} , \\ \beta =  \texttt{scale} \cdot \sin \texttt{angle} \end{array}\]</pre>
   * <p>
   * The transformation maps the rotation center to itself. If this is not the target, adjust the shift.
   * <p>
   *
   * @param center Center of the rotation in the source image.
   * @param angle  Rotation angle in degrees. Positive values mean counter-clockwise rotation (the
   *               coordinate origin is assumed to be the top-left corner).
   * @param scale  Isotropic scale factor.
   *               <p>
   * @see getAffineTransform, warpAffine, transform
   */
  def getRotationMatrix2D(center: Point2f, angle: Double, scale: Double): Mat =
    opencv_imgproc.getRotationMatrix2D(center, angle, scale)

  /** \brief Calculates an affine transform from three pairs of the corresponding points.
   * <p>
   * The function calculates the <pre>2 * 3</pre> matrix of an affine transform so that:
   * <p>
   * <pre>\[\begin{bmatrix} x'_i \\ y'_i \end{bmatrix} = \texttt{map_matrix} \cdot \begin{bmatrix} x_i \\ y_i \\ 1 \end{bmatrix}\]</pre>
   * <p>
   * where
   * <p>
   * <pre>\[dst(i)=(x'_i,y'_i), src(i)=(x_i, y_i), i=0,1,2\]</pre>
   * <p>
   *
   * @param src Coordinates of triangle vertices in the source image.
   * @param dst Coordinates of the corresponding triangle vertices in the destination image.
   *            <p>
   * @see warpAffine, transform
   */
  def getAffineTransform(src: Point2f): (Mat, Point2f) = {
    val dst = new Point2f()
    val result = opencv_imgproc.getAffineTransform(src,dst)
    (result,dst)
  }

  /** \brief Inverts an affine transformation.
   * <p>
   * The function computes an inverse affine transformation represented by <pre>2 * 3</pre> matrix M:
   * <p>
   * <pre>\[\begin{bmatrix} a_{11} & a_{12} & b_1  \\ a_{21} & a_{22} & b_2 \end{bmatrix}\]</pre>
   * <p>
   * The result is also a <pre>2 * 3</pre> matrix of the same type as M.
   * <p>
   *
   * @param affine        Original affine transformation.
   * @param reverseAffine Output reverse affine transformation.
   */
  def invertAffineTransform(affine: Mat): Mat = {
    val reverseAffine = new Mat()
    opencv_imgproc.invertAffineTransform(affine,reverseAffine)
    reverseAffine
  }

  /** \brief Calculates a perspective transform from four pairs of the corresponding points.
   * <p>
   * The function calculates the <pre>3 * 3</pre> matrix of a perspective transform so that:
   * <p>
   * <pre>\[\begin{bmatrix} t_i x'_i \\ t_i y'_i \\ t_i \end{bmatrix} = \texttt{map_matrix} \cdot \begin{bmatrix} x_i \\ y_i \\ 1 \end{bmatrix}\]</pre>
   * <p>
   * where
   * <p>
   * <pre>\[dst(i)=(x'_i,y'_i), src(i)=(x_i, y_i), i=0,1,2,3\]</pre>
   * <p>
   *
   * @param src         Coordinates of quadrangle vertices in the source image.
   * @param dst         Coordinates of the corresponding quadrangle vertices in the destination image.
   * @param solveMethod method passed to cv::solve (#DecompTypes)
   *                    <p>
   * @see findHomography, warpPerspective, perspectiveTransform
   */

  def getPerspectiveTransform(src: Mat, dst: Mat, solveMethod: DecompositionMethod): Mat =
    opencv_imgproc.getPerspectiveTransform(src, dst, solveMethod.flag)

  def getPerspectiveTransform(src: Mat, dst: Mat): Mat =
    getPerspectiveTransform(src, dst, DecompositionMethods.Lu)

  /** \overload */
  def getPerspectiveTransform(src: Point2f, dst: Point2f, solveMethod: DecompositionMethod): Mat =
    opencv_imgproc.getPerspectiveTransform(src, dst, solveMethod.flag)

  def getPerspectiveTransform(src: Point2f, dst: Point2f): Mat =
    getPerspectiveTransform(src, dst, DecompositionMethods.Lu)

  def getAffineTransform(src: Mat, dst: Mat): Mat =
    opencv_imgproc.getAffineTransform(src, dst)

  implicit class ImageTransforms(image: Mat) {

    /** \brief Resizes an image.
     * <p>
     * The function resize resizes the image src down to or up to the specified size. Note that the
     * initial dst type or size are not taken into account. Instead, the size and type are derived from
     * the <pre>src</pre>,<pre>dsize</pre>,<pre>fx</pre>, and <pre>fy</pre>. If you want to resize src so that it fits the pre-created dst,
     * you may call the function as follows:
     * <pre>
     * // explicitly specify dsize=dst.size(); fx and fy will be computed from that.
     * resize(src, dst, dst.size(), 0, 0, interpolation);
     * </pre>
     * If you want to decimate the image by factor of 2 in each direction, you can call the function this
     * way:
     * <pre>
     * // specify fx and fy and let the function compute the destination image size.
     * resize(src, dst, Size(), 0.5, 0.5, interpolation);
     * </pre>
     * To shrink an image, it will generally look best with #INTER_AREA interpolation, whereas to
     * enlarge an image, it will generally look best with c#INTER_CUBIC (slow) or #INTER_LINEAR
     * (faster but still looks OK).
     * <p>
     * @param src           input image.
     * @param dst           output image; it has the size dsize (when it is non-zero) or the size computed from
     *                      src.size(), fx, and fy; the type of dst is the same as of src.
     * @param dsize         output image size; if it equals zero (<pre>None</pre> in Python), it is computed as:
     *                      <pre>\[\texttt{dsize = Size(round(fx*src.cols), round(fy*src.rows))}\]</pre>
     *                      Either dsize or both fx and fy must be non-zero.
     * @param fx            scale factor along the horizontal axis; when it equals 0, it is computed as
     *                      <pre>\[\texttt{(double)dsize.width/src.cols}\]</pre>
     * @param fy            scale factor along the vertical axis; when it equals 0, it is computed as
     *                      <pre>\[\texttt{(double)dsize.height/src.rows}\]</pre>
     * @param interpolation interpolation method, see #InterpolationFlags
     *                      <p>
     * @see warpAffine, warpPerspective, remap
     *
     */
    def resize(dsize: Size, fx: Double, fy: Double, interpolation: InterpolationFlag): Mat = {
      val dst = new Mat()
      opencv_imgproc.resize(image,dst,dsize,fx,fy,interpolation.flag)
      dst
    }

    def resize(dsize: Size): Unit = resize(dsize, fx = 0, fy = 0, InterpolationFlags.Linear)

    /** \brief Applies an affine transformation to an image.
     * <p>
     * The function warpAffine transforms the source image using the specified matrix:
     * <p>
     * <pre>\[\texttt{dst} (x,y) =  \texttt{src} ( \texttt{M} _{11} x +  \texttt{M} _{12} y +  \texttt{M} _{13}, \texttt{M} _{21} x +  \texttt{M} _{22} y +  \texttt{M} _{23})\]</pre>
     * <p>
     * when the flag #WARP_INVERSE_MAP is set. Otherwise, the transformation is first inverted
     * with #invertAffineTransform and then put in the formula above instead of M. The function cannot
     * operate in-place.
     * <p>
     * @param src         input image.
     * @param dst         output image that has the size dsize and the same type as src .
     * @param matrix      <pre>2 * 3</pre> transformation matrix.
     * @param dsize       size of the output image.
     * @param flags combination of interpolation methods (see #InterpolationFlags) and the optional
     *              flag #WARP_INVERSE_MAP that means that M is the inverse transformation (
     *              <pre>\texttt{dst}</pre>\rightarrow\texttt{src}} ).
     * @param borderMode  pixel extrapolation method (see #BorderTypes); when
     *                    borderMode=#BORDER_TRANSPARENT, it means that the pixels in the destination image corresponding to
     *                    the "outliers" in the source image are not modified by the function.
     * @param borderValue value used in case of a constant border; by default, it is 0.
     *                    <p>
     * @see warpPerspective, resize, remap, getRectSubPix, transform
     */
    def warpAffine(matrix: Mat, dsize: Size, flags: Set[InterpolationFlag], borderMode: BorderType, borderValue: Scalar): Mat = {
      val iflags = flags.foldLeft(0)(_ | _.flag)
      val dst = new Mat()
      opencv_imgproc.warpAffine(image,dst,matrix,dsize,iflags,borderMode.flag,borderValue)
      dst
    }

    def warpAffine(matrix: Mat, dsize: Size): Mat =
      warpAffine(matrix,dsize,Set(InterpolationFlags.Linear),BorderTypes.Constant,new Scalar())

    /** \brief Applies a perspective transformation to an image.
     * <p>
     * The function warpPerspective transforms the source image using the specified matrix:
     * <p>
     * <pre>\[\texttt{dst} (x,y) =  \texttt{src} \left ( \frac{M_{11} x + M_{12} y + M_{13}}{M_{31} x + M_{32} y + M_{33}} ,
     * \frac{M_{21} x + M_{22} y + M_{23}}{M_{31} x + M_{32} y + M_{33}} \right )\]</pre>
     * <p>
     * when the flag #WARP_INVERSE_MAP is set. Otherwise, the transformation is first inverted with invert
     * and then put in the formula above instead of M. The function cannot operate in-place.
     * <p>
     * @param src         input image.
     * @param dst         output image that has the size dsize and the same type as src .
     * @param matrix      <pre>3 * 3</pre> transformation matrix.
     * @param dsize       size of the output image.
     * @param flags       combination of interpolation methods (#INTER_LINEAR or #INTER_NEAREST) and the
     *                    optional flag #WARP_INVERSE_MAP, that sets M as the inverse transformation (
     *                    <pre>\texttt{dst\rightarrow\texttt{src}} </pre>).
     * @param borderMode  pixel extrapolation method (#BORDER_CONSTANT or #BORDER_REPLICATE).
     * @param borderValue value used in case of a constant border; by default, it equals 0.
     *                    <p>
     * @see warpAffine, resize, remap, getRectSubPix, perspectiveTransform
     */
    def warpPerspective(matrix: Mat, dsize: Size, flags: Set[InterpolationFlag], borderMode: BorderType, borderValue: Scalar): Mat =
    {
      val iflags = flags.foldLeft(0)(_ | _.flag)
      val dst = new Mat()
      opencv_imgproc.warpPerspective(image,dst,matrix,dsize,iflags,borderMode.flag,borderValue)
      dst
    }

    def warpPerspective(matrix: Mat, dsize: Size): Mat =
      warpPerspective(matrix,dsize,Set(InterpolationFlags.Linear),BorderTypes.Constant,new Scalar())

    /** \brief Applies a generic geometrical transformation to an image.
     * <p>
     * The function remap transforms the source image using the specified map:
     * <p>
     * <pre>\[\texttt{dst} (x,y) =  \texttt{src} (map_x(x,y),map_y(x,y))\]</pre>
     * <p>
     * where values of pixels with non-integer coordinates are computed using one of available
     * interpolation methods. <pre>map_x</pre> and <pre>map_y</pre> can be encoded as separate floating-point maps
     * in <pre>map_1</pre> and <pre>map_2</pre> respectively, or interleaved floating-point maps of <pre>(x,y)</pre> in
     * <pre>map_1</pre>, or fixed-point maps created by using convertMaps. The reason you might want to
     * convert from floating to fixed-point representations of a map is that they can yield much faster
     * (\~2x) remapping operations. In the converted case, <pre>map_1</pre> contains pairs (cvFloor(x),
     * cvFloor(y)) and <pre>map_2</pre> contains indices in a table of interpolation coefficients.
     * <p>
     * This function cannot operate in-place.
     * <p>
     * @param src           Source image.
     * @param dst           Destination image. It has the same size as map1 and the same type as src .
     * @param map1          The first map of either (x,y) points or just x values having the type CV_16SC2 ,
     *                      CV_32FC1, or CV_32FC2. See convertMaps for details on converting a floating point
     *                      representation to fixed-point for speed.
     * @param map2          The second map of y values having the type CV_16UC1, CV_32FC1, or none (empty map
     *                      if map1 is (x,y) points), respectively.
     * @param interpolation Interpolation method (see #InterpolationFlags). The methods #INTER_AREA
     *                      and #INTER_LINEAR_EXACT are not supported by this function.
     * @param borderMode    Pixel extrapolation method (see #BorderTypes). When
     *                      borderMode=#BORDER_TRANSPARENT, it means that the pixels in the destination image that
     *                      corresponds to the "outliers" in the source image are not modified by the function.
     * @param borderValue   Value used in case of a constant border. By default, it is 0.
     *                      \note
     *                      Due to current implementation limitations the size of an input and output images should be less than 32767x32767.
     */
    def remap(map1: Mat, map2: Mat, interpolation: InterpolationFlag, borderMode: BorderType /*=cv::BORDER_CONSTANT*/, borderValue: Scalar): Mat = {
      val dst = new Mat()
      opencv_imgproc.remap(image,dst,map1,map2,interpolation.flag,borderMode.flag,borderValue)
      dst
    }

    def remap(map1: Mat, map2: Mat, interpolation: InterpolationFlag): Mat =
      remap(map1,map2,interpolation,BorderTypes.Constant,new Scalar())

    /** \brief Retrieves a pixel rectangle from an image with sub-pixel accuracy.
     * <p>
     * The function getRectSubPix extracts pixels from src:
     * <p>
     * <pre>\[patch(x, y) = src(x +  \texttt{center.x} - ( \texttt{dst.cols} -1)*0.5, y +  \texttt{center.y} - ( \texttt{dst.rows} -1)*0.5)\]}</pre>
     * <p>
     * where the values of the pixels at non-integer coordinates are retrieved using bilinear
     * interpolation. Every channel of multi-channel images is processed independently. Also
     * the image should be a single channel or three channel image. While the center of the
     * rectangle must be inside the image, parts of the rectangle may be outside.
     * <p>
     * @param image     Source image.
     * @param patchSize Size of the extracted patch.
     * @param center    Floating point coordinates of the center of the extracted rectangle within the
     *                  source image. The center must be inside the image.
     * @param patch     Extracted patch that has the size patchSize and the same number of channels as src .
     * @param patchType Depth of the extracted pixels. By default, they have the same depth as src .
     *                  <p>
     * @see warpAffine, warpPerspective
     */
    def rectSubPixels(patchSize: Size, center: Point2f, patchType: Type): Mat = {
      val patch = new Mat()
      opencv_imgproc.getRectSubPix(image,patchSize,center,patch,patchType.flag)
      patch
    }

    def rectSubPixels(patchSize: Size, center: Point2f): Mat =
      rectSubPixels(patchSize,center,Types.CvUndefined)

    /** \example samples/cpp/polar_transforms.cpp
     * An example using the cv::linearPolar and cv::logPolar operations
     */

    /** \brief Remaps an image to polar coordinates space.
     * <p>
     * @deprecated This function produces same result as cv::warpPolar(src, dst, src.size(), center, maxRadius, flags)
     *             <p>
     *             \internal
     *             Transform the source image using the following transformation (See \ref polar_remaps_reference_image "Polar remaps reference image c)"):
     *             <pre>\[\begin{array}{l}
     *             dst( \rho , \phi ) = src(x,y) \\
     *             dst.size() \leftarrow src.size()
     *             \end{array}\]</pre>
     *             <p>
     *             where
     *             <pre>\[\begin{array}{l}
     *             I = (dx,dy) = (x - center.x,y - center.y) \\
     *             \rho = Kmag \cdot \texttt{magnitude} (I) ,\\
     *             \phi = angle \cdot \texttt{angle} (I)
     *             \end{array}\]</pre>
     *             <p>
     *             and
     *             <pre>\[\begin{array}{l}
     *             Kx = src.cols / maxRadius \\
     *             Ky = src.rows / 2\Pi
     *             \end{array}\]</pre>
     *             <p>
     *             <p>
     * @param src       Source image
     * @param dst       Destination image. It will have same size and type as src.
     * @param center    The transformation center;
     * @param maxRadius The radius of the bounding circle to transform. It determines the inverse magnitude scale parameter too.
     * @param flags     A combination of interpolation methods, see #InterpolationFlags
     *                  <p>
     *                  \note
     *                  - The function can not operate in-place.
     *                  - To calculate magnitude and angle in degrees #cartToPolar is used internally thus angles are measured from 0 to 360 with accuracy about 0.3 degrees.
     *                  <p>
     */
    def linearPolar(center: Point2f, maxRadius: Double, flags: Set[InterpolationFlag]): Mat = {
      val iflags = flags.foldLeft(0)(_ | _.flag)
      val dst = new Mat()
      opencv_imgproc.linearPolar(image,dst,center,maxRadius,iflags)
      dst
    }


    /** \brief Remaps an image to polar or semilog-polar coordinates space
     * <p>
     * \anchor polar_remaps_reference_image
     * ![Polar remaps reference](pics/polar_remap_doc.png)
     * <p>
     * Transform the source image using the following transformation:
     * <pre> [ dst(\rho , \phi ) = src(x,y) ]</pre>
     * <p>
     * where
     * <pre>\[\begin{array}{l}
     * \vec{I} = (x - center.x, \;y - center.y) \\
     * \phi = Kangle \cdot \texttt{angle} (\vec{I}) \\
     * \rho = \left\{\begin{matrix}
     * Klin \cdot \texttt{magnitude} (\vec{I}) & default \\
     * Klog \cdot log_e(\texttt{magnitude} (\vec{I})) & if \; semilog \\
     * \end{matrix}\right.
     * \end{array}
     * \]</pre>
     * <p>
     * and
     * <pre>\[\begin{array}{l}
     * Kangle = dsize.height / 2\Pi \\
     * Klin = dsize.width / maxRadius \\
     * Klog = dsize.width / log_e(maxRadius) \\
     * \end{array}
     * \]}</pre>
     * <p>
     * <p>
     * \par Linear vs semilog mapping
     * <p>
     * Polar mapping can be linear or semi-log. Add one of #WarpPolarMode to <pre>flags</pre> to specify the polar mapping mode.
     * <p>
     * Linear is the default mode.
     * <p>
     * The semilog mapping emulates the human "foveal" vision that permit very high acuity on the line of sight (central vision)
     * in contrast to peripheral vision where acuity is minor.
     * <p>
     * \par Option on <pre>dsize</pre>:
     * <p>
     * - if both values in <pre>dsize <&#61;0 </pre> (default),
     * the destination image will have (almost) same area of source bounding circle:
     * <pre>\[\begin{array}{l}
     * dsize.area  \leftarrow (maxRadius \power 2 \cdot \Pi) \\
     * dsize.width = \texttt{cvRound}(maxRadius) \\
     * dsize.height = \texttt{cvRound}(maxRadius \cdot \Pi) \\
     * \end{array}\]
     * </pre>
     * <p>
     * <p>
     * - if only <pre>dsize.height <&#61; 0</pre>,
     * the destination image area will be proportional to the bounding circle area but scaled by <pre>Kx * Kx</pre>:
     * <pre> \[\begin{array}{l}
     * dsize.height = \texttt{cvRound}(dsize.width \cdot \Pi) \\
     * \end{array}
     * \]</pre>
     * <p>
     * - if both values in <pre>dsize > 0 </pre>,
     * the destination image will have the given size therefore the area of the bounding circle will be scaled to <pre>dsize</pre>.
     * <p>
     * <p>
     * \par Reverse mapping
     * <p>
     * You can get reverse mapping adding #WARP_INVERSE_MAP to <pre>flags</pre>
     * \snippet polar_transforms.cpp InverseMap
     * <p>
     * In addiction, to calculate the original coordinate from a polar mapped coordinate <pre>(rho, phi)->(x, y)</pre>:
     * \snippet polar_transforms.cpp InverseCoordinate
     * <p>
     * @param src       Source image.
     * @param dst       Destination image. It will have same type as src.
     * @param dsize     The destination image size (see description for valid options).
     * @param center    The transformation center.
     * @param maxRadius The radius of the bounding circle to transform. It determines the inverse magnitude scale parameter too.
     * @param flags     A combination of interpolation methods, #InterpolationFlags + #WarpPolarMode.
     *                  - Add #WARP_POLAR_LINEAR to select linear polar mapping (default)
     *                  - Add #WARP_POLAR_LOG to select semilog polar mapping
     *                  - Add #WARP_INVERSE_MAP for reverse mapping.
     *                  \note
     *                  - The function can not operate in-place.
     *                  - To calculate magnitude and angle in degrees #cartToPolar is used internally thus angles are measured from 0 to 360 with accuracy about 0.3 degrees.
     *                  - This function uses #remap. Due to current implementation limitations the size of an input and output images should be less than 32767x32767.
     *                  <p>
     * @see cv::remap
     */
    def warpPolar(dsize: Size, center: Point2f, maxRadius: Double, flags: Set[InterpolationFlag]): Mat = {
      val dst = new Mat()
      val iflags = flags.foldLeft(0)(_ | _.flag)
      opencv_imgproc.warpPolar(image,dst,dsize,center,maxRadius,iflags)
      dst
    }

    /** \brief Calculates the integral of an image.
     * <p>
     * The function calculates one or more integral images for the source image as follows:
     * <p>
     * <pre>\[\texttt{sum} (X,Y) =  \sum _{x<X,y<Y}  \texttt{image} (x,y)\]</pre>
     * <p>
     * <pre>\[\texttt{sqsum} (X,Y) =  \sum _{x<X,y<Y}  \texttt{image} (x,y) \power 2\]</pre>
     * <p>
     * <pre>\[\texttt{tilted} (X,Y) =  \sum _{y<Y,abs(x-X+1) \leq Y-y-1}  \texttt{image} (x,y)\]</pre>
     * <p>
     * Using these integral images, you can calculate sum, mean, and standard deviation over a specific
     * up-right or rotated rectangular region of the image in a constant time, for example:
     * <p>
     * <pre>\[\sum _{x_1 \leq x < x_2,  \, y_1  \leq y < y_2}  \texttt{image} (x,y) =  \texttt{sum} (x_2,y_2)- \texttt{sum} (x_1,y_2)- \texttt{sum} (x_2,y_1)+ \texttt{sum} (x_1,y_1)\]</pre>
     * <p>
     * It makes possible to do a fast blurring or fast block correlation with a variable window size, for
     * example. In case of multi-channel images, sums for each channel are accumulated independently.
     * <p>
     * As a practical example, the next figure shows the calculation of the integral of a straight
     * rectangle Rect(3,3,3,2) and of a tilted rectangle Rect(5,1,2,3) . The selected pixels in the
     * original image are shown, as well as the relative pixels in the integral images sum and tilted .
     * <p>
     * ![integral calculation example](pics/integral.png)
     * <p>
     * @param src     input image as <pre>W * H</pre>, 8-bit or floating-point (32f or 64f).
     * @param sum     integral image as <pre>(W+1) * (H+1)</pre> , 32-bit integer or floating-point (32f or 64f).
     * @param sqsum   integral image for squared pixel values; it is <pre>(W+1) * (H+1)</pre>, double-precision
     *                floating-point (64f) array.
     * @param tilted  integral for the image rotated by 45 degrees; it is <pre>(W+1) * (H+1)</pre> array with
     *                the same data type as sum.
     * @param sdepth  desired depth of the integral and the tilted integral images, CV_32S, CV_32F, or
     *                CV_64F.
     * @param sqdepth desired depth of the integral image of squared pixel values, CV_32F or CV_64F.
     */
    def integral(sdepth: Int): Mat = {
      val sum = new Mat()
      opencv_imgproc.integral(image, sum, sdepth)
      sum
    }

    def integral(): Mat = integral(sdepth = -1)

    def integral2(sdepth: Int, sqdepth: Int): (Mat,Mat) = {
      val sum = new Mat()
      val sqsum = new Mat()
      opencv_imgproc.integral2(image,sum,sqsum,sdepth,sqdepth)
      (sum,sqsum)
    }

    def integral2(): (Mat,Mat) = integral2(sdepth = -1,sqdepth = -1)

    def integral3(sdepth: Int, sqdepth: Int): (Mat,Mat,Mat) = {
      val sum = new Mat()
      val sqsum = new Mat()
      val tilted = new Mat()
      opencv_imgproc.integral3(image,sum,sqsum,tilted,sdepth,sqdepth)
      (sum,sqsum,tilted)
    }

    def integral3(): (Mat,Mat,Mat) = integral3(sdepth = -1,sqdepth = -1)

    /** \brief Applies a fixed-level threshold to each array element.
     * <p>
     * The function applies fixed-level thresholding to a multiple-channel array. The function is typically
     * used to get a bi-level (binary) image out of a grayscale image ( #compare could be also used for
     * this purpose) or for removing a noise, that is, filtering out pixels with too small or too large
     * values. There are several types of thresholding supported by the function. They are determined by
     * type parameter.
     * <p>
     * Also, the special values #THRESH_OTSU or #THRESH_TRIANGLE may be combined with one of the
     * above values. In these cases, the function determines the optimal threshold value using the Otsu's
     * or Triangle algorithm and uses it instead of the specified thresh.
     * <p>
     * \note Currently, the Otsu's and Triangle methods are implemented only for 8-bit single-channel images.
     * <p>
     * @param src    input array (multiple-channel, 8-bit or 32-bit floating point).
     * @param dst    output array of the same size  and type and the same number of channels as src.
     * @param thresh threshold value.
     * @param maxval maximum value to use with the #THRESH_BINARY and #THRESH_BINARY_INV thresholding
     *               types.
     * @param thresholdType thresholding type (see #ThresholdTypes).
     * @return the computed threshold value if Otsu's or Triangle methods used.
     *         <p>
     * @see adaptiveThreshold, findContours, compare, min, max
     */
    def threshold(thresh: Double, maxval: Double, thresholdType: Set[ThresholdType]): (Mat,Double) = {
      val tflag = thresholdType.foldLeft(0)(_ | _.flag)
      val dst = new Mat()
      val result = opencv_imgproc.threshold(image,dst,thresh,maxval,tflag)
      (dst,result)
    }

    /** \brief Applies an adaptive threshold to an array.
     * <p>
     * The function transforms a grayscale image to a binary image according to the formulae:
     * -   **THRESH_BINARY**
     * <pre>\[dst(x,y) =  \fork{\texttt{maxValue}}{if \(src(x,y) > T(x,y)\)}{0}{otherwise}\]</pre>
     * -   **THRESH_BINARY_INV**
     * <pre>\[dst(x,y) =  \fork{0}{if \(src(x,y) > T(x,y)\)}{\texttt{maxValue}}{otherwise}\]</pre>
     * where <pre>T(x,y)</pre> is a threshold calculated individually for each pixel (see adaptiveMethod parameter).
     * <p>
     * The function can process the image in-place.
     * <p>
     *
     * @param src            Source 8-bit single-channel image.
     * @param dst            Destination image of the same size and the same type as src.
     * @param maxValue       Non-zero value assigned to the pixels for which the condition is satisfied
     * @param adaptiveMethod Adaptive thresholding algorithm to use, see #AdaptiveThresholdTypes.
     *                       The #BORDER_REPLICATE | #BORDER_ISOLATED is used to process boundaries.
     * @param thresholdType  Thresholding type that must be either #THRESH_BINARY or #THRESH_BINARY_INV,
     *                       see #ThresholdTypes.
     * @param blockSize      Size of a pixel neighborhood that is used to calculate a threshold value for the
     *                       pixel: 3, 5, 7, and so on.
     * @param constant              Constant subtracted from the mean or weighted mean (see the details below). Normally, it
     *                       is positive but may be zero or negative as well.
     *                       <p>
     * @see threshold, blur, GaussianBlur
     */
    def adaptiveThreshold(maxValue: Double, adaptiveMethod: ThresholdMethod, thresholdType: ThresholdType, blockSize: Int, constant: Double): Mat = {
      val dst = new Mat()
      opencv_imgproc.adaptiveThreshold(image,dst,maxValue,adaptiveMethod.flag,thresholdType.flag,blockSize,constant)
      dst
    }

    /** \brief Calculates the distance to the closest zero pixel for each pixel of the source image.
     * <p>
     * The function cv::distanceTransform calculates the approximate or precise distance from every binary
     * image pixel to the nearest zero pixel. For zero image pixels, the distance will obviously be zero.
     * <p>
     * When maskSize == #DIST_MASK_PRECISE and distanceType == #DIST_L2 , the function runs the
     * algorithm described in \cite Felzenszwalb04 . This algorithm is parallelized with the TBB library.
     * <p>
     * In other cases, the algorithm \cite Borgefors86 is used. This means that for a pixel the function
     * finds the shortest path to the nearest zero pixel consisting of basic shifts: horizontal, vertical,
     * diagonal, or knight's move (the latest is available for a <pre>5 x 5</pre> mask). The overall
     * distance is calculated as a sum of these basic distances. Since the distance function should be
     * symmetric, all of the horizontal and vertical shifts must have the same cost (denoted as a ), all
     * the diagonal shifts must have the same cost (denoted as <pre>b</pre>), and all knight's moves must have the
     * same cost (denoted as <pre>c</pre>). For the #DIST_C and #DIST_L1 types, the distance is calculated
     * precisely, whereas for #DIST_L2 (Euclidean distance) the distance can be calculated only with a
     * relative error (a <pre>5 x 5</pre> mask gives more accurate results). For <pre>a, b, and c</pre>, OpenCV
     * uses the values suggested in the original paper:
     * - DIST_L1: <pre>a &#61; 1, b &#61; 2</pre>
     * - DIST_L2:
     * - <pre>3 x 3</pre>: <pre>a&#61;0.955, b&#61;1.3693</pre>
     * - <pre>5 x 5</pre>: <pre> a=1, b=1.4, c=2.1969</pre>
     * - DIST_C: <pre>a &#61; 1, b &#61; 1</pre>
     * <p>
     * Typically, for a fast, coarse distance estimation #DIST_L2, a <pre>3 x 3</pre> mask is used. For a
     * more accurate distance estimation #DIST_L2, a <pre>5 x 5</pre> mask or the precise algorithm is used.
     * Note that both the precise and the approximate algorithms are linear on the number of pixels.
     * <p>
     * This variant of the function does not only compute the minimum distance for each pixel <pre>(x, y)</pre>
     * but also identifies the nearest connected component consisting of zero pixels
     * (labelType==#DIST_LABEL_CCOMP) or the nearest zero pixel (labelType==#DIST_LABEL_PIXEL). Index of the
     * component/pixel is stored in <pre>labels(x, y)</pre>. When labelType==#DIST_LABEL_CCOMP, the function
     * automatically finds connected components of zero pixels in the input image and marks them with
     * distinct labels. When labelType==#DIST_LABEL_PIXEL, the function scans through the input image and
     * marks all the zero pixels with distinct labels.
     * <p>
     * In this mode, the complexity is still linear. That is, the function provides a very fast way to
     * compute the Voronoi diagram for a binary image. Currently, the second variant can use only the
     * approximate distance transform algorithm, i.e. maskSize=#DIST_MASK_PRECISE is not supported
     * yet.
     * <p>
     * @param src          8-bit, single-channel (binary) source image.
     * @param dst          Output image with calculated distances. It is a 8-bit or 32-bit floating-point,
     *                     single-channel image of the same size as src.
     * @param labels       Output 2D array of labels (the discrete Voronoi diagram). It has the type
     *                     CV_32SC1 and the same size as src.
     * @param distanceType Type of distance, see #DistanceTypes
     * @param maskSize Size of the distance transform mask, see #DistanceTransformMasks.
     *                 #DIST_MASK_PRECISE is not supported by this variant. In case of the #DIST_L1 or #DIST_C distance type,
     *                 the parameter is forced to 3 because a <pre>3 x 3</pre> mask gives the same result as <pre>5 x 5</pre>
     *                 or any larger aperture.
     * @param labelType    Type of the label array to build, see #DistanceTransformLabelTypes.
     */
    def distanceTransformWithLabels(labels: Mat, distanceType: DistanceType, maskSize: Int, labelType: DistanceLabelType): Mat = {
      val dst = new Mat()
      opencv_imgproc.distanceTransformWithLabels(image,dst,labels,distanceType.flag,maskSize,labelType.flag)
      dst
    }

    def distanceTransformWithLabels(labels: Mat, distanceType: DistanceType, maskSize: Int): Mat =
      distanceTransformWithLabels(labels,distanceType,maskSize,DistanceLabelTypes.ConnectedComponent)

    /** \overload
     * @param src          8-bit, single-channel (binary) source image.
     * @param dst          Output image with calculated distances. It is a 8-bit or 32-bit floating-point,
     *                     single-channel image of the same size as src .
     * @param distanceType Type of distance, see #DistanceTypes
     * @param maskSize     Size of the distance transform mask, see #DistanceTransformMasks. In case of the
     *                     #DIST_L1 or #DIST_C distance type, the parameter is forced to 3 because a {@code 3\times 3} mask gives
     *                     the same result as {@code 5\times 5} or any larger aperture.
     * @param dstType      Type of output image. It can be CV_8U or CV_32F. Type CV_8U can be used only for
     *                     the first variant of the function and distanceType == #DIST_L1.
     */
    def distanceTransform(distanceType: DistanceType, maskSize: Int, dstType: Type): Mat = {
      val dst = new Mat()
      opencv_imgproc.distanceTransform(image,dst,distanceType.flag,maskSize,dstType.flag)
      dst
    }

    def distanceTransform(distanceType: DistanceType, maskSize: Int): Mat =
      distanceTransform(distanceType,maskSize,Types.Cv32F)

    /** \brief Fills a connected component with the given color.
     * <p>
     * The function cv::floodFill fills a connected component starting from the seed point with the specified
     * color. The connectivity is determined by the color/brightness closeness of the neighbor pixels. The
     * pixel at {@code (x,y)} is considered to belong to the repainted domain if:
     * <p>
     * - in case of a grayscale image and floating range
     * <pre>{@code \[\texttt{src} (x',y')- \texttt{loDiff} \leq \texttt{src} (x,y)  \leq \texttt{src} (x',y')+ \texttt{upDiff}\]}</pre>
     * <p>
     * <p>
     * - in case of a grayscale image and fixed range
     * <pre>{@code \[\texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)- \texttt{loDiff} \leq \texttt{src} (x,y)  \leq \texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)+ \texttt{upDiff}\]}</pre>
     * <p>
     * <p>
     * - in case of a color image and floating range
     * <pre>{@code \[\texttt{src} (x',y')_r- \texttt{loDiff} _r \leq \texttt{src} (x,y)_r \leq \texttt{src} (x',y')_r+ \texttt{upDiff} _r,\]}</pre>
     * <pre>{@code \[\texttt{src} (x',y')_g- \texttt{loDiff} _g \leq \texttt{src} (x,y)_g \leq \texttt{src} (x',y')_g+ \texttt{upDiff} _g\]}</pre>
     * and
     * <pre>{@code \[\texttt{src} (x',y')_b- \texttt{loDiff} _b \leq \texttt{src} (x,y)_b \leq \texttt{src} (x',y')_b+ \texttt{upDiff} _b\]}</pre>
     * <p>
     * <p>
     * - in case of a color image and fixed range
     * <pre>{@code \[\texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)_r- \texttt{loDiff} _r \leq \texttt{src} (x,y)_r \leq \texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)_r+ \texttt{upDiff} _r,\]}</pre>
     * <pre>{@code \[\texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)_g- \texttt{loDiff} _g \leq \texttt{src} (x,y)_g \leq \texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)_g+ \texttt{upDiff} _g\]}</pre>
     * and
     * <pre>{@code \[\texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)_b- \texttt{loDiff} _b \leq \texttt{src} (x,y)_b \leq \texttt{src} ( \texttt{seedPoint} .x, \texttt{seedPoint} .y)_b+ \texttt{upDiff} _b\]}</pre>
     * <p>
     * <p>
     * where {@code src(x',y')} is the value of one of pixel neighbors that is already known to belong to the
     * component. That is, to be added to the connected component, a color/brightness of the pixel should
     * be close enough to:
     * - Color/brightness of one of its neighbors that already belong to the connected component in case
     * of a floating range.
     * - Color/brightness of the seed point in case of a fixed range.
     * <p>
     * Use these functions to either mark a connected component with the specified color in-place, or build
     * a mask and then extract the contour, or copy the region to another image, and so on.
     * <p>
     * @param image     Input/output 1- or 3-channel, 8-bit, or floating-point image. It is modified by the
     *                  function unless the #FLOODFILL_MASK_ONLY flag is set in the second variant of the function. See
     *                  the details below.
     * @param mask      Operation mask that should be a single-channel 8-bit image, 2 pixels wider and 2 pixels
     *                  taller than image. Since this is both an input and output parameter, you must take responsibility
     *                  of initializing it. Flood-filling cannot go across non-zero pixels in the input mask. For example,
     *                  an edge detector output can be used as a mask to stop filling at edges. On output, pixels in the
     *                  mask corresponding to filled pixels in the image are set to 1 or to the a value specified in flags
     *                  as described below. Additionally, the function fills the border of the mask with ones to simplify
     *                  internal processing. It is therefore possible to use the same mask in multiple calls to the function
     *                  to make sure the filled areas do not overlap.
     * @param seedPoint Starting point.
     * @param newVal    New value of the repainted domain pixels.
     * @param loDiff    Maximal lower brightness/color difference between the currently observed pixel and
     *                  one of its neighbors belonging to the component, or a seed pixel being added to the component.
     * @param upDiff    Maximal upper brightness/color difference between the currently observed pixel and
     *                  one of its neighbors belonging to the component, or a seed pixel being added to the component.
     * @param rect      Optional output parameter set by the function to the minimum bounding rectangle of the
     *                  repainted domain.
     * @param flags Operation flags. The first 8 bits contain a connectivity value. The default value of
     *              4 means that only the four nearest neighbor pixels (those that share an edge) are considered. A
     *              connectivity value of 8 means that the eight nearest neighbor pixels (those that share a corner)
     *              will be considered. The next 8 bits (8-16) contain a value between 1 and 255 with which to fill
     *              the mask (the default value is 1). For example, 4 | ( 255 \<\< 8 ) will consider 4 nearest
     *              neighbours and fill the mask with a value of 255. The following additional options occupy higher
     *              bits and therefore may be further combined with the connectivity and mask fill values using
     *              bit-wise or (|), see #FloodFillFlags.
     *              <p>
     *              \note Since the mask is larger than the filled image, a pixel <pre>(x, y)</pre> in image corresponds to the
     *              pixel <pre>(x+1, y+1)</pre> in the mask .
     *              <p>
     * @see findContours
     */
    def floodFill(mask: Mat, seedPoint: Point, newVal: Scalar, loDiff: Scalar, upDiff: Scalar, flags: Int): (Rect,Int) = {
      val rect = new Rect(0)
      val result = opencv_imgproc.floodFill(image,mask,seedPoint,newVal,rect,loDiff,upDiff,flags)
      (rect,result)
    }

    def floodFill(mask: Mat, seedPoint: Point, newVal: Scalar): (Rect,Int) =
      floodFill(mask,seedPoint,newVal,new Scalar(),new Scalar(),flags = 4)

    /** \example samples/cpp/ffilldemo.cpp
     * An example using the FloodFill technique
     */

    /** \overload
     * <p>
     * variant without <pre>mask</pre> parameter
     */
    def floodFill(seedPoint: Point, newVal: Scalar, loDiff: Scalar, upDiff: Scalar, flags: Int): (Rect,Int) = {
      val rect = new Rect(0)
      val result = opencv_imgproc.floodFill(image,seedPoint,newVal,rect,loDiff,upDiff,flags)
      (rect,result)
    }

    def floodFill(seedPoint: Point, newVal: Scalar): (Rect,Int) =
      floodFill(seedPoint, newVal, new Scalar(), new Scalar(), flags = 4)

    /** Performs linear blending of two images:
     * <pre>\[ \texttt{dst}(i,j) = \texttt{weights1}(i,j)*\texttt{src1}(i,j) + \texttt{weights2}(i,j)*\texttt{src2}(i,j) \]</pre>
     *
     * @param src1     It has a type of CV_8UC(n) or CV_32FC(n), where n is a positive integer.
     * @param src2     It has the same type and size as src1.
     * @param weights1 It has a type of CV_32FC1 and the same size with src1.
     * @param weights2 It has a type of CV_32FC1 and the same size with src1.
     * @param dst      It is created if it does not have the same size and type with src1. */
    def blendLinear(src2: Mat, weights1: Mat, weights2: Mat): Mat = {
      val dst = new Mat()
      opencv_imgproc.blendLinear(image,src2,weights1,weights2,dst)
      dst
    }
  }
}



