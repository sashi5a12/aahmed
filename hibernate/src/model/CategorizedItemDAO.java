package model;

import dao.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * CategorizedItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.CategorizedItem
 * @author MyEclipse Persistence Tools
 */

public class CategorizedItemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(CategorizedItemDAO.class);
	// property constants
	public static final String ADDED_BY_USER = "addedByUser";

	public void save(CategorizedItem transientInstance) {
		log.debug("saving CategorizedItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CategorizedItem persistentInstance) {
		log.debug("deleting CategorizedItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List findByExample(CategorizedItem instance) {
		log.debug("finding CategorizedItem instance by example");
		try {
			List results = getSession().createCriteria("model.CategorizedItem")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CategorizedItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CategorizedItem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAddedByUser(Object addedByUser) {
		return findByProperty(ADDED_BY_USER, addedByUser);
	}

	public List findAll() {
		log.debug("finding all CategorizedItem instances");
		try {
			String queryString = "from CategorizedItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CategorizedItem merge(CategorizedItem detachedInstance) {
		log.debug("merging CategorizedItem instance");
		try {
			CategorizedItem result = (CategorizedItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CategorizedItem instance) {
		log.debug("attaching dirty CategorizedItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CategorizedItem instance) {
		log.debug("attaching clean CategorizedItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}