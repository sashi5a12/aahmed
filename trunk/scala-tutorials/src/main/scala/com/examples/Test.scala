package com.examples

import util.control.Breaks._

object Test {
  def main(args: Array[String]) {

    breakable {
      for (i <- 0 to 10) {
        if (i > 5) break
        else println(i)
      }
    }
    
    println("=================")
    for (i <- 0 to 10){
      breakable {
        if (i ==0 || i==5 || i==10) break
        else println(i)
      }
    }
  }
}

