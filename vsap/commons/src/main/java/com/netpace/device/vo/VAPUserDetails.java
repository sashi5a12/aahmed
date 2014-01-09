package com.netpace.device.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.netpace.device.utils.VAPConstants;

public class VAPUserDetails implements UserDetails {

    //id database PK of vap_user table
    private Integer id;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled; //represented by column isActive in database
    private Collection<GrantedAuthority> authroities;
    private String fullName;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String emailAddress;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private Integer companyId; //companyId=null means there is no registered company for this user
    private String companyDomain;
    private Date createdDate;
    private String companyStatus="NOT_SET";
    private List<SystemRoleVO> roles;

    public VAPUserDetails(Integer id, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Collection<GrantedAuthority> authroities, String fullName, String phoneNumber, String mobilePhoneNumber, String emailAddress, String address, String city, String state, String country, String zip, Integer companyId, String companyDomain, Date createdDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.authroities = authroities;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.companyId = companyId;
        this.companyDomain = companyDomain;
        this.createdDate = createdDate;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authroities;
    }

    /**
     * Sets user authorities and updates the roles list In system we will use
     * the roles list as its a list of SystemRoleVO We can't use
     * GrantedAuthority object in system because this object is part of the
     * spring security package
     *
     * @param authroities
     */
    public void setAuthroities(Collection<GrantedAuthority> authroities) {
        this.authroities = authroities;
    }

    /**
     * Adds a new role in the granted authorities list and updated the roles
     * list
     *
     * @param roleName
     */
    public void addRole(String roleName) {
        List<GrantedAuthority> newAuthorities = AuthorityUtils.createAuthorityList(roleName);
        getAuthorities().addAll(newAuthorities);
    }

    /**
     * Removes a role from the granted authorities list and updates the roles
     * list
     *
     * @param roleName
     */
    public void removeRole(String roleName) {
        List<GrantedAuthority> authoritiesToRemove = AuthorityUtils.createAuthorityList(roleName);
        getAuthorities().removeAll(authoritiesToRemove);
    }
    /**
     * Important : This method is deliberately made private To update roles use
     * the addRole and removeRole method
     *
     * @param roles
     */
    private void setRoles(List<SystemRoleVO> roles) {
        this.roles = roles;
    }

    /**
     * I know this function is heavy. But lets have it like this for know
     * Will re-factor to optimize later
     */
    public List<SystemRoleVO> getRoles() {
        List<SystemRoleVO> systemRoles = new ArrayList<SystemRoleVO>();

        for (GrantedAuthority authority : getAuthorities()) {
            SystemRoleVO systemRole = new SystemRoleVO();
            systemRole.setRoleName(authority.getAuthority());

            systemRoles.add(systemRole);
        }
        
        return systemRoles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCompanyDomain() {
        return companyDomain;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean isIsActive() {
        return enabled;
    }

    public void setIsActive(boolean isActive) {
        this.enabled = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    
    public String commaSeparatedRolesList() {
        String commaSeparatedRoles = "";
        if ( getAuthorities() != null ) {
            for ( GrantedAuthority authority : getAuthorities()) {
                commaSeparatedRoles += authority.getAuthority();
            }
        }
        return commaSeparatedRoles;
    }

    public boolean isPartner(){
    	return StringUtils.contains(commaSeparatedRolesList(), VAPConstants.ROLE_PARTNER_USER);
    }
    
    public boolean isSuperAdmin(){
    	return StringUtils.contains(commaSeparatedRolesList(), VAPConstants.ROLE_SUPER_ADMIN);
    }
    
    public boolean isVerizonAdmin(){
    	return StringUtils.contains(commaSeparatedRolesList(), VAPConstants.ROLE_VERIZON_ADMIN);
    }
    
    public boolean isDeviceMarketingManager(){
    	return StringUtils.contains(commaSeparatedRolesList(), VAPConstants.ROLE_DEVICE_MARKETING);
    }
    
    public boolean isVerizonUser(){
    	String roles=commaSeparatedRolesList();
    	if(StringUtils.contains(roles, VAPConstants.ROLE_SUPER_ADMIN)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_VERIZON_ADMIN)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_DEVICE_MARKETING)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_OFAC)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_REQUIREMENTS_GROUP)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_DEVICE_COMPLIANCE)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_EXPORT_COMPLAINCE)){
    		return true;
    	}
    	return false;
    }
    public boolean isUserPresentInRoleList(String commanSeparatedRoles){
        if ( getAuthorities() != null ) {
            for ( GrantedAuthority authority : getAuthorities()) {
            		if(authority.getAuthority().indexOf(commanSeparatedRoles)!=-1){
            			return true;
            		}                    
            }
        }
        return false;
    }
    
    public String[] getRolesArray() {
    	if ( getAuthorities() != null ) {
	        String[] roles = new String[getAuthorities().size()];
	        int i=0;
	        for (GrantedAuthority authority : getAuthorities()) {
	            roles[i++]=authority.getAuthority();
	        }   
	        return roles;
    	}
        return new String[0];
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	@Override
    public String toString() {
        return "\nVAPUserDetails ["+
                "id= "+id+
                ", username= "+username+
                ", password=" + password +
                ",companyId=" + companyId +
                ",companyDomain=" + companyDomain +
                ",enabled=" + enabled +
                ",roles=" + commaSeparatedRolesList() +
                ",accountNonExpired=" + accountNonExpired + 
                ",accountNonLocked=" + accountNonLocked +
                ",credentialsNonExpired=" + credentialsNonExpired +                
                ",fullName=" + fullName +
                ",phoneNumber=" + phoneNumber +
                ",mobilePhoneNumber=" + mobilePhoneNumber +
                ",emailAddress=" + emailAddress +
                ",address=" + address +
                ",city=" + city +
                ",state=" + state +
                ",country=" + country +
                ",zip=" + zip +                
                ",createdDate=" + createdDate + "]";    
    }
}
