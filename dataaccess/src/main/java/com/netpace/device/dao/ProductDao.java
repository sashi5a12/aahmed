package com.netpace.device.dao;

import java.util.List;

import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.VapProductAttachment;
import com.netpace.device.entities.VapProductProcessInfo;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.product.ProductListVO;

public interface ProductDao extends GenericDao<VapProduct, Integer> {
    public static final String name="productDao";
    
    public List<Object[]> getProductsForList(PageModel<ProductListVO> pageModel, Integer companyId, Boolean isVerizonUser);
    
    public Long getProductsCountForList(PageModel<ProductListVO> pageModel, Integer companyId, Boolean isVerizonUser);

    public VapProduct getProductForEdit(Integer productId, Integer companyId, Boolean isVerizonUser);

    public void inactiveProduct(Integer companyId, Integer productId, String userName);

    public VapProductAttachment getAttachment(Integer productId, Integer mediaId);

    public void saveAttachment(VapProductAttachment attachment);

    public void deleteAttachment(Integer companyId, Integer productId, Integer mediaId, Boolean isVerizonUser);

    public VapProductProcessInfo getProcessingInfo(Integer processId);

    public void saveProcessingInfo(VapProductProcessInfo processingInfo);
 	
 	public VapProduct getProcessingInfoForEdit(Integer productId, Integer companyId, Boolean isVerizonUser);
 	
 	public void saveComments(Integer productId, VapComment comment);
 	
 	public void updateProductStatus(Integer productId, ProductStatus status);
 	
 	public void deleteSupportedDevices(Integer productId);
}