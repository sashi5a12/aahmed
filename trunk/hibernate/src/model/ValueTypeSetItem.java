package model;

import java.util.HashSet;
import java.util.Set;

/**
 * ValueTypeSetItem entity. @author MyEclipse Persistence Tools
 */

public class ValueTypeSetItem implements java.io.Serializable {

	private static final long serialVersionUID = -193958528281550579L;
	private Long itemId;
	private String itemName;
	private Set<String> images = new HashSet<String>(0);

	// Constructors

	/** default constructor */
	public ValueTypeSetItem() {
	}

	/** minimal constructor */
	public ValueTypeSetItem(String itemName) {
		this.itemName = itemName;
	}

	/** full constructor */
	public ValueTypeSetItem(String itemName, Set<String> images) {
		this.itemName = itemName;
		this.images = images;
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

	public Set<String> getImages() {
		return this.images;
	}

	public void setImages(Set<String> images) {
		this.images = images;
	}

}