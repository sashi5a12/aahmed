package com.packtpub.ch03;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.packtpub.springhibernate.model.Student;

public class Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/packtpub/ch03/application-context.xml");
		Student student = (Student) ctx.getBean("student");
		student.setStudentId(41);
		student.setFirstName("John");
		student.setLastName("White");
	}
}