package com.netpace.aims.controller.vzdn;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.vzdn.VzdnUserManager;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.OpenssoRestService;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import com.netpace.aims.ws.ServiceManager;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 * @struts.action path="/vzdnLoginAction"
 *                validate="false"
 * @struts.action-forward name="error" path="/common/security.jsp"
 * @struts.action-forward name="checkoffer" path="/checkOffer.do"
 * @struts.action-forward name="login" path="/login.do"
 */
public class VzdnLoginAction extends Action{

	Logger log = Logger.getLogger(VzdnLoginAction.class);
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
		try{
			log.debug("-------start OF VzdnLoginAction action");
			log.debug("-------request URL IS "+request.getServletPath());
			VzdnCredentials vzdnCredentials = new VzdnCredentials();
    		vzdnCredentials= this.retrieveUserName(request, vzdnCredentials);
    		if(request.getAttribute("registrationLink")!=null)
    			vzdnCredentials.setRequestURI((String)request.getAttribute("registrationLink"));
    		else
    			vzdnCredentials.setRequestURI(request.getServletPath());
    		
    		if(!vzdnCredentials.getUserName().equals("")){
    			
    			vzdnCredentials = this.getVzdnUserRolesAndType(request, vzdnCredentials);
    				
    			AimsUser aimsUser = AimsAccountsManager.validateAnyUser(vzdnCredentials.getUserName());
    			if(aimsUser!=null){
    				this.userExistsInZon(request, response, vzdnCredentials, mapping,aimsUser);
    			}else{
    				this.userDoesNotExistsInZon(request, response, vzdnCredentials, mapping,aimsUser);
    			}
    			/*if(vzdnCredentials.getVzdnUserType().equals("Verizon")){
					this.performActionForVerizonUsers(request, response, vzdnCredentials, mapping);
				}else{
					this.performActionForDeveloperUsers(request, response, vzdnCredentials, mapping);
				}*/
    		}else{
    			ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError("error.contact.admin");
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                request.setAttribute(Globals.ERROR_KEY, errors);
                return mapping.findForward("error");
    		}
		}catch(Exception ex){
			ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("error.contact.admin");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            request.setAttribute(Globals.ERROR_KEY, errors);
            return mapping.findForward("error");
		}
		ActionErrors errors = new ActionErrors();
        ActionError error = new ActionError("error.contact.admin");
        errors.add(ActionErrors.GLOBAL_ERROR, error);
        request.setAttribute(Globals.ERROR_KEY, errors);
        return mapping.findForward("error");
		
		
	}
	
	
	private void userExistsInZon(HttpServletRequest request,HttpServletResponse response, VzdnCredentials  vzdnCredentials,ActionMapping mapping, AimsUser aimsUser) throws Exception{
		if(aimsUser.getUserAccountStatus().equals(AimsConstants.USER_STATUS_ACTIVE)){
			log.debug("->->-> User is Active in ZON");
			this.performActionForActiveUsers(request, response, vzdnCredentials, mapping, aimsUser);
		}else{
			log.debug("->->-> User is IN Active in ZON");
			this.performActionForINActiveUsers(request, response, vzdnCredentials, mapping, aimsUser);
		}
	}
	
	private void userDoesNotExistsInZon(HttpServletRequest request,HttpServletResponse response, VzdnCredentials  vzdnCredentials,ActionMapping mapping, AimsUser aimsUser)throws Exception{
		if(vzdnCredentials.getVzdnUserType().equals(AimsConstants.VZDN_VERIZON_USER_TYPE)){
			this.performActionForVerizonUsers(request, response, vzdnCredentials, mapping,aimsUser);
		}else{
			this.performActionForDeveloperUsers(request, response, vzdnCredentials, mapping,aimsUser);
		}
	}
	
	private void performActionForActiveUsers(HttpServletRequest request,HttpServletResponse response, VzdnCredentials  vzdnCredentials,ActionMapping mapping, AimsUser aimsUser) throws Exception{
		if(aimsUser.getUserType().equals(AimsConstants.VZW_USERTYPE)){
			log.debug("->->-> User is VZ in ZON");
			aimsUser = VzdnUserManager.updateUser(aimsUser, vzdnCredentials);
			this.forwardToLogin(aimsUser, vzdnCredentials, request, response,mapping);
		}else{
			log.debug("->->-> User is DEVELOPER in ZON");
			//Call updateDeveloper onto VDS Systems.
			if ( !aimsUser.getAimsContact().getFirstName().equals(vzdnCredentials.getFirstName()) 
					|| !aimsUser.getAimsContact().getLastName().equals(vzdnCredentials.getLastName())
					//Phone number is optional and is not needed by vds. So following check is of no use. Waseem Akram
					//|| !aimsUser.getAimsContact().getPhone().equals(vzdnCredentials.getPhoneNumber())
					//|| !aimsUser.getAimsContact().getFax().equals(vzdnCredentials.getFaxNumber())
					|| !aimsUser.getAimsContact().getEmailAddress().equals(vzdnCredentials.getUserName())
				) {
					//Update just before making updateDeveloper call to VDS.
					aimsUser = VzdnUserManager.updateUser(aimsUser, vzdnCredentials);
					log.debug("User Profile is changed, making updateDeveloper() call to VDS System");
					ServiceManager.updateDeveloper(aimsUser.getAimsAllianc());
			}else {
				log.debug("Sync up user profile from VZDN to ZON");
				//Always update SSO/VZDN Profile.
				aimsUser = VzdnUserManager.updateUser(aimsUser, vzdnCredentials);
			}
			this.forwardToLogin(aimsUser, vzdnCredentials, request, response, mapping);
		}
	}
	
 private void performActionForINActiveUsers(HttpServletRequest request,HttpServletResponse response, VzdnCredentials  vzdnCredentials,ActionMapping mapping, AimsUser aimsUser) throws Exception{
	if(vzdnCredentials.getVzdnUserType().equals(AimsConstants.VZDN_VERIZON_USER_TYPE)){
		log.debug("->->-> User is inactive and Verizon in HEADER");
		AimsUser newUser = VzdnUserManager.createVerizonUserWithMarkingPermDelete(vzdnCredentials,aimsUser);
		request.getSession().removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
		log.debug("---forward to login");
		this.forwardToLogin(newUser, vzdnCredentials, request, response,mapping);
	}else{
		log.debug("->->-> User is inactive and Developer in HEADER");
		
        //remove page after login, for unregistered users, so that new users will go through proper registration process
        request.getSession().removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
        this.forwardToCheckOffer(aimsUser, vzdnCredentials, request, response, mapping);
	}
	}
	

	
	private void performActionForDeveloperUsers(HttpServletRequest request,HttpServletResponse response, VzdnCredentials  vzdnCredentials,ActionMapping mapping, AimsUser aimsUser) throws Exception{
			log.debug("----New User and is Developer");
            request.getSession().removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
            this.forwardToCheckOffer(aimsUser, vzdnCredentials, request, response, mapping);
    }
    
	
	
	private void performActionForVerizonUsers(HttpServletRequest request, HttpServletResponse response, VzdnCredentials vzdnCredentials,ActionMapping mapping,AimsUser aimsUser ) throws Exception{
            	log.debug("----user is verizon and not available in ZON");
				aimsUser= VzdnUserManager.createUser(vzdnCredentials);
                //remove page after login, for unregistered users, so that new users will go through proper registration process
                request.getSession().removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
				this.forwardToLogin(aimsUser, vzdnCredentials, request, response,mapping);
	}
    







