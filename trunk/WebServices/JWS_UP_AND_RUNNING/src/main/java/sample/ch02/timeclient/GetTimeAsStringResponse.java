
package sample.ch02.timeclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getTimeAsStringResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="getTimeAsStringResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="return" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTimeAsStringResponse", propOrder = {"_return"})
public class GetTimeAsStringResponse {

    @XmlElement(name = "return")
    protected String _return;

    public String getReturn() {
        return _return;
    }

    public void setReturn(String value) {
        this._return = value;
    }

}
