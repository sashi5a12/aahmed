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


public class UDReportCriteriaDO  implements DisplayObject
{

  private String columnCondition;
  private String columnName;
  private String columnDisplayName;
  private String criteriaValue;
  private String columnOperator;
  private String criteriaId;
  private Collection columnValues;
  private String recordValue;
  private String columnOperatorName;



  public String getColumnCondition()
  {
    return columnCondition;
  }
  public void setColumnCondition(String columnCondition)
  {
    this.columnCondition = columnCondition;
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
  public String getColumnOperator()
  {
    return columnOperator;
  }
  public void setColumnOperator(String columnOperator)
  {
    this.columnOperator = columnOperator;
  }
  public String getCriteriaId()
  {
    return criteriaId;
  }
  public void setCriteriaId(String criteriaId)
  {
    this.criteriaId = criteriaId;
  }
  public java.util.Collection getColumnValues()
  {
    return columnValues;
  }
  public void setColumnValues(java.util.Collection columnValues)
  {
    this.columnValues = columnValues;
  }
  public String getRecordValue()
  {
    return recordValue;
  }
  public void setRecordValue(String recordValue)
  {
    this.recordValue = recordValue;
  }
  public String getColumnOperatorName()
  {
    return columnOperatorName;
  }
  public void setColumnOperatorName(String columnOperatorName)
  {
    this.columnOperatorName = columnOperatorName;
  }



 }
