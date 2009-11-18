package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.alliance.AllianceRegistrationManager;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.roles.AimsSysRolesManager;
import com.netpace.aims.bo.vzdn.VzdnUserManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.masters.AimsVzwReasons;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.*;

import java.util.*;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/allianceRegistrationUpdate"
 *                name="AllianceRegistrationForm"
 *                scope="request"
 *                input="/alliance/allianceRegistrationUpdate.jsp"
 *				  validate="true"
 * @struts.action-forward name="view" path="/public/index.jsp"
 * @struts.action-forward name="error" path="/alliance/allianceRegistrationUpdate.jsp"
 * @author Adnan Makda.
 */
public class AllianceRegistrationInsertUpdateAction extends BaseAction
{
    static Logger log = Logger.getLogger(AllianceRegistrationInsertUpdateAction.class.getName());


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = request.getParameter("task");
        log.debug("start AllianceRegistrationInsertUpdateAction: task= "+taskname);
        String forward = "view";

        Collection rolePrivilegeList = null;

        if (taskname.equalsIgnoreCase("create"))
        {
            AllianceRegistrationForm allianceRegistrationForm = (AllianceRegistrationForm) form;
            //printFormValues(allianceRegistrationForm);
            AimsUser aimsUser = new AimsUser();
            AimsUser aimsUserAlreadyExists =  AimsAccountsManager.validateAnyUser(allianceRegistrationForm.getLoginId());
            Long aimsUserByAdminUserId = null;
            if(aimsUserAlreadyExists!=null && !aimsUserAlreadyExists.getUserAccountStatus().equals(AimsConstants.USER_STATUS_ACTIVE) )
            {            	
            	int count = VzdnUserManager.recursviceUserCheck(aimsUserAlreadyExists.getUsername());
            	String updateUserName=aimsUserAlreadyExists.getUsername()+"del_00"+count;
            	AimsAccountsManager.UpdateUserRolesWithPermanentDelete
            	(aimsUserAlreadyExists.getUserId().intValue(),
            			updateUserName, aimsUserAlreadyExists.getPassword(),
            			aimsUserAlreadyExists.getUserAccountStatus(),
            			aimsUserAlreadyExists.getAimsContact().getFirstName(),
            			aimsUserAlreadyExists.getAimsContact().getLastName(),
            			aimsUserAlreadyExists.getAimsContact().getEmailAddress(),
            			aimsUserAlreadyExists.getAimsContact().getTitle(),
            			aimsUserAlreadyExists.getAimsContact().getPhone(),
            			aimsUserAlreadyExists.getAimsContact().getMobile(),
            			aimsUserAlreadyExists.getAimsContact().getFax(),
            			aimsUserAlreadyExists.getUsername(), aimsUserAlreadyExists.getUserType(), VzdnUserManager.getStringArrayFromObject(aimsUserAlreadyExists.getRoles()), AimsConstants.VZDN_PERMANENT_DELETE);
            	
            	
            	/*Collection c =  AllianceManager.getAllianceDetails(aimsUser.getAimsAllianc(),"");
            	for (Iterator iter = c.iterator(); iter.hasNext();){
            		Object [] userValues = (Object []) iter.next();
                     if(userValues[18]!=null){
                    	 aimsUserByAdminUserId = ( (Long) userValues[11]);
                     }
                }
            	
            	if(aimsUserByAdminUserId!=null && aimsUser.getUserId()==aimsUserByAdminUserId){
            		 ActionErrors errors = new ActionErrors();
                     ActionError error = new ActionError("error.AllianceRegistrationForm.alliance.alreadytied");
                     errors.add(ActionErrors.GLOBAL_ERROR, error);
                     saveErrors(request, errors);
                     return (mapping.findForward("error"));
            	}*/
            	
            	/*aimsUser.setUserId(aimsUserAlreadyExists.getUserId());
            	aimsUser.setPassword(StringFuncs.asHex(MiscUtils.generate128bitkey()));
            	aimsUser.setAimsVzwDepartment(aimsUserAlreadyExists.getAimsVzwDepartment());
            	aimsUser.setCreatedBy(aimsUserAlreadyExists.getCreatedBy());
            	aimsUser.setCreatedDate(aimsUserAlreadyExists.getCreatedDate());
            	
            	aimsUser.setIfBlocked(aimsUserAlreadyExists.getIfBlocked());
            	aimsUser.setIfHq(aimsUserAlreadyExists.getIfHq());
            	aimsUser.setIsAccountManager(aimsUserAlreadyExists.getIsAccountManager());
            	aimsUser.setLastLoginDate(aimsUserAlreadyExists.getLastLoginDate());
            	aimsUser.setLastUpdatedBy(aimsUserAlreadyExists.getLastUpdatedBy());
            	aimsUser.setLastUpdatedDate(aimsUserAlreadyExists.getLastUpdatedDate());
            	aimsUser.setManagerId(aimsUserAlreadyExists.getManagerId());
            	aimsUser.setMotherMaidenName(aimsUserAlreadyExists.getMotherMaidenName());
            	aimsUser.setNotificationType(aimsUserAlreadyExists.getNotificationType());
            	
            	aimsUser.setRowsLength(aimsUserAlreadyExists.getRowsLength());
            	aimsUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
            	aimsUser.setUserId(aimsUserAlreadyExists.getUserId());
            	aimsUser.setUsername(aimsUserAlreadyExists.getUsername());
            	aimsUser.setUserSessionStatus(aimsUserAlreadyExists.getUserSessionStatus());
            	aimsUser.setUserType(aimsUserAlreadyExists.getUserType());*/
            	
            	
            	
            	
            	
            	//aimsUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
            	//aimsUser.setRoles(null);
            	//aimsUser.setAimsContact(null);
            	//aimsUser.setAimsVzwDepartment(null);
            }
	            aimsUser.setUsername(allianceRegistrationForm.getLoginId());
	            aimsUser.setPassword(StringFuncs.asHex(MiscUtils.generate128bitkey()));
	            aimsUser.setUserType(AimsConstants.ALLIANCE_USERTYPE);
	            aimsUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
	            aimsUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
	            aimsUser.setCreatedDate(new Date());
	            aimsUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
	            aimsUser.setLastUpdatedDate(new Date());
	            aimsUser.setRowsLength(new java.lang.Long(10L));
	            aimsUser.setNotificationType(AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE);
            
            /**** todo no need to create techUser, as it will not be used, only techContact will be created*********/
            AimsUser techUser = new AimsUser();
            techUser.setUsername(allianceRegistrationForm.getTechEmail());
            techUser.setPassword(StringFuncs.asHex(MiscUtils.generate128bitkey()));
            techUser.setUserType(AimsConstants.ALLIANCE_USERTYPE);
            techUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            techUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
            techUser.setCreatedDate(new Date());
            techUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            techUser.setLastUpdatedDate(new Date());
            techUser.setRowsLength(new java.lang.Long(10L));
            techUser.setNotificationType(AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE);
            /********** End tech user*********/

            AimsAllianc aimsAllianc = new AimsAllianc();
            aimsAllianc.setCompanyName(allianceRegistrationForm.getCompanyName().trim());
            aimsAllianc.setWebSiteUrl(allianceRegistrationForm.getWebSiteUrl());
            aimsAllianc.setStreetAddress1(allianceRegistrationForm.getCompanyAddress());
            aimsAllianc.setState(allianceRegistrationForm.getStateProvince());
            aimsAllianc.setZipCode(allianceRegistrationForm.getZipCode().toString());
            aimsAllianc.setCountry(allianceRegistrationForm.getCountry());
            aimsAllianc.setCompanyCountryOfOrigin(allianceRegistrationForm.getCountry());
            aimsAllianc.setCity(allianceRegistrationForm.getCity());
            aimsAllianc.setCompanyLegalName(allianceRegistrationForm.getCompanyName().trim());
            aimsAllianc.setAuthRepName(Utility.strTruncate(allianceRegistrationForm.getFirstName(), 49) + " " + Utility.strTruncate(allianceRegistrationForm.getLastName(),49));
            aimsAllianc.setYearCompanyFounded(allianceRegistrationForm.getYearFounded());
            
            //copy company name to mportal alliance name
            aimsAllianc.setMportalAllianceName(allianceRegistrationForm.getCompanyName().trim());

            aimsAllianc.setCompetetiveAdvantages(allianceRegistrationForm.getCompetitiveAdvantages());
            aimsAllianc.setPartnerBrand(allianceRegistrationForm.getPartner());
            aimsAllianc.setNumFullTimeEmp(AimsConstants.NON_NULLABLE_FIELD_NUMERIC);
            aimsAllianc.setStatus(AimsConstants.ALLIANCE_STATUS_ACCEPTED);
            aimsAllianc.setIsOnHold("N");
            aimsAllianc.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsAllianc.setCreatedDate(new Date());
            aimsAllianc.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsAllianc.setLastUpdatedDate(new Date());

            //aimsAllianc.setAimsCarriers((MiscUtils.convertCarriersArrayToHashSet(allianceRegistrationForm.getAlliancesWithOtherCarriers())));
            aimsAllianc.setAimsCarriers(null);
            //aimsAllianc.setOtherCarrierAlliances(allianceRegistrationForm.getAllianceWithOtherCarriers());
            aimsAllianc.setOtherCarrierAlliances(null);
            //aimsAllianc.setAimsFinancingOptions((MiscUtils.convertFinancingArrayToHashSet(allianceRegistrationForm.getFinancing())));
            aimsAllianc.setAimsFinancingOptions(null);
            //aimsAllianc.setAimsDevelopmentTechnologies(MiscUtils.convertDevelopmentTechnologiesArrayToHashSet(allianceRegistrationForm.getDevelopmentTechnologies()));
            aimsAllianc.setAimsDevelopmentTechnologies(null);
            //aimsAllianc.setAimsDevelopments(MiscUtils.convertDevelopmentsArrayToHashSet(allianceRegistrationForm.getDevelopments()));
            aimsAllianc.setAimsDevelopments(null);
            //aimsAllianc.setOutsourceDevPublisherName(allianceRegistrationForm.getOutsourceDevelopmentPublisherName());
            aimsAllianc.setOutsourceDevPublisherName(null);
            aimsAllianc.setEmployeesRange(allianceRegistrationForm.getEmployeesRange());
            
            aimsAllianc.setRemitAddress1(allianceRegistrationForm.getRemitAddress1());
            aimsAllianc.setRemitAddress2(allianceRegistrationForm.getRemitAddress2());
            aimsAllianc.setRemitCity(allianceRegistrationForm.getRemitCity());
            aimsAllianc.setRemitCountry(allianceRegistrationForm.getRemitCountry());
            aimsAllianc.setRemitPostalCode(allianceRegistrationForm.getRemitPostalCode());
            aimsAllianc.setRemitState(allianceRegistrationForm.getRemitState());
            aimsAllianc.setRemitTo(allianceRegistrationForm.getRemitTo());            

            AimsContact aimsContact = new AimsContact();
            aimsContact.setType("UC");
            
             
            aimsContact.setFirstName(Utility.strTruncate(allianceRegistrationForm.getFirstName(),50));
            aimsContact.setLastName(Utility.strTruncate(allianceRegistrationForm.getLastName(),50));
            //TODO :: Update contact fields later
            aimsContact.setTitle(Utility.strTruncate(allianceRegistrationForm.getTitle(),50));
            aimsContact.setEmailAddress(allianceRegistrationForm.getLoginId());
            aimsContact.setPhone(Utility.strTruncate(allianceRegistrationForm.getPhone(),50));
            aimsContact.setMobile(Utility.strTruncate(allianceRegistrationForm.getMobile(),50));
            aimsContact.setFax(Utility.strTruncate(allianceRegistrationForm.getFax(),50));
            aimsContact.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsContact.setCreatedDate(new Date());
            aimsContact.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsContact.setLastUpdatedDate(new Date());            
            

            AimsContact techContact = null;
            boolean sameUserTechContact = false;
            log.debug("username= "+allianceRegistrationForm.getLoginId());
            log.debug("tech contact email= "+allianceRegistrationForm.getTechEmail());
            //if user id and tech contact id are not same then create tech contact
            if (!allianceRegistrationForm.getLoginId().equalsIgnoreCase(allianceRegistrationForm.getTechEmail()))
            {
                techContact = new AimsContact();
                techContact.setType("UC");
                techContact.setEmailAddress(allianceRegistrationForm.getTechEmail());
                techContact.setFirstName(Utility.strTruncate(allianceRegistrationForm.getTechFirstName(),50));
                techContact.setLastName(Utility.strTruncate(allianceRegistrationForm.getTechLastName(),50));
                techContact.setTitle(Utility.strTruncate(allianceRegistrationForm.getTechTitle(),50));
                techContact.setPhone(Utility.strTruncate(allianceRegistrationForm.getTechPhone(),50));
                techContact.setMobile(Utility.strTruncate(allianceRegistrationForm.getTechMobile(),50));
                techContact.setFax(Utility.strTruncate(allianceRegistrationForm.getTechFax(),50));
                techContact.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                techContact.setCreatedDate(new Date());
                techContact.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                techContact.setLastUpdatedDate(new Date());
            }
            else
            {
                sameUserTechContact = true;
                log.debug("Username and TechContact Email are same, tech contact will not be created. username= "+allianceRegistrationForm.getLoginId());
            }

            try
            {
                
            	
            	/*Collection sysRoles = AimsSysRolesManager.getAllSysRoles();
        		Set roleSet = new HashSet();
        		Iterator it = sysRoles.iterator();
        		AimsUser newCreatedAimsUser = null;
        		while (it.hasNext()) {

        			AimsSysRole sysRole = (AimsSysRole) it.next();
        			if (sysRole.getVzdnMappingRoleID() != null
        					&& allianceRegistrationForm.getVzdnManagerRoles().contains(
        							sysRole.getVzdnMappingRoleID().toString()))
        				roleSet.add(sysRole);
        		}*/
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	AllianceRegistrationManager.saveOrUpdateAlliance(aimsUser, techUser, aimsAllianc, aimsContact, techContact, sameUserTechContact,
                                                                    allianceRegistrationForm.getCompanyLogoTempFileId(),
                                                                    allianceRegistrationForm.getCompanyPresentationTempFileId());

            	
            	OpenssoRestService.updateUserCompany(aimsUser, aimsAllianc);
            	

                //Raising an alert on successful registration - Alert for Alliance Users
                AimsEventLite aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ALLIANCE_REGISTERED_EVENT_FOR_ALLIANCE_ONLY);
                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAllianc.getAllianceId().toString());

