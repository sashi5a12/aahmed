package	com.netpace.aims.bo.system;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.IntegerType;
import com.netpace.aims.model.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.bo.core.*;

import org.apache.log4j.Logger;
import java.util.*;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.system.AimsEmailMessag;


/**
 * This	class	is responsible for getting the BO for aims platform.
 * It	has	static methods for getting the aims platform.
 * @author Fawad Sikandar
 */
public class AimsEmailMessageManager
{
	static Logger	log	=	Logger.getLogger(AimsEmailMessageManager.class.getName());

	/**
	 *	This static	method gets 	the	platform in the database which are available
	 *	to the current user.
	 */
	public static	Collection getEmailMessageList()	throws HibernateException
	{
		Collection collection	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			collection = session.find("from	com.netpace.aims.model.system.AimsEmailMessag as emailMessage order by emailMessage.emailTitle");
                        log.debug("No. of Email Messages : " + collection.size());
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw	e;
		}
		finally
		{
                  session.close();
		}
		return collection;
	}

	/**
	 *	This static	method gets 	the	platform in the database which are available
	 *	to the current user.
	 */
	public static	Collection getCannedEmailMessageList()	throws HibernateException
	{
		Collection collection	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			collection = session.find("from	com.netpace.aims.model.system.AimsEmailMessag as emailMessage where emailMessage.emailCategory = 'C' order by emailMessage.emailTitle");
                        log.debug("No. of Email Messages : " + collection.size());
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw	e;
		}
		finally
		{
                  session.close();
		}
		return collection;
	}

	/**
	 *	This static method returns platform for a given Id.
         *
    	 */

	public static	AimsEmailMessag getEmailMessage(int emailMessageId) throws RecordNotFoundException, HibernateException
	{
		AimsEmailMessag emailMessage =	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Query	query	= session.createQuery("select	from com.netpace.aims.model.system.AimsEmailMessag as emailMessage where emailMessage.emailMessageId = :emailMessageId");
			query.setInteger("emailMessageId", emailMessageId);

			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				emailMessage	=	(AimsEmailMessag)	it.next();
				log.debug("Email Message"	+	emailMessage.toString() );
			}
			if (emailMessage == null)
			{
                          throw new RecordNotFoundException("error.security");
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw	e;
		}
		finally
		{
			session.close();
		}

		return emailMessage;
	}

	/**
	 *	This static method deletes aims plateform represented by a given plateformId
	 *
	 */
	public static int deleteEmailMessage(int emailMessageId) throws	IntegrityConstraintException, HibernateException
	{
		int	delCount = 0;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();
			delCount = session.delete("from	com.netpace.aims.model.system.AimsEmailMessag as emailMessage where emailMessage.emailMessageId = :emailMessageId",
									new	Integer(emailMessageId),	new	IntegerType());

			tx.commit();
		}

		catch(JDBCException je)
		{
			String exMessage = je.getMessage();
			if (DBErrorFinder.searchIntegrityConstraintErrors(exMessage,SystemConstants.INTEGRITY_CONSTRAINT_KEYS,new IntegrityConstraintException()))
			{
				throw new IntegrityConstraintException ();
			}

			else
			{
				je.printStackTrace();
				throw new HibernateException(je);
			}

		}

		catch(HibernateException	e)
		{
			e.printStackTrace();
			throw	e;
		 }
		 finally
		 {
			session.close();
		 }

		return delCount;
	}

	/**
	*	 This	static method	updates	a	given	platform.
	*/
	public static	void saveOrUpdateEmailMessage(AimsEmailMessag emailMessage) throws	UniqueConstraintException, HibernateException
	{
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();

			DBHelper.getInstance().save(emailMessage);
		}

		catch(JDBCException je)
		{
			String exMessage = je.getMessage();


			if (DBErrorFinder.searchUniqueConstraintErrors(exMessage,SystemConstants.UNIQUE_CONSTRAINT_KEYS,new UniqueConstraintException()))
			{
    			  throw new UniqueConstraintException ();
                   	}

			else
			{
			  je.printStackTrace();
			  throw new HibernateException(je);
			}

	    }

		catch(HibernateException	e)
		{
			e.printStackTrace();
			throw	e;
		}
		finally
		{
			session.close();
		}
	}


}