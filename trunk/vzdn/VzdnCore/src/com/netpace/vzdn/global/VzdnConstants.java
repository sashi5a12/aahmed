package com.netpace.vzdn.global;

import java.text.MessageFormat;
import java.util.HashMap;

public class VzdnConstants {	
	
	public static final String LOGGED_IN_USER="loggedInUser";
	
	//User Types
	public static final int DeveloperUser = 1;
	public static final int VerizonUser = 2;
	
	//User Statusses	
	public static final int ACTIVE_STATUS = 5;
	public static final int INACTIVE_STATUS = 6;
	
	
	//Menu Types
	public static final int MyInfoMenus = 1;
	public static final int CommunityMenus = 2;
	public static final int GoToMarketMenus = 3;
	public static final int ManagementMenu = 5;	
	
	//Default Vzdn Core role
	public static final int HIDDEN_ROLE = 4;
	public static final int DEFAULT_VZDN_CORE_ROLE = 6;
	public static final int ROLE_DEVELOPER = 1;
	public static final int ROLE_VERIZON = 2;
	public static final int ROLE_BOTH = 3;
	
	//ldap-opensso-http header attributes
	//public static final String OPENSSO_ATTRIBUTE_FIRST_NAME ="firstName";
	//public static final String OPENSSO_ATTRIBUTE_LAST_NAME ="lastName";	
	
	//The following two attributes are used to get info directly from ldap.
	public static final String OPENSSO_ATTRIBUTE_FIRST_NAME ="givenname";
	public static final String OPENSSO_ATTRIBUTE_LAST_NAME ="sn";
	
	//The following two attributes are used to get info from the request header of the user.
	public static final String OPENSSO_ATTRIBUTE_FIRST_NAME_RH = "firstName";
	public static final String OPENSSO_ATTRIBUTE_LAST_NAME_RH = "lastName";
	public static final String OPENSSO_ATTRIBUTE_COMP_ID_RH = "gtm_company_id";
	
	public static final String OPENSSO_ATTRIBUTE_COMP_ID = "vzdn-dev-organization-id";
	public static final String OPENSSO_ATTRIBUTE_USER_ROLES ="vzdn-user-roles";
	public static final String OPENSSO_ATTRIBUTE_USER_TYPE ="vzdn-user-type";
	public static final String OPENSSO_ATTRIBUTE_PARTNER_ORGANIZATION ="vzdn-partner-organization";
	public static final String OPENSSO_ATTRIBUTE_USER_STATUS ="inetuserstatus";	
	public static final String OPENSSO_ATTRIBUTE_UI = "uid";
	//public static final String OPENSSO_ATTRIBUTE_COMP_ID = "gtm_company_id";
	
	
	
	public static final String OPENSSO_ATTRIBUTE_USER_TITLE = "vzdn-title";
	public static final String OPENSSO_ATTRIBUTE_USER_PHONE = "vzdn-phone-number";
	public static final String OPENSSO_ATTRIBUTE_USER_FAX = "vzdn-fax-number";
	public static final String OPENSSO_ATTRIBUTE_USER_MOBILE = "vzdn-mobile-number";
	public static final String OPENSSO_ATTRIBUTE_USER_COUNTRY =  "vzdn-country";
	
	
	//Attribute used to set and get user's privileges hashbtable in the session
	public static final String VZDN_USER_PRIVILEGES = "USER_PRIVILEGES";
	
	
	//User Privileges Keys
	public static final String EDIT_PROFILE = "EDIT_PROFILE";
	public static final String MY_NOTIFICATIONS = "MY_NOTIFICATIONS";
	public static final String BLOG_LINK_FOR_ADMIN = "BLOG_LINK_FOR_ADMIN";
	public static final String FORUM_LINK_FOR_ADMIN = "FORUM_LINK_FOR_ADMIN";
	public static final String APPLICATIONS = "APPLICATIONS";
	public static final String ALLIANCES = "ALLIANCES";
	public static final String MANAGE_NOTIFICATIONS = "MANAGE_NOTIFICATIONS";
	public static final String PUBLISH_CONTENT = "PUBLISH_CONTENT";
	public static final String MANAGE_USERS = "MANAGE_USERS";
	public static final String MY_APPLICATION_FOR_DEV = "MY_APPLICATION_FOR_DEV";
	public static final String MY_CONTRACTS_FOR_DEV = "MY_CONTRACTS_FOR_DEV";
	public static final String CONTRACTS_PAGE_FOR_VZ_USER = "CONTRACTS_PAGE_FOR_VZ_USER";
	public static final String BLOG_LINK_FOR_DEVELOPER = "BLOG_LINK_FOR_DEVELOPER";
	public static final String FORUM_LINK_FOR_DEVELOPER = "FORUM_LINK_FOR_DEVELOPER";
	public static final String MANAGER_NEWSLETTER = "MANAGER_NEWSLETTER";
	public static final String ROLE_REPORT = "ROLE_REPORT";
	public static final String USER_REPORT = "USER_REPORT";
	public static final String DEV_REPORT = "DEV_REPORT";
	public static final String REPORT_VIEWER = "REPORT_VIEWER";
	
	//General Searching Related Parameters
	public static final String FORUM_PRE_SEARCH_STR = "/forum/jforum.page?module=search&action=search&search_keywords=";
	public static final String FORUM_POST_SEARCH_STR = "&match_type=all&search_forum=&sort_by=relevance";	
	public static final String BLOG_PRE_SEARCH_STR = "/blogs/blogs/main/search?q=";
	
	//Regular Expression for email
	public static final String EMAIL_REG_EXPR = ".+@.+\\.[a-z]+";
	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
	
	//Notification Field Lengths
	public static final int EMAIL_TITLE_LENGHT = 200;
	
	public static final int EMAIL_DESC_LENGHT = 1000;
	
	public static final int EMAIL_CONT_LENGHT = 3500;
	
	public static final String OPENSSO_COOKIE = "iPlanetDirectoryPro";
	
	//Results Types
	public static final String RESULT_LOGIN_FIRST = "login-first";
	
	public static final char[] INVALID_EMAIL_SEPARATORS = {';',':','\'','\"','[',']','{','}','=',
															'(',')','*','&','^','%','$','#','!',
															'~','`','<','>','?','/','|','\\'
														};
	public static final float FILE_SIZE_DIVIDER = 1024*1024;
	
	
    //Error Messages of Oracle
    public static MessageFormat UNIQUE_CONSTRAINT_MESSAGE = new MessageFormat("{0}) violated");
    public static MessageFormat UNIQUE_CONSTRAINT_MESSAGE_DB = new MessageFormat("ORA-00001: unique constraint ({0}.{1}) violated");
    public static MessageFormat INTEGRITY_CONSTRAINT_MESSAGE = new MessageFormat("ORA-02292: integrity constraint ({0}) violated - child record found");
    public static String INTEGRITY_CONSTRAINT_MESSAGE_PREFIX = "ORA-02292: integrity constraint";
    public static String INTEGRITY_CONSTRAINT_RES_MESSAGE = "masters.integrity.constraint.violation";
    public static String ERROR_GENERIC_DATABASE_RES_MESSAGE = "error.generic.database";

    //Email Address of Admins
    public static String EMAIL_EXCEPTION_ADMIN = "vzwtech@netpace.com";

    //Email Subjects
    public static String EMAIL_SUBJECT_RARE_EXCEPTION = "RARE EXCEPTION"; //"Exception Should Not Occur Here" Case

    //Common Constants
    public static String ALLIANCEADMIN_ROLENAME = "Primary User";
    public static Long SECONDARY_USERS_ROLE_ID = new Long(131);
    public static Long VZDN_MAPPING_SECONDARY_USERS_ROLE_ID = new Long(2025);
    public static String ALLIANCE_USERTYPE = "A";
    public static String VZW_USERTYPE = "V";
    public static String ALLIANCEADMIN_ROLETYPE = "AA";
    public static String VZWADMIN_ROLETYPE = "VA";
    public static String RECORD_CREATED_BY_SYSTEM = "system";
    public static String RECORD_UPDATED_BY_SYSTEM = "system";
    public static String NON_NULLABLE_FIELD_STRING = "-";
    public static String NON_NULLABLE_FIELD_NUMERIC = "0";

    public static String ACTIVE="Active";
    public static String IN_ACTIVE="InActive";
    public static final String ACTIVE_CHAR = "Y";
    public static final String INACTIVE_CHAR = "N";

    public static final String YES_CHAR = "Y";
    public static final String NO_CHAR = "N";

    public static final String NOT_APPLICABLE_VALUE = "N/A";

    public static final String XML_DIR = "/schema";

    //Common Tasks
    public static final String CREATE_TASK = "create";
    public static final String DELETE_TASK = "delete";
    public static final String EDIT_TASK = "edit";
    public static final String VIEW_TASK = "view";

    //Device Related Constants
    public static Long NON_EXISTANT_DEVICE_ID = new Long(0);

    //Misc File Names
    public static final String MISC_IMAGE_NOT_AVAILABLE = "not-available.jpg";

