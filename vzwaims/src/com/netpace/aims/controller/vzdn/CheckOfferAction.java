package com.netpace.aims.controller.vzdn;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.vzdn.AimsUserOfferManager;
import com.netpace.aims.bo.vzdn.VzdnUserManager;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsUserOffer;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.OpenssoRestService;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 * @struts.action path="/checkOffer"
 * 				  name="CheckOfferForm" 	
 *                validate="false"
 * @struts.action-forward name="validOffer" path="/vzdn/showoffer.jsp"
 * @struts.action-forward name="allianceRegistration" path="/allianceRegistrationSetup.do?task=createForm"
 * @struts.action-forward name="musicRegistration" path="/allianceMusicRegistrationSetup.do"
 * @struts.action-forward name="error" path="/jsp/common/security.jsp"
 * @struts.action-forward name="login" path="/login.do" 
 */

public class CheckOfferAction extends Action{

	static Logger log = Logger.getLogger(CheckOfferAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		VzdnCredentials vzdnCredentials = new VzdnCredentials();
		
		log.debug("---in check offer");
		try{
		CheckOfferForm offerForm = (CheckOfferForm)form;
		
		log.debug("---checkOfferForm :::"+offerForm.getFormOfferStatus());
		boolean offerExcepted=false;
		
		//If Developer Accept Invitation
		if(offerForm.getFormOfferStatus()!=null && offerForm.getFormOfferStatus().equals(AimsConstants.OFFER_ACCEPTED)){
			log.debug("offer excepted by user");
			this.swapVzdnValues(vzdnCredentials, offerForm);
			AimsUser aimsUser =  AimsAccountsManager.validateAnyUser(offerForm.getUserName());
			//If User exists in Zon but might be marked deleted or in-active.
			if(aimsUser!=null){
				try{
					AimsUserOffer aimsUserOffer =  AimsUserOfferManager.getUserOffer(offerForm.getOfferId());
					//1. set Offer Marker as Accepted
					aimsUserOffer.setStatus(AimsConstants.OFFER_ACCEPTED);
					aimsUserOffer.setAccRejDate(new Date());
					AimsUserOfferManager.updateUserOffer(aimsUserOffer);
					log.debug("accepted offer user is present in db");
					
					//2. Marked User as Active and Change Allaince .
					/*aimsUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
					aimsUser.setAimsAllianc(offerForm.getAllianceId());
					AimsUserOfferManager.updateUserAllaince(aimsUser,session);*/
					
					//3. Update Role to Secondary.
					//vzdnCredentials.setVzdnManagerRoles(AimsConstants.VZDN_MAPPING_SECONDARY_USERS_ROLE_ID.toString());
					//VzdnUserManager.updateUser(aimsUser, vzdnCredentials,null);
					
					int count  = VzdnUserManager.recursviceUserCheck(aimsUser.getUsername());
					String newUserName = aimsUser.getUsername()+"del_00"+count;
					AimsAccountsManager.UpdateUserRolesWithPermanentDelete(aimsUser.getUserId().intValue(),
							newUserName, aimsUser.getPassword(),
	            			aimsUser.getUserAccountStatus(),
	            			aimsUser.getAimsContact().getFirstName(),
	            			aimsUser.getAimsContact().getLastName(),
	            			aimsUser.getAimsContact().getEmailAddress(),
	            			aimsUser.getAimsContact().getTitle(),
	            			aimsUser.getAimsContact().getPhone(),
	            			aimsUser.getAimsContact().getMobile(),
	            			aimsUser.getAimsContact().getFax(),
	            			aimsUser.getUsername(), aimsUser.getUserType(), VzdnUserManager.getStringArrayFromObject(aimsUser.getRoles()), AimsConstants.VZDN_PERMANENT_DELETE);
					

					
					vzdnCredentials.setVzdnManagerRoles(AimsConstants.SECONDARY_USERS_ROLE_ID.toString());
					VzdnUserManager.createUser(vzdnCredentials,offerForm.getAllianceId());
					aimsUser = AimsAccountsManager.validateUser(aimsUser.getUsername());
					AimsAllianc allaince = AllianceManager.getAllianceOnAllianceID(offerForm.getAllianceId());
					
					OpenssoRestService.updateUserCompany(aimsUser, allaince);
					
					request.setAttribute("aimsUser", aimsUser);
					request.setAttribute("vzdnCredentials", vzdnCredentials);
					return mapping.findForward("login");
				}catch(Exception ex){
					ex.printStackTrace();
					ActionErrors errors = new ActionErrors();
		            ActionError error = new ActionError("error.contact.admin");
		            errors.add(ActionErrors.GLOBAL_ERROR, error);
		            request.setAttribute(Globals.ERROR_KEY, errors);
		            return mapping.findForward("error");
		    		
				}
			}else{
				try{
					AimsUserOffer aimsUserOffer =  AimsUserOfferManager.getUserOffer(offerForm.getOfferId());
					aimsUserOffer.setStatus(AimsConstants.OFFER_ACCEPTED);
					aimsUserOffer.setAccRejDate(new Date());
					AimsUserOfferManager.updateUserOffer(aimsUserOffer);
					vzdnCredentials.setVzdnManagerRoles(AimsConstants.SECONDARY_USERS_ROLE_ID.toString());
					VzdnUserManager.createUser(vzdnCredentials,offerForm.getAllianceId());
				
					aimsUser = AimsAccountsManager.validateUser(vzdnCredentials.getUserName());
					
					AimsAllianc allaince = AllianceManager.getAllianceOnAllianceID(offerForm.getAllianceId());
					
					OpenssoRestService.updateUserCompany(aimsUser, allaince);
					
					log.debug("accepted offer user is not in DB");
					request.setAttribute("aimsUser", aimsUser);
					request.setAttribute("vzdnCredentials", vzdnCredentials);
					return mapping.findForward("login");
				}catch(Exception ex){
					ex.printStackTrace();
					//throw exception
					ActionErrors errors = new ActionErrors();
		            ActionError error = new ActionError("error.contact.admin");
		            errors.add(ActionErrors.GLOBAL_ERROR, error);
		            request.setAttribute(Globals.ERROR_KEY, errors);
		            return mapping.findForward("error");
				}
			}
			//if Developer Rejected Invitation
		}else if(offerForm.getFormOfferStatus()!=null && offerForm.getFormOfferStatus().equals(AimsConstants.OFFER_REJECTED)){
			log.debug("----offer id rejected is :"+offerForm.getOfferId());
			log.debug("----alliance id  is :"+offerForm.getAllianceId());
			this.swapVzdnValues(vzdnCredentials, offerForm);
			log.debug("----username swapped value is  :"+vzdnCredentials.getUserName());
			
			
			try{
				AimsUserOffer aimsUserOffer =  AimsUserOfferManager.getUserOffer(offerForm.getOfferId());
				aimsUserOffer.setStatus(AimsConstants.OFFER_REJECTED);
				aimsUserOffer.setAccRejDate(new Date());
				AimsUserOfferManager.updateUserOffer(aimsUserOffer);
			}catch(Exception ex){
				ex.printStackTrace();
				ActionErrors errors = new ActionErrors();
	            ActionError error = new ActionError("error.contact.admin");
	            errors.add(ActionErrors.GLOBAL_ERROR, error);
	            request.setAttribute(Globals.ERROR_KEY, errors);
	            return mapping.findForward("error");
			}
		}

		if(request.getAttribute("vzdnCredentials")!=null){
			log.debug("---found vzdncredentials in request");
			vzdnCredentials = (VzdnCredentials) request.getAttribute("vzdnCredentials");
			 
		}
        
        	log.debug("---finding offers against user : "+vzdnCredentials.getUserName());
	        Collection userOfferList = AimsUserOfferManager.getUserOffers(vzdnCredentials.getUserName());
	        if(userOfferList!=null && userOfferList.size()>0){
	        	log.debug("---found offers and size is :"+userOfferList.size());
	        	List validOffers = new ArrayList();
	        	Iterator it= userOfferList.iterator();
	        	while(it.hasNext()){
	        		AimsUserOffer userOffer = (AimsUserOffer) it.next();
	        		if(userOffer.getAlliance()!=null && this.daysBetween(userOffer.getOfferDate())<30){
	        			offerForm.setAllianceId(userOffer.getAlliance().getAllianceId());
	        			offerForm.setAllianceName(userOffer.getAlliance().getCompanyName());
	        			offerForm.setOfferDate(userOffer.getOfferDate());
	        			offerForm.setOfferFrom(userOffer.getOfferFromName()+" ["+userOffer.getOfferFromEmail()+"]");
	        			offerForm.setOfferId(userOffer.getOfferId());
	        			offerForm.setOfferTo(userOffer.getOfferTo());
	        			offerForm.setStatus(userOffer.getStatus());
	        			offerForm.setRequestURI(vzdnCredentials.getRequestURI());
	        			offerForm.setUserName(vzdnCredentials.getUserName());
	        			offerForm.setUserType(vzdnCredentials.getVzdnUserType());
	        			offerForm.setManagerRoles(vzdnCredentials.getVzdnManagerRoles());
	        			offerForm.setFirstName(vzdnCredentials.getFirstName());
	        			offerForm.setLastName(vzdnCredentials.getLastName());
	        			offerForm.setTitle(vzdnCredentials.getTitle());
	        			offerForm.setPhone(vzdnCredentials.getPhoneNumber());
	        			offerForm.setFax(vzdnCredentials.getFaxNumber());
	        			offerForm.setMobile(vzdnCredentials.getMobileNumber());
	        			return mapping.findForward("validOffer");
	        		}
	        	}
	        }
        
	    request.setAttribute("vzdnCredentials", vzdnCredentials);
	        
        if(vzdnCredentials.getRequestURI().contains("allianceRegistrationSetup") 
        		|| vzdnCredentials.getRequestURI().contains("allianceMusicRegistrationSetup")
        		|| vzdnCredentials.getRequestURI().contains("jmaAllianceRegistrationSetup")
        ){
    		log.debug("----url contains allianceregistation or alliancemusicregistration or jmaAllianceRegistrationSetup");
    		log.debug("----url ==="+vzdnCredentials.getRequestURI());
    		RequestDispatcher ds = request.getRequestDispatcher(vzdnCredentials.getRequestURI());
    		ds.forward(request, response);
        }
        
        return mapping.findForward("allianceRegistration");
		}catch(Exception e){
			e.printStackTrace();
			return mapping.findForward("allianceRegistration");
		}
	}
	
	
	
