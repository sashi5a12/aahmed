package com.netpace.aims.controller.application;

import java.io.Serializable;
import java.util.ArrayList;

public class WapInfoSpaceSubmitDCRBean extends com.netpace.aims.model.BaseValueObject implements Serializable
{
    private String vendorName; //Size:80
    private String vendorId;
    private String vendorLogoURL;

    //VendorBusinessContact (minOccurs = "0") .. but required first time
    private String businessContactName; //Required ...All others minOccurs="0"
    private String businessContactTitle;
    private String businessContactAddressStreet;
    private String businessContactAddressCity;
    private String businessContactAddressState;
    private String businessContactAddressZip;
    private String businessContactOfficePhone;
    private String businessContactMobilePhone;
    private String businessContactEmail;

    //VendorTechnicalContact (minOccurs = "0") .. but required first time
    private String technicalContactName;
    private String technicalContactTitle;
    private String technicalContactAddressStreet;
    private String technicalContactAddressCity;
    private String technicalContactAddressState;
    private String technicalContactAddressZip;
    private String technicalContactOfficePhone;
    private String technicalContactMobilePhone;
    private String technicalContactEmail;

    //VendorEscalationContact (minOccurs = "0") .. but required first time
    private String escalationContactName;
    private String escalationContactTitle;
    private String escalationContactAddressStreet;
    private String escalationContactAddressCity;
    private String escalationContactAddressState;
    private String escalationContactAddressZip;
    private String escalationContactOfficePhone;
    private String escalationContactMobilePhone;
    private String escalationContactEmail;
    private String escalationPhone;
    private String escalationInstructions; //Size: 1024 ... minOccurs="0" ..but this is required 

    //CustomerContact (minOccurs = "0") .. but required first time
    private String customerContactName;
    private String customerContactEmail;

    private String vendorProductCode; //Size: 20
    private String vendorProductVersion; //Size: 10
    private String productPresentationNameLargeScreens; //Long Product Name? 
    private String productPresentationNameSmallScreens; //Short Product Name?

    private String productIconURL;
    private String productImageURL;

    private String longDescription;
    private String shortDescription;

    private String demoUrl; //WAP Demo URL?  
    private String testUrl; //Test URL? 
    private String availabilityDateForTestURL; //Testing Effective Date? 
    private String productionUrl; //Production URL? 
    private String availabilityDateForProductionURL; //Product Live Date? 
    private String desktopUrl; //Website URL (For Reference)?  
    private ArrayList userProfile;
    private String premiumIndicator;

    private ArrayList initialPortalPlacementCategory; //Size: 20
    private ArrayList initialPortalPlacementSubCategory; //Size: 20  ... minOccurs="0" 
    private ArrayList initialPortalPlacementPosition; //Link Order?

    private String action;

    /**
     * @return
     */
    public String getAvailabilityDateForProductionURL()
    {
        return availabilityDateForProductionURL;
    }

    /**
     * @return
     */
    public String getAvailabilityDateForTestURL()
    {
        return availabilityDateForTestURL;
    }

    /**
     * @return
     */
    public String getBusinessContactAddressCity()
    {
        return businessContactAddressCity;
    }

    /**
     * @return
     */
    public String getBusinessContactAddressState()
    {
        return businessContactAddressState;
    }

    /**
     * @return
     */
    public String getBusinessContactAddressStreet()
    {
        return businessContactAddressStreet;
    }

    /**
     * @return
     */
    public String getBusinessContactAddressZip()
    {
        return businessContactAddressZip;
    }

    /**
     * @return
     */
    public String getBusinessContactEmail()
    {
        return businessContactEmail;
    }

    /**
     * @return
     */
    public String getBusinessContactMobilePhone()
    {
        return businessContactMobilePhone;
    }

    /**
     * @return
     */
    public String getBusinessContactName()
    {
        return businessContactName;
    }

    /**
     * @return
     */
    public String getBusinessContactOfficePhone()
    {
        return businessContactOfficePhone;
    }

    /**
     * @return
     */
    public String getBusinessContactTitle()
    {
        return businessContactTitle;
    }

    /**
     * @return
     */
    public String getCustomerContactName()
    {
        return customerContactName;
    }

    /**
     * @return
     */
    public String getDemoUrl()
    {
        return demoUrl;
    }

    /**
     * @return
     */
    public String getDesktopUrl()
    {
        return desktopUrl;
    }

    /**
     * @return
     */
    public String getEscalationContactAddressCity()
    {
        return escalationContactAddressCity;
    }

    /**
     * @return
     */
    public String getEscalationContactAddressState()
    {
        return escalationContactAddressState;
    }

    /**
     * @return
     */
    public String getEscalationContactAddressStreet()
    {
        return escalationContactAddressStreet;
    }

