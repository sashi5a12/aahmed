package com.netpace.aims.controller.devices;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.bo.masters.AimsSysParamManager;
import com.netpace.aims.model.masters.AimsSysParameter;

import net.sf.hibernate.HibernateException;

import java.util.Date;



/**
 * @struts.form name="SysParamEditForm"
 */
public class SysParamEditForm extends BaseValidatorForm {

    /** identifier field */
    private java.lang.String parameterId;

    /** persistent field */
    private java.lang.String parameterName;

    /** nullable persistent field */
    private java.lang.String parameterDesc;

    /** nullable persistent field */
    private java.lang.String parameterValue;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;


    public java.lang.String getParameterId() {
        return this.parameterId;
    }

    public void setParameterId(java.lang.String parameterId) {
        this.parameterId = parameterId;
    }

    public java.lang.String getParameterName() {
        return this.parameterName;
    }

    public void setParameterName(java.lang.String parameterName) {
        this.parameterName = parameterName;
    }

    public java.lang.String getParameterDesc() {
        return this.parameterDesc;
    }

    public void setParameterDesc(java.lang.String parameterDesc) {
        this.parameterDesc = parameterDesc;
    }

    public java.lang.String getParameterValue() {
        return this.parameterValue;
    }

    public void setParameterValue(java.lang.String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.lang.String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public java.util.Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(java.util.Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

	public void reset (ActionMapping mapping, HttpServletRequest request)   {
		
        AimsSysParameter AimsSysParam = null;
        HttpSession session = request.getSession(true); 
		
        if (request.getParameter("task").equalsIgnoreCase("editForm") )
        {			

            try
            {
            	AimsSysParam = AimsSysParamManager.getSysParam(Integer.parseInt(request.getParameter("parameterId")));
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }
			
			
			session.setAttribute("AimsSysParam", AimsSysParam);

            this.parameterId = AimsSysParam.getParameterId().toString();
            this.parameterName = AimsSysParam.getParameterName();
            this.parameterDesc = AimsSysParam.getParameterDesc();
            this.parameterValue = AimsSysParam.getParameterValue();

        }


	}

}
