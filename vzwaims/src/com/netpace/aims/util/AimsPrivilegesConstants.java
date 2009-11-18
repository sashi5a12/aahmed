package com.netpace.aims.util;

/**
 * This class has static final constants which will be refered accross the application
 * for finding out the Privileges Keys.
 * This strings defined here should match the PRIVILEGE_KEY column entry in the
 * AIMS_SYS_PRIVILEGES table of the database.
 *
 * @author Adnan Makda
 */
public class AimsPrivilegesConstants
{
    public static String MANAGE_CREATE_APPLICATION = "CREATE_APPLICATION";
    public static String MANAGE_ALL_APPLICATIONS = "ALL_APPLICATIONS";
    public static String MANAGE_ARCHIVED_APPLICATIONS = "ARCHIVED_APPLICATIONS";
    public static String MANAGE_MY_APPLICATIONS = "MY_APPLICATIONS";
    public static String MANAGE_NEW_APPLICATIONS = "NEW_APPLICATIONS";
    public static String MANAGE_NEW_BREW_APPLICATIONS = "NEW_BREW_APPLICATIONS";
    public static String MANAGE_NEW_BREW_CONCEPTS = "NEW_BREW_CONCEPTS";
    public static String MANAGE_NEW_ENTERPRISE_APPLICATIONS = "NEW_ENTERPRISE_APPLICATIONS";
    public static String MANAGE_NEW_MMS_APPLICATIONS = "NEW_MMS_APPLICATIONS";
    public static String MANAGE_NEW_SMS_APPLICATIONS = "NEW_SMS_APPLICATIONS";
    public static String MANAGE_NEW_VCAST_VIDEO_APPLICATIONS = "NEW_VCAST_VIDEO_APPLICATIONS";
    public static String MANAGE_NEW_WAP_APPLICATIONS = "NEW_WAP_APPLICATIONS";
    public static String MANAGE_NEW_VZAPPZONE_APPLICATIONS = "NEW_VZAPPZONE_APPLICATIONS";
    public static String MANAGE_SAVED_APPLICATIONS = "SAVED_APPLICATIONS";
    public static String MANAGE_TEST_APPLICATIONS = "TESTING_QUEUE";
    public static String BROWSERS = "BROWSERS";
    public static String MANAGE_UPLOAD_WAP_ICONS = "UPLOAD_WAP_ICONS";

    public static String MANAGE_BREW_APPS = "MANAGE_APP_BREW";
    public static String MANAGE_ENTERPRISE_APPS = "MANAGE_APP_ENTERPRISE";
    public static String MANAGE_MMS_APPS = "MANAGE_APP_MMS";
    public static String MANAGE_SMS_APPS = "MANAGE_APP_SMS";
    public static String MANAGE_WAP_APPS = "MANAGE_APP_WAP";
    public static String MANAGE_VCAST_APPS = "MANAGE_APP_VCAST";
    public static String MANAGE_DASHBOARD_APPS = "MANAGE_APP_DASHBOARD";
    public static String MANAGE_VZAPPZONE_APPS = "MANAGE_APP_VZAPPZONE";    
    public static String MANAGE_JAVA_APP = "MANAGE_APP_JAVA";        

    public static String SECTION_APP_PROCESS_INFO_BREW_RELATED_INFO = "SECTION_APP_PROCESS_INFO_BREW_RELATED_INFO";
    public static String SECTION_APP_PROCESS_INFO_ASSIGN = "SECTION_APP_PROCESS_INFO_ASSIGN";
    public static String SECTION_APP_PROCESS_INFO_PRIORITIZATION = "SECTION_APP_PROCESS_INFO_PRIORITIZATION";
    public static String SECTION_APP_PROCESS_INFO_TESTING = "SECTION_APP_PROCESS_INFO_TESTING";

