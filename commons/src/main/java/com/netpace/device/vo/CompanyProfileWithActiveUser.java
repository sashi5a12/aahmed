package com.netpace.device.vo;

import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CompanyProfileWithActiveUser {
    CompanyRegistration company;
    List<UserInfo> userInfoList;
    private int mainContactId;

    @Valid
    public CompanyRegistration getCompany() {
        return company;
    }

    public void setCompany(CompanyRegistration company) {
        this.company = company;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public int getMainContactId() {
        return mainContactId;
    }

    public void setMainContactId(int mainContactId) {
        this.mainContactId = mainContactId;
    }
    
    @Override 
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
