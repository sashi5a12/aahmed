package com.sample.ch08;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class EmployeeTestClient {
    public static void main(String arg[]) throws MalformedURLException, Exception{
        //Location of the wsdl
        URL url = new URL("http://localhost:9090/cxf/services/employee?wsdl");
        
        /*
         * 1st ags is service uri - maps to targetNamespace in wsdl, 
         * 2nd arg is service name - maps to <wsdl:service/> name element.
         */
        QName qname = new QName("http://ch08.sample.com/", "employeeService");
        
        Service service=Service.create(url, qname);
        
        //get the service port - maps to <wsdl:portType/> in wsdl
        EmployeeService employeeService=service.getPort(EmployeeService.class);
        
        //invoke the service endpoint methods.
        String address = employeeService.getEmployeeAddressInfo("11");
        
        Employee emp = employeeService.getEmployee("123");
        System.out.println("Employee Id: "+emp.getEmployeeId()+" first name: "+emp.getFirstName());
    }
}
