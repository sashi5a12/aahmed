package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ApplicationInformation;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.accounts.AccountForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/applicationsViewDelete"
 *                name="ApplicationsFilterForm"
 *                scope="request"
 *				  			validate="false"
 *                input="/application/applicationsViewDelete.jsp"
 * @struts.action-forward name="view" path="/application/applicationsViewDelete.jsp"
 * @author Adnan Makda.
 */
public class ApplicationsViewDeleteAction extends BaseAction
{
    //private HashMap fieldMapper = new HashMap();
    private HashMap privilegeMapper = new HashMap();

    static Logger log = Logger.getLogger(ApplicationsViewDeleteAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //fillFieldMapper(request);
        fillPrivilegeMapper();

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);

        String forward = "view";
        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        String taskname;

        Long userId = user.getUserId();
        Long allianceId = user.getAimsAllianc();
        Long contactId = user.getAimsContact().getContactId();
        String userType = user.getUserType();

        Object o_param;
        String filterPlatforms=null;
        String filterStatuses=null;
        
        String sortBy=request.getParameter("sort_field");
        String lastSortBy=request.getParameter("lastSortBy");
        String sortOrder=request.getParameter("sort_order");
        String isPageLnkClicked=request.getParameter("isPageLnk");
        int statusCount=0;
        o_param = request.getParameter("task");
        if (o_param != null)
            taskname = request.getParameter("task");
        else
            taskname = "view";

        //Get Form
        ApplicationsFilterForm applicationsFilterForm = (ApplicationsFilterForm) form;

