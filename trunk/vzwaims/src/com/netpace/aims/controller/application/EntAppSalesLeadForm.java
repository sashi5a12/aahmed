package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.masters.*;

/**
 * @struts.form name="EntAppSalesLeadForm"
 */
public class EntAppSalesLeadForm extends BaseValidatorForm {
	 
	static Logger log = Logger.getLogger(EntAppSalesLeadForm.class.getName());
	
	/** identifier field */
    private java.lang.Long salesLeadId;

    /** nullable persistent field */
    private java.lang.String customerName;

    /** nullable persistent field */
    private java.lang.String city;

    /** nullable persistent field */
    private java.lang.String state;

    /** nullable persistent field */
    private java.lang.String zipCode;

    /** nullable persistent field */
    private java.lang.String isNewVzwCustomer;

    /** nullable persistent field */
    private java.lang.String newVzwCustomer;

    /** nullable persistent field */
    private java.lang.String solutionName;

    /** nullable persistent field */
    private java.lang.String solutionDescription;

    /** nullable persistent field */
    private java.lang.String deviceName;

    /** nullable persistent field */
    private java.lang.String numberOfDevices;

    /** nullable persistent field */
    private java.lang.String solutionComments;

    /** nullable persistent field */
    private java.lang.String solutionTotalSales;

    /** nullable persistent field */
    private java.lang.String salesRepFullName;

    /** nullable persistent field */
    private java.lang.String salesRepEmailAddress;

    /** nullable persistent field */
    private java.lang.String salesRepPhoneNumber;

    /** nullable persistent field */
    private java.lang.String salesRepContactInformation;

    /** nullable persistent field */
    private java.lang.String closeDate;

    /** nullable persistent field */
    private java.lang.String createBy;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;

    /** nullable persistent field */
    private java.lang.Long regionId;

    /** nullable persistent field */
    private java.lang.Long subRegionId;

    /** nullable persistent field */
    private java.lang.Long jmaPartnerId;

    /** nullable persistent field */
    private java.lang.Long verticalId;

    /** nullable persistent field */
    private java.lang.Long salesLeadStatus;
	
    private String task;
    
    private String view;
    
    private Collection allRegion;
	
    private Collection allSubRegion;
    
    private Collection allStatus;
	
    private Collection allSubmitBy;
	
    private Collection allVertical;
	
