package com.netpace.device.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.dao.ProductDao;
import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.VapProductAttachment;
import com.netpace.device.entities.VapProductProcessInfo;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.SearchType;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.product.ProductListVO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(ProductDao.name)
public class ProductDaoJpaImpl extends GenericDaoJpaImpl<VapProduct, Integer> implements ProductDao {

    private static final Log log = LogFactory.getLog(ProductDaoJpaImpl.class);

    public ProductDaoJpaImpl() {
        super(VapProduct.class);
    }

	@Override
	public List<Object[]> getProductsForList(PageModel<ProductListVO> pageModel, Integer companyId, Boolean isVerizonUser) {
		log.debug("ProductDaoJpaImpl.getProductsForList start: ----------------");
		log.debug("pageModel: "+pageModel);
		log.debug("companyId: "+companyId);
		StringBuilder qryStr = new StringBuilder();
		List<String> categoryValues = null;
		List<String> workStepValues = null;
		String searchValue=pageModel.getEscapedSearchValue();
		
		if (pageModel.getFilters() != null && pageModel.getFilters().size() > 0) {
			categoryValues = VAPUtils.getFilterValues(pageModel.getFilters().get(0));
			
			if (pageModel.getFilters().get(1) != null){
				workStepValues = VAPUtils.getFilterValues(pageModel.getFilters().get(1));
			}
		}
		log.debug("categoryFilterValues: " + categoryValues);
		
		qryStr.append("select distinct vp.productId, vp.productName, vp.productType, vp.productCategory, vp.submittedDate, vp.status, vp.company.name, vp.workFlowSteps ");
		qryStr.append("   from VapProduct vp left join vp.workFlow wf left join wf.workItems wi");
		qryStr.append("   where vp.active='1' ");
		
		if(isVerizonUser==false){
			qryStr.append(" and vp.company.id=:companyId ");
		}

		if(isVerizonUser){
			qryStr.append(" and vp.submittedDate is not null ");
		}

		if (categoryValues != null && categoryValues.size() > 0) {
			qryStr.append(" and (vp.productCategory in (:categoryValues)) ");
		}

		if (workStepValues != null && workStepValues.size() > 0) {
			qryStr.append(" and ((wi.displayTitle in (:workStepValues)  and wi.status='InProgress')");
		}
		
		if (workStepValues!=null && workStepValues.size()>0 && workStepValues.contains("Approved")){
			qryStr.append(" or (wi.title in ('Approved')  and wi.status='Processed')");
		}
        if (workStepValues != null && workStepValues.size() > 0) {
        	qryStr.append(" )");
        }

		if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
			if (SearchType.Integer.name().equals(pageModel.getSearchType()) || SearchType.Date.name().equals(pageModel.getSearchType())){
				qryStr.append(" and ").append(pageModel.getSearchBy()).append(" = :searchValue ESCAPE '!'");
			}
			else {				
				qryStr.append(" and lower(").append(pageModel.getSearchBy()).append(") like :searchValue ESCAPE '!' ");
			}
		}
		
		if (pageModel.getSortBy().indexOf("date")>=0){
			qryStr.append("order by ").append(pageModel.getSortBy()).append(pageModel.isAscending() ? "asc" : "desc");			
		}
		else {
			qryStr.append("order by lower(").append(pageModel.getSortBy()).append(") ").append(pageModel.isAscending() ? "asc" : "desc");
		}

		Query query = entityManager.createQuery(qryStr.toString());

		if(isVerizonUser==false){
			query.setParameter("companyId", companyId);
		}
		
		if (categoryValues != null && categoryValues.size() > 0) {
			query.setParameter("categoryValues",categoryValues);
		}

		if (workStepValues != null && workStepValues.size() > 0) {
			query.setParameter("workStepValues",workStepValues);
		}
		
