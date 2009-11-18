package com.netpace.aims.controller.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.WorkitemVO;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.workflow.AimsWorkitem;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.WorkflowDescriptor;

/**
*
* @struts.action path="/executeActions"
*                name="WorklistForm"
*                scope="request"
*				 validate="true"
*				 input="/worklist.do"
* @struts.action-forward name="list" path="/worklist.do"
*/

public class WorkitemExecuteActions extends BaseAction {
private static final Logger log = Logger.getLogger(WorkitemExecuteActions.class.getName());
	
	public ActionForward execute(ActionMapping mapping, 
			 ActionForm form, 
			 HttpServletRequest request, 
			 HttpServletResponse response) throws Exception{		
		String forward="list";
		log.debug("WorkitemExecuteActions.execute: Start");
		if (!AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WORK_LIST, AimsSecurityManager.UPDATE)){
			throw new AimsSecurityException();
		}		
		
		WorklistForm worklistForm=(WorklistForm)form;
		List saveWorkitemList=worklistForm.getSaveWorkitemList();
		List formWorkitemList=worklistForm.getWorkitemList();
		
		HttpSession session=request.getSession();
		AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		
		if (saveWorkitemList == null){
			saveWorkitemList=new ArrayList();
		}
		
		for(int i=0; i<formWorkitemList.size(); i++){
			WorkitemVO a=(WorkitemVO)formWorkitemList.get(i);
			if (!saveWorkitemList.contains(a)){
				if(StringUtils.isNotEmpty(a.getSelectedAction()) && "0".equals(a.getSelectedAction())==false){
					saveWorkitemList.add(a);					
				}
			}
		}
		for (int i=0; i<saveWorkitemList.size(); i++){
			WorkitemVO b=(WorkitemVO)saveWorkitemList.get(i);
			if ((StringUtils.isEmpty(b.getSelectedAction()) || "0".equals(b.getSelectedAction()))
					|| (b.getWorkflowId() == null  || b.getWorkitemId().longValue()==0) ){
				saveWorkitemList.remove(b);				
			}
		}
		
		for(int i=0; i<formWorkitemList.size(); i++){
			WorkitemVO a=(WorkitemVO)formWorkitemList.get(i);
			for (int j=0; j<saveWorkitemList.size(); j++){
				WorkitemVO b=(WorkitemVO)saveWorkitemList.get(j);
				if (saveWorkitemList.contains(a)){
					if (a.getWorkitemId().longValue()==b.getWorkitemId().longValue()){
						b.setSelectedAction(a.getSelectedAction());
						b.setComments(a.getComments());
						saveWorkitemList.set(j, b);
						break;
					}
				}
			}
		}
		if (saveWorkitemList.size() > 0){
			BasicWorkflow wf = new BasicWorkflow("User");
			ActionMessages messages = new ActionMessages();
			for (int i=0; i<saveWorkitemList.size(); i++){
				WorkitemVO vo=(WorkitemVO)saveWorkitemList.get(i);
				
				if (StringUtils.isNotEmpty(vo.getSelectedAction()) && "0".equals(vo.getSelectedAction()) == false){
					
					log.debug("Executing action for");
					log.debug("workitemId= "+vo.getWorkitemId());
					log.debug("workflowId= "+vo.getWorkflowId());
					log.debug("recordId= "+vo.getRecordId());
					log.debug("actionId= "+vo.getSelectedAction());
					log.debug("detail= "+vo.getDetail());
					
					AimsWorkitem workitem=WorkflowManager.getWorkitemById(vo.getWorkitemId());
//					AimsAppLite app=ApplicationsManagerHelper.getAimsAppLite(workitem.getModuleWorkflows().getModuleRecordId());					
					boolean isActionExecuted=WorkflowManager.isActionExecuted(workitem.getModuleWorkflows().getWorkflowId().getId(), workitem.getWorkflowStateId());
					
					if (isActionExecuted){
						log.debug("action aleardy executed.");
						ActionMessage message = new ActionMessage("message.worklist.already.actionExecuted", new Object[]{vo.getDetail()});
						messages.add(ActionMessages.GLOBAL_MESSAGE, message);
					}
					else{
						log.debug("going to execute action.");
						HashMap hs = new HashMap();
						Date date=new Date();
						String comments=vo.getComments();
						comments=(StringUtils.isNotEmpty(comments) && comments.length()>500)?comments.substring(0, 500):comments;
						
						hs.put("workflowDetailId",String.valueOf(workitem.getModuleWorkflows().getWorkflowDetail().getWorkflowDetailId()));
						hs.put("recordId",String.valueOf(workitem.getModuleWorkflows().getModuleRecordId()));
						hs.put("createdBy",user.getUsername());
						hs.put("createdDate",date);
						hs.put("modifiedBy",user.getUsername());
						hs.put("modifiedDate",date);
						hs.put("workitemId",String.valueOf(vo.getWorkitemId()));
						hs.put("aId",String.valueOf(vo.getSelectedAction()));
						hs.put("comments",comments);
						
						try {
							wf.doAction(vo.getWorkflowId().longValue(), Integer.parseInt(vo.getSelectedAction()), hs);
						} catch (Exception e) {
							WorkflowDescriptor wd =  wf.getWorkflowDescriptor(wf.getWorkflowName(vo.getWorkflowId().intValue()));
							String actionName = wd.getAction(Integer.parseInt(vo.getSelectedAction())).getName();
							
							if (actionName.indexOf("RFI") != -1 && workitem.getActionTaken().equals(AimsConstants.WORKFLOW_NO_ACTION_TAKEN)){
								workitem.setStatus(AimsConstants.WORKFLOW_ABANDON);
								workitem.setModifiedBy("system");
								workitem.setModifiedDate(date);
								WorkflowManager.saveWorkItem(workitem);								
							}
						}
						ActionMessage message = new ActionMessage("message.worklist.success.actionExecuted", new Object[]{vo.getDetail()});
						messages.add(ActionMessages.GLOBAL_MESSAGE, message);
						saveMessages(request, messages);			
					}
				}								
			}
		}
		request.setAttribute("afterSave", "true");
		log.debug("WorkitemExecuteActions.execute: End");
		return mapping.findForward(forward);
	}
	
}
