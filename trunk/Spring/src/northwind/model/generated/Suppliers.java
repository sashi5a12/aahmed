package northwind.model.generated;

import java.util.ArrayList;
import java.util.Collection;

public class Suppliers implements java.io.Serializable {

	private static final long serialVersionUID = 6713989739441554934L;
	private Integer supplierId;
	private String companyName;
	private String contactName;
	private String contactTitle;
	private String address;
	private String city;
	private String region;
	private String postalCode;
	private String country;
	private String phone;
	private String fax;
	private String homePage;
	private Collection<Products> products= new ArrayList<Products>();

	public Suppliers() {
	}

	public Suppliers(String companyName, String contactName,
			String contactTitle, String address, String city, String region,
			String postalCode, String country, String phone, String fax,
			String homePage) {
		this.companyName = companyName;
		this.contactName = contactName;
		this.contactTitle = contactTitle;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		this.homePage = homePage;
	}

	public Integer getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return this.contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomePage() {
		return this.homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public Collection<Products> getProducts() {
		return products;
	}

	public void setProducts(Collection<Products> products) {
		this.products = products;
	}

	public void addProduct(Products product){
		product.setSupplier(this);
		this.products.add(product);
	}

	@Override
	public String toString() {
		return "Suppliers [supplierId=" + supplierId + ", companyName="
				+ companyName + ", contactName=" + contactName
				+ ", contactTitle=" + contactTitle + ", address=" + address
				+ ", city=" + city + ", region=" + region + ", postalCode="
				+ postalCode + ", country=" + country + ", phone=" + phone
				+ ", fax=" + fax + ", homePage=" + homePage + "]";
	}
}
