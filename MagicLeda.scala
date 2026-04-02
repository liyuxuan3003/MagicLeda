package magicleda

import spinal.core._
import spinal.lib._
import spinal.lib.io.TriState

case class MagicLeda() extends Bundle with IMasterSlave {
  val key = Bits(4 bits)
  val switch = Vec(Bits(8 bits), 2)
  val led = Vec(Bits(8 bits), 2)
  val digit = Vec(DigitPin(), 2)
  val uart = UartPin()
  val pin = Analog(Bits(32 bits))

  key.default(B(0, 4 bits))
  switch.default(Vec(B(0, 8 bits), 2))
  led.default(Vec(B(0, 8 bits), 2))
  digit.default(Vec(DigitPin(), 2))
  uart.default(UartPin())
  pin.default(B(0, 32 bits))

  override def asMaster(): Unit = {
    in(key, switch)
    out(led, digit)
    inout(pin)
    master(uart)
  }
}

case class DigitPin() extends Bundle {
  val seg = Bits(8 bits)
  val sel = Bits(4 bits)
  seg.default(B(0, 8 bits))
  sel.default(B(0, 4 bits))
}

case class UartPin() extends Bundle with IMasterSlave {
  val tx = Bool()
  val rx = Bool()
  tx.default(False)
  rx.default(False)
  override def asMaster(): Unit = {
    in(rx)
    out(tx)
  }
}

case class SpiPin() extends Bundle with IMasterSlave {
  val sclk = Bool()
  val miso = Bool()
  val mosi = Bool()
  sclk.default(False)
  miso.default(False)
  mosi.default(False)
  override def asMaster(): Unit = {
    in(miso)
    out(sclk, mosi)
  }
}
