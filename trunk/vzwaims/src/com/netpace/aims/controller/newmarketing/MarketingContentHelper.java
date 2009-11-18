package com.netpace.aims.controller.newmarketing;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.util.AimsConstants;

/**
 * This class containts helper functions for modules related to Managing Applications
 *                                           
 * @author Adnan Makda
 */

public class MarketingContentHelper
{

    static Logger log = Logger.getLogger(MarketingContentHelper.class.getName());

    public static String CONTENT_PUBLISHER_LOGO_BLOB_DB_INFO[] = { "publisher_logo", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_APP_TITLE_GRAPHIC_BLOB_DB_INFO[] = { "app_title_graphic", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_SPLASH_SCREEN_BLOB_DB_INFO[] = { "splash_screen", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_ACTIVE_SCREEN_BLOB_DB_INFO[] = { "active_screen", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_SCREEN_JPEG_1_BLOB_DB_INFO[] = { "screen_jpeg_1", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_SCREEN_JPEG_2_BLOB_DB_INFO[] = { "screen_jpeg_2", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_SCREEN_JPEG_3_BLOB_DB_INFO[] = { "screen_jpeg_3", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_SCREEN_JPEG_4_BLOB_DB_INFO[] = { "screen_jpeg_4", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_SCREEN_JPEG_5_BLOB_DB_INFO[] = { "screen_jpeg_5", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_VIDEO_FILE_BLOB_DB_INFO[] = { "video_file", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_APP_LOGO_BW_SMALL_BLOB_DB_INFO[] = { "app_logo_bw_small", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_APP_LOGO_BW_LARGE_BLOB_DB_INFO[] = { "app_logo_bw_large", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_APP_LOGO_CLRSMALL_BLOB_DB_INFO[] = { "app_logo_clrsmall", "aims_creative_contents", "creative_content_id" };
    public static String CONTENT_APP_LOGO_CLRLARGE_BLOB_DB_INFO[] = { "app_logo_clrlarge", "aims_creative_contents", "creative_content_id" };

    public static boolean checkAccess(HttpServletRequest request, String taskname, String PRIV_KEY)
    {
        try
        {
            if (taskname.equalsIgnoreCase("create"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.INSERT))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("edit"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.UPDATE))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("view"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.SELECT))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("delete"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.DELETE))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("clone"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.INSERT))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean checkEditAccess(String currUserType, String status)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((status.equalsIgnoreCase(AimsConstants.CP_MARKETING_CONTENT_STATUS_APPROVED))
                || (status.equalsIgnoreCase(AimsConstants.CP_MARKETING_CONTENT_STATUS_REJECTED)))
                return false;
            else
                return true;
        }
        else
            return true;
    }

    public static boolean checkDeleteAccess(String currUserType, String status)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if (status.equalsIgnoreCase(AimsConstants.CP_MARKETING_CONTENT_STATUS_SAVED))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public static ActionMessages getMessagesOnUpdate(String appSubmitType)
    {
        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;

        if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            message = new ActionMessage("message.mkt.content.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            message = new ActionMessage("message.mkt.content.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            message = new ActionMessage("message.mkt.content.submitted");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            message = new ActionMessage("message.mkt.content.accepted");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
            message = new ActionMessage("message.mkt.content.rejected");

        messages.add(ActionMessages.GLOBAL_MESSAGE, message);

        return messages;
    }

}