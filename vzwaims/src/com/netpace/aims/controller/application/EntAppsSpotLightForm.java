package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import java.util.*;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.bo.application.EntAppsSpotlightsManager;

import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import org.apache.log4j.Logger;

/**
 * @struts.form name="EntAppsSpotLightForm"
 */
public class EntAppsSpotLightForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(EntAppsSpotLightForm.class.getName());
   
	private	 java.lang.Long 	spotlightId;
	private	 java.lang.String 	spotlightName;
	private	 java.lang.String 	spotlightDesc;
	private	 java.lang.Long 	allianceId;
	private	 java.lang.Long 	enterpriseAppsId;
	private	 java.lang.String 	createdBy;
	private	 java.util.Date 	createdDate;
	private	 FormFile			spotlightFile;
	private	 java.lang.String 	spotlightFileFileName;
	private	 java.lang.String 	spotlightFileContentType;
	private	 java.lang.String 	status;
	private	 java.lang.Long 	spotlightTypeId;

	public  java.lang.Long  getSpotlightId() {
        return this.spotlightId;
    }

    public void setSpotlightId( java.lang.Long  spotlightId) {
        this.spotlightId = spotlightId;
    }

	public  java.lang.String  getSpotlightName() {
        return this.spotlightName;
    }

    public void setSpotlightName( java.lang.String  spotlightName) {
        this.spotlightName = spotlightName;
    }

	public  java.lang.String  getSpotlightDesc() {
        return this.spotlightDesc;
    }

    public void setSpotlightDesc( java.lang.String  spotlightDesc) {
        this.spotlightDesc = spotlightDesc;
    }

	public java.lang.Long getAllianceId() {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId) {
        this.allianceId = allianceId;
    }
    
	public java.lang.Long getEnterpriseAppsId() {
        return this.enterpriseAppsId;
    }

    public void setEnterpriseAppsId(java.lang.Long inputId) {
        this.enterpriseAppsId = inputId;
    }

	public  java.lang.String  getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy( java.lang.String  createdBy) {
        this.createdBy = createdBy;
    }

	public  java.util.Date  getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate( java.util.Date  createdDate) {
        this.createdDate = createdDate;
    }

	public  FormFile getSpotlightFile() {
        return this.spotlightFile;
    }

    public void setSpotlightFile( FormFile spotlightFile) {
        this.spotlightFile = spotlightFile;
    }

	public  java.lang.String  getSpotlightFileFileName() {
        return this.spotlightFileFileName;
    }

    public void setSpotlightFileFileName( java.lang.String  spotlightFileFileName) {
        this.spotlightFileFileName = spotlightFileFileName;
    }

	public  java.lang.String  getSpotlightFileContentType() {
        return this.spotlightFileContentType;
    }

    public void setSpotlightFileContentType( java.lang.String  spotlightFileContentType) {
        this.spotlightFileContentType = spotlightFileContentType;
    }

	public  java.lang.String  getStatus() {
        return this.status;
    }

    public void setStatus( java.lang.String  status) {
        this.status = status;
    }

	public  java.lang.Long  getSpotlightTypeId() {
        return this.spotlightTypeId;
    }

    public void setSpotlightTypeId( java.lang.Long  spotlightTypeId) {
        this.spotlightTypeId = spotlightTypeId;
    }


	public void reset (ActionMapping mapping, HttpServletRequest request)
	{
      
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = user.getAimsAllianc();
		Object [] userValues = null;
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		Collection AllianceSpotLightInfo = null;
		Long spotLightId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("spotlightId"),"0"));
		Long spotLightTypeId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("spotLightTypeId"),"0"));

        if (task_name.equalsIgnoreCase("editForm") )
        {			
            try
            {
				AllianceSpotLightInfo = EntAppsSpotlightsManager.getSpotLightInfo(spotLightId);

				for (Iterator iter = AllianceSpotLightInfo.iterator(); iter.hasNext();) 
				{
					userValues  = (Object []) iter.next();
					
					this.setSpotlightId(spotLightId);
					this.setSpotlightTypeId(spotLightTypeId);
					//this.setEnterpriseAppsId(alliance_id);
					this.setSpotlightName( (String) userValues[0]);
					this.setSpotlightDesc((String) userValues[1]);
					this.setSpotlightFileFileName((String) userValues[2]);
					this.setSpotlightFileContentType((String) userValues[3]);
					this.setStatus((String) userValues[4]);


				}		

			}
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }            
	

        }		
		
	}


	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		log.debug("\n\nIn Validate of EntAppsSpotLightForm \n\n");	

		ActionErrors errors = new ActionErrors();

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
 
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = user.getAimsAllianc();
		Collection AimsSpotlightTypes = null;

        try
        {	
				
		    AimsSpotlightTypes = EntAppsSpotlightsManager.getSpotlightTypes();
        }
        catch (HibernateException he)
        {
            log.debug("A hibernate exception occured!");
        }          

		request.setAttribute("AimsSpotlightTypes", AimsSpotlightTypes);

        if (task_name.equalsIgnoreCase("create") || task_name.equalsIgnoreCase("edit"))
        {
			if (this.isBlankString(spotlightName))
			{
                log.debug("EntAppsSpotLightForm Blank spotlightName");
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.SpotLightForm.spotlightName"));
			}
		
			if ( ((this.spotlightFile == null) || !(this.spotlightFile.getFileSize()>0)) && (this.isBlankString(this.spotlightFileFileName)) )
			{				
				log.debug("AllianceSpotLightForm NO spotlightFile");
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.SpotLightForm.spotlightFile"));
			}
			    
			if(this.spotlightDesc!=null && this.spotlightDesc.length() > 500)
		    {
				log.debug("Description lengeth is greater tha 500");
		        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.length.SpotLightForm.spotlightDescription"));
		     }		
			
			
		}
      
		return errors;
	}


}