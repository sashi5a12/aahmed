package com.netpace.aims.controller.workflow;

import java.util.ArrayList;
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

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.WorkitemVO;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
*
* @struts.action path="/worklist"
*                name="WorklistForm"
*                scope="request"
*				 validate="false"
* @struts.action-forward name="list" path="/workflow/worklist.jsp"
*/

public class WorklistAction extends BaseAction {
	private static final Logger log = Logger.getLogger(WorklistAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, 
			 ActionForm form, 
			 HttpServletRequest request, 
			 HttpServletResponse response) throws Exception{
		
		log.debug("WorklistAction.execute: Start");
		
		if (!AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WORK_LIST, AimsSecurityManager.SELECT)){
			throw new AimsSecurityException();
		}

		String forward="list";
		WorklistForm worklistForm=(WorklistForm)form;
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        int pageLength = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        String afterSave=null;
        String afterValidate=null;
        String filterTypes=null;
        String filterWorkItems=null;
        int workItemCount=0;
        String sortBy=request.getParameter("sort_field");
        String lastSortBy=request.getParameter("lastSortBy");
        String sortOrder=request.getParameter("sort_order");
        String isPageLnkClicked=request.getParameter("isPageLnk");

        Object o_param;
        
        if (StringUtils.isEmpty(sortBy)){
        	sortBy="3";
        }
        
        if (StringUtils.isEmpty(isPageLnkClicked)){
	        //Click on menu item
	        if (StringUtils.isEmpty(lastSortBy)){
	        	sortOrder="asc"; //Current Sorting order
	        }
	        else if (lastSortBy.equalsIgnoreCase(sortBy)==true && "asc".equalsIgnoreCase(sortOrder)){ //user click on same column       	
	        	sortOrder="desc";
	        }
	        else if (lastSortBy.equalsIgnoreCase(sortBy)==true && "desc".equalsIgnoreCase(sortOrder)){ //user click on same column
	        	sortOrder="asc";
	        }	        	        
	        else if (lastSortBy.equalsIgnoreCase(sortBy)==false){ //user click on different column
	        	sortOrder="asc";
	        }
        }

        if (StringUtils.isEmpty(sortOrder)){
        	sortOrder="asc";
        }
        else if (sortOrder.equalsIgnoreCase("asc"));
        else if	(sortOrder.equalsIgnoreCase("desc"));
        else sortOrder="asc";
        
        worklistForm.setSortField(sortBy);
        worklistForm.setSortOrder(sortOrder);
        request.setAttribute("sort_order", sortOrder);
        request.setAttribute("sort_field", sortBy);
        request.setAttribute("lastSortBy", sortBy);

        o_param = request.getParameter("page_id");
        if (o_param != null){
            try{
                pageNo = Integer.parseInt((String) o_param);
            }
            catch (NumberFormatException ex){
            	//Ignore
            }
        }

        o_param = request.getAttribute("afterValidate");
        if (o_param != null){
        	afterValidate=(String)request.getAttribute("afterValidate");
        }
        o_param = request.getAttribute("afterSave");
        if (o_param != null){
        	afterSave=(String)request.getAttribute("afterSave");
        }
        
		if (StringUtils.isNotEmpty(afterSave) && "true".equals(afterSave)){
			pageNo=1;
			worklistForm.setSaveWorkitemList(null);
			worklistForm.setWorkitemList(null);
			worklistForm.setErrorMessages(null);
		}
		if (StringUtils.isNotEmpty(afterValidate) &&"true".equals(afterValidate)){
			worklistForm.setErrorMessages(null);
		}

