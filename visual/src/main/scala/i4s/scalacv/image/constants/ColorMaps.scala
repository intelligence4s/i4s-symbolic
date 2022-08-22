package i4s.scalacv.image.constants

object ColorMaps extends Enumeration {
  protected case class TypeVal(flag: Int) extends super.Val
  type ColorMap = Value

  import scala.language.implicitConversions
  implicit def valueToColorMap(v: Value): TypeVal = v.asInstanceOf[TypeVal]

  /** ![autumn](pics/colormaps/colorscale_autumn.jpg) */
  val Autumn: TypeVal = TypeVal(0)

  /** ![bone](pics/colormaps/colorscale_bone.jpg) */
  val Bone: TypeVal = TypeVal(1)

  /** ![jet](pics/colormaps/colorscale_jet.jpg) */
  val Jet: TypeVal = TypeVal(2)

  /** ![winter](pics/colormaps/colorscale_winter.jpg) */
  val Winter: TypeVal = TypeVal(3)

  /** ![rainbow](pics/colormaps/colorscale_rainbow.jpg) */
  val Rainbow: TypeVal = TypeVal(4)

  /** ![ocean](pics/colormaps/colorscale_ocean.jpg) */
  val Ocean: TypeVal = TypeVal(5)

  /** ![summer](pics/colormaps/colorscale_summer.jpg) */
  val Summer: TypeVal = TypeVal(6)

  /** ![spring](pics/colormaps/colorscale_spring.jpg) */
  val Spring: TypeVal = TypeVal(7)

  /** ![cool](pics/colormaps/colorscale_cool.jpg) */
  val Cool: TypeVal = TypeVal(8)

  /** ![HSV](pics/colormaps/colorscale_hsv.jpg) */
  val HSV: TypeVal = TypeVal(9)

  /** ![pink](pics/colormaps/colorscale_pink.jpg) */
  val Pink: TypeVal = TypeVal(10)

  /** ![hot](pics/colormaps/colorscale_hot.jpg) */
  val Hot: TypeVal = TypeVal(11)

  /** ![parula](pics/colormaps/colorscale_parula.jpg) */
  val Parula: TypeVal = TypeVal(12)

  /** ![magma](pics/colormaps/colorscale_magma.jpg) */
  val Magma: TypeVal = TypeVal(13)

  /** ![inferno](pics/colormaps/colorscale_inferno.jpg) */
  val Inferno: TypeVal = TypeVal(14)

  /** ![plasma](pics/colormaps/colorscale_plasma.jpg) */
  val Plasma: TypeVal = TypeVal(15)

  /** ![viridis](pics/colormaps/colorscale_viridis.jpg) */
  val Viridis: TypeVal = TypeVal(16)

  /** ![cividis](pics/colormaps/colorscale_cividis.jpg) */
  val Cividis: TypeVal = TypeVal(17)

  /** ![twilight](pics/colormaps/colorscale_twilight.jpg) */
  val Twilight: TypeVal = TypeVal(18)

  /** ![twilight shifted](pics/colormaps/colorscale_twilight_shifted.jpg) */
  val TwilightShifted: TypeVal = TypeVal(19)

  /** ![turbo](pics/colormaps/colorscale_turbo.jpg) */
  val Turbo: TypeVal = TypeVal(20)

  /** ![deepgreen](pics/colormaps/colorscale_deepgreen.jpg) */
  val DeepGreen: TypeVal = TypeVal(21)
}
