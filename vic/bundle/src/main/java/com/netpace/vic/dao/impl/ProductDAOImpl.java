package com.netpace.vic.dao.impl;

import com.netpace.vic.dao.ProductDAO;
import com.netpace.vic.dto.Media;
import com.netpace.vic.dto.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mysql.jdbc.Driver;
import com.netpace.vic.dto.Industry;
import java.util.LinkedHashMap;

public class ProductDAOImpl extends GenericDAOImpl implements ProductDAO {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public Map<Industry, List<Product>> productsList(Integer categoryId, boolean showAll) {
        log.info("================ProductDAOImpl.listByPartnerId Start===============");        
        Connection conn = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        Map<Industry, List<Product>> productsMap = new LinkedHashMap<Industry, List<Product>>();
        try {
            conn = super.getConnection();

            StringBuilder categoryQry = new StringBuilder();
            categoryQry.append("SELECT DISTINCT industry_name, i.industry_id ");
            categoryQry.append("FROM products p, industry i ");
            categoryQry.append("WHERE i.industry_id=p.industry_id ");
            if (showAll) {
                categoryQry.append("AND i.industry_id=? ");
            }
            categoryQry.append("order by lower(industry_name) ");
            
            log.info("Query: " + categoryQry);
            ps = conn.prepareStatement(categoryQry.toString());
            if (showAll) {
                ps.setInt(1, categoryId);
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                StringBuilder productQry = new StringBuilder();
                productQry.append("select distinct ");
                productQry.append("    i.industry_name, ");
                productQry.append("    i.industry_id, ");
                productQry.append("    par.partner_name, ");
                productQry.append("    p.product_id, ");
                productQry.append("    p.product_title, ");
                productQry.append("    p.short_desc, ");
                productQry.append("    p.featured, ");
                productQry.append("    p.sort_order, ");
                productQry.append("    m.media_id, ");
                productQry.append("    m.file_name, ");
                productQry.append("    m.file_length, ");
                productQry.append("    m.file_mime_type, ");
                productQry.append("    m.file_field_name ");
                productQry.append("from ");
                productQry.append("    products p, ");
                productQry.append("    media m, ");
                productQry.append("    industry i, ");
                productQry.append("    partners par ");
                productQry.append("where");
                productQry.append("    p.product_id = m.media_type_id ");
                productQry.append("        and p.partner_id = par.partner_id ");
                productQry.append("        and p.row_enabled='1' ");
                productQry.append("        and i.industry_id = p.industry_id ");
                //productQry.append("        and m.file_field_name = 'Product Pic1' ");
                productQry.append("        and i.industry_name = ? ");
                productQry.append("order by p.featured DESC, p.sort_order ASC ");
                if (!showAll) {
                    productQry.append("limit 0,5");
                }

                String industryName = rs.getString("industry_name");
                Integer industryId=rs.getInt("industry_id");
                Industry industry = new Industry(industryId, industryName);
                
                log.info("Getting date for: " + industryName);
                log.info("Query: " + productQry);
                ps2 = conn.prepareStatement(productQry.toString());
                ps2.setString(1, industryName);
                rs2 = ps2.executeQuery();

                List<Product> products = new ArrayList<Product>();
                

                while (rs2.next()) {
                    Product product = new Product();
                    Media media = new Media();

                    product.setIndustryId(rs2.getInt("industry_id"));
                    product.setIndustryName(rs2.getString("industry_name"));
                    product.setParnterName(rs2.getString("partner_name"));
                    product.setProductId(rs2.getInt("product_id"));
                    product.setProductTitle(rs2.getString("product_title"));
                    product.setShortDesc(rs2.getString("short_desc"));
                    product.setFeatured(rs2.getString("featured"));

                    media.setMediaId(rs2.getInt("media_id"));
                    media.setFileName(rs2.getString("file_name"));
                    media.setFileLength(rs2.getInt("file_length"));
                    media.setFileMimeType(rs2.getString("file_mime_type"));
                    media.setFileFieldName(rs2.getString("file_field_name"));

                    product.setMedia(media);

                    products.add(product);
                }
                
                if(products.size()>0){
                    productsMap.put(industry, products);
                }
                rs2.close();
                ps2.close();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (rs2 != null) {
                    rs2.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
        }
        log.info("Result: " + productsMap);
        log.info("================ProductDAOImpl.listByPartnerId End===============");
        return productsMap;
    }

    public List<Product> allProducts() {
        log.info("================ProductDAOImpl.allProducts Start===============");        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Product> products = new ArrayList<Product>();
        try {
            conn = super.getConnection();
            StringBuilder productQry = new StringBuilder();
            productQry.append("select distinct ");
            productQry.append("    i.industry_name, ");
            productQry.append("    i.industry_id, ");
            productQry.append("    par.partner_name, ");
            productQry.append("    p.product_id, ");
            productQry.append("    p.product_title, ");
            productQry.append("    p.short_desc, ");
            productQry.append("    p.featured, ");
            productQry.append("    p.sort_order, ");
            productQry.append("    m.media_id, ");
            productQry.append("    m.file_name, ");
            productQry.append("    m.file_length, ");
            productQry.append("    m.file_mime_type, ");
            productQry.append("    m.file_field_name ");
            productQry.append("from ");
            productQry.append("    products p, ");
            productQry.append("    media m, ");
            productQry.append("    industry i, ");
            productQry.append("    partners par ");
            productQry.append("where");
            productQry.append("    p.product_id = m.media_type_id ");
            productQry.append("        and p.partner_id = par.partner_id ");
            productQry.append("        and p.row_enabled='1' ");
            productQry.append("        and i.industry_id = p.industry_id ");
            productQry.append("order by p.featured DESC, p.sort_order ASC ");

            ps = conn.prepareStatement(productQry.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                Media media = new Media();

                product.setIndustryId(rs.getInt("industry_id"));
                product.setIndustryName(rs.getString("industry_name"));
                product.setParnterName(rs.getString("partner_name"));
                product.setProductId(rs.getInt("product_id"));
                product.setProductTitle(rs.getString("product_title"));
                product.setShortDesc(rs.getString("short_desc"));
                product.setFeatured(rs.getString("featured"));

                media.setMediaId(rs.getInt("media_id"));
                media.setFileName(rs.getString("file_name"));
                media.setFileLength(rs.getInt("file_length"));
                media.setFileMimeType(rs.getString("file_mime_type"));
                media.setFileFieldName(rs.getString("file_field_name"));

                product.setMedia(media);

                products.add(product);
            }

        } catch (Exception e) {
            log.error(e.toString(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
        }
        log.info("Result: " + products);
        log.info("================ProductDAOImpl.allProducts End===============");
        return products;
    }

    public Product productById(Integer productId) {
        log.info("================ProductDAOImpl.productById Start===============");
        log.info("productId: " + productId);
        Connection conn = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        Product product = new Product();
        try {
            conn = super.getConnection();

            StringBuilder productQry = new StringBuilder();
            productQry.append("SELECT DISTINCT ");
            productQry.append("	p.product_id, ");
            productQry.append("	p.product_title, ");
            productQry.append("	p.short_desc, ");
            productQry.append("	p.long_desc, ");
            productQry.append("	i.industry_id, ");
            productQry.append("	i.industry_name, ");
            productQry.append("	par.partner_name, ");
            productQry.append("	par.url, ");
            productQry.append("	p.sales_info, ");
            productQry.append("	m.media_id product_image_id, ");
            productQry.append("	m2.media_id partner_image_id ");
            productQry.append("FROM products p ");
            productQry.append("INNER JOIN partners par ON p.partner_id = par.partner_id ");
            productQry.append("INNER JOIN industry i ON p.industry_id = i.industry_id ");
            productQry.append("LEFT JOIN media m ON p.product_id =  m.media_type_id ");
            productQry.append("LEFT JOIN media m2 ON p.partner_id = m2.media_type_id ");
            productQry.append("WHERE p.row_enabled = '1'  ");
            productQry.append("AND p.product_id = ?  ");
        
            ps = conn.prepareStatement(productQry.toString());
            ps.setInt(1, productId);
            rs = ps.executeQuery();

            while (rs.next()) {
                product.setProductId(rs.getInt("product_id"));
                product.setProductTitle(rs.getString("product_title"));
                product.setShortDesc(rs.getString("short_desc"));
                product.setLongDesc(rs.getString("long_desc"));
                product.setIndustryId(rs.getInt("industry_id"));
                product.setIndustryName(rs.getString("industry_name"));
                product.setParnterName(rs.getString("partner_name"));
                product.setPartnerUrl(rs.getString("url"));
                product.setSalesInfo(rs.getString("sales_info"));

                Media media = new Media();
                media.setMediaId(rs.getInt("product_image_id"));
                
                Media media2 = new Media();
                media2.setMediaId(rs.getInt("partner_image_id"));
                
                product.setMedia(media);
                product.setPartnerMedia(media2);
                                    
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (rs2 != null) {
                    rs2.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
        }
        log.info("Result: " + product);
        log.info("================ProductDAOImpl.productById End===============");
        return product;
    }

    public Media getMediaById(Integer mediaId) {
        log.info("================ProductDAOImpl.getMediaById Start===============");
        log.info("mediaId: " + mediaId);
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Media media = new Media();
        try {
            conn = super.getConnection();

            StringBuilder imageQry = new StringBuilder();
            imageQry.append("select m.media_id, ");
            imageQry.append("    m.file_name, ");
            imageQry.append("    m.file_length, ");
            imageQry.append("    m.file_mime_type, ");
            imageQry.append("    m.file_field_name, ");
            imageQry.append("    m.file_data ");
            imageQry.append("from ");
            imageQry.append("    media m ");
            imageQry.append("where");
            imageQry.append("    m.media_id = ? ");
            ps = conn.prepareStatement(imageQry.toString());
            ps.setInt(1, mediaId);
            rs = ps.executeQuery();

            while (rs.next()) {
                media.setMediaId(rs.getInt("media_id"));
                media.setFileName(rs.getString("file_name"));
                media.setFileLength(rs.getInt("file_length"));
                media.setFileMimeType(rs.getString("file_mime_type"));
                media.setFileFieldName(rs.getString("file_field_name"));
                
                //Blob image = rs.getBlob("file_data");
                //BufferedInputStream bis = new BufferedInputStream(image.getBinaryStream(1,image.length()));
                media.setData(rs.getBytes("file_data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e.toString(), e);
            }
        }
        log.info("Result: " + media);
        log.info("================ProductDAOImpl.getMediaById End===============");
        return media;
    }

	public List<Product> getProductsByPartner(Integer partnerId) {
		log.info("================ProductDAOImpl.getProductsByPartner Start===============");        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Product> productsList = new ArrayList<Product>();
        try {
            conn = super.getConnection();

            StringBuilder productQry = new StringBuilder();
            productQry.append("select ");
            productQry.append("    par.partner_name, ");
            productQry.append("    p.product_id, ");
            productQry.append("    p.product_title, ");
            productQry.append("    p.short_desc, ");
            productQry.append("    p.featured, ");
            productQry.append("    m.media_id ");
            productQry.append("from ");
            productQry.append("    products p, ");
            productQry.append("    media m, ");
            productQry.append("    partners par ");
            productQry.append("where");
            productQry.append("    p.product_id = m.media_type_id ");
            productQry.append("        and p.partner_id = par.partner_id ");
            productQry.append("        and p.row_enabled = '1' ");
            productQry.append("        and p.partner_id = ? ");
            productQry.append("order by lower(p.product_title) ");
                
            log.info("Query: " + productQry);
            ps = conn.prepareStatement(productQry.toString());
            ps.setInt(1, partnerId);
            rs = ps.executeQuery();
                             
            while (rs.next()) {
                Product product = new Product();
                Media media = new Media();

                product.setParnterName(rs.getString("partner_name"));
                product.setProductId(rs.getInt("product_id"));
                product.setProductTitle(rs.getString("product_title"));
                product.setShortDesc(rs.getString("short_desc"));
                product.setFeatured(rs.getString("featured"));

                media.setMediaId(rs.getInt("media_id"));
                
                product.setMedia(media);
                productsList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e.toString(), e);
            }
        }
        log.info("Result: " + productsList);
        log.info("================ProductDAOImpl.getProductsByPartner End===============");
        return productsList;
	}
}
