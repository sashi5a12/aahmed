package com.control_structure

import scala.annotation.switch

object Ex_3_7 {
  case class Person()

  def main(args: Array[String]) {
    var i = 1
    (i: @switch) match {
      case 1 => println("1")
      case 2 => println("2")
      case _ => println("other");
    }
  }

  def getClassAsString(x: Any): String = x match {
    case s: String => s + "is String"
    case i: Int => "Int"
    case f: Float => "Float"
    case l: List[_] => "List"
    case p: Person => "Person"
    case _ => "Unknown"
  }

}