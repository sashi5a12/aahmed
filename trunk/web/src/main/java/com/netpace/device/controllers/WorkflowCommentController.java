package com.netpace.device.controllers;

import com.netpace.device.services.ApprovalService;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.CommentVO;
import com.netpace.device.vo.VAPUserDetails;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author trafique
 */
@Controller
public class WorkflowCommentController {
    
    @Autowired
    private ApprovalService approvalService;
    
    /**
     * partner process add comment controller
     * 
     * @param request
     * @param commentVO
     * @return 
     */
    @RequestMapping("/secure/addComment.do")
    public ModelAndView addComment(HttpServletRequest request, @RequestParam(value="redirectUrl") String redirectUrl,
            @ModelAttribute(value = "commentVO") CommentVO commentVO) {
        ModelAndView mav = new ModelAndView();
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        approvalService.addWorkflowComment(commentVO, loggedInUser);
        
        mav.setViewName("redirect:"+redirectUrl);
        mav.addObject("companyid", commentVO.getCompanyId());
        if(commentVO.getProductId() != null)
            mav.addObject("productId", commentVO.getProductId());
            
        return mav;
    }

}
