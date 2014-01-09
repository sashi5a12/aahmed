package com.netpace.device.controllers.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.core.Local;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.entities.VapMedia;
import com.netpace.device.entities.VapProductAttachment;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.exceptions.VapGenericException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.MediaService;
import com.netpace.device.services.ProductService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.product.AttachmentFile;
import com.netpace.device.vo.product.FileUploadVO;

@Controller
public class ProductAttachmentController {

	@Autowired
	MediaService mediaService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	protected MessageSource messageSource;
	
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
	
	private static final Log log = LogFactory.getLog(ProductAttachmentController.class);
	@RequestMapping(value = "/json/upload.do", method = RequestMethod.POST)
	public @ResponseBody String upload(HttpServletRequest request, 
				@RequestParam(value = "product_id", required = false) String productId,
				@RequestParam(value = "tab", required = true) Integer tab,
				@RequestParam(value = "out_front_view", required = false) MultipartFile out_front_view, 
				@RequestParam(value = "out_angeled_view", required = false) MultipartFile out_angeled_view,
				@RequestParam(value = "out_another_object", required = false) MultipartFile out_another_object,
				@RequestParam(value = "out_other_view1", required = false) MultipartFile out_other_view1,
				@RequestParam(value = "out_other_view2", required = false) MultipartFile out_other_view2,
				@RequestParam(value = "out_other_view3", required = false) MultipartFile out_other_view3,
				@RequestParam(value = "out_other_view4", required = false) MultipartFile out_other_view4,
				@RequestParam(value = "in_front_view", required = false) MultipartFile in_front_view,
				@RequestParam(value = "in_angeled_view", required = false) MultipartFile in_angeled_view,
				@RequestParam(value = "in_another_object", required = false) MultipartFile in_another_object,
				@RequestParam(value = "screen_shot1", required = false) MultipartFile screen_shot1,
				@RequestParam(value = "screen_shot2", required = false) MultipartFile screen_shot2,
				@RequestParam(value = "screen_shot3", required = false) MultipartFile screen_shot3,
				@RequestParam(value = "lifestyle_image1", required = false) MultipartFile lifestyle_image1,
				@RequestParam(value = "lifestyle_image2", required = false) MultipartFile lifestyle_image2,
				@RequestParam(value = "lifestyle_image3", required = false) MultipartFile lifestyle_image3,
				@RequestParam(value = "phone_splash_screen", required = false) MultipartFile phone_splash_screen,
				@RequestParam(value = "tablet_splash_screen", required = false) MultipartFile tablet_splash_screen,
				@RequestParam(value = "application_icon", required = false) MultipartFile application_icon,
				@RequestParam(value = "phone_in_app_screen", required = false) MultipartFile phone_in_app_screen,
				@RequestParam(value = "tablet_in_app_screen", required = false) MultipartFile tablet_in_app_screen,
				@RequestParam(value = "product_video", required = false) MultipartFile product_video,
				@RequestParam(value = "phone_app_video", required = false) MultipartFile phone_app_video,
				@RequestParam(value = "tablet_app_video", required = false) MultipartFile tablet_app_video,
				@RequestParam(value = "product_copy_doc", required = false) MultipartFile product_copy_doc,
				@RequestParam(value = "app_copy_doc", required = false) MultipartFile app_copy_doc,
				@RequestParam(value = "launch_presentation_video", required = false) MultipartFile launch_presentation_video,
				@RequestParam(value = "testing_spreadsheet", required = false) MultipartFile testing_spreadsheet,
				@RequestParam(value = "product_label", required = false) MultipartFile product_label,
				@RequestParam(value = "sustainability_disclosure", required = false) MultipartFile sustainability_disclosure,
				@RequestParam(value = "packaging_label", required = false) MultipartFile packaging_label,
				@RequestParam(value = "pdf_sample_product", required = false) MultipartFile pdf_sample_product) {
		
		log.debug("ProductAttachmentController.upload:  (Upload File) ----------------- Start");
		Message responseMsg=new Message();
		VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
				
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			log.debug(parameterName +" == "+request.getParameter(parameterName));
		}
		
