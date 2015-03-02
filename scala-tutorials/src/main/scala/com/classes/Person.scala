package com.classes

class Person(var firstName: String, var lastName:String) {

  println ("the constructor begins")
  
  //class fields
  var age = 0
  private val HOME = System.getProperty("user.home")
  
  //some methods
  override def toString = s"$firstName $lastName is $age years old"
  def printHome = {println(HOME)}
  def printFullName = {println(this)}
  
  printHome
  printFullName
  
  println("still in constructor")
}