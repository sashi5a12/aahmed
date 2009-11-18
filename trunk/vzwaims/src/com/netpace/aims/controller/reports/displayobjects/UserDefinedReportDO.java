package com.netpace.aims.controller.reports.displayobjects;

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


public class UserDefinedReportDO  implements DisplayObject
{

  private java.util.Collection userDefObjectList;
  private String tableName;
  private String tableDisplayName;
  private String reportObjectID;
  private String reportObjectColumnId;
  private String columnName;
  private String columnDisplayName;
  private String criteriaValue;


  public java.util.Collection getUserDefObjectList() {
    return userDefObjectList;
  }
  public void setUserDefObjectList(java.util.Collection userDefObjectList) {
    this.userDefObjectList = userDefObjectList;
  }
  public String getTableName()
  {
    return tableName;
  }
  public void setTableName(String tableName)
  {
    this.tableName = tableName;
  }
  public String getTableDisplayName()
  {
    return tableDisplayName;
  }
  public void setTableDisplayName(String tableDisplayName)
  {
    this.tableDisplayName = tableDisplayName;
  }
  public String getReportObjectID()
  {
    return reportObjectID;
  }
  public void setReportObjectID(String reportObjectID)
  {
    this.reportObjectID = reportObjectID;
  }
  public String getReportObjectColumnId()
  {
    return reportObjectColumnId;
  }
  public void setReportObjectColumnId(String reportObjectColumnId)
  {
    this.reportObjectColumnId = reportObjectColumnId;
  }
  public String getColumnName()
  {
    return columnName;
  }
  public void setColumnName(String columnName)
  {
    this.columnName = columnName;
  }
  public String getColumnDisplayName()
  {
    return columnDisplayName;
  }
  public void setColumnDisplayName(String columnDisplayName)
  {
    this.columnDisplayName = columnDisplayName;
  }
  public String getCriteriaValue()
  {
    return criteriaValue;
  }
  public void setCriteriaValue(String criteriaValue)
  {
    this.criteriaValue = criteriaValue;
  }


 }
