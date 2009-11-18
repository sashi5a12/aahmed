package com.netpace.vzdn.util;

/**
 * This class has static final constants which will be refered accross the Nofication functionality
 *
 * @author Rizwan
 */
public class VzdnNotificationConstants
{
    public static final String NOTIFICATION_EMAIL_TYPE = "E";
    public static final String NOTIFICATION_SMS_TYPE = "S";

    public static final String FROM_ADDRESS = "Verizon Wireless Developer Relations <vzwdevelopers@wireless.netpace.com>";
    public static final String FROM_ADDRESS_ID = "-1";

    //Event IDs
    public static final String EVENT_BREW_APP_STATUS_CHANGE = "1";
    public static final String EVENT_ENT_APP_STATUS_CHANGE = "2";
    public static final String EVENT_ENT_APP_STATUS_SUBMITTED = "3";
    public static final String EVENT_ENT_APP_STATUS_ASSIGNED = "4";
    public static final String EVENT_ENT_APP_STATUS_UNDER_TESTING = "5";
    public static final String EVENT_ENT_APP_STATUS_ACCEPTED = "6";
    public static final String EVENT_ENT_APP_STATUS_REJECTED = "7";
    public static final String EVENT_ENT_APP_DELETED = "8";
    public static final String EVENT_ENT_CONTRACT_OFFERED = "9";
    public static final String EVENT_ENT_CONTRACT_ACCEPTED = "10";
    public static final String EVENT_ENT_CONTRACT_REJECTED = "11";
    public static final String EVENT_BREW_APP_STATUS_SUBMITTED = "12";
    public static final String EVENT_BREW_APP_STATUS_EVALUATED = "13";
    public static final String EVENT_BREW_APP_STATUS_UNDER_TESTING = "14";
    public static final String EVENT_BREW_APP_STATUS_ACCEPTED = "15";
    public static final String EVENT_BREW_APP_STATUS_REJECTED = "16";
    public static final String EVENT_BREW_APP_STATUS_SUNSET = "17";

    public static final String EVENT_BREW_LAUNCH_PRODUCT = "138";
    public static final String EVENT_BREW_CONTENT_STANDARD_PENDING = "139";
    public static final String EVENT_BREW_CONTENT_STANDARD_APPROVAL = "140";
    public static final String EVENT_BREW_CONTENT_STANDARD_RFI = "141";
    public static final String EVENT_BREW_LEGAL_PENDING = "142";
    public static final String EVENT_BREW_LEGAL_APPROVAL = "143";
    public static final String EVENT_BREW_LEGAL_RFI = "144";
    public static final String EVENT_BREW_APPLICATION_UNDER_REVIEW = "145";
    
    public static final String EVENT_SPOTLIGHT_DOCUMENT_UPLOADED = "18";
    public static final String EVENT_SPOTLIGHT_DOCUMENT_DELETED = "19";
    public static final String EVENT_SPOTLIGHT_DOCUMENT_ACCEPTED = "20";
    public static final String EVENT_SPOTLIGHT_DOCUMENT_REJECTED = "21";
    public static final String EVENT_ALLIANCE_ACCEPTED = "22";
    public static final String EVENT_ALLIANCE_REGISTERED_EVENT_FOR_ALLIANCE_ONLY = "23";
    public static final String EVENT_WAP_APP_STATUS_SUBMITTED = "24";
    public static final String EVENT_WAP_APP_STATUS_BUSINESS_APPROVAL_GRANTED = "25";
    public static final String EVENT_WAP_APP_STATUS_PENDING_DCR = "26";
    public static final String EVENT_WAP_APP_STATUS_BUSINESS_APPROVAL_DENIED = "27";
    public static final String EVENT_WAP_APP_STATUS_SUBMITTED_DCR = "28";
    public static final String EVENT_WAP_APP_ERROR_SENDING_DCR_TO_INFOSPACE = "29";
    public static final String EVENT_WAP_APP_STATUS_TESTING_PASSED = "30";
    public static final String EVENT_WAP_APP_STATUS_PUBLICATION_READY = "31";
    public static final String EVENT_WAP_APP_STATUS_SUNSET = "32";
    public static final String EVENT_WAP_APP_SUCCESS_SENDING_DCR_TO_INFOSPACE = "33";
    public static final String EVENT_VCAST_APP_STATUS_SUBMITTED = "34";
    public static final String EVENT_VCAST_APP_STATUS_ACCEPTED = "35";
    public static final String EVENT_VCAST_APP_STATUS_REJECTED = "36";
    public static final String EVENT_MARKETING_CONTENT_STATUS_SUBMITTED = "37";
    public static final String EVENT_MARKETING_CONTENT_STATUS_APPROVED = "38";
    public static final String EVENT_MARKETING_CONTENT_STATUS_REJECTED = "39";
    public static final String EVENT_MARKETING_PROGRAM_ACCEPTED_BY_ALLIANCE = "40";
    public static final String EVENT_MARKETING_PROGRAM_FORWARDED_TO_ALLIANCE = "41";
    public static final String EVENT_MARKETING_PROGRAM_SUBMITTED_BY_MKT_USER = "42";
    public static final String EVENT_SUBMITTED_ALLIANCE_UPDATED = "43";
    public static final String EVENT_BREW_LBS_APP_STATUS_SUBMITTED = "44";
    public static final String EVENT_LBS_ERROR_SENDING_XML_TO_AUTODESK = "47";
    public static final String EVENT_WAP_APP_ROLLBACK_TO_STATUS_PENDING_DCR = "48";
    public static final String EVENT_WAP_APP_RESUBMITTED_STATUS_SUBMITTED_DCR = "49";
    public static final String EVENT_ALLIANCE_REGISTERED_EVENT_FOR_VERIZON_ONLY = "50";
    public static final String EVENT_MUSIC_ALLIANCE_REGISTERED_EVENT_FOR_ALLIANCE_ONLY = "51";
    public static final String EVENT_MUSIC_ALLIANCE_REGISTERED_EVENT_FOR_VERIZON_ONLY = "52";
    public static final String EVENT_ALLIANCE_ADMINISTRATOR_CHANGED = "53";

