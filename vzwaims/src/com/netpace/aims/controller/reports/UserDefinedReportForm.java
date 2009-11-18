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
 * @struts.form name="UserDefinedReportForm"
 */

public class UserDefinedReportForm extends BaseValidatorForm
{
  private String task;
  private String allianceFilter;
  private String logicalFilter;
  private String applicationFilter;
  private String allianceInput;
  private String applicationInput;
  private String allianceName;
  private String allianceStatus;
  private String allianceRegDate;
  private String allianceContract;
  private String accountManager;
  private String salesContact;
  private String applicationTitle;
  private String appPlatform;
  private String appPhonePlatform;
  private String appStatus;
  private String appCertification;
  private java.util.Collection userDefObjectList;
  private String allianceId;
  private java.util.Collection contractList;
  private java.util.Collection appList;
  private String reportObject;
  private String reportColumn;
  private String reportCriteria;
  private java.util.Collection objectsAdded;
  private String objectValue;
  private String objectValues;
  private java.util.Collection columnsAdded;
  private String removableColumn;
  private String columnValue;
  private String[] columnValues;
  private String[] criteriaValues;
  private java.util.Collection criteriaAdded;
  private String columnOperator;
  private String[] columnOperators;
  private String columnCondition;
  private java.util.Collection columnOperatorList;
  private com.netpace.aims.dataaccess.valueobjects.AimsReportObjectVO[] reportObjectList;
  private com.netpace.aims.dataaccess.valueobjects.AimsReportObjectColumnVO[] reportColumnList;
  public String getTask()
  {
    return task;
  }
  public void setTask(String task)
  {
    this.task = task;
  }
  public String getAllianceFilter()
  {
    return allianceFilter;
  }
  public void setAllianceFilter(String allianceFilter)
  {
    this.allianceFilter = allianceFilter;
  }
  public String getLogicalFilter()
  {
    return logicalFilter;
  }
  public void setLogicalFilter(String logicalFilter)
  {
    this.logicalFilter = logicalFilter;
  }
  public String getApplicationFilter()
  {
    return applicationFilter;
  }
  public void setApplicationFilter(String applicationFilter)
  {
    this.applicationFilter = applicationFilter;
  }
  public String getAllianceInput()
  {
    return allianceInput;
  }
  public void setAllianceInput(String allianceInput)
  {
    this.allianceInput = allianceInput;
  }
  public String getApplicationInput()
  {
    return applicationInput;
  }
  public void setApplicationInput(String applicationInput)
  {
    this.applicationInput = applicationInput;
  }
  public String getAllianceName()
  {
    return allianceName;
  }
  public void setAllianceName(String allianceName)
  {
    this.allianceName = allianceName;
  }
  public String getAllianceStatus()
  {
    return allianceStatus;
  }
  public void setAllianceStatus(String allianceStatus)
  {
    this.allianceStatus = allianceStatus;
  }
  public String getAllianceRegDate()
  {
    return allianceRegDate;
  }
  public void setAllianceRegDate(String allianceRegDate)
  {
    this.allianceRegDate = allianceRegDate;
  }
  public String getAllianceContract()
  {
    return allianceContract;
  }
  public void setAllianceContract(String allianceContract)
  {
    this.allianceContract = allianceContract;
  }
  public String getAccountManager()
  {
    return accountManager;
  }
  public void setAccountManager(String accountManager)
  {
    this.accountManager = accountManager;
  }
  public String getSalesContact()
  {
    return salesContact;
  }
  public void setSalesContact(String salesContact)
  {
    this.salesContact = salesContact;
  }
  public String getApplicationTitle()
  {
    return applicationTitle;
  }
  public void setApplicationTitle(String applicationTitle)
  {
    this.applicationTitle = applicationTitle;
  }
  public String getAppPlatform()
  {
    return appPlatform;
  }
  public void setAppPlatform(String appPlatform)
  {
    this.appPlatform = appPlatform;
  }
  public String getAppPhonePlatform()
  {
    return appPhonePlatform;
  }
  public void setAppPhonePlatform(String appPhonePlatform)
  {
    this.appPhonePlatform = appPhonePlatform;
  }
  public String getAppStatus()
  {
    return appStatus;
  }
  public void setAppStatus(String appStatus)
  {
    this.appStatus = appStatus;
  }
  public String getAppCertification()
  {
    return appCertification;
  }
  public void setAppCertification(String appCertification)
  {
    this.appCertification = appCertification;
  }
  public java.util.Collection getUserDefObjectList() {
    return userDefObjectList;
  }
  public void setUserDefObjectList(java.util.Collection userDefObjectList) {
    this.userDefObjectList = userDefObjectList;
  }
  public String getAllianceId() {
    return allianceId;
  }
  public void setAllianceId(String allianceId) {
    this.allianceId = allianceId;
  }
  public java.util.Collection getContractList() {
    return contractList;
  }
  public void setContractList(java.util.Collection contractList) {
    this.contractList = contractList;
  }
  public java.util.Collection getAppList()
  {
    return appList;
  }
  public void setAppList(java.util.Collection appList)
  {
    this.appList = appList;
  }
  public String getReportObject()
  {
    return reportObject;
  }
  public void setReportObject(String reportObject)
  {
    this.reportObject = reportObject;
  }
  public String getReportColumn()
  {
    return reportColumn;
  }
  public void setReportColumn(String reportColumn)
  {
    this.reportColumn = reportColumn;
  }
  public String getReportCriteria()
  {
    return reportCriteria;
  }
  public void setReportCriteria(String reportCriteria)
  {
    this.reportCriteria = reportCriteria;
  }
  public com.netpace.aims.dataaccess.valueobjects.AimsReportObjectVO[] getReportObjectList()
  {
    return reportObjectList;
  }
  public void setReportObjectList(com.netpace.aims.dataaccess.valueobjects.AimsReportObjectVO[] reportObjectList)
  {
    this.reportObjectList = reportObjectList;
  }
  public java.util.Collection getObjectsAdded()
  {
    return objectsAdded;
  }
  public void setObjectsAdded(java.util.Collection objectsAdded)
  {
    this.objectsAdded = objectsAdded;
  }
  public String getObjectValue()
  {
    return objectValue;
  }
  public void setObjectValue(String objectValue)
  {
    this.objectValue = objectValue;
  }
  public String getObjectValues()
  {
    return objectValues;
  }
  public void setObjectValues(String objectValues)
  {
    this.objectValues = objectValues;
  }
  public com.netpace.aims.dataaccess.valueobjects.AimsReportObjectColumnVO[] getReportColumnList()
  {
    return reportColumnList;
  }
  public void setReportColumnList(com.netpace.aims.dataaccess.valueobjects.AimsReportObjectColumnVO[] reportColumnList)
  {
    this.reportColumnList = reportColumnList;
  }
  public java.util.Collection getColumnsAdded()
  {
    return columnsAdded;
  }
  public void setColumnsAdded(java.util.Collection columnsAdded)
  {
    this.columnsAdded = columnsAdded;
  }
  public String getRemovableColumn()
  {
    return removableColumn;
  }
  public void setRemovableColumn(String removableColumn)
  {
    this.removableColumn = removableColumn;
  }
  public String getColumnValue()
  {
    return columnValue;
  }
  public void setColumnValue(String columnValue)
  {
    this.columnValue = columnValue;
  }
  public String[] getColumnValues()
  {
    return columnValues;
  }
  public void setColumnValues(String[] columnValues)
  {
    this.columnValues = columnValues;
  }
  public String[] getCriteriaValues()
  {
    return criteriaValues;
  }
  public void setCriteriaValues(String[] criteriaValues)
  {
    this.criteriaValues = criteriaValues;
  }
  public java.util.Collection getCriteriaAdded()
  {
    return criteriaAdded;
  }
  public void setCriteriaAdded(java.util.Collection criteriaAdded)
  {
    this.criteriaAdded = criteriaAdded;
  }
  public String getColumnOperator() {
    return columnOperator;
  }
  public void setColumnOperator(String columnOperator) {
    this.columnOperator = columnOperator;
  }
  public String getColumnCondition() {
    return columnCondition;
  }
  public void setColumnCondition(String columnCondition) {
    this.columnCondition = columnCondition;
  }
  public Collection getColumnOperatorList()
  {
     return columnOperatorList;
  }
  public void setColumnOperatorList(Collection columnOperatorList)
  {
     this.columnOperatorList = columnOperatorList;
  }
  public String[] getColumnOperators()
  {
    return columnOperators;
  }
  public void setColumnOperators(String[] columnOperators)
  {
    this.columnOperators = columnOperators;
  }


 }
