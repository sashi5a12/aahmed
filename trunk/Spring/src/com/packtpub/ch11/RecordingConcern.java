package com.packtpub.ch11;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.packtpub.ch03.ISetterInfoPrinter;
import com.packtpub.springhibernate.model.Student;

public class RecordingConcern implements MethodBeforeAdvice {
	private ISetterInfoPrinter printer;

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println(target.getClass());
		System.out.println(method.getName());
		if (method.getName().startsWith("set") && (target.getClass() == Student.class)) {
			String methodName = method.getName();
			Object newValue = args[0];
			Method getter = getterForSetter(Student.class, methodName);
//			Object oldValue = getter.invoke(target, args);
			printer.print(methodName, "", newValue);
		}
	}

	public Method getterForSetter(Class<Student> clazz, String methodName) {
		try {
			return clazz.getMethod(methodName.replaceFirst("set", "get"));
		} catch (NoSuchMethodException e) {
			;
		}
		return null;
	}

	public ISetterInfoPrinter getPrinter() {
		return printer;
	}

	public void setPrinter(ISetterInfoPrinter printer) {
		this.printer = printer;
	}

}
