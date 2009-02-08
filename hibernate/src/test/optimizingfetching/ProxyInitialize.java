package test.optimizingfetching;

import model.Item;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import dao.ItemDAO;

public class ProxyInitialize {

	public static void main(String[] args) {
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		Item item=(Item)session.load(Item.class, 2L);
		Long count=(Long)session.createFilter(item.getBids(), "select count(*)").uniqueResult();
		System.out.println(count);
		Hibernate.initialize(item.getBids());
		session.close();
		System.out.println(item.getBids().size());
	}

}
