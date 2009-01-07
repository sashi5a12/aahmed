package model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Category entity. @author MyEclipse Persistence Tools
 */

public class Category  implements java.io.Serializable {

	private static final long serialVersionUID = -6913479740925393278L;
	private Long categoryId;
	private Category parentCategory;
	private Integer objVersion;
	private String categoryName;
	private Timestamp created;
	private Set<Category> childCategories = new HashSet<Category>(0);

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
    
    // Property accessors

    public Long getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getParentCategory() {
		return parentCategory;
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
    
    public Set<Category> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(Set<Category> childCategories) {
		this.childCategories = childCategories;
	}

	public void addChildCategory(Category childCategory) {

		if (childCategory == null)
			throw new IllegalArgumentException("Null child category!");
		if (childCategory.getParentCategory() != null)
			childCategory.getParentCategory().getChildCategories().remove(childCategory);
		childCategory.setParentCategory(this);
		childCategories.add(childCategory);
	}

}