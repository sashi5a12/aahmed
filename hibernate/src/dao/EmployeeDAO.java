////////////////////////////////////////////////////////////////////////////////
// CountryDAO: $Source$
// TODO Class summary goes here
//
// Created : 26 oct. 2005 by jfsmart
// Last modified $Date$ by $Author$
// Revision: $Revision$
// Version : $ID$
// Copyright (c) 2005
////////////////////////////////////////////////////////////////////////////////

package dao;

import java.util.List;

import org.hibernate.Transaction;

import model.*;

public class EmployeeDAO extends BaseHibernateDAO   {

	public List getEmployeesByCountry(Country country) {
		return getSession().createQuery("from Employee as e where e.country = :country "
				+ " order by e.surname, e.firstname")
				.setParameter("country",country)
				.list();
	}
}
