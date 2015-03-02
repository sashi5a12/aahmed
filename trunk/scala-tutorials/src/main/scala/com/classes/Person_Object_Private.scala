package com.classes

class Person_Object_Private {

  var name:String = _
  
  private[this] var age:Int=_
  
  def setAge(a: Int) = {this.age = a}
  
  // error: this method won't compile because price is now object-private
  //def isHigher(other: Person_Object_Private) = this.age > other.age
  
  override def toString = s"$name, $age"
}