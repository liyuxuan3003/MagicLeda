######## Makefile for SpinalHDL ########

# --------------------------------
# Get current dir
THIS_DIR?=$(patsubst %/,%,$(dir $(lastword ${MAKEFILE_LIST})))

# MagicLeda
LEDA_XDC?=${THIS_DIR}/MagicLeda.xdc
LEDA_SPINAL?=${THIS_DIR}/MagicLeda.scala
LEDA_PLATFORM?=xc7a35tcsg324-1
LEDA_FLASH?=n25q64-3.3v-spi-x1_x2_x4
