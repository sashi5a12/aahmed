package com.netpace.aims.controller.contracts;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.AimsUtils;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/contracts"                
 *                scope="request" 
 *				  name="ContractForm"
 *				  validate="false"
 *                input="/contracts/contractsInfoView.jsp"  
 * @struts.action-forward name="view" path="/contracts/contractsInfoView.jsp"
 * @struts.action-forward name="delete" path="/contracts/contractsInfoView.jsp"
 * @author Rizwan Qazi
 */
public class ContractsAction extends BaseAction
{

    static Logger log = Logger.getLogger(ContractsAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        log.debug("ContractsAction.execute Start:");
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTRACTS);

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();

        ContractForm contractForm = (ContractForm) form;
        Collection AimsContracts = null;
        ActionErrors errors = new ActionErrors();
        String forward = "";

        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        String sortBy=request.getParameter("sortBy");
        String lastSortBy=request.getParameter("lastSortBy");
        String sortOrder=request.getParameter("sortOrder");
        String isPageLnkClicked=request.getParameter("isPageLnk");

        pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0) {
            contractForm.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "contractTitle"));
        }
        else {
            contractForm.setFilterField(StringFuncs.initializeStringGetParam(contractForm.getFilterField(), "contractTitle"));
        }
        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0) {
            contractForm.setFilterText(request.getParameter("filter_text"));
        }
        log.debug("contractForm.getFilterField() : " + contractForm.getFilterField());
        log.debug("contractForm.getFilterText() : " + contractForm.getFilterText());

        if (StringUtils.isEmpty(sortBy)){
            sortBy="1";
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

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("view"))
        {
            forward = "view";
        }

        if (taskname.equalsIgnoreCase("delete"))
        {

            Long contract_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("contract_id"), "0"));
            AimsContract aimsContract = ContractsManager.getAimsContract(contract_id);
            String deletedContractTitle = "";
            try
            {
                if(aimsContract!=null) {
                    deletedContractTitle = aimsContract.getContractTitle();
                }

                ContractsManager.deleteContract(contract_id);

                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.ContractForm.delete.success", deletedContractTitle));
                saveMessages(request, messages);
            }
            catch (AimsException ae)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));
                saveErrors(request, errors);
            }
            forward = "delete";
        }

        int rows = ContractsManager.getContractsCount(contractForm.getSelectedFilterValue(),
                    getFilterExpression(
                                StringFuncs.initializeStringGetParam(contractForm.getFilterField(), ""),
                                StringFuncs.initializeStringGetParam(contractForm.getFilterText(), "")));
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
        }

        AimsContracts = ContractsManager.getContracts(user_type, PAGE_LENGTH, pageNo, this.getColumnName(sortBy), sortOrder, contractForm.getSelectedFilterValue(),
                getFilterExpression(
                            StringFuncs.initializeStringGetParam(contractForm.getFilterField(), ""),
                            StringFuncs.initializeStringGetParam(contractForm.getFilterText(), "")));
        this.setFilterString(request,contractForm);
        request.setAttribute("AimsContracts", AimsContracts);
        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("lastSortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);

        log.debug("ContractsAction.execute End:");
        return mapping.findForward(forward);
    }

    private void setFilterString(HttpServletRequest request, ContractForm contactForm) {
        StringBuffer value = new StringBuffer();

        for (int i=0; i<contactForm.getSelectedFilterValue().length; i++) {
            if (contactForm.getSelectedFilterValue()[i].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)){
                value.append("Showing All");
                break;
            }
            else if (i != (contactForm.getSelectedFilterValue().length-1)){
                value.append(contactForm.getContractStatus(contactForm.getSelectedFilterValue()[i])).append(", ");
            }
            else {
                value.append(contactForm.getContractStatus(contactForm.getSelectedFilterValue()[i]));
            }
        }
        request.setAttribute("filterValue", value.toString());
    }

    private String getColumnName(String key){
        HashMap columns=new HashMap();

        columns.put("1", "contract.contractTitle");
        columns.put("2", "contract.version");
        columns.put("3", "platform.platformName");
        columns.put("4", "contract.documentFileName");
        columns.put("5", "contract.status");
        columns.put("6", "contract.expiryDate");
        //sortBy 7 was ringNumber, ringNumber was removed after ring_removal changes
        columns.put("8", "contract.isClickThroughContract");

        if (StringUtils.isEmpty(key)){
            return columns.get("1").toString();
        }
        else if (columns.containsKey(key)){
            return columns.get(key).toString();
        }
        else {
            return columns.get("1").toString();
        }
    }

    private String getFilterExpression(String filter_field, String filter_text) {

        StringBuffer expressionBuffer = new StringBuffer("");

        if (filter_text.trim().length() > 0) {
            if (filter_field.equalsIgnoreCase("contractTitle")) {
                expressionBuffer.append("and upper(contract.contractTitle) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%' ");
            }
            if (filter_field.equalsIgnoreCase("contractVersion")) {
                expressionBuffer.append("and upper(contract.version) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%' ");
            }
        }

        return expressionBuffer.toString();
    }//end getFilter Expression
}
