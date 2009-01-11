package dao;

import java.util.List;
import java.util.Set;

import model.ValueTypeClistItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ValueTypeClistItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.ValueTypeClistItem
 * @author MyEclipse Persistence Tools
 */

public class ValueTypeClistItemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(ValueTypeClistItemDAO.class);
	// property constants
	public static final String ITEM_NAME = "itemName";

	public void save(ValueTypeClistItem transientInstance) {
		log.debug("saving ValueTypeClistItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ValueTypeClistItem persistentInstance) {
		log.debug("deleting ValueTypeClistItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ValueTypeClistItem findById(java.lang.Long id) {
		log.debug("getting ValueTypeClistItem instance with id: " + id);
		try {
			ValueTypeClistItem instance = (ValueTypeClistItem) getSession()
					.get("model.ValueTypeClistItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ValueTypeClistItem instance) {
		log.debug("finding ValueTypeClistItem instance by example");
		try {
			List results = getSession().createCriteria(
					"model.ValueTypeClistItem").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ValueTypeClistItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ValueTypeClistItem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List findAll() {
		log.debug("finding all ValueTypeClistItem instances");
		try {
			String queryString = "from ValueTypeClistItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ValueTypeClistItem merge(ValueTypeClistItem detachedInstance) {
		log.debug("merging ValueTypeClistItem instance");
		try {
			ValueTypeClistItem result = (ValueTypeClistItem) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ValueTypeClistItem instance) {
		log.debug("attaching dirty ValueTypeClistItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ValueTypeClistItem instance) {
		log.debug("attaching clean ValueTypeClistItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}