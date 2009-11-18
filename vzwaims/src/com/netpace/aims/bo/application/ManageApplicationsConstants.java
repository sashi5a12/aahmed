package com.netpace.aims.bo.application;

/**
 * This class has static final constants which will be refered accross the application
 *
 * @author Adnan Makda
 */
public class ManageApplicationsConstants
{

    //URL for Setup Action Classes for Platform related Applications 
    public static String BREW_APPLICATION_SETUP_URL = "/aims/brewApplicationSetup.do";
    public static String SMS_APPLICATION_SETUP_URL = "/aims/smsApplicationSetup.do";
    public static String MMS_APPLICATION_SETUP_URL = "/aims/mmsApplicationSetup.do";
    public static String WAP_APPLICATION_SETUP_URL = "/aims/wapApplicationSetup.do";
    public static String ENTERPRISE_APPLICATION_SETUP_URL = "/aims/entApplicationSetup.do";
    public static String VCAST_APPLICATION_SETUP_URL = "/aims/vcastApplicationSetup.do";
    public static String VZAPPZONE_APPLICATION_SETUP_URL = "/aims/vzAppZoneApplicationSetup.do";
    public static String DASHBOARD_APPLICATION_SETUP_URL = "/aims/dashboardApplicationSetup.do";
    public static String JAVA_APPLICATION_SETUP_URL = "/aims/javaApplicationSetup.do";

    //URL Parameter Names for Application Types
    public static String APP_TYPE_ALL_APPS = "all_apps";
    public static String APP_TYPE_ARCHIVE_APPS = "archive_apps";
    public static String APP_TYPE_MY_APPS = "my_apps";
    public static String APP_TYPE_SAVED_APPS = "saved_apps";
    public static String APP_TYPE_NEW_APPS = "new_apps";
    public static String APP_TYPE_NEW_BREW_APPS = "new_brew_apps";
    public static String APP_TYPE_NEW_BREW_CONCEPTS = "new_brew_concepts";
    public static String APP_TYPE_NEW_ENTERPRISE_APPS = "new_ent_apps";
    public static String APP_TYPE_NEW_MMS_APPS = "new_mms_apps";
    public static String APP_TYPE_NEW_SMS_APPS = "new_sms_apps";
    public static String APP_TYPE_NEW_VCAST_VIDEO_APPS = "new_vcast_video_apps";
    public static String APP_TYPE_NEW_WAP_APPS = "new_wap_apps";
    public static String APP_TYPE_NEW_VZAPPZONE_APPS = "new_vzappzone_apps";
    public static String APP_TYPE_TEST_APPS = "test_apps";

    public static final String SAVE_APPLICATION = "SAVE";
    public static final String SUBMIT_APPLICATION = "SUBMIT";

    public static final String AIMS_APP_SEARCH_QUERY = "SEARCH_QUERY";

    public static final String RES_BUNDLE = "com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE";

    //Privileges
    public static final String PRIV_BREW_LEGAL_USER = "LEGAL_USER";
    public static final String PRIV_BREW_EVALUATION_INFO = "BREW_EVALUATION_INFO";
    public static final String PRIV_MANAGE_PROV_APPS = "MANAGE_PROV_APPS";

    //Privileges
    public static final String PRIV_JAVA_LEGAL_USER = "LEGAL_USER";
    public static final String PRIV_JAVA_EVALUATION_INFO = "BREW_EVALUATION_INFO";
    
