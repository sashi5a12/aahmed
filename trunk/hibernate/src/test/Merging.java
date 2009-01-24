package test;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Category;
import dao.CategoryDAO;

public class Merging {
	public static void main(String args[]){
		CategoryDAO dao=new CategoryDAO();
		Session s1=dao.getSession();
		Category c1=dao.findById(1L);
		s1.close();
		
		
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Category c2=(Category)session.get(Category.class, 1L);
		
//		session.update(c1); //throw exception. if not call merge 
		Category c3=(Category)session.merge(c1);
		trx.commit();
	}
}
