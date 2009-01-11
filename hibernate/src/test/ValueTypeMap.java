package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import model.ValueTypeMapItem;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.ValueTypeMapItemDAO;

public class ValueTypeMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ValueTypeMapItemDAO dao=new ValueTypeMapItemDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		ValueTypeMapItem item=new ValueTypeMapItem();
		SortedMap<String,String> images=new TreeMap<String, String>();
		item.setItemName("Item-2");
		images.put("C", "Image-1");
		images.put("B", "Image-2");
		images.put("A", "Image-3");
		item.setImages(images);
		dao.save(item);
		trx.commit();
		
		List<ValueTypeMapItem>  list=dao.findAll();
		Iterator<ValueTypeMapItem> itr=list.iterator();
		while(itr.hasNext()){
			ValueTypeMapItem i=itr.next();
			System.out.println(i.getItemName() +"="+i.getImages());
		}
	}

}
