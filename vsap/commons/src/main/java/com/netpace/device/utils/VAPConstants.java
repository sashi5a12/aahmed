/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.utils;

/**
 *
 * @author Hamza Ghayas
 */
public class VAPConstants {
    public static final String MENU_INFORMATION = "MENU_INFORMATION";
    
    public enum Email_Type{
        ALL,USER,ADMIN
    }
    
    /* Workflow Constants */
    public static final String VAP_WORKFLOW_VAR_BUSINESS_KEY    = "_VAP_WORKFLOW_BUSINESS_KEY";
    public static final String VAP_WORKFLOW_VAR_DECISION        = "decision";
    public static final String VAP_WORKFLOW_VAR_COMMENT         = "_VAP_WORKFLOW_COMMENT_VAR";
    public static final String VAP_WORKFLOW_VAR_LOGGED_USER_NAME     = "_VAP_WORKFLOW_LOGGED_USER";
    public static final String VAP_WORKFLOW_VAR_PRODUCT_SUBMISSION_TYPE    = "productSubmissionType";
    public static final String VAP_WORKFLOW_VAR_COMPANY_TITLE    = "_VAP_COMPANY_TITLE";
    public static final String VAP_WORKFLOW_VAR_COMPANY_DOMAIN    = "_VAP_COMPANY_DOMAIN";
    
    public static final String VAP_WORKFLOW_PRODUCT_STEP_STATUS_PROCESSED      = "PROCESSED";
    public static final String VAP_WORKFLOW_PRODUCT_STEP_STATUS_NOT_PROCESSED  = "NOT_PROCESSED";
    public static final String VAP_WORKFLOW_COMPANY_STEP_TITLE_DeviceMarketingReviewDenied  = "Device Marketing Review Denied";
    public static final String VAP_WORKFLOW_COMPANY_STEP_TITLE_OfflineCertificationNDADenied  = "Offline Certification Agreement Denied";
    public static final String VAP_WORKFLOW_COMPANY_STEP_DISPLAY_TITLES  = "Device Marketing Review,Device Marketing Review Denied,Device Marketing Review RFI,OFAC Review,OFAC Review RFI,OFAC Review Suspended,Certification Agreement & Non-Disclosure Agreement,Offline Certification Agreement,Offline Certification Agreement Denied";
    public static final String VAP_WORKFLOW_PRODUCT_STEP_DISPLAY_TITLES  = "Device Marketing Review,Device Marketing Review Denied,Device Marketing Review RFI,Device Marketing Concept Approved,Export Compliance,Export Compliance Review,Export Compliance Review RFI,Export Compliance Review Denied,Product Info Upload,Requirements Group Review,Requirements Group Review RFI,Requirements Group Review Denied,Device Compliance Review,Device Compliance Review RFI,Device Compliance Review Denied,Device Compliance Review EOT,Device Compliance Review EOT RFI,Device Compliance Review EOT Denied,Upload PDF of Sample Product,Device Marketing Final Review,Device Marketing Final Review RFI,Device Marketing Final Review Denied";
    
