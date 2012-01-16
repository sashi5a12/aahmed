/* Generated by Together */

package com.netpace.aims.bo.events;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

import java.util.*;

import com.netpace.aims.model.events.*;
import com.netpace.aims.model.*;


/**
 * This Singleton classes acts as a manager for Events.
 * 
 * On startup this singleton object is created and the loadEvents method is called to load all events from database 
 * and register all the intrested listeners. 
 */
public class EventManager
{
    /**
     * This method loads all events from the database and maintains it in a
     * hashtable with event name as the key 
     */
    private Hashtable events = null;
    private static Logger log = Logger.getLogger(EventManager.class.getName());

    public void loadEvents()
    {
        Session session = null;
        events = new Hashtable();
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery("select from com.netpace.aims.model.events.AimsEventLite as event where event.eventId is not null");
            AimsEventLite aimsEvent = null;
            AimsEventHandler aimsEventHandler = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aimsEvent = (AimsEventLite) it.next();
                try
                {
                    for (Iterator iter = aimsEvent.getEventHandlers().iterator(); iter.hasNext();)
                    {
                        aimsEventHandler = (AimsEventHandler) iter.next();
                        if (aimsEventHandler.getClassName() != null)
                            aimsEvent.attach((EventHandler) (Class.forName(aimsEventHandler.getClassName()).newInstance()));
                    }
                    events.put(aimsEvent.getEventId().toString(), aimsEvent);
                }
                catch(ClassNotFoundException cnfEx)
                {
                    log.debug("ClassNotFoundException");
                    System.out.println("ClassNotFoundException");
                    cnfEx.printStackTrace();
                }
                catch(java.lang.InstantiationException insEx)
                {
                    log.debug("InstantiationException");
                    System.out.println("InstantiationException");
                    insEx.printStackTrace();
                }
                catch(IllegalAccessException illEx)
                {
                    log.debug("IllegalAccessException");
                    System.out.println("IllegalAccessException");
                    illEx.printStackTrace();
                }
                catch(Exception allEx)
                {
                    log.debug("Exception");
                    System.out.println("Exception");
                    allEx.printStackTrace();
                }
            }
         }
        catch(HibernateException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                session.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }        
    }

    /**
     * Adds the given Event to the Event hashtable. 
     */
    public void addEvent(Event event)
    {
    }

    /**
     * Removes the Event from the event hashtable 
     */
    public void removeEvent(Event event) {
    }

    /**
     * Returns the event from the hashtable with the specified name. 
     */
    public AimsEventLite getEvent(String eventId) {
        return (AimsEventLite) events.get(eventId);
    }

    /**
     * @link
     * @shapeType PatternLink
     * @pattern Singleton
     * @supplierRole Singleton factory
     */

    /*# private EventManagerFactory _eventManagerFactory; */

}