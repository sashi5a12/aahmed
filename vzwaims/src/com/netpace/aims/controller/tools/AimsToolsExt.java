package com.netpace.aims.controller.tools;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.*;

/** @author Ahson Imtiaz */
public class AimsToolsExt implements Serializable {

    private static int autoToolId;
    /** identifier field */
    private int toolIdHash;

    private java.lang.Long toolId;

    /** nullable persistent field */
    private java.lang.String toolName;

    /** nullable persistent field */
    private java.util.Set categories;

    /** nullable persistent field */
    private java.util.Set platforms;

    /** nullable persistent field */
    private java.lang.String platformName;

    /** nullable persistent field */
    private java.util.Date createdDate;

	static
	{
		autoToolId = 1;
	}

	/** default constructor */
    public AimsToolsExt() {
		toolIdHash = autoToolId++;
    }


    public java.lang.Long getToolId() {
        return this.toolId;
    }

    public void setToolId(java.lang.Long toolId) {
        this.toolId = toolId;
    }

    /**/
    public java.lang.Long getToolIdHash() {
        return new java.lang.Long(this.toolIdHash);
    }

    public void setToolIdHash(java.lang.Long toolIdHash) {
        this.toolIdHash = toolIdHash.intValue();
    }

    public java.lang.String getToolName() {
        return this.toolName;
    }

    public void setToolName(java.lang.String toolName) {
        this.toolName = toolName;
    }

    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }


    public java.lang.String getPlatformName() {
        return this.platformName;
    }

    public void setPlatformName(java.lang.String platformName) {
        this.platformName = platformName;
    }

    public java.util.Set getCategories() {
        return this.categories;
    }

    public void setCategories(java.util.Set categories) {
        this.categories = categories;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("toolId", getToolId())
            .toString();
    }


    public boolean equals(Object other) {
        if ( !(other instanceof AimsToolsExt) ) return false;
        AimsToolsExt castOther = (AimsToolsExt) other;
        return new EqualsBuilder()
            .append(toolIdHash, castOther.toolIdHash)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(toolIdHash)
            .toHashCode();
    }
  public java.util.Set getPlatforms()
  {
    return platforms;
  }
  public void setPlatforms(java.util.Set platforms)
  {
    this.platforms = platforms;
  }


}
