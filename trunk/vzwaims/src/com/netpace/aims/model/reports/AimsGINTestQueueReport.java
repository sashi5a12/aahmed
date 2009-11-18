package com.netpace.aims.model.reports;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class AimsGINTestQueueReport extends com.netpace.aims.model.BaseValueObject implements Serializable {


  private java.lang.String reportDate;
  private String applicationName;
  private Long platformId;
  private Long lifecyclePhaseId;
  private java.util.List verizonTestingPhases;
  private String allianceName;
  private String handset;
  private String version;
  private String partNumber;
  private String phaseName;
  private String phaseStatus;


    public java.lang.String getReportDate() {
        return this.reportDate;
    }

    public void setReportDate(java.lang.String reportDate) {
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
  public Long getPlatformId()
  {
    return platformId;
  }
  public void setPlatformId(Long platformId)
  {
    this.platformId = platformId;
  }
  public String getAllianceName()
  {
    return allianceName;
  }
  public void setAllianceName(String allianceName)
  {
    this.allianceName = allianceName;
  }
  public Long getLifecyclePhaseId()
  {
    return lifecyclePhaseId;
  }
  public void setLifecyclePhaseId(Long lifecyclePhaseId)
  {
    this.lifecyclePhaseId = lifecyclePhaseId;
  }
  public java.util.List getVerizonTestingPhases()
  {
    return verizonTestingPhases;
  }
  public void setVerizonTestingPhases(java.util.List verizonTestingPhases)
  {
    this.verizonTestingPhases = verizonTestingPhases;
  }
  public String getHandset()
  {
    return handset;
  }
  public void setHandset(String handset)
  {
    this.handset = handset;
  }
  public String getVersion()
  {
    return version;
  }
  public void setVersion(String version)
  {
    this.version = version;
  }
  public String getPartNumber()
  {
    return partNumber;
  }
  public void setPartNumber(String partNumber)
  {
    this.partNumber = partNumber;
  }
  public String getPhaseName()
  {
    return phaseName;
  }
  public void setPhaseName(String phaseName)
  {
    this.phaseName = phaseName;
  }
  public String getPhaseStatus()
  {
    return phaseStatus;
  }
  public void setPhaseStatus(String phaseStatus)
  {
    this.phaseStatus = phaseStatus;
  }

}
