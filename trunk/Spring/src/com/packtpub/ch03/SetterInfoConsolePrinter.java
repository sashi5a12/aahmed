package com.packtpub.ch03;

public class SetterInfoConsolePrinter implements ISetterInfoPrinter {
	public void print(String methodName, Object oldValue, Object newValue) {
		System.out.println("[Method=" + methodName + "|Old Value=" + oldValue + "|New Value=" + newValue + "]");
	}
}