		if (worklistForm.getSelectedFilterType()!=null && worklistForm.getSelectedFilterType().length>0){
        	String[] selectedFilterType=worklistForm.getSelectedFilterType();
        	if (selectedFilterType.length == 1 && AimsConstants.FILTER_SHOW_ALL.equals(selectedFilterType[0])==false ){
        		filterTypes=selectedFilterType[0];
        	}
        	else if (selectedFilterType.length > 1){
        		filterTypes="";        			
	        	for(int i=0; i<selectedFilterType.length; i++){
	        		if (selectedFilterType[i].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
		            	if (i != (selectedFilterType.length-1)){
		            		filterTypes += selectedFilterType[i] + ",";
		            	}
		            	else {
		            		filterTypes += selectedFilterType[i];
		            	}
	        		}
	        	}
        	}
        }
        if (worklistForm.getSelectedFilterWorkItem()!=null && worklistForm.getSelectedFilterWorkItem().length>0){
        	String[] workitems=worklistForm.getSelectedFilterWorkItem();
        	if (workitems.length == 1 && AimsConstants.FILTER_SHOW_ALL.equals(workitems[0])==false){
        		String[] workitem=workitems[0].split(",");
        		filterWorkItems=workitem[0];
        	}
        	else {
	        	ArrayList list=new ArrayList();
	        	filterWorkItems="";
	        	for (int i=0; i<workitems.length; i++){
	        		String[] workitem=workitems[i].split(",");
	    			if (AimsConstants.FILTER_SHOW_ALL.equalsIgnoreCase(workitem[0]) == false
	    					&& AimsConstants.FILTER_BREW_SHOW_ALL.equalsIgnoreCase(workitem[0])==false 
	    					&& AimsConstants.FILTER_JAVA_SHOW_ALL.equalsIgnoreCase(workitem[0])==false 
	    					&& list.contains(workitem[0])==false){        		
		        			list.add(workitem[0]);
	    			}
	        	}
	        	for (int i=0; i<list.size(); i++){
	        		if (i != (list.size()-1)){
	        			filterWorkItems += list.get(i)+",";
	        		}
	        		else {
	        			filterWorkItems += list.get(i);
	        		}        		
	        	}
        	}
        }		
    	workItemCount=AimsConstants.FILTER_BREW_WORKITEMS.length; 		
		
		List saveWorkitemList=worklistForm.getSaveWorkitemList();
		List formWorkitemList=worklistForm.getWorkitemList();

		int totalRecords=WorkflowManager.getWorkListCount(worklistForm.getFilterField(), worklistForm.getFilterText(), filterTypes, filterWorkItems, this.getColumnName(worklistForm.getSortField(), request), sortOrder, user);
		List dbWorkitemList=WorkflowManager.getWorkList(pageNo, pageLength, totalRecords, worklistForm.getFilterField(), worklistForm.getFilterText(), filterTypes, filterWorkItems, this.getColumnName(worklistForm.getSortField(), request), sortOrder, user);

		//save user changes if user press paging links
		if (saveWorkitemList !=null && formWorkitemList !=null){
			for(int i=0; i<formWorkitemList.size(); i++){
				WorkitemVO a=(WorkitemVO)formWorkitemList.get(i);
				if (!saveWorkitemList.contains(a)){
					if("0".equals(a.getSelectedAction())==false){
						saveWorkitemList.add(a);
					}
					else if (StringUtils.isNotEmpty(a.getComments())){
						saveWorkitemList.add(a);
					}
				}
			}
		}
		if (saveWorkitemList != null && saveWorkitemList.size()>1){
			for (int i=0; i<saveWorkitemList.size(); i++){
				WorkitemVO b=(WorkitemVO)saveWorkitemList.get(i);
				if (StringUtils.isEmpty(b.getSelectedAction())||"0".equals(b.getSelectedAction())
						&& (b.getWorkflowId() == null || b.getWorkflowId().longValue() == 0)
						&& (b.getWorkflowId() == null || b.getWorkflowId().longValue() == 0)){
					saveWorkitemList.remove(b);					
				}
			}
		}
		if (formWorkitemList != null && formWorkitemList.size()>0){
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
		}
		//update the current list state with previous value if user press paging links
		if (saveWorkitemList != null){
			for(int i=0; i<saveWorkitemList.size(); i++){
				WorkitemVO a=(WorkitemVO)saveWorkitemList.get(i);

				for(int j=0; j<dbWorkitemList.size(); j++){
					WorkitemVO b=(WorkitemVO)dbWorkitemList.get(j);
					if (a.getWorkitemId().longValue()==b.getWorkitemId().longValue()){
						b.setSelectedAction(a.getSelectedAction());
						b.setComments(a.getComments());
						dbWorkitemList.set(j, b);
						break;
					}
				}
			}
		}
		
		
    	int pmax = new Double(Math.ceil(totalRecords * 1.0 / pageLength)).intValue();
        log.debug("Max Page : " + pmax);

        if (pageNo < 1)
            pageNo = 1;
        else if (pageNo > pmax)
            pageNo = pmax;
        		