    /* Roles Constants */
    public static final String  ROLE_MPOC                       = "ROLE_MPOC";
    public static final Integer ROLE_MPOC_ID                    = 1;
    public static final String  ROLE_PARTNER_USER               = "ROLE_PARTNER_USER";
    public static final Integer ROLE_PARTNER_USER_ID            = 2;
    public static final String  ROLE_ADMIN                      = "ROLE_ADMIN";
    public static final Integer ROLE_ADMIN_ID                   = 3;
    public static final String  ROLE_SUPER_ADMIN                = "ROLE_SUPER_ADMIN";
    public static final Integer ROLE_SUPER_ADMIN_ID             = 4;
    public static final String  ROLE_VERIZON_ADMIN              = "ROLE_VERIZON_SYSTEM_ADMIN";
    public static final Integer ROLE_VERIZON_ADMIN_ID           = 5;
    public static final String  ROLE_DEVICE_MARKETING           = "ROLE_DEVICE_MARKETING";
    public static final Integer ROLE_DEVICE_MARKETING_ID        = 6;
    public static final String  ROLE_OFAC                       = "ROLE_OFAC";
    public static final Integer ROLE_OFAC_ID                    = 7;
    public static final String  ROLE_REQUIREMENTS_GROUP    		= "ROLE_REQUIREMENTS_GROUP";
    public static final String  ROLE_DEVICE_COMPLIANCE     		= "ROLE_DEVICE_COMPLIANCE";
    public static final String  ROLE_EXPORT_COMPLAINCE     		= "ROLE_EXPORT_COMPLIANCE";
    /*
     * Anonymous represents a user who is not authenticated in the system
     * and is accessing public resources/urls
     */
    public static final String ANONYMOUS_AUTH_NAME = "anonymousUser";
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    
    /* Email Placeholders */
    public static final String  PLACEHOLDER_ACTIVATION_LINK     = "ACTIVATION_LINK";
    public static final String  PLACEHOLDER_COMPANY_DOMAIN      = "COMPANY_DOMAIN";
    public static final String  PLACEHOLDER_FULL_NAME           = "FULL_NAME";
    public static final String  PLACEHOLDER_DATE                = "DATE";
    public static final String  PLACEHOLDER_RESET_LINK          = "RESET_LINK";
    public static final String  PLACEHOLDER_USERNAME            = "USERNAME";
    public static final String  PLACEHOLDER_INVITATION_LINK     = "INVITATION_LINK";
    public static final String  PLACEHOLDER_COMPANY_ID          = "COMPANY_ID";
    public static final String  PLACEHOLDER_COMPANY_TITLE       = "COMPANY_TITLE";
    public static final String  PLACEHOLDER_INVITEE_FULLNAME    = "INVITEE_FULLNAME";
    public static final String  PLACEHOLDER_REPORT_DOWNLOAD_LINK    = "REPORT_DOWNLOAD_LINK";
    public static final String  PLACEHOLDER_USER_COMMENT        = "USER_COMMENT";
    public static final String  PLACEHOLDER_PRODUCT_NAME        = "PRODUCT_NAME";
    public static final String  PLACEHOLDER_INVITED_USER_FULLNAME    = "INVITED_USER_FULLNAME";
    public static final String  PLACEHOLDER_REQUESTING_USER_FULLNAME   = "REQUESTING_USER_FULLNAME";
    public static final String  PLACEHOLDER_ACCEPTING_USER_FULLNAME   = "ACCEPTING_USER_FULLNAME";
    public static final String  PLACEHOLDER_REJECTING_USER_FULLNAME   = "REJECTING_USER_FULLNAME";
    public static final String  PLACEHOLDER_UPDATED_BY          = "UPDATED_BY";
    public static final String  PLACEHOLDER_WORKFLOW_STEP          = "WORKFLOW_STEP";
    public static final String  PLACEHOLDER_EXCEPTION_STACK_TRACE = "EXCEPTION_STACK_TRACE";
    public static final String  PLACEHOLDER_WORKITEMS_LIST      = "WORKITEMS_LIST";
    
//    public static final String  EMAIL_REGEXP_STRING = "/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$/";
    public static final String  EMAIL_REGEXP_STRING = "([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";
    public static final String  USERNAME_REGEXP_STRING = "([A-Za-z0-9._]+)";
    
    public static final String REGEX_STD_USERNAME = "([A-Za-z0-9._]+)";
    public static final String REGEX_STD_PASSWORD = "^[a-zA-Z0-9]+$";
    public static final String REGEX_STD_FULLNAME = "([A-Za-z.\\s\\-\',]+)";
    public static final String REGEX_STD_DESCIRPTION = "([A-Za-z0-9.,_\\s]+)*";
    public static final String REGEX_STD_TITLE = "([A-Za-z. ]+)";
    public static final String REGEX_STD_PHONE = "\\(?\\d\\d\\d\\)? *\\-? *\\d\\d\\d *\\-? *\\d\\d\\d\\d";
    public static final String REGEX_STD_CITY = "([A-Za-z ]+)";
    public static final String REGEX_STD_STATE = "([A-Za-z ]+)";
    public static final String REGEX_STD_ADDRESS = "([A-Za-z0-9,\\(\\)\\-_. #\\+\\{\\}\\[\\]]+)";
    public static final String REGEX_STD_EMAIL = "([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";
    public static final String REGEX_STD_EMAIL_COMMA_SEPARATED = "((" + REGEX_STD_EMAIL + ")+(," + REGEX_STD_EMAIL + ")*)?";
    public static final String REGEX_STD_ZIPCODE = "([A-Za-z0-9]+)";
    public static final String REGEX_STD_DOMAIN_NAME = "((http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ;,./?%&=]*)?)";
    public static final String REGEX_STD_TEXTFIELD = "([A-Za-z0-9 ._]+)";
    public static final String REGEX_COMPANY_NAME = "([A-Za-z0-9,\\(\\)\\-_. &]+)";
    
