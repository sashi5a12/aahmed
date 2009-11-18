package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import com.netpace.aims.ws.vds.BatchContentDeliveryServices;
import com.netpace.aims.ws.vds.BulkDevelopersLog;
import com.netpace.aims.ws.amdocs.BatchSettlementServices;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * This class takes to update multiple content delivery and settlement services.
 *
 * @struts.action path="/batchContentDeliverySettlement"
 *                scope="request"
 *				  name="BatchContentDeliverySettlementForm"
 *				  validate="true"
 *                input="/alliance/batchContentDeliverySettlement.jsp"
 * @struts.action-forward name="batchInput" path="/alliance/batchContentDeliverySettlement.jsp" 
 * @author Waseem Akram
 */
public class BatchContentDeliverySettlementAction extends BaseAction {
	private static Logger log = Logger.getLogger(BatchContentDeliverySettlementAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, 
									ActionForm form, 
									HttpServletRequest request, 
									HttpServletResponse response) throws Exception
    {        
		if(log.isDebugEnabled()){
			log.info("###################################### (BULK LOAD START) ##############################################");
		}
    	String forward = "batchInput";
    	BatchContentDeliverySettlementForm batchUpateForm = (BatchContentDeliverySettlementForm)form;
    	String task = StringFuncs.NullValueReplacement(batchUpateForm.getTask());
    	String dateFormat = this.getResources(request).getMessage("date.format");
    	
    	HttpSession session = request.getSession();
    	AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
    	String currUserType = user.getUserType();
    	
    	if(log.isDebugEnabled()) 
    		log.debug("Task		: "+batchUpateForm.getTask()+"\t currUserType	: "+currUserType);
    	
    	if (!currUserType.equals(AimsConstants.VZW_USERTYPE)) 
    	{
    		//only vzw user can perform this action
    		throw new AimsSecurityException();
    	}    	   	    	
    	
    	if(task.equals("update"))
    	{
    		if(log.isDebugEnabled()) 
    		{        		
        		log.debug("	Start Date		: "+batchUpateForm.getStartDate());
        		log.debug("	End Date		: "+batchUpateForm.getEndDate());    		
        		log.debug("	Service Type	: "+batchUpateForm.getServiceType());
        	}
    		
    		Date startDateObj = Utility.convertToDate(batchUpateForm.getStartDate(), dateFormat);
    		Date endDateObj = Utility.convertToDate(batchUpateForm.getEndDate(), dateFormat);
    		
    		if(batchUpateForm.getServiceType().equals("CD"))
    		{
    			BulkDevelopersLog bulkDevelopersLog = BatchContentDeliveryServices.bulkCreateDeveloper(startDateObj, endDateObj);
    			
    			request.setAttribute( "bulkDevelopersLog" , bulkDevelopersLog );
    			
    			this.logBulkOperation( bulkDevelopersLog , "Total calls to VDS for Developer Create: " );
    			
    			//todo catch success and errors on service and show result in separate page
    			
    			ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.batchContentDeliverySettlement.success.contentDelivery"));
                saveMessages(request, messages);
                                
                this.resetFormValues(batchUpateForm);
    		}
    		else if(batchUpateForm.getServiceType().equals("S"))
    		{
    			List settlementList = BatchSettlementServices.bulkUploadPartnerOffer(startDateObj, endDateObj);
    			
    			if ( settlementList.size() == 2 )
    			{
    				request.setAttribute( "partnerOnBoardingLog" , settlementList.get( 0 ) );
        			this.logBulkOperation( (BulkDevelopersLog)settlementList.get( 0 ) , "Total calls to Amdocs for Partner On boarding: " );
        			
    				request.setAttribute( "offerCreationLog" , settlementList.get( 1 ) );
        			this.logBulkOperation( (BulkDevelopersLog)settlementList.get( 1 ) , "Total calls to Amdocs for Offer Creation: " );
    			}
    			
    			//todo catch success and errors on service and show result in separate page
    			
    			ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.batchContentDeliverySettlement.success.settlementService"));
                saveMessages(request, messages);
                
                this.resetFormValues(batchUpateForm);
    		}
    	}
    	
    	if(log.isDebugEnabled()){
    		log.info("###################################### (BULK LOAD END) ##############################################");
    	}
    	return mapping.findForward(forward);        
    }
	
	private void resetFormValues(BatchContentDeliverySettlementForm batchUpateForm)
	{
		batchUpateForm.setStartDate("");
		batchUpateForm.setEndDate("");
		batchUpateForm.setServiceType("");
	}
	
	private void logBulkOperation(BulkDevelopersLog bulkLog, String titleMsg)
	{
		if( log.isInfoEnabled() )
		{
			log.info( titleMsg + bulkLog.getTotal() );
			log.info( "Success: " + bulkLog.getSuccess() );
			log.info( "Failure: " + bulkLog.getFail() );
			
			StringBuffer sb = new StringBuffer("Success Ids: ");
			
			List successIds = bulkLog.getSuccessIds();
			if ( successIds.size() == 0 )
				sb.append( "none" );
			else
			{
				Iterator it = successIds.iterator();
				while( it.hasNext() )
					sb.append( it.next() ).append( ", ");
			}
			
			log.info( sb );
			
			sb = new StringBuffer("Failure Ids: ");
			
			List  failIds = bulkLog.getFailureIds();
			if ( failIds .size() == 0 )
				sb.append( "none" );
			else
			{
				Iterator  it = failIds .iterator();
				while( it.hasNext() )
					sb.append( it.next() ).append( "\n" );
			}
			
			log.info( sb );
		}
	}

}
