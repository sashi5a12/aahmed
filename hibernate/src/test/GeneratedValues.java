package test;

import model.Message;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.MessageDAO;

public class GeneratedValues {
	public static void main(String args[]){
		MessageDAO dao=new MessageDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Message msg=new Message("To generated vaules");
		dao.save(msg);		
		trx.commit();
		System.out.println(msg.getDefaultMessage());
	}

}
