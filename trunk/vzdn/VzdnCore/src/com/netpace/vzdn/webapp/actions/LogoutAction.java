package com.netpace.vzdn.webapp.actions;


import org.apache.log4j.Logger;


import com.netpace.vzdn.global.VzdnConstants;

public class LogoutAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(LogoutAction.class.getName());
  
    /**
     * This method invalidates the user and logs the user out. 
     */
    public void logout() throws Exception
    {
        // reset the user atrribute and invalidate the session
        getServletRequest().getSession().removeAttribute(VzdnConstants.LOGGED_IN_USER);
        getServletRequest().getSession().invalidate();
        log.debug("**************************logout successfully************************");
        
        //ConfigEnvProperties props=ConfigEnvProperties.getInstance();
		//String openssoUrl=props.getProperty("opensso.logout.url");
		//String redirectURI=props.getProperty("opensso.redirect.uri");
		
		//log.debug("---openssoLogoutUri :"+openssoUrl);
		//log.debug("---redirectURI :"+redirectURI);
		//String redirectUrl = getServletRequest().getScheme()+"://"+getServletRequest().getServerName()+":"+getServletRequest().getServerPort()+getServletRequest().getContextPath()+redirectURI;
		//log.debug("---redirectUrl :"+redirectUrl);
		//String logOutUri = openssoUrl+"?goto="+redirectUrl;
		//log.debug("---logOutUri :"+logOutUri);
		//getServletResponse().sendRedirect(logOutUri);
		//log.debug("---after redirect :");
		//return "";
		//return SUCCESS;
		
    }
}
