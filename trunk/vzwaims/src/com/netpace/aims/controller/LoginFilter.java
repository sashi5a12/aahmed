package com.netpace.aims.controller;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.logincontent.AimsLoginContentManager;
import com.netpace.aims.bo.security.InvalidLoginException;
import com.netpace.aims.bo.vzdn.VzdnUserManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.OpenssoRestService;
import com.netpace.aims.util.StringFuncs;
import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;





/**
 *  This is the filter class which validates the Aims User
 *
 *  @web.filter name="LoginFilter"
 *
 *  @web.filter-mapping url-pattern="*.do"
 *
 *  @web.filter-mapping url-pattern="*.jsp"
 */
public final class LoginFilter implements Filter
{

	static final  Logger log = Logger.getLogger(LoginFilter.class.getName());
	
    public static final String login_action = "login.do";
    public static final String loginSetup_action = "loginSetup.do";
    
    public static final String allianceMusicRegistrationSetup_action = "allianceMusicRegistrationSetup.do";
    public static final String allianceMusicRegistrationUpdate_action = "allianceMusicRegistrationUpdate.do";
    
    //JMA alliance
    public static final String jmaAllianceRegistrationSetup_action = "jmaAllianceRegistrationSetup.do";
    public static final String jmaAllianceRegistrationUpdate_action = "jmaAllianceRegistrationUpdate.do";
        
    public static final String forgotPassword_action = "forgotPassword.do";
    public static final String forgotPasswordSetup_action = "forgotPasswordSetup.do";
    public static final String whitePapers_action = "WPList.do";
    public static final String whitePapers_resource = "whitePaperResource.do";
    public static final String login_jsp = "index.jsp";
    public static final String webapp = "/aims/";
    public static final String logout_action = "logout.do";
    public static final String userHome_action = "userHome.do";
    public static final String bulletinAcknowledged_action = "bulletinAcknowledged.do";
    public static final String bulletinResource_action = "bulletinResource.do";
    

    //resources that dosn't require a Login
    public static final String infoSpace_resource = "infoSpaceResource.do";
    public static final String infoSpaceResubmit_action = "infoSpaceResubmit.do";
    public static final String autodeskResubmit_action = "autodeskResubmit.do";
    public static final String zonRestAPI_action = "zonRestAPI.do";
    public static final String intertekResubmit_action = "intertekResubmit.do";
    public static final String mPortalResubmit_action = "mPortalResubmit.do";
    public static final String public_folder = "public/";
    public static final String wapoptout_action = "wapOptout.do";
    public static final String checkOffer_action = "checkOffer.do";
    public static final String allianceRegistrationSetup_action = "allianceRegistrationSetup.do";
    public static final String allianceRegistrationUpdate_action = "allianceRegistrationUpdate.do";
    
    
    
    
    

    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        //Makda - Setting Character Encoding to UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String path = req.getServletPath(); 
        System.out.println("Request PATH : " + path);
        System.out.println("Current Time: " + new Date());

        if (req.getHeader("X-Forwarded-For") == null)
            System.out.println("Remote Address : " + req.getRemoteAddr());
        else
            System.out.println("Remote Address : " + req.getHeader("X-Forwarded-For"));

