package i4s.scalacv.image

import i4s.scalacv.image.constants.ColorConversionCodes.ColorConversionCode
import i4s.scalacv.image.constants.ColorMaps.ColorMap
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_core.UMat

object Colors extends Colors

trait Colors {

  implicit class ImageColor(image: Image) {

    /** \brief Converts an image from one color space to another.
     * <p>
     * The function converts an input image from one color space to another. In case of a transformation
     * to-from RGB color space, the order of the channels should be specified explicitly (RGB or BGR). Note
     * that the default color format in OpenCV is often referred to as RGB but it is actually BGR (the
     * bytes are reversed). So the first byte in a standard (24-bit) color image will be an 8-bit Blue
     * component, the second byte will be Green, and the third byte will be Red. The fourth, fifth, and
     * sixth bytes would then be the second pixel (Blue, then Green, then Red), and so on.
     * <p>
     * The conventional ranges for R, G, and B channel values are:
     * -   0 to 255 for CV_8U images
     * -   0 to 65535 for CV_16U images
     * -   0 to 1 for CV_32F images
     * <p>
     * In case of linear transformations, the range does not matter. But in case of a non-linear
     * transformation, an input RGB image should be normalized to the proper value range to get the correct
     * results, for example, for RGB {@code \rightarrow} L\*u\*v\* transformation. For example, if you have a
     * 32-bit floating-point image directly converted from an 8-bit image without any scaling, then it will
     * have the 0..255 value range instead of 0..1 assumed by the function. So, before calling #cvtColor ,
     * you need first to scale the image down:
     * <pre>
     * img *= 1./255;
     * cvtColor(img, img, COLOR_BGR2Luv);
     * </pre>
     * If you use #cvtColor with 8-bit images, the conversion will have some information lost. For many
     * applications, this will not be noticeable but it is recommended to use 32-bit images in applications
     * that need the full range of colors or that convert an image before an operation and then convert
     * back.
     * <p>
     * If conversion adds the alpha channel, its value will set to the maximum of corresponding channel
     * range: 255 for CV_8U, 65535 for CV_16U, 1 for CV_32F.
     * <p>
     * @param src input image: 8-bit unsigned, 16-bit unsigned ( CV_16UC... ), or single-precision
     * floating-point.
     * @param dst output image of the same size and depth as src.
     * @param code color space conversion code (see #ColorConversionCodes).
     * @param dstCn number of channels in the destination image; if the parameter is 0, the number of the
     * channels is derived automatically from src and code.
     * <p>
     * @see \ref imgproc_color_conversions
     */

    def cvtColor(code: ColorConversionCode, dstCn: Int /*=0*/): Image = {
      val dst = new Image()
      opencv_imgproc.cvtColor(image,dst,code.flag,dstCn)
      dst
    }

    def cvtColor(code: ColorConversionCode): Image =
      cvtColor(code, dstCn = 0)

    /** \brief Converts an image from one color space to another where the source image is
     * stored in two planes.
     * <p>
     * This function only supports YUV420 to RGB conversion as of now.
     * <p>
     * @param src1 : 8-bit image (#CV_8U) of the Y plane.
     * @param src2 : image containing interleaved U/V plane.
     * @param dst  : output image.
     * @param code : Specifies the type of conversion. It can take any of the following values:
     *             - #COLOR_YUV2BGR_NV12
     *             - #COLOR_YUV2RGB_NV12
     *             - #COLOR_YUV2BGRA_NV12
     *             - #COLOR_YUV2RGBA_NV12
     *             - #COLOR_YUV2BGR_NV21
     *             - #COLOR_YUV2RGB_NV21
     *             - #COLOR_YUV2BGRA_NV21
     *             - #COLOR_YUV2RGBA_NV21
     */
    def cvtColorTwoPlane(src2: Image, code: ColorConversionCode): Image = {
      val dst = new Image()
      opencv_imgproc.cvtColorTwoPlane(image,src2,dst,code.flag)
      dst
    }

    /** \brief main function for all demosaicing processes
     * <p>
     * @param src   input image: 8-bit unsigned or 16-bit unsigned.
     * @param dst   output image of the same size and depth as src.
     * @param code  Color space conversion code (see the description below).
     * @param dstCn number of channels in the destination image; if the parameter is 0, the number of the
     *              channels is derived automatically from src and code.
     *              <p>
     *              The function can do the following transformations:
     *              <p>
     *              - Demosaicing using bilinear interpolation
     *              <p>
     *              #COLOR_BayerBG2BGR , #COLOR_BayerGB2BGR , #COLOR_BayerRG2BGR , #COLOR_BayerGR2BGR
     *              <p>
     *              #COLOR_BayerBG2GRAY , #COLOR_BayerGB2GRAY , #COLOR_BayerRG2GRAY , #COLOR_BayerGR2GRAY
     *              <p>
     *              - Demosaicing using Variable Number of Gradients.
     *              <p>
     *              #COLOR_BayerBG2BGR_VNG , #COLOR_BayerGB2BGR_VNG , #COLOR_BayerRG2BGR_VNG , #COLOR_BayerGR2BGR_VNG
     *              <p>
     *              - Edge-Aware Demosaicing.
     *              <p>
     *              #COLOR_BayerBG2BGR_EA , #COLOR_BayerGB2BGR_EA , #COLOR_BayerRG2BGR_EA , #COLOR_BayerGR2BGR_EA
     *              <p>
     *              - Demosaicing with alpha channel
     *              <p>
     *              #COLOR_BayerBG2BGRA , #COLOR_BayerGB2BGRA , #COLOR_BayerRG2BGRA , #COLOR_BayerGR2BGRA
     *              <p>
     * @see cvtColor
     */
    def demosaicing(code: ColorConversionCode, dstCn: Int /*=0*/): Image = {
      val dst = new Image()
      opencv_imgproc.demosaicing(image,dst,code.flag,dstCn)
      dst
    }

    def demosaicing(code: ColorConversionCode): Image =
      demosaicing(code,dstCn = 0)

    /** \brief Applies a GNU Octave/MATLAB equivalent colormap on a given image.
     * <p>
     * @param src      The source image, grayscale or colored of type CV_8UC1 or CV_8UC3.
     * @param dst      The result is the colormapped source image. Note: Mat::create is called on dst.
     * @param colorMap The colormap to apply, see #ColormapTypes
     */
    def applyColorMap(colorMap: ColorMap): Image = {
      val dst = new Image()
      opencv_imgproc.applyColorMap(image,dst,colorMap.flag)
      dst
    }

    /** \brief Applies a user colormap on a given image.
     * <p>
     * @param src       The source image, grayscale or colored of type CV_8UC1 or CV_8UC3.
     * @param dst       The result is the colormapped source image. Note: Mat::create is called on dst.
     * @param userColor The colormap to apply of type CV_8UC1 or CV_8UC3 and size 256
     */
    def applyColorMap(userColor: UMat): Image = {
      val dst = new Image()
      opencv_imgproc.applyColorMap(image,dst,userColor)
      dst
    }

  }
}
