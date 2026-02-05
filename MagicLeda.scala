package magicleda

import spinal.core._
import spinal.lib._
import spinal.lib.io.TriState

case class MagicLeda() extends Bundle with IMasterSlave {
  val key = Vec(master(TriState(Bool())), 8)
  val switch = Vec(Vec(master(TriState(Bool())), 8), 2)
  val led = Vec(Vec(master(TriState(Bool())), 8), 2)

  // led.default(Vec(B(0, 8 bits), 2))

  override def asMaster(): Unit = {}
}
