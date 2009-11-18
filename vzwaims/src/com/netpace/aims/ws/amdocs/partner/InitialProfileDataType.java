//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.08.07 at 08:38:24 AM VET 
//


package com.netpace.aims.ws.amdocs.partner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InitialProfileDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitialProfileDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="registrantFirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registrantLastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registrantUserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registrantEmailId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="securityQuestion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="securityAnswer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="companyReferenceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productsDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="termsAndConditions" type="{http://api.qpass.com/cponboarding}TermsAndConditionsType"/>
 *         &lt;element name="companyUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitialProfileDataType", propOrder = {
    "registrantFirstName",
    "registrantLastName",
    "registrantUserName",
    "registrantEmailId",
    "password",
    "securityQuestion",
    "securityAnswer",
    "companyName",
    "companyReferenceName",
    "productsDescription",
    "termsAndConditions",
    "companyUrl"
})
public class InitialProfileDataType {

    @XmlElement(required = true)
    protected String registrantFirstName;
    @XmlElement(required = true)
    protected String registrantLastName;
    @XmlElement(required = true)
    protected String registrantUserName;
    @XmlElement(required = true)
    protected String registrantEmailId;
    @XmlElement(required = true)
    protected String password;
    @XmlElement(required = true)
    protected String securityQuestion;
    @XmlElement(required = true)
    protected String securityAnswer;
    @XmlElement(required = true)
    protected String companyName;
    @XmlElement(required = true)
    protected String companyReferenceName;
    protected String productsDescription;
    @XmlElement(required = true)
    protected TermsAndConditionsType termsAndConditions;
    @XmlElement(required = true)
    protected String companyUrl;

    /**
     * Gets the value of the registrantFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrantFirstName() {
        return registrantFirstName;
    }

    /**
     * Sets the value of the registrantFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrantFirstName(String value) {
        this.registrantFirstName = value;
    }

    /**
     * Gets the value of the registrantLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrantLastName() {
        return registrantLastName;
    }

    /**
     * Sets the value of the registrantLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrantLastName(String value) {
        this.registrantLastName = value;
    }

    /**
     * Gets the value of the registrantUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrantUserName() {
        return registrantUserName;
    }

    /**
     * Sets the value of the registrantUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrantUserName(String value) {
        this.registrantUserName = value;
    }

    /**
     * Gets the value of the registrantEmailId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrantEmailId() {
        return registrantEmailId;
    }

    /**
     * Sets the value of the registrantEmailId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrantEmailId(String value) {
        this.registrantEmailId = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the securityQuestion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Sets the value of the securityQuestion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityQuestion(String value) {
        this.securityQuestion = value;
    }

    /**
     * Gets the value of the securityAnswer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * Sets the value of the securityAnswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityAnswer(String value) {
        this.securityAnswer = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the companyReferenceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyReferenceName() {
        return companyReferenceName;
    }

    /**
     * Sets the value of the companyReferenceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyReferenceName(String value) {
        this.companyReferenceName = value;
    }

    /**
     * Gets the value of the productsDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductsDescription() {
        return productsDescription;
    }

    /**
     * Sets the value of the productsDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductsDescription(String value) {
        this.productsDescription = value;
    }

    /**
     * Gets the value of the termsAndConditions property.
     * 
     * @return
     *     possible object is
     *     {@link TermsAndConditionsType }
     *     
     */
    public TermsAndConditionsType getTermsAndConditions() {
        return termsAndConditions;
    }

    /**
     * Sets the value of the termsAndConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsAndConditionsType }
     *     
     */
    public void setTermsAndConditions(TermsAndConditionsType value) {
        this.termsAndConditions = value;
    }

    /**
     * Gets the value of the companyUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyUrl() {
        return companyUrl;
    }

    /**
     * Sets the value of the companyUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyUrl(String value) {
        this.companyUrl = value;
    }

}
