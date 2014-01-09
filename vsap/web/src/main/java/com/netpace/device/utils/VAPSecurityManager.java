package com.netpace.device.utils;

import com.netpace.device.services.UserService;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.services.VAPUserDetailsService;
import com.netpace.device.vo.MenuInfo;
import com.netpace.device.vo.VAPUserDetails;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class VAPSecurityManager {

    private static final Log log = LogFactory.getLog(VAPSecurityManager.class);
    
    public static boolean isUserAuthenticated() {
        boolean retVal = false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            retVal = true;
        }
        return retVal;
    }

    public static String getLoggedInUserName() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            try {
                VAPUserDetails loggedInUser = (VAPUserDetails) auth.getPrincipal();
                if (loggedInUser != null) {
                    return loggedInUser.getUsername();
                }
            } catch (ClassCastException cce) {
                throw new AuthenticationException("Invalid authentication type ", cce) {
                };
            }
        }
        throw new AuthenticationException("User is not authenticated") {
        };
    }

    public static String getUserName() {
        return getLoggedInUserName();
    }

    public static Collection<GrantedAuthority> getAuthorities() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getAuthorities();
        }
        throw new AuthenticationException("User is not authenticated") {
        };

    }

    public static VAPUserDetails getAuthenticationToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            log.debug("Authentication Details");
            log.debug("\nAuthentication [Authorities="+auth.getAuthorities()+ ", Credentials=chal_nikal_muney , Details="+auth.getDetails()+" , Name="+auth.getName()+" , Principal= "+auth.getPrincipal()+"]");
            return (VAPUserDetails) auth.getPrincipal();
        }
        throw new AuthenticationException("User is not authenticated") {
        };
    }

    public static void doLogin(HttpServletRequest request, VAPUserDetailsService userDetailsService, UtilitiesService utilitiesService, String userName) {
        doLogin(request, userDetailsService, utilitiesService, userName, false);
    }

    public static void doLogin(HttpServletRequest request, VAPUserDetailsService userDetailsService, UtilitiesService utilitiesService, String userName, boolean reload) {
        VAPUserDetails loggedInUser = (VAPUserDetails) userDetailsService.loadUserByUsername(userName);
        
        Authentication auth = new UsernamePasswordAuthenticationToken(loggedInUser, loggedInUser.getPassword(), loggedInUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        List<MenuInfo> menu = utilitiesService.getMenuInformation(loggedInUser);
        request.getSession().setAttribute(VAPConstants.MENU_INFORMATION, menu);
    }

    public static void doLogout(HttpServletRequest request, UserService userService, String userName) {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
