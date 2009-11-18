package com.netpace.vzdn.db;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	static Logger log = Logger.getLogger(HibernateUtil.class.getName());
	
	private Transaction tx;
	private Session session;

    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<HibernateUtil> threadLocal = new ThreadLocal<HibernateUtil>();
    private static Configuration configuration = new Configuration();    
    private static org.hibernate.SessionFactory sessionFactory;
    private static String configFile = CONFIG_FILE_LOCATION;

	static {
    	try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			log.debug("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }

	public HibernateUtil() {
		log.debug("constructor called..");
		session = sessionFactory.getCurrentSession();
		log.debug("session created..");
	}

	public static Transaction begingTransaction() {
		log.debug("beginTransaction is called...");
		HibernateUtil util = (HibernateUtil) threadLocal.get();
		if (null == util) {
			log.debug("instance is null util=>" + util);
			threadLocal.set(new HibernateUtil());
			util = (HibernateUtil) threadLocal.get();
			log.debug("instance created util=>" + util);
			util.tx = util.session.beginTransaction();
		}
		log.debug("begining transaction " + util.session);
		return util.tx;
	}

	public Transaction getTransaction() {
		HibernateUtil util = (HibernateUtil) threadLocal.get();
		return util.tx;
	}

	public static Session getSession() {
		HibernateUtil util = (HibernateUtil) threadLocal.get();
		return util.session;
	}

	public static void commitTransaction() {
		HibernateUtil util = (HibernateUtil) threadLocal.get();
		log.debug("comiting transaction  util=>" + util);
		util.tx.commit();
	}

	public static void rollbackTransaction() {
		HibernateUtil util = (HibernateUtil) threadLocal.get();
		log.debug("rollingback transaction util=>" + util);
		util.tx.rollback();
	}

	public static void endTransaction() {
		HibernateUtil util = (HibernateUtil) threadLocal.get();
		log.debug("ending transaction util=>" + util.session.isOpen());
		if (util.session.isOpen())
			util.session.close();
	}

}
