package com.generated.ws;

import javax.xml.ws.BindingProvider;

public class Client {
    public static void main(String args[]) throws EmployeeFault_Exception{
        
        //create the client stub using tool generated classes.
        EmployeeService_Service service = new EmployeeService_Service();
        EmployeeService stub=service.getEmployeeServiceImplPort();
        
        //service endpoint url
        String target="http://localhost:9090/cxf/services/employee2";
        BindingProvider provider = (BindingProvider)stub;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, target);
        
        Employee emp = stub.getEmployee("111");
        stub.getEmployeeAddress("123");
    }
}
