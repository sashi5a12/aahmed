package design.patterns.command.StockTrade;

public class Client {

	public static void main(String args[]){
		Agent invoker=new Agent();
		
		StockTrade receiver=new StockTrade();
		StockBuyOrder buyCmd=new StockBuyOrder(receiver);
		
		invoker.placeOrder(buyCmd);
	}
}
