package com.netpace.aims.controller.login;

import java.util.ResourceBundle;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.security.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ConfigEnvProperties;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/logout"
 *                scope="request"
 *                input="/index.jsp"
 * @struts.action-forward name="success" path="/"
 * @author Shahnawaz Bagdadi
 */
public class LogoutAction extends Action 
{
  
    static Logger log = Logger.getLogger(LogoutAction.class.getName());
  
    /**
     * This method invalidates the user and logs the user out. 
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {
        // reset the user atrribute and invalidate the session
        request.getSession().removeAttribute(AimsConstants.AIMS_USER);
        request.getSession().removeAttribute(AimsConstants.GLOBAL_NAV_HAEDER);
        request.getSession().removeAttribute(AimsConstants.SESSION_FIRST_NAME);
        request.getSession().removeAttribute(AimsConstants.SESSION_LAST_NAME);
        request.getSession().removeAttribute(AimsConstants.COOKIE_EMAIL_ADDRESS);
        
        
        request.getSession().invalidate();
        log.debug("logout successful");
        //"http://viper.netpace.com:8002/opensso/UI/Logout?goto=http://mudassir.netpace.com:8080/aims/userHome.do"

        /*ConfigEnvProperties props=ConfigEnvProperties.getInstance();
		String openssoUrl=props.getProperty("opensso.logout.url");
		String redirectURI=props.getProperty("opensso.redirect.uri");
		
		log.debug("---openssoLogoutUri :"+openssoUrl);
		log.debug("---redirectURI :"+redirectURI);
		String redirectUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+redirectURI;
		log.debug("---redirectUrl :"+redirectUrl);
		String logOutUri = openssoUrl+"?goto="+redirectUrl;
		log.debug("---logOutUri :"+logOutUri);
        response.sendRedirect(logOutUri);*/
        
        //return mapping.findForward("success");
        return null;
    }
}
