package com.examples.service.impl;

import java.util.List;

import com.examples.dao.CustomerDao;
import com.examples.model.Customer;
import com.examples.service.CustomerBo;

public class CustomerBoImpl implements CustomerBo {
	         
	CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void addCustomer(Customer customer) {
		customerDao.addCustomer(customer);
	}

	public List<Customer> findAllCustomer() {
		return customerDao.findAllCustomer();
	}
}