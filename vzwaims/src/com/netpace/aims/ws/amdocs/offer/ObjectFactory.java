//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.07.30 at 12:21:59 PM PKT 
//


package com.netpace.aims.ws.amdocs.offer;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.netpace.aims.ws.amdocs.offer package. 
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

    private final static QName _ProductOffer_QNAME = new QName("http://api.qpass.com/productoffer", "ProductOffer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.netpace.aims.ws.amdocs.offer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProductOfferTermDataType }
     * 
     */
    public ProductOfferTermDataType createProductOfferTermDataType() {
        return new ProductOfferTermDataType();
    }

    /**
     * Create an instance of {@link ProductOfferDataType }
     * 
     */
    public ProductOfferDataType createProductOfferDataType() {
        return new ProductOfferDataType();
    }

    /**
     * Create an instance of {@link ConstraintDataType }
     * 
     */
    public ConstraintDataType createConstraintDataType() {
        return new ConstraintDataType();
    }

    /**
     * Create an instance of {@link OfferPackagingOptionsDataType }
     * 
     */
    public OfferPackagingOptionsDataType createOfferPackagingOptionsDataType() {
        return new OfferPackagingOptionsDataType();
    }

    /**
     * Create an instance of {@link OfferCategoryEnumDataType }
     * 
     */
    public OfferCategoryEnumDataType createOfferCategoryEnumDataType() {
        return new OfferCategoryEnumDataType();
    }

    /**
     * Create an instance of {@link ErrorsType }
     * 
     */
    public ErrorsType createErrorsType() {
        return new ErrorsType();
    }

    /**
     * Create an instance of {@link CompositionDataType }
     * 
     */
    public CompositionDataType createCompositionDataType() {
        return new CompositionDataType();
    }

    /**
     * Create an instance of {@link PriceSchemeDataType }
     * 
     */
    public PriceSchemeDataType createPriceSchemeDataType() {
        return new PriceSchemeDataType();
    }

    /**
     * Create an instance of {@link PaymentPlanDataType }
     * 
     */
    public PaymentPlanDataType createPaymentPlanDataType() {
        return new PaymentPlanDataType();
    }

    /**
     * Create an instance of {@link ErrorType }
     * 
     */
    public ErrorType createErrorType() {
        return new ErrorType();
    }

    /**
     * Create an instance of {@link ProductOfferType }
     * 
     */
    public ProductOfferType createProductOfferType() {
        return new ProductOfferType();
    }

    /**
     * Create an instance of {@link TaxSchemaDataType }
     * 
     */
    public TaxSchemaDataType createTaxSchemaDataType() {
        return new TaxSchemaDataType();
    }

    /**
     * Create an instance of {@link RequirementDataType }
     * 
     */
    public RequirementDataType createRequirementDataType() {
        return new RequirementDataType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductOfferType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.qpass.com/productoffer", name = "ProductOffer")
    public JAXBElement<ProductOfferType> createProductOffer(ProductOfferType value) {
        return new JAXBElement<ProductOfferType>(_ProductOffer_QNAME, ProductOfferType.class, null, value);
    }

}
