package com.netpace.device.services;


import java.util.Map;
import java.util.SortedMap;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.BaseServiceTest;
import com.netpace.device.entities.VapMedia;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.ReprotsNameEnum;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.exceptions.VapGenericException;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.ProductSubmissionType;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.product.AttachmentFile;
import com.netpace.device.vo.product.ProcessingInfoVO;
import com.netpace.device.vo.product.ProductListVO;
import com.netpace.device.vo.product.ProductInfoVO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author aahmed
 */
public class ReportServiceTest extends BaseServiceTest {

	private static final Log log = LogFactory.getLog(ReportServiceTest.class);
	
	VAPUserDetails loggedInUser = new VAPUserDetails(null, "aahmed", null,
			true, true, true, true, null, null, null, null, null, null, null,
			null, null, null, 115, null, null);
	
	@Autowired
	private ReportService reportService;

	public ReportServiceTest() {
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

	@Test
	public void sendCSV(){
//		reportService.sendCSVReport(ReprotsNameEnum.PRODUCTS_DETAIL, loggedInUser);
//		reportService.sendCSVReport(ReprotsNameEnum.PRODUCTS_TESTING_DETAIL, loggedInUser);
//		reportService.sendCSVReport(ReprotsNameEnum.COMPANY_DETAIL, loggedInUser);
//		reportService.sendCSVReport(ReprotsNameEnum.ALL_DEVELOPER_USERS, loggedInUser);
//		reportService.sendCSVReport(ReprotsNameEnum.WEEKLY_DEVELOPER_USERS, loggedInUser);
	}
	
	
	
}
