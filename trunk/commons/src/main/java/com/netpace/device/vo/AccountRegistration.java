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

@GroupSequence(value = {AccountRegistration.class, RequiredGroup.class, SizeGroup.class, PatternGroup.class})
public class AccountRegistration extends UserInfo {

    private String confirmPassword;
    private String confirmEmailAddress;
    private String password;
    private String activationCode;
    private String activationType;

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGULAR_EXPRESSION_PASSWORD, groups = {PatternGroup.class})
    @Length(min = 6, max = 25, groups = {SizeGroup.class})
    public String getPassword() {
        return password;
    }

    @NotBlank(groups = RequiredGroup.class)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @NotBlank(groups = RequiredGroup.class)
    public String getConfirmEmailAddress() {
        return confirmEmailAddress;
    }

    public void setConfirmEmailAddress(String confirmEmailAddress) {
        this.confirmEmailAddress = confirmEmailAddress;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