    //File Types
    public static String FILE_TYPE_IMAGE[] = { "gif", "jpg", "tif", "png", "wmf", "avi" };
    public static String FILE_TYPE_DOC[] = { "doc", "pdf", "xls" };
    public static String FILE_TYPE_XLS[] = { "xls" };
    public static String FILE_TYPE_EPS[] = { "eps", "avi" };
    public static String FILE_TYPE_VIDEO_CLIP[] = { "avi", "3g2", "wmv", "mpg", "mpeg" };
    public static String FILE_TYPE_EPS_ONLY[] = { "eps" };
    public static String FILE_TYPE_JPEG[] = { "jpg" };
    public static String FILE_TYPE_JPEG_PNG[] = { "jpg", "png" };
    public static String FILE_TYPE_GIF_ONLY[] = { "gif" };
    public static String FILE_TYPE_FLASH[] = { "swf", "avi" };
    public static String FILE_TYPE_FLASH_ONLY[] = { "swf" };
    public static String FILE_TYPE_PRESENTATION[] = { "ppt" };
    public static String FILE_TYPE_SCREEN_SHOT[] = { "gif", "jpg", "avi" };
    public static String FILE_TYPE_WAP_FTP_IMAGES[] = { "gif", "jpg" };
    public static String FILE_TYPE_BREW_USER_GUIDE[] = { "template.doc" };
    public static String FILE_TYPE_BREW_PROG_GUIDE[] = { "pgwinter04.ppt" };
    public static String FILE_TYPE_BINARY[] = { "cab", "jar" };
    public static String FILE_TYPE_ZIP[] = { "zip" };
    public static String FILE_TYPE_ICON[] = { "gif", "jpg"};
    public static String FILE_TYPE_DASHBOARD_USER_GUIDE[] = { "template.doc" };
	public static String FILE_TYPE_PREVIEW_IMAGE[] = { "gif", "jpg", "png" };
	public static String FILE_TYPE_HTML_ONLY[] = { "html","htm" };

    //File Size Constants
    public static String FILE_SIZE = "FILE_SIZE";
    public static String IMAGE_FILE_SIZE = "IMAGE_FILE_SIZE";
    public static String DOC_FILE_SIZE = "DOC_FILE_SIZE";
    public static String EPS_FILE_SIZE = "EPS_FILE_SIZE";
    public static String JPEG_FILE_SIZE = "JPG_FILE_SIZE";
    public static String FLASH_FILE_SIZE = "SWF_FILE_SIZE";
    public static String FLASH_DEMO_FILE_SIZE = "FLASH_DEMO_SWF_FILE_SIZE";
    public static String PRESENTATION_FILE_SIZE = "IMAGE_FILE_SIZE";
    public static String SCREEN_SHOT_FILE_SIZE = "IMAGE_FILE_SIZE";
    public static String FILE_MSG = "FILE_MESSAGE";
    public static String STATUS_MSG = "STATUS_MESSAGE";

    //Type of Contacts Constants
    public static final String CONTACT_TYPE_ALLIANCE_CONTACT = "AC";
    public static final String CONTACT_TITLE_NEW = "New Title";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String DATE_FORMAT_INFOSPACE = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

    //Journal Constants
    public static String JOURNAL_TYPE_PRIVATE = "PR";
    public static String JOURNAL_TYPE_PUBLIC = "PU";

    //Platform Constants
    public static Long BREW_PLATFORM_ID = new Long(1);
    public static Long SMS_PLATFORM_ID = new Long(2);
    public static Long MMS_PLATFORM_ID = new Long(3);
    public static Long WAP_PLATFORM_ID = new Long(4);
    public static Long ENTERPRISE_PLATFORM_ID = new Long(5);
    public static Long VCAST_PLATFORM_ID = new Long(6);
    public static Long DASHBOARD_PLATFORM_ID = new Long(43);
    public static Long VZAPPZONE_PLATFORM_ID = new Long(42);
    public static Long JAVA_PLATFORM_ID = new Long(44);
    
    public static final Long CONTRACT_RING_2_ID = new Long(172); //  ONDECK
    public static final Long CONTRACT_RING_3_ID = new Long(173); //  OFFDECK
    
    //Platform names
    public static String VZAPPZONE_PLATFORM_NAME = "VZAppZone";

    //LifeCycle Phases Constants
    public static Long SUBMISSION_ID = new Long(1);
    public static Long SAVED_ID = new Long(8);
    //public static Long TESTING_ID = new Long(5);
    public static Long TESTING_ID = new Long(6);
    public static Long ASSIGNED_ID = new Long(3);
    public static Long CERTIFICATION_ID = new Long(4);
    public static Long ACCEPTANCE_ID = new Long(7);
    public static Long REJECTED_ID = new Long(9);
    public static Long EVALUATED_ID = new Long(10);
    public static Long SUNSET_ID = new Long(11);
    public static Long CONCEPT_ACCEPTED_ID = new Long(12);
    public static Long CONCEPT_REJECTED_ID = new Long(13);
    public static Long PHASE_INITIAL_APPROVAL_ID = new Long(14);
    public static Long PHASE_INITIAL_DENIED_ID = new Long(15);
    public static Long PHASE_BUSINESS_APPROVAL_GRANTED_ID = new Long(16);
    public static Long PHASE_BUSINESS_APPROVAL_DENIED_ID = new Long(17);
    public static Long PHASE_PENDING_DCR_ID = new Long(18);
    public static Long PHASE_PENDING_ARM_ID = new Long(19);
    public static Long PHASE_SUBMITTED_DCR_ID = new Long(20);
    public static Long PHASE_TESTING_PASSED_ID = new Long(21);
    public static Long PHASE_TESTING_FAILED_ID = new Long(22);
    public static Long PHASE_PUBLICATION_READY_ID = new Long(23);
    public static Long PHASE_COMPLETED_IN_PRODUCTION_ID = new Long(24);

    //VZW Testing Phases Constants
    public static Long TESTING_WAP_TD_REVIEW_ID = new Long(25);
    public static Long TESTING_WAP_PD_REVIEW_ID = new Long(26);

    public static Long PHASE_IN_PRODUCTION_ID = new Long(28);
    public static Long PHASE_TEST_PASSED_ID = new Long(29);
    public static Long PHASE_OTA_TEST_PASSED_ID = new Long(31);    

    //For Dashboard
    public static final Long PHASE_INITIAL_REJECTED=new Long(34);
    public static final Long PHASE_CONTENT_IN_REVIEW=new Long(35);
    public static final Long PHASE_CONTENT_APPROVED=new Long(36);
    public static final Long PHASE_CONTENT_REJECTED=new Long(37);
    public static final Long PHASE_PENDING_PRODUCTION=new Long(38);
    public static final Long PHASE_CHANNEL_REJECTED=new Long(39);
    public static final Long PHASE_IN_PRODUCTION=new Long(40);
    public static final Long PHASE_DASHBOARD_INITIAL_APPROVAL=new Long(41);
    
    //Brew phase constants
    public static final Long PHASE_UNDER_REVIEW=new Long(42);
    public static final Long PHASE_REJECTED=new Long(43);
    
    //Java life Phase Constants
	public static Long PHASE_JAVA_SUBMITTED =  new Long(1);
    public static Long PHASE_JAVA_RFI_CONTENT_PROG =  new Long(2002);
	public static Long PHASE_JAVA_CONTENT_APPROVED =  new Long(2003);
	public static Long PHASE_JAVA_RFI_LEGAL_CONTENT =  new Long(2004);
	public static Long PHASE_JAVA_LEGAL_APPROVED =  new Long(2005);
	public static Long PHASE_JAVA_RFI_TAX_REVIEW =  new Long(2006);
	public static Long PHASE_JAVA_PENDING_TAX_APPROVAL =  new Long(2007);
	public static Long PHASE_JAVA_REJECTED =  new Long(2008);
    public static Long PHASE_JAVA_APPROVED = new Long(2009);	
    public static String AIMS_JAVA_RESUBMIT = "resubmit";
	
    public static Long JAVA_CONTENT_TYPE_APPLICATION =  new Long(301);
    public static Long JAVA_CONTENT_TYPE_GAME = new Long(302);
    
    //Region Constants
    public static Long REGION_MIDWEST_ID = new Long(21);
    public static Long REGION_NORTHEAST_ID = new Long(22);
    public static Long REGION_SOUTH_ID = new Long(23);
    public static Long REGION_WEST_ID = new Long(24);
    public static Long REGION_NATIONAL_ID = new Long(25);

    public static final String CERTIFICATION = "CERTIFICATION";
    public static final String TESTING = "TESTING";
    public static final String ACCEPTANCE = "ACCEPTANCE";

    //Alliance Status Constants
    public static String ALLIANCE_STATUS_ACCEPTED = "A";
    public static String ALLIANCE_STATUS_REJECTED = "R";
    public static String ALLIANCE_STATUS_SUBMITTED = "S";
    public static String ALLIANCE_STATUS_REVIEWED = "U";
    public static String ALLIANCE_STATUS_DELETED = "D";

    // Spotlight Document Status Constants
    public static String SPOTLIGHT_DOCUMENT_STATUS_ACCEPTED = "ACCEPT";
    public static String SPOTLIGHT_DOCUMENT_STATUS_REJECTED = "REJECT";

