package com.netpace.aims.controller.alliance;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.alliance.VZWAllianceManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Iterator;


/**
 * This class takes care of action for display of update form of Journal Entry
 *
 * @struts.action path="/allianceJournalEntrySetup"  
 *                name="AllianceJournalEntryUpdateForm" 
 *                scope="request"
 *				  validate="false"  
 * @struts.action-forward name="update" path="/alliance/journalEntryUpdate.jsp"
 * @author Adnan Makda
 */
public class AllianceJournalEntrySetupAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceJournalEntrySetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        
        String dateFormat = this.getResources(request).getMessage("date.format");
        String forward = "view";
        String company_name = "";
		String taskname= "";
		String pageNumber= "";
		Object o_param;
        Long alliance_id = null;

		HttpSession session = request.getSession(); 

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
                                                                AimsSecurityManager.SELECT))) 
            {
                throw new AimsSecurityException(); 
            }
        }

        AllianceJournalEntryUpdateForm journalEntryUpdateForm = (AllianceJournalEntryUpdateForm) form;	

        if (StringFuncs.NullValueReplacement(request.getParameter("alliance_id")).length() > 0)
        {
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        }
        else
        {
            alliance_id = journalEntryUpdateForm.getAimsAllianceId();
        }       
        
        company_name = StringFuncs.NullValueReplacement(AllianceManager.getAllianceCompanyName(alliance_id));        
        journalEntryUpdateForm.setCompanyName(company_name);
				
        journalEntryUpdateForm.setAimsAllianceId(alliance_id);					
        journalEntryUpdateForm.setTask("create");	

		o_param = request.getParameter("task"); 

      	log.debug(o_param);
      	if (o_param != null )
      	{
			taskname =  request.getParameter("task");   
			request.setAttribute("task", (String)o_param);   					
      	}

      
        if ( (taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("view")) )
            {	

                journalEntryUpdateForm.setJournalType("PR");    
                Collection journalEntries = null;					
                StringBuffer journalCombinedText = new StringBuffer();
                
                try
                {
                    journalEntries = VZWAllianceManager.getJournalEntries(alliance_id);
                }
                catch(Exception ex)
                {
                    log.debug("Exception in AllianceJournalEntrySetupAction");
                }
                
                for (Iterator it = journalEntries.iterator(); it.hasNext();) 
                {
                    AimsJournalEntry aimsJournalEntry = (AimsJournalEntry) it.next();
                    if (aimsJournalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PRIVATE))
                    		journalCombinedText.append("[Private] ");
                    else if (aimsJournalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PUBLIC))
                    		journalCombinedText.append("[Public] ");
                    journalCombinedText.append(Utility.convertToString(aimsJournalEntry.getCreatedDate(), AimsConstants.DATE_TIME_FORMAT));
                    journalCombinedText.append(": (");
                    journalCombinedText.append(aimsJournalEntry.getCreatedBy());
                    journalCombinedText.append(")\n");
                    journalCombinedText.append(aimsJournalEntry.getJournalText());
                    journalCombinedText.append("\n\n");		 				
                }		
                                           

                journalEntryUpdateForm.setJournalCombinedText(journalCombinedText.toString());
                journalEntryUpdateForm.setJournalText("");	
                
                forward = "update";           
                
            } 
            
            
        return mapping.findForward(forward);
        }

  		
}
