package com.netpace.aims.controller.profile;

import java.lang.*;
import java.util.*;
import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="EditProfileNotifForm"
 * @author Ahson Imtiaz.
 */
 
public class EditProfileNotifForm extends BaseValidatorForm{ 

	String task;
	Long[] eventsIds;

/* Property setter and getter methods */
	public String getTask() {

	return task;
	}

	public void setTask(String input ) {

	this.task = input;
	}

	public Long[] getEventsIds() {

	return eventsIds;
	}

	public void setEventsIds(Long[] input ) {

	this.eventsIds = input;
	}

	public void reset(ActionMapping mapping,	HttpServletRequest request) {
		eventsIds = null;
	}
	/* Validation Function */
	public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	{

		ActionErrors errors	=	new	ActionErrors();
		
		/*
		if ( this.rowsLength == null ) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.rowsLength"));
		}
		*/
		return errors;

	}

} 
 /* Class ends */