        o_param = request.getParameter("cancel_search");
        if (o_param != null)
        {
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_TITLE);
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_PLATFORM);
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_STATUS);
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_CATEGORY);
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_SUB_CATEGORY);
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_BREW_APP_TYPE);
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_CONTRACT);
            session.removeAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_DEVICES);
            request.setAttribute("cancel_search", "true");
        }
        
        o_param = request.getAttribute("ADV_SEARCH");
        if (AimsConstants.VZW_USERTYPE.equals(userType) && o_param != null){
        	applicationsFilterForm.setSelectedFilterPlatform(applicationsFilterForm.getAllSelectedFilterPlatform());
        	applicationsFilterForm.setSelectedFilterStatus(applicationsFilterForm.getAllSelectedFilterStatus());
        }
        
        if (taskname.equalsIgnoreCase("filterview"))
        {
            applicationsFilterForm = (ApplicationsFilterForm) form;
            request.setAttribute("filter_text", applicationsFilterForm.getFilterText());
            request.setAttribute("filter_field", applicationsFilterForm.getFilterField());
            request.setAttribute("sort_field", applicationsFilterForm.getSortField());
            request.setAttribute("sort_order", applicationsFilterForm.getSortOrder());
            request.setAttribute("app_type", applicationsFilterForm.getTypeOfApplicationsToView());
        }

        o_param = request.getParameter("app_type");
        if (o_param != null)
        {
            applicationsFilterForm.setTypeOfApplicationsToView((String) o_param);
            request.setAttribute("app_type", (String) o_param);
        }
        else
        {
            if (request.getAttribute("app_type") == null)
            {
                log.debug("\nIn Application View Delete the app_type is null\n");
                applicationsFilterForm.setTypeOfApplicationsToView(ManageApplicationsConstants.APP_TYPE_ALL_APPS);
                request.setAttribute("app_type", ManageApplicationsConstants.APP_TYPE_ALL_APPS);
                request.setAttribute("cancel_search", "true");
            }
        }
        
        if (StringUtils.isEmpty(sortBy)){
        	if (user.getUserType().equals(AimsConstants.VZW_USERTYPE)) {
        		sortBy="2";
        	}
        	else {
        		sortBy="3";
        	}
        }
        
        if (StringUtils.isEmpty(isPageLnkClicked)){
	        //Click on menu item
	        if (StringUtils.isEmpty(lastSortBy)){
	        	sortOrder="asc"; //Current Sorting order
	        }
	        else if (lastSortBy.equalsIgnoreCase(sortBy)==true && "asc".equalsIgnoreCase(sortOrder)){ //user click on same column       	
	        	sortOrder="desc";
	        }
	        else if (lastSortBy.equalsIgnoreCase(sortBy)==true && "desc".equalsIgnoreCase(sortOrder)){ //user click on same column
	        	sortOrder="asc";
	        }	        	        
	        else if (lastSortBy.equalsIgnoreCase(sortBy)==false){ //user click on different column
	        	sortOrder="asc";
	        }
        }

        if (StringUtils.isEmpty(sortOrder)){
        	sortOrder="asc";
        }
        else if (sortOrder.equalsIgnoreCase("asc"));
        else if	(sortOrder.equalsIgnoreCase("desc"));
        else sortOrder="asc";
        
        applicationsFilterForm.setSortField(sortBy);
        applicationsFilterForm.setSortOrder(sortOrder);
        request.setAttribute("sort_order", sortOrder);
        request.setAttribute("sort_field", sortBy);
        request.setAttribute("lastSortBy", sortBy);
        
        o_param = request.getParameter("filter_text");
        if (o_param != null)
        {
            request.setAttribute("filter_text", (String) o_param);
            applicationsFilterForm.setFilterText((String) o_param);
        }

        o_param = request.getParameter("filter_field");
        if (o_param != null)
        {
            request.setAttribute("filter_field", (String) o_param);
            applicationsFilterForm.setFilterField((String) o_param);
        }

        o_param = request.getParameter("page_id");
        if (o_param != null)
        {
            try
            {
                pageNo = Integer.parseInt((String) o_param);
            }
            catch (NumberFormatException ex)
            {
                //Ignore
            }
        }
        if (applicationsFilterForm.getSelectedFilterPlatform()!=null && applicationsFilterForm.getSelectedFilterPlatform().length>0){
        	String[] selectedFilterPlatform=applicationsFilterForm.getSelectedFilterPlatform();
        	if (selectedFilterPlatform.length == 1 && AimsConstants.FILTER_SHOW_ALL.equals(selectedFilterPlatform[0])==false ){
        		filterPlatforms=selectedFilterPlatform[0];
        	}
        	else if (selectedFilterPlatform.length > 1){
        		filterPlatforms="";        			
	        	for(int i=0; i<selectedFilterPlatform.length; i++){
	        		if (selectedFilterPlatform[i].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
		            	if (i != (selectedFilterPlatform.length-1)){
		            		filterPlatforms += selectedFilterPlatform[i] + ",";
		            	}
		            	else {
		            		filterPlatforms += selectedFilterPlatform[i];
		            	}
	        		}
	        	}
        	}
        }
        if (applicationsFilterForm.getSelectedFilterStatus()!=null && applicationsFilterForm.getSelectedFilterStatus().length>0){
        	String[] statuses=applicationsFilterForm.getSelectedFilterStatus();
        	if (statuses.length == 1 && AimsConstants.FILTER_SHOW_ALL.equals(statuses[0])==false){
        		String[] status=statuses[0].split(",");
        		filterStatuses=status[0];
        	}
        	else {
	        	ArrayList list=new ArrayList();
	        	filterStatuses="";
	        	for (int i=0; i<statuses.length; i++){
	        		String[] status=statuses[i].split(",");
	    			if (AimsConstants.FILTER_SHOW_ALL.equalsIgnoreCase(status[0]) == false
	    					&& AimsConstants.FILTER_BREW_SHOW_ALL.equalsIgnoreCase(status[0])==false 
	    					&& AimsConstants.FILTER_ENT_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& AimsConstants.FILTER_MMS_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& AimsConstants.FILTER_SMS_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& AimsConstants.FILTER_VCAST_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& AimsConstants.FILTER_WAP_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& AimsConstants.FILTER_VZAPPZON_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& AimsConstants.FILTER_DASHBOARD_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& AimsConstants.FILTER_JAVA_SHOW_ALL.equalsIgnoreCase(status[0])==false
	    					&& list.contains(status[0])==false){        		
		        			list.add(status[0]);
	    			}
	        	}
	        	for (int i=0; i<list.size(); i++){
	        		if (i != (list.size()-1)){
	        			filterStatuses += list.get(i)+",";
	        		}
	        		else {
	        			filterStatuses += list.get(i);
	        		}        		
	        	}
        	}
        }
        //This LINE WILL CHECK FOR ACCESS PERMISSIONS TO THIS FUNCTIONALITY
        checkAccess(request, getValueFromHash(privilegeMapper, applicationsFilterForm.getTypeOfApplicationsToView()));        

        //NEW QUERY

        ApplicationInformation appInfo = null;
        StringBuffer totalRecords = new StringBuffer();

        Collection appInfoNew =
            AimsApplicationsManager.getApps(
                (String) session.getAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_TITLE),
                Utility.ZeroValueReplacement((Long) session.getAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_PLATFORM)),
                Utility.ZeroValueReplacement((Long) session.getAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_STATUS)),
                Utility.ZeroValueReplacement((Long) session.getAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_CATEGORY)),
                Utility.ZeroValueReplacement((Long) session.getAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_SUB_CATEGORY)),
                (String) session.getAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_BREW_APP_TYPE),
                (String) session.getAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_DEVICES),
                StringFuncs.ConvertArrToString(ApplicationHelper.getPermittedPlatformsForView(request), ","),
                applicationsFilterForm.getTypeOfApplicationsToView(),
                pageNo,
                PAGE_LENGTH,
                userType,
                this.getColumnName(applicationsFilterForm.getSortField(),request),
                applicationsFilterForm.getFilterField(),
                applicationsFilterForm.getFilterText(),
                allianceId,
                userId,
                contactId,
                totalRecords,
                filterStatuses,
                filterPlatforms,
                //archive,
                sortOrder
                );

        for (Iterator it = appInfoNew.iterator(); it.hasNext();)
        {
            appInfo = (ApplicationInformation) it.next();

            //Show Create Button?
            appInfo.setAllowCreate(ApplicationHelper.checkPlatformAccess(request, "create", appInfo.getPlatformId()));

            //Show Edit Button?
            if (ApplicationHelper.checkPlatformAccess(request, "edit", appInfo.getPlatformId()))
                if (appInfo.getPlatformId().longValue() == AimsConstants.WAP_PLATFORM_ID.longValue())
                    appInfo.setAllowEdit(WapApplicationHelper.checkEditAccess(userType, appInfo.getPhaseId()));
                else
                    appInfo.setAllowEdit(ApplicationHelper.checkEditAccess(userType, appInfo.getPhaseId()));
            else
                appInfo.setAllowEdit(false);

            //Show Delete Button?
            if (ApplicationHelper.checkPlatformAccess(request, "delete", appInfo.getPlatformId()))
                if (appInfo.getPlatformId().longValue() == AimsConstants.WAP_PLATFORM_ID.longValue())
                    appInfo.setAllowDelete(WapApplicationHelper.checkEditAccess(userType, appInfo.getPhaseId()));
                else if ( AimsConstants.JAVA_PLATFORM_ID.equals(appInfo.getPlatformId()) )
                	appInfo.setAllowDelete(ApplicationHelper.checkJavaDeleteAccess(userType, appInfo.getPhaseId()));
                else
                    appInfo.setAllowDelete(ApplicationHelper.checkDeleteAccess(userType, appInfo.getPhaseId()));
            else
                appInfo.setAllowDelete(false);

        }

        int pmax = new Double(Math.ceil(new Long(totalRecords.toString()).intValue() * 1.0 / PAGE_LENGTH)).intValue();
        log.debug("Max Page : " + pmax);

        if (pageNo < 1)
            pageNo = 1;
        else if (pageNo > pmax)
            pageNo = pmax;

        if (userType.equals(AimsConstants.VZW_USERTYPE)){
        	statusCount=AimsConstants.FILTER_BREW_VZW.length + AimsConstants.FILTER_ENTERPRISE_VZW.length +
        				AimsConstants.FILTER_MMS_VZW.length + AimsConstants.FILTER_SMS_VZW.length +
        				AimsConstants.FILTER_VCAST_VZW.length + AimsConstants.FILTER_WAP_VZW.length +
        				AimsConstants.FILTER_VZ_APP_ZON_VZW.length + 
        				AimsConstants.FILTER_DASHBOARD_VZW.length;        	
        }
        else {
        	statusCount=AimsConstants.FILTER_BREW_ALLIANCE.length + AimsConstants.FILTER_ENTERPRISE_ALLIANCE.length +
						AimsConstants.FILTER_MMS_ALLIANCE.length + AimsConstants.FILTER_SMS_ALLIANCE.length +
						AimsConstants.FILTER_VCAST_ALLIANCE.length + AimsConstants.FILTER_WAP_ALLIANCE.length +
						AimsConstants.FILTER_VZ_APP_ZON_ALLIANCE.length + 
						AimsConstants.FILTER_DASHBOARD_ALLIANCE.length;
        	
        }
        
        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));
        setFilterPlatformString(request, applicationsFilterForm);
        setFilterStatusString(request, applicationsFilterForm);
        request.setAttribute("statusCount", statusCount+"");
        
        request.setAttribute("AimsApplicationsInformation", appInfoNew);        
        return mapping.findForward(forward);
    }

    /**
     *	This function fills the Hash, mapping 'Resource Field Constant' with 'Privilege Constants'
     */
    public void fillPrivilegeMapper()
    {
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_ALL_APPS, AimsPrivilegesConstants.MANAGE_ALL_APPLICATIONS);        
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_ARCHIVE_APPS, AimsPrivilegesConstants.MANAGE_ARCHIVED_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_MY_APPS, AimsPrivilegesConstants.MANAGE_MY_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_APPS, AimsPrivilegesConstants.MANAGE_NEW_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_BREW_APPS, AimsPrivilegesConstants.MANAGE_NEW_BREW_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_BREW_CONCEPTS, AimsPrivilegesConstants.MANAGE_NEW_BREW_CONCEPTS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_ENTERPRISE_APPS, AimsPrivilegesConstants.MANAGE_NEW_ENTERPRISE_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_MMS_APPS, AimsPrivilegesConstants.MANAGE_NEW_MMS_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_SMS_APPS, AimsPrivilegesConstants.MANAGE_NEW_SMS_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_VCAST_VIDEO_APPS, AimsPrivilegesConstants.MANAGE_NEW_VCAST_VIDEO_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_WAP_APPS, AimsPrivilegesConstants.MANAGE_NEW_WAP_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_VZAPPZONE_APPS, AimsPrivilegesConstants.MANAGE_NEW_VZAPPZONE_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_SAVED_APPS, AimsPrivilegesConstants.MANAGE_SAVED_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_TEST_APPS, AimsPrivilegesConstants.MANAGE_TEST_APPLICATIONS);
    }

    /**
    *	This function fills the Hash, mapping 'Resource Field Constant' with 'DB Field Constant'
    */
    /*
    public void fillFieldMapper(HttpServletRequest request)
    {
        fieldMapper.put(getResString("ManageApplicationForm.field.companyName", request), ApplicationsManagerHelper.FIELD_COMPANY_NAME);
        fieldMapper.put(getResString("ManageApplicationForm.field.platformName", request), ApplicationsManagerHelper.FIELD_PLATFORM_NAME);
        fieldMapper.put(getResString("ManageApplicationForm.field.applicationTitle", request), ApplicationsManagerHelper.FIELD_TITLE);
        fieldMapper.put(getResString("ManageApplicationForm.field.applicationVersion", request), ApplicationsManagerHelper.FIELD_VERSION);
        fieldMapper.put(getResString("ManageApplicationForm.field.category", request), ApplicationsManagerHelper.FIELD_CATEGORY_NAME);
        fieldMapper.put(getResString("ManageApplicationForm.field.brewAppType", request), ApplicationsManagerHelper.FIELD_BREW_APP_TYPE);
        fieldMapper.put(getResString("ManageApplicationForm.field.dateSubmitted", request), ApplicationsManagerHelper.FIELD_CREATE_DATE_NAME);
        fieldMapper.put(getResString("ManageApplicationForm.field.currentStatus", request), ApplicationsManagerHelper.FIELD_PHASE_NAME);
        fieldMapper.put(getResString("ManageApplicationForm.field.onHold", request), ApplicationsManagerHelper.FIELD_IF_ON_HOLD);
    }
    */

    /**
    *	This function gets resource string.
    */
    /*
    public String getResString(String key, HttpServletRequest request)
    {
        return this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage(key);
    }
    */

    /**
    *	This function gets value from HashMap
    */
    public String getValueFromHash(HashMap collection, String key)
    {
        if (collection.containsKey(key))
            return (String) collection.get(key);
        else
            return "";
    }

    private void setFilterPlatformString(HttpServletRequest request, ApplicationsFilterForm appForm) {
        StringBuffer value = new StringBuffer();
		HashMap platforms = new HashMap();
		platforms.put(AimsConstants.BREW_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_BREW);
		platforms.put(AimsConstants.SMS_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_SMS);
		platforms.put(AimsConstants.MMS_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_MMS);
		platforms.put(AimsConstants.WAP_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_WAP);
		platforms.put(AimsConstants.ENTERPRISE_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_ENTERPRISE);
		platforms.put(AimsConstants.VCAST_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_VCAST);
		platforms.put(AimsConstants.VZAPPZONE_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_VZ_APP_ZON);
		platforms.put(AimsConstants.DASHBOARD_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_DASHBOARD);
		platforms.put(AimsConstants.JAVA_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_JAVA);
//		platforms.put(AimsConstants.DASHBOARD_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_DASHBOARD);

        for (int i=0; i<appForm.getSelectedFilterPlatform().length; i++) {
        	if (appForm.getSelectedFilterPlatform()[i].equalsIgnoreCase("SHOW_ALL")){
        		value=new StringBuffer("Showing All");
        		break;
        	}
        	else if (i != (appForm.getSelectedFilterPlatform().length-1)){
        		value.append(platforms.get(appForm.getSelectedFilterPlatform()[i])).append(", ");
        	}
        	else {
        		value.append(platforms.get(appForm.getSelectedFilterPlatform()[i]));
        	}
        }
        request.setAttribute("filterPlateform", value.toString());
    }
    
    private void setFilterStatusString(HttpServletRequest request, ApplicationsFilterForm appForm) {
        StringBuffer value = new StringBuffer();
		HashMap statusMap = new HashMap();
		List list=new ArrayList();
				
		statusMap.put(AimsConstants.SUBMISSION_ID.toString(),AimsConstants.FILTER_LABEL_SUBMITTED);
  		statusMap.put(AimsConstants.EVALUATED_ID.toString(),AimsConstants.FILTER_LABEL_EVALUATED);
  		statusMap.put(AimsConstants.TESTING_ID.toString(),AimsConstants.FILTER_LABEL_UNDER_TESTING);
  		statusMap.put(AimsConstants.ACCEPTANCE_ID.toString(),AimsConstants.FILTER_LABEL_ACCEPTED);
  		statusMap.put(AimsConstants.REJECTED_ID.toString(),AimsConstants.FILTER_LABEL_NOT_ACCEPTED);
  		statusMap.put(AimsConstants.SUNSET_ID.toString(),AimsConstants.FILTER_LABEL_SUNSET);
		statusMap.put(AimsConstants.ASSIGNED_ID.toString(),AimsConstants.FILTER_LABEL_ASSIGNED);
		statusMap.put(AimsConstants.PHASE_INITIAL_APPROVAL_ID.toString(),AimsConstants.FILTER_LABEL_INITIAL_APPROVAL);
		statusMap.put(AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.toString(),AimsConstants.FILTER_LABEL_BUSINESS_APPROVAL_GRANTED);
		statusMap.put(AimsConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID.toString(),AimsConstants.FILTER_LABEL_BUSINESS_APPROVAL_DENIED);
		statusMap.put(AimsConstants.PHASE_TESTING_PASSED_ID.toString(),AimsConstants.FILTER_LABEL_TESTING_PASSED);
		statusMap.put(AimsConstants.PHASE_SUBMITTED_DCR_ID.toString(),AimsConstants.FILTER_LABEL_SUBMITTED_DCR);
		statusMap.put(AimsConstants.PHASE_PUBLICATION_READY_ID.toString(),AimsConstants.FILTER_LABEL_PUBLICATION_READY);
		statusMap.put(AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.toString(),AimsConstants.FILTER_LABEL_COMPLETED_IN_PRODUCTION);
		statusMap.put(AimsConstants.SAVED_ID.toString(), AimsConstants.FILTER_LABEL_SAVED);
		statusMap.put(AimsConstants.CERTIFICATION_ID.toString(), AimsConstants.FILTER_LABEL_CERTIFICATION);
		statusMap.put(AimsConstants.CONCEPT_ACCEPTED_ID.toString(), AimsConstants.FILTER_LABEL_CONCEPT_ACCEPTED);
		statusMap.put(AimsConstants.CONCEPT_REJECTED_ID.toString(), AimsConstants.FILTER_LABEL_CONCEPT_REJECTED);
		statusMap.put(AimsConstants.PHASE_INITIAL_DENIED_ID.toString(), AimsConstants.FILTER_LABEL_INITIAL_DENIED);
		statusMap.put(AimsConstants.PHASE_PENDING_DCR_ID.toString(), AimsConstants.FILTER_LABEL_PENDING_DCR);
		statusMap.put(AimsConstants.PHASE_PENDING_ARM_ID.toString(), AimsConstants.FILTER_LABEL_PENDING_ARM);
		statusMap.put(AimsConstants.PHASE_TESTING_FAILED_ID.toString(), AimsConstants.FILTER_LABEL_TESTING_FAILED);
		statusMap.put(AimsConstants.PHASE_TEST_PASSED_ID.toString(), AimsConstants.FILTER_LABEL_TEST_PASSED);        
        statusMap.put(AimsConstants.PHASE_OTA_TEST_PASSED_ID.toString(), AimsConstants.FILTER_LABEL_OTA_TEST_PASSED);
        statusMap.put(AimsConstants.PHASE_IN_PRODUCTION_ID.toString(), AimsConstants.FILTER_LABEL_IN_PRODUCTION);
        statusMap.put(AimsConstants.PHASE_UNDER_REVIEW.toString(), AimsConstants.FILTER_LABEL_UNDER_REVIEW);
        statusMap.put(AimsConstants.PHASE_REJECTED.toString(), AimsConstants.FILTER_LABEL_REJECTED);

		statusMap.put(AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.toString() ,AimsConstants.FILTER_LABEL_INITIAL_APPROVAL);
		statusMap.put(AimsConstants.PHASE_INITIAL_REJECTED.toString() , AimsConstants.FILTER_LABEL_INITIAL_REJECTED);
		statusMap.put(AimsConstants.PHASE_CONTENT_IN_REVIEW.toString() , AimsConstants.FILTER_LABEL_CONTENT_IN_REVIEW);
		statusMap.put(AimsConstants.PHASE_CONTENT_APPROVED.toString() ,	AimsConstants.FILTER_LABEL_CONTENT_APPROVED);
		statusMap.put(AimsConstants.PHASE_CONTENT_REJECTED.toString() ,	AimsConstants.FILTER_LABEL_CONTENT_REJECTED);
		statusMap.put(AimsConstants.PHASE_PENDING_PRODUCTION.toString() , AimsConstants.FILTER_LABEL_PENDING_PRODUCTION);
		statusMap.put(AimsConstants.PHASE_IN_PRODUCTION.toString() , AimsConstants.FILTER_LABEL_IN_PRODUCTION);
		statusMap.put(AimsConstants.PHASE_CHANNEL_REJECTED.toString() , AimsConstants.FILTER_LABEL_CHANNEL_REJECTED);
		
		statusMap.put(AimsConstants.PHASE_JAVA_RFI_CONTENT_PROG.toString() ,	AimsConstants.FILTER_LABEL_RFI_CONTENT_PROG);
		statusMap.put(AimsConstants.PHASE_JAVA_CONTENT_APPROVED.toString() ,	AimsConstants.FILTER_LABEL_CONTENT_APPROVED);
		statusMap.put(AimsConstants.PHASE_JAVA_RFI_LEGAL_CONTENT.toString() ,	AimsConstants.FILTER_LABEL_RFI_LEGAL_CONTENT);
		statusMap.put(AimsConstants.PHASE_JAVA_LEGAL_APPROVED.toString() ,	AimsConstants.FILTER_LABEL_LEGAL_APPROVED);
		statusMap.put(AimsConstants.PHASE_JAVA_RFI_TAX_REVIEW.toString() ,	AimsConstants.FILTER_LABEL_RFI_TAX_REVIEW);
		statusMap.put(AimsConstants.PHASE_JAVA_PENDING_TAX_APPROVAL.toString() ,	AimsConstants.FILTER_LABEL_RFI_TAX_REVIEW);
		statusMap.put(AimsConstants.PHASE_JAVA_REJECTED.toString() ,	AimsConstants.FILTER_LABEL_REJECTED);
		statusMap.put(AimsConstants.PHASE_JAVA_APPROVED.toString() ,	AimsConstants.FILTER_LABEL_APPROVED);
		statusMap.put(AimsConstants.PHASE_JAVA_SUBMITTED.toString() , AimsConstants.FILTER_LABEL_SUBMITTED);
			
		String[] statuses=appForm.getSelectedFilterStatus();

    	for (int i=0; i<statuses.length; i++){
    		String[] status=statuses[i].split(",");
			if (AimsConstants.FILTER_BREW_SHOW_ALL.equalsIgnoreCase(status[0])==false
					&& AimsConstants.FILTER_BREW_SHOW_ALL.equalsIgnoreCase(status[0])==false 
					&& AimsConstants.FILTER_ENT_SHOW_ALL.equalsIgnoreCase(status[0])==false 
					&& AimsConstants.FILTER_MMS_SHOW_ALL.equalsIgnoreCase(status[0])==false 
					&& AimsConstants.FILTER_SMS_SHOW_ALL.equalsIgnoreCase(status[0])==false 
					&& AimsConstants.FILTER_VCAST_SHOW_ALL.equalsIgnoreCase(status[0])==false 
					&& AimsConstants.FILTER_WAP_SHOW_ALL.equalsIgnoreCase(status[0])==false 
					&& AimsConstants.FILTER_VZAPPZON_SHOW_ALL.equalsIgnoreCase(status[0])==false					
					&& AimsConstants.FILTER_DASHBOARD_SHOW_ALL.equalsIgnoreCase(status[0])==false					
					&& AimsConstants.FILTER_JAVA_SHOW_ALL.equalsIgnoreCase(status[0])==false					
					&& list.contains(status[0])==false){        		
        			list.add(status[0]);
			}
    	}
    	for(int i=0; i<list.size(); i++){
    		if (AimsConstants.FILTER_SHOW_ALL.equalsIgnoreCase(list.get(i).toString())){
    			value=new StringBuffer("Showing All");
    			break;
    		}
    		if (i != (list.size()-1)){
    			value.append(statusMap.get(list.get(i))).append(", ");
    		}
    		else {
    			value.append(statusMap.get(list.get(i)));
    		}    		
    	}
        request.setAttribute("filterStatuses", value.toString());
    }
    private String getColumnName(String key, HttpServletRequest request){
    	HashMap columns=new HashMap();
    	AimsUser user=(AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
    	columns.put("2", "a.company_name");
    	columns.put("3", "p.platform_name");
    	columns.put("4", "app.title");
    	columns.put("7", "app.submitted_date");
    	columns.put("9", "lp.phase_name");    	
    	
    	if (StringUtils.isEmpty(key)){
    		if (user.getUserType().equals(AimsConstants.VZW_USERTYPE)) {
    			return columns.get("2").toString();
    		}
    		else {
    			return columns.get("3").toString();
    		}
    	}
    	else if (columns.containsKey(key)){
    		return columns.get(key).toString();
    	}
    	else {
    		if (user.getUserType().equals(AimsConstants.VZW_USERTYPE)) {
    			return columns.get("2").toString();
    		}
    		else {
    			return columns.get("3").toString();
    		}
    	}
    }    
}