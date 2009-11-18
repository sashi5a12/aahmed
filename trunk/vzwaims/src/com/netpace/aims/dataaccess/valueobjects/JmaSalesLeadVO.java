package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for aims salesLead.
 *
 * @author    Arsalan Qureshi
 * @version   1.0
 */

public class JmaSalesLeadVO {
	
	private Long salesLeadId;
	private Long allianceId;
	private String allianceName;
	private String customerName;
	private String solutionName;
	private String saledRepresentative;
	private String salesLeadStatus;
	private String salesRepEmailAddress;
	
	
	public Long getSalesLeadId() {
		return salesLeadId;
	}
	public void setSalesLeadId(Long salesLeadId) {
		this.salesLeadId = salesLeadId;
	}
	public Long getAllianceId() {
		return allianceId;
	}
	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
	}
	public String getAllianceName() {
		return allianceName;
	}
	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSolutionName() {
		return solutionName;
	}
	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}
	public String getSaledRepresentative() {
		return saledRepresentative;
	}
	public void setSaledRepresentative(String saledRepresentative) {
		this.saledRepresentative = saledRepresentative;
	}
	public String getSalesLeadStatus() {
		return salesLeadStatus;
	}
	public void setSalesLeadStatus(String salesLeadStatus) {
		this.salesLeadStatus = salesLeadStatus;
	}
	public String getSalesRepEmailAddress() {
		return salesRepEmailAddress;
	}
	public void setSalesRepEmailAddress(String salesRepEmailAddress) {
		this.salesRepEmailAddress = salesRepEmailAddress;
	}
	
}
