////////////////////////////////////////////////////////////////////////////////
// CountryDAOTest: $Source$
// TODO Class summary goes here
//
// Created : 26 oct. 2005 by jfsmart
// Last modified $Date$ by $Author$
// Revision: $Revision$
// Version : $ID$
// Copyright (c) 2005
////////////////////////////////////////////////////////////////////////////////

package test.caching;

import java.util.List;

import org.hibernate.Transaction;

import dao.*;
import model.*;

import junit.framework.TestCase;

public class EmployeeDAOTest extends TestCase {

	CountryDAO countryDao = new CountryDAO();
	EmployeeDAO employeeDao = new EmployeeDAO();

	/**
	 * Ensure that the Hibernate session is availiable
	 * to avoid the Hibernate initialisation interfering with
	 * the benchmarks
	 */
	protected void setUp() throws Exception {		
		super.setUp();
		countryDao.getSession();
	}

	public void testGetNZEmployees() {
		TestTimer timer = new TestTimer("testGetNZEmployees");
		Transaction tx = employeeDao.getSession().beginTransaction();
		Country nz = countryDao.findCountryByCode("nz");
		List kiwis = employeeDao.getEmployeesByCountry(nz);
		tx.commit();
		employeeDao.closeSession();
		timer.done();
	}

	public void testGetAUEmployees() {
		TestTimer timer = new TestTimer("testGetAUEmployees");
		Transaction tx = employeeDao.getSession().beginTransaction();
		Country au = countryDao.findCountryByCode("au");
		List aussis = employeeDao.getEmployeesByCountry(au);	
		tx.commit();
		employeeDao.closeSession();
		timer.done();
	}

	public void testRepeatedGetEmployees() {
		testGetNZEmployees();
		testGetAUEmployees();
		testGetNZEmployees();
		testGetAUEmployees();
	}
}
