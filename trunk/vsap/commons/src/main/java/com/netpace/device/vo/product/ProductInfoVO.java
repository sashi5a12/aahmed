package com.netpace.device.vo.product;

import java.util.Arrays;

import com.netpace.device.vo.Record;

/**
 * @author aahmed
 */
public class ProductInfoVO extends ProductVO{
    
	private static final long serialVersionUID = 2155272781122770090L;
	private String pageType;
    private Boolean validRequiredFields;
    private String btnType;
    private String dbProductType;
    
    //Basic Information
    private String productName;
    private String modelNumber;
    private String description;
    private String productCategory;
    private String partNumber;
    private String sampleTracking;
    private String availabilityDate;
    private String vzwExclusive;
    
    //Product Details
    private String targetSegment;
    private String positioningStatement;
    private String deviceNeed;
    private String mainCompetition;    
    private String uniqueFunctionality;
    
    //Highlighted Features
    private String connectivityType;
    private String paltformSupported;
    private String cloudSupported;
    private String formRequirements;
    private String productDimensions;
    private String itemIncluded;
    
    //Product Contact
    private Integer contactId;
    private String name;
    private String streetAddress;
    private String emailAddress;
    private String city;
    private String phone;
    private String postalCode;
    private String mobile;
    private String country;
    private String state;
    
    //Product Images. NEVER CHANGE THIS ATTRIBUTE NAMEs. THERE IS A LOGIC IN KEEPING THE NAME LIKE THIS
    private AttachmentFile out_front_view;
    private AttachmentFile out_angeled_view;
    private AttachmentFile out_another_object;
    private AttachmentFile out_other_view1;
    private AttachmentFile out_other_view2;
    private AttachmentFile out_other_view3;
    private AttachmentFile out_other_view4;
    private AttachmentFile in_front_view;
    private AttachmentFile in_angeled_view;
    private AttachmentFile in_another_object;
    private AttachmentFile screen_shot1;
    private AttachmentFile screen_shot2;
    private AttachmentFile screen_shot3;
    private AttachmentFile lifestyle_image1;
    private AttachmentFile lifestyle_image2;
    private AttachmentFile lifestyle_image3;
    
    private String[] supportedDevices;
    private AttachmentFile phone_splash_screen;
    private AttachmentFile tablet_splash_screen;
    private AttachmentFile application_icon;
    private AttachmentFile phone_in_app_screen;
    private AttachmentFile tablet_in_app_screen;
    
    private AttachmentFile  product_video;
    private AttachmentFile  phone_app_video;
    private AttachmentFile  tablet_app_video;
    private AttachmentFile  product_copy_doc;
    private AttachmentFile  app_copy_doc;
    private AttachmentFile  launch_presentation_video;
    
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public Boolean getValidRequiredFields() {
		return validRequiredFields;
	}
	public void setValidRequiredFields(Boolean validRequiredFields) {
		this.validRequiredFields = validRequiredFields;
	}
	 