        //when user first time visit the page.
		if (saveWorkitemList== null){
			List temp=new ArrayList();
			temp.add(new WorkitemVO());
			saveWorkitemList=temp;
		}
		
        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));
        request.setAttribute("workItemCount", String.valueOf(workItemCount));
        setFilterTypeString(request, worklistForm);
        setFilterWorkItemString(request, worklistForm);
        
		worklistForm.setSaveWorkitemList(saveWorkitemList);
		worklistForm.setWorkitemList(dbWorkitemList);
		log.debug("WorklistAction.execute: End");
		return mapping.findForward(forward);
	}
	
	private void setFilterTypeString(HttpServletRequest request, WorklistForm worklistForm) {
        StringBuffer value = new StringBuffer();
		HashMap types = new HashMap();
		types.put(AimsConstants.BREW_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_BREW);
		types.put(AimsConstants.JAVA_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_JAVA);

        for (int i=0; i<worklistForm.getSelectedFilterType().length; i++) {
        	if (worklistForm.getSelectedFilterType()[i].equalsIgnoreCase("SHOW_ALL")){
        		value=new StringBuffer("Showing All");
        		break;
        	}
        	else if (i != (worklistForm.getSelectedFilterType().length-1)){
        		value.append(types.get(worklistForm.getSelectedFilterType()[i])).append(", ");
        	}
        	else {
        		value.append(types.get(worklistForm.getSelectedFilterType()[i]));
        	}
        }
        request.setAttribute("filterType", value.toString());
    }
    
    private void setFilterWorkItemString(HttpServletRequest request, WorklistForm worklistForm) {
        StringBuffer value = new StringBuffer();
		HashMap wiMap = new HashMap();
		List list=new ArrayList();
				
		wiMap.put("201",AimsConstants.FILTER_LABEL_WORKFLOW_EVALUATED);
  		wiMap.put("202",AimsConstants.FILTER_LABEL_WORKFLOW_CONTENT_STANDARD_PENDING);
  		wiMap.put("203",AimsConstants.FILTER_LABEL_WORKFLOW_LEGAL_PENDING);
  		
  		wiMap.put("200" ,	AimsConstants.FILTER_LABEL_WORKFLOW_PROG_CONTENT_PENDING);
  		wiMap.put("400" ,	AimsConstants.FILTER_LABEL_WORKFLOW_LEGAL_PENDING);
  		wiMap.put("500" ,	AimsConstants.FILTER_LABEL_WORKFLOW_CONTENT_STANDARD_PENDING);
  		wiMap.put("700" ,	AimsConstants.FILTER_LABEL_PENDING_TAX_APPROVAL);	

        String[] workitems=worklistForm.getSelectedFilterWorkItem();
    	for (int i=0; i<workitems.length; i++){
    		String[] wi=workitems[i].split(",");
    		if ( (AimsConstants.FILTER_BREW_SHOW_ALL.equalsIgnoreCase(wi[0])==false && list.contains(wi[0])==false) && (AimsConstants.FILTER_JAVA_SHOW_ALL.equalsIgnoreCase(wi[0])==false && list.contains(wi[0])==false) ){        		
        			list.add(wi[0]);
			}
    	}
    	for(int i=0; i<list.size(); i++){
    		if (AimsConstants.FILTER_SHOW_ALL.equalsIgnoreCase(list.get(i).toString())){
    			value=new StringBuffer("Showing All");
    			break;
    		}
    		if (i != (list.size()-1)){
    			value.append(wiMap.get(list.get(i))).append(", ");
    		}
    		else {
    			value.append(wiMap.get(list.get(i)));
    		}    		
    	}
        request.setAttribute("filterWorkItem", value.toString());
    }	
    
    private String getColumnName(String key, HttpServletRequest request){
    	HashMap columns=new HashMap();
    	columns.put("1", "wi.step_name");
    	columns.put("2", "p.platform_name");
    	columns.put("3", "app.title");
    	columns.put("4", "al.company_name");
    	columns.put("5", "wi.entry_date");    	
    	
    	if (StringUtils.isEmpty(key)){
    		return columns.get("3").toString();
    	}
    	else if (columns.containsKey(key)){
    		return columns.get(key).toString();
    	}
    	else {
    		return columns.get("3").toString();
    	}
    }
}