package com.classes

import com.classes._

object Test {
	val p = new Person("Adnan", "Ahmed")      //> the constructor begins
                                                  //| /Users/aahmed
                                                  //| Adnan Ahmed is 0 years old
                                                  //| still in constructor
                                                  //| p  : com.classes.Person = Adnan Ahmed is 0 years old
                                                  
	p.firstName = "ABC"
	p.lastName = "XYZ"
	p.age=30
	
	println(p)                                //> ABC XYZ is 30 years old
	
	val p2 = new Person2("Adnan Ahmed")       //> p2  : com.classes.Person2 = com.classes.Person2@35f3198f
	p2.getName                                //> Adnan Ahmed
	
	val pizza1 = new Pizza                    //> pizza1  : com.classes.Pizza = A 12 inch pizza with a Thin crust
	val pizza2 = new Pizza(Pizza.DEFAULT_CRUST_SIZE)
                                                  //> pizza2  : com.classes.Pizza = A 12 inch pizza with a Thin crust
                                                  
  val p4 = Person4()                              //> p4  : com.classes.Person4 = No Name is 0 years old
  val b = Person4("Pam")                          //> b  : com.classes.Person4 = Pam is 0 years old
  b.name                                          //> res0: String = Pam
  
  val p10 = Person_Private_Constructor.getInstance//> p10  : com.classes.Person_Private_Constructor = Adnan Ahmed is 30 years old
  
  val p101 = new Person_Default_Value_Constructor //> p101  : com.classes.Person_Default_Value_Constructor = Adnan Ahmed is 35 yea
                                                  //| rs old
  var p102 = new Person_Default_Value_Constructor("ABC XYZ")
                                                  //> p102  : com.classes.Person_Default_Value_Constructor = ABC XYZ Ahmed is 35 y
                                                  //| ears old
                                                  
  var p103 = new Person_Default_Value_Constructor(age=40)
                                                  //> p103  : com.classes.Person_Default_Value_Constructor = Adnan Ahmed is 40 yea
                                                  //| rs old

  var p104 = new Person_Default_Value_Constructor(firstName="XYZ",lastName="ABC", 40)
                                                  //> p104  : com.classes.Person_Default_Value_Constructor = XYZ ABC is 40 years o
                                                  //| ld
                                                  
	val p105 = new Person_Private             //> p105  : com.classes.Person_Private = null, 0
	p105.name="adnan ahmed"
	p105.setAge(30)
	
	val p106 = new Person_Private             //> p106  : com.classes.Person_Private = null, 0
	p106.name="ABC XYZ"
	p106.setAge(40)
	
	p105.isHigher(p106)                       //> res1: Boolean = false
	

}