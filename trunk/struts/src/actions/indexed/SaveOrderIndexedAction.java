package actions.indexed;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import forms.indexed.OrderIndexedForm;

public class SaveOrderIndexedAction extends Action {

	public ActionForward execute(ActionMapping mapping, 
								ActionForm form,
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {
		OrderIndexedForm indexedForm = (OrderIndexedForm) form;

		// get the list of order from the form
		List<OrderItem> orderList = indexedForm.getOrderList();

		// update the ordered quantities using the business layer
		IndexedBusinessLayer.updateOrderQty(orderList);

		return mapping.findForward("success");
	}

}
