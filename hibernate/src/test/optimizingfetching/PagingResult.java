package test.optimizingfetching;

import java.util.List;

import model.Users;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import dao.UsersDAO;

public class PagingResult {

	public static void main(String[] args) {
		UsersDAO dao=new UsersDAO();
		Session session=dao.getSession();
		
/*		Query query=session.createQuery("from Users u order by u.userId");
		query.setFirstResult(10*1);
		query.setMaxResults(10);
*/		
/*		Criteria query = session.createCriteria(Users.class);
		query.addOrder( Order.asc("userId") );
		query.setFirstResult(5);
		query.setMaxResults(10);
*/		
		Query query =session.createSQLQuery("select {u.*} from USERS {u}").addEntity("u", Users.class);
		query.setFirstResult(5);
		query.setMaxResults(10);
		
		List<Users> list=query.list();
		for (Users users : list) {
			System.out.println(users);
		}
		session.close();
	}

}
