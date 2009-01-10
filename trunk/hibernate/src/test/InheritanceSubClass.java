package test;

import java.util.List;

import model.SubClassBankAccount;
import model.SubClassBillingDetails;
import model.SubClassCreditCard;
import model.Users;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.SubClassBillingDetailsDAO;

public class InheritanceSubClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SubClassBillingDetailsDAO dao=new SubClassBillingDetailsDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Users user=(Users)session.load(Users.class, 2L);
		SubClassBillingDetails bankaccount=new SubClassBankAccount(user, "aahmed", "Bank al Falah", "000-00-000");
//		dao.save(bankaccount);		
		trx.commit();

		Session secondSession=dao.getSession();
		Transaction secondTrx=secondSession.beginTransaction();
		SubClassBillingDetails creditCard=new SubClassCreditCard(user, "111-11-1111", "12", "2009");
//		dao.save(creditCard);		
		secondTrx.commit();
		
		List<SubClassBillingDetails> list=dao.findAll();
		for(int i=0; i<list.size(); i++){
			SubClassBillingDetails bd=list.get(i);
			if (bd instanceof SubClassBankAccount){
				System.out.println("Bank Account--> "+ ((SubClassBankAccount)bd).getBaAccount());
			}
			else {
				System.out.println("Credit Card--> "+ ((SubClassCreditCard)bd).getCcNumber());
			}
		}
		
	}

}