    public static String WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL = "WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL";
    public static String WAP_APP_MANAGE_SECTION_INITIAL_BUSINESS_APPROVAL = "WAP_APP_MANAGE_SECTION_INITIAL_BUSINESS_APPROVAL";
    public static String WAP_APP_MANAGE_SECTION_MOVE_TO_PENDING_DCR = "WAP_APP_MANAGE_SECTION_MOVE_TO_PENDING_DCR";
    public static String WAP_APP_MANAGE_SECTION_PENDING_DCR = "WAP_APP_MANAGE_SECTION_PENDING_DCR";
    public static String WAP_APP_MANAGE_SECTION_CONTENT_TESTING = "WAP_APP_MANAGE_SECTION_CONTENT_TESTING";
    public static String WAP_APP_MANAGE_SECTION_CONTENT_COMPLETION = "WAP_APP_MANAGE_SECTION_CONTENT_COMPLETION";
    public static String WAP_APP_MANAGE_SECTION_CONTENT_REMOVAL = "WAP_APP_MANAGE_SECTION_CONTENT_REMOVAL";
    public static String WAP_APP_MANAGE_ROLLBACK_TO_PENDING_DCR = "WAP_APP_ROLLBACK_TO_PENDING_DCR";
    public static String WAP_FTP_IMAGE_MANUAL = "WAP_FTP_IMAGE_MANUAL";
    
    public static String REPORT_APPLICATION_EVALUATION_FORM = "APP_EVALUATION_FORM";
    //VZAppZone Sections
    public static String VZAPPZONE_APP_SECTION_INITIAL_APPROVAL = "VZAPPZONE_APP_SECTION_INITIAL_APPROVAL";
    public static String VZAPPZONE_APP_SECTION_DEVICE_TESTING = "VZAPPZONE_APP_SECTION_DEVICE_TESTING";
    public static String VZAPPZONE_APP_SECTION_MOVE_TO_STAGING = "VZAPPZONE_APP_SECTION_MOVE_TO_STAGING";    
    public static String VZAPPZONE_APP_SECTION_APPLICATION_MANAGEMENT = "VZAPPZONE_APP_SECTION_APPLICATION_MANAGEMENT";
    public static String VZAPPZONE_APP_SECTION_OTA_TESTING = "VZAPPZONE_APP_SECTION_OTA_TESTING";
    public static String VZAPPZONE_APP_SECTION_MOVE_TO_PRODUCTION = "VZAPPZONE_APP_SECTION_MOVE_TO_PRODUCTION";
    public static String VZAPPZONE_APP_SECTION_SUNSET = "VZAPPZONE_APP_SECTION_SUNSET";
    public static String VZAPPZONE_APP_JOURNAL_ENTRIES = "VZAPPZONE_APP_JOURNAL_ENTRIES";

    public static String NEW_ALLIANCES = "NEW_ALLIANCES";
    public static String SEARCH_ALLIANCES = "SEARCH_ALLIANCES";
    public static String ALLIANCE_STATUS = "ALLIANCE_STATUS";
    public static String COMPANY_INFORMATION = "COMPANY_INFORMATION";
    public static String BUSINESS_INFORMATION = "BUSINESS_INFORMATION";
    public static String CONTACT_INFORMATION = "CONTACT_INFORMATION";
    public static String VCAST_MUSIC_INFORMATION = "VCAST_MUSIC_INFORMATION";
    public static String ALLIANCE_CONTRACTS = "ALLIANCE_CONTRACTS";
    public static String ALLIANCE_CASESTUDIES = "ALLIANCE_CASESTUDIES";
    public static String ALL_ALLIANCES = "ALL_ALLIANCES";
    public static String SPOTLIGHT_PAGE = "SPOTLIGHT_PAGE";
    public static String TOOLS = "TOOLS";

    public static String CLICK_THROUGH_CONTRACTS = "CLICK_THROUGH_CONTRACTS";

    public static String USERS = "USERS";
    public static String ALLIANCE_USERS = "ALLIANCE_USERS";
    public static String ROLES = "ROLES";
    public static String MANAGE_CONTACTS = "MANAGE_CONTACTS";
    public static String SEARCH_PROGRAM_GUIDE = "SEARCH_PROGRAM_GUIDE";
    public static String PROVISIONED_APPS = "PROVISIONED_APPS";

    public static String MARKETING_ADIM = "MARKETING_ADMIN_CONTENT";
    public static String MARKETING_PROV_CONTENT = "MARKETING_PROV_APP";

    public static String CP_MARKETING_CONTENT = "CP_MARKETING_CONTENT";
    public static String SUBMIT_MARKETING_CONTENT = "SUBMIT_MARKETING_CONTENT";

    public static String REVIEW_CONTENT_REQUEST = "REVIEW_CONTENT_REQUEST";

    //Privileges for New Marketing Module (created by Fawad)
    public static String NEW_MARKETING_CONTENT = "MARKETING_CONTENT";
    public static String NEW_PROVISIONED_CONTENTS = "NEW_PROVISIONED_CONTENTS";

