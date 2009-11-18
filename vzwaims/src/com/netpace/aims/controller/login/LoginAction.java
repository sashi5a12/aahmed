package com.netpace.aims.controller.login;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.management.relation.RoleList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.netpace.aims.bo.roles.AimsSysRolesManager;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.security.AimsBulletinManager;
import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.logincontent.AimsLoginContentManager;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsSessionListener;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.OpenssoRestService;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.CommonProperties;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/login"
 *                name="LoginForm"
 *                scope="request"
 *                input="/public/login.jsp"
 *                validate="false"
 * @struts.action-forward name="success" path="/userHome.do"
 * @struts.action-forward name="prov_apps" path="/applicationSearchProvisioned.do?task=search" redirect="true"
 * @struts.action-exception key="error.login.InvalidUser" type="com.netpace.aims.bo.security.InvalidLoginException"
 * @struts.action-forward name="allianceContracts" path="/allianceContracts.do?task=view"
 * @author Shahnawaz Bagdadi
 * @see com.netpace.aims.bo.security.InvalidLoginException
 */
public class LoginAction extends Action
{

    static Logger log = Logger.getLogger(LoginAction.class.getName());

    private static String SESSION_LISTENER = "SESSION_LISTENER";

    /**
     * This method validates the user.
     * It calls the Security manager with the user name and password and gets a
     * User object if the user is valid. It then moves the user to his logged in page.
     *
     * If the username and password does not match it throws
     * a InvalidUserException.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        log.debug("---in Login action ...");
    	AimsUser user = (AimsUser)request.getAttribute("aimsUser");
        //VzdnCredentials vzdnCredentials = (VzdnCredentials) request.getAttribute("vzdnCredentials");
        //String vzdnRequestPath=vzdnCredentials.getRequestURI();
        
    	log.debug("---aimsuser parsed ...");
        //vzdnCredentials
        //String csvVzdnRoles=(String)request.getAttribute("csvVzdnRoles");;
        //String csvVzdnRoles="";
        //TODO :: check in the case of user null SSO 
    	//DynaValidatorForm lf = (DynaValidatorForm) form;
        MessageResources appResource = this.getResources(request);
        HttpSession session = request.getSession();
        String strPath = null;
        ActionForward loginContentforward = null;

        session.setAttribute(AimsConstants.FILE_SIZE ,appResource.getMessage("file.sizelimit"));
        session.setAttribute(AimsConstants.IMAGE_FILE_SIZE ,appResource.getMessage("file.image.sizelimit"));
        session.setAttribute(AimsConstants.DOC_FILE_SIZE ,appResource.getMessage("file.doc.sizelimit"));
        session.setAttribute(AimsConstants.EPS_FILE_SIZE ,appResource.getMessage("file.eps.sizelimit"));
        session.setAttribute(AimsConstants.JPEG_FILE_SIZE ,appResource.getMessage("file.jpeg.sizelimit"));
        session.setAttribute(AimsConstants.FLASH_FILE_SIZE ,appResource.getMessage("file.flash.sizelimit"));
        session.setAttribute(AimsConstants.FLASH_DEMO_FILE_SIZE ,appResource.getMessage("file.flashdemo.sizelimit"));
        session.setAttribute(AimsConstants.PRESENTATION_FILE_SIZE ,appResource.getMessage("file.presentation.sizelimit"));
        session.setAttribute(AimsConstants.SCREEN_SHOT_FILE_SIZE ,appResource.getMessage("file.screenShot.sizelimit"));
        session.setAttribute(AimsConstants.FILE_MSG,appResource.getMessage("message.filesize.note"));
        session.setAttribute(AimsConstants.STATUS_MSG,appResource.getMessage("message.request.status"));

        log.debug("Doc file size :: " + appResource.getMessage("file.doc.sizelimit"));

        //String username = (String) lf.get("userName");
        //String password = (String) lf.get("password");

        //log.debug("User : " + lf.get("userName") + " Passwd : " + lf.get("password"));

        //AimsUser user = AimsSecurityManager.validateUser(username, password);
        //request.getSession().setAttribute(AimsConstants.AIMS_USER, user.getAimsContact().getFirstName() + " " + user.getAimsContact().getLastName());
        
        //  VZDN Update code start 
     
         
        log.debug("---before setting in action ");
        request.getSession().setAttribute(AimsConstants.AIMS_USER, user);
        request.getSession().setAttribute(AimsConstants.SESSION_FIRST_NAME,user.getAimsContact().getFirstName());
        request.getSession().setAttribute(AimsConstants.SESSION_LAST_NAME,user.getAimsContact().getLastName());
        request.getSession().setAttribute(AimsConstants.COOKIE_EMAIL_ADDRESS,user.getUsername());
        request.getSession().removeAttribute(AimsConstants.GLOBAL_NAV_HAEDER);
        
        this.checkGTMCompanyID(user, request);
     
        
        
        log.debug("---before setting user id   ");
        log.debug("---might be user is null :   "+user.getUserId());
        
        AimsSecurityManager.setLastLoginDate(user.getUserId());
        // Bulletin ID to view for Alliances
        
        log.debug("---login filter user.getUserType() : "+user.getUserType());
        if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user.getUserType()))
        {
        	log.debug("---check passed ");
            String[] bulletinIdsArr = null;
            String bullitenIdsStr = "";
            List bulletinIds = new Vector();

            String[] loginContentIdsArr = null;
            String loginContentIdsStr = "";
            List loginContentIds = new Vector();
            boolean bulletinFound = false;

            bullitenIdsStr = AimsBulletinManager.getUnreadBulletinIdsStr(user.getUserId());
            if(!StringFuncs.isNullOrEmpty(bullitenIdsStr))
            {
                bulletinIdsArr = StringFuncs.tokenize(bullitenIdsStr, ",");//convert comma separated bulletin ids to array
                if(bulletinIdsArr!=null && bulletinIdsArr.length>0)
                {
                    //make bulletinIds List
                    for(int bulletinIdsArrIndex=0; bulletinIdsArrIndex<bulletinIdsArr.length; bulletinIdsArrIndex++)
                    {
                        bulletinIds.add(new Long(bulletinIdsArr[bulletinIdsArrIndex]));
                    }
                    AimsBulletinManager.setNextBulletinDetails(request, bulletinIds);
                    bulletinFound = true;
                }
            }//end set bulletin id
            log.debug("---check passed 2");
            if(AimsUtils.isAllianceAdmin(user))
            {	log.debug("---check passed 3");
                CommonProperties comProperties = CommonProperties.getInstance();
                String contactUpdateDuration = comProperties.getProperty("loginContent.alliance.contactUpdate.duration");
                if(!StringFuncs.isNullOrEmpty(contactUpdateDuration) && StringUtils.isNumeric(contactUpdateDuration))
                {
                    //from database check user in show content table
                    loginContentIdsStr = AimsLoginContentManager.getLoginContentIdsToShow(user.getUserId(), user.getAimsAllianc(), Integer.parseInt(contactUpdateDuration));
                    if(!StringFuncs.isNullOrEmpty(loginContentIdsStr))
                    {	log.debug("---check passed 4");
                        log.debug("login content to show: "+loginContentIdsStr);
                        loginContentIdsArr = StringFuncs.tokenize(loginContentIdsStr, ",");
                        //make loginContentIds List
                        for(int loginContentArrIndex=0; loginContentArrIndex<loginContentIdsArr.length; loginContentArrIndex++)
                        {
                            loginContentIds.add(new Long(loginContentIdsArr[loginContentArrIndex]));
                        }
                        //show content code
                        loginContentforward = AimsLoginContentManager.setNextLoginContent(request, loginContentIds);
                        if(bulletinFound)
                        {
                            loginContentforward = null;
                        }

                    }
                }
            }
            //end show content code
        }
        log.debug("---check passed 5");
        // add the session listener
        request.getSession().setAttribute(SESSION_LISTENER, new AimsSessionListener());
        AimsSecurityManager.createMenu(request.getSession());
        log.debug("---check passed 6");
        //check and set pop-up functionality
        javax.servlet.ServletContext scontext = servlet.getServletContext();
        if (AimsConstants.VZW_USERTYPE.equalsIgnoreCase(user.getUserType()))
        {
            strPath = scontext.getRealPath(AimsConstants.POPUP_VZW_LOCATION);
        }
        else if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user.getUserType()))
        {
            strPath = scontext.getRealPath(AimsConstants.POPUP_ALLIANCE_LOCATION);
        }
        log.debug("*************** strPath **************** :" + strPath);
        if (strPath != null)
        {
            try
            {
                java.io.FileReader fhandle = new java.io.FileReader(strPath);
                fhandle.close();
                request.setAttribute(AimsConstants.SHOW_POPUP, AimsConstants.SHOW_POPUP_ENABLE);
            }
            catch (Exception ex)
            {
                log.debug("*** File is not available for pop-up");
            }
        }
        // check and set pop-up functionality ends

        // If the user has only one role (Provisioned Apps) then redirect him directly to
        // Provisioned Apps page

        if ("Y".equalsIgnoreCase((String) request.getSession().getAttribute(AimsConstants.AIMS_ONLY_PROV_APPS)))
        {
            return mapping.findForward("prov_apps");
        }

        String redirect_url = request.getParameter("forward");
        if (redirect_url != null)
        {
            ActionForward forward = mapping.findForward(redirect_url);
            ActionForward loadForward = new ActionForward();
            PropertyUtils.copyProperties(loadForward, forward);

            if (forward != null)
            {
                String params = request.getParameter("params");
                if (params != null)
                {
                    String param_path = "";
                    StringTokenizer st = new StringTokenizer(params, ",");
                    while (st.hasMoreTokens())
                    {
                        String param = st.nextToken();
                        StringTokenizer stp = new StringTokenizer(param, ":");
                        String name = stp.nextToken();
                        String value = stp.nextToken();
                        param_path = param_path + "&" + name + "=" + value;
                    }
                    loadForward.setPath(forward.getPath() + param_path);
                    System.out.println("Redirect URL : " + loadForward.getPath());
                }
                return loadForward;
            }
        }

        /*
            no need to set contracts page in request, this page will be set in session after successfull registration (using SESSION_SHOW_PAGE_AFTER_LOGIN)
            if(request.getAttribute("sendToContractPage")!=null){
                return mapping.findForward("allianceContracts");
            }
        */

