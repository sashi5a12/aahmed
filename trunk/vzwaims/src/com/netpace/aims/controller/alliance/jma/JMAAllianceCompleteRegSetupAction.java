package com.netpace.aims.controller.alliance.jma;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceRegistrationManager;
import com.netpace.aims.bo.alliance.JMAAllianceRegistrationManager;
import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.AllianceCompInfoEditAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsJMAAlliance;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.StringFuncs;

/**
 * @struts.action path="/jmaAllianceCompleteRegistration"
 * 				  name="JMAAllianceCompleteRegistrationForm"
 * 				  scope="request"
 * 				  validate="false"
 * @struts.action-forward name="createUpdate" path="/alliance/jma/jmaAllianceCompleteRegistration.jsp"
 * * @struts.action-forward name="vzwView" path="/alliance/jma/jmaAllianceInfoView.jsp"
 * @struts.action-forward name="skipRegistration" path="/common/logged-in.jsp"				
 * @author aqureshi
 *
 */
public class JMAAllianceCompleteRegSetupAction extends BaseAction {
	
	 static Logger log = Logger.getLogger(JMAAllianceCompleteRegSetupAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		JMAAllianceCompleteRegistrationForm completeInfoForm=(JMAAllianceCompleteRegistrationForm) form;
		HttpSession session = request.getSession();
		String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
		Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
		Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
		Long[] indFocus=null;
		Long[] indTopFocus=null;
		Long[] carriers=null;
		AimsAllianc aimsAllianceOfApplication=null;
		boolean skipCompleteRegistration = false;
		String task=request.getParameter("task");
		String forword="createUpdate";
		
		try{
			if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
				
				String allianceId=request.getParameter("alliance_id");
				log.debug("alliance_id :"+allianceId);
				aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, StringFuncs.NullValueReplacement((allianceId)));
				log.debug("aimsAllianceOfApplication: "+aimsAllianceOfApplication);
				try{
					currentUserAllianceId=new Long(allianceId);
				}catch(NumberFormatException nfex)
				{
					log.debug("Exception occure while casting alliance id to long", nfex);
				}
				
				//Code for displaying accept/reject button
				String status=aimsAllianceOfApplication.getStatus();
				if ((status!=null) && (status.equalsIgnoreCase("S") || status.equalsIgnoreCase("R")) )
		        {
					completeInfoForm.setShowAcceptRejectButton("Y");
		        }
		        else
		        {
		        	completeInfoForm.setShowAcceptRejectButton("N");
		        }
				forword="vzwView";
			}
			else if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE) &&  Utility.ZeroValueReplacement(currentUserAllianceId).longValue()>0 ) {
				//current user is alliance and has alliance id
				try{
					aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, currentUserAllianceId.toString());
					if(aimsAllianceOfApplication!=null ) {
						if(StringFuncs.NullValueReplacement(aimsAllianceOfApplication.getIsJMAAlliance()).equals("Y")) {
							//if user is jma alliance and has completed registration then skip registration process
							if(StringFuncs.NullValueReplacement(aimsAllianceOfApplication.getIsJmaInfoComplete()).equals("Y")) {
								log.debug("JMAAllianceCompleteRegSetupAction: alliance is jma and has completed registration");
								//if task!=rditForm then skip
								if(!StringFuncs.NullValueReplacement(task).equals("editForm"))
								{
									log.debug("JMAAllianceCompleteRegSetupAction: alliance is jma and has completed registration and task is not editForm, skip complete registration process");
									log.debug("JMAAllianceCompleteRegSetupAction: redirecting to user home... ");
									skipCompleteRegistration = true;
								}
							}
						}
						else {
							//if user is not jma alliance then skip registration process
							skipCompleteRegistration = true;
						}						
					}
					if(skipCompleteRegistration) {
						//session.removeAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW);
						return mapping.findForward("skipRegistration");
					}					
				}catch(Exception ex)
				{
					System.out.println("Exception occured while loading alliace");
					ex.printStackTrace();
				}
			}
			/**
			 * Loading data for form bean
			 */
			completeInfoForm.setJmaAllianceId(currentUserAllianceId);
			completeInfoForm.setStatus(aimsAllianceOfApplication.getStatus());
			if(StringFuncs.NullValueReplacement(aimsAllianceOfApplication.getIsJmaInfoComplete()).equals("Y")){
				log.debug("JMA information completed by user");
				completeInfoForm.setIsJmaInfoComplete("Y");
			}
			else{
				log.debug("JMA information is not completed by user");
				completeInfoForm.setIsJmaInfoComplete("N");
			}
				
			AimsJMAAlliance jmaAlliance=JMAAllianceRegistrationManager.getJMAAllianceById(currentUserAllianceId);
			if(jmaAlliance!=null){
				this.setFormAttributes(jmaAlliance, completeInfoForm);
			}
			indFocus=JMAAllianceRegistrationManager.getAllianceIndFocus(currentUserAllianceId);
			indTopFocus=JMAAllianceRegistrationManager.getAllianceTopIndFocus(currentUserAllianceId);
			carriers=JMAAllianceRegistrationManager.getAllianceCarreirs(currentUserAllianceId);
			if(indFocus!=null)
				completeInfoForm.setAllianceIndustryVerticals(indFocus);
			if(indTopFocus!=null)
				completeInfoForm.setAllianceTopIndustryVerticals(indTopFocus);
			if(carriers!=null)
				completeInfoForm.setAlliancesWithOtherCarriers(carriers);
			if(aimsAllianceOfApplication!=null)
			{
				completeInfoForm.setAllianceWithOtherCarriers(aimsAllianceOfApplication.getOtherCarrierAlliances());
				
				AimsContact contact=null;
				//if Technical user exist  
				if(aimsAllianceOfApplication.getTechContact()!=null){
					contact=VZAppZoneApplicationManager.getAimsContactById(aimsAllianceOfApplication.getTechContact());
					log.debug(contact);
					if(contact!=null)
					{
						completeInfoForm.setTechEmail(contact.getEmailAddress());
						completeInfoForm.setTechPassword("");
						completeInfoForm.setTechFirstName(contact.getFirstName());
						completeInfoForm.setTechLastName(contact.getLastName());
						completeInfoForm.setTechPhone(contact.getPhone());
						completeInfoForm.setTechTitle(contact.getTitle());
						completeInfoForm.setTechPhone(contact.getPhone());
						completeInfoForm.setTechMobile(contact.getMobile());
						completeInfoForm.setTechFax(contact.getFax());
					}
					completeInfoForm.setIsTechincalInfo("Y");
				} 
				else{
					completeInfoForm.setIsTechincalInfo("N");
				}
				
				//Set Sales user
				if(aimsAllianceOfApplication.getAimsContactBySalesContactId()!=null){
					contact=null;
					contact=VZAppZoneApplicationManager.getAimsContactById(aimsAllianceOfApplication.getAimsContactBySalesContactId());
					log.debug(contact);
					if(contact!=null)
					{
						completeInfoForm.setSalesEmail(contact.getEmailAddress());
						completeInfoForm.setSalesPassword("");
						completeInfoForm.setSalesFirstName(contact.getFirstName());
						completeInfoForm.setSalesLastName(contact.getLastName());
						completeInfoForm.setSalesPhone(contact.getPhone());
						completeInfoForm.setSalesTitle(contact.getTitle());
						completeInfoForm.setSalesPhone(contact.getPhone());
						completeInfoForm.setSalesMobile(contact.getMobile());
						completeInfoForm.setSalesFax(contact.getFax());
					}
					completeInfoForm.setIsSalesSupport("Y");
				} 
				else{
					completeInfoForm.setIsSalesSupport("N");
				}
			}

		}catch(Exception e)
		{
			log.error("JMAAllianceCompleteRegSetupAction: error occure.");
			e.printStackTrace();
		}
		
		return mapping.findForward(forword);
	}
	
	private void setFormAttributes(AimsJMAAlliance jmaAlliance,JMAAllianceCompleteRegistrationForm completeInfoForm)
	{
		completeInfoForm.setShortDescription(jmaAlliance.getCompanyShortDescription());
		completeInfoForm.setFaq(jmaAlliance.getFaq());
		completeInfoForm.setReasonOfReleation(jmaAlliance.getReasonForRelationShip());
		completeInfoForm.setContractAgreements(jmaAlliance.getListExistingContract());	
		completeInfoForm.setIsOptionToGoWithVZW(jmaAlliance.getIsOptionToGoVZW());
		completeInfoForm.setOptionToGoWithVZW(jmaAlliance.getOptionToGoVzw());		
		completeInfoForm.setIsSalesEngaugementWithVZW(jmaAlliance.getIsSalesEngagements());
		completeInfoForm.setSalesEngaugementWithVZW(jmaAlliance.getSalesEngagements());	
		completeInfoForm.setProductMenu(jmaAlliance.getProductMenu());
		completeInfoForm.setIsProductUseVzwVzNt(jmaAlliance.getIsAnyProductuseVzwVzNt());
		completeInfoForm.setIsProductCertifiedVZW(jmaAlliance.getIsProductCertifiedVzw());
		completeInfoForm.setIsProductCertifiedODI(jmaAlliance.getIsProcessODI());
		completeInfoForm.setBriefDescription(jmaAlliance.getBriefDescription());
		completeInfoForm.setProductInformation(jmaAlliance.getProducrInfo());
		completeInfoForm.setSolutionReside(jmaAlliance.getSolutionReside());
		completeInfoForm.setIsProductRequiredLBS(jmaAlliance.getIsRequiredLBS());
		completeInfoForm.setIsOfferBOBOServices(jmaAlliance.getIsOfferBOBO());		
		completeInfoForm.setProductPresentationFileName(jmaAlliance.getPresentationFileName());
		completeInfoForm.setProductPresentationContnetType(jmaAlliance.getPresentationContentType());
		completeInfoForm.setProductPresentationTempFileId(new Long(0));
		completeInfoForm.setWinOpportunityFileName(jmaAlliance.getWinOpportunitiesFileName());
		completeInfoForm.setWinOpportunityContnetType(jmaAlliance.getWinOpportunitiesContentType());
		completeInfoForm.setWinOpportunityTempFileId(new Long(0));
		
		
	}
	

}
