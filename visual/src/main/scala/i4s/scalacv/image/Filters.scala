package i4s.scalacv.image

import i4s.scalacv.core.constants.BorderTypes.BorderType
import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types._
import i4s.scalacv.core.constants.BorderTypes
import i4s.scalacv.core.constants.core.CvPi
import i4s.scalacv.image.constants.MorphShapes.MorphShape
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_core._

/**
 *
 * <pre>(x,y)</pre> in the source image (normally, rectangular), its neighborhood is considered and used to
 * compute the response. In case of a linear filter, it is a weighted sum of pixel values. In case of
 * morphological operations, it is the minimum or maximum values, and so on. The computed response is
 * stored in the destination image at the same location <pre>(x,y)</pre>. It means that the output image
 * will be of the same size as the input image. Normally, the functions support multi-channel arrays,
 * in which case every channel is processed independently. Therefore, the output image will also have
 * the same number of channels as the input one.
 * <p>
 * Another common feature of the functions and classes described in this section is that, unlike
 * simple arithmetic functions, they need to extrapolate values of some non-existing pixels. For
 * example, if you want to smooth an image using a Gaussian <pre>3 \times 3</pre> filter, then, when
 * processing the left-most pixels in each row, you need pixels to the left of them, that is, outside
 * of the image. You can let these pixels be the same as the left-most image pixels ("replicated
 * border" extrapolation method), or assume that all the non-existing pixels are zeros ("constant
 * border" extrapolation method), and so on. OpenCV enables you to specify the extrapolation method.
 * For details, see #BorderTypes
 * <p>
 * \anchor filter_depths
 * ### Depth combinations
 * Input depth (src.depth()) | Output depth (ddepth)
 * --------------------------|----------------------
 * [[Cv8U]]                  | [[CvUndefined]] / [[Cv16S]] / [[Cv32F]] / [[Cv64F]]
 * [[Cv16U]] / [[Cv16S]]     | [[CvUndefined]] / [[Cv32F]] / [[Cv64F]]
 * [[Cv32F]]                 | [[CvUndefined]] / [[Cv32F]] / [[Cv64F]]
 * [[Cv64F]]                 | [[CvUndefined]] / [[Cv64F]]
 * <p>
 * \note when <pre>ddepth=[[CvUndefined]]</pre>, the output image will have the same depth as the source.*/

object Filters {
  /**
   * Returns Gaussian filter coefficients.
   * <p>
   * The function computes and returns the <pre>\texttt{ksize</pre> \times 1} matrix of Gaussian filter
   * coefficients:
   * <p>
   * <pre><pre>\[G_i&#61; \alpha *e&#94;{-(i-( \texttt{ksize</pre> -1)/2)^2/(2* \texttt{sigma}^2)},\]}</pre>
   * <p>
   * where <pre>i&#61;0..\texttt{ksize</pre>-1} and <pre>\alpha</pre> is the scale factor chosen so that <pre>\sum_i G_i&#61;1</pre>.
   * <p>
   * Two of such generated kernels can be passed to sepFilter2D. Those functions automatically recognize
   * smoothing kernels (a symmetrical kernel with sum of weights equal to 1) and handle them accordingly.
   * You may also use the higher-level GaussianBlur.
   * @param ksize Aperture size. It should be odd ( <pre>\texttt{ksize</pre> \mod 2 = 1} ) and positive.
   * @param sigma Gaussian standard deviation. If it is non-positive, it is computed from ksize as
   *              <pre>sigma &#61; 0.3*((ksize-1)*0.5 - 1) + 0.8</pre>.
   * @param ktype Type of filter coefficients. It can be CV_32F or CV_64F .
   * @see sepFilter2D, getDerivKernels, getStructuringElement, GaussianBlur
   */
  def getGaussianKernel(ksize: Int, sigma: Double, ktype: Type): Mat =
      opencv_imgproc.getGaussianKernel(ksize,sigma,ktype.flag)

  def getGaussianKernel(ksize: Int, sigma: Double): Mat =
    getGaussianKernel(ksize,sigma,Types.Cv64F)

  /** Returns filter coefficients for computing spatial image derivatives.
   * <p>
   * The function computes and returns the filter coefficients for spatial image derivatives. When
   * <pre>ksize&#61;FILTER_SCHARR</pre>, the Scharr <pre>3 \times 3</pre> kernels are generated (see #Scharr). Otherwise, Sobel
   * kernels are generated (see #Sobel). The filters are normally passed to #sepFilter2D or to
   * <p>
   * @param kx        Output matrix of row filter coefficients. It has the type ktype .
   * @param ky        Output matrix of column filter coefficients. It has the type ktype .
   * @param dx        Derivative order in respect of x.
   * @param dy        Derivative order in respect of y.
   * @param ksize     Aperture size. It can be FILTER_SCHARR, 1, 3, 5, or 7.
   * @param normalize Flag indicating whether to normalize (scale down) the filter coefficients or not.
   *                  Theoretically, the coefficients should have the denominator <pre>&#61;2&#94;{ksize*2-dx-dy-2</pre>}. If you are
   *                  going to filter floating-point images, you are likely to use the normalized kernels. But if you
   *                  compute derivatives of an 8-bit image, store the results in a 16-bit image, and wish to preserve
   *                  all the fractional bits, you may want to set normalize=false .
   * @param ktype     Type of filter coefficients. It can be CV_32f or CV_64F .
   * @return          Type of the x and y [[Mat]] containing row and column filter coefficients, of the type passed in ktype
   *
   */
  def getDerivKernels(dx: Int, dy: Int, ksize: Int, normalize: Boolean, ktype: Type): (Mat,Mat) = {
    val kx, ky: Mat = new Mat()
    opencv_imgproc.getDerivKernels(kx,ky,dx,dy,ksize,normalize,ktype.flag)
    (kx,ky)
  }

