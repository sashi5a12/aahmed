package test.optimizingfetching;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import model.Category;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;

public class Cache {

	public static void main(String[] args) {
		CategoryDAO dao=new CategoryDAO();
/*		Session s1=dao.getSession();
		Transaction trx=s1.beginTransaction();
		s1.setCacheMode(CacheMode.IGNORE);
		Category p=(Category)s1.load(Category.class, 1L);
		for (int i=2; i<=100; i++){
			Category c=new Category();
			c.setCategoryName("Category-"+i);
			c.setObjVersion(i);
			c.setCreated(new Timestamp(System.currentTimeMillis()));
			c.setParentCategory(p);
			dao.save(c);
			if(i%10==0){
				s1.flush();
				s1.clear();
			}
		}
		trx.commit();
		s1.close();
*/		
		Session s2=dao.getSession();
		List<Category> list=dao.findAll();
		s2.close();
		
		Session s3=dao.getSession();
		Category c1=dao.findById(1L);
		Category c11=dao.findById(11L);
		Category c12=dao.findById(12L);
		Category c13=dao.findById(13L);
//		List<Category> cs=s3.createQuery("from Category where categoryId in (11,12,13)").list();
		s3.close();
		
		System.out.println(c1.getCategoryName());
		System.out.println(c11.getCategoryName());
		System.out.println(c12.getCategoryName());
		System.out.println(c13.getCategoryName());
		/*for (Iterator<Category> iterator = cs.iterator(); iterator.hasNext();) {
			Category category = iterator.next();
			System.out.println(category.getParentCategory().getCategoryName() + "->" + category.getCategoryName());
			
		}*/
	}

}
