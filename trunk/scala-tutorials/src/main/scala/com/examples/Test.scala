package com.examples

object Test {
  def main(args: Array[String]) {
    val array = Array.ofDim[Int](2, 2)
    array(0)(0) = 0
    array(0)(1) = 1
    array(1)(0) = 2
    array(1)(1) = 3

    for {
      i <- 0 until array.length
      j <- 0 until array(i).length
    } println(s"($i)($j) = ${array(i)(j)}")
  }
}