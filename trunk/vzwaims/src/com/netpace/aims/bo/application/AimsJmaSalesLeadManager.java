package com.netpace.aims.bo.application;

import net.sf.hibernate.*;
import net.sf.hibernate.type.LongType;


import org.apache.log4j.Logger;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.*;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.dataaccess.valueobjects.JmaSalesLeadVO;
import com.netpace.aims.model.*;
import com.netpace.aims.util.*;
import com.netpace.aims.model.application.*;


/**
 * Decsription here
 * @author Arsaln Qureshi
 */
public class AimsJmaSalesLeadManager
{
	 
	static Logger log = Logger.getLogger(AimsJmaSalesLeadManager.class.getName());
	
	  
	public static void saveOrUpdate(AimsJmaSalesLead salesLead)throws HibernateException
	{
		Session session = null;
		Transaction tx = null;

		try
		{

			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(salesLead);

			session.flush();
			tx.commit();

		}
		catch (HibernateException e) {
			if (tx != null)
				tx.rollback();

			e.printStackTrace();
			throw e;
		}

		finally {
			session.close();
		}

	}
	
	
	 public static AimsJmaSalesLead getSalesLeadById(java.lang.Long salesLeadId)throws HibernateException {
		Session session = null;
		AimsJmaSalesLead objAimsEntApp = null;
		try {
			session = DBHelper.getInstance().getSession();
			objAimsEntApp = (AimsJmaSalesLead) session
					.find(
							"from com.netpace.aims.model.application.AimsJmaSalesLead as sl where sl.salesLeadId = "
									+ salesLeadId.toString()).get(0);
			
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

		return objAimsEntApp;
	}


	 public static Collection getPartners() throws HibernateException
	    {
	    	Collection collection = null;
	        Session session = null;
	        StringBuffer queryStringBuffer = new StringBuffer();
	        Long [] longArray=null; 
	        try
	        {
	        	session = DBHelper.getInstance().getSession();
				queryStringBuffer
						.append(" select distinct ")
						.append("        alliance.allianceId, ")
						.append("        alliance.companyName ")
						
						.append(" from   com.netpace.aims.model.core.AimsAllianc as alliance,  ")
						.append("        com.netpace.aims.model.core.AimsContract contract, ")
						.append("        com.netpace.aims.model.core.AimsAllianceContract allianceContract, ")
						.append("        com.netpace.aims.model.core.AimsUser user, ")
		                .append("        com.netpace.aims.model.core.AimsContact vzwAccountManager, ")
		                .append("        com.netpace.aims.model.core.AimsUser allianceAdminUser, ")
		                .append("        com.netpace.aims.model.core.AimsContact allianceAdminContact ")
						
						.append("where ")
                        .append("        1=1 ")
						.append("        and alliance.isJMAAlliance= 'Y' ")
						.append("        and alliance.isJmaInfoComplete= 'Y' ")
						.append("        and allianceContract.status = 'ACCEPTED' ")
						.append("        and contract.status = 'A' ")
						.append("        and contract.platformId = 5 ")
						
						.append("       and allianceContract.allianceId (+) = alliance.allianceId  ")
		                .append("       and allianceContract.contractId   = contract.contractId (+) ")
		                .append("       and alliance.vzwAccountManager = user.userId (+) ")
		                .append("       and user.aimsContact.contactId = vzwAccountManager.contactId (+) ")
		                .append("       and alliance.aimsUserByAdminUserId = allianceAdminUser.userId (+) ")
		                .append("       and allianceAdminUser.aimsContact.contactId = allianceAdminContact.contactId (+) ");
				
				
						
					
				queryStringBuffer.append("    order by alliance.companyName ");
				  
				Query query = session.createQuery(queryStringBuffer.toString());
				collection=query.list();
	        
	        }
	        catch (HibernateException e)
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
	 
	 public static Collection getSalesLeadRecord(int PAGE_LENGTH,int pageNo,String search_expression,String order_by,Long submittedBy,Long partnerId) throws HibernateException
	 {
		Collection collection = null;

		Session session = null;
		StringBuffer queryStringBuffer = new StringBuffer();
		try 
		{
			session = DBHelper.getInstance().getSession();
			queryStringBuffer.append(" SELECT    ")
							 .append("         salesLead.salesLeadId, ")
							 .append("         salesLead.customerName, ")
							 .append("         salesLead.solutionName, ")
							 .append("         salesLead.salesRepFullName, ")
							 .append("         aimsType.typeValue, ")
							 .append("         alliance.allianceId, ")
							 .append("         alliance.companyName, ")
							 .append("         salesLead.salesRepEmailAddress ")
							 .append(" FROM        ")
							 .append("         com.netpace.aims.model.application.AimsJmaSalesLead as salesLead, ")
							 .append("         com.netpace.aims.model.core.AimsAllianc as alliance, ")
							 .append("         com.netpace.aims.model.core.AimsTypes as aimsType ")
							 .append(" WHERE       ")
							 .append("         salesLead.jmaPartnerId = alliance.allianceId ")
							 .append("         AND salesLead.salesLeadStatus = aimsType.typeId ")
							 
							 .append("         AND salesLead.salesLeadSubmittedBy = :submittedBy ");
			
			if (partnerId != null)
                queryStringBuffer.append("  AND salesLead.jmaPartnerId = " + partnerId);
			
			if (search_expression.length() > 0)
			{
				queryStringBuffer.append(search_expression);
			}
			
			queryStringBuffer.append("    order by ")
							 .append(order_by);
			
			Query query = session.createQuery(queryStringBuffer.toString());
			query.setLong("submittedBy", submittedBy.longValue());
			
			query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (pageNo - 1));
			
			collection=query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

		return collection;

	}
	 
	 
	 public static int getSalesLeadRecordCount(String search_expression,String order_by,Long submittedBy,Long partnerId) throws HibernateException
	 {
		Collection collection = null;

		Session session = null;
		StringBuffer queryStringBuffer = new StringBuffer();
		try 
		{
			session = DBHelper.getInstance().getSession();
			queryStringBuffer.append(" SELECT    ")
							 .append("         salesLead.salesLeadId, ")
							 .append("         salesLead.customerName, ")
							 .append("         salesLead.solutionName, ")
							 .append("         salesLead.salesRepFullName, ")
							 .append("         aimsType.typeValue, ")
							 .append("         alliance.allianceId, ")
							 .append("         alliance.companyName, ")
							 .append("         salesLead.salesRepEmailAddress ")
							 .append(" FROM        ")
							 .append("         com.netpace.aims.model.application.AimsJmaSalesLead as salesLead, ")
							 .append("         com.netpace.aims.model.core.AimsAllianc as alliance, ")
							 .append("         com.netpace.aims.model.core.AimsTypes as aimsType ")
							 .append(" WHERE       ")
							 .append("         salesLead.jmaPartnerId = alliance.allianceId ")
							 .append("         AND salesLead.salesLeadStatus = aimsType.typeId ")
							 
							 .append("         AND salesLead.salesLeadSubmittedBy = :submittedBy ");
			
			if (partnerId != null)
                queryStringBuffer.append("  AND salesLead.jmaPartnerId = " + partnerId);
			
			if (search_expression.length() > 0)
			{
				queryStringBuffer.append(search_expression);
			}
			
			queryStringBuffer.append("    order by ")
							 .append(order_by);
			
			Query query = session.createQuery(queryStringBuffer.toString());
			query.setLong("submittedBy", submittedBy.longValue());
			collection=query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

		if(collection==null)
			return 0;
		else
		return collection.size();

	}

	 
	 public static int delete(Long salesLeadId) throws HibernateException
	    {
		    Session session = null;
		    Transaction tx = null;
	        int delCount = 0;
	        try
	        {
	        	session = DBHelper.getInstance().getSession();
	        	tx = session.beginTransaction();
	            delCount =
	                    session.delete(
	                        "from	com.netpace.aims.model.application.AimsJmaSalesLead as salesLead	where	salesLead.salesLeadId	=	:salesLeadId",
	                        salesLeadId,
	                        new LongType());
	        	
	            session.flush();
	            tx.commit();
	        }
	        catch (HibernateException he)
	        {
	        	if (tx != null)
					tx.rollback();
	            he.printStackTrace();
	            throw he;
	        }
	        finally
	        {
	        	session.close();
	        }
	        return delCount;
	    }
		 
	 
}
 