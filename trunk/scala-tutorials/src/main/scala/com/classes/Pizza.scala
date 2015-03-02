package com.classes

class Pizza(var crustSize: Int, var crustType: String) {

  def this(crustSize: Int) {
    this(crustSize, Pizza.DEFAULT_CRUST_TYPE)
  }
  
  def this(crustType: String){
    this(Pizza.DEFAULT_CRUST_SIZE, crustType)
  }
  
  def this(){
    this(Pizza.DEFAULT_CRUST_SIZE, Pizza.DEFAULT_CRUST_TYPE)
  }
  override def toString = s"A $crustSize inch pizza with a $crustType crust"
}

object Pizza {
  val DEFAULT_CRUST_SIZE = 12
  val DEFAULT_CRUST_TYPE = "Thin"
}