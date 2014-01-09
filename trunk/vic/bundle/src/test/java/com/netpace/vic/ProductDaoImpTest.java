package com.netpace.vic;

import com.netpace.vic.dao.ProductDAO;
import com.netpace.vic.dto.Media;
import com.netpace.vic.dto.Product;
import com.netpace.vic.service.ProductService;
import com.netpace.vic.service.impl.ProductServiceImpl;
import com.netpace.vic.util.DAOFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ProductDaoImpTest {

    @Test
    public void someTest() {
        /*ProductDAO dao=DAOFactory.getProductDAO();
        Media media = dao.getMediaById(new Integer(1));
        File file=new File("C:/resources/"+media.getFileName());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(media.getData());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
           ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        */
        
        /*ProductDAO dao=DAOFactory.getProductDAO();
        Product product=dao.productById(1);
        System.out.println(product);*/
        
        //Map<String, List<Product>> products = DAOFactory.getProductDAO().productsList(null, false);
        //System.out.println(products);
        
        //ProductService service = new ProductServiceImpl();
        //Map<String, List<Product>> products = service.getProductsList();
        //System.out.println(products);
    }  
        
}
