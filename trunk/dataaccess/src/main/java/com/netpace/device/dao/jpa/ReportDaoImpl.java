package com.netpace.device.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.netpace.device.dao.ReportDao;
import com.netpace.device.entities.VapProduct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(ReportDao.name)
public class ReportDaoImpl extends GenericDaoJpaImpl<VapProduct, Integer> implements ReportDao {

    private static final Log log = LogFactory.getLog(ReportDaoImpl.class);

    public ReportDaoImpl() {
        super(VapProduct.class);
    }
    @Override
	public List<Object[]> getAllProductsDetailForCSV() {
		log.debug("ReportDaoImpl.getAllProductsDetailForCSV start: ----------------");		
		
		StringBuilder qryStr=new StringBuilder();
		
		qryStr.append("SELECT ");
		qryStr.append("    company_name, ");
		qryStr.append("    product_name, ");
		qryStr.append("    product_type, ");
		qryStr.append("    description, ");
		qryStr.append("    model_number, ");
		qryStr.append("    part_number, ");
		qryStr.append("    product_category, ");
		qryStr.append("    IF(STATUS='Approved','Approved',product_work_flow_steps(product_id,'')),  ");
		qryStr.append("    DATE_FORMAT(DATE(submitted_date), '%m-%d-%Y'), TIME_FORMAT(TIME(submitted_date),'%T'), ");
		qryStr.append("    get_work_item_end_date(product_id, 'DeviceMarketingReview'), ");
		qryStr.append("    get_work_item_end_date(product_id, 'ExportComplianceReview'), ");
		qryStr.append("    get_work_item_end_date(product_id, 'RequirementsGroupReview'), ");
		qryStr.append("    get_work_item_end_date(product_id, 'DeviceComplianceReview'), ");
		qryStr.append("    get_work_item_end_date(product_id, 'DeviceComplianceReviewEOT'), ");
		qryStr.append("    get_work_item_end_date(product_id, 'DeviceMarketingFinalReview') ");
		qryStr.append("FROM ");
		qryStr.append("    vap_products vp, ");
		qryStr.append("    vap_company comp ");
		qryStr.append("WHERE ");
		qryStr.append("    vp.company_id = comp.company_id AND vp.is_active='1'");
		qryStr.append("ORDER BY submitted_date, LOWER(product_name), LOWER(company_name) ");		
//		qryStr.append("LIMIT 1,10");
		
		Query qry=entityManager.createNativeQuery(qryStr.toString());
		List<Object[]> list=qry.getResultList();

		log.debug("ReportDaoImpl.getAllProductsDetailForCSV end: ----------------");
		return list;
	}
	
    @Override
	public List<Object[]> getAllCompleteTestingProductsForCSV() {
		log.debug("ReportDaoImpl.getAllCompleteTestingProductsForCSV start: ----------------");		
		
		StringBuilder qryStr=new StringBuilder();
		
		qryStr.append("SELECT * FROM ( ");
		qryStr.append("SELECT DISTINCT ");
		qryStr.append("	    company_name, ");
		qryStr.append("product_name, ");
		qryStr.append("	    description, ");
		qryStr.append("	    model_number, ");
		qryStr.append("	    part_number, ");
		qryStr.append("	    product_category, ");
		qryStr.append("	    get_work_item_end_date(vp.product_id, 'DeviceComplianceReview') testing_start_date, ");
		qryStr.append("	    get_work_item_end_date(vp.product_id, 'DeviceComplianceReviewEOT') testing_end_date ");
		qryStr.append("	FROM ");
		qryStr.append("	    vap_products vp, ");
		qryStr.append("	    vap_company comp, ");
		qryStr.append("	    vap_workflow wf, ");
		qryStr.append("	    vap_workitem wi ");
		qryStr.append("	WHERE ");
		qryStr.append("	    vp.company_id = comp.company_id ");
		qryStr.append("	        AND vp.product_id = wf.product_id ");
		qryStr.append("	        AND wf.workflow_id = wi.workflow_id ");
		qryStr.append("	        AND wf.workflow_type='Product Workflow' ");
		qryStr.append("	        AND (wi.action_taken IN ('Start Testing', 'End Testing')) AND vp.is_active='1' ");
		qryStr.append("	ORDER BY submitted_date , LOWER(product_name) , LOWER(company_name) ");
		qryStr.append(" ) AS t WHERE testing_start_date!='' AND testing_end_date!=''");
		
		Query qry=entityManager.createNativeQuery(qryStr.toString());
		List<Object[]> list=qry.getResultList();

		log.debug("ReportDaoImpl.getAllCompleteTestingProductsForCSV end: ----------------");
		return list;
	}
	
