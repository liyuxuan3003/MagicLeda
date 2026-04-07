# MagicLeda

MagicLeda的项目名称来自木卫十三Leda，所有FPGA板卡的引脚定义项目，均采用木星卫星来命名。

MagicLeda适用于EGo1板卡（依元素科技），搭载芯片型号为XC7A35T-1CSG324C。

https://e-elements.readthedocs.io/zh/ego1_v2.2/EGo11.html

FPGA型号：xc7a35tcsg324-1

Flash型号：n25q64-3.3v-spi-x1_x2_x4

目前仅提供了对板上时钟，复位，两组8个开关，两组8个LED灯，四个按钮（中央按钮是复位）的支持。

## 引入方式
MagicLeda应当搭配MakefileSpinal一起使用。

MagicLeda应当以Git子模块的方式引入Spinal项目中，假设Spinal项目结构如下
```
SpinalSulfurTemplate [Your Repo Root]
|- .git
|- .gitignore
|- .scalafmt.conf
|- README.md
|- SpinalSulfur
    |- magicleda [Add Submodule here]
        |- magicleda.mk
        |- MagicLeda.scala
        |- MagicLeda.xdc
        |- README.md
    |- TopLevel.scala
    |- Config.scala
    |- build.sbt
    |- Makefile
```

那么，该子模块应当位于`SpinalSulfur/magicleda`处，输入以下命令
```
git submodule add --name "MagicLeda" -b github git@github.com:liyuxuan3003/MagicLeda.git SpinalSulfur/magicleda
```

同时，你应当在你的Makefile中写入以下内容
```
PROJECT:=SpinalSulfur

TOP:=TopLevel

include magicleda/magicleda.mk

SRCS_XDC_EXTRA:=${LEDA_XDC}
SRCS_SPINAL_EXTRA:=${LEDA_SPINAL}
PLATFORM:=${LEDA_PLATFORM}
FLASH:=${LEDA_FLASH}

include makefile-spinal/makefile-spinal.mk
```

`${PLATFORM}`设定了FPGA的型号（xc7a35tcsg324-1）。

`${FLASH}`设定了Flash的型号（n25q64-3.3v-spi-x1_x2_x4）。

`${SRCS_XDC_EXTRA}`和`${SRCS_SPINAL_EXTRA}`会将该模块中的Spinal代码和Xdc引脚约束文件添加到对应变量中。

## 使用方式
MagicLeda要求你的`TopLevel`是一个额外的顶层，用于包裹真正的顶层模块，并将其逻辑IO（乘法器的输入输出）和板上元件的IO（板上的开关和LED灯）连接在一起。

MagicLeda要求你的`TopLevel`的`io`中有且仅能有一项名为`m`且值为`master(magicleda.MagicLeda())`的变量，请注意，名称必须是`m`，因为`MagicLeda.xdc`对接口和引脚的连接是基于这一假设编写的。随后，你可以在接下来的代码实例化你真正的顶层模块，比如`MyTop`并实例化为`top`，并将`top`的IO接口和`io.m`中提供的对应实际板上器件的IO接口连接在一起。
```
case class TopLevel() extends Component {
  val io = new Bundle {
    val m = master(magicleda.MagicLeda())
  }

  val top = MyTop()
  // Connect io.m to io port of your top "MyTop()" here
}
```

MagicLeda目前提供了以下接口（通过`io.m.`访问）
```
val key = Bits(4 bits)
val switch = Vec(Bits(8 bits), 2)
val led = Vec(Bits(8 bits), 2)
val digit = Vec(DigitPin(), 2)
val uart = UartPin()
val pin = Analog(Bits(32 bits))

case class DigitPin() extends Bundle {
  val seg = Bits(8 bits)
  val sel = Bits(4 bits)
}

case class UartPin() extends Bundle {
  val tx = Bool()
  val rx = Bool()
}

case class SpiPin() extends Bundle with IMasterSlave {
  val sclk = Bool()
  val miso = Bool()
  val mosi = Bool()
}
```
`key`的`0,1,2,3`的顺序是：右、下、左、上。中间按钮被用于复位信号。

`switch`和`led`中，`0`对应右侧较小的开关和LED灯，`1`对应左侧较大的开关和LED灯。

`digit`中，`0`对应右侧的4个数码管，`1`对应左侧的4个数码管。

`digit`下有`seg`和`sel`分别对应段选和位选信号，两者都是高电平有效不需要反转。
- `seg(7 downto 0)`对应的段码，从高位到低位依次是`dp, a, b, c, d, e, f, g`。
- `sel(3 downto 0)`对应的位码，从高位到低位依次控制从左到右的数码管。

`uart`中，`tx`是FPGA的发送端，`rx`是FPGA的接收端。

`pin`是板卡左侧的扩展引脚，顺序是：从右至左，从下到上。特别注意最上面两行是电源和地。

## 板卡操作流程
当使用`make load`时，JP3应当在加载期间被短接。

当使用`make burn`时，JP3应当在烧录期间被短接，随后，关闭电源开关，将JP2的QSPI一侧短接，打开电源开关，稍等片刻，FPGA就会从Flash启动。
