package dao;

import java.util.List;

import model.SubClassBillingDetails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SubClassBillingDetails entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see model.SubClassBillingDetails
 * @author MyEclipse Persistence Tools
 */

public class SubClassBillingDetailsDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(SubClassBillingDetailsDAO.class);
	// property constants
	public static final String CC_NUMBER = "ccNumber";
	public static final String CC_EXP_MONTH = "ccExpMonth";
	public static final String CC_EXP_YEAR = "ccExpYear";
	public static final String BA_ACCOUNT = "baAccount";
	public static final String BA_BANK_NAME = "baBankName";
	public static final String BA_SWIFT = "baSwift";

	public void save(SubClassBillingDetails transientInstance) {
		log.debug("saving SubClassBillingDetails instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SubClassBillingDetails persistentInstance) {
		log.debug("deleting SubClassBillingDetails instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SubClassBillingDetails findById(java.lang.Long id) {
		log.debug("getting SubClassBillingDetails instance with id: " + id);
		try {
			SubClassBillingDetails instance = (SubClassBillingDetails) getSession()
					.get("model.SubClassBillingDetails", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SubClassBillingDetails> findByExample(
			SubClassBillingDetails instance) {
		log.debug("finding SubClassBillingDetails instance by example");
		try {
			List<SubClassBillingDetails> results = getSession().createCriteria(
					"model.SubClassBillingDetails").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SubClassBillingDetails> findByProperty(String propertyName,
			Object value) {
		log.debug("finding SubClassBillingDetails instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SubClassBillingDetails as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SubClassBillingDetails> findByCcNumber(Object ccNumber) {
		return findByProperty(CC_NUMBER, ccNumber);
	}

	public List<SubClassBillingDetails> findByCcExpMonth(Object ccExpMonth) {
		return findByProperty(CC_EXP_MONTH, ccExpMonth);
	}

	public List<SubClassBillingDetails> findByCcExpYear(Object ccExpYear) {
		return findByProperty(CC_EXP_YEAR, ccExpYear);
	}

	public List<SubClassBillingDetails> findByBaAccount(Object baAccount) {
		return findByProperty(BA_ACCOUNT, baAccount);
	}

	public List<SubClassBillingDetails> findByBaBankName(Object baBankName) {
		return findByProperty(BA_BANK_NAME, baBankName);
	}

	public List<SubClassBillingDetails> findByBaSwift(Object baSwift) {
		return findByProperty(BA_SWIFT, baSwift);
	}

	public List<SubClassBillingDetails> findAll() {
		log.debug("finding all SubClassBillingDetails instances");
		try {
			String queryString = "from SubClassBillingDetails";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SubClassBillingDetails merge(SubClassBillingDetails detachedInstance) {
		log.debug("merging SubClassBillingDetails instance");
		try {
			SubClassBillingDetails result = (SubClassBillingDetails) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SubClassBillingDetails instance) {
		log.debug("attaching dirty SubClassBillingDetails instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SubClassBillingDetails instance) {
		log.debug("attaching clean SubClassBillingDetails instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}