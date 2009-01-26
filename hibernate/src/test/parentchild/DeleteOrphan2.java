package test.parentchild;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.Category;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;

public class DeleteOrphan2 {

	public static void main(String[] args) {
		CategoryDAO dao=new CategoryDAO();
		Session s1=dao.getSession();
		Category c=dao.findById(22L);
		Category ch1=(Category)dao.findByCategoryName("Child-1").get(0);
		Category ch2=(Category)dao.findByCategoryName("Child-3").get(0);
		s1.close();
		
		c.getChildCategories().remove(ch1);
		c.getChildCategories().remove(ch2);
		
		c.addChildCategory(new Category("New-1"));
		c.addChildCategory(new Category("New-2"));
		
		Session s2=dao.getSession();
		Transaction trx=s2.beginTransaction();
		s2.saveOrUpdate(c);
		trx.commit();
		s2.close();
	}

}
