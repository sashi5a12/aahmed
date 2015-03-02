package com.classes

class Person_Private_Constructor private (var name: String, var age: Int){
  override def toString = s"$name is $age years old"
}

object Person_Private_Constructor {
  var person = new Person_Private_Constructor("Adnan Ahmed",30)
  
  def getInstance = person
}