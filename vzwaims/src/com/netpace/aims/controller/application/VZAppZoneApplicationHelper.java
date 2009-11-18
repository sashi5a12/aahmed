package com.netpace.aims.controller.application;

import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneOTATestVO;
import com.netpace.aims.dataaccess.valueobjects.FirmwareInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBinaryFirmwarePhaseInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBinaryFirmwareInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBaseTestVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneStageInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneProdInfoVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsAppSubCategory;
import com.netpace.aims.model.application.AimsVZAppBinaries;
import com.netpace.aims.model.application.AimsVZAppZoneApp;
import com.netpace.aims.model.application.AimsVZAppBinaryFirmware;
import com.netpace.aims.model.application.AimsVzappIntertekWsLog;
import com.netpace.aims.model.application.AimsVZAppMPortalXMLLog;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsTypes;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.XMLUtils;
import com.netpace.aims.util.CommonProperties;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.regex.Pattern;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.sql.Blob;


public class VZAppZoneApplicationHelper {

    private static Logger log = Logger.getLogger(VZAppZoneApplicationHelper.class.getName());

    public static final String VZAPPZONE_XML_INTERFACE_SUBMIT="CONTENT_SUBMIT";
    public static final String VZAPPZONE_XML_PLAN_TYPE_FREE="FREE";
    public static final String VZAPPZONE_XML_PLAN_TYPE_ONETIME="ONETIME";
    public static final String VZAPPZONE_XML_ACTION_NAME_ADD="action";
    public static final String VZAPPZONE_XML_ACTION_ADD="ADD";
    public static final String VZAPPZONE_XML_ACTION_MODIFY="MODIFY";
    public static final String VZAPPZONE_XML_ACTION_DELETE="DELETE";
    public static final String VZAPPZONE_XML_LANGUAGE="EN";
    public static final String VZAPPZONE_XML_DEFAULT_DEVICE_NAME="DEVICE_NAME";

    public static final String VZAPPZONE_BINARY_FILE_TYPE_JAR = "jar";
    public static final String VZAPPZONE_BINARY_FILE_TYPE_CAB = "cab";

    public static final String VZAPPZONE_TYPE_OTA="OTA";
    public static final String VZAPPZONE_TYPE_STAGE="STAGE";
    public static final String VZAPPZONE_TYPE_PROD="PROD";

    public static final Long FIRMWARE_FIELD_DEVICE_UUID = new Long(1);
    public static final Long FIRMWARE_FIELD_DEVICE_MR_NUMBER = new Long(2);

    public static final String VZAPPZONE_CONTENT_CATEGORY_PREFIX="/Get Content";

    //key extension, value mime type
    public static Hashtable VZAPP_MIMETYPES_MAPPING = populateVZAppMimeTypesMapping();

    /**
     * returns true if status is equal or above submitted state
     * i.e., submitted or initial denied or under testing (initial approval) or testing passed
     * @param appPhaseId
     * @return
     */
    public static boolean isStatusEqualOrAboveSubmitted(Long appPhaseId) {
        boolean equalOrAbove = false;
        if(appPhaseId != null) {
            equalOrAbove = appPhaseId.equals(AimsConstants.SUBMISSION_ID)
                        || appPhaseId.equals(AimsConstants.PHASE_INITIAL_DENIED_ID)
                        || VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(appPhaseId);
        }
        return equalOrAbove;
    }

    public static boolean isStatusEqualOrAboveInitialApproval(Long appPhaseId) {
        boolean equalOrAbove = false;
        if(appPhaseId != null) {
            if(appPhaseId.equals(AimsConstants.TESTING_ID)
                    || VZAppZoneApplicationHelper.isStatusEqualOrAboveTestPassed(appPhaseId)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }//end isStatusEqualOrAboveInitialApproval

    public static boolean isStatusEqualOrAboveTestPassed(Long appPhaseId) {
        boolean equalOrAbove = false;
        if(appPhaseId != null) {
            if(appPhaseId.equals(AimsConstants.PHASE_TEST_PASSED_ID)
                    || VZAppZoneApplicationHelper.isStatusEqualOrAboveOTATestPassed(appPhaseId)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }//end isStatusEqualOrAboveTestPassed

    public static boolean isStatusEqualOrAboveOTATestPassed(Long appPhaseId) {
        boolean equalOrAbove = false;
        if(appPhaseId != null) {
            if(appPhaseId.equals(AimsConstants.PHASE_OTA_TEST_PASSED_ID)
                    || VZAppZoneApplicationHelper.isStatusEqualOrAboveProduction(appPhaseId)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }//end isStatusEqualOrAboveOTATestPassed

    public static boolean isStatusEqualOrAboveProduction(Long appPhaseId) {
        boolean equalOrAbove = false;
        if(appPhaseId != null) {
            if(appPhaseId.equals(AimsConstants.PHASE_IN_PRODUCTION_ID)
                    || VZAppZoneApplicationHelper.isStatusEqualSunset(appPhaseId)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }//end isStatusEqualOrAboveProduction

    public static boolean isStatusEqualSunset(Long appPhaseId) {
        boolean equalSunset = false;
        if(appPhaseId != null) {
            if(appPhaseId.equals(AimsConstants.SUNSET_ID)) {
                equalSunset = true;
            }
        }
        return equalSunset;
    }//end isStatusEqualOrAboveProduction

    public static boolean isBinaryStatusEqualOrAboveTesting(Long binaryFirmwareStatus) {
        boolean equalOrAbove = false;
        if(binaryFirmwareStatus!=null) {
            if(binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_UNDER_TESTING)
                    || binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_TEST_FAILED)
                    || VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveTestPassed(binaryFirmwareStatus)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }

    public static boolean isBinaryStatusEqualOrAboveTestPassed(Long binaryFirmwareStatus) {
        boolean equalOrAbove = false;
        if(binaryFirmwareStatus!=null) {
            if(binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_TEST_PASSED)
                    || VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATesting(binaryFirmwareStatus)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }

    public static boolean isBinaryStatusEqualOrAboveOTATesting(Long binaryFirmwareStatus) {
        boolean equalOrAbove = false;
        if(binaryFirmwareStatus!=null) {
            if(binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_OTA_TESTING)
                    || binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED)
                    || VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(binaryFirmwareStatus)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }

    public static boolean isBinaryStatusEqualOrAboveOTATestPassed(Long binaryFirmwareStatus) {
        boolean equalOrAbove = false;
        if(binaryFirmwareStatus!=null) {
            if(binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_PASSED)
                    || VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveProduction(binaryFirmwareStatus)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }

    public static boolean isBinaryStatusEqualOrAboveProduction(Long binaryFirmwareStatus) {
        boolean equalOrAbove = false;
        if(binaryFirmwareStatus!=null) {
            if(binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_PRODUCTION)) {
                    //|| binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_STAGING)) {
                equalOrAbove = true;
            }
        }
        return equalOrAbove;
    }

    public static String getVZAppZoneApplicationStatus(Long aimsLifecyclePhaseId) {
        String status = "";
        if(aimsLifecyclePhaseId.longValue()==AimsConstants.SAVED_ID.longValue()) {
            status = "Saved";
        }
        if(aimsLifecyclePhaseId.longValue()==AimsConstants.SUBMISSION_ID.longValue()) {
            status = "Submitted";
        }
        if(aimsLifecyclePhaseId.longValue()==AimsConstants.TESTING_ID.longValue()) {
            status = "Under Testing";
        }
        else if(aimsLifecyclePhaseId.longValue()==AimsConstants.PHASE_TEST_PASSED_ID.longValue()) {
            status = "Test Passed";
        }
        else if(aimsLifecyclePhaseId.longValue()==AimsConstants.PHASE_OTA_TEST_PASSED_ID.longValue()) {
            status = "OTA Test Passed";
        }
        else if(aimsLifecyclePhaseId.longValue()==AimsConstants.PHASE_IN_PRODUCTION_ID.longValue()) {
            status = "In Production";
        }
        else if(aimsLifecyclePhaseId.longValue()==AimsConstants.SUNSET_ID.longValue()) {
            status = "Sunset";
        }
        return status;
    }

    /*
    * This method constructs the Journal Entry for updated Test for
    */
    public static String constructJournalEntryForUpdatedTest(FirmwareInfoVO firmwareInfo,
                                                             String baseTestStatus, String currUser) {
        StringBuffer strValue = new StringBuffer();
        strValue.append("Test For device: '");
        strValue.append(firmwareInfo.getPhoneModel());
        strValue.append("' with firmware '");
        strValue.append(firmwareInfo.getFirmwareName());
        strValue.append("' (");
        strValue.append(firmwareInfo.getMrNumber());
        strValue.append(") updated by '");
        strValue.append(currUser);
        strValue.append("' ");
        if (baseTestStatus != null) {
            strValue.append("with status '");
            if (baseTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0]))
                strValue.append(AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1]);
            else if (baseTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0]))
                strValue.append(AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1]);
        }
        strValue.append("'\n");
        return strValue.toString();
    }

    /*
    * This method constructs the Journal Entry for updated Test for
    */
    public static String constructJournalEntryForUpdatedOTATest(FirmwareInfoVO firmwareInfo,
                                                             String otaTestStatus, String currUser) {
        StringBuffer strValue = new StringBuffer();
        strValue.append("OTA Test For device: '");
        strValue.append(firmwareInfo.getPhoneModel());
        strValue.append("' with firmware '");
        strValue.append(firmwareInfo.getFirmwareName());
        strValue.append("' (");
        strValue.append(firmwareInfo.getMrNumber());
        strValue.append(") updated by '");
        strValue.append(currUser);
        strValue.append("' ");
        if (otaTestStatus != null) {
            strValue.append("with status '");
            if (otaTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[0]))
                strValue.append(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[1]);
            else if (otaTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[0]))
                strValue.append(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[1]);
        }
        strValue.append("'\n");
        return strValue.toString();
    }

    public static String constructJournalEntryForMoveToOTATesting(FirmwareInfoVO firmwareInfo, String currUser) {
        StringBuffer strValue = new StringBuffer();
        strValue.append("OTA Test For device: '");
        strValue.append(firmwareInfo.getPhoneModel());
        strValue.append("' with firmware '");
        strValue.append(firmwareInfo.getFirmwareName());
        strValue.append("' (");
        strValue.append(firmwareInfo.getMrNumber());
        strValue.append(") is moved to OTA Testing by '");
        strValue.append(currUser);
        strValue.append("'\n");
        return strValue.toString();
    }

    public static String constructJournalEntryForMovedToProduction(FirmwareInfoVO firmwareInfo, String currUser) {
        StringBuffer strValue = new StringBuffer();
        strValue.append("Device: '");
        strValue.append(firmwareInfo.getPhoneModel());
        strValue.append("' with firmware '");
        strValue.append(firmwareInfo.getFirmwareName());
        strValue.append("' (");
        strValue.append(firmwareInfo.getMrNumber());
        strValue.append(") is moved to Production by '");
        strValue.append(currUser);
        strValue.append("'\n");
        return strValue.toString();
    }

    public static String constructJournalEntryForMovedToStaging(FirmwareInfoVO firmwareInfo, String currUser) {
        StringBuffer strValue = new StringBuffer();
        strValue.append("Device: '");
        strValue.append(firmwareInfo.getPhoneModel());
        strValue.append("' with firmware '");
        strValue.append(firmwareInfo.getFirmwareName());
        strValue.append("' (");
        strValue.append(firmwareInfo.getMrNumber());
        strValue.append(") is moved to Staging by '");
        strValue.append(currUser);
        strValue.append("'\n");
        return strValue.toString();
    }

    /*
    * This method constructs the Journal Entry to log binary file upload
    */
    public static String constructJournalEntryForBinaryUpload(String binaryFileFileName,
                                                              String binaryVersion,
                                                              FirmwareInfoVO firmwareInfo,
                                                              String currUser) {
        StringBuffer journalEntryBuf = new StringBuffer();
        journalEntryBuf.append("Binary File: '");
        journalEntryBuf.append(binaryFileFileName);
        journalEntryBuf.append("' (ver: ");
        journalEntryBuf.append(binaryVersion);
        journalEntryBuf.append(") for device: '");
        journalEntryBuf.append(firmwareInfo.getPhoneModel());
        journalEntryBuf.append("' with firmware: '");
        journalEntryBuf.append(firmwareInfo.getFirmwareName());
        journalEntryBuf.append("' (");
        journalEntryBuf.append(firmwareInfo.getMrNumber());
        journalEntryBuf.append(") ");
        journalEntryBuf.append("uploaded by '");
        journalEntryBuf.append(currUser);
        journalEntryBuf.append("'");

        return journalEntryBuf.toString();
    }

    /*
    * This method constructs the Journal Entry for device deleted
    */
    public static String constructJournalEntryForDeviceDeleted(String vzAppZoneDeviceModel, String currUser) {
        StringBuffer strValue = new StringBuffer();
        strValue.append("Device: '");
        strValue.append(vzAppZoneDeviceModel);
        strValue.append("' deleted by '");
        strValue.append(currUser);

        return strValue.toString();
    }

    public static boolean validateVersionPattern(String version) {
        Pattern versionPattern = Pattern.compile(AimsConstants.VERSION_PATTERN);
        return versionPattern.matcher(version).matches();
    }

    public static boolean validateVersion(String oldVersion, String newVersion) {

        log.debug("validateVersion start old: "+oldVersion+"\tnew: "+newVersion);
        if(oldVersion.equals(newVersion)) {
            return false;
        }
        else if(StringFuncs.isEmpty(oldVersion)) {
            return true;
        }

        String[] oldVersionTokens = oldVersion.split("\\.");
        String[] newVersionTokens = newVersion.split("\\.");
        boolean validated = false;
        if((oldVersionTokens!=null && oldVersionTokens.length>0)
                && (newVersionTokens!=null && newVersionTokens.length>0) ) {
           validated = compareVersions(StringFuncs.ConvertArrToArrayList(oldVersionTokens), StringFuncs.ConvertArrToArrayList(newVersionTokens));
        }
        log.debug("validateVersion ends old: "+oldVersion+"\tnew: "+newVersion+"\tvalidated: "+validated+"\n");
        return validated;
    }

    private static boolean compareVersions(List oldVersionsList, List newVersionsList) {
        boolean result = false;

        String newVersionElem = "";
        String oldVersionElem = "";
        float newVersionValue;
        float oldVersionValue;
        try {
            newVersionElem = StringFuncs.NullValueReplacement ((String)newVersionsList.get(0));
            oldVersionElem = StringFuncs.NullValueReplacement ((String) oldVersionsList.get(0));

            //to avoid values starting with 0 e.g 01, make it 0.01
            if(newVersionElem.startsWith("0")) {
                newVersionElem = "0."+newVersionElem;
            }
            //to avoid values starting with 0 e.g 01, make it 0.01
            if(oldVersionElem.startsWith("0")) {
                oldVersionElem = "0."+oldVersionElem;
            }
            newVersionValue = Float.parseFloat(newVersionElem);
            oldVersionValue = Float.parseFloat(oldVersionElem);

            if(newVersionValue > oldVersionValue) {
                result = true;
            }
            else if(newVersionValue == oldVersionValue) {
                //if values matches then compare next values
                //e.g. for 1.2.1 and 1.2.2, first element matches, now match next 2.1 and 2.2 and so on
                List oldVersionSubList = oldVersionsList.subList(1, oldVersionsList.size());
                List newVersionSubList = newVersionsList.subList(1, newVersionsList.size());
                if(oldVersionSubList.isEmpty() && newVersionSubList.isEmpty()) {
                    //means all values matches till end
                    result = false;
                }
                else if(oldVersionSubList.isEmpty() && !newVersionSubList.isEmpty()) {
                    //if old sublist ends and new list has values means new version is greater
                    //e.g. 1.1 and 1.1.1
                    if(newVersionSubList.get(0).equals("0")) {
                        result = false;
                    }
                    else {
                        result = true;
                    }
                }
                else if(!oldVersionSubList.isEmpty() && newVersionSubList.isEmpty()) {
                    //if old sublist has values and new list is empty means new version is less than old version
                    //e.g. 1.1.1 and 1.1
                    if(oldVersionSubList.get(0).equals("0")) {
                        result = false;
                    }
                    result = false;
                }
                else {
                    //if both lists not empty, then match again
                    result = compareVersions(oldVersionSubList, newVersionSubList);
                }
            }
        }
        catch(NumberFormatException nfe) {
            System.out.println("NumberFormatException found in compare versions: ");
            nfe.printStackTrace();
            result = false;
        }
        catch(ArrayIndexOutOfBoundsException ofbe) {
            System.out.println("ArrayIndexOutOfBoundsException found in compare versions: ");
            ofbe.printStackTrace();
            result = false;
        }
        return result;
    }



    public static boolean canDeleteDevice(Long appStatus, Long deviceStatus, String userType) {
        boolean canDelete = false;
        //if status is submitted or saved, user can delete device
        if(appStatus.equals(AimsConstants.SAVED_ID) || appStatus.equals(AimsConstants.SUBMISSION_ID)) {
            canDelete = true;
        }
        else if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(appStatus)) {
            //after initial approval, vzw user can delete device only when device status is failed or ota test failed
            if (userType.equals(AimsConstants.VZW_USERTYPE)) {
                /*if(deviceStatus.equals(AimsConstants.PHASE_TEST_FAILED_ID) || deviceStatus.equals(AimsConstants.PHASE_OTA_TEST_FAILED_ID)) {
                    canDelete = true;
                }*/
                canDelete = true;
            }
        }
        return canDelete;
    }

