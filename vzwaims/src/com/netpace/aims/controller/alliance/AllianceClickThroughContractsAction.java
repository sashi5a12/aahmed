package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.contracts.ContractsHelper;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.model.core.AimsUser;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * This class deals with alliance click through contracts, e.g., listing click through contracts.
 *
 * @struts.action path="/allianceClickThroughContracts"
 *				  name="AllianceClickThroughContractForm"
 *                scope="request"
 * @struts.action-forward name="viewAllianceClickThroughContracts" path="/alliance/allianceClickThroughContracts.jsp"
 * @struts.action-forward name="allianceClickThroughDetail" path="/allianceContractAcceptReject.do"
 * @author Sajjad Raza
 */
public class AllianceClickThroughContractsAction extends BaseAction {

    private static Logger log = Logger.getLogger(AllianceContractAcceptRejectAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception  {
        String taskName =  StringFuncs.NullValueReplacement(request.getParameter("task"));
        log.debug("========  start AllianceClickThroughContractsAction, taskName = "+taskName);

        String forward = "viewAllianceClickThroughContracts";//show click through contracts list by default
        
        AllianceClickThroughContractForm allianceClickThroughContractForm = (AllianceClickThroughContractForm)form;
        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String currUserType = currUser.getUserType();
        Long alliance_id = null;

        request.setAttribute("allCTPlatforms", AimsConstants.FILTER_PLATFORMS);
        if(currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) {

            //check whether user is allowed to view click through contract
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CLICK_THROUGH_CONTRACTS, AimsSecurityManager.SELECT)))  {
                throw new AimsSecurityException();
            }

            alliance_id = currUser.getAimsAllianc();

            //set allFilterCTPlatforms and available click through contracts in request to show click through contracts in filter
            request.setAttribute("availableClickThroughContracts", AllianceContractInfoManager.getAvailableClickThroughContracts(alliance_id, AimsConstants.CONTRACT_STATUS_ACTIVE, allianceClickThroughContractForm.getSelectedFilterCTPlatform()));
        }
        else {
            //vzw user can not view this screen,
            //in future, if vzw user is allowed to view alliance click through contracts, remove this exception and add related code here
            throw new AimsSecurityException();
        }

        if(taskName.equalsIgnoreCase("allianceClickThroughDetail")) {
            request.setAttribute(ContractsHelper.REQUEST_CONTRACT_REQUESTING_PAGE, ContractsHelper.CONTRACT_REQUESTED_PAGE_ALLIANCE_CLICK_THROUGH);            
            forward = "allianceClickThroughDetail";
        }
        setFilterPlatformString(request, allianceClickThroughContractForm);
        log.debug("========  end AllianceClickThroughContractsAction, forward= "+forward);
        return mapping.findForward(forward);
    }

    private void setFilterPlatformString(HttpServletRequest request, AllianceClickThroughContractForm filterFrm) {
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

        for (int i=0; i<filterFrm.getSelectedFilterCTPlatform().length; i++) {
            if (filterFrm.getSelectedFilterCTPlatform()[i].equalsIgnoreCase("SHOW_ALL")){
                value=new StringBuffer("Showing All");
                break;
            }
            else if (i != (filterFrm.getSelectedFilterCTPlatform().length-1)){
                value.append(platforms.get(filterFrm.getSelectedFilterCTPlatform()[i])).append(", ");
            }
            else {
                value.append(platforms.get(filterFrm.getSelectedFilterCTPlatform()[i]));
            }
        }
        request.setAttribute("filterCTPlatform", value.toString());
    }
}
