package test;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;
import dao.ItemDAO;
import model.Category;
import model.Item;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Category c1=new Category();
		c1.setCategoryId(2L);
		c1.setObjVersion(2);
		c1.setCreated(new Timestamp(System.currentTimeMillis()));
		c1.setCategoryName("Category-1");
		
		CategoryDAO categoryDao=new CategoryDAO();
		Session session=categoryDao.getSession();
		Transaction trx=session.beginTransaction();
		categoryDao.attachDirty(c1);
		Category c2=categoryDao.findById(2L);
		c2.setObjVersion(4);
		
		trx.commit();
	}

}
