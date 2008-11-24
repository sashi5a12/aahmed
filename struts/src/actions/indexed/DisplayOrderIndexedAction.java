package actions.indexed;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import forms.indexed.OrderIndexedForm;

public class DisplayOrderIndexedAction extends Action {

	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {
		OrderIndexedForm indexedForm = (OrderIndexedForm) form;

		// get the list of orders from our business layer
		List<OrderItem> orderList = IndexedBusinessLayer.getOrders();

		// save the list of order on our form
		indexedForm.setOrderList(orderList);

		return mapping.findForward("success");
	}

}
