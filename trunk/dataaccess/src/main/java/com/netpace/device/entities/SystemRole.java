package com.netpace.device.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * VapSystemRole entity.
 *
 * @author MyEclipse Persistence Tools
 */
@Entity()
@Table(name = "vap_system_role")
@NamedQueries({
    @NamedQuery(name = "findSystemRoles", query = "from SystemRole systemRole"
            + " where systemRole.active = '1' and systemRole.title in :rolesList"),
    @NamedQuery(name = "findUnhiddenSystemRoles", query = "from SystemRole systemRole"
            + " where systemRole.active = '1' and systemRole.hidden = '0' ")
})
public class SystemRole extends BaseEntity implements Serializable {
    // Fields    

    private Integer id;
    private String title;
    private String description;
    private Set<UserRole> userRoles;
    private String displayTitle;
    private boolean hidden;

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false, insertable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemRole")
    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Column(name = "display_title")
    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    @Column(name = "is_hidden")
    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}