    public static void sendMPortalXML(AimsApp aimsApp, AimsVZAppZoneApp vzAppZoneApp, AimsAllianc aimsAllianceOfApplication, Long[] binaryIdsArr, String xmlType) throws Exception {
        Map binaryFirmwareInfoMapForXML = null;
        if(binaryIdsArr!=null && binaryIdsArr.length>0) {

            /***** get binaryFirmwareInfo , those binaryFirmwares which are Moved to server
             * binaryFirwmares in vzAppMoveToSeverList are already set to moved to server when saveOrUpdateVZAppZoneApplication
             * is called above, so requirement of getting all binaryFirmwares which are  moved to server plus binaryFirmwares in
             * vzAppMoveToSeverList automcatically fulfilled.
             * send binary ids as null, to fetch all  binaryFirmwares which are moved to server
            **/
            binaryFirmwareInfoMapForXML = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoMapWithBinariesByBinaryIds(
                                                                                    vzAppZoneApp.getVzAppZoneAppsId(),
                                                                                    AimsConstants.ACTIVE_CHAR,
                                                                                    null,
                                                                                    xmlType,
                                                                                    "MR",
                                                                                    false);
            if(binaryFirmwareInfoMapForXML!=null) {
                List vzAppBinaryFirmwareInfoList = (List)binaryFirmwareInfoMapForXML.get("vzAppBinaryFirmwareInfoList");
                List binariesList = (List)binaryFirmwareInfoMapForXML.get("vzAppBinariesList");
                List vzAppBinaryFirmwareIdsList = (List)binaryFirmwareInfoMapForXML.get("vzAppBinaryFirmwareIdsList");
                List vzAppBinaryIdsList = (List)binaryFirmwareInfoMapForXML.get("vzAppBinaryIdsList");

                VZAppZoneApplicationHelper.sendXMLToMPortalServer(false,
                                                aimsApp, vzAppZoneApp, aimsAllianceOfApplication,
                                                vzAppBinaryFirmwareInfoList, binariesList,
                                                vzAppBinaryFirmwareIdsList, vzAppBinaryIdsList,
                                                xmlType);
            }
            else {
                log.debug("ERROR in VZAppZoneApplicationHelper.sendMPortalXML: binaryFirmwareInfoMapForXML not found against binaryIds, xmlType: "+xmlType);
            }
        }
        else {
            log.debug("ERROR in VZAppZoneApplicationHelper.sendMPortalXML: binaryIds not found against vzAppMoveToSeverList, xmlType: "+xmlType);
        }
    }//end sendMPortalXML


    public static boolean sendXMLToMPortalServer(boolean sync,
                                          final AimsApp aimsApp,
                                          final AimsVZAppZoneApp vzAppZoneApp,
                                          final AimsAllianc alliance,
                                          final List vzAppBinaryFirmwareInfoList,
                                          final List binariesList,
                                          final List vzAppBinaryFirmwareIdsList,
                                          final List vzAppBinaryIdsList,
                                          final String xmlType) throws Exception {

        if (sync) {
           return VZAppZoneApplicationHelper.sendXMLToMPortalServer(aimsApp, vzAppZoneApp, alliance,
                                                            vzAppBinaryFirmwareInfoList, binariesList,
                                                            vzAppBinaryFirmwareIdsList, vzAppBinaryIdsList,
                                                            xmlType);
        }
        else {
            new Thread() {
                public void run() {
                    try {
                        VZAppZoneApplicationHelper.sendXMLToMPortalServer(aimsApp, vzAppZoneApp, alliance,
                                                                    vzAppBinaryFirmwareInfoList, binariesList,
                                                                    vzAppBinaryFirmwareIdsList, vzAppBinaryIdsList,
                                                                    xmlType);
                    }
                    catch(Exception e) {
                        System.out.println("Exception occured while sending xml:");
                        e.printStackTrace();
                    }
                }
            }
            .start();
            return true;
        }
    }

