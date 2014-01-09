package com.netpace.device.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.vo.CompanyListVO;
import com.netpace.device.vo.FilterModel;
import com.netpace.device.vo.FilterVal;
import com.netpace.device.vo.PageModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author aahmed
 */
public class CompanyServiceTest extends BaseServiceTest {

    private static final Log log = LogFactory.getLog(CompanyServiceTest.class);
    @Autowired
    private CompanyService companyService;

    public CompanyServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCompanyList method, of class CompanyServiceImpl.
     */
    @Test
    public void testManagePartners() {
        PageModel pageModel = new PageModel();
        pageModel.setSearchBy("");
        pageModel.setSearchValue("");
        pageModel.setSortBy("c.name");
        pageModel.setAscending(true);
        pageModel.setPage("0");
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

//        log.info("sortBy = " + pageModel.getSortBy());
//        log.info("sortOrder = " + pageModel.isAscending());
//        log.info("searchBy = " + pageModel.getSearchBy());
//        log.info("searchValue = " + pageModel.getSearchValue());
//        log.info("pageSize = "+pageModel.getPageSize());
//        log.info("page = "+pageModel.getPage());
//        log.info("filters = "+pageModel.getFilters());
        
//        companyService.getPartnersList(pageModel);
        
//        log.info("\n-------------Start Search Result------------------------\n");
//        log.info("list = " + pageModel.getRecords());
//        log.info("count = " + pageModel.getTotalRecords());
//        log.info("\n-------------End Search Result------------------------\n");

//        Assert.assertEquals("Search is correct...", (long) pageModel.getTotalRecords(), (long) pageModel.getRecords().size());
    }

    @Test
    public void testAllCompanies() {
//        companyService.getAllCompanies();
    }
    
    @Test
    public void isCompanyApproved(){
//    	log.debug("------------------------------ : "+companyService.isCompanyApproved(104));
    }
}
