package com.netpace.device.vo;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.validation.groups.PatternGroup;
import com.netpace.device.vo.validation.groups.RequiredGroup;
import com.netpace.device.vo.validation.groups.SizeGroup;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@GroupSequence(value = {CompanyRegistration.class, RequiredGroup.class, SizeGroup.class, PatternGroup.class})
public class CompanyRegistration extends Record {

    private UserInfo user;
    private String companyName;
    private String companyLegalName;
    private String mainPhoneNumber;
    private String mainCompanyStreetAddress;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String website;
    private String domainName;
    private ContactVO salesContact;
    private boolean suspended;

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_COMPANY_NAME, groups = PatternGroup.class)
    @Length(max = 200, groups = SizeGroup.class)
    public String getCompanyName() {
        return companyName;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_COMPANY_NAME, groups = PatternGroup.class)
    @Length(max = 200, groups = SizeGroup.class)
    public String getCompanyLegalName() {
        return companyLegalName;
    }

    @Pattern(regexp = VAPConstants.REGEX_STD_PHONE, groups = PatternGroup.class)
    @Length(max = 25, groups = SizeGroup.class)
    public String getMainPhoneNumber() {
        return mainPhoneNumber;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_ADDRESS, groups = PatternGroup.class)
    @Length(max = 250, groups = SizeGroup.class)
    public String getMainCompanyStreetAddress() {
        return mainCompanyStreetAddress;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_CITY, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getCity() {
        return city;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_STATE, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getState() {
        return state;
    }

    @NotBlank(groups = RequiredGroup.class)
    public String getCountry() {
        return country;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_ZIPCODE, groups = SizeGroup.class)
    @Length(max = 20, groups = SizeGroup.class)
    public String getZip() {
        return zip;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_DOMAIN_NAME, groups = SizeGroup.class)
    @Length(max = 200, groups = SizeGroup.class)
    public String getWebsite() {
        return website;
    }

    public String getDomainName() {
        return domainName;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCompanyLegalName(String companyLegalName) {
        this.companyLegalName = companyLegalName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setMainCompanyStreetAddress(String mainCompanyStreetAddress) {
        this.mainCompanyStreetAddress = mainCompanyStreetAddress;
    }

    public void setMainPhoneNumber(String mainPhoneNumber) {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Valid
    public ContactVO getSalesContact() {
        return salesContact;
    }

    public void setSalesContact(ContactVO salesContact) {
        this.salesContact = salesContact;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
