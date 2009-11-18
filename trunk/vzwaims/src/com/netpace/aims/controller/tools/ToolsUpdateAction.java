package com.netpace.aims.controller.tools;

import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.tools.AimsToolsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.tools.AimsTool;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.MiscUtils;

/**
 * This class takes care of action for display of update form of Tools.
 *
 * @struts.action path="/toolUpdate"
 *                name="ToolsForm"
 *                scope="request"
 *                input="/tools/toolsUpdate.jsp"
 *				  			validate="true"
 * @struts.action-forward name="update" path="/tools/toolsUpdate.jsp"
 * @struts.action-forward name="listview" path="/toolSetup.do"
 * @struts.action-forward name="view" path="/tools/toolView.jsp"
 * @author Ahson Imtiaz
 */
public class ToolsUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(ToolsUpdateAction.class.getName());
        
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.TOOLS);

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String forward = "listview";
        String taskname = "";
        Object o_param;

        AimsTool atools = new AimsTool();
        AimsPlatform platform = null;
        HashSet platformSet = new HashSet();
        ToolsForm toolForm = (ToolsForm) form;
        AimsUser oAimsUser = (AimsUser) (session.getAttribute(AimsConstants.AIMS_USER));
        String currUser = oAimsUser.getUsername();

        o_param = request.getParameter("taskPerform");

        if (o_param != null)
        {
            taskname = request.getParameter("taskPerform");
            request.setAttribute("taskPerform", (String) o_param);
            toolForm.setTaskPerform(taskname);
        }
        else
        {
            taskname = "";
        }

        if (toolForm.getTaskPerform().equalsIgnoreCase("create"))
        {
            atools.setCreatedBy(currUser);
            atools.setCreatedDate(new Date());
            atools.setToolName(toolForm.getToolName());
            atools.setToolDesc(toolForm.getDescription());
            atools.setUrl(toolForm.getDownloadURL());
            if ("3".equals(toolForm.getShareType())){
            	atools.setShowAll("N");
            }
            else{
            	atools.setShowAll("Y");
            }
            atools.setPlatform(MiscUtils.ConvertPlatformArrayToHashSet(toolForm.getPlatformIds()));
            atools.setAimsContracts(MiscUtils.ConvertContractArrayToHashSet(toolForm.getContractList()));
            atools.setAimsAppCategories(new HashSet(AimsToolsManager.getAimsCategories(toolForm.getCategoriesIds())));
            try {
				atools.setToolId(AimsToolsManager.saveOrUpdate(atools, toolForm.getDisplayFilenameId(), currUser ));
				toolForm.setToolId(atools.getToolId());
				toolForm.setDisplayFilenameId(new Long(0));
			} catch (AimsException e) {
				ActionErrors errors=new ActionErrors();
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(e.getMessage()));				
				saveErrors(request, errors);
				return mapping.getInputForward();
			}
            forward = "listview";
        }
        else if (toolForm.getTaskPerform().equalsIgnoreCase("update"))
        {

            atools = AimsToolsManager.getAimsTool(toolForm.getToolId());
            atools.setToolName(toolForm.getToolName());
            atools.setToolDesc(toolForm.getDescription());
            if (StringUtils.isNotEmpty(toolForm.getDownloadURL())){
            	atools.setUrl(toolForm.getDownloadURL());
            	atools.setFileName(null);
            	toolForm.setDisplayFilename(null);
            }
            if (StringUtils.isNotEmpty(toolForm.getDisplayFilename())){
            	atools.setUrl(null);
            }
            if ("3".equals(toolForm.getShareType())){
            	atools.setShowAll("N");
            }
            else{
            	atools.setShowAll("Y");
            }
            atools.setPlatform(MiscUtils.ConvertPlatformArrayToHashSet(toolForm.getPlatformIds()));
            atools.setAimsContracts(MiscUtils.ConvertContractArrayToHashSet(toolForm.getContractList()));
            atools.setAimsAppCategories(new HashSet(AimsToolsManager.getAimsCategories(toolForm.getCategoriesIds())));
            try {
                AimsToolsManager.saveOrUpdate(atools, toolForm.getDisplayFilenameId(), currUser);
                toolForm.setDisplayFilenameId(new Long(0));
			} catch (AimsException e) {
				ActionErrors errors=new ActionErrors();
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(e.getMessage()));				
				saveErrors(request, errors);
				return mapping.getInputForward();
			}
            forward = "listview";
        }

        log.debug("Forward String " + forward + " Task Name : " + toolForm.getTaskPerform());
        return mapping.findForward(forward);
    }

}
