package test;

import java.util.ArrayList;
import java.util.List;

import model.ValueTypeListItem;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.ValueTypeListItemDAO;

public class ValueTypeList {
	public static void main(String args[]){
		ValueTypeListItemDAO dao=new ValueTypeListItemDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		ValueTypeListItem item=new ValueTypeListItem();
		List<String> images=new ArrayList<String>();
		item.setItemName("Item-1");
		images.add("image-1");
		images.add("image-2");
		item.setImages(images);
		dao.save(item);
		trx.commit();
	}
}
