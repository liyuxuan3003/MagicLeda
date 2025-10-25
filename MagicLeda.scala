package magicleda

import spinal.core._
import spinal.lib._

case class MagicLeda() extends Bundle with IMasterSlave {
  val clk = Bool().default(Bool())
  val reset = Bool().default(Bool())
  val key = Bits(5 bits).default(0)
  val switch = Vec(Bits(8 bits), 2).default(Vec(B(0, 8 bits), 2))
  val led = Vec(Bits(8 bits), 2).default(Vec(B(0, 8 bits), 2))

  override def asMaster(): Unit = {
    in(clk, reset, key, switch)
    out(led)
  }
}
