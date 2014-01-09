/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.enums;

/**
 *
 * @author Hamza Ghayas
 */
public enum WORKLIST_PAGE_FIELDS implements PageQueryField{
    
    ALL_REQUIRED,
    WORKFLOW("Workflow","w","inner join",null,1),
    COMPANY("company","c","inner join",WORKFLOW,2),
    COMPANY_NAME("name",COMPANY),
    WORK_ITEM_NAME("workflowPhaseName"),
    ASSIGNEE_USER("assigneeUser","au","",WORKFLOW,3),
    ADMIN("fullName",ASSIGNEE_USER),
    STARTDATE("startDate"),
    ISRFI("isRfi");
    
    private String fieldName;
    private String alias;
    private String joinType;
    private WORKLIST_PAGE_FIELDS parentEntityAlias;
    private Integer order;
    private WORKLIST_PAGE_FIELDS field = null;
    
    WORKLIST_PAGE_FIELDS(){
    }
    
    WORKLIST_PAGE_FIELDS(String fieldName,WORKLIST_PAGE_FIELDS field){
        this.fieldName = fieldName;
        this.field = field;
    }
    WORKLIST_PAGE_FIELDS(String fieldName){
        this.fieldName = fieldName;
    }
    WORKLIST_PAGE_FIELDS(String fieldName,String alias,String joinType,
            WORKLIST_PAGE_FIELDS parentEntityAlias,  Integer sortOrder){
        this.fieldName = fieldName;
        this.alias = alias;
        this.joinType =joinType;
        this.parentEntityAlias = parentEntityAlias;
        this.order =sortOrder;
    }
    WORKLIST_PAGE_FIELDS(String fieldName,String alias,Integer sortOrder){
        this.fieldName = fieldName;
        this.alias = alias;
        this.order =sortOrder;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
    @Override
    public PageQueryField getField() {
        return field;
    }
    @Override
    public String getAlias(){
        return alias;
    }
    @Override
    public String getJoinType(){
        return joinType;
    }
    @Override
    public Integer getOrder(){
        return order;
    }
    @Override
    public String getParentEntityAlias() {
        if(parentEntityAlias != null)
            return parentEntityAlias.getAlias();
        
        return null;
    }
    @Override
    public String getFirstEntityAlias(){
        return WORKFLOW.getAlias();
    }
    @Override
    public PageQueryField[] getAllFields(){
        PageQueryField[] pageQueryFieldArray= new PageQueryField[5];
        
        pageQueryFieldArray[0]=WORKLIST_PAGE_FIELDS.WORK_ITEM_NAME;
        pageQueryFieldArray[1]=WORKLIST_PAGE_FIELDS.COMPANY_NAME;
        pageQueryFieldArray[2]=WORKLIST_PAGE_FIELDS.ADMIN;
        pageQueryFieldArray[3]=WORKLIST_PAGE_FIELDS.STARTDATE;
        pageQueryFieldArray[4]=WORKLIST_PAGE_FIELDS.ISRFI;
        
        return pageQueryFieldArray;
    }
    @Override
    public PageQueryField[] getAllEntities(){
        PageQueryField[] pageQueryFieldArray= new PageQueryField[3];
        
        pageQueryFieldArray[0]=WORKLIST_PAGE_FIELDS.WORKFLOW;
        pageQueryFieldArray[1]=WORKLIST_PAGE_FIELDS.COMPANY;
        pageQueryFieldArray[2]=WORKLIST_PAGE_FIELDS.ASSIGNEE_USER;
        
        return pageQueryFieldArray;
    }
    @Override
    public String getAliasList(){
        return WORKLIST_PAGE_FIELDS.WORKFLOW.getAlias();
    }
}