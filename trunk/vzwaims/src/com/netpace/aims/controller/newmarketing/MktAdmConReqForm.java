package com.netpace.aims.controller.newmarketing;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="MktAdmConReqForm"
 * @author Ahson Imtiaz.
 */

public class MktAdmConReqForm extends BaseValidatorForm
{

    private Integer pageNo;
    private String task;
    private Long crequestId;
    private String[] appsId;
    private String expiryDate;
    private String filterText;
    private String filterField;

    // used by Alliance section of content request approvals and listing
    private Long applicationId;
    private String sortField;

    /**
     * @return Returns the filterField.
     */
    public String getFilterField()
    {
        return filterField;
    }
    /**
     * @param filterField The filterField to set.
     */
    public void setFilterField(String filterField)
    {
        this.filterField = filterField;
    }
    /**
     * @return Returns the filterText.
     */
    public String getFilterText()
    {
        return filterText;
    }
    /**
     * @param filterText The filterText to set.
     */
    public void setFilterText(String filterText)
    {
        this.filterText = filterText;
    }
    /**
     * @return Returns the applicationId.
     */
    public Long getApplicationId()
    {
        return applicationId;
    }
    /**
     * @param applicationId The applicationId to set.
     */
    public void setApplicationId(Long applicationId)
    {
        this.applicationId = applicationId;
    }
    /**
     * @return Returns the expiryDate.
     */
    public String getExpiryDate()
    {
        return expiryDate;
    }
    /**
     * @param expiryDate The expiryDate to set.
     */
    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    /**
     * @return Returns the appsId.
     */
    public String[] getAppsId()
    {
        return appsId;
    }
    /**
     * @param appsId The appsId to set.
     */
    public void setAppsId(String[] appsId)
    {
        this.appsId = appsId;
    }
    /**
     * @return Returns the crequestId.
     */
    public Long getCrequestId()
    {
        return crequestId;
    }
    /**
     * @param crequestId The crequestId to set.
     */
    public void setCrequestId(Long crequestId)
    {
        this.crequestId = crequestId;
    }
    /**
     * @return Returns the pageNo.
     */
    public Integer getPageNo()
    {
        return pageNo;
    }
    /**
     * @param pageNo The pageNo to set.
     */
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    /**
     * @return Returns the task.
     */
    public String getTask()
    {
        return task;
    }
    /**
     * @param task The task to set.
     */
    public void setTask(String task)
    {
        this.task = task;
    }

    /**
     * Function perform validation.
     * */

    //    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    //    {
    //
    //        ActionErrors errors = new ActionErrors();
    //
    //        if (task!= null && task.equals("acceptparts")) {
    //
    //			String[] appsId = getAppsId();
    //			String strAction = null;
    //			boolean bCheckDate = false;
    //			String strPrevAction = null;
    //
    //			if ( appsId != null && appsId.length > 0 ){
    //				for (int iCount=0; iCount < appsId.length ; iCount++) {
    //					strAction = request.getParameter("action_" + appsId[iCount]);
    //					if (strAction.equals("A") ) {
    //                                         if (ContentRequestManager.isEventBasedContent(new Long(appsId[iCount])))
    //                                           errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.ExpiredDate"));
    //                                        }
    //				}
    //			}
    //
    //			if (bCheckDate == true) {
    //	        	if (isBlankString(expiryDate)) {
    //	        		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.ExpiredDate"));
    //	        	}
    //		        else if (!isDate(expiryDate)) {
    //		        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.invalid.ExpiredDate"));
    //		        }
    //			}
    //			else if (!isBlankString(expiryDate) && !isDate(expiryDate))
    //				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.invalid.ExpiredDate"));
    //        }
    //        return errors;
    //
    //    }

    public String getSortField()
    {
        return sortField;
    }
    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
}