    //User Status Constants
    public static String USER_STATUS_ACTIVE = "ACTIVE";
    public static String USER_STATUS_ONHOLD = "ONHOLD";
    public static String USER_STATUS_DELETED = "DELETED";

    //Application Netowork usage constants
    public static String AIMS_APP_ENABLE_NETWORK_USAGE = "Y";
    public static String AIMS_APP_DISABLE_NETWORK_USAGE = "N";

    //Brew Application Type Constants
    public static String BREW_APP_TYPE_NEW[] = { "N", "New VZW App" };
    public static String BREW_APP_TYPE_EXISTING[] = { "E", "Existing VZW App" };
    public static String BREW_APP_TYPE_CONCEPT[] = { "C", "Concept" };
    public static String BREW_APP_CHECKBOX_IS_LBS[] = { "Y", "Is LBS Application?" };
    public static final int BREW_USER_GUIDE_FIELD_LEN = 1000;

    //Java Application Type Constants
    public static String JAVA_APP_TYPE_NEW[] = { "N", "New VZW App" };
    public static String jAVA_APP_TYPE_EXISTING[] = { "E", "Existing VZW App" };
    public static String jAVA_APP_TYPE_CONCEPT[] = { "C", "Concept" };
    public static String JAVA_APP_CHECKBOX_IS_LBS[] = { "Y", "Is LBS Application?" };
    public static final int JAVA_USER_GUIDE_FIELD_LEN = 1000;
    
    //Brew Evaluation Constants
    public static String BREW_EVALUATION_ACCEPTED_FEATURED[] = { "F", "Accepted Featured" };
    public static String BREW_EVALUATION_ACCEPTED_GENERAL[] = { "G", "Accepted General" };
    public static String BREW_EVALUATION_NOT_ACCEPTED[] = { "N", "Not Accepted" };
    public static String BREW_EVALUATION_SUNSET[] = { "S", "Sunset" };

    //Wap Application Content Type Constants && Wap Application Radio Button Constants
    public static String WAP_APP_CONTENT_TYPE_STANDARD[] = { "S", "Standard" };
    public static String WAP_APP_CONTENT_TYPE_PREMIUM[] = { "P", "Premium" };
    public static String WAP_APP_VERSION_WAP_1_0[] = { "1.0", "Wap 1.0" };
    public static String WAP_APP_VERSION_WAP_2_0[] = { "2.0", "Wap 2.0" };
    public static String WAP_APP_RADIO_TEST_PASSED[] = { "P", "Passed" };
    public static String WAP_APP_RADIO_TEST_FAILED[] = { "F", "Failed" };
    public static String WAP_APP_RADIO_INITIAL_APPROVE[] = { "A", "Approve" };
    public static String WAP_APP_RADIO_INITIAL_DENY[] = { "D", "Deny" };
    public static String WAP_APP_RADIO_BUSINESS_OK[] = { "O", "OK" };
    public static String WAP_APP_RADIO_BUSINESS_ARM[] = { "A", "ARM" };
    public static String WAP_APP_RADIO_BUSINESS_DENY[] = { "D", "Deny" };
    public static String WAP_APP_CHECKBOX_PENDING_DCR[] = { "Y", "Move the application to 'Pending DCR' status" };
    public static String WAP_APP_CHECKBOX_COMPLETED_IN_PRODUCTION[] = { "Y", "Move the application to 'Completed - In Production' status" };
    public static String WAP_APP_CHECKBOX_SUNSET[] = { "Y", "Move the application to 'Sunset' status" };

    //VZAppZone application related constants
    public static String VZAPPZONE_APP_RADIO_INITIAL_APPROVE[] = { "A", "Approve" };
    public static String VZAPPZONE_APP_RADIO_INITIAL_DENY[] = { "D", "Deny" };
    public static String VZAPPZONE_APP_RADIO_TEST_PASSED[] = { "P", "Passed" };
    public static String VZAPPZONE_APP_RADIO_TEST_FAILED[] = { "F", "Failed" };
    public static String VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[] = { "P", "Passed" };
    public static String VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[] = { "F", "Failed" };
    public static String VZAPPZONE_APP_CHECKBOX_MOVE_TO_STAGING[] = { "Y", "Move to Staging" };
    public static String VZAPPZONE_APP_LOCK_APPLICATION[] = { "Y", "Lock Application" };
    public static String VZAPPZONE_APP_CHECKBOX_SUNSET[] = { "Y", "Sunset" };
    public static final String INTERTEK_KEY_FOR_RESUBMIT_URL = "Do Not Change - 32970 is the Password.";
    public static final String MPORTAL_KEY_FOR_RESUBMIT_URL = "Do Not Change - 32970 is the MPORTAL Password.";
    public static final String SECRET_KEY_FOR_BINARY_DOWNLOAD= "BINARY_DOWNLOAD_SECRET_KEY";
    
    //VZAppZone Binary Status
    public static final Long VZAPPZONE_BINARY_STATUS_SUBMITTED = new Long(17);
    public static final Long VZAPPZONE_BINARY_STATUS_UNDER_TESTING = new Long(18);
    public static final Long VZAPPZONE_BINARY_STATUS_TEST_FAILED = new Long(19);
    public static final Long VZAPPZONE_BINARY_STATUS_TEST_PASSED = new Long(20);
    public static final Long VZAPPZONE_BINARY_STATUS_IN_OTA_TESTING = new Long(21);
    public static final Long VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED = new Long(22);
    public static final Long VZAPPZONE_BINARY_STATUS_OTA_TEST_PASSED = new Long(23);
    public static final Long VZAPPZONE_BINARY_STATUS_IN_PRODUCTION = new Long(25);


    //VCAST Application Related Constants
    public static Long VCAST_DEFAULT_ENGLISH_LANGUAGE = new Long(1);

    //Dashboard Constants
    public static final String DASHBOARD_APP_RADIO_APPROVE[] = { "A", "Approve" };
    public static final String DASHBOARD_APP_RADIO_DENY[] = { "D", "Deny" };
    public static final String DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[] = { "Y", "Lock Content Zip File" };
    public static final String DASHBOARD_APP_RADIO_ACCEPT[] = { "A", "Accept" };
    public static final String DASHBOARD_APP_RADIO_REJECT[] = { "R", "Reject" };
    public static final String DASHBOARD_APP_CHECKBOX_REMOVE[] = { "Y", "Remove" };
    public static final String DASHBOARD_APP_RADIO_CHANNEL_TYPE_BASIC[] = { "B", "Basic" };
    public static final String DASHBOARD_APP_RADIO_CHANNEL_TYPE_PREMIUM[] = { "P", "Premium" };
    public static final String DASHBOARD_XML_FILE="/Dashboard.xml";
    /**/ 
    public static final String[]DASHBOARD_INVALID_CHARS=new String[]{"~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", ",", "=", "'", ".", "?", "|", ":", "\\", "/", "\"", "<", ">"};
    
    public static final int DASHBOARD_USER_GUIDE_FIELDS_LENGTH=1000;
    public static final int JAVA_USER_GUIDE_FIELDS_LENGTH=1000;

    //Workflow
    
    public static final String WORKFLOW_TAKEN_ACTION_REQUEST_FOR_INFORMATION = "Request For Information (RFI)";
    public static final String WORKFLOW_TAKEN_ACTION_RESUBMIT = "Resubmit";
    
    public static final String WORKFLOW_STEPNAME_APPROVED = "Approved";
    public static final String WORKFLOW_STEPNAME_DENIED = "Denied";
    
    public static final String WORKFLOW_STARTED="Started";
    public static final Long WORKFLOW_BREW_APP=new Long(1);

    public static final Long WORKFLOW_JAVA_ONDECK_APP=new Long(8);
    public static final Long WORKFLOW_JAVA_OFFDECK_APP=new Long(10);

    public static final String WORKFLOW_UNDERWAY="Underway";
    public static final String WORKFLOW_COMPLETED="Completed";
    public static final String WORKFLOW_NO_ACTION_TAKEN="No Action Taken";
    public static final String WORKFLOW_ABANDON="Abandon";
    
    //VZW Testing Phases
    public static Long TECHNICAL_PHASE_ID = new Long(1);
    public static Long BIZDEV_PHASE_ID = new Long(2);
    public static Long LEGAL_PHASE_ID = new Long(3);
    public static Long VP_DATA_PHASE_ID = new Long(4);

    public static final String TECHNICAL_PHASE = "BREW Technical Review";
    public static final String BIZDEV_PHASE = "BREW BizDev Review";
    public static final String LEGAL_PHASE = "BREW Legal Review";
    public static final String VP_DATA_PHASE = "BREW VP Data Review";

    //GIN Report Constants
    public static final String HANDSET = "CDM 8900";
    public static final String PART_NUMBER = "240-568-1805";

