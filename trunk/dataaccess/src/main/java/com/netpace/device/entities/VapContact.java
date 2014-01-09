package com.netpace.device.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.netpace.device.vo.product.ProductInfoVO;

@Entity
@Table(name = "vap_contact")
@NamedQueries({
    @NamedQuery(name = "findContactById", query = "select contact from VapContact contact"
            + " where contact.contactId = :contactId and contact.active = '1'")
})
public class VapContact extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2533912968635157253L;
    private Integer contactId;
    private String contactType;
    private String name;
    private String streetAddress;
    private String emailAddress;
    private String city;
    private String phone;
    private String postalCode;
    private String mobile;
    private String country;
    private String state;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false, unique = true)
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type")
    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "street_address")
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Column(name = "email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "state")
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void copyFromProductVo(ProductInfoVO vo) {
        this.setContactId(vo.getContactId());
        this.setName(vo.getName());
        this.setStreetAddress(vo.getStreetAddress());
        this.setEmailAddress(vo.getEmailAddress());
        this.setCity(vo.getCity());
        this.setPhone(vo.getPhone());
        this.setPostalCode(vo.getPostalCode());
        this.setMobile(vo.getMobile());
        this.setCountry(vo.getCountry());
        this.setState(vo.getState());
    }

    public void copyInProductVo(ProductInfoVO vo) {
        vo.setContactId(this.getContactId());
        vo.setName(this.getName());
        vo.setStreetAddress(this.getStreetAddress());
        vo.setEmailAddress(this.getEmailAddress());
        vo.setCity(this.getCity());
        vo.setPhone(this.getPhone());
        vo.setPostalCode(this.getPostalCode());
        vo.setMobile(this.getMobile());
        vo.setCountry(this.getCountry());
        vo.setState(this.getState());
    }

    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        buffer.append("contactId=[").append(contactId).append("] ");
        buffer.append("contactType=[").append(contactType).append("] ");
        buffer.append("name=[").append(name).append("] ");
        buffer.append("streetAddress=[").append(streetAddress).append("] ");
        buffer.append("emailAddress=[").append(emailAddress).append("] ");
        buffer.append("city=[").append(city).append("] ");
        buffer.append("phone=[").append(phone).append("] ");
        buffer.append("postalCode=[").append(postalCode).append("] ");
        buffer.append("mobile=[").append(mobile).append("] ");
        buffer.append("country=[").append(country).append("] ");
        buffer.append("state=[").append(state).append("] ");
        buffer.append("createdDate=[").append(createdDate).append("] ");
        buffer.append("createdBy=[").append(createdBy).append("] ");
        buffer.append("updatedDate=[").append(lastUpdatedDate).append("] ");
        buffer.append("updatedBy=[").append(lastUpdatedBy).append("] ");

        return buffer.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((contactId == null) ? 0 : contactId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VapContact)) {
            return false;
        }
        VapContact equalCheck = (VapContact) obj;
        if ((contactId == null && equalCheck.contactId != null) || (contactId != null && equalCheck.contactId == null)) {
            return false;
        }
        if (contactId != null && !contactId.equals(equalCheck.contactId)) {
            return false;
        }
        return true;
    }
}
