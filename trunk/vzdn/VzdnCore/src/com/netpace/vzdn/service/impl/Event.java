/* Generated by Together */

package com.netpace.vzdn.service.impl;

/**
 * This class is the base class of all Aims_Events.
 * Event Handlers will register themselves with this Event object.
 */
public interface Event
{
    /**
     * This method  registers Event handlers 
     */
    void attach(EventHandler listener);

    /**
     * This method unregisters event handlers. 
     */
    void detach(EventHandler listener);

    /**
     * This method raises the event and informs all registered Event Handlers about the event raised. 
     */
    void raiseEvent(VzdnEventObject eventObj);

    /**
     * @link
     * @shapeType PatternLink
     * @pattern Observer
     * @supplierRole Concrete subjects
     */

    /*# private BaseEvent _concreteEvent; */

    /**
     * @link
     * @shapeType PatternLink
     * @pattern Observer
     * @supplierRole Observer
     * @hidden
     */

    /*# private EventHandler _listener; */
}
