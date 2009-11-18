package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.*;
import java.lang.Long;
import org.apache.log4j.Logger;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.tools.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;


/**
 * @struts.form name="SalesLeadForm"
 * @author Ahson Imtiaz.
 */

public class SalesLeadForm extends
		com.netpace.aims.controller.BaseValidatorForm
{

	private String companySolution;
	private String salesLeadDesc;
	private String status;
	private String allianceName;
	private Long   salesLeadId;
	private String submittedBy;
	private String submittedByEmailAddress;
	private String submittedByPhone;
	private String submittedDate;
	private String leadQualification;
	private String purchaseDecision;
	private Date   purchaseDecisionDate;
	private Long   assignedTo;
	private String assignedToName;
	private String comments;
	private Collection cAssignedTo;

	static Logger log = Logger.getLogger(SalesLeadForm.class.getName());
  private String filterField;
  private String filterText;
  private Long[] platformIds;
  private com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO[] allPlatforms;
  private Long[] appsId;
  private String estimatedSize;
  private String estimatedARPU;
 // private String submittedDate;

		public String getCompanySolution()
			{
			return this.companySolution;
			}

		public void setCompanySolution(String _companySolution)
			{
			this.companySolution = _companySolution;
			}

		public String getSalesLeadDesc()
			{
			return this.salesLeadDesc;
			}

		public void setSalesLeadDesc(String _salesLeadDesc)
			{
			this.salesLeadDesc = _salesLeadDesc;
			}

		public String getStatus()
			{
			return this.status;
			}

		public void setStatus(String _status)
			{
			this.status = _status;
			}

		public String getAllianceName()
			{
			return this.allianceName;
			}

		public void setAllianceName(String _allianceName)
			{
			this.allianceName = _allianceName;
			}

		public Long getSalesLeadId()
			{
			return this.salesLeadId;
			}

		public void setSalesLeadId(Long _salesLeadId)
			{
			this.salesLeadId = _salesLeadId;
			}

		public String getSubmittedBy()
			{
			return this.submittedBy;
			}

		public void setSubmittedBy(String _submittedBy)
			{
			this.submittedBy = _submittedBy;
			}
			
		public String getSubmittedByEmailAddress()
			{
			return this.submittedByEmailAddress;
			}

		public void setSubmittedByEmailAddress(String submittedByEmailAddress)
			{
			this.submittedByEmailAddress = submittedByEmailAddress;
			}

		public String getSubmittedByPhone()
			{
			return this.submittedByPhone;
			}

		public void setSubmittedByPhone(String submittedByPhone)
			{
			this.submittedByPhone = submittedByPhone;
			}

		public String getSubmittedDate()
			{
			return this.submittedDate;
			}

		public void setSubmittedDate(String submittedDate)
			{
			this.submittedDate = submittedDate;
			}

		public String getLeadQualification()
			{
			return this.leadQualification;
			}

		public void setLeadQualification(String _leadQualification)
			{
			this.leadQualification = _leadQualification;
			}

		public String getEstimatedSize()
			{
			return this.estimatedSize;
			}

		public void setEstimatedSize(String estimatedSize)
			{
			this.estimatedSize = estimatedSize;
			}

		public String getPurchaseDecision()
			{
			return this.purchaseDecision;
			}

		public void setPurchaseDecision(String _purschaseDecision)
			{
			this.purchaseDecision = _purschaseDecision;
			}

		/* */
		public Date getPurchaseDecisionDate()
			{
			return this.purchaseDecisionDate;
			}

		public void setPurchaseDecisionDate(Date _purschaseDecisionDate)
			{
			this.purchaseDecisionDate = _purschaseDecisionDate;
			}

		/* */
		public Long getAssignedTo()
			{
			return this.assignedTo;
			}

		public void setAssignedTo(Long _assignedTo)
			{
			this.assignedTo = _assignedTo;
			}

		/* */
		public String getAssignedToName()
			{
			return this.assignedToName;
			}

		public void setAssignedToName(String _assignedToName)
			{
			this.assignedToName = _assignedToName;
			}
		/* */
		public String getComments()
			{
			return this.comments;
			}

		public void setComments(String _comments)
			{
			this.comments = _comments;
			}

		public Collection getAssignedTosCollection()
		{
			return cAssignedTo;
		}

		public String getStatusDesc()
		{
			if (status.equals("N"))
				return "New";
			if (status.equals("I"))
				return "In Progress";
			if (status.equals("C"))
				return "Closed";
			else
				return "Unknown";
		}
		/* */
      /* RESET FUNCTION */
		public void reset (ActionMapping mapping, HttpServletRequest request)   {

				if (request.getParameter("task") != null && (request.getParameter("task").equals("edit") || request.getParameter("task").equals("update")))
				{
					try {
						cAssignedTo = AllianceSalesLeadManager.getAssingedTos();
				    } catch (HibernateException ex){
				        cAssignedTo = new HashSet();
				    }
                                }

                                setAllPlatforms(VOLookupFactory.getInstance().getAimsPlatformList());

				log.debug("In RESET Sales Lead.");
		}

		public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	{

			ActionErrors errors	=	new	ActionErrors();

                        log.debug("In Validate Sales Lead.");
                        String taskname = request.getParameter("task");
                        log.debug("Task Name : " + taskname);
                        if ( !this.isBlankString(taskname) && "create".equalsIgnoreCase(taskname) && !this.isBlankString(this.estimatedARPU) && (!this.isNumber(this.estimatedARPU)))
      					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.SalesLeadForm.validEstimatedARPU"));

			if (this.isBlankString(this.salesLeadDesc))
					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.SalesLeadForm.salesLeadDesc"));

/*
removed by rqazi June 04, 2004 reference Cedric email dt June 04, 2004
			if (this.isBlankString(this.companySolution))
					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.SalesLeadForm.companySolution"));
*/
			if (this.isBlankString(this.leadQualification))
					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.SalesLeadForm.leadQualification"));

			if (this.isBlankString(this.estimatedSize) )
					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.SalesLeadForm.estimatedSize"));

                        else if (! this.isNumber(this.estimatedSize) )
                          errors.add(ActionErrors.GLOBAL_MESSAGE,
                                     new ActionMessage("error.SalesLeadForm.validEstimatedSize"));

			if (this.isBlankString(this.purchaseDecision))
                          errors.add(ActionErrors.GLOBAL_MESSAGE,
                                     new ActionMessage("error.SalesLeadForm.purschaseDescisionDate"));

			else if (!this.isDate(this.purchaseDecision))
                          errors.add(ActionErrors.GLOBAL_MESSAGE,
                                     new ActionMessage("error.SalesLeadForm.NVPurschaseDescisionDate"));

                        else if ( !this.compareDateWithCurrentDate(this.purchaseDecision) )
                          errors.add(ActionErrors.GLOBAL_MESSAGE,
                                     new ActionMessage("error.SalesLeadForm.CompareDescisionDate"));

			return errors;

		}
  public String getFilterField()
  {
    return filterField;
  }
  public void setFilterField(String filterField)
  {
    this.filterField = filterField;
  }
  public String getFilterText()
  {
    return filterText;
  }
  public void setFilterText(String filterText)
  {
    this.filterText = filterText;
  }
  public Long[] getPlatformIds() {
    return platformIds;
  }
  public void setPlatformIds(Long[] platformIds) {
    this.platformIds = platformIds;
  }
  public com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO[] getAllPlatforms() {
    return allPlatforms;
  }
  public void setAllPlatforms(com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO[] allPlatforms) {
    this.allPlatforms = allPlatforms;
  }
  public String getEstimatedARPU() {
    return estimatedARPU;
  }
  public void setEstimatedARPU(String estimatedARPU) {
    this.estimatedARPU = estimatedARPU;
  }
  public Long[] getAppsId() {
    return appsId;
  }
  public void setAppsId(Long[] appsId) {
    this.appsId = appsId;
  }

}