package test.manytomany;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;
import dao.ItemDAO;

import model.Category;
import model.Item;

public class Bidirectional {

	public static void main(String[] args) {
		ItemDAO itemDao=new ItemDAO();
		Category category=new Category(1,"Category-1",new Timestamp(System.currentTimeMillis()));
		Item item1=itemDao.findById(2L);
		Item item2=itemDao.findById(3L);
		
		category.addItem(item1);
		category.addItem(item2);
		
		CategoryDAO categoryDao=new CategoryDAO();
		Session session=categoryDao.getSession();
		Transaction trx=session.beginTransaction();
		categoryDao.save(category);
		trx.commit();
	}

}
