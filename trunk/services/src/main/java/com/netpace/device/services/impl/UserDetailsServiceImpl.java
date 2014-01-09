/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.services.impl;

import com.netpace.device.dao.UserDao;
import com.netpace.device.dao.UserRoleDao;
import com.netpace.device.entities.User;
import com.netpace.device.entities.UserRole;
import com.netpace.device.services.VAPUserDetailsService;
import com.netpace.device.vo.VAPUserDetails;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wakram
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements VAPUserDetailsService {

    private final static Log log = LogFactory.getLog(UserDetailsServiceImpl.class);
    
    @Resource(name = UserRoleDao.name)
    private UserRoleDao userRoleDao;
    
    @Resource(name = UserDao.name)
    private UserDao userDao;
    /**
     * Spring security uses this function to get the user details from the
     * database. The details are used for authentication and are set in the
     * SpringSecurityContextHolder upon successful authentication
     *
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    @Override
    @Transactional(readOnly = true)
    public VAPUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        VAPUserDetails userDetails = null;

        log.info("Fetching details of user [" + userName + "]");

        //User can login by username or email address        
        User user = userDao.getUserByUserName(userName);
        if (user == null) {
            user = userDao.getUserByEmailAddress(userName);
        }

        if (user == null) 
            throw new UsernameNotFoundException("User ["+userName+"] not found");
        
        userDetails = convertToVAPUserDetails(user, userRolesByUser(user));
        log.info(userDetails.toString());

        return userDetails;
    }

    private VAPUserDetails convertToVAPUserDetails(User user, String[] userRoles) {
        VAPUserDetails userDetails = null;

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);

        Integer companyId = null;
        String companyDomain = null;
        if (user.getCompany() != null) {
            companyId = user.getCompany().getId();
        }
        companyDomain = user.getCompanyDomain();

        userDetails = new VAPUserDetails(
                user.getId(), user.getUserName(), user.getPassword(), true, 
                true, true,user.isEnable(), authorities, user.getFullName(),
                user.getPhone(), user.getMobile(), user.getEmailAddress(),
                user.getAddress(), user.getCity(), user.getState(), user.getCountry(),
                user.getPostalCode(), companyId, companyDomain,
                user.getCreatedDate());

        return userDetails;
    }
    
    private String[] userRolesByUser(User user) {
        String[] userRoleStrArr = null;

        Set<UserRole> userRoles = user.getUserRoles();
        
        if (userRoles != null && userRoles.size() > 0) {
            log.info("Num of assigned roles to user [" + user.getUserName() + "] = " + userRoles.size());
            userRoleStrArr = new String[userRoles.size()];
            int index = 0;
            for (UserRole userRole : userRoles) {
                String role = userRole.getSystemRole().getTitle();
                log.info("ROLE[" + index + "] = " + role);
                userRoleStrArr[index++] = role;
            }
        }

        return userRoleStrArr;
    }

    @Override
    @Transactional
    public void updateUserLastLoginDate(VAPUserDetails loggedInUser) {
        log.info("Updating last login date for user: userId [" + loggedInUser.getId() + "]");
        
        userDao.updateUserLastLoginDate(loggedInUser.getId());
    }
    
}
