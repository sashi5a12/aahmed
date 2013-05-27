package com.packtpub.ch11;

import org.hibernate.HibernateException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.packtpub.ch11.advice.NotificationThrowsAdvice;
import com.packtpub.ch11.notification.NotificationServiceImpl;
import com.packtpub.ch11.service.StudentService;
import com.packtpub.ch11.service.StudentServiceImpl;

public class Main {
	public static void  main(String[] args) throws Throwable{
		ApplicationContext ctx=new ClassPathXmlApplicationContext("/com/packtpub/ch11/applicationContext.xml");
		StudentService service=(StudentService)ctx.getBean("studentServiceTarget");
		System.out.println(service.getStudent(1L));
//		service.getStudent(1L);
	}
}
