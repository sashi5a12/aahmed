package com.packtpub.ch11;

import org.springframework.aop.framework.ProxyFactory;

import com.packtpub.ch03.ISetterInfoPrinter;
import com.packtpub.ch03.SetterInfoConsolePrinter;
import com.packtpub.springhibernate.model.Student;

public class StudentFactory {
	public static Student getStudent(){
		Student student=new Student();
		
		//Create and configure the advice object
		RecordingConcern advice=new RecordingConcern();
		ISetterInfoPrinter printer = new SetterInfoConsolePrinter();
	    advice.setPrinter(printer);
	    
	    //create proxy
	    ProxyFactory pf=new ProxyFactory();
	    
	    //introduce target & advice to proxy
	    pf.addAdvice(advice);
	    pf.setTarget(student);
	    
	    //use proxy object instead of actual target object
	    Student proxy=(Student)pf.getProxy();
	    
	    return proxy;
	}
	
	public static void main(String args[]){
		Student stud=StudentFactory.getStudent();
		
		stud.setStudentId(1);
		stud.setFirstName("Adnan");
		stud.setLastName("Last");
	}
}
