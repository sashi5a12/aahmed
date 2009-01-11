package test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.ValueTypeBagItem;
import model.ValueTypeSetItem;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.ValueTypeBagItemDAO;
import dao.ValueTypeSetItemDAO;

public class ValueTypeBag {

	public static void main(String[] args) {
		ValueTypeBagItemDAO dao=new ValueTypeBagItemDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		ValueTypeBagItem item=new ValueTypeBagItem("Item-1");
		Set<String> images=new HashSet<String>(0);
		images.add("Image-1");
		images.add("Image-2");
		item.setImages(images);
		dao.save(item);		
		trx.commit();

		List<ValueTypeBagItem> list=dao.findAll();
		
		Iterator<ValueTypeBagItem> itr=list.iterator();
		while(itr.hasNext()){
			ValueTypeBagItem i=itr.next();
			System.out.println(i.getItemName() + "=" + i.getImages());
		}

	}

}
