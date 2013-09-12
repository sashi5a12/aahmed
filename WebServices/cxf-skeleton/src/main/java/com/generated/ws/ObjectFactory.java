
package com.generated.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.generated.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteEmployee_QNAME = new QName("http://ws.generated.com/", "deleteEmployee");
    private final static QName _EmployeeFault_QNAME = new QName("http://ws.generated.com/", "EmployeeFault");
    private final static QName _GetEmployee_QNAME = new QName("http://ws.generated.com/", "getEmployee");
    private final static QName _DeleteEmployeeResponse_QNAME = new QName("http://ws.generated.com/", "deleteEmployeeResponse");
    private final static QName _GetEmployerInformationResponse_QNAME = new QName("http://ws.generated.com/", "getEmployerInformationResponse");
    private final static QName _GetEmployeeAddress_QNAME = new QName("http://ws.generated.com/", "getEmployeeAddress");
    private final static QName _GetEmployeeResponse_QNAME = new QName("http://ws.generated.com/", "getEmployeeResponse");
    private final static QName _GetEmployerInformation_QNAME = new QName("http://ws.generated.com/", "getEmployerInformation");
    private final static QName _GetEmployeeAddressResponse_QNAME = new QName("http://ws.generated.com/", "getEmployeeAddressResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.generated.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEmployeeAddress }
     * 
     */
    public GetEmployeeAddress createGetEmployeeAddress() {
        return new GetEmployeeAddress();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link GetEmployerInformation }
     * 
     */
    public GetEmployerInformation createGetEmployerInformation() {
        return new GetEmployerInformation();
    }

    /**
     * Create an instance of {@link GetEmployerInformationResponse }
     * 
     */
    public GetEmployerInformationResponse createGetEmployerInformationResponse() {
        return new GetEmployerInformationResponse();
    }

    /**
     * Create an instance of {@link GetEmployeeResponse }
     * 
     */
    public GetEmployeeResponse createGetEmployeeResponse() {
        return new GetEmployeeResponse();
    }

    /**
     * Create an instance of {@link GetEmployee }
     * 
     */
    public GetEmployee createGetEmployee() {
        return new GetEmployee();
    }

    /**
     * Create an instance of {@link EmployeeFault }
     * 
     */
    public EmployeeFault createEmployeeFault() {
        return new EmployeeFault();
    }

    /**
     * Create an instance of {@link GetEmployeeAddressResponse }
     * 
     */
    public GetEmployeeAddressResponse createGetEmployeeAddressResponse() {
        return new GetEmployeeAddressResponse();
    }

    /**
     * Create an instance of {@link DeleteEmployee }
     * 
     */
    public DeleteEmployee createDeleteEmployee() {
        return new DeleteEmployee();
    }

    /**
     * Create an instance of {@link DeleteEmployeeResponse }
     * 
     */
    public DeleteEmployeeResponse createDeleteEmployeeResponse() {
        return new DeleteEmployeeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "deleteEmployee")
    public JAXBElement<DeleteEmployee> createDeleteEmployee(DeleteEmployee value) {
        return new JAXBElement<DeleteEmployee>(_DeleteEmployee_QNAME, DeleteEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmployeeFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "EmployeeFault")
    public JAXBElement<EmployeeFault> createEmployeeFault(EmployeeFault value) {
        return new JAXBElement<EmployeeFault>(_EmployeeFault_QNAME, EmployeeFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "getEmployee")
    public JAXBElement<GetEmployee> createGetEmployee(GetEmployee value) {
        return new JAXBElement<GetEmployee>(_GetEmployee_QNAME, GetEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "deleteEmployeeResponse")
    public JAXBElement<DeleteEmployeeResponse> createDeleteEmployeeResponse(DeleteEmployeeResponse value) {
        return new JAXBElement<DeleteEmployeeResponse>(_DeleteEmployeeResponse_QNAME, DeleteEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployerInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "getEmployerInformationResponse")
    public JAXBElement<GetEmployerInformationResponse> createGetEmployerInformationResponse(GetEmployerInformationResponse value) {
        return new JAXBElement<GetEmployerInformationResponse>(_GetEmployerInformationResponse_QNAME, GetEmployerInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "getEmployeeAddress")
    public JAXBElement<GetEmployeeAddress> createGetEmployeeAddress(GetEmployeeAddress value) {
        return new JAXBElement<GetEmployeeAddress>(_GetEmployeeAddress_QNAME, GetEmployeeAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "getEmployeeResponse")
    public JAXBElement<GetEmployeeResponse> createGetEmployeeResponse(GetEmployeeResponse value) {
        return new JAXBElement<GetEmployeeResponse>(_GetEmployeeResponse_QNAME, GetEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployerInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "getEmployerInformation")
    public JAXBElement<GetEmployerInformation> createGetEmployerInformation(GetEmployerInformation value) {
        return new JAXBElement<GetEmployerInformation>(_GetEmployerInformation_QNAME, GetEmployerInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeAddressResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.generated.com/", name = "getEmployeeAddressResponse")
    public JAXBElement<GetEmployeeAddressResponse> createGetEmployeeAddressResponse(GetEmployeeAddressResponse value) {
        return new JAXBElement<GetEmployeeAddressResponse>(_GetEmployeeAddressResponse_QNAME, GetEmployeeAddressResponse.class, null, value);
    }

}
