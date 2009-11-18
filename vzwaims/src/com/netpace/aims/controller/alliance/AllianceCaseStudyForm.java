package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import java.util.*;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.bo.alliance.*;

import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import org.apache.log4j.Logger;

/**
 * @struts.form name="AllianceCaseStudyForm"
 */
public class AllianceCaseStudyForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(AllianceCaseStudyForm.class.getName());
   
	private	 java.lang.Long 	caseStudyId;
	private	 java.lang.String 	caseStudyName;
	private	 java.lang.String 	caseStudyDesc;
	private	 java.lang.String 	status;
	private	 java.util.Date 	createdDate;
	private	 java.util.Date 	acceptDeclineDate;
	private	 java.lang.String 	comments;
	private	 FormFile			caseStudyDoc;
	private	 java.lang.String 	caseStudyDocFileName;
	private	 java.lang.String 	caseStudyDocContentType;
	private	 java.lang.Long 	appsId;
	private	 java.lang.Long 	allianceId;
	private	 java.lang.String 	acceptDeclineContactFirstName;
	private	 java.lang.String 	acceptDeclineContactLastName;
	private	 java.lang.String 	createdContactFirstName;
	private	 java.lang.String 	createdContactLastName;
    private	 java.lang.String 	showAcceptRejectButton;

	


	public  java.lang.Long  getCaseStudyId() {
        return this.caseStudyId;
    }

    public void setCaseStudyId( java.lang.Long  caseStudyId) {
        this.caseStudyId = caseStudyId;
    }

	public  java.lang.String  getCaseStudyName() {
        return this.caseStudyName;
    }

    public void setCaseStudyName( java.lang.String  caseStudyName) {
        this.caseStudyName = caseStudyName;
    }

	public  java.lang.String  getCaseStudyDesc() {
        return this.caseStudyDesc;
    }

    public void setCaseStudyDesc( java.lang.String  caseStudyDesc) {
        this.caseStudyDesc = caseStudyDesc;
    }

	public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }

	public  java.util.Date  getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate( java.util.Date  createdDate) {
        this.createdDate = createdDate;
    }

	public  java.util.Date  getAcceptDeclineDate() {
        return this.acceptDeclineDate;
    }

    public void setAcceptDeclineDate( java.util.Date  acceptDeclineDate) {
        this.acceptDeclineDate = acceptDeclineDate;
    }

	public  java.lang.String  getComments() {
        return this.comments;
    }

    public void setComments( java.lang.String  comments) {
        this.comments = comments;
    }

	public  FormFile getCaseStudyDoc() {
        return this.caseStudyDoc;
    }

    public void setCaseStudyDoc( FormFile caseStudyDoc) {
        this.caseStudyDoc = caseStudyDoc;
    }

	public  java.lang.String  getCaseStudyDocFileName() {
        return this.caseStudyDocFileName;
    }

    public void setCaseStudyDocFileName( java.lang.String  caseStudyDocFileName) {
        this.caseStudyDocFileName = caseStudyDocFileName;
    }

	public  java.lang.String  getCaseStudyDocContentType() {
        return this.caseStudyDocContentType;
    }

    public void setCaseStudyDocContentType( java.lang.String  caseStudyDocContentType) {
        this.caseStudyDocContentType = caseStudyDocContentType;
    }

	public  java.lang.Long  getAppsId() {
        return this.appsId;
    }

    public void setAppsId( java.lang.Long  appsId) {
        this.appsId = appsId;
    }

	public  java.lang.Long  getAllianceId() {
        return this.allianceId;
    }

    public void setAllianceId( java.lang.Long  allianceId) {
        this.allianceId = allianceId;
    }

	public  java.lang.String  getAcceptDeclineContactFirstName() {
        return this.acceptDeclineContactFirstName;
    }

    public void setAcceptDeclineContactFirstName( java.lang.String  acceptDeclineContactFirstName) {
        this.acceptDeclineContactFirstName = acceptDeclineContactFirstName;
    }

	public  java.lang.String  getAcceptDeclineContactLastName() {
        return this.acceptDeclineContactLastName;
    }

    public void setAcceptDeclineContactLastName( java.lang.String  acceptDeclineContactLastName) {
        this.acceptDeclineContactLastName = acceptDeclineContactLastName;
    }

	public  java.lang.String  getCreatedContactFirstName() {
        return this.createdContactFirstName;
    }

    public void setCreatedContactFirstName( java.lang.String  createdContactFirstName) {
        this.createdContactFirstName = createdContactFirstName;
    }

	public  java.lang.String  getCreatedContactLastName() {
        return this.createdContactLastName;
    }

    public void setCreatedContactLastName( java.lang.String  createdContactLastName) {
        this.createdContactLastName = createdContactLastName;
	}

	public  java.lang.String  getShowAcceptRejectButton() {
        return this.showAcceptRejectButton;
    }

    public void setShowAcceptRejectButton( java.lang.String  showAcceptRejectButton) {
        this.showAcceptRejectButton = showAcceptRejectButton;
    }

	public void reset (ActionMapping mapping, HttpServletRequest request)
	{

		Collection AimsCaseStudy = null;
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
		Long caseStudyId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("caseStudyId"),"0"));
		Object [] userValues = null;
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (task_name.equalsIgnoreCase("editForm") ||  task_name.equalsIgnoreCase("editFormView"))
        {			
            try
            {

				AimsCaseStudy = AimsCaseStudiesManager.getCaseStudy(caseStudyId);
								
				for (Iterator iter = AimsCaseStudy.iterator(); iter.hasNext();) 
				{
					userValues  = (Object []) iter.next();

					this.setCaseStudyId((Long) userValues[0]);
					this.setCaseStudyName((String) userValues[1]);
					this.setCaseStudyDesc((String) userValues[2]);
					this.setStatus((String) userValues[3]);		

                    if (this.getStatus().equalsIgnoreCase("NEW"))
                    {
                        this.setShowAcceptRejectButton("Y");
                    }
                    else 
                    {
                        this.setShowAcceptRejectButton("N");
                    }

					this.setCreatedDate((Date) userValues[4]);
					this.setAcceptDeclineDate((Date) userValues[5]);
					this.setComments((String) userValues[6]);
					this.setCaseStudyDocFileName((String) userValues[7]);
					this.setCaseStudyDocContentType((String) userValues[8]);
					this.setAppsId((Long) userValues[9]);
					this.setAllianceId((Long) userValues[10]);
				}		

			}
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }            
	

        }
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		log.debug("\n\nIn Validate of AllianceSpotLightForm \n\n");	

		ActionErrors errors = new ActionErrors();

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
 
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = user.getAimsAllianc();
		Collection AimsSpotlightTypes = null;

       

        if (task_name.equalsIgnoreCase("create") || task_name.equalsIgnoreCase("edit"))
        {
			if (this.isBlankString(caseStudyName))
			{
                log.debug("AllianceCaseStudyForm Blank spotlightName");
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.SpotLightForm.spotlightName"));
			}
		
			if ( ((this.caseStudyDoc == null) || !(this.caseStudyDoc.getFileSize()>0)) && (this.isBlankString(this.caseStudyDocFileName)) )
			{				
				log.debug("AllianceCaseStudyForm NO caseStudyDoc");
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.SpotLightForm.spotlightFile"));
			}
		}	
		return errors;
	}



}

