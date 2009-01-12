package dao;

import java.sql.Timestamp;
import java.util.List;

import model.Bid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for Bid entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see model.Bid
  * @author MyEclipse Persistence Tools 
 */

public class BidDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BidDAO.class);
	//property constants
	public static final String BID_AMOUNT = "bidAmount";
	public static final String BID_AMOUNT_CURRENCY = "bidAmountCurrency";
	public static final String IS_SUCCESSFUL = "isSuccessful";
	public static final String BID_POSITION = "bidPosition";



    
    public void save(Bid transientInstance) {
        log.debug("saving Bid instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Bid persistentInstance) {
        log.debug("deleting Bid instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Bid findById( java.lang.Long id) {
        log.debug("getting Bid instance with id: " + id);
        try {
            Bid instance = (Bid) getSession()
                    .get("model.Bid", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Bid instance) {
        log.debug("finding Bid instance by example");
        try {
            List results = getSession()
                    .createCriteria("model.Bid")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Bid instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Bid as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByBidAmount(Object bidAmount
	) {
		return findByProperty(BID_AMOUNT, bidAmount
		);
	}
	
	public List findByBidAmountCurrency(Object bidAmountCurrency
	) {
		return findByProperty(BID_AMOUNT_CURRENCY, bidAmountCurrency
		);
	}
	
	public List findByIsSuccessful(Object isSuccessful
	) {
		return findByProperty(IS_SUCCESSFUL, isSuccessful
		);
	}
	
	public List findByBidPosition(Object bidPosition
	) {
		return findByProperty(BID_POSITION, bidPosition
		);
	}
	

	public List findAll() {
		log.debug("finding all Bid instances");
		try {
			String queryString = "from Bid";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Bid merge(Bid detachedInstance) {
        log.debug("merging Bid instance");
        try {
            Bid result = (Bid) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Bid instance) {
        log.debug("attaching dirty Bid instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Bid instance) {
        log.debug("attaching clean Bid instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}