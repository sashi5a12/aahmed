package com.netpace.aims.controller.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.dataaccess.valueobjects.WorkitemVO;
import com.netpace.aims.util.AimsConstants;


/**
 * @struts.form	name="WorklistForm"
 */

public class WorklistForm extends BaseValidatorForm{

   private static final Logger log = Logger.getLogger(WorklistForm.class.getName());
	
    private List workitemList;
    private List saveWorkitemList;
	private String filterField;
	private String filterText;		
	private final Object[][] filterType= AimsConstants.FILTER_WORKFLOW_TYPE;
	private final Object[][] filterWorkItemsBrew= AimsConstants.FILTER_BREW_WORKITEMS;
	private final Object[][] filterWorkItemsJava= AimsConstants.FILTER_JAVA_WORKITEMS;
    private String[] selectedFilterType=new String[0];
    private String[] selectedFilterWorkItem=new String[0];
	private String sortField;		
	private String sortOrder;		

    private java.lang.String[] errorMessages;
    
    public List getWorkitemList() {
		return workitemList;
	}
	public void setWorkitemList(List workitemList) {
		this.workitemList = workitemList;
	}
	
    public List getSaveWorkitemList() {
		return saveWorkitemList;
	}
	public void setSaveWorkitemList(List saveWorkitemList) {
		this.saveWorkitemList = saveWorkitemList;
	}
	
	public WorkitemVO getWorkitem(int index){

        if(this.workitemList == null){
            this.workitemList = new ArrayList();
        }
 
        // indexes do not come in order, populate empty spots
        while(index >= this.workitemList.size())
        {
            this.workitemList.add(new WorkitemVO());
        }
 
        return (WorkitemVO) workitemList.get(index);
    }

	public WorkitemVO getSaveWorkitem(int index){
		
		if(this.saveWorkitemList == null){
			this.saveWorkitemList = new ArrayList();
		}
		
		// indexes do not come in order, populate empty spots
		while(index >= this.saveWorkitemList.size())
		{
			this.saveWorkitemList.add(new WorkitemVO());
		}
		
		return (WorkitemVO) saveWorkitemList.get(index);
	}
	
	public java.lang.String[] getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(java.lang.String[] errorMessages) {
		this.errorMessages = errorMessages;
	}	
	public String getFilterField() {
		return filterField;
	}
	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}
	public String getFilterText() {
		return filterText;
	}
	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}
	
	public String[] getSelectedFilterType() {
		return selectedFilterType;
	}
	public void setSelectedFilterType(String[] selectedFilterType) {
		this.selectedFilterType = selectedFilterType;
	}
	public String[] getSelectedFilterWorkItem() {
		return selectedFilterWorkItem;
	}
	public void setSelectedFilterWorkItem(String[] selectedFilterWorkItem) {
		this.selectedFilterWorkItem = selectedFilterWorkItem;
	}		
	public Object[][] getFilterType() {
		return filterType;
	}
	public Object[][] getFilterWorkItemsBrew() {
		return filterWorkItemsBrew;
	}
	public Object[][] getFilterWorkItemsJava() {
		return filterWorkItemsJava;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		this.setErrorMessages(null);
		if (workitemList != null){
			for (int i=0; i<workitemList.size(); i++){
				WorkitemVO vo=(WorkitemVO)workitemList.get(i);
				if (!this.isBlankString(vo.getComments()) && vo.getComments().length() > 500){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.workflow.comments.length", new Object[]{vo.getDetail()}));
				}
			}			
		}
		
		if (saveWorkitemList != null && workitemList != null){
			for (int i=0; i<saveWorkitemList.size(); i++){
				WorkitemVO vo=(WorkitemVO)saveWorkitemList.get(i);
				if (!workitemList.contains(vo)){
					if (!this.isBlankString(vo.getComments()) && vo.getComments().length() > 500){
						errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.workflow.comments.length", new Object[]{vo.getDetail()}));
					}
				}
			}
		}
		request.setAttribute("afterValidate", "true");
		return errors;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
    	log.debug("WorklistForm.reset: Start");
		this.selectedFilterType=new String[]{AimsConstants.FILTER_SHOW_ALL};
		this.selectedFilterWorkItem=new String[]{AimsConstants.FILTER_SHOW_ALL};    	
    	log.debug("WorklistForm.reset: End");
	}    
}