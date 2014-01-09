package com.netpace.device.dao;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.AbstractDaoTest;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.FilterModel;
import com.netpace.device.vo.FilterVal;
import com.netpace.device.vo.PageModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class CompanyDaoTest extends AbstractDaoTest {

    private static final Log log = LogFactory.getLog(CompanyDaoTest.class);
    @Resource(name = CompanyDao.name)
    CompanyDao companyDao;

    @Test
    public void testCheckDomainNameExists() {
//        Company company = companyDao.getRegisteredCompanyByDomainName("netpace.com");
//        Company comp = companyDao.getCompanyByDomainName("netpace.com");
//        Assert.isNull(comp);
    }

    @Test
    public void testManagePartners() {
        PageModel pageModel = new PageModel();
        pageModel.setSearchBy("");
        pageModel.setSearchValue("");
        pageModel.setSortBy("c.name");
        pageModel.setAscending(true);
        pageModel.setPage("1");
        pageModel.setPageSize(10);

        FilterVal val1 = new FilterVal();
        FilterVal val2 = new FilterVal();
        FilterVal val3 = new FilterVal();

        val1.setSelected(true);
        val1.setTitle("DeviceMarketingReview");
        val2.setSelected(true);
        val2.setTitle("DeviceMarketingReviewRFI");
        val3.setSelected(true);
        val3.setTitle("DeviceMarketingReviewDenied");


        List<FilterVal> filterModelValues = new ArrayList<FilterVal>();
        filterModelValues.add(val1);
        filterModelValues.add(val2);
        filterModelValues.add(val3);

        FilterModel filterModel = new FilterModel();
        filterModel.setFilterValues(filterModelValues);

        List<FilterModel> filters = new ArrayList<FilterModel>();
        filters.add(filterModel);

//        pageModel.setFilters(filters);

//        companyDao.getPartnersList(pageModel);
    }
    
    //@Test
    public void testGetAllCompanyUsers() {
        
        PageModel pageModel = new PageModel();
        pageModel.setSearchBy("");
        pageModel.setSearchValue("");
        pageModel.setSortBy("u.user_id");
        pageModel.setAscending(true);
        pageModel.setPage("1");
        pageModel.setPageSize(10);

        PaggedResult allPartnerUsers = companyDao.getAllPartnerUsersList(pageModel, 72);
        log.debug("Records found: " + allPartnerUsers.getRecords());
        for(Object partnerUserObj : allPartnerUsers.getList()) {
            Object[] partnerUser = (Object[]) partnerUserObj;
            log.debug("**************************** companyUser **********************");
            log.debug("userId: " + partnerUser[VAPConstants.COMPANYUSER_USER_ID]);
            log.debug("emailAddress: " + partnerUser[VAPConstants.COMPANYUSER_EMAIL_ADDRESS]);
            log.debug("fullName: " + partnerUser[VAPConstants.COMPANYUSER_FULL_NAME]);
            log.debug("isActive: " + partnerUser[VAPConstants.COMPANYUSER_IS_ACTIVE]);
            log.debug("createdDate: " + partnerUser[VAPConstants.COMPANYUSER_CREATED_DATE]);
            log.debug("offerId: " + partnerUser[VAPConstants.COMPANYUSER_OFFER_ID]);
            log.debug("offerTo: " + partnerUser[VAPConstants.COMPANYUSER_OFFER_TO]);
            log.debug("status: " + partnerUser[VAPConstants.COMPANYUSER_STATUS]);
        }
    }
}