    /**
     * @return
     */
    public String getEscalationContactAddressZip()
    {
        return escalationContactAddressZip;
    }

    /**
     * @return
     */
    public String getEscalationContactEmail()
    {
        return escalationContactEmail;
    }

    /**
     * @return
     */
    public String getEscalationContactMobilePhone()
    {
        return escalationContactMobilePhone;
    }

    /**
     * @return
     */
    public String getEscalationContactName()
    {
        return escalationContactName;
    }

    /**
     * @return
     */
    public String getEscalationContactOfficePhone()
    {
        return escalationContactOfficePhone;
    }

    /**
     * @return
     */
    public String getEscalationContactTitle()
    {
        return escalationContactTitle;
    }

    /**
     * @return
     */
    public String getEscalationInstructions()
    {
        return escalationInstructions;
    }

    /**
     * @return
     */
    public String getEscalationPhone()
    {
        return escalationPhone;
    }

    /**
     * @return
     */
    public String getLongDescription()
    {
        return longDescription;
    }

    /**
     * @return
     */
    public String getPremiumIndicator()
    {
        return premiumIndicator;
    }

    /**
     * @return
     */
    public String getProductIconURL()
    {
        return productIconURL;
    }

    /**
     * @return
     */
    public String getProductImageURL()
    {
        return productImageURL;
    }

    /**
     * @return
     */
    public String getProductionUrl()
    {
        return productionUrl;
    }

    /**
     * @return
     */
    public String getProductPresentationNameLargeScreens()
    {
        return productPresentationNameLargeScreens;
    }

    /**
     * @return
     */
    public String getProductPresentationNameSmallScreens()
    {
        return productPresentationNameSmallScreens;
    }

    /**
     * @return
     */
    public String getShortDescription()
    {
        return shortDescription;
    }

    /**
     * @return
     */
    public String getTechnicalContactAddressCity()
    {
        return technicalContactAddressCity;
    }

    /**
     * @return
     */
    public String getTechnicalContactAddressState()
    {
        return technicalContactAddressState;
    }

    /**
     * @return
     */
    public String getTechnicalContactAddressStreet()
    {
        return technicalContactAddressStreet;
    }

    /**
     * @return
     */
    public String getTechnicalContactAddressZip()
    {
        return technicalContactAddressZip;
    }

    /**
     * @return
     */
    public String getTechnicalContactEmail()
    {
        return technicalContactEmail;
    }

    /**
     * @return
     */
    public String getTechnicalContactMobilePhone()
    {
        return technicalContactMobilePhone;
    }

    /**
     * @return
     */
    public String getTechnicalContactName()
    {
        return technicalContactName;
    }

    /**
     * @return
     */
    public String getTechnicalContactOfficePhone()
    {
        return technicalContactOfficePhone;
    }

    /**
     * @return
     */
    public String getTechnicalContactTitle()
    {
        return technicalContactTitle;
    }

    /**
     * @return
     */
    public String getTestUrl()
    {
        return testUrl;
    }

    /**
     * @return
     */
    public String getVendorId()
    {
        return vendorId;
    }

    /**
     * @return
     */
    public String getVendorLogoURL()
    {
        return vendorLogoURL;
    }

    /**
     * @return
     */
    public String getVendorName()
    {
        return vendorName;
    }

    /**
     * @return
     */
    public String getVendorProductCode()
    {
        return vendorProductCode;
    }

    /**
     * @param string
     */
    public void setAvailabilityDateForProductionURL(String string)
    {
        availabilityDateForProductionURL = string;
    }

