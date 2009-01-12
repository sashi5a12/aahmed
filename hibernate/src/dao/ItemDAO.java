package dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import model.Item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for Item entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see model.Item
  * @author MyEclipse Persistence Tools 
 */

public class ItemDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(ItemDAO.class);
	//property constants
	public static final String OBJ_VERSION = "objVersion";
	public static final String ITEM_NAME = "itemName";
	public static final String DESCRIPTION = "description";
	public static final String INITIAL_PRICE = "initialPrice";
	public static final String INITIAL_PRICE_CURRENCY = "initialPriceCurrency";
	public static final String RESERVE_PRICE = "reservePrice";
	public static final String RESERVE_PRICE_CURRENCY = "reservePriceCurrency";
	public static final String ITEM_STATE = "itemState";



    
    public void save(Item transientInstance) {
        log.debug("saving Item instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Item persistentInstance) {
        log.debug("deleting Item instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Item findById( java.lang.Long id) {
        log.debug("getting Item instance with id: " + id);
        try {
            Item instance = (Item) getSession()
                    .get("model.Item", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Item instance) {
        log.debug("finding Item instance by example");
        try {
            List results = getSession()
                    .createCriteria("model.Item")
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
      log.debug("finding Item instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Item as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByObjVersion(Object objVersion
	) {
		return findByProperty(OBJ_VERSION, objVersion
		);
	}
	
	public List findByItemName(Object itemName
	) {
		return findByProperty(ITEM_NAME, itemName
		);
	}
	
	public List findByDescription(Object description
	) {
		return findByProperty(DESCRIPTION, description
		);
	}
	
	public List findByInitialPrice(Object initialPrice
	) {
		return findByProperty(INITIAL_PRICE, initialPrice
		);
	}
	
	public List findByInitialPriceCurrency(Object initialPriceCurrency
	) {
		return findByProperty(INITIAL_PRICE_CURRENCY, initialPriceCurrency
		);
	}
	
	public List findByReservePrice(Object reservePrice
	) {
		return findByProperty(RESERVE_PRICE, reservePrice
		);
	}
	
	public List findByReservePriceCurrency(Object reservePriceCurrency
	) {
		return findByProperty(RESERVE_PRICE_CURRENCY, reservePriceCurrency
		);
	}
	
	public List findByItemState(Object itemState
	) {
		return findByProperty(ITEM_STATE, itemState
		);
	}
	

	public List findAll() {
		log.debug("finding all Item instances");
		try {
			String queryString = "from Item";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Item merge(Item detachedInstance) {
        log.debug("merging Item instance");
        try {
            Item result = (Item) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Item instance) {
        log.debug("attaching dirty Item instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Item instance) {
        log.debug("attaching clean Item instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}