#!/bin/sh

export CROSS_BIN=$1
export PATH=$CROSS_BIN:$PATH
echo $PATH
which gcc
which ld

cd make
ant all -DnoX11=true -Drootrel.build=$2 -Dantlr.jar=$3 -Dbuild.noarchives=true -D$4=$5 -Djogl.home=$6 -Dgluegen.compiler.import=$7
