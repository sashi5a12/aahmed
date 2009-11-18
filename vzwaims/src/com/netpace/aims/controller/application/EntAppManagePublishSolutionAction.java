package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsEnterpriseApp;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;


/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entAppManagePublishSolution"
 *                input="/application/entAppPublishSolution.jsp"
 * 				  name="EntAppPublishSolutionForm"	
 *                scope="request"
 *                validate="true"
 * @struts.action-forward name="view" path="/application/entAppPublishSolution.jsp"

 * @author Arsalan Qureshi
 */
public class EntAppManagePublishSolutionAction extends BaseAction {

	static Logger log= Logger.getLogger(EntAppManagePublishSolutionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		log.debug("EntAppSetupPublishSolutionAction.ActionForward : Start");
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String currUser = ((AimsUser) (request.getSession().getAttribute(AimsConstants.AIMS_USER))).getUsername();
		EntAppPublishSolutionForm entAppPublishSolutionForm=(EntAppPublishSolutionForm) form;
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		
		Collection publishSolutionRecord=null;
		Object[] userValues = null;
		Long[] marketSegments=null;
		EntAppPublishSolutionVO publishSolutionVO=null;
		List publishApps = new ArrayList();
		int PAGE_LENGTH =AimsConstants.JMA_PUPLISH_SOLUTION_RECORD_PER_PAGE_COUNT; //user.getRowsLength().intValue();
        int pageNo = 1;
        
        try
        {
            pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);
        }
        catch (Exception e)
        {
            pageNo = 1;
        }
		
