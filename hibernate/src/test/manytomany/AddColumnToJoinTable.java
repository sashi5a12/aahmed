package test.manytomany;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.CategorizedItem;
import model.CategorizedItemDAO;
import model.Category;
import model.Item;
import dao.CategoryDAO;
import dao.ItemDAO;

public class AddColumnToJoinTable {
	public static void main(String args[]){
		ItemDAO itemDao=new ItemDAO();
		CategoryDAO categoryDao=new CategoryDAO();
		CategorizedItemDAO ciDao=new CategorizedItemDAO();
		
		Item item=itemDao.findById(2L);
		Category category=categoryDao.findById(1L);
		CategorizedItem ci=new CategorizedItem("aahmed",category,item);
		
		Session session=ciDao.getSession();
		Transaction trx=session.beginTransaction();
		session.save(ci);
		trx.commit();
	}
}
