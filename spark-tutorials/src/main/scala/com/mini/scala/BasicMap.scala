/**
 * Illustrates a simple map in Scala
 */
package com.mini.scala

import org.apache.spark._

object BasicMap {
  def main(args: Array[String]) {
    val master = args.length match {
      case x: Int if x > 0 => args(0)
      case _ => "local"
    }
    val sc = new SparkContext(master, "BasicMap", System.getenv("SPARK_HOME"))
    val input = sc.parallelize(List(1, 2, 3, 4))
    val result = input.map(x => x * x)
    println(result.collect().mkString(","))
  }
}

//%SPARK_HOME%\bin\spark-submit --class com.mini.scala.BasicMap .\target\scala-2.10\spark-tutorials_2.10-1.0.jar
//sbt "run-main com.mini.scala.BasicMap"