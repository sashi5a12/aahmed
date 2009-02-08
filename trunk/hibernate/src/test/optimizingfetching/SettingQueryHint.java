package test.optimizingfetching;

import java.util.Iterator;
import java.util.List;

import model.Category;
import model.Item;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;

import test.caching.TestTimer;

import dao.ItemDAO;

public class SettingQueryHint {
	public static void main(String args[]){
		TestTimer timer=new TestTimer("SettingQueryHint");
		ItemDAO dao=new ItemDAO();
		Session s1=dao.getSession();
		Query q1=s1.createQuery("from Category");

		Iterator<Category> c1=q1.iterate();
		while(c1.hasNext()){
			Category c=c1.next();
			System.out.println(c.getCategoryName());
		}
/*		List<Category> c1=q1.list();
		for (Category item : c1) {
			System.out.println(item.getCategoryName());
		}
*/		s1.close();
		
		Session s2=dao.getSession();
		Query q2=s2.createQuery("from Category");

		Iterator<Category> c2=q2.iterate();
		while(c2.hasNext()){
			Category c=c2.next();
			System.out.println(c.getCategoryName());
		}		
/*		List<Category> c2=q2.list();
		for (Category item : c2) {
			System.out.println(item.getCategoryName());
		}
*/		
		s2.close();
		timer.done();
	}
}
