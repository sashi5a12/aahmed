package model;

import java.io.Serializable;
import java.util.Date;

public class CategorizedItem {
	public static class Id implements Serializable {
		private Long categoryId;
		private Long itemId;

		public Id() {
		}
		
		public Long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}

		public Long getItemId() {
			return itemId;
		}

		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}

		public Id(Long categoryId, Long itemId) {
			this.categoryId = categoryId;
			this.itemId = itemId;
		}

		public boolean equals(Object o) {

			if (o != null && o instanceof Id) {
				Id that = (Id) o;
				return this.categoryId.equals(that.categoryId) && this.itemId.equals(that.itemId);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return categoryId.hashCode() + itemId.hashCode();
		}
	}

	private Id id = new Id();
	private String username;
	private Date dateAdded = new Date();
	private Item item;
	private Category category;

	public CategorizedItem() {
	}

	public CategorizedItem(String username, Category category, Item item) {
		// Set fields
		this.username = username;
		this.category = category;
		this.item = item;

		// Set identifier values
		this.id.categoryId = category.getCategoryId();
		this.id.itemId = item.getItemId();

		// Guarantee referential integrity
		category.getCategorizedItems().add(this);
		item.getCategorizedItems().add(this);
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}