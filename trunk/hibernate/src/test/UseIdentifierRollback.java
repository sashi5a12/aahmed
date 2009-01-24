package test;

import model.Category;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;
/**
 * 
 * If you enable the hibernate.use_identifier_rollback configuration option. 
 * Hibernate sets the database identifier property of the deleted item to null after deletion 
 * and flushing.
 *
 */
public class UseIdentifierRollback {
	public static void main(String args[]){
		CategoryDAO dao=new CategoryDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Category c=dao.findById(2L);
		dao.delete(c);
		trx.commit();
		session.close();
		System.out.println(c.getCategoryId());
	}
}