/*private void performActionForVerizonUsers(HttpServletRequest request, HttpServletResponse response, VzdnCredentials vzdnCredentials,ActionMapping mapping ) throws Exception{
    	
    	log.debug("----user is verizon");
		//TODO :: login if exists, else create and Login
		AimsUser aimsUser = AimsAccountsManager.validateUser(vzdnCredentials.getUserName());
		//try{
			if(aimsUser!=null){
				log.debug("---User is available in Zon");
				aimsUser = VzdnUserManager.updateUser(aimsUser, vzdnCredentials);
				this.forwardToLogin(aimsUser, vzdnCredentials, request, response,mapping);
			}else{
				log.debug("---User is NOT available in Zon");	
				aimsUser= VzdnUserManager.createUser(vzdnCredentials);
                //remove page after login, for unregistered users, so that new users will go through proper registration process
                request.getSession().removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
				this.forwardToLogin(aimsUser, vzdnCredentials, request, response,mapping);
	    	}
		}catch(Exception ex){
			ex.printStackTrace();
			ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("error.contact.admin");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            request.setAttribute(Globals.ERROR_KEY, errors);
            RequestDispatcher ds = request.getRequestDispatcher("/jsp/common/security.jsp");
    		ds.forward(request, response);
    		return;
		}
    	
	}*/
	
