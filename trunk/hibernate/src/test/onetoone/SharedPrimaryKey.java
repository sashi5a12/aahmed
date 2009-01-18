package test.onetoone;


import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Address;
import model.Users;
import dao.UsersDAO;

public class SharedPrimaryKey {

	public static void main(String[] args) {
		Users user=new Users();
		Address address=new Address();

		user.setUsername("UserName-2");
		user.setShippingAddress(address);
		
		address.setCity("City");
		address.setStreet("Street");
		address.setZipcode("Zipcode");
		address.setObjVersion(1);
		address.setUser(user);
		
		UsersDAO dao=new UsersDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		dao.save(user);
		trx.commit();
	}

}
