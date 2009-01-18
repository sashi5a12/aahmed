package model;

/**
 * OtoJtaItem entity. @author MyEclipse Persistence Tools
 */

public class OtoJtaItem implements java.io.Serializable {

	// Fields

	private Long itemId;
	private String itemName;

	// Constructors

	/** default constructor */
	public OtoJtaItem() {
	}

	/** full constructor */
	public OtoJtaItem(String itemName) {
		this.itemName = itemName;
	}

	// Property accessors

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}