    //Form Management Constants
    public static final String AIMS_SAVE_FORM = "save";
    public static final String AIMS_SUBMIT_FORM = "submit";
    public static final String AIMS_SAVE_FORM_AFTER_SUBMIT = "saveAfterSubmit";
    public static final String AIMS_VZW_SAVE_FORM = "vzwsave";
    public static final String AIMS_VZW_PROCESS_FORM = "vzwprocess";
    public static final String AIMS_VZW_ACCEPT_FORM = "accept";
    public static final String AIMS_VZW_REJECT_FORM = "reject";
    public static final String AIMS_EXECUTE_ACTION = "executeAction";

    // Login Constants
    public static final String AIMS_USER = "AIMS_USER";

    public static final String AIMS_MENU = "AIMS_MENU";

    public static final String AIMS_SUB_MENU = "AIMS_SUB_MENU";

    public static final String AIMS_PRIVILEGES = "AIMS_PRIVILEGES";

    public static final String AIMS_PROV_APPS_PRIVILEGE = "PROVISIONED_APPS";

    public static final String AIMS_ONLY_PROV_APPS = "ONLY_PROV_APPS";

    //Message Constants
    public static final String NEW_MESSAGE = "newMail";
    public static final String SHOW_RECIPIENTS = "showRecipients";
    public static final String NEWMAIL = "newMail";
    public static final String SENDMAIL = "sendMail";
    public static final String REPLYMAIL = "replyMail";
    public static final String FWDMAIL = "fwdMail";
    public static final String REPLY_ALL_MAIL = "replyAllMail";

    // Contract status constants

    public static final String CONTRACT_ACCEPTED = "ACCEPTED";
    public static final String CONTRACT_DECLINED = "DECLINED";
    public static final String CONTRACT_REQUEST_CHANGE = "REQUEST_CHANGE";
    public static final String CONTRACT_OFFERED = "OFFERED";

    public static final String NEW_CASE_STUDY = "NEW";

    public static final String SPOTLIGHT_CASE_STUDY = "1";
    public static final String SPOTLIGHT_SALES_PRESENTATION = "2";
    public static final String SPOTLIGHT_SALES_COLLATERAL = "3";
    public static final String SPOTLIGHT_TESTIMONY = "4";
    public static final String SPOTLIGHT_WHITE_PAPER = "5";
    public static final String SPOTLIGHT_SUCCESS_STORY = "6";
    public static final String SPOTLIGHT_DEMO = "7";
    public static final String SPOTLIGHT_SALES_PARTNERS = "8";
    public static final String SPOTLIGHT_QRG = "9";

    public static final int DEVICE_ON_LOAN_PAGE_SIZE = 10;
    
    public static final String GLOBAL_NAV_HAEDER="var_global_nav_head";
    public static final String SESSION_FIRST_NAME="firstName";
    public static final String SESSION_LAST_NAME="lastName";
    public static final String COOKIE_EMAIL_ADDRESS="cookieEmailAddress";

    public static final String VZDN_VERIZON_USER_TYPE="Verizon";
    public static final String VZDN_DEVELOPER_USER_TYPE="Developer";
    
    public static final String VZDN_PERMANENT_DELETE="Y";
    
    // Device On Loan Constants
    public static final String DEVICE_ACTIVE = "ACTIVE";

    //Alliance Registration Constants
    public static String AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS[] =
        {
            "AIMS.AK_USERS_USERNAME",
            "error.alliance_registration.user_name.exists",
            "AIMS.AK_ALLIANCES_COMPANY_NAME",
            "error.alliance_registration.company_name.exists" };

    //error.alliance.compinfoform.unique.companyName

    //Alliance Company Information Constants
    public static String AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[] =
        {
            "AIMS.AK_ALLIANCES_COMPANY_NAME",
            "error.alliance.compinfoform.unique.companyName",
            "AIMS.AK_ALLIANCES_COMP_LEGAL_NAME",
            "error.alliance.compinfoform.unique.companyLegalName" };

    //Alliance Company Information Constants
    public static String AIMS_ALLIANCE_EMAIL_GROUP_UNIQUE_KEYS[] = { "AIMS.AK_EMAIL_GROUPS_GROUP_TITLE", "error.emailgroup.unique.groupTitle" };

    public static String[] comparisonOperators = { "=", "Like", "!=", ">", "<" };
    public static final String recordLimit = "200";

    public static final String SHOW_POPUP = "SHOW_POPUP";
    public static final String SHOW_POPUP_ENABLE = "TRUE";
    public static final String POPUP_VZW_LOCATION = "/html/export/popup_vzw.html";
    public static final String POPUP_ALLIANCE_LOCATION = "/html/export/popup_alliance.html";

    public static final String MKT_APPLICATION_PENDING = "PENDING";
    public static final String MKT_APPLICATION_ACCEPTED = "ACCEPTED";
    public static final String MKT_APPLICATION_REJECTED = "REJECTED";
    public static final String MKT_REQUEST_SAVED = "SAVED";
    public static final String MKT_REQUEST_ACCEPTED = "ACCEPTED";
    public static final String MKT_REQUEST_DECLINED = "DECLINED";
    public static final String MKT_REQUEST_PARTIAL_APPROVAL = "PARTIAL APPROVAL";
    public static final String MKT_REQUEST_IN_PROGRESS = "IN PROGRESS";

    public static final String CP_MARKETING_CONTENT_STATUS_NEW = "NEW";
    public static final String CP_MARKETING_CONTENT_STATUS_SAVED = "SAVED";
    public static final String CP_MARKETING_CONTENT_STATUS_SUBMITTED = "SUBMITTED";
    public static final String CP_MARKETING_CONTENT_STATUS_APPROVED = "APPROVED";
    public static final String CP_MARKETING_CONTENT_STATUS_REJECTED = "REJECTED";
    public static String CP_MARKETING_CONTENT_USAGE_TYPE_GENERAL[] = { "G", "General Usage" };
    public static String CP_MARKETING_CONTENT_USAGE_TYPE_EVENT[] = { "E", "Event Based  " };

    //Device On Loan Constants
    public static String DEVICE_ON_LOAN_STATUS_OPEN = "Open";
    public static String DEVICE_ON_LOAN_STATUS_CLOSED = "Closed";

    //InfoSpace Related Constants
    public static final String INFOSPACE_XSD_URL = "/schema/DCR.xsd";
    public static final MessageFormat INFOSPACE_RESOURCE_URL = new MessageFormat("http://{0}/aims/infoSpaceResource.do");
    public static final MessageFormat INFOSPACE_RESUBMIT_URL = new MessageFormat("http://{0}/aims/infoSpaceResubmit.do");
    public static final String INFOSPACE_KEY_DIGEST_FOR_URLS = "Do Not Change - 32970 is the Password.";

    //Bulletin Types
    public static final String BULLETIN_TYPE_BREW = "1";
    public static final String BULLETIN_TYPE_ENTERPRISE = "2";

    //Session Related Constants
    public static final String SESSION_BULLETIN_TO_READ = "SESSION_BULLETIN_TO_READ";
    public static final String SESSION_BULLETIN_IDS_TO_READ = "SESSION_BULLETIN_IDS_TO_READ";

    public static final String SESSION_LOGIN_CONTENT_TO_SHOW = "SESSION_LOGIN_CONTENT_TO_SHOW";
    public static final String SESSION_LOGIN_CONTENT_IDS_TO_SHOW = "SESSION_LOGIN_CONTENT_IDS_TO_SHOW";
    
    public static final String SESSION_CONTENT_ZIP_FILE_TMP_LOCATION="SESSION_CONTENT_ZIP_FILE_TMP_LOCATION";
    public static final String SESSION_FTPS_SEND_TEST_FILE="SESSION_FTPS_SEND_TEST_FILE";

    public static final String SESSION_SHOW_PAGE_AFTER_LOGIN = "SHOW_PAGE_AFTER_LOGIN";

    //Request Related Constants
    public static final String REQUEST_BULLETIN_TEXT = "REQUEST_BULLETIN_TEXT";
    public static final String REQUEST_BULLETIN_HEADER = "REQUEST_BULLETIN_HEADER";
    public static final String REQUEST_BULLETIN_POST_ACTION = "REQUEST_BULLETIN_POST_ACTION";
    public static final String REQUEST_BULLETIN_ERROR_MESSAGE = "REQUEST_BULLETIN_ERROR_MESSAGE";
    public static final String REQUEST_SHOW_BULLETIN_ERROR = "REQUEST_SHOW_BULLETIN_ERROR";
    public static final String REQUEST_BULLETIN_SUCCESS_MESSAGE = "REQUEST_BULLETIN_SUCCESS_MESSAGE";
    public static final String REQUEST_SHOW_BULLETIN_SUCCESS = "REQUEST_SHOW_BULLETIN_SUCCESS";

    public static final String REQUEST_LOGIN_CONTENT_ERROR_MESSAGE = "REQUEST_LOGIN_CONTENT_ERROR_MESSAGE";
    public static final String REQUEST_SHOW_LOGIN_CONTENT_ERROR = "REQUEST_SHOW_LOGIN_CONTENT_ERROR";

