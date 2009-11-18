package com.netpace.aims.controller;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import javax.servlet.ServletException;
import org.apache.struts.config.*;
import org.apache.log4j.Logger;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.io.*;

import com.netpace.aims.model.*;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.bo.events.*;
import com.netpace.aims.util.ConfigEnvProperties;

/**
 * This plugin class is created to do all the startup actions for the VZW AIMS project.
 * 
 * This user defined plug in class extends the plugin interface and is configured in the struts config file along with other plugins like the validator and tiles plugin.
 * 
 * Startup tasks like configuring Hibernate etc is done in this class.
 * @author Shahnawaz Bagdadi
 * @version 1.0
 */
public class StartupPlugin implements PlugIn
{

 static Logger log = Logger.getLogger(StartupPlugin.class.getName());

 private String hconfig = null;

 /**
  * Default Constructor 
  */
 public StartupPlugin()
 {
 }

/**
 * This method is called from the ActionServlet's init. All VZW - AIMS related 
 * startup tasks are added here. 
 */
public void init(ActionServlet servlet, ModuleConfig config) throws javax.servlet.ServletException
{
  log.debug("Loading Hibernate Config from  : " + getHibernateConfig());

  // Initialize Hibernate
  try
  {	 
	  Configuration conf =new Configuration();
	  ConfigEnvProperties confProp=ConfigEnvProperties.getInstance();
	  String url=confProp.getProperty("connection.url");
	  String userName=confProp.getProperty("connection.username");
	  String password=confProp.getProperty("connection.password");
	  conf.setProperty("hibernate.connection.url", url);
	  conf.setProperty("hibernate.connection.username", userName);
	  conf.setProperty("hibernate.connection.password", password);
	  SessionFactory session_factory = conf.configure(servlet.getServletContext().getResource(getHibernateConfig())).buildSessionFactory();
	  DBHelper.getInstance().sessionFactory = session_factory;
	  PrivilegeManager.getInstance();
	  EventManagerFactory.getInstance();
	  ConfigEnvProperties.getInstance();
  }
  catch(Exception e)
  {
    e.printStackTrace();
    System.out.println("FATAL ERROR : " + e.getMessage());
  }
 }



  /**
   * Gets the path to hibernate config file
   * @return path to hibernates config file
   */
  public String getHibernateConfig() {
      return hconfig;
  }

  /**
   * Sets the path to hibernate config file. Called automatically by struts !!
   * @param pathnames delimited list of Validator resource path names
   */
  public void setHibernateConfig(String hibernateConfig) {
      this.hconfig = hibernateConfig;
  }

  /**
   * This method is called from the ActionServlets destroy method. 
   * All VZW AIMS related cleanup tasks should be done here. 
   */
  public void destroy()
  {
  }

}