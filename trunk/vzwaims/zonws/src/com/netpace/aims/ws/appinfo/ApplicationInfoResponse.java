
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
 *         &lt;element name="VendorID" type="{http://www.vzwdeveloper.com/vds/application/}VendorId_Type"/>
 *         &lt;element name="RingNumber" type="{http://www.vzwdeveloper.com/vds/application/}RingNumber_Type"/>
 *         &lt;element name="ContractRefId" type="{http://www.vzwdeveloper.com/vds/application/}ContractRefId_Type"/>
 *         &lt;element name="ApplicationRefId" type="{http://www.vzwdeveloper.com/vds/application/}ApplicationRefId_Type"/>
 *         &lt;element name="ApplicationName" type="{http://www.vzwdeveloper.com/vds/application/}ApplicationName_Type"/>
 *         &lt;element name="ApplicationShortDescription" type="{http://www.vzwdeveloper.com/vds/application/}ApplicationShortDescription_Type"/>
 *         &lt;element name="ApplicationLongDescription" type="{http://www.vzwdeveloper.com/vds/application/}ApplicationLongDescription_Type"/>
 *         &lt;element name="ContentRating" type="{http://www.vzwdeveloper.com/vds/application/}ContentRating_Type"/>
 *         &lt;element name="TaxCategory" type="{http://www.vzwdeveloper.com/vds/application/}TaxCategory_Type"/>
 *         &lt;element name="ContentType" type="{http://www.vzwdeveloper.com/vds/application/}ContentType_Type"/>
 *         &lt;element name="EnterpriseFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EnterpriseId" type="{http://www.vzwdeveloper.com/vds/application/}EnterpriseId_Type" minOccurs="0"/>
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
    "vendorID",
    "ringNumber",
    "contractRefId",
    "applicationRefId",
    "applicationName",
    "applicationShortDescription",
    "applicationLongDescription",
    "contentRating",
    "taxCategory",
    "contentType",
    "enterpriseFlag",
    "enterpriseId"
})
@XmlRootElement(name = "ApplicationInfoResponse")
public class ApplicationInfoResponse {

    @XmlElement(name = "VendorID")
    protected long vendorID;
    @XmlElement(name = "RingNumber")
    protected int ringNumber;
    @XmlElement(name = "ContractRefId")
    protected long contractRefId;
    @XmlElement(name = "ApplicationRefId")
    protected long applicationRefId;
    @XmlElement(name = "ApplicationName", required = true)
    protected String applicationName;
    @XmlElement(name = "ApplicationShortDescription", required = true)
    protected String applicationShortDescription;
    @XmlElement(name = "ApplicationLongDescription", required = true)
    protected String applicationLongDescription;
    @XmlElement(name = "ContentRating", required = true)
    protected String contentRating;
    @XmlElement(name = "TaxCategory", required = true)
    protected String taxCategory;
    @XmlElement(name = "ContentType", required = true)
    protected ContentTypeType contentType;
    @XmlElement(name = "EnterpriseFlag")
    protected boolean enterpriseFlag;
    @XmlElement(name = "EnterpriseId")
    protected String enterpriseId;

    /**
     * Gets the value of the vendorID property.
     * 
     */
    public long getVendorID() {
        return vendorID;
    }

    /**
     * Sets the value of the vendorID property.
     * 
     */
    public void setVendorID(long value) {
        this.vendorID = value;
    }

    /**
     * Gets the value of the ringNumber property.
     * 
     */
    public int getRingNumber() {
        return ringNumber;
    }

    /**
     * Sets the value of the ringNumber property.
     * 
     */
    public void setRingNumber(int value) {
        this.ringNumber = value;
    }

    /**
     * Gets the value of the contractRefId property.
     * 
     */
    public long getContractRefId() {
        return contractRefId;
    }

    /**
     * Sets the value of the contractRefId property.
     * 
     */
    public void setContractRefId(long value) {
        this.contractRefId = value;
    }

    /**
     * Gets the value of the applicationRefId property.
     * 
     */
    public long getApplicationRefId() {
        return applicationRefId;
    }

    /**
     * Sets the value of the applicationRefId property.
     * 
     */
    public void setApplicationRefId(long value) {
        this.applicationRefId = value;
    }

    /**
     * Gets the value of the applicationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Sets the value of the applicationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationName(String value) {
        this.applicationName = value;
    }

    /**
     * Gets the value of the applicationShortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationShortDescription() {
        return applicationShortDescription;
    }

    /**
     * Sets the value of the applicationShortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationShortDescription(String value) {
        this.applicationShortDescription = value;
    }

    /**
     * Gets the value of the applicationLongDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationLongDescription() {
        return applicationLongDescription;
    }

    /**
     * Sets the value of the applicationLongDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationLongDescription(String value) {
        this.applicationLongDescription = value;
    }

    /**
     * Gets the value of the contentRating property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentRating() {
        return contentRating;
    }

    /**
     * Sets the value of the contentRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentRating(String value) {
        this.contentRating = value;
    }

    /**
     * Gets the value of the taxCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxCategory() {
        return taxCategory;
    }

    /**
     * Sets the value of the taxCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxCategory(String value) {
        this.taxCategory = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link ContentTypeType }
     *     
     */
    public ContentTypeType getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentTypeType }
     *     
     */
    public void setContentType(ContentTypeType value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the enterpriseFlag property.
     * 
     */
    public boolean isEnterpriseFlag() {
        return enterpriseFlag;
    }

    /**
     * Sets the value of the enterpriseFlag property.
     * 
     */
    public void setEnterpriseFlag(boolean value) {
        this.enterpriseFlag = value;
    }

    /**
     * Gets the value of the enterpriseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * Sets the value of the enterpriseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterpriseId(String value) {
        this.enterpriseId = value;
    }
    
    /**
     * toString method
     */
    public String toString(){
    	return  new StringBuffer(super.toString())
    		.append("\n\t{ ").append("vendorID=").append(vendorID)
    		.append(", ").append("ringNumber=").append(ringNumber)
    		.append(", ").append("contractRefId=").append(contractRefId)
    		.append(", ").append("applicationRefId=").append(applicationRefId)
    		.append(", ").append("applicationName=").append(applicationName)
    		.append(", ").append("applicationShortDescription=").append(applicationShortDescription)
    		.append(", ").append("applicationLongDescription=").append(applicationLongDescription)
    		.append(", ").append("contentRating=").append(contentRating)
    		.append(", ").append("taxCategory=").append(taxCategory)
    		.append(", ").append("contentType=").append(contentType)
    		.append(", ").append("enterpriseFlag=").append(enterpriseFlag)
    		.append(", ").append("enterpriseId=").append(enterpriseId)
    		.append(" }")
    		.toString();
    }

}
