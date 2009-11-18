package com.netpace.aims.controller.application;

import org.apache.log4j.Logger;
import java.util.*;

import com.netpace.aims.bo.events.*;
import com.netpace.aims.controller.events.*;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.model.events.*;
import com.netpace.aims.util.*;

/**
 * This class containts functions to help in raising the event(s) related to Spotlights
 *											 
 * @author Adnan Makda
 */

public class SpotlightEventHelper
{

    static Logger log = Logger.getLogger(SpotlightEventHelper.class.getName());

    public static void raiseDocumentUploadEvent(Long appsId, Long spotlightTypeId, String spotlightName, String spotlightFileName) throws Exception
    {
        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_SPOTLIGHT_DOCUMENT_UPLOADED);

        if (aimsEvent != null)
        {
            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

            //Getting all objects containing data we require
            AimsApp aimsApp = (AimsApp) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsApp.class, appsId.toString());

            AimsAllianc aimsAllianceOfApplication =
                (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

            AimsSpotlightType aimsSpotlightType =
                (AimsSpotlightType) DBHelper.getInstance().load(com.netpace.aims.model.masters.AimsSpotlightType.class, spotlightTypeId.toString());

            String allianceAdminName = PlaceHolderHelper.getAllianceAdminName(aimsApp.getAimsAllianceId());
            String applicationAssigneeName = PlaceHolderHelper.getApplicationAssigneeName(aimsApp.getAimsVzwAppsContactId());

            //Setting Properties
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_NAME, spotlightName);
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_DOCUMENT_NAME, spotlightFileName);
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_TYPE, aimsSpotlightType.getSpotlightTypeName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME, allianceAdminName);

            if (aimsApp.getAimsVzwAppsContactId() != null)
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APPLICATION_ASSIGNEE, applicationAssigneeName);

            aimsEvent.raiseEvent(aimsEventObject);
        }
    }

    public static void raiseDocumentDeleteEvent(Long appsId, Long spotlightTypeId, String spotlightName, String spotlightFileName, String spotlightStatus)
        throws Exception
    {
        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_SPOTLIGHT_DOCUMENT_DELETED);

        if (aimsEvent != null)
        {
            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

            //Getting all objects containing data we require
            AimsApp aimsApp = (AimsApp) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsApp.class, appsId.toString());

            AimsAllianc aimsAllianceOfApplication =
                (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

            AimsSpotlightType aimsSpotlightType =
                (AimsSpotlightType) DBHelper.getInstance().load(com.netpace.aims.model.masters.AimsSpotlightType.class, spotlightTypeId.toString());

            String allianceAdminName = PlaceHolderHelper.getAllianceAdminName(aimsApp.getAimsAllianceId());
            String applicationAssigneeName = PlaceHolderHelper.getApplicationAssigneeName(aimsApp.getAimsVzwAppsContactId());

            //Setting Properties
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_NAME, spotlightName);
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_DOCUMENT_NAME, spotlightFileName);
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_DOCUMENT_STATUS, spotlightStatus);
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_TYPE, aimsSpotlightType.getSpotlightTypeName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME, allianceAdminName);

            if (aimsApp.getAimsVzwAppsContactId() != null)
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APPLICATION_ASSIGNEE, applicationAssigneeName);

            aimsEvent.raiseEvent(aimsEventObject);

        }
    }

    public static void raiseDocumentStatusChangeEvent(Long appsId, Long spotlightTypeId, Long spotlightId) throws Exception
    {
        //Getting all objects containing data we require
        AimsApp aimsApp = (AimsApp) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsApp.class, appsId.toString());

        AimsAllianc aimsAllianceOfApplication =
            (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

        AimsSpotlightType aimsSpotlightType =
            (AimsSpotlightType) DBHelper.getInstance().load(com.netpace.aims.model.masters.AimsSpotlightType.class, spotlightTypeId.toString());

        AimsEntAppsSpotlight aimsEntAppsSpotlight =
            (AimsEntAppsSpotlight) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsEntAppsSpotlight.class, spotlightId.toString());

        //Start of Event Related Code
        AimsEventLite aimsEvent = null;

        if (aimsEntAppsSpotlight.getStatus().equals(AimsConstants.SPOTLIGHT_DOCUMENT_STATUS_ACCEPTED))
            aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_SPOTLIGHT_DOCUMENT_ACCEPTED);
        else if (aimsEntAppsSpotlight.getStatus().equals(AimsConstants.SPOTLIGHT_DOCUMENT_STATUS_REJECTED))
            aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_SPOTLIGHT_DOCUMENT_REJECTED);

        if (aimsEvent != null)
        {
            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

            String allianceAdminName = PlaceHolderHelper.getAllianceAdminName(aimsApp.getAimsAllianceId());
            String applicationAssigneeName = PlaceHolderHelper.getApplicationAssigneeName(aimsApp.getAimsVzwAppsContactId());

            //Setting Properties
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_NAME, aimsEntAppsSpotlight.getSpotlightName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_DOCUMENT_NAME, aimsEntAppsSpotlight.getSpotlightFileFileName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_DOCUMENT_STATUS, aimsEntAppsSpotlight.getStatus());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SPOTLIGHT_TYPE, aimsSpotlightType.getSpotlightTypeName());
            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME, allianceAdminName);

            if (aimsApp.getAimsVzwAppsContactId() != null)
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APPLICATION_ASSIGNEE, applicationAssigneeName);

            aimsEvent.raiseEvent(aimsEventObject);

        }
    }

}
