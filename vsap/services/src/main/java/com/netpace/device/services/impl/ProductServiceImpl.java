package com.netpace.device.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.annotation.utils.ETDConverter;
import com.netpace.device.dao.CompanyDao;
import com.netpace.device.dao.ContactDao;
import com.netpace.device.dao.MediaDao;
import com.netpace.device.dao.ProductDao;
import com.netpace.device.dao.WorkitemDao;
import com.netpace.device.entities.Company;
import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapContact;
import com.netpace.device.entities.VapMedia;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.VapProductAttachment;
import com.netpace.device.entities.VapProductProcessInfo;
import com.netpace.device.entities.VapProductSupportedDevice;
import com.netpace.device.entities.Workitem;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.EventType;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.exceptions.VapGenericException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.ProductService;
import com.netpace.device.utils.DateUtils;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.ContactType;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.ProductSubmissionType;
import com.netpace.device.utils.enums.WorkflowType;
import com.netpace.device.utils.enums.WorkitemStatus;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import com.netpace.device.vo.product.AttachmentFile;
import com.netpace.device.vo.product.FileUploadVO;
import com.netpace.device.vo.product.ProcessingInfoVO;
import com.netpace.device.vo.product.ProductListVO;
import com.netpace.device.vo.product.ProductInfoVO;
import com.netpace.notification.services.EventService;



