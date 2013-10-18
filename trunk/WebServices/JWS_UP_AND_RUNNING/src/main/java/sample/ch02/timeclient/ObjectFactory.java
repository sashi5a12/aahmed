
package sample.ch02.timeclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sample.ch02.timeclient package. 
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

    private final static QName _GetTimeAsLong_QNAME = new QName("http://ch02.sample/", "getTimeAsLong");
    private final static QName _GetTimeAsString_QNAME = new QName("http://ch02.sample/", "getTimeAsString");
    private final static QName _GetTimeAsLongResponse_QNAME = new QName("http://ch02.sample/", "getTimeAsLongResponse");
    private final static QName _GetTimeAsStringResponse_QNAME = new QName("http://ch02.sample/", "getTimeAsStringResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sample.ch02.timeclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTimeAsStringResponse }
     * 
     */
    public GetTimeAsStringResponse createGetTimeAsStringResponse() {
        return new GetTimeAsStringResponse();
    }

    /**
     * Create an instance of {@link GetTimeAsString }
     * 
     */
    public GetTimeAsString createGetTimeAsString() {
        return new GetTimeAsString();
    }

    /**
     * Create an instance of {@link GetTimeAsLongResponse }
     * 
     */
    public GetTimeAsLongResponse createGetTimeAsLongResponse() {
        return new GetTimeAsLongResponse();
    }

    /**
     * Create an instance of {@link GetTimeAsLong }
     * 
     */
    public GetTimeAsLong createGetTimeAsLong() {
        return new GetTimeAsLong();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeAsLong }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch02.sample/", name = "getTimeAsLong")
    public JAXBElement<GetTimeAsLong> createGetTimeAsLong(GetTimeAsLong value) {
        return new JAXBElement<GetTimeAsLong>(_GetTimeAsLong_QNAME, GetTimeAsLong.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeAsString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch02.sample/", name = "getTimeAsString")
    public JAXBElement<GetTimeAsString> createGetTimeAsString(GetTimeAsString value) {
        return new JAXBElement<GetTimeAsString>(_GetTimeAsString_QNAME, GetTimeAsString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeAsLongResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch02.sample/", name = "getTimeAsLongResponse")
    public JAXBElement<GetTimeAsLongResponse> createGetTimeAsLongResponse(GetTimeAsLongResponse value) {
        return new JAXBElement<GetTimeAsLongResponse>(_GetTimeAsLongResponse_QNAME, GetTimeAsLongResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeAsStringResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ch02.sample/", name = "getTimeAsStringResponse")
    public JAXBElement<GetTimeAsStringResponse> createGetTimeAsStringResponse(GetTimeAsStringResponse value) {
        return new JAXBElement<GetTimeAsStringResponse>(_GetTimeAsStringResponse_QNAME, GetTimeAsStringResponse.class, null, value);
    }

}
