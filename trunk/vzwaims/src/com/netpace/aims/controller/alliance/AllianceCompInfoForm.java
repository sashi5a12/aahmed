package com.netpace.aims.controller.alliance;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.bo.roles.AimsSysRolesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.NameValueBean;

/**
 * @struts.form name="AllianceCompInfoForm"
 */
public class AllianceCompInfoForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(AllianceCompInfoForm.class.getName());

    private java.lang.Long allianceId;
    private java.lang.String companyName;
    private java.lang.String companyLegalName;
    private java.lang.String stateOfInc;
    private java.lang.String authRepName;
    private java.lang.String streetAddress1;
    private java.lang.String steetAddress2;
    private java.lang.String city;
    private java.lang.String state;
    private java.lang.String zipCode;
    private java.lang.String country;
    private java.lang.String webSiteUrl;
    private java.lang.String prevYearRevenues;
    private java.lang.String dbNumber;
    private java.lang.String isFinanceInfoPublic;
    private java.lang.String reasonsForRelationshipWithVZW;

    private java.lang.String existingContractsWithVZW;
    
    
    private java.lang.String commentsAllianceWithOtherCarriers;
    private java.lang.String otherIndustryFocus;
    private java.lang.String vendorId;
    private java.lang.String status;

    private java.util.Collection assignedIndustryFocus;
    private java.util.Collection availableIndustryFocus;
    private java.util.Collection assignedRegions;
    private java.util.Collection availableRegions;
    private java.util.Collection assignedPlatforms;
    private java.util.Collection availablePlatforms;

    private java.lang.String[] assignedArrIndustryFocus;
    private java.lang.String[] availableArrIndustryFocus;
    private java.lang.String[] assignedArrRegions;
    private java.lang.String[] availableArrRegions;
    private java.lang.String[] assignedArrPlatforms;
    private java.lang.String[] availableArrPlatforms;

    private java.lang.String allianceWithOtherCarriers;
    private java.lang.Long[] arrAlliancesWithOtherCarriers;
    private java.util.Collection assigendAlliancesWithOtherCarriers;

    private java.lang.String remitTo;
    private java.lang.String remitAddress1;
    private java.lang.String remitAddress2;
    private java.lang.String remitCity;
    private java.lang.String remitState;
    private java.lang.String remitPostalCode;
    private java.lang.String remitCountry;
    
    public java.lang.Long getAllianceId()
    {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId)
    {
        this.allianceId = allianceId;
    }

    public java.lang.String getCompanyName()
    {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String companyName)
    {
        this.companyName = companyName;
    }

    public java.lang.String getCompanyLegalName()
    {
        return this.companyLegalName;
    }

    public void setCompanyLegalName(java.lang.String companyLegalName)
    {
        this.companyLegalName = companyLegalName;
    }

    public java.lang.String getStateOfInc()
    {
        return this.stateOfInc;
    }

    public void setStateOfInc(java.lang.String stateOfInc)
    {
        this.stateOfInc = stateOfInc;
    }

    public java.lang.String getAuthRepName()
    {
        return this.authRepName;
    }

    public void setAuthRepName(java.lang.String authRepName)
    {
        this.authRepName = authRepName;
    }

    public java.lang.String getStreetAddress1()
    {
        return this.streetAddress1;
    }

    public void setStreetAddress1(java.lang.String streetAddress1)
    {
        this.streetAddress1 = streetAddress1;
    }

    public java.lang.String getSteetAddress2()
    {
        return this.steetAddress2;
    }

    public void setSteetAddress2(java.lang.String steetAddress2)
    {
        this.steetAddress2 = steetAddress2;
    }

    public java.lang.String getCity()
    {
        return this.city;
    }

    public void setCity(java.lang.String city)
    {
        this.city = city;
    }

    public java.lang.String getState()
    {
        return this.state;
    }

    public void setState(java.lang.String state)
    {
        this.state = state;
    }

    public java.lang.String getZipCode()
    {
        return this.zipCode;
    }

    public void setZipCode(java.lang.String zipCode)
    {
        this.zipCode = zipCode;
    }

    public java.lang.String getCountry()
    {
        return this.country;
    }

    public void setCountry(java.lang.String country)
    {
        this.country = country;
    }

    public java.lang.String getWebSiteUrl()
    {
        return this.webSiteUrl;
    }

    public void setWebSiteUrl(java.lang.String webSiteUrl)
    {
        this.webSiteUrl = webSiteUrl;
    }

    public java.lang.String getPrevYearRevenues()
    {
        return this.prevYearRevenues;
    }

    public void setPrevYearRevenues(java.lang.String prevYearRevenues)
    {
        this.prevYearRevenues = prevYearRevenues;
    }

    public java.lang.String getDbNumber()
    {
        return this.dbNumber;
    }

    public void setDbNumber(java.lang.String dbNumber)
    {
        this.dbNumber = dbNumber;
    }

    public java.lang.String getIsFinanceInfoPublic()
    {
        return this.isFinanceInfoPublic;
    }

    public void setIsFinanceInfoPublic(java.lang.String isFinanceInfoPublic)
    {
        this.isFinanceInfoPublic = isFinanceInfoPublic;
    }


    public java.lang.String getReasonsForRelationshipWithVZW()
    {
        return this.reasonsForRelationshipWithVZW;
    }

    public void setReasonsForRelationshipWithVZW(java.lang.String reasonsForRelationshipWithVZW)
    {
        this.reasonsForRelationshipWithVZW = reasonsForRelationshipWithVZW;
    }


    public java.lang.String getExistingContractsWithVZW()
    {
        return this.existingContractsWithVZW;
    }

    public void setExistingContractsWithVZW(java.lang.String existingContractsWithVZW)
    {
        this.existingContractsWithVZW = existingContractsWithVZW;
    }

    public java.lang.String getAllianceWithOtherCarriers()
    {
        return this.allianceWithOtherCarriers;
    }

    public void setAllianceWithOtherCarriers(java.lang.String allianceWithOtherCarriers)
    {
        this.allianceWithOtherCarriers = allianceWithOtherCarriers;
    }

    public java.lang.String getCommentsAllianceWithOtherCarriers()
    {
        return this.commentsAllianceWithOtherCarriers;
    }

    public void setCommentsAllianceWithOtherCarriers(java.lang.String commentsAllianceWithOtherCarriers)
    {
        this.commentsAllianceWithOtherCarriers = commentsAllianceWithOtherCarriers;
    }

    public java.lang.String getOtherIndustryFocus()
    {
        return this.otherIndustryFocus;
    }

    public void setOtherIndustryFocus(java.lang.String otherIndustryFocus)
    {
        this.otherIndustryFocus = otherIndustryFocus;
    }

    public java.util.Collection getAssignedIndustryFocus()
    {
        return this.assignedIndustryFocus;
    }

    public void setAssignedIndustryFocus(java.util.Collection assignedIndustryFocus)
    {
        this.assignedIndustryFocus = assignedIndustryFocus;
    }

    public java.util.Collection getAvailableIndustryFocus()
    {
        return this.availableIndustryFocus;
    }

    public void setAvailableIndustryFocus(java.util.Collection availableIndustryFocus)
    {
        this.availableIndustryFocus = availableIndustryFocus;
    }

    public java.util.Collection getAssignedRegions()
    {
        return this.assignedRegions;
    }

    public void setAssignedRegions(java.util.Collection assignedRegions)
    {
        this.assignedRegions = assignedRegions;
    }

    public java.util.Collection getAvailableRegions()
    {
        return this.availableRegions;
    }

    public void setAvailableRegions(java.util.Collection availableRegions)
    {
        this.availableRegions = availableRegions;
    }

    public String[] getAssignedArrIndustryFocus()
    {
        return this.assignedArrIndustryFocus;
    }

    public void setAssignedArrIndustryFocus(String[] assignedArrIndustryFocus)
    {
        this.assignedArrIndustryFocus = assignedArrIndustryFocus;
    }

    public String[] getAvailableArrIndustryFocus()
    {
        return this.availableArrIndustryFocus;
    }

    public void setAvailableArrIndustryFocus(String[] availableArrIndustryFocus)
    {
        this.availableArrIndustryFocus = availableArrIndustryFocus;
    }

    //

    public String[] getAssignedArrRegions()
    {
        return this.assignedArrRegions;
    }

    public void setAssignedArrRegions(String[] assignedArrRegions)
    {
        this.assignedArrRegions = assignedArrRegions;
    }

    public String[] getAvailableArrRegions()
    {
        return this.availableArrRegions;
    }

    public void setAvailableArrRegions(String[] availableArrRegions)
    {
        this.availableArrRegions = availableArrRegions;
    }

    public java.lang.String[] getAssignedArrPlatforms()
    {
        return assignedArrPlatforms;
    }

    public java.util.Collection getAssignedPlatforms()
    {
        return assignedPlatforms;
    }

    public java.lang.String[] getAvailableArrPlatforms()
    {
        return availableArrPlatforms;
    }

    public java.util.Collection getAvailablePlatforms()
    {
        return availablePlatforms;
    }

    public void setAssignedArrPlatforms(java.lang.String[] strings)
    {
        assignedArrPlatforms = strings;
    }

    public void setAssignedPlatforms(java.util.Collection collection)
    {
        assignedPlatforms = collection;
    }

    public void setAvailableArrPlatforms(java.lang.String[] strings)
    {
        availableArrPlatforms = strings;
    }

    public void setAvailablePlatforms(java.util.Collection collection)
    {
        availablePlatforms = collection;
    }

    public java.lang.String getVendorId()
    {
        return vendorId;
    }

    public void setVendorId(java.lang.String string)
    {
        vendorId = string;
    }

    //

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        Collection AllianceCompInfo = null;
        Collection AllianceIndFocus = null;
        Collection AvailableIndFocus = null;
        Collection AllianceRegion = null;
        Collection AvailableRegion = null;

        Collection AvailablePlatform = null;
        Collection AvailableRemitInfo = null;


        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long user_id = user.getUserId();

        Long alliance_id = null;

        if (user_type.equalsIgnoreCase("A"))
        {
            alliance_id = user.getAimsAllianc();
        }

        if (user_type.equalsIgnoreCase("V"))
        {
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        }

        Object[] userValues = null;
        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (task_name.equalsIgnoreCase("editForm"))
        {
            try
            {
                AllianceCompInfo = AllianceCompInfoManager.getAllianceCompInfo(alliance_id, user_type);
                AllianceIndFocus = AllianceCompInfoManager.getAllianceIndFocus(alliance_id, user_type);
                AvailableIndFocus = AllianceCompInfoManager.getAvailableIndFocus(alliance_id, user_type);
                AllianceRegion = AllianceCompInfoManager.getAllianceRegions(alliance_id, user_type);
                AvailableRegion = AllianceCompInfoManager.getAvailableRegions(alliance_id, user_type);
                AvailablePlatform = AllianceCompInfoManager.getAllAvailablePlatforms();
                
                for (Iterator iter = AllianceCompInfo.iterator(); iter.hasNext();)
                {
                    userValues = (Object[]) iter.next();

                    this.setAllianceId(alliance_id);
                    this.setCompanyName((String) userValues[0]);
                    this.setCompanyLegalName((String) userValues[1]);
                    this.setStateOfInc((String) userValues[2]);
                    this.setAuthRepName((String) userValues[3]);
                    this.setStreetAddress1((String) userValues[4]);
                    this.setSteetAddress2((String) userValues[5]);
                    this.setCity((String) userValues[6]);
                    this.setState((String) userValues[7]);
                    this.setZipCode((String) userValues[8]);
                    this.setCountry((String) userValues[9]);
                    this.setWebSiteUrl((String) userValues[10]);
                    this.setPrevYearRevenues((String) userValues[11]);
                    this.setDbNumber((String) userValues[12]);
                    this.setRemitTo((String) userValues[21]);
                    this.setRemitAddress1((String) userValues[22]);
                    this.setRemitAddress2((String) userValues[23]);
                    this.setRemitCity((String) userValues[24]);
                    this.setRemitState((String) userValues[25]);
                    this.setRemitPostalCode((String) userValues[26]);
                    this.setRemitCountry((String) userValues[27]);                    

                    if (user_type.equalsIgnoreCase("V"))
                    {
                        this.setIsFinanceInfoPublic(AimsUtils.getYesNoStatus((String) userValues[13]));
                        this.setExistingContractsWithVZW(AimsUtils.getYesNoStatus((String) userValues[15]));
                        //this.setAllianceWithOtherCarriers(AimsUtils.getYesNoStatus((String) userValues[16]));
                    }
                    if (user_type.equalsIgnoreCase("A"))
                    {
                        this.setIsFinanceInfoPublic((String) userValues[13]);
                        this.setExistingContractsWithVZW((String) userValues[15]);
                        //this.setAllianceWithOtherCarriers((String) userValues[16]);
                    }
                    this.setAllianceWithOtherCarriers((String) userValues[16]);
                    this.setReasonsForRelationshipWithVZW((String) userValues[14]);
                    this.setCommentsAllianceWithOtherCarriers((String) userValues[17]);
                    this.setOtherIndustryFocus((String) userValues[18]);
                    if (userValues[19] != null)
                        this.setVendorId(userValues[19].toString());
                    this.setStatus((String) userValues[20]);
                }

                this.setAssignedIndustryFocus(AllianceIndFocus);
                this.setAvailableIndustryFocus(AvailableIndFocus);
                this.setAssignedRegions(AllianceRegion);
                this.setAvailableRegions(AvailableRegion);
                this.setAssigendAlliancesWithOtherCarriers(AllianceCompInfoManager.getAlliancesWithOtherCarriers(alliance_id, user_type));

                request.setAttribute("aimsCarriers", this.getAimsCarriersList());
                this.setArrAlliancesWithOtherCarriers(AllianceCompInfoManager.getAlliancesWithOtherCarriersIds(alliance_id, user_type));
                request.setAttribute("availablePlatforms", AvailablePlatform);
                this.setAssignedArrPlatforms(AllianceCompInfoManager.getAlliancePlatformIds(alliance_id, user_type));
                request.setAttribute("countryList", this.getCountriesList());
            }
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }            
        }

        if (task_name.equalsIgnoreCase("edit"))
        {
            try
            {
                AllianceRegion = AllianceCompInfoManager.getAllianceRegions(alliance_id, user_type);
                AvailableRegion = AllianceCompInfoManager.getAvailableRegions(alliance_id, user_type);
                AllianceIndFocus = AllianceCompInfoManager.getAssignedIndFocusFromArray(request.getParameterValues("assignedArrIndustryFocus"));
                AvailableIndFocus = AllianceCompInfoManager.getAvailableIndFocusFromArray(request.getParameterValues("assignedArrIndustryFocus"));
                AvailablePlatform = AllianceCompInfoManager.getAllAvailablePlatforms();

                this.setAssignedIndustryFocus(AllianceIndFocus);
                this.setAvailableIndustryFocus(AvailableIndFocus);
                this.setAssignedRegions(AllianceRegion);
                this.setAvailableRegions(AvailableRegion);
                this.setAssigendAlliancesWithOtherCarriers(AllianceCompInfoManager.getAlliancesWithOtherCarriers(alliance_id, user_type));



                request.setAttribute("aimsCarriers", this.getAimsCarriersList());

                request.setAttribute("availablePlatforms", AvailablePlatform);

                request.setAttribute("countryList", this.getCountriesList());
            }

            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }
        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (task_name.equalsIgnoreCase("edit"))
        {
            if (this.isBlankString(this.companyName))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.companyName"));
            }

            if (this.isBlankString(this.companyLegalName))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.companyLegalName"));
            }

            if (this.isBlankString(this.authRepName))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.authRepName"));
            }

            if (this.isBlankString(this.streetAddress1))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.streetAddress1"));
            }

            if (this.isBlankString(this.webSiteUrl))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.webSiteUrl"));
            }

            if ((this.webSiteUrl != null) && (this.webSiteUrl.length() > 255))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.webSiteUrl"));

            if ((this.webSiteUrl != null) && !this.isValidHttpURL(this.webSiteUrl)  )
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.webSiteUrl"));     
            
            if ( !StringUtils.isEmpty(this.webSiteUrl) && (this.webSiteUrl.indexOf("<")!=-1 || this.webSiteUrl.indexOf(">")!=-1) )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalidchars.webSiteUrl"));
            
            if ( !StringUtils.isEmpty(this.state) && this.state.length() > 100 )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.stateprovince"));
            
            if (this.isBlankString(this.isFinanceInfoPublic))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.isFinanceInfoPublic"));
            }

            if (this.isBlankString(this.state))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.state"));
            }

            if (this.isBlankString(this.country))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.country"));
            }

            if (StringFuncs.NullValueReplacement(this.allianceWithOtherCarriers).equalsIgnoreCase("Y")
                && this.isBlankString(this.commentsAllianceWithOtherCarriers))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.commentsAllianceWithOtherCarriers"));
            }

            if ((!this.isSelectBoxPopulated(this.assignedArrIndustryFocus)) && (this.isBlankString(this.otherIndustryFocus)))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.industryFocus"));
            }

            if (!this.isSelectBoxPopulated(this.assignedArrRegions))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.regions"));
            }


            if(this.arrAlliancesWithOtherCarriers==null || this.arrAlliancesWithOtherCarriers.length==0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.compInfo.allianceCarriers"));
            }
            if(this.isBlankString(this.allianceWithOtherCarriers) == false && this.allianceWithOtherCarriers.length() > 500){
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.length.allianceWithOtherCarriers"));
            }

            if ((this.remitTo != null) && (this.remitTo.length() > 50))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitTo"));        
            
            if ((this.remitAddress1 != null) && (this.remitAddress1.length() > 200))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitAddress1"));
            
            if ((this.remitAddress2 != null) && (this.remitAddress2.length() > 200))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitAddress2"));
            
            if ((this.remitCity != null) && (this.remitCity.length() > 50))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitCity"));
            
            if ((this.remitCountry != null) && (this.remitCountry.length() > 50))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitCountry"));
            
            //New checks in the condition below added for Bug Fix# 7844 and 7850
            //Updated by Waseem. Postal code Field length is increased from 5 to 9 characters
            if ( !StringUtils.isEmpty(this.remitPostalCode) && this.remitPostalCode.length() > 9 )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitPostalCode"));
            else if ( !StringUtils.isEmpty(this.remitPostalCode) && !this.isAlphaNumeric((this.remitPostalCode) ) )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.format.remitPostalCode"));
            
            if ((this.remitState != null) && (this.remitState.length() > 50))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitState"));
            
            if ( (this.remitTo != null) && (this.remitTo.indexOf("<")!=-1 || this.remitTo.indexOf(">")!=-1) )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitTo"));
            
            if ((this.remitAddress1 != null) && (this.remitAddress1.indexOf("<")!=-1 || this.remitAddress1.indexOf(">")!=-1) )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitAddress1"));
            
            if ((this.remitAddress2 != null) && (this.remitAddress2.indexOf("<")!=-1 || this.remitAddress2.indexOf(">")!=-1))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitAddress2"));
            
            if ((this.remitCity != null) && (this.remitCity.indexOf("<")!=-1 || this.remitCity.indexOf(">")!=-1))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitCity"));
            
            if ((this.remitState != null) && (this.remitState.indexOf("<")!=-1 || this.remitState.indexOf(">")!=-1))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitState"));        
            
            if ((this.remitCountry != null) && (this.remitCountry.indexOf("<")!=-1 || this.remitCountry.indexOf(">")!=-1))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitCountry"));
            
            
            try
            {
                this.setAssignedRegions(AllianceCompInfoManager.getAllianceRegionsFromArray(this.getAssignedArrRegions()));
                this.setAvailableRegions(AllianceCompInfoManager.getAvailableRegionsFromArray(this.getAssignedArrRegions()));
                this.setAssignedIndustryFocus(AllianceCompInfoManager.getAssignedIndFocusFromArray(this.getAssignedArrIndustryFocus()));
                this.setAvailableIndustryFocus(AllianceCompInfoManager.getAvailableIndFocusFromArray(this.getAssignedArrIndustryFocus()));
                this.setAssignedPlatforms(AllianceCompInfoManager.getAssignedPlatformsFromArray(this.getAssignedArrPlatforms()));
                this.setAvailablePlatforms(AllianceCompInfoManager.getAvailablePlatformsFromArray(this.getAssignedArrPlatforms()));


                request.setAttribute("aimsCarriers", this.getAimsCarriersList());
                request.setAttribute("availablePlatforms", AllianceCompInfoManager.getAllAvailablePlatforms());
                request.setAttribute("countryList", this.getCountriesList());
            }

            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }

        }
        return errors;
    }

    public java.lang.String getStatus()
    {
        return status;
    }

    public void setStatus(java.lang.String string)
    {
        status = string;
    }


    private Collection getAimsCarriersList() {
        Collection allAimsCarriersList = null;
        try {
            allAimsCarriersList = AllianceCompInfoManager.getAllAimsCarriers();
        } catch (HibernateException e) {
            e.printStackTrace(); 
        }
        return allAimsCarriersList;
    }

    private Collection getCountriesList() {
        Collection countryList = null;
        try {
            countryList = AllianceCompInfoManager.getAllCountries();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return countryList;
    }

    public Long[] getArrAlliancesWithOtherCarriers() {
        return arrAlliancesWithOtherCarriers;
    }

    public void setArrAlliancesWithOtherCarriers(Long[] arrAlliancesWithOtherCarriers) {
        this.arrAlliancesWithOtherCarriers = arrAlliancesWithOtherCarriers;
    }

    public Collection getAssigendAlliancesWithOtherCarriers() {
        return assigendAlliancesWithOtherCarriers;
    }

    public void setAssigendAlliancesWithOtherCarriers(Collection assigendAlliancesWithOtherCarriers) {
        this.assigendAlliancesWithOtherCarriers = assigendAlliancesWithOtherCarriers;
    }


    public java.lang.String getRemitTo() {
		return remitTo;
	}

	public void setRemitTo(java.lang.String remitTo) {
		this.remitTo = remitTo;
	}

	public java.lang.String getRemitAddress1() {
		return remitAddress1;
	}

	public void setRemitAddress1(java.lang.String remitAddress1) {
		this.remitAddress1 = remitAddress1;
	}

	public java.lang.String getRemitAddress2() {
		return remitAddress2;
	}

	public void setRemitAddress2(java.lang.String remitAddress2) {
		this.remitAddress2 = remitAddress2;
	}

	public java.lang.String getRemitCity() {
		return remitCity;
	}

	public void setRemitCity(java.lang.String remitCity) {
		this.remitCity = remitCity;
	}

	public java.lang.String getRemitState() {
		return remitState;
	}

	public void setRemitState(java.lang.String remitState) {
		this.remitState = remitState;
	}

	public java.lang.String getRemitPostalCode() {
		return remitPostalCode;
	}

	public void setRemitPostalCode(java.lang.String remitPostalCode) {
		this.remitPostalCode = remitPostalCode;
	}

	public java.lang.String getRemitCountry() {
		return remitCountry;
	}

	public void setRemitCountry(java.lang.String remitCountry) {
		this.remitCountry = remitCountry;
	}    
}

