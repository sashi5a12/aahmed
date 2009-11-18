package com.netpace.aims.controller.alliance;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.alliance.AllianceBusInfoManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.*;

/**
 * @struts.form name="AllianceBusInfoForm"
 */
public class AllianceBusInfoForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(AllianceBusInfoForm.class.getName());

    private java.lang.Long allianceId;
    private java.lang.String companyName;
    private java.lang.String dateEstablished;
    private java.lang.String fullTimeEmployees;
    private java.lang.String partTimeEmployees;
    private java.lang.String insideSalesEmployees;
    private java.lang.String outsideSalesEmployees;
    private java.lang.String retailSalesEmployees;
    private java.lang.String teleMarketingEmployees;
    private java.lang.String technicianEmployees;
    private java.lang.String warehouseEmployees;
    private java.lang.String supportingEmployees;
    private java.lang.String otherEmployees;
    private java.lang.String countryOfOrigin;
    private java.lang.String prodServicesDesc;
    private java.lang.String competitiveAdvantages;
    private FormFile companyPresentation;
    private java.lang.String companyPresentationFileName;
    private java.lang.String companyPresentationContentType;
    private java.lang.Long companyPresentationTempFileId;
    private FormFile companyLogo;
    private java.lang.String companyLogoFileName;
    private java.lang.String companyLogoContentType;
    private java.lang.Long companyLogoTempFileId;
    private FormFile progGuide;
    private java.lang.String progGuideFileName;
    private java.lang.String progGuideContentType;
    private java.lang.Long progGuideTempFileId;
    private java.lang.String[] availableArrRolesOfAlliance;
    private java.lang.String[] assignedArrRolesOfAlliance;
    private java.lang.String[] availableArrDevTechnologies;
    private java.lang.String[] assignedArrDevTechnologies;
    private java.lang.String[] availableArrLinesOfBusiness;
    private java.lang.String[] assignedArrLinesOfBusiness;

    private java.util.Collection availableLinesOfBusiness;
    private java.util.Collection assignedLinesOfBusiness;
    private java.util.Collection availableDevTechnologies;
    private java.util.Collection assignedDevTechnologies;
    private java.util.Collection availableRoles;
    private java.util.Collection assignedRoles;

    private java.lang.String employeesRange;
    private java.lang.String partner;

    private java.lang.Long[] arrAllianceFinancing;

    private java.util.Collection allDevelopments;
    private java.lang.Long[] arrAllianceDevelopments;
    private java.lang.String outsourceDevelopmentPublisherName;

    private java.util.Collection allDevelopmentTechnologies;
    private java.lang.String[] developmentTechnologies;

    private java.util.Collection assignedAllianceFinancing;
    private java.util.Collection assignedAllianceDevelopments;


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

    public java.lang.String getDateEstablished()
    {
        return this.dateEstablished;
    }

    public void setDateEstablished(java.lang.String dateEstablished)
    {
        this.dateEstablished = dateEstablished;
    }

    public java.lang.String getFullTimeEmployees()
    {
        return this.fullTimeEmployees;
    }

    public void setFullTimeEmployees(java.lang.String fullTimeEmployees)
    {
        this.fullTimeEmployees = fullTimeEmployees;
    }

    public java.lang.String getPartTimeEmployees()
    {
        return this.partTimeEmployees;
    }

    public void setPartTimeEmployees(java.lang.String partTimeEmployees)
    {
        this.partTimeEmployees = partTimeEmployees;
    }

    public java.lang.String getInsideSalesEmployees()
    {
        return this.insideSalesEmployees;
    }

    public void setInsideSalesEmployees(java.lang.String insideSalesEmployees)
    {
        this.insideSalesEmployees = insideSalesEmployees;
    }

    public java.lang.String getOutsideSalesEmployees()
    {
        return this.outsideSalesEmployees;
    }

    public void setOutsideSalesEmployees(java.lang.String outsideSalesEmployees)
    {
        this.outsideSalesEmployees = outsideSalesEmployees;
    }

    public java.lang.String getRetailSalesEmployees()
    {
        return this.retailSalesEmployees;
    }

    public void setRetailSalesEmployees(java.lang.String retailSalesEmployees)
    {
        this.retailSalesEmployees = retailSalesEmployees;
    }

    public java.lang.String getTeleMarketingEmployees()
    {
        return this.teleMarketingEmployees;
    }

    public void setTeleMarketingEmployees(java.lang.String teleMarketingEmployees)
    {
        this.teleMarketingEmployees = teleMarketingEmployees;
    }

    public java.lang.String getTechnicianEmployees()
    {
        return this.technicianEmployees;
    }

    public void setTechnicianEmployees(java.lang.String technicianEmployees)
    {
        this.technicianEmployees = technicianEmployees;
    }

    public java.lang.String getWarehouseEmployees()
    {
        return this.warehouseEmployees;
    }

    public void setWarehouseEmployees(java.lang.String warehouseEmployees)
    {
        this.warehouseEmployees = warehouseEmployees;
    }

    public java.lang.String getSupportingEmployees()
    {
        return this.supportingEmployees;
    }

    public void setSupportingEmployees(java.lang.String supportingEmployees)
    {
        this.supportingEmployees = supportingEmployees;
    }

    public java.lang.String getOtherEmployees()
    {
        return this.otherEmployees;
    }

    public void setOtherEmployees(java.lang.String otherEmployees)
    {
        this.otherEmployees = otherEmployees;
    }

    public java.lang.String getCountryOfOrigin()
    {
        return this.countryOfOrigin;
    }

    public void setCountryOfOrigin(java.lang.String countryOfOrigin)
    {
        this.countryOfOrigin = countryOfOrigin;
    }

    public java.lang.String getProdServicesDesc()
    {
        return this.prodServicesDesc;
    }

    public void setProdServicesDesc(java.lang.String prodServicesDesc)
    {
        this.prodServicesDesc = prodServicesDesc;
    }

    public java.lang.String getCompetitiveAdvantages()
    {
        return this.competitiveAdvantages;
    }

    public void setCompetitiveAdvantages(java.lang.String competitiveAdvantages)
    {
        this.competitiveAdvantages = competitiveAdvantages;
    }

    public FormFile getCompanyPresentation()
    {
        return this.companyPresentation;
    }

    public void setCompanyPresentation(FormFile companyPresentation)
    {
        this.companyPresentation = companyPresentation;
    }

    public java.lang.String getCompanyPresentationFileName()
    {
        return this.companyPresentationFileName;
    }

    public void setCompanyPresentationFileName(java.lang.String companyPresentationFileName)
    {
        this.companyPresentationFileName = companyPresentationFileName;
    }

    public java.lang.String getCompanyPresentationContentType()
    {
        return this.companyPresentationContentType;
    }

    public void setCompanyPresentationContentType(java.lang.String companyPresentationContentType)
    {
        this.companyPresentationContentType = companyPresentationContentType;
    }

    public FormFile getCompanyLogo()
    {
        return this.companyLogo;
    }

    public void setCompanyLogo(FormFile companyLogo)
    {
        this.companyLogo = companyLogo;
    }

    public java.lang.String getCompanyLogoFileName()
    {
        return this.companyLogoFileName;
    }

    public void setCompanyLogoFileName(java.lang.String companyLogoFileName)
    {
        this.companyLogoFileName = companyLogoFileName;
    }

    public java.lang.String getCompanyLogoContentType()
    {
        return this.companyLogoContentType;
    }

    public void setCompanyLogoContentType(java.lang.String companyLogoContentType)
    {
        this.companyLogoContentType = companyLogoContentType;
    }

    /**
     * @return Returns the progGuide.
     */
    public FormFile getProgGuide()
    {
        return progGuide;
    }
    /**
     * @param progGuide The progGuide to set.
     */
    public void setProgGuide(FormFile progGuide)
    {
        this.progGuide = progGuide;
    }
    /**
     * @return Returns the progGuideContentType.
     */
    public java.lang.String getProgGuideContentType()
    {
        return progGuideContentType;
    }
    /**
     * @param progGuideContentType The progGuideContentType to set.
     */
    public void setProgGuideContentType(java.lang.String progGuideContentType)
    {
        this.progGuideContentType = progGuideContentType;
    }
    /**
     * @return Returns the progGuideFileName.
     */
    public java.lang.String getProgGuideFileName()
    {
        return progGuideFileName;
    }
    /**
     * @param progGuideFileName The progGuideFileName to set.
     */
    public void setProgGuideFileName(java.lang.String progGuideFileName)
    {
        this.progGuideFileName = progGuideFileName;
    }

    public java.lang.String[] getAvailableArrRolesOfAlliance()
    {
        return this.availableArrRolesOfAlliance;
    }

    public void setAvailableArrRolesOfAlliance(java.lang.String[] availableArrRolesOfAlliance)
    {
        this.availableArrRolesOfAlliance = availableArrRolesOfAlliance;
    }

    public java.lang.String[] getAssignedArrRolesOfAlliance()
    {
        return this.assignedArrRolesOfAlliance;
    }

    public void setAssignedArrRolesOfAlliance(java.lang.String[] assignedArrRolesOfAlliance)
    {
        this.assignedArrRolesOfAlliance = assignedArrRolesOfAlliance;
    }

    public java.lang.String[] getAvailableArrDevTechnologies()
    {
        return this.availableArrDevTechnologies;
    }

    public void setAvailableArrDevTechnologies(java.lang.String[] availableArrDevTechnologies)
    {
        this.availableArrDevTechnologies = availableArrDevTechnologies;
    }

    public java.lang.String[] getAssignedArrDevTechnologies()
    {
        return this.assignedArrDevTechnologies;
    }

    public void setAssignedArrDevTechnologies(java.lang.String[] assignedArrDevTechnologies)
    {
        this.assignedArrDevTechnologies = assignedArrDevTechnologies;
    }

    public java.lang.String[] getAvailableArrLinesOfBusiness()
    {
        return this.availableArrLinesOfBusiness;
    }

    public void setAvailableArrLinesOfBusiness(java.lang.String[] availableArrLinesOfBusiness)
    {
        this.availableArrLinesOfBusiness = availableArrLinesOfBusiness;
    }

    public java.lang.String[] getAssignedArrLinesOfBusiness()
    {
        return this.assignedArrLinesOfBusiness;
    }

    public void setAssignedArrLinesOfBusiness(java.lang.String[] assignedArrLinesOfBusiness)
    {
        this.assignedArrLinesOfBusiness = assignedArrLinesOfBusiness;
    }

    public java.util.Collection getAssignedLinesOfBusiness()
    {
        return this.assignedLinesOfBusiness;
    }

    public void setAssignedLinesOfBusiness(java.util.Collection assignedLinesOfBusiness)
    {
        this.assignedLinesOfBusiness = assignedLinesOfBusiness;
    }

    public java.util.Collection getAvailableLinesOfBusiness()
    {
        return this.availableLinesOfBusiness;
    }

    public void setAvailableLinesOfBusiness(java.util.Collection availableLinesOfBusiness)
    {
        this.availableLinesOfBusiness = availableLinesOfBusiness;
    }

    public java.util.Collection getAssignedDevTechnologies()
    {
        return this.assignedDevTechnologies;
    }

    public void setAssignedDevTechnologies(java.util.Collection assignedDevTechnologies)
    {
        this.assignedDevTechnologies = assignedDevTechnologies;
    }

    public java.util.Collection getAvailableDevTechnologies()
    {
        return this.availableDevTechnologies;
    }

    public void setAvailableDevTechnologies(java.util.Collection availableDevTechnologies)
    {
        this.availableDevTechnologies = availableDevTechnologies;
    }

    public java.util.Collection getAssignedRoles()
    {
        return this.assignedRoles;
    }

    public void setAssignedRoles(java.util.Collection assignedRoles)
    {
        this.assignedRoles = assignedRoles;
    }

    public java.util.Collection getAvailableRoles()
    {
        return this.availableRoles;
    }

    public void setAvailableRoles(java.util.Collection availableRoles)
    {
        this.availableRoles = availableRoles;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //this.setOutsourceDevelopmentPublisherName("Name of Publisher");
        Collection AllianceBusInfo = null;
        Collection AllianceLinesOfBusiness = null;
        Collection AvailableLinesOfBusiness = null;
        Collection AllianceDevTechnologies = null;
        //Collection AvailableDevTechnologies = null;
        Collection AllianceRoles = null;
        Collection AvailableRoles = null;
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

        log.debug("request --> " + request);
        log.debug("request.getParameter(task) --> " + request.getParameter("task"));

        if (task_name.equalsIgnoreCase("editForm"))
        {
            try
            {
                AllianceLinesOfBusiness = AllianceBusInfoManager.getAllianceLinesOfBusiness(alliance_id, user_type);
                AvailableLinesOfBusiness = AllianceBusInfoManager.getAvailableLinesOfBusiness(alliance_id, user_type);
                AllianceDevTechnologies = AllianceBusInfoManager.getAllianceDevTechnologies(alliance_id, user_type);
                //AvailableDevTechnologies = AllianceBusInfoManager.getAvailableDevTechnologies(alliance_id, user_type);
                AllianceRoles = AllianceBusInfoManager.getAllianceRoles(alliance_id, user_type);
                AvailableRoles = AllianceBusInfoManager.getAvailableRoles(alliance_id, user_type);

                AllianceBusInfo = AllianceBusInfoManager.getAllianceBusInfo(alliance_id, user_type);

                for (Iterator iter = AllianceBusInfo.iterator(); iter.hasNext();)
                {
                    userValues = (Object[]) iter.next();

                    this.setAllianceId(alliance_id);
                    this.setCompanyName((String) userValues[0]);
                    this.setDateEstablished(Utility.convertToString(((Date) userValues[1]), AimsConstants.DATE_FORMAT));
                    this.setFullTimeEmployees((String) userValues[2]);
                    this.setPartTimeEmployees(StringFuncs.NullValueReplacement((Long) userValues[3]));
                    this.setInsideSalesEmployees(StringFuncs.NullValueReplacement((Long) userValues[4]));
                    this.setOutsideSalesEmployees(StringFuncs.NullValueReplacement((Long) userValues[5]));
                    this.setRetailSalesEmployees(StringFuncs.NullValueReplacement((Long) userValues[6]));
                    this.setTeleMarketingEmployees(StringFuncs.NullValueReplacement((Long) userValues[7]));
                    this.setTechnicianEmployees(StringFuncs.NullValueReplacement((Long) userValues[8]));
                    this.setWarehouseEmployees(StringFuncs.NullValueReplacement((Long) userValues[9]));
                    this.setSupportingEmployees(StringFuncs.NullValueReplacement((Long) userValues[10]));
                    this.setOtherEmployees(StringFuncs.NullValueReplacement((Long) userValues[11]));
                    this.setCountryOfOrigin((String) userValues[12]);
                    this.setProdServicesDesc((String) userValues[13]);
                    this.setCompetitiveAdvantages((String) userValues[14]);
                    this.setCompanyPresentationFileName((String) userValues[15]);
                    this.setCompanyPresentationContentType((String) userValues[16]);
                    this.setCompanyLogoFileName((String) userValues[17]);
                    this.setCompanyLogoContentType((String) userValues[18]);
                    this.setProgGuideFileName((String) userValues[19]);
                    this.setProgGuideContentType((String) userValues[20]);

                    this.setEmployeesRange ((String) userValues[21]);
                    this.setPartner((String) userValues[22]);
                    this.setOutsourceDevelopmentPublisherName ((String) userValues[23]);


                }
                this.setAssignedLinesOfBusiness(AllianceLinesOfBusiness);
                this.setAvailableLinesOfBusiness(AvailableLinesOfBusiness);

                this.setAssignedDevTechnologies(AllianceDevTechnologies);
                //this.setAvailableDevTechnologies(AvailableDevTechnologies);
                this.setAssignedArrDevTechnologies(AllianceBusInfoManager.getAllianceDevTechnologyIds(alliance_id, user_type));
                this.setAllDevelopmentTechnologies(AllianceBusInfoManager.getAllDevelopmentTechnologies());

                this.setAllDevelopments(AllianceBusInfoManager.getAllDevelopments());
                this.setArrAllianceDevelopments(AllianceBusInfoManager.getAllianceDevelopmentIds(alliance_id, user_type));

                this.setAssignedRoles(AllianceRoles);
                this.setAvailableRoles(AvailableRoles);

                this.setArrAllianceFinancing(AllianceBusInfoManager.getAllianceFinancingOptionIds(alliance_id, user_type));

                this.setAssignedAllianceFinancing(AllianceBusInfoManager.getAllianceFinancingOptions(alliance_id, user_type));
                this.setAssignedAllianceDevelopments(AllianceBusInfoManager.getAllianceDevelopments(alliance_id, user_type));

                request.setAttribute("numberOfEmployeesList", this.getNumberOfEmployeesList());
                request.setAttribute("financingList", this.getFinaningList());

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
                log.debug("\n\nIn Called edit in Reset of AllianceBusInfoForm \n\n" + new Date());

                this.setAssignedLinesOfBusiness(AllianceBusInfoManager.getAllianceLinesOfBusinessFromArray(this.getAssignedArrRolesOfAlliance()));
                this.setAvailableLinesOfBusiness(AllianceBusInfoManager.getAvailableLinesOfBusinessFromArray(this.getAssignedArrRolesOfAlliance()));

                this.setAssignedArrDevTechnologies(AllianceBusInfoManager.getAllianceDevTechnologyIds(alliance_id, user_type));
                this.setAllDevelopmentTechnologies(AllianceBusInfoManager.getAllDevelopmentTechnologies());

                this.setAssignedRoles(AllianceBusInfoManager.getAllianceRolesFromArray(this.getAssignedArrRolesOfAlliance()));
                this.setAvailableRoles(AllianceBusInfoManager.getAvailableRolesFromArray(this.getAssignedArrRolesOfAlliance()));

                this.setArrAllianceFinancing(AllianceBusInfoManager.getAllianceFinancingOptionIds(alliance_id, user_type));
                request.setAttribute("numberOfEmployeesList", this.getNumberOfEmployeesList());
                request.setAttribute("financingList", this.getFinaningList());


                this.setAllDevelopmentTechnologies(AllianceBusInfoManager.getAllDevelopmentTechnologies());
                this.setAllDevelopments(AllianceBusInfoManager.getAllDevelopments());

                this.setAssignedAllianceFinancing(AllianceBusInfoManager.getAllianceFinancingOptions(alliance_id, user_type));
                this.setAssignedAllianceDevelopments(AllianceBusInfoManager.getAllianceDevelopments(alliance_id, user_type));
            }

            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }

        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        log.debug("\n\nIn Validate of AllianceBusInfoForm \n\n");

        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        String sessionId = session.getId();
        String fileSize = (String) session.getAttribute(AimsConstants.FILE_SIZE);
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        TempFile tempFile = null;
        request.setAttribute("numberOfEmployeesList", this.getNumberOfEmployeesList());
        request.setAttribute("financingList", this.getFinaningList());

        //Saving Files into Temp Table
        try
        {
            if ((this.companyPresentation != null)
                && (this.companyPresentation.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.companyPresentation.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.filesize.note"));
                this.companyPresentation.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.companyPresentation, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.companyPresentationTempFileId = tempFile.getFileId();
                    this.companyPresentationFileName = tempFile.getFileName();
                }
            }

            if ((this.companyLogo != null) && (this.companyLogo.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.companyLogo.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.filesize.note"));
                this.companyLogo.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.companyLogo, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.companyLogoTempFileId = tempFile.getFileId();
                    this.companyLogoFileName = tempFile.getFileName();
                }
            }

            if (((this.progGuide != null) && (this.progGuide.getFileSize() > 0)) && !(this.isValidFileSize(fileSize, this.progGuide.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.filesize.note"));
                this.progGuide.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.progGuide, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.progGuideTempFileId = tempFile.getFileId();
                    this.progGuideFileName = tempFile.getFileName();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN validation() of AllianceBusInfoForm");
        }

        if (task_name.equalsIgnoreCase("edit"))
        {

            if (!this.isDate(this.dateEstablished))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.valid.date"));
            }

            if (this.isBlankString(countryOfOrigin))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.countryOfOrigin"));
            }

            if (!this.isSelectBoxPopulated(this.assignedArrRolesOfAlliance))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.rolesOfAlliance"));
            }

            if(this.assignedArrDevTechnologies!=null && this.assignedArrDevTechnologies.length==0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.devTechnologies"));
            }

            if(this.arrAllianceDevelopments!=null && this.arrAllianceDevelopments.length==0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.development"));
            }

            if (this.isBlankString(competitiveAdvantages))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.competitiveAdvantages"));
            }

            if (this.isBlankString(partner))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.partner"));
            }

            if (((this.companyPresentation == null) || !(this.companyPresentation.getFileSize() > 0))
                && (this.isBlankString(this.companyPresentationFileName)))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.companyPresentation"));
            }

            if (((this.companyLogo == null) || !(this.companyLogo.getFileSize() > 0)) && (this.isBlankString(this.companyLogoFileName)))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.companyLogo"));
            }

            if (this.progGuide != null)
            {
                if (!(this.isValidFileTypeWithName(this.progGuide.getFileName(), AimsConstants.FILE_TYPE_BREW_PROG_GUIDE)))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appProgGuide.file.type"));
                }
            }

            if (this.arrAllianceFinancing==null || this.arrAllianceFinancing.length==0)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.select.busInfo.financing"));
            }
            if (this.isBlankString(this.competitiveAdvantages)==false && this.competitiveAdvantages.length()>4000){
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.length.competitiveAdvantages"));
            }
            if (this.isBlankString(this.partner)==false && this.partner.length()>1000){
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.length.partner"));
            }
            if (this.isBlankString(this.outsourceDevelopmentPublisherName)==false && this.outsourceDevelopmentPublisherName.length()>1000){
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.length.outsourceDevPubName"));
            }
            try
            {
                this.setAssignedLinesOfBusiness(AllianceBusInfoManager.getAllianceLinesOfBusinessFromArray(this.getAssignedArrLinesOfBusiness()));
                this.setAvailableLinesOfBusiness(AllianceBusInfoManager.getAvailableLinesOfBusinessFromArray(this.getAssignedArrLinesOfBusiness()));
                this.setAllDevelopmentTechnologies(AllianceBusInfoManager.getAllDevelopmentTechnologies());
                this.setAllDevelopments(AllianceBusInfoManager.getAllDevelopments());
                
                this.setAssignedRoles(AllianceBusInfoManager.getAllianceRolesFromArray(this.getAssignedArrRolesOfAlliance()));
                this.setAvailableRoles(AllianceBusInfoManager.getAvailableRolesFromArray(this.getAssignedArrRolesOfAlliance()));
            }
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }

        }
        return errors;
    }

    /**
     * @return
     */
    public java.lang.Long getCompanyLogoTempFileId()
    {
        return companyLogoTempFileId;
    }

    /**
     * @return
     */
    public java.lang.Long getCompanyPresentationTempFileId()
    {
        return companyPresentationTempFileId;
    }

    /**
     * @return
     */
    public java.lang.Long getProgGuideTempFileId()
    {
        return progGuideTempFileId;
    }

    /**
     * @param long1
     */
    public void setCompanyLogoTempFileId(java.lang.Long long1)
    {
        companyLogoTempFileId = long1;
    }

    /**
     * @param long1
     */
    public void setCompanyPresentationTempFileId(java.lang.Long long1)
    {
        companyPresentationTempFileId = long1;
    }

    /**
     * @param long1
     */
    public void setProgGuideTempFileId(java.lang.Long long1)
    {
        progGuideTempFileId = long1;
    }

    public String getEmployeesRange() {
        return employeesRange;
    }

    public void setEmployeesRange(String employeesRange) {
        this.employeesRange = employeesRange;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Long[] getArrAllianceFinancing() {
        return arrAllianceFinancing;
    }

    public void setArrAllianceFinancing(Long[] arrAllianceFinancing) {
        this.arrAllianceFinancing = arrAllianceFinancing;
    }

    public Collection getAllDevelopmentTechnologies() {
        return allDevelopmentTechnologies;
    }

    public void setAllDevelopmentTechnologies(Collection allDevelopmentTechnologies) {
        this.allDevelopmentTechnologies = allDevelopmentTechnologies;
    }

    public String[] getDevelopmentTechnologies() {
        return developmentTechnologies;
    }

    public void setDevelopmentTechnologies(String[] developmentTechnologies) {
        this.developmentTechnologies = developmentTechnologies;
    }

    public Collection getAllDevelopments() {
        return allDevelopments;
    }

    public void setAllDevelopments(Collection allDevelopments) {
        this.allDevelopments = allDevelopments;
    }

    public Long[] getArrAllianceDevelopments() {
        return arrAllianceDevelopments;
    }

    public void setArrAllianceDevelopments(Long[] arrAllianceDevelopments) {
        this.arrAllianceDevelopments = arrAllianceDevelopments;
    }

    public String getOutsourceDevelopmentPublisherName() {
        return outsourceDevelopmentPublisherName;
    }

    public void setOutsourceDevelopmentPublisherName(String outsourceDevelopmentPublisherName) {
        this.outsourceDevelopmentPublisherName = outsourceDevelopmentPublisherName;
    }

    public Collection getAssignedAllianceFinancing() {
        return assignedAllianceFinancing;
    }

    public void setAssignedAllianceFinancing(Collection assignedAllianceFinancing) {
        this.assignedAllianceFinancing = assignedAllianceFinancing;
    }

    public Collection getAssignedAllianceDevelopments() {
        return assignedAllianceDevelopments;
    }

    public void setAssignedAllianceDevelopments(Collection assignedAllianceDevelopments) {
        this.assignedAllianceDevelopments = assignedAllianceDevelopments;
    }

    private Collection getNumberOfEmployeesList() {
        Collection numberOfEmployeesList = new ArrayList();
        numberOfEmployeesList.add(new NameValueBean("0-19", "0-19"));
        numberOfEmployeesList.add(new NameValueBean("20-49", "20-49"));
        numberOfEmployeesList.add(new NameValueBean("50-99", "50-99"));
        numberOfEmployeesList.add(new NameValueBean("100-199", "100-199"));
        numberOfEmployeesList.add(new NameValueBean("200-499", "200-499"));
        numberOfEmployeesList.add(new NameValueBean("More than 500", "More than 500"));
        return numberOfEmployeesList;
    }

    private Collection getFinaningList() {
        Collection aimsFinancingOptionsList = null;
        try {
            aimsFinancingOptionsList = AllianceBusInfoManager.getAllAimsFinancingOptions();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return aimsFinancingOptionsList;
    }

}
