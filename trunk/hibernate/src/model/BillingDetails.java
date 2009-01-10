package model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * BillingDetails entity. @author MyEclipse Persistence Tools
 */

public class BillingDetails  implements java.io.Serializable {


    // Fields    

     private Long billingDetailsId;
     private String billingDetailsType;
     private Integer objVersion;
     private String owner;
     private Timestamp created;
     private String baAccount;
     private String baBankname;
     private String baSwift;
     private Set<Users> userses = new HashSet<Users>(0);


    // Constructors

    /** default constructor */
    public BillingDetails() {
    }

	/** minimal constructor */
    public BillingDetails(String billingDetailsType, Integer objVersion, String owner, Timestamp created) {
        this.billingDetailsType = billingDetailsType;
        this.objVersion = objVersion;
        this.owner = owner;
        this.created = created;
    }
    
    // Property accessors

    public Long getBillingDetailsId() {
        return this.billingDetailsId;
    }
    
    public void setBillingDetailsId(Long billingDetailsId) {
        this.billingDetailsId = billingDetailsId;
    }

    public String getBillingDetailsType() {
        return this.billingDetailsType;
    }
    
    public void setBillingDetailsType(String billingDetailsType) {
        this.billingDetailsType = billingDetailsType;
    }

    public Integer getObjVersion() {
        return this.objVersion;
    }
    
    public void setObjVersion(Integer objVersion) {
        this.objVersion = objVersion;
    }

    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Timestamp getCreated() {
        return this.created;
    }
    
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getBaAccount() {
        return this.baAccount;
    }
    
    public void setBaAccount(String baAccount) {
        this.baAccount = baAccount;
    }

    public String getBaBankname() {
        return this.baBankname;
    }
    
    public void setBaBankname(String baBankname) {
        this.baBankname = baBankname;
    }

    public String getBaSwift() {
        return this.baSwift;
    }
    
    public void setBaSwift(String baSwift) {
        this.baSwift = baSwift;
    }

    public Set<Users> getUserses() {
        return this.userses;
    }
    
    public void setUserses(Set<Users> userses) {
        this.userses = userses;
    }
   








}