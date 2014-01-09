/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.entities;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wakram
 */
@Entity
@Table(name="vap_application_properties")
public class ApplicationProperties extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 8788527616393061096L;

    // Fields
    private Integer propertyId;
    private ApplicationPropertyType type;
    private String name;
    private String value;
    private Integer sortOrder;
	
    // Property accessors
    @Id 
    @GeneratedValue(strategy=IDENTITY)    
    @Column(name="property_id", unique=true, nullable=false)
    public Integer getPropertyId() {
        return this.propertyId;
    }
    
    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }
    
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    public ApplicationPropertyType getType() {
        return this.type;
    }
    
    public void setType(ApplicationPropertyType type) {
        this.type = type;
    }
    
    @Column(name="name")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="value")
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Column(name="sort_order")
    public Integer getSortOrder() {
        return this.sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}