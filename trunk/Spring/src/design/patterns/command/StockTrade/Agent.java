package design.patterns.command.StockTrade;


public class Agent {

	public void placeOrder(Order order){
		order.execute();
	}
}
