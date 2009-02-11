package test.optimizingfetching;

import java.util.List;

import org.hibernate.Session;

import dao.BidDAO;

public class ReportQueries {

	public static void main(String[] args) {
		BidDAO bidDao=new BidDAO();
		Session session=bidDao.getSession();
		Long itemCount=(Long)session.createQuery("select count(i) from Item i").uniqueResult();
		Long itemDescCount=(Long)session.createQuery("select count(distinct i.description) from Item i").uniqueResult();
		Double bidAmountSum=(Double)session.createQuery("select sum(b.bidAmount) from Bid b").uniqueResult();
//		List bidAvgWithItemIds=session.createQuery("select b.item.id, count(b), avg(b.bidAmount) from Bid b group by b.item.id").list();
		List bidAvgWithItemIds=session.createQuery("select bidItem.id, count(bid), avg(bid.bidAmount) from Bid bid join bid.item bidItem where bid.isSuccessful='Y' group by bidItem.id").list();
		List userHavingA=session.createQuery("select user.lastname, count(user) from Users user group by user.lastname having user.lastname like 'A%'").list();
		session.close();
		System.out.println("Item Count= "+itemCount);
		System.out.println("Item Description Count= "+itemDescCount);
		System.out.println("Bid amount sum= "+bidAmountSum);
		for (Object obj : bidAvgWithItemIds) {
			Object[] objs=(Object[])obj;
			System.out.println("itemId="+objs[0]+", count="+objs[1]+", amount="+objs[2]);
		}
		for (Object obj : userHavingA) {
			Object[] objs=(Object[])obj;
			System.out.println("lastname="+objs[0]+", count="+objs[1]);
		}
		
	}

}
