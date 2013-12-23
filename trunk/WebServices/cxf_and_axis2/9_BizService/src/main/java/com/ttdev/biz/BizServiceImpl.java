package com.ttdev.biz;

import java.util.List;

import javax.jws.WebService;

import com.ttdev.biz.ProductQueryComplexType.QueryItem;
import com.ttdev.biz.ProductQueryResultComplexType.ResultItem;

@WebService(endpointInterface = "com.ttdev.biz.BizService")
public class BizServiceImpl implements BizService {

	@Override
	public ProductQueryResultComplexType query(ProductQueryComplexType parameters) {
		
		ProductQueryResultComplexType result = new ProductQueryResultComplexType();
		List<QueryItem> queryItem = parameters.getQueryItem();
		for (QueryItem item : queryItem) {
			if (item.getQty() <= 200) {
				ResultItem resultItem = new ResultItem();
				resultItem.setProductId(item.getProductId());
				resultItem.setPrice((float) 20);
				result.getResultItem().add(resultItem);
			}
		}
		return result;
	}
}
