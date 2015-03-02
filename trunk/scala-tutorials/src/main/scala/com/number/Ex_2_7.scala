package com.number

object Ex_2_7 {
  def main(args: Array[String]){
    val rand = new scala.util.Random(1000)
    
    println(rand.nextInt());
    println(rand.nextInt(100))
    val range = 0 to rand.nextInt(10);
    
    println(range);
    
    val range2=for (i <- 0 to rand.nextInt(10)) yield i * 2
    
    println(range2)
    

  }
}