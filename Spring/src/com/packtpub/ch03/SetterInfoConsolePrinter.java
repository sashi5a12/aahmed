package com.packtpub.ch03;

public class SetterInfoConsolePrinter implements SetterInfoPrinter {
	public void print(String methodName, Object oldValue, Object newValue) {
		System.out.println("[Method=" + methodName + "|Old Value=" + oldValue + "|New Value=" + newValue + "]");
	}
}
