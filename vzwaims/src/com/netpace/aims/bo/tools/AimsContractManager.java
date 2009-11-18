package	com.netpace.aims.bo.tools;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import com.netpace.aims.model.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;

import org.apache.log4j.Logger;
import java.util.*;
import java.text.*;

import com.netpace.aims.model.core.*;


/**
 * This	class	is responsible for getting the BO for	Contracts.
 * It	has	static methods for getting the Contracts Details.
 * @author Ahson Imtiaz
 */
public class AimsContractManager
{

	static Logger	log	=	Logger.getLogger(AimsContractManager.class.getName());

	/*
	 *Collection will be returned having all contracts in the system
	 **/
	public static	Collection getAllContracts()	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		collection = session.find("from com.netpace.aims.model.core.AimsContract aimscontract WHERE aimscontract.status = 'A' order by lower(aimscontract.contractTitle)");

	  		log.debug("No of records returned: " + collection.size() );

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

	    return collection;

	}

  /* */
  public static Collection getAimsContracts(java.lang.Long[] lngContractIds) throws HibernateException
  {
    Session session = null;

    try
    {
      session = DBHelper.getInstance().getSession();
      String strQuery = "from com.netpace.aims.model.core.AimsContract as acontract ";

      for (int iCount=0 ; lngContractIds != null && iCount < lngContractIds.length ; iCount++)
      {
		 if (iCount == 0)
      		strQuery += " where acontract.contractId = " + lngContractIds[iCount].toString();
      	 else
      	 	strQuery += " OR acontract.contractId = " + lngContractIds[iCount].toString();
      }

	  //log.debug("*********** Contracts Query: " + strQuery);
      return session.find(strQuery);

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

  }

}