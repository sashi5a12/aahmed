package dao;

import java.util.List;
import java.util.Set;

import model.ValueTypeSetItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ValueTypeSetItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.ValueTypeSetItem
 * @author MyEclipse Persistence Tools
 */

public class ValueTypeSetItemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ValueTypeSetItemDAO.class);
	// property constants
	public static final String ITEM_NAME = "itemName";

	public void save(ValueTypeSetItem transientInstance) {
		log.debug("saving ValueTypeSetItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ValueTypeSetItem persistentInstance) {
		log.debug("deleting ValueTypeSetItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ValueTypeSetItem findById(java.lang.Long id) {
		log.debug("getting ValueTypeSetItem instance with id: " + id);
		try {
			ValueTypeSetItem instance = (ValueTypeSetItem) getSession().get(
					"model.ValueTypeSetItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ValueTypeSetItem> findByExample(ValueTypeSetItem instance) {
		log.debug("finding ValueTypeSetItem instance by example");
		try {
			List<ValueTypeSetItem> results = getSession()
					.createCriteria("model.ValueTypeSetItem").add(
							Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<ValueTypeSetItem> findByProperty(String propertyName, Object value) {
		log.debug("finding ValueTypeSetItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ValueTypeSetItem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ValueTypeSetItem> findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List<ValueTypeSetItem> findAll() {
		log.debug("finding all ValueTypeSetItem instances");
		try {
			String queryString = "from ValueTypeSetItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ValueTypeSetItem merge(ValueTypeSetItem detachedInstance) {
		log.debug("merging ValueTypeSetItem instance");
		try {
			ValueTypeSetItem result = (ValueTypeSetItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ValueTypeSetItem instance) {
		log.debug("attaching dirty ValueTypeSetItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ValueTypeSetItem instance) {
		log.debug("attaching clean ValueTypeSetItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}