package com.netpace.device.entities;

import com.netpace.device.utils.enums.WorkitemStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author trafique
 */
@Entity
@Table(name = "vap_workitem")
@NamedQueries({
    @NamedQuery(name = "findWorkitemByCriteria", query = "from Workitem workitem"
        + " inner join fetch workitem.workflow workflow"
        + " where workitem.active = '1' and workitem.title = :title"
        + " and workitem.status = :status"
        + " and workflow.id = :workflowId")
})
public class Workitem extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;
    private Integer id;
    private String title;
    private String displayTitle;
    private WorkitemStatus status;
    private String allowedRoles;
    private boolean requireInput;
    private String nextActions;
    private String actionTaken;
    private Timestamp startDate;
    private Timestamp endDate;
    private int version;
    private Workflow workflow;
    private boolean locked;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workitem_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "require_input")
    public boolean isRequireInput() {
        return requireInput;
    }

    public void setRequireInput(boolean requireInput) {
        this.requireInput = requireInput;
    }

    @Column(name = "next_actions")
    public String getNextActions() {
        return nextActions;
    }

    public void setNextActions(String nextActions) {
        this.nextActions = nextActions;
    }

    @Column(name = "allowed_roles")
    public String getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(String allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public WorkitemStatus getStatus() {
        return status;
    }

    public void setStatus(WorkitemStatus status) {
        this.status = status;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "display_title")
    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    @Column(name = "action_taken")
    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    @Column(name = "is_locked")
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