    public static final String ALLIANCE_REGISTERED_FOR_MULTIPLE_ZON_POTENTIAL_DEV = "54";
    public static final String ALLIANCE_REGISTERED_FOR_GAMES = "55";
    public static final String ALLIANCE_REGISTERED_FOR_TONES_OR_MUSIC = "56";
    public static final String ALLIANCE_REGISTERED_FOR_MOBILE_OR_WEB_BROWSING = "57";
    public static final String ALLIANCE_REGISTERED_FOR_VDO_ON_DEMAND_OR_MOB_TV = "58";
    public static final String ALLIANCE_REGISTERED_FOR_OFF_DECK = "59";
    public static final String ALLIANCE_REGISTERED_FOR_PREMIUM_SMS_OR_MMS = "60";
    public static final String ALLIANCE_REGISTERED_FOR_TOOLS_AND_APPLICATIONS = "61";
    public static final String ALLIANCE_REGISTERED_FOR_NAVIGATION = "62";
    public static final String ALLIANCE_REGISTERED_FOR_NEWS_AND_INFO = "63";
    public static final String ALLIANCE_REGISTERED_FOR_LIFESTYLE = "64";    
    public static final String ALLIANCE_REGISTERED_FOR_BUSINESS_SOLUTIONS = "66";
    public static final String ALLIANCE_REGISTERED_FOR_EMAIL = "67";
    public static final String ALLIANCE_REGISTERED_FOR_FFA = "68";
    public static final String ALLIANCE_REGISTERED_FOR_SFA = "69";
    public static final String ALLIANCE_REGISTERED_FOR_TELEMETRY = "70";
    public static final String ALLIANCE_REGISTERED_FOR_GRAPHICS_OR_WALLPAPER = "71";
    public static final String ALLIANCE_REGISTERED_FOR_COMM_OR_SOCIAL_NETWORKING = "72";
    public static final String ALLIANCE_REGISTERED_FOR_EMAIL_OR_INSTANT_MESSAGING = "73";
    
    public static final String EVENT_CONTRACT_OFFERED="99";
    public static final String EVENT_CONTRACT_ACCEPTED="100";
    public static final String EVENT_CONTRACT_REJECTED="101";

    public static final String ALLIANCE_REGISTERED_FOR_DASHBOARD_FLASH_CAST = "110";
    public static final String ALLIANCE_REGISTERED_FOR_FLASH_LITE_FOR_BREW = "111";
    
    //VZW optimization wap optout Events.
    public static final String EVENT_VZW_OPTIMIZED_WAPOPTOUT_EVENT_FOR_REQUESTER="112";
    public static final String EVENT_VZW_OPTIMIZED_WAPOPTOUT_EVENT_FOR_VERIZON="113";