    /**
     * @param string
     */
    public void setAvailabilityDateForTestURL(String string)
    {
        availabilityDateForTestURL = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactAddressCity(String string)
    {
        businessContactAddressCity = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactAddressState(String string)
    {
        businessContactAddressState = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactAddressStreet(String string)
    {
        businessContactAddressStreet = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactAddressZip(String string)
    {
        businessContactAddressZip = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactEmail(String string)
    {
        businessContactEmail = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactMobilePhone(String string)
    {
        businessContactMobilePhone = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactName(String string)
    {
        businessContactName = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactOfficePhone(String string)
    {
        businessContactOfficePhone = string;
    }

    /**
     * @param string
     */
    public void setBusinessContactTitle(String string)
    {
        businessContactTitle = string;
    }

    /**
     * @param string
     */
    public void setCustomerContactName(String string)
    {
        customerContactName = string;
    }

    /**
     * @param string
     */
    public void setDemoUrl(String string)
    {
        demoUrl = string;
    }

    /**
     * @param string
     */
    public void setDesktopUrl(String string)
    {
        desktopUrl = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactAddressCity(String string)
    {
        escalationContactAddressCity = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactAddressState(String string)
    {
        escalationContactAddressState = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactAddressStreet(String string)
    {
        escalationContactAddressStreet = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactAddressZip(String string)
    {
        escalationContactAddressZip = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactEmail(String string)
    {
        escalationContactEmail = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactMobilePhone(String string)
    {
        escalationContactMobilePhone = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactName(String string)
    {
        escalationContactName = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactOfficePhone(String string)
    {
        escalationContactOfficePhone = string;
    }

    /**
     * @param string
     */
    public void setEscalationContactTitle(String string)
    {
        escalationContactTitle = string;
    }

    /**
     * @param string
     */
    public void setEscalationInstructions(String string)
    {
        escalationInstructions = string;
    }

    /**
     * @param string
     */
    public void setEscalationPhone(String string)
    {
        escalationPhone = string;
    }

    /**
     * @param string
     */
    public void setLongDescription(String string)
    {
        longDescription = string;
    }

    /**
     * @param string
     */
    public void setPremiumIndicator(String string)
    {
        premiumIndicator = string;
    }

    /**
     * @param string
     */
    public void setProductIconURL(String string)
    {
        productIconURL = string;
    }

    /**
     * @param string
     */
    public void setProductImageURL(String string)
    {
        productImageURL = string;
    }

    /**
     * @param string
     */
    public void setProductionUrl(String string)
    {
        productionUrl = string;
    }

    /**
     * @param string
     */
    public void setProductPresentationNameLargeScreens(String string)
    {
        productPresentationNameLargeScreens = string;
    }

    /**
     * @param string
     */
    public void setProductPresentationNameSmallScreens(String string)
    {
        productPresentationNameSmallScreens = string;
    }

    /**
     * @param string
     */
    public void setShortDescription(String string)
    {
        shortDescription = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactAddressCity(String string)
    {
        technicalContactAddressCity = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactAddressState(String string)
    {
        technicalContactAddressState = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactAddressStreet(String string)
    {
        technicalContactAddressStreet = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactAddressZip(String string)
    {
        technicalContactAddressZip = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactEmail(String string)
    {
        technicalContactEmail = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactMobilePhone(String string)
    {
        technicalContactMobilePhone = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactName(String string)
    {
        technicalContactName = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactOfficePhone(String string)
    {
        technicalContactOfficePhone = string;
    }

    /**
     * @param string
     */
    public void setTechnicalContactTitle(String string)
    {
        technicalContactTitle = string;
    }

    /**
     * @param string
     */
    public void setTestUrl(String string)
    {
        testUrl = string;
    }

    /**
     * @param string
     */
    public void setVendorId(String string)
    {
        vendorId = string;
    }

    /**
     * @param string
     */
    public void setVendorLogoURL(String string)
    {
        vendorLogoURL = string;
    }

    /**
     * @param string
     */
    public void setVendorName(String string)
    {
        vendorName = string;
    }

    /**
     * @param string
     */
    public void setVendorProductCode(String string)
    {
        vendorProductCode = string;
    }

    /**
     * @return
     */
    public ArrayList getInitialPortalPlacementCategory()
    {
        return initialPortalPlacementCategory;
    }

    /**
     * @return
     */
    public ArrayList getInitialPortalPlacementPosition()
    {
        return initialPortalPlacementPosition;
    }

    /**
     * @return
     */
    public ArrayList getInitialPortalPlacementSubCategory()
    {
        return initialPortalPlacementSubCategory;
    }

    /**
     * @return
     */
    public ArrayList getUserProfile()
    {
        return userProfile;
    }

    /**
     * @param list
     */
    public void setInitialPortalPlacementCategory(ArrayList list)
    {
        initialPortalPlacementCategory = list;
    }

    /**
     * @param list
     */
    public void setInitialPortalPlacementPosition(ArrayList list)
    {
        initialPortalPlacementPosition = list;
    }

    /**
     * @param list
     */
    public void setInitialPortalPlacementSubCategory(ArrayList list)
    {
        initialPortalPlacementSubCategory = list;
    }

    /**
     * @param list
     */
    public void setUserProfile(ArrayList list)
    {
        userProfile = list;
    }

    /**
     * @return
     */
    public String getVendorProductVersion()
    {
        return vendorProductVersion;
    }

    /**
     * @param string
     */
    public void setVendorProductVersion(String string)
    {
        vendorProductVersion = string;
    }

    /**
     * @return
     */
    public String getAction()
    {
        return action;
    }

    /**
     * @param string
     */
    public void setAction(String string)
    {
        action = string;
    }

    /**
     * @return
     */
    public String getCustomerContactEmail()
    {
        return customerContactEmail;
    }

    /**
     * @param string
     */
    public void setCustomerContactEmail(String string)
    {
        customerContactEmail = string;
    }

}