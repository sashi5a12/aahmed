package com.netpace.device.dao;

import com.netpace.device.entities.Company;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.PartnerUserVO;
import java.util.List;

public interface CompanyDao extends GenericDao<Company, Integer> {

    public static final String name = "companyDao";

    public Company getCompanyByDomainName(String domainName);

    public Company getCompanyByUserId(Integer userId);

    public Company getRegisteredCompanyByDomainName(String domainName);

    public List<Company> getAllCompanies();

    public List<Object[]> getPartnersList(PageModel pageModel);

    public Long getPartnersListCount(PageModel pageModel);
    //public List<Workflow> getAllCompaniesInWorkflow();

    public PaggedResult getAllPartnerUsersList(PageModel<PartnerUserVO> pageModel, Integer companyId);

    public Integer getAllPartnerUsersListCount(PageModel<PartnerUserVO> pageModel, Integer companyId);

    // Soft delete company
    public void deleteCompany(Integer companyId, String username);
    
    public Company getCompanyByName(String companyName);
    
    public Company getCompanyById(Integer companyId);
}
