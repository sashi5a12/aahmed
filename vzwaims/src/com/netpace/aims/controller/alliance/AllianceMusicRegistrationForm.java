package com.netpace.aims.controller.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="AllianceMusicRegistrationForm"
 */
public class AllianceMusicRegistrationForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(AllianceMusicRegistrationForm.class.getName());

    private java.lang.String companyName;
    private java.lang.String webSiteUrl;
    private java.lang.String firstName;
    private java.lang.String lastName;
    private java.lang.String email;
    private java.lang.String confirmEmail;
    private java.lang.String mobilePhone;
    private java.lang.String officePhone;
    private java.lang.String title;
    private java.lang.String fax;
    private java.lang.String haveRightsCleared;
    private java.lang.String annualRevenue;
    private java.lang.String contentThruAggregator;
    private java.lang.String additionalInformation;
    private java.lang.String currentDistributionPartners;
    private java.lang.String haveExclusiveRights;
    private java.lang.String whatIsExclusive;
    private java.lang.String vzdnManagerRoles;
    
    
    
    private java.util.Collection allProductTypes;
    private java.lang.String[] productId;
    private java.lang.String[] productTypeId;
    private java.lang.String[] productName;
    private java.lang.String[] sizeTotalCatalog;
    private java.lang.String[] sizeMobileCatalog;
    private java.lang.String[] incomeNonMobile;
    private java.lang.String[] incomeMobile;
    private java.lang.String[] topSellingArtists;
    private HashMap productMapper;

    //private java.lang.Boolean isAccepted; //Commented by Muzammil as part of fix for Bug# 7845

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {}

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        productMapper = new HashMap();

        Collection products = new ArrayList();
        for (int iIndex = 0; iIndex < this.productTypeId.length; iIndex++)
        {
            products.add(
                new AllianceMusicRegistrationProductBean(
                    this.productTypeId[iIndex],
                    this.productName[iIndex],
                    this.sizeTotalCatalog[iIndex],
                    this.sizeMobileCatalog[iIndex],
                    this.incomeNonMobile[iIndex],
                    this.incomeMobile[iIndex],
                    this.topSellingArtists[iIndex]));

            productMapper.put(this.productTypeId[iIndex], new Long(iIndex));
            //System.out.println("\nSize is: " + productMapper.size());
            //System.out.println("\nKey is: " + this.productTypeId[iIndex]);
            //System.out.println("\nValue is: " + productMapper.get(this.productTypeId[iIndex]));
        }

        this.allProductTypes = products;

        //Check to see if data is compatible with DB fields
        validateToDBFields(errors);

        if (this.isBlankString(this.companyName))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.companyName"));

        if (this.isBlankString(this.webSiteUrl))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.webSiteUrl"));

        if ((this.productId == null) || (!(this.productId.length > 0)))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.productId"));
        }
        else
        {
            for (int iIndex = 0; iIndex < this.productId.length; iIndex++)
            {

                int originalIndex = ((Long) productMapper.get(this.productId[iIndex])).intValue();

                if ((this.sizeTotalCatalog[originalIndex] == null) || !(this.sizeTotalCatalog[originalIndex].length() > 0))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.required.sizeTotalCatalog", this.productName[originalIndex]));

                if ((this.sizeMobileCatalog[originalIndex] == null) || !(this.sizeMobileCatalog[originalIndex].length() > 0))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.required.sizeMobileCatalog", this.productName[originalIndex]));

                if ((this.incomeNonMobile[originalIndex] == null) || !(this.incomeNonMobile[originalIndex].length() > 0))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.required.incomeNonMobile", this.productName[originalIndex]));

                if ((this.incomeMobile[originalIndex] == null) || !(this.incomeMobile[originalIndex].length() > 0))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.required.incomeMobile", this.productName[originalIndex]));

                if ((this.topSellingArtists[originalIndex] == null) || !(this.topSellingArtists[originalIndex].length() > 0))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.required.topSellingArtists", this.productName[originalIndex]));

            }
        }

        if ((this.haveRightsCleared == null) || !(this.haveRightsCleared.length() > 0))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.haveRightsCleared"));

        //if (this.isBlankString(this.annualRevenue))
        //    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.annualRevenue"));

        if ((this.haveExclusiveRights == null) || !(this.haveExclusiveRights.length() > 0))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.haveExclusiveRights"));
        else if ((this.haveExclusiveRights.equals("N")) && (this.isBlankString(this.whatIsExclusive)))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.whatIsExclusive"));

        if ((this.contentThruAggregator == null) || !(this.contentThruAggregator.length() > 0))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.contentThruAggregator"));

        if (this.isBlankString(this.currentDistributionPartners))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.currentDistributionPartners"));

        if (this.isBlankString(this.additionalInformation))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.additionalInformation"));
