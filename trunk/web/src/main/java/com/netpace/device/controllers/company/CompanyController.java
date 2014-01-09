package com.netpace.device.controllers.company;

import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.services.UserService;
import com.netpace.device.vo.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyController {

    private static final Log log = LogFactory.getLog(CompanyController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    ApplicationPropertiesService applicationPropertiesService;
    @Autowired
    UserService userService;

    @RequestMapping("/secure/managePartners.do")
    public ModelAndView list(HttpServletRequest request, @ModelAttribute(value = "listForm") GenericListForm<CompanyListVO> listForm) {

        log.debug("CompanyController.list (Manage Partners): Start -------------------");

        ModelAndView mav = new ModelAndView("/secure/company/managePartners.jsp");
        PageModel<CompanyListVO> pageModel;

        pageModel = listForm.getPageModel();
        pageModel.setPageSize(applicationPropertiesService.defaultPageSize());

        log.debug("Getting manager partners list from database......");
        companyService.getPartnersList(pageModel);
        //worklistForm.setPageModel(pageModel);

        mav.addObject("listForm", listForm);

        if (StringUtils.isNotEmpty(request.getParameter("COMPANY_DELETED"))) {
            mav.addObject("COMPANY_DELETED", "msg.company.delete");
        }

        log.debug("CompanyController.list (Manage Partners): End --------------------");
        return mav;
    }
}
