package com.netpace.device.entities;

import com.netpace.device.entities.enums.JoinCompanyRequestType;
import com.netpace.device.entities.enums.JoinCompanyStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vap_company_join_offer")
public class JoinCompanyRequest extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;
    private Integer id;
    private Timestamp offerDate;
    private Timestamp acceptRejectDate;
    private JoinCompanyRequestType type;
    private JoinCompanyStatus status;
    private Company company;
    private User offerTo;
    private User acceptedBy;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id", nullable = false, unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "accept_reject_date")
    public Timestamp getAcceptRejectDate() {
        return acceptRejectDate;
    }

    public void setAcceptRejectDate(Timestamp acceptRejectDate) {
        this.acceptRejectDate = acceptRejectDate;
    }

    @OneToOne
    @JoinColumn(name="acceptor_id")
    public User getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(User acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    @ManyToOne
    @JoinColumn(name="company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name = "offer_date")
    public Timestamp getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(Timestamp offerDate) {
        this.offerDate = offerDate;
    }

    @OneToOne
    @JoinColumn(name="offered_to")
    public User getOfferTo() {
        return offerTo;
    }

    public void setOfferTo(User offerTo) {
        this.offerTo = offerTo;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public JoinCompanyStatus getStatus() {
        return status;
    }

    public void setStatus(JoinCompanyStatus status) {
        this.status = status;
    }

    @Column(name = "offer_type")
    @Enumerated(EnumType.STRING)
    public JoinCompanyRequestType getType() {
        return type;
    }

    public void setType(JoinCompanyRequestType type) {
        this.type = type;
    }
}