@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

    private final static Log log = LogFactory.getLog(ProductServiceImpl.class);
   
    @Autowired
    private ProductDao productDao;

    @Autowired
    private CompanyDao companyDao ;
    
    @Autowired
    private ContactDao contactDao;
    
    @Autowired
    private MediaDao mediaDao;
    
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    
    @Autowired
    private ApprovalService approvalService;
    
    @Autowired
    private WorkitemDao workitemDao;
    
    @Autowired
    private EventService eventService;
    
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProductListVO> getProductsForList(PageModel<ProductListVO> pageModel, VAPUserDetails loggedInUser) {

		Boolean isVerizonUser = loggedInUser.isVerizonUser();
		
		if (StringUtils.isEmpty(pageModel.getSortBy())) {
			if (isVerizonUser){
				pageModel.setSortBy("vp.submittedDate");
				pageModel.setAscending(false);				
			}
			else {
				pageModel.setSortBy("vp.productName");
				pageModel.setAscending(true);
			}
		}
		if ("Search Term".equals(pageModel.getSearchValue())) {
			pageModel.setSearchValue("");
		}

		log.debug("ProductServiceImp.getProductsForList Start ---------------");
		log.debug("sortBy = " + pageModel.getSortBy());
		log.debug("sortOrder = " + pageModel.isAscending());
		log.debug("searchBy = " + pageModel.getSearchBy());
		log.debug("searchValue = " + pageModel.getSearchValue());
		log.debug("pageSize = " + pageModel.getPageSize());
		log.debug("page = " + pageModel.getPage());
		log.debug("filters = " + pageModel.getFilters());
		log.debug("companyId = " + loggedInUser.getCompanyId());

		List<ProductListVO> products = new ArrayList<ProductListVO>();

		log.debug("isVerizonUser: "+isVerizonUser);
		
		Long count = productDao.getProductsCountForList(pageModel, loggedInUser.getCompanyId(), isVerizonUser);

		List<Object[]> partnersObj = productDao.getProductsForList(pageModel, loggedInUser.getCompanyId(), isVerizonUser);
		for (Object[] obj : partnersObj) {
			ProductListVO vo = new ProductListVO();
			vo.setProductId((Integer) obj[0]);
			vo.setProductName((String) obj[1]);
			
			if(ProductSubmissionType.Product.getLabel().equals((String) obj[2])){
				vo.setProductType("Product");
			}
			else {
				vo.setProductType("Concept");				
			}
			
			vo.setProductCategory((String) obj[3]);	
			vo.setSubmittedDate((Timestamp) obj[4]);
			vo.setStatus((String)obj[5]);
			vo.setCompanyName((String)obj[6]);			
			vo.setWorkFlowSteps((String)obj[7]);
			
			if(StringUtils.isEmpty(vo.getWorkFlowSteps()) && ProductStatus.Approved.name().equals(vo.getStatus())){
				vo.setWorkFlowSteps(vo.getStatus());
			}
			
			if(loggedInUser.isPartner()){
				if(ProductStatus.Saved.name().equals(vo.getStatus())){
					vo.setAllowDelete(new Boolean (true));
					vo.setAllowEdit(new Boolean (true));
				}
				else if(ProductStatus.Submitted.name().equals(vo.getStatus()) && "Concept".equals(vo.getProductType())){
					if ("Concept Approved".equals(vo.getWorkFlowSteps())){
						vo.setAllowEdit(new Boolean (true));
					}
				}
				else if(ProductStatus.RFI.name().equals(vo.getStatus())){
					vo.setAllowEdit(new Boolean (true));
				}
			}
			else {
				if (VAPUtils.canVerizonUserDeleteProduct(loggedInUser)){
					vo.setAllowDelete(new Boolean (true));
				}
				if (VAPUtils.canVerizonUserEditProduct(loggedInUser)){				
					vo.setAllowEdit(new Boolean (true));
				}
			}
			products.add(vo);
		}

		log.debug("-------------Start Search Result------------------------");
		log.debug("list = " + products);
		log.debug("count = " + count);
		log.debug("-------------End Search Result------------------------");

		pageModel.setTotalRecords(count.intValue());
		pageModel.setRecords(products);

		log.debug("ProductServiceImp.getProductsForList End ---------------");
		
		return products;
	}
	
	@Override 
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void createProduct(ProductInfoVO vo, VAPUserDetails loggedInUser) {
		log.debug("ProductServiceImp.createProduct (create Product): Start -------------------");
		VapProduct product = new VapProduct();
		VapContact contact = new VapContact();
		VapProductProcessInfo processInfo=new VapProductProcessInfo();
		
		log.debug("Product Form Data: "+vo);
		log.debug("username: "+loggedInUser.getUsername());
		
		if(VAPConstants.BTN_SAVE.equals(vo.getBtnType())){
			product.setStatus(String.valueOf(ProductStatus.Saved));
		}
		else if(VAPConstants.BTN_SUBMIT.equals(vo.getBtnType())){
			product.setStatus(String.valueOf(ProductStatus.Submitted));
			product.setSubmittedDate(DateUtils.currentTimeStamp());
			product.setSubmittedBy(loggedInUser.getUsername());
			
		}
		
		Company company= companyDao.getReference(loggedInUser.getCompanyId());
		product.setCompany(company);
		product.copyFromVo(vo);		
		product.populatedAuditFields(loggedInUser.getUsername());
		
		contact.setContactType(ContactType.ProductContact.name());
		contact.copyFromProductVo(vo);
		contact.populatedAuditFields(loggedInUser.getUsername());		
		
		product.setContact(contact);
		
		product.setProcessingInfo(processInfo);
		
		if(vo.getSupportedDevices() !=null && vo.getSupportedDevices().length>0){
			for (String sd: vo.getSupportedDevices()){
				product.addSupportedDevice(new VapProductSupportedDevice(sd));
			}
		}
		
		try {
			productDao.add(product);
		} catch (JpaSystemException e) {
			log.error(e, e);
			if(e.getMessage().indexOf("ConstraintViolationException") != -1 ){
				throw new VapGenericException("error.duplicate.product");
			}
			else{
				throw new VapGenericException("error.generic.product");
			}
		}
		log.debug("Data saved succesfully in database for new product");
		
		if(ProductStatus.Saved.name().equals(vo.getProductStatus()) && VAPConstants.BTN_SUBMIT.equals(vo.getBtnType())){
			log.debug("Going to initiate product/workflow: ");
			approvalService.startApprovalProcess(WorkflowType.ProductWorkflow,  product.getProductId(),  loggedInUser);
		}
		
		vo.setId(product.getProductId());
		vo.setProductStatus(product.getStatus());
		
		log.debug("Going to save attachments");
		if (vo.getOut_front_view() != null && vo.getOut_front_view().getMediaId() !=null ){
			saveAttachment(vo.getId(), vo.getOut_front_view().getMediaId(), VAPConstants.PRODUCT_OUT_FRONT_VIEW, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_OUT_FRONT_VIEW);
		}
		if (vo.getOut_angeled_view() != null && vo.getOut_angeled_view().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getOut_angeled_view().getMediaId(), VAPConstants.PRODUCT_OUT_ANGELED_VIEW, new Integer(1),loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_OUT_ANGELED_VIEW);
		}
		if (vo.getOut_another_object() != null && vo.getOut_another_object().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getOut_another_object().getMediaId(), VAPConstants.PRODUCT_OUT_ANOTHER_OBJECT, new Integer(1),loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_OUT_ANOTHER_OBJECT);
		}
		if (vo.getOut_other_view1() != null && vo.getOut_other_view1().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getOut_other_view1().getMediaId(), VAPConstants.PRODUCT_OUT_OTHER_VIEW1, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_OUT_OTHER_VIEW1);
		}
		if (vo.getOut_other_view2() != null && vo.getOut_other_view2().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getOut_other_view2().getMediaId(), VAPConstants.PRODUCT_OUT_OTHER_VIEW2, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_OUT_OTHER_VIEW2);
		}
		if (vo.getOut_other_view3() != null && vo.getOut_other_view3().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getOut_other_view3().getMediaId(), VAPConstants.PRODUCT_OUT_OTHER_VIEW3, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_OUT_OTHER_VIEW3);
		}
		if (vo.getOut_other_view4() != null && vo.getOut_other_view4().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getOut_other_view4().getMediaId(), VAPConstants.PRODUCT_OUT_OTHER_VIEW4, new Integer(1),loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_OUT_OTHER_VIEW4);
		}
		if (vo.getIn_front_view() != null && vo.getIn_front_view().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getIn_front_view().getMediaId(), VAPConstants.PRODUCT_IN_FRONT_VIEW, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_IN_FRONT_VIEW);
		} 
		if (vo.getIn_angeled_view() != null && vo.getIn_angeled_view().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getIn_angeled_view().getMediaId(), VAPConstants.PRODUCT_IN_ANGELED_VIEW, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_IN_ANGELED_VIEW);
		} 
		if (vo.getIn_another_object() != null && vo.getIn_another_object().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getIn_another_object().getMediaId(), VAPConstants.PRODUCT_IN_ANOTHER_OBJECT, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_IN_ANOTHER_OBJECT);
		} 
		if (vo.getScreen_shot1() != null && vo.getScreen_shot1().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getScreen_shot1().getMediaId(), VAPConstants.PRODUCT_SCREEN_SHOT1, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_SCREEN_SHOT1);
		} 
		if (vo.getScreen_shot2() != null && vo.getScreen_shot2().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getScreen_shot2().getMediaId(), VAPConstants.PRODUCT_SCREEN_SHOT2, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_SCREEN_SHOT2);
		} 
		if (vo.getScreen_shot3() != null && vo.getScreen_shot3().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getScreen_shot3().getMediaId(), VAPConstants.PRODUCT_SCREEN_SHOT3, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_SCREEN_SHOT3);
		} 
		if (vo.getLifestyle_image1() != null && vo.getLifestyle_image1().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getLifestyle_image1().getMediaId(), VAPConstants.PRODUCT_LIFESTYLE_IMAGE1, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_LIFESTYLE_IMAGE1);
		} 
		if (vo.getLifestyle_image2() != null && vo.getLifestyle_image2().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getLifestyle_image2().getMediaId(), VAPConstants.PRODUCT_LIFESTYLE_IMAGE2, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_LIFESTYLE_IMAGE2);
		} 
		if (vo.getLifestyle_image3() != null && vo.getLifestyle_image3().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getLifestyle_image3().getMediaId(), VAPConstants.PRODUCT_LIFESTYLE_IMAGE3, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_LIFESTYLE_IMAGE3);
		} 
		
		if (vo.getPhone_splash_screen() != null && vo.getPhone_splash_screen().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getPhone_splash_screen().getMediaId(), VAPConstants.PRODUCT_PHONE_SPLASH_SCREEN, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_PHONE_SPLASH_SCREEN);
		} 
		if (vo.getTablet_splash_screen() != null && vo.getTablet_splash_screen().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getTablet_splash_screen().getMediaId(), VAPConstants.PRODUCT_TABLET_SPLASH_SCREEN, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_TABLET_SPLASH_SCREEN);
		} 
		if (vo.getApplication_icon() != null && vo.getApplication_icon().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getApplication_icon().getMediaId(), VAPConstants.PRODUCT_APPLICATION, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_APPLICATION);
		} 
		if (vo.getPhone_in_app_screen() != null && vo.getPhone_in_app_screen().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getPhone_in_app_screen().getMediaId(), VAPConstants.PRODUCT_PHONE_IN_APP_SCREEN, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_PHONE_IN_APP_SCREEN);
		} 
		if (vo.getTablet_in_app_screen() != null && vo.getTablet_in_app_screen().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getTablet_in_app_screen().getMediaId(), VAPConstants.PRODUCT_TABLET_IN_APP_SCREEN, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_TABLET_IN_APP_SCREEN);
		} 

		if (vo.getProduct_video() != null && vo.getProduct_video().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getProduct_video().getMediaId(), VAPConstants.PRODUCT_PRODUCT_VIDEO, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_PRODUCT_VIDEO);
		} 
		if (vo.getPhone_app_video() != null && vo.getPhone_app_video().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getPhone_app_video().getMediaId(), VAPConstants.PRODUCT_PHONE_APP_VIDEO, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_PHONE_APP_VIDEO);
		}
		if (vo.getTablet_app_video() != null && vo.getTablet_app_video().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getTablet_app_video().getMediaId(), VAPConstants.PRODUCT_TABLET_APP_VIDEO, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_TABLET_APP_VIDEO);
		} 
		if (vo.getProduct_copy_doc() != null && vo.getProduct_copy_doc().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getProduct_copy_doc().getMediaId(), VAPConstants.PRODUCT_PRODUCT_COPY_DOC, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_PRODUCT_COPY_DOC);
		}
		if (vo.getApp_copy_doc() != null && vo.getApp_copy_doc().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getApp_copy_doc().getMediaId(), VAPConstants.PRODUCT_APP_COPY_DOC, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_APP_COPY_DOC);
		}
		if (vo.getLaunch_presentation_video() != null && vo.getLaunch_presentation_video().getMediaId() != null){
			saveAttachment(vo.getId(), vo.getLaunch_presentation_video().getMediaId(), VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO, new Integer(1), loggedInUser);
			log.debug("Attachment with product save successfully: "+ VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO);
		}
		log.debug("ProductServiceImp.createProduct (create Product): End -------------------");
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	private void saveAttachment(Integer productId, Integer mediaId, String fileType, Integer tab, VAPUserDetails loggedInUser) {
		log.debug("ProductServiceImp.saveAttachment (Save Attachment): Start -------------------");
		log.debug("ProductId: "+productId);
		log.debug("mediaId: "+mediaId);
		log.debug("fileType: "+fileType);
		log.debug("username: "+loggedInUser.getUsername());
		
		VapProduct product=productDao.getReference(productId);
		VapMedia media=mediaDao.getReference(mediaId);
		
		VapProductAttachment attachment=new VapProductAttachment();
		attachment.setFileFieldName(fileType);
		attachment.setTab(tab);
		attachment.populatedAuditFields(loggedInUser.getUsername());
		attachment.setProduct(product);
		attachment.setMedia(media);
		
		productDao.saveAttachment(attachment);
		log.debug("Attachment saved succesfully in database");
		log.debug("ProductServiceImp.saveAttachment (Save Attachment): End -------------------");
	}

	/*This method is for edit screen and is fully loaded object. Donn't use it for if you need minimal product information.*/
	@Override
	public ProductInfoVO getProductDetialById(Integer productId, VAPUserDetails loggedInUser){
		log.debug("ProductServiceImp.getProduct (Get Product): Start -------------------");
		ProductInfoVO vo=new ProductInfoVO();
		log.debug("productId: "+productId);
		log.debug("companyId: "+loggedInUser.getCompanyId());
		Boolean isVerizonUser = loggedInUser.isVerizonUser();
		/*if(VAPConstants.PRODUCT_PAGE_EDIT.equals(pageType) && isVerizonUser){			
			isVerizonUser = VAPUtils.canVerizonUserEditProduct(loggedInUser);
		}
		else if(VAPConstants.PRODUCT_PAGE_VIEW.equals(pageType) && isVerizonUser){
			isVerizonUser = VAPUtils.canVerizonUserViewProduct(loggedInUser);
		}*/
		try {
			log.debug("isVerizonUser: "+isVerizonUser);
			
			VapProduct product=productDao.getProductForEdit(productId, loggedInUser.getCompanyId(), isVerizonUser);
			log.debug("Product returned from database: "+product);
			if (product!=null && product.getProductId()!=null){
				product.copyProductInfoInVo(vo);
				product.getContact().copyInProductVo(vo);
				product.copyPlatformSupportedDeviceInfoInVo(vo);

				vo.setOut_front_view(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_OUT_FRONT_VIEW));
				vo.setOut_angeled_view(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_OUT_ANGELED_VIEW));
				vo.setOut_another_object(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_OUT_ANOTHER_OBJECT));
				vo.setOut_other_view1(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_OUT_OTHER_VIEW1));
				vo.setOut_other_view2(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_OUT_OTHER_VIEW2));
				vo.setOut_other_view3(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_OUT_OTHER_VIEW3));
				vo.setOut_other_view4(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_OUT_OTHER_VIEW4));
				vo.setIn_front_view(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_IN_FRONT_VIEW));
				vo.setIn_angeled_view(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_IN_ANGELED_VIEW));
				vo.setIn_another_object(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_IN_ANOTHER_OBJECT));
				vo.setScreen_shot1(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_SCREEN_SHOT1));
				vo.setScreen_shot2(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_SCREEN_SHOT2));
				vo.setScreen_shot3(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_SCREEN_SHOT3));
				vo.setLifestyle_image1(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_LIFESTYLE_IMAGE1));
				vo.setLifestyle_image2(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_LIFESTYLE_IMAGE2));
				vo.setLifestyle_image3(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_LIFESTYLE_IMAGE3));
				
				vo.setPhone_splash_screen(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_PHONE_SPLASH_SCREEN));
				vo.setTablet_splash_screen(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_TABLET_SPLASH_SCREEN));
				vo.setApplication_icon(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_APPLICATION));
				vo.setPhone_in_app_screen(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_PHONE_IN_APP_SCREEN));
				vo.setTablet_in_app_screen(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_TABLET_IN_APP_SCREEN));
				
				vo.setProduct_video(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_PRODUCT_VIDEO));
				vo.setPhone_app_video(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_PHONE_APP_VIDEO));
				vo.setTablet_app_video(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_TABLET_APP_VIDEO));
				vo.setProduct_copy_doc(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_PRODUCT_COPY_DOC));
				vo.setApp_copy_doc(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_APP_COPY_DOC));
				vo.setLaunch_presentation_video(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO));
				
				vo.setCompanyName(product.getCompany().getName());
				
				if (!ProductStatus.Saved.name().equals(product.getStatus())){
					
					vo.setWorklflowStep(VAPUtils.formatWorkflowSteps(product.getWorkFlowSteps()));
					
					/*
					ProcessingInfoVO processingInfo=new ProcessingInfoVO();
					processingInfo.setId(productId);
					setWorkItems(processingInfo, loggedInUser);
					log.debug("workflowstep: "+processingInfo.getWorklflowStep());
					vo.setWorklflowStep(processingInfo.getWorklflowStep());
					*/
				}
				else if(ProductStatus.Saved.name().equals(vo.getProductStatus())){
					vo.setWorklflowStep(ProductStatus.Saved.name());
				}
			}
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException("record.not.found");
		}
		log.debug("ProductServiceImp.getProduct (Get Product): End -------------------");
		return vo;
	}

	@Override 
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void updateProduct(ProductInfoVO vo, VAPUserDetails loggedInUser) {
		log.debug("ProductServiceImp.updateProduct (update Product): Start -------------------");
		VapProduct product = productDao.get(vo.getId());
		VapContact contact = contactDao.getReference(vo.getContactId());
		log.debug("Product Form Data: "+vo);
		log.debug("username: "+loggedInUser.getUsername());
		
		String dbProductType=product.getProductType();
		
		//Saved -> Submitted.
		if(ProductStatus.Saved.name().equals(product.getStatus()) && VAPConstants.BTN_SUBMIT.equals(vo.getBtnType())){
			product.setStatus(String.valueOf(ProductStatus.Submitted));
			product.setSubmittedDate(DateUtils.currentTimeStamp());
			product.setSubmittedBy(loggedInUser.getUsername());
			
			log.debug("Going to initiate product/workflow: ");
			approvalService.startApprovalProcess(WorkflowType.ProductWorkflow,  product.getProductId(),  loggedInUser);
		}
		
//		Company company= companyDao.getReference(loggedInUser.getCompanyId());
//		product.setCompany(company);
		product.copyFromVo(vo);		
		product.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
		
		contact.setContactType(ContactType.ProductContact.name());
		contact.copyFromProductVo(vo);
		contact.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());		
		
		product.setContact(contact);
		
		
		productDao.deleteSupportedDevices(product.getProductId());
		product.setSupportedDevices(null);
		
		if(vo.getSupportedDevices() !=null && vo.getSupportedDevices().length>0){
			for (String sd: vo.getSupportedDevices()){
				VapProductSupportedDevice v= new VapProductSupportedDevice(sd);
				v.populatedAuditFields(loggedInUser.getUsername());
				v.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
				product.addSupportedDevice(v);
			}
		}
		
		try{
			productDao.update(product);
			
			//if product type was concept and user changed it from concept to product then start the workflow from next step
			if( ProductSubmissionType.Product.getLabel().equals(vo.getProductType()) && 
					ProductSubmissionType.Concept.getLabel().equals(dbProductType) && 
					ProductStatus.Submitted.name().equals(product.getStatus())){
				log.debug("User changed the type of this record from concept to product.");				
				List<Workitem> workItems=workitemDao.getProductWorkitems(vo.getId(), WorkitemStatus.InProgress, null);
				WorkItem workItemVO=new WorkItem();
				for (Workitem workitem : workItems) {
					WorkflowVO workflowVO=new WorkflowVO();
					
			        workItemVO.setTitle(workitem.getTitle());
			        workItemVO.setDecision(workitem.getNextActions());
			        workflowVO.setId(workitem.getWorkflow().getId());
			        
					workItemVO.setWorkflow(workflowVO);
				}
				
				log.debug("Going to join the product workflow. THIS CHANGED FROM PRODUCT TO PRODUCT.");
				approvalService.processWorkitem(workItemVO, loggedInUser);
				log.debug("Concept joined the product workflow successfully. ");
			}
			
			productDao.flush();
		} catch (JpaSystemException e) {
			if(e.getMessage().indexOf("ConstraintViolationException") != -1 ){
				throw new VapGenericException("error.duplicate.product");
			}
			else{
				throw new VapGenericException("error.generic.product");
			}
		}
		
		log.debug("ProductServiceImp.updateProduct (update Product): End -------------------");
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void inactiveProduct(Integer productId, VAPUserDetails loggedInUser){
		log.debug("ProductServiceImp.inactiveProduct start: ----------------");
		log.debug("companyId: "+loggedInUser.getCompanyId());
		log.debug("productId: "+productId);
		
		VapProduct product=productDao.get(productId);
		
//		Boolean isVerizonUser = VAPUtils.canVerizonUserDeleteProduct(loggedInUser);
//		log.debug("isVerizonUser: "+isVerizonUser);
		
		productDao.inactiveProduct(loggedInUser.getCompanyId(), productId, loggedInUser.getUsername());

		if (productId!=null && !ProductStatus.Saved.name().equals(product.getStatus())){
			Map<String, String> params= new HashMap<String, String>();
			
			params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
			params.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, product.getCompany().getName());
			params.put(VAPConstants.PLACEHOLDER_PRODUCT_NAME, product.getProductName());
	        eventService.raiseEvent(EventType.PRODUCT_DELETED.toString(), params, null, null, null, null);
		}
		log.debug("ProductServiceImp.inactiveProduct end: ----------------");
	}
	
	@Override
	public VapProductAttachment getAttachment(Integer productId, Integer mediaId){
		log.debug("ProductServiceImp.getAttachment (Get attachment): Start -------------------");
		VapProductAttachment att=new VapProductAttachment();
		log.debug("productId: "+productId);
		log.debug("mediaId: "+mediaId);
		try {
			if (productId==0){
				VapMedia media=mediaDao.get(mediaId);
				att.setMedia(media);
			}
			else {				
				att=productDao.getAttachment(productId, mediaId);
			}
			log.debug("Attachment returned from database: "+att);
			
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException("record.not.found");
		}
		log.debug("ProductServiceImp.getAttachment (Get attachment): End -------------------");
		return att;
	}

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void saveAttachment(FileUploadVO fileUpload, VapMedia media, VAPUserDetails loggedInUser) {
		log.debug("ProductServiceImp.saveAttachment (Save Attachment): Start -------------------");
		log.debug("ProductId: "+fileUpload.getProductId());
		log.debug("fileType: "+fileUpload.getFileType());
		log.debug("username: "+loggedInUser.getUsername());
		
		mediaDao.add(media);
		
		if(fileUpload.getProductId()>0){
			VapProduct product=productDao.getReference(fileUpload.getProductId());
			VapProductAttachment attachment=new VapProductAttachment();
			attachment.setFileFieldName(fileUpload.getFileType());
			attachment.setTab(fileUpload.getTab());
			attachment.populatedAuditFields(loggedInUser.getUsername());
			attachment.setProduct(product);
			attachment.setMedia(media);
			
			productDao.saveAttachment(attachment);
			
			log.debug("Attachment saved successfully with productId.");
		}
		log.debug("Attachment saved succesfully in database");
		
		try {
			String filePath=applicationPropertiesService.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, VAPConstants.ATTACHMENT_FILE_PATH);
			File file = new File(filePath + media.getMediaId() + "." + VAPUtils.getFileExtension(media.getFileName()));
			log.debug("file: " + file.getAbsolutePath());
			FileUtils.writeByteArrayToFile(file, fileUpload.getData());
			log.debug("File saved successfully on file system .");
		} catch (IOException e) {
			log.debug(e);
			throw new VapGenericException("error.save.attachment");
		}
		log.debug("ProductServiceImp.saveAttachment (Save Attachment): End -------------------");
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void deleteAttachment(AttachmentFile file, VAPUserDetails loggedInUser){
		log.debug("ProductServiceImp.deleteAttachment (delete Attachment): Start -------------------");
		log.debug("companyId: "+file.getCompanyId());
		log.debug("productId: "+file.getProductId());
		log.debug("mediaId: "+file.getMediaId());		
		Boolean isVerizonUser = VAPUtils.canVerizonUserDeleteProductAttachment(loggedInUser);
		try {
			
			log.debug("isVerizonUser: "+isVerizonUser);	
			if (file.getMediaId() != null && file.getProductId().intValue() != 0) {
				log.debug("Going to delete media information form database. when productId found");

				productDao.deleteAttachment(file.getCompanyId(), file.getProductId(), file.getMediaId(), isVerizonUser);
				log.debug("Attachment removed successfully.");
			} 		
			
			log.debug("Attachment deleted succesfully from table vap_product_attachments");
			
			mediaDao.remove(file.getMediaId());
			log.debug("Attachment deleted succesfully from table vap_media");
			
			String filePath=applicationPropertiesService.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, VAPConstants.ATTACHMENT_FILE_PATH);
			File f = new File(filePath + file.getMediaId() + "." + VAPUtils.getFileExtension(file.getFileName()));
			log.debug("Going to delete file from file system");
			f.delete();
			
			log.debug("File from file system deleted succesfully.");

		} catch (JpaObjectRetrievalFailureException e) {
			throw new RecordNotFoundException("record.not.found");
		}
		
		log.debug("ProductServiceImp.deleteAttachment (delete Attachment): End -------------------");
	}
	
	@Override
	public void getProcessingInfoForEdit(ProcessingInfoVO vo, Integer productId, VAPUserDetails loggedInUser){
		log.debug("ProductServiceImp.getProcessingInfoForEdit (Get ProcessingInfo): Start -------------------");
		
		log.debug("productId: "+productId);
		log.debug("companyId: "+loggedInUser.getCompanyId());
		Boolean isVerizonUser = loggedInUser.isVerizonUser();
		try {
			log.debug("isVerizonUser: "+isVerizonUser);
			
			VapProduct product=productDao.getProcessingInfoForEdit(productId, loggedInUser.getCompanyId(), isVerizonUser);
			log.debug("Product returned from database: "+product);
			if (product!=null && product.getProductId()!=null && product.getProcessingInfo()!=null){
				
				product.getProcessingInfo().copyProcessingInfoInVo(vo);
				
				vo.setTesting_spreadsheet(product.copyAttachmentInfoInVo(VAPConstants.TESTING_SPREADSHEET));
				vo.setProduct_label(product.copyAttachmentInfoInVo(VAPConstants.PRODUCT_LABEL));
				vo.setSustainability_disclosure(product.copyAttachmentInfoInVo(VAPConstants.SUSTAINABILITY_DISCLOSURE));
				vo.setPackaging_label(product.copyAttachmentInfoInVo(VAPConstants.PACKAGING_LABEL));
				vo.setPdf_sample_product(product.copyAttachmentInfoInVo(VAPConstants.PDF_SAMPLE_PRODUCT));
				
				vo.setCompanyName(product.getCompany().getName());
				vo.setProductStatus(product.getStatus());
				vo.setProductType(product.getProductType());
				vo.setId(product.getProductId());
				vo.setCompanyId(product.getCompany().getId());
				vo.setWorklflowStep(VAPUtils.formatWorkflowSteps(product.getWorkFlowSteps()));				
				
				processingInfoPrepopulatedData(vo, productId, loggedInUser);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException("record.not.found");
		}
		log.debug("ProductServiceImp.getProcessingInfoForEdit (Get Product): End -------------------");
	}

	@Override
	public void processingInfoPrepopulatedData(ProcessingInfoVO vo, Integer productId, VAPUserDetails loggedInUser) {
		setWorkItems(vo, loggedInUser);
		vo.setComments(approvalService.getProductComments(productId, loggedInUser));
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void saveProcessingInfo(ProcessingInfoVO vo, VAPUserDetails loggedInUser) {
		log.debug("ProductServiceImp.saveProcessingInfo (Save ProcessingInfo): Start -------------------");
		VapProductProcessInfo entity=productDao.getProcessingInfo(vo.getProcessId());
		if(StringUtils.isNotBlank(entity.getRingAssociation())){
			entity.populatedAuditFields(loggedInUser.getUsername());
		}
		else {
			entity.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
		}
		entity.copyProcessingInfoFromVo(vo);
		
		log.debug("Going to saving data in processing table.");
		productDao.saveProcessingInfo(entity);
		log.debug("Data saved successfully.");
		
		log.debug("Going to process workitem.");
		approvalService.processWorkitem(vo.getWorkItem(), loggedInUser);
		log.debug("workitem processed successfully.");
		
		/*if(VAPConstants.BTN_APPROVE.equals(vo.getBtnType()) 
				&& VAPConstants.PRODUCT_PROCESS_SECTION_DM.equals(vo.getSectionName())
				&& ProductSubmissionType.Concept.name().equals(vo.getProductType())){
			productDao.updateProductStatus(vo.getId(), ProductStatus.Approved);
		}*/
		
		
		log.debug("ProductServiceImp.saveProcessingInfo (End ProcessingInfo): End -------------------");
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void saveComments(Integer productId, VapComment comment) {
		productDao.saveComments(productId, comment);
	}

	@Override
	public void setWorkItems(ProcessingInfoVO vo, VAPUserDetails loggedInUser){
		String[] roles=loggedInUser.getRolesArray();

//		StringBuilder buff = new StringBuilder();
//		String sep = "";
		log.debug("Going to get workitem for user: "+loggedInUser.getUsername()+" with roles: "+Arrays.asList(roles));
		List<Workitem> workItems=workitemDao.getProductWorkitems(vo.getId(), WorkitemStatus.InProgress, roles);
//		List<Workitem> workItemsForStatus=workitemDao.getProductWorkitems(vo.getId(), WorkitemStatus.InProgress, null);
		log.debug("Workitem got for this user: "+workItems);
		WorkItem workItemVO;
		List<WorkItem> procesInfoWorkItems=new ArrayList<WorkItem>();
		for (Workitem workitem : workItems) {
			workItemVO=ETDConverter.convert(workitem, new WorkItem());
			vo.setWorkflowId(workitem.getWorkflow().getId());
			procesInfoWorkItems.add(workItemVO);
		}
		
		/*
		for (Workitem workitem : workItemsForStatus) {
			workItemVO=ETDConverter.convert(workitem, new WorkItem());
						
			buff.append(sep);
			if (workItemVO.getTitle().indexOf("Denied")>=0){					
				buff.append(workItemVO.getDisplayName() + " - Denied");
			}
			else if (workItemVO.getTitle().indexOf("Approved")>=0){
				buff.append("Approved");
			}
			else {
				buff.append(workItemVO.getDisplayName() + " - In Progress");
			}
		    sep = "<br/>";
		}
		vo.setWorklflowStep(buff.toString());	*/	
		vo.setWorkItems(procesInfoWorkItems);
	}
	
	
}
