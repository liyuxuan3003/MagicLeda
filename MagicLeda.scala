package magicleda

import spinal.core._
import spinal.lib._
import spinal.lib.io.TriState

case class MagicLeda() extends Bundle with IMasterSlave {
  val key = Vec(TriState(Bool()), 8)
  val switch = Vec(Vec(TriState(Bool()), 8), 2)
  val led = Vec(Vec(TriState(Bool()), 8), 2)

  def defaultTri(t: TriState[Bool]): Unit = {
    t.read.default(False)
    t.write.default(False)
    t.writeEnable.default(False)
  }
  def defaultTriVec1(trivec1: Vec[TriState[Bool]]): Unit = trivec1.foreach { defaultTri(_) }
  def defaultTriVec2(trivec2: Vec[Vec[TriState[Bool]]]): Unit = trivec2.foreach { defaultTriVec1(_) }

  defaultTriVec1(key)
  defaultTriVec2(switch)
  defaultTriVec2(led)

  override def asMaster(): Unit = {
    key.foreach { t => master(t) }
    switch.foreach { p => p.foreach { t => master(t) } }
    led.foreach { p => p.foreach { t => master(t) } }
  }
}
