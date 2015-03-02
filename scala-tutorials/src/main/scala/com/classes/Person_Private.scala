package com.classes

class Person_Private {

  var name:String = _
  
  private var age:Int=_
  
  def setAge(a: Int) = {this.age = a}
  
  def isHigher(that: Person_Private) = this.age > that.age
  
  override def toString = s"$name, $age"
}