	 @Override
		public List<Object[]> getAllCompanyForCSV() {
			log.debug("ReportDaoImpl.getAllCompanyForCSV start: ----------------");		
			
			StringBuilder qryStr=new StringBuilder();

			qryStr.append("	select ");
			qryStr.append("	    company.company_name,");
			qryStr.append("	    work_flow_steps(company.company_id, ''), ");
			qryStr.append("	    DATE_FORMAT(DATE(company.created_date), '%m-%d-%Y'), ");
			qryStr.append("	    company.address1, ");
			qryStr.append("	    company.city, ");
			qryStr.append("	    company.state, ");
			qryStr.append("	    company.postal_code, ");
			qryStr.append("	    company.country, ");
			qryStr.append("	    sys_user.full_name, ");
			qryStr.append("	    sys_user.email_address, ");
			qryStr.append("	    sys_user.phone, ");
			qryStr.append("	    contact.email_address, ");
			qryStr.append("	    contact.phone ");
			qryStr.append("	from ");
			qryStr.append("	    vap_company company, ");
			qryStr.append("	    vap_contact contact, ");
			qryStr.append("	    vap_user sys_user, ");
			qryStr.append("	    vap_system_role sys_role, ");
			qryStr.append("	    vap_user_role user_role ");
			qryStr.append("	where ");
			qryStr.append("	    company.sales_contact_id = contact.contact_id ");
			qryStr.append("	        and company.company_id = sys_user.company_id ");
			qryStr.append("	        and sys_user.user_id = user_role.user_id ");
			qryStr.append("	        and sys_role.role_id = user_role.role_id ");
			qryStr.append("	        and sys_role.title = 'ROLE_MPOC' ");
			qryStr.append("	        and company.is_active = 1 ");
			qryStr.append("	order by lower(company.company_name) ");

			Query qry=entityManager.createNativeQuery(qryStr.toString());
			List<Object[]> list=qry.getResultList();

			log.debug("ReportDaoImpl.getAllCompanyForCSV end: ----------------");
			return list;
		}
		
	    @Override
		public List<Object[]> getUsersForCSV(boolean isWeekly) {
			log.debug("ReportDaoImpl.getUsersForCSV start: ----------------");		
			
			StringBuilder qryStr=new StringBuilder();

			qryStr.append("		SELECT DISTINCT ");
			qryStr.append("		    CONVERT( company.company_id , CHAR), ");
			qryStr.append("		    company.company_name, ");
			qryStr.append("		    company.address1, ");
			qryStr.append("		    company.city, ");
			qryStr.append("		    company.state, ");
			qryStr.append("		    company.postal_code, ");
			qryStr.append("		    company.country, ");
			qryStr.append("		    sys_user.full_name, ");
			qryStr.append("		    sys_user.address, ");
			qryStr.append("		    sys_user.city, ");
			qryStr.append("		    sys_user.state, ");
			qryStr.append("		    sys_user.postal_code, ");
			qryStr.append("		    sys_user.country ");
			qryStr.append("		FROM ");
			qryStr.append("		    vap_user sys_user, ");
			qryStr.append("		    vap_system_role sys_role, ");
			qryStr.append("		    vap_user_role user_role, ");
			qryStr.append("		    vap_company company ");
			qryStr.append("		WHERE ");
			qryStr.append("		    sys_user.user_id = user_role.user_id ");
			qryStr.append("		        AND sys_role.role_id = user_role.role_id ");
			qryStr.append("		        AND sys_user.company_id = company.company_id ");
			qryStr.append("		        AND sys_role.title = 'ROLE_PARTNER_USER' ");
			qryStr.append("		        AND sys_user.is_active = 1 ");
			if (isWeekly){
				qryStr.append("         AND (DATE(sys_user.created_date) >= CURDATE() - INTERVAL 7 DAY AND DATE(sys_user.created_date) < CURDATE())");
			}
			qryStr.append("		ORDER BY LOWER(company.company_name), LOWER(sys_user.full_name) ");

			Query qry=entityManager.createNativeQuery(qryStr.toString());
			List<Object[]> list=qry.getResultList();

			log.debug("ReportDaoImpl.getUsersForCSV end: ----------------");
			return list;
		}	
}
