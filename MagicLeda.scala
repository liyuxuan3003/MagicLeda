package magicleda

import spinal.core._
import spinal.lib._

case class MagicLeda() extends Bundle with IMasterSlave {
  val key = Bits(4 bits)
  val switch = Vec(Bits(8 bits), 2)
  val led = Vec(Bits(8 bits), 2)

  led.default(Vec(B(0, 8 bits), 2))

  override def asMaster(): Unit = {
    in(key, switch)
    out(led)
  }
}
