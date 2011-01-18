package com.examples.dao;

import java.util.List;

import com.examples.model.Customer;

public interface CustomerDao{
	 
	void addCustomer(Customer customer);
 
	List<Customer> findAllCustomer();
 
}