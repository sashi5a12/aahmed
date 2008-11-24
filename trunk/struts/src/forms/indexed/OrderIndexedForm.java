package forms.indexed;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import actions.indexed.OrderItem;

public class OrderIndexedForm extends ActionForm {

	private static final long serialVersionUID = -289263655882481241L;
	private List<OrderItem> orderList; // list of OrderItem objects

	public void setOrderList(List<OrderItem> orderList) {
		this.orderList = orderList;
	}

	public List<OrderItem> getOrderList() {
		return this.orderList;
	}

	// this is the method that will be called to save the indexed properties when the form is saved
	public OrderItem getOrderItem(int index) {
		
		// make sure that orderList is not null
		if (this.orderList == null) {
			this.orderList = new ArrayList<OrderItem>();
		}

		// indexes do not come in order, populate empty spots
		while (index >= this.orderList.size()) {
			this.orderList.add(new OrderItem());
		}

		// return the requested item
		return (OrderItem) orderList.get(index);
	}
	
//	public void setOrderItem(int index, )

}