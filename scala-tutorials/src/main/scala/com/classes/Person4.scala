package com.classes

case class Person4 (var name: String, var age: Int) {

  override def toString = s"$name is $age years old"
}

object Person4{
  def apply() = new Person4("No Name", 0)
  def apply(name:String)= new Person4(name,0);
}