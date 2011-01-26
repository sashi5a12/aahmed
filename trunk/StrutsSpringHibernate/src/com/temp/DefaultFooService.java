package com.temp;

public class DefaultFooService implements FooService {
	private FooDao fooDao;
	
	public FooDao getFooDao() {
		return fooDao;
	}

	public void setFooDao(FooDao fooDao) {
		this.fooDao = fooDao;
	}

	public Foo getFoo(String fooName) {
		throw new UnsupportedOperationException();
	}

	public Foo getFoo(String fooName, String barName) {
		throw new UnsupportedOperationException();
	}

	public void insertFoo(Foo foo) {
		fooDao.insertFoo(foo);
	}

	public void updateFoo(Foo foo) {
		throw new UnsupportedOperationException();
	}

}
