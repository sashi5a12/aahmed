package com.packtpub.ch03;

public class SetterInfoPrinterFactory {
	
	public static SetterInfoPrinter getSetterInfoPrinter(){
	       return new SetterInfoConsolePrinter();
	}
}
