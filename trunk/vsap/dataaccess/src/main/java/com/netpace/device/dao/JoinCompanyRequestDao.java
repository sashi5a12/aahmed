package com.netpace.device.dao;

import com.netpace.device.entities.JoinCompanyRequest;
import com.netpace.device.vo.JoinCompanyRequestVO;
import com.netpace.device.vo.PageModel;
import java.util.List;

public interface JoinCompanyRequestDao extends GenericDao<JoinCompanyRequest, Integer> {

    String name = "joinCompanyRequestDao";

    public JoinCompanyRequest getRequestByOfferedUserId(Integer userId);

    public List<JoinCompanyRequest> getUserInPendingStatus(String companyDomain, Integer userId);

    public List<JoinCompanyRequest> getUserStatus(String companyDomain,Integer userId);
    public List<JoinCompanyRequest> getAllCompanyUsers(Integer userId);

    public JoinCompanyRequest getRequestByOfferId(Integer offerId);

    public List<JoinCompanyRequest> getAllCompanyUsers(PageModel<JoinCompanyRequestVO> pageModel, Integer userId);
    
    public Integer getCompanyIdByOfferedUserId(Integer userId);
}