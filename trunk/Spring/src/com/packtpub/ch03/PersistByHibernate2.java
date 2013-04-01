package com.packtpub.ch03;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import com.packtpub.springhibernate.model.Student;

public class PersistByHibernate2 {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// configuring Hibernate
		Configuration config = new Configuration();
		config.configure("/com/packtpub/ch03/hibernate.cfg.xml");
		
		ServiceRegistryBuilder serviceRegistryBuilder=new ServiceRegistryBuilder().applySettings(config.getProperties());
		
		// obtaining a session object
		SessionFactory factory = config.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
		Session session = factory.openSession();
		
		// starting a transaction
		Transaction tx = session.beginTransaction();
		
		// persisting...
		Student student = new Student("Andrew", "White");
		session.save(student);
		
		// commiting the transaction
		tx.commit();
		session.close();
	}

}
