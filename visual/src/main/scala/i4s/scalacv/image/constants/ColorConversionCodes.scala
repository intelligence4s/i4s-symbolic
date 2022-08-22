package i4s.scalacv.image.constants

object ColorConversionCodes extends Enumeration {
  protected case class TypeVal(flag: Int) extends super.Val
  type ColorConversionCode = Value

  import scala.language.implicitConversions
  implicit def valueToColorConversionCode(v: Value): TypeVal = v.asInstanceOf[TypeVal]

  /** add alpha channel to RGB or BGR image */
  val BGR2BGRA: TypeVal = TypeVal(0)
  val RGB2RGBA: TypeVal = BGR2BGRA

  /** remove alpha channel from RGB or BGR image */
  val BGRA2BGR: TypeVal = TypeVal(1)
  val RGBA2RGB: TypeVal = BGRA2BGR

  /** convert between RGB and BGR color spaces (with or without alpha channel) */
  val BGR2RGBA: TypeVal = TypeVal(2)
  val RGB2BGRA: TypeVal = BGR2RGBA
  

  val RGBA2BGR: TypeVal = TypeVal(3)
  val BGRA2RGB: TypeVal = RGBA2BGR

  val BGR2RGB: TypeVal = TypeVal(4)
  val RGB2BGR: TypeVal = BGR2RGB

  val BGRA2RGBA: TypeVal = TypeVal(5)
  val RGBA2BGRA: TypeVal = BGRA2RGBA

  /** convert between RGB/BGR and grayscale \ref color_convert_rgb_gray "color conversions" */
  val BGR2Gray: TypeVal = TypeVal(6)
  val RGB2Gray: TypeVal = TypeVal(7)
  val Gray2BGR: TypeVal = TypeVal(8)
  val Gray2RGB: TypeVal = Gray2BGR
  val Gray2BGRA: TypeVal = TypeVal(9)
  val Gray2RGBA: TypeVal = Gray2BGRA
  val BGRA2Gray: TypeVal = TypeVal(10)
  val RGBA2Gray: TypeVal = TypeVal(11)
  

  /** convert between RGB/BGR and BGR565 (16-bit images) */
  val BGR2BGR565: TypeVal = TypeVal(12)
  val RGB2BGR565: TypeVal = TypeVal(13)
  val BGR5652BGR: TypeVal = TypeVal(14)
  val BGR5652RGB: TypeVal = TypeVal(15)
  val BGRA2BGR565: TypeVal = TypeVal(16)
  val RGBA2BGR565: TypeVal = TypeVal(17)
  val BGR5652BGRA: TypeVal = TypeVal(18)
  val BGR5652RGBA: TypeVal = TypeVal(19)
  

  /** convert between grayscale to BGR565 (16-bit images) */
  val Gray2BGR565: TypeVal = TypeVal(20)
  val BGR5652Gray: TypeVal = TypeVal(21)

  /** convert between RGB/BGR and BGR555 (16-bit images) */
  val BGR2BGR555: TypeVal = TypeVal(22)
  val RGB2BGR555: TypeVal = TypeVal(23)
  val BGR5552BGR: TypeVal = TypeVal(24)
  val BGR5552RGB: TypeVal = TypeVal(25)
  val BGRA2BGR555: TypeVal = TypeVal(26)
  val RGBA2BGR555: TypeVal = TypeVal(27)
  val BGR5552BGRA: TypeVal = TypeVal(28)
  val BGR5552RGBA: TypeVal = TypeVal(29)
  

  /** convert between grayscale and BGR555 (16-bit images) */
  val Gray2BGR555: TypeVal = TypeVal(30)
  val BGR5552Gray: TypeVal = TypeVal(31)
  

  /** convert RGB/BGR to CIE XYZ \ref color_convert_rgb_xyz "color conversions" */
  val BGR2XYZ: TypeVal = TypeVal(32)
  val RGB2XYZ: TypeVal = TypeVal(33)
  val XYZ2BGR: TypeVal = TypeVal(34)
  val XYZ2RGB: TypeVal = TypeVal(35)

  /** convert RGB/BGR to luma-chroma (aka YCC) \ref color_convert_rgb_ycrcb "color conversions" */
  val BGR2YCrCb: TypeVal = TypeVal(36)
  val RGB2YCrCb: TypeVal = TypeVal(37)
  val YCrCb2BGR: TypeVal = TypeVal(38)
  val YCrCb2RGB: TypeVal = TypeVal(39)