    public static final int REGESTRATION_PASSWORD_LENGTH = 8;
    public static final int REGESTRATION_USERNAME_LENGTH = 6;
    public static final int REGESTRATION_USERNAME_LENGTH_MAX = 25;
    public static final String  REGESTRATION_PHONE_NUMBER_FORMAT = "[\\d\\s+]*";
    public static final String  REGESTRATION_COMPANY_DOMAIN = "((http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ;,./?%&=]*)?)";
    public static final String  REGESTRATION_USERNAME = "(([A-Za-z]+)(\\.[_A-Za-z0-9-]+)*)";
    public static final String  REGESTRATION_TEXTFIELD_REGEXP = "([A-Za-z0-9 ._]+)";
    public static final String REGULAR_EXPRESSION_ZIP_CODE = "^\\d{5}(?:[-\\s]\\d{4})?$";
    public static final String REGULAR_EXPRESSION_PASSWORD = "^[a-zA-Z0-9]+$";

   public static final String PROCESS_INSTANCE_BUSINESS_KEY_NAME="processInstanceBusinessKeyName";
   
   // Default country is 'United States of America'
   public static final String DEFAULT_COUNTRY="United States of America";
   
   public static final String APP_PROPERTY_TO_EDIT_COMPANY_ON_ROLES = "redirect.to.edit.company.on.roles";
   public static final String APP_PROPERTY_TO_DELETE_COMPANY_ON_ROLES = "redirect.to.delete.company.on.roles";
   public static final String APP_PROPERTY_TO_SUSPEND_OR_UNSUSPEND_COMPANY_ON_ROLES = "suspend.or.unsuspend.company.on.roles";
   public static final String APP_PROPERTY_TO_EDIT_NOTIFICATION_ON_ROLES = "edit.notification.on.roles";

   public static final boolean SUSPEND_COMPANY = true;
   public static final boolean UNSUSPEND_COMPANY = false;
   
   public static final String ACTION_SUSPEND = "Suspend";
   public static final String ACTION_RFI = "RFI";
   public static final String ACTION_UnRFI = "UnRFI";
   
   public static final String DATE_FORMAT_MM_DD_YYYY="MM/dd/yyyy";
   
   //Application key constants
   public static final String ATTACHMENT_FILE_PATH="attachments.path";
   
   //Attachment field names
   public static final String PRODUCT_OUT_FRONT_VIEW="out_front_view";
   public static final String PRODUCT_OUT_ANGELED_VIEW="out_angeled_view";
   public static final String PRODUCT_OUT_ANOTHER_OBJECT="out_another_object";
   public static final String PRODUCT_OUT_OTHER_VIEW1="out_other_view1";
   public static final String PRODUCT_OUT_OTHER_VIEW2="out_other_view2";
   public static final String PRODUCT_OUT_OTHER_VIEW3="out_other_view3";
   public static final String PRODUCT_OUT_OTHER_VIEW4="out_other_view4";
   public static final String PRODUCT_IN_FRONT_VIEW="in_front_view";
   public static final String PRODUCT_IN_ANGELED_VIEW="in_angeled_view";
   public static final String PRODUCT_IN_ANOTHER_OBJECT="in_another_object";
   public static final String PRODUCT_SCREEN_SHOT1="screen_shot1";
   public static final String PRODUCT_SCREEN_SHOT2="screen_shot2";
   public static final String PRODUCT_SCREEN_SHOT3="screen_shot3";
   public static final String PRODUCT_LIFESTYLE_IMAGE1="lifestyle_image1";
   public static final String PRODUCT_LIFESTYLE_IMAGE2="lifestyle_image2";
   public static final String PRODUCT_LIFESTYLE_IMAGE3="lifestyle_image3";
   
   public static final String PRODUCT_PHONE_SPLASH_SCREEN="phone_splash_screen";
   public static final String PRODUCT_TABLET_SPLASH_SCREEN="tablet_splash_screen";
   public static final String PRODUCT_APPLICATION="application_icon";
   public static final String PRODUCT_PHONE_IN_APP_SCREEN="phone_in_app_screen";
   public static final String PRODUCT_TABLET_IN_APP_SCREEN="tablet_in_app_screen";

   public static final String PRODUCT_PRODUCT_VIDEO="product_video";
   public static final String PRODUCT_PHONE_APP_VIDEO="phone_app_video";
   public static final String PRODUCT_TABLET_APP_VIDEO="tablet_app_video";
   public static final String PRODUCT_PRODUCT_COPY_DOC="product_copy_doc";
   public static final String PRODUCT_APP_COPY_DOC="app_copy_doc";
   public static final String PRODUCT_LAUNCH_PRESENTATION_VIDEO="launch_presentation_video";
   
	public static final String TESTING_SPREADSHEET = "testing_spreadsheet";
	public static final String PRODUCT_LABEL = "product_label";
	public static final String SUSTAINABILITY_DISCLOSURE = "sustainability_disclosure";
	public static final String PACKAGING_LABEL = "packaging_label";
	public static final String PDF_SAMPLE_PRODUCT = "pdf_sample_product";
  