                    //Here we are explicitly sending UserID of Alliance Admin ID (to the stored proc to be mailed).
                    //This is because it is a one-time alert and there is no need to first opt-in the Alliance Admin to this alert.
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS, aimsUser.getUserId().toString());

                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianc.getCompanyName().trim());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianc.getVendorId().toString());

                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimsContact.getFirstName() + " " + aimsContact.getLastName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }

                //Raising an alert on successful registration - Alert for Verizon Users
                aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ALLIANCE_REGISTERED_EVENT_FOR_VERIZON_ONLY);
                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAllianc.getAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());

                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianc.getCompanyName().trim());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianc.getVendorId().toString());

                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimsContact.getFirstName() + " " + aimsContact.getLastName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }                

            }
            catch (UniqueConstraintException uce)
            {
            	uce.printStackTrace();
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(uce.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }catch (HibernateException hbe)
            {
            	hbe.printStackTrace();
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError("error.alliance_registration.company_name.exists");
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }
            catch (Exception ex)
            {
            	ex.printStackTrace();
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError("error.generic.database");
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }

            //Set Temp File Ids to Zero
            allianceRegistrationForm.setCompanyPresentationTempFileId(new Long(0));
            allianceRegistrationForm.setCompanyLogoTempFileId(new Long(0));

            ActionMessages messages = new ActionMessages();
            ActionMessage message = new ActionMessage("message.alliance_registration.success.contracts");
            messages.add(ActionMessages.GLOBAL_MESSAGE, message);
            saveMessages(request, messages);

            //Automatically log in the user
            //return mapping.findForward(forward);
            log.debug("---------ALL SET . GOING TO LOGIN PAGE");
            AimsUser user = AimsAccountsManager.validateUser(allianceRegistrationForm.getLoginId());
            request.setAttribute("aimsUser",user);

            /********************
                no need to set contracts page in request, this page will be set in session after successfull registration (using SESSION_SHOW_PAGE_AFTER_LOGIN)
                request.setAttribute("sendToContractPage", "true");
            ********************/

            //after successfull registration, set contracts page in session for SESSION_SHOW_PAGE_AFTER_LOGIN
            //after registration, bulletins, login content will be shown and then SESSION_SHOW_PAGE_AFTER_LOGIN i.e., contracts page 
            log.debug("AllianceRegistrationInsertUpdateAction: setting "+ AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN+"= "+"/allianceClickThroughContracts.do");
            request.getSession().setAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN, (request.getContextPath()+"/allianceClickThroughContracts.do"));

            return new ActionForward("/login.do?userName=" + aimsUser.getUsername() + "&password=" + aimsUser.getPassword());
        }
        log.debug("end AllianceRegistrationInsertUpdateAction: forward= "+forward);
        return mapping.findForward(forward);
    }

}
