package com.netpace.aims.controller.application;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.webservices.AutodeskManager;
import com.netpace.aims.controller.webservices.InfospaceUtils;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsBrewApp;
import com.netpace.aims.model.application.AimsLbsAutodeskPhase;
import com.netpace.aims.model.application.AimsLbsGeoService;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.XMLUtils;

/**
 * This class containts helper functions for modules related to Managing Applications
 *                                           
 * @author Adnan Makda
 */

public class BrewApplicationHelper
{

    static Logger log = Logger.getLogger(BrewApplicationHelper.class.getName());

    public static Long GEO_SERVICE_INITIATED_FROM_NONE = new Long(0);
    public static Long GEO_SERVICE_INITIATED_FROM_MOBILE = new Long(1);
    public static Long GEO_SERVICE_INITIATED_FROM_NETWORK = new Long(2);
    public static Long GEO_SERVICE_INITIATED_FROM_BOTH = new Long(3);

    public static String AUTODESK_XML_TO_STAGE = "STAGE";
    public static String AUTODESK_XML_TO_PROD = "PROD";
    public static String AUTODESK_PROVISION_XML_LIFECYCLE_STAGE = "staging";
    public static String AUTODESK_PROVISION_XML_LIFECYCLE_PROD = "production";
    public static String AUTODESK_PROVISION_XML_TYPE_BREW = "BREW";
    public static String AUTODESK_PROVISION_XML_TYPE_NETWORK = "NETWORK";

    public static Long checkGeoServiceInitiation(Collection geoServices)
    {
        boolean mobileInitiated = false;
        boolean networkInitiated = false;

        if (geoServices != null)
        {
            AimsLbsGeoService aimsLbsGeoService = null;

            for (Iterator it = geoServices.iterator(); it.hasNext();)
            {
                aimsLbsGeoService = (AimsLbsGeoService) it.next();
                if ((aimsLbsGeoService.getStatus() == null) || (aimsLbsGeoService.getStatus().equalsIgnoreCase("A")))
                {
                    if (aimsLbsGeoService.getInitiatedFrom().equalsIgnoreCase(AimsConstants.LBS_GEO_SERV_INITIATED_FROM_MOBILE))
                        mobileInitiated = true;
                    if (aimsLbsGeoService.getInitiatedFrom().equalsIgnoreCase(AimsConstants.LBS_GEO_SERV_INITIATED_FROM_NETWORK))
                        networkInitiated = true;
                }
            }
        }

        if ((mobileInitiated) && (networkInitiated))
            return GEO_SERVICE_INITIATED_FROM_BOTH;
        else if (mobileInitiated)
            return GEO_SERVICE_INITIATED_FROM_MOBILE;
        else if (networkInitiated)
            return GEO_SERVICE_INITIATED_FROM_NETWORK;
        else
            return GEO_SERVICE_INITIATED_FROM_NONE;
    }

    public static void addGeoServiceToXML(Element rootNode, AimsBrewApp aimsBrewApp, String initiatedFrom) throws Exception
    {
        Element recordElement = null;
        Text textNode = null;
        HashSet noDuplicateRole = new HashSet();

        if (aimsBrewApp.getGeoServices() != null)
        {
            AimsLbsGeoService aimsLbsGeoService = null;
            int iRoleCount = 0;
            NodeList roleNodeList = rootNode.getElementsByTagName("roleList");
            Element roleNode = (Element) roleNodeList.item(0);

            for (Iterator it = aimsBrewApp.getGeoServices().iterator(); it.hasNext();)
            {
                aimsLbsGeoService = (AimsLbsGeoService) it.next();
                if ((aimsLbsGeoService.getStatus() == null) || (aimsLbsGeoService.getStatus().equalsIgnoreCase("A")))
                {
                    if ((aimsLbsGeoService.getInitiatedFrom().equalsIgnoreCase(initiatedFrom))
                        && (noDuplicateRole.add(aimsLbsGeoService.getAutodeskInterface())))
                    {
                        iRoleCount++;
                        if (iRoleCount == 1)
                        {
                            recordElement = (Element) rootNode.getElementsByTagName("role").item(0);
                        }
                        else
                        {
                            recordElement = (Element) rootNode.getElementsByTagName("role").item(0).cloneNode(true);
                            roleNode.insertBefore(recordElement, null);
                        }

                        textNode = (Text) (recordElement.getFirstChild());
                        textNode.setData(aimsLbsGeoService.getAutodeskInterface());
                    }

                }
            }
        }
    }

