package com.examples.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Employee;

import com.examples.dao.CustomerDao;
import com.examples.model.Customer;

@Transactional
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

	public void addCustomer(Customer customer) {
		customer.setCreatedDate(new Date());
		getHibernateTemplate().save(customer);
	}
	
	@SuppressWarnings("unchecked")
	public List<Customer> findAllCustomer() {
		return getHibernateTemplate().find("from Customer");
	}
}