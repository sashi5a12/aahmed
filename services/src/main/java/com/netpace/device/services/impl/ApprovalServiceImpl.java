package com.netpace.device.services.impl;

import com.netpace.device.annotation.utils.ETDConverter;
import com.netpace.device.dao.CommentDao;
import com.netpace.device.dao.CompanyDao;
import com.netpace.device.dao.PaggedResult;
import com.netpace.device.dao.ProductDao;
import com.netpace.device.dao.UserDao;
import com.netpace.device.dao.WorkflowDao;
import com.netpace.device.dao.WorkitemDao;
import com.netpace.device.entities.Company;
import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.Workflow;
import com.netpace.device.entities.Workitem;
import com.netpace.device.services.ApprovalWorkflowService;
import com.netpace.device.services.UserService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.WorkflowStep;
import com.netpace.device.utils.enums.WorkflowType;
import com.netpace.device.utils.enums.WorkitemStatus;
import com.netpace.device.vo.CommentVO;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import com.netpace.notification.services.EventService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.netpace.device.utils.VAPConstants.PLACEHOLDER_COMPANY_DOMAIN;
import static com.netpace.device.utils.VAPConstants.PLACEHOLDER_COMPANY_TITLE;
import static com.netpace.device.utils.VAPConstants.PLACEHOLDER_DATE;
import static com.netpace.device.utils.VAPConstants.PLACEHOLDER_PRODUCT_NAME;
import static com.netpace.device.utils.VAPConstants.PLACEHOLDER_USER_COMMENT;
import static com.netpace.device.utils.VAPConstants.VAP_WORKFLOW_VAR_COMMENT;
import static com.netpace.device.utils.VAPConstants.VAP_WORKFLOW_VAR_LOGGED_USER_NAME;

/**
 *
 * @author trafique
 */
@Service("approvalService")
public class ApprovalServiceImpl extends AbstractApprovalServiceImpl {

    private final static Log log = LogFactory.getLog(ApprovalServiceImpl.class);
    @Autowired
    private WorkitemDao workitemDao;
    @Autowired
    private ApprovalWorkflowService approvalWorkflowService;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkflowDao workflowDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CompanyDao companydDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public PageModel<WorkItem> getPagedList(VAPUserDetails loggedInUser, PageModel<WorkItem> pageModel) {
        PaggedResult<Workitem> paggedResult;
        List<WorkItem> records = new ArrayList<WorkItem>();
        WorkItem workItemVO;
        String[] userRoles;

        // Setting default sort if no sort is applied
        if (StringUtils.isEmpty(pageModel.getSortBy())) {
            pageModel.setSortBy("workitem.startDate");
            pageModel.setAscending(false);
        }

        // Get PaggedResult
        userRoles = loggedInUser.getRolesArray();
        paggedResult = workitemDao.getPaggedWorkitems(WorkitemStatus.InProgress, userRoles,
                pageModel, loggedInUser.getCompanyId(), loggedInUser.isPartner());

        // Populate PageModel
        pageModel.setTotalRecords(paggedResult.getRecords());
        for (Workitem workitem : paggedResult.getList()) {
            workItemVO = ETDConverter.convert(workitem, new WorkItem());
            workItemVO.setWorkflow(ETDConverter.convert(workitem.getWorkflow(), new WorkflowVO()));

            // Remove RFI entry from next actions
            if(workItemVO.getNextActions() != null){
                if( workItemVO.getNextActions().indexOf(VAPConstants.ACTION_RFI) > -1 ){
                    workItemVO.getNextActions().remove(VAPConstants.ACTION_RFI);
                }
            }

            records.add(workItemVO);
        }
        pageModel.setRecords(records);

        return pageModel;
    }

    /**
     * Start company approval process
     *
     * @param companyReview
     */
    @Override
    @Transactional
    public void startApprovalProcess(WorkflowType enumWorkflowType, Integer recordId, VAPUserDetails loggedInUser) {

        log.info("Start company approval process: recordId [" + recordId + "]");
        Workflow workflow = new Workflow();
        workflow.setWorkflowType(enumWorkflowType.getLabel());
        HashMap<String, Object> taskVariables = new HashMap<String, Object>();

        if (enumWorkflowType.equals(WorkflowType.PartnerWorkflow)) {
            Company company = companydDao.get(recordId);
            workflow.setCompany(company);
        } else if (enumWorkflowType.equals(WorkflowType.ProductWorkflow)) {
            VapProduct product = productDao.getReference(recordId);
            workflow.setProduct(product);
            workflow.setCompany(product.getCompany());
        }

        workflow.setStartDate(new Timestamp(new Date().getTime()));
        workflow.populatedAuditFields(loggedInUser.getUsername());
        workflowDao.add(workflow);

        taskVariables.put(VAPConstants.VAP_WORKFLOW_VAR_COMMENT, "");
        taskVariables.put(VAPConstants.VAP_WORKFLOW_VAR_LOGGED_USER_NAME, loggedInUser.getUsername());

        approvalWorkflowService.startApproval(enumWorkflowType.getLabel(), workflow.getId(), taskVariables, loggedInUser);
    }

