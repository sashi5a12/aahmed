package test;

import model.Message;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.MessageDAO;
/*
 * The dynamic-insert attribute tells Hibernate whether to include null property values 
 * in an SQL INSERT. If "dynamic-insert=true" then null properties values not present in query.
 * In below example, modifiedDate property will not present in insertion query.
 */
public class DynamicInsertion {
	public static void main (String args[]){
		MessageDAO dao=new MessageDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Message msg=new Message("Dynamic Insertion test");
		dao.save(msg);
		trx.commit();
	}
}
