package com.examples.service;

import java.util.List;

import com.examples.model.Customer;

public interface CustomerBo {

	void addCustomer(Customer customer);

	List<Customer> findAllCustomer();

}