package com.netpace.aims.bo.workflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.netpace.aims.bo.application.BrewApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.dataaccess.valueobjects.WorkitemVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.workflow.AimsModuleWorkflows;
import com.netpace.aims.model.workflow.AimsWorkflowDetails;
import com.netpace.aims.model.workflow.AimsWorkitem;
import com.netpace.aims.model.workflow.OsWfentry;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.Utility;
import com.netpace.aims.workflow.MyBasicWorkflow;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;

/**
 * @author Adnan Ahmed
 *
 * @Date 20-Nov-2008
 *  
 */
public class WorkflowManager {
	
	private static final Logger log = Logger.getLogger(WorkflowManager.class.getName());
	
	
	public static boolean isWorkflowExists(Long recordId, Long workflowDetailId) throws HibernateException {
		log.debug("WorkflowManager.IsCampaignWorkflowExists: Start");
		log.debug("recordId: "+recordId);
		log.debug("workflowDetailId: "+workflowDetailId);
		Session session=null;
		boolean flag=false;
		try{
            session=DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append(" select ")
                .append("          module.moduleWorkflowId")
                .append(" from ")
                .append("          AimsWorkflowDetails	as detail, ")
                .append("          AimsModuleWorkflows	as module ")
                .append(" where ")
                .append("          detail.workflowDetailId = module.workflowDetail.workflowDetailId ")
                .append("          and	module.moduleRecordId = :recordId ")
                .append("          and	module.status = :status ")
            	.append("          and	detail.workflowDetailId = :workflowDetailId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("recordId", recordId.longValue());
            query.setString("status", AimsConstants.WORKFLOW_STARTED);
            query.setLong("workflowDetailId", workflowDetailId.longValue());
            
            List list=query.list();
            if (list != null && list.size() >0){
            	flag=true;
            }
        }
        catch (HibernateException e){
            e.printStackTrace();
            throw e;
        }
        finally {
        	if (session != null){
        		session.close();
        	}
        }
        log.debug("WorkflowManager.IsCampaignWorkflowExists: End "+flag);
		return flag;
	}
	
	public static void initiateWorkflow(Long recordId, Long workflowDetailId, String createdBy, Date createdDate) throws HibernateException, AimsException{
		
		log.debug("WorkflowManager.initiateWorkflow: Start");
		log.debug("recordId="+recordId);
		log.debug("workflowDetailId="+workflowDetailId);
		log.debug("createdBy="+createdBy);
		log.debug("createdDate="+createdDate);
		Session session=null;
		Transaction trx=null;
		AimsException aimsException=new AimsException();
                
        long id=0;    	
    	HashMap hs = new HashMap();    	    	
    	if (isWorkflowExists(recordId, workflowDetailId) == true){
    		log.debug("Workflow already initiated for "+recordId +" and workflowDetailId="+workflowDetailId);
    		return;
    	}
    	
    	try {
    		Set workitemSet=new HashSet();
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			AimsWorkflowDetails workflowDetail=(AimsWorkflowDetails)session.load(AimsWorkflowDetails.class, workflowDetailId);
			
			BasicWorkflow wf = new BasicWorkflow("User");
			hs.put("workflowDetailId",String.valueOf(workflowDetailId));
			hs.put("recordId",String.valueOf(recordId));
			hs.put("createdBy",createdBy);
			hs.put("createdDate",createdDate);
			hs.put("modifiedBy",createdBy);
			hs.put("modifiedDate",createdDate);			
			id = wf.initialize(workflowDetail.getWorkflowName(), 100, hs);
			WorkflowDescriptor wd = wf.getWorkflowDescriptor(wf.getWorkflowName(id));

			OsWfentry oswf = new OsWfentry();
			oswf.setId(new Long(id));
			
			AimsModuleWorkflows moduleWorkflow=new AimsModuleWorkflows();
			moduleWorkflow.setModuleRecordId(recordId);
			moduleWorkflow.setStatus(AimsConstants.WORKFLOW_STARTED);
			moduleWorkflow.setCreatedBy(createdBy);
			moduleWorkflow.setCreatedDate(createdDate);
			moduleWorkflow.setModifiedBy(createdBy);
			moduleWorkflow.setModifiedDate(createdDate);
			moduleWorkflow.setWorkflowDetail(workflowDetail);
			moduleWorkflow.setWorkflowId(oswf);			
			moduleWorkflow.setAimsWorkitems(workitemSet);			
						
			List steps = wf.getCurrentSteps(id);
			
			for (int a = 0; a < steps.size(); a++) {
				Step step = (Step) steps.get(a);
				AimsWorkitem workitem = new AimsWorkitem();
				
				workitem.setWorkflowStateId(new Long(step.getStepId()));
				workitem.setStepName(wd.getStep(step.getStepId()).getName());
				workitem.setEntryDate(createdDate);
				workitem.setActionTaken(AimsConstants.WORKFLOW_NO_ACTION_TAKEN);
				workitem.setStatus(AimsConstants.WORKFLOW_UNDERWAY);
				workitem.setDescription(wd.getStep(step.getStepId()).getName());
				workitem.setPrivilege(step.getOwner());
				workitem.setCreatedBy(createdBy);
				workitem.setCreatedDate(createdDate);
				workitem.setModuleWorkflows(moduleWorkflow);
				workitemSet.add(workitem);
			}
			session.save(moduleWorkflow);
			trx.commit();
		} catch (HibernateException e) {
			log.error(e,e);
			if (trx!= null){
				trx.rollback();
			}
			throw e;
		} catch (InvalidRoleException e) {
			log.error(e,e);
			aimsException.addException(new AimsException("error.workflow.initialization"));
			if (trx!= null){
				trx.rollback();
			}			
    		throw aimsException;
		} catch (InvalidInputException e) {
			log.error(e,e);
			aimsException.addException(new AimsException("error.workflow.initialization"));
			if (trx!= null){
				trx.rollback();
			}			
    		throw aimsException;
		} catch (WorkflowException e) {
			log.error(e,e);
			aimsException.addException(new AimsException("error.workflow.initialization"));
			if (trx!= null){
				trx.rollback();
			}
    		throw aimsException;
		} finally {
        	if (session != null){
        		session.close();
        	}
        }
  	    	    	     	    			    
    	log.debug("WorkflowManager.initiateWorkflow: End");    	
	}
	
	public static AimsModuleWorkflows getModuleWorkflowByRecordIdAndStatus(Long recordId, Long  workflowDetailId, String status) throws HibernateException{
		log.debug("WorkflowManager.getModuleWorkflowByRecordIdAndStatus: Start");
		log.debug("recordId="+recordId); 
		log.debug("workflowDetailId="+workflowDetailId);
		log.debug("status="+status);
		Session session=null;
		AimsModuleWorkflows module=null;
		try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery("from AimsModuleWorkflows module where module.moduleRecordId=:recId and module.workflowDetail.workflowDetailId = :detailId and module.status= :status");
			query.setLong("recId", recordId.longValue());
			query.setLong("detailId", workflowDetailId.longValue());
			query.setString("status", status);
			List list=query.list();
			if (list != null && list.size()>0){
				log.debug("Record found.");
				module=(AimsModuleWorkflows)list.get(0);
			}
		} catch (HibernateException e) {		
			log.error(e,e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.getModuleWorkflowByRecordIdAndStatus: End");
		return module;
	}

	public static void saveWorkItem(AimsWorkitem workItem) throws HibernateException{
		log.debug("WorkflowManager.saveWorkItem: Start");
		
		Session session=null;
		Transaction trx=null;
		try {
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			session.saveOrUpdate(workItem);
			trx.commit();
		} catch (HibernateException e) {		
			log.error(e,e);
			if (trx != null){
				trx.rollback();
			}
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.saveWorkItem: End");
	}

	public static void saveModuleWorkflow(AimsModuleWorkflows moduleWorkflow) throws HibernateException{
		log.debug("WorkflowManager.saveModuleWorkflow Start");
		Session session=null;
		Transaction trx=null;
		try {
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			session.saveOrUpdate(moduleWorkflow);
			trx.commit();
		} catch (HibernateException e) {		
			log.error(e,e);
			if (trx != null){
				trx.rollback();
			}
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.saveModuleWorkflow End");
	}
	
	public static List getWorkList(int pageNo, 
			   int pageLength, 
			   int totalRecord, 
			   String filterField, 
			   String filterText, 
			   String selectedFilterTypes, 
			   String selectedFilterWorkItems, 
			   String sortBy,
			   String sortOrder,
			   AimsUser user) throws HibernateException, SQLException{
		log.debug("WorkflowManager.getWorkList Start:");
		Session session=null;
		Connection conn=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		List list=new ArrayList();
		CommonProperties commonProps = CommonProperties.getInstance();
		
		BasicWorkflow wf = new BasicWorkflow("User");
		try {
			if (StringUtils.isNotEmpty(filterText)){
				filterText=Utility.replace(filterText, "_", "~_", Utility.REPLACE_ALL);				
				filterText=Utility.replace(filterText, "%", "~%", Utility.REPLACE_ALL);				
			}
			StringBuffer query=new StringBuffer();
			query.append("SELECT b.*");
			query.append("  FROM (SELECT a.*, ROWNUM rnum");
			query.append("          FROM (SELECT   wi.workitem_id, MOD.workflow_id, workflow_state_id, wi.step_name,");
			query.append("                         p.platform_name, p.platform_id,  app.title, al.company_name,");
			query.append("                         wi.entry_date, MOD.module_record_id, MOD.workflow_detail_id ");
			query.append("                    FROM aims_workitem wi,");
			query.append("                         aims_module_workflows MOD,");
			query.append("                         os_wfentry wfentry,");
			query.append("                         aims_apps app,");
			query.append("                         aims_alliances al,");
			query.append("                         aims_platforms p");
			query.append("                   WHERE wi.module_workflow_id = MOD.module_workflow_id");
			query.append("                     AND MOD.workflow_id = wfentry.ID");
			query.append("                     AND MOD.module_record_id = app.apps_id");
			query.append("                     AND app.platform_id = p.platform_id");
			query.append("                     AND app.alliance_id = al.alliance_id");
			query.append("                     AND wi.status = ?");
			query.append("                     AND MOD.status = ?");
			query.append("                     AND wfentry.ID IN (SELECT entry_id");
			query.append("                                          FROM os_currentstep");
			query.append("                                         WHERE step_id = wi.workflow_state_id)");			
			query.append("		     		   AND wi.PRIVILEGE IN ( SELECT UPPER (p.privilege_key)"); 
			query.append("	  	   		  								FROM aims_user_roles ur, aims_sys_roles r, ");
			query.append("	  	   		  									 aims_role_privileges rp, aims_sys_privileges p");
			query.append("												WHERE ur.role_id = r.role_id");
			query.append("												AND rp.role_id = r.role_id");
			query.append("												AND rp.privilege_id = p.privilege_id");
			query.append("												AND ur.user_id = ?)");

			if (StringUtils.isNotEmpty(selectedFilterTypes)){
				query.append("		AND p.platform_id in (").append(selectedFilterTypes).append(")");
			}
			if (StringUtils.isNotEmpty(selectedFilterWorkItems)){
				query.append("		AND wi.workflow_state_id in (").append(selectedFilterWorkItems).append(")");
			}
			if (StringUtils.isNotEmpty(filterField) && StringUtils.isNotEmpty(filterText)){
				query.append(" 		AND UPPER(").append(filterField).append(") LIKE ?");
				query.append(" escape('~') ");
			}
			if (sortBy.indexOf("date") != -1){
				query.append("            ORDER BY ").append(sortBy).append(" ").append(sortOrder);
			}
			else{
				query.append("            ORDER BY lower(").append(sortBy).append(") ").append(sortOrder);
			}
			
			query.append("										  ) a) b");
			query.append(" WHERE rnum BETWEEN ? AND ?");
			
			
			if (pageNo < 1) 
            	pageNo = 1;
			else if (pageNo > (new Double(Math.ceil(totalRecord * 1.0/ pageLength)).intValue())) 
            	pageNo = new Double(Math.ceil(totalRecord * 1.0/ pageLength)).intValue();
			
			int fromRowNum = ((pageNo - 1) * pageLength) + 1;
			int toRowNum   = fromRowNum -1 + pageLength;
			
			log.debug("pageNo= "+pageNo);
			log.debug("fromRowNum="+fromRowNum);
			log.debug("toRowNum="+toRowNum);
			log.debug("Param-1="+AimsConstants.WORKFLOW_UNDERWAY);
			log.debug("Param-2="+AimsConstants.WORKFLOW_STARTED);
			log.debug("userId-3="+user.getUserId().longValue());
			log.debug("filterField="+ filterField);
			log.debug("filterText="+filterText);
			log.debug("selectedFilterTypes="+selectedFilterTypes);
			log.debug("selectedFilterWorkItems="+selectedFilterWorkItems); 
			
			log.debug("Query: "+query.toString());
			
			session=DBHelper.getInstance().getSession();
			conn=session.connection();
			prepStmt=conn.prepareStatement(query.toString());
			prepStmt.setString(1, AimsConstants.WORKFLOW_UNDERWAY);
			prepStmt.setString(2, AimsConstants.WORKFLOW_STARTED);
			prepStmt.setLong(3, user.getUserId().longValue());
			if (StringUtils.isNotEmpty(filterField) && StringUtils.isNotEmpty(filterText)){				
				prepStmt.setString(4, "%"+filterText.toUpperCase().trim()+"%");
				prepStmt.setInt(5, fromRowNum);
				prepStmt.setInt(6, toRowNum);
			}
			else {
				prepStmt.setInt(4, fromRowNum);
				prepStmt.setInt(5, toRowNum);
			}
			rs=prepStmt.executeQuery();
			while(rs.next()){
				WorkitemVO vo=new WorkitemVO();
				vo.setWorkitemId(new Long(rs.getLong("workitem_id")));
				vo.setWorkflowId(new Long(rs.getLong("workflow_id")));
				vo.setWorkItem(rs.getString("step_name"));
				vo.setType(rs.getString("platform_name"));
                vo.setPlatformId(new Long(rs.getLong("platform_id")));
                vo.setDetail(rs.getString("title"));
				vo.setCompany(rs.getString("company_name"));
				vo.setStartDate(Utility.convertToString(rs.getDate("entry_date"), AimsConstants.DATE_FORMAT) );
				vo.setRecordId(new Long(rs.getLong("module_record_Id")));
				vo.setRownum(rs.getLong("rnum")-1);
				vo.setStateId(new Long(rs.getLong("workflow_state_id")));
				vo.setWorkFlowDetailId(new Long(rs.getLong("workflow_detail_id")));
				vo.setWorkItemDispTitle(Utility.getEllipseString(25,rs.getString("title")));
				//Default setValidateStep to false, for BREW and other platforms.
				vo.setValidateStep(Boolean.FALSE);
				
				if ( vo.getWorkFlowDetailId().equals(AimsConstants.WORKFLOW_JAVA_ONDECK_APP) )
				{
					if ( commonProps.getProperty("java.ondeck.workflow.validate.steps")!=null && commonProps.getProperty("java.ondeck.workflow.validate.steps").indexOf(vo.getStateId().toString())!=-1 ) 
						vo.setValidateStep(Boolean.TRUE);
					else
						vo.setValidateStep(Boolean.FALSE);
				}
				if ( vo.getWorkFlowDetailId().equals(AimsConstants.WORKFLOW_JAVA_OFFDECK_APP) )
				{
					if ( commonProps.getProperty("java.offdeck.workflow.validate.steps")!=null && commonProps.getProperty("java.offdeck.workflow.validate.steps").indexOf(vo.getStateId().toString())!=-1 ) 
						vo.setValidateStep(Boolean.TRUE);
					else
						vo.setValidateStep(Boolean.FALSE);
				}
				/*int[] actions = wf.getAvailableActions(vo.getWorkflowId().intValue(), null);
			    WorkflowDescriptor wd =  wf.getWorkflowDescriptor(wf.getWorkflowName(vo.getWorkflowId().intValue()));
			    Vector actionList=new Vector();
			    actionList.add(new LabelValueBean("Select an Action" , "0"));
			    for (int i = 0; i < actions.length; i++) {
			        String actionName = wd.getAction(actions[i]).getName();
			        int actionId = actions[i];
			        actionList.add(new LabelValueBean(actionName, String.valueOf(actionId)));
			    }
			    vo.setActions(actionList);*/
				vo.setActions(getWorkItemActions(vo.getWorkflowId().longValue(), rs.getInt("workflow_state_id")));
				list.add(vo);
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (rs != null){
				rs.close();
			}
			if (prepStmt != null){
				prepStmt.close();
			}
			if(session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.getWorkflowList: End"+list.size());
		return list;
	}
	
	/*
	 * stepId is null if list of all the current work items are needed
	 * stepId is not null is any particular step is required
	 */
    public static List getCurrentWorkItemsForApplication(Long appsId, Long platformId, AimsUser user, Long stepId, boolean checkPrivilege) throws HibernateException, SQLException{	
		log.debug("WorkflowManager.getCurrentWorkItemsForApplication Start:");
		Session session=null;
		Connection conn=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		List list=new ArrayList();
		
		CommonProperties commonProps = CommonProperties.getInstance();
		BasicWorkflow wf = new BasicWorkflow("User");
		try {
            int parameterIndex = 1;
			StringBuffer query=new StringBuffer();
			query.append(" SELECT   wi.workitem_id, MOD.workflow_id, workflow_state_id, wi.step_name,");
			query.append("                         p.platform_name, p.platform_id,  app.title, al.company_name,");
			query.append("                         wi.entry_date, MOD.module_record_id, MOD.workflow_detail_id ");
			query.append("                    FROM aims_workitem wi,");
			query.append("                         aims_module_workflows MOD,");
			query.append("                         os_wfentry wfentry,");
			query.append("                         aims_apps app,");
			query.append("                         aims_alliances al,");
			query.append("                         aims_platforms p");
			query.append("                   WHERE wi.module_workflow_id = MOD.module_workflow_id");
			query.append("                     AND MOD.workflow_id = wfentry.ID");
			query.append("                     AND MOD.module_record_id = app.apps_id");
			query.append("                     AND app.platform_id = p.platform_id");
			query.append("                     AND app.alliance_id = al.alliance_id");
            query.append("                     AND app.apps_id = ? ");
            query.append("                     AND wi.status = ? ");
			query.append("                     AND MOD.status = ? ");
			query.append("                     AND wfentry.ID IN (SELECT entry_id");
			query.append("                                          FROM os_currentstep");
			query.append("                                         WHERE step_id = wi.workflow_state_id)");
            if(checkPrivilege) {
                query.append("		     		   AND wi.PRIVILEGE IN ( SELECT UPPER (p.privilege_key)");
                query.append("	  	   		  								FROM aims_user_roles ur, aims_sys_roles r, ");
                query.append("	  	   		  									 aims_role_privileges rp, aims_sys_privileges p");
                query.append("												WHERE ur.role_id = r.role_id");
                query.append("												AND rp.role_id = r.role_id");
                query.append("												AND rp.privilege_id = p.privilege_id");
                query.append("												AND ur.user_id = ?)");
            }
            query.append("                    AND p.platform_id = ? ");
			if ( stepId != null ) {
				query.append("                    AND workflow_state_id = ? ");
            }
            query.append("		ORDER BY wi.workitem_id ");

            log.debug("appsId-1 = "+appsId);
            log.debug("Param-2 = "+AimsConstants.WORKFLOW_UNDERWAY);
			log.debug("Param-3 = "+AimsConstants.WORKFLOW_STARTED);
            log.debug("checkPrivilege = "+checkPrivilege);
            log.debug("userId = " + user.getUserId().longValue());
            log.debug("platform = " + platformId.longValue());
            if(stepId != null) {
                log.debug("stepId = " + stepId.longValue());
            }

            log.debug("Query: " + query.toString());

			session=DBHelper.getInstance().getSession();
			conn=session.connection();
			prepStmt=conn.prepareStatement(query.toString());
			prepStmt.setLong(parameterIndex++, appsId.longValue());
            prepStmt.setString(parameterIndex++, AimsConstants.WORKFLOW_UNDERWAY);
			prepStmt.setString(parameterIndex++, AimsConstants.WORKFLOW_STARTED);
            if(checkPrivilege) {
                prepStmt.setLong(parameterIndex++, user.getUserId().longValue());
            }
            prepStmt.setLong(parameterIndex++, platformId.longValue());
            if(stepId != null) {
                prepStmt.setLong(parameterIndex++, stepId.longValue());
            }

            rs=prepStmt.executeQuery();
			while(rs.next()){
				WorkitemVO vo=new WorkitemVO();
				vo.setWorkitemId(new Long(rs.getLong("workitem_id")));
				vo.setWorkflowId(new Long(rs.getLong("workflow_id")));
				vo.setWorkItem(rs.getString("step_name"));
				vo.setType(rs.getString("platform_name"));
                vo.setPlatformId(new Long(rs.getLong("platform_id")));
                vo.setDetail(rs.getString("title"));
				vo.setCompany(rs.getString("company_name"));
				vo.setStartDate(Utility.convertToString(rs.getDate("entry_date"), AimsConstants.DATE_FORMAT) );
				vo.setRecordId(new Long(rs.getLong("module_record_Id")));
				vo.setStateId(new Long(rs.getLong("workflow_state_id")));
				vo.setWorkFlowDetailId(new Long(rs.getLong("workflow_detail_id")));
				//vo.setRownum(rs.getLong("rnum")-1);

				vo.setActions(getWorkItemActions(vo.getWorkflowId().longValue(), rs.getInt("workflow_state_id")));
				list.add(vo);
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (rs != null){
				rs.close();
			}
			if (prepStmt != null){
				prepStmt.close();
			}
			if(session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.getCurrentWorkItemsForApplication: End"+list.size());
		return list;
	}

	private static Vector getWorkItemActions(long wfentryId, int sId) {

		MyBasicWorkflow mwf = new MyBasicWorkflow("User");
		Vector wfactions = new Vector();
		HashMap actionMap = mwf.getAvailableActionsByStep(wfentryId, null);
		Set stepKeys = actionMap.keySet();
		Iterator iter = stepKeys.iterator();
		WorkflowDescriptor wd = mwf.getWorkflowDescriptor(mwf.getWorkflowName(wfentryId));

		wfactions.add(new LabelValueBean("Select an Action" , "0"));
		while (iter.hasNext()) {
			Integer stepId = (Integer) iter.next();
			if (sId == stepId.intValue()) {
				ArrayList l = (ArrayList) actionMap.get(stepId);
				for (int m = 0; m < l.size(); m++) {
					Integer aid = (Integer) l.get(m);
					String actionName = wd.getAction(aid.intValue()).getName();
					wfactions.add(new LabelValueBean(actionName, String.valueOf((Integer) l.get(m))));
				}
			}
		}

		return wfactions;
	}
	public static int getWorkListCount(String filterField, 
			   String filterText, 
			   String selectedFilterTypes, 
			   String selectedFilterWorkItems, 
			   String sortBy,
			   String sortOrder,
			   AimsUser user) throws HibernateException, SQLException{
		log.debug("WorkflowManager.getWorkListCount Start:");
		Session session=null;
		Connection conn=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		int totalRecords=0;
		try {
			
			if (StringUtils.isNotEmpty(filterText)){
				filterText=Utility.replace(filterText, "_", "~_", Utility.REPLACE_ALL);				
				filterText=Utility.replace(filterText, "%", "~%", Utility.REPLACE_ALL);				
			}

			StringBuffer query=new StringBuffer();
			query.append("SELECT COUNT (*) count");
			query.append("  FROM (SELECT wi.workitem_id, MOD.workflow_id, wi.step_name, p.platform_name,");
			query.append("               app.title, al.company_name, wi.entry_date,");
			query.append("               MOD.module_record_id");
			query.append("          FROM aims_workitem wi,");
			query.append("               aims_module_workflows MOD,");
			query.append("               os_wfentry wfentry,");
			query.append("               aims_apps app,");
			query.append("               aims_alliances al,");
			query.append("               aims_platforms p");
			query.append("         WHERE wi.module_workflow_id = MOD.module_workflow_id");
			query.append("           AND MOD.workflow_id = wfentry.ID");
			query.append("           AND MOD.module_record_id = app.apps_id");
			query.append("           AND app.platform_id = p.platform_id");
			query.append("           AND app.alliance_id = al.alliance_id");
			query.append("           AND wi.status = ?");
			query.append("           AND MOD.status = ?");
			query.append("           AND wfentry.ID IN (SELECT entry_id FROM os_currentstep WHERE step_id = wi.workflow_state_id)");
			query.append("		     AND wi.PRIVILEGE IN ( SELECT UPPER (p.privilege_key)"); 
			query.append("	  	   		  							FROM aims_user_roles ur, aims_sys_roles r, ");
			query.append("	  	   		  								 aims_role_privileges rp, aims_sys_privileges p");
			query.append("											WHERE ur.role_id = r.role_id");
			query.append("											AND rp.role_id = r.role_id");
			query.append("											AND rp.privilege_id = p.privilege_id");
			query.append("											AND ur.user_id = ?)");
			if (StringUtils.isNotEmpty(selectedFilterTypes)){
				query.append("		AND p.platform_id in (").append(selectedFilterTypes).append(")");
			}
			if (StringUtils.isNotEmpty(selectedFilterWorkItems)){
				query.append("		AND wi.workflow_state_id in (").append(selectedFilterWorkItems).append(")");
			}			
			if (StringUtils.isNotEmpty(filterField) && StringUtils.isNotEmpty(filterText)){
				query.append(" 		AND UPPER(").append(filterField).append(") LIKE ?");
				query.append(" escape('~') ");
			}
			if (sortBy.indexOf("date") != -1){
				query.append("            ORDER BY ").append(sortBy).append(" ").append(sortOrder);
			}
			else{
				query.append("            ORDER BY lower(").append(sortBy).append(") ").append(sortOrder);
			}

			query.append(")");

			log.debug("Param-1="+AimsConstants.WORKFLOW_UNDERWAY);
			log.debug("Param-2="+AimsConstants.WORKFLOW_STARTED);
			log.debug("userId-3="+user.getUserId().longValue());
			log.debug("filterField="+ filterField);
			log.debug("filterText="+filterText);
			log.debug("selectedFilterTypes="+selectedFilterTypes);
			log.debug("selectedFilterWorkItems="+selectedFilterWorkItems); 			
			log.debug("Query: "+query.toString());			

			session=DBHelper.getInstance().getSession();
			conn=session.connection();
			prepStmt=conn.prepareStatement(query.toString());
			prepStmt.setString(1, AimsConstants.WORKFLOW_UNDERWAY);
			prepStmt.setString(2, AimsConstants.WORKFLOW_STARTED);
			prepStmt.setLong(3, user.getUserId().longValue());
			if (StringUtils.isNotEmpty(filterField) && StringUtils.isNotEmpty(filterText)){				
				prepStmt.setString(4, "%"+filterText.toUpperCase().trim()+"%");
			}
			
			rs=prepStmt.executeQuery();
			while(rs.next()){
				totalRecords=rs.getInt("count");
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (rs != null){
				rs.close();
			}
			if (prepStmt != null){
				prepStmt.close();
			}
			if(session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.getWorkListCount: End"+totalRecords);
		return totalRecords;
	}
	
	public static AimsWorkitem getWorkitemById(Long workitemId) throws HibernateException{
		log.debug("WorkflowManager.getWorkitemById: Start");
		log.debug("workitemId="+workitemId);
		Session session=null;
		AimsWorkitem workitem=new AimsWorkitem();
		try {
			session=DBHelper.getInstance().getSession();
			workitem=(AimsWorkitem)session.load(AimsWorkitem.class, workitemId);
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.getWorkitemById: End");
		return workitem;
	}

	public static void updateWorkitemByStateId(Long stateId, Long recordId) throws HibernateException{
		log.debug("WorkflowManager.updateWorkitemByStateId: Start");
		log.debug("stateId="+stateId);
		Session session=null;
		Transaction trx=null;
		try {
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			Query query=session.createQuery("from AimsWorkitem wi where wi.workflowStateId=:stateId and wi.status=:status and wi.moduleWorkflows.moduleRecordId=:recordId");
			query.setLong("stateId", stateId.longValue());
			query.setString("status", AimsConstants.WORKFLOW_UNDERWAY);
			query.setLong("recordId", recordId.longValue());
			List list=query.list();
			for (int i=0; i<list.size(); i++){
				AimsWorkitem workitem=(AimsWorkitem)list.get(i);
				workitem.setStatus(AimsConstants.WORKFLOW_ABANDON);
				workitem.setModifiedBy("system");
				workitem.setModifiedDate(new Date());
				session.saveOrUpdate(workitem);
			}						
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null){
				trx.rollback();
			}
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.updateWorkitemByStateId: End");
	}
	
	
	
	public static boolean isActionExecuted(Long workflowId, Long stepId) throws HibernateException{
		log.debug("WorkflowManager.isActionExecute: Start");
		boolean flag=true;
		Session session=null;
		try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery("from AimsWorkitem wi where wi.moduleWorkflows.workflowId.id=:workflowId and wi.workflowStateId=:stepId and wi.status=:status");
			query.setLong("workflowId", workflowId.longValue());
			query.setLong("stepId", stepId.longValue());
			query.setString("status", AimsConstants.WORKFLOW_UNDERWAY);
			List list=query.list();
			if (list != null && list.size()>0){
				flag=false;
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("flag= "+flag);		
		log.debug("WorkflowManager.isActionExecute: End");
		return flag;
	}
	
	public static AimsModuleWorkflows getModuleWorkflowByWorkflowId(long workflowId) throws HibernateException{
		log.debug("WorkflowManager.getModuleWorkflowByWorkflowId Start:");
		Session session=null;
		AimsModuleWorkflows mod=null;
		try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery("from AimsModuleWorkflows mod where mod.workflowId.id=:id");
			query.setLong("id", workflowId);
			mod=(AimsModuleWorkflows)query.list().get(0);
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.getModuleWorkflowByWorkflowId End:");
		return mod;
	}
	
	public static List getHistory(Long recordId) throws HibernateException{
		log.debug("WorkflowManager.getHistory Start:");
		List historyList=new ArrayList();
		Session session=null;
		try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery("from AimsWorkitem wi where wi.moduleWorkflows.moduleRecordId=:id order by wi.workitemId desc");
			query.setLong("id", recordId.longValue());
			historyList=query.list();
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		log.debug("WorkflowManager.getHistory End:");
		return historyList;
	}
	public static void main(String args[]){
		DBHelper dbHelper=null;
		try {
			ConfigEnvProperties props= ConfigEnvProperties.getInstance();
			Configuration conf =new Configuration();
			dbHelper=DBHelper.getInstance();
			conf.setProperty("hibernate.connection.url", props.getProperty("connection.url"));
			conf.setProperty("hibernate.connection.username", props.getProperty("connection.username"));
			conf.setProperty("hibernate.connection.password", props.getProperty("connection.password"));
			dbHelper.sessionFactory=conf.configure().buildSessionFactory();
			log.debug(BrewApplicationManager.getAggregators());
		} catch (Exception e) {
			log.error(e,e);
			e.printStackTrace();
		} finally {
			if (dbHelper != null){
				try {
					dbHelper.sessionFactory.close();
					dbHelper=null;
				} catch (HibernateException he){
					log.debug("Error occured while closing the session factory");
					log.debug(he,he);
				}
			}
		}
	}
}