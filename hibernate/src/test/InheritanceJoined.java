package test;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conf.HibernateSessionFactory;

import model.JoinedBillingDetails;
import model.JoinedCreditCard;
import model.Users;

public class InheritanceJoined {
	public static void main(String args[]){
		Session session=HibernateSessionFactory.getSession();
		Transaction trx=session.beginTransaction();
		Users user=(Users)session.load(Users.class, 2L);
		JoinedBillingDetails bd=new JoinedCreditCard(user,"111-111-1111","12","2009");
//		session.save(bd);
		trx.commit();
		
		JoinedBillingDetails tmp=(JoinedBillingDetails)session.load(JoinedCreditCard.class, 2L);
		if (tmp instanceof JoinedCreditCard){
			System.out.println(((JoinedCreditCard)tmp).getNumber());
		}
	}
}
