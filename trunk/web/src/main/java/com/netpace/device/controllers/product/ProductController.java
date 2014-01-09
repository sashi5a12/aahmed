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

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.exceptions.VapGenericException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.services.ProductService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.ProductSubmissionType;
import com.netpace.device.validation.product.ProductValidation;
import com.netpace.device.vo.CompanyListVO;
import com.netpace.device.vo.GenericListForm;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.product.ProductListVO;
import com.netpace.device.vo.product.ProductInfoVO;

@Controller
@RequestMapping("/secure/product")
public class ProductController {

    private static final Log log = LogFactory.getLog(ProductController.class);

    private static final String NEW_PRODUCT="NEW_PRODUCT";
    private static final String UPDATE_PRODUCT="UPDATE_PRODUCT";

    
    @Autowired
	private ApplicationPropertiesService applicationPropertiesService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private ProductValidation productValidation;
    
    @RequestMapping(value = "/new.do", method = RequestMethod.GET)
    public ModelAndView newProduct(HttpServletRequest request) {
    	log.debug("ProductController.newProduct (Start New Product): Start -------------------");
    	VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
    	
        ModelAndView mv = new ModelAndView("/secure/product/addEdit.jsp");
        ProductInfoVO vo=new ProductInfoVO();
        vo.setPageType(NEW_PRODUCT);
        vo.setProductStatus(String.valueOf(ProductStatus.Saved));
        vo.setCountry("United States of America");
        
        vo.setName(loggedInUser.getFullName());
        vo.setEmailAddress(loggedInUser.getEmailAddress());
        vo.setPhone(loggedInUser.getPhoneNumber());
        vo.setMobile(loggedInUser.getMobilePhoneNumber());
        vo.setStreetAddress(loggedInUser.getAddress());
        vo.setCity(loggedInUser.getCity());
        vo.setPostalCode(loggedInUser.getZip());
        vo.setState(loggedInUser.getState());
        vo.setCountry(loggedInUser.getCountry());
        
        mv.addObject("productVO", vo);
        vo.setCompanyName(companyService.getCompanyById(loggedInUser.getCompanyId()).getName());
        log.debug("Displaying create screen to user.");
        log.debug("ProductController.newProduct (Start New Product): End -------------------");
        return mv;
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, @ModelAttribute (value="productVO") ProductInfoVO productVO, BindingResult result){
    	log.debug("ProductController.save (save Product): Start -------------------");
    	ModelAndView mv = new ModelAndView("/secure/product/addEdit.jsp");
		VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
    	
    	log.debug("USER INPUTED FORM VALUES: "+productVO);
    	
    	log.debug("Going to valid form values.");
    	productValidation.validateForm(productVO, result);
    	if (!result.hasErrors()){
    		log.debug("No validation error found.");
    		
    		try {
				productService.createProduct(productVO, loggedInUser);
				if (VAPConstants.BTN_SUBMIT.equals(productVO.getBtnType())){
					mv.setViewName("redirect:/secure/product/list.do?SUCCESS_MESSAGE=msg.product.submit");
				}
				else{ 
					mv.addObject("SUCCESS_MESSAGE","msg.product.new");
					mv.setViewName("redirect:/secure/product/edit.do?productId="+productVO.getId());
				}
				log.debug("Product record created succesfully. Redirecting on redirect:/secure/product/edit.do?productId="+productVO.getId());
			} catch (VapGenericException e) {
				result.reject(e.getMessage(), "Duplicate Product Name");
			}
    	}
    	else {
    		log.debug("Form has errors = "+result);
    	}
    	log.debug("ProductController.save (save Product): End -------------------");
    	return mv;
    }
    
