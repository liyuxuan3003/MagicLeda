######## Makefile for SpinalHDL ########

# --------------------------------
# Get current dir
CURRENT_DIR?=$(patsubst %/,%,$(dir $(lastword ${MAKEFILE_LIST})))

# MagicLeda
LEDA_XDC?=${CURRENT_DIR}/MagicLeda.xdc
LEDA_PLATFORM?=xc7a35tcsg324-1
LEDA_FLASH?=n25q64-3.3v-spi-x1_x2_x4