    //Privileges
    public static String ALL_APPLICATIONS = "ALL_APPLICATIONS";
    public static String AMENDMENTS = "AMENDMENTS";
    public static String APP_EVALUATION_FORM = "APP_EVALUATION_FORM";
    public static String BREW_EVALUATION_INFO = "BREW_EVALUATION_INFO";
    public static String CONTACT_TECH_SUPPORT = "CONTACT_TECH_SUPPORT";
    public static String CREATE_APPLICATION = "CREATE_APPLICATION";
    public static String DEVICES = "DEVICES";
    public static String MANAGE_FIRMWARE = "MANAGE_FIRMWARE";
    public static String DEVICES_ON_LOAN = "DEVICES_ON_LOAN";
    public static String EMAIL_GROUPS = "EMAIL_GROUPS";
    public static String EMAIL_MESSAGES = "EMAIL_MESSAGES";
    public static String FAQ = "FAQ";
    public static String FORUM = "FORUM";
    public static String INBOX = "INBOX";
    public static String LEGAL_USER = "LEGAL_USER";
    public static String MANAGE_APP_BREW = "MANAGE_APP_BREW";
    public static String MANAGE_APP_ENTERPRISE = "MANAGE_APP_ENTERPRISE";
    public static String MANAGE_APP_MMS = "MANAGE_APP_MMS";
    public static String MANAGE_APP_SMS = "MANAGE_APP_SMS";
    public static String MANAGE_APP_TAB_MANAGE_INFO = "MANAGE_APP_TAB_MANAGE_INFO";
    public static String MANAGE_APP_VCAST = "MANAGE_APP_VCAST";
    public static String MANAGE_APP_WAP = "MANAGE_APP_WAP";
    public static String MANAGE_CONTRACTS = "MANAGE_CONTRACTS";
    public static String OFFER_CONTRACTS = "OFFER_CONTRACT";
    public static String MANAGE_FAQS = "MANAGE_FAQS";
    public static String MANAGE_FAQ_CATEGORIES = "MANAGE_FAQ_CATEGORIES";
    public static String MANAGE_FORUM = "MANAGE_FORUM";
    public static String MANAGE_NOTIFICATIONS = "MANAGE_NOTIFICATIONS";
    public static String MANAGE_PROV_APPS = "MANAGE_PROV_APPS";
    public static String MANAGE_SALES_LEADS = "MANAGE_SALES_LEADS";
    public static String MANAGE_TOPICS = "MANAGE_TOPICS";
    public static String MY_ALLIANCES = "MY_ALLIANCES";
    public static String MY_APPLICATIONS = "MY_APPLICATIONS";
    public static String NEW_APPLICATIONS = "NEW_APPLICATIONS";
    public static String NEW_BREW_APPLICATIONS = "NEW_BREW_APPLICATIONS";
    public static String NEW_BREW_CONCEPTS = "NEW_BREW_CONCEPTS";
    

    // mnauman@netpace : introduces privileges for java applications 
    public static String JAVA_APP_PROGRAMMING_CONTENT_MANAGER = "JAVA_APP_PROGRAMMING_CONTENT_MANAGER";
    public static String JAVA_APP_ALLIANCE_USER = "JAVA_APP_ALLIANCE_USER";
    public static String JAVA_APP_LEGAL_MANAGER = "JAVA_APP_LEGAL_MANAGER";
    public static String JAVA_APP_CONTENT_STANDARD_MANAGER = "JAVA_APP_CONTENT_STANDARD_MANAGER";
    public static String JAVA_APP_TAX_MANAGER = "JAVA_APP_TAX_MANAGER";
    