  def getDerivKernels(dx: Int, dy: Int, ksize: Int): (Mat,Mat) =
    getDerivKernels(dx,dy,ksize,normalize = false,Types.Cv32F)


  /** FtReturns Gabor filter coefficients.
   * <p>
   * For more details about gabor filter equations and parameters, see: [Gabor
   * Filter](http://en.wikipedia.org/wiki/Gabor_filter).
   * <p>
   * @param ksize Size of the filter returned.
   * @param sigma Standard deviation of the gaussian envelope.
   * @param theta Orientation of the normal to the parallel stripes of a Gabor function.
   * @param lambd Wavelength of the sinusoidal factor.
   * @param gamma Spatial aspect ratio.
   * @param psi   Phase offset.
   * @param ktype Type of filter coefficients. It can be CV_32F or CV_64F .
   */
  def getGaborKernel(ksize: Size, sigma: Double, theta: Double, lambd: Double, gamma: Double, psi: Double, ktype: Type): Mat =
    opencv_imgproc.getGaborKernel(ksize,sigma,theta,lambd,gamma,psi,ktype.flag)

  def getGaborKernel(ksize: Size, sigma: Double, theta: Double, lambd: Double, gamma: Double): Mat =
    getGaborKernel(ksize,sigma,theta,lambd,gamma,psi = CvPi * 0.5, Cv64F)

  /** returns "magic" border value for erosion and dilation. It is automatically transformed to Scalar::all(-DBL_MAX) for dilation. */
  def morphologyDefaultBorderValue: Scalar = opencv_imgproc.morphologyDefaultBorderValue()

  /** FtReturns a structuring element of the specified size and shape for morphological operations.
   * <p>
   * The function constructs and returns the structuring element that can be further passed to #erode,
   * #dilate or #morphologyEx. But you can also construct an arbitrary binary mask yourself and use it as
   * the structuring element.
   * <p>
   * @param shape  Element shape that could be one of #MorphShapes
   * @param ksize  Size of the structuring element.
   * @param anchor Anchor position within the element. The default value <pre>(-1, -1)</pre> means that the
   *               anchor is at the center. Note that only the shape of a cross-shaped element depends on the anchor
   *               position. In other cases the anchor just regulates how much the result of the morphological
   *               operation is shifted.
   */
  def getStructuringElement(shape: MorphShape, ksize: Size, anchor: Point): Mat =
    opencv_imgproc.getStructuringElement(shape.flag,ksize,anchor)

  def getStructuringElement(shape: MorphShape, ksize: Size): Mat =
    getStructuringElement(shape,ksize,new Point(-1,-1))

  /** \example samples/cpp/tutorial_code/ImgProc/Smoothing/Smoothing.cpp
   * Sample code for simple filters
   * ![Sample screenshot](Smoothing_Tutorial_Result_Median_Filter.jpg)
   * Check \ref tutorial_gausian_median_blur_bilateral_filter "the corresponding tutorial" for more details
   */

