package com.netpace.device.dao;

import java.util.List;

public interface UtilitiesDao {
    String name="utilitiesDao";
    public List<Object[]> getMenuInfo(Integer userId);
    public Object findBlockedDomain(String domainName);
}
