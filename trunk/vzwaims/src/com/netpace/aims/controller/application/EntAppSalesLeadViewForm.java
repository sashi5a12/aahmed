package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.dataaccess.valueobjects.JmaSalesLeadVO;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.masters.*;

/**
 * @struts.form name="EntAppSalesLeadViewForm"
 */
public class EntAppSalesLeadViewForm extends BaseValidatorForm {
	 
	static Logger log = Logger.getLogger(EntAppSalesLeadViewForm.class.getName());
	
	private List salesLeadVOs;
	
	private String filterField;
	 
	private String filterText;		
	
	private String sortField;
	
	private String view;

	
	
	public JmaSalesLeadVO getSalesLeadVO(int index) {
		
		if(this.salesLeadVOs == null){
			this.salesLeadVOs=new ArrayList();
		}
		
		 while(index >= this.salesLeadVOs.size()) {
			 this.salesLeadVOs.add(new JmaSalesLeadVO());
		 }
		 
		 return (JmaSalesLeadVO)salesLeadVOs.get(index);
	}
	
	
	public void setSalesLeadVO(int index,JmaSalesLeadVO vo) {
		this.salesLeadVOs.set(index, vo);
	}
	
		
	public List getSalesLeadVOs() {
		return salesLeadVOs;
	}

	public void setSalesLeadVOs(List salesLeadVOs) {
		this.salesLeadVOs = salesLeadVOs;
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

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public static Logger getLog() {
		return log;
	}


	public static void setLog(Logger log) {
		EntAppSalesLeadViewForm.log = log;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
	
	
}

