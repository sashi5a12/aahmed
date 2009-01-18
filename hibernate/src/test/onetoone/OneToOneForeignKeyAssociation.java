package test.onetoone;

import org.dom4j.util.UserDataAttribute;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.OtoFkaUsersDAO;

import model.OtoFkaAddress;
import model.OtoFkaUsers;

public class OneToOneForeignKeyAssociation {

	public static void main(String[] args) {
		OtoFkaUsers user=new OtoFkaUsers();
		OtoFkaAddress address= new OtoFkaAddress();
		user.setUsername("UserName-2");
		address.setCity("karachi");

		user.setShippingAddress(address);
		address.setUser(user);

		OtoFkaUsersDAO dao=new OtoFkaUsersDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		session.save(user);
		trx.commit();
	}

}
