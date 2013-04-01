package com.packtpub.springhibernate.ch04;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateHelper {
	private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	private static final ThreadLocal transaction = new ThreadLocal();
	private static final SessionFactory sessionFactory;
	static {
		Configuration config = new Configuration();
		config.configure("com/packtpub/springhibernate/ch04/hibernate.cfg.xml");
		
		ServiceRegistryBuilder serviceRegistryBuilder=new ServiceRegistryBuilder().applySettings(config.getProperties());
		
		// obtaining a session object
		sessionFactory = config.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
	}

	// inaccessible constructor
	private HibernateHelper() {
	}

	public static Session getSession() {
		Session session = HibernateHelper.session.get();
		if (session == null) {
			session = sessionFactory.openSession();
			HibernateHelper.session.set(session);
		}
		return session;
	}
}