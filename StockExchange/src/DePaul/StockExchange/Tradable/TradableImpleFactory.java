package DePaul.StockExchange.Tradable;


import DePaul.StockExchange.InvalidPriceOperation;
import DePaul.StockExchange.Price.Price;

public class TradableImpleFactory {
	
	 public static TradableImpleFactory build(String userName,String productSymbol, 
			 Price orderPrice, int originalVolume, String side,
			 String id,boolean isQuote) throws InvalidPriceOperation
	    {
		  return  TradableImpleFactory.build(userName, productSymbol, orderPrice, 
				  originalVolume, side, id, isQuote);
	    }

	

}