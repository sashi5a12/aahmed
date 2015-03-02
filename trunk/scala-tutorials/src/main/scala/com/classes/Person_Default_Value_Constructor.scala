package com.classes

class Person_Default_Value_Constructor(var firstName:String ="Adnan", var lastName:String="Ahmed", var age:Int = 35) {

  override def toString = s"$firstName $lastName is $age years old"
}