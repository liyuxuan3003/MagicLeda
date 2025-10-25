######## Makefile for SpinalHDL ########

# --------------------------------
# Get current dir
CURRENT_DIR?=$(patsubst %/,%,$(dir $(lastword ${MAKEFILE_LIST})))

# MagicLeda
LEDA_XDC?=${CURRENT_DIR}/MagicLeda.xdc
LEDA_PLATFORM?=xc7a35tcsg324-1
