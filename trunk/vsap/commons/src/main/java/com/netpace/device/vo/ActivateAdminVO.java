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
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;

@GroupSequence(value = {ActivateAdminVO.class, RequiredGroup.class, SizeGroup.class, PatternGroup.class})
public class ActivateAdminVO extends BaseUserVO {

    private String password;
    private String confirmPassword;
    private String activationCode;
    private String activationType;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGULAR_EXPRESSION_PASSWORD, groups = {PatternGroup.class})
    @Length(min = 6, max = 25, groups = {SizeGroup.class})
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Expression(value = "confirmPassword=password", applyIf = "confirmPassword is not blank")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getActivationType() {
        return activationType;
    }

    public void setActivationType(String activationType) {
        this.activationType = activationType;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
