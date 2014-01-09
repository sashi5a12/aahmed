package com.netpace.device.controllers.product;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.exceptions.VapGenericException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.ProductService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.ProductSubmissionType;
import com.netpace.device.validation.product.ProcessingInfoValidation;
import com.netpace.device.validation.product.ProductValidation;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.product.ProcessingInfoVO;
import com.netpace.device.vo.product.ProductInfoVO;

@Controller
@RequestMapping("/secure/product")
public class ProductProcessingInfoController {
	private static final Log log = LogFactory.getLog(ProductProcessingInfoController.class);

    @Autowired
	private ApplicationPropertiesService applicationPropertiesService;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private ProcessingInfoValidation processingInfoValidation;
    
	public ApplicationPropertiesService getApplicationPropertiesService() {
		return applicationPropertiesService;
	}

	public void setApplicationPropertiesService(
			ApplicationPropertiesService applicationPropertiesService) {
		this.applicationPropertiesService = applicationPropertiesService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

    public ProcessingInfoValidation getProcessingInfoValidation() {
		return processingInfoValidation;
	}

	public void setProcessingInfoValidation(
			ProcessingInfoValidation processingInfoValidation) {
		this.processingInfoValidation = processingInfoValidation;
	}

	@RequestMapping(value = "/processInfoEdit.do", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request, @RequestParam(value = "productId") Integer productId) {
    	log.debug("ProductProcessingInfoController.edit (edit processingInfo): Start -------------------");
        ModelAndView mv = new ModelAndView("/secure/product/processingInfo.jsp");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        log.debug("Get processInfo data for productId: "+productId);
        try {
			ProcessingInfoVO vo=new ProcessingInfoVO();
			productService.getProcessingInfoForEdit(vo, productId, loggedInUser);
			if(StringUtils.isEmpty(vo.getRingAssociation())){
				vo.setRingAssociation("Ring 3");
			}
			if(StringUtils.isNotEmpty(vo.getAgreementAcceptTitle())){
				vo.setAggreeChkbox("Yes");
			}
			
			if(StringUtils.isNotEmpty(request.getParameter("SUCCESS_MESSAGE"))){
				mv.addObject("SUCCESS_MESSAGE","msg.product.update");
			}
			mv.addObject("processingInfoVO", vo);
			log.debug("Processing Info form object : "+vo);
			log.debug("Record found for processInfo: "+productId);
		} catch (RecordNotFoundException e) {
			log.debug("No record found for processingInfo with productId: "+productId +"OR this product is not registered with this company "+loggedInUser.getCompanyId());
			mv.setViewName("redirect:/secure/product/list.do?RECORD_NOT_FOUND="+e.getMessage());
		}
        log.debug("ProductProcessingInfoController.edit (edit processingInfo): end -------------------");
        return mv;
    }
    
    @RequestMapping(value = "/processInfoProcess.do", method = RequestMethod.POST)
    public ModelAndView process(HttpServletRequest request, @ModelAttribute (value="processingInfoVO") ProcessingInfoVO form, BindingResult result){
    	log.debug("ProductProcessingInfoController.process (update processing info): Start -------------------");
    	ModelAndView mv = new ModelAndView("/secure/product/processingInfo.jsp");
    	VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
    	
    	log.debug("USER INPUTED FORM VALUES: "+form);
    	
    	log.debug("Going to valid user Input");
    	
    	
    	processingInfoValidation.validateForm(form, result);
    	if (!result.hasErrors()){
    		log.debug("No validation error found.");
    		String emailText="";
    		if (StringUtils.isNotEmpty(form.getEmailText()) && !VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT.equals(form.getEmailText())){
    			emailText=form.getEmailText();
    		}
    		
    		productService.saveProcessingInfo(form, loggedInUser);
    		log.debug(form.getBtnType() + " pressed for section "+form.getSectionName());
    		log.debug("Email Text is:"+emailText);
    		mv.addObject("SUCCESS_MESSAGE","msg.product.update");
    		log.debug("Processing Info updated succesfully. Redirecting on redirect:/secure/product/processInfoEdit.do?productId="+form.getId());
    		mv.setViewName("redirect:/secure/product/processInfoEdit.do?productId="+form.getId());
    	}
    	else {
    		log.debug("Form has errors = "+result);
//    		productService.setWorkItems(form, loggedInUser);
    		productService.processingInfoPrepopulatedData(form, form.getId(), loggedInUser);
    		mv.addObject("processingInfoVO", form);
    	}
    	log.debug("ProductProcessingInfoController.process (update processing info): End -------------------");
    	return mv;
    }    
    @ModelAttribute("populatedFormElements")
    public Map<String, Map<String, String>> populateFormListElements(){
    	Map<String, Map<String, String>> map=new HashMap<String, Map<String, String>>();
    	
    	SortedMap<String, String> ringsList=applicationPropertiesService.getApplicationPropertiesMap(ApplicationPropertyType.RING_ASSOCIATION);
    	
    	map.put("ringsList", ringsList);
    	
    	return map;
    }

}
