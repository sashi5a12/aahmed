package com.netpace.device.services;

import com.netpace.device.vo.CompanyRegistration;
import com.netpace.device.vo.CompanyVO;
import com.netpace.device.vo.JoinCompanyRequestVO;
import com.netpace.device.vo.VAPUserDetails;

public interface CompanyRegistrationService {

    public CompanyRegistration getCompanyByDomainName(String domainName);

    public CompanyRegistration getUserCompany(Integer userId);

    public JoinCompanyRequestVO getPendingRequestToJoin(Integer userId);

    public void registerCompany(VAPUserDetails loggedInUser, CompanyRegistration company);

    public CompanyVO requestToJoinCompany(VAPUserDetails loggedInUser);

    public void rejectToJoinCompany(VAPUserDetails loggedInUser);
}
