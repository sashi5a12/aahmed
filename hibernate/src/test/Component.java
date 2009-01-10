package test;

import java.sql.Timestamp;
import java.util.Date;

import model.Address;
import model.BillingDetails;
import model.Users;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.UsersDAO;

public class Component {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UsersDAO dao=new UsersDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Users user=new Users(1, "Adnan", "Ahmed", "aahmed", "aahmed", "leo_adnan@hotmail.com", 1, true, new Timestamp(System.currentTimeMillis()));
		BillingDetails billingDetails=new BillingDetails("CC",1,"aahmed", new Timestamp(System.currentTimeMillis()));
		Address address=new Address();
		address.setCity("city");
		address.setStreet("street");
		address.setZipcode("zipcode");
		user.setHomeAddress(address);
		user.setBillingDetails(billingDetails);
		dao.save(user);
		trx.commit();
	}

}
