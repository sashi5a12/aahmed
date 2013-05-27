package design.patterns.command.StockTrade;

public class StockBuyOrder implements Order {

	StockTrade stockTrade;

	public StockBuyOrder(StockTrade stockTrade) {
		super();
		this.stockTrade = stockTrade;
	}

	@Override
	public void execute() {
		stockTrade.buyStock();
	}

}
