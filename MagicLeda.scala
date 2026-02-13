package magicleda

import spinal.core._
import spinal.lib._
import spinal.lib.io.TriState

case class MagicLeda() extends Bundle with IMasterSlave {
  val key = Bits(4 bits)
  val switch = Vec(Bits(8 bits), 2)
  val led = Vec(Bits(8 bits), 2)
  val digit = Vec(DigitPin(), 2)

  key.default(B(0, 4 bits))
  switch.default(Vec(B(0, 8 bits), 2))
  led.default(Vec(B(0, 8 bits), 2))
  digit.default(Vec(DigitPin(), 2))

  override def asMaster(): Unit = {
    in(key, switch)
    out(led, digit)
  }
}

case class DigitPin() extends Bundle {
  val seg = Bits(8 bits)
  val sel = Bits(4 bits)
  seg.default(B(0, 8 bits))
  sel.default(B(0, 4 bits))
}
