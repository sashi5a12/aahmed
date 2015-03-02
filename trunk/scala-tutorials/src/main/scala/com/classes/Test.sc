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
	
	val p2 = new Person2("Adnan Ahmed")       //> p2  : com.classes.Person2 = com.classes.Person2@642a590d
	p2.getName                                //> Adnan Ahmed
	
	val p3 = Person3("Adnan Ahmed")           //> p3  : com.classes.Person3 = Person3(Adnan Ahmed)
	println(p3.name)                          //> Adnan Ahmed
}