        return loginContentforward==null ? mapping.findForward("success") : loginContentforward;
    }

    	private void checkGTMCompanyID(AimsUser aimsUser, HttpServletRequest request){
    		String gtmCompanyId = request.getHeader("gtm_company_id");
    		ConfigEnvProperties props = ConfigEnvProperties.getInstance();
    		try{
    			String gtm_company_id_attr = props.getProperty("dev.organization.id"); 
    			gtmCompanyId=OpenssoRestService.readAttribute(aimsUser.getUsername(), gtm_company_id_attr);
    		}
    		catch (Exception e){
    			log.error(e,e);
    			gtmCompanyId = request.getHeader("gtm_company_id");
    			try {
					MailUtils.sendMail(
					        AimsConstants.EMAIL_EXCEPTION_ADMIN,
					        "exceptions@netpace.com", 
					        "Error occured when making OpenssoRestAPI call to get gtm_company_id. Switched to alternate approach getting value from request.",
					        null, 
					        MiscUtils.getRequestInfo(request));
				} catch (Exception e1) {
					log.error(e1,e1);
				}  			
    		}
    		
    		try {
				if(aimsUser.getUserType().equals(AimsConstants.ALLIANCE_USERTYPE)){
					log.debug("---Verifying GTM ID Info . User is Developer");
					Long allianceID = aimsUser.getAimsAllianc();
					log.debug("---alliance ID : "+allianceID);
					AimsAllianc alliance = AllianceManager.getAllianceOnAllianceID(allianceID);
					if((gtmCompanyId==null || !gtmCompanyId.equals(alliance.getVendorId().toString())) && daysBetween(aimsUser.getCreatedDate())>1){
						log.debug("---request gtmCompanyId : "+gtmCompanyId);
						log.debug("---DB vendor id  : "+alliance.getVendorId());
						String subject="Problem with Gtm company ID . Gtm company ID is "+gtmCompanyId+ " and vendor id is "+alliance.getVendorId().toString()+". System is about to set correct gtm ID";
						MailUtils.sendMail(
					            AimsConstants.EMAIL_EXCEPTION_ADMIN,
					            "exceptions@netpace.com", 
					            subject,
					            null, 
					            MiscUtils.getRequestInfo(request));
						OpenssoRestService.updateUserCompany(aimsUser, alliance);
					}
				
				}
			} catch (Exception e) {
				log.error("Error while Emailing Developers GTM Company ID Info",e);
			} 
    	}
    	
    	
    	public static long daysBetween(Date createDate) {  
		    Calendar offerDateCalendar = Calendar.getInstance();
		    offerDateCalendar.setTime(createDate);

		    Calendar today = Calendar.getInstance();
		    today.setTime(new Date());
		    
		    
		   Calendar date = (Calendar) offerDateCalendar.clone();  
		   long daysBetween = 0;  
		   while (date.before(today)) {  
		     date.add(Calendar.DAY_OF_MONTH, 1);  
		     daysBetween++;  
		   }
		   log.debug("difference in create  days : "+daysBetween);
		   
		   return daysBetween;  
		 }
    	
}