    public static boolean sendXMLToMPortalServer(AimsApp aimsApp,
                                          AimsVZAppZoneApp vzAppZoneApp,
                                          AimsAllianc alliance,
                                          List vzAppBinaryFirmwareInfoList,
                                          List binariesList,
                                          List binaryFirmwareIdsList,
                                          List binaryIdsList,
                                          String xmlType) throws Exception {

        log.debug("============== start VZAppZoneApplicationHelper.sendXMLToMPortalServer ==============");
        boolean success = false;
		
		log.debug("(01) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());

        ConfigEnvProperties envProps=ConfigEnvProperties.getInstance();
        String hostName = "";

        log.debug("(02) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());
		
		String binaryDownloadURL = envProps.getProperty("vzappzone.binary.download.URL");
        String mPortalUsername= envProps.getProperty("vzappzone.mportal.username");
        String mPortalPassword= envProps.getProperty("vzappzone.mportal.password");
        String resubmitUrl=envProps.getProperty("vzappzone.mportal.resubmit.URL");

        int readTimeout = 1800000;//30 minutes by default
        String readTimeoutStr = CommonProperties.getInstance().getProperty("vzappzone.mportal.xml.timeout");
        if(StringUtils.isNotEmpty(readTimeoutStr) && StringUtils.isNumeric(readTimeoutStr)) {
            try {
                readTimeout = Integer.parseInt(readTimeoutStr);
                log.debug("readTimeout is= "+readTimeout);
            }
            catch(NumberFormatException ne) {
                log.error("VZAppZoneApplicationHelper.sendXMLToMPortalServer: Exception occured while parsing readTimeout");
                ne.printStackTrace();
            }
        }

        log.debug("(03) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());
		
		if(StringFuncs.NullValueReplacement(xmlType).equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA)) {
            hostName = envProps.getProperty("vzappzone.mportal.ota.server.url");
            log.debug("VZAppZoneApplicationHelper.sendXMLToMPortalServer: xmlType is OTA, OTA Server URL is: "+hostName);
        }
        else if(StringFuncs.NullValueReplacement(xmlType).equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_STAGE)) {
            hostName = envProps.getProperty("vzappzone.mportal.stage.server.url");
            log.debug("VZAppZoneApplicationHelper.sendXMLToMPortalServer: xmlType is Stage, Stage Server URL is: "+hostName);
        }
        else if(StringFuncs.NullValueReplacement(xmlType).equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD)) {
            hostName = envProps.getProperty("vzappzone.mportal.prod.server.url");
            log.debug("VZAppZoneApplicationHelper.sendXMLToMPortalServer: xmlType is Prod, Prod Server URL is: "+hostName);
        }

        log.debug("(04) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());
		
		AimsVZAppMPortalXMLLog mPortalXMLLog=new AimsVZAppMPortalXMLLog();
		Date currDate=new Date();

        String binaryIdsStr = "0";
        String binaryFirmwareIdsStr = "0";

        if(binaryIdsList!=null && binaryIdsList.size()>0) {
            binaryIdsStr = StringFuncs.ConvertLongArrToString((Long [])binaryIdsList.toArray(new Long[0]), ",");
        }

        if(binaryFirmwareIdsList!=null && binaryFirmwareIdsList.size()>0) {
            binaryFirmwareIdsStr = StringFuncs.ConvertLongArrToString((Long [])binaryFirmwareIdsList.toArray(new Long[0]), ",");
        }

        log.debug("(05) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());
		
		String xmlToSend = VZAppZoneApplicationHelper.createMPortalXML( aimsApp, vzAppZoneApp,
                                                                        vzAppBinaryFirmwareInfoList,
                                                                        binariesList,alliance,
                                                                        binaryDownloadURL,
                                                                        mPortalUsername, mPortalPassword,
                                                                        xmlType);
        
		log.debug("(06) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());
		
		String mPortalResponseXML = "";
        log.debug("========== xmlToSend ==========");
        log.debug(xmlToSend);
        log.debug("========== end xmlToSend ==========");


        try {
			//log.debug("\n\n"+xmlToSend+"\n\n");
            if(!StringFuncs.isNullOrEmpty(xmlToSend)) {
                log.debug("========== sending mPortal xml to server ==========");
                log.debug("(07) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());
				mPortalResponseXML = XMLUtils.postXMLWithTimeout(hostName, xmlToSend, readTimeout);
				log.debug("(08) - " + System.currentTimeMillis() + " - Tracking Bug: sendXMLToMPortalServer for: " + aimsApp.getAppsId().toString());
				log.debug("========== response XML ==========");
                log.debug(mPortalResponseXML);
                log.debug("========== end respnse XML ==========");
            }
            else {
                log.debug("VZAppZoneApplicationHelper.sendXMLToMPortalServer: ERROR: xmlToSend is empty ");
            }

            //No success message
			if (!VZAppZoneApplicationHelper.isSuccessFoundInMPortalResponseXML(mPortalResponseXML)){
                //save log and notification if no success
                try {
                        mPortalXMLLog.setSubmitStatus("N");
                        mPortalXMLLog.setSubmitResponse(mPortalResponseXML);
                        mPortalXMLLog.setAppId(aimsApp.getAppsId());
                        mPortalXMLLog.setSubmitDate(currDate);
                        mPortalXMLLog.setXmlType(xmlType);
                        mPortalXMLLog.setBinaryIds(binaryIdsStr);
                        mPortalXMLLog.setBinaryFirmwareIds(binaryFirmwareIdsStr);
                        VZAppZoneApplicationManager.saveMPortalXMLLog(mPortalXMLLog,xmlToSend);

                        VZAppZoneApplicationHelper.mPortalResubmitNotification(aimsApp, alliance, binaryIdsStr, binaryFirmwareIdsStr, resubmitUrl, xmlType, currDate);
                    } catch (HibernateException e) {
                        log.error(e,e);
                        log.debug("NO SUCESS RESPONSE: Error in saving the log entry for unsucessfull submission of mPortal XML.");
                    }
			}
			else {
				//save log and notification if success
				try {
                    log.debug("VZAppZoneApplicationHelper.sendXMLToMPortalServer: success found in mPortalXMLResponse");
                    mPortalXMLLog.setSubmitStatus("Y");
					mPortalXMLLog.setSubmitResponse(mPortalResponseXML);
					mPortalXMLLog.setAppId(aimsApp.getAppsId());
                    mPortalXMLLog.setSubmitDate(currDate);
                    mPortalXMLLog.setXmlType(xmlType);
                    mPortalXMLLog.setBinaryIds(binaryIdsStr);
                    mPortalXMLLog.setBinaryFirmwareIds(binaryFirmwareIdsStr);

                    VZAppZoneApplicationManager.saveMPortalXMLLog(mPortalXMLLog,xmlToSend);

                    //send success notification
                    VZAppZoneApplicationHelper.mPortalSuccessNotification(aimsApp, alliance, vzAppBinaryFirmwareInfoList, binariesList, xmlType, currDate);

                    VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares(aimsApp, vzAppBinaryFirmwareInfoList, xmlType, currDate);

                    //set succes flag
                    success = true;

                } catch (HibernateException e) {
					log.error(e,e);
					log.debug("SUCESS RESPONSE: Error in saving the log entry for successful submission of mPortal XML.");
				}
			}//end of checking success message.
		} catch (Exception e) {
			log.debug("EXCEPTION FOR MPORTAL");
			log.error(e,e);
			//save log for no connection,
            mPortalXMLLog.setSubmitStatus("N");
            mPortalXMLLog.setSubmitResponse(mPortalResponseXML);
            mPortalXMLLog.setAppId(aimsApp.getAppsId());
            mPortalXMLLog.setSubmitDate(currDate);
            mPortalXMLLog.setXmlType(xmlType);
            mPortalXMLLog.setBinaryIds(binaryIdsStr);
            mPortalXMLLog.setBinaryFirmwareIds(binaryFirmwareIdsStr);
           //Save log entry.
           try {
               VZAppZoneApplicationManager.saveMPortalXMLLog(mPortalXMLLog,xmlToSend);
           } catch (HibernateException he) {
               log.error(he,he);
               log.debug("FAILED: Error occurred while saving the log entry for mPortal XML exception.");
           }

			System.out.println("\n\naimsApp.getAppsId(): "+ aimsApp.getAppsId());
			System.out.println("\n\nSending out emails related to MPortal");

            //send email for no connection,
            StringBuffer emailSubject = new StringBuffer("Exception in VZAppZoneApplicationHelper.sendXMLToMPortalServer(), on .... ");
			emailSubject.append(resubmitUrl);

			try {
				MailUtils.sendMail(
								AimsConstants.EMAIL_EXCEPTION_ADMIN,
								"exceptions@netpace.com",
								emailSubject.toString(),
								null,
								"Exception Message:\n"
										+ StringFuncs.NullValueReplacement(e.getMessage())
										+ "\n\nmPortal Message:\n"
										+ StringFuncs.NullValueReplacement(mPortalResponseXML)
										+ "\n\nException Trace:\n"
										+ MiscUtils.getExceptionStackTraceInfo(e)
										+ "\n\nXML Submitted:\n"
										+ xmlToSend);
                  VZAppZoneApplicationHelper.mPortalResubmitNotification(aimsApp, alliance, binaryIdsStr, binaryFirmwareIdsStr, resubmitUrl, xmlType, currDate);
			} catch (Exception xyz) {
				try {
					MailUtils.sendMail(
										AimsConstants.EMAIL_EXCEPTION_ADMIN,
										"exceptions@netpace.com",
										AimsConstants.EMAIL_SUBJECT_RARE_EXCEPTION
										+ " .... on " + resubmitUrl, null, MiscUtils.getExceptionStackTraceInfo(xyz));
				} catch (Exception mailEx) {
					System.out.println("Exception in VZAppZoneAppliationHelper.sendXMLToMPortalServer while sending email");
					log.error(e,e);
				}
			}
		}

        log.debug("============== end VZAppZoneApplicationHelper.sendXMLToMPortalServer, xmlType:"+xmlType+" ==============");
        return success;
    }

    public static boolean isSuccessFoundInMPortalResponseXML(String mPortalResponseXML) throws Exception {
        boolean success = false;
        if(!StringFuncs.isNullOrEmpty(mPortalResponseXML)) {
            Document responseDoc = XMLUtils.loadXML(mPortalResponseXML);
            String responseStatusXPath = "/ResponseDetails/Response/@status";
            Node responseStatusNode = XMLUtils.getNodeByXPath(responseDoc, responseStatusXPath);//response status attribute
            String successValue = "SUCCESS";

            if(responseStatusXPath!=null) {
                success = StringFuncs.NullValueHTMLReplacement(responseStatusNode.getNodeValue()).equals(successValue);
            }
        }
        return success;
    }

    public static String createMPortalXML(AimsApp aimsApp,
                                          AimsVZAppZoneApp vzAppZoneApp,
                                          List vzAppBinaryFirmwareInfoList,
                                          List binariesList,
                                          AimsAllianc alliance,
                                          String binaryDownloadURL,
                                          String mPortalUsername,
                                          String mPortalPassword,
                                          String xmlType) throws Exception {

        StringBuffer xmlBuff = new StringBuffer();
        
		log.debug("(01) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
		
		if(vzAppBinaryFirmwareInfoList!=null && binariesList!=null && binariesList.size()==vzAppBinaryFirmwareInfoList.size() && binariesList.size()>0) {

            log.debug("(02) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
            OMElement rootElement = fac.createOMElement("RequestData", null);
			
			log.debug("(03) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());

            OMNamespace rootElemNS = rootElement.declareNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi");
            rootElement.addAttribute("noNamespaceSchemaLocation", "Request.xsd", rootElemNS);

            OMElement headerElement = fac.createOMElement("Header", null);
                addElement(fac, null, headerElement, "UserName", mPortalUsername);
                addElement(fac, null, headerElement, "Password", mPortalPassword);
                addElement(fac, null, headerElement, "InterfaceName", VZAppZoneApplicationHelper.VZAPPZONE_XML_INTERFACE_SUBMIT);
            rootElement.addChild(headerElement);

            log.debug("(04) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
			
			OMElement requestBodyElement = fac.createOMElement("RequestBody", null);
                OMElement contentElement = fac.createOMElement("Content", null);
                contentElement.addAttribute(VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD, null);
                    addElement(fac, null, contentElement, "ContentId", aimsApp.getAppsId().toString());
                    //appTitle will be used instead of catalog name
                    addElement(fac, null, contentElement, "DisplayName", aimsApp.getTitle());
                    addElement(fac, null, contentElement, "Description", aimsApp.getLongDesc());
                    log.debug("(04a) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
					for(int binaryIdx=0; binaryIdx<binariesList.size(); binaryIdx++) {
                        AimsVZAppBinaries binary = (AimsVZAppBinaries)binariesList.get(binaryIdx);
                        List binaryFirmwareInfoVOList = (List)vzAppBinaryFirmwareInfoList.get(binaryIdx);
                        log.debug("(04b) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
						OMElement assetElement = createAssetElement(fac, binaryFirmwareInfoVOList, binary,
                                aimsApp.getAppsId(), aimsApp.getAimsAllianceId(), binaryDownloadURL, xmlType);
						log.debug("(04c) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
                        if(assetElement!=null) {
                            contentElement.addChild(assetElement);
                        }
                    }
                requestBodyElement.addChild(contentElement);

            log.debug("(05) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
			
			//priceplan
            OMElement pricePlansElement = fac.createOMElement("PricePlans", null);
            OMElement pricePlanElement = fac.createOMElement("PricePlan", null);
            pricePlanElement.addAttribute(VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD, null);
            pricePlanElement.addAttribute("isGeneric", "false", null);

            log.debug("(06) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
			
			if(StringFuncs.isNullOrEmpty(vzAppZoneApp.getOnetimeBilling()) || !vzAppZoneApp.getOnetimeBilling().equals("Y")) {
                addElement(fac, null, pricePlanElement, "Price", "0.0");
                addElement(fac, null, pricePlanElement, "PlanType", VZAppZoneApplicationHelper.VZAPPZONE_XML_PLAN_TYPE_FREE);
            }
            else {
                //vzwRecommendedPrice is used instead of OnetimeBillingPrice
                //addElement(fac, null, pricePlanElement, "Price", vzAppZoneApp.getOnetimeBillingPricepoint());
                addElement(fac, null, pricePlanElement, "Price", vzAppZoneApp.getOnetimeVZWRecommendedPrice());
                addElement(fac, null, pricePlanElement, "PlanType", VZAppZoneApplicationHelper.VZAPPZONE_XML_PLAN_TYPE_ONETIME);

                //add in xnl if not null
                if(!StringFuncs.isNullOrEmpty(vzAppZoneApp.getOnetimeVendorProdDisplay())) {
                    addElement(fac, null, pricePlanElement, "InvoiceDescription", vzAppZoneApp.getOnetimeVendorProdDisplay());
                }
                if(!StringFuncs.isNullOrEmpty(vzAppZoneApp.getOnetimeVendorSplitPercent())) {
                    addElement(fac, null, pricePlanElement, "RevenueSharePercentage", vzAppZoneApp.getOnetimeVendorSplitPercent());
                }
            }
            pricePlansElement.addChild(pricePlanElement);
            contentElement.addChild(pricePlansElement);
			
			log.debug("(07) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());

            //Content Categories
            OMElement contentCategoriesElement = fac.createOMElement("ContentCategories", null);
            if(Utility.ZeroValueReplacement(vzAppZoneApp.getSubCategory1()).longValue()>0) {
                OMElement contentCategoryElement = fac.createOMElement("ContentCategory", null);
                contentCategoryElement.addAttribute(VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD, null);
                addElement(fac, null, contentCategoryElement, "CategoryPath", VZAppZoneApplicationHelper.makeContentCategoryPath(vzAppZoneApp.getSubCategory1()));
                contentCategoriesElement.addChild(contentCategoryElement);
            }
            if(Utility.ZeroValueReplacement(vzAppZoneApp.getSubCategory2()).longValue()>0) {
                OMElement contentCategoryElement2 = fac.createOMElement("ContentCategory", null);
                contentCategoryElement2.addAttribute(VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD, null);
                addElement(fac, null, contentCategoryElement2, "CategoryPath", VZAppZoneApplicationHelper.makeContentCategoryPath(vzAppZoneApp.getSubCategory2()));
                contentCategoriesElement.addChild(contentCategoryElement2);
            }
            if(Utility.ZeroValueReplacement(vzAppZoneApp.getSubCategory3()).longValue()>0) {
                OMElement contentCategoryElement3 = fac.createOMElement("ContentCategory", null);
                contentCategoryElement3.addAttribute(VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD, null);
                addElement(fac, null, contentCategoryElement3, "CategoryPath", VZAppZoneApplicationHelper.makeContentCategoryPath(vzAppZoneApp.getSubCategory3()));
                contentCategoriesElement.addChild(contentCategoryElement3);
            }
            contentElement.addChild(contentCategoriesElement);

			log.debug("(08) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
			
            //content id
            AimsTypes contentType =
                        (AimsTypes) DBHelper.getInstance().load(AimsTypes.class, vzAppZoneApp.getContentType().toString());
            addElement(fac, null, contentElement, "ContentType", contentType.getTypeValue());

            //content Detail
            OMElement contentDetailElement = fac.createOMElement("ContentDetail", null);
            addElement(fac, null, contentDetailElement, "ShortDescription", aimsApp.getShortDesc());
            //send mportal alliance name as vendor name in mportal xml
            addElement(fac, null, contentDetailElement, "VendorName", StringFuncs.NullValueReplacement(alliance.getMportalAllianceName()));

            if(Utility.ZeroValueReplacement(vzAppZoneApp.getContentRating()).longValue()>0) {
                //content Rating
                AimsTypes contentRating =
                            (AimsTypes) DBHelper.getInstance().load(AimsTypes.class, vzAppZoneApp.getContentRating().toString());
                addElement(fac, null, contentDetailElement, "ContentRating", contentRating.getTypeValue());
            }
            contentElement.addChild(contentDetailElement);

            rootElement.addChild(requestBodyElement);

            xmlBuff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            
			xmlBuff.append(rootElement.toString());
			
			log.debug("(09) - " + System.currentTimeMillis() + " - Tracking Bug: createMPortalXML for: " + aimsApp.getAppsId().toString());
        }
        else {
            log.debug("VZAppZoneApplicationHelper.createMPortalXML: size mismatch b/w vzAppBinaryFirmwareInfoList and vzAppBinariesList ");
        }
       return xmlBuff.toString();
    }//end createMPortalXML

    private static OMElement createAssetElement(OMFactory fac, List binaryFirmwareInfoVOList, AimsVZAppBinaries binary,
                                                    Long appsId, Long allianceId,
                                                    String binaryDownloadURL, String xmlType) throws Exception {
        OMElement assetElement = null;
        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        VZAppBinaryFirmwareInfoVO firstBinaryFirmwareInfo = null;

        boolean includeDeviceInXML = true;
        boolean deviceDeletedFromServer = false;
        String assetActionValue = "";

        boolean allDevicesDeleted = false;

        String mPortalDeviceName = "";

        String downloadKey = "";

        Long fileSize = new Long(0);

        String assetTypeValue = "";

        log.debug("(01) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
		
		if(binaryFirmwareInfoVOList!=null && binaryFirmwareInfoVOList.size()>0 && binary!=null) {
            //generate download key
            log.debug("(02) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
			try {
                downloadKey=URLEncoder.encode(MiscUtils.getBase64Digest(binary.getBinaryId().toString(), appsId.toString(), AimsConstants.SECRET_KEY_FOR_BINARY_DOWNLOAD),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.debug("ERROR in generating key for download binary");
                log.error(e,e);
            }
            
			log.debug("(03) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
			
			assetElement = fac.createOMElement("Asset", null);
                //assetElement.addAttribute(VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD, null);

                //set default to windows mobile
                assetTypeValue = AimsConstants.ASSET_TYPE_WINDOWS_MOBILE_VALUE;

                //set assetType value of first binaryFirmware Device, (if it is not empty)
                firstBinaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)binaryFirmwareInfoVOList.get(0);
                if(Utility.ZeroValueReplacement(firstBinaryFirmwareInfo.getAssetType()).longValue()>0
                        && !StringFuncs.isNullOrEmpty(firstBinaryFirmwareInfo.getAssetTypeValue())) {
                    assetTypeValue = firstBinaryFirmwareInfo.getAssetTypeValue();
                }
                assetElement.addAttribute("type", assetTypeValue, null);

                log.debug("(04) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
				
				addElement(fac, null, assetElement, "AssetId", binary.getBinaryId().toString());
                addElement(fac, null, assetElement, "AssetVersion", binary.getBinaryVersion());

                //check allDevicesDeleted before including devices in xml
                allDevicesDeleted = VZAppZoneApplicationHelper.checkDeletedFlagForAllBinaryFirmwares(binaryFirmwareInfoVOList, xmlType);

                //iterate all devices of this binary to create device node in Asset
                for(int binaryFirmwareIdx=0; binaryFirmwareIdx<binaryFirmwareInfoVOList.size(); binaryFirmwareIdx++) {
                    
					log.debug("(05 Loop) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
					
					binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)binaryFirmwareInfoVOList.get(binaryFirmwareIdx);
                    //by default include device in xml with assetActionValue=add
                    includeDeviceInXML = true;
                    assetActionValue = VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD;

                    log.debug("(06 Loop) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());

                    if(allDevicesDeleted) {
                        //if all devices of this binary are marked as deleted,
                        //then include all devices in xml, with action = delete
                        //also applicable when binary has only one device, and its deleted flag is Y
                        assetActionValue = VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_DELETE;
                    }
                    else {
                        deviceDeletedFromServer = VZAppZoneApplicationHelper.checkDeletedFlag(binaryFirmwareInfo, xmlType);
                        if(deviceDeletedFromServer) {
                            //do not include this device in xml, if binary has multiple devices, and deleted flag for this device is Y
                            includeDeviceInXML = false;
                        }
                    }

                    log.debug("(07 Loop) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
					
					if(includeDeviceInXML) {
                        if(!StringFuncs.isNullOrEmpty(binaryFirmwareInfo.getMportalDeviceName())) {
                            //set mportal device name, if mPortal device name found in device
                            mPortalDeviceName = binaryFirmwareInfo.getMportalDeviceName();
                        }
                        else {
                            //otherwise set it to default name
                            mPortalDeviceName = VZAppZoneApplicationHelper.VZAPPZONE_XML_DEFAULT_DEVICE_NAME;
                        }

                        log.debug("(08 Loop) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
						
						VZAppZoneApplicationHelper.addElementWithAttribute(fac, null, assetElement,
                                "Device", VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD,
                                mPortalDeviceName+binaryFirmwareInfo.getMrNumber());
						
						log.debug("(09 Loop) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
						
                    }//end includeDeviceInXML
                }//end for
                assetElement.addAttribute(VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, assetActionValue, null);

                VZAppZoneApplicationHelper.addElementWithAttribute(fac, null, assetElement,
                            "Language", VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_NAME_ADD, VZAppZoneApplicationHelper.VZAPPZONE_XML_ACTION_ADD,
                            VZAppZoneApplicationHelper.VZAPPZONE_XML_LANGUAGE);

                log.debug("(10) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());

				
				log.debug("(11a) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());


					log.debug("(11b) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
					
                    if(Utility.ZeroValueReplacement(binary.getBinaryFileSizeInBytes()).longValue()>0) {
                        fileSize = binary.getBinaryFileSizeInBytes();
                    }
                    else {
                        log.debug("VZAppZoneApplicationHelper.createAssetElement: binary file size is null or 0");
                    }
                    
					log.debug("(12) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
					
					OMElement assetDetailsElement = fac.createOMElement("AssetDetails", null);

                    String assetMimeType =  VZAppZoneApplicationHelper.getVZAppMimeType(binary.getBinaryFileFileName());
                    if(StringFuncs.isNullOrEmpty(assetMimeType)) {
                        //if mimetype not found in map, then send mimetype of binary file stored in database
                        assetMimeType =  binary.getBinaryFileContentType();
                        log.debug("VZAppZoneApplicationHelper.createAssetElement: mimeType not found for binary file, sending default mimeType for "+binary.getBinaryFileFileName());
                    }

                    addElement(fac, null, assetDetailsElement, "AssetLocation", binaryDownloadURL
                                                                                    +"?type=binary&id="+binary.getBinaryId()
                                                                                    +"&key="+downloadKey);
                    addElement(fac, null, assetDetailsElement, "AssetSize", fileSize.toString());
                    addElement(fac, null, assetDetailsElement, "AssetMimeType", assetMimeType);
                    assetElement.addChild(assetDetailsElement);
					
					log.debug("(13) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());


                //reset filesize
                fileSize = new Long(0);

				log.debug("(14) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
				
                //if preview file exists, then make AssetPreviewDetails Node
                if(!StringFuncs.isNullOrEmpty(binary.getPreviewFileFileName())
                        && !StringFuncs.isNullOrEmpty(binary.getPreviewFileContentType())) {
                    
					log.debug("(15) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());

					
					log.debug("(16) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());


						log.debug("(17) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
						
                        if(Utility.ZeroValueReplacement(binary.getPreviewFileSizeInBytes()).longValue()>0) {
                            fileSize = binary.getPreviewFileSizeInBytes();
                        }
                        else {
                            log.debug("VZAppZoneApplicationHelper.createAssetElement: preview file size is null or 0");
                        }
                        
						log.debug("(18) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
						
						OMElement assetPreviewDetailsElement = fac.createOMElement("AssetPreviewDetails", null);

                        String previewMimeType =  VZAppZoneApplicationHelper.getVZAppMimeType(binary.getPreviewFileFileName());
                        if(StringFuncs.isNullOrEmpty(previewMimeType)) {
                            //if mimetype not found in map, then send mimetype of preview file stored in database
                            previewMimeType =  binary.getPreviewFileContentType();
                            log.debug("VZAppZoneApplicationHelper.createAssetElement: mimeType not found for preview file, sending default mimeType for "+binary.getPreviewFileFileName());
                        }

                        log.debug("(19) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());
						
						addElement(fac, null, assetPreviewDetailsElement, "PreviewLocation", binaryDownloadURL
                                                                                            +"?type=preview&id="+binary.getBinaryId()
                                                                                            +"&key="+downloadKey);
                        addElement(fac, null, assetPreviewDetailsElement, "PreviewSize", fileSize.toString());
                        addElement(fac, null, assetPreviewDetailsElement, "PreviewMimeType", previewMimeType);
                        assetElement.addChild(assetPreviewDetailsElement);
						
						log.debug("(20) - " + System.currentTimeMillis() + " - Tracking Bug: createAssetElement for: " + appsId.toString());


                }//end if previewFilename
        }
        else {
            log.debug("VZAppZoneApplicationHelper.createAssetElement: can not create asset element, either binary or binaryFirmwareInfoVOList is null");
        }
        return assetElement;
    }

    public static boolean checkDeletedFlag(VZAppBinaryFirmwareInfoVO binaryFirmware, String xmlType) {
        boolean deleted = false;
        if(!StringFuncs.isNullOrEmpty(xmlType)) {
            if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA)
                && (StringFuncs.NullValueReplacement(binaryFirmware.getOtaDeleted()).equals(AimsConstants.YES_CHAR))) {
                deleted = true;
            }
            else if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_STAGE)
                    && (StringFuncs.NullValueReplacement(binaryFirmware.getStageDeleted()).equals(AimsConstants.YES_CHAR))) {
                deleted = true;
            }
            else if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD)
                    && (StringFuncs.NullValueReplacement(binaryFirmware.getProdDeleted()).equals(AimsConstants.YES_CHAR))) {
                deleted = true;
            }
        }
        return deleted;
    }

    public static boolean checkDeletedFlagForAllBinaryFirmwares(List binaryFirmwareInfoVOList, String xmlType) {
        boolean allDeleted = true;
        boolean deviceDeletedFromServer = false;
        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        if(binaryFirmwareInfoVOList!=null && binaryFirmwareInfoVOList.size()>0) {
            for(int binaryFirmwareIdx=0; binaryFirmwareIdx<binaryFirmwareInfoVOList.size(); binaryFirmwareIdx++) {
                binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)binaryFirmwareInfoVOList.get(binaryFirmwareIdx);
                deviceDeletedFromServer = VZAppZoneApplicationHelper.checkDeletedFlag(binaryFirmwareInfo, xmlType);
                //if atleast one device found which is not marked as deleted, then set allDeleted to false
                if(!deviceDeletedFromServer) {
                    allDeleted = false;
                    break;
                }
            }//end for
        }
        else {
            log.debug("VZAppZoneAppliationHelper.checkDeletedFlagForAllBinaryFirmwares: binaryFirmwareInfoVOList is null or empty, sending allDeleted as false");
        }
        return allDeleted;
    }

    public static String makeContentCategoryPath(Long subCategoryId) {
        StringBuffer categoryPath = new StringBuffer();
        AimsAppSubCategory subCategory = null;
        AimsAppCategory category = null;
        try {
            subCategory = (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, subCategoryId.toString());
            category = (AimsAppCategory) DBHelper.getInstance().load(AimsAppCategory.class, subCategory.getAimsAppCategoryId().toString());
            //content_category_prefix/category/subcategory
            categoryPath.append(VZAppZoneApplicationHelper.VZAPPZONE_CONTENT_CATEGORY_PREFIX);
            categoryPath.append("/").append(category.getCategoryName());
            if(!(subCategory.getSubCategoryName().equals(AimsConstants.NOT_APPLICABLE_VALUE))) {
                categoryPath.append("/").append(subCategory.getSubCategoryName());
            }
        }
        catch(HibernateException he) {
            log.debug("VZAppZoneApplicationHelper.makeContentCategoryPath: EXCEPTION occured while making content category path");
            log.error(he, he);
        }
        return categoryPath.toString();
    }//end makeContentCategoryPath


    public static void saveXMLToDir(String xmlData, String vzAppZoneTempDirName,
                                    String xmlType, String applicationTitle) throws IOException {
        File vzAppZoneTempDirectory = new File(vzAppZoneTempDirName);
        File vzAppZoneXMLFile = null;

        FileWriter fWriter = null;
        BufferedWriter bufWriter = null;
        String xmlFileName = "";
        Date currentDate = null;
        SimpleDateFormat dateFormat = null;

        if(!vzAppZoneTempDirectory.exists()) {
            //if directory does not exists then create it
            vzAppZoneTempDirectory.mkdir();
            log.debug("vzAppZoneTempDirectory created on path : "+vzAppZoneTempDirectory.getAbsolutePath());
        }

        currentDate = new Date();
        dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
        //applicationTitle_currentDate_FileName.xml
        xmlFileName = applicationTitle+"_"+xmlType+"_"+dateFormat.format(currentDate)+"_"+xmlFileName;
        vzAppZoneXMLFile = new File(vzAppZoneTempDirectory, xmlFileName);

        try {
            fWriter = new FileWriter(vzAppZoneXMLFile);
            bufWriter =  new BufferedWriter(fWriter);
            bufWriter.write(xmlData);
        }
        catch (IOException ioe) {

        }
        finally {
            if(bufWriter!=null) {
                bufWriter.close();
            }
            if(fWriter!=null) {
                fWriter.close();
            }
        }
    }

    public static int getFileSize(InputStream is) throws Exception {
        int length = 0;
        ByteArrayInputStream bis = null;
        if(is!=null) {
            try {
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    length+= bytesRead;
                }
            }
            catch(IOException ioe) {
                System.out.println("IOException occured while reading file size");
                ioe.printStackTrace();
                throw ioe;
            }
            catch(Exception e) {
                System.out.println("Exception occured while reading file size");
                e.printStackTrace();
                throw e;
            }
            finally {
                is.close();
            }
        }
        return length;
    }

    public static Long[] getFirmwareIdsFromAimsVZAppBinaryFirmwares(List aimsVZAppBinaryFirmwareList) {
        Long[] firmwareIds = null;
        AimsVZAppBinaryFirmware binaryFirmware = null;
        if(aimsVZAppBinaryFirmwareList !=null && aimsVZAppBinaryFirmwareList.size()>0) {
            firmwareIds = new Long[aimsVZAppBinaryFirmwareList.size()];
            for(int firmwareIdIndex=0; firmwareIdIndex<aimsVZAppBinaryFirmwareList.size(); firmwareIdIndex++) {
                binaryFirmware = (AimsVZAppBinaryFirmware)aimsVZAppBinaryFirmwareList.get(firmwareIdIndex);
                firmwareIds[firmwareIdIndex] = binaryFirmware.getFirmwareId();
            }
        }
        return firmwareIds;
    }

    public static FirmwareInfoVO getFirmwareInfoVOFromList(List firmwareInfoList, Long firmwareId) {
        FirmwareInfoVO firmwareInfo = null;
        FirmwareInfoVO tempFirmware = null;
        if(firmwareInfoList!=null && firmwareInfoList.size()>0 && firmwareId!=null) {
            for(int firmwareIndex=0; firmwareIndex<firmwareInfoList.size(); firmwareIndex++) {
                tempFirmware = (FirmwareInfoVO)firmwareInfoList.get(firmwareIndex);
                if(firmwareId.equals(tempFirmware.getFirmwareId())) {                    
                    firmwareInfo = tempFirmware;
                    break;
                }
            }
        }
        return firmwareInfo;
    }

    public static void setupVZAppSections(VZAppZoneApplicationUpdateForm vzAppZoneApplicationForm,
                                          List vzAppBinaryFirmwarePhaseList,
                                          AimsApp aimsApp, AimsVZAppZoneApp vzAppZoneApp,
                                          boolean hasAccessBasicTesting, boolean hasAccessApplicationManagement, boolean hasAccessOTATesting, boolean hasAccessMoveToStaging, boolean hasAccessMoveToProd, boolean hasAccessSunset) throws Exception {
        List basicTestInfoList = new ArrayList();
        List otaTestInfoList = new ArrayList();
        List stageInfoList = new ArrayList();
        List prodInfoList = new ArrayList();
        List binaryFirmwareInfoList = new ArrayList(); //info list to show section labels in testing sections

        VZAppBinaryFirmwarePhaseInfoVO binaryFirmwarePhase = null;
        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;

        VZAppBaseTestVO basicTest = null;
        VZAppZoneOTATestVO otaTest = null;
        VZAppZoneStageInfoVO stageInfo = null;
        VZAppZoneProdInfoVO prodInfo = null;

        Long binaryFirmwareStatus = null;
        if(vzAppBinaryFirmwarePhaseList!=null && vzAppBinaryFirmwarePhaseList.size()>0) {
            for(int binaryFirmwareIndex=0; binaryFirmwareIndex<vzAppBinaryFirmwarePhaseList.size(); binaryFirmwareIndex++) {
                binaryFirmwarePhase = (VZAppBinaryFirmwarePhaseInfoVO)vzAppBinaryFirmwarePhaseList.get(binaryFirmwareIndex);
                binaryFirmwareStatus = binaryFirmwarePhase.getBinaryFirmwareStatus();
                binaryFirmwareInfo = new VZAppBinaryFirmwareInfoVO();
                //copy fields
                BeanUtils.copyProperties(binaryFirmwareInfo, binaryFirmwarePhase);
                binaryFirmwareInfoList.add(binaryFirmwareInfo);

                //Device Phases (Testing)
                if (hasAccessBasicTesting) {
                    basicTest = new VZAppBaseTestVO();
                    basicTest.setBinaryFirmwareId(binaryFirmwarePhase.getBinaryFirmwareId());
                    basicTest.setBaseTestedDate(binaryFirmwarePhase.getBaseTestedDate());
                    basicTest.setBaseTestStatus(binaryFirmwarePhase.getBaseTestStatus());
                    basicTest.setBaseComments(binaryFirmwarePhase.getBaseComments());
                    basicTest.setBaseResultsFileFileName(binaryFirmwarePhase.getBaseResultsFileFileName());
                    basicTest.setBaseResultsFileTempFileId(new Long(0));
                    if(binaryFirmwareStatus!=null && VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveTestPassed(binaryFirmwareStatus)) {
                        basicTest.setDisableBaseTest(new Boolean(true));
                    }
                    else {
                        basicTest.setDisableBaseTest(new Boolean(false));
                    }
                    basicTestInfoList.add(basicTest);
                }
                if(VZAppZoneApplicationHelper.isStatusEqualOrAboveTestPassed(aimsApp.getAimsLifecyclePhaseId())) {
                    //OTA Testing Phase
                    if (hasAccessOTATesting) {
                        otaTest = new VZAppZoneOTATestVO();
                        otaTest.setBinaryFirmwareId(binaryFirmwarePhase.getBinaryFirmwareId());
                        otaTest.setOtaTestedDate(binaryFirmwarePhase.getOtaTestedDate());
                        otaTest.setOtaTestStatus(binaryFirmwarePhase.getOtaTestStatus());
                        otaTest.setOtaComments(binaryFirmwarePhase.getOtaComments());
                        otaTest.setOtaResultsFileFileName(binaryFirmwarePhase.getOtaResultsFileFileName());
                        otaTest.setOtaResultsFileTempFileId(new Long(0));
                        if(binaryFirmwareStatus!=null) {
                            //if binary status is test passed or above and not ota test passed/failed then do not disable move to ota
                            if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveTestPassed(binaryFirmwareStatus)
                                    && !(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(binaryFirmwareStatus))
                                    && !(binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED))) {
                                otaTest.setDisableMoveToOTATesting(new Boolean(false));
                            }
                            else {
                                otaTest.setDisableMoveToOTATesting(new Boolean(true));
                            }

                            if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATesting(binaryFirmwareStatus)
                                && !(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(binaryFirmwareStatus))) {
                                otaTest.setDisableOTATest(new Boolean(false));
                            }
                            else {
                                otaTest.setDisableOTATest(new Boolean(true));
                            }
                        }
                        otaTestInfoList.add(otaTest);
                    }
                    if(VZAppZoneApplicationHelper.isStatusEqualOrAboveOTATestPassed(aimsApp.getAimsLifecyclePhaseId())) {
                        //Stage Info Section
                        if (hasAccessMoveToStaging) {
                            stageInfo = new VZAppZoneStageInfoVO();
                            stageInfo.setBinaryFirmwareId(binaryFirmwarePhase.getBinaryFirmwareId());
                            stageInfo.setStageMovedDate(binaryFirmwarePhase.getStageMovedDate());
                            if(binaryFirmwareStatus!=null) {
                                if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(binaryFirmwareStatus)) {
                                    stageInfo.setDisableMoveToStaging(new Boolean(false));
                                }
                                else {
                                    stageInfo.setDisableMoveToStaging(new Boolean(true));
                                }
                            }
                            stageInfoList.add(stageInfo);
                        }

                        //Prod Info Section
                        if (hasAccessMoveToProd) {
                            prodInfo = new VZAppZoneProdInfoVO();
                            prodInfo.setBinaryFirmwareId(binaryFirmwarePhase.getBinaryFirmwareId());
                            prodInfo.setProdMovedDate(binaryFirmwarePhase.getProdMovedDate());
                            if(binaryFirmwareStatus!=null) {
                                if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(binaryFirmwareStatus)) {
                                    prodInfo.setDisableMoveToProd(new Boolean(false));
                                }
                                else {
                                    prodInfo.setDisableMoveToProd(new Boolean(true));
                                }
                            }
                            prodInfoList.add(prodInfo);
                        }
                    }//end if ota test passed
                }//end if test passed
            }//end for

            //binaryFirmwareInfoList and xxxSectionVOList must have same size
            if(binaryFirmwareInfoList.size()>0) {
                vzAppZoneApplicationForm.setVZAppBinaryFirmwareInfoVOs(binaryFirmwareInfoList);
            }

            //set form fields
            if(hasAccessBasicTesting && basicTestInfoList.size()>0) {
                vzAppZoneApplicationForm.setVZAppBaseTests(basicTestInfoList);
            }

            if(hasAccessOTATesting && otaTestInfoList.size()>0) {
                vzAppZoneApplicationForm.setVZAppZoneOTATests(otaTestInfoList);
            }

            if(hasAccessMoveToStaging && stageInfoList.size()>0) {
                vzAppZoneApplicationForm.setVZAppZoneStageInfoVOs(stageInfoList);
            }
            if(hasAccessMoveToProd && prodInfoList.size()>0) {
                vzAppZoneApplicationForm.setVZAppZoneProdInfoVOs(prodInfoList);
            }
        }//end if null
    }//end setupVZAppSections

    public static void sendXmlToIntertek(final AimsAllianc alliance,
    									 final AimsContact adminContact,
    									 final AimsApp app,
    									 final List binaryFirmwareInfoList,
    									 final AimsVZAppBinaries binary,
    									 boolean sync) {

		if (sync) {
			VZAppZoneApplicationHelper.sendXmlToIntertek(alliance, adminContact, app, binaryFirmwareInfoList, binary);

		} else {
			new Thread() {
				public void run() {
					VZAppZoneApplicationHelper.sendXmlToIntertek(alliance, adminContact, app, binaryFirmwareInfoList, binary);
				}
			}.start();
		}
	}

    private static void addElement(OMFactory fac, OMNamespace omNs, OMElement parentNode, String elementName, String text){
		OMElement element = fac.createOMElement(elementName, omNs);
		element.addChild(fac.createOMText(element, text));
		parentNode.addChild(element);
    }
    private static void addElementWithAttribute(OMFactory fac, OMNamespace omNs, OMElement parentNode,
                                                String elementName, String attributeName, String attributeValue, String text){
		OMElement element = fac.createOMElement(elementName, omNs);
        element.addAttribute(attributeName, attributeValue, omNs);
        element.addChild(fac.createOMText(element, text));
		parentNode.addChild(element);
    }
    private static void addElement(OMFactory fac, OMElement parentNode, String elementName){
		OMElement element = fac.createOMElement(elementName, null);
		parentNode.addChild(element);
    }

    public synchronized static void sendXmlToIntertek(AimsAllianc alliance,
    									 AimsContact adminContact,
    									 AimsApp app,
    									 List binaryFirmwareInfoList,
    									 AimsVZAppBinaries binary) {
    	log.debug("VZAppZoneApplicationHelper.sendXmlToIntertek Start, binaryID: "+binary.getBinaryId());
		
		log.debug("(01) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
		
		ConfigEnvProperties props=ConfigEnvProperties.getInstance();
		String binaryUrl=props.getProperty("vzappzone.binary.download.URL");
		String endPointRef=props.getProperty("vzappzone.intertek.endpoint.URL");
		String resubmitUrl=props.getProperty("vzappzone.intertek.resubmit.URL");
		String returnValue=null;
		
		log.debug("(02) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());

		AimsVzappIntertekWsLog wsLog=new AimsVzappIntertekWsLog();
		Date currDate=new Date();
		EndpointReference targetEPR = new EndpointReference(endPointRef);
		StringBuffer binaryFirmwareIds=new StringBuffer();
		String downloadKey="";
		
		log.debug("(03) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
		
		try {
			downloadKey=URLEncoder.encode(MiscUtils.getBase64Digest(binary.getBinaryId().toString(), app.getAppsId().toString(), AimsConstants.SECRET_KEY_FOR_BINARY_DOWNLOAD),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.debug("ERROR in generating key for download binary");
			log.error(e,e);
		}
		
		log.debug("(04) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
		
        log.debug("VZAppZoneApplicationHelper.sendXmlToIntertek before creating factory, binaryID: "+binary.getBinaryId());
		OMFactory fac = OMAbstractFactory.getOMFactory();
        log.debug("VZAppZoneApplicationHelper.sendXmlToIntertek after creating factory, binaryID: "+binary.getBinaryId());
		
		log.debug("(05) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
		
        OMNamespace omNs = fac.createOMNamespace("http://webservices.qpqa.com/xsd", "ns0");
		OMElement result=null;
		OMElement rootElement = fac.createOMElement("binaryInfo", omNs);
		
		log.debug("(06) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());

		addElement(fac, omNs, rootElement, "date", Utility.convertToString(currDate, AimsConstants.DATE_FORMAT));
		addElement(fac, omNs, rootElement, "companyID", alliance.getAllianceId().toString());
		addElement(fac, omNs, rootElement, "vendorID", alliance.getVendorId().toString());
		addElement(fac, omNs, rootElement, "allianceName", alliance.getCompanyName());
		addElement(fac, omNs, rootElement, "allianceAddr", alliance.getStreetAddress1()+" "+StringFuncs.NullValueReplacement(alliance.getSteetAddress2()) );
		addElement(fac, omNs, rootElement, "allianceCity", alliance.getCity());
		addElement(fac, omNs, rootElement, "allianceState", alliance.getState());
		addElement(fac, omNs, rootElement, "allianceZip", alliance.getZipCode());
		addElement(fac, omNs, rootElement, "allianceCountry", alliance.getCountry());
		addElement(fac, omNs, rootElement, "allianceAuthorizedRep", alliance.getAuthRepName());
		addElement(fac, omNs, rootElement, "allianceAdminName", adminContact.getFirstName() + " "+adminContact.getLastName());
		addElement(fac, omNs, rootElement, "allianceAdminPhone", adminContact.getPhone());
		addElement(fac, omNs, rootElement, "allianceAdminEmail", adminContact.getEmailAddress());

		addElement(fac, omNs, rootElement, "applicationID", app.getAppsId().toString());
		addElement(fac, omNs, rootElement, "applicationTitle", app.getTitle());
		addElement(fac, omNs, rootElement, "applicationVersion", app.getVersion());
		
		log.debug("(07) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
		
		for (int i=0; i<binaryFirmwareInfoList.size(); i++){
			VZAppBinaryFirmwareInfoVO vo=(VZAppBinaryFirmwareInfoVO) binaryFirmwareInfoList.get(i);
			OMElement deviceElement=fac.createOMElement("device",omNs);
			addElement(fac, omNs, deviceElement, "deviceUUID", vo.getBinaryFirmwareId().toString());
			addElement(fac, omNs, deviceElement, "deviceName", vo.getPhoneModel());
            addElement(fac, omNs, deviceElement, "deviceOS", vo.getAssetTypeValue());            
            addElement(fac, omNs, deviceElement, "firmwareName", vo.getFirmwareName());
			addElement(fac, omNs, deviceElement, "MR", vo.getMrNumber());
			rootElement.addChild(deviceElement);

			if (i < binaryFirmwareInfoList.size()-1){
				binaryFirmwareIds.append(vo.getBinaryFirmwareId()+",");
			}
			else {
				binaryFirmwareIds.append(vo.getBinaryFirmwareId());
			}
		}
		
		log.debug("(08) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
		
		addElement(fac, omNs, rootElement, "binaryID", binary.getBinaryId().toString());
		addElement(fac, omNs, rootElement, "fileName", binary.getBinaryFileFileName());
		addElement(fac, omNs, rootElement, "fileVer", binary.getBinaryVersion());
		addElement(fac, omNs, rootElement, "fileDownloadPath", binaryUrl+"?type=binary&id="+binary.getBinaryId().toString()+"&key="+downloadKey);

		log.debug("(09) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
		
		try {
			log.debug("\n\n"+rootElement+"\n\n");
			Options options = new Options();
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setTimeOutInMilliSeconds(10000);

			ServiceClient sender = new ServiceClient();
			
			log.debug("(10) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
			
			sender.setOptions(options);
			
			//log.debug("\n\nEntering synchronized block...");
			
			log.debug("(11) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
			result = sender.sendReceive(rootElement);
			log.debug("(12) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
			
			//log.debug("\n\nExisting synchronized block...");
				

			returnValue = result.getFirstElement().getText();
			
			log.debug("(13) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
			
			//No success message
			if (!"success".equalsIgnoreCase(returnValue)){
				//Save log entry.
				try {
					log.debug("(14) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
					wsLog.setSubmitStatus("N");
					wsLog.setSubmitResponse(result.toString());
					wsLog.setAppId(app.getAppsId());
					wsLog.setSubmitDate(currDate);
					wsLog.setBinaryFirmwareIds(binaryFirmwareIds.toString());
					VZAppZoneApplicationManager.saveIntertekServiceResponse(wsLog,rootElement.toString());
					resubmitNotification(alliance, app, binary, resubmitUrl, currDate, binaryFirmwareIds);
					log.debug("(15) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());					
				} catch (HibernateException e) {
					log.error(e,e);
					log.debug("NO SUCESS RESPONSE: Error in saving the log entry for unsucessfull webserivce call.");
				}
			}
			else {
				//Save log entry.
				try {
					log.debug("(16) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
					wsLog.setSubmitStatus("Y");
					wsLog.setSubmitResponse(result.toString());
					wsLog.setAppId(app.getAppsId());
					wsLog.setSubmitDate(currDate);
					wsLog.setBinaryFirmwareIds(binaryFirmwareIds.toString());
					VZAppZoneApplicationManager.saveIntertekServiceResponse(wsLog,rootElement.toString());
					log.debug("(17) - " + System.currentTimeMillis() + " - Tracking Bug: sendXmlToIntertek for: " + app.getAppsId().toString());
				} catch (HibernateException e) {
					log.error(e,e);
					log.debug("SUCESS RESPONSE: Error in saving the log entry for successful submission of webserivce");
				}
			}//end of checking success message.
		} catch (Exception e) {
			log.debug("EXCEPTION FOR INTERTEK");
			log.error(e,e);
			wsLog.setSubmitStatus("N");
			wsLog.setSubmitResponse(result!=null?result.toString():"");
			wsLog.setAppId(app.getAppsId());
			wsLog.setSubmitDate(currDate);
			wsLog.setBinaryFirmwareIds(binaryFirmwareIds.toString());

			//Save log entry.
			try {
				VZAppZoneApplicationManager.saveIntertekServiceResponse(wsLog,rootElement.toString());
			} catch (HibernateException he) {
				log.error(he,he);
				log.debug("FAILED: Error occurred while saving the log entry for webservice exception.");
			}

			System.out.println("\n\naimsApp.getAppsId(): "+ app.getAppsId());
			System.out.println("\n\nSending out emails related to Intertek");

			StringBuffer emailSubject = new StringBuffer("Exception in VZAppZoneApplicationHelper.sendXmlToIntertek(), on .... ");
			emailSubject.append(resubmitUrl);

			try {
				MailUtils.sendMail(
								AimsConstants.EMAIL_EXCEPTION_ADMIN,
								"exceptions@netpace.com",
								emailSubject.toString(),
								null,
								"Exception Message:\n"
										+ StringFuncs.NullValueReplacement(e.getMessage())
										+ "\n\nIntertek Message:\n"
										+ StringFuncs.NullValueReplacement(result!=null?result.toString():"")
										+ "\n\nException Trace:\n"
										+ MiscUtils.getExceptionStackTraceInfo(e)
										+ "\n\nXML Submitted:\n"
										+ rootElement);

				resubmitNotification(alliance, app, binary, resubmitUrl, currDate, binaryFirmwareIds);
			} catch (Exception xyz) {
				try {
					MailUtils.sendMail(
										AimsConstants.EMAIL_EXCEPTION_ADMIN,
										"exceptions@netpace.com",
										AimsConstants.EMAIL_SUBJECT_RARE_EXCEPTION
										+ " .... on " + resubmitUrl, null, MiscUtils.getExceptionStackTraceInfo(xyz));
				} catch (Exception mailEx) {
					System.out.println("Exception in VZAppZoneAppliationHelper while sending email");
					log.error(e,e);
				}
			}
		}

		log.debug("VZAppZoneApplicationHelper.sendXmlToIntertek End, binaryID: "+binary.getBinaryId());
	}

	private static void resubmitNotification(AimsAllianc alliance, AimsApp app,
			AimsVZAppBinaries binary, String requestUrl, Date currDate,
			StringBuffer idsIfError)throws Exception {
		AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_RESUBMIT_INTERTEK_XML_URL);
		String key=MiscUtils.getBase64Digest(
									(app.getAppsId().toString()+binary.getBinaryId().toString()).getBytes(), 
									idsIfError.toString().getBytes(), 
									AimsConstants.INTERTEK_KEY_FOR_RESUBMIT_URL.getBytes());
		if (aimsEvent != null) {
			AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
			aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance.getAllianceId().toString());
			aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, app.getAppsId().toString());					
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, alliance.getCompanyName());
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, currDate.toString());
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, app.getTitle());
			aimsEventObject.setProperty(
							AimsNotificationConstants.PLACE_HOLDER_RESUBMIT_INTERTEK_URL,
							requestUrl
									+ "/intertekResubmit.do?app_id="+app.getAppsId().toString()
									+ "&bf_id="+ URLEncoder.encode(idsIfError.toString(),"UTF-8")
									+ "&b_id="+ binary.getBinaryId().toString()
									+ "&other_info="+ URLEncoder.encode(key,"UTF-8"));
									
			log.debug("Intertek Resubmit URL: "+aimsEventObject.getProperty(AimsNotificationConstants.PLACE_HOLDER_RESUBMIT_INTERTEK_URL));
			aimsEvent.raiseEvent(aimsEventObject);
		}
	}

    private static void mPortalResubmitNotification(AimsApp aimsApp, AimsAllianc alliance,
                                                    String binaryIds, String binaryFirmwareIds,
                                                    String requestUrl, String xmlType, Date currDate)throws Exception {
		log.debug("--------- VZAppZoneApplicationHelper.mPortalResubmitNotification ends.... ---------");
        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_RESUBMIT_MPORTAL_XML_URL);
		String key=MiscUtils.getBase64Digest(
									(aimsApp.getAppsId().toString()+binaryIds+xmlType).getBytes(),
									binaryFirmwareIds.getBytes(),
									AimsConstants.MPORTAL_KEY_FOR_RESUBMIT_URL.getBytes());
		if (aimsEvent != null) {
			AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
			aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance.getAllianceId().toString());
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, alliance.getCompanyName());
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, aimsApp.getVersion());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, currDate.toString());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MPORTAL_XML_TYPE, xmlType);
            aimsEventObject.setProperty(
							AimsNotificationConstants.PLACE_HOLDER_RESUBMIT_MPORTAL_URL,
							requestUrl
									+ "/mPortalResubmit.do?app_id="+aimsApp.getAppsId().toString()
                                    + "&xml_type="+xmlType
                                    + "&bf_id="+ URLEncoder.encode(binaryFirmwareIds,"UTF-8")
									+ "&b_id="+ URLEncoder.encode(binaryIds,"UTF-8")
									+ "&other_info="+ URLEncoder.encode(key,"UTF-8"));

			log.debug("MPortal Resubmit URL: "+aimsEventObject.getProperty(AimsNotificationConstants.PLACE_HOLDER_RESUBMIT_MPORTAL_URL));
			aimsEvent.raiseEvent(aimsEventObject);
		}
        log.debug("--------- VZAppZoneApplicationHelper.mPortalResubmitNotification ends.... ---------");
    }//end mPortalResubmitNotification

    private static void mPortalSuccessNotification(AimsApp aimsApp, AimsAllianc alliance,
                                                   List vzAppBinaryFirmwareInfoList, List vzAppBinariesList,
                                                   String xmlType,
                                                   Date currDate)throws Exception {
        log.debug("--------- VZAppZoneApplicationHelper.mPortalSuccessNotification starts.... ---------");
        //mPortal Success notification
        if(vzAppBinaryFirmwareInfoList!=null && vzAppBinariesList!=null && vzAppBinariesList.size()==vzAppBinaryFirmwareInfoList.size() && vzAppBinariesList.size()>0) {
            for(int binaryIdx=0; binaryIdx<vzAppBinaryFirmwareInfoList.size(); binaryIdx++) {
                //iterate first to send notifications
                List binaryFirmwareInfoVOList = (List)vzAppBinaryFirmwareInfoList.get(binaryIdx);
                AimsVZAppBinaries binary = (AimsVZAppBinaries)vzAppBinariesList.get(binaryIdx);
                if(binary!=null && binaryFirmwareInfoVOList!=null && binaryFirmwareInfoVOList.size()>0) {
                    VZAppZoneApplicationHelper.mPortalSuccessNotification(aimsApp, alliance, binaryFirmwareInfoVOList, binary, xmlType, currDate);
                }
                else {
                    log.debug("ERROR in VZAppZoneApplicationHelper.mPortalSuccessNotification: can not send notification, either binary or binaryFirmwareInfoVOList is null");
                }
            }//end for
        }
        else {
            log.debug("ERROR in VZAppZoneApplicationHelper.mPortalSuccessNotification: size mismatch b/w vzAppBinaryFirmwareInfoList and vzAppBinariesList ");
        }
        log.debug("--------- VZAppZoneApplicationHelper.mPortalSuccessNotification ends.... ---------");
    }//end mPortalSuccessNotification

    private static void mPortalSuccessNotification(AimsApp aimsApp, AimsAllianc alliance,
                                                   List vzAppBinaryFirmwareInfoVOList, AimsVZAppBinaries binary,
                                                   String xmlType,
                                                   Date currDate)throws Exception {

        AimsEventLite aimsEvent = null;

        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_MPORTAL_XML_SUCCESS);

        if (aimsEvent != null) {
            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
            //make deviceUUIDs and Device Names
            //ignore deleted device for this xmlType
            String deviceUUIDs = VZAppZoneApplicationHelper.getFormattedDeviceUUIDs(vzAppBinaryFirmwareInfoVOList, xmlType, true);
            String deviceMRNumbers = VZAppZoneApplicationHelper.getFormattedDeviceMRNumbers(vzAppBinaryFirmwareInfoVOList, xmlType, true);

            //send notification if deviceUUIDs and MRNumbers found
            if(!StringFuncs.isNullOrEmpty(deviceUUIDs) && !StringFuncs.isNullOrEmpty(deviceMRNumbers)) {
                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance.getAllianceId().toString());
                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, alliance.getCompanyName());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, aimsApp.getVersion());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_UUID, deviceUUIDs);

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_NAME, deviceMRNumbers);

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FIRMWARE_NAME, "");
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MAINTENANCE_RELEASE_NUMBER, "");

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_BINARY_ID, StringFuncs.NullValueReplacement(binary.getBinaryId()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FILE_NAME, StringFuncs.NullValueReplacement(binary.getBinaryFileFileName()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FILE_VERSION, StringFuncs.NullValueReplacement(binary.getBinaryVersion()));

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MPORTAL_XML_TYPE, xmlType);

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, currDate);

                aimsEvent.raiseEvent(aimsEventObject);
            }
		}
	}//end mPortalSuccessNotification

    public static void main(String[] args) throws Exception {
		AimsAllianc alliance=new AimsAllianc();
		AimsContact contact=new AimsContact();
		AimsApp app=new AimsApp();
		List binaryFirmwareInfoList=new ArrayList();
		VZAppBinaryFirmwareInfoVO vo1=new VZAppBinaryFirmwareInfoVO();
		VZAppBinaryFirmwareInfoVO vo2=new VZAppBinaryFirmwareInfoVO();
		AimsVZAppBinaries binary=new AimsVZAppBinaries();
		
		DBHelper dbHelper=null;
		try {
			Configuration conf =new Configuration();			
			conf.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@xeon:1521:ora9i");
			conf.setProperty("hibernate.connection.username", "aimsapp3");
			conf.setProperty("hibernate.connection.password", "aimsapp3");
			dbHelper=DBHelper.getInstance();
			dbHelper.sessionFactory=conf.configure().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbHelper != null){
				dbHelper.sessionFactory.close();
			}
		}
		alliance.setAllianceId(new Long(1234));
		alliance.setVendorId(new Long(5678));
		alliance.setCompanyName("Intertek");
		alliance.setStreetAddress1("450 main st.");
		alliance.setSteetAddress2("suite 207");
		alliance.setCity("Pleasanton");
		alliance.setState("CA");
		alliance.setZipCode("94566");
		alliance.setCountry("United States");
		alliance.setAuthRepName("Mary Smith");
		contact.setFirstName("Richard");
		contact.setLastName("Jones");
		contact.setPhone("925-555-1212");
		contact.setEmailAddress("Richard.Jones@alliance.com");

		app.setAppsId(new Long(1122));
		app.setTitle("New Phone");
		app.setVersion("New 1234");

		vo1.setBinaryFirmwareId(new Long(9000));
		vo1.setPhoneModel("LG 8300");
		vo1.setFirmwareName("M361");
		vo1.setMrNumber("MR4");

		vo2.setBinaryFirmwareId(new Long(9001));
		vo2.setPhoneModel("LG 8301");
		vo2.setFirmwareName("M362");
		vo2.setMrNumber("MR5");

		binary.setBinaryId(new Long(1234));
		binary.setBinaryFileFileName("binary.jar");
		binary.setBinaryVersion("1.1");

		binaryFirmwareInfoList.add(vo1);
		binaryFirmwareInfoList.add(vo2);
		
		sendXmlToIntertek(alliance, contact, app, binaryFirmwareInfoList, binary);		
		
	}


    public static Long[] getBinaryIdsArrFromBinaryFirmwareInfos(List binaryFirmwarePhaseInfoList) {
        Long[] binaryIdsArr = null;
        List binaryIdsList = new ArrayList();
        VZAppBinaryFirmwarePhaseInfoVO binaryFirmwarePhaseInfo = null;

        if(binaryFirmwarePhaseInfoList !=null && binaryFirmwarePhaseInfoList.size()>0) {
            for(int phaseInfoIdx =0; phaseInfoIdx <binaryFirmwarePhaseInfoList.size(); phaseInfoIdx++) {
                binaryFirmwarePhaseInfo = (VZAppBinaryFirmwarePhaseInfoVO)binaryFirmwarePhaseInfoList.get(phaseInfoIdx);
                if(binaryFirmwarePhaseInfo !=null && Utility.ZeroValueReplacement(binaryFirmwarePhaseInfo.getBinaryId()).longValue()>0) {
                    if(!binaryIdsList.contains(binaryFirmwarePhaseInfo.getBinaryId())) {
                        binaryIdsList.add(binaryFirmwarePhaseInfo.getBinaryId());
                    }
                }
            }
            binaryIdsArr = (Long [])binaryIdsList.toArray(new Long[0]);
        }

        return binaryIdsArr;
    }

    public static Long[] getBinaryIdsFromAimsBinariesList(List aimsBinariesList) {
        Long[] binaryIdsArr = null;
        List binaryIdsList = null;
        AimsVZAppBinaries aimsBinary = null;
        if(aimsBinariesList!=null && aimsBinariesList.size()>0) {
            binaryIdsList = new ArrayList();
            for(int binaryIdx=0; binaryIdx<aimsBinariesList.size(); binaryIdx++) {
                aimsBinary = (AimsVZAppBinaries)aimsBinariesList.get(binaryIdx);
                binaryIdsList.add(aimsBinary.getBinaryId());
            }
            binaryIdsArr = (Long [])binaryIdsList.toArray(new Long[0]);
        }
        return binaryIdsArr;
    }

    public static Long[] getBinaryFirmwareIdsFromBinaryFirmwarePhasesList(List binaryFirmwaresList) {
        Long[] binaryFirmwareIdsArr = null;
        List binaryFirmwareIdsList = null;
        VZAppBinaryFirmwarePhaseInfoVO binaryFirwmarePhaseInfo = null;
        if(binaryFirmwaresList!=null && binaryFirmwaresList.size()>0) {
            binaryFirmwareIdsList = new ArrayList();
            for(int binaryFirmwareIdx=0; binaryFirmwareIdx<binaryFirmwaresList.size(); binaryFirmwareIdx++) {
                binaryFirwmarePhaseInfo = (VZAppBinaryFirmwarePhaseInfoVO)binaryFirmwaresList.get(binaryFirmwareIdx);
                binaryFirmwareIdsList.add(binaryFirwmarePhaseInfo.getBinaryFirmwareId());
            }
            binaryFirmwareIdsArr = (Long [])binaryFirmwareIdsList.toArray(new Long[0]);
        }
        return binaryFirmwareIdsArr;
    }

    public static Long[] getBinaryFirmwareIdsFromBinaryFirmwareList(List binaryFirmwaresList) {
        Long[] binaryFirmwareIdsArr = null;
        List binaryFirmwareIdsList = null;
        VZAppBinaryFirmwareInfoVO binaryFirwmareInfo = null;
        if(binaryFirmwaresList!=null && binaryFirmwaresList.size()>0) {
            binaryFirmwareIdsList = new ArrayList();
            for(int binaryFirmwareIdx=0; binaryFirmwareIdx<binaryFirmwaresList.size(); binaryFirmwareIdx++) {
                binaryFirwmareInfo = (VZAppBinaryFirmwareInfoVO)binaryFirmwaresList.get(binaryFirmwareIdx);
                binaryFirmwareIdsList.add(binaryFirwmareInfo.getBinaryFirmwareId());
            }
            binaryFirmwareIdsArr = (Long [])binaryFirmwareIdsList.toArray(new Long[0]);
        }
        return binaryFirmwareIdsArr;
    }

    public static String getFormattedDeviceMRNumbers(List vzAppBinaryFirmwareInfoVOList) {
        return VZAppZoneApplicationHelper.getFormattedDeviceMRNumbers(vzAppBinaryFirmwareInfoVOList, null, false);
    }

    /**
     * returns device names with their mrNumbers e.g., device1(mr1, mr2...). device2(mr1, mr2...)
     * @param vzAppBinaryFirmwareInfoVOList
     * @return
     */
    public static String getFormattedDeviceMRNumbers(List vzAppBinaryFirmwareInfoVOList, String xmlType, boolean ignoreDeletedDevice) {
        StringBuffer formattedNamesBuff = new StringBuffer();
        Map deviceMRMap = VZAppZoneApplicationHelper.getFormattedDeviceFieldMap(vzAppBinaryFirmwareInfoVOList,
                                                                                VZAppZoneApplicationHelper.FIRMWARE_FIELD_DEVICE_MR_NUMBER,
                                                                                xmlType, ignoreDeletedDevice);
        Set deviceNamesSet = null;
        Iterator deviceNamesItr = null;
        String deviceName = "";
        List mrNumbersList = null;
        if(deviceMRMap!=null && deviceMRMap.size()>0) {
            try {
                deviceNamesSet = deviceMRMap.keySet();
                deviceNamesItr = deviceNamesSet.iterator();
                while(deviceNamesItr.hasNext()) {
                    deviceName = (String)deviceNamesItr.next();
                    if(!StringFuncs.isNullOrEmpty(deviceName)) {
                        mrNumbersList = (List)deviceMRMap.get(deviceName);
                        if(mrNumbersList!=null && mrNumbersList.size()>0) {
                            formattedNamesBuff.append(deviceName);
                            formattedNamesBuff.append("(");
                            //get comma separated mrNumbers
                            formattedNamesBuff.append(StringFuncs.ConvertArrToString((String [])mrNumbersList.toArray(new String[0]), ","));
                            formattedNamesBuff.append(")");
                            if(deviceNamesItr.hasNext()) {
                                formattedNamesBuff.append(",");
                            }
                        }
                    }
                }
            }
            catch(Exception e) {
                log.debug("VZAppZoneApplicationHelper.getFormattedDeviceMRNumbers: Exception occured while making mrNumbers List");
                log.error(e, e);
            }
        }
        return formattedNamesBuff.toString();
    }

    public static String getFormattedDeviceUUIDs(List vzAppBinaryFirmwareInfoVOList) {
        return VZAppZoneApplicationHelper.getFormattedDeviceUUIDs(vzAppBinaryFirmwareInfoVOList, null, false);
    }

    /**
     * returns device names with their UUIDs e.g., device1(uuid1, uuid2...). device2(uuid1, uuid2...)
     * @param vzAppBinaryFirmwareInfoVOList
     * @return
     */
    public static String getFormattedDeviceUUIDs(List vzAppBinaryFirmwareInfoVOList, String xmlType, boolean ignoreDeletedDevice) {
        StringBuffer formattedNamesBuff = new StringBuffer();
        Map deviceUUIDMap = VZAppZoneApplicationHelper.getFormattedDeviceFieldMap(vzAppBinaryFirmwareInfoVOList,
                                                                VZAppZoneApplicationHelper.FIRMWARE_FIELD_DEVICE_UUID,
                                                                xmlType, ignoreDeletedDevice);
        Set deviceNamesSet = null;
        Iterator deviceNamesItr = null;
        String deviceName = "";
        List deviceUUIDsList = null;
        if(deviceUUIDMap !=null && deviceUUIDMap.size()>0) {
            try {
                deviceNamesSet = deviceUUIDMap.keySet();
                deviceNamesItr = deviceNamesSet.iterator();
                while(deviceNamesItr.hasNext()) {
                    deviceName = (String)deviceNamesItr.next();
                    if(!StringFuncs.isNullOrEmpty(deviceName)) {
                        deviceUUIDsList = (List)deviceUUIDMap.get(deviceName);
                        if(deviceUUIDsList !=null && deviceUUIDsList.size()>0) {
                            //get comma separated UUIDs
                            formattedNamesBuff.append(StringFuncs.ConvertLongArrToString((Long [])deviceUUIDsList.toArray(new Long[0]), ","));
                            if(deviceNamesItr.hasNext()) {
                                formattedNamesBuff.append(",");
                            }
                        }
                    }
                }
            }
            catch(Exception e) {
                log.debug("VZAppZoneApplicationHelper.getFormattedDeviceFieldMap: Exception occured while making deviceUUIDs List");
                log.error(e, e);
            }
        }
        return formattedNamesBuff.toString();
    }//end getFormattedDeviceUUIDs

    /**
     * returns map where key is phoneModel and value is the list of given firmwareFieldType
     * @param vzAppBinaryFirmwareInfoVOList
     * @param firmwareFieldType
     * @return
     */
    public static Map getFormattedDeviceFieldMap(List vzAppBinaryFirmwareInfoVOList, Long firmwareFieldType,
                                                 String xmlType, boolean ignoreDeletedDevice) {
        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        Map deviceMRMap = new HashMap();
        List firmwareFieldList = null;
        boolean includeDeviceInfo = true;
        if(vzAppBinaryFirmwareInfoVOList !=null && vzAppBinaryFirmwareInfoVOList.size()>0) {
            for(int idx=0; idx<vzAppBinaryFirmwareInfoVOList.size(); idx++) {
                binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO) vzAppBinaryFirmwareInfoVOList.get(idx);
                includeDeviceInfo = true;

                //by default inlude device info, but if ignoreDeletedDevice flag is true, then check deleted status
                //if deleted status is also true, then do not send notification
                if(ignoreDeletedDevice && VZAppZoneApplicationHelper.checkDeletedFlag(binaryFirmwareInfo, xmlType)) {
                    includeDeviceInfo = false;
                }

                if(includeDeviceInfo) {
                    firmwareFieldList = (List)deviceMRMap.get(binaryFirmwareInfo.getPhoneModel());
                    if(firmwareFieldList ==null) {
                        //if device/phone model appears first time, add it in map and create new fieldList
                        firmwareFieldList = new ArrayList();
                        deviceMRMap.put(binaryFirmwareInfo.getPhoneModel(), firmwareFieldList);
                    }
                    if(firmwareFieldType.equals(VZAppZoneApplicationHelper.FIRMWARE_FIELD_DEVICE_MR_NUMBER)) {
                        firmwareFieldList.add(binaryFirmwareInfo.getMrNumber());
                    }
                    else if(firmwareFieldType.equals(VZAppZoneApplicationHelper.FIRMWARE_FIELD_DEVICE_UUID)) {
                        firmwareFieldList.add(binaryFirmwareInfo.getBinaryFirmwareId());
                    }
                    else {
                        log.debug("VZAppZoneApplicationHelper.getFormattedDeviceFieldMap: ERROR, firmwareFieldType not matched: "+firmwareFieldType);
                    }
                }//end includeDeviceInfo
            }
        }
        return deviceMRMap;
    }//end getFormattedDeviceFieldMap

    public static void updateDeletedBinaryFirmwares(AimsApp aimsApp,
                                                    List vzAppBinaryFirmwareInfoList,
                                                    String xmlType,
                                                    Date currDate) {
        log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: starts xmlType: "+xmlType);
        if(vzAppBinaryFirmwareInfoList!=null && vzAppBinaryFirmwareInfoList.size()>0 && !StringFuncs.isNullOrEmpty(xmlType)) {
            VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
            List vzAppBinaryFirmwareInfoVOList = null;
            List deletedBinaryFirmwareIds = new ArrayList();
            List aimsBinaryFirmwaresListToDelete = null;
            AimsVZAppBinaryFirmware aimsBinaryFirmwareToDelete = null;
            for(int binaryFirmwareIndex=0; binaryFirmwareIndex<vzAppBinaryFirmwareInfoList.size(); binaryFirmwareIndex++) {
                vzAppBinaryFirmwareInfoVOList = (List)vzAppBinaryFirmwareInfoList.get(binaryFirmwareIndex);
                if(vzAppBinaryFirmwareInfoVOList!=null && vzAppBinaryFirmwareInfoVOList.size()>0) {
                    for(int binaryFirmwareVOIndex=0; binaryFirmwareVOIndex<vzAppBinaryFirmwareInfoVOList.size(); binaryFirmwareVOIndex++) {
                        binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)vzAppBinaryFirmwareInfoVOList.get(binaryFirmwareVOIndex);
                        if(binaryFirmwareInfo!=null && VZAppZoneApplicationHelper.checkDeletedFlag(binaryFirmwareInfo, xmlType)) {
                            deletedBinaryFirmwareIds.add(binaryFirmwareInfo.getBinaryFirmwareId());
                        }
                    }
                }
            }//end for

            try {
                aimsBinaryFirmwaresListToDelete = VZAppZoneApplicationManager.getAimsVZAppBinaryFirmwaresByBinaryFirmwareIds(aimsApp.getAppsId(),
                                                        (Long []) deletedBinaryFirmwareIds.toArray(new Long[0]), AimsConstants.ACTIVE);
                if(aimsBinaryFirmwaresListToDelete!=null && aimsBinaryFirmwaresListToDelete.size()>0) {
                    log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: BinaryFirmwares found having delete flag. size: "+aimsBinaryFirmwaresListToDelete.size());
                    for(int deleteFlagIndex=0; deleteFlagIndex<aimsBinaryFirmwaresListToDelete.size(); deleteFlagIndex++) {
                        aimsBinaryFirmwareToDelete = (AimsVZAppBinaryFirmware) aimsBinaryFirmwaresListToDelete.get(deleteFlagIndex);
                        if(aimsBinaryFirmwareToDelete!=null) {
                            if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA)) {
                                log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: setting ota_moved flag to N of "+aimsBinaryFirmwareToDelete.getBinaryFirmwareId());
                                aimsBinaryFirmwareToDelete.setOtaMoved(AimsConstants.NO_CHAR);
                            }
                            else if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_STAGE)) {
                                log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: setting stage_moved flag to N of "+aimsBinaryFirmwareToDelete.getBinaryFirmwareId());
                                aimsBinaryFirmwareToDelete.setStageMoved(AimsConstants.NO_CHAR);
                            }
                            else if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD)) {
                                log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: setting prod_moved flag to N of "+aimsBinaryFirmwareToDelete.getBinaryFirmwareId());
                                aimsBinaryFirmwareToDelete.setProdMoved(AimsConstants.NO_CHAR);

                                /***************************** no need to set inactive here, deleted device from production is already set before sending xml
                                    //log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: setting is_active flag to N of "+aimsBinaryFirmwareToDelete.getBinaryFirmwareId());
                                    //aimsBinaryFirmwareToDelete.setIsActive(AimsConstants.NO_CHAR);
                                *****************************/
                            }
                            aimsBinaryFirmwareToDelete.setLastUpdatedBy(AimsConstants.RECORD_UPDATED_BY_SYSTEM);
                            aimsBinaryFirmwareToDelete.setLastUpdatedDate(currDate);
                        }
                    }
                    VZAppZoneApplicationManager.updateAimsVZAppZoneBinaryFirmwares(aimsBinaryFirmwaresListToDelete);
                }
            }
            catch (HibernateException he) {
                log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: Exception occured while updating deleted binary firmare status after sending mPortal xml.");
                log.error(he, he);
            }
        }
        log.debug("VZAppZoneApplicationHelper.updateDeletedBinaryFirmwares: ends xmlType: "+xmlType);
    }

    /**
     * This function creates map for VZAppZone Binary Files MimeTypes
     * @return
     */
    private static Hashtable populateVZAppMimeTypesMapping() {
        Hashtable mimeTypesMapping = new Hashtable();
        //key extension, value mime type
        mimeTypesMapping.put("zip", "application/zip");
        mimeTypesMapping.put("jpg", "image/jpeg");
        mimeTypesMapping.put("gif", "image/gif");
        mimeTypesMapping.put("png", "image/png");
        return mimeTypesMapping;
    }

    public static String getVZAppMimeType(String fileName) {
        return (String)VZAppZoneApplicationHelper.VZAPP_MIMETYPES_MAPPING.get(
                                                                    fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase());
    }
}