        if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0)
        {
            entAppPublishSolutionForm.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "partner_name"));
        }
        else
        {
            entAppPublishSolutionForm.setFilterField(StringFuncs.initializeStringGetParam(entAppPublishSolutionForm.getFilterField(), "partner_name"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0)
        {
            entAppPublishSolutionForm.setFilterText(request.getParameter("filter_text"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("sort_field")).trim().length() > 0)
        {
            entAppPublishSolutionForm.setSortField(request.getParameter("sort_field"));
        }
		
		try{
			
			String search_expression=getFilterExpression(entAppPublishSolutionForm.getFilterField(), entAppPublishSolutionForm.getFilterText());
			String order_by=StringFuncs.initializeStringGetParam(entAppPublishSolutionForm.getSortField(), "2");//default company_name
			log.debug("order_by: " +order_by);	
				
			int rows = AimsEntAppsManager.countPublishSolutionRecord(search_expression,order_by);
			int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
		        
			if (pageNo < 1)
				pageNo = 1;
			else if (pageNo > pmax)    
				pageNo = pmax;
		        
			request.setAttribute("page_id", new Integer(pageNo));   
			request.setAttribute("page_max", new Integer(pmax));
			
			
			if(task_name.equals(AimsConstants.AIMS_SAVE_FORM)){
				
				//CHECK ACCESS
		        if (!(ApplicationHelper.checkAccess(request, AimsConstants.EDIT_TASK, AimsPrivilegesConstants.PUBLISH_SOLUTION)))
		            throw new AimsSecurityException();
				
				Long[] bdsMktSeg = null;
				Long[] mktSeg = null;
				EntAppPublishSolutionVO vo = null;
				AimsEnterpriseApp entApp=null;
				String isPublishOld;
				String isPublishNew;
				try{
					if(entAppPublishSolutionForm.getSolutionVOs() != null){
						
						for(int i=0; i<entAppPublishSolutionForm.getSolutionVOs().size(); i++){
							
							vo = (EntAppPublishSolutionVO) entAppPublishSolutionForm.getSolutionVOs().get(i);
							entApp=AimsEntAppsManager.getAimsEntApps(vo.getAppId());
							isPublishOld=StringFuncs.NullValueReplacement(entApp.getIsPublished());
							isPublishNew=StringFuncs.NullValueReplacement(vo.getIsPublished());
							log.debug("JMA Application :" + vo.getAppTitle());
							log.debug("isPublishOld :" + isPublishOld);
							log.debug("isPublishNew :" + isPublishNew);
							
							mktSeg = JMAApplicationHelper.getMarketSegmentId(vo);
							//bdsMktSeg = JMAApplicationHelper.getBdsMarketSegmentId(mktSeg);
							//AimsEntAppsManager.publishSolutions(vo.getAppId(), vo.getIsPublished(), vo.getIsFeatured(), mktSeg, bdsMktSeg);
							AimsEntAppsManager.publishSolutions(vo, mktSeg);
							
							//Journal Entry for published solution
							if(!isPublishOld.equals("Y") && isPublishNew.equals("Y"))
							{
								 StringBuffer journalText=new StringBuffer();
								 journalText.append("Application ")
								 			.append("\"").append(vo.getAppTitle()).append("\"").append(" ")
								 			.append("Published by "+currUser);
								 
								 log.debug("Journal Entry :"+journalText.toString());
								 JMAApplicationHelper.journalEntry(journalText.toString(), AimsConstants.JOURNAL_TYPE_PRIVATE, vo.getAllianceId(), vo.getAppId(), currUser);
								
							}
							//Journal Entry for unpublished solution
							if(isPublishOld.equals("Y") && isPublishNew.equals(""))
							{
								 StringBuffer journalText=new StringBuffer();
								 journalText.append("Application ")
								 			.append("\"").append(vo.getAppTitle()).append("\"").append(" ")
								 			.append("Unpublished by "+currUser);
								 
								 log.debug("Journal Entry :"+journalText.toString());
								 JMAApplicationHelper.journalEntry(journalText.toString(), AimsConstants.JOURNAL_TYPE_PRIVATE, vo.getAllianceId(), vo.getAppId(), currUser);
								
							}
						}
						
						log.debug("EntAppManagePublishSolutionAction: Solution published successfully.");
						ActionMessages messages = new ActionMessages();
				        ActionMessage message = new ActionMessage("message.entApp.publishSolutions.success");
				        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
				        saveMessages(request, messages);
					}
				}
				catch(HibernateException hx)
				{
					log.error("Error occur",hx);
					ActionErrors errors = new ActionErrors();
		            ActionError error = new ActionError("error.generic.database");
		            errors.add(ActionErrors.GLOBAL_ERROR, error);
		            saveErrors(request, errors);
		            return mapping.findForward("view");
				}
			}
		      
			//CHECK ACCESS
	        if (!(ApplicationHelper.checkAccess(request, AimsConstants.VIEW_TASK, AimsPrivilegesConstants.PUBLISH_SOLUTION)))
	            throw new AimsSecurityException();
	        
			publishSolutionRecord = AimsEntAppsManager.getPublishSolutionRecord(PAGE_LENGTH,pageNo,search_expression,order_by);
			if(publishSolutionRecord != null){
				
				 for (Iterator iter = publishSolutionRecord.iterator(); iter.hasNext();){
			            userValues = (Object[]) iter.next();
			            publishSolutionVO=new EntAppPublishSolutionVO();
			            
			            publishSolutionVO.setAllianceId((Long) userValues[0]);
			            publishSolutionVO.setCompanyName((String) userValues[1]);
			            publishSolutionVO.setAppId((Long) userValues[2]);
			            publishSolutionVO.setAppTitle((String) userValues[3]);
			            publishSolutionVO.setIsPublished((String) userValues[4]);
			            publishSolutionVO.setIsFeatured((String) userValues[5]);
			            
			            if(StringFuncs.NullValueReplacement(publishSolutionVO.getIsPublished()).equals("Y"))
			            	publishSolutionVO.setDisplaySpotlight("Y");
			            else
			            	publishSolutionVO.setDisplaySpotlight("N");
			            
			            marketSegments=null;
			            marketSegments = AimsEntAppsManager.getMarketSegmentInfo((Long) userValues[2]);
			            if(marketSegments!=null){	
			            	for(int i=0; i<marketSegments.length; i++){
			            		
			            		if(marketSegments[i].equals(new Long(161)))
			            			publishSolutionVO.setIsMobileProfessional("Y");
			            		else if(marketSegments[i].equals(new Long(162)))
			            			publishSolutionVO.setIsSoho("Y");
			            		else if(marketSegments[i].equals(new Long(163)))
			            			 publishSolutionVO.setIsSme("Y");
			            		else if(marketSegments[i].equals(new Long(164)))
			            			publishSolutionVO.setIsEnterprise("Y");
			            	}
			            		
			            	
			            }
			            publishApps.add(publishSolutionVO);
			        }
			}
			
		}
		catch(HibernateException hx)
		{
			log.error("Error occur",hx);
			ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("error.generic.database");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return mapping.findForward("view");
		}
		
		entAppPublishSolutionForm.setSolutionVOs(publishApps);
		
		log.debug("EntAppSetupPublishSolutionAction.ActionForward : End");
		return mapping.findForward("view");
	}

	
	private String getFilterExpression(String filter_field, String filter_text)
    {

        StringBuffer expressionBuffer = new StringBuffer("");

        if (filter_field!=null && filter_text!=null && filter_text.trim().length() > 0)
        {
            if (filter_field.equalsIgnoreCase("partner_name"))
            {
                expressionBuffer.append("		and upper(aa.companyName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
         
            if (filter_field.equalsIgnoreCase("solution_name"))
            {
                expressionBuffer.append("		and upper(aas.title) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }


        }

        return expressionBuffer.toString();
    }
	
	
}