    public static final String ALLIANCE_REGISTERED_FOR_VZAPPZONE = "125";

    //WAP FTP Notifications
    public static final String EVENT_WAP_FTP_FAILURE = "74";

	//Application URLS Event
    public static final String EVENT_APPLICATION_URLS_CHANGED = "75";

    //Notification IDs
    public static final String NOTIFICATION_ALLIANCE_ACCEPTED = "50";
    
    //Dashboard 
    public static final String EVENT_DASHBOARD_APP_STATUS_INITIALLY_APPROVED = "126";
    public static final String EVENT_DASHBOARD_APP_STATUS_INITIALLY_REJECTED = "127";
    public static final String EVENT_DASHBOARD_APP_STATUS_CONTENT_IN_REVIEW = "128";
    public static final String EVENT_DASHBOARD_APP_STATUS_CONTENT_APPROVED = "129";
    public static final String EVENT_DASHBOARD_APP_STATUS_CONTENT_REJECTED = "130";
    public static final String EVENT_DASHBOARD_APP_STATUS_PENDING_PRODUCTION = "131";
    public static final String EVENT_DASHBOARD_APP_STATUS_MOVED_TO_PRODUCTION = "132";
    public static final String EVENT_DASHBOARD_APP_STATUS_CHANNEL_REJECTED = "133";
    public static final String EVENT_DASHBOARD_APP_STATUS_SUNSET = "134";
    public static final String EVENT_DASHBOARD_APP_EMAIL_TO_ADOBE = "135";
    public static final String EVENT_DASHBOARD_APP_CONTENT_ZIP_FILE_UPLOADED = "136";
    public static final String EVENT_DASHBOARD_APP_STATUS_SUBMITTED = "137";
    

    // ****** VZAppZone Notifications
    // VZAppZone Device Status Notifications
    public static final String EVENT_VZAPPZONE_TEST_FAILURE = "102";
    public static final String EVENT_VZAPPZONE_DEVICE_TEST_PASSED = "103";
    // VZAppZone Application Status Notifications
    public static final String EVENT_VZAPPZONE_APP_STATUS_SUBMITTED = "104";
    public static final String EVENT_VZAPPZONE_APP_STATUS_INITIAL_APPROVAL_DENIED = "105";
    public static final String EVENT_VZAPPZONE_APP_STATUS_INITIAL_APPROVAL_GRANTED = "106";
    public static final String EVENT_VZAPPZONE_APP_STATUS_TEST_PASSED = "107";
    
    public static final String EVENT_VZAPPZONE_DEVICE_BINARY_FILE_UPLOADED = "109";

    public static final String EVENT_VZAPPZONE_FIRMWARE = "114";
    public static final String EVENT_VZAPPZONE_RESUBMIT_INTERTEK_XML_URL = "115";
    public static final String EVENT_VZAPPZONE_RESUBMIT_MPORTAL_XML_URL = "123";
    public static final String EVENT_VZAPPZONE_MPORTAL_XML_SUCCESS= "124";

    public static final String EVENT_VZAPPZONE_DEVICE_OTA_TEST_FAILED = "116";
    public static final String EVENT_VZAPPZONE_DEVICE_OTA_TEST_PASSED = "117";
    public static final String EVENT_VZAPPZONE_DEVICE_IN_OTA_TESTING = "118";
    public static final String EVENT_VZAPPZONE_DEVICE_IN_PRODUCTION = "119";
    public static final String EVENT_VZAPPZONE_APP_STATUS_OTA_TEST_PASSED = "120";
    public static final String EVENT_VZAPPZONE_APP_STATUS_IN_PRODUCTION = "121";
    public static final String EVENT_VZAPPZONE_APP_STATUS_SUNSET = "122";
    
    //Handler Properties
    public static final String HANDLER_PROPERTY_ALLIANCE_ID = "HP_ID_1";
    public static final String HANDLER_PROPERTY_APPS_ID = "HP_ID_2";
    public static final String HANDLER_PROPERTY_USER_IDS = "HP_ID_3";
    public static final String HANDLER_PROPERTY_ADHOC_EMAILS = "HP_ID_4";    
    
