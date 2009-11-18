package com.netpace.aims.controller.events;

import org.apache.log4j.Logger;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.*;

/**
 * This class containts functions to help in finding values for Place Holders
 *                                           
 * @author Adnan Makda
 */

public class PlaceHolderHelper
{

    static Logger log = Logger.getLogger(PlaceHolderHelper.class.getName());

    // Get Alliance Name
    public static String getAllianceName(Long allianceId) throws Exception
    {
        AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, allianceId.toString());
        return aimsAllianceOfApplication.getCompanyName();
    }

    // Get Alliance Admin Name
    public static String getAllianceAdminName(Long allianceId) throws Exception
    {
        AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, allianceId.toString());

        AimsUser aimAllianceAdminUser =
            (AimsUser) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsUser.class, aimsAllianceOfApplication.getAimsUserByAdminUserId().toString());

        AimsContact aimAllianceAdminContact =
            (AimsContact) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsContact.class, aimAllianceAdminUser.getAimsContactId().toString());

        return aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName();
    }

    // Get Alliance Admin Phone
    public static String getAllianceAdminPhone(Long allianceId) throws Exception
    {
        AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, allianceId.toString());

        AimsUser aimAllianceAdminUser =
            (AimsUser) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsUser.class, aimsAllianceOfApplication.getAimsUserByAdminUserId().toString());

        AimsContact aimAllianceAdminContact =
            (AimsContact) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsContact.class, aimAllianceAdminUser.getAimsContactId().toString());

        return aimAllianceAdminContact.getPhone();
    }

    // Get Application Assignee Name
    public static String getApplicationAssigneeName(Long vzwAppsContactId) throws Exception
    {
        if (vzwAppsContactId != null)
        {
            AimsContact aimsVzwAppsContact =
                (AimsContact) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsContact.class, vzwAppsContactId.toString());

            return aimsVzwAppsContact.getFirstName() + " " + aimsVzwAppsContact.getLastName();
        }
        else
        {
            return "";
        }
    }

}