/*private void performActionForDeveloperUsers(HttpServletRequest request,HttpServletResponse response, VzdnCredentials  vzdnCredentials,ActionMapping mapping) throws Exception{
log.debug("----user is Developer");
AimsUser aimsUser = AimsAccountsManager.validateUser(vzdnCredentials.getUserName());
//vzdnCredentials.setRequestURI(request.getServletPath());

if(aimsUser!=null && aimsUser.getUserAccountStatus().equals(AimsConstants.USER_STATUS_ACTIVE)){
	log.debug("---User is available in Zon");
	aimsUser = VzdnUserManager.updateUser(aimsUser, vzdnCredentials);
	//aimsUser = VzdnUserManager.updateUser(aimsUser, vzdnCredentials);
	
	//Call Update to Developer on VDS Systems.
	if ( !aimsUser.getAimsContact().getFirstName().equals(vzdnCredentials.getFirstName()) 
			|| !aimsUser.getAimsContact().getLastName().equals(vzdnCredentials.getLastName())
			|| !aimsUser.getAimsContact().getPhone().equals(vzdnCredentials.getPhoneNumber())
			|| !aimsUser.getAimsContact().getFax().equals(vzdnCredentials.getFaxNumber())
			|| !aimsUser.getAimsContact().getEmailAddress().equals(vzdnCredentials.getUserName())
		) {
			ServiceManager.updateDeveloper(aimsUser.getAimsAllianc());
	}
	
	this.forwardToLogin(aimsUser, vzdnCredentials, request, response, mapping);
}else{
    log.debug("---User is NOT available in Zon");
    //remove page after login, for unregistered users, so that new users will go through proper registration process
    request.getSession().removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
    this.forwardToCheckOffer(aimsUser, vzdnCredentials, request, response, mapping);
}
}

*/
    
    private void forwardToLogin(AimsUser aimsUser, VzdnCredentials vzdnCredentials, HttpServletRequest request, HttpServletResponse response,ActionMapping mapping) throws Exception{
    	log.debug("--Forwarding to Login");
		request.setAttribute("aimsUser", aimsUser);
		request.setAttribute("vzdnCredentials", vzdnCredentials);
		RequestDispatcher ds = request.getRequestDispatcher("/login.do");
		ds.forward(request, response);
		return;
		//mapping.findForward("login");
    }
    
    
    private void forwardToCheckOffer(AimsUser aimsUser, VzdnCredentials vzdnCredentials, HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) throws Exception{
    	log.debug("--Forwarding to checkOffer");
		request.setAttribute("aimsUser", aimsUser);
		request.setAttribute("vzdnCredentials", vzdnCredentials);
		RequestDispatcher ds = request.getRequestDispatcher("/checkOffer.do");
		ds.forward(request, response);
		return;
		//mapping.findForward("checkoffer");
    }
    
    private VzdnCredentials getVzdnUserRolesAndType(HttpServletRequest req, VzdnCredentials vzdnCredentials){
    	log.debug("VzdnLoginAction.getVzdnUserRolesAndType Start:");    	
    	ConfigEnvProperties props=ConfigEnvProperties.getInstance();
    	String opensso_firstName = props.getProperty("opensso.vzdn.firstName");
    	String opensso_lastName = props.getProperty("opensso.vzdn.lastName");
    	String opensso_userRoles = props.getProperty("opensso.vzdn.user.roles");
    	String opensso_userType = props.getProperty("opensso.vzdn.user.type");
    	String opensso_title = props.getProperty("opensso.vzdn.title");
    	String opensso_mobileNumber = props.getProperty("opensso.vzdn.mobile.number");
    	String opensso_phoneNumber = props.getProperty("opensso.vzdn.phone.number");
    	String opensso_faxNumber = props.getProperty("opensso.vzdn.fax.number");

		String opensso_firstName_attr="givenname";
		String opensso_lastName_attr="sn";

    	try {
    		Map values=OpenssoRestService.read(retrieveUserNameFromCookie(req), new String[]{opensso_userRoles,opensso_userType,opensso_firstName_attr,opensso_lastName_attr,opensso_title,opensso_mobileNumber,opensso_phoneNumber,opensso_faxNumber});
    		req.setAttribute(AimsConstants.SESSION_OPEN_SSO_MAP, values);
    		
			String userRoles = (String)values.get(opensso_userRoles);
			if (StringFuncs.isNullOrEmpty(userRoles))
				userRoles=null;
			vzdnCredentials.setVzdnManagerRoles(userRoles);
			log.debug("vzdn roles fetched from opensso :"+userRoles);
			
			String userType = (String)values.get(opensso_userType);
			vzdnCredentials.setVzdnUserType(userType);
			log.debug("vzdn user type  fetched from opensso :"+userType);	  
			
			String firstName = (String)values.get(opensso_firstName_attr);
			firstName = Utility.strTruncate(firstName,50);
			vzdnCredentials.setFirstName(firstName);
			log.debug("vzdn first name  fetched from opensso:"+firstName);	    
			
			String lastName = (String)values.get(opensso_lastName_attr);
			lastName = Utility.strTruncate(lastName,50);
			vzdnCredentials.setLastName(lastName);
			log.debug("vzdn last name  fetched from opensso:"+lastName);	  
			
			String title = (String)values.get(opensso_title);
			title = Utility.strTruncate(title,50);
			if(StringFuncs.isNullOrEmpty(title))
				title="--";
			vzdnCredentials.setTitle(title);
			log.debug("vzdn title  fetched from opensso:"+title);
			
			String mobileNumber = (String)values.get(opensso_mobileNumber);
			mobileNumber = Utility.strTruncate(mobileNumber,50);
			if(StringFuncs.isNullOrEmpty(mobileNumber))
				mobileNumber="--";
			vzdnCredentials.setMobileNumber(mobileNumber);
			log.debug("vzdn mobile fetched from opensso:"+mobileNumber);
			
			String phoneNumber = (String)values.get(opensso_phoneNumber);
			phoneNumber = Utility.strTruncate(mobileNumber,50);
			if(StringFuncs.isNullOrEmpty(phoneNumber))
				phoneNumber="--";
			vzdnCredentials.setPhoneNumber(phoneNumber);
			log.debug("vzdn phone fetched from opensso:"+phoneNumber);
			
			String faxNumber = (String)values.get(opensso_faxNumber);
			faxNumber = Utility.strTruncate(faxNumber,50);
			if(StringFuncs.isNullOrEmpty(faxNumber))
				faxNumber="--";
			vzdnCredentials.setFaxNumber(faxNumber);
			log.debug("vzdn fax fetched from opensso:"+faxNumber);
			
    		return vzdnCredentials;
    	} catch (Exception e) {
			log.debug("Exception occurend in openssoapi call code..... ");
			log.error(e,e);

			String userRoles = req.getHeader(opensso_userRoles);
			vzdnCredentials.setVzdnManagerRoles(userRoles);
			log.debug("vzdn roles fetched from request header :"+userRoles);
			
			String userType = req.getHeader(opensso_userType);
			vzdnCredentials.setVzdnUserType(userType);
			log.debug("vzdn user type  fetched from request header :"+userType);	  
			
			String firstName = req.getHeader(opensso_firstName);
			firstName = Utility.strTruncate(firstName,50);
			vzdnCredentials.setFirstName(firstName);
			log.debug("vzdn first name  fetched from request header:"+firstName);	    
			
			String lastName = req.getHeader(opensso_lastName);
			lastName = Utility.strTruncate(lastName,50);
			vzdnCredentials.setLastName(lastName);
			log.debug("vzdn last name  fetched from request header:"+lastName);	  
			
			String title = req.getHeader(opensso_title);
			title = Utility.strTruncate(title,50);
			if(StringFuncs.isNullOrEmpty(title))
				title="--";
			vzdnCredentials.setTitle(title);
			log.debug("vzdn title  fetched from request header:"+title);
			
			String mobileNumber = req.getHeader(opensso_mobileNumber);
			mobileNumber = Utility.strTruncate(mobileNumber,50);
			if(StringFuncs.isNullOrEmpty(mobileNumber))
				mobileNumber="--";
			vzdnCredentials.setMobileNumber(mobileNumber);
			log.debug("vzdn mobile fetched from request header:"+mobileNumber);
			
			String phoneNumber = req.getHeader(opensso_phoneNumber);
			phoneNumber = Utility.strTruncate(phoneNumber,50);
			if(StringFuncs.isNullOrEmpty(phoneNumber))
				phoneNumber="--";
			vzdnCredentials.setPhoneNumber(phoneNumber);
			log.debug("vzdn phone fetched from request header:"+phoneNumber);
			
			String faxNumber = req.getHeader(opensso_faxNumber);
			faxNumber = Utility.strTruncate(faxNumber,50);
			if(StringFuncs.isNullOrEmpty(faxNumber))
				faxNumber="--";
			vzdnCredentials.setFaxNumber(faxNumber);
			log.debug("vzdn fax fetched from request header:"+faxNumber);	
			
			try {
				MailUtils.sendMail(
				        AimsConstants.EMAIL_EXCEPTION_ADMIN,
				        "exceptions@netpace.com", 
				        "Error occured when making OpenssoRestAPI call to get user info. Switched to alternate approach getting values from request.",
				        null, 
				        MiscUtils.getRequestInfo(req));
			} catch (Exception ex) {
				log.error(e,e);
			}
			
			return vzdnCredentials;
		}
    	
    	
    }
    
    
    private VzdnCredentials retrieveUserName(HttpServletRequest req, VzdnCredentials vzdnCredentials){
    	vzdnCredentials.setUserName(this.retrieveUserNameFromCookie(req));
		return vzdnCredentials;	
    }
    	

    
    
    private String retrieveUserNameFromCookie(HttpServletRequest req){
    	log.debug("=-------in retrieveUserNameFromCookie method -----");
    	String userName="";
    	try {
				SSOTokenManager stm= SSOTokenManager.getInstance();
				Cookie []  requestCookies = req.getCookies();
				for(int i=0; i < requestCookies.length; i++){
					Cookie cookie = requestCookies[i];
					if(cookie.getName().equals("iPlanetDirectoryPro")){
						//String ssoTokenId = java.net.URLDecoder.decode(cookie.getValue(),"UTF-8");
						log.debug("---cookie value : "+cookie.getValue());
						//com.iplanet.am.cookie.name=iPlanetDirectoryPro 	
						SSOToken st = stm.createSSOToken(req);
						    userName = st.getPrincipal().getName();
						    userName = userName.substring( userName.indexOf( "=" ) + 1, userName.indexOf( "," ) ).toLowerCase();
						/*String ssotoken =
					    	AmFilterManager.getAmSSOCache().getSSOTokenForUser(req);		    
						    log.debug("user found in sso cookie");
						    System.out.println("got the cookie user name : "+ssotoken);
						    System.out.println("decoding   : "+java.net.URLDecoder.decode(ssotoken));
						    System.out.println("decoding using utf : "+java.net.URLDecoder.decode(ssotoken,"UTF-8"));*/
						log.debug("got the cookie user name : "+userName);
												    
					}
				}
				return userName;
			} catch (Exception e) {
				log.error("error occured while retrieving cookie sent by sso.",e);
				return userName;
			}
    	
    	
    }


	
	
	
	
}
