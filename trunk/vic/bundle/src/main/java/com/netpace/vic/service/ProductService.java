package com.netpace.vic.service;

import com.netpace.vic.dto.Industry;
import com.netpace.vic.dto.Media;
import com.netpace.vic.dto.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {
    
     public Map<Industry, List<Product>> getProductsList() ;
     
     public Map<Industry, List<Product>> getProductsListByCategoryId(Integer categoryId) ;
     
     public Product productById(Integer productId);
     
     public Media getMediaById(Integer media);
     
     public List<Product> getProductsByPartner(Integer partnerId);
     
     public List<Product> allProducts();
    
}
