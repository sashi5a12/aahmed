package com.netpace.aims.bo.system;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.log4j.*;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.controller.system.*;
import com.netpace.aims.model.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.events.*;
import com.netpace.aims.model.system.*;
import com.netpace.aims.util.*;
import net.sf.hibernate.*;
import net.sf.hibernate.type.*;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.model.events.AimsEmailMessagesAtt;
import oracle.sql.BLOB;
import org.apache.struts.upload.FormFile;

/**
 * This	class is responsible for getting the BO for system notification.
 * It has static methods for getting the AIMS system notifications.
 * @author Fawad Sikandar
 */

public class AimsSystemNotificationManager {

  static Logger log = Logger.getLogger(AimsSystemNotificationManager.class.
                                       getName());

  /**
   * This static method gets the list of system email messages from the
   * database which are available to the current user.
   */

  public static Collection getSystemEmailMessageList() throws
      HibernateException {

    Collection collection = null;
    Session session = null;
    try {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from	com.netpace.aims.model.system.AimsEmailMessag as emailMessage where emailMessage.emailCategory = 'S' order by emailMessage.emailTitle");
      log.debug("No. of Email Messages : " + collection.size());
    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }
    return collection;
  }


  /**
   * This static method gets the list of place holders from the
   * database which are available to the current user.
   */

  public static Collection getPlaceHoldlerList() throws
      HibernateException {

    Collection collection = null;
    Session session = null;
    try {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from	com.netpace.aims.model.events.AimsPlaceHolder as placeHolder  order by placeHolder.placeHolderDisplayName");
      log.debug("No. of AimsPlaceHolders : " + collection.size());
    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }
    return collection;
  }

  /**
   * This static method gets the list of events from the
   * database which are available to the current user.
   */

  public static Collection getEventList() throws HibernateException {
    Collection collection = null;
    Session session = null;

    try {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from	com.netpace.aims.model.events.AimsEventLite as event order by event.eventName");
      log.debug("No. of Event : " + collection.size());
    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }
    return collection;
  }


  /**
   * This static method returns the AimsEmailMessage object for a
   * given email message id.
   */

  public static AimsEmailMessag getEmailMessage(int emailMessageId) throws
      RecordNotFoundException, HibernateException {

    AimsEmailMessag emailMessage = null;
    Session session = null;
    try {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select	from com.netpace.aims.model.system.AimsEmailMessag as emailMessage where emailMessage.emailMessageId = :emailMessageId");

      query.setInteger("emailMessageId", emailMessageId);

      for (Iterator it = query.iterate(); it.hasNext(); ) {
        emailMessage = (AimsEmailMessag) it.next();
        log.debug("Email Message" + emailMessage.toString());
      }
      if (emailMessage == null) {
        throw new RecordNotFoundException("error.security");
      }
    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }

    return emailMessage;
  }

  /**
   * This static method returns Collection AimsNotifAdHocRecipient objects
   * for a given notification id.
   */

