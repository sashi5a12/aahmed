package com.netpace.device.dao;

import java.util.List;

import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.VapProductAttachment;
import com.netpace.device.entities.VapProductProcessInfo;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.product.ProductListVO;

public interface ReportDao extends GenericDao<VapProduct, Integer> {
    public static final String name="reportDao";
    
 	public List<Object[]> getAllProductsDetailForCSV();
 	
 	public List<Object[]> getAllCompleteTestingProductsForCSV();
 	
    public List<Object[]> getAllCompanyForCSV();
    
    public List<Object[]> getUsersForCSV(boolean isWeekly);
 	
}