    //PlaceHolder IDs
    public static final String PLACE_HOLDER_APP_TITLE = "APPLICATION_TITLE";
    public static final String PLACE_HOLDER_ALLIANCE_NAME = "ALLIANCE_NAME";
    public static final String PLACE_HOLDER_APP_VERSION = "APPLICATION_VERSION";
    public static final String PLACE_HOLDER_APP_STATUS_OLD = "APPLICATION_STATUS_OLD";
    public static final String PLACE_HOLDER_APP_STATUS_NEW = "APPLICATION_STATUS_NEW";
    public static final String PLACE_HOLDER_DATE_OF_EVENT = "DATE_OF_EVENT";
    public static final String PLACE_HOLDER_ALLIANCE_ADMIN_NAME = "ALLIANCE_ADMIN_NAME";
    public static final String PLACE_HOLDER_ALLIANCE_ADMIN_PHONE = "ALLIANCE_ADMIN_PHONE";
    public static final String PLACE_HOLDER_CONTRACT_TITLE = "CONTRACT_TITLE";
    public static final String PLACE_HOLDER_CONTRACT_VERSION = "CONTRACT_VERSION";
    public static final String PLACE_HOLDER_CONTRACT_DOCUMENT_NAME = "CONTRACT_DOCUMENT_NAME";
    public static final String PLACE_HOLDER_APPLICATION_ASSIGNEE = "APPLICATION_ASSIGNEE";
    public static final String PLACE_HOLDER_SPOTLIGHT_TYPE = "SPOTLIGHT_TYPE";
    public static final String PLACE_HOLDER_SPOTLIGHT_NAME = "SPOTLIGHT_NAME";
    public static final String PLACE_HOLDER_SPOTLIGHT_DOCUMENT_NAME = "SPOTLIGHT_DOCUMENT_NAME";
    public static final String PLACE_HOLDER_SPOTLIGHT_DOCUMENT_STATUS = "SPOTLIGHT_DOCUMENT_STATUS";
    public static final String PLACE_HOLDER_VENDOR_ID = "VENDOR_ID";
    public static final String PLACE_HOLDER_CONTENT_TYPE = "CONTENT_TYPE";
    public static final String PLACE_HOLDER_PROJECTED_LIVE_DATE = "PROJECTED_LIVE_DATE";
    public static final String PLACE_HOLDER_RESEND_INFOSPACE_URL = "RESEND_INFOSPACE_URL";
    public static final String PLACE_HOLDER_MARKETING_CONTENT_TITLE = "MARKETING_CONTENT_TITLE";
    public static final String PLACE_HOLDER_MARKETING_PROGRAM_NAME = "MARKETING_PROGRAM_NAME";
    public static final String PLACE_HOLDER_MARKETING_PROGRAM_START_DATE = "MARKETING_PROGRAM_START_DATE";
    public static final String PLACE_HOLDER_ALLIANCE_INFO_PAGE = "ALLIANCE_INFO_PAGE";
    public static final String PLACE_HOLDER_LBS_AUTODESK_STATUS_NEW = "LBS_AUTODESK_STATUS_NEW";
    public static final String PLACE_HOLDER_RESEND_AUTODESK_URL = "RESEND_AUTODESK_URL";
    public static final String PLACE_HOLDER_ALLIANCE_ADMIN_EMAIL = "ALLIANCE_ADMIN_EMAIL";
    public static final String PLACE_HOLDER_ALLIANCE_WEBSITE = "ALLIANCE_WEBSITE";
    public static final String PLACE_HOLDER_COMMA_SEPARATED_STR = "COMMA_SEPARATED_STR";
    public static final String PLACE_HOLDER_FTP_FILE_NAME = "FTP_FILE_NAME";
    public static final String PLACE_HOLDER_PLATFORM="PLATFORM_NAME";

	public static final String PLACE_HOLDER_DEVICE_NAME = "DEVICE_NAME";
    public static final String PLACE_HOLDER_COMMENTS = "COMMENTS";
    public static final String PLACE_HOLDER_TESTED_DATE = "TESTED_DATE";
    public static final String PLACE_HOLDER_FILE_VERSION = "FILE_VERSION";
    public static final String PLACE_HOLDER_USER_NAME = "USER_NAME";
    public static final String PLACE_HOLDER_FILE_NAME = "FILE_NAME";
    public static final String PLACE_HOLDER_FIRMWARE_NAME = "FIRMWARE_NAME";
    public static final String PLACE_HOLDER_MAINTENANCE_RELEASE_NUMBER = "MAINTENANCE_RELEASE_NUMBER";

