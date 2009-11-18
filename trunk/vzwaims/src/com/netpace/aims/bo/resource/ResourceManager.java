package com.netpace.aims.bo.resource;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.resource.AimsResource;

public class ResourceManager {

	public static AimsResource getResource(java.lang.Long resourceId) throws HibernateException
    {
        Session session = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            Query query=session.createQuery("from com.netpace.aims.model.resource.AimsResource r where r.resourceId=:id");
            query.setLong("id", resourceId.longValue());
            List list=query.list();
            if (list !=null && list.size()>0){
            	return (AimsResource) list.get(0);
            }
            else {
            	return null;
            }

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        	if (session != null){
        		session.close();
        	}
        }

    }
}