  /** convert RGB/BGR to HSV (hue saturation value) with H range 0..180 if 8 bit image \ref color_convert_rgb_hsv "color conversions" */
  val BGR2HSV: TypeVal = TypeVal(40)
  val RGB2HSV: TypeVal = TypeVal(41)

  /** convert RGB/BGR to CIE Lab \ref color_convert_rgb_lab "color conversions" */
  val BGR2Lab: TypeVal = TypeVal(44)
  val RGB2Lab: TypeVal = TypeVal(45)

  /** convert RGB/BGR to CIE Luv \ref color_convert_rgb_luv "color conversions" */
  val BGR2Luv: TypeVal = TypeVal(50)
  val RGB2Luv: TypeVal = TypeVal(51)
  

  /** convert RGB/BGR to HLS (hue lightness saturation) with H range 0..180 if 8 bit image \ref color_convert_rgb_hls "color conversions" */
  val BGR2HLS: TypeVal = TypeVal(52)
  val RGB2HLS: TypeVal = TypeVal(53)

  /** backward conversions HSV to RGB/BGR with H range 0..180 if 8 bit image */
  val HSV2BGR: TypeVal = TypeVal(54)
  val HSV2RGB: TypeVal = TypeVal(55)

  val Lab2BGR: TypeVal = TypeVal(56)
  val Lab2RGB: TypeVal = TypeVal(57)
  val Luv2BGR: TypeVal = TypeVal(58)
  val Luv2RGB: TypeVal = TypeVal(59)

  /** backward conversions HLS to RGB/BGR with H range 0..180 if 8 bit image */
  val HLS2BGR: TypeVal = TypeVal(60)
  val HLS2RGB: TypeVal = TypeVal(61)

  /** convert RGB/BGR to HSV (hue saturation value) with H range 0..255 if 8 bit image \ref color_convert_rgb_hsv "color conversions" */
  val BGR2HSVFull: TypeVal = TypeVal(66)
  val RGB2HSVFull: TypeVal = TypeVal(67)

  /** convert RGB/BGR to HLS (hue lightness saturation) with H range 0..255 if 8 bit image \ref color_convert_rgb_hls "color conversions" */
  val BGR2HLSFull: TypeVal = TypeVal(68)
  val RGB2HLSFull: TypeVal = TypeVal(69)

  /** backward conversions HSV to RGB/BGR with H range 0..255 if 8 bit image */
  val HSV2BGRFull: TypeVal = TypeVal(70)
  val HSV2RGBFull: TypeVal = TypeVal(71)

  /** backward conversions HLS to RGB/BGR with H range 0..255 if 8 bit image */
  val HLS2BGRFull: TypeVal = TypeVal(72)
  val HLS2RGBFull: TypeVal = TypeVal(73)

  val LBGR2Lab: TypeVal = TypeVal(74)
  val LRGB2Lab: TypeVal = TypeVal(75)
  val LBGR2Luv: TypeVal = TypeVal(76)
  val LRGB2Luv: TypeVal = TypeVal(77)

  val Lab2LBGR: TypeVal = TypeVal(78)
  val Lab2LRGB: TypeVal = TypeVal(79)
  val Luv2LBGR: TypeVal = TypeVal(80)
  val Luv2LRGB: TypeVal = TypeVal(81)

  /** convert between RGB/BGR and YUV */
  val BGR2YUV: TypeVal = TypeVal(82)
  val RGB2YUV: TypeVal = TypeVal(83)
  val YUV2BGR: TypeVal = TypeVal(84)
  val YUV2RGB: TypeVal = TypeVal(85)

  /** YUV 4:2:0 family to RGB */
  val YUV2RGB_NV12: TypeVal = TypeVal(90)
  val YUV2BGR_NV12: TypeVal = TypeVal(91)
  val YUV2RGB_NV21: TypeVal = TypeVal(92)
  val YUV2BGR_NV21: TypeVal = TypeVal(93)
  
  val YUV420sp2RGB: TypeVal = YUV2RGB_NV21
  val YUV420sp2BGR: TypeVal = YUV2BGR_NV21
  val YUV2RGBA_NV12: TypeVal = TypeVal(94)
  val YUV2BGRA_NV12: TypeVal = TypeVal(95)
  val YUV2RGBA_NV21: TypeVal = TypeVal(96)
  val YUV2BGRA_NV21: TypeVal = TypeVal(97)
  val YUV420sp2RGBA: TypeVal = YUV2RGBA_NV21
  val YUV420sp2BGRA: TypeVal = YUV2BGRA_NV21

