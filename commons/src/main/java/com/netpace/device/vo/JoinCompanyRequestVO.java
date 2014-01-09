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

@GroupSequence(value = {JoinCompanyRequestVO.class, RequiredGroup.class, PatternGroup.class, SizeGroup.class})
public class JoinCompanyRequestVO extends Record {

    private String fullName;
    private String emailAddress;
    private String status;
    private Date createdDate;
    private Integer offerToId;
    private boolean offerToActive;

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
    @Pattern(regexp = VAPConstants.REGEX_STD_EMAIL, groups = PatternGroup.class)
    @Length(max = 100, groups = SizeGroup.class)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @NotBlank(groups = RequiredGroup.class)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotBlank(groups = RequiredGroup.class)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getOfferToId() {
        return offerToId;
    }

    public void setOfferToId(Integer offerToId) {
        this.offerToId = offerToId;
    }

    public boolean isOfferToActive() {
        return offerToActive;
    }

    public void setOfferToActive(boolean offerToActive) {
        this.offerToActive = offerToActive;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