    /**
     * adds a workitem, and called from the add workitem listener
     *
     * @param workItemVO (title, status, allowedRoles, requireInput,
     * nextActions, workflow.id, startDate) are required
     * @param loggedInUser
     */
    @Override
    @Transactional
    public void addWorkitem(WorkItem workItemVO, String loggedInUserName) {
        Workitem workitem;

        log.info("addWorkitem: workflowId[" + workItemVO.getWorkflow().getId() + "],  title[" + workItemVO.getTitle() + "]");

        workitem = ETDConverter.convert(workItemVO, new Workitem());
        workitem.populatedAuditFields(loggedInUserName);
        workitemDao.add(workitem);

        // Set Product Processing Info Status
        /*Workflow workflow = workflowDao.get(workItemVO.getWorkflow().getId());
         WorkflowType enumWorkflowType = WorkflowType.getByLabel(workflow.getWorkflowType());
         if (enumWorkflowType.equals(WorkflowType.ProductWorkflow)) {
         VapProductProcessInfo productProcessInfo = workflow.getProduct().getProcessingInfo();
         setProductProcessingInfoStepStatus(productProcessInfo, workItemVO.getTitle(), VAPConstants.VAP_WORKFLOW_PRODUCT_STEP_STATUS_NOT_PROCESSED);
         }*/
    }

    /**
     * updates a workitem, and called from the update workitem listener
     *
     * @param workItemVO (title, status, decision, workflow.id) are required
     * params
     * @param loggedInUser
     */
    @Override
    @Transactional
    public void updateWorkitem(WorkItem workItemVO, String loggedInUserName) {
        Workitem workitem;
        WorkitemStatus status;
        String commentText = "";

        log.info("updateWorkitem: workflowId[" + workItemVO.getWorkflow().getId() + "], title[" + workItemVO.getTitle() + "],  status[" + workItemVO.getStatus() + "]");

        // Update Workitem
        status = WorkitemStatus.valueOf(workItemVO.getStatus());
        workitem = workitemDao.getWorkitem(workItemVO.getTitle(), WorkitemStatus.InProgress, workItemVO.getWorkflow().getId());
        workitem.setStatus(status);
        if (status == WorkitemStatus.Processed) {
            workitem.setEndDate(new Timestamp(new Date().getTime()));
        }
        workitem.setActionTaken(workItemVO.getDecision());
        workitem.populatedAuditFieldsOnUpdate(loggedInUserName);
        workitemDao.update(workitem);

        Workflow workflow = workitem.getWorkflow();
        if (StringUtils.isNotBlank(workItemVO.getCommentText())
                && !workItemVO.getCommentText().equals(VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT)) {
            commentText = workItemVO.getCommentText();
        }

        // Add Workitem comment
        if( StringUtils.isNotBlank(workitem.getNextActions()) && workitem.getNextActions().contains(workItemVO.getDecision()) ){
            VapComment comment = super.getComment(workitem.getDisplayTitle(), workItemVO.getDecision(), commentText,
                    workflow.getWorkflowType(), workflow.getCompany(), workflow.getProduct(), loggedInUserName);
            commentDao.addComment(comment);
        }

        // Add product workflow step status
        /*WorkflowType enumWorkflowType = WorkflowType.getByLabel(workflow.getWorkflowType());
         if (enumWorkflowType.equals(WorkflowType.ProductWorkflow)) {

         VapProductProcessInfo productProcessInfo = workflow.getProduct().getProcessingInfo();
         setProductProcessingInfoStepStatus(productProcessInfo, workitem.getTitle(), VAPConstants.VAP_WORKFLOW_PRODUCT_STEP_STATUS_PROCESSED);

         updateProductStatus(workflow.getProduct(), workItemVO.getTitle(), workItemVO.getDecision());
         }*/

    }

