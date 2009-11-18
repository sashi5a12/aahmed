package com.netpace.aims.controller.application;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.ActionMapping;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;

import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.form name="ApplicationsFilterForm"
 */
public class ApplicationsFilterForm extends BaseValidatorForm {

    static Logger log = Logger.getLogger(ApplicationsFilterForm.class.getName());
    
    /** identifier field */
    private java.lang.String filterString;
		private java.lang.String filterField;
		private java.lang.String filterText;		
		private java.lang.String sortField;		
		private java.lang.String sortOrder;		
		private java.lang.String typeOfApplicationsToView;
		
		private Collection allFilters;
	    
		private final Object[][] filterPlatform=   AimsConstants.FILTER_PLATFORMS;	    
	    
		private Object[][] filterBREW = new Object[0][0];	    
		private Object[][] filterDashboard = new Object[0][0];	    
	    private Object[][] filterEnterprise= new Object[0][0];	    
	    private Object[][] filterSMS = new Object[0][0];
	    private Object[][] filterMMS= new Object[0][0];
	    private Object[][] filterVCAST= new Object[0][0];	    
	    private Object[][] filterWAP= new Object[0][0];	    
	    private Object[][] filterVzAppZon= new Object[0][0];
	    private Object[][] filterJava= new Object[0][0];
	    
		private String[] selectedFilterPlatform=new String[0];
	    private String[] selectedFilterStatus=new String[0];

	    private Collection platforms;	    
	    
	public java.lang.String getFilterString() {
        return this.filterString;
    }

    public void setFilterString(java.lang.String filterString) {
        this.filterString = filterString;
    }

    public java.lang.String getFilterField() {
        return this.filterField;
    }

    public void setFilterField(java.lang.String filterField) {
        this.filterField = filterField;
    }

		public java.lang.String getFilterText() {
        return this.filterText;
    }

    public void setFilterText(java.lang.String filterText) {
        this.filterText = filterText;
    }

		public java.lang.String getSortField() {
        return this.sortField;
    }

    public void setSortField(java.lang.String sortField) {
        this.sortField = sortField;
    }
    
    public java.lang.String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(java.lang.String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public java.lang.String getTypeOfApplicationsToView() {
        return this.typeOfApplicationsToView;
    }

    public void setTypeOfApplicationsToView(java.lang.String typeOfApplicationsToView) {
        this.typeOfApplicationsToView = typeOfApplicationsToView;
    }
    

		public Collection getAllFilters() {
        return this.allFilters;
    }

    public void setAllFilters(Collection allFilters) {
        this.allFilters = allFilters;
    }
    
    public Object[][] getFilterPlatform() {
		return filterPlatform;
	}

	public String[] getSelectedFilterPlatform() {
		return selectedFilterPlatform;
	}

	public void setSelectedFilterPlatform(String[] selectedFilterPlatform) {
		this.selectedFilterPlatform = selectedFilterPlatform;
	}

	public Object[][] getFilterBREW() {
		return filterBREW;
	}

	public Object[][] getFilterDashboard() {
		return filterDashboard;
	}

	public Object[][] getFilterEnterprise() {
		return filterEnterprise;
	}

	public Object[][] getFilterMMS() {
		return filterMMS;
	}

	public Object[][] getFilterSMS() {
		return filterSMS;
	}

	public Object[][] getFilterVCAST() {
		return filterVCAST;
	}

	public Object[][] getFilterWAP() {
		return filterWAP;
	}

	public Object[][] getFilterVzAppZon() {
		return filterVzAppZon;
	}

	public String[] getSelectedFilterStatus() {
		return selectedFilterStatus;
	}

	public void setSelectedFilterStatus(String[] selectedFilterStatus) {
		this.selectedFilterStatus = selectedFilterStatus;
	}

	public void reset (ActionMapping mapping, HttpServletRequest request)   {			
		System.out.println ("\n\n\nI N    R E S E T\n\n\n");
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String userType=user.getUserType();
		if (userType.equals(AimsConstants.VZW_USERTYPE)){
			this.filterBREW=AimsConstants.FILTER_BREW_VZW;
			this.filterDashboard=AimsConstants.FILTER_DASHBOARD_VZW;
			this.filterEnterprise= AimsConstants.FILTER_ENTERPRISE_VZW;	    
			this.filterSMS = AimsConstants.FILTER_SMS_VZW;
			this.filterMMS= AimsConstants.FILTER_MMS_VZW;
			this.filterVCAST= AimsConstants.FILTER_VCAST_VZW;	    
			this.filterWAP= AimsConstants.FILTER_WAP_VZW;
			this.filterVzAppZon=AimsConstants.FILTER_VZ_APP_ZON_VZW;
			this.filterJava = AimsConstants.FILTER_JAVA_VZW;
		}
		else {
			this.filterBREW=AimsConstants.FILTER_BREW_ALLIANCE;
			this.filterDashboard=AimsConstants.FILTER_DASHBOARD_ALLIANCE;
			this.filterEnterprise= AimsConstants.FILTER_ENTERPRISE_ALLIANCE;	    
			this.filterSMS = AimsConstants.FILTER_SMS_ALLIANCE;
			this.filterMMS= AimsConstants.FILTER_MMS_ALLIANCE;
			this.filterVCAST= AimsConstants.FILTER_VCAST_ALLIANCE;	    
			this.filterWAP= AimsConstants.FILTER_WAP_ALLIANCE;			
			this.filterVzAppZon= AimsConstants.FILTER_VZ_APP_ZON_ALLIANCE;
			this.filterJava= AimsConstants.FILTER_JAVA_ALLIANCE;
		}
		this.selectedFilterPlatform=this.getAllSelectedFilterPlatform();
		this.selectedFilterStatus=this.getAllSelectedFilterStatus();
		
		try {
			if ( user.getUserType().equals(AimsConstants.ALLIANCE_USERTYPE) )
				this.platforms = ContractsManager.getPlatformsWithAcceptedContracts(user.getAimsAllianc());
			else
				this.platforms = AimsDevicesManager.getAllPlatformIds();
				
		} catch (HibernateException e) {
			e.printStackTrace();
		}		
	}				
    public String[] getAllSelectedFilterPlatform(){
    	return new String[]{AimsConstants.FILTER_SHOW_ALL};
    }
    public String[] getAllSelectedFilterStatus(){
    	return new String[]{AimsConstants.FILTER_SHOW_ALL};
    }
    
    public Object[][] getFilterJava() {
		return filterJava;
	}

	public void setFilterJava(Object[][] filterJava) {
		this.filterJava = filterJava;
	}

	public Collection getPlatforms() {
		return platforms;
	}

	public void setPlatforms(Collection platforms) {
		this.platforms = platforms;
	}
}

