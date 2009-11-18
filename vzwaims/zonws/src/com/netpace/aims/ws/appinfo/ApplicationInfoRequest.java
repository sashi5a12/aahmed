
package com.netpace.aims.ws.appinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApplicationKeyword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "applicationKeyword"
})
@XmlRootElement(name = "ApplicationInfoRequest")
public class ApplicationInfoRequest {

    @XmlElement(name = "ApplicationKeyword", required = true)
    protected String applicationKeyword;

    /**
     * Gets the value of the applicationKeyword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationKeyword() {
        return applicationKeyword;
    }

    /**
     * Sets the value of the applicationKeyword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationKeyword(String value) {
        this.applicationKeyword = value;
    }
    
    public String toString(){
    	return  new StringBuffer(super.toString())
    		.append("\n\t{ ").append("applicationKeyword=").append(applicationKeyword)
    		.append(" }")
    		.toString();
    }

}
