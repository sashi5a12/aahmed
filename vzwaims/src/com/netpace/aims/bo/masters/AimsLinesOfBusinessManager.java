package	com.netpace.aims.bo.masters;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.IntegerType;
import com.netpace.aims.model.*;
import com.netpace.aims.model.masters.*;

//import javax.servlet.ServletException;
import com.netpace.aims.bo.core.*;

import org.apache.log4j.Logger;
import java.util.*;

 
/**
 * This	class	is responsible for getting the BO	for	Lines of Business.
 * It	has	static methods for getting the Lines Of Business.
 * @author Adnan Makda
 */
public class AimsLinesOfBusinessManager
{
	static Logger	log	=	Logger.getLogger(AimsLinesOfBusinessManager.class.getName());

	/**
	 *	This static	method gets	the	Lines of Business in the database which are available
	 *	to the current user.
	 */
	public static	Collection getLinesOfBusiness()	throws HibernateException
	{		 
		Collection collection	=	null;		
		Session	session	=	null;
		try
		{					
			session	=	DBHelper.getInstance().getSession();
			collection = session.find("from	com.netpace.aims.model.masters.AimsLinesOfBusiness as linesOfBusiness	order	by line_of_business_name");
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
	 *	This static	method returns a AimsLinesOfBusiness object	for	a	given	lineOfBusinessId (primary_key)		 
	 */
	public static	AimsLinesOfBusiness	getLineOfBusiness(int lineOfBusinessId)	throws HibernateException
	{
		AimsLinesOfBusiness	lineOfBusiness	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Query	query	=	session.createQuery("select	from com.netpace.aims.model.masters.AimsLinesOfBusiness	as linesOfBusiness where linesOfBusiness.lineOfBusinessId = :lineOfBusinessId");
			query.setInteger("lineOfBusinessId",	lineOfBusinessId);
			
			for	(Iterator	it = query.iterate();	it.hasNext();) 
			{
				lineOfBusiness	=	(AimsLinesOfBusiness)	it.next();
				log.debug("Lines Of Business:	"	+	lineOfBusiness.toString() );
			}
			if (lineOfBusiness	== null)
			{
				//throw	new	GenericException();
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

		return lineOfBusiness;
	}	 
		
	/**
	 *	This static	method deletes a lineOfBusiness represented by	a	given	linesOfBusinessId (primary_key)	 
	 *	It returns the count (most probably	1) of	the	number of	linesOfBusiness deleted.
	 */
	public static int deleteLineOfBusiness(int lineOfBusinessId) throws	IntegrityConstraintException, HibernateException
	{
		int	delCount = 0;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();
			delCount = session.delete("from	com.netpace.aims.model.masters.AimsLinesOfBusiness as linesOfBusiness where	linesOfBusiness.lineOfBusinessId	=	:lineOfBusinessId",
									new	Integer(lineOfBusinessId),	new	IntegerType());
		
			tx.commit();
		}
		
		catch(JDBCException je)
		{
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-02292: integrity constraint (AIMS.FK_ALLIANCE_LINES_OF_BUS_2) violated - child record found") > -1)
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
	*	 This	static method	updates	a	given	lineOfBusiness.		
	*/
	public static	void saveOrUpdateLineOfBusiness(AimsLinesOfBusiness lineOfBusiness) throws	UniqueConstraintException, HibernateException
	{
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Transaction	tx = session.beginTransaction();
			
			DBHelper.getInstance().save(lineOfBusiness);
		}
		
		catch(JDBCException je)
		{
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint (AIMS.AK_LINES_OF_BUSINESS_NAME) violated") > -1)
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