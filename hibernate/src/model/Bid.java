package model;

import java.sql.Timestamp;

/**
 * Bid entity. @author MyEclipse Persistence Tools
 */

public class Bid implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1085269444371868629L;
	private Long bidId;
	private Item item;
	private Integer bidderId;
	private Double bidAmount;
	private String bidAmountCurrency;
	private Timestamp created;
	private String isSuccessful;
	private Integer bidPosition;

	// Constructors

	/** default constructor */
	public Bid() {
	}

	// Property accessors

	public Long getBidId() {
		return this.bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getBidderId() {
		return this.bidderId;
	}

	public void setBidderId(Integer bidderId) {
		this.bidderId = bidderId;
	}

	public Double getBidAmount() {
		return this.bidAmount;
	}

	public void setBidAmount(Double bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getBidAmountCurrency() {
		return this.bidAmountCurrency;
	}

	public void setBidAmountCurrency(String bidAmountCurrency) {
		this.bidAmountCurrency = bidAmountCurrency;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getIsSuccessful() {
		return this.isSuccessful;
	}

	public void setIsSuccessful(String isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public Integer getBidPosition() {
		return this.bidPosition;
	}

	public void setBidPosition(Integer bidPosition) {
		this.bidPosition = bidPosition;
	}

}