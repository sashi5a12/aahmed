/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 *
 * @author Wakram
 */
public class ApplicationPropertiesServiceTest extends BaseServiceTest  {
    private static final Log log = LogFactory.getLog(ApplicationPropertiesServiceTest.class);
	
	@Autowired
	private ApplicationPropertiesService applicationProperties;
	
	public ApplicationPropertiesServiceTest() {
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
	public void testService() {
            //test here
            
            applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.email.host");
            String exceptionEmail = applicationProperties.exceptionSendtoEmail();
            Integer workitemdelayInDays = applicationProperties.workitemDelayInDays();
            
            Assert.notNull(exceptionEmail, "Exception email is null");
            Assert.notNull(workitemdelayInDays, "Workitem delay in days is null");
            
            log.info("exceptionEmail: "+exceptionEmail);
            log.info("workitemdelayInDays: "+workitemdelayInDays);
	}
}