   public static final String BTN_SAVE="SAVE";
   public static final String BTN_UPDATE="UPDATE";
   public static final String BTN_SUBMIT="SUBMIT";
   public static final String BTN_APPROVE="Approve";
  
   public static final String EMAIL_TEXT_BOX_DEFAULT_TEXT="Add email notification text here...";
   public static final String COMMENT_DEFAULT_TEXT="Add new comment text here...";
   
   public static final String PRODUCT_PROCESS_SECTION_DM="DeviceMarketingReview";
   public static final String PRODUCT_PROCESS_SECTION_PIU="ProductInfoUpload";
   public static final String PRODUCT_PROCESS_SECTION_EC="ExportCompliance";
   public static final String PRODUCT_PROCESS_SECTION_ECR="ExportComplianceReview";
   public static final String PRODUCT_PROCESS_SECTION_RGR="RequirementsGroupReview";
   public static final String PRODUCT_PROCESS_SECTION_DCR="DeviceComplianceReview";
   public static final String PRODUCT_PROCESS_SECTION_DCRT="DeviceComplianceReviewEOT";
   public static final String PRODUCT_PROCESS_SECTION_PDF="UploadPDFofSampleProduct";
   public static final String PRODUCT_PROCESS_SECTION_DMA="DeviceMarketingFinalReview";
   public static final String PRODUCT_PROCESS_SECTION_WFC="Workflow History & Comments";
   
   public static final int NOTIFICATION_ID = 0;
   public static final int NOTIFICATION_TITLE = 1;
   public static final int NOTIFICATION_DESCRIPTION = 2;
   public static final int NOTIFICATION_OPTOUT_STATUS = 3;
   
   public static final int COMPANYUSER_USER_ID = 0;
   public static final int COMPANYUSER_EMAIL_ADDRESS = 1;
   public static final int COMPANYUSER_FULL_NAME = 2;
   public static final int COMPANYUSER_IS_ACTIVE = 3;
   public static final int COMPANYUSER_IS_ENABLE = 4;
   public static final int COMPANYUSER_CREATED_DATE = 5;
   public static final int COMPANYUSER_OFFER_ID = 6;
   public static final int COMPANYUSER_OFFER_TO = 7;
   public static final int COMPANYUSER_STATUS = 8;
   
   // Static resources types
   public static final String CSS_TYPE = "css";
   public static final String JS_TYPE = "js";
   public static final String FAVICON_TYPE = "favicon";
   
   // Static resources
   public static final String PRODUCT = "product";
   public static final String PAGEMODEL = "pagemodel";
   public static final String FINEUPLOAD = "fineupload";
   public static final String JQUERY = "jquery";
   public static final String JQUERY_UI = "jquery-ui";
   public static final String CKEDITOR = "ckeditor";
   public static final String STYLE = "style";
   public static final String JS_COMMON = "common";
   public static final String JS_CHARACTERS_COUNTER = "characters_counter";
   
   public static final String PRODUCT_PAGE_VIEW="VIEW";
   public static final String PRODUCT_PAGE_EDIT="EDIT";
   
   // Following used on all the pages with grid for truncating columns.
   public static final int DEFAULT_WORKLIST_WORK_ITEM_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_WORKLIST_TYPE_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_WORKLIST_COMPANY_NAME_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_WORKLIST_PRODUCT_NAME_TRUNCATION_LENGTH = 30;
   
   public static final int DEFAULT_PARTNER_EMAIL_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_PARTNER_FULL_NAME_TRUNCATION_LENGTH = 30;
   
   public static final int DEFAULT_PARTNER_MANAGE_PARTNER_NAME_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_PARTNER_MANAGE_PRTNER_POC_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_PARTNER_MANAGE_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH = 30;
   
   public static final int DEFAULT_PRODUCT_PRODUCT_NAME_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_PRODUCT_COMPANY_NAME_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_PRODUCT_PRODUCT_TYPE_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_PRODUCT_CATEGORY_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_PRODUCT_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH = 30;
   
   public static final int DEFAULT_USER_EMAIL_ADDRESS_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_USER_FULL_NAME_TRUNCATION_LENGTH = 30;
   public static final int DEFAULT_USER_USER_ROLE_TRUNCATION_LENGTH = 30;
   
   public static final int DEFAULT_NOTIFICATION_MANAGE_TITLE_TRUNCATION_LENGTH = 50;
   public static final int DEFAULT_NOTIFICATION_MANAGE_DESCRIPTION_TRUNCATION_LENGTH = 50;
   
   public static final String MY_PROFILE_UPDATE_USERNAME_SECTION="updateUsernameSection";
   public static final String MY_PROFILE_RESET_PASSWORD_SECTION="resetPasswordSection";
}
