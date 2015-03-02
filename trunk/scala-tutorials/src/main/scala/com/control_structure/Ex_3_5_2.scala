package com.control_structure

import scala.util.control._

object Ex_3_5_2 {

  def main(args: Array[String]) {
    val inner = new Breaks
    val outer = new Breaks

    outer.breakable {
      for (i <- 1 to 5) {
        inner.breakable {
          for (j <- 'a' to 'e') {
            if (i == 1 && j == 'c') inner.break else println(s"i: $i, j: $j")
            if (i == 2 && j == 'b') outer.break
          }
        }
      }
    }
  }
}