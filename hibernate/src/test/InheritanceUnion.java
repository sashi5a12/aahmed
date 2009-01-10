package test;

import model.UnionBilllingDetails;
import model.UnionCreditCard;
import model.Users;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conf.HibernateSessionFactory;

public class InheritanceUnion {
	public static void main(String args[]){
		Session session = HibernateSessionFactory.getSession();
		Transaction trx=session.beginTransaction();
		/*Users user=(Users)session.load(Users.class, new Long(2L));
		UnionCreditCard creditCard=new UnionCreditCard(user, "11-11-111", "12", "2009");
		session.save(creditCard);*/
		UnionBilllingDetails detail=(UnionCreditCard)session.load(UnionCreditCard.class, 1L);
		UnionCreditCard creditCard=(UnionCreditCard)detail;
		System.out.println(creditCard.getNumber());
		
		trx.commit();
	}
}
