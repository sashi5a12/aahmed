/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.enums;

/**
 *
 * @author Hamza Ghayas
 */
public interface PageQueryField {
    public String getFieldName() ;
    public PageQueryField getField();
    public String getAlias();
    public String getJoinType();
    public Integer getOrder();
    public PageQueryField[] getAllFields();
    public PageQueryField[] getAllEntities();
    public String getAliasList();
    public String getParentEntityAlias();
    public String getFirstEntityAlias();
}
