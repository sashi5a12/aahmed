package com.netpace.device.controllers;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.CommentVO;
import com.netpace.device.vo.CompanyVO;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.PartnerProcessVO;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorklistForm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for handling company approval process
 *
 * @author trafique
 */
@Controller
public class CompanyApprovalController {

    private static final Log log = LogFactory.getLog(CompanyApprovalController.class);
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    @Autowired
    private CompanyService companyService;

    /**
     * Render list of pending work items for logged in user
     *
     * @param request
     * @return
     */
    @RequestMapping("/secure/worklist.do")
    public ModelAndView worklist(HttpServletRequest request,
            @ModelAttribute(value = "worklistForm") WorklistForm worklistForm) {
        ModelAndView mav = new ModelAndView("/secure/worklist.jsp");
        PageModel<WorkItem> pageModel;

        log.debug("Get worklist");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        pageModel = worklistForm.getPageModel();
        pageModel.setPageSize(applicationPropertiesService.defaultPageSize());

        // get pagged result
        pageModel = approvalService.getPagedList(loggedInUser, worklistForm.getPageModel());
        worklistForm.setPageModel(pageModel);

        mav.addObject("worklistForm", worklistForm);

        return mav;
    }

    /**
     * execute actions submitted from worklist
     *
     * @param request
     * @param worklistForm
     * @return
     */
    @RequestMapping("/secure/processworklist.do")
    public ModelAndView processWorklist(HttpServletRequest request,
            @ModelAttribute(value = "worklistForm") WorklistForm worklistForm) {

        log.debug("Process Workitems");
        ModelAndView mav;
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (worklistForm.getItems() != null) {
            approvalService.processWorklist(worklistForm.getItems(), loggedInUser);
        }

        mav = worklist(request, worklistForm);
        mav.addObject("actionsExecuted", true);

        return mav;
    }

    /**
     * partner process controller
     *
     * @param request
     * @param companyId
     * @return
     */
    @RequestMapping("/secure/company/process.do")
    public ModelAndView partnerProcess(HttpServletRequest request,
            @RequestParam(value = "companyid", required = true) Integer companyId) {
        ModelAndView mav = new ModelAndView("/secure/company/partnerprocess.jsp");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        CompanyVO companyVO;
        List<CommentVO> comments;
        List<WorkItem> workitems;

        if (loggedInUser.isPartner()) {
            //If the companyId of the logged in partner is null or the companyId of the logged in partner 
            //is not equal to the companyId passed in param then this is a malicious access by a user to 
            //see other users company data thats why we are redirecting him to access denied page
            if (loggedInUser.getCompanyId() == null || loggedInUser.getCompanyId().intValue() != companyId) {
                log.error("Access Denied.");
                return new ModelAndView("error/error403.jsp");
            }
        }

        PartnerProcessVO partnerProcessVO = new PartnerProcessVO();

        companyVO = companyService.getCompanyById(companyId);
        partnerProcessVO.setCompany(companyVO);

        comments = approvalService.getPartnerComments(companyId, loggedInUser);
        partnerProcessVO.setComments(comments);

        workitems = approvalService.getPartnerWorkitems(companyId, loggedInUser);
        partnerProcessVO.setWorkitems(workitems);

        mav.addObject("partnerProcessVO", partnerProcessVO);

        return mav;
    }

    /**
     * processes a partner workitem
     *
     * @param request
     * @param commentVO
     * @return
     */
    @RequestMapping("/secure/company/processWorkitem.do")
    public ModelAndView processWorkitem(HttpServletRequest request,
            @ModelAttribute(value = "workItemVO") WorkItem workItemVO) {
        ModelAndView mav = new ModelAndView();
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        log.debug("Process Workitem");

        approvalService.processWorkitem(workItemVO, loggedInUser);

        mav.setViewName("redirect:/secure/company/process.do");
        mav.addObject("companyid", workItemVO.getWorkflow().getCompanyId());

        return mav;
    }

    @RequestMapping("/secure/company/acceptAgreement.do")
    public ModelAndView acceptAgreement(HttpServletRequest request,
            @ModelAttribute(value = "workItemVO") WorkItem workItemVO) {
        ModelAndView mav = new ModelAndView();
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        log.debug("Process Workitem - Certification NDA Accept Agreement");

        approvalService.acceptAgreement(workItemVO, loggedInUser);

        mav.setViewName("redirect:/secure/company/process.do");
        mav.addObject("companyid", workItemVO.getWorkflow().getCompanyId());

        return mav;
    }

    @RequestMapping("/secure/company/submitAgreement.do")
    public ModelAndView submitAgreement(HttpServletRequest request,
            @ModelAttribute(value = "workItemVO") WorkItem workItemVO) {
        ModelAndView mav = new ModelAndView();
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        log.debug("Process Workitem - Certification NDA Submit Agreement");

        approvalService.submitAgreement(workItemVO, loggedInUser);

        mav.setViewName("redirect:/secure/company/process.do");
        mav.addObject("companyid", workItemVO.getWorkflow().getCompanyId());

        return mav;
    }

    @ModelAttribute("populatedFormElements")
    public Map<String, Map<String, String>> populateFormListElements(
            HttpServletRequest request) {

        Map<String, Map<String, String>> map =
                new HashMap<String, Map<String, String>>();

        String uri = request.getRequestURI();
        if (uri.indexOf("marketingreview.do") != -1) {

            SortedMap<String, String> countryList =
                    applicationPropertiesService.propertiesByType(
                    ApplicationPropertyType.COUNTRIES);

            map.put("countryList", countryList);
        }
        return map;
    }
}
