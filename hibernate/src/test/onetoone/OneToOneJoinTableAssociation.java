package test.onetoone;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.OtoJtaItemDAO;
import dao.OtoJtaShipmentDAO;
import model.OtoJtaItem;
import model.OtoJtaShipment;

public class OneToOneJoinTableAssociation {
	public static void main(String args[]){
		OtoJtaItem item=new OtoJtaItem("Item-1");
		OtoJtaShipment shipment=new OtoJtaShipment("State-1", item);
		
		OtoJtaItemDAO itemDao=new OtoJtaItemDAO();
		OtoJtaShipmentDAO shipmentDao=new OtoJtaShipmentDAO();
		Session session=itemDao.getSession();
		Transaction trx=session.beginTransaction();
		itemDao.save(item);
		shipmentDao.save(shipment);
		trx.commit();
		
	}
}
