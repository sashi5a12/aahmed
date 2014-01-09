package com.netpace.device.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vap_user")
@NamedQueries({
    @NamedQuery(name = "findUsers", query = "select user from User user"
            + " where user.active = '1'"),
    @NamedQuery(name = "findUsersCount", query = "select count(user) from User user"
            + " where user.active = '1'"),
    @NamedQuery(name = "findUserById", query = "select user from User user"
            + " where user.id = :userId and user.active = '1'"),
    @NamedQuery(name = "findUsersByCompanyId", query = "select DISTINCT user from User user"
            + " inner join fetch user.userRoles userroles"
            + " inner join fetch userroles.systemRole sysroles"
            + " inner join fetch user.company c where user.active = '1' and c.id=:companyId"),
    @NamedQuery(name = "findPartnerUserById", query = "select user from User user"
            + " where user.id = :userId and company.id = :companyId and user.active = '1'"),
    @NamedQuery(name = "findPartnerUserByIdForEdit", query = "select user from User user"
            + " where user.id = :userId and company.id = :companyId and user.active = '1'"),
    @NamedQuery(name = "findAdminUserByIdForEdit", query = "select user from User user"
            + " where user.id = :userId"),
    @NamedQuery(name = "findMPOC", query = "select user from User user"
            + " inner join user.userRoles userroles"
            + " inner join userroles.systemRole sysroles"
            + " inner join fetch user.company c where c.id=:companyId "
            + " and sysroles.title = :roleName and c.active = '1'")})
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;
    private Integer id;
    private Company company;
    private String userName;
    private String password;
    private String emailAddress;
    private String fullName;
    private String phone;
    private String mobile;
    private String companyDomain;
    private String token;
    private String resetPasswordToken;
    private Timestamp lastLoginDate;
    private Set<UserRole> userRoles;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private boolean enable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "company_domain")
    public String getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    @Column(name = "email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "last_login_date")
    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name = "reset_password_token")
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "is_enable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
