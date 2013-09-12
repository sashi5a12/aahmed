package com.generated.ws;

import javax.jws.WebService;

@WebService(
        serviceName = "employeeService",
        endpointInterface = "com.generated.ws.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee getEmployee(String employeeId) {
        System.out.println("--------- employeeId --------" + employeeId);
        Employee emp = new Employee();
        emp.setEmployeeId(employeeId);
        emp.setFirstName("John");
        emp.setLastName("Smith");

        return emp;
    }

    @Override
    public String getEmployeeAddress(String employeeId) {
        System.out.println("--------- Address employeeId --------" + employeeId);
        String address = "3943 W.Roundabout CIR, Chandler, Arizona, 85226";
        return address;
    }

    @Override
    public void deleteEmployee(String employeeId) {
        System.out.println("--------- Delete Employee --------" + employeeId);
    }

    @Override
    public String getEmployerInformation(String employeeId, String state) {
        String employer = "Chandler Bank, Chandler, Arizona, 85226";
        return employer;
    }
}