    public static final String PLACE_HOLDER_INTERTEK_PAYMENT_URL = "INTERTEK_PAYMENT_URL";
    public static final String PLACE_HOLDER_BINARY_ID = "BINARY_ID";
    public static final String PLACE_HOLDER_DEVICE_UUID = "DEVICE_UUID";
    public static final String PLACE_HOLDER_APPLICATION_ID = "APPLICATION_ID";
    public static final String PLACE_HOLDER_ALLIANCE_ID = "ALLIANCE_ID";
    public static final String PLACE_HOLDER_ADDRESS = "ADDRESS";

    public static final String PLACE_HOLDER_RESUBMIT_INTERTEK_URL = "RESUBMIT_INTERTEK_URL";
    public static final String PLACE_HOLDER_RESUBMIT_MPORTAL_URL = "RESUBMIT_MPORTAL_URL";
    public static final String PLACE_HOLDER_MPORTAL_XML_TYPE = "MPORTAL_XML_TYPE";

    public static final String PLACE_HOLDER_PRICE = "PRICE";

    public static final String PLACE_HOLDER_COMPANY_NAME="COMPANY_NAME";
    public static final String PLACE_HOLDER_SUBMITTAL_NUMBER="SUBMITTAL_NUMBER";

    public static final String PLACE_HOLDER_USER_ID = "USER_ID";
    public static final String PLACE_HOLDER_CREATE_ACCOUNT = "CREATE_ACCOUNT";
    public static final String PLACE_HOLDER_CONTENT_FILE_NAME = "CONTENT_FILE_NAME";
    
    public static final String PLACE_HOLDER_NO_VALUE = "--N/A--";
    public static final String PLACE_HOLDER_CURRENT_STATE="CURRENT_STATE";
    public static final String PLACE_HOLDER_PREVIOUS_STATE="PREVIOUS_STATE";
    

    public static final String EMAIL_CATEGORY_VCAST_MUSIC_AGGREGATOR_INFO = "VCAST_MUSIC_AGGREGATOR_INFO";

    public static final Long VZW_REASON_GAMES = new Long(1);
    public static final Long VZW_REASON_TONES_OR_MUSIC = new Long(2);
    public static final Long VZW_REASON_MOBILE_OR_WEB_BROWSING = new Long(3);
    public static final Long VZW_REASON_VDO_ON_DEMAND_OR_MOB_TV = new Long(4);
    public static final Long VZW_REASON_OFF_DECK = new Long(5);
    public static final Long VZW_REASON_PREMIUM_SMS_OR_MMS = new Long(6);
    public static final Long VZW_REASON_TOOLS_AND_APPLICATIONS = new Long(7);
    public static final Long VZW_REASON_NAVIGATION = new Long(8);
    public static final Long VZW_REASON_NEWS_AND_INFO = new Long(9);
    public static final Long VZW_REASON_LIFESTYLE = new Long(10);
    public static final Long VZW_REASON_BUSINESS_SOLUTIONS = new Long(12);
    public static final Long VZW_REASON_EMAIL = new Long(13);
    public static final Long VZW_REASON_FFA = new Long(14);
    public static final Long VZW_REASON_SFA = new Long(15);
    public static final Long VZW_REASON_TELEMETRY = new Long(16);
    public static final Long VZW_REASON_GRAPHICS_OR_WALLPAPER = new Long(17);
    public static final Long VZW_REASON_COMM_OR_SOCIAL_NETWORKING = new Long(18);
    public static final Long VZW_REASON_EMAIL_OR_INSTANT_MESSAGING = new Long(19);
    public static final Long VZW_REASON_DASHBOARD_FLASH_CAST = new Long(40);
    public static final Long VZW_REASON_FLASH_LITE_FOR_BREW = new Long(41);
    public static final Long VZW_REASON_VZAPPZONE = new Long(44);
    
    public static final String PLACE_HOLDER_TEST_NAME_1 = "PLACE_HOLDER_TEST_NAME_1";	
    public static final String PLACE_HOLDER_TEST_NAME_2 = "PLACE_HOLDER_TEST_NAME_2";
    public static final String PLACE_HOLDER_TEST_NAME_3 = "PLACE_HOLDER_TEST_NAME_3";
    public static final String PLACE_HOLDER_TEST_NAME_4 = "PLACE_HOLDER_TEST_NAME_4";
    public static final String PLACE_HOLDER_TEST_NAME_5 = "PLACE_HOLDER_TEST_NAME_5";
    public static final String PLACE_HOLDER_TEST_NAME_6 = "PLACE_HOLDER_TEST_NAME_6";    
    


}
