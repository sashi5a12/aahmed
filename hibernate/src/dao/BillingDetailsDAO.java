package dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import model.BillingDetails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BillingDetails entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.BillingDetails
 * @author MyEclipse Persistence Tools
 */

public class BillingDetailsDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BillingDetailsDAO.class);
	// property constants
	public static final String BILLING_DETAILS_TYPE = "billingDetailsType";
	public static final String OBJ_VERSION = "objVersion";
	public static final String OWNER = "owner";
	public static final String BA_ACCOUNT = "baAccount";
	public static final String BA_BANKNAME = "baBankname";
	public static final String BA_SWIFT = "baSwift";

	public void save(BillingDetails transientInstance) {
		log.debug("saving BillingDetails instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BillingDetails persistentInstance) {
		log.debug("deleting BillingDetails instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BillingDetails findById(java.lang.Long id) {
		log.debug("getting BillingDetails instance with id: " + id);
		try {
			BillingDetails instance = (BillingDetails) getSession().get(
					"model.BillingDetails", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BillingDetails instance) {
		log.debug("finding BillingDetails instance by example");
		try {
			List results = getSession().createCriteria("model.BillingDetails")
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
		log.debug("finding BillingDetails instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BillingDetails as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBillingDetailsType(Object billingDetailsType) {
		return findByProperty(BILLING_DETAILS_TYPE, billingDetailsType);
	}

	public List findByObjVersion(Object objVersion) {
		return findByProperty(OBJ_VERSION, objVersion);
	}

	public List findByOwner(Object owner) {
		return findByProperty(OWNER, owner);
	}

	public List findByBaAccount(Object baAccount) {
		return findByProperty(BA_ACCOUNT, baAccount);
	}

	public List findByBaBankname(Object baBankname) {
		return findByProperty(BA_BANKNAME, baBankname);
	}

	public List findByBaSwift(Object baSwift) {
		return findByProperty(BA_SWIFT, baSwift);
	}

	public List findAll() {
		log.debug("finding all BillingDetails instances");
		try {
			String queryString = "from BillingDetails";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BillingDetails merge(BillingDetails detachedInstance) {
		log.debug("merging BillingDetails instance");
		try {
			BillingDetails result = (BillingDetails) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BillingDetails instance) {
		log.debug("attaching dirty BillingDetails instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BillingDetails instance) {
		log.debug("attaching clean BillingDetails instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}