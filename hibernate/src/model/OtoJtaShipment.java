package model;

/**
 * OtoJtaShipment entity. @author MyEclipse Persistence Tools
 */

public class OtoJtaShipment implements java.io.Serializable {

	// Fields

	private Long shipmentId;
	private String shipmentState;
	private OtoJtaItem auction;

	// Constructors

	/** default constructor */
	public OtoJtaShipment() {
	}

	/** minimal constructor */
	public OtoJtaShipment(String shipmentState) {
		this.shipmentState = shipmentState;
	}

	/** full constructor */
	public OtoJtaShipment(String shipmentState, OtoJtaItem auction) {
		this.shipmentState = shipmentState;
		this.auction = auction;
	}

	// Property accessors

	public Long getShipmentId() {
		return this.shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getShipmentState() {
		return this.shipmentState;
	}

	public void setShipmentState(String shipmentState) {
		this.shipmentState = shipmentState;
	}

	public OtoJtaItem getAuction() {
		return this.auction;
	}

	public void setAuction(OtoJtaItem auction) {
		this.auction = auction;
	}

}