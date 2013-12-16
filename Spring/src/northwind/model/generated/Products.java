package northwind.model.generated;

// Generated Apr 4, 2013 5:05:43 PM by Hibernate Tools 4.0.0

/**
 * Products generated by hbm2java
 */
public class Products implements java.io.Serializable {

	private Integer productId;
	private String productName;
	private String quantityPerUnit;
	private Float unitPrice;
	private Short unitsInStock;
	private Short unitsOnOrder;
	private Short reorderLevel;
	private Boolean discontinued;
	private Suppliers supplier;
	private Categories category;

	public Products() {
	}

	public Products(String productName,
			String quantityPerUnit, Float unitPrice, Short unitsInStock,
			Short unitsOnOrder, Short reorderLevel, Boolean discontinued) {
		this.productName = productName;
		this.quantityPerUnit = quantityPerUnit;
		this.unitPrice = unitPrice;
		this.unitsInStock = unitsInStock;
		this.unitsOnOrder = unitsOnOrder;
		this.reorderLevel = reorderLevel;
		this.discontinued = discontinued;
	}
	
	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getQuantityPerUnit() {
		return this.quantityPerUnit;
	}

	public void setQuantityPerUnit(String quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}

	public Float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Short getUnitsInStock() {
		return this.unitsInStock;
	}

	public void setUnitsInStock(Short unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public Short getUnitsOnOrder() {
		return this.unitsOnOrder;
	}

	public void setUnitsOnOrder(Short unitsOnOrder) {
		this.unitsOnOrder = unitsOnOrder;
	}

	public Short getReorderLevel() {
		return this.reorderLevel;
	}

	public void setReorderLevel(Short reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public Boolean getDiscontinued() {
		return this.discontinued;
	}

	public void setDiscontinued(Boolean discontinued) {
		this.discontinued = discontinued;
	}

	@Override
	public String toString() {
		return "Products [productId=" + productId + ", productName="
				+ productName 
				+ ", supplier=" + supplier + ", category=" + category + "]\n";
	}

}