    private Collection allPartner;

	
	public void reset(ActionMapping mapping, HttpServletRequest request)
    {
	  String currUserType = ((AimsUser) (request.getSession().getAttribute(AimsConstants.AIMS_USER))).getUserType();
		try
		{
			allRegion = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JMA_SALES_LEAD_AREA);
			allSubRegion = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JMA_SALES_LEAD_REGIONS);
			allStatus = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JMA_SALES_LEAD_STATUS);
			allSubmitBy = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JMA_SALES_LEAD_SUBMIT_BY);
			allVertical = AimsIndFocusManager.getIndFocuses();
			
			if(currUserType.equals(AimsConstants.VZW_USERTYPE))
			{
				allPartner = getPartnerCollection(AimsJmaSalesLeadManager.getPartners());
			}
		}
		catch(Exception ex)
		{
			log.error("Exception occur while in reset method", ex);
		}

    }
	
	  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)  
	  {
		  log.debug("EntAppSalesLeadForm.validate : Start");
		  HttpSession session = request.getSession();
	      ActionErrors errors = new ActionErrors();
	      String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
	      String task_name = StringFuncs.NullValueReplacement(this.getTask());
	        
	      if(currUserType.equals(AimsConstants.VZW_USERTYPE))
	      {
	    	if(!this.isDropDownSelected(this.jmaPartnerId))
	    		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.jmaPartner"));
	      }
	      
	      this.validateToDBFields(errors,currUserType);
	      
	      if(this.isBlankString(this.customerName))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.customerName"));
	      
	      if(!this.isDropDownSelected(this.regionId))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.region"));
	      
	      if(!this.isDropDownSelected(this.subRegionId))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.subRegion"));
	    
	      if(this.isBlankString(this.city))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.city"));
	    
	      if(this.isBlankString(this.state))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.state"));
	      
	      if(this.isBlankString(this.zipCode))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.zipCode"));
	      
	      if(this.isBlankString(this.isNewVzwCustomer))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.isNewVzwCustomer"));
	      
	      if(StringFuncs.NullValueReplacement(this.isNewVzwCustomer).equals("Y") && this.isBlankString(this.newVzwCustomer))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.newVzwCustomer"));
	    
	      if(this.isBlankString(this.solutionName))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.solutionName"));
	      
	      if(this.isBlankString(this.solutionDescription))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.solutionDescription"));
	      
	      if(!this.isDropDownSelected(this.verticalId))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.vertical"));
	      
	      if(this.isBlankString(this.deviceName))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.deviceName"));
	      
	      if(this.isBlankString(this.numberOfDevices))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.noOfDevices"));
	    
	      if(this.isBlankString(this.solutionComments))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.comments"));
	      
	      if(this.isBlankString(this.solutionTotalSales))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.totalSales"));
	      
	      if(this.isBlankString(this.salesRepFullName))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.fullName"));
	      
	      if(this.isBlankString(this.salesRepEmailAddress))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.emailAddress"));
	      else if(!this.isValidEmail(this.salesRepEmailAddress))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.invalid.emailAddress"));
	      
	      if(this.isBlankString(this.salesRepPhoneNumber))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.phoneNumber"));
	     
	      //Phase-2 Enhancement
	      if(currUserType.equals(AimsConstants.VZW_USERTYPE))
	      {
	    	  if(view.equals("sent"))
	    	  {
			      if(this.isBlankString(this.salesRepContactInformation))
			    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.contactInfo"));
	    	  }
	    	  else if(view.equals("received"))
	    	  {
	    		  if(this.isBlankString(this.salesRepContactInformation))
			    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.additionalInfo"));  
	    	  }
	      }
	      else if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
	      {
	    	  if(view.equals("sent"))
	    	  {
			      if(this.isBlankString(this.salesRepContactInformation))
			    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.additionalInfo"));
	    	  }
	    	  else if(view.equals("received"))
	    	  {
	    		  if(this.isBlankString(this.salesRepContactInformation))
			    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.contactInfo"));  
	    	  }
	      }
	     
	      if(!this.isDropDownSelected(this.salesLeadStatus))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.status"));
	      else if(task_name.equals("create") &&  !this.salesLeadStatus.equals(AimsConstants.SALES_LEAD_STATUS_OPPORTUNITY))
	    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.submit.invalid.status"));
	      else if(this.salesLeadStatus.equals(AimsConstants.SALES_LEAD_STATUS_WIN))
          {
		      if(this.isBlankString(this.closeDate))
		    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.required.closeDate"));
		      else if(!this.isDate(this.closeDate))
		    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.invalid.closeDate"));
          }
          
	      log.debug("EntAppSalesLeadForm.validate : End");
	      return errors;
	        
	   }
	  
	  public void validateToDBFields(ActionErrors errors,String currUserType)
	  {
		  if ((customerName != null) && (customerName.length() > 200))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.customerName"));
		  
		  if ((city != null) && (city.length() > 100))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.city"));
		  
		  if ((zipCode != null) && (zipCode.length() > 20))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.zipCode"));
		  
		  if ((newVzwCustomer != null) && (newVzwCustomer.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.newVzwCustomer"));
	      
		  if ((solutionName != null) && (solutionName.length() > 200))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.solutionName"));
		  
		  if ((solutionDescription != null) && (solutionDescription.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.solutionDescription"));
		  
		  if ((deviceName != null) && (deviceName.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.deviceName"));
	    
		  if ((numberOfDevices != null) && (numberOfDevices.length() > 100))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.noOfDevice"));
		  
		  if ((solutionComments != null) && (solutionComments.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.solutionComments"));
		  
		  if ((solutionTotalSales!= null) && (solutionTotalSales.length() > 100))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.totalSales"));
		  
		  if ((salesRepFullName != null) && (salesRepFullName.length() > 100))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.fullName"));
	    
		  if ((salesRepEmailAddress != null) && (salesRepEmailAddress.length() > 200))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.emailAddress"));
		  
		  if ((salesRepPhoneNumber != null) && (salesRepPhoneNumber.length() > 100))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.phoneNumber"));
		  
		 //Phase-2 Enhancement
		  if(currUserType.equals(AimsConstants.VZW_USERTYPE))
	      {
			  if(view.equals("sent"))
			  {
				  if ((salesRepContactInformation != null) && (salesRepContactInformation.length() > 1000))
			            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.contactInfo"));
			  }
			  else if(view.equals("received"))
			  {
				  if ((salesRepContactInformation != null) && (salesRepContactInformation.length() > 1000))
			            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.additionalInfo"));
			  }
	      }
		  else if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
		  {
			  if(view.equals("sent"))
			  {
				  if ((salesRepContactInformation != null) && (salesRepContactInformation.length() > 1000))
			            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.additionalInfo"));
			  }
			  else if(view.equals("received"))
			  {
				  if ((salesRepContactInformation != null) && (salesRepContactInformation.length() > 1000))
			            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.salesLead.length.contactInfo"));
			  }  
		  }
	    
	  }
	   
	
	private Collection getPartnerCollection(Collection c)
	{
		PartnerDetailBean bean = null;
		Object[] userValues = null;
		Collection collection=null;
		if(c!=null)
		{
			collection = new ArrayList();
			bean = null;
			for(Iterator itr = c.iterator(); itr.hasNext();)
			{
				userValues=(Object []) itr.next();
				bean=new PartnerDetailBean();
				
				bean.setPartnerId((Long) userValues[0]);
				bean.setPartnerName((String) userValues[1]);
				
				collection.add(bean);
				
			}
		}
		
		return collection;
	}
	
	public Collection getAllPartner() {
		return allPartner;
	}

	public void setAllPartner(Collection allPartner) {
		this.allPartner = allPartner;
	}
	public Collection getAllVertical() {
		return allVertical;
	}

	public void setAllVertical(Collection allVertical) {
		this.allVertical = allVertical;
	}

	public Collection getAllRegion() {
		return allRegion;
	}

	public void setAllRegion(Collection allRegion) {
		this.allRegion = allRegion;
	}

	public Collection getAllSubRegion() {
		return allSubRegion;
	}

	public void setAllSubRegion(Collection allSubRegion) {
		this.allSubRegion = allSubRegion;
	}

	public Collection getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(Collection allStatus) {
		this.allStatus = allStatus;
	}

	public Collection getAllSubmitBy() {
		return allSubmitBy;
	}

	public void setAllSubmitBy(Collection allSubmitBy) {
		this.allSubmitBy = allSubmitBy;
	}

	public java.lang.Long getSalesLeadId() {
		return salesLeadId;
	}
	public void setSalesLeadId(java.lang.Long salesLeadId) {
		this.salesLeadId = salesLeadId;
	}

	public java.lang.String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(java.lang.String customerName) {
		this.customerName = customerName;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	public java.lang.String getZipCode() {
		return zipCode;
	}

	public void setZipCode(java.lang.String zipCode) {
		this.zipCode = zipCode;
	}

	public java.lang.String getIsNewVzwCustomer() {
		return isNewVzwCustomer;
	}

	public void setIsNewVzwCustomer(java.lang.String isNewVzwCustomer) {
		this.isNewVzwCustomer = isNewVzwCustomer;
	}

	public java.lang.String getNewVzwCustomer() {
		return newVzwCustomer;
	}

	public void setNewVzwCustomer(java.lang.String newVzwCustomer) {
		this.newVzwCustomer = newVzwCustomer;
	}

	public java.lang.String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(java.lang.String solutionName) {
		this.solutionName = solutionName;
	}

	public java.lang.String getSolutionDescription() {
		return solutionDescription;
	}

	public void setSolutionDescription(java.lang.String solutionDescription) {
		this.solutionDescription = solutionDescription;
	}

	public java.lang.String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(java.lang.String deviceName) {
		this.deviceName = deviceName;
	}

	public java.lang.String getNumberOfDevices() {
		return numberOfDevices;
	}

	public void setNumberOfDevices(java.lang.String numberOfDevices) {
		this.numberOfDevices = numberOfDevices;
	}

	public java.lang.String getSolutionComments() {
		return solutionComments;
	}

	public void setSolutionComments(java.lang.String solutionComments) {
		this.solutionComments = solutionComments;
	}

	public java.lang.String getSolutionTotalSales() {
		return solutionTotalSales;
	}

	public void setSolutionTotalSales(java.lang.String solutionTotalSales) {
		this.solutionTotalSales = solutionTotalSales;
	}

	public java.lang.String getSalesRepFullName() {
		return salesRepFullName;
	}

	public void setSalesRepFullName(java.lang.String salesRepFullName) {
		this.salesRepFullName = salesRepFullName;
	}

	public java.lang.String getSalesRepEmailAddress() {
		return salesRepEmailAddress;
	}

	public void setSalesRepEmailAddress(java.lang.String salesRepEmailAddress) {
		this.salesRepEmailAddress = salesRepEmailAddress;
	}

	public java.lang.String getSalesRepPhoneNumber() {
		return salesRepPhoneNumber;
	}

	public void setSalesRepPhoneNumber(java.lang.String salesRepPhoneNumber) {
		this.salesRepPhoneNumber = salesRepPhoneNumber;
	}

	public java.lang.String getSalesRepContactInformation() {
		return salesRepContactInformation;
	}

	public void setSalesRepContactInformation(
			java.lang.String salesRepContactInformation) {
		this.salesRepContactInformation = salesRepContactInformation;
	}

	public java.lang.String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.lang.String closeDate) {
		this.closeDate = closeDate;
	}

	public java.lang.String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public java.lang.String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public java.util.Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(java.util.Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public java.lang.Long getRegionId() {
		return regionId;
	}

	public void setRegionId(java.lang.Long regionId) {
		this.regionId = regionId;
	}

	public java.lang.Long getSubRegionId() {
		return subRegionId;
	}

	public void setSubRegionId(java.lang.Long subRegionId) {
		this.subRegionId = subRegionId;
	}

	public java.lang.Long getJmaPartnerId() {
		return jmaPartnerId;
	}

	public void setJmaPartnerId(java.lang.Long jmaPartnerId) {
		this.jmaPartnerId = jmaPartnerId;
	}

	public java.lang.Long getVerticalId() {
		return verticalId;
	}

	public void setVerticalId(java.lang.Long verticalId) {
		this.verticalId = verticalId;
	}

	public java.lang.Long getSalesLeadStatus() {
		return salesLeadStatus;
	}

	public void setSalesLeadStatus(java.lang.Long salesLeadStatus) {
		this.salesLeadStatus = salesLeadStatus;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

}

