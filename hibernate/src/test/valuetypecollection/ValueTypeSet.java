package test.valuetypecollection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.ValueTypeSetItem;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.ValueTypeSetItemDAO;

public class ValueTypeSet {

	public static void main(String[] args) {
		ValueTypeSetItemDAO dao=new ValueTypeSetItemDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		ValueTypeSetItem item=new ValueTypeSetItem("Item-2");
		Set<String> images=new HashSet<String>(0);
		images.add("Image-1");
		images.add("Image-2");
		item.setImages(images);
		dao.save(item);		
		trx.commit();

		List<ValueTypeSetItem> list=dao.findAll();
		
		Iterator<ValueTypeSetItem> itr=list.iterator();
		while(itr.hasNext()){
			ValueTypeSetItem i=itr.next();
			System.out.println(i.getItemName() + "=" + i.getImages());
		}

	}

}
