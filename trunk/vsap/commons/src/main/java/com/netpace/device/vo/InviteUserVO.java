package com.netpace.device.vo;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.validation.groups.PatternGroup;
import com.netpace.device.vo.validation.groups.RequiredGroup;
import com.netpace.device.vo.validation.groups.SizeGroup;
import java.util.List;
import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GroupSequence(value = {InviteUserVO.class, RequiredGroup.class, SizeGroup.class, PatternGroup.class})
public class InviteUserVO extends Record {

    private String emailAddress;
    private String fullName;
    private List<String> userRoles;
    private String companyDomain;

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_EMAIL, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Pattern(regexp = VAPConstants.REGEX_STD_FULLNAME, groups = PatternGroup.class)
    @Length(max = 70, groups = SizeGroup.class)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotEmpty(groups = RequiredGroup.class)
    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public String getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}