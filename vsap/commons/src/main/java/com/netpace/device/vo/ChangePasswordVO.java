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

@GroupSequence(value = {ChangePasswordVO.class, RequiredGroup.class, SizeGroup.class, PatternGroup.class})
public class ChangePasswordVO {

    private String currentPassword;
    private String createPassword;
    private String confirmPassword;

    @NotBlank(groups = RequiredGroup.class)
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @NotBlank( groups=RequiredGroup.class)
    @Pattern(regexp=VAPConstants.REGULAR_EXPRESSION_PASSWORD,groups={PatternGroup.class})    
    @Length(min=6, max=25, groups={SizeGroup.class})
    public String getCreatePassword() {
        return createPassword;
    }

    public void setCreatePassword(String createPassword) {
        this.createPassword = createPassword;
    }

    @NotBlank(groups = RequiredGroup.class)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
