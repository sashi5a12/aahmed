package dao;

import java.util.List;
import java.util.Set;

import model.ValueTypeBagItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import test.ValueTypeBag;

/**
 * A data access object (DAO) providing persistence and search support for
 * ValueTypeBagItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.ValueTypeBagItem
 * @author MyEclipse Persistence Tools
 */

public class ValueTypeBagItemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ValueTypeBagItemDAO.class);
	// property constants
	public static final String ITEM_NAME = "itemName";

	public void save(ValueTypeBagItem transientInstance) {
		log.debug("saving ValueTypeBagItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ValueTypeBagItem persistentInstance) {
		log.debug("deleting ValueTypeBagItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ValueTypeBagItem findById(java.lang.Long id) {
		log.debug("getting ValueTypeBagItem instance with id: " + id);
		try {
			ValueTypeBagItem instance = (ValueTypeBagItem) getSession().get(
					"model.ValueTypeBagItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ValueTypeBagItem> findByExample(ValueTypeBagItem instance) {
		log.debug("finding ValueTypeBagItem instance by example");
		try {
			List<ValueTypeBagItem> results = getSession()
					.createCriteria("model.ValueTypeBagItem").add(
							Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<ValueTypeBagItem> findByProperty(String propertyName, Object value) {
		log.debug("finding ValueTypeBagItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ValueTypeBagItem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ValueTypeBagItem> findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List<ValueTypeBagItem> findAll() {
		log.debug("finding all ValueTypeBagItem instances");
		try {
			String queryString = "from ValueTypeBagItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ValueTypeBagItem merge(ValueTypeBagItem detachedInstance) {
		log.debug("merging ValueTypeBagItem instance");
		try {
			ValueTypeBagItem result = (ValueTypeBagItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ValueTypeBagItem instance) {
		log.debug("attaching dirty ValueTypeBagItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ValueTypeBagItem instance) {
		log.debug("attaching clean ValueTypeBagItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}