package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ApplicationInformation;
import com.netpace.aims.bo.application.JavaApplicationInformation;
import com.netpace.aims.bo.application.JavaApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsJavaApps;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 *
 * @struts.action path="/approvedJavaApps"
 *                name="approvedJavaAppsForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do" validate="false"
 * @struts.action-forward name="success" path="/application/approvedJavaApps.jsp" 
 * @author Abbasi
*/

public class ApprovedJavaAppsAction extends BaseAction{
	
	static Logger log = Logger.getLogger(ApprovedJavaAppsAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		if ( log.isDebugEnabled() )
			log.debug("ApprovedJavaAppsAction : start");		
		
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);

        
        //CHECK Platform ACCESS
        if (!(ApplicationHelper.checkPlatformAccess(request, AimsConstants.VIEW_TASK, AimsConstants.JAVA_PLATFORM_ID)))
            throw new AimsSecurityException();        

        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;

        
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
        
        if (StringUtils.isEmpty(sortOrder)){
        	sortOrder="asc";
        }
        else if (sortOrder.equalsIgnoreCase("asc"));
        else if	(sortOrder.equalsIgnoreCase("desc"));
        else sortOrder="asc";
        
        request.setAttribute("sort_order", sortOrder);
        request.setAttribute("lastSortBy", sortBy);
        
        o_param = request.getParameter("filterText");
        if (o_param != null)
        {
        	request.setAttribute("filterText", (String) o_param);
        }

        o_param = request.getParameter("filter_field");
        if (o_param != null)
        {
            request.setAttribute("filter_field", (String) o_param);
        
        
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
		
		sortBy="2";
		
		
        ApplicationInformation appInfo = null;
        StringBuffer totalRecords = new StringBuffer();
		
		
		
        //This LINE WILL CHECK FOR ACCESS PERMISSIONS TO THIS FUNCTIONALITY
        //checkAccess(request, getValueFromHash(privilegeMapper, applicationsFilterForm.getTypeOfApplicationsToView()));
        checkAccess(request, AimsPrivilegesConstants.APPROVED_JAVA_APPLICATIONS);

        
        // Constant values for the function to be used for java approved only applications

        String categoryId = "0";
		String subCategoryId = "0";
		String brewAppType = null;
		String selectedDeviceIds = null;
		String permittedPlatformIds = AimsConstants.JAVA_PLATFORM_ID.toString();
		String queryType = "all_apps";
		String sortField = "p.platform_name";
		String filterField = "";
		String filterText = "";

		
		Collection appInfoNew =
            AimsApplicationsManager.getApps(
	            			request.getParameter("filterText")	 ,     // appTitle 
	            			new Long(AimsConstants.JAVA_PLATFORM_ID) , // platformId     
	            			AimsConstants.PHASE_JAVA_APPROVED   ,      // phaseId
	            			new Long(categoryId),					   // categoryId  
	            			new Long(subCategoryId) ,				   // subCategoryId 	 
	            			brewAppType       , 					   // brewAppType 
	            			selectedDeviceIds             ,            // selectedDeviceIds
	            			permittedPlatformIds,					   // permittedPlatformIds 
	            			queryType,								   // queryType	 	 
	            			pageNo, 								   // pageNo,
	            			PAGE_LENGTH    ,						   // PAGE_LENGTH 
	            			AimsConstants.ALLIANCE_USERTYPE      ,     // userType     
	            			sortField ,	 					   		   // sortField 			
	            			filterField 			     ,        	   // filterField      
	            			filterText          ,  					   // filterText 
	            			new Long(allianceId)      ,   			   // allianceId 
	            			new Long(userId),  						   // userId                            
	            			new Long(contactId),  					   // contactId       
	            			totalRecords   ,    					   // totalRecords   
	            			filterStatuses        ,  				   // filterStatuses       
	            			filterPlatforms ,  						   // filterPlatforms
	            			sortOrder         						   // sortOrder
            			);
		
		Collection javaAppInfoNew = getJavaAppInformationWithKeyword(appInfoNew);
		
		int pmax = new Double(Math.ceil(new Long(totalRecords.toString()).intValue() * 1.0 / PAGE_LENGTH)).intValue();
		if ( log.isDebugEnabled() )
			log.debug("Max Page : " + pmax);

        if (pageNo < 1)
            pageNo = 1;
        else if (pageNo > pmax)
            pageNo = pmax;
        
        
		
		
        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));
        
        request.setAttribute("AimsApplicationsInformation", javaAppInfoNew);
		
        if ( log.isDebugEnabled() )
        	log.debug("ApprovedJavaAppsAction : END");
		return mapping.findForward("success");
	}

	
	/**Returns the collection of Aims Applicaitons and returned a collection of java applicaiton includign keyworks
	 * 
	 * @param appInfoNew
	 * @return
	 * @throws AimsException
	 * @throws HibernateException
	 */
	private Collection getJavaAppInformationWithKeyword(Collection appInfoNew)
			throws AimsException, HibernateException {
		ApplicationInformation applicationInformation;
		JavaApplicationInformation javaApplicationInformation;
		
		Collection javaAppInfoNew = new ArrayList();
		
		
		for (Iterator iterator = appInfoNew.iterator(); iterator.hasNext();) {
			applicationInformation = (ApplicationInformation) iterator.next();
			
			javaApplicationInformation = new JavaApplicationInformation();
			javaApplicationInformation.setAppinfo(applicationInformation);
			
			javaApplicationInformation.setApplicationKeyWord(
					((AimsJavaApps)   (JavaApplicationManager.getJavaApp(applicationInformation.getAppsId())).get("AimsJavaApp")).getAppKeyword()
			);
				
			javaAppInfoNew.add(javaApplicationInformation);
			
		}
		return javaAppInfoNew;
	}
	
	
	

}
