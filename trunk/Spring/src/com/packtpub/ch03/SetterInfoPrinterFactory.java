package com.packtpub.ch03;

public class SetterInfoPrinterFactory {
	
	public static ISetterInfoPrinter getSetterInfoPrinter(){
	       return new SetterInfoConsolePrinter();
	}
}
