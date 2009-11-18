package	com.netpace.aims.bo.system;

import java.util.*;

import org.apache.log4j.*;
import com.netpace.aims.model.*;
import net.sf.hibernate.*;


/**
 * This	class is responsible for getting the BO for system administration.
 * It	has static methods for getting the System Objects.
 * @author Fawad Sikandar
 */

public class AimsSystemManager
{
	static Logger	log	=	Logger.getLogger(AimsSystemManager.class.getName());

	/**
	 *	This static method gets the Region Objects from the database which are available
	 *	to the current user.
	 */

	  public static	Collection getRegionsList()	throws HibernateException
	  {
		Collection collection	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			collection = session.find("from	com.netpace.aims.model.masters.AimsRegion as region");
                        log.debug("No. of Regions : " + collection.size());
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
              *	This static method gets the List of Email Template PlaceHolders Objects
              * from the database which are available to the current user.
              */

           public static	Collection getEmailTemplatePHSList()	throws HibernateException
           {
                     Collection collection	=	null;
                     Session	session	=	null;
                     try
                     {
                             session	=	DBHelper.getInstance().getSession();
                             collection = session.find("from	com.netpace.aims.model.system.AimsEmailTemplatePh");
                             log.debug("No. of Email Template PHS List : " + collection.size());
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



}