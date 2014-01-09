package com.netpace.device.services;


import java.util.Map;
import java.util.SortedMap;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.BaseServiceTest;
import com.netpace.device.entities.VapMedia;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.ReprotsNameEnum;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.exceptions.VapGenericException;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.ProductSubmissionType;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.product.AttachmentFile;
import com.netpace.device.vo.product.ProcessingInfoVO;
import com.netpace.device.vo.product.ProductListVO;
import com.netpace.device.vo.product.ProductInfoVO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author aahmed
 */
public class ProductServiceTest extends BaseServiceTest {

	private static final Log log = LogFactory.getLog(ProductServiceTest.class);
	
	VAPUserDetails loggedInUser = new VAPUserDetails(null, "aahmed", null,
			true, true, true, true, null, null, null, null, null, null, null,
			null, null, null, 115, null, null);
	
	Integer productId=null;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ApplicationPropertiesService applicationPropertiesService;
	
	@Autowired
	private MediaService mediaService;
	
	public ProductServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() {

	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ApplicationPropertiesService getApplicationPropertiesService() {
		return applicationPropertiesService;
	}

	public void setApplicationPropertiesService(
			ApplicationPropertiesService applicationPropertiesService) {
		this.applicationPropertiesService = applicationPropertiesService;
	}

	public MediaService getMediaService() {
		return mediaService;
	}

	public void setMediaService(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	@Test
	public void testAll(){
//		createProduct();
//		triggerWorkItem();
//		testProductList();
//		testGetProduct();
//		testUpdateProduct();
//		inactiveProduct();
//		deleteMedia();
//		deleteAttachment();
		tmp();
	}
	
	
	public void createProduct() {
		for(int i=0; i<5000; i++){
		ProductInfoVO product=new ProductInfoVO();
		VapMedia media = new VapMedia();
		
		product.setName("Adnan Ahmed");
		product.setStreetAddress("12657 Alcosta Blvd");
		product.setEmailAddress("aahmed@netpace.com");
		product.setCity("San Ramon");
		product.setPhone("111-22-3333");
		product.setPostalCode("94583");
		product.setMobile("000-000-00000");
		product.setCountry("USA");
		product.setBtnType(VAPConstants.BTN_SUBMIT);
		
		media.setFileName("Out_Front_View.jpg");
		media.setFileLength(1000);
		media.setFileType("image/jpeg");
		media.populatedAuditFields("aahmed");
		
		log.debug("ProductServiceTest --- Add Product Start");
		product.setProductName("Product-"+i +" - "+System.currentTimeMillis());
		product.setProductStatus(String.valueOf(ProductStatus.Saved));
		product.setProductType(ProductSubmissionType.Product.getLabel());
		product.setDescription("Description");
		product.setModelNumber("Model #");
		product.setPartNumber("Supplier Product Part #");
		
		product.setProductCategory(applicationPropertiesService.propertiesByType(ApplicationPropertyType.PRODUCT_CATEGORY).get("Health & Fitness"));
		product.setSupportedDevices(new String[]{"Health & Fitness","Toys and Games"});
        
		try {
			productService.createProduct(product,loggedInUser);
			productId=product.getId();
		} catch (VapGenericException e) {
//				e.printStackTrace();
			log.error(e);
		}
		}
//		productService.saveProcessingInfo(product.getProductId(), new VapProductProcessInfo(applicationPropertiesService.getApplicationProperties(ApplicationPropertyType.RING_ASSOCIATION).get(2).getValue(), "eccn", "ccats"));
		
//		mediaService.saveMedia(media);
		
//		productService.saveAttachment(product.getId(), media, "out_front_view", loggedInUser);
		
		
		log.debug("ProductServiceTest --- Add Product End");
	}
	
	public void testGetProduct(){
		try {
			ProductInfoVO vo =productService.getProductDetialById(new Integer(147), loggedInUser);
//			ProcessingInfoVO vo =productService.getProcessingInfoForEdit(new Integer(147), loggedInUser);
			log.debug(vo);
		} catch (RecordNotFoundException e) {
			log.debug("======================"+e.getMessage());
			log.error(e);
		}
	}
	
	public void testUpdateProduct(){
		ProductInfoVO vo = productService.getProductDetialById(productId, loggedInUser);
		vo.setProductName("Update Product"+System.currentTimeMillis());
		vo.setModelNumber("Update Model #");
		vo.setDescription("Update description");
		vo.setPartNumber("Update part number");
		vo.setName("Update Name");
		vo.setStreetAddress("Update street");
		vo.setCity("update city");
		
		productService.updateProduct(vo, loggedInUser);
	}
	
	public void deleteMedia(){
		try {
			mediaService.deleteMedia(1);
		} catch (RecordNotFoundException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public void deleteAttachment(){
		Integer productId=33;
		Integer mediaId=9;
		Integer companyId=1;
		AttachmentFile file =new AttachmentFile();
		file.setCompanyId(companyId);
		file.setProductId(productId);
		file.setMediaId(mediaId);
		file.setFileName("1.png");
		try {
			productService.deleteAttachment(file,loggedInUser);
		} catch (RecordNotFoundException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
    public void testProductList() {
        PageModel<ProductListVO> pageModel = new PageModel<ProductListVO>();
        pageModel.setSearchBy("vp.status");
        pageModel.setSearchValue("Saved");
//        pageModel.setSortBy("wf.product.submittedDate");
//        pageModel.setAscending(true);
        pageModel.setPage("0");
        pageModel.setPageSize(10);

//        FilterVal val1 = new FilterVal();
//        FilterVal val2 = new FilterVal();
//        FilterVal val3 = new FilterVal();

//        val1.setSelected(true);
//        val1.setTitle("DeviceMarketingReview");
//        val2.setSelected(true);
//        val2.setTitle("DeviceMarketingReviewRFI");
//        val3.setSelected(true);
//        val3.setTitle("DeviceMarketingReviewDenied");


//        List<FilterVal> filterModelValues = new ArrayList<FilterVal>();
//        filterModelValues.add(val1);
//        filterModelValues.add(val2);
//        filterModelValues.add(val3);

//        FilterModel filterModel = new FilterModel();
//        filterModel.setFilterValues(filterModelValues);

//        List<FilterModel> filters = new ArrayList<FilterModel>();
//        filters.add(filterModel);

//        pageModel.setFilters(filters);

        
        productService.getProductsForList(pageModel, loggedInUser);
        
    }

    public void inactiveProduct(){
    	productService.inactiveProduct(productId, loggedInUser);
    }
    

    public void tmp(){
    	
    	log.info("**************************"+VAPUtils.formatWorkflowSteps("Device Marketing Review Denied,<br/> Product Info Upload"));
    	//String[] userRoles=new String[]{"ROLE_DEVICE_MARKETING","ROLE_OFAC","ROLE_REQUIREMENTS_GROUP","ROLE_DEVICE_COMPLIANCE"};
    	//log.info("//**********************************"+StringUtils.join(userRoles, "%' or workitem.allowedRoles like '%"));
    }
	
    
    public void triggerWorkItem(){
    	for (int i=101; i<=200;  i++){
    		ProcessingInfoVO vo =new ProcessingInfoVO();
    		vo.setProcessId(new Integer(i));
    		vo.setRingAssociation("Ring-3");
    		vo.setAgreementAcceptTitle("AA");
    		vo.setCcats("ccats");
    		vo.setEccn("eccn");
    		
    		int workflowId=i;
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_DM;
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_EC;
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_PIU;   
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_ECR;
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_RGR;
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_DCR;
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_DCRT;    		
//    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_PDF;    		
    		String sectionName=VAPConstants.PRODUCT_PROCESS_SECTION_DMA;

//     		String btnType="Approve";
//    		String btnType="Submit to Verizon";
//    		String btnType="Accept Agreement and Submit to Verizon";
//    		String btnType="Approve";
//    		String btnType="Approve";
//    		String btnType="Start Testing";
//    		String btnType="End Testing";     	
//    		String btnType="Submit to Verizon";     		
    		String btnType="Approve";
     		
    		
    		vo.setWorkflowId(workflowId);
    		vo.setSectionName(sectionName);
    		vo.setBtnType(btnType);
    		
    		productService.saveProcessingInfo(vo, loggedInUser);
    	}
    }
    
}
