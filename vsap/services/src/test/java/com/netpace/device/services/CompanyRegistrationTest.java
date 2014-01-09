package com.netpace.device.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.dao.UserActivationDao;
import com.netpace.device.dao.UserDao;
import com.netpace.device.dao.WorkflowDao;
import com.netpace.device.entities.User;
import com.netpace.device.entities.UserActivation;
import com.netpace.device.entities.Workflow;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.AccountRegistration;
import com.netpace.device.vo.CompanyRegistration;
import com.netpace.device.vo.ContactVO;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import com.netpace.device.vo.product.ProcessingInfoVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author trafique
 */
public class CompanyRegistrationTest extends BaseServiceTest {
    
    private static final Log log = LogFactory.getLog(CompanyRegistrationTest.class);
    
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserActivationDao userActivationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyRegistrationService companyRegistrationService;
    
    @Autowired 
    WorkflowDao workflowDao;
    
    @Autowired 
    ApprovalService approvalService;
    
    @Test
    public void testAll(){
//        testRegisterCompany("test", 1, 1000);
//    	triggerWorkItem();
    }
    
    public void testRegisterCompany(String testText, int start, int end){
        AccountRegistration accountInfo;
        CompanyRegistration companyRegistration;
        VAPUserDetails userDetails;
        User user;
        ContactVO contactVO;

        for(int i=start; i<=end; i++){
            String username = testText+i;
            String domainName = username+".com";
            String emailAddress = username+"@"+username+".com";
            
            accountInfo = new AccountRegistration();
            accountInfo.setFullName(testText);
            accountInfo.setPhoneNumber("+1 111 111 111");
            accountInfo.setMobilePhoneNumber("+1 111 111 111");
            accountInfo.setEmailAddress(emailAddress);
            accountInfo.setConfirmEmailAddress(emailAddress);
            accountInfo.setCompanyDomain(domainName);
            accountInfo.setUserName(username);
            accountInfo.setPassword(username);
            accountInfo.setConfirmPassword(username);
            accountInfo.setAddress("3rd street");
            accountInfo.setCity("San Ramon");
            accountInfo.setState("California");
            accountInfo.setCountry(VAPConstants.DEFAULT_COUNTRY);
            accountInfo.setZip("75750");
            testRegisterAccount(accountInfo);
            
            UserActivation userActivation = userActivationDao.search(username);
            testActivateAccount(userActivation.getActivationCode());
            
            user = userDao.getUserByUserName(username);
            userDetails = new VAPUserDetails(
                user.getId(), user.getUserName(), user.getPassword(), true, 
                true, true, true, null , user.getFullName(),
                user.getPhone(), user.getMobile(), user.getEmailAddress(),
                user.getAddress(), user.getCity(), user.getState(), user.getCountry(),
                user.getPostalCode(), null, domainName,
                user.getCreatedDate());
            
            companyRegistration = new CompanyRegistration();
            companyRegistration.setCompanyName(username);
            companyRegistration.setCompanyLegalName(username);
            companyRegistration.setWebsite("http://www."+username+".com");
            companyRegistration.setDomainName(domainName);
            companyRegistration.setMainPhoneNumber("+1 111 111 111");
            companyRegistration.setCity("San Ramon");
            companyRegistration.setState("California");
            companyRegistration.setZip("75750");
            
            contactVO = new ContactVO();
            contactVO.setName(username);
            contactVO.setPhone("+1 111 111 111");
            contactVO.setCountry(VAPConstants.DEFAULT_COUNTRY);
            contactVO.setMobile("+1 111 111 111");
            contactVO.setEmailAddress(emailAddress);
            companyRegistration.setSalesContact(contactVO);
            testRegisterCompany(userDetails, companyRegistration);
            
            log.info("Registered company domain ["+domainName+"] successfully");
        }
    }
    
    
    public void triggerWorkItem(){
    	for (int i=117; i<=120;  i++){
    		WorkItem wi=new WorkItem();
    		WorkflowVO wf=new WorkflowVO();
    		VAPUserDetails loggedInUser = new VAPUserDetails(null, "aahmed", null,
    				true, true, true, true, null, null, null, null, null, null, null,
    				null, null, null, 115, null, null);
    		
    		Workflow dbwf=workflowDao.getWorkflowByCompanyId(i);
    		
    		String sectionName="DeviceMarketingReview";
//    		String sectionName="OFACReview";
//    		String sectionName="CertificationNDA";   

     		String btnType="Approve";
//    		String btnType="Approve";
//    		String btnType="Accept Agreement";     		
     		
     		wf.setId(dbwf.getId());
    		wi.setWorkflow(wf);
    		wi.setTitle(sectionName);
    		wi.setDecision(btnType);
    		
    		approvalService.processWorkitem(wi, loggedInUser);
    		
    	}
    }
    public void testRegisterAccount(AccountRegistration accountInfo) {
        registrationService.registerAccount(accountInfo, "");
    }
    
    public void testActivateAccount(String activationCode) {
        registrationService.activateRegisteredAccount(activationCode);
    }
    
    public void testRegisterCompany(VAPUserDetails userDetails, CompanyRegistration companyRegistration) {
        companyRegistrationService.registerCompany(userDetails, companyRegistration);
    }
    
}
