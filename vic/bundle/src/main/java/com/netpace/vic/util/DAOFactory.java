package com.netpace.vic.util;

import com.netpace.vic.dao.EmailLogDAO;
import com.netpace.vic.dao.PartnerDAO;
import com.netpace.vic.dao.ProductDAO;
import com.netpace.vic.dao.UserApplicationDAO;
import com.netpace.vic.dao.impl.EmailLogDAOImpl;
import com.netpace.vic.dao.impl.PartnerDAOImpl;
import com.netpace.vic.dao.impl.ProductDAOImpl;
import com.netpace.vic.dao.impl.UserApplicationDAOImpl;

public class DAOFactory {

    private static PartnerDAO partnerDAO = null;
    private static ProductDAO productDAO = null;
    private static UserApplicationDAO userApplicationDAO = null;
    private static EmailLogDAO emailLogDAO = null;

    private DAOFactory() {
    }

    public static PartnerDAO getPartnerDAO() {
        return partnerDAO;
    }

    public static ProductDAO getProductDAO() {
        return productDAO;
    }
    
    public static UserApplicationDAO getUserApplicationDAO() {
        return userApplicationDAO;
    }
    
    public static EmailLogDAO getEmailLogDAO() {
        return emailLogDAO;
    }
    
    static {
        partnerDAO = new PartnerDAOImpl();
        productDAO = new ProductDAOImpl();
        userApplicationDAO =  new UserApplicationDAOImpl();
        emailLogDAO = new EmailLogDAOImpl();
    }

}
