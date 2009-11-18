package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.StringContent;

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
 * @struts.form name="EntAppPublishSolutionForm"
 */
public class EntAppPublishSolutionForm extends BaseValidatorForm {
	
	 static Logger log = Logger.getLogger(EntAppPublishSolutionForm.class.getName());
	 
	 
	 private List solutionVOs;
	 
	 private Long allianceId;
	 private String filterField;
	 private String filterText;		
	 private String sortField;	
	 private String allianceType;
	 private int saved_page_id;
	 private int saved_page_max;

	 
	 public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		 
		 ActionErrors errors = new ActionErrors();
		 String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		 log.debug("task_name:"+task_name);
		 
		 if(task_name.equals(AimsConstants.AIMS_SAVE_FORM))
		 {
			 if(solutionVOs != null)
			 {
				 EntAppPublishSolutionVO vo=null;
				 int isPublishedVoCount = this.getIsFeaturedCount();
				 int isPublishedDbCount=0;
				 boolean skipValidation=true;
				 try
				 {
					 isPublishedDbCount=AimsEntAppsManager.getFeaturedSolutionsCount(convertAppIdsToString(","));
					 int totalCount=isPublishedDbCount + isPublishedVoCount;
					 if(totalCount>AimsConstants.IS_FEATURED_MAX_COUNT)
					 {
						 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.isFeature.count.exceed"));
						 skipValidation=false;
					 }
				 }
				 catch(Exception ex)
				 {
					 log.error("Error occur while getting isFeatured count from DB", ex);
					 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.isFeatured"));
				 }
				 //perform validation
				 if(skipValidation)
				 {
					 for(int i=0; i<solutionVOs.size(); i++)
					 {
						 vo = null;
						 vo = (EntAppPublishSolutionVO) solutionVOs.get(i);
						 
						 //Check for isPublished
						 
						 
						 String mobilePro = StringFuncs.NullValueReplacement(vo.getIsMobileProfessional());
						 String soHo = StringFuncs.NullValueReplacement(vo.getIsSoho());
						 String sme = StringFuncs.NullValueReplacement(vo.getIsSme());
						 String enterprise = StringFuncs.NullValueReplacement(vo.getIsEnterprise());
						 
						 // if no market segment selected
						 if(mobilePro.equals("") && soHo.equals("") && sme.equals("") && enterprise.equals(""))
						 {
							 errors.add( ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.publishSolution.marketSegment", vo.getAppTitle()));
							  
						 }
						//if isPublished = Y and isFeatured checked
						 if(this.isBlankString(vo.getIsPublished()) && !this.isBlankString(vo.getIsFeatured()))
		                	 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.select.isFeatured",vo.getAppTitle()));
					 }
				 }
				 //In case of error maintain page state
				 if(errors.size()>0)
				 {
					 request.setAttribute("page_id", saved_page_id);   
					 request.setAttribute("page_max", new Integer(saved_page_max));
					 
					 if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0)
				        {
				            this.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "partner_name"));
				        }
				        else
				        {
				        	this.setFilterField(StringFuncs.initializeStringGetParam(this.getFilterField(), "partner_name"));
				        }

				        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0)
				        {
				        	this.setFilterText(request.getParameter("filter_text"));
				        }

				        if (StringFuncs.NullValueReplacement(request.getParameter("sort_field")).trim().length() > 0)
				        {
				        	this.setSortField(request.getParameter("sort_field"));
				        }
				 }
			 }
			
		 }
		 
		 return errors;
	 }
	
	

	 private String convertAppIdsToString(String delimiter)
	    {
	        String retString = "";

	        if (solutionVOs == null)
	        {
	            retString = "-1";
	        }
	        else
	        {
	        	EntAppPublishSolutionVO vo=null;
	        	 for(int i=0; i<solutionVOs.size(); i++)
	            {
	        		 vo = (EntAppPublishSolutionVO) solutionVOs.get(i);
	                if (i != solutionVOs.size() - 1)
	                {
	                    retString = retString + vo.getAppId() + delimiter;
	                }
	                else
	                {
	                    retString = retString + vo.getAppId();
	                }
	            }
	        }

	        return retString;
	    } 
	 
	 private int getIsFeaturedCount()
	 {
		 int count=0;
		 if(solutionVOs != null)
		 {
			 EntAppPublishSolutionVO vo=null;
			 for(int i=0; i<solutionVOs.size(); i++)
			 {
				 vo = null;
				 vo = (EntAppPublishSolutionVO) solutionVOs.get(i);
				 if(StringFuncs.NullValueReplacement(vo.getIsFeatured()).equals("Y"))
				 {
					 count++;
				 }
				 
			 }
		 }
		 
		 log.debug("Value Object isFeatured count :" + count);
		 return count;
	 }

	public int getSaved_page_id() {
		return saved_page_id;
	}




	public void setSaved_page_id(int saved_page_id) {
		this.saved_page_id = saved_page_id;
	}

	public int getSaved_page_max() {
		return saved_page_max;
	}

	public void setSaved_page_max(int saved_page_max) {
		this.saved_page_max = saved_page_max;
	}
	public Long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
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

	public String getAllianceType() {
		return allianceType;
	}

	public void setAllianceType(String allianceType) {
		this.allianceType = allianceType;
	}

	public List getSolutionVOs() {
		return solutionVOs;
	}

	public void setSolutionVOs(List solutionVOs) {
		this.solutionVOs = solutionVOs;
	}

	public EntAppPublishSolutionVO getSolutionVO(int index) {
		
		if(this.solutionVOs == null){
			this.solutionVOs=new ArrayList();
		}
		
		 while(index >= this.solutionVOs.size()) {
			 this.solutionVOs.add(new EntAppPublishSolutionVO());
		 }
		 
		 return (EntAppPublishSolutionVO)solutionVOs.get(index);
	}
	
	public void setSolutionVO(int index,EntAppPublishSolutionVO entAppPublishSolutionVO) {
		this.solutionVOs.set(index, entAppPublishSolutionVO);
	}
	 
	 
}

