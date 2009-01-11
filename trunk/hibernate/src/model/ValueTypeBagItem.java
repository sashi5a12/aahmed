package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ValueTypeBagItem entity. @author MyEclipse Persistence Tools
 */

public class ValueTypeBagItem implements java.io.Serializable {

	private static final long serialVersionUID = -1966454555782550459L;
	private Long itemId;
	private String itemName;
	private Collection<String> images = new ArrayList<String>(0);

	// Constructors

	/** default constructor */
	public ValueTypeBagItem() {
	}

	/** minimal constructor */
	public ValueTypeBagItem(String itemName) {
		this.itemName = itemName;
	}

	/** full constructor */
	public ValueTypeBagItem(String itemName, Collection<String> images) {
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

	public Collection<String> getImages() {
		return this.images;
	}

	public void setImages(Collection<String> images) {
		this.images = images;
	}

}