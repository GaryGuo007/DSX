package TradeRelated;

import PriceRelated.Price;
import TradeRelated.BookSide;
public class TradableDTO {
	
	public String product;    // - The product (i.e., IBM, GOOG, AAPL, etc.) that the Tradable works with
	public Price price; // - The price of the Tradable
	public int originalVolume;  // - The original volume (i.e., the original quantity) of the Tradable
	public int remainingVolume;  // - The remaining volume (i.e., the remaining quantity) of the Tradable
	public int cancelledVolume;  // - The cancelled volume (i.e., the cancelled quantity) of the Tradable
	public String user;  // - The User id associated with the Tradable.
	public BookSide side;  // - The “side” (BUY/SELL) of the Tradable.
	public boolean isQuote;  // – Set to true if the Tradable is part of a Quote, false if not (i.e., false if it’s part
	//of an order)
	public String id;    // - The Tradable “id” – the value each tradable is given once it is received by the system

	
	
	public String toString(){
		return "Product: "+ product + "Price: " + price + "OriginalVolume: " + originalVolume
				+ "RemainingVolume: " + remainingVolume + "CancelledVolume: " +cancelledVolume +  "User: " + user + "BookSide: " + side +  "IsQuote: " + isQuote + "Id: " + id;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
