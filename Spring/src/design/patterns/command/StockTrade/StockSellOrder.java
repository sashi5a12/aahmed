package design.patterns.command.StockTrade;


public class StockSellOrder implements Order{

	StockTrade stockTrade;
	
	public StockSellOrder(StockTrade stockTrade) {
		super();
		this.stockTrade = stockTrade;
	}

	@Override
	public void execute() {
		stockTrade.sellStock();
	}

}
