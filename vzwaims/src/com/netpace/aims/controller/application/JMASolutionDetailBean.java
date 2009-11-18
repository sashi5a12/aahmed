package com.netpace.aims.controller.application;

import org.apache.log4j.Logger;

public class JMASolutionDetailBean
{

    static Logger log = Logger.getLogger(JMASolutionDetailBean.class.getName());

    private java.lang.Long solutionId;
    private java.lang.String longDesc;
    private java.lang.String bdsName;
    private java.lang.String allianceSponsorFirstName;
    private java.lang.String allianceSponsorLastName;
    private java.lang.Long[] regions;
    private java.lang.Long[] indFocuses;
	private java.lang.Long[] mktSegments;
    private java.util.List spotLights;
    private java.util.Date spotLightMaxCreateDate;
    private java.lang.String spotLightTypeName;
    private java.lang.Long qrgSpotLightId;
    private java.lang.String solutionContactFirstName;
    private java.lang.String solutionContactLastName;
    private java.lang.String solutionContactPhone;
    private java.lang.String solutionContactEmailAddress;
    private java.util.Map spotLightCounts;
  private String solutionContactTitle;

    public java.lang.Long getSolutionId()
    {
        return this.solutionId;
    }

    public void setSolutionId(java.lang.Long solutionId)
    {
        this.solutionId = solutionId;
    }

    public java.lang.String getLongDesc()
    {
        return this.longDesc;
    }

    public void setLongDesc(java.lang.String longDesc)
    {
        this.longDesc = longDesc;
    }

    public java.lang.String getBdsName()
    {
        return this.bdsName;
    }

    public void setBdsName(java.lang.String bdsName)
    {
        this.bdsName = bdsName;
    }

    public java.lang.Long[] getRegions()
    {
        return this.regions;
    }

    public void setRegions(java.lang.Long[] regions)
    {
        this.regions = regions;
    }

    public java.lang.Long[] getIndFocuses()
    {
        return this.indFocuses;
    }

    public void setIndFocuses(java.lang.Long[] indFocuses)
    {
        this.indFocuses = indFocuses;
    }

    public java.util.List getSpotLights()
    {
        return this.spotLights;
    }

    public void setSpotLights(java.util.List spotLights)
    {
        this.spotLights = spotLights;
    }

    public java.util.Date getSpotLightMaxCreateDate()
    {
        return this.spotLightMaxCreateDate;
    }

    public void setSpotLightMaxCreateDate(java.util.Date spotLightMaxCreateDate)
    {
        this.spotLightMaxCreateDate = spotLightMaxCreateDate;
    }

    public java.lang.String getSpotLightTypeName()
    {
        return this.spotLightTypeName;
    }

    public void setSpotLightTypeName(java.lang.String spotLightTypeName)
    {
        this.spotLightTypeName = spotLightTypeName;
    }

    public java.lang.Long getQrgSpotLightId()
    {
        return this.qrgSpotLightId;
    }

    public void setQrgSpotLightId(java.lang.Long qrgSpotLightId)
    {
        this.qrgSpotLightId = qrgSpotLightId;
    }

    /**
     * @return
     */
    public java.lang.String getAllianceSponsorFirstName()
    {
        return allianceSponsorFirstName;
    }

    /**
     * @return
     */
    public java.lang.String getAllianceSponsorLastName()
    {
        return allianceSponsorLastName;
    }

    /**
     * @return
     */
    public java.lang.String getSolutionContactFirstName()
    {
        return solutionContactFirstName;
    }

    /**
     * @return
     */
    public java.lang.String getSolutionContactLastName()
    {
        return solutionContactLastName;
    }

    /**
     * @param string
     */
    public void setAllianceSponsorFirstName(java.lang.String string)
    {
        allianceSponsorFirstName = string;
    }

    /**
     * @param string
     */
    public void setAllianceSponsorLastName(java.lang.String string)
    {
        allianceSponsorLastName = string;
    }

    /**
     * @param string
     */
    public void setSolutionContactFirstName(java.lang.String string)
    {
        solutionContactFirstName = string;
    }

    /**
     * @param string
     */
    public void setSolutionContactLastName(java.lang.String string)
    {
        solutionContactLastName = string;
    }

    /**
     * @return
     */
    public java.lang.String getSolutionContactEmailAddress()
    {
        return solutionContactEmailAddress;
    }

    /**
     * @return
     */
    public java.lang.String getSolutionContactPhone()
    {
        return solutionContactPhone;
    }

    /**
     * @param string
     */
    public void setSolutionContactEmailAddress(java.lang.String string)
    {
        solutionContactEmailAddress = string;
    }

    /**
     * @param string
     */
    public void setSolutionContactPhone(java.lang.String string)
    {
        solutionContactPhone = string;
    }

    /**
     * @return
     */
    public java.util.Map getSpotLightCounts()
    {
        return spotLightCounts;
    }

    /**
     * @param map
     */
    public void setSpotLightCounts(java.util.Map map)
    {
        spotLightCounts = map;
    }

    /**
     * @return
     */
    public java.lang.Long[] getMktSegments()
    {
        return mktSegments;
    }

    /**
     * @param longs
     */
    public void setMktSegments(java.lang.Long[] longs)
    {
        mktSegments = longs;
    }
  public String getSolutionContactTitle() {
    return solutionContactTitle;
  }
  public void setSolutionContactTitle(String solutionContactTitle) {
    this.solutionContactTitle = solutionContactTitle;
  }

}
