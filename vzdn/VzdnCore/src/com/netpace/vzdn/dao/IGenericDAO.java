package com.netpace.vzdn.dao;

import java.io.Serializable;
import java.util.List;


	/**
		 * @author Ikramullah Khan
		 * This interface provides contract for the abstract class GenericDAO
		 * All the client DAOs must extend GenericDAO and optionally implement an interface specific to their needs.
		 * This interface should not be implemented by the client DAOs directly.
		 * As an example, see the UserDAO client class.
	 */
public interface IGenericDAO<T, PK extends Serializable> {
	
	/**
	 * save method would insert the given object into its respective table. 
	 */
    public void save(T entity);   
    /**
     * Remove the given object from the database.
     * 
     */
    public void delete(T entity);
    /**
     * Update / merge the given object into the database
     * 
     */
	public T update(T entity);
	/**
	 * Find the object having a given id.
	 * 
	 */
	public T findById(PK id);
	/**
	 * Find all records of the table represented by the given entity.
	 * 
	 */
	public List<T> findAll();	
}