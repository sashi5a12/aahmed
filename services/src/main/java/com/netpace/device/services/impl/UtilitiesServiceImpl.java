package com.netpace.device.services.impl;

import com.netpace.device.dao.UtilitiesDao;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.vo.KeyValuePair;
import com.netpace.device.vo.MenuInfo;
import com.netpace.device.vo.VAPUserDetails;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilitiesServiceImpl implements UtilitiesService {

    private final static Log log = LogFactory.getLog(UtilitiesServiceImpl.class);
    @Autowired
    UtilitiesDao utilitiesDao;

    @Override
    public List<MenuInfo> getMenuInformation(VAPUserDetails loggedInUser) {

        List<MenuInfo> menu = new ArrayList<MenuInfo>();
        List<Object[]> list = utilitiesDao.getMenuInfo(loggedInUser.getId());
        String oldMenuName = "";
        MenuInfo menuInfo = null;
        log.info("List Size is : " + list.size());
        for (Object[] arrObj : list) {
            String menuName = String.valueOf(arrObj[0]);
            log.info("Menu Name " + menuName);
            log.info("is New Menu " + (menuName.compareTo(oldMenuName) != 0));
            if (menuName.compareTo(oldMenuName) != 0) {
                if (menuInfo != null) {
                    menu.add(menuInfo);
                }
                menuInfo = new MenuInfo();
                menuInfo.setName(menuName);
                oldMenuName = menuName;
            }
            menuInfo.addSubmenuItem(new KeyValuePair(String.valueOf(arrObj[1]), String.valueOf(arrObj[2])));
            log.info(menuInfo);
        }
        if (menuInfo != null) {
            menu.add(menuInfo);
        }
        return Collections.unmodifiableList(menu);
    }

    @Override
    public Boolean isDomainBlocked(String domainName) {
        Object domainNameObj = utilitiesDao.findBlockedDomain(domainName);

        if (domainNameObj != null) {
            String blockedDomain = (String) domainNameObj;

            if (blockedDomain.equals(domainName)) {
                return true;
            }
        }
        return false;
    }
}
