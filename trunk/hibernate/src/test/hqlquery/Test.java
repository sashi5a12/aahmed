package test.hqlquery;

import java.util.List;

import model.Item;
import model.Users;

import org.hibernate.Query;
import org.hibernate.Session;

import dao.ItemDAO;

public class Test {

	public static void main(String[] args) {
		ItemDAO dao=new ItemDAO();
		Users user=new Users();
		user.setUserId(2L);
		user.setFirstname("Adnan");
		user.setLastname("Ahmed");
		Session session=dao.getSession();
		Query query=session.createQuery("from Item item where item.seller=:seller").setParameter("seller", user);
		List<Item> items=query.list();
		for (Item item : items) {
			System.out.println(item.getItemName());
		}
		session.close();
	}

}
