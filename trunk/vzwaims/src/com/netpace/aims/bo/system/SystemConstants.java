package com.netpace.aims.bo.system;

import java.util.Date;

/**
 * This class has static final constants which will be refered accross the system
 *
 * @author Fawad Sikandar
 */
public class SystemConstants
{


  public static Date currentDate = new Date();
  public static Date Last_Email_Sent_Date = new Date();

  public static String INTEGRITY_CONSTRAINT_KEYS[] =
  {
      "AIMS.FK_BROWSERS_1", "error.system.platform.integrity",
      "AIMS.FK_DEVICE_PLATFORMS_2", "error.system.platform.integrity",
      "AIMS.FK_APPS_5", "error.system.platform.integrity",
      "AIMS.FK_APP_SUB_CATEGORIES_1", "error.system.category.integrity",
      "AIMS.FK_TOOL_CATEGORIES_2", "error.system.category.integrity",
      "AIMS.FK_EMAIL_REMINDERS_1", "error.system.emailmessage.integrity",
      "AIMS.FK_EVENT_NOTIFICATIONS_2", "error.system.emailmessage.integrity",
      "AIMS.FK_FAQ_TOPICS_1","error.content.platform.integrity"

  };


  public static String UNIQUE_CONSTRAINT_KEYS[] =
  {
      "AIMS.AK_PLATFORMS_PLATFORM_NAME", "error.system.category.duplicate",
      "AIMS.AK_APP_CATEGORIES_CAT_NAME", "error.system.category.duplicate",
      "AIMS.AK_APP_SUB_CATEGORIES_SCT_NAME", "error.system.subcategory.duplicate",
      "AIMS.AK_DECKS_DECK_NAME", "error.system.deck.duplicate",
      "AIMS.AK_FAQ_TOPICS_TOPIC_QUESTION", "error.content.faq.duplicate",
      "AIMS.AK_FAQ_CATEGORIES_NAME","error.content.faqcategory.duplicate",
      "AIMS.AK_EVENT_NOTIFICATIONS_NAME","error.system.eventNotification.duplicate",
      "AIMS.AK_EMAIL_MESSAGES_EMAIL_TITLE","error.system.systemNotification.duplicate"
  };



   public static String Email_Message_Categories[] =
   {
      "C",
      "S"
   };


}