    public static String NEW_ENTERPRISE_APPLICATIONS = "NEW_ENTERPRISE_APPLICATIONS";
    public static String NEW_MMS_APPLICATIONS = "NEW_MMS_APPLICATIONS";
    public static String NEW_SMS_APPLICATIONS = "NEW_SMS_APPLICATIONS";
    public static String NEW_WAP_APPLICATIONS = "NEW_WAP_APPLICATIONS";
    public static String OUTBOX = "OUTBOX";
    public static String RECONCILE_CATALOG_DATA = "RECONCILE_CATALOG_DATA";
    public static String SAVED_APPLICATIONS = "SAVED_APPLICATIONS";
    public static String SEARCH_APPLICATIONS = "SEARCH_APPLICATIONS";
    public static String SUBMIT_SALES_LEAD = "SUBMIT_SALES_LEAD";
    public static String TOPICS = "TOPICS";
    public static String UPLOAD_CATALOG_DATA = "UPLOAD_CATALOG_DATA";
    public static String UPLOAD_DEVICE_ON_LOAN_DATA = "UPLOAD_DEVICE_ON_LOAN_DATA";
    public static String UREPORTS = "UREPORTS";
    public static String CHANGE_ALLIANCE_ADMIN = "CHANGE_ALLIANCE_ADMIN";
    public static String MANAGE_ACCOUNT_MANAGERS = "MANAGE_ACCOUNT_MANAGERS";
    public static String MANAGE_DISCLAIMERS="MANAGE_DISCLAIMERS";
    public static String APPROVED_JAVA_APPLICATIONS = "APPROVED_JAVA_APPLICATIONS";

    //Privileges on STAGE and not in PRODUCTION. Controller file on PROD
    public static String INDUSTRY_FOCUS = "INDUSTRY_FOCUS";
    public static String PROGRAMMING_LANGUAGES = "PROGRAMMING_LANGUAGES";
    public static String VZW_PLATFORMS = "VZW_PLATFORMS";
    public static String FEATURES = "FEATURES";
    public static String LINES_OF_BUSINESS = "LINES_OF_BUSINESS";
    public static String DECKS = "DECKS";
    public static String UPLOAD_NSTL_DATA = "UPLOAD_NSTL_DATA";
    public static String RECONCILE_NSTL_DATA = "RECONCILE_NSTL_DATA";
    public static String MANAGE_WHITE_PAPERS = "MANAGE_WHITE_PAPERS";
    public static String LIVE_CHAT = "LIVE_CHAT";
    public static String MANAGE_CHAT = "MANAGE_CHAT";
    public static String APP_CATEGORIES = "APP_CATEGORIES";
    public static String APP_SUB_CATEGORIES = "APP_SUB_CATEGORIES";
    public static String CERTIFICATIONS = "CERTIFICATIONS";
    public static String SALES_CONTACTS = "SALES_CONTACTS";
    
    //Temporary Privileges to Access to Modules not given a green light by VZW
    public static String NEW_MARKETING = "NEW_MARKETING";

    public static String EXPORT_CONTENT_FILTER_URLS = "EXPORT_CONTENT_FILTER_URLS";
    
    public static final String UPDATE_ALLIANCE_CONTACT_INFO_BY_VZW = "UPDATE_ALLIANCE_CONTACT_INFO_BY_VZW";
    public static final String CREATE_ALLIANCE_USER_BY_VZW = "CREATE_ALLIANCE_USER_BY_VZW";
    
    //Dashboard Privileges
    public static final String DASHBOARD_INITIAL_APPROVAL="DASHBOARD_INITIAL_APPROVAL";
    public static final String DASHBOARD_CONTENT_ZIP_FILE="DASHBOARD_CONTENT_ZIP_FILE";
    public static final String DASHBOARD_TRACKING="DASHBOARD_TRACKING";
    
    //JMA alliances
    public static final String ALL_JMA_ALLIANCES = "ALL_JMA_ALLIANCES";
    public static final String JMA_INFO = "JMA_INFO";
    
    public static final String VERTICALS_EMAIL_LIST="VERTICALS_EMAIL_LIST";
    public static final String ACCEPT_JMA_PRODUCT="ACCEPT_JMA_PRODUCT";
    public static final String PUBLISH_SOLUTION="PUBLISH_SOLUTIONS";
    public static final String SUBMIT_JMA_SALES_LEAD="SUBMIT_JMA_SALES_LEAD";
    public static final String JMA_SALES_LEAD_SENT="JMA_SALES_LEAD_SENT";
    public static final String JMA_SALES_LEAD_RECEIVED="JMA_SALES_LEAD_RECEIVED";
    public static final String JMA_APPLICATION_SPOTLIGHT_VIEW="JMA_APPLICATION_SPOTLIGHT_VIEW";

    public static final String WORK_LIST = "WORK_LIST";
    
    public static final String SECTION_APP_PROCESS_INFO_ARCHIVED_USER_GUIDE="SECTION_APP_PROCESS_INFO_ARCHIVED_USER_GUIDE";
    
    
    
    
    
    
}