  implicit class ImageFilters(image: Mat) {

    /** \example samples/cpp/tutorial_code/ImgProc/Smoothing/Smoothing.cpp
     * Sample code for simple filters
     * ![Sample screenshot](Smoothing_Tutorial_Result_Median_Filter.jpg)
     * Check \ref tutorial_gausian_median_blur_bilateral_filter "the corresponding tutorial" for more details
     */

    /** FtBlurs an image using the median filter.
     * <p>
     * The function smoothes an image using the median filter with the <pre>\texttt{ksize</pre> \times
     * \texttt{ksize}} aperture. Each channel of a multi-channel image is processed independently.
     * In-place operation is supported.
     * <p>
     * \note The median filter uses #BORDER_REPLICATE internally to cope with border pixels, see #BorderTypes
     * <p>
     * @param src   input 1-, 3-, or 4-channel image; when ksize is 3 or 5, the image depth should be
     *              CV_8U, CV_16U, or CV_32F, for larger aperture sizes, it can only be CV_8U.
     * @param dst   destination array of the same size and type as src.
     * @param ksize aperture linear size; it must be odd and greater than 1, for example: 3, 5, 7 ...
     * @see bilateralFilter, blur, boxFilter, GaussianBlur
     */
   def medianBlur(ksize: Int): Mat = {
     val dst = new Mat()
     opencv_imgproc.medianBlur(image,dst,ksize)
     dst
   }

    /** FtBlurs an image using a Gaussian filter.
     * <p>
     * The function convolves the source image with the specified Gaussian kernel. In-place filtering is
     * supported.
     * <p>
     * @param src        input image; the image can have any number of channels, which are processed
     *                   independently, but the depth should be CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
     * @param dst        output image of the same size and type as src.
     * @param ksize      Gaussian kernel size. ksize.width and ksize.height can differ but they both must be
     *                   positive and odd. Or, they can be zero's and then they are computed from sigma.
     * @param sigmaX     Gaussian kernel standard deviation in X direction.
     * @param sigmaY     Gaussian kernel standard deviation in Y direction; if sigmaY is zero, it is set to be
     *                   equal to sigmaX, if both sigmas are zeros, they are computed from ksize.width and ksize.height,
     *                   respectively (see #getGaussianKernel for details); to fully control the result regardless of
     *                   possible future modifications of all this semantics, it is recommended to specify all of ksize,
     *                   sigmaX, and sigmaY.
     * @param borderType pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     *                   <p>
     * @see sepFilter2D, filter2D, blur, boxFilter, bilateralFilter, medianBlur
     */
    def gaussianBlur(ksize: Size, sigmaX: Double, sigmaY: Double /*=0*/ , borderType: BorderType /*=cv::BORDER_DEFAULT*/): Mat = {
      val dst = new Mat()
      opencv_imgproc.GaussianBlur(image,dst,ksize,sigmaX,sigmaY,borderType.flag)
      dst
    }

    def gaussianBlur(ksize: Size, sigmaX: Double): Mat =
      gaussianBlur(ksize,sigmaX,sigmaY = -1,BorderTypes.Default)

    /** FtApplies the bilateral filter to an image.
     * <p>
     * The function applies bilateral filtering to the input image, as described in
     * http://www.dai.ed.ac.uk/CVonline/LOCAL_COPIES/MANDUCHI1/Bilateral_Filtering.html
     * bilateralFilter can reduce unwanted noise very well while keeping edges fairly sharp. However, it is
     * very slow compared to most filters.
     * <p>
     * _Sigma values_: For simplicity, you can set the 2 sigma values to be the same. If they are small (\<
     * 10), the filter will not have much effect, whereas if they are large (\> 150), they will have a very
     * strong effect, making the image look "cartoonish".
     * <p>
     * _Filter size_: Large filters (d \> 5) are very slow, so it is recommended to use d=5 for real-time
     * applications, and perhaps d=9 for offline applications that need heavy noise filtering.
     * <p>
     * This filter does not work inplace.
     * @param src        Source 8-bit or floating-point, 1-channel or 3-channel image.
     * @param dst        Destination image of the same size and type as src .
     * @param d          Diameter of each pixel neighborhood that is used during filtering. If it is non-positive,
     *                   it is computed from sigmaSpace.
     * @param sigmaColor Filter sigma in the color space. A larger value of the parameter means that
     *                   farther colors within the pixel neighborhood (see sigmaSpace) will be mixed together, resulting
     *                   in larger areas of semi-equal color.
     * @param sigmaSpace Filter sigma in the coordinate space. A larger value of the parameter means that
     *                   farther pixels will influence each other as long as their colors are close enough (see sigmaColor
     *                   ). When d\>0, it specifies the neighborhood size regardless of sigmaSpace. Otherwise, d is
     *                   proportional to sigmaSpace.
     * @param borderType border mode used to extrapolate pixels outside of the image, see #BorderTypes
     */
    def bilateralFilter(d: Int, sigmaColor: Double, sigmaSpace: Double, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.bilateralFilter(image,dst,d,sigmaColor,sigmaSpace,borderType.flag)
      dst
    }

    def bilateralFilter(dst: Mat, d: Int, sigmaColor: Double, sigmaSpace: Double): Mat =
      bilateralFilter(d, sigmaColor, sigmaSpace, BorderTypes.Default)

    /** FtBlurs an image using the box filter.
     * <p>
     * The function smooths an image using the kernel:
     * <p>
     * <pre>\[\texttt{K</pre> =  \alpha \begin{bmatrix} 1 & 1 & 1 &  \cdots & 1 & 1  \\ 1 & 1 & 1 &  \cdots & 1 & 1  \\ \hdotsfor{6} \\ 1 & 1 & 1 &  \cdots & 1 & 1 \end{bmatrix}\]}</pre>
     * <p>
     * where
     * <p>
     * <pre>\[\alpha = \begin{cases} \frac{1}{\texttt{ksize.width*ksize.height}} & \texttt{when } \texttt{normalize=true}  \\1 & \texttt{otherwise}\end{cases}\]}</pre>
     * <p>
     * Unnormalized box filter is useful for computing various integral characteristics over each pixel
     * neighborhood, such as covariance matrices of image derivatives (used in dense optical flow
     * algorithms, and so on). If you need to compute pixel sums over variable-size windows, use #integral.
     * <p>
     * @param src        input image.
     * @param dst        output image of the same size and type as src.
     * @param ddepth     the output image depth (-1 to use src.depth()).
     * @param ksize      blurring kernel size.
     * @param anchor     anchor point; default value Point(-1,-1) means that the anchor is at the kernel
     *                   center.
     * @param normalize  flag, specifying whether the kernel is normalized by its area or not.
     * @param borderType border mode used to extrapolate pixels outside of the image, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see blur, bilateralFilter, GaussianBlur, medianBlur, integral
     */
    def boxFilter(ddepth: Type, ksize: Size, anchor: Point, normalize: Boolean, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.boxFilter(image,dst,ddepth.flag,ksize,anchor,normalize,borderType.flag)
      dst
    }

    def boxFilter(ddepth: Type, ksize: Size): Mat =
      boxFilter(ddepth,ksize,new Point(-1,-1),normalize = true,BorderTypes.Default)

    /** FtCalculates the normalized sum of squares of the pixel values overlapping the filter.
     * <p>
     * For every pixel <pre>(x, y) </pre> in the source image, the function calculates the sum of squares of those neighboring
     * pixel values which overlap the filter placed over the pixel <pre>(x, y) </pre>.
     * <p>
     * The unnormalized square box filter can be useful in computing local image statistics such as the the local
     * variance and standard deviation around the neighborhood of a pixel.
     * <p>
     * @param src        input image
     * @param dst        output image of the same size and type as src
     * @param ddepth     the output image depth (-1 to use src.depth())
     * @param ksize      kernel size
     * @param anchor     kernel anchor point. The default value of Point(-1, -1) denotes that the anchor is at the kernel
     *                   center.
     * @param normalize  flag, specifying whether the kernel is to be normalized by it's area or not.
     * @param borderType border mode used to extrapolate pixels outside of the image, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see boxFilter
     */
    def sqrBoxFilter(ddepth: Type, ksize: Size, anchor: Point, normalize: Boolean, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.sqrBoxFilter(image,dst,ddepth.flag,ksize,anchor,normalize,borderType.flag)
      dst
    }

    def sqrBoxFilter(dst: Mat, ddepth: Type, ksize: Size): Mat =
      sqrBoxFilter(ddepth,ksize,new Point(-1,-1),normalize = true,BorderTypes.Default)

    /** FtBlurs an image using the normalized box filter.
     * <p>
     * The function smooths an image using the kernel:
     * <p>
     * <pre>\[\texttt{K} =  \frac{1}{\texttt{ksize.width*ksize.height}} \begin{bmatrix} 1 & 1 & 1 &  \cdots & 1 & 1  \\ 1 & 1 & 1 &  \cdots & 1 & 1  \\ \hdotsfor{6} \\ 1 & 1 & 1 &  \cdots & 1 & 1  \\ \end{bmatrix}\]}</pre>
     * <p>
     * The call <pre>blur(src, dst, ksize, anchor, borderType)</pre> is equivalent to <pre>boxFilter(src, dst, src.type(), ksize, anchor, true, borderType)</pre>.
     * <p>
     * @param src        input image; it can have any number of channels, which are processed independently, but
     *                   the depth should be CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
     * @param dst        output image of the same size and type as src.
     * @param ksize      blurring kernel size.
     * @param anchor     anchor point; default value Point(-1,-1) means that the anchor is at the kernel
     *                   center.
     * @param borderType border mode used to extrapolate pixels outside of the image, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see boxFilter, bilateralFilter, GaussianBlur, medianBlur
     */
    def blur(ksize: Size, anchor: Point, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.blur(image,dst,ksize,anchor,borderType.flag)
      dst
    }

    def blur(ksize: Size): Mat = blur(ksize,new Point(-1,-1),BorderTypes.Default)

    /** FtConvolves an image with the kernel.
     * <p>
     * The function applies an arbitrary linear filter to an image. In-place operation is supported. When
     * the aperture is partially outside the image, the function interpolates outlier pixel values
     * according to the specified border mode.
     * <p>
     * The function does actually compute correlation, not the convolution:
     * <p>
     * <pre>\[\texttt{dst} (x,y) =  \sum _{ \substack{0\leq x' < \texttt{kernel.cols}\\{0\leq y' < \texttt{kernel.rows}}}}  \texttt{kernel} (x',y')* \texttt{src} (x+x'- \texttt{anchor.x} ,y+y'- \texttt{anchor.y} )\]}</pre>
     * <p>
     * That is, the kernel is not mirrored around the anchor point. If you need a real convolution, flip
     * the kernel using #flip and set the new anchor to <pre>(kernel.cols - anchor.x - 1, kernel.rows -anchor.y - 1)</pre>.
     * <p>
     * The function uses the DFT-based algorithm in case of sufficiently large kernels (~<pre>11 x 11</pre> or
     * larger) and the direct algorithm for small kernels.
     * <p>
     * @param src        input image.
     * @param dst        output image of the same size and the same number of channels as src.
     * @param ddepth     desired depth of the destination image, see \ref filter_depths "combinations"
     * @param kernel     convolution kernel (or rather a correlation kernel), a single-channel floating point
     *                   matrix; if you want to apply different kernels to different channels, split the image into
     *                   separate color planes using split and process them individually.
     * @param anchor     anchor of the kernel that indicates the relative position of a filtered point within
     *                   the kernel; the anchor should lie within the kernel; default value (-1,-1) means that the anchor
     *                   is at the kernel center.
     * @param delta      optional value added to the filtered pixels before storing them in dst.
     * @param borderType pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see sepFilter2D, dft, matchTemplate
     */
    def filter2D(ddepth: Type, kernel: Mat, anchor: Point, delta: Double, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.filter2D(image,dst,ddepth.flag,kernel,anchor,delta,borderType.flag)
      dst
    }

    def filter2D(dst: Mat, ddepth: Type, kernel: Mat): Mat =
      filter2D(ddepth,kernel,new Point(-1,-1),delta = 0,BorderTypes.Default)

    /** FtApplies a separable linear filter to an image.
     * <p>
     * The function applies a separable linear filter to the image. That is, first, every row of src is
     * filtered with the 1D kernel kernelX. Then, every column of the result is filtered with the 1D
     * kernel kernelY. The final result shifted by delta is stored in dst .
     * <p>
     * @param src        Source image.
     * @param dst        Destination image of the same size and the same number of channels as src .
     * @param ddepth     Destination image depth, see \ref filter_depths "combinations"
     * @param kernelX    Coefficients for filtering each row.
     * @param kernelY    Coefficients for filtering each column.
     * @param anchor     Anchor position within the kernel. The default value <pre>(-1,-1)</pre> means that the anchor
     *                   is at the kernel center.
     * @param delta      Value added to the filtered results before storing them.
     * @param borderType Pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see filter2D, Sobel, GaussianBlur, boxFilter, blur
     */
    def sepFilter2D(ddepth: Type, kernelX: Mat, kernelY: Mat, anchor: Point, delta: Double, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.sepFilter2D(image,dst,ddepth.flag,kernelX,kernelY,anchor,delta,borderType.flag)
      dst
    }

    def sepFilter2D(dst: Mat, ddepth: Type, kernelX: Mat, kernelY: Mat): Mat =
      sepFilter2D(ddepth,kernelX,kernelY,new Point(-1,-1),0,BorderTypes.Default)

    /** \example samples/cpp/tutorial_code/ImgTrans/Sobel_Demo.cpp
     * Sample code using Sobel and/or Scharr OpenCV functions to make a simple Edge Detector
     * ![Sample screenshot](Sobel_Derivatives_Tutorial_Result.jpg)
     * Check \ref tutorial_sobel_derivatives "the corresponding tutorial" for more details
     */

    /** FtCalculates the first, second, third, or mixed image derivatives using an extended Sobel operator.
     * <p>
     * In all cases except one, the <pre>\texttt{ksize</pre> \times \texttt{ksize}} separable kernel is used to
     * calculate the derivative. When <pre>\texttt{ksize &#61; 1</pre>}, the <pre>3 \times 1</pre> or <pre>1 \times 3</pre>
     * kernel is used (that is, no Gaussian smoothing is done). <pre>ksize &#61; 1</pre> can only be used for the first
     * or the second x- or y- derivatives.
     * <p>
     * There is also the special value <pre>ksize &#61; #FILTER_SCHARR (-1)</pre> that corresponds to the <pre>3\times3</pre> Scharr
     * filter that may give more accurate results than the <pre>3\times3</pre> Sobel. The Scharr aperture is
     * <p>
     * <pre>\[\vecthreethree{-3}{0}{3}{-10}{0}{10}{-3}{0}{3}\]}</pre>
     * <p>
     * for the x-derivative, or transposed for the y-derivative.
     * <p>
     * The function calculates an image derivative by convolving the image with the appropriate kernel:
     * <p>
     *
     * <pre> \[\texttt{dst} =  \frac{\partial^{xorder+yorder} \texttt{src}}{\partial x^{xorder} \partial y^{yorder}\]</pre>
     *
     * <p>
     * The Sobel operators combine Gaussian smoothing and differentiation, so the result is more or less
     * resistant to the noise. Most often, the function is called with ( xorder = 1, yorder = 0, ksize = 3)
     * or ( xorder = 0, yorder = 1, ksize = 3) to calculate the first x- or y- image derivative. The first
     * case corresponds to a kernel of:
     * <p>
     * <pre><pre>\[\vecthreethree{-1</pre>{0}{1}{-2}{0}{2}{-1}{0}{1}\]}</pre>
     * <p>
     * The second case corresponds to a kernel of:
     * <p>
     * <pre><pre>\[\vecthreethree{-1</pre>{-2}{-1}{0}{0}{0}{1}{2}{1}\]}</pre>
     * <p>
     * @param src        input image.
     * @param dst        output image of the same size and the same number of channels as src .
     * @param ddepth     output image depth, see \ref filter_depths "combinations"; in the case of
     *                   8-bit input images it will result in truncated derivatives.
     * @param dx         order of the derivative x.
     * @param dy         order of the derivative y.
     * @param ksize      size of the extended Sobel kernel; it must be 1, 3, 5, or 7.
     * @param scale      optional scale factor for the computed derivative values; by default, no scaling is
     *                   applied (see #getDerivKernels for details).
     * @param delta      optional delta value that is added to the results prior to storing them in dst.
     * @param borderType pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see Scharr, Laplacian, sepFilter2D, filter2D, GaussianBlur, cartToPolar
     */
    def sobel(ddepth: Type, dx: Int, dy: Int, ksize: Int /*=3*/ , scale: Double /*=1*/ , delta: Double, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.Sobel(image,dst,ddepth.flag,dx,dy,ksize,scale,delta,borderType.flag)
      dst
    }

    def sobel(ddepth: Type, dx: Int, dy: Int): Mat =
      sobel(ddepth,dx,dy,ksize = 3,scale = 1,delta = 0,BorderTypes.Default)

    /** FtCalculates the first order image derivative in both x and y using a Sobel operator
     * <p>
     * Equivalent to calling:
     * <p>
     * <pre>
     * Sobel( src, dx, CV_16SC1, 1, 0, 3 );
     * Sobel( src, dy, CV_16SC1, 0, 1, 3 );
     * }</pre>
     * <p>
     * @param src input image.
     * @param dx output image with first-order derivative in x.
     * @param dy output image with first-order derivative in y.
     * @param ksize size of Sobel kernel. It must be 3.
     * @param borderType pixel extrapolation method, see #BorderTypes.
     * Only #BORDER_DEFAULT=#BORDER_REFLECT_101 and #BORDER_REPLICATE are supported.
     * <p>
     * @see Sobel
     */
    def spatialGradient(ksize: Int, borderType: BorderType): (Mat,Mat) = {
      val dx: Mat = new Mat()
      val dy: Mat = new Mat()
      opencv_imgproc.spatialGradient(image,dx,dy,ksize,borderType.flag)
      (dx,dy)
    }

    def spatialGradient(): (Mat,Mat) =
      spatialGradient(ksize = 3,BorderTypes.Default)

    /** FtCalculates the first x- or y- image derivative using Scharr operator.
     * <p>
     * The function computes the first x- or y- spatial image derivative using the Scharr operator. The
     * call
     * <p>
     * <pre>\[\texttt{Scharr(src, dst, ddepth, dx, dy, scale, delta, borderType)}\]}</pre>
     * <p>
     * is equivalent to
     * <p>
     * <pre>\[\texttt{Sobel(src, dst, ddepth, dx, dy, FILTER_SCHARR, scale, delta, borderType)} .\]}</pre>
     * <p>
     * @param src        input image.
     * @param dst        output image of the same size and the same number of channels as src.
     * @param ddepth     output image depth, see \ref filter_depths "combinations"
     * @param dx         order of the derivative x.
     * @param dy         order of the derivative y.
     * @param scale      optional scale factor for the computed derivative values; by default, no scaling is
     *                   applied (see #getDerivKernels for details).
     * @param delta      optional delta value that is added to the results prior to storing them in dst.
     * @param borderType pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see cartToPolar
     */
    def scharr(ddepth: Type, dx: Int, dy: Int, scale: Double, delta: Double, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.Scharr(image,dst,ddepth.flag,dx,dy,scale,delta,borderType.flag)
      dst
    }

    def scharr(dst: Mat, ddepth: Type, dx: Int, dy: Int): Mat =
      scharr(ddepth,dx,dy,scale = 1,delta = 0,BorderTypes.Default)

    /** \example samples/cpp/laplace.cpp
     * An example using Laplace transformations for edge detection
     */

    /** FtCalculates the Laplacian of an image.
     * <p>
     * The function calculates the Laplacian of the source image by adding up the second x and y
     * derivatives calculated using the Sobel operator:
     * <p>
     * <pre>=\[\texttt{dst} =  \Delta \texttt{src} =  \frac{\partial^2 \texttt{src}}{\partial x^2} +  \frac{\partial^2 \texttt{src}}{\partial y^2}\]}</pre>
     * <p>
     * This is done when <pre>ksize > 1</pre>. When <pre>ksize &#61;&#61; 1</pre>, the Laplacian is computed by filtering the image
     * with the following <pre>3 \times 3</pre> aperture:
     * <p>
     * <pre>\[\vecthreethree {0}{1}{0}{1}{-4}{1}{0}{1}{0}\]}</pre>
     * <p>
     * @param src        Source image.
     * @param dst        Destination image of the same size and the same number of channels as src .
     * @param ddepth     Desired depth of the destination image.
     * @param ksize      Aperture size used to compute the second-derivative filters. See #getDerivKernels for
     *                   details. The size must be positive and odd.
     * @param scale      Optional scale factor for the computed Laplacian values. By default, no scaling is
     *                   applied. See #getDerivKernels for details.
     * @param delta      Optional delta value that is added to the results prior to storing them in dst .
     * @param borderType Pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     * @see Sobel, Scharr
     */
    def laplacian(ddepth: Type, ksize: Int, scale: Double, delta: Double, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.Laplacian(image,dst,ddepth.flag,ksize,scale,delta,borderType.flag)
      dst
    }

    def laplacian(ddepth: Type): Mat =
      laplacian(ddepth,ksize = 1,scale = 1,delta = 0,BorderTypes.Default)

    /** FtErodes an image by using a specific structuring element.
     * <p>
     * The function erodes the source image using the specified structuring element that determines the
     * shape of a pixel neighborhood over which the minimum is taken:
     * <p>
     * <pre>\[\texttt{dst} (x,y) =  \min _{(x',y'):  \, \texttt{element} (x',y') \ne0 } \texttt{src} (x+x',y+y')\]</pre>
     * <p>
     * The function supports the in-place mode. Erosion can be applied several ( iterations ) times. In
     * case of multi-channel images, each channel is processed independently.
     * <p>
     * @param src         input image; the number of channels can be arbitrary, but the depth should be one of
     *                    CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
     * @param dst         output image of the same size and type as src.
     * @param kernel      structuring element used for erosion; if <pre>element&#61;Mat()</pre>, a <pre>3 x 3</pre> rectangular
     *                    structuring element is used. Kernel can be created using #getStructuringElement.
     * @param anchor      position of the anchor within the element; default value (-1, -1) means that the
     *                    anchor is at the element center.
     * @param iterations  number of times erosion is applied.
     * @param borderType  pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     * @param borderValue border value in case of a constant border
     * @see dilate, morphologyEx, getStructuringElement
     */
    def erode(kernel: Mat, anchor: Point, iterations: Int, borderType: BorderType, borderValue: Scalar): Mat = {
      val dst = new Mat()
      opencv_imgproc.erode(image,dst,kernel,anchor,iterations,borderType.flag,borderValue)
      dst
    }

    def erode(dst: Mat, kernel: Mat): Mat =
      erode(kernel,new Point(-1,-1),iterations = 1,BorderTypes.Constant,new Scalar(morphologyDefaultBorderValue))

    /** \example samples/cpp/tutorial_code/ImgProc/Morphology_1.cpp
     * Erosion and Dilation sample code
     * ![Sample Screenshot-Erosion](Morphology_1_Tutorial_Erosion_Result.jpg)![Sample Screenshot-Dilation](Morphology_1_Tutorial_Dilation_Result.jpg)
     * Check \ref tutorial_erosion_dilatation "the corresponding tutorial" for more details
     */

    /** FtDilates an image by using a specific structuring element.
     * <p>
     * The function dilates the source image using the specified structuring element that determines the
     * shape of a pixel neighborhood over which the maximum is taken:
     * <pre>\[\texttt{dst} (x,y) =  \max _{(x',y'):  \, \texttt{element} (x',y') \ne0 } \texttt{src} (x+x',y+y')\]</pre>
     * <p>
     * The function supports the in-place mode. Dilation can be applied several ( iterations ) times. In
     * case of multi-channel images, each channel is processed independently.
     * <p>
     * @param src         input image; the number of channels can be arbitrary, but the depth should be one of
     *                    CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
     * @param dst         output image of the same size and type as src.
     * @param kernel      structuring element used for dilation; if elemenat=Mat(), a 3 x 3 rectangular
     *                    structuring element is used. Kernel can be created using #getStructuringElement
     * @param anchor      position of the anchor within the element; default value (-1, -1) means that the
     *                    anchor is at the element center.
     * @param iterations  number of times dilation is applied.
     * @param borderType  pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not suported.
     * @param borderValue border value in case of a constant border
     * @see erode, morphologyEx, getStructuringElement
     */
    def dilate(dst: Mat, kernel: Mat, anchor: Point, iterations: Int, borderType: BorderType, borderValue: Scalar): Unit =
      opencv_imgproc.dilate(image,dst,kernel,anchor,iterations,borderType.flag,borderValue)

    def dilate(dst: Mat, kernel: Mat): Unit =
      dilate(dst,kernel,new Point(-1,-1),iterations = 1,BorderTypes.Constant,new Scalar(morphologyDefaultBorderValue))

    /** FtPerforms advanced morphological transformations.
     * <p>
     * The function cv::morphologyEx can perform advanced morphological transformations using an erosion and dilation as
     * basic operations.
     * <p>
     * Any of the operations can be done in-place. In case of multi-channel images, each channel is
     * processed independently.
     * <p>
     * @param src         Source image. The number of channels can be arbitrary. The depth should be one of
     *                    CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
     * @param dst         Destination image of the same size and type as source image.
     * @param op          Type of a morphological operation, see #MorphTypes
     * @param kernel      Structuring element. It can be created using #getStructuringElement.
     * @param anchor      Anchor position with the kernel. Negative values mean that the anchor is at the
     *                    kernel center.
     * @param iterations  Number of times erosion and dilation are applied.
     * @param borderType  Pixel extrapolation method, see #BorderTypes. #BORDER_WRAP is not supported.
     * @param borderValue Border value in case of a constant border. The default value has a special
     *                    meaning.
     * @see dilate, erode, getStructuringElement
     *      \note The number of iterations is the number of times erosion or dilatation operation will be applied.
     *      For instance, an opening operation (#MORPH_OPEN) with two iterations is equivalent to apply
     *      successively: erode -> erode -> dilate -> dilate (and not erode -> dilate -> erode -> dilate).
     */
    def morphologyEx(op: Int, kernel: Mat, anchor: Point, iterations: Int, borderType: BorderType, borderValue: Scalar): Mat = {
      val dst = new Mat()
      opencv_imgproc.morphologyEx(image,dst,op,kernel,anchor,iterations,borderType.flag,borderValue)
      dst
    }

    def morphologyEx(op: Int, kernel: Mat): Mat =
      morphologyEx(op,kernel,new Point(-1,-1),iterations = 1,BorderTypes.Constant,new Scalar(morphologyDefaultBorderValue))

    /** FtBlurs an image and downsamples it.
     * <p>
     * By default, size of the output image is computed as <pre>Size((src.cols+1)/2, (src.rows+1)/2)</pre>, but in
     * any case, the following conditions should be satisfied:
     * <p>
     * <pre>\[\begin{array}{l} | \texttt{dstsize.width} *2-src.cols| \leq 2 \\ | \texttt{dstsize.height} *2-src.rows| \leq 2 \end{array}\]</pre>
     * <p>
     * The function performs the downsampling step of the Gaussian pyramid construction. First, it
     * convolves the source image with the kernel:
     * <p>
     * <pre>\[\frac{1}{256} \begin{bmatrix} 1 & 4 & 6 & 4 & 1  \\ 4 & 16 & 24 & 16 & 4  \\ 6 & 24 & 36 & 24 & 6  \\ 4 & 16 & 24 & 16 & 4  \\ 1 & 4 & 6 & 4 & 1 \end{bmatrix}\]</pre>
     * <p>
     * Then, it downsamples the image by rejecting even rows and columns.
     * <p>
     *
     * @param src        input image.
     * @param dst        output image; it has the specified size and the same type as src.
     * @param dstsize    size of the output image.
     * @param borderType Pixel extrapolation method, see #BorderTypes (#BORDER_CONSTANT isn't supported)
     */
    def pyrDown(dstsize: Size, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.pyrDown(image,dst,dstsize,borderType.flag)
      dst
    }

    def pyrDown(): Mat = pyrDown(new Size(),BorderTypes.Default)

    /** FtUpsamples an image and then blurs it.
     * <p>
     * By default, size of the output image is computed as <pre>Size(src.cols\*2, (src.rows\*2)</pre>, but in any
     * case, the following conditions should be satisfied:
     * <p>
     * <pre>\[\begin{array}{l} | \texttt{dstsize.width} -src.cols*2| \leq  ( \texttt{dstsize.width}   \mod  2)  \\ | \texttt{dstsize.height} -src.rows*2| \leq  ( \texttt{dstsize.height}   \mod  2) \end{array}\]</pre>
     * <p>
     * The function performs the upsampling step of the Gaussian pyramid construction, though it can
     * actually be used to construct the Laplacian pyramid. First, it upsamples the source image by
     * injecting even zero rows and columns and then convolves the result with the same kernel as in
     * pyrDown multiplied by 4.
     * <p>
     *
     * @param src        input image.
     * @param dst        output image. It has the specified size and the same type as src .
     * @param dstsize    size of the output image.
     * @param borderType Pixel extrapolation method, see #BorderTypes (only #BORDER_DEFAULT is supported)
     */
    def pyrUp(dstsize: Size, borderType: BorderType): Mat = {
      val dst = new Mat()
      opencv_imgproc.pyrUp(image,dst,dstsize,borderType.flag)
      dst
    }

    def pyrUp(): Mat = pyrUp(dstsize = new Size(),BorderTypes.Default)

    /** FtConstructs the Gaussian pyramid for an image.
     * <p>
     * The function constructs a vector of images and builds the Gaussian pyramid by recursively applying
     * pyrDown to the previously built pyramid layers, starting from <pre>dst[0]&#61;&#61;src</pre>.
     * <p>
     *
     * @param src        Source image. Check pyrDown for the list of supported types.
     * @param dst        Destination vector of maxlevel+1 images of the same type as src. dst[0] will be the
     *                   same as src. dst[1] is the next pyramid layer, a smoothed and down-sized src, and so on.
     * @param maxlevel   0-based index of the last (the smallest) pyramid layer. It must be non-negative.
     * @param borderType Pixel extrapolation method, see #BorderTypes (#BORDER_CONSTANT isn't supported)
     */
    def buildPyramid(maxlevel: Int, borderType: BorderType): MatVector = {
      val dst = new MatVector()
      opencv_imgproc.buildPyramid(image,dst,maxlevel,borderType.flag)
      dst
    }

    def buildPyramid(dst: MatVector, maxlevel: Int): MatVector =
      buildPyramid(maxlevel, BorderTypes.Default)

    /**
     * <pre>\[(x,y): X- \texttt{sp</pre> \le x  \le X+ \texttt{sp} , Y- \texttt{sp} \le y  \le Y+ \texttt{sp} , ||(R,G,B)-(r,g,b)||   \le \texttt{sr}\]</pre>
     * <p>
     * where (R,G,B) and (r,g,b) are the vectors of color components at (X,Y) and (x,y), respectively
     * (though, the algorithm does not depend on the color space used, so any 3-component color space can
     * be used instead). Over the neighborhood the average spatial value (X',Y') and average color vector
     * (R',G',B') are found and they act as the neighborhood center on the next iteration:
     * <p>
     * <pre>\[(X,Y)~(X',Y'), (R,G,B)~(R',G',B').\]}</pre>
     * <p>
     * After the iterations over, the color components of the initial pixel (that is, the pixel from where
     * the iterations started) are set to the final value (average color at the last iteration):
     * <p>
     * <pre>\[I(X,Y) <- (R*,G*,B*)\]}</pre>
     * <p>
     * When maxLevel \> 0, the gaussian pyramid of maxLevel+1 levels is built, and the above procedure is
     * run on the smallest layer first. After that, the results are propagated to the larger layer and the
     * iterations are run again only on those pixels where the layer colors differ by more than sr from the
     * lower-resolution layer of the pyramid. That makes boundaries of color regions sharper. Note that the
     * results will be actually different from the ones obtained by running the meanshift procedure on the
     * whole original image (i.e. when maxLevel==0).
     * <p>
     *
     * @param src      The source 8-bit, 3-channel image.
     * @param dst      The destination image of the same format and the same size as the source.
     * @param sp       The spatial window radius.
     * @param sr       The color window radius.
     * @param maxLevel Maximum level of the pyramid for the segmentation.
     * @param termcrit Termination criteria: when to stop meanshift iterations.
     */

    def pyrMeanShiftFiltering(sp: Double, sr: Double, maxlevel: Int, termCriteria: TermCriteria): Mat = {
      val dst = new Mat()
      opencv_imgproc.pyrMeanShiftFiltering(image,dst,sp,sr,maxlevel,termCriteria)
      dst
    }

    def pyrMeanShiftFiltering(sp: Double, sr: Double): Mat =
      pyrMeanShiftFiltering(sp,sr,1,new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS,5,1))
  }
}

