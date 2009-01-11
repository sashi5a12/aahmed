package dao;

import java.util.List;
import java.util.Set;

import model.ValueTypeMapItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ValueTypeMapItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.ValueTypeMapItem
 * @author MyEclipse Persistence Tools
 */

public class ValueTypeMapItemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ValueTypeMapItemDAO.class);
	// property constants
	public static final String ITEM_NAME = "itemName";

	public void save(ValueTypeMapItem transientInstance) {
		log.debug("saving ValueTypeMapItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ValueTypeMapItem persistentInstance) {
		log.debug("deleting ValueTypeMapItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ValueTypeMapItem findById(java.lang.Long id) {
		log.debug("getting ValueTypeMapItem instance with id: " + id);
		try {
			ValueTypeMapItem instance = (ValueTypeMapItem) getSession().get(
					"model.ValueTypeMapItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ValueTypeMapItem instance) {
		log.debug("finding ValueTypeMapItem instance by example");
		try {
			List results = getSession()
					.createCriteria("model.ValueTypeMapItem").add(
							Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ValueTypeMapItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ValueTypeMapItem as model where model."
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
		log.debug("finding all ValueTypeMapItem instances");
		try {
			String queryString = "from ValueTypeMapItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ValueTypeMapItem merge(ValueTypeMapItem detachedInstance) {
		log.debug("merging ValueTypeMapItem instance");
		try {
			ValueTypeMapItem result = (ValueTypeMapItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ValueTypeMapItem instance) {
		log.debug("attaching dirty ValueTypeMapItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ValueTypeMapItem instance) {
		log.debug("attaching clean ValueTypeMapItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}