package com.netpace.aims.util;

import java.util.UUID;

public class JavaUtils {

	public static String getAppKeyword() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	public static void main(String[] args){
		System.out.println("JavaUtils.getAppKeyword()1 ="+getAppKeyword());
		System.out.println("JavaUtils.getAppKeyword()2 ="+getAppKeyword());
		System.out.println("JavaUtils.getAppKeyword()3 ="+getAppKeyword());
		System.out.println("JavaUtils.getAppKeyword()4 ="+getAppKeyword());
	}
}
