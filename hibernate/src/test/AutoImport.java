package test;

import java.util.List;

import model.Message;

import org.hibernate.Session;

import dao.MessageDAO;

/*
 * If auto-import="false" is defined in mapping file then you have to define fully qualify name
 * of the entity in HQL. Otherwise you will get the error.
 */
public class AutoImport {
	public static void main(String args[]){
		MessageDAO dao=new MessageDAO();
		Session session=dao.getSession();
		List<Message> list=dao.findAll();
		System.out.println(list.size());
	}
}
