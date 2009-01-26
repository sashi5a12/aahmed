package test.parentchild;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CategoryDAO;
import model.Category;

public class DeleteOrphan {
	public static void main(String args[]){
		Category pc=new Category();
		pc.setCategoryName("Parent");
		
		pc.addChildCategory(new Category("Child-1"));
		pc.addChildCategory(new Category("Child-2"));
		pc.addChildCategory(new Category("Child-3"));
		
		CategoryDAO dao=new CategoryDAO();
		Session s1=dao.getSession();
		Transaction t1=s1.beginTransaction();
		dao.save(pc);
		t1.commit();
		Category c1=(Category)dao.findByCategoryName("Child-2").get(0);
		s1.close();
		
		
		pc.getChildCategories().remove(c1);
		
		Session s2=dao.getSession();
		Transaction t2=s2.beginTransaction();
		s2.saveOrUpdate(pc);
		t2.commit();
	}
}