		MultipartFile uploadedFile=null;
		try {
			if(out_front_view!=null && !out_front_view.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_OUT_FRONT_VIEW);
				uploadedFile=out_front_view;
			}
			else if(out_angeled_view!=null && !out_angeled_view.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_OUT_ANGELED_VIEW);
				uploadedFile=out_angeled_view;
			}
			else if(out_another_object!=null && !out_another_object.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_OUT_ANOTHER_OBJECT);
				uploadedFile=out_another_object;
			}
			else if(out_other_view1!=null && !out_other_view1.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_OUT_OTHER_VIEW1);
				uploadedFile=out_other_view1;
			}
			else if(out_other_view2!=null && !out_other_view2.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_OUT_OTHER_VIEW2);
				uploadedFile=out_other_view2;
			}
			else if(out_other_view3!=null && !out_other_view3.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_OUT_OTHER_VIEW3);
				uploadedFile=out_other_view3;
			}
			else if(out_other_view4!=null && !out_other_view4.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_OUT_OTHER_VIEW4);
				uploadedFile=out_other_view4;
			}
			else if(in_front_view!=null && !in_front_view.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_IN_FRONT_VIEW);
				uploadedFile=in_front_view;
			}
			else if(in_angeled_view!=null && !in_angeled_view.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_IN_ANGELED_VIEW);
				uploadedFile=in_angeled_view;
			}
			else if(in_another_object!=null && !in_another_object.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_IN_ANOTHER_OBJECT);
				uploadedFile=in_another_object;
			}
			else if(screen_shot1!=null && !screen_shot1.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_SCREEN_SHOT1);
				uploadedFile=screen_shot1;
			}
			else if(screen_shot2!=null && !screen_shot2.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_SCREEN_SHOT2);
				uploadedFile=screen_shot2;
			}
			else if(screen_shot3!=null && !screen_shot3.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_SCREEN_SHOT3);
				uploadedFile=screen_shot3;
			}
			else if(lifestyle_image1!=null && !lifestyle_image1.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_LIFESTYLE_IMAGE1);
				uploadedFile=lifestyle_image1;
			}
			else if(lifestyle_image2!=null && !lifestyle_image2.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_LIFESTYLE_IMAGE2);
				uploadedFile=lifestyle_image2;
			}
			else if(lifestyle_image3!=null && !lifestyle_image3.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_LIFESTYLE_IMAGE3);
				uploadedFile=lifestyle_image3;
			}
			else if(phone_splash_screen!=null && !phone_splash_screen.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_PHONE_SPLASH_SCREEN);
				uploadedFile=phone_splash_screen;
			}
			else if(tablet_splash_screen!=null && !tablet_splash_screen.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_TABLET_SPLASH_SCREEN);
				uploadedFile=tablet_splash_screen;
			}
			else if(application_icon!=null && !application_icon.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_APPLICATION);
				uploadedFile=application_icon;
			}
			else if(phone_in_app_screen!=null && !phone_in_app_screen.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_PHONE_IN_APP_SCREEN);
				uploadedFile=phone_in_app_screen;
			}
			else if(tablet_in_app_screen!=null && !tablet_in_app_screen.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_TABLET_IN_APP_SCREEN);
				uploadedFile=tablet_in_app_screen;
			}
			else if(product_video!=null && !product_video.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_PRODUCT_VIDEO);
				uploadedFile=product_video;
			}
			else if(phone_app_video!=null && !phone_app_video.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_PHONE_APP_VIDEO);
				uploadedFile=phone_app_video;
			}
			else if(tablet_app_video!=null && !tablet_app_video.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_TABLET_APP_VIDEO);
				uploadedFile=tablet_app_video;
			}
			else if(product_copy_doc!=null && !product_copy_doc.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_PRODUCT_COPY_DOC);
				uploadedFile=product_copy_doc;
			}
			else if(app_copy_doc!=null && !app_copy_doc.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_APP_COPY_DOC);
				uploadedFile=app_copy_doc;
			}
			else if(launch_presentation_video!=null && !launch_presentation_video.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO);
				uploadedFile=launch_presentation_video;
			}
			else if(testing_spreadsheet!=null && !testing_spreadsheet.isEmpty()){
				responseMsg.setFileType(VAPConstants.TESTING_SPREADSHEET);
				uploadedFile=testing_spreadsheet;
			}
			else if(product_label!=null && !product_label.isEmpty()){
				responseMsg.setFileType(VAPConstants.PRODUCT_LABEL);
				uploadedFile=product_label;
			}
			else if(sustainability_disclosure!=null && !sustainability_disclosure.isEmpty()){
				responseMsg.setFileType(VAPConstants.SUSTAINABILITY_DISCLOSURE);
				uploadedFile=sustainability_disclosure;
			}
			else if(packaging_label!=null && !packaging_label.isEmpty()){
				responseMsg.setFileType(VAPConstants.PACKAGING_LABEL);
				uploadedFile=packaging_label;
			}
			else if(pdf_sample_product!=null && !pdf_sample_product.isEmpty()){
				responseMsg.setFileType(VAPConstants.PDF_SAMPLE_PRODUCT);
				uploadedFile=pdf_sample_product;
			}
			
			log.debug("Uploading file for "+responseMsg.getFileType());
			
			if(uploadedFile!=null && !uploadedFile.isEmpty()){							
				log.debug("OrginalFileName: "+uploadedFile.getOriginalFilename());
				log.debug("ContentType: "+uploadedFile.getContentType());
				log.debug("size: "+uploadedFile.getSize());
				
				if (uploadedFile.getOriginalFilename().length()>300){
					log.debug("File size is greater than 300.");
					responseMsg.setSuccess(false);
					responseMsg.setError(messageSource.getMessage("productVO.attachment.file.length", new String[]{"300"}, Locale.getDefault()));
					responseMsg.setPreventRetry(true);	
					return getJsonString(responseMsg);
				}
				
				VapMedia media = new VapMedia();
				media.setFileName(uploadedFile.getOriginalFilename());
				media.setFileType(uploadedFile.getContentType());
				media.setFileLength((int)uploadedFile.getSize());
				media.populatedAuditFields(loggedInUser.getUsername());
				
				if(StringUtils.isEmpty(productId)){
					log.debug("Product id not found. Mean it is being upload for new product.");
					productId="0";
				}
				
				FileUploadVO fileUpload=new FileUploadVO();
				fileUpload.setProductId(Integer.parseInt(productId));
				fileUpload.setFileType(responseMsg.getFileType());
				fileUpload.setData(uploadedFile.getBytes());
				fileUpload.setTab(tab);
				productService.saveAttachment(fileUpload, media, loggedInUser);						
				
				responseMsg.setMediaId(media.getMediaId());
				responseMsg.setSuccess(true);
			}
			else {
				responseMsg.setError("Error occured while uploading the file. ");
			}
		} 
		catch (VapGenericException e){
			log.error(e);
			responseMsg.setSuccess(false);
			responseMsg.setError(messageSource.getMessage(e.getMessage(), null, Locale.getDefault()));
			responseMsg.setPreventRetry(true);			
		}
		catch (Exception e) {
			log.error(e);
			responseMsg.setSuccess(false);
			responseMsg.setError("Unknown error occurred while uploading the file.");
			responseMsg.setPreventRetry(true);
		} 
		finally {
			if (uploadedFile != null){
				uploadedFile=null;
			}
			if (out_front_view!=null){
				out_front_view=null;
			}
			if (out_angeled_view!=null){
				out_angeled_view=null;
			}
		
			if (out_another_object!=null){
				out_another_object=null;
			}
			if (out_other_view1!=null){
				out_other_view1=null;
			}
			if (out_other_view2!=null){
				out_other_view2=null;
			}
			if (out_other_view3!=null){
				out_other_view3=null;
			}
			if (out_other_view4!=null){
				out_other_view4=null;
			}
			if (in_front_view!=null){
				in_front_view=null;
			}
			if (in_another_object!=null){
				in_another_object=null;
			}
			if (screen_shot1!=null){
				screen_shot1=null;
			}
			if (screen_shot2!=null){
				screen_shot2=null;
			}
			if (screen_shot3!=null){
				screen_shot3=null;
			}
			if (lifestyle_image1!=null){
				lifestyle_image1=null;
			}
			if (lifestyle_image2!=null){
				lifestyle_image2=null;
			}
			if (lifestyle_image3!=null){
				lifestyle_image3=null;
			}
			if (phone_splash_screen!=null){
				phone_splash_screen=null;
			}
			if (tablet_splash_screen!=null){
				tablet_splash_screen=null;
			}
			if (application_icon!=null){
				application_icon=null;
			}
			if (phone_in_app_screen!=null){
				phone_in_app_screen=null;
			}
			if (tablet_in_app_screen!=null){
				tablet_in_app_screen=null;
			}
			if (product_video!=null){
				product_video=null;
			}
			if (phone_app_video!=null){
				phone_app_video=null;
			}
			if (tablet_app_video!=null){
				tablet_app_video=null;
			}
			if (product_copy_doc!=null){
				product_copy_doc=null;
			}
			if (app_copy_doc!=null){
				app_copy_doc=null;
			}
			if (launch_presentation_video!=null){
				launch_presentation_video=null;
			}
			if (testing_spreadsheet!=null){
				testing_spreadsheet=null;
			}
			if (product_label!=null){
				product_label=null;
			}
			if (sustainability_disclosure!=null){
				sustainability_disclosure=null;
			}
			if (packaging_label!=null){
				packaging_label=null;
			}
			if (pdf_sample_product!=null){
				pdf_sample_product=null;
			}
		}
		
		log.debug("ProductAttachmentController.upload:  (Upload File) ----------------- End");
		return getJsonString(responseMsg);
	}
	
	@RequestMapping(value = "/json/test.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)  
	public @ResponseBody String jsonText(HttpServletRequest request, HttpServletResponse response) {

		Message responseMsg=new Message();
		responseMsg.setFileType(VAPConstants.PRODUCT_OUT_FRONT_VIEW);
		responseMsg.setMediaId(new Integer(1));
		responseMsg.setSuccess(true);
		
//		String success="{\"success\":\"true\",\"error\":\"null\",\"preventRetry\":\"false\",\"mediaId\":\"1\",\"fileType\":\"out_front_view\"}";
	
		String success=getJsonString(responseMsg);
		log.debug("***********JSON: "+success);
		response.setHeader("Content-Type", "text/plain");

//		return getJsonString(responseMsg);
		
		return success;
	}

	public String getJsonString(Message responseMsg) {
		String jsonStr="{\"success\":true,\"error\":\"Unkonw Error.\"}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(responseMsg);
			log.debug("JSON Response: "+jsonStr);
		} catch (JsonGenerationException e) {
			log.error(e);
		} catch (JsonMappingException e) {
			log.error(e);		
		} catch (IOException e) {
			log.error(e);
		}
		return jsonStr;
	}

	@RequestMapping(value = "/json/delete.do", method = RequestMethod.GET)
	public @ResponseBody String deleteMedia(HttpServletRequest request, 
			@RequestParam(value = "mediaId") String mediaId,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "fileName") String fileName) {
		
		Message responseMsg=new Message();
		
		AttachmentFile file=new AttachmentFile();
		VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
		
		log.debug("ProductAttachmentController.deleteMedia:  (delete File) ----------------- Start");
		
		log.debug("p_mediaId: "+mediaId);
		log.debug("p_productId: "+productId);
		log.debug("p_fileName: "+fileName);
		
		if(StringUtils.isEmpty(productId)){
			productId="0";
		}
		
		try {

			file.setCompanyId(loggedInUser.getCompanyId());
			file.setProductId(Integer.parseInt(productId));
			file.setMediaId(Integer.parseInt(mediaId));
			file.setFileName(fileName);

			productService.deleteAttachment(file, loggedInUser);

			responseMsg.setSuccess(true);
		} catch (JpaObjectRetrievalFailureException e) {
			log.debug(e);
			responseMsg.setSuccess(false);
			responseMsg.setError(messageSource.getMessage(e.getMessage(), null, Locale.getDefault()));
		} catch (Exception e) {
			log.debug(e);
			responseMsg.setSuccess(false);
			responseMsg.setError(messageSource.getMessage("record.constraint.violation", null, Locale.getDefault()));
		}
		
		log.debug("ProductAttachmentController.deleteMedia:  (delete File) ----------------- End");
		return getJsonString(responseMsg);
	}
	
	@RequestMapping(value = "/secure/process/download.do", method = RequestMethod.GET)
	public void download(HttpServletRequest request, 
			@RequestParam(value = "mediaId") String mediaId,
			@RequestParam(value = "productId", required=false) String productId,
			HttpServletResponse response) {
		
		final String FILE_PATH=applicationPropertiesService.getApplicationPropertiesByTypeAndKey(ApplicationPropertyType.APPLICATION_PROPERTY, VAPConstants.ATTACHMENT_FILE_PATH).getValue();
		
		log.debug("ProductAttachmentController.download:  (download File) ----------------- Start");
		
		log.debug("p_mediaId: "+mediaId);
		log.debug("p_productId: "+productId);
		FileInputStream in=null;
		ServletOutputStream out=null;
		
		if(StringUtils.isEmpty(productId)){
			productId="0";
		}
		
		try {
			VapProductAttachment att=new VapProductAttachment();
			if (StringUtils.isNotEmpty(mediaId)) {
				att=productService.getAttachment(Integer.parseInt(productId), Integer.parseInt(mediaId));
			}

			if (att !=null && att.getMedia() != null){				
				File file = new File(FILE_PATH + mediaId + "." + VAPUtils.getFileExtension(att.getMedia().getFileName()));
				
//				response.setContentType(att.getMedia().getFileType()); 
				response.setContentType("application/octet-stream");
		        response.setContentLength(att.getMedia().getFileLength());
		        response.setHeader("Content-Disposition","attachment; filename="+StringUtils.replace(att.getMedia().getFileName(), " ", "_"));
		        
		        in = new FileInputStream(file);
		        out = response.getOutputStream();
		        
		        byte[] outputByte = new byte[4096];
		        while(in.read(outputByte, 0, 4096) != -1) {
		        	out.write(outputByte, 0, 4096);
		        }		      
		        out.flush();
		        response.flushBuffer();
			}

		} catch (JpaObjectRetrievalFailureException e) {
			log.debug(e);
//			responseMsg.setError(messageSource.getMessage(e.getMessage(), null, Locale.getDefault()));
		} catch (IOException e) {
			log.debug(e);
		} catch (Exception e) {
			log.debug(e);
//			responseMsg.setError(messageSource.getMessage("record.constraint.violation", null, Locale.getDefault()));
		} finally {
			if (in !=null){
				try {
					in.close();
				} catch (IOException e) {
					log.debug(e);
				}
			}
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					log.debug(e);
				}
			}
		}
		
		log.debug("ProductAttachmentController.download:  (download File) ----------------- End");
	}
	
	public MediaService getMediaService() {
		return mediaService;
	}

	public void setMediaService(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ApplicationPropertiesService getApplicationPropertiesService() {
		return applicationPropertiesService;
	}

	public void setApplicationPropertiesService(
			ApplicationPropertiesService applicationPropertiesService) {
		this.applicationPropertiesService = applicationPropertiesService;
	}


	private class Message{
		boolean success;
		String error;
		boolean preventRetry;
		Integer mediaId;
		String fileType;
		
		public Message() {
		}
		
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public boolean isPreventRetry() {
			return preventRetry;
		}
		public void setPreventRetry(boolean preventRetry) {
			this.preventRetry = preventRetry;
		}
		public Integer getMediaId() {
			return mediaId;
		}

		public void setMediaId(Integer mediaId) {
			this.mediaId = mediaId;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
	}
	
}