		if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
			if (SearchType.Integer.name().equals(pageModel.getSearchType()) || SearchType.Date.name().equals(pageModel.getSearchType())){
				query.setParameter("searchValue", searchValue.toLowerCase().trim());
			}
			else {				
				query.setParameter("searchValue", "%" + searchValue.toLowerCase().trim() + "%");
			}
		}

		query.setFirstResult(pageModel.firstResult());
		query.setMaxResults(pageModel.getPageSize());

		List<Object[]> products = new ArrayList<Object[]>();
		try {
			products = query.getResultList();
		} catch (NoResultException nre) {
			log.debug(nre.getMessage());
		}
		log.debug("ProductDaoJpaImpl.getProductsForList end: ----------------");
		return products;
	}
	
    @Override
    public Long getProductsCountForList(PageModel<ProductListVO> pageModel, Integer companyId, Boolean isVerizonUser) {
    	log.debug("ProductDaoJpaImpl.getProductsCountForList start: ----------------");
    	log.debug("pageModel: "+pageModel);
    	log.debug("companyId: "+companyId);
        Long count = new Long(0);
        StringBuilder qryStr = new StringBuilder();
        List<String> categoryValues=null;
        List<String> workStepValues = null;
        String searchValue=pageModel.getEscapedSearchValue();
        
        if (pageModel.getFilters() != null && pageModel.getFilters().size() > 0) {
			categoryValues = VAPUtils.getFilterValues(pageModel.getFilters().get(0));
			
			if (pageModel.getFilters().get(1) != null){
				workStepValues = VAPUtils.getFilterValues(pageModel.getFilters().get(1));
			}
		}
        log.debug("categoryValues: "+categoryValues);
       
		qryStr.append("select count(distinct vp.productId) ");
		qryStr.append("   from VapProduct vp left join vp.workFlow wf left join wf.workItems wi");
		qryStr.append("   where vp.active='1' ");

		if(isVerizonUser==false){
			qryStr.append(" and vp.company.id=:companyId ");
		}
		
		if(isVerizonUser){
			qryStr.append(" and vp.submittedDate is not null ");
		}

        if (categoryValues!= null && categoryValues.size()>0){
			qryStr.append(" and (vp.productCategory in (:categoryValues)) ");
        }
        
        if (workStepValues != null && workStepValues.size() > 0) {
			qryStr.append(" and ((wi.displayTitle in (:workStepValues)  and wi.status='InProgress')");
		}
        if (workStepValues!=null && workStepValues.size()>0 && workStepValues.contains("Approved")){
			qryStr.append(" or (wi.title in ('Approved')  and wi.status='Processed')");
		}
        if (workStepValues != null && workStepValues.size() > 0) {
        	qryStr.append(" )");
        }
        if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
			if (SearchType.Integer.name().equals(pageModel.getSearchType()) || SearchType.Date.name().equals(pageModel.getSearchType())){
				qryStr.append(" and ").append(pageModel.getSearchBy()).append(" = :searchValue ESCAPE '!' ");
			}
			else {				
				qryStr.append(" and lower(").append(pageModel.getSearchBy()).append(") like :searchValue ESCAPE '!' ");
			}
        }
        
        Query query = entityManager.createQuery(qryStr.toString());
        
		if(isVerizonUser==false){
			query.setParameter("companyId", companyId);
		}
        
        if (categoryValues!= null && categoryValues.size()>0){
            query.setParameter("categoryValues", VAPUtils.getFilterValueList(pageModel.getFilters()));
        }
        
        if (workStepValues != null && workStepValues.size() > 0) {
			query.setParameter("workStepValues",workStepValues);
		}
        
        if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
			if (SearchType.Integer.name().equals(pageModel.getSearchType()) || SearchType.Date.name().equals(pageModel.getSearchType())){
				query.setParameter("searchValue", searchValue.toLowerCase().trim());
			}
			else {				
				query.setParameter("searchValue", "%" + searchValue.toLowerCase().trim() + "%");
			}
        }
        try {
            count = (Long)query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        log.debug("ProductDaoJpaImpl.getProductsCountForList end: ----------------");
        return count;
    }	

	/*This method is for edit screen and is fully loaded object. Donn't use it for if you need minimal product information.*/
	@Override
	public VapProduct getProductForEdit(Integer productId, Integer companyId, Boolean isVerizonUser) {
		log.debug("ProductDaoJpaImpl.getProductForEdit start: ----------------");
		log.debug("companyId: "+companyId);
		log.debug("productId: "+productId);
		
		StringBuilder qryStr=new StringBuilder();
		qryStr.append(" from VapProduct vp left join fetch vp.attachments att left join fetch vp.supportedDevices where vp.productId=:productId and (att.tab is null or att.tab=1) and vp.active=1");
		if(isVerizonUser==false){
			qryStr.append("and vp.company.id=:companyId ");
		}
		Query qry=entityManager.createQuery(qryStr.toString());
		qry.setParameter("productId", productId);
		if (isVerizonUser==false){
			qry.setParameter("companyId", companyId);
		}
		VapProduct product=new VapProduct();
		product=(VapProduct)qry.getSingleResult();
		log.debug("ProductDaoJpaImpl.getProductForEdit end: ----------------");
		return product;
	}

    @Override
    public void inactiveProduct(Integer companyId, Integer productId, String userName){
    	log.debug("ProductDaoJpaImpl.inactiveProduct start: ----------------");
    	log.debug("companyId: "+companyId);
    	log.debug("productId: "+productId);
    	
    	/*StringBuilder qryStr=new StringBuilder();
    	qryStr.append("update VapProduct vp set vp.active=false where vp.productId=:productId ");
    	if (isVerizonUser==false){
    		qryStr.append("and vp.company.id=:companyId and vp.status=:status");
    	}
    	Query qry=entityManager.createQuery(qryStr.toString());
    	qry.setParameter("productId", productId);
    	if (isVerizonUser==false){
    		qry.setParameter("companyId", companyId);
    		qry.setParameter("status", ProductStatus.Saved.name());
    	}
    	int result=qry.executeUpdate();
    	*/
    	
    	if (companyId==null){
    		companyId=new Integer(0);
    	}
    	Query query = entityManager.createNativeQuery("{CALL delete_product(?,?,?)};");

        query.setParameter(1, productId);
        query.setParameter(2, companyId);
        query.setParameter(3, userName);

        int result=query.executeUpdate();
        
    	log.debug("ProductDaoJpaImpl.inactiveProduct record(s) marked as inactive: "+result);
    	log.debug("ProductDaoJpaImpl.inactiveProduct end: ------------------");
    }

	@Override
	public VapProductAttachment getAttachment(Integer productId, Integer mediaId) {
		log.debug("ProductDaoJpaImpl.getAttachment start: ----------------");
		Query qry=entityManager.createQuery(" from VapProductAttachment att where att.product.productId=:productId and att.media.mediaId=:mediaId ");
		log.debug("productId: "+productId);
		log.debug("mediaId: "+mediaId);

		qry.setParameter("productId", productId);
		qry.setParameter("mediaId", mediaId);
		VapProductAttachment product=new VapProductAttachment();
		product=(VapProductAttachment)qry.getSingleResult();
		log.debug("ProductDaoJpaImpl.getAttachment end: ----------------");
		return product;
	}

	@Override
	public void saveAttachment(VapProductAttachment attachment) {
		log.debug("ProductDaoJpaImpl.saveAttachment start: ----------------");
		log.debug("attachment: "+attachment);
		entityManager.persist(attachment);
		log.debug("ProductDaoJpaImpl.saveAttachment end: ----------------");
	}

	@Override
	public void deleteAttachment(Integer companyId, Integer productId, Integer mediaId, Boolean isVerizonUser){
		log.debug("ProductDaoJpaImpl.deleteAttachment start: ----------------");
		log.debug("companyId: "+companyId);
		log.debug("productId: "+productId);
		log.debug("mediaId: "+mediaId);
		StringBuilder qryStr=new StringBuilder();
		qryStr.append("delete VapProductAttachment att where  att.media.mediaId=:mediaId ");
		if(isVerizonUser==false){
			qryStr.append("and att.product.productId in (select productId from VapProduct vp where vp.company.id=:companyId and vp.productId=:productId) ");
		}
		Query qry=entityManager.createQuery(qryStr.toString());
		if (isVerizonUser==false){
			qry.setParameter("companyId", companyId);
			qry.setParameter("productId", productId);
		}
		qry.setParameter("mediaId", mediaId);
		int result=qry.executeUpdate();
    	log.debug("No of attachment deleted "+result);
		log.debug("ProductDaoJpaImpl.deleteAttachment end: ----------------");
	}
    
	@Override
	public VapProductProcessInfo getProcessingInfo(Integer processId){
		log.debug("ProductDaoJpaImpl.getProcessingInfo start: ----------------");
		log.debug("processId: "+processId);
		return entityManager.find(VapProductProcessInfo.class, processId);
	}

	@Override
	public void saveProcessingInfo(VapProductProcessInfo processingInfo) {
		log.debug("ProductDaoJpaImpl.saveProcessingInfo start: ----------------");
		log.debug("processingInfo: "+processingInfo);
		entityManager.merge(processingInfo);
		entityManager.flush();
		log.debug("ProductDaoJpaImpl.saveProcessingInfo end: ----------------");
	}

	@Override
	public VapProduct getProcessingInfoForEdit(Integer productId, Integer companyId, Boolean isVerizonUser) {
		log.debug("ProductDaoJpaImpl.getProcessingInfoForEdit end: ----------------");
		log.debug("companyId: "+companyId);
		log.debug("productId: "+productId);

		StringBuilder qryStr=new StringBuilder();
		qryStr.append(" from VapProduct vp left join fetch vp.attachments att left join fetch vp.processingInfo where vp.productId=:productId and vp.active=1");
		if(isVerizonUser==false){
			qryStr.append("and vp.company.id=:companyId ");
		}
		Query qry=entityManager.createQuery(qryStr.toString());
		qry.setParameter("productId", productId);
		if(isVerizonUser==false){
			qry.setParameter("companyId", companyId);
		}
		VapProduct product=new VapProduct();
		product=(VapProduct)qry.getSingleResult();
		log.debug("ProductDaoJpaImpl.getProcessingInfoForEdit end: ----------------");
		return product;
	}

	@Override
	public void updateProductStatus(Integer productId, ProductStatus status) {
		log.debug("ProductDaoJpaImpl.updateProductStatus start: ----------------");
		log.debug("productId: " + productId);

		Query query = entityManager.createQuery("update VapProduct vp set vp.status=:status where vp.productId=:productId ");
		query.setParameter("status", status.name());
		query.setParameter("productId", productId);

		int result = query.executeUpdate();

		log.debug("ProductDaoJpaImpl.updateProductStatus record(s) updated " + result);
		log.debug("ProductDaoJpaImpl.updateProductStatus end: ------------------");
	}
	
	@Override
	public void deleteSupportedDevices(Integer productId) {
		log.debug("productId: " + productId);

		Query query = entityManager.createQuery("delete VapProductSupportedDevice d where d.product.productId=:productId ");
		query.setParameter("productId", productId);

		int result = query.executeUpdate();
		log.debug("ProductDaoJpaImpl.deleteSupportedDevices record(s) delete " + result);
	}
    @Override
	public void saveComments(Integer productId, VapComment comment) {
		VapProduct product=getReference(productId);
		comment.setProduct(product);
		entityManager.persist(comment);
	}
    
    
	
}
