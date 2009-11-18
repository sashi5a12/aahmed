package com.netpace.aims.bo.core;

import net.sf.hibernate.Session;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.LongType;

import java.util.Collection;

import com.netpace.aims.model.DBHelper;
import org.apache.log4j.Logger;

public class AimsTypeManager {

    static Logger log = Logger.getLogger(AimsTypeManager.class.getName());

    public static Collection getTypesByTypeDefId(Long typeDefId) throws HibernateException {
        Collection aimsTypes = null;
        Session	session	=	null;
        StringBuffer queryStringBuffer = new StringBuffer();
        try {
            session = DBHelper.getInstance().getSession();

            queryStringBuffer.append("select aimsType ")
                             .append("from ")
                             .append("		com.netpace.aims.model.core.AimsTypes aimsType, ")
                             .append("		com.netpace.aims.model.core.AimsTypeDefs aimsTypeDef ")
                             .append("where ")
                             .append("		aimsType.typeDefId = :typeDefId ")
                             .append("		and aimsType.typeDefId = aimsTypeDef.typeDefId ")
                             .append("order by ")
                             .append("		aimsType.sortOrder ");


            aimsTypes = session.find(queryStringBuffer.toString(), typeDefId,  new LongType());

            log.debug("No. of AimsTypes : " + aimsTypes.size());
        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw	he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in getTypesByTypeDefId()");
            }
        }
        return aimsTypes;

    }//end getTypesByTypeDefId
    
    public static Collection getTypesByTypeDefId(String typeDefIds) throws HibernateException {
        Collection aimsTypes = null;
        Session	session	=	null;
        StringBuffer queryStringBuffer = new StringBuffer();
        try {
            session = DBHelper.getInstance().getSession();
            
            aimsTypes =session.find(
                    "select aimsType from com.netpace.aims.model.core.AimsTypes AS aimsType where aimsType.typeDefId  "
                        + " in ("
                        + typeDefIds
                        + ") order by aimsType.sortOrder ");

                      
        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw	he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in getTypesByTypeDefId()");
            }
        }
        return aimsTypes;

    }//end getTypesByTypeDefId

}
