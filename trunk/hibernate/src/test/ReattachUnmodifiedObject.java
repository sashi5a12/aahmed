package test;

import java.sql.Timestamp;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;

import model.Category;

public class ReattachUnmodifiedObject {
	public static void main(String args[]){
		CategoryDAO dao=new CategoryDAO();
		
		Category c=dao.findById(1L);
		
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		session.lock(c, LockMode.NONE);
		c.setObjVersion(10);
		trx.commit();
	}
}