    public static String createAddXML(String fileName, AimsApp aimsApp, AimsBrewApp aimsBrewApp, String server) throws Exception
    {
        Element clonedElement = null;
        String textData;
        Element searchFrom = null;
        Text textNode = null;
        AimsAllianc aimsAllianceOfApplication = null;
        Long geoServiceInitiation = null;

        Document doc = XMLUtils.loadFile(fileName);
        aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

        searchFrom = (Element) doc.getElementsByTagName("clientProv").item(0);

        if (server.equals(AUTODESK_XML_TO_STAGE))
        {
            XMLUtils.updateTextNode("id", searchFrom, AimsConstants.AUTODESK_PROV_STAGE_NETPACE_ID, 0);
            XMLUtils.updateTextNode("pwd", searchFrom, AimsConstants.AUTODESK_PROV_STAGE_NETPACE_PASSWORD, 0);
        }
        else if (server.equals(AUTODESK_XML_TO_PROD))
        {
            XMLUtils.updateTextNode("id", searchFrom, AimsConstants.AUTODESK_PROV_PROD_NETPACE_ID, 0);
            XMLUtils.updateTextNode("pwd", searchFrom, AimsConstants.AUTODESK_PROV_PROD_NETPACE_PASSWORD, 0);
        }

        NodeList rootNodeList = doc.getElementsByTagName("addClientReq");
        Element rootNode = (Element) rootNodeList.item(0);
        rootNode.setAttribute("requestID", "ID1");

        searchFrom = rootNode;
        textData = aimsBrewApp.getLbsClientId().toString();
        XMLUtils.updateTextNode("clientId", searchFrom, textData, 0);

        textData = aimsApp.getTitle();
        XMLUtils.updateTextNode("clientName", searchFrom, textData, 0);

        textData = aimsBrewApp.getLbsSecretKey();
        XMLUtils.updateTextNode("password", searchFrom, textData, 0);

        textData = "Vendor:" + aimsAllianceOfApplication.getCompanyName() + ";Version:" + aimsApp.getVersion();
        XMLUtils.updateTextNode("description", searchFrom, textData, 0);

        NodeList propertyNodeList = rootNode.getElementsByTagName("property");

        for (int iPropertyCount = 0; iPropertyCount < propertyNodeList.getLength(); iPropertyCount++)
        {
            Element propertyNode = (Element) propertyNodeList.item(iPropertyCount);
            textNode = (Text) (propertyNode.getFirstChild());

            if (propertyNode.getAttribute("name").equals("lifecyclestage"))
            {
                if (server.equals(AUTODESK_XML_TO_STAGE))
                    textNode.setData(AUTODESK_PROVISION_XML_LIFECYCLE_STAGE);
                else if (server.equals(AUTODESK_XML_TO_PROD))
                    textNode.setData(AUTODESK_PROVISION_XML_LIFECYCLE_PROD);
            }

            if (propertyNode.getAttribute("name").equals("vendorid"))
                textNode.setData(aimsAllianceOfApplication.getVendorId().toString());
        }

        //Setting Roles - Geo Services
        geoServiceInitiation = checkGeoServiceInitiation(aimsBrewApp.getGeoServices());

        //If need be, that is Geo Services are selected that are Mobile-Initiated as well as Network-Initiated
        clonedElement = (Element) rootNode.cloneNode(true);
        clonedElement.setAttribute("requestID", "ID2");
        XMLUtils.updateTextNode("type", clonedElement, AUTODESK_PROVISION_XML_TYPE_NETWORK, 0);

        if (geoServiceInitiation.longValue() == GEO_SERVICE_INITIATED_FROM_NONE.longValue())
            throw new Exception("ERROR: No Geo Service Selected");
        else if (geoServiceInitiation.longValue() == GEO_SERVICE_INITIATED_FROM_NETWORK.longValue())
        {
            XMLUtils.updateTextNode("type", searchFrom, AUTODESK_PROVISION_XML_TYPE_NETWORK, 0);
            addGeoServiceToXML(rootNode, aimsBrewApp, AimsConstants.LBS_GEO_SERV_INITIATED_FROM_NETWORK);
        }
        else
        {
            XMLUtils.updateTextNode("type", searchFrom, AUTODESK_PROVISION_XML_TYPE_BREW, 0);
            addGeoServiceToXML(rootNode, aimsBrewApp, AimsConstants.LBS_GEO_SERV_INITIATED_FROM_MOBILE);
            if (geoServiceInitiation.longValue() == GEO_SERVICE_INITIATED_FROM_BOTH.longValue())
            {
                addGeoServiceToXML(clonedElement, aimsBrewApp, AimsConstants.LBS_GEO_SERV_INITIATED_FROM_NETWORK);
                doc.getElementsByTagName("clientProv").item(0).insertBefore(clonedElement, null);
            }
        }

        return XMLUtils.docToString(doc);
    }

