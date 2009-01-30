package test;

import model.Category;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;

public class BulkInsertion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CategoryDAO dao=new CategoryDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		for(int i=0; i<100000; i++){
			Category c=new Category("Category-"+i);
			dao.save(c);
			if ( i % 100 == 0){
				session.flush();
				session.clear();
			}
		}
		trx.commit();
		session.close();
	}

}
