package com.netpace.device.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "vap_company")
@NamedQueries({
    @NamedQuery(name = "findCompanyByDomainName", query = "from Company company"
            + " where company.companyDomain= :domainName and company.active = '1'"),
    @NamedQuery(name = "findCompanyByName", query = "from Company company"
            + " where company.name= :companyName and company.active = '1'"),
    @NamedQuery(name = "findCompanyById", query = "from Company company"
            + " where company.id= :companyId and company.active = '1'")
})
public class Company extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;
    private Integer id;
    private String name;
    private String legalName;
    private String mobile;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String website;
    private String companyDomain;
    private String workFlowSteps;
    private List<Workflow> workFlow;
    private VapContact salesContact;
    private Timestamp certNdaAcceptDate;
    private Integer offlineCertNdaId;
    private boolean suspended;

    public Company() {
    }

    public Company(Integer id) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false, unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "address1")
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

    @Column(name = "company_domain")
    public String getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "company_legal_name")
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "company_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "website")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Formula(value = "work_flow_steps(company_id, '<br/> ')")
    @Basic(fetch = FetchType.LAZY)
    public String getWorkFlowSteps() {
        return workFlowSteps;
    }

    public void setWorkFlowSteps(String workFlowSteps) {
        this.workFlowSteps = workFlowSteps;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public List<Workflow> getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(List<Workflow> workFlow) {
        this.workFlow = workFlow;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_contact_id")
    public VapContact getSalesContact() {
        return salesContact;
    }

    public void setSalesContact(VapContact salesContact) {
        this.salesContact = salesContact;
    }

    @Column(name = "cert_nda_accept_date")
    public Timestamp getCertNdaAcceptDate() {
        return certNdaAcceptDate;
    }

    public void setCertNdaAcceptDate(Timestamp certNdaAcceptDate) {
        this.certNdaAcceptDate = certNdaAcceptDate;
    }

    @Column(name = "offline_cert_nda_id")
    public Integer getOfflineCertNdaId() {
        return offlineCertNdaId;
    }

    public void setOfflineCertNdaId(Integer offlineCertNdaId) {
        this.offlineCertNdaId = offlineCertNdaId;
    }

    @Column(name = "is_suspended")
    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }
}