package test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.Message;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conf.HibernateSessionFactory;

import dao.MessageDAO;

public class MessageTest {

	public static void main(String[] args) {
		MessageDAO dao = new MessageDAO();
		/*Query q = session.createQuery(
				"from User u where :bid in elements(u.bids)"
				);
				q.setParameter("bid", bid);*/
		// First unit of work
		Session session = dao.getSession();
		Transaction trx = session.beginTransaction();
		Message msg = new Message("Message-1", new Date());
		Integer msgId = dao.save(msg);
		trx.commit();
		session.close();

		// Second unit of work
		List<Message> messages = dao.findAll();
		System.out.println(messages.size() + " message(s) found:");
		for (Iterator<Message> iter = messages.iterator();
		iter.hasNext();) {
			Message loadedMsg = iter.next();
			System.out.println(loadedMsg.getText());
		}
		
		// Third unit of work 
		Session thirdSession = dao.getSession(); 
		Transaction thirdTransaction = thirdSession.beginTransaction(); 
		// msgId holds the identifier value of the first message 
		msg = (Message) thirdSession.get( Message.class, msgId ); 
		msg.setText( "Greetings Earthling" ); 
		msg.setNextMessage(new Message( "Take me to your leader (please)", new Date() )); 
		thirdTransaction.commit(); 
		thirdSession.close();

		// Fourth unit of work
		Session forthSession=dao.getSession();
		Transaction fourthTrx=forthSession.beginTransaction();
		Message newMsg = new Message("Message-2", new Date());
		newMsg.setNextMessage(new Message("newmessage", new Date()));
		dao.save(newMsg);
		fourthTrx.commit();
		forthSession.close();
		
		HibernateSessionFactory.getSessionFactory().close();
		
	}

}
