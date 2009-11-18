package com.netpace.aims.controller.reports;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.*;

import java.io.*;
import java.sql.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.netpace.aims.util.AimsConstants;

import com.netpace.aims.model.reports.*;
import com.netpace.aims.model.core.*;

import com.netpace.aims.util.StringFuncs;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.bo.reports.AimsGINReportManager;
import java.util.Collection;
import com.netpace.aims.controller.BaseValidatorForm;


/**
 * @struts.form name="GINReportForm"
 */

public class GINReportForm extends BaseValidatorForm
{
  private java.util.Collection brewApplicationList;
  private String aimsAppId;
  private String reportDate;
  private String applicationName;
  private String allianceName;
  private java.util.Collection aimsAppPhasesList;
  private String version;
  private String handset;
  private String partNumber;
  private String launchDate;
  private String ginNumber;
  private java.util.Collection ginNumberList;

  public java.util.Collection getBrewApplicationList()
  {
    return brewApplicationList;
  }
  public void setBrewApplicationList(java.util.Collection brewApplicationList)
  {
    this.brewApplicationList = brewApplicationList;
  }
  public String getAimsAppId()
  {
    return aimsAppId;
  }
  public void setAimsAppId(String aimsAppId)
  {
    this.aimsAppId = aimsAppId;
  }
  public String getReportDate()
  {
    return reportDate;
  }
  public void setReportDate(String reportDate)
  {
    this.reportDate = reportDate;
  }
  public String getApplicationName()
  {
    return applicationName;
  }
  public void setApplicationName(String applicationName)
  {
    this.applicationName = applicationName;
  }
  public String getAllianceName()
  {
    return allianceName;
  }
  public void setAllianceName(String allianceName)
  {
    this.allianceName = allianceName;
  }
  public java.util.Collection getAimsAppPhasesList()
  {
    return aimsAppPhasesList;
  }
  public void setAimsAppPhasesList(java.util.Collection aimsAppPhasesList)
  {
    this.aimsAppPhasesList = aimsAppPhasesList;
  }
  public String getVersion()
  {
    return version;
  }
  public void setVersion(String version)
  {
    this.version = version;
  }
  public String getHandset()
  {
    return handset;
  }
  public void setHandset(String handset)
  {
    this.handset = handset;
  }
  public String getPartNumber()
  {
    return partNumber;
  }
  public void setPartNumber(String partNumber)
  {
    this.partNumber = partNumber;
  }
  public String getLaunchDate()
  {
    return launchDate;
  }
  public void setLaunchDate(String launchDate)
  {
    this.launchDate = launchDate;
  }
  public String getGinNumber()
  {
    return ginNumber;
  }
  public void setGinNumber(String ginNumber)
  {
    this.ginNumber = ginNumber;
  }
  public java.util.Collection getGinNumberList()
  {
    return ginNumberList;
  }
  public void setGinNumberList(java.util.Collection ginNumberList)
  {
    this.ginNumberList = ginNumberList;
  }


 }