	public String getBtnType() {
		return btnType;
	}
	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}
	public String getDbProductType() {
		return dbProductType;
	}
	public void setDbProductType(String dbProductType) {
		this.dbProductType = dbProductType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getSampleTracking() {
		return sampleTracking;
	}
	public void setSampleTracking(String sampleTracking) {
		this.sampleTracking = sampleTracking;
	}
	
	public String getAvailabilityDate() {
		return availabilityDate;
	}
	public void setAvailabilityDate(String availabilityDate) {
		this.availabilityDate = availabilityDate;
	}
	
	public String getVzwExclusive() {
		return vzwExclusive;
	}
	public void setVzwExclusive(String vzwExclusive) {
		this.vzwExclusive = vzwExclusive;
	}
	
	public String getTargetSegment() {
		return targetSegment;
	}
	public void setTargetSegment(String targetSegment) {
		this.targetSegment = targetSegment;
	}
	public String getPositioningStatement() {
		return positioningStatement;
	}
	public void setPositioningStatement(String positioningStatement) {
		this.positioningStatement = positioningStatement;
	}
	public String getDeviceNeed() {
		return deviceNeed;
	}
	public void setDeviceNeed(String deviceNeed) {
		this.deviceNeed = deviceNeed;
	}
	public String getMainCompetition() {
		return mainCompetition;
	}
	public void setMainCompetition(String mainCompetition) {
		this.mainCompetition = mainCompetition;
	}
	public String getUniqueFunctionality() {
		return uniqueFunctionality;
	}
	public void setUniqueFunctionality(String uniqueFunctionality) {
		this.uniqueFunctionality = uniqueFunctionality;
	}
	public String getConnectivityType() {
		return connectivityType;
	}
	public void setConnectivityType(String connectivityType) {
		this.connectivityType = connectivityType;
	}
	public String getPaltformSupported() {
		return paltformSupported;
	}
	public void setPaltformSupported(String paltformSupported) {
		this.paltformSupported = paltformSupported;
	}
	public String getCloudSupported() {
		return cloudSupported;
	}
	public void setCloudSupported(String cloudSupported) {
		this.cloudSupported = cloudSupported;
	}
	public String getFormRequirements() {
		return formRequirements;
	}
	public void setFormRequirements(String formRequirements) {
		this.formRequirements = formRequirements;
	}
	public String getProductDimensions() {
		return productDimensions;
	}
	public void setProductDimensions(String productDimensions) {
		this.productDimensions = productDimensions;
	}
	public String getItemIncluded() {
		return itemIncluded;
	}
	public void setItemIncluded(String itemIncluded) {
		this.itemIncluded = itemIncluded;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public AttachmentFile getOut_front_view() {
		return out_front_view;
	}
	public void setOut_front_view(AttachmentFile out_front_view) {
		this.out_front_view = out_front_view;
	}
	public AttachmentFile getOut_angeled_view() {
		return out_angeled_view;
	}
	public void setOut_angeled_view(AttachmentFile out_angeled_view) {
		this.out_angeled_view = out_angeled_view;
	}
	public AttachmentFile getOut_another_object() {
		return out_another_object;
	}
	public void setOut_another_object(AttachmentFile out_another_object) {
		this.out_another_object = out_another_object;
	}
	public AttachmentFile getOut_other_view1() {
		return out_other_view1;
	}
	public void setOut_other_view1(AttachmentFile out_other_view1) {
		this.out_other_view1 = out_other_view1;
	}
	public AttachmentFile getOut_other_view2() {
		return out_other_view2;
	}
	public void setOut_other_view2(AttachmentFile out_other_view2) {
		this.out_other_view2 = out_other_view2;
	}
	public AttachmentFile getOut_other_view3() {
		return out_other_view3;
	}
	public void setOut_other_view3(AttachmentFile out_other_view3) {
		this.out_other_view3 = out_other_view3;
	}
	public AttachmentFile getOut_other_view4() {
		return out_other_view4;
	}
	public void setOut_other_view4(AttachmentFile out_other_view4) {
		this.out_other_view4 = out_other_view4;
	}
	public AttachmentFile getIn_front_view() {
		return in_front_view;
	}
	public void setIn_front_view(AttachmentFile in_front_view) {
		this.in_front_view = in_front_view;
	}
	public AttachmentFile getIn_angeled_view() {
		return in_angeled_view;
	}
	public void setIn_angeled_view(AttachmentFile in_angeled_view) {
		this.in_angeled_view = in_angeled_view;
	}
	public AttachmentFile getIn_another_object() {
		return in_another_object;
	}
	public void setIn_another_object(AttachmentFile in_another_object) {
		this.in_another_object = in_another_object;
	}
	public AttachmentFile getScreen_shot1() {
		return screen_shot1;
	}
	public void setScreen_shot1(AttachmentFile screen_shot1) {
		this.screen_shot1 = screen_shot1;
	}
	public AttachmentFile getScreen_shot2() {
		return screen_shot2;
	}
	public void setScreen_shot2(AttachmentFile screen_shot2) {
		this.screen_shot2 = screen_shot2;
	}
	public AttachmentFile getScreen_shot3() {
		return screen_shot3;
	}
	public void setScreen_shot3(AttachmentFile screen_shot3) {
		this.screen_shot3 = screen_shot3;
	}
	public AttachmentFile getLifestyle_image1() {
		return lifestyle_image1;
	}
	public void setLifestyle_image1(AttachmentFile lifestyle_image1) {
		this.lifestyle_image1 = lifestyle_image1;
	}
	public AttachmentFile getLifestyle_image2() {
		return lifestyle_image2;
	}
	public void setLifestyle_image2(AttachmentFile lifestyle_image2) {
		this.lifestyle_image2 = lifestyle_image2;
	}
	public AttachmentFile getLifestyle_image3() {
		return lifestyle_image3;
	}
	public void setLifestyle_image3(AttachmentFile lifestyle_image3) {
		this.lifestyle_image3 = lifestyle_image3;
	}
	public AttachmentFile getPhone_splash_screen() {
		return phone_splash_screen;
	}
	public void setPhone_splash_screen(AttachmentFile phone_splash_screen) {
		this.phone_splash_screen = phone_splash_screen;
	}
	public AttachmentFile getTablet_splash_screen() {
		return tablet_splash_screen;
	}
	public void setTablet_splash_screen(AttachmentFile tablet_splash_screen) {
		this.tablet_splash_screen = tablet_splash_screen;
	}
	public AttachmentFile getApplication_icon() {
		return application_icon;
	}
	public void setApplication_icon(AttachmentFile application_icon) {
		this.application_icon = application_icon;
	}
	public AttachmentFile getPhone_in_app_screen() {
		return phone_in_app_screen;
	}
	public void setPhone_in_app_screen(AttachmentFile phone_in_app_screen) {
		this.phone_in_app_screen = phone_in_app_screen;
	}
	public AttachmentFile getTablet_in_app_screen() {
		return tablet_in_app_screen;
	}
	public void setTablet_in_app_screen(AttachmentFile tablet_in_app_screen) {
		this.tablet_in_app_screen = tablet_in_app_screen;
	}
	public String[] getSupportedDevices() {
		return supportedDevices;
	}
	public void setSupportedDevices(String[] supportedDevices) {
		this.supportedDevices = supportedDevices;
	}
	public AttachmentFile getProduct_video() {
		return product_video;
	}
	public void setProduct_video(AttachmentFile product_video) {
		this.product_video = product_video;
	}
	public AttachmentFile getPhone_app_video() {
		return phone_app_video;
	}
	public void setPhone_app_video(AttachmentFile phone_app_video) {
		this.phone_app_video = phone_app_video;
	}
	public AttachmentFile getTablet_app_video() {
		return tablet_app_video;
	}
	public void setTablet_app_video(AttachmentFile tablet_app_video) {
		this.tablet_app_video = tablet_app_video;
	}
	public AttachmentFile getProduct_copy_doc() {
		return product_copy_doc;
	}
	public void setProduct_copy_doc(AttachmentFile product_copy_doc) {
		this.product_copy_doc = product_copy_doc;
	}
	public AttachmentFile getApp_copy_doc() {
		return app_copy_doc;
	}
	public void setApp_copy_doc(AttachmentFile app_copy_doc) {
		this.app_copy_doc = app_copy_doc;
	}
	public AttachmentFile getLaunch_presentation_video() {
		return launch_presentation_video;
	}
	public void setLaunch_presentation_video(
			AttachmentFile launch_presentation_video) {
		this.launch_presentation_video = launch_presentation_video;
	}
	@Override
	public String toString() {
		return "ProductInfoVO [pageType=" + pageType + ", productName=" + productName + "]";
	}

	
}
