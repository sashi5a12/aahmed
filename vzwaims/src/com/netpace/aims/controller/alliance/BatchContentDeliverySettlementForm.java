package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="BatchContentDeliverySettlementForm"
 */
public class BatchContentDeliverySettlementForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(BatchContentDeliverySettlementForm.class.getName());
	
	private String startDate;
	private String endDate;
	
	private String task;
	private String serviceType;

	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
		log.info("BatchContentDeliverySettlementForm.validate : START");
		
        ActionErrors errors = new ActionErrors();
        if(this.task!=null && this.task.equalsIgnoreCase("update")) 
        {
	        if(this.isBlankString(this.startDate))
	        {
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.batchContentDeliverySettlement.required.startDate"));
	        }
	        else if (!this.isDate(this.startDate))
			{
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.batchContentDeliverySettlement.invalid.startDate", this.startDate));
			}
	        
	        if(this.isBlankString(this.endDate))
	        {
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.batchContentDeliverySettlement.required.endDate"));
	        }
	        else if (!this.isDate(this.endDate))
			{
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.batchContentDeliverySettlement.invalid.endDate", this.endDate));
			}
	        
	        if(this.isBlankString(this.serviceType))
	        {
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.batchContentDeliverySettlement.required.serviceType"));
	        }
        }//end task
        log.info("BatchContentDeliverySettlementForm.validate : END\t errors size: "+errors.size());
        return errors;
    }
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}	

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
}
