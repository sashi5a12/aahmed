package com.netpace.aims.bo.profile;

import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;

/**
 * Alliance User Profile Manager.
 * It has static methods for accessing the BO.
 * @author Ahson Imtiaz
 */

public class AimsProfileManager
{

  static Logger log = Logger.getLogger(AimsProfileManager.class.getName());

  /**
   *  This static method save or update the data for user profile.
   *
   */

  public static void saveOrUpdate(AimsUser user) throws HibernateException, AimsEmailAddressException, AimsUsernameException
  {

      Session session = null;
      Transaction tx = null;
      
      try
      {
         session = DBHelper.getInstance().getSession();
         tx = session.beginTransaction();
			
         session.saveOrUpdate(user);
         tx.commit();

      }
      catch(HibernateException e)
      {
          if (tx!=null)
          {
              tx.rollback();
          }
         e.printStackTrace();
         throw e;
      }
      finally
      {
         session.close();
      }
  }
  public static void changePassword(Long userId, String oldPassword, String newPassword) throws HibernateException, AimsException{

		Session session = null;
		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;
		Transaction tx = null;

		try {
			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			AimsUser user=(AimsUser)session.load(AimsUser.class, userId);
			if (!user.getPassword().equals(oldPassword)){
				AimsException aimsException = new AimsException();
				aimsException.setMessageKey("error.changePassword.invalid.currentPassword");
				throw aimsException;
			}
			user.setPassword(newPassword);
			session.saveOrUpdate(user);
			tx.commit();

			ConOra = session.connection();
			statement = ConOra.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");

			statement.setLong(1, user.getUserId().longValue());
			statement.setString(2, "U");
			statement.execute();

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}			
			e.printStackTrace();
			throw e;
		} catch (java.sql.SQLException sqle) {
			if (tx != null) {
				tx.rollback();
			}
			else {
				sqle.printStackTrace();
				throw new HibernateException(sqle);
			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ignore) {
				}
			}
			session.close();
		}
	}
  
    /**
	 * This static method validates the uniqueness of the user name. Otherwise
	 * throw AimsUsernameException.
	 */

      public static boolean hasUsernameAlreadyReserved(String username, java.lang.Long UserId ) throws AimsUsernameException, HibernateException
      {

        AimsUser user = null;
        Session session = null;
        try
        {
          session = DBHelper.getInstance().getSession();
          Query query = session.createQuery("select from com.netpace.aims.model.core.AimsUser as user where user.username = :username AND user.userId <> :userId");
          query.setString("username", username);
          query.setString("userId",UserId.toString());

             for (Iterator it = query.iterate(); it.hasNext();)
               {
                 user = (AimsUser) it.next();
                 log.debug("userName : " + user.getUsername());
                 if( username.equalsIgnoreCase(user.getUsername()))
                     throw new AimsUsernameException();
               }

         }
         catch(HibernateException e)
         {
          e.printStackTrace();
          throw e;
         }
         finally
         {
          session.close();
         }

        return false;
      }
      
   /**
    *  This static method validates the uniqueness of the email address.
    *  Otherwise throw AimsEmailAddressException.
    */

       public static boolean hasEmailAddressAlreadyReserved(String emailAddress, java.lang.Long ContactId ) throws AimsEmailAddressException, HibernateException
           {

             AimsContact contact = null;
             Session session = null;
             try
             {
               session = DBHelper.getInstance().getSession();
               Query query = session.createQuery("select from com.netpace.aims.model.core.AimsContact as contact where contact.emailAddress = :emailAddress AND contact.contactId <> :contactId");
               query.setString("emailAddress", emailAddress);
               query.setString("contactId", ContactId.toString());

                  for (Iterator it = query.iterate(); it.hasNext();)
                    {
                      contact = (AimsContact) it.next();
                      log.debug("emailAddress : " + contact.getEmailAddress());
                      if( emailAddress.equalsIgnoreCase(contact.getEmailAddress()))
                         throw new AimsEmailAddressException();

                    }

              }
              catch(HibernateException e)
              {
               e.printStackTrace();
               throw e;
              }
              finally
              {
               session.close();
              }

             return false;
      }

}