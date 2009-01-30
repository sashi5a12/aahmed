package test;

import java.sql.Timestamp;

import model.Bid;
import model.CategorizedItem;
import model.Category;
import model.Item;
import model.Users;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.UsersDAO;

/*
delete from item_images;
delete from bid where bid_id>3;
delete from categorized_item;
delete from category_item;
delete from item where item_id>2;
delete from users where user_id>2;
*/
public class PopulateTestData {
	public static void main(String args[]){
		UsersDAO dao=new UsersDAO();
		
		Session session=dao.getSession();
		Category category=(Category)session.load(Category.class, 1L);
		
		Transaction trx=session.beginTransaction();
		for(int i=1; i<=20; i++){
			Users user=new Users(i,"First-"+i, "Last-"+i, "UserName-"+i, "Password-"+i, "email-"+i, i,false, new Timestamp(System.currentTimeMillis()));
			session.save(user);
			
			Item item=new Item();
			item.setDescription("Description-"+i);
			item.setInitialPrice(new Double(i));
			item.setItemName("Item-"+i);
			item.setItemState("State-"+i);
			item.setObjVersion(i);
			item.setApprovedBy(user);
			item.setSeller(user);

			for (int j=1; j<=20; j++){
				item.getImages().add("File-"+i+"-"+j);
				Bid bid=new Bid();
				bid.setBidAmount(new Double(i+"."+j));
				bid.setCreated(new Timestamp(System.currentTimeMillis()));
				bid.setIsSuccessful("Y");
				item.addBid(bid);
			}
			category.getItems().add(item);
			session.save(item);
			
			CategorizedItem categorizedItem=new CategorizedItem(user.getUsername(), category, item);
			session.save(categorizedItem);			
		}
		session.flush();
		session.clear();
		trx.commit();
		session.close();
	}
}