    @Override
    @Transactional(readOnly = true)
    public void sendWorkflowNotification(String eventTitle, Integer workflowId, Map<String, Object> variables) {
        Map<String, String> params = new HashMap<String, String>();
        List<String> partnerEmails = null;
        String loggedInUserName;

        log.info("Sending Worklfow Notification: eventTitle[" + eventTitle + "], workflowId[" + workflowId + "]");

        // populate notifivation parameters
        Object[] objects = workflowDao.getNotifParamsByWorkflowId(workflowId);
        if (objects != null && objects.length > 0) {
            log.info("Setting parameters for companyId: " + objects[0]);

            params.put(PLACEHOLDER_COMPANY_TITLE, String.valueOf(objects[1]));
            params.put(PLACEHOLDER_COMPANY_DOMAIN, String.valueOf(objects[2]));
            params.put(PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
            params.put(PLACEHOLDER_USER_COMMENT, String.valueOf(variables.get(VAP_WORKFLOW_VAR_COMMENT)));

            if (String.valueOf(objects[3]).equals(WorkflowType.ProductWorkflow.getLabel())) {
                params.put(PLACEHOLDER_PRODUCT_NAME, String.valueOf(objects[4]));
            }

            partnerEmails = userDao.getUserEmailsByCompanyId(Integer.valueOf(objects[0].toString()));
        }

        // this try catch added to support old workflows
        try {
            loggedInUserName = (String) variables.get(VAP_WORKFLOW_VAR_LOGGED_USER_NAME);
        } catch (ClassCastException cce) {
            loggedInUserName = ((VAPUserDetails) variables.get(VAP_WORKFLOW_VAR_LOGGED_USER_NAME)).getUsername();
        }

        // Raise event
        eventService.raiseEvent(eventTitle, params, null, partnerEmails, null, loggedInUserName);
    }

    /**
     * processes list of workitems
     *
     * @param workItems
     * @param loggedInUser
     */
    @Override
    public void processWorklist(List<WorkItem> workItems, VAPUserDetails loggedInUser) {

        log.info("Processing workitems ");

        for (WorkItem workItem : workItems) {
            // Process workitem activiti task with nextAction
            if (StringUtils.isNotEmpty(workItem.getDecision())) {
                this.processWorkitem(workItem, loggedInUser);
            }
        }
    }

    /**
     * Get all comments by company
     *
     * @param companyId
     * @param loggedInUser
     * @return
     */
    @Override
    @Transactional
    public List<CommentVO> getPartnerComments(Integer companyId, VAPUserDetails loggedInUser) {
        List<CommentVO> list = new ArrayList<CommentVO>();
        List<VapComment> commentsList;

        commentsList = commentDao.getCommentsByCompany(companyId);
        for (VapComment comment : commentsList) {
            list.add(ETDConverter.convert(comment, new CommentVO()));
        }

        return list;
    }

    /**
     * Get all comments by company
     *
     * @param companyId
     * @param loggedInUser
     * @return
     */
    @Override
    @Transactional
    public List<CommentVO> getProductComments(Integer productId, VAPUserDetails loggedInUser) {
        List<CommentVO> list = new ArrayList<CommentVO>();
        List<VapComment> commentsList;

        commentsList = commentDao.getCommentsByProduct(productId);
        for (VapComment comment : commentsList) {
            list.add(ETDConverter.convert(comment, new CommentVO()));
        }

        return list;
    }

    /**
     * Add a company comment
     *
     * @param commentVO
     * @param loggedInUser
     */
    @Override
    @Transactional
    public void addWorkflowComment(CommentVO commentVO, VAPUserDetails loggedInUser) {
        VapComment comment = new VapComment();

        if (StringUtils.isNotBlank(commentVO.getCommentText())
                && !commentVO.getCommentText().equals(VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT)
                && !commentVO.getCommentText().equals(VAPConstants.COMMENT_DEFAULT_TEXT)) {
            comment.setCommentsText("Comment Added: " + commentVO.getCommentText());

            Company company = new Company();
            company.setId(commentVO.getCompanyId());
            comment.setCompany(company);

            if (commentVO.getProductId() != null && commentVO.getProductId() > 0) {
                VapProduct product = new VapProduct();
                product.setProductId(commentVO.getProductId());
                comment.setProduct(product);
                comment.setCommentsType(WorkflowType.ProductWorkflow.getLabel());
            } else {
                comment.setCommentsType(WorkflowType.PartnerWorkflow.getLabel());
            }

            comment.populatedAuditFields(loggedInUser.getUsername());

            commentDao.addComment(comment);
        }
    }

    /**
     * Get all partner workitem which are In-Progress
     *
     * @param companyId
     * @param loggedInUser
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkItem> getPartnerWorkitems(Integer companyId, VAPUserDetails loggedInUser) {
        String[] userRoles;
        List<Workitem> workitems;
        List<WorkItem> list = new ArrayList<WorkItem>();
        WorkItem workItemVO;

        userRoles = userService.getUserRoles(loggedInUser.getId());
        workitems = workitemDao.getPartnerWorkitems(companyId, WorkitemStatus.InProgress, userRoles);

        for (Workitem workitem : workitems) {
            workItemVO = ETDConverter.convert(workitem, new WorkItem());
            workItemVO.setWorkflow(ETDConverter.convert(workitem.getWorkflow(), new WorkflowVO()));
            list.add(workItemVO);
        }

        return list;
    }

    /**
     * processes a workitem
     *
     * @param workItem
     * @param loggedInUser
     */
    @Override
    @Transactional
    public void processWorkitem(WorkItem workItemVO, VAPUserDetails loggedInUser) {
        Workflow workflow;
        HashMap<String, Object> taskVariables = new HashMap<String, Object>();
        String commentText = "";

        log.info("Processing workitem: step[" + workItemVO.getTitle() + "], decision[" + workItemVO.getDecision() + "]");

        if (StringUtils.isEmpty(workItemVO.getDecision())) {
            log.info("workflow decision variable is empty, workflow cannot be processed");
            return;
        }

        Workitem workitem = workitemDao.getWorkitem(workItemVO.getTitle(), WorkitemStatus.InProgress, workItemVO.getWorkflow().getId());

        if (workitem == null) {
            log.info("Workitem not found");
            return;
        } else if (workitem.isLocked()) {
            log.info("Workitem is locked");
            return;
        }

        log.info("Workitem is available");

        workitem.setLocked(true);

        // check comment if have default text
        if (StringUtils.isNotBlank(workItemVO.getCommentText())
                && !workItemVO.getCommentText().equals(VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT)
                && !workItemVO.getCommentText().equals(VAPConstants.COMMENT_DEFAULT_TEXT)) {
            commentText = workItemVO.getCommentText();
        }

        // Add additional variables required for workflow        
        workflow = workflowDao.get(workItemVO.getWorkflow().getId());
        if (workflow.getWorkflowType().equals(WorkflowType.ProductWorkflow.getLabel())
                && workItemVO.getTitle().equals(WorkflowStep.DeviceMarketingReview.toString())) {
            VapProduct product = productDao.get(workflow.getProduct().getProductId());
            taskVariables.put(VAPConstants.VAP_WORKFLOW_VAR_PRODUCT_SUBMISSION_TYPE, product.getProductType());
        }

        taskVariables.put(VAPConstants.VAP_WORKFLOW_VAR_DECISION, workItemVO.getDecision());
        taskVariables.put(VAPConstants.VAP_WORKFLOW_VAR_COMMENT, commentText);
        taskVariables.put(VAPConstants.VAP_WORKFLOW_VAR_LOGGED_USER_NAME, loggedInUser.getUsername());

        // Process workitem activiti task with nextAction
        approvalWorkflowService.processWorkitem(workItemVO, taskVariables, loggedInUser);

        workitem.setLocked(false);
    }

    /**
     * accepts company agreement
     *
     * @param workItem
     * @param loggedInUser
     */
    @Override
    @Transactional
    public void acceptAgreement(WorkItem workItem, VAPUserDetails loggedInUser) {
        Workflow workflow;

        log.info("Processing workitem: step[" + workItem.getTitle() + "], decision[" + workItem.getDecision() + "]");
        workflow = workflowDao.get(workItem.getWorkflow().getId());
        workflow.getCompany().setCertNdaAcceptDate(new Timestamp(new Date().getTime()));

        // Process workitem activiti task with nextAction
        this.processWorkitem(workItem, loggedInUser);
    }

    /**
     * submits company agreement
     *
     * @param workItem
     * @param loggedInUser
     */
    @Override
    @Transactional
    public void submitAgreement(WorkItem workItem, VAPUserDetails loggedInUser) {
        Workflow workflow;

        log.info("Processing workitem: step[" + workItem.getTitle() + "], decision[" + workItem.getDecision() + "]");
        workflow = workflowDao.get(workItem.getWorkflow().getId());
        workflow.getCompany().setCertNdaAcceptDate(new Timestamp(new Date().getTime()));
        workflow.getCompany().setOfflineCertNdaId(workItem.getCompanyOfflineCertNdaId());

        // Process workitem activiti task with nextAction
        this.processWorkitem(workItem, loggedInUser);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowVO getWorkflow(Integer workflowId) {

        Workflow workflow = workflowDao.get(workflowId);
        WorkflowVO workflowVO = ETDConverter.convert(workflow, new WorkflowVO());

        return workflowVO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkItem> getDelayedWorkitems(Date olderThanDate) {
        List<Workitem> delayedWorkitems;
        List<WorkItem> list = new ArrayList<WorkItem>();
        WorkItem workItemVO;

        delayedWorkitems = workitemDao.getDelayedWorkitems(olderThanDate);
        for (Workitem workitem : delayedWorkitems) {
            workItemVO = ETDConverter.convert(workitem, new WorkItem());
            workItemVO.setWorkflow(ETDConverter.convert(workitem.getWorkflow(), new WorkflowVO()));
            list.add(workItemVO);
        }
        return list;
    }
}