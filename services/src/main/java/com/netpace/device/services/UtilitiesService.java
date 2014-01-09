package com.netpace.device.services;

import com.netpace.device.vo.MenuInfo;
import com.netpace.device.vo.VAPUserDetails;
import java.util.List;

public interface UtilitiesService {
    public List<MenuInfo> getMenuInformation(VAPUserDetails loggedInUser);
    public Boolean isDomainBlocked(String domainName);
}
