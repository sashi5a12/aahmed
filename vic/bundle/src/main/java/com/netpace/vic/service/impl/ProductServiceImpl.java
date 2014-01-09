package com.netpace.vic.service.impl;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.netpace.vic.dao.ProductDAO;
import com.netpace.vic.dto.Industry;
import com.netpace.vic.dto.Media;
import com.netpace.vic.dto.Product;
import com.netpace.vic.service.ProductService;
import com.netpace.vic.util.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
public class ProductServiceImpl implements ProductService {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Reference
    private DataSourcePool source;

    public Map<Industry, List<Product>> getProductsList() {
        LOGGER.info("info: ProductServiceImpl.getProductsList");
        ProductDAO dao = DAOFactory.getProductDAO();
        dao.setSource(source);
        return dao.productsList(null, false);
    }

    public Map<Industry, List<Product>> getProductsListByCategoryId(Integer categoryId) {
        ProductDAO dao = DAOFactory.getProductDAO();
        dao.setSource(source);
        return dao.productsList(categoryId, true);
    }

    public Product productById(Integer productId) {
        LOGGER.info("info: ProductServiceImpl.productById");
        ProductDAO dao = DAOFactory.getProductDAO();
        dao.setSource(source);
        return dao.productById(productId);
    }

    public Media getMediaById(Integer media) {
        ProductDAO dao = DAOFactory.getProductDAO();
        dao.setSource(source);
        return dao.getMediaById(media);
    }

    public List<Product> getProductsByPartner(Integer partnerId) {
        ProductDAO dao = DAOFactory.getProductDAO();
        dao.setSource(source);
        return dao.getProductsByPartner(partnerId);
    }

    public List<Product> allProducts(){
        ProductDAO dao = DAOFactory.getProductDAO();
        dao.setSource(source);
        return dao.allProducts();
    }
}
