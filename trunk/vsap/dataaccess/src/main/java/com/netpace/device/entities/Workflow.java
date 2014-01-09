package com.netpace.device.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author trafique
 */
@Entity
@Table(name = "vap_workflow")
public class Workflow extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;
    private Integer id;
    private Company company;
    private VapProduct product;
    private String workflowType;
    private Timestamp startDate;
    private List<Workitem> workItems;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public VapProduct getProduct() {
        return product;
    }

    public void setProduct(VapProduct product) {
        this.product = product;
    }

    @Column(name = "workflow_type")
    public String getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    public List<Workitem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List<Workitem> workItems) {
        this.workItems = workItems;
    }

    
}
