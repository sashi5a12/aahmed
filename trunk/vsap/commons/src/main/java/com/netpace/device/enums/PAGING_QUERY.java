/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.enums;

/**
 *
 * @author Hamza Ghayas
 */
public enum PAGING_QUERY{
    
    WORKLIST_PAGE(WORKLIST_PAGE_FIELDS.ALL_REQUIRED.getAllFields(),
            WORKLIST_PAGE_FIELDS.ALL_REQUIRED.getAllEntities(),WORKLIST_PAGE_FIELDS.ALL_REQUIRED.getAliasList(),
            WORKLIST_PAGE_FIELDS.ALL_REQUIRED.getFirstEntityAlias());
    
    private PAGING_QUERY(PageQueryField[] pageQueryFields,PageQueryField[] entityArray,String aliasList,String firstEntityAlias){
        this.pageQueryFields = pageQueryFields;
        this.entityArray = entityArray;
        this.aliasList = aliasList;
        this.firstEntityAlias = firstEntityAlias;
    }
    
    private String firstEntityAlias;
    private String aliasList;
    private PageQueryField[] pageQueryFields;
    private PageQueryField[] entityArray;
    
    public String getFirstEntityAlias() {
        return firstEntityAlias;
    }
    public PageQueryField[] getPageQueryFields() {
        return pageQueryFields;
    }
    public PageQueryField[] getAllEntities(){
        return entityArray;
    }
    public String getAliasList() {
        return aliasList;
    }
}
