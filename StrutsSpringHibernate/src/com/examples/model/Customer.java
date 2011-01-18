package com.examples.model;

import java.util.Date;

public class Customer implements java.io.Serializable {
	 
	private long customerId;
	private String name;
	private String address;
	private Date createdDate;
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "Customer [address=" + address + ", createdDate=" + createdDate + ", customerId=" + customerId + ", name=" + name + "]";
	}
}
