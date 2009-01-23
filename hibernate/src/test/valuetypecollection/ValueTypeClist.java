package test.valuetypecollection;

import java.util.ArrayList;
import java.util.List;

import model.ValueTypeClistItem;
import model.ValueTypeClistItemImages;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conf.HibernateSessionFactory;

public class ValueTypeClist {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session session=HibernateSessionFactory.getSession();
		Transaction trx=session.beginTransaction();
		ValueTypeClistItem item=new ValueTypeClistItem();
		List<ValueTypeClistItemImages> images=new ArrayList();
		images.add(new ValueTypeClistItemImages("Image-1"));
		images.add(new ValueTypeClistItemImages("Image-2"));
		item.setItemName("Item-2");
		item.setImages(images);
		session.save(item);
		trx.commit();
	}

}
