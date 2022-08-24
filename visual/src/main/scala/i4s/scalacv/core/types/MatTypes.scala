package i4s.scalacv.core.types

import i4s.scalacv.core.types.Types.{Cv16F, Cv16S, Cv16U, Cv32F, Cv32S, Cv64F, Cv8S, Cv8U, Type}

object MatTypes extends Enumeration {

  private val ChannelMax = 512
  private val ChannelShift = 3
  private val DepthMax = 1 << ChannelShift

  protected case class FlagVal(flag: Int) extends super.Val {
    override def toString(): String = s"${dataType.toString}C${channels}"

    def channels: Int = MatTypes.channels(this)
    def dataType: Type = MatTypes.depth(this)
  }
  type MatType = Value

  import scala.language.implicitConversions
  implicit def valueToTypeChannel(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  def toString(f: Int): String = makeType(depth(f),channels(f)).toString
  def toEnum(f: Int): MatType =
    values.find(_.flag == f)
      .getOrElse(FlagVal(makeType(depth(f),channels(f))))

  def toEnumOption(f: Int): Option[MatType] = values.find(_.flag == f)

  // predefined type constants
  val Ft8UC1: FlagVal = FlagVal(Ft8UC(1))
  val Ft8UC2: FlagVal = FlagVal(Ft8UC(2))
  val Ft8UC3: FlagVal = FlagVal(Ft8UC(3))
  val Ft8UC4: FlagVal = FlagVal(Ft8UC(4))

  val Ft8SC1:FlagVal = FlagVal(Ft8SC(1))
  val Ft8SC2:FlagVal = FlagVal(Ft8SC(2))
  val Ft8SC3:FlagVal = FlagVal(Ft8SC(3))
  val Ft8SC4:FlagVal = FlagVal(Ft8SC(4))

  val Ft16UC1:FlagVal = FlagVal(Ft16UC(1))
  val Ft16UC2:FlagVal = FlagVal(Ft16UC(2))
  val Ft16UC3:FlagVal = FlagVal(Ft16UC(3))
  val Ft16UC4:FlagVal = FlagVal(Ft16UC(4))

  val Ft16SC1:FlagVal = FlagVal(Ft16SC(1))
  val Ft16SC2:FlagVal = FlagVal(Ft16SC(2))
  val Ft16SC3:FlagVal = FlagVal(Ft16SC(3))
  val Ft16SC4:FlagVal = FlagVal(Ft16SC(4))

  val Ft32SC1:FlagVal = FlagVal(Ft32SC(1))
  val Ft32SC2:FlagVal = FlagVal(Ft32SC(2))
  val Ft32SC3:FlagVal = FlagVal(Ft32SC(3))
  val Ft32SC4:FlagVal = FlagVal(Ft32SC(4))

  val Ft32FC1:FlagVal = FlagVal(Ft32FC(1))
  val Ft32FC2:FlagVal = FlagVal(Ft32FC(2))
  val Ft32FC3:FlagVal = FlagVal(Ft32FC(3))
  val Ft32FC4:FlagVal = FlagVal(Ft32FC(4))

  val Ft64FC1:FlagVal = FlagVal(Ft64FC(1))
  val Ft64FC2:FlagVal = FlagVal(Ft64FC(2))
  val Ft64FC3:FlagVal = FlagVal(Ft64FC(3))
  val Ft64FC4:FlagVal = FlagVal(Ft64FC(4))

  val Ft16FC1:FlagVal = FlagVal(Ft16FC(1))
  val Ft16FC2:FlagVal = FlagVal(Ft16FC(2))
  val Ft16FC3:FlagVal = FlagVal(Ft16FC(3))
  val Ft16FC4:FlagVal = FlagVal(Ft16FC(4))

  def makeType(depth: Type, channels: Int): Int = {
    require(channels > 0 && channels < ChannelMax, s"Channel value is $channels -- Channels count should be 1..${ChannelMax - 1}")
    require(depth.flag <= ChannelMax, s"Unsupported channel depth $depth")
    (depth.flag & (DepthMax - 1)) + ((channels - 1) << ChannelShift)
  }

  def Ft8UC(ch: Int): Int = makeType(Types.Cv8U,ch)
  def Ft8SC(ch: Int): Int = makeType(Types.Cv8S,ch)
  def Ft16UC(ch: Int): Int = makeType(Types.Cv16U,ch)
  def Ft16SC(ch: Int): Int = makeType(Types.Cv16S,ch)
  def Ft32SC(ch: Int): Int = makeType(Types.Cv32S,ch)
  def Ft32FC(ch: Int): Int = makeType(Types.Cv32F,ch)
  def Ft64FC(ch: Int): Int = makeType(Types.Cv64F,ch)
  def Ft16FC(ch: Int): Int = makeType(Types.Cv16F,ch)

  def channels(flowType: MatType): Int = channels(flowType.flag)
  def depth(flowType: MatType): Type = depth(flowType.flag)

  def channels(flag: Int): Int = (flag >> ChannelShift) + 1
  def depth(flag: Int): Type = {
    val f = flag & (DepthMax - 1)
    Types.values.find(_.flag == f).getOrElse(throw new UnsupportedOperationException(s"Unsupported TypeDepth - $f from $flag"))
  }

  def isInteger(flowType: MatType): Boolean = depth(flowType).flag < Cv32F.flag

  def ElementSize(flowType: MatType): Int = depth(flowType) match {
    case Cv8U | Cv8S => channels(flowType)
    case Cv16S | Cv16U | Cv16F => 2 * channels(flowType)
    case Cv32S | Cv32F => 4 * channels(flowType)
    case Cv64F => 8 * channels(flowType)
    case _ =>
      throw new UnsupportedOperationException(s"Unsupported CvType value: $flowType:${flowType.flag}")
  }
  
}
