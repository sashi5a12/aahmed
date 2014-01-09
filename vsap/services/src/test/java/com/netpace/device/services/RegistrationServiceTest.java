package com.netpace.device.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.dao.UserActivationDao;
import com.netpace.device.utils.UniqueStringGenerator;
import com.netpace.device.vo.AccountRegistration;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class RegistrationServiceTest extends BaseServiceTest {

    private static final Log log = LogFactory.getLog(RegistrationServiceTest.class);
    
    @Resource(name = "registrationService")
    private RegistrationService registrationService;
    
    @Resource(name="passwordEncoder")
    PasswordEncoder encoder;
    
    @Resource(name = UserActivationDao.name)
    private UserActivationDao userActivationDao;
    
    private static String userName;
    private static String emailAddress;

    static{
        userName =  (UniqueStringGenerator.generateUniqueString());
        emailAddress = userName + "@netpace.com";
    }

    @Test
    public void testRegisterUser() throws Exception {
        log.info("Successfull Registration -- Start");
        String encoded = encoder.encodePassword("netpace123", null);
        log.info("encoded password is :"+encoded);
        AccountRegistration ar = initUser();
        registrationService.registerAccount(ar, "");
        log.info("Successfull Registration -- End");
    }
//
//    @Test
//    public void testAlreadyRegisteredUserName() {
//        log.info("Already Registered UserName -- Start");
//        AccountRegistration ar = initUser();
//        try {
//            registrationService.registerAccount(ar);
//            Assert.fail("Should Fire and BusinessRuleException for "+AccountRegistrationBusinessRule.ACCOUNT_USERNAME_EXISTS);
//        } catch (BusinessRuleException bre) {
//            log.error("Expected Exception", bre);
//            Assert.assertEquals(AccountRegistrationBusinessRule.ACCOUNT_USERNAME_EXISTS, bre.getBusinessRule());
//        }
//        log.info("Already Registered UserName -- End");
//    }
//    @Test
//    public void testAlreadyRegisteredEmail() {
//        log.info("Already Registered Email -- Start");
//        AccountRegistration ar = initUser();
//        ar.setUserName("t"+ar.getUserName());
//        
//        registrationService.registerAccount(ar);
//        
//        log.info("Already Registered Email -- End");
//    }
//    
//    @Test
//    public void testActivateUser() {
//        log.info("Already Activate User -- Start");
//        UserActivation ua = userActivationDao.getAll().get(0);
//        String activationCode = ua.getActivationCode();
//        
//        AccountRegistration account = registrationService.activateRegisteredAccount(activationCode);
//        log.info("Activated user name : "+account.getUserName());
//        
//        log.info("Already Activate User -- End");
//    }

    private AccountRegistration initUser() {
        AccountRegistration ar = new AccountRegistration();
        ar.setFullName("Umair Iqbal");
        ar.setUserName(userName);
        ar.setCompanyDomain("netpace.com");
        ar.setConfirmEmailAddress(emailAddress);
        ar.setEmailAddress(emailAddress);
        ar.setPassword("testing");
        ar.setConfirmPassword("testing");
        ar.setPhoneNumber("+122 233 3345");
        ar.setMobilePhoneNumber("+1 254 222 5545");
        ar.setAddress("3rd Street");
        ar.setCity("San Raman");
        ar.setState("California");
        ar.setCountry("United States of America");
        ar.setZip("75750");
        return ar;
    }
}