    @RequestMapping(value = "/edit.do")
    public ModelAndView edit(HttpServletRequest request, @RequestParam(value = "productId") Integer productId) {
    	log.debug("ProductController.edit (edit Product): Start -------------------");
        ModelAndView mv = new ModelAndView("/secure/product/addEdit.jsp");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        log.debug("Get data for productId: "+productId);
        try {
			ProductInfoVO vo=productService.getProductDetialById(productId, loggedInUser);
			
			mv.addObject("productVO", vo);
			
			if(ProductSubmissionType.Product.getLabel().equals(vo.getProductType())){
				if (loggedInUser.isPartner() && (ProductStatus.Saved.name().equals(vo.getProductStatus()) || ProductStatus.RFI.name().equals(vo.getProductStatus()))){
					
				}
				else if (VAPUtils.canVerizonUserEditProduct(loggedInUser) && !ProductStatus.Saved.name().equals(vo.getProductStatus())){
					
				}
				else {
					mv.setViewName("/secure/product/view.jsp");
					return mv;
					
				}
				/*if(!ProductStatus.Saved.name().equals(vo.getProductStatus()) && !ProductStatus.RFI.name().equals(vo.getProductStatus())){
					
					//After submission partner cannot edit product.
					//After submission super.admin, verizon.admin, device.marketing can edit product.
					if(loggedInUser.isPartner() || VAPUtils.canVerizonUserEditProduct(loggedInUser) == false){
						
						mv.setViewName("redirect:/secure/product/view.do?productId="+vo.getId());
						return mv;
					}
				}*/
			}
			vo.setPageType(UPDATE_PRODUCT);
			
			if(StringUtils.isNotEmpty(request.getParameter("SUCCESS_MESSAGE"))){
				mv.addObject("SUCCESS_MESSAGE",request.getParameter("SUCCESS_MESSAGE"));
			}
			
			log.debug("Record found for productId: "+productId);
		} catch (RecordNotFoundException e) {
			//result.reject(e.getMessage(), "Record Not Found.");
			log.debug("No record found for productId: "+productId +"OR this product is not registered with this company "+loggedInUser.getCompanyId());
			mv.setViewName("redirect:/secure/product/list.do?RECORD_NOT_FOUND="+e.getMessage());
		}
        log.debug("ProductController.edit (edit Product): end -------------------");
        return mv;
    }
    
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest request, @ModelAttribute (value="productVO") ProductInfoVO productVO, BindingResult result){
    	log.debug("ProductController.update (update Product): Start -------------------");
    	ModelAndView mv = new ModelAndView("/secure/product/addEdit.jsp");
    	VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
    	
    	log.debug("USER INPUTED FORM VALUES: "+productVO);
    	
    	log.debug("Going to valid user Input");
    	if(!ProductStatus.Saved.name().equals(productVO.getProductStatus())){
    		productVO.setValidRequiredFields(true);
    	}
    	
    	productValidation.validateForm(productVO, result);
    	if (!result.hasErrors()){
    		log.debug("No validation error found.");
    		try {
	    		productService.updateProduct(productVO, loggedInUser);
	    		if (VAPConstants.BTN_SUBMIT.equals(productVO.getBtnType())){
					mv.setViewName("redirect:/secure/product/list.do?SUCCESS_MESSAGE=msg.product.submit");
	    		}
	    		else if(productVO.getDbProductType().equals(ProductSubmissionType.Concept.getLabel()) 
	    				&& productVO.getProductType().equals(ProductSubmissionType.Product.getLabel())
	    				&& productVO.getProductStatus().equals(ProductStatus.Submitted.name())){
	    			mv.setViewName("redirect:/secure/product/list.do?SUCCESS_MESSAGE=msg.productType.changed");
	    		}
	    		else {
	    			mv.addObject("SUCCESS_MESSAGE","msg.product.update");
	    			mv.setViewName("redirect:/secure/product/edit.do?productId="+productVO.getId());
	    		}
	    		log.debug("Product record updated succesfully. Redirecting on redirect:/secure/product/edit.do?productId="+productVO.getId());
			} catch (VapGenericException e) {
				result.reject(e.getMessage(), "Duplicate Product Name");
			}
    	}
    	else {
    		log.debug("Form has errors = "+result);
    	}
    	log.debug("ProductController.update (update Product): End -------------------");
    	return mv;
    }
    
    @RequestMapping(value = "/list.do")
    public ModelAndView list(HttpServletRequest request, @ModelAttribute(value = "listForm") GenericListForm<ProductListVO> listForm, BindingResult result) {
        log.debug("ProductController.list (list): Start -------------------");
        
        ModelAndView mv = new ModelAndView("/secure/product/list.jsp");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        
        Boolean displayCompanyColumn = loggedInUser.isVerizonUser();
        
        PageModel<ProductListVO> pageModel;
        
        if(StringUtils.isNotEmpty(request.getParameter("RECORD_NOT_FOUND"))){
        	result.reject(request.getParameter("RECORD_NOT_FOUND"), "Record Not Found.");
        }
        else if(StringUtils.isNotEmpty(request.getParameter("SUCCESS_MESSAGE"))){
			mv.addObject("SUCCESS_MESSAGE",request.getParameter("SUCCESS_MESSAGE"));
		}
        
        pageModel = listForm.getPageModel();
        pageModel.setPageSize(applicationPropertiesService.defaultPageSize());

        log.debug("Getting product list from database......");
        
        productService.getProductsForList(pageModel, loggedInUser);

        mv.addObject("listForm", listForm);
        
        mv.addObject("categories", applicationPropertiesService.geValuesAsCommaSeparateString(ApplicationPropertyType.PRODUCT_CATEGORY));
        
        mv.addObject("displayCompanyColumn", displayCompanyColumn);
        
        log.debug("ProductController.list (list): End -------------------");
        
        return mv;
    }
    
    @ModelAttribute("populatedFormElements")
    public Map<String, Map<String, String>> populateFormListElements(HttpServletRequest request){
    	Map<String, Map<String, String>> map=new HashMap<String, Map<String, String>>();
    	if(request.getRequestURI().indexOf("new.do") !=-1 
    			|| request.getRequestURI().indexOf("edit.do") !=-1 
    			|| request.getRequestURI().indexOf("view.do") !=-1
    			|| request.getRequestURI().indexOf("update.do") !=-1
    			|| request.getRequestURI().indexOf("create.do") !=-1){
	    	Map<String, String> productList=new HashMap<String, String>();
	    	productList.put(ProductSubmissionType.Product.getLabel(), ProductSubmissionType.Product.getLabel());
	    	productList.put(ProductSubmissionType.Concept.getLabel(), ProductSubmissionType.Concept.getLabel());
	    	
	    	SortedMap<String, String> categoryList=applicationPropertiesService.propertiesByType(ApplicationPropertyType.PRODUCT_CATEGORY);
	    	
	    	SortedMap<String, String> countryList=applicationPropertiesService.propertiesByType(ApplicationPropertyType.COUNTRIES);
	    	
	    	SortedMap<String, String> supportedDevicesList=applicationPropertiesService.propertiesByType(ApplicationPropertyType.PRODUCT_SUPPORT_DEVICE);
	    	
	    	map.put("productList", productList);
	    	map.put("categoryList", categoryList);
	    	map.put("countryList", countryList);
	    	map.put("supportedDevicesList", supportedDevicesList);
    	}
    	return map;
    }
    
    @RequestMapping(value = "/view.do", method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest request, @RequestParam(value = "productId") Integer productId) {
    	log.debug("ProductController.view (view Product): Start -------------------");
        ModelAndView mv = new ModelAndView("/secure/product/view.jsp");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        log.debug("Get data for productId: "+productId);
        try {
			ProductInfoVO vo=productService.getProductDetialById(productId, loggedInUser);
			
			mv.addObject("productVO", vo);
			
			log.debug("Record found for productId: "+productId);
		} catch (RecordNotFoundException e) {
			log.debug("No record found for productId: "+productId +"OR this product is not registered with this company "+loggedInUser.getCompanyId());
			mv.setViewName("redirect:/secure/product/list.do?RECORD_NOT_FOUND="+e.getMessage());
		}
        log.debug("ProductController.view (view Product): end -------------------");
        return mv;
    }

    @RequestMapping(value = "/delete.do")
    public ModelAndView delete(@RequestParam(value = "productId") Integer productId) {
        ModelAndView mv = new ModelAndView("redirect:/secure/product/list.do?SUCCESS_MESSAGE=msg.product.delete");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        productService.inactiveProduct(productId, loggedInUser);
        return mv;
    }
   
}
