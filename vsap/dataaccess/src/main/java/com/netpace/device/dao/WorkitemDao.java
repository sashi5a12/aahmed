package com.netpace.device.dao;

import com.netpace.device.entities.Workitem;
import com.netpace.device.utils.enums.WorkflowStep;
import com.netpace.device.utils.enums.WorkitemStatus;
import com.netpace.device.vo.PageModel;
import java.util.Date;
import java.util.List;

public interface WorkitemDao extends GenericDao<Workitem, Integer> {

    public static final String name = "workitemDao";

    public Workitem getWorkitem(String title, WorkitemStatus status, Integer workflowId);

    public List<Workitem> getPartnerWorkitems(Integer companyId, WorkitemStatus status, String[] userRoles);

    public PaggedResult<Workitem> getPaggedWorkitems(WorkitemStatus status, String[] userRoles, PageModel pageModel, Integer companyId, boolean isPartner);
    
    public List<Workitem> getProductWorkItemsByTitle(String title, Integer productId, String op) ;
    
    public List<Workitem> getProductWorkitems(Integer productId, WorkitemStatus status, String[] userRoles);
    
    public List<Workitem> getPartnerWorkItemsByTitle(Integer companyId, WorkflowStep title);
    
    public List<Workitem> getDelayedWorkitems(Date olderThanDate);
}
