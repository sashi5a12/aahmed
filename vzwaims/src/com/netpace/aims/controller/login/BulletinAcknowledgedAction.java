package com.netpace.aims.controller.login;

import com.netpace.aims.bo.contracts.ContractOfferManager;
import com.netpace.aims.bo.security.AimsBulletinManager;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.alliance.AimsBulletin;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.AimsPrivilegesConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Vector;

/**
 * This class takes user to his logged in home page.
 *
 * @struts.action path="/bulletinAcknowledged"
 *                scope="request"
 *                input="/"
 * @struts.action-forward name="success" path="/userHome.do"
 * @author Shahnawaz Bagdadi
 */
public class BulletinAcknowledgedAction extends Action
{

    static Logger log = Logger.getLogger(BulletinAcknowledgedAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if (request.getSession().getAttribute(AimsConstants.SESSION_BULLETIN_TO_READ) != null)
        {
        	AimsUser user=(AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
            Long userId = user.getUserId();
            String userName=user.getUsername();
            Long session_bulletinId = (Long) request.getSession().getAttribute(AimsConstants.SESSION_BULLETIN_TO_READ);//session bulletin id
            Long request_bulletinId = null;//request bulletin id
            Long allianceid = user.getAimsAllianc();
            List bulletinIds = null;//next bulletin ids to show
            AimsBulletin bulletin =  null;
            Long bulletinType = null;
            boolean acknowledgmentProcessed = false;

            //if bulletinId is passed in request
            if(!(StringFuncs.isNullOrEmpty(request.getParameter("bulletinId"))) )
            {
                request_bulletinId = new Long(request.getParameter("bulletinId"));

                //match bulletin id of session and bulletin id given in request, if both are same then start processing
                if(session_bulletinId.longValue() == request_bulletinId.longValue())
                {
                    bulletin =  (AimsBulletin) DBHelper.getInstance().load(AimsBulletin.class, request_bulletinId.toString());
                    if(bulletin != null)
                    {
                        bulletinType = bulletin.getBulletinType();
                        //check if bulletin is of Brew Type then update counter
                        if( (StringFuncs.NullValueReplacement(bulletinType)).equals(AimsConstants.BULLETIN_TYPE_BREW))
                        {
                            log.debug("BulletinAcknowledgedAction: Brew Bulletin Acknowledged, updating counter..");
                            AimsBulletinManager.updateBulletinCounter(userId, request_bulletinId);
                            acknowledgmentProcessed = true;
                            log.debug("BulletinAcknowledgedAction: Brew Bulletin Acknowledged, counter updated");
                        }
                        else if( (StringFuncs.NullValueReplacement(bulletinType)).equals(AimsConstants.BULLETIN_TYPE_ENTERPRISE))
                        {
                            //check if bulletin is of Enterprise Type then Accept it
                            String contract_id = ConfigEnvProperties.getInstance().getProperty("enterprise.bulletin.contract.jma3.id");
                            AimsContact userContact = null;
                            try
                            {
                                log.debug("BulletinAcknowledgedAction: Enterprise Bulletin for JMA3 Acknowledged, accepting contract..");
                                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_CONTRACTS, AimsSecurityManager.UPDATE))
                                {   //if user has access to change contracts
                                    userContact = ((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getAimsContact();
                                    if(!StringFuncs.isNullOrEmpty(contract_id))
                                    {
                                        ContractOfferManager.editContractAmendmentStatus(
                                                                        new Long(contract_id),
                                                                        allianceid,
                                                                        new Long(contract_id),
                                                                        "C",
                                                                        "A",
                                                                        userContact.getFirstName(),
                                                                        userContact.getTitle(),
                                                                        null,
                                                                        userId,
                                                                        userName
                                                                      );
                                        log.debug("BulletinAcknowledgedAction: Enterprise Bulletin for JMA3 Acknowledged, Contract accepted.");
                                        acknowledgmentProcessed = true;
                                    }
                                }
                                else
                                {
                                    log.debug("BulletinAcknowledgedAction: Error changing contract status: user does not have access to change contract");
                                    acknowledgmentProcessed = false;
                                }
                            }
                            catch(Exception ex)
                            {
                                System.out.println("BulletinAcknowledgedAction: Error occured while changing contract status.");
                                ex.printStackTrace();
                            }
                        }//end if enterprise

                        //if acknowledgment processed successfully, then set next bulletin
                        if(acknowledgmentProcessed)
                        {
                            request.getSession().removeAttribute(AimsConstants.SESSION_BULLETIN_TO_READ);

                            bulletinIds = (Vector)request.getSession().getAttribute(AimsConstants.SESSION_BULLETIN_IDS_TO_READ);
                            AimsBulletinManager.setNextBulletinDetails(request, bulletinIds);
                            request.setAttribute("lastBulletinAcknowledged", new Boolean(true));

                            //if there is any success message configured for bulletin then show success message
                            if(!StringFuncs.isNullOrEmpty(bulletin.getBulletinSuccessMessage()))
                            {
                                request.setAttribute(AimsConstants.REQUEST_SHOW_BULLETIN_SUCCESS, new Boolean(true));
                                request.setAttribute(AimsConstants.REQUEST_BULLETIN_SUCCESS_MESSAGE, bulletin.getBulletinSuccessMessage());
                            }
                        }//end if acknowledgmentProcessed
                    }
                }//end if session_bulletinId === request_bulletinId
                else
                {
                    log.debug("BulletinAcknowledgedAction: Bulletin id stored in session is not matched with bulletinId in url");
                }
            }//end if bulletinId
        }
        return mapping.findForward("success");
    }
}
