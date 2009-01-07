package dao;

import java.util.List;

import model.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * Message entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see model.Message
 * @author MyEclipse Persistence Tools
 */

public class MessageDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(MessageDAO.class);
	// property constants
	public static final String TEXT = "text";
	public static final String NEXT_MESSAGE = "nextMessage";

	public Integer save(Message transientInstance) {
		log.debug("saving Message instance");
		Integer msgId=null;
		try {
			msgId=(Integer) getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return msgId;
	}

	public void delete(Message persistentInstance) {
		log.debug("deleting Message instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Message findById(java.lang.Integer id) {
		log.debug("getting Message instance with id: " + id);
		try {
			Message instance = (Message) getSession().get("model.Message", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Message> findByExample(Message instance) {
		log.debug("finding Message instance by example");
		try {
			List<Message> results = getSession().createCriteria("model.Message").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Message> findByProperty(String propertyName, Object value) {
		log.debug("finding Message instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Message as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Message> findByText(Object text) {
		return findByProperty(TEXT, text);
	}

	public List<Message> findByNextMessage(Object nextMessage) {
		return findByProperty(NEXT_MESSAGE, nextMessage);
	}

	public List<Message> findAll() {
		log.debug("finding all Message instances");
		try {
			String queryString = "from Message";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Message merge(Message detachedInstance) {
		log.debug("merging Message instance");
		try {
			Message result = (Message) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Message instance) {
		log.debug("attaching dirty Message instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Message instance) {
		log.debug("attaching clean Message instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}