  val YUV2RGB_YV12: TypeVal = TypeVal(98)
  val YUV2BGR_YV12: TypeVal = TypeVal(99)
  val YUV2RGB_IYUV: TypeVal = TypeVal(100)
  val YUV2BGR_IYUV: TypeVal = TypeVal(101)
  val YUV2RGB_I420: TypeVal = YUV2RGB_IYUV
  val YUV2BGR_I420: TypeVal = YUV2BGR_IYUV
  val YUV420p2RGB: TypeVal = YUV2RGB_YV12
  val YUV420p2BGR: TypeVal = YUV2BGR_YV12

  val YUV2RGBA_YV12: TypeVal = TypeVal(102)
  val YUV2BGRA_YV12: TypeVal = TypeVal(103)
  val YUV2RGBA_IYUV: TypeVal = TypeVal(104)
  val YUV2BGRA_IYUV: TypeVal = TypeVal(105)
  val YUV2RGBA_I420: TypeVal = YUV2RGBA_IYUV
  val YUV2BGRA_I420: TypeVal = YUV2BGRA_IYUV
  val YUV420p2RGBA: TypeVal = YUV2RGBA_YV12
  val YUV420p2BGRA: TypeVal = YUV2BGRA_YV12

  val YUV2Gray_420: TypeVal = TypeVal(106)
  val YUV2Gray_NV21: TypeVal = YUV2Gray_420
  val YUV2Gray_NV12: TypeVal = YUV2Gray_420
  val YUV2Gray_YV12: TypeVal = YUV2Gray_420
  val YUV2Gray_IYUV: TypeVal = YUV2Gray_420
  val YUV2Gray_I420: TypeVal = YUV2Gray_420
  val YUV420sp2Gray: TypeVal = YUV2Gray_420
  val YUV420p2Gray: TypeVal = YUV2Gray_420

  /** YUV 4:2:2 family to RGB */
  val YUV2RGB_UYVY: TypeVal = TypeVal(107)
  val YUV2BGR_UYVY: TypeVal = TypeVal(108)
  val YUV2RGB_VYUY: TypeVal = TypeVal(109)
  val YUV2BGR_VYUY: TypeVal = TypeVal(110)
  val YUV2RGB_Y422: TypeVal = YUV2RGB_UYVY
  
  val YUV2BGR_Y422: TypeVal = YUV2BGR_UYVY
  val YUV2RGB_UYNV: TypeVal = YUV2RGB_UYVY
  val YUV2BGR_UYNV: TypeVal = YUV2BGR_UYVY

  val YUV2RGBA_UYVY: TypeVal = TypeVal(111)
  val YUV2BGRA_UYVY: TypeVal = TypeVal(112)
  val YUV2RGBA_VYUY: TypeVal = TypeVal(113)
  val YUV2BGRA_VYUY: TypeVal = TypeVal(114)
  val YUV2RGBA_Y422: TypeVal = YUV2RGBA_UYVY
  val YUV2BGRA_Y422: TypeVal = YUV2BGRA_UYVY
  val YUV2RGBA_UYNV: TypeVal = YUV2RGBA_UYVY
  val YUV2BGRA_UYNV: TypeVal = YUV2BGRA_UYVY

  val YUV2RGB_YUY2: TypeVal = TypeVal(115)
  val YUV2BGR_YUY2: TypeVal = TypeVal(116)
  val YUV2RGB_YVYU: TypeVal = TypeVal(117)
  val YUV2BGR_YVYU: TypeVal = TypeVal(118)
  val YUV2RGB_YUYV: TypeVal = YUV2RGB_YUY2
  val YUV2BGR_YUYV: TypeVal = YUV2BGR_YUY2
  val YUV2RGB_YUNV: TypeVal = YUV2RGB_YUY2
  val YUV2BGR_YUNV: TypeVal = YUV2BGR_YUY2

