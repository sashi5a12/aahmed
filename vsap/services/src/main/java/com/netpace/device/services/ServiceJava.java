package com.netpace.device.services;

public class ServiceJava implements org.activiti.engine.delegate.JavaDelegate {
	 
	@Override
	public void execute(org.activiti.engine.delegate.DelegateExecution arg0) throws Exception {
            int i = ((int)(Math.random()*100))%2;
            i++;
            arg0.setVariable("result",String.valueOf(i));
            System.out.println("Result : "+i);
	}

}