    //Autodesk Related Constants
    public static final String AUTODESK_PROV_STAGE_NETPACE_ID = "100000107";
    public static final String AUTODESK_PROV_STAGE_NETPACE_PASSWORD = "93B83F18BEAC3328D2130C77147D666D";
    public static final String AUTODESK_PROV_PROD_NETPACE_ID = "10190056";
    public static final String AUTODESK_PROV_PROD_NETPACE_PASSWORD = "83B84F18BEAC3218C2130D77127D666D";
    public static final String AUTODESK_SUBMIT_STAGE_URL = "http://vzwlpsstg.autodesk.com/llwsg/clientprov";
    public static final String AUTODESK_SUBMIT_PROD_URL = "https://lpsweb.vzwlbs.net/llwsg/clientprov";
    public static final String AUTODESK_XML_DIR = "/schema";
    public static final MessageFormat AUTODESK_RESUBMIT_URL = new MessageFormat("http://{0}/aims/autodeskResubmit.do");
    public static final String AUTODESK_KEY_DIGEST_FOR_URLS = "Do Not Change - Niles is the Password.";
    public static String LBS_APP_TYPE_ACTIVE[] = { "A", "Active" };
    public static String LBS_APP_TYPE_PASSIVE[] = { "P", "Passive" };
    public static String LBS_APP_TYPE_BOTH[] = { "B", "Both" };

    //Autodesk Related Status
    public static Long LBS_STATUS_NOT_SUBMITTED = new Long(1);
    public static Long LBS_STATUS_STAGING = new Long(2);
    public static Long LBS_STATUS_PRODUCTION = new Long(3);
    public static Long LBS_STATUS_DEPROVISIONED_ON_STAGE = new Long(4);
    public static Long LBS_STATUS_DEPROVISIONED = new Long(5);

    //LBS Geo Services
    public static String LBS_GEO_SERV_INITIATED_FROM_MOBILE = "Mobile Initiated";
    public static String LBS_GEO_SERV_INITIATED_FROM_NETWORK = "Network Initiated";

    //Rest Module Names
    public static final String REST_MODULE_WAP_FTP = "wapAckFTP";
    public static final String REST_MODULE_VZAPPZONE_ACK = "vzAppZoneAckDevicePayment";

    //WAP FTP Module Status
    public static final String WAP_FTP_STATUS_PENDING = "P";
    public static final String WAP_FTP_STATUS_SUCCESS = "S";
    public static final String WAP_FTP_STATUS_FAILURE = "F";

    public static final String VERSION_PATTERN = "(\\d)+(\\.(\\d+))*";
    
    //Contact Status Constant
    public static final String CONTRACT_STATUS_ACTIVE="A";
    public static final String CONTRACT_STATUS_EXPIRED="E";
    public static final String CONTRACT_STATUS_ONHOLD="H";
    
    //Filter Show All
    public static final String FILTER_SHOW_ALL="SHOW_ALL";
    public static final String FILTER_BREW_SHOW_ALL="BREW_SHOW_ALL";
    public static final String FILTER_ENT_SHOW_ALL="ENT_SHOW_ALL";
    public static final String FILTER_SMS_SHOW_ALL="SMS_SHOW_ALL";
    public static final String FILTER_MMS_SHOW_ALL="MMS_SHOW_ALL";
    public static final String FILTER_VCAST_SHOW_ALL="VCAST_SHOW_ALL";
    public static final String FILTER_WAP_SHOW_ALL="WAP_SHOW_ALL";
    public static final String FILTER_VZAPPZON_SHOW_ALL="VZAPPZON_SHOW_ALL";
    public static final String FILTER_DASHBOARD_SHOW_ALL="DASHBOARD_SHOW_ALL";
    public static final String FILTER_JAVA_SHOW_ALL = "JAVA_SHOW_ALL";
    
    //Filter label constant
    public static final String FILTER_LABEL_SHOW_ALL="Show All";
    public static final String FILTER_LABEL_BREW_SHOW_ALL="Brew Show All";
    public static final String FILTER_LABEL_ENT_SHOW_ALL="Enterprise Show All";
    public static final String FILTER_LABEL_SMS_SHOW_ALL="SMS Show All";
    public static final String FILTER_LABEL_MMS_SHOW_ALL="MMS Show All";
    public static final String FILTER_LABEL_VCAST_SHOW_ALL="VCAST Show All";
    public static final String FILTER_LABEL_WAP_SHOW_ALL="WAP Show All";
    public static final String FILTER_LABEL_VZAPPZON_SHOW_ALL="VZAppZone Show All";
    public static final String FILTER_LABEL_DASHBOARD_SHOW_ALL="Dashboard Show All";
    public static final String FILTER_LABEL_JAVA_SHOW_ALL="Java Show All";
    public static final String FILTER_LABEL_ACTIVE="Active";
    public static final String FILTER_LABEL_DELETED="Deleted";
    public static final String FILTER_LABEL_EXPIRED="Expired";
    public static final String FILTER_LABEL_ONHOLD="OnHold";
    public static final String FILTER_LABEL_BREW="BREW";
    public static final String FILTER_LABEL_SMS="SMS";
    public static final String FILTER_LABEL_MMS="MMS";
    public static final String FILTER_LABEL_WAP="WAP";
    public static final String FILTER_LABEL_ENTERPRISE="Enterprise";
    public static final String FILTER_LABEL_VCAST="VCAST";    
    public static final String FILTER_LABEL_VZ_APP_ZON="VZAppZone";  
    public static final String FILTER_LABEL_DASHBOARD="Dashboard";    
    public static final String OFFER_ACTIVE="Active";
    public static final String OFFER_ACCEPTED="Accepted";
    public static final String OFFER_REJECTED="Rejected";
	public static final String OFFER_REVOKED="Revoked";


    public static final String FILTER_LABEL_JAVA="JAVA";    
    public static final String FILTER_LABEL_SAVED="Saved";
    public static final String FILTER_LABEL_SUBMITTED = "Submitted";
    public static final String FILTER_LABEL_EVALUATED = "Evaluated";
    public static final String FILTER_LABEL_UNDER_REVIEW = "Under Review";
    public static final String FILTER_LABEL_REJECTED = "Rejected";
    public static final String FILTER_LABEL_APPROVED = "Approved";
    public static final String FILTER_LABEL_UNDER_TESTING = "Under Testing";	
    public static final String FILTER_LABEL_ACCEPTED = "Accepted";
    public static final String FILTER_LABEL_NOT_ACCEPTED = "Not Accepted";
    public static final String FILTER_LABEL_SUNSET = "Sunset";
    public static final String FILTER_LABEL_INITIAL_APPROVAL = "Initial Approval";
    public static final String FILTER_LABEL_BUSINESS_APPROVAL_DENIED = "Business Approval Denied";
    public static final String FILTER_LABEL_BUSINESS_APPROVAL_GRANTED = "Business Approval Granted";    
    public static final String FILTER_LABEL_INITIAL_DENIED = "Initial Denied";
    public static final String FILTER_LABEL_TESTING_PASSED = "Testing Passed";
    public static final String FILTER_LABEL_TESTING_FAILED = "Testing Failed";
    public static final String FILTER_LABEL_SUBMITTED_DCR = "Submitted DCR";
    public static final String FILTER_LABEL_PENDING_DCR = "Pending DCR";
    public static final String FILTER_LABEL_PENDING_ARM = "Pending ARM";
    public static final String FILTER_LABEL_PUBLICATION_READY = "Publication Ready";
    public static final String FILTER_LABEL_ASSIGNED = "Assigned";
    public static final String FILTER_LABEL_COMPLETED_IN_PRODUCTION = "Completed In Production";
    public static final String FILTER_LABEL_CERTIFICATION = "Certified";
    public static final String FILTER_LABEL_CONCEPT_ACCEPTED = "Concept Accepted";
    public static final String FILTER_LABEL_CONCEPT_REJECTED = "Concept Rejected";
    public static final String FILTER_LABEL_TEST_PASSED = "Test Passed";
    public static final String FILTER_LABEL_IN_OTA_TESTING = "In OTA Testing";
    public static final String FILTER_LABEL_OTA_TEST_PASSED = "OTA Test Passed";
    public static final String FILTER_LABEL_IN_PRODUCTION = "In Production";
    public static final String FILTER_LABEL_WORKFLOW_EVALUATED = "Evaluated";
    public static final String FILTER_LABEL_WORKFLOW_CONTENT_STANDARD_PENDING = "Content Standard Pending";
    public static final String FILTER_LABEL_WORKFLOW_LEGAL_PENDING = "Legal Pending";
    public static final String FILTER_LABEL_LEGAL_APPROVED = "Legal Approved";
    
    public static final String FILTER_LABEL_RFI_CONTENT_PROG = "RFI-Content Prog" ;
    public static final String FILTER_LABEL_RFI_LEGAL_CONTENT = "RFI-Legal/Content" ;
    public static final String FILTER_LABEL_RFI_TAX_REVIEW = "RFI-Tax Review";
    public static final String FILTER_LABEL_PENDING_TAX_APPROVAL = "Pending Tax Approval";

