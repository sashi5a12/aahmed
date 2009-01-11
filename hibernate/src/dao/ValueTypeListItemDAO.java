package dao;

import java.util.List;
import java.util.Set;

import model.ValueTypeListItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ValueTypeListItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.ValueTypeListItem
 * @author MyEclipse Persistence Tools
 */

public class ValueTypeListItemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(ValueTypeListItemDAO.class);
	// property constants
	public static final String ITEM_NAME = "itemName";

	public void save(ValueTypeListItem transientInstance) {
		log.debug("saving ValueTypeListItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ValueTypeListItem persistentInstance) {
		log.debug("deleting ValueTypeListItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ValueTypeListItem findById(java.lang.Long id) {
		log.debug("getting ValueTypeListItem instance with id: " + id);
		try {
			ValueTypeListItem instance = (ValueTypeListItem) getSession().get(
					"model.ValueTypeListItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ValueTypeListItem> findByExample(ValueTypeListItem instance) {
		log.debug("finding ValueTypeListItem instance by example");
		try {
			List<ValueTypeListItem>  results = getSession().createCriteria(
					"model.ValueTypeListItem").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<ValueTypeListItem>  findByProperty(String propertyName, Object value) {
		log.debug("finding ValueTypeListItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ValueTypeListItem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ValueTypeListItem>  findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List<ValueTypeListItem>  findAll() {
		log.debug("finding all ValueTypeListItem instances");
		try {
			String queryString = "from ValueTypeListItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ValueTypeListItem merge(ValueTypeListItem detachedInstance) {
		log.debug("merging ValueTypeListItem instance");
		try {
			ValueTypeListItem result = (ValueTypeListItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ValueTypeListItem instance) {
		log.debug("attaching dirty ValueTypeListItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ValueTypeListItem instance) {
		log.debug("attaching clean ValueTypeListItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}