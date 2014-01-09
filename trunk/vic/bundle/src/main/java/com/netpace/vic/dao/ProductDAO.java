package com.netpace.vic.dao;

import com.netpace.vic.dto.Industry;
import com.netpace.vic.dto.Media;
import com.netpace.vic.dto.Product;
import java.util.List;
import java.util.Map;

public interface ProductDAO extends GenericDAO{
    public Map<Industry, List<Product>> productsList(Integer categoryId, boolean showAll);
    public Product productById(Integer productId);
    public Media getMediaById(Integer media);
    public List<Product> getProductsByPartner(Integer partnerId);
    public List<Product> allProducts();
}
