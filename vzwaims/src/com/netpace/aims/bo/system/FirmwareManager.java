package com.netpace.aims.bo.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.type.LongType;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.core.IntegrityConstraintException;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.dataaccess.valueobjects.FirmwareVO;
import com.netpace.aims.dataaccess.valueobjects.FirmwareInfoVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.model.system.AimsDeviceFirmware;
import com.netpace.aims.model.system.AimsVzappFirmwareEmailLog;
import com.netpace.aims.util.AimsConstants;


public class FirmwareManager {
	private static final Logger log = Logger.getLogger(FirmwareManager.class.getName());
	
	public static List getFirmwaresList(int pageLength, int pageNo)throws HibernateException{
		log.debug("FirmwareManager.getFirmwaresList: Start PAGE_LENGTH="+pageLength+ " ,pageNo="+pageNo);
		Session session = null;
		List firmwares = new ArrayList();
		
        try {
			session = DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

			queryStringBuffer.append("select d, f from AimsDeviceFirmware as f, AimsDevic as d where d.deviceId = f.deviceId order by lower(d.deviceModel), lower(f.firmwareName), f.mrNumber");

			Query query = session.createQuery(queryStringBuffer.toString());
			query.setMaxResults(pageLength);
			query.setFirstResult(pageLength * (pageNo - 1));
			Object[] resultValues = null;			
            for (Iterator it = query.iterate(); it.hasNext();){
            	resultValues=(Object[])it.next();
            	AimsDevic device=(AimsDevic)resultValues[0];
            	AimsDeviceFirmware firmware=(AimsDeviceFirmware)resultValues[1];
            	String[] columns=new String[6];
            	columns[0] = device.getDeviceModel();
            	columns[1] = firmware.getFirmwareName();
            	columns[2] = firmware.getMrNumber().toString();
            	columns[3] = ((AimsPlatform)device.getPlatform().iterator().next()).getPlatformName();
            	columns[4] = firmware.getStatus();
            	columns[5] = firmware.getFirmwareId().toString();
            	firmwares.add(columns);       
            }
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("FirmwareManager.getFirmwaresList: End size="+firmwares.size());
		return firmwares;
	}
	
	public static List getFirmwaresList(String status)throws HibernateException{
		log.debug("FirmwareManager.getFirmwaresList: Start ");
		Session session = null;
		List firmwares = new ArrayList();
        try {
			session = DBHelper.getInstance().getSession();
			StringBuffer sb = new StringBuffer();

			sb.append("select d, f from AimsDeviceFirmware as f, AimsDevic as d where d.deviceId = f.deviceId ");
			if (StringUtils.isNotEmpty(status) && (AimsConstants.ACTIVE.equals(status) || AimsConstants.IN_ACTIVE.equals(status))){
				sb.append(" and f.status = :status "); 
			}
			sb.append(" order by lower(d.deviceModel),  f.mrNumber ");

			Query query = session.createQuery(sb.toString());
			if (StringUtils.isNotEmpty(status) && (AimsConstants.ACTIVE.equals(status) || AimsConstants.IN_ACTIVE.equals(status))){
				query.setString("status", status);
			}
			
			Object[] resultValues = null;
			List firmwareList=new ArrayList();
			FirmwareVO vo=new FirmwareVO();
			List deviceIds = new ArrayList();
            for (Iterator it = query.iterate(); it.hasNext();){
            	resultValues=(Object[])it.next();
            	AimsDevic device=(AimsDevic)resultValues[0];
            	AimsDeviceFirmware firmware=(AimsDeviceFirmware)resultValues[1];

            	Long deviceId=device.getDeviceId();
            	String phoneModel=device.getDeviceModel();
                Long firmwareId = firmware.getFirmwareId();
                String mrNumber = "MR-"+firmware.getMrNumber();
                String firmwareName = firmware.getFirmwareName();
            	
                if(!deviceIds.contains(deviceId)){
                	vo=new FirmwareVO();
                	firmwareList = new ArrayList();
                	deviceIds.add(deviceId);
                	vo.setPhoneModel(phoneModel);
                	vo.setFirmwareList(firmwareList);
                	firmwares.add(vo);
                }
                
                Object[] firmwareObjs=new Object[3];
                firmwareObjs[0] = firmwareId;
                firmwareObjs[1] = mrNumber;
                firmwareObjs[2] = firmwareName;
                firmwareList.add(firmwareObjs);
            }
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("FirmwareManager.getFirmwaresList: End ");
		return firmwares;
	}

    public static List getFirmwareInfoVOListByFirmwareIds(Long[] firmwareIds, String status)throws HibernateException{
		log.debug("FirmwareManager.getFirmwareVOListByFirmwareIds: Start ");
		Session session = null;
		Query query  = null;

        Object[] resultValues;
        StringBuffer sb = new StringBuffer();

        List firmwareInfoVOList = new ArrayList();
        FirmwareInfoVO firmwareInfo = null;

        if(firmwareIds!=null && firmwareIds.length>0) {
            try {
                session = DBHelper.getInstance().getSession();

                sb.append("select device.deviceModel, firmware.firmwareId, firmware.firmwareName, firmware.mrNumber ")
                        .append(" from AimsDeviceFirmware as firmware, AimsDevic as device ")
                        .append(" where device.deviceId = firmware.deviceId ")
                        .append(" and firmware.firmwareId in (:firmwareIds)");
                if (StringUtils.isNotEmpty(status) && (AimsConstants.ACTIVE.equals(status) || AimsConstants.IN_ACTIVE.equals(status))){
                    sb.append(" and firmware.status = :status ");
                }

                query = session.createQuery(sb.toString());
                if (StringUtils.isNotEmpty(status) && (AimsConstants.ACTIVE.equals(status) || AimsConstants.IN_ACTIVE.equals(status))){
                    query.setString("status", status);
                }
                query.setParameterList("firmwareIds", firmwareIds);

                for (Iterator it = query.iterate(); it.hasNext();){
                    resultValues=(Object[])it.next();
                    firmwareInfo = new FirmwareInfoVO();
                    firmwareInfo.setPhoneModel((String)resultValues[0]);
                    firmwareInfo.setFirmwareId((Long)resultValues[1]);
                    firmwareInfo.setFirmwareName((String)resultValues[2]);
                    firmwareInfo.setMrNumber("MR-"+(Integer)resultValues[3]);
                    firmwareInfoVOList.add(firmwareInfo);
                }
            } catch (HibernateException e) {
                log.error(e,e);
                throw e;
            } finally {
                if (session != null){
                    session.close();
                    log.debug("Session closed in FirmwareManager.getFirmwareVOListByFirmwareIds");
                }
            }
        }//end if null
        log.debug("FirmwareManager.getFirmwareVOListByFirmwareIds: End, results size: "+firmwareInfoVOList.size());
		return firmwareInfoVOList;
	}

    public static int getFirmwaresCount() throws HibernateException {
		log.debug("FirmwareManager.getFirmwaresList: Start");
		Session session = null;
		int rows = 0;

		try {

			session = DBHelper.getInstance().getSession();
			Query query = session.createQuery("select count(*) from AimsDeviceFirmware");
			rows = ((Integer) query.iterate().next()).intValue();
			log.debug("Rows Counted : " + rows);

		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			session.close();
		}
		log.debug("FirmwareManager.getFirmwaresList: End");
		return rows;
	}
	
	public static void saveOrUpdate(AimsDeviceFirmware firmware) throws HibernateException, Exception {
		log.debug("FirmwareManager.saveOrUpdate: Start");
		Session session = null;
		Transaction tx = null;

		try {
			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(firmware);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			if (e.getMessage().indexOf("ORA-00001") > 0) {
				UniqueConstraintException uce = new UniqueConstraintException();
				uce.setMessageKey("error.FirmwareForm.unique.firmwareName");
				throw uce;
			}
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("FirmwareManager.saveOrUpdate: End");
	}
	
	public static AimsDeviceFirmware getFirmwareById(Long id)throws HibernateException{
		log.debug("FirmwareManager.getFirmwareById: Start id="+id);
		Session session = null;
		AimsDeviceFirmware firmware=new AimsDeviceFirmware();
		try {
			session = DBHelper.getInstance().getSession();
			Query query = session.createQuery("from AimsDeviceFirmware f where f.firmwareId=:id");
			query.setLong("id", id.longValue());
			for (Iterator it = query.iterate(); it.hasNext();){
				firmware = (AimsDeviceFirmware) it.next();
		    }

		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			session.close();
		}
		log.debug("FirmwareManager.getFirmwareById: End");
		return firmware;
	}
	
	public static int deleteFrimware(Long firmwareId) throws HibernateException, IntegrityConstraintException {
		log.debug("FirmwareManager.deleteFrimware: Start firmwareId="+firmwareId);
		int delCount = 0;
		Session session = null;
		Transaction tx = null;

		try {

			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();
			delCount = session.delete("from AimsDeviceFirmware as f where f.firmwareId = :firmwareId", firmwareId, new LongType());
			tx.commit();
			log.debug("Number of firmware deleted: " + delCount);

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			if (e.getMessage().indexOf("ORA-02292") > 0) {
				IntegrityConstraintException ice = new IntegrityConstraintException();
				ice.setMessageKey("error.FirmwareForm.delete");
				throw ice;
			}
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("FirmwareManager.deleteFrimware: End");
		return delCount;
	}
	
	public static List getVzAppZoneDevelopersForEmail(Long firmwareId) throws HibernateException{
		List list=new ArrayList();
		log.debug("FirmwareManager.getVzAppZoneDevelopersForEmail: Start firmwareId="+firmwareId);
		Session session=null;
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT DISTINCT u.userId, c.emailAddress");
		sb.append("           FROM AimsUserRole ur, AimsUser u, AimsContact c, AimsAllianc al, AimsApp app");
		sb.append("          WHERE ur.userId = u.userId");
		sb.append("		  	   and u.aimsContactId = c.contactId");
		sb.append("            AND u.aimsAllianc = al.allianceId");
		sb.append("            AND al.allianceId = app.aimsAllianceId");
		sb.append("            AND ur.roleId = 3");
		sb.append("            AND app.aimsPlatformId = 42");
		sb.append("            AND app.aimsLifecyclePhaseId != 8");
		sb.append("			   AND u.userAccountStatus='ACTIVE'");
		sb.append("			   AND al.status = 'A'");
		sb.append("			   AND u.userId not in (select log.userId from AimsVzappFirmwareEmailLog log where log.firmwareId = :firmwareId)");
		sb.append("       ORDER BY  u.userId");

		try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(sb.toString());
			query.setLong("firmwareId", firmwareId.longValue());
			for (Iterator it = query.iterate(); it.hasNext();){
				list.add(it.next());
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("FirmwareManager.getVzAppZoneDevelopersForEmail: End size="+list.size());
		return list;
	}

	public static void saveEmailLog(AimsVzappFirmwareEmailLog emailLog) throws HibernateException, Exception {
		log.debug("FirmwareManager.saveEmailLog: Start");
		Session session = null;
		Transaction tx = null;

		try {
			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(emailLog);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("FirmwareManager.saveEmailLog: End");
	}
	
	public static void main(String args[])throws HibernateException{		
		DBHelper dbHelper=null;
		try {
			Configuration conf =new Configuration();			
			conf.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@xeon:1521:ora9i");
			conf.setProperty("hibernate.connection.username", "aimsapp3");
			conf.setProperty("hibernate.connection.password", "aimsapp3");
			dbHelper=DBHelper.getInstance();
			dbHelper.sessionFactory=conf.configure().buildSessionFactory();
			/*List list=FirmwareManager.getFirmwaresList(AimsConstants.ACTIVE);
			for(int i=0; i<list.size(); i++){
				FirmwareVO vo=(FirmwareVO)list.get(i);
				List firmwareList=vo.getFirmwareList();
				System.out.print(vo.getPhoneModel()+"\t");
				for(int j=0; j<firmwareList.size(); j++){
					Object[] objs=(Object[])firmwareList.get(j);
					System.out.print(objs[0]+"\t");
					System.out.print(objs[1]+"\t");
				}
				System.out.println();
			}*/
			
			List list=FirmwareManager.getVzAppZoneDevelopersForEmail(new Long(1));
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Object[] o = (Object[]) itr.next();
				System.out.println(o[0] + "\t" + o[1]);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbHelper != null){
				dbHelper.sessionFactory.close();
			}
		}
	}
}
