package northwind.model.generated;

// Generated Apr 4, 2013 5:05:43 PM by Hibernate Tools 4.0.0

/**
 * OrderDetails generated by hbm2java
 */
public class OrderDetails implements java.io.Serializable {

	private Integer odId;
	private Integer orderId;
	private Integer productId;
	private Float unitPrice;
	private Short quantity;
	private Float discount;

	public OrderDetails() {
	}

	public OrderDetails(Integer orderId, Integer productId, Float unitPrice,
			Short quantity, Float discount) {
		this.orderId = orderId;
		this.productId = productId;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.discount = discount;
	}

	public Integer getOdId() {
		return this.odId;
	}

	public void setOdId(Integer odId) {
		this.odId = odId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Short getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public Float getDiscount() {
		return this.discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

}