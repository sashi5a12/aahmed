package com.netpace.device.vo;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.validation.groups.PatternGroup;
import com.netpace.device.vo.validation.groups.RequiredGroup;
import com.netpace.device.vo.validation.groups.SizeGroup;
import java.util.Date;
import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * This class has been particularly created for the partner user edit screen. It
 * is created by duplicating most of the properties from UserInfo class.
 * UserInfo class has few properties like userName, emailAddress and
 * companyDomain which are validating as required. In partner user edit screen
 * those properties are not required moreover, roles are also not needed, that's
 * why this class is created.
 *
 * @author nraza
 */
@GroupSequence(value = {PartnerUserInfo.class, RequiredGroup.class, PatternGroup.class, SizeGroup.class})
public class PartnerUserInfo extends Record {

    private String fullName;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private Integer companyId;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private boolean isActive;
    private Date createdDate;

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_FULLNAME, groups = PatternGroup.class)
    @Length(max = 70, groups = SizeGroup.class)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_PHONE, groups = SizeGroup.class)
    @Length(max = 25, groups = SizeGroup.class)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Pattern(regexp = VAPConstants.REGEX_STD_PHONE, groups = RequiredGroup.class)
    @Length(max = 25, groups = SizeGroup.class)
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_ADDRESS, groups = PatternGroup.class)
    @Length(max = 250, groups = SizeGroup.class)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_CITY, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_STATE, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @NotBlank(groups = RequiredGroup.class)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_ZIPCODE, groups = SizeGroup.class)
    @Length(max = 20, groups = SizeGroup.class)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
