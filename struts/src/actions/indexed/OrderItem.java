package actions.indexed;

public class OrderItem {
	private String productId;
	private String productName;
	private String quantity;

	
	public OrderItem() {
	}

	
	public OrderItem(String productId, String productName, String quantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
