package com.netpace.aims.controller.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.tools.AimsToolsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.tools.AimsTool;
import com.netpace.aims.model.tools.AimsToolCatMaster;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for display of update form of Tools.
 *
 * @struts.action path="/toolSetup"
 *                name="ToolsForm"
 *                scope="request"
 *                input="/toolSetup.do"
 *				  			validate="false"
 * @struts.action-forward name="update" path="/tools/toolsUpdate.jsp"
 * @struts.action-forward name="listview" path="/tools/toolsList.jsp"
 * @struts.action-forward name="view" path="/tools/toolView.jsp"
 * @author Ahson Imtiaz
 */
public class ToolsSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(ToolsSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.TOOLS);

        ToolsForm toolForm = (ToolsForm) form;
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        String dateFormat = this.getResources(request).getMessage("date.format");
        String forward = "listview";
        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);
        String taskname = "";
        Object o_param;

        o_param = request.getParameter("task");

        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
        {
            taskname = "list";
        }

        if (taskname.equalsIgnoreCase("list"))
        {

            int rows = AimsToolsManager.getAimsToolsCount();
            int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

            if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
            {
                pageNo = 1;
                pmax = 1;
            }

            request.setAttribute("AimsTools", AimsToolsManager.getToolsList(PAGE_LENGTH, pageNo));
            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));

            forward = "listview";
        }

        if (taskname.equalsIgnoreCase("delete"))
        {
            try
            {
                int delCount = AimsToolsManager.deleteTool(new Long(request.getParameter("toolId")));
            }
            catch (Exception ae)
            {
                //create an exception and throw so that user knows we are unable
                // to delete the specified record of tool
            }

            int rows = AimsToolsManager.getAimsToolsCount();
            int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

            if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
            {
                pageNo = 1;
                pmax = 1;
            }

            request.setAttribute("AimsTools", AimsToolsManager.getToolsList(PAGE_LENGTH, pageNo));
            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));

            forward = "listview";
        }

        if (taskname.equalsIgnoreCase("create"))
        {
            toolForm.setTaskPerform("create");
            toolForm.setToolId(new java.lang.Long("0"));
            toolForm.setDisplayFilenameId(new Long(0));
            toolForm.setShareType("1");
            toolForm.setDocType("1");
            log.debug(" Contracts List: " + toolForm.getAllContracts().size());
            forward = "update";
        }

        if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("view")))
        {
            String strToolId = request.getParameter("toolId");
            if (strToolId == null)
                return mapping.findForward("listview");
            java.lang.Long lngToolId = new java.lang.Long(strToolId);

            AimsTool atools = AimsToolsManager.getAimsTool(lngToolId);

            if (atools.getAimsAppCategories() == null)
                request.setAttribute("ToolCategories", new java.util.HashSet());
            if (atools.getAimsAppCategories() != null && atools.getAimsAppCategories().size() > 0)
            {
                request.setAttribute("ToolCategories", atools.getAimsAppCategories());
                if(taskname.equalsIgnoreCase("edit")){
	                java.util.Set sTemp = atools.getAimsAppCategories();
	                int iSize = sTemp.size();
	
	                if (iSize > 0)
	                {
	                    java.lang.Long[] lngVV = new java.lang.Long[iSize];
	                    int iCount = 0;
	                    for (java.util.Iterator itr = sTemp.iterator(); itr.hasNext();)
	                    {
	                        lngVV[iCount++] = ((AimsToolCatMaster) itr.next()).getCategoryId();
	                    }
	
	                    toolForm.setCategoriesIds(lngVV);
	                }
                }
            }           

            toolForm.setToolId(atools.getToolId());
            toolForm.setToolName(atools.getToolName());
            toolForm.setDescription(atools.getToolDesc());
            toolForm.setDisplayFilename(atools.getFileName());
            toolForm.setDownloadURL(atools.getUrl());
            toolForm.setDisplayFilenameId(new Long(0));

        	if ("Y".equals(atools.getShowAll()) 
        			&& (atools.getAimsContracts() == null || atools.getAimsContracts().size() == 0 )
        			&& (atools.getPlatform() == null || atools.getPlatform().size() == 0)){
        		toolForm.setShareType("1");
        	}
        	else if ("Y".equals(atools.getShowAll()) 
        			&& (atools.getPlatform() != null && atools.getPlatform().size() > 0)){
        		toolForm.setShareType("2");
        	}
        	else if ("N".equals(atools.getShowAll())             			
        			&& (atools.getAimsContracts() != null && atools.getAimsContracts().size() > 0 )){
        		toolForm.setShareType("3");
        	}
        	
        	if (StringUtils.isNotEmpty(atools.getFileName())){
        		toolForm.setDocType("1");
        	}
        	else {
        		toolForm.setDocType("2");
        	}

            if (atools.getPlatform() == null)
                request.setAttribute("ToolPlatforms", new java.util.HashSet());        	
            if (atools.getPlatform() != null && atools.getPlatform().size()>0 && toolForm.getShareType().equals("2")){
            	request.setAttribute("ToolPlatforms", atools.getPlatform());
            	if(taskname.equalsIgnoreCase("edit")){            	
	            	java.util.Set sTemp = atools.getPlatform();
	            	int iSize = sTemp.size();            	
	            	if (iSize > 0){
	            		java.lang.Long[] lngVV = new java.lang.Long[iSize];
	            		int iCount = 0;
	            		for (java.util.Iterator itr = sTemp.iterator(); itr.hasNext();)
	            		{
	            			lngVV[iCount++] = ((AimsPlatform) itr.next()).getPlatformId();
	            		}
	            		
	            		toolForm.setPlatformIds(lngVV);
	            	}
            	}
            }
            if (atools.getAimsContracts() != null)
                toolForm.setAddedContracts(atools.getAimsContracts());

            if (taskname.equalsIgnoreCase("edit"))
            {
                toolForm.setTaskPerform("update");
                forward = "update";
            }
            else if (taskname.equalsIgnoreCase("view"))
            {
                toolForm.setTaskPerform("view");
                forward = "view";
            }
        }

        log.debug("In ToolsSetup : Forward String " + forward + " Task Name : " + taskname);
        return mapping.findForward(forward);
    }
}