    //SESSION VARIABLES
    public static final String MANAGE_APP_TASKNAME = "MANAGE_APP_TASKNAME";
    public static final String SESSION_VAR_CERTIFICATIONS = "AIMS_APP_CERTIFICATIONS";
    public static final String SESSION_VAR_PHASES = "AIMS_APP_PHASES";
    public static final String SESSION_VAR_PRODUCT_INFO = "AIMS_BREW_APP_DEVICES";
    public static final String SESSION_VAR_OWNER_ALLIANCE_ID = "APP_OWNER_ALLIANCE_ID";
    public static final String SESSION_SELECTED_DEVICES_LIST = "MANAGE_APP_SELECTED_DEVICES";
    public static final String SESSION_SELECTED_BROWSERS_LIST = "MANAGE_APP_SELECTED_BROWSERS";
    public static final String SESSION_SELECTED_PROG_LANGS_LIST = "MANAGE_APP_SELECTED_PROG_LANGS";
    public static final String SESSION_SELECTED_VERT_MARKETS_LIST = "MANAGE_APP_SELECTED_VERT_MARKETS";
    public static final String SESSION_SELECTED_REGIONS_LIST = "MANAGE_APP_SELECTED_REGIONS";
    public static final String SESSION_SELECTED_IND_FOCUS_LIST = "MANAGE_APP_SELECTED_IND_FOCUS";
    public static final String SESSION_SELECTED_LINE_OF_BUSS_LIST = "MANAGE_APP_SELECTED_LINE_OF_BUSS";
    public static final String SESSION_SELECTED_SOL_COMP_ID_ONLY = "MANAGE_APP_SELECTED_SOL_COMP_ID_ONLY";
    public static final String SESSION_SELECTED_SOL_COMP = "MANAGE_APP_SELECTED_SOL_COMP";
    public static final String SESSION_SELECTED_ENT_APP_SUB_CAT = "MANAGE_APP_SELECTED_ENT_APP_SUB_CAT";
    public static final String SESSION_APP_SEARCH_TITLE = "SESSION_APP_SEARCH_TITLE";
    public static final String SESSION_APP_SEARCH_PLATFORM = "SESSION_APP_SEARCH_PLATFORM";
    public static final String SESSION_APP_SEARCH_STATUS = "SESSION_APP_SEARCH_STATUS";
    public static final String SESSION_APP_SEARCH_CATEGORY = "SESSION_APP_SEARCH_CATEGORY";
    public static final String SESSION_APP_SEARCH_SUB_CATEGORY = "SESSION_APP_SEARCH_SUB_CATEGORY";
    public static final String SESSION_APP_SEARCH_BREW_APP_TYPE = "SESSION_APP_SEARCH_APP_TYPE";
    public static final String SESSION_APP_SEARCH_CONTRACT = "SESSION_APP_SEARCH_CONTRACT";
    public static final String SESSION_APP_SEARCH_DEVICES = "SESSION_APP_SEARCH_DEVICES";

    //Database Unique Constraint Errors Constraints
    public static String PHASES_UNIQUE_CONSTRAINT_MESSAGE = "ORA-00001: unique constraint (AIMS.PK_APP_PHASES) violated";

    //Integrity Constraint Errors
    public static String INTEGRITY_CONSTRAINT_KEYS[] = { "AIMS.APPS_ID", "error.application.integrity", };
    public static String UNIQUE_CONSTRAINT_KEYS[] = { "AK_APPS_TITLE_VERSION_ALLIANCE", "error.application.title.exists", };
    public static String UNIQUE_CONSTRAINT_KEYS_DB[] = { "AK_APPS_TITLE_VERSION_ALLIANCE", "error.application.title.exists" };

    //Arrays containing database specific info, related to transfer of files from AIMS_TEMP_FILES table
    //{COLUMN_NAME, TABLE_NAME, PK_OF_TABLE}

    //AIMS_APPS TABLE
    public static String SCREEN_JPEG_BLOB_DB_INFO[] = { "screen_jpeg", "aims_apps", "apps_id" };
    public static String SCREEN_JPEG_2_BLOB_DB_INFO[] = { "screen_jpeg_2", "aims_apps", "apps_id" };
    public static String SCREEN_JPEG_3_BLOB_DB_INFO[] = { "screen_jpeg_3", "aims_apps", "apps_id" };
    public static String SCREEN_JPEG_4_BLOB_DB_INFO[] = { "screen_jpeg_4", "aims_apps", "apps_id" };
    public static String SCREEN_JPEG_5_BLOB_DB_INFO[] = { "screen_jpeg_5", "aims_apps", "apps_id" };
    public static String FLASH_DEMO_BLOB_DB_INFO[] = { "flash_demo", "aims_apps", "apps_id" };
    public static String USER_GUIDE_BLOB_DB_INFO[] = { "user_guide", "aims_apps", "apps_id" };
    public static String SPLASH_SCREEN_EPS_BLOB_DB_INFO[] = { "splash_screen_eps", "aims_apps", "apps_id" };
    public static String ACTIVE_SCREEN_EPS_BLOB_DB_INFO[] = { "active_screen_eps", "aims_apps", "apps_id" };
    public static String FAQ_DOC_BLOB_DB_INFO[] = { "faq_doc", "aims_apps", "apps_id" };
    public static String TEST_PLAN_RESULTS_BLOB_DB_INFO[] = { "test_plan_results", "aims_apps", "apps_id" };

