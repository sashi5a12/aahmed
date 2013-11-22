
package com.ch06.ejb.persist.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ch06.ejb.persist.client package. 
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

    private final static QName _FibResponse_QNAME = new QName("http://persist.ejb.ch06.com/", "fibResponse");
    private final static QName _GetFibsResponse_QNAME = new QName("http://persist.ejb.ch06.com/", "getFibsResponse");
    private final static QName _Fib_QNAME = new QName("http://persist.ejb.ch06.com/", "fib");
    private final static QName _GetFibs_QNAME = new QName("http://persist.ejb.ch06.com/", "getFibs");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ch06.ejb.persist.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FibResponse }
     * 
     */
    public FibResponse createFibResponse() {
        return new FibResponse();
    }

    /**
     * Create an instance of {@link GetFibs }
     * 
     */
    public GetFibs createGetFibs() {
        return new GetFibs();
    }

    /**
     * Create an instance of {@link Fib_Type }
     * 
     */
    public Fib_Type createFib_Type() {
        return new Fib_Type();
    }

    /**
     * Create an instance of {@link GetFibsResponse }
     * 
     */
    public GetFibsResponse createGetFibsResponse() {
        return new GetFibsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FibResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://persist.ejb.ch06.com/", name = "fibResponse")
    public JAXBElement<FibResponse> createFibResponse(FibResponse value) {
        return new JAXBElement<FibResponse>(_FibResponse_QNAME, FibResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFibsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://persist.ejb.ch06.com/", name = "getFibsResponse")
    public JAXBElement<GetFibsResponse> createGetFibsResponse(GetFibsResponse value) {
        return new JAXBElement<GetFibsResponse>(_GetFibsResponse_QNAME, GetFibsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fib_Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://persist.ejb.ch06.com/", name = "fib")
    public JAXBElement<Fib_Type> createFib(Fib_Type value) {
        return new JAXBElement<Fib_Type>(_Fib_QNAME, Fib_Type.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFibs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://persist.ejb.ch06.com/", name = "getFibs")
    public JAXBElement<GetFibs> createGetFibs(GetFibs value) {
        return new JAXBElement<GetFibs>(_GetFibs_QNAME, GetFibs.class, null, value);
    }

}
