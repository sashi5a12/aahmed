package com.netpace.aims.controller.system;

import org.apache.log4j.Logger;

/**
 * @struts.form name="EmailGroupEntity"
 */
public class EmailGroupEntity
{

    static Logger log = Logger.getLogger(EmailGroupEntity.class.getName());

    private java.lang.String entityId;
    private java.lang.String entityName;
    private java.lang.String entityDesc;

    public java.lang.String getEntityId()
    {
        return this.entityId;
    }

    public void setEntityId(java.lang.String entityId)
    {
        this.entityId = entityId;
    }

    public java.lang.String getEntityName()
    {
        return this.entityName;
    }

    public void setEntityName(java.lang.String entityName)
    {
        this.entityName = entityName;
    }

    public java.lang.String getEntityDesc()
    {
        return this.entityDesc;
    }

    public void setEntityDesc(java.lang.String entityDesc)
    {
        this.entityDesc = entityDesc;
    }

}
