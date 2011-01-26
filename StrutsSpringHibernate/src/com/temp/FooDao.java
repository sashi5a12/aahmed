package com.temp;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FooDao extends HibernateDaoSupport{
	public void insertFoo(Foo foo){
		throw new UnsupportedOperationException();
	}
}