  public static Collection getAdHocRecipientsByNotifId(String notifId) throws
      HibernateException {

    Collection collection = null;
    Session session = null;
    try {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.events.AimsNotifAdHocRecipientLite as notifRecipient where notifRecipient.notificationId = '" +
                                notifId + "'");
      log.debug("No. of  Recipients : " + collection.size());
    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }
    return collection;
  }

  /**
   * This static method returns AimsEventNotification object for a
   * given message id.
   */

  public static AimsEventNotificationLite getEventNotificationByMessageId(int
      messageId) throws HibernateException {

    AimsEventNotificationLite eventNotification = null;
    Session session = null;
    try {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("from com.netpace.aims.model.events.AimsEventNotificationLite as eventNotification where eventNotification.messageId = :messageId");
      query.setInteger("messageId", messageId);

      for (Iterator it = query.iterate(); it.hasNext(); ) {
        eventNotification = (AimsEventNotificationLite) it.next();

      }
      if (eventNotification == null) {
        eventNotification = new AimsEventNotificationLite();
        eventNotification.setEventId(new Long("0"));
      }
    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }

    return eventNotification;
  }

    /**
   * This static method deletes system notification with all its child records
   * by giving message id.
   */

  public static int deleteSystemNotification(int messageId) throws
      IntegrityConstraintException, HibernateException {

    int delCount = 0;
    Session session = null;
    Connection con = null;
    Statement stmt = null;

    try {
      session = DBHelper.getInstance().getSession();
      con = session.connection();
      con.setAutoCommit(false);
      stmt = con.createStatement();

      String notId = getEventNotificationByMessageId(messageId).
          getNotificationId().toString();

      stmt.addBatch(
          "delete from AIMS_EMAIL_MESSAGES_ATT where EMAIL_MESSAGE_ID = " +
          messageId);
      stmt.addBatch(
          "delete from AIMS_NOTIFICATION_ROLES where NOTIFICATION_ID = " +
          notId);
      stmt.addBatch(
          "delete from AIMS_NOTIF_AD_HOC_RECIPIENTS where NOTIFICATION_ID = " +
          notId);      
      stmt.addBatch( 
           "delete from AIMS_NOTIF_OPT_IN_RECIPIENTS where NOTIFICATION_ID = " +
            notId);  
      stmt.addBatch(
          "delete from AIMS_EVENT_NOTIFICATIONS where NOTIFICATION_ID = " +
          notId);      stmt.addBatch("delete from AIMS_EMAIL_MESSAGES where EMAIL_MESSAGE_ID = " +
                    String.valueOf(messageId));
          
      stmt.executeBatch();
      stmt.close();
      con.commit();

    }
    catch (SQLException je) {
      if (con != null) {

        try {
          con.rollback();
        }
        catch (SQLException ex) {
        }
      }
      String exMessage = je.getMessage();
      if (exMessage.indexOf("violated - child record found") > -1) {
        throw new IntegrityConstraintException();
      }
      else {
        je.printStackTrace();
        throw new HibernateException(je);
      }
    }

    finally {
      session.close();
    }

    return delCount;

  }

  /**
   * This static method deletes all notification roles on a given
   * notification id.
   */

  public static int deleteNotificationRoles(int notificationId) throws
      IntegrityConstraintException, HibernateException {

    int delCount = 0;
    Session session = null;
    Transaction tx = null;
    try {
      session = DBHelper.getInstance().getSession();
      tx = session.beginTransaction();
      delCount = session.delete("from	com.netpace.aims.model.events.AimsNotificationRoleLite as notification where notification.notificationId = :notificationId",
                                new Integer(notificationId), new IntegerType());
      tx.commit();
    }
    catch (JDBCException je) {
      if (tx != null) {
        tx.rollback();
      }
      String exMessage = je.getMessage();
      if (exMessage.indexOf("violated - child record found") > -1) {
        throw new IntegrityConstraintException();
      }
      else {
        je.printStackTrace();
        throw new HibernateException(je);
      }
    }
    catch (HibernateException e) {
      if (tx != null) {
        tx.rollback();
      }
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }

    return delCount;
  }

  /**
   * This static method deletes all adhoc recipients of notification on
   * a given notification id.
   */

  public static int deleteAdHocRecipients(int notificationId) throws
      IntegrityConstraintException, HibernateException {

    int delCount = 0;
    Session session = null;
    Transaction tx = null;
    try {
      session = DBHelper.getInstance().getSession();
      tx = session.beginTransaction();
      delCount = session.delete("from	com.netpace.aims.model.events.AimsNotifAdHocRecipientLite as notification where notification.notificationId = :notificationId",
                                new Integer(notificationId), new IntegerType());
      tx.commit();
    }
    catch (JDBCException je) {
      if (tx != null) {
        tx.rollback();
      }
      String exMessage = je.getMessage();
      if (exMessage.indexOf("violated - child record found") > -1) {
        throw new IntegrityConstraintException();
      }
      else {
        je.printStackTrace();
        throw new HibernateException(je);
      }
    }
    catch (HibernateException e) {
      if (tx != null) {
        tx.rollback();
      }
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }

    return delCount;
  }


  /**
   * This static method delete the attachments if any on a given
   * notification id.
   */

  public static int deleteEmailAttachments(Long emailMessageId) throws
      IntegrityConstraintException, HibernateException {

    int delCount = 0;
    Session session = null;
    Transaction tx = null;
    try {
      session = DBHelper.getInstance().getSession();
      tx = session.beginTransaction();
      delCount = session.delete("from	com.netpace.aims.model.events.AimsEmailMessagesAtt as att where att.emailMessageId = :emailMessageId",
                                emailMessageId, new LongType());

      tx.commit();
    }
    catch (JDBCException je) {
      if (tx != null) {
        tx.rollback();
      }
      String exMessage = je.getMessage();
      if (exMessage.indexOf("violated - child record found") > -1) {
        throw new IntegrityConstraintException();
      }
      else {
        je.printStackTrace();
        throw new HibernateException(je);
      }
    }
    catch (HibernateException e) {
      if (tx != null) {
        tx.rollback();
      }
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }

    return delCount;
  }



  /**
   * This static method save the given system notification.
   */

  public static void saveNotification(SystemNotificationForm notificationForm,
                                      AimsUser aimsUser, FormFile emailAttachment) throws
      UniqueConstraintException, RecordNotFoundException, HibernateException, Exception

  {
    Session session = null;
    Transaction tx = null;

    try {

      session = DBHelper.getInstance().getSession();
      tx = session.beginTransaction();

      AimsEmailMessag emailMessage = new AimsEmailMessag();

      emailMessage.setEmailTitle(notificationForm.getSubject());
      emailMessage.setEmailDesc(notificationForm.getDescription());
      emailMessage.setEmailText(notificationForm.getContent());
      emailMessage.setEmailCategory(SystemConstants.Email_Message_Categories[1]);
      emailMessage.setCreatedBy(aimsUser.getUsername());
      emailMessage.setCreatedDate(new Date());
      emailMessage.setLastUpdatedBy(aimsUser.getUsername());
      emailMessage.setLastUpdatedDate(new Date());
      session.save(emailMessage);
      session.flush();


      log.debug(" First Session Flushed : ");


      if (emailAttachment != null && emailAttachment.getFileSize() > 0) {

        AimsEmailMessagesAtt emailMsgAtt = new AimsEmailMessagesAtt();
        emailMsgAtt.setEmailMessageId(emailMessage.getEmailMessageId());

        boolean emailAttachmentPresent = false;
        byte[] buffer = new byte[1];
        buffer[0] = 1;

        log.debug("emailAttachment Name : " + emailAttachment.getFileName());
        log.debug("emailAttachment Type : " + emailAttachment.getContentType());
        log.debug("emailAttachment Size : " + emailAttachment.getFileSize());

        emailMsgAtt.setAttContentType(emailAttachment.getContentType());
        emailMsgAtt.setAttFileName(emailAttachment.getFileName());
        emailMsgAtt.setAtt(Hibernate.createBlob(buffer));
        emailAttachmentPresent = true;

        session.save(emailMsgAtt);
        session.flush();
        session.refresh(emailMsgAtt, LockMode.UPGRADE);
        if (emailAttachmentPresent)
          LobUtils.writeToOraBlob( (BLOB) emailMsgAtt.getAtt(),
                                  emailAttachment.getInputStream());
        session.flush();
        tx.commit();

        if (emailAttachmentPresent)
          emailAttachment.destroy();

      }

      log.debug(" Attachment Saved : ");

      AimsEventNotificationLite eventNotification = new
          AimsEventNotificationLite();
      eventNotification.setMessageId(emailMessage.getEmailMessageId());
      eventNotification.setEventId(Utility.convertToLong(notificationForm.
          getEventId()));
      eventNotification.setNotificationTitle(emailMessage.getEmailTitle());
      eventNotification.setMedia("E");
      eventNotification.setStatus("ACTIVE");
      eventNotification.setFromAddress(aimsUser.getAimsContact().
                                       getEmailAddress());

      eventNotification.setIncludeVzwAccountManager(MiscUtils.checkYesNo(
          notificationForm.getVzwAccountManager()));
      eventNotification.setIncludeVzwAppsContact(MiscUtils.checkYesNo(
          notificationForm.getAppManager()));

      session.save(eventNotification);
      session.flush();

      log.debug(" Second Session Flushed : ");

      if (notificationForm.getRoleIds() != null)
      {
          AimsNotificationRoleLite roleLite = null;
          for (int i = 0; notificationForm.getRoleIds().length > i; i++) {
            roleLite = new AimsNotificationRoleLite();
            roleLite.setNotificationId(eventNotification.getNotificationId());
            roleLite.setRoleId(notificationForm.getRoleIds()[i]);
            session.save(roleLite);
          }
          session.flush();
          log.debug(" Third Session Flushed : ");
      }

      AimsNotifAdHocRecipientLite recipient = null;
      String[] strArray = StringFuncs.tokenize(notificationForm.
                                               getRecipientEmail(), ",");

      for (int i = 0; i < strArray.length; i++) {
        recipient = new AimsNotifAdHocRecipientLite();
        recipient.setNotificationId(eventNotification.getNotificationId());
        recipient.setEmailAddress(strArray[i]);
        log.debug(" Email Address : " + strArray[i]);
        session.save(recipient);
      }
      session.flush();

      log.debug(" Fourth Session Flushed : ");

      tx.commit();
      log.debug(" Transaction Committed : ");

    }
    catch (JDBCException je) {
      if (tx != null) {
        tx.rollback();

      }
      //String exMessage = je.getMessage();
      String exMessage = je.getCause().toString();
      //changed je.getMessage() to je.getCause().toString(), making it compatible with oracle.jar
      if (DBErrorFinder.searchUniqueConstraintErrors(exMessage,
          SystemConstants.UNIQUE_CONSTRAINT_KEYS, new UniqueConstraintException())) {
        throw new UniqueConstraintException();
      }
      else {
        je.printStackTrace();
        throw new HibernateException(je);
      }
    }
    catch (HibernateException he) {
      if (tx != null) {
        tx.rollback();
      }
      he.printStackTrace();
      throw he;
    }
    catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      e.printStackTrace();
      throw e;
    }

    finally {
      session.close();
    }
  }

  /**
   * This static method update the given system notification.
   */

  public static void UpdateNotification(SystemNotificationForm notificationForm,
                                        AimsUser aimsUser, FormFile emailAttachment) throws
      UniqueConstraintException, IntegrityConstraintException,
      RecordNotFoundException, HibernateException, Exception

  {
    Session session = null;
    Transaction tx = null;
    try {
      session = DBHelper.getInstance().getSession();
      tx = session.beginTransaction();

      AimsEmailMessag emailMessage = new AimsEmailMessag();

      emailMessage.setEmailMessageId(Utility.convertToLong(notificationForm.
          getSystemNotificationId()));
      emailMessage.setEmailTitle(notificationForm.getSubject());
      emailMessage.setEmailDesc(notificationForm.getDescription());
      emailMessage.setEmailText(notificationForm.getContent());
      emailMessage.setEmailCategory(SystemConstants.Email_Message_Categories[1]);
      emailMessage.setCreatedBy(aimsUser.getUsername());
      emailMessage.setCreatedDate(new Date());
      emailMessage.setLastUpdatedBy(aimsUser.getUsername());
      emailMessage.setLastUpdatedDate(new Date());
      session.update(emailMessage);
      session.flush();

      log.debug(" First Session Flushed : ");


     if (emailAttachment != null && emailAttachment.getFileSize() > 0) {

       deleteEmailAttachments(emailMessage.getEmailMessageId());
       AimsEmailMessagesAtt emailMsgAtt = new AimsEmailMessagesAtt();
       emailMsgAtt.setEmailMessageId(emailMessage.getEmailMessageId());

       boolean emailAttachmentPresent = false;
       byte[] buffer = new byte[1];
       buffer[0] = 1;

       log.debug("emailAttachment Name : " + emailAttachment.getFileName());
       log.debug("emailAttachment Type : " + emailAttachment.getContentType());
       log.debug("emailAttachment Size : " + emailAttachment.getFileSize());

       emailMsgAtt.setAttContentType(emailAttachment.getContentType());
       emailMsgAtt.setAttFileName(emailAttachment.getFileName());
       emailMsgAtt.setAtt(Hibernate.createBlob(buffer));
       emailAttachmentPresent = true;

       session.save(emailMsgAtt);
       session.flush();
       session.refresh(emailMsgAtt, LockMode.UPGRADE);
       if (emailAttachmentPresent)
         LobUtils.writeToOraBlob( (BLOB) emailMsgAtt.getAtt(),
                                 emailAttachment.getInputStream());
       session.flush();
       tx.commit();

       if (emailAttachmentPresent)
         emailAttachment.destroy();

     }

     log.debug(" Attachment Saved : ");


      AimsEventNotificationLite eventNotification =
          getEventNotificationByMessageId(emailMessage.getEmailMessageId().
                                          intValue());
      eventNotification.setMessageId(emailMessage.getEmailMessageId());
      eventNotification.setEventId(Utility.convertToLong(notificationForm.
          getEventId()));
      eventNotification.setNotificationTitle(emailMessage.getEmailTitle());
      eventNotification.setMedia("E");
      eventNotification.setStatus("ACTIVE");
      eventNotification.setIncludeVzwAccountManager(notificationForm.
          getVzwAccountManager());
      eventNotification.setIncludeVzwAccountManager(MiscUtils.checkYesNo(
          notificationForm.getVzwAccountManager()));
      eventNotification.setIncludeVzwAppsContact(MiscUtils.checkYesNo(
          notificationForm.getAppManager()));


      deleteNotificationRoles(eventNotification.getNotificationId().intValue());
      deleteAdHocRecipients(eventNotification.getNotificationId().intValue());
      session.update(eventNotification);
      session.flush();

      log.debug(" Second Session Flushed : ");

      if (notificationForm.getRoleIds() != null)
      {
          AimsNotificationRoleLite roleLite = null;
            for (int i = 0; notificationForm.getRoleIds().length > i; i++) {
            roleLite = new AimsNotificationRoleLite();
            roleLite.setNotificationId(eventNotification.getNotificationId());
            roleLite.setRoleId(notificationForm.getRoleIds()[i]);
            session.save(roleLite);
          }

          session.flush();
          log.debug(" Third Session Flushed : ");
      }

      AimsNotifAdHocRecipientLite recipient = null;
      String[] strArray = StringFuncs.tokenize(notificationForm.
                                               getRecipientEmail(), ",");

      for (int i = 0; i < strArray.length; i++) {
        recipient = new AimsNotifAdHocRecipientLite();
        recipient.setNotificationId(eventNotification.getNotificationId());
        recipient.setEmailAddress(strArray[i]);
        log.debug(" Email Address : " + strArray[i]);
        session.save(recipient);
      }
      session.flush();

      log.debug(" Fourth Session Flushed : ");
      tx.commit();

      log.debug(" Transaction Committed : ");

    }
    catch (JDBCException je) {
      if (tx != null) {
        tx.rollback();

      }
      //String exMessage = je.getMessage();
      //changed je.getMessage() to je.getCause().toString(), making it compatible with oracle.jar
      String exMessage = je.getCause().toString();
      if (DBErrorFinder.searchUniqueConstraintErrors(exMessage, SystemConstants.UNIQUE_CONSTRAINT_KEYS, new UniqueConstraintException())) {
        throw new UniqueConstraintException();
      }
      else {
        je.printStackTrace();
        throw new HibernateException(je);
      }
    }
    catch (HibernateException e) {
      if (tx != null) {
        tx.rollback();
      }
      e.printStackTrace();
      throw e;
    }
    catch (Exception e) {
     if (tx != null) {
       tx.rollback();
     }
     e.printStackTrace();
     throw e;
   }

    finally {
      session.close();
    }
  }

  /**
   * This static method gets the list of system roles from the
   * database which are available to the current user.
   */

  public static Collection getSysRoles() throws HibernateException {

    Collection collection = null;
    Session session = null;
    try {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.masters.AimsSysRol as sysrole order by sysrole.roleName");
      log.debug("No of records returned: " + collection.size());

    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      session.close();
    }

    return collection;
  }





  /**
   *  This static method gets the blob resource for a given message attachment.
   */
  public static Object[] getMessageAttachment(Long emailMessageId) throws
      HibernateException {

    Session session = null;
    Collection collection = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    Object[] resourceValues = null;

    try {

      queryStringBuffer.append("select ")
          .append("		att.att, ")
          .append("		att.attFileName, ")
          .append("		att.attContentType ")
          .append(" from ")
          .append("		com.netpace.aims.model.events.AimsEmailMessagesAtt att ")
          .append(" where ")
          .append("		att.emailMessageId = :emailMessageId ");

      session = DBHelper.getInstance().getSession();

      collection = session.find(queryStringBuffer.toString(), emailMessageId,
                                new LongType());

      for (Iterator iter = collection.iterator(); iter.hasNext(); ) {
        resourceValues = (Object[]) iter.next();
      }

    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      if (session != null)
        session.close();
    }

    return resourceValues;
  }

  /**
    *  This static method gets the message attachment name by given email message Id.
    */

  public static Collection getMessageAttachmentsName(Long emailMessageId) throws
      HibernateException {

    Session session = null;
    ArrayList coll = new ArrayList();
    Collection collection = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    AimsEmailMessagesAtt msgAtt = null;
    Object[] ObjValues = null;

    try {
      queryStringBuffer.append("select ")
          .append("		att.emailMessageId, ")
          .append("		att.attFileName ")
          .append(" from ")
          .append("		com.netpace.aims.model.events.AimsEmailMessagesAtt att ")
          .append(" where ")
          .append("		att.emailMessageId = ").append(emailMessageId).append(
          " ORDER BY att.attFileName ");

      session = DBHelper.getInstance().getSession();

      collection = session.find(queryStringBuffer.toString());

      for (Iterator iter = collection.iterator(); iter.hasNext(); ) {
        msgAtt = new AimsEmailMessagesAtt();
        ObjValues = (Object[]) iter.next();
        msgAtt.setEmailMessageId( (Long) ObjValues[0]);
        msgAtt.setAttFileName( (String) ObjValues[1]);
        coll.add(msgAtt);
      }

      return coll;

    }
    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      if (session != null)
        session.close();
    }

  }
}
