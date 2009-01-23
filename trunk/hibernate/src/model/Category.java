package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Category entity. @author MyEclipse Persistence Tools
 */

public class Category implements java.io.Serializable {

	// Fields

	private Long categoryId;
	private Category parentCategory;
	private Integer objVersion;
	private String categoryName;
	private Timestamp created;
	private Set childCategories = new HashSet(0);
	private List items = new ArrayList(0);

	// Constructors

	/** default constructor */
	public Category() {
	}

	/** minimal constructor */
	public Category(Integer objVersion, String categoryName, Timestamp created) {
		this.objVersion = objVersion;
		this.categoryName = categoryName;
		this.created = created;
	}

	/** full constructor */
	public Category(Category parentCategory, Integer objVersion,
			String categoryName, Timestamp created, Set childCategories,
			List items) {
		this.parentCategory = parentCategory;
		this.objVersion = objVersion;
		this.categoryName = categoryName;
		this.created = created;
		this.childCategories = childCategories;
		this.items = items;
	}

	// Property accessors

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Category getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Integer getObjVersion() {
		return this.objVersion;
	}

	public void setObjVersion(Integer objVersion) {
		this.objVersion = objVersion;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Set getChildCategories() {
		return this.childCategories;
	}

	public void setChildCategories(Set childCategories) {
		this.childCategories = childCategories;
	}

	public List getItems() {
		return this.items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public void addItem(Item item){
		items.add(item);
		item.getCategories().add(item);
	}
}