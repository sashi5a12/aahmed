package com.netpace.aims.ca.model;

import java.util.Date;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class DeveloperFeed implements Comparable<DeveloperFeed>{
	
	private int sortOrder;
	private char recordType;
	private Integer allianceId;
	private String allianceName;		
	private Integer contractId;
	private Date acceptRejectTime;
	private String formattedDate;
	private Integer vendorId;
	
	public String getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}
	public char getRecordType() {
		return recordType;
	}
	public void setRecordType(char recordType) {
		this.recordType = recordType;
	}
	public Integer getAllianceId() {
		return allianceId;
	}
	public void setAllianceId(Integer allianceId) {
		this.allianceId = allianceId;
	}
	public String getAllianceName() {
		return allianceName;
	}
	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}	
	public Date getAcceptRejectTime() {
		return acceptRejectTime;
	}
	public void setAcceptRejectTime(Date acceptRejectTime) {
		this.acceptRejectTime = acceptRejectTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((allianceId == null) ? 0 : allianceId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DeveloperFeed other = (DeveloperFeed) obj;
		if (allianceId == null) {
			if (other.allianceId != null)
				return false;
		} else if (!allianceId.equals(other.allianceId))
			return false;
		return true;
	}	
	public int compareTo(DeveloperFeed feed){
		if(this.acceptRejectTime.before(feed.acceptRejectTime))
			return -1;		
		else
			return 1;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
