/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Wakram
 */
@Entity
@Table(name="vap_user_role")
@NamedQueries({
    @NamedQuery(name = "findUserRoleByUserId", query = "select userrole from UserRole userrole"
        + " inner join fetch userrole.user user "
        + " inner join fetch userrole.systemRole where user.id = :userid")
})
public class UserRole extends BaseEntity implements Serializable {

     // Fields    

     private Integer id;
     private User user;
     private SystemRole systemRole;

     
     // Property accessors
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_role_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="role_id")
    public SystemRole getSystemRole() {
        return this.systemRole;
    }
    
    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }     
}


