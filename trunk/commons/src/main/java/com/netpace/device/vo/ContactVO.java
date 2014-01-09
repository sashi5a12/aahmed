package com.netpace.device.vo;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.validation.groups.PatternGroup;
import com.netpace.device.vo.validation.groups.RequiredGroup;
import com.netpace.device.vo.validation.groups.SizeGroup;
import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@GroupSequence(value = {ContactVO.class, RequiredGroup.class, PatternGroup.class, SizeGroup.class})
public class ContactVO extends Record {

    Integer contactId;
    String contactType;
    String name;
    String streetAddress;
    String emailAddress;
    String city;
    String phone;
    String postalCode;
    String mobile;
    String country;

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_FULLNAME, groups = PatternGroup.class)
    @Length(max = 70, groups = SizeGroup.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_EMAIL, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_PHONE, groups = SizeGroup.class)
    @Length(max = 25, groups = SizeGroup.class)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Pattern(regexp = VAPConstants.REGEX_STD_PHONE, groups = RequiredGroup.class)
    @Length(max = 25, groups = SizeGroup.class)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
