package com.sample.ch08;

import javax.jws.WebParam.Mode;
import javax.jws.*;

@WebService
public interface EmployeeService {

    @WebMethod(operationName="getEmployee")
    Employee getEmployee(@WebParam (name="employeeId") String employeeId) throws EmployeeFault;

    
    @WebMethod(operationName="getEmployeeAddress")
    String getEmployeeAddressInfo(@WebParam(name="empId", mode= Mode.IN) String employeeId) throws Exception;

    String getEmployerInformation(@WebParam(name="empId", mode= Mode.IN) String employeeId, @WebParam(name="state", mode= Mode.IN) String state) throws Exception;

    @WebMethod(operationName="deleteEmployee")
    void deleteEmployee(@WebParam (name="employeeId") String employeeId);

}