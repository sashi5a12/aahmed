package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;

/**
 * This class containts helper functions for modules related to Managing Applications
 *                                           
 * @author Adnan Makda
 */

public class VcastApplicationHelper
{

    static Logger log = Logger.getLogger(VcastApplicationHelper.class.getName());


    public static boolean checkEditAccess(String currUserType, Long aimsLifecyclePhaseId)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue()))
                return false;
            else
                return true;
        }
        else
            return true;
    }

    public static boolean checkDeleteAccess(String currUserType, Long aimsLifecyclePhaseId)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_INITIAL_APPROVAL_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue()))
                return false;
            else
                return true;
        }
        else
            return true;
    }

    /*
     * This method along with  getFormattedJournalEntry(AimsJournalEntry journalEntry), gets
     * a list of formatted Journal Entries
     */
    public static String getFormattedJournalEntries(Collection journalEntries)
    {
        StringBuffer journalCombinedText = new StringBuffer();

        if (journalEntries != null)
        {
            for (Iterator it = journalEntries.iterator(); it.hasNext();)
            {
                journalCombinedText.append(getFormattedJournalEntry((AimsJournalEntry) it.next()));
            }
        }
        return journalCombinedText.toString();
    }

    /*
     * This method formats a single AimsJournalEntry
     */
    public static String getFormattedJournalEntry(AimsJournalEntry journalEntry)
    {
        StringBuffer journalCombinedText = new StringBuffer();

        if (journalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PRIVATE))
            journalCombinedText.append("[Private] ");
        else if (journalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PUBLIC))
            journalCombinedText.append("[Public] ");

        journalCombinedText.append(Utility.convertToString(journalEntry.getCreatedDate(), AimsConstants.DATE_TIME_FORMAT));
        journalCombinedText.append(" : (");
        journalCombinedText.append(journalEntry.getCreatedBy());
        journalCombinedText.append(")\n");
        journalCombinedText.append(journalEntry.getJournalText());
        journalCombinedText.append("\n\n");
        return journalCombinedText.toString();
    }


    public static ActionMessages getMessagesOnUpdate(String appSubmitType)
    {
        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;

        if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))
            message = new ActionMessage("message.manage.app.processed");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            message = new ActionMessage("message.manage.app.submitted");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            message = new ActionMessage("message.manage.app.accepted");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
            message = new ActionMessage("message.manage.app.rejected");

        messages.add(ActionMessages.GLOBAL_MESSAGE, message);

        return messages;
    }


}