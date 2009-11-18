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
import com.netpace.aims.model.system.AimsDeck;


/**
 * This	class	is responsible for getting the BO for aims platform.
 * It	has	static methods for getting the aims platform.
 * @author Fawad Sikandar
 */
public class AimsDeckManager
{
	static Logger	log	=	Logger.getLogger(AimsDeckManager.class.getName());

	/**
	 *	This static	method gets 	the	platform in the database which are available
	 *	to the current user.
	 */
	public static	Collection getDeckList()	throws HibernateException
	{
		Collection collection	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			collection = session.find("from	com.netpace.aims.model.system.AimsDeck as deck order by deck.deckName");
                        log.debug("No. of Decks : " + collection.size());
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

	public static	AimsDeck getDeck(int deckId) throws RecordNotFoundException, HibernateException
	{
		AimsDeck deck =	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Query	query	= session.createQuery("select	from com.netpace.aims.model.system.AimsDeck as deck where deck.deckId = :deckId");
			query.setInteger("deckId", deckId);

			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				deck	=	(AimsDeck)	it.next();

			}
			if (deck == null)
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

		return deck;
	}

	/**
	 *	This static method deletes aims plateform represented by a given plateformId
	 *
	 */
	public static int deleteDeck(int deckId) throws	IntegrityConstraintException, HibernateException
	{
		int	delCount = 0;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();
			delCount = session.delete("from	com.netpace.aims.model.system.AimsDeck as deck where deck.deckId = :deckId",
									new	Integer(deckId),	new	IntegerType());

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
	public static	void saveOrUpdateDeck(AimsDeck deck) throws	UniqueConstraintException, HibernateException
	{
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();

			DBHelper.getInstance().save(deck);
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