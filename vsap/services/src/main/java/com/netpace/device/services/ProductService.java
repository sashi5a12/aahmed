package com.netpace.device.services;

import java.util.List;

import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapMedia;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.VapProductAttachment;
import com.netpace.device.entities.VapProductProcessInfo;
import com.netpace.device.enums.ReprotsNameEnum;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.product.AttachmentFile;
import com.netpace.device.vo.product.FileUploadVO;
import com.netpace.device.vo.product.ProcessingInfoVO;
import com.netpace.device.vo.product.ProductListVO;
import com.netpace.device.vo.product.ProductInfoVO;

public interface ProductService {
	
	public List<ProductListVO> getProductsForList(PageModel<ProductListVO> pageModel, VAPUserDetails loggedInUser);

	public void createProduct(ProductInfoVO product, VAPUserDetails loggedInUser);
	
	public ProductInfoVO getProductDetialById(Integer productId, VAPUserDetails loggedInUser);

	public void updateProduct(ProductInfoVO product, VAPUserDetails loggedInUser);

	public void inactiveProduct(Integer productId, VAPUserDetails loggedInUser);

	public VapProductAttachment getAttachment(Integer productId, Integer mediaId);
	
	public void saveAttachment(FileUploadVO fileUpload, VapMedia media, VAPUserDetails loggedInUser);
	
	public void deleteAttachment(AttachmentFile file, VAPUserDetails loggedInUser);
	
	public void getProcessingInfoForEdit(ProcessingInfoVO vo, Integer productId, VAPUserDetails loggedInUser);

	public void processingInfoPrepopulatedData(ProcessingInfoVO vo, Integer productId, VAPUserDetails loggedInUser);
	
	public void saveProcessingInfo(ProcessingInfoVO vo, VAPUserDetails loggedInUser);
	
	public void setWorkItems(ProcessingInfoVO vo, VAPUserDetails loggedInUser);

	public void saveComments(Integer productId, VapComment comment);

}
