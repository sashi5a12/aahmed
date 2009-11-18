package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.Long;
import org.apache.log4j.Logger;

import com.netpace.aims.util.*;


/**
 * @struts.form name="ReconcileAppSelectForm"
 * @author Ahson Imtiaz.
 */
public class ReconcileAppSelectForm  extends 
		com.netpace.aims.controller.BaseValidatorForm
{

    	static Logger log = Logger.getLogger(ReconcileAppSelectForm.class.getName());

		private Long catalogId;
		private Long catalogDataId;
		private Long brewAppsId;
		private Long deviceId;
		private String strTask;
    	private java.lang.Long allianceId;
    	
		/* Public Getter and Setter Functions Starts*/
		
		/*  */
		public void setCatalogId(Long Id)
		{
			this.catalogId = Id;
		}
	
		public Long getCatalogId()
		{
			return this.catalogId;
		}
	

		/*  */
		public void setCatalogDataId(Long Id)
		{
			this.catalogDataId = Id;
		}
	
		public Long getCatalogDataId()
		{
			return this.catalogDataId;
		}
		
		/*  */
		public void setBrewAppsId(Long Id)
		{
			this.brewAppsId = Id;
		}
	
		public Long getBrewAppsId()
		{
			return this.brewAppsId;
		}
		
		/*  */
		public void setDeviceId(Long Id)
		{
			this.deviceId = Id;
		}
	
		public Long getDeviceId()
		{
			return this.deviceId;
		}

	    /**/
	    public java.lang.String getTask() {
	    	  return this.strTask;
	    }
	    
	    public void setTask(String strInput) {
	    	  this.strTask = strInput;
	    }

		 /**/
	    public java.lang.Long getAllianceId() {
	        return this.allianceId;
	    }
	
	    public void setAllianceId(java.lang.Long allianceId) {
	        this.allianceId = allianceId;
	    }

      /* RESET FUNCTION */
		public void reset (ActionMapping mapping, HttpServletRequest request)   {
				
		}

		public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	{

			ActionErrors errors	=	new	ActionErrors();
			/*			
			if ( this.catalogId == null || this.catalogId.intValue() == 0 )
			{
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewForm.selectFile"));
			}
			*/
			return errors;

		}


}

