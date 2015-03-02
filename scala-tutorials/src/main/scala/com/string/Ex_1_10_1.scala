package com.string

import StringUtils._;

object Ex_1_10_1 {
  def main(args: Array[String]){
    println("HAL".increment)
    
  }
  
  implicit class StringImprovements(s: String) {
    def increment = s.map(c => (c+1).toChar)
  }
}