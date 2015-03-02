package com.control_structure

trait Command
case object Start extends Command
case object Go extends Command
case object Stop extends Command
case object Whoa extends Command
object Test {
  def main(args: Array[String]){
   
    def start = println("start")
    def stop = println("stop")
    
    def executeCommand(cmd: Command)=cmd match{
      case Start | Go => start
      case Stop | Whoa => stop  
    }
    
    val sta = Whoa;
    
    executeCommand(sta);
    
    
    def toInt (s: String) : Option[Int] = {
      try {
        Some(s.toInt)
      }
      catch {
        case e: Exception => None
      }
    }
    
    toInt("10") match {
      case Some(x) => println(x)
      case None => println("not a number")
    }
  }
}