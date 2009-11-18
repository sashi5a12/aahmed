package com.netpace.aims.controller.application;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.application.JavaApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.system.AimsDeckManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="approvedJavaAppsForm"
 */

public class ApprovedJavaAppsForm extends ApplicationUpdateForm{

	
	static Logger log = Logger.getLogger(ApprovedJavaAppsForm.class.getName());
	
    private java.lang.String filterString;
	private java.lang.String filterField;
	private java.lang.String filterText;
	public java.lang.String getFilterString() {
		return filterString;
	}
	public void setFilterString(java.lang.String filterString) {
		this.filterString = filterString;
	}
	public java.lang.String getFilterField() {
		return filterField;
	}
	public void setFilterField(java.lang.String filterField) {
		this.filterField = filterField;
	}
	public java.lang.String getFilterText() {
		return filterText;
	}
	public void setFilterText(java.lang.String filterText) {
		this.filterText = filterText;
	}		

	

	



}