  val YUV2RGBA_YUY2: TypeVal = TypeVal(119)
  val YUV2BGRA_YUY2: TypeVal = TypeVal(120)
  val YUV2RGBA_YVYU: TypeVal = TypeVal(121)
  val YUV2BGRA_YVYU: TypeVal = TypeVal(122)
  val YUV2RGBA_YUYV: TypeVal = YUV2RGBA_YUY2
  val YUV2BGRA_YUYV: TypeVal = YUV2BGRA_YUY2
  val YUV2RGBA_YUNV: TypeVal = YUV2RGBA_YUY2
  val YUV2BGRA_YUNV: TypeVal = YUV2BGRA_YUY2
  val YUV2Gray_UYVY: TypeVal = TypeVal(123)
  val YUV2Gray_YUY2: TypeVal = TypeVal(124)
  
  //CV_YUV2Gray_VYUY    = CV_YUV2Gray_UYVY
  val YUV2Gray_Y422: TypeVal = YUV2Gray_UYVY
  val YUV2Gray_UYNV: TypeVal = YUV2Gray_UYVY
  val YUV2Gray_YVYU: TypeVal = YUV2Gray_YUY2
  val YUV2Gray_YUYV: TypeVal = YUV2Gray_YUY2
  val YUV2Gray_YUNV: TypeVal = YUV2Gray_YUY2

  /** alpha premultiplication */
  val RGBA2mRGBA: TypeVal = TypeVal(125)
  val mRGBA2RGBA: TypeVal = TypeVal(126)

  /** RGB to YUV 4:2:0 family */
  val RGB2YUV_I420: TypeVal = TypeVal(127)
  val BGR2YUV_I420: TypeVal = TypeVal(128)
  val RGB2YUV_IYUV: TypeVal = RGB2YUV_I420
  val BGR2YUV_IYUV: TypeVal = BGR2YUV_I420

  val RGBA2YUV_I420: TypeVal = TypeVal(129)
  val BGRA2YUV_I420: TypeVal = TypeVal(130)
  val RGBA2YUV_IYUV: TypeVal = RGBA2YUV_I420
  val BGRA2YUV_IYUV: TypeVal = BGRA2YUV_I420
  val RGB2YUV_YV12: TypeVal = TypeVal(131)
  val BGR2YUV_YV12: TypeVal = TypeVal(132)
  val RGBA2YUV_YV12: TypeVal = TypeVal(133)
  val BGRA2YUV_YV12: TypeVal = TypeVal(134)

  /** Demosaicing see \ref color_convert_bayer "color conversions" for additional information */
  /** equivalent to RGGB Bayer pattern */
  val BayerBG2BGR: TypeVal = TypeVal(46)

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2BGR: TypeVal = TypeVal(47)

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2BGR: TypeVal = TypeVal(48)

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2BGR: TypeVal = TypeVal(49)

  val BayerRGGB2BGR: TypeVal = BayerBG2BGR
  val BayerGRBG2BGR: TypeVal = BayerGB2BGR
  val BayerBGGR2BGR: TypeVal = BayerRG2BGR
  val BayerGBRG2BGR: TypeVal = BayerGR2BGR

  val BayerRGGB2RGB: TypeVal = BayerBGGR2BGR
  val BayerGRBG2RGB: TypeVal = BayerGBRG2BGR
  val BayerBGGR2RGB: TypeVal = BayerRGGB2BGR
  val BayerGBRG2RGB: TypeVal = BayerGRBG2BGR

  /** equivalent to RGGB Bayer pattern */
  val BayerBG2RGB: TypeVal = BayerRG2BGR

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2RGB: TypeVal = BayerGR2BGR

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2RGB: TypeVal = BayerBG2BGR

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2RGB: TypeVal = BayerGB2BGR

  /** equivalent to RGGB Bayer pattern */
  val BayerBG2Gray: TypeVal = TypeVal(86)

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2Gray: TypeVal = TypeVal(87)

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2Gray: TypeVal = TypeVal(88)

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2Gray: TypeVal = TypeVal(89)

  val BayerRGGB2Gray: TypeVal = BayerBG2Gray
  val BayerGRBG2Gray: TypeVal = BayerGB2Gray
  val BayerBGGR2Gray: TypeVal = BayerRG2Gray
  val BayerGBRG2Gray: TypeVal = BayerGR2Gray

  /** Demosaicing using Variable Number of Gradients */
  /** equivalent to RGGB Bayer pattern */
  val BayerBG2BGR_VNG: TypeVal = TypeVal(62)

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2BGR_VNG: TypeVal = TypeVal(63)

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2BGR_VNG: TypeVal = TypeVal(64)

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2BGR_VNG: TypeVal = TypeVal(65)

