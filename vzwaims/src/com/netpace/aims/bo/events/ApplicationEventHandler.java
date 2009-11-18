package com.netpace.aims.bo.events;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import oracle.jdbc.driver.OracleTypes;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.StringFuncs;

public class ApplicationEventHandler extends BaseEventHandler
{
    public void handleEvent(AimsEventObject eventObj)
    {
        Session session = null;
        Connection ConOra = null;
        CallableStatement statement = null;
        CallableStatement statement2 = null;
        ResultSet results = null;
        ResultSet embeddedResults = null;
        Collection attachments = new ArrayList();

        try
        {
            String email_address = null;
            String message_title = null;
            String message_body = null;
            int message_id = 0;

            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();
            statement = ConOra.prepareCall("call AIMS_NOTIFICATIONS_PKG.get_apps_notification_cursor(?,?,?,?,?,?)");
            statement2 = ConOra.prepareCall("call AIMS_NOTIFICATIONS_PKG.insert_messages(?,?,?,?,?,?,?)");

            Hashtable eventObjProperties = eventObj.getProperties();
            System.out.println("\n\n\n Event Object Properties " + eventObjProperties.toString());
            System.out.println("\n\n\n Event Object Properties With Pattern" + eventObj.getPropertiesWithPattern().toString());

            System.out.println("\n\n\n ApplicationId : " + eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID));
            System.out.println("\n\n\n AllianceId : " + eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID));

            /*IMPORTANT NOTE for "HANDLER_PROPERTY_USER_IDS" and "HANDLER_PROPERTY_ADHOC_EMAILS":
             *   Emails will be sent to these emails, for ALL  Notifications tied to This Event.
             *   Hence be careful when using this and be sure to name Events as such.
             *   For example if a Alliance User is being set in one of these properties (like in the case of Registration,
             *   where the user is just being created), name the event like "ALLIANCE_REGISTERED_EVENT_FOR_ALLIANCE_ONLY",
             *   so as to the same event is not used to send notifications to Verizon User.
             *
             */
            System.out.println("\n\n\n UserIds : " + eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS));
            System.out.println("\n\n\n Adhoc Emails : " + eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ADHOC_EMAILS));
            System.out.println("\n\n\n EventId : " + ((AimsEventLite) eventObj.getEvent()).getEventId().longValue());

            if (eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID) != null)
                statement.setLong(1, (new Long(eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID).toString())).longValue());
            else
                statement.setLong(1, 0);

            if (eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID) != null)
                statement.setLong(2, (new Long(eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID).toString())).longValue());
            else
                statement.setLong(2, 0);

            statement.setLong(3, ((AimsEventLite) eventObj.getEvent()).getEventId().longValue());
            statement.setString(4, eventObj.getPropertiesWithPattern().toString());

            if (eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS) != null)
                statement.setString(5, eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS).toString());
            else
                statement.setString(5, null);

            statement.registerOutParameter(6, OracleTypes.CURSOR);

            //execute the db statement
            System.out.println("\n\n\n ApplicationEventHandler: B E F O R E   E X E C U T E");
            statement.execute();
            System.out.println("\n\n\n ApplicationEventHandler notificationId : A F T E R   E X E C U T E ");

            results = (ResultSet) statement.getObject(6);
            if (results != null)
                results.setFetchSize(100);

            while (results.next())
            {
                System.out.println("ApplicationEventHandler notificationId : " + results.getLong(1));

                message_title = results.getString(2);
                message_body = results.getString(3);
                message_id = results.getInt(4);

                attachments = GenericEventHandler.getAttachments(new Long(new Integer(message_id).toString()));

                System.out.println("ApplicationEventHandler MessageTitle : " + message_title);
                System.out.println("ApplicationEventHandler MessageText : " + message_body);

                embeddedResults = (ResultSet) results.getObject(5);

                for (int j = 0; embeddedResults.next(); j++)
                {
                    email_address = embeddedResults.getString(1);

                    System.out.println("ApplicationEventHandler EmailAddress : " + embeddedResults.getString(1));
                    System.out.println("ApplicationEventHandler Notification Type : " + embeddedResults.getString(2));
                    System.out.println("AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE : " + AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE);
                    if (AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE.equalsIgnoreCase(embeddedResults.getString(2)))
                    {
                        System.out.println("\n\n\n AN  EMAIL  NEEDS  TO  BE  SENT !!!\n\n\n");
                        try
                        {
                            statement2.setString(1, email_address);
                            statement2.setString(2, message_title);
                            statement2.setString(3, message_body);
                            statement2.setString(4, AimsNotificationConstants.FROM_ADDRESS_ID);
                            statement2.setString(5, java.lang.Integer.toString(message_id, 10));
                            if (eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID) != null) {
                                statement2.setString(6, (String)eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID));
                            }
                            else {
                                statement2.setString(6, null);
                            }
                            if (eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID) != null) {
                                statement2.setString(7, (String)eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID));
                            }
                            else {
                                statement2.setString(7, null);
                            }

                            statement2.execute();

                            MailUtils.sendMail(email_address, AimsNotificationConstants.FROM_ADDRESS, message_title, null, message_body, attachments);
                        }
                        catch (SendFailedException sfe)
                        {
                            System.out.println("SendFailedException occured in ApplicationEventHandler ");
                            sfe.printStackTrace();
                        }
                        catch (MessagingException me)
                        {
                            System.out.println("MessagingException occured in ApplicationEventHandler ");
                            me.printStackTrace();
                        }

                    }
                }

                if (eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ADHOC_EMAILS) != null)
                {

                    String[] strArray = StringFuncs.tokenize(eventObjProperties.get(AimsNotificationConstants.HANDLER_PROPERTY_ADHOC_EMAILS).toString(), ",");

                    for (int i = 0; i < strArray.length; i++)
                    {
                        System.out.println("Adhoc EmailAddress : " + strArray[i]);
                        System.out.println("\n\n\n AN  -ADHOC-  EMAIL  NEEDS  TO  BE  SENT !!!\n\n\n");
                        try
                        {
                            MailUtils.sendMail(strArray[i], AimsNotificationConstants.FROM_ADDRESS, message_title, null, message_body, attachments);
                        }
                        catch (SendFailedException sfe)
                        {
                            System.out.println("SendFailedException occured in ApplicationEventHandler ");
                            sfe.printStackTrace();
                        }
                        catch (MessagingException me)
                        {
                            System.out.println("MessagingException occured in ApplicationEventHandler ");
                            me.printStackTrace();
                        }
                    }
                }

                embeddedResults.close();
                embeddedResults = null;
                ConOra.commit();
            }

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        finally
        {

            try
            {
                if (results != null)
                {
                    results.close();
                    results = null;
                }
                if (statement != null)
                {
                    statement.close();
                    statement = null;
                }
                if (statement2 != null)
                {
                    statement2.close();
                    statement2 = null;
                }
                session.close();
            }
            catch (HibernateException ignore)
            {}
            catch (SQLException sqle)
            {
                sqle.printStackTrace();
            }

        }

        System.out.println("ApplicationEventHandler SUCESS! SUCESS! SUCESS!");

    }
}