    public static String createRemoveXML(String fileName, AimsBrewApp aimsBrewApp, String server) throws Exception
    {
        Document doc = XMLUtils.loadFile(fileName);
        Element searchFrom = (Element) doc.getElementsByTagName("clientProv").item(0);
        if (server.equals(AUTODESK_XML_TO_STAGE))
        {
            XMLUtils.updateTextNode("id", searchFrom, AimsConstants.AUTODESK_PROV_STAGE_NETPACE_ID, 0);
            XMLUtils.updateTextNode("pwd", searchFrom, AimsConstants.AUTODESK_PROV_STAGE_NETPACE_PASSWORD, 0);
        }
        else if (server.equals(AUTODESK_XML_TO_PROD))
        {
            XMLUtils.updateTextNode("id", searchFrom, AimsConstants.AUTODESK_PROV_PROD_NETPACE_ID, 0);
            XMLUtils.updateTextNode("pwd", searchFrom, AimsConstants.AUTODESK_PROV_PROD_NETPACE_PASSWORD, 0);
        }

        NodeList rootNodeList = doc.getElementsByTagName("removeClientReq");
        Element rootNode = (Element) rootNodeList.item(0);
        rootNode.setAttribute("requestID", "ID1");
        XMLUtils.updateTextNode("clientId", rootNode, aimsBrewApp.getLbsClientId().toString(), 0);
        return XMLUtils.docToString(doc);
    }

