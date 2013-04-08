import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import northwind.model.generated.Categories;
import northwind.model.generated.Products;
import northwind.model.generated.Suppliers;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistryBuilder;
import com.packtpub.springhibernate.model.Student;


public class NorthwindTest {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		// configuring Hibernate
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		
		ServiceRegistryBuilder serviceRegistryBuilder=new ServiceRegistryBuilder().applySettings(config.getProperties());
		
		// obtaining a session object
		SessionFactory factory = config.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
		Session session = factory.openSession();
		
		// starting a transaction
		Transaction tx = session.beginTransaction();
		
		//where clause
//		List<Suppliers> suppliers=session.createQuery("from Suppliers as s where s.supplierId=1").list();
		
		//inner join
//		List<Object[]> suppliers=session.createQuery("from Suppliers as s inner join s.products where s.supplierId=1").list();
		
		//join fetch
//		List<Object[]> suppliers=session.createQuery("from Suppliers s join fetch s.products p where s.supplierId=1").list();

		//distinct join fetch
//		List<Object[]> suppliers=session.createQuery("select distinct s from Suppliers s left join fetch s.products p order by s.supplierId").list();

		//Fields
//		List<Object[]> suppliers=session.createQuery("select s.supplierId as supplierId, s.companyName as companyName, s.address from Suppliers as s").list();
		
		
		//select with persisted entity
		/*Categories category=(Categories) session.get(Categories.class, 1);
		
		Query hQry=session.createQuery("from Products p where p.category=:category");
		hQry.setEntity("category", category);
		List<Products> products=hQry.list();*/
		
		//Native query. select from single table 
//		List<Object[]> nc=session.createSQLQuery("SELECT * FROM CATEGORIES").list();
		
		//Native query. with populate persisting object.
		SQLQuery hQry=session.createSQLQuery("select categories.* from Categories categories");
		hQry.addEntity(Categories.class);
		List<Categories> categories=hQry.list();
		
		//Native query. with join
/*		SQLQuery hQry3=session.createSQLQuery("select c.*, p.* from Categories c, Products p where c.categoryId=p.categoryId and p.supplierId=3");
		hQry3.addEntity("c",Categories.class);
		hQry3.addJoin("p", "c.products");
		List<Categories> categories3=hQry3.list();
*/		
		//Criteria with simple query.
		/*Criteria criteria = session.createCriteria(Categories.class);
		List<Categories> list=criteria.list();*/
		
		Criteria criteria=session.createCriteria(Categories.class);
//		Criterion criterian=Restrictions.eq("categoryId", 1);
//		Criterion criterian=Restrictions.ne("categoryId", 1);
//		Criterion criterian=Restrictions.isNotNull("categoryId");
//		criteria.add(criterian);
//		Criteria criteria=session.createCriteria(Categories.class).add(Restrictions.eq("categoryId", 1));
		
		/*Map<String, Object> properties=new HashMap<String, Object>();
		properties.put("categoryId",1);
		properties.put("categoryName","Beverages");
		criteria.add(Restrictions.allEq(properties));*/
		
		
		
//		List<Categories> list=criteria.list();
		
//		Categories c=(Categories) session.load(Categories.class, 1);
//		c.getCategoryName();		
//		Hibernate.initialize(c.getProducts());
		
		// commiting the transaction
		tx.commit();
		session.close();

		//Native query. with childern
//		System.out.println(categories3);
		
		//Native query. with join
//		System.out.println(categories2);
		
		//Native query. with populate persisting object.
		System.out.println(categories);
		
		//select with persisted entity
//		System.out.println(products);
		
		//with native query. select from single table.
		/*for(Object[] obj:nc){
			for (Object o:obj){
				System.out.print(o+"\t");
			}
			System.out.println();
		}*/
		
		//with join fetch
		/*Set noDups=new LinkedHashSet(suppliers);
		System.out.println(suppliers.size());
		System.out.println(noDups.iterator().next().getClass() );
		for (Object object : noDups) {
			Suppliers s=(Suppliers)object;
			System.out.println(s.getSupplierId() + " ----------- " +s.getProducts().size());
		}*/
	
	}

}
