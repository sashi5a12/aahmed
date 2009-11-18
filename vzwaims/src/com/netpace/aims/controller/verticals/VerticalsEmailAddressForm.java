package com.netpace.aims.controller.verticals;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import net.sf.hibernate.HibernateException;

import com.netpace.aims.bo.alliance.JMAAllianceRegistrationManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.masters.AimsIndustryFocu;

/**
 * @struts.form name="VerticalsEmailAddressForm"
 * @author aqureshi
 *
 */
public class VerticalsEmailAddressForm extends BaseValidatorForm {

	
	private Collection allIndustryVerticals;
    private String task;
    private String []emailAddress;
	private Long [] industryId;
    
	
	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	}


	public Collection getAllIndustryVerticals() {
		return allIndustryVerticals;
	}


	public void setAllIndustryVerticals(Collection allIndustryVerticals) {
		this.allIndustryVerticals = allIndustryVerticals;
	}


	

	private Collection getAllIndustryVerticalsList()
    {
        Collection aimsIndVetricalList = null;
        try {
        	aimsIndVetricalList = JMAAllianceRegistrationManager.getAllIndustyVerticals();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return aimsIndVetricalList;
    }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
		Collection c=this.getAllIndustryVerticalsList();
		this.setAllIndustryVerticals(c);
		emailAddress= new String[c.size()];
		industryId =new Long[c.size()];
		
		
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
		  ActionErrors errors = new ActionErrors();
		  
		  if(allIndustryVerticals!=null)
		  {
			  int i=0;
			  for(Iterator iter =allIndustryVerticals.iterator(); iter.hasNext();)
			  {
				  AimsIndustryFocu indFoc=(AimsIndustryFocu)iter.next();
				  indFoc.getIndustryId();
				  /*if(isBlankString( emailAddress[i]))
				  {
					  errors.add( ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.vertical.email.address", indFoc.getIndustryName()));
				  }*/
				  if(emailAddress[i].length()>1000)
				  {
					  errors.add( ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.length.vertical.email.address", indFoc.getIndustryName()));
				  }
				  else if(!isBlankString( emailAddress[i]))
				  {
					if(!validateEmailAddress(emailAddress[i].split(",")))
				  	{
						errors.add( ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.invalid.vertical.email.address", indFoc.getIndustryName()));
				  	 }
				  }
				  indFoc.setEmailAddress(emailAddress[i]);
				  
			  	  
			  i++;
			  
			  }
		  
		  }
		  return errors;
    }


	public String[] getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String[] emailAddress) {
		this.emailAddress = emailAddress;
	}


	public Long[] getIndustryId() {
		return industryId;
	}


	public void setIndustryId(Long[] industryId) {
		this.industryId = industryId;
	}
	
	

    private boolean validateEmailAddress(String [] temp)
    {
    	boolean flage=true;
    	if(temp!=null)
    	{
    		for(int i=0;i<temp.length;i++)
    		{
    			if(!isValidEmail(temp[i]))
    			{
    				flage=false;
    			}
    		}
    	}
    	else //Array contain no email address
    	{
    		flage=false;
    	}
    	
    	return flage;
    }

	
}
