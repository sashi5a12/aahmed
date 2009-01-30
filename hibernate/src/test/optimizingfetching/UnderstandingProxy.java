package test.optimizingfetching;

import model.Address;
import model.Category;
import model.Item;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.BidDAO;
import dao.CategoryDAO;

public class UnderstandingProxy {

	public static void main(String[] args) {
		CategoryDAO dao=new CategoryDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		Category parentCategory=(Category)session.load(Category.class, 3L);
		System.out.println(parentCategory.getCategoryName());
//		Category childCategory=new Category("Child1");
//		childCategory.setParentCategory(parentCategory);
//		dao.save(childCategory);
		trx.commit();
		session.close();
		System.out.println(parentCategory.getParentCategory().getCategoryId());
		System.out.println(parentCategory.getParentCategory().getCategoryName());
	}

}
