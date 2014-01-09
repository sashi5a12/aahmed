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

@GroupSequence(value={ChangeUserNameVO.class,RequiredGroup.class,SizeGroup.class,PatternGroup.class})
public class ChangeUserNameVO {

    private String userName;
    private String currentPassword;

    @NotBlank( groups=RequiredGroup.class)
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    @NotBlank( groups=RequiredGroup.class)
    @Pattern(regexp=VAPConstants.REGEX_STD_USERNAME, groups=PatternGroup.class)
    @Length(min=6, max=25, groups=SizeGroup.class)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
