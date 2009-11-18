package com.netpace.vzdn.webapp.actions;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.netpace.vzdn.dao.impl.GenericDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnEventLite;
import com.netpace.vzdn.service.impl.EventManagerFactory;
import com.netpace.vzdn.service.impl.VzdnEventObject;
import com.netpace.vzdn.util.VzdnNotificationConstants;
public class TestNotificationAction extends BaseAction{
	
	private static Logger log = Logger.getLogger(TestNotificationAction.class);

	public String testEventRaise()throws HibernateException, Exception {
		log.info("going to raise event");
		
		VzdnEventLite aimsEvent = null;
		Integer eventId = Integer.parseInt(getServletRequest().getParameter("eventId"));
		
		log.info("event id="+eventId);
		
		aimsEvent = EventManagerFactory.getInstance().getEvent(eventId.toString());
		
		log.info("created aimsEvent for event id="+eventId);
		
		Session session = null;
		try{
			if (aimsEvent != null) {
				
				log.info("going to get hibernate session:");
				
				session = HibernateSessionFactory.getSession();
				
				log.info("Setting up template properties for the event:");
				
				VzdnEventObject aimsEventObject = aimsEvent.getNewEventObject();
				aimsEventObject.setProperty(VzdnNotificationConstants.PLACE_HOLDER_TEST_NAME_1,"Testing value 1");
				aimsEventObject.setProperty(VzdnNotificationConstants.PLACE_HOLDER_TEST_NAME_2,"Testing value 2");
				aimsEventObject.setProperty(VzdnNotificationConstants.PLACE_HOLDER_TEST_NAME_3,"Testing value 3");
				aimsEventObject.setProperty(VzdnNotificationConstants.PLACE_HOLDER_TEST_NAME_4,"Testing value 4");
				aimsEventObject.setProperty(VzdnNotificationConstants.PLACE_HOLDER_TEST_NAME_5,"Testing value 5");
				aimsEventObject.setProperty(VzdnNotificationConstants.PLACE_HOLDER_TEST_NAME_6,"Testing value 6");
				
				log.info("Handing over the aimsEventObject to raiseEvent() for the event to be raised:");
				
				aimsEvent.raiseEvent(aimsEventObject);
				
				log.info("even raised succsessfully:");
			}
		}
		catch(Exception exp){
			log.error(exp.getStackTrace());
			log.error(exp.getMessage());
			exp.printStackTrace();
		}
		finally{
			HibernateSessionFactory.closeSession();		
		}		
		return SUCCESS;
	}// end notification
	
	/*public static void main(String[] str) throws HibernateException, Exception {
		sendNotificationForCotnract() ;
	}*/
}