package com.string

object StringUtils {
  implicit class StringImprovements(s: String){
    def increment: String = s.map(c => (c+1).toChar);
    def decrement: String = s.map(c => (c-1).toChar)
    def hideAll: String = s.replaceAll(".", "*")
    def plusOne: Int = s.toInt + 1
    def asBoolean: Boolean = s match {
      case "0" | "zero" | "" | " " => false
      case _ =>true
    }
  }
}