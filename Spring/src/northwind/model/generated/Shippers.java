package northwind.model.generated;

// Generated Apr 4, 2013 5:05:43 PM by Hibernate Tools 4.0.0

/**
 * Shippers generated by hbm2java
 */
public class Shippers implements java.io.Serializable {

	private Integer shipperId;
	private String companyName;
	private String phone;

	public Shippers() {
	}

	public Shippers(String companyName, String phone) {
		this.companyName = companyName;
		this.phone = phone;
	}

	public Integer getShipperId() {
		return this.shipperId;
	}

	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
