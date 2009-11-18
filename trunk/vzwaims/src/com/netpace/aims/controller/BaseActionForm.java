package com.netpace.aims.controller;

import org.apache.struts.action.*;

/**
 * This is the base class from which all ActionForms will be derived.
 * This layer of abstraction is done to enable common tasks across all forms.
 * 
 * 
 * Use this class for inheritance to create a Action form only if a DynaForm will
 *  not serve your purpose. In other words use DynaForms with validator where ever possible.
 * 
 * Your subclass will also be read by the XDoclet task configured in Ant so 
 * if you plan to inherit from this class, Also use XDoclet tags to add the 
 * entry to the struts config file 
 * (Talk to Shahnawaz if this is not clear !!)
 * @author Shahnawaz Bagdadi
 * @version 1.0
 */
public abstract class BaseActionForm extends ActionForm 
{
}
