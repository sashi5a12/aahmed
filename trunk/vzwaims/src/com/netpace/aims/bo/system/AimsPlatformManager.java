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
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.util.Utility;


/**
 * This	class	is responsible for getting the BO for aims platform.
 * It	has	static methods for getting the aims platform.
 * @author Fawad Sikandar
 */
public class AimsPlatformManager
{
	static Logger	log	=	Logger.getLogger(AimsPlatformManager.class.getName());

	/**
	 *	This static	method gets 	the	platform in the database which are available
	 *	to the current user.
	 */
	public static	Collection getPlatforms()	throws HibernateException
	{
		Collection collection	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			collection = session.find("from	com.netpace.aims.model.core.AimsPlatform as platform	order	by platform_name");
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

	public static	AimsPlatform	getPlatform(int platformId) throws RecordNotFoundException, HibernateException
	{
		AimsPlatform	platform =	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Query	query	=	session.createQuery("select	from com.netpace.aims.model.core.AimsPlatform as platform where platform.platformId = :platformId");
			query.setInteger("platformId",	platformId);

			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				platform	=	(AimsPlatform)	it.next();
				log.debug("Plateform	"	+	platform.toString() );
			}
			if (platform	== null)
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

		return platform;
	}

	/**
	 *	This static method deletes aims plateform represented by a given plateformId
	 *
	 */
	public static int deletePlatform(int platformId) throws	IntegrityConstraintException, HibernateException
	{
		int	delCount = 0;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();
			delCount = session.delete("from	com.netpace.aims.model.core.AimsPlatform as platform where	platform.platformId	=	:platformId",
                      	             				new	Integer(platformId),	new	IntegerType());
			tx.commit();
                        //to do - Utility Required
                        VOLookupFactory.getInstance().deleteAimsPlatformVO(Long.valueOf(""+platformId));

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
	public static	void saveOrUpdatePlateform(AimsPlatform platform) throws	UniqueConstraintException, HibernateException
	{
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();

			DBHelper.getInstance().save(platform);
                        VOLookupFactory.getInstance().updateAimsPlatformVO(platform);

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