    //AIMS_BREW_APPS TABLE
    public static String STYLE_GUIDE_BLOB_DB_INFO[] = { "style_guide", "aims_brew_apps", "brew_apps_id" };
    public static String MKTG_SLICK_SHEET_BLOB_DB_INFO[] = { "mktg_slick_sheet", "aims_brew_apps", "brew_apps_id" };
    public static String APP_LOGO_BW_SMALL_BLOB_DB_INFO[] = { "app_logo_bw_small", "aims_brew_apps", "brew_apps_id" };
    public static String APP_LOGO_BW_LARGE_BLOB_DB_INFO[] = { "app_logo_bw_large", "aims_brew_apps", "brew_apps_id" };
    public static String APP_LOGO_CLRSMALL_BLOB_DB_INFO[] = { "app_logo_clrsmall", "aims_brew_apps", "brew_apps_id" };
    public static String APP_LOGO_CLRLARGE_BLOB_DB_INFO[] = { "app_logo_clrlarge", "aims_brew_apps", "brew_apps_id" };
    public static String CLR_PUB_LOGO_BLOB_DB_INFO[] = { "clr_pub_logo", "aims_brew_apps", "brew_apps_id" };
    public static String PROG_GUIDE_BLOB_DB_INFO[] = { "prog_guide", "aims_brew_apps", "brew_apps_id" };
    public static String APP_TITLE_NAME_BLOB_DB_INFO[] = { "app_title_name", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_PRESENTATION_BLOB_DB_INFO[] = { "brew_presentation", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_COMPANY_LOGO_BLOB_DB_INFO[] = { "company_logo", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_TITLE_SHOT_BLOB_DB_INFO[] = { "title_shot", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_HIGH_RES_SPLASH_DB_INFO[] = { "high_res_splash", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_HIGH_RES_ACTIVE_DB_INFO[] = { "high_res_active", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_SPLASH_SCREEN_DB_INFO[] = { "splash_screen", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_SMALL_SPLASH_DB_INFO[] = { "small_splash", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_ACTIVE_SCREEN_1_DB_INFO[] = { "active_screen_1", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_ACTIVE_SCREEN_2_DB_INFO[] = { "active_screen_2", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_SML_ACTIVE_SCREEN_DB_INFO[] = { "sml_active_screen", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_APP_ACTIION_FLASH_DB_INFO[] = { "app_actiion_flash", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_APP_GIF_ACTION_DB_INFO[] = { "app_gif_action", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_MEDIA_STORE_DB_INFO[] = { "media_store", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_FLASH_DEMO_MOVIE_DB_INFO[] = { "flash_demo_movie", "aims_brew_apps", "brew_apps_id" };
    public static String BREW_DASHBOARD_SCR_IMG_DB_INFO[] = { "dashboard_scr_img", "aims_brew_apps", "brew_apps_id" };
    //AIMS_SMS_APPS TABLE
    public static String MESSAGE_FLOW_BLOB_DB_INFO[] = { "message_flow", "aims_sms_apps", "sms_apps_id" };

    //AIMS_MMS_APPS TABLE
    public static String SAMPLE_CONTENT_BLOB_DB_INFO[] = { "sample_content", "aims_mms_apps", "mms_apps_id" };

    //AIMS_ENTERPRISE_APPS TABLE
    public static String PRESENTATION_BLOB_DB_INFO[] = { "presentation", "aims_enterprise_apps", "enterprise_apps_id" };
    
    //AIMS_ENTERPRISE_APPS TABLE
    public static String BOBO_LETTER_OF_AUTH_BLOB_DB_INFO[] = { "bobo_letter_auth", "aims_enterprise_apps", "enterprise_apps_id" };
    
    //AIMS_ENTERPRISE_APPS TABLE
    public static String LBS_CONTRACT_FIELD_BLOB_DB_INFO[] = { "lbs_contract", "aims_enterprise_apps", "enterprise_apps_id" };

    //AIMS_WAP_APPS TABLE
    public static String WAP_PRODUCT_LOGO_BLOB_DB_INFO[] = { "product_logo", "aims_wap_apps", "wap_apps_id" };
    public static String WAP_PRODUCT_ICON_BLOB_DB_INFO[] = { "product_icon", "aims_wap_apps", "wap_apps_id" };
    public static String WAP_PRESENTATION_BLOB_DB_INFO[] = { "presentation", "aims_wap_apps", "wap_apps_id" };
    public static String WAP_APP_IMG_MEDIUM_BLOB_DB_INFO[] = { "app_img_medium", "aims_wap_apps", "wap_apps_id" };
    public static String WAP_APP_IMG_POTRAIT_BLOB_DB_INFO[] = { "app_img_potrait", "aims_wap_apps", "wap_apps_id" };
    public static String WAP_APP_IMG_LANDSCAPE_BLOB_DB_INFO[] = { "app_img_landscape", "aims_wap_apps", "wap_apps_id" };
    
    //AIMS_WAP_APPS TABLE
    public static String JAVA_PRODUCT_LOGO_BLOB_DB_INFO[] = { "product_logo", "aims_java_apps", "java_apps_id" };
    public static String JAVA_PRODUCT_ICON_BLOB_DB_INFO[] = { "product_icon", "aims_java_apps", "java_apps_id" };
    public static String JAVA_PRESENTATION_BLOB_DB_INFO[] = { "presentation", "aims_java_apps", "java_apps_id" };
    public static String JAVA_APP_IMG_MEDIUM_BLOB_DB_INFO[] = { "app_img_medium", "aims_java_apps", "java_apps_id" };
    public static String JAVA_APP_IMG_POTRAIT_BLOB_DB_INFO[] = { "app_img_potrait", "aims_java_apps", "java_apps_id" };
    public static String JAVA_APP_IMG_LANDSCAPE_BLOB_DB_INFO[] = { "app_img_landscape", "aims_java_apps", "java_apps_id" };

    //AIMS_VZAPPZONE_APP Table
    public static String VZAPPZONE_PRESENTATION_BLOB_DB_INFO[] = { "app_presentation", "aims_vzappzone_apps", "vzappzone_apps_id" };
    public static String VZAPPZONE_CONTENT_LANDING_PAGE_BLOB_DB_INFO[] = { "app_landing_page", "aims_vzappzone_apps", "vzappzone_apps_id" };

    public static String VZAPPZONE_BINARY_FILE_BLOB_DB_INFO[] = { "binary_file", "aims_vzapp_binaries", "binary_id" };
    public static String VZAPPZONE_PREVIEW_FILE_BLOB_DB_INFO[] = { "preview_file", "aims_vzapp_binaries", "binary_id" };
    public static String VZAPPZONE_DOCUMENT_FILE_BLOB_DB_INFO[] = { "document_file", "aims_vzapp_binaries", "binary_id" };

    public static String VZAPPZONE_BASE_TEST_RESULTS_FILE_BLOB_DB_INFO[] = { "base_results_file", "aims_vzapp_binary_firmware", "binary_firmware_id" };
    public static String VZAPPZONE_OTA_TEST_RESULTS_FILE_BLOB_DB_INFO[] = { "ota_results_file", "aims_vzapp_binary_firmware", "binary_firmware_id" };

    //AIMS_VCAST_APPS TABLE
    public static String VCAST_SAMPLE_CLIP_1_BLOB_DB_INFO[] = { "sample_clip_1", "aims_vcast_apps", "vcast_apps_id" };
    public static String VCAST_SAMPLE_CLIP_2_BLOB_DB_INFO[] = { "sample_clip_2", "aims_vcast_apps", "vcast_apps_id" };
    public static String VCAST_SAMPLE_CLIP_3_BLOB_DB_INFO[] = { "sample_clip_3", "aims_vcast_apps", "vcast_apps_id" };

    //AIMS_DASHBOARD_APPS
    public static String DASHBOARD_CLR_PUB_LOGO_BLOB_DB_INFO[] = { "clr_pub_logo", "aims_dashboard_apps", "dashboard_apps_id" };
    public static String DASHBOARD_APP_TITLE_NAME_BLOB_DB_INFO[] = { "app_title_name", "aims_dashboard_apps", "dashboard_apps_id" };
    public static String DASHBOARD_CONTENT_ZIP_FILE_BLOB_DB_INFO[] = { "content_zip_file", "aims_dashboard_apps", "dashboard_apps_id" };
    public static String DASHBOARD_COMPANY_LOGO_BLOB_DB_INFO[] = { "company_logo", "aims_dashboard_apps", "dashboard_apps_id" };
    public static String DASHBOARD_TITLE_IMAGE_BLOB_DB_INFO[] = { "title_image", "aims_dashboard_apps", "dashboard_apps_id" };

    //AIMS_DASHBOARD_APPS
    public static String JAVA_HR_PUBLISHER_LOGO_BLOB_DB_INFO[] = { "hr_publisher_logo", "aims_java_apps", "java_apps_id" };
    public static String JAVA_CHNL_TITLE_ICON_BLOB_DB_INFO[] = { "chnl_title_icon", "aims_java_apps", "java_apps_id" };
    //public static String JAVA_CONTENT_ZIP_FILE_BLOB_DB_INFO[] = { "content_zip_file", "aims_java_apps", "java_apps_id" };
    public static String JAVA_COMPANY_LOGO_BLOB_DB_INFO[] = { "company_logo", "aims_java_apps", "java_apps_id" };
    public static String JAVA_APP_TITLE_NAME_BLOB_DB_INFO[] = { "app_title_name", "aims_java_apps", "java_apps_id" };   

    //AIMS_APP_PHASE TABLE //4th element if the 'otherCompositeId'
    public static String RESULTS_FILE_BLOB_DB_INFO[] = { "results", "aims_app_phases", "apps_id", "testing_phase_id", "device_id" };

    //End of Arrays containing database specific info. 				

    //Arrays containing model object specific info, related to fetching a files from the different APPS table
    //{	PROPERTY(FILE)_OF_OBJECT, MODEL_OBJECT_NAME, PK_PROPERTY_OF_OBJECT}

    //AIMS_APPS OBJECT
    public static String SCREEN_JPEG_BLOB_OBJ_INFO[] =
        { "appfiles.screenJpeg", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String SCREEN_JPEG_2_BLOB_OBJ_INFO[] =
        { "appfiles.screenJpeg2", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String SCREEN_JPEG_3_BLOB_OBJ_INFO[] =
        { "appfiles.screenJpeg3", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String SCREEN_JPEG_4_BLOB_OBJ_INFO[] =
        { "appfiles.screenJpeg4", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String SCREEN_JPEG_5_BLOB_OBJ_INFO[] =
        { "appfiles.screenJpeg5", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String FLASH_DEMO_BLOB_OBJ_INFO[] =
        { "appfiles.flashDemo", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String USER_GUIDE_BLOB_OBJ_INFO[] =
        { "appfiles.userGuide", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String SPLASH_SCREEN_EPS_BLOB_OBJ_INFO[] =
        { "appfiles.splashScreenEps", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String ACTIVE_SCREEN_EPS_BLOB_OBJ_INFO[] =
        { "appfiles.activeScreenEps", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String FAQ_DOC_BLOB_OBJ_INFO[] = { "appfiles.faqDoc", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    public static String TEST_PLAN_RESULTS_BLOB_OBJ_INFO[] =
        { "appfiles.testPlanResults", "com.netpace.aims.model.application.AimsAppFiles as appfiles", "appfiles.appsId" };

    //AIMS_BREW_APPS OBJECT
    public static String STYLE_GUIDE_BLOB_OBJ_INFO[] =
        { "brewfiles.styleGuide", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String MKTG_SLICK_SHEET_BLOB_OBJ_INFO[] =
        { "brewfiles.mktgSlickSheet", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String APP_LOGO_BW_SMALL_BLOB_OBJ_INFO[] =
        { "brewfiles.appLogoBwSmall", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String APP_LOGO_BW_LARGE_BLOB_OBJ_INFO[] =
        { "brewfiles.appLogoBwLarge", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String APP_LOGO_CLRSMALL_BLOB_OBJ_INFO[] =
        { "brewfiles.appLogoClrsmall", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String APP_LOGO_CLRLARGE_BLOB_OBJ_INFO[] =
        { "brewfiles.appLogoClrlarge", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String CLR_PUB_LOGO_BLOB_OBJ_INFO[] =
        { "brewfiles.clrPubLogo", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String PROG_GUIDE_BLOB_OBJ_INFO[] =
        { "brewfiles.progGuide", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String APP_TITLE_NAME_BLOB_OBJ_INFO[] =
        { "brewfiles.appTitleName", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String BREW_PRESENTATION_BLOB_OBJ_INFO[] =
        { "brewfiles.brewPresentation", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    
    public static String BREW_COMPANY_LOGO_BLOB_OBJ_INFO[] =
    	{ "brewfiles.companyLogo", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    
    public static String BREW_TITLE_SHOT_BLOB_OBJ_INFO[] =
    	{ "brewfiles.titleShot", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    public static String BREW_HIGH_RES_SPLASH_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.highResSplash", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_HIGH_RES_ACTIVE_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.highResActive", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_SPLASH_SCREEN_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.splashScreen", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_SMALL_SPLASH_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.smallSplash", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_ACTIVE_SCREEN_1_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.activeScreen1", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_ACTIVE_SCREEN_2_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.activeScreen2", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_SML_ACTIVE_SCREEN_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.smlActiveScreen", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_APP_ACTIION_FLASH_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.appActiionFlash", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_APP_GIF_ACTION_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.appGifAction", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_MEDIA_STORE_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.mediaStore", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String BREW_USER_GUIDE_PDF_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.userGuide", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String FLASH_DEMO_MOVIE_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.flashDemoMovie", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };
    public static String DASHBOARD_SCR_IMG_BLOB_OBJ_INFO[] = 
    	{ "brewfiles.dashboardScrImg", "com.netpace.aims.model.application.AimsBrewFiles as brewfiles", "brewfiles.brewAppsId" };

    //AIMS_SMS_APPS OBJECT
    public static String MESSAGE_FLOW_BLOB_OBJ_INFO[] =
        { "smsfiles.messageFlow", "com.netpace.aims.model.application.AimsSmsFiles as smsfiles", "smsfiles.smsAppsId" };

    //AIMS_MMS_APPS OBJECT
    public static String SAMPLE_CONTENT_BLOB_OBJ_INFO[] =
        { "mmsfiles.sampleContent", "com.netpace.aims.model.application.AimsMmsFiles as mmsfiles", "mmsfiles.mmsAppsId" };

    //AIMS_WAP_APPS OBJECT
    public static String WAP_PRODUCT_LOGO_BLOB_OBJ_INFO[] =
        { "wapfiles.productLogo", "com.netpace.aims.model.application.AimsWapFiles as wapfiles", "wapfiles.wapAppsId" };

    public static String WAP_PRODUCT_ICON_BLOB_OBJ_INFO[] =
        { "wapfiles.productIcon", "com.netpace.aims.model.application.AimsWapFiles as wapfiles", "wapfiles.wapAppsId" };

    public static String WAP_PRESENTATION_BLOB_OBJ_INFO[] =
        { "wapfiles.presentation", "com.netpace.aims.model.application.AimsWapFiles as wapfiles", "wapfiles.wapAppsId" };
    public static String WAP_VENDOR_LOGO_BLOB_OBJ_INFO[] =
        { "alliance.companyLogo", "com.netpace.aims.model.application.AimsWapFiles as wapfiles", "wapfiles.wapAppsId" };
    public static String WAP_MEDIUM_IMAGE_BLOB_OBJ_INFO[] =
        { "wapfiles.mediumLargeImage", "com.netpace.aims.model.application.AimsWapFiles as wapfiles", "wapfiles.wapAppsId" };
    public static String WAP_POTRAIT_IMAGE_BLOB_OBJ_INFO[] =
        { "wapfiles.appQVGAPotraitImage", "com.netpace.aims.model.application.AimsWapFiles as wapfiles", "wapfiles.wapAppsId" };
    public static String WAP_LANDSCAPE_IMAGE_BLOB_OBJ_INFO[] =
        { "wapfiles.appQVGALandscapeImage", "com.netpace.aims.model.application.AimsWapFiles as wapfiles", "wapfiles.wapAppsId" };


    //AIMS_VZAPPZONE_APPS
    public static String VZAPPZONE_PRESENTATION_BLOB_OBJ_INFO[] =
        { "vzAppZoneFiles.appPresentation", "com.netpace.aims.model.application.AimsVZAppZoneFiles as vzAppZoneFiles", "vzAppZoneFiles.vzAppZoneAppsId" };
    public static String VZAPPZONE_CONTENT_LANDING_PAGE_BLOB_OBJ_INFO[] =
        { "vzAppZoneFiles.appLandingPage", "com.netpace.aims.model.application.AimsVZAppZoneFiles as vzAppZoneFiles", "vzAppZoneFiles.vzAppZoneAppsId" };
    
    //AIMS_ENTERPRISE_APPS OBJECT
    public static String PRESENTATION_BLOB_OBJ_INFO[] =
        { "entfiles.presentation", "com.netpace.aims.model.application.AimsEnterpriseFiles as entfiles", "entfiles.enterpriseAppsId" };

    //AIMS_ENTERPRISE_APPS OBJECT
    public static String BOBO_LETTER_AUTH_BLOB_OBJ_INFO[] =
        { "entfiles.bobLetterofAuth", "com.netpace.aims.model.application.AimsEnterpriseFiles as entfiles", "entfiles.enterpriseAppsId" };

  //AIMS_ENTERPRISE_APPS OBJECT
    public static String LBS_CONTRACT_BLOB_OBJ_INFO[] =
        { "entfiles.lbsContract", "com.netpace.aims.model.application.AimsEnterpriseFiles as entfiles", "entfiles.enterpriseAppsId" };

    
    //AIMS_APP_PHASE OBJECT
    public static String RESULTS_FILE_BLOB_OBJ_INFO[] =
        {
            "appphasefiles.results",
            "com.netpace.aims.model.application.AimsAppPhaseFiles as appphasefiles",
            "appphasefiles.appsId",
            "appphasefiles.aimsVzwTestingPhase.testingPhaseId" };

    //AIMS_VCAST_APPS OBJECT
    public static String VCAST_SAMPLE_CLIP_1_BLOB_OBJ_INFO[] =
        { "vcastfiles.sampleClip1", "com.netpace.aims.model.application.AimsVcastFiles as vcastfiles", "vcastfiles.vcastAppsId" };

    public static String VCAST_SAMPLE_CLIP_2_BLOB_OBJ_INFO[] =
        { "vcastfiles.sampleClip2", "com.netpace.aims.model.application.AimsVcastFiles as vcastfiles", "vcastfiles.vcastAppsId" };

    public static String VCAST_SAMPLE_CLIP_3_BLOB_OBJ_INFO[] =
        { "vcastfiles.sampleClip3", "com.netpace.aims.model.application.AimsVcastFiles as vcastfiles", "vcastfiles.vcastAppsId" };

    //AIMS_DASHBOARD_APPS OBJECT
    public static String DASH_CLR_PUB_LOGO_BLOB_OBJ_INFO[] =
    	{ "dashfiles.clrPubLogo", "com.netpace.aims.model.application.AimsDashboardFiles as dashfiles", "dashfiles.dashboardAppsId" };

    public static String DASH_APP_TITLE_NAME_BLOB_OBJ_INFO[] =
    	{ "dashfiles.appTitleName", "com.netpace.aims.model.application.AimsDashboardFiles as dashfiles", "dashfiles.dashboardAppsId" };

    public static String DASH_CONTENT_ZIP_FILE_BLOB_OBJ_INFO[] =
    	{ "dashfiles.contentZipFile", "com.netpace.aims.model.application.AimsDashboardFiles as dashfiles", "dashfiles.dashboardAppsId" };

    public static String DASH_COMPANY_LOGO_BLOB_OBJ_INFO[] =
		{ "dashfiles.companyLogo", "com.netpace.aims.model.application.AimsDashboardFiles as dashfiles", "dashfiles.dashboardAppsId" };

    public static String DASH_TITLE_IMAGE_BLOB_OBJ_INFO[] =
    	{ "dashfiles.titleImage", "com.netpace.aims.model.application.AimsDashboardFiles as dashfiles", "dashfiles.dashboardAppsId" };
    
    //AIMS_DASHBOARD_APPS OBJECT    
    public static String JAVA_CLR_PUB_LOGO_BLOB_OBJ_INFO[] =
	{ "javafiles.clrPubLogo", "com.netpace.aims.model.application.AimsJavaFiles as javafiles", "javafiles.javaAppsId" };
    
    public static String JAVA_COMPANY_LOGO_BLOB_OBJ_INFO[] =
    { "javafiles.companyLogo", "com.netpace.aims.model.application.AimsJavaFiles as javafiles", "javafiles.javaAppsId" };	
    
    public static String JAVA_APP_CHNL_TITLE_BLOB_OBJ_INFO[] =
	{ "javafiles.appTitleName", "com.netpace.aims.model.application.AimsJavaFiles as javafiles", "javafiles.javaAppsId" };

    //End of Arrays containing database specific info. 				

    
    //wap ftp tranfered status
    public static final String WAP_IMAGES_FTP_TRANSFERED = "Y";
    public static final String WAP_IMAGES_FTP_TRANSFERED_AGAIN = "A";
    
    public static final Long USER_GUIDE_DISCLAIMER_ID =new Long(1);
    public static final Long AIR_TIME_DISCLAIMER_ID =new Long(2);
}