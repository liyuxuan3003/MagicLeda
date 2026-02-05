package magicleda

import spinal.core._
import spinal.lib._
import spinal.lib.io.TriState

case class MagicLeda() extends Bundle with IMasterSlave {
  val key = Vec(master(TriState(Bool())), 8)
  val switch = Vec(Vec(master(TriState(Bool())), 8), 2)
  val led = Vec(Vec(master(TriState(Bool())), 8), 2)

  key.foreach { t =>
    t.write.default(False)
    t.writeEnable.default(False)
  }

  switch.foreach { p =>
    p.foreach { t =>
      t.write.default(False)
      t.writeEnable.default(False)
    }
  }

  led.foreach { p =>
    p.foreach { t =>
      t.write.default(False)
      t.writeEnable.default(False)
    }
  }

  override def asMaster(): Unit = {}
}
