package com.netpace.aims.controller.alliance;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.alliance.AllianceMusicInfoManager;
import com.netpace.aims.bo.alliance.AllianceRegistrationManager;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.alliance.AimsAllianceMusic;
import com.netpace.aims.model.alliance.AimsAllianceMusicProdType;
import com.netpace.aims.model.alliance.AimsMusicProductType;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.OpenssoRestService;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceMusicRegistrationUpdate"  
 *                name="AllianceMusicRegistrationForm" 
 *                scope="request"				  
 *                input="/alliance/allianceMusicRegistrationUpdate.jsp"   
 *				  validate="true"      
 * @struts.action-forward name="view" path="/alliance/allianceMusicRegistrationResult.jsp"
 * @struts.action-forward name="error" path="/alliance/allianceMusicRegistrationUpdate.jsp"
 * @struts.action-forward name="login" path="/login.do"
 * @author Adnan Makda.
 */
public class AllianceMusicRegistrationInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceMusicRegistrationInsertUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = request.getParameter("task");
        String forward = "view";
        System.out.println("\n\ntaskname: " + taskname);

        if (taskname.equalsIgnoreCase("create"))
        {
            AllianceMusicRegistrationForm allianceMusicRegistrationForm = (AllianceMusicRegistrationForm) form;

            AimsUser aimsUser = new AimsUser();
            aimsUser.setUsername(allianceMusicRegistrationForm.getEmail());
            aimsUser.setPassword(StringFuncs.asHex(MiscUtils.generate128bitkey()));
            aimsUser.setUserType(AimsConstants.ALLIANCE_USERTYPE);
            //Setting random Mother Maiden Name to cater for 'Forgot Password'.
            aimsUser.setMotherMaidenName(StringFuncs.asHex(MiscUtils.generate128bitkey()));
            aimsUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
            aimsUser.setCreatedDate(new Date());
            aimsUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsUser.setLastUpdatedDate(new Date());
            aimsUser.setRowsLength(new java.lang.Long(10L));
            aimsUser.setNotificationType(AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE);
            AimsAllianc aimsAllianc = new AimsAllianc();
            aimsAllianc.setCompanyName(allianceMusicRegistrationForm.getCompanyName());
            aimsAllianc.setWebSiteUrl(allianceMusicRegistrationForm.getWebSiteUrl());
            aimsAllianc.setNumFullTimeEmp(AimsConstants.NON_NULLABLE_FIELD_NUMERIC);
            aimsAllianc.setStatus(AimsConstants.ALLIANCE_STATUS_ACCEPTED);
            aimsAllianc.setIsOnHold("N");

            aimsUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsAllianc.setCreatedDate(new Date());
            aimsUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsAllianc.setLastUpdatedDate(new Date());

            AimsContact aimsContact = new AimsContact();
            aimsContact.setType("UC");
            aimsContact.setFirstName(allianceMusicRegistrationForm.getFirstName());
            aimsContact.setLastName(allianceMusicRegistrationForm.getLastName());
            aimsContact.setTitle(allianceMusicRegistrationForm.getTitle());
            aimsContact.setEmailAddress(allianceMusicRegistrationForm.getEmail());
            aimsContact.setPhone(allianceMusicRegistrationForm.getOfficePhone());
            aimsContact.setMobile(allianceMusicRegistrationForm.getMobilePhone());
            aimsContact.setFax(allianceMusicRegistrationForm.getFax());
            aimsUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsContact.setCreatedDate(new Date());
            aimsUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsContact.setLastUpdatedDate(new Date());

            AimsAllianceMusic aimsAllianceMusic = new AimsAllianceMusic();
            aimsAllianceMusic.setHaveRightsCleared(allianceMusicRegistrationForm.getHaveRightsCleared());
            aimsAllianceMusic.setHaveExclusiveRights(allianceMusicRegistrationForm.getHaveExclusiveRights());
            aimsAllianceMusic.setWhatIsExclusive(allianceMusicRegistrationForm.getWhatIsExclusive());
            aimsAllianceMusic.setContentThruAggregator(allianceMusicRegistrationForm.getContentThruAggregator());
            aimsAllianceMusic.setCurrentDistributionPartners(allianceMusicRegistrationForm.getCurrentDistributionPartners());
            aimsAllianceMusic.setAnnualRevenue(allianceMusicRegistrationForm.getAnnualRevenue());
            aimsAllianceMusic.setAdditionalInformation(allianceMusicRegistrationForm.getAdditionalInformation());
            aimsAllianceMusic.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsAllianceMusic.setCreatedDate(new Date());
            aimsAllianceMusic.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsAllianceMusic.setLastUpdatedDate(new Date());

            String[] productsSelected = allianceMusicRegistrationForm.getProductId();
            //String[] productTypeId = allianceMusicRegistrationForm.getProductTypeId();
            String[] productName = allianceMusicRegistrationForm.getProductName();
            String[] productSizeTotalCatalog = allianceMusicRegistrationForm.getSizeTotalCatalog();
            String[] productSizeMobileCatalog = allianceMusicRegistrationForm.getSizeMobileCatalog();
            String[] productIncomeNonMobile = allianceMusicRegistrationForm.getIncomeNonMobile();
            String[] productIncomeMobile = allianceMusicRegistrationForm.getIncomeMobile();
            String[] productTopSellingArtists = allianceMusicRegistrationForm.getTopSellingArtists();

            Set productTypeSet = new HashSet();
            AimsMusicProductType aimsMusicProductType = null;
            AimsAllianceMusicProdType aimsAllianceMusicProdType = null;

            for (Iterator iter = AllianceMusicInfoManager.getMusicProductTypes().iterator(); iter.hasNext();)
            {
                aimsMusicProductType = (AimsMusicProductType) iter.next();
                aimsAllianceMusicProdType = new AimsAllianceMusicProdType();
                aimsAllianceMusicProdType.setProductTypeId(aimsMusicProductType);
                aimsAllianceMusicProdType.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                aimsAllianceMusicProdType.setCreatedDate(new Date());
                aimsAllianceMusicProdType.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                aimsAllianceMusicProdType.setLastUpdatedDate(new Date());
                aimsAllianceMusicProdType.setIsSelected("N");

                for (int iIndex = 0; iIndex < productsSelected.length; iIndex++)
                {
                    if (aimsMusicProductType.getProductTypeId().toString().equals(productsSelected[iIndex]))
                    {
                        int loopProductId = ((Long) allianceMusicRegistrationForm.getProductMapper().get(productsSelected[iIndex])).intValue();
                        aimsAllianceMusicProdType.setSizeTotalCatalog(productSizeTotalCatalog[loopProductId]);
                        aimsAllianceMusicProdType.setSizeMobileCatalog(productSizeMobileCatalog[loopProductId]);
                        aimsAllianceMusicProdType.setIncomeNonMobile(productIncomeNonMobile[loopProductId]);
                        aimsAllianceMusicProdType.setIncomeMobile(productIncomeMobile[loopProductId]);
                        aimsAllianceMusicProdType.setTopSellingArtists(productTopSellingArtists[loopProductId]);
                        aimsAllianceMusicProdType.setIsSelected("Y");
                    }
                }
                productTypeSet.add(aimsAllianceMusicProdType);
            }

            /*
            for (int iIndex = 0; iIndex < productsSelected.length; iIndex++)
            {
                int loopProductId = ((Long) allianceMusicRegistrationForm.getProductMapper().get(productsSelected[iIndex])).intValue();
            
                AimsAllianceMusicProdType aimsAllianceMusicProdType = new AimsAllianceMusicProdType();
            
                aimsAllianceMusicProdType.setProductTypeId(
                    (AimsMusicProductType) DBHelper.getInstance().load(AimsMusicProductType.class, productTypeId[loopProductId]));
                aimsAllianceMusicProdType.setSizeTotalCatalog(productSizeTotalCatalog[loopProductId]);
                aimsAllianceMusicProdType.setSizeMobileCatalog(productSizeMobileCatalog[loopProductId]);
                aimsAllianceMusicProdType.setIncomeNonMobile(productIncomeNonMobile[loopProductId]);
                aimsAllianceMusicProdType.setIncomeMobile(productIncomeMobile[loopProductId]);
                aimsAllianceMusicProdType.setTopSellingArtists(productTopSellingArtists[loopProductId]);
                aimsAllianceMusicProdType.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                aimsAllianceMusicProdType.setCreatedDate(new Date());
                aimsAllianceMusicProdType.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                aimsAllianceMusicProdType.setLastUpdatedDate(new Date());
                productTypeSet.add(aimsAllianceMusicProdType);
            }
            */

            //aimsAllianceMusic.setMusicProductTypeMap(productTypeSet);

            //registeredUserMessage will move to Notification UI.
            /*
            StringBuffer registeredUserMessage = new StringBuffer("");
            registeredUserMessage
                .append("Thank you for your interest in working with Verizon Wireless ")
                .append("Music as a potential content provider. We will review your submission, ")
                .append("and we may contact you to confirm that we understand your offering and ")
                .append("possibly collect additional information. However, due to the volume of ")
                .append("submissions we are not able to guarantee follow up with everyone but we ")
                .append("will keep all applications on file for future consideration. Please also ")
                .append("feel free to update us on any new developments in your catalogue or new ")
                .append("product offerings by submitting a revised form.");
            */

            StringBuffer verizonUserMessage = new StringBuffer("");
            verizonUserMessage.append("A User has registered, and wants to be a Content Provider. He has provided the following information:\n");
            verizonUserMessage.append("\nFirm Name: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getCompanyName());
            verizonUserMessage.append("\nFirst Name: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getFirstName());
            verizonUserMessage.append("\nLast Name: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getLastName());
            verizonUserMessage.append("\nEmail Address: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getEmail());
            verizonUserMessage.append("\nMobile Phone Number: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getMobilePhone());
            verizonUserMessage.append("\nOffice Phone Number: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getOfficePhone());

            verizonUserMessage.append("\n\n\nFollowing are the products that the user is interested in supplying the content for: ");

            for (int iIndex = 0; iIndex < productsSelected.length; iIndex++)
            {
                int loopProductId = ((Long) allianceMusicRegistrationForm.getProductMapper().get(productsSelected[iIndex])).intValue();

                verizonUserMessage.append("\n\nProduct Type: ");
                verizonUserMessage.append(productName[loopProductId]);

                verizonUserMessage.append("\nSize of Total Catalog: ");
                verizonUserMessage.append(productSizeTotalCatalog[loopProductId]);

                verizonUserMessage.append("\nSize of Catalog proposed for Mobile: ");
                verizonUserMessage.append(productSizeMobileCatalog[loopProductId]);

                verizonUserMessage.append("\nAnnual Non-Mobile Income for Catalog (Most Recent Year): ");
                verizonUserMessage.append(productIncomeNonMobile[loopProductId]);

                verizonUserMessage.append("\nAnnual Mobile Income for Catalog (Most Recent Year): ");
                verizonUserMessage.append(productIncomeMobile[loopProductId]);

                verizonUserMessage.append("\nTop Selling Artists: ");
                verizonUserMessage.append(productTopSellingArtists[loopProductId]);
            }

            verizonUserMessage.append("\n\n\nDo you currently have rights fully cleared and documented for all content you propose for mobile: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getHaveRightsCleared());
            verizonUserMessage.append("\n\nAre you currently delivering your content to us through a content provider / aggregator: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getContentThruAggregator());
            verizonUserMessage.append("\n\nAnnual Revenue for the Content Sales Proposed: ");
            verizonUserMessage.append(allianceMusicRegistrationForm.getAnnualRevenue());
            verizonUserMessage.append("\n\nWho are your current digital distribution partners (mobile or otherwise):\n");
            verizonUserMessage.append(allianceMusicRegistrationForm.getCurrentDistributionPartners());
            verizonUserMessage.append("\n\n\nAdditional Information:\n");
            verizonUserMessage.append(allianceMusicRegistrationForm.getAdditionalInformation());
            verizonUserMessage.append("\n\n\n\n\nFrom,\nVerizon Wireless Developer Relations.");

            System.out.println(verizonUserMessage.toString());

            try
            {
                AllianceRegistrationManager.saveOrUpdateMusicAlliance(aimsUser, aimsAllianc, aimsContact, aimsAllianceMusic, productTypeSet);

                OpenssoRestService.updateUserCompany(aimsUser, aimsAllianc);
            	
                
                //Raising an alert on successful registration - Alert for Alliance Users
                AimsEventLite aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MUSIC_ALLIANCE_REGISTERED_EVENT_FOR_ALLIANCE_ONLY);
                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAllianc.getAllianceId().toString());

                    //Here we are explicitly sending UserID of Alliance Admin ID (to the stored proc to be mailed). 
                    //This is because it is a one-time alert and there is no need to first opt-in the Alliance Admin to this alert.
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS, aimsUser.getUserId().toString());
                    
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianc.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianc.getVendorId().toString());

                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimsContact.getFirstName() + " " + aimsContact.getLastName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }

                //Raising an alert on successful registration - Alert for Verizon Users
                aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MUSIC_ALLIANCE_REGISTERED_EVENT_FOR_VERIZON_ONLY);
                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAllianc.getAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianc.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_WEBSITE, aimsAllianc.getWebSiteUrl());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianc.getVendorId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_PHONE, aimsContact.getMobile());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_EMAIL, aimsUser.getUsername());

                    //aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MUSIC_ALLIANCE_REGISTRATION_INFO, verizonUserMessage.toString());

                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimsContact.getFirstName() + " " + aimsContact.getLastName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }
            }
            catch (UniqueConstraintException uce)
            {
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(uce.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }
            catch (Exception ex)
            {
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError("error.generic.database");
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }

            ActionMessages messages = new ActionMessages();
            ActionMessage message = new ActionMessage("message.alliance_music_registration.success");
            messages.add(ActionMessages.GLOBAL_MESSAGE, message);
            saveMessages(request, messages);
            log.debug("fetching user : "+allianceMusicRegistrationForm.getEmail()+" after music registration ");
            request.setAttribute("aimsUser",AimsAccountsManager.validateUser(allianceMusicRegistrationForm.getEmail()));
            //return mapping.findForward("login");
            RequestDispatcher ds = request.getRequestDispatcher("/login.do");
    		ds.forward(request, response);
    		
            
        }

        return mapping.findForward(forward);
    }
}
