package actions.indexed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IndexedBusinessLayer {

	// in reality this data should come from a database, here it is just in memory
	private static Map<String,OrderItem> orderMap = new HashMap<String, OrderItem>();

	static {
		orderMap.put("493-LW", new OrderItem("493-LW", "Coke, 48oz", "6"));
		orderMap.put("693-CL", new OrderItem("693-CL", "Granola Snack, 1 lb","3"));
		orderMap.put("145-JD", new OrderItem("145-JD", "Pretzel Sticks, 130 oz", "1"));
		orderMap.put("247-WQ", new OrderItem("247-WQ", "Popcorn with Butter, Super Large", "6"));
		orderMap.put("789-MB", new OrderItem("789-MB", "Mike & Ike, 3 lbs", "2"));
	}

	public static List<OrderItem> getOrders() {
		return new ArrayList<OrderItem>(orderMap.values());
	}

	public static void updateOrderQty(List<OrderItem> orderList) {
		for (Iterator<OrderItem> orderIter = orderList.iterator(); orderIter.hasNext();) {
			OrderItem newItem = orderIter.next();
			String key = newItem.getProductId();
			OrderItem orderItem = (OrderItem) orderMap.get(key);
			if (orderItem != null) {
				orderItem.setQuantity(newItem.getQuantity());
			}
		}
	}
}
