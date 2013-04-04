package test.onetomany;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Item;
import model.Users;
import dao.ItemDAO;
import dao.UsersDAO;

public class JoinTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UsersDAO userDao=new UsersDAO();
		ItemDAO itemDao=new ItemDAO();
		
		Users user=userDao.findById(2L);
		Item item1=itemDao.findById(7L);
		Item item2=itemDao.findById(10L);
		
		user.addItem(item1);
		user.addItem(item2);
		
		Session session=itemDao.getSession();
		Transaction trx=session.beginTransaction();
		userDao.attachDirty(user);
		trx.commit();
	}

}