	/*request.setAttribute("aimsUser", aimsUser);
	request.setAttribute("vzdnCredentials", vzdnCredentials);
	RequestDispatcher ds = request.getRequestDispatcher("/login.do");
	ds.forward(request, response);
	return;*/
	
	 
	 private void swapVzdnValues(VzdnCredentials vzdnCredentials, CheckOfferForm form){
		 
		 vzdnCredentials.setRequestURI(form.getRequestURI());
		 vzdnCredentials.setUserName(form.getUserName());
		 vzdnCredentials.setVzdnManagerRoles(form.getManagerRoles());
		 vzdnCredentials.setVzdnUserType(form.getUserType());
		 vzdnCredentials.setFirstName(form.getFirstName());
		 vzdnCredentials.setLastName(form.getLastName());
		 vzdnCredentials.setTitle(form.getTitle());
		 vzdnCredentials.setPhoneNumber(form.getPhone());
		 vzdnCredentials.setFaxNumber(form.getFax());
		 vzdnCredentials.setMobileNumber(form.getMobile());
		 
		 
	 }
	 
	 
	 
	 public static long daysBetween(Date offerDate) {  
		    Calendar offerDateCalendar = Calendar.getInstance();
		    offerDateCalendar.setTime(offerDate);

		    Calendar today = Calendar.getInstance();
		    today.setTime(new Date());
		    
		    
		   Calendar date = (Calendar) offerDateCalendar.clone();  
		   long daysBetween = 0;  
		   while (date.before(today)) {  
		     date.add(Calendar.DAY_OF_MONTH, 1);  
		     daysBetween++;  
		   }
		   log.debug("difference in offer days : "+daysBetween);
		   
		   return daysBetween;  
		 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
}
