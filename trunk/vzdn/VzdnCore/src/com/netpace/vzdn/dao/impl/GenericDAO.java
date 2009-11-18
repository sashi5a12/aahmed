package com.netpace.vzdn.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.netpace.vzdn.dao.IGenericDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;

	/**
		 * @author Ikramullah Khan
		 * This abstract class provides the implementation of the basic CRUD operations for all the
		 * extending client DAOs. The client DAO must have a no-argument constructor which must call
		 * this class's one-argument constructor to resolve the template class at run-time. The first
		 * generic argument after the class-name specifies the class at runtime and the second argument
		 * defines the type of the primary key the object is using.
	 */
public abstract class GenericDAO<T,PK extends Serializable> implements IGenericDAO<T,PK>{
	
	private static Logger log = Logger
	.getLogger(GenericDAO.class);
	
	private Class<T> pojoClass;
	/**
	 * Single argument constructing setting the type of the runtime object in pojoClass object
	 */
	public GenericDAO(Class<T> pojoClass){
		this.pojoClass = pojoClass;
	}
	/**
	 * save method would insert the given object into its respective table. 
	 */
	public void save(T entity) {    	
    	Session hibernateSession = null;
    	//Transaction hibernateTransaction = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		//hibernateTransaction = hibernateSession.beginTransaction();
    		log.info("Going to save "+ pojoClass.getName() +" object");
    		hibernateSession.save(entity);   
    		log.info(pojoClass.getName() + " Object saved successfully");
	     } 
    	catch (RuntimeException re) {
    		log.info("Some problem with saving "+ pojoClass.getName()+" object");
        	if(null != hibernateSession){
        		//hibernateTransaction.rollback();
        	}
	        throw re;
        }
    }
	 /**
     * Remove the given object from the database.
     * 
     */
    public void delete(T entity) {
    	Session hibernateSession = null;
    	//Transaction hibernateTransaction = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		//hibernateTransaction = hibernateSession.beginTransaction();
    		log.info("Going to delete " + pojoClass.getName() + " object");
    		hibernateSession.delete(entity);
    		log.info(pojoClass.getName() +" Object removed successfully");
	     } 
    	catch (RuntimeException re) {
    		log.info("Some problem with removing "+ pojoClass.getName() +" object");
        	if(null != hibernateSession){
        		//hibernateTransaction.rollback();
        	}
	        throw re;
        }			
    }
    /**
     * Update / merge the given object into the database
     * 
     */
    public T update(T entity) {
    	Session hibernateSession = null;
    	//Transaction hibernateTransaction = null;
    	T object = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		//hibernateTransaction = hibernateSession.beginTransaction();
    		log.info("Going to update "+ pojoClass.getName()+" object");
    		//hibernateSession.evict(entity);
    		hibernateSession.saveOrUpdate(entity);
    		//object = (T)hibernateSession.saveOrUpate(entity);
    		log.info(pojoClass.getName() + " Object updated successfully");
    		return object;
	     } 
    	catch (RuntimeException re) {
    		log.info("Some problem with updating " +pojoClass.getName()+ " object");
        	/*if(null != hibernateSession){
        		//hibernateTransaction.rollback();
        	}*/
	        throw re;
        }
    }
    /**
	 * Find the object having a given id.
	 * 
	 */
    public T findById(PK id) {
    	Session hibernateSession = null;
    	T object = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		log.info("Going to find "+pojoClass.getName()+" object");
    		object = (T)hibernateSession.get(pojoClass, id);
    		log.info(pojoClass.getName() + " Object found successfully");
    		return object;
	     } 
    	catch (RuntimeException re) {
    		log.info("Some problem with finding "+ pojoClass.getName() +" object");
            throw re;
        }			
    }    
    /**
	 * Find all records of the table represented by the given entity.
	 * 
	 */
	public List<T> findAll() {
		Session hibernateSession = null;
		List<T> objects = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		hibernateSession.beginTransaction();
    		log.info("Going to find all "+pojoClass.getClass()+" object");
    		Query query = hibernateSession.createQuery("from "+pojoClass.getName()+" as objects");
    		objects = query.list();
    		log.info("All "+pojoClass.getName()+" Object found successfully");
    		hibernateSession.getTransaction().commit();    		
    		return objects;
	     } 
    	catch (RuntimeException re) {
    		log.info("Some Issue with finding all " +pojoClass.getName()+ " object");
	        throw re;
        }		
    	finally{
    		if(null != hibernateSession){
    			hibernateSession.close();
    		}
    	}
	}

}
