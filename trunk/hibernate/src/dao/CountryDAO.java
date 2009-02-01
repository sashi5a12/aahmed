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

import model.*;;

public class CountryDAO extends BaseHibernateDAO  {

	public Country findCountryByCode(String code) {
		return (Country) getSession()
							 .createQuery("from Country as c where c.code = :code")
							  .setParameter("code",code)
							  .uniqueResult();
	}
	
	public List getCountries() {
		return getSession().createQuery(
							  "from Country as c order by c.name")
//							  .setCacheable(true)
							  .list();
	}
}
