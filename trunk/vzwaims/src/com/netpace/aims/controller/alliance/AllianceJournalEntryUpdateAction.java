package com.netpace.aims.controller.alliance;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/allianceJournalEntryUpdate"
 *                name="AllianceJournalEntryUpdateForm"
 *                scope="request"
 *                input="/alliance/journalEntryUpdate.jsp"
 *				  validate="true"
 * @struts.action-forward name="update" path="/allianceJournalEntrySetup.do"
 * @author Adnan Makda
 */
 
public class AllianceJournalEntryUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceJournalEntryUpdateAction.class.getName());

		public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

				HttpSession session = request.getSession();
				String dateFormat = this.getResources(request).getMessage("date.format");
                String forward = "update";
                boolean errorFound = false;
				String taskname="";

                AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
                String user_name = user.getUsername();
                String user_type = user.getUserType();
                Long user_id = user.getUserId();

                if (user_type.equalsIgnoreCase("A"))
                {
                    throw new AimsSecurityException(); 
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, 
                                                                        AimsSecurityManager.UPDATE))) 
                    {
                        throw new AimsSecurityException(); 
                    }
                }

				Object o_param = request.getParameter("task");
                if (o_param != null )
                {
                    taskname =  request.getParameter("task");
                    request.setAttribute("task", (String)o_param);
                }
     

                log.debug("TASK is:  " + taskname);
				
                AimsJournalEntry aimsJournalEntry = null;
                String currUser = ((AimsUser)(session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
				
				
				AllianceJournalEntryUpdateForm journalEntryUpdateForm = (AllianceJournalEntryUpdateForm) form;

                String journalEntryText = StringFuncs.trim(journalEntryUpdateForm.getJournalText());
                Long allianceId = journalEntryUpdateForm.getAimsAllianceId();

				if (taskname.equalsIgnoreCase("create"))
				{
					aimsJournalEntry = new AimsJournalEntry();
					aimsJournalEntry.setJournalText(journalEntryText);
					aimsJournalEntry.setJournalType(journalEntryUpdateForm.getJournalType());
					aimsJournalEntry.setCreatedBy(currUser);
         	        aimsJournalEntry.setCreatedDate(new Date());         	        
         	        aimsJournalEntry.setAimsAllianceId(allianceId);
          	
        	        try
					{						
						AimsApplicationsManager.saveOrUpdateJournalEntries(aimsJournalEntry);						
					}
	                catch(Exception ex)
					{						
						log.debug("Exception in AllianceJournalEntryUpdateAction");
					}

                    journalEntryUpdateForm.setJournalText("");

				}

				return mapping.findForward(forward);

  	}
  
  
}

