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

public class CountryDAOTest extends TestCase {

	public void testGetCountries() {
		CountryDAO dao = new CountryDAO();
		for(int i = 1; i <= 5; i++) {
			Transaction tx = dao.getSession().beginTransaction();
			TestTimer timer = new TestTimer("testGetCountries");
			List countries = dao.getCountries();
			tx.commit();
			timer.done();
			dao.closeSession();
			assertNotNull(countries);
			assertEquals(countries.size(),229);			
		}
	}
}