  val BayerRGGB2BGR_VNG: TypeVal = BayerBG2BGR_VNG
  val BayerGRBG2BGR_VNG: TypeVal = BayerGB2BGR_VNG
  val BayerBGGR2BGR_VNG: TypeVal = BayerRG2BGR_VNG
  val BayerGBRG2BGR_VNG: TypeVal = BayerGR2BGR_VNG

  val BayerRGGB2RGB_VNG: TypeVal = BayerBGGR2BGR_VNG
  val BayerGRBG2RGB_VNG: TypeVal = BayerGBRG2BGR_VNG
  val BayerBGGR2RGB_VNG: TypeVal = BayerRGGB2BGR_VNG
  val BayerGBRG2RGB_VNG: TypeVal = BayerGRBG2BGR_VNG

  /** equivalent to RGGB Bayer pattern */
  val BayerBG2RGB_VNG: TypeVal = BayerRG2BGR_VNG

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2RGB_VNG: TypeVal = BayerGR2BGR_VNG

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2RGB_VNG: TypeVal = BayerBG2BGR_VNG

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2RGB_VNG: TypeVal = BayerGB2BGR_VNG

  /** Edge-Aware Demosaicing */
  /** equivalent to RGGB Bayer pattern */
  val BayerBG2BGR_EA: TypeVal = TypeVal(135)

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2BGR_EA: TypeVal = TypeVal(136)

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2BGR_EA: TypeVal = TypeVal(137)

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2BGR_EA: TypeVal = TypeVal(138)

  val BayerRGGB2BGR_EA: TypeVal = BayerBG2BGR_EA
  val BayerGRBG2BGR_EA: TypeVal = BayerGB2BGR_EA
  val BayerBGGR2BGR_EA: TypeVal = BayerRG2BGR_EA
  val BayerGBRG2BGR_EA: TypeVal = BayerGR2BGR_EA

  val BayerRGGB2RGB_EA: TypeVal = BayerBGGR2BGR_EA
  val BayerGRBG2RGB_EA: TypeVal = BayerGBRG2BGR_EA
  val BayerBGGR2RGB_EA: TypeVal = BayerRGGB2BGR_EA
  val BayerGBRG2RGB_EA: TypeVal = BayerGRBG2BGR_EA

  /** equivalent to RGGB Bayer pattern */
  val BayerBG2RGB_EA: TypeVal = BayerRG2BGR_EA

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2RGB_EA: TypeVal = BayerGR2BGR_EA

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2RGB_EA: TypeVal = BayerBG2BGR_EA

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2RGB_EA: TypeVal = BayerGB2BGR_EA

  /** Demosaicing with alpha channel */
  /** equivalent to RGGB Bayer pattern */
  val BayerBG2BGRA: TypeVal = TypeVal(139)

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2BGRA: TypeVal = TypeVal(140)

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2BGRA: TypeVal = TypeVal(141)

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2BGRA: TypeVal = TypeVal(142)

  val BayerRGGB2BGRA: TypeVal = BayerBG2BGRA
  val BayerGRBG2BGRA: TypeVal = BayerGB2BGRA
  val BayerBGGR2BGRA: TypeVal = BayerRG2BGRA
  val BayerGBRG2BGRA: TypeVal = BayerGR2BGRA

  val BayerRGGB2RGBA: TypeVal = BayerBGGR2BGRA
  val BayerGRBG2RGBA: TypeVal = BayerGBRG2BGRA
  val BayerBGGR2RGBA: TypeVal = BayerRGGB2BGRA
  val BayerGBRG2RGBA: TypeVal = BayerGRBG2BGRA

  /** equivalent to RGGB Bayer pattern */
  val BayerBG2RGBA: TypeVal = BayerRG2BGRA

  /** equivalent to GRBG Bayer pattern */
  val BayerGB2RGBA: TypeVal = BayerGR2BGRA

  /** equivalent to BGGR Bayer pattern */
  val BayerRG2RGBA: TypeVal = BayerBG2BGRA

  /** equivalent to GBRG Bayer pattern */
  val BayerGR2RGBA: TypeVal = BayerGB2BGRA

  val COLORCVTMax: TypeVal = TypeVal(143)
}
