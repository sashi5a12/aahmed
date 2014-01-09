package com.netpace.device.vo;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.validation.groups.PatternGroup;
import com.netpace.device.vo.validation.groups.RequiredGroup;
import com.netpace.device.vo.validation.groups.SizeGroup;
import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@GroupSequence(value = {BaseUserVO.class, RequiredGroup.class, PatternGroup.class, SizeGroup.class})
public class BaseUserVO extends Record {

    private String fullName;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String emailAddress;
    private String userName;

    @NotBlank( groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_FULLNAME, groups = PatternGroup.class)
    @Length(max = 70, groups = SizeGroup.class)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotBlank( groups = RequiredGroup.class)
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

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_EMAIL, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @NotBlank( groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_USERNAME, groups = PatternGroup.class)
    @Length(min = 6, max = 25, groups = SizeGroup.class)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