/*
 * Commented by Muzammil as part of fix for Bug# 7845
        if ((this.isAccepted == null) || (!this.isAccepted.booleanValue()))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.required.isAccepted"));
*/
        return errors;
    }

    public void validateToDBFields(ActionErrors errors)
    {

        if ((this.companyName != null) && (this.companyName.length() > 250))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.length.companyName"));

        if ((this.webSiteUrl != null) && (this.webSiteUrl.length() > 250))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.length.webSiteUrl"));

        
        
       

        if ((this.whatIsExclusive != null) && (this.whatIsExclusive.length() > 3000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.length.whatIsExclusive"));

        if ((this.currentDistributionPartners != null) && (this.currentDistributionPartners.length() > 3000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.length.currentDistributionPartners"));

        if ((this.annualRevenue != null) && (this.annualRevenue.length() > 100))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.length.annualRevenue"));

        if ((this.additionalInformation != null) && (this.additionalInformation.length() > 3000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicRegistrationForm.length.additionalInformation"));

        if (this.productId != null)
        {
            for (int iIndex = 0; iIndex < this.productId.length; iIndex++)
            {
                /*
                System.out.println("\nSize is: " + this.productMapper.size());
                for (Iterator it = productMapper.keySet().iterator(); it.hasNext();)
                {
                    String asd = (String)it.next();
                    System.out.println("\nKeySet is: " + asd);
                    //System.out.println("\nKeySet Value is: " + asd);
                }
                for (Iterator it = productMapper.values().iterator(); it.hasNext();)
                {
                    System.out.println("\nValues is: " + it.next());
                }
                
                System.out.println("\nKey is: " + this.productId[iIndex]);
                System.out.println("\nValue is: " + this.productMapper.get(this.productId[iIndex]));
                */

                int originalIndex = ((Long) productMapper.get(this.productId[iIndex])).intValue();

                if ((this.sizeTotalCatalog[originalIndex] != null) && (this.sizeTotalCatalog[originalIndex].length() > 50))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.length.sizeTotalCatalog", productName[originalIndex]));

                if ((this.sizeMobileCatalog[originalIndex] != null) && (this.sizeMobileCatalog[originalIndex].length() > 50))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.length.sizeMobileCatalog", productName[originalIndex]));

                if ((this.incomeNonMobile[originalIndex] != null) && (this.incomeNonMobile[originalIndex].length() > 50))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.length.incomeNonMobile", productName[originalIndex]));

                if ((this.incomeMobile[originalIndex] != null) && (this.incomeMobile[originalIndex].length() > 50))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.length.incomeMobile", productName[originalIndex]));

                if ((this.topSellingArtists[originalIndex] != null) && (this.topSellingArtists[originalIndex].length() > 1000))
                    errors.add(
                        ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("error.AllianceMusicRegistrationForm.length.topSellingArtists", productName[originalIndex]));
            }
        }

    }

    /**
     * @return
     */
    public java.lang.String getAdditionalInformation()
    {
        return additionalInformation;
    }

    /**
     * @return
     */
    public java.lang.String getAnnualRevenue()
    {
        return annualRevenue;
    }

    /**
     * @return
     */
    public java.lang.String getCompanyName()
    {
        return companyName;
    }

    /**
     * @return
     */
    public java.lang.String getEmail()
    {
        return email;
    }

    /**
     * @return
     */
    public java.lang.String getFirstName()
    {
        return firstName;
    }

    /**
     * @return
     */
    public java.lang.String getLastName()
    {
        return lastName;
    }

    /**
     * @param string
     */
    public void setAdditionalInformation(java.lang.String string)
    {
        additionalInformation = string;
    }

    /**
     * @param string
     */
    public void setAnnualRevenue(java.lang.String string)
    {
        annualRevenue = string;
    }

    /**
     * @param string
     */
    public void setCompanyName(java.lang.String string)
    {
        companyName = string;
    }

    /**
     * @param string
     */
    public void setEmail(java.lang.String string)
    {
        email = string;
    }

    /**
     * @param string
     */
    public void setFirstName(java.lang.String string)
    {
        firstName = string;
    }

    /**
     * @param string
     */
    public void setLastName(java.lang.String string)
    {
        lastName = string;
    }

    /**
     * @return
     */
    public java.lang.String getConfirmEmail()
    {
        return confirmEmail;
    }

    /**
     * @param string
     */
    public void setConfirmEmail(java.lang.String string)
    {
        confirmEmail = string;
    }

    /**
     * @return
     */
    public java.lang.String[] getTopSellingArtists()
    {
        return topSellingArtists;
    }

    /**
     * @param strings
     */
    public void setTopSellingArtists(java.lang.String[] strings)
    {
        topSellingArtists = strings;
    }

    public void setSizeTotalCatalog(java.lang.String[] strings)
    {
        sizeTotalCatalog = strings;
    }

    public java.lang.String[] getSizeTotalCatalog()
    {
        return sizeTotalCatalog;
    }

    public java.lang.String[] getIncomeNonMobile()
    {
        return incomeNonMobile;
    }

    public void setIncomeNonMobile(java.lang.String[] strings)
    {
        incomeNonMobile = strings;
    }

    /**
     * @return
     */
    public java.util.Collection getAllProductTypes()
    {
        return allProductTypes;
    }

    /**
     * @param collection
     */
    public void setAllProductTypes(java.util.Collection collection)
    {
        allProductTypes = collection;
    }

    /**
     * @return
     */
    public java.lang.String[] getProductName()
    {
        return productName;
    }

    /**
     * @param strings
     */
    public void setProductName(java.lang.String[] strings)
    {
        productName = strings;
    }

    /**
     * @return
     */
    public java.lang.String[] getIncomeMobile()
    {
        return incomeMobile;
    }

    /**
     * @return
     */
    public java.lang.String[] getSizeMobileCatalog()
    {
        return sizeMobileCatalog;
    }

    /**
     * @param strings
     */
    public void setIncomeMobile(java.lang.String[] strings)
    {
        incomeMobile = strings;
    }

    /**
     * @param strings
     */
    public void setSizeMobileCatalog(java.lang.String[] strings)
    {
        sizeMobileCatalog = strings;
    }

    /**
     * @return
     */
    public java.lang.String getMobilePhone()
    {
        return mobilePhone;
    }

    /**
     * @return
     */
    public java.lang.String getOfficePhone()
    {
        return officePhone;
    }

    /**
     * @param string
     */
    public void setMobilePhone(java.lang.String string)
    {
        mobilePhone = string;
    }

    /**
     * @param string
     */
    public void setOfficePhone(java.lang.String string)
    {
        officePhone = string;
    }

    /**
     * @return
     */
    public java.lang.String getHaveRightsCleared()
    {
        return haveRightsCleared;
    }

    /**
     * @param string
     */
    public void setHaveRightsCleared(java.lang.String string)
    {
        haveRightsCleared = string;
    }

    /**
     * @return
     */
    public java.lang.String getContentThruAggregator()
    {
        return contentThruAggregator;
    }

    /**
     * @param string
     */
    public void setContentThruAggregator(java.lang.String string)
    {
        contentThruAggregator = string;
    }

    /**
     * @return
     */
    public java.lang.String getCurrentDistributionPartners()
    {
        return currentDistributionPartners;
    }

    /**
     * @param string
     */
    public void setCurrentDistributionPartners(java.lang.String string)
    {
        currentDistributionPartners = string;
    }

    /**
     * @return
     */
    /*
     * Commented by Muzammil as part of fix for Bug# 7845
    public java.lang.Boolean getIsAccepted()
    {
        return isAccepted;
    }
*/
    /**
     * @param boolean1
     */
    /*
     * Commented by Muzammil as part of fix for Bug# 7845
    public void setIsAccepted(java.lang.Boolean boolean1)
    {
        isAccepted = boolean1;
    }
*/
    /**
     * @return
     */
    public java.lang.String getHaveExclusiveRights()
    {
        return haveExclusiveRights;
    }

    /**
     * @param string
     */
    public void setHaveExclusiveRights(java.lang.String string)
    {
        haveExclusiveRights = string;
    }

    /**
     * @return
     */
    public java.lang.String getWhatIsExclusive()
    {
        return whatIsExclusive;
    }

    /**
     * @param string
     */
    public void setWhatIsExclusive(java.lang.String string)
    {
        whatIsExclusive = string;
    }

    /**
     * @return
     */
    public java.lang.String[] getProductTypeId()
    {
        return productTypeId;
    }

    /**
     * @param strings
     */
    public void setProductTypeId(java.lang.String[] strings)
    {
        productTypeId = strings;
    }

    /**
     * @return
     */
    public java.lang.String[] getProductId()
    {
        return productId;
    }

    /**
     * @param strings
     */
    public void setProductId(java.lang.String[] strings)
    {
        productId = strings;
    }

    /**
     * @return
     */
    public HashMap getProductMapper()
    {
        return productMapper;
    }

    /**
     * @param map
     */
    public void setProductMapper(HashMap map)
    {
        productMapper = map;
    }

    /**
     * @return
     */
    public java.lang.String getWebSiteUrl()
    {
        return webSiteUrl;
    }

    /**
     * @param string
     */
    public void setWebSiteUrl(java.lang.String string)
    {
        webSiteUrl = string;
    }

	public java.lang.String getVzdnManagerRoles() {
		return vzdnManagerRoles;
	}

	public void setVzdnManagerRoles(java.lang.String vzdnManagerRoles) {
		this.vzdnManagerRoles = vzdnManagerRoles;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	

    
}