        if (req.getSession().getAttribute(AimsConstants.AIMS_USER)!=null && !this.retrieveUserNameFromCookie(req).equals(""))
        {
        	//Check if Active Bulletin is read/login content to show, otherwise redirect to 'My Account'/'Welcome' Page.
            if (((req.getSession().getAttribute(AimsConstants.SESSION_BULLETIN_TO_READ) != null) || (req.getSession().getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW)!=null))
                && (path.indexOf(login_action) == -1)
                && (path.indexOf(userHome_action) == -1)
                && (path.indexOf(bulletinAcknowledged_action) == -1)
                && (path.indexOf(bulletinResource_action) == -1)
                && (path.indexOf(logout_action) == -1))
            {
                if(req.getSession().getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW)!=null)
                {
                    //skip ignored paths for login content
                    if(!AimsLoginContentManager.checkIngoredPathsForLoginContent(req, path))
                    {
                        resp.sendRedirect(webapp + userHome_action);
                        return;
                    }
                }
                else
                {
                    resp.sendRedirect(webapp + userHome_action);
                    return;
                }
            }
            chain.doFilter(request, response);
            return;
        }
        else
        {
        	openssoLoggedInUserAccessingZONPublicResources(req);	
        	 //this.checkUserCookie(req,resp);	
        	if (          
        		   (path.indexOf(public_folder) != -1)
                || (path.indexOf(infoSpace_resource)) != -1
                || (path.indexOf(infoSpaceResubmit_action)) != -1
                || (path.indexOf(autodeskResubmit_action)) != -1
                || (path.indexOf(zonRestAPI_action)) != -1
                || (path.indexOf(intertekResubmit_action)) != -1
                || (path.indexOf(mPortalResubmit_action)) != -1
                || (path.indexOf(wapoptout_action)) != -1
                || (path.indexOf(checkOffer_action)) != -1
                || (path.indexOf(allianceRegistrationSetup_action)) != -1
                || (path.indexOf(allianceRegistrationUpdate_action)) != -1
                || (path.indexOf(jmaAllianceRegistrationSetup_action) != -1)
                || (path.indexOf(jmaAllianceRegistrationUpdate_action) != -1)
                || (path.indexOf(allianceMusicRegistrationSetup_action)) != -1
                || (path.indexOf(allianceMusicRegistrationUpdate_action)) != -1
                || (path.indexOf(logout_action)) != -1
                
                
        	  )
            {
                //remove SESSION_SHOW_PAGE_AFTER_LOGIN attribute for public pages,
                //i.e., show public pages if requested instead of showing SESSION_SHOW_PAGE_AFTER_LOGIN
                session.removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);

                chain.doFilter(request, response);
                return;
            }
            else
            {
        		try
                {
                    log.debug("LoginFilter: user comes first time (without login)");

                    if( path.indexOf("/vzdnLoginAction.do") == -1)
                    {
                        //if request url is not public, i.e., a secure page, save this url in session, and show it after succesfull login                        
                        StringBuffer pageAfterLoginBuffer = new StringBuffer();
                        String queryString = req.getQueryString();
                        pageAfterLoginBuffer.append(req.getRequestURL());
                        if(!StringFuncs.isNullOrEmpty(queryString))
                        {
                            pageAfterLoginBuffer.append("?").append(queryString);
                        }
                        log.debug("LoginFilter: setting "+ AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN+"= "+pageAfterLoginBuffer.toString());
                        session.setAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN, pageAfterLoginBuffer.toString());						
                    }

                    //this.checkUserCookie(req,resp);
        			RequestDispatcher ds = request.getRequestDispatcher("/vzdnLoginAction.do");
        			ds.forward(request, response);
        			return;
        			
        			
        		}
                catch(Exception ex)
                {
        			ex.printStackTrace();
        			//opensso URl should be called
        			resp.sendRedirect(webapp);
        		}
        	
            }
        }//end else outer
        return;
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
						log.debug("---cookie value : "+cookie.getValue());
						SSOToken st = stm.createSSOToken(req);
						    userName = st.getPrincipal().getName();
						    userName = userName.substring( userName.indexOf( "=" ) + 1, userName.indexOf( "," ) ).toLowerCase();
						log.debug("got the cookie user name : "+userName);
						if(userName!=null && ! userName.equals("")){
							if(req.getSession().getAttribute(AimsConstants.AIMS_USER)!=null){
								AimsUser user = (AimsUser)req.getSession().getAttribute(AimsConstants.AIMS_USER);
								if(!user.getUsername().equals(userName)){
									userName="";
                                    req.getSession().removeAttribute(AimsConstants.AIMS_USER);
                                    req.getSession().removeAttribute(AimsConstants.GLOBAL_NAV_HAEDER);
                                    req.getSession().removeAttribute(AimsConstants.SESSION_FIRST_NAME);
                                    req.getSession().removeAttribute(AimsConstants.SESSION_LAST_NAME);
                                    req.getSession().removeAttribute(AimsConstants.COOKIE_EMAIL_ADDRESS);
									//try{req.getSession().invalidate();}catch(Exception ex){}
								}
									
							}
						}
					}
				}
				return userName;
			} catch (Exception e) {
				log.error("error occured while retrieving cookie sent by sso.",e);
				return userName;
			}
    }
    
    	

    private void openssoLoggedInUserAccessingZONPublicResources(HttpServletRequest req){
    	try {
    		String userName="";
    		SSOTokenManager stm= SSOTokenManager.getInstance();
			log.debug("level 1 ");
    		Cookie []  requestCookies = req.getCookies();
			log.debug("level 2 ");
			if (requestCookies != null){
				for(int i=0; i < requestCookies.length; i++){
					Cookie cookie = requestCookies[i];
					if(cookie.getName().equals("iPlanetDirectoryPro")){
							log.debug("---cookie value for non logged in user in zon: "+cookie.getValue());
							SSOToken st = stm.createSSOToken(req);
							log.debug("level 3 ");
							userName = st.getPrincipal().getName();
							log.debug("level 4 ");
							userName = userName.substring( userName.indexOf( "=" ) + 1, userName.indexOf( "," ) ).toLowerCase();
							log.debug("got the cookie user name for user that is not logged in to the system: "+userName);
					}
				}
			}
			log.debug("level 5 ");
			if (StringUtils.isNotEmpty(userName)){
				if(req.getSession().getAttribute(AimsConstants.COOKIE_EMAIL_ADDRESS)==null || !((String)req.getSession().getAttribute(AimsConstants.COOKIE_EMAIL_ADDRESS)).equals(userName)){
					String firstName = OpenssoRestService.readAttribute(userName,"givenName");
					String lastName = OpenssoRestService.readAttribute(userName,"sn");
					req.getSession().setAttribute(AimsConstants.SESSION_FIRST_NAME, firstName);
					req.getSession().setAttribute(AimsConstants.SESSION_LAST_NAME, lastName);
					req.getSession().setAttribute(AimsConstants.COOKIE_EMAIL_ADDRESS, userName);
					req.getSession().removeAttribute(AimsConstants.GLOBAL_NAV_HAEDER);
					log.debug("impl23 change---User trying to access public page : "+firstName+" " + lastName);
				}
			}
    	}catch(Exception ex){
    		log.warn("user tried to access ZOn without logging into VZDN");
			log.error(ex,ex);
			}
    }
    
       
    

    public void destroy()
    {}

    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

}