    public static boolean sendDCRToAutodesk(AimsApp aimsApp, AimsBrewApp aimsBrewApp, Long autodeskPhaseId, String xmlFolderPath, String hostName)
    {
        boolean success = true;
        AimsEventObject aimsEventObject = null;
        AimsAllianc aimsAllianceOfApplication = null;
        AimsEventLite aimsEvent = null;
        Object[] objHostName = { hostName };
        String submitDCRBean = null;
        String submitDCRURL = null;

        try
        {
            //If the DCR has been submitted successfully before, skip the entire function.
            if (!AutodeskManager.hasAlreadyBeenSubmittedSuccessfully(aimsApp.getAppsId(), autodeskPhaseId.toString()))
            {
                //Not sending De-Provision to PROD for the time being.
                if (!(autodeskPhaseId.longValue() == AimsConstants.LBS_STATUS_DEPROVISIONED.longValue()))
                {
                    aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

                    if (autodeskPhaseId.longValue() == AimsConstants.LBS_STATUS_STAGING.longValue())
                    {
                        submitDCRBean = createAddXML(xmlFolderPath + "/AddClient.xml", aimsApp, aimsBrewApp, AUTODESK_XML_TO_STAGE);
                        submitDCRURL = AimsConstants.AUTODESK_SUBMIT_STAGE_URL;
                    }
                    else if (autodeskPhaseId.longValue() == AimsConstants.LBS_STATUS_PRODUCTION.longValue())
                    {
                        submitDCRBean = createAddXML(xmlFolderPath + "/AddClient.xml", aimsApp, aimsBrewApp, AUTODESK_XML_TO_PROD);
                        submitDCRURL = AimsConstants.AUTODESK_SUBMIT_PROD_URL;
                    }
                    else if (autodeskPhaseId.longValue() == AimsConstants.LBS_STATUS_DEPROVISIONED_ON_STAGE.longValue())
                    {
                        submitDCRBean = createRemoveXML(xmlFolderPath + "/RemoveClient.xml", aimsBrewApp, AUTODESK_XML_TO_STAGE);
                        submitDCRURL = AimsConstants.AUTODESK_SUBMIT_STAGE_URL;
                    }
                    else if (autodeskPhaseId.longValue() == AimsConstants.LBS_STATUS_DEPROVISIONED.longValue())
                    {
                        submitDCRBean = createRemoveXML(xmlFolderPath + "/RemoveClient.xml", aimsBrewApp, AUTODESK_XML_TO_PROD);
                        submitDCRURL = AimsConstants.AUTODESK_SUBMIT_PROD_URL;
                    }

                    System.out.println("Sending to: " + submitDCRURL + "\n The following xml request......\n" + submitDCRBean);

                    String response = XMLUtils.postXML(submitDCRURL, submitDCRBean);
                    System.out.println("Response from client......\n" + response);

                    Document doc = XMLUtils.loadXML(response);
                    NodeList rootNodeList = doc.getElementsByTagName("result");
                    Element rootNode = (Element) rootNodeList.item(0);
                    Text textNode = (Text) (rootNode.getFirstChild());

                    if (!rootNode.getAttribute("resultId").equals("0"))
                    {
                        throw new Exception("ERROR: " + textNode.getData());
                    }
                    else
                    {
                        AutodeskManager.submitAutodeskLog(aimsApp.getAppsId(), autodeskPhaseId.toString(), "Y", textNode.getData());
                        AimsApplicationsManager.saveJournalEntry(
                            aimsApp.getAppsId(),
                            "Contents were successfully pushed to Autodesk",
                            AimsConstants.JOURNAL_TYPE_PRIVATE,
                            "system");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Helper Exception");
            ex.printStackTrace();

            try
            {
                String autodeskLogString = null;

                if (ex.getMessage() != null)
                    autodeskLogString = ex.getMessage();
                else
                    autodeskLogString = "LOCAL ERROR: Could not connect to the Autodesk";

                AutodeskManager.submitAutodeskLog(aimsApp.getAppsId(), autodeskPhaseId.toString(), "N", autodeskLogString);

                StringBuffer emailSubject = new StringBuffer("Exception in BrewApplicationHelper.sendDCRToAutodesk(), on .... ");
                if (hostName != null)
                    emailSubject.append(hostName);

                MailUtils.sendMail(
                    AimsConstants.EMAIL_EXCEPTION_ADMIN,
                    "exceptions@netpace.com",
                    emailSubject.toString(),
                    null,
                    StringFuncs.NullValueReplacement(ex.getMessage()) + "\n\n" + MiscUtils.getExceptionStackTraceInfo(ex) + "\n\n" + submitDCRBean);

                aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_LBS_ERROR_SENDING_XML_TO_AUTODESK);

                if (aimsEvent != null)
                {
                    aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());
                    aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_LBS_AUTODESK_STATUS_NEW,
                        ((AimsLbsAutodeskPhase) DBHelper.getInstance().load(AimsLbsAutodeskPhase.class, autodeskPhaseId.toString())).getAutodeskPhaseName());
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_RESEND_AUTODESK_URL,
                        AimsConstants.AUTODESK_RESUBMIT_URL.format(objHostName)
                            + "?p_id="
                            + autodeskPhaseId.toString()
                            + "&app_id="
                            + aimsApp.getAppsId().toString()
                            + "&other_info="
                            + URLEncoder.encode(
                                MiscUtils.getBase64Digest(
                                    InfospaceUtils.utf8decode(AimsConstants.AUTODESK_KEY_DIGEST_FOR_URLS),
                                    InfospaceUtils.utf8decode(aimsApp.getAppsId().toString()),
                                    InfospaceUtils.utf8decode(autodeskPhaseId.toString())),
                                "UTF-8"));

                    log.debug(aimsEventObject.getProperty(AimsNotificationConstants.PLACE_HOLDER_RESEND_AUTODESK_URL));
                    aimsEvent.raiseEvent(aimsEventObject);
                }

            }
            catch (Exception xyz)
            {
                try
                {
                    MailUtils.sendMail(
                        AimsConstants.EMAIL_EXCEPTION_ADMIN,
                        "exceptions@netpace.com",
                        AimsConstants.EMAIL_SUBJECT_RARE_EXCEPTION + " .... on " + hostName,
                        null,
                        MiscUtils.getExceptionStackTraceInfo(xyz));
                }
                catch (Exception mailEx)
                {
                    System.out.println("Exception in BrewApplicationHelper while sending email");
                    mailEx.printStackTrace();
                }
            }
            success = false;
        }

        return success;
    }

    public static boolean sendDCRToAutodesk(
        final AimsApp aimsApp,
        final AimsBrewApp aimsBrewApp,
        final Long autodeskPhaseId,
        final String xmlFolderPath,
        final String hostName,
        boolean sync)
    {
        if (sync)
        {
            return BrewApplicationHelper.sendDCRToAutodesk(aimsApp, aimsBrewApp, autodeskPhaseId, xmlFolderPath, hostName);

        }
        else
        {
            new Thread()
            {
                public void run()
                {
                    BrewApplicationHelper.sendDCRToAutodesk(aimsApp, aimsBrewApp, autodeskPhaseId, xmlFolderPath, hostName);
                }
            }
            .start();
            return true;
        }
    }

}