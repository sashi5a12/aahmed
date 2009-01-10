package test;

import java.util.Date;

import model.Message;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.MessageDAO;

/*
 * The dynamic-update attribute tells Hibernate whether to include unmodified properties 
 * in the SQL UPDATE. If "dynamic-update=true" then unmodified properties not present in query.
 * In below example, updating object will not include text propety.
 */
public class DynamicUpdation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MessageDAO dao=new MessageDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Message msg=new Message("Dynamic Insertion test");
		dao.save(msg);
		msg.setDateModified(new Date());
		trx.commit();
	}

}
