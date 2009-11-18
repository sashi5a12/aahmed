package com.netpace.aims.controller.accounts;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.model.core.AimsUserOffer;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.vzdn.AimsUserOfferManager;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Collection;

import net.sf.hibernate.HibernateException;

/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/accountsInvite"
 *                name="AccountInviteForm"
 *                scope="request"
 *                input="/accounts/accountsInvite.jsp"
 *				  validate="true"
 * @struts.action-forward name="inviteUser" path="/accounts/accountsInvite.jsp"
 * @struts.action-forward name="viewList" path="/accounts.do?task=view"
 * @author Sajjad Raza
 */
public class AccountInviteAction extends BaseAction {

    private static Logger log = Logger.getLogger(AccountInviteAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception  {

        String forward = "viewList";
        String task = request.getParameter("task");
        log.debug("==== start AccountInviteAction, task= "+task);

        AccountInviteForm accountInviteForm = (AccountInviteForm)form;

        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        AimsContact currUserContact = currUser.getAimsContact();
        Long currentUserAllianceId = currUser.getAimsAllianc();
        String currUserType = currUser.getUserType();
        String currUserEmail = currUserContact.getEmailAddress();
        String currUserFullName = currUserContact.getFirstName()+" "+currUserContact.getLastName();
        Date currDate = new Date();

        if(!StringFuncs.isNullOrEmpty(task)) {

            //if user doesnt have access for alliance users
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, AimsSecurityManager.INSERT))) {
                throw new AimsSecurityException();
            }

            if(task.equalsIgnoreCase("inviteUser")) {
                forward = "inviteUser";
            }
            else if(task.equalsIgnoreCase("edit")) {
                try {
                    if(this.validateOffer(accountInviteForm.getUserEmail(), currentUserAllianceId)) {
                        //create AimsUserOffer
                        AimsUserOffer aimsUserOffer = new AimsUserOffer();
                        aimsUserOffer.setOfferDate(currDate);
                        aimsUserOffer.setOfferTo(accountInviteForm.getUserEmail());
                        aimsUserOffer.setAllianceId(currentUserAllianceId);
                        aimsUserOffer.setStatus(AimsConstants.OFFER_ACTIVE);
                        aimsUserOffer.setOfferFromName(currUserFullName);
                        aimsUserOffer.setOfferFromEmail(currUserEmail);
                        AimsUserOfferManager.saveUserOffer(aimsUserOffer);

                        log.debug("AccountInviteAction: user= "+accountInviteForm.getUserEmail()+
                                " has been invited to join alliance, allianceId= "+currentUserAllianceId+", offerId= "+aimsUserOffer.getOfferId());
                        ActionMessages messages = new ActionMessages();
                        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.inviteUser.user.invited", accountInviteForm.getUserEmail()));
                        saveMessages(request, messages);
                        //todo change success message text

                        //post invitation tasks, go to account inviation screen
                        accountInviteForm.setUserEmail("");//reset email field
                        accountInviteForm.setTask("inviteUser");//reset task
                        forward = "inviteUser";
                    }
                    else {
                        log.debug("AccountInviteAction: user= "+accountInviteForm.getUserEmail()+" already invited for this alliance, allianceId= "+currentUserAllianceId);
                        ActionErrors errors = new ActionErrors();
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.inviteUser.duplicate.userEmail", accountInviteForm.getUserEmail()));
                        saveErrors(request, errors);
                        //todo change duplicate user error message text
                        return mapping.getInputForward();
                    }
                }
                catch(UniqueConstraintException uce) {
                   uce.printStackTrace();
                   throw new AimsException();
                   //todo error message for user, throw aims exception
                }
                catch(Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        log.debug("==== end AccountInviteAction, forward= "+forward);
        return mapping.findForward(forward);
    }

    /**
     * Returns false if offer is accepted or invited within given duration (30 days)
     * @param offerToUserEmail
     * @param allianceId
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    private boolean validateOffer(String offerToUserEmail, Long allianceId) throws HibernateException, Exception{
        boolean validated = false;
        int duration = 30;//todo load it from common env properties
        Collection userOffers = null;
        try {
            //show error if offer is accepted or invited within last 30 days
            userOffers = AimsUserOfferManager.getUserAcceptedOrInvitedOffers(offerToUserEmail, allianceId, duration);
            //if no active user offer found with in given duration for this alliance, then set validate = true
            if(userOffers==null) {
                validated = true;
            }
            else if(userOffers.size()==0) {
                validated = true;
            }
        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        return validated;
    }//end validateOffer
}
