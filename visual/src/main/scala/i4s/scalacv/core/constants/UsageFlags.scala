package i4s.scalacv.core.constants

object UsageFlags extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val
  type UsageFlag = Value

  import scala.language.implicitConversions
  implicit def valueToUsageFlag(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val UsageFlags32Bit = 0x7fffffff; // Binary compatibility hint

  val Default: FlagVal = FlagVal(0)

  // buffer allocation policy is platform and usage specific
  val AllocateHostMemory: FlagVal = FlagVal(1)

  val AllocateDeviceMemory: FlagVal = FlagVal(2)

  val AllocateSharedMemory: FlagVal = FlagVal(4)  // It is not equal to: USAGE_ALLOCATE_HOST_MEMORY | USAGE_ALLOCATE_DEVICE_MEMORY

}
