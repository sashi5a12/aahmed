package com.number

object Ex_2_1{

  
  def toInt(s: String) : Option[Int]={
    try{
      Some(s.toInt)
    }
    catch {
      case e: NumberFormatException => None
    }
  }
    
  
  def main(args:Array[String]){
      println(toInt("10").getOrElse("0"))
      
      toInt("100a") match {
        case Some(x) => println(x)
        case None => println("Not a number")
      }
      
      val result = toInt("100") match {
        case Some(x) => x
        case None => 0
      }
  }
}