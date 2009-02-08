package test.querywithhql;

import java.util.List;

import model.Item;

import org.hibernate.Session;

import dao.ItemDAO;

public class ExpressionsWithCollections {

	public static void main(String[] args) {
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
//		List list=session.createQuery("from Item i where i.bids is not empty").list();
//		List list=session.createQuery("from Item i, Category c where i.itemId = '88' and i member of c.items").list();
//		List list=session.createQuery("from Item i where size(i.bids) = 3").list();
//		List list=session.createQuery("select i.itemId, i.description, i.initialPrice from Item i where i.endDate is null").list();
		List list=session.createQuery("select item.startDate, current_date() from Item item").list();
		session.close();
		System.out.println(list.size());
		/*for (Object object : list) {
			Item item=(Item)object;
			System.out.println(item.getItemName());
		}*/
	}

}
