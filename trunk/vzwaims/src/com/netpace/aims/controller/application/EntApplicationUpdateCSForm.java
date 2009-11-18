package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.Set;
import org.apache.log4j.Logger;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.masters.*;

/**
 * @struts.form name="EntApplicationUpdateCSForm"
 */
public class EntApplicationUpdateCSForm extends EntApplicationUpdateForm {

    static Logger log = Logger.getLogger(EntApplicationUpdateCSForm.class.getName());


		/* RESET FUNCITON */

		public void reset (ActionMapping mapping, HttpServletRequest request)   {

			super.reset(mapping,request);

		}

		public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	{

			ActionErrors errors	=	new	ActionErrors();
			HttpSession session = request.getSession();

			
			if ( request.getParameter("cstask") != null && request.getParameter("cstask").equals("delete"))
			{
				boolean bFoundOneToDelete = false;
				java.util.HashSet csHS = (java.util.HashSet) session.getAttribute("AIMS_APP_CASE_STUDIES");
				if ( (csHS != null) && (csCaseStudyId != null) )
				{
					for (int iCount = 0 ; iCount < csCaseStudyId.length ; iCount++)
					{
						for(java.util.Iterator itr = csHS.iterator(); itr.hasNext() ;)
						{
							AimsAppsCaseStudyExt acs = (AimsAppsCaseStudyExt) itr.next();
							if ( acs.getCaseStudyIdHash().equals(csCaseStudyId[iCount]))
							{
								csHS.remove(acs);
								bFoundOneToDelete = true;
								break;
							}
						}
					}
				}
				
				if (!bFoundOneToDelete)
				{
					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csNoRecordRemoved"));
					return errors;
				}

			}
			else
			{
				if (this.isBlankString(this.csCustomerName))
						errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csCustomerName"));
	
				if (this.isBlankString(this.csProblemStatement))
						errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csProblemStatement"));
	
				if (this.isBlankString(this.csBusinessBenifit))
						errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csBusinessBenifit"));
	
				if (this.isBlankString(this.csProductionLaunchDate))
						errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csProductionLaunchDate"));
	
				if ( this.csTotalEndUsers.intValue() == 0 )
						errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csTotalEndUsers"));
	
				if ( this.csNoOfUsersAccess.intValue() == 0 )
						errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csNoOfUsersAccess"));
	
		
				java.util.Date date = null;
	
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
	
					try {
						date = df.parse(csProductionLaunchDate);
					} catch (Exception ex) {
						errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csNotValidDate"));
					}
	
				if (errors.isEmpty())
				{
					AimsAppsCaseStudyExt acs = new AimsAppsCaseStudyExt();
					java.util.HashSet csHS = (java.util.HashSet) session.getAttribute("AIMS_APP_CASE_STUDIES");
					if (csHS == null) csHS = new java.util.HashSet();
					acs.setCustomerName(csCustomerName);
					acs.setProblemStatement(csProblemStatement);
					acs.setBusinessBenifit(csBusinessBenifit);
					acs.setProductionLaunchDate(date);
					acs.setNumUsers(csTotalEndUsers);
					acs.setNumWirelessUsers(csNoOfUsersAccess);
					csHS.add(acs);
					session.setAttribute("AIMS_APP_CASE_STUDIES",csHS);
					this.allCaseStudies = csHS;
					csCustomerName = null;
					csProblemStatement = null;
					csBusinessBenifit = null;
					csProductionLaunchDate = null;
					csTotalEndUsers = null;
					csNoOfUsersAccess = null;
					csPlatformId = null;
					
					//errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.enterprise.app.csRecordAdded"));
				}
			}
			return errors;

		}

}