    public static final String FILTER_LABEL_INITIAL_REJECTED = "Initial Rejected";
    public static final String FILTER_LABEL_CONTENT_IN_REVIEW = "Content In Review";
    public static final String FILTER_LABEL_CONTENT_APPROVED = "Content Approved";
    public static final String FILTER_LABEL_CONTENT_REJECTED = "Content Rejected";
    public static final String FILTER_LABEL_PENDING_PRODUCTION = "Pending Production";
    public static final String FILTER_LABEL_CHANNEL_REJECTED = "Channel Rejected";

    
    public static final String JE_TEXT_CONTRACT_OFFERED="Contract Offered";
    public static final String JE_TEXT_CONTRACT_ACCEPTED="Contract Accepted";
    public static final String JE_TEXT_CONTRACT_REJECTED="Contract Rejected";
    //Application statuses

    //Ellipse String length
    public static final int ELLIPSE_STR_LEN=15;
    
    //VZW users filters statuses
    public static final Object[][] FILTER_BREW_VZW = 	   
		{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.EVALUATED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_EVALUATED},
		{VzdnConstants.PHASE_UNDER_REVIEW.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_UNDER_REVIEW},
		{VzdnConstants.PHASE_REJECTED.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_REJECTED},		
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_SUNSET},
		{VzdnConstants.CONCEPT_ACCEPTED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_CONCEPT_ACCEPTED},
		{VzdnConstants.CONCEPT_REJECTED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_CONCEPT_REJECTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};
    
    public static final Object[][] FILTER_ENTERPRISE_VZW= 
    	{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ASSIGNED_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ASSIGNED},
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};
	
    public static final Object[][] FILTER_SMS_VZW =	   
		{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ASSIGNED_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ASSIGNED},
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};
	
    public static final Object[][] FILTER_MMS_VZW= 	   
    	{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ASSIGNED_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ASSIGNED},
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};
	
    public static final Object[][] FILTER_VCAST_VZW= 	   
    	{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.VCAST_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.VCAST_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.VCAST_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};	    
	
    public static final Object[][] FILTER_WAP_VZW= 	   
    	{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 					VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.PHASE_INITIAL_APPROVAL_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 			VzdnConstants.FILTER_LABEL_INITIAL_APPROVAL},
		{VzdnConstants.PHASE_PENDING_ARM_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 				VzdnConstants.FILTER_LABEL_PENDING_ARM},
		{VzdnConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_BUSINESS_APPROVAL_GRANTED},
		{VzdnConstants.PHASE_PENDING_DCR_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 				VzdnConstants.FILTER_LABEL_PENDING_DCR},
		{VzdnConstants.PHASE_SUBMITTED_DCR_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 			VzdnConstants.FILTER_LABEL_SUBMITTED_DCR},
		{VzdnConstants.PHASE_TESTING_PASSED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 			VzdnConstants.FILTER_LABEL_TESTING_PASSED},		
		{VzdnConstants.PHASE_PUBLICATION_READY_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_PUBLICATION_READY},
		{VzdnConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_COMPLETED_IN_PRODUCTION},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 						VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 							VzdnConstants.FILTER_LABEL_SUNSET},
		{VzdnConstants.PHASE_INITIAL_DENIED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 			VzdnConstants.FILTER_LABEL_INITIAL_DENIED},
		{VzdnConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_BUSINESS_APPROVAL_DENIED},		
		{VzdnConstants.PHASE_TESTING_FAILED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 			VzdnConstants.FILTER_LABEL_TESTING_FAILED}};

    public static final Object[][] FILTER_DASHBOARD_VZW= 	   
		{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_SUBMITTED},
    	 {VzdnConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_INITIAL_APPROVAL},
	     {VzdnConstants.PHASE_INITIAL_REJECTED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_INITIAL_REJECTED},
	     {VzdnConstants.PHASE_CONTENT_IN_REVIEW.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_CONTENT_IN_REVIEW},
	     {VzdnConstants.PHASE_CONTENT_APPROVED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_CONTENT_APPROVED},
	     {VzdnConstants.PHASE_CONTENT_REJECTED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_CONTENT_REJECTED},
	     {VzdnConstants.PHASE_PENDING_PRODUCTION.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_PENDING_PRODUCTION},
	     {VzdnConstants.PHASE_IN_PRODUCTION.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_IN_PRODUCTION},
	     {VzdnConstants.PHASE_CHANNEL_REJECTED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_CHANNEL_REJECTED},
	     {VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_SUNSET}};
  
    public static final Object[][] FILTER_JAVA_VZW = 	   
       {
    	{VzdnConstants.SAVED_ID+","+VzdnConstants.JAVA_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_SAVED},
    	{VzdnConstants.PHASE_JAVA_SUBMITTED+","+VzdnConstants.JAVA_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_SUBMITTED},
    	{VzdnConstants.PHASE_JAVA_APPROVED+","+VzdnConstants.JAVA_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_APPROVED},
	    {VzdnConstants.PHASE_JAVA_CONTENT_APPROVED+","+VzdnConstants.JAVA_PLATFORM_ID.toString(),VzdnConstants.FILTER_LABEL_CONTENT_APPROVED},
        {VzdnConstants.PHASE_JAVA_LEGAL_APPROVED+","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_LEGAL_APPROVED},
        {VzdnConstants.PHASE_JAVA_REJECTED+","+VzdnConstants.JAVA_PLATFORM_ID.toString(),VzdnConstants.FILTER_LABEL_REJECTED},
        {VzdnConstants.PHASE_JAVA_RFI_CONTENT_PROG+","+VzdnConstants.JAVA_PLATFORM_ID.toString(),VzdnConstants.FILTER_LABEL_RFI_CONTENT_PROG},
        {VzdnConstants.PHASE_JAVA_RFI_LEGAL_CONTENT +","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_RFI_LEGAL_CONTENT},
        {VzdnConstants.PHASE_JAVA_RFI_TAX_REVIEW +","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_RFI_TAX_REVIEW},
        {VzdnConstants.PHASE_JAVA_PENDING_TAX_APPROVAL +","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_PENDING_TAX_APPROVAL}
       };	

    public static final Object[][] FILTER_VZ_APP_ZON_VZW =	   
		{{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() ,			VzdnConstants.FILTER_LABEL_SUBMITTED},
    	{VzdnConstants.PHASE_INITIAL_DENIED_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_INITIAL_DENIED},		
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 				VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.PHASE_TEST_PASSED_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_TEST_PASSED},
        {VzdnConstants.PHASE_OTA_TEST_PASSED_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_OTA_TEST_PASSED},
        {VzdnConstants.PHASE_IN_PRODUCTION_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_IN_PRODUCTION},
        {VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_SUNSET}};
    
    //Alliance users filters statuses
    public static final Object[][] FILTER_BREW_ALLIANCE = 	   
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_SAVED},
    	{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.EVALUATED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_EVALUATED},
		{VzdnConstants.PHASE_UNDER_REVIEW.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_UNDER_REVIEW},
		{VzdnConstants.PHASE_REJECTED.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_REJECTED},
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_SUNSET},
		{VzdnConstants.CONCEPT_ACCEPTED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_CONCEPT_ACCEPTED},
		{VzdnConstants.CONCEPT_REJECTED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_CONCEPT_REJECTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.BREW_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};
    
    public static final Object[][] FILTER_ENTERPRISE_ALLIANCE= 
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_SAVED},
    	{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ASSIGNED_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ASSIGNED},
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.ENTERPRISE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};

    public static final Object[][] FILTER_SMS_ALLIANCE =	   
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_SAVED},
    	{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ASSIGNED_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ASSIGNED},
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.SMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};
	
	public static final Object[][] FILTER_MMS_ALLIANCE= 	   
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_SAVED},
    	{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ASSIGNED_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ASSIGNED},
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.MMS_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};
	
	public static final Object[][] FILTER_VCAST_ALLIANCE= 	   
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.VCAST_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_SAVED},
		{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.VCAST_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.VCAST_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.REJECTED_ID.toString()+","+VzdnConstants.VCAST_PLATFORM_ID.toString() , 		VzdnConstants.FILTER_LABEL_NOT_ACCEPTED}};	    
	
	public static final Object[][] FILTER_WAP_ALLIANCE= 	   
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 							VzdnConstants.FILTER_LABEL_SAVED},
		{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 						VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.PHASE_SUBMITTED_DCR_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 			VzdnConstants.FILTER_LABEL_SUBMITTED_DCR},
		{VzdnConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_COMPLETED_IN_PRODUCTION},
		{VzdnConstants.ACCEPTANCE_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 						VzdnConstants.FILTER_LABEL_ACCEPTED},
		{VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 							VzdnConstants.FILTER_LABEL_SUNSET},		
		{VzdnConstants.PHASE_INITIAL_DENIED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 			VzdnConstants.FILTER_LABEL_INITIAL_DENIED},
		{VzdnConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID.toString()+","+VzdnConstants.WAP_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_BUSINESS_APPROVAL_DENIED}};
	
	public static final Object[][] FILTER_VZ_APP_ZON_ALLIANCE =	   
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 				VzdnConstants.FILTER_LABEL_SAVED},
		{VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() ,			VzdnConstants.FILTER_LABEL_SUBMITTED},
		{VzdnConstants.PHASE_INITIAL_DENIED_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_INITIAL_DENIED},		
		{VzdnConstants.TESTING_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 				VzdnConstants.FILTER_LABEL_UNDER_TESTING},
		{VzdnConstants.PHASE_TEST_PASSED_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_TEST_PASSED},        
        {VzdnConstants.PHASE_OTA_TEST_PASSED_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_OTA_TEST_PASSED},
        {VzdnConstants.PHASE_IN_PRODUCTION_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_IN_PRODUCTION},
        {VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.VZAPPZONE_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_SUNSET}};

	public static final Object[][] FILTER_DASHBOARD_ALLIANCE= 	   
		{{VzdnConstants.SAVED_ID.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() , 					VzdnConstants.FILTER_LABEL_SAVED},
		 {VzdnConstants.SUBMISSION_ID.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() , 				VzdnConstants.FILTER_LABEL_SUBMITTED},
		 {VzdnConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_INITIAL_APPROVAL},
	     {VzdnConstants.PHASE_INITIAL_REJECTED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,		VzdnConstants.FILTER_LABEL_INITIAL_REJECTED},
	     {VzdnConstants.PHASE_CONTENT_IN_REVIEW.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,		VzdnConstants.FILTER_LABEL_CONTENT_IN_REVIEW},
	     {VzdnConstants.PHASE_CONTENT_APPROVED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,		VzdnConstants.FILTER_LABEL_CONTENT_APPROVED},
	     {VzdnConstants.PHASE_CONTENT_REJECTED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,		VzdnConstants.FILTER_LABEL_CONTENT_REJECTED},
	     {VzdnConstants.PHASE_PENDING_PRODUCTION.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_PENDING_PRODUCTION},
	     {VzdnConstants.PHASE_IN_PRODUCTION.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,			VzdnConstants.FILTER_LABEL_IN_PRODUCTION},
	     {VzdnConstants.PHASE_CHANNEL_REJECTED.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,		VzdnConstants.FILTER_LABEL_CHANNEL_REJECTED},	     
	     {VzdnConstants.SUNSET_ID.toString()+","+VzdnConstants.DASHBOARD_PLATFORM_ID.toString() ,					VzdnConstants.FILTER_LABEL_SUNSET}};

	public static final Object[][] FILTER_JAVA_ALLIANCE= 	   
	   {
		{VzdnConstants.SAVED_ID+","+VzdnConstants.JAVA_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_SAVED},
    	{VzdnConstants.PHASE_JAVA_SUBMITTED+","+VzdnConstants.JAVA_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_SUBMITTED},
    	{VzdnConstants.PHASE_JAVA_APPROVED+","+VzdnConstants.JAVA_PLATFORM_ID.toString() , VzdnConstants.FILTER_LABEL_APPROVED},
	    {VzdnConstants.PHASE_JAVA_CONTENT_APPROVED+","+VzdnConstants.JAVA_PLATFORM_ID.toString(),VzdnConstants.FILTER_LABEL_CONTENT_APPROVED},
        {VzdnConstants.PHASE_JAVA_LEGAL_APPROVED+","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_LEGAL_APPROVED},
        {VzdnConstants.PHASE_JAVA_REJECTED+","+VzdnConstants.JAVA_PLATFORM_ID.toString(),VzdnConstants.FILTER_LABEL_REJECTED},
        {VzdnConstants.PHASE_JAVA_RFI_CONTENT_PROG+","+VzdnConstants.JAVA_PLATFORM_ID.toString(),VzdnConstants.FILTER_LABEL_RFI_CONTENT_PROG},
        {VzdnConstants.PHASE_JAVA_RFI_LEGAL_CONTENT +","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_RFI_LEGAL_CONTENT},
        {VzdnConstants.PHASE_JAVA_RFI_TAX_REVIEW +","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_RFI_TAX_REVIEW},
        {VzdnConstants.PHASE_JAVA_PENDING_TAX_APPROVAL +","+VzdnConstants.JAVA_PLATFORM_ID.toString() ,VzdnConstants.FILTER_LABEL_PENDING_TAX_APPROVAL}
       };	
	
	//filter platforms
	public static final Object[][] FILTER_PLATFORMS=   
		{{VzdnConstants.BREW_PLATFORM_ID,VzdnConstants.FILTER_LABEL_BREW},
		{VzdnConstants.DASHBOARD_PLATFORM_ID,VzdnConstants.FILTER_LABEL_DASHBOARD},
	    {VzdnConstants.ENTERPRISE_PLATFORM_ID,VzdnConstants.FILTER_LABEL_ENTERPRISE},
	    {VzdnConstants.MMS_PLATFORM_ID,VzdnConstants.FILTER_LABEL_MMS},
	    {VzdnConstants.SMS_PLATFORM_ID,VzdnConstants.FILTER_LABEL_SMS},
	    {VzdnConstants.VCAST_PLATFORM_ID,VzdnConstants.FILTER_LABEL_VCAST},
        {VzdnConstants.VZAPPZONE_PLATFORM_ID,VzdnConstants.FILTER_LABEL_VZ_APP_ZON},
        {VzdnConstants.JAVA_PLATFORM_ID,VzdnConstants.FILTER_LABEL_JAVA},
        {VzdnConstants.WAP_PLATFORM_ID,VzdnConstants.FILTER_LABEL_WAP}
	    };	    

    //Type definitions
    public static final Long TYPE_DEF_BREW_CONTENT_RATING_ID = new Long(1);
    public static final Long TYPE_DEF_VZAPPZONE_CONTENT_TYPE_ID = new Long(2);
    public static final Long TYPE_DEF_VZAPPZONE_BINARY_STATUS = new Long(3);//device binary status type def
    public static final Long TYPE_DEF_DEVICE_ASSET_TYPE = new Long(4);
    public static final Long TYPE_DEF_VZAPPZONE_CONTENT_RATING_ID = new Long(5);
    public static final Long TYPE_DEF_JAVA_CONTENT_RATING_ID = new Long(9);
    public static final Long TYPE_DEF_JAVA_CONTENT_TYPE_ID = new Long(10);

    public static final Long TYPE_DEF_CONTRACT_RING_NUMBER = new Long(20);

    public static final String ASSET_TYPE_WINDOWS_MOBILE_VALUE = "WINDOWS_MOBILE";
    public static final String ASSET_TYPE_RIM_APPLICATION_VALUE = "RIM_APPLICATION";

    //brew Content Rating Column Name
    public static final String COLUMN_BREW_CONTENT_RATING = "CONTENT_RATING_TYPE_ID";

    public static final String PAGE_TYPE_ALLIANCE_CONTACT_UPDATE = "ALLIANCE_CONTACT_UPDATE";
    public static final String PAGE_TYPE_LOGIN_ALLIANCE_CONTACT_UPDATE = "LOGIN_ALLIANCE_CONTACT_UPDATE";

    public static final String VALIDATION_DATE_FOR_WAP_UNDERSCORE="VALIDATION_DATE_FOR_WAP_UNDERSCORE";
  

	/**
	 * Constants for the rules for url validation 
	 * @author mnauman
	 */
    
    public static final int URL_IS_VALID = 0;
    public static final int URL_CONTAINS_INVALID_CHARECTORS_AT_START = 1;
    public static final int URL_CONTAINS_INVALID_CHARECTORS_SOMEWHERE = 2;
    public static final int URL_ENDS_WITH_MOBI = 3;
    public static final int URL_ENDS_WITH_INVALID_CHAR = 4;
	public static final int URL_CONTAINS_WHITESPACES = 5;
	public static final int DOMAIN_NAME_DOES_NOT_EXISTS = 6;	
	public static final int URL_ALREADY_EXISTS_IN_WHITE_LIST = 7;
	public static final int URL_ENDS_WITH_WML_OR_XHTML = 8;
	
	public static final int URL_IS_INVALID = 100;

	
	
	
	/**
	 * List of top level country domain 
	 * @author mnauman
	 */
	public static final  HashMap TOP_LEVEL_COUNTRY_DOMAINS=new HashMap(){
		{
			put( "ad","Andorra");put( "ae","UnitedArabEmirates");put( "af","Afghanistan");
			put( "ag","AntiguaAndBarbuda");put( "ai","Anguilla");put( "al","Albania");
			put( "am","Armenia");put( "an","NetherlandsAntilles");put( "ao","Angola");
			put( "aq","Antarctica");put( "ar","Argentina");put( "as","AmericanSamoa");
			put( "at","Austria");put( "au","Australia");put( "aw","Aruba");
			put( "az","Azerbaijan");put( "ba","BosniaAndHerzegovina");put( "bb","Barbados");
			put( "bd","Bangladesh");put( "be","Belgium");put( "bf","BurkinaFaso");
			put( "bg","Bulgaria");put( "bh","Bahrain");put( "bi","Burundi");
			put( "bj","Benin");put( "bm","Bermuda");put( "bn","BruneiDarussalam");
			put( "bo","Bolivia");put( "br","Brazil");put( "bs","Bahamas");
			put( "bt","Bhutan");put( "bv","BouvetIsland");put( "bw","Botswana");
			put( "by","Belarus");put( "bz","Belize");put( "ca","Canada");
			put( "cc","CocosKeelingIslands");put( "cf","CentralAfricanRepublic");put( "cg","Congo");
			put( "ch","Switzerland");put( "ci","IvoryCoast");put( "ck","CookIsland");
			put( "cl","Chile");put( "cm","Cameroon");put( "cn","China");
			put( "co","Colombia");put( "cr","CostaRica");put( "cs","Czechoslovakia");
			put( "cu","Cuba");put( "cv","CapeVerde");put( "cx","ChristmasIsland");
			put( "cy","Cyprus");put( "cz","CzechRepublic");put( "de","Germany");
			put( "dj","Djibouti");put( "dk","Denmark");put( "dm","Dominica");
			put( "do","DominicanRepublic");put( "dz","Algeria");put( "ec","Ecuador");
			put( "ee","Estonia");put( "eg","Egypt");put( "eh","WesternSahara");
			put( "er","Eritrea");put( "es","Spain");put( "et","Ethiopia");
			put( "fi","Finland");put( "fj","Fiji");put( "fk","FalklandIslands");
			put( "fm","Micronesia");put( "fo","FaroeIslands");put( "fr","France");
			put( "fx","FranceMetropolitan");put( "ga","Gabon");put( "gb","GreatBritain");
			put( "gd","Grenada");put( "ge","Georgia");put( "gf","FrenchGuiana");
			put( "gh","Ghana");put( "gi","Gibraltar");put( "gl","Greenland");
			put( "gm","Gambia");put( "gn","Guinea");put( "gp","Guadeloupe");
			put( "gq","EquatorialGuinea");put( "gr","Greece");put( "gs","GeorgiaSandwichIsls");
			put( "gt","Guatemala");put( "gu","Guam");put( "gw","GuineaBissau");
			put( "gy","Guyana");put( "hk","HongKong");put( "hm","HeardAndMcDonaldIslands");
			put( "hn","Honduras");put( "hr","Hrvatska");put( "ht","Haiti");
			put( "hu","Hungary");put( "id","Indonesia");put( "ie","Ireland");
			put( "il","Israel");put( "in","India");put( "io","BritishIndianOceanTerritory");
			put( "iq","Iraq");put( "ir","Iran");put( "is","Iceland");
			put( "it","Italy");put( "jm","Jamaica");put( "jo","Jordan");
			put( "jp","Japan");put( "ke","Kenya");put( "kg","Kyrgyzstan");
			put( "kh","Cambodia");put( "ki","Kiribati");put( "km","Comoros");
			put( "kn","SaintKittsAndNevis");put( "kp","NorthKorea");put( "kr","SouthKorea");
			put( "kw","Kuwait");put( "ky","CaymanIslands");put( "kz","Kazakhstan");
			put( "la","Laos");put( "lb","Lebanon");put( "lc","SaintLucia");
			put( "li","Liechtenstein");put( "lk","SriLanka");put( "lr","Liberia");
			put( "ls","Lesotho");put( "lt","Lithuania");put( "lu","Luxembourg");
			put( "lv","Latvia");put( "ly","Libya");put( "ma","Morocco");
			put( "mc","Monaco");put( "md","Moldova");put( "mg","Madagascar");
			put( "mh","MarshallIslands");put( "mk","Macedonia");put( "ml","Mali");
			put( "mm","Myanmar");put( "mn","Mongolia");put( "mo","Macau");
			put( "mp","NorthernMarianaIslands");put( "mq","Martinique");put( "mr","Mauritania");
			put( "ms","Montserrat");put( "mt","Malta");put( "mu","Mauritius");
			put( "mv","Maldives");put( "mw","Malawi");put( "mx","Mexico");
			put( "my","Malaysia");put( "mz","Mozambique");put( "na","Namibia");
			put( "nc","NewCaledonia");put( "ne","Niger");put( "nf","NorfolkIsland");
			put( "ng","Nigeria");put( "ni","Nicaragua");put( "nl","Netherlands");
			put( "no","Norway");put( "np","Nepal");put( "nr","Nauru");
			put( "nt","NeutralZone");put( "nu","Niue");put( "nz","NewZealand");
			put( "om","Oman");put( "pa","Panama");put( "pe","Peru");
			put( "pf","FrenchPolynesia");put( "pg","PapuaNewGuinea");put( "ph","Philippines");
			put( "pk","Pakistan");put( "pl","Poland");put( "pm","PierreAndMiquelon");
			put( "pn","Pitcairn");put( "pr","PuertoRico");put( "pt","Portugal");
			put( "pw","Palau");put( "py","Paraguay");put( "qa","Qatar");
			put( "re","Reunion");put( "ro","Romania");put( "ru","RussianFederation");
			put( "rw","Rwanda");put( "sa","SaudiArabia");put( "sb","SolomonIslands");
			put( "sc","Seychelles");put( "sd","Sudan");put( "se","Sweden");
			put( "sg","Singapore");put( "sh","StHelena");put( "si","Slovenia");
			put( "sj","SvalbardAndJanMayenIslands");put( "sk","SlovakRepublic");put( "sl","SierraLeone");
			put( "sm","SanMarino");put( "sn","Senegal");put( "so","Somalia");
			put( "sr","Suriname");put( "st","SaoTomeandPrincipe");put( "su","USSR");
			put( "sv","ElSalvador");put( "sy","Syria");put( "sz","Swaziland");
			put( "tc","TurksAndCaicosIslands");put( "td","Chad");put( "tf","FrenchSouthernTerritories");
			put( "tg","Togo");put( "th","Thailand");put( "tj","Tajikistan");
			put( "tk","Tokelau");put( "tm","Turkmenistan");put( "tn","Tunisia");
			put( "to","Tonga");put( "tp","EastTimor");put( "tr","Turkey");
			put( "tt","TrinidadAndTobago");put( "tv","Tuvalu");put( "tw","Taiwan");
			put( "tz","Tanzania");put( "ua","Ukraine");put( "ug","Uganda");
			put( "uk","UnitedKingdom");put( "um","USMinorOutlyingIslands");put( "us","UnitedStates");
			put( "uy","Uruguay");put( "uz","Uzbekistan");put( "va","VaticanCity");
			put( "vc","SaintVincentAndTheGrenadines");put( "ve","Venezuela");put( "vg","BritishVirginIslands");
			put( "vi","USVirginIslands");put( "vn","VietNam");put( "vu","Vanuatu");
			put( "wf","WallisAndFutunaIslands");put( "ws","Samoa");put( "ye","Yemen");
			put( "yt","Mayotte");put( "yu","Yugoslavia");put( "za","SouthAfrica");
			put( "zm","Zambia");put( "zr","Zaire");put( "zw","Zimbabwe");
		 }
	};
	
	
	/**
	 * List of top level domain 
	 * @author mnauman
	 */
	public static final HashMap TOP_LEVEL_DOMAINS=new HashMap(){
		{
			put( "com","Commercial");put( "edu","Educational");put( "gov","Government");
			put( "int","International");put( "mil","USMilitary");put( "net","Network");
			put( "org","NonProfitOrganization");put( "arpa","OldStyleArpanet");put( "nato","Natofield");

			put( "ac","Academic");put( "co","BritishCommercial");put( "ltd","LimitedCompanies");
			put( "me","Personal");put( "mod","MinistryOfDefence");put( "nhs","NationalHealthServiceInstitutions");
			put( "plc","PublicLimitedCompanies");put( "police","PoliceForces");put( "sch","LocalEducationAuthorities");
		 }

		};

   
   
    public static final String VALIDATION_DATE_FOR_ZON_AUTOMATION="VALIDATION_DATE_FOR_ZON_AUTOMATION";
    
	public static final Object[][] FILTER_WORKFLOW_TYPE=
		{{VzdnConstants.BREW_PLATFORM_ID,VzdnConstants.FILTER_LABEL_BREW},
		 {VzdnConstants.JAVA_PLATFORM_ID,VzdnConstants.FILTER_LABEL_JAVA},
		 {VzdnConstants.VZAPPZONE_PLATFORM_ID,VzdnConstants.FILTER_LABEL_VZ_APP_ZON}};	    

    public static final Object[][] FILTER_BREW_WORKITEMS =
    	{{"201,"+VzdnConstants.BREW_PLATFORM_ID.toString() ,	VzdnConstants.FILTER_LABEL_WORKFLOW_EVALUATED},
    	{"202,"+VzdnConstants.BREW_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_WORKFLOW_CONTENT_STANDARD_PENDING},
    	{"203,"+VzdnConstants.BREW_PLATFORM_ID.toString() , 	VzdnConstants.FILTER_LABEL_WORKFLOW_LEGAL_PENDING}};
    
    
    /************************ NEWS LETTER ******************************/
  
    
	public static final String NEWS_LETTER_EMAIL_LOG = "NEWS_LETTER_EMAIL_LOG";	    
    public static final String ATTACHMENT_FILE_UPLOAD_PATH = "ATTACHMENT_FILE_UPLOAD_PATH";
	public static final String ATTACHMENT_FILE_UPLOAD_NAME = "ATTACHMENT_FILE_UPLOAD_NAME";
	public static final String ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE = "ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE";
	public static final String CSV_FILE_CONTENT = "CSV_FILE_CONTENT";

		
	

	
	
}
