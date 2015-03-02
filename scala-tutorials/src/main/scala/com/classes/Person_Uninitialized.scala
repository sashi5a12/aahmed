package com.classes

case class Address(city: String, state: String, zip: String) 

case class Person_Uninitialized(var userName: String, var password:String) {

  var firstName =""
  var lastName = ""
  var age =0
  
  var address = None : Option[Address]
  
}