package model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Item entity. @author MyEclipse Persistence Tools
 */

public class Item implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8469030983225738060L;
	private Long itemId;
	private Integer sellerId;
	private Integer approvedBy;
	private Integer objVersion;
	private String itemName;
	private String description;
	private Double initialPrice;
	private String initialPriceCurrency;
	private Double reservePrice;
	private String reservePriceCurrency;
	private Timestamp startDate;
	private Timestamp endDate;
	private String itemState;
	private Timestamp approvalDatetime;
	private Timestamp created;
	private Set<Bid> bids = new HashSet<Bid>(0);

	// Constructors

	/** default constructor */
	public Item() {
	}

	/** minimal constructor */
	public Item(String itemName) {
		this.itemName = itemName;
	}

	// Property accessors

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Integer getObjVersion() {
		return this.objVersion;
	}

	public void setObjVersion(Integer objVersion) {
		this.objVersion = objVersion;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getInitialPrice() {
		return this.initialPrice;
	}

	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public String getInitialPriceCurrency() {
		return this.initialPriceCurrency;
	}

	public void setInitialPriceCurrency(String initialPriceCurrency) {
		this.initialPriceCurrency = initialPriceCurrency;
	}

	public Double getReservePrice() {
		return this.reservePrice;
	}

	public void setReservePrice(Double reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getReservePriceCurrency() {
		return this.reservePriceCurrency;
	}

	public void setReservePriceCurrency(String reservePriceCurrency) {
		this.reservePriceCurrency = reservePriceCurrency;
	}

	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getItemState() {
		return this.itemState;
	}

	public void setItemState(String itemState) {
		this.itemState = itemState;
	}

	public Timestamp getApprovalDatetime() {
		return this.approvalDatetime;
	}

	public void setApprovalDatetime(Timestamp approvalDatetime) {
		this.approvalDatetime = approvalDatetime;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Set<Bid> getBids() {
		return this.bids;
	}

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}
	
	public void addBid(Bid bid){
		bid.setItem(this);
		this.bids.add(bid);
	}
}