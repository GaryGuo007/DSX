package TradeRelated;

import PriceRelated.PriceFactory;
import PriceRelated.Price;
import TradeRelated.BookSide;

public class TradableDTO {
	
	public String product;    
	public Price price; 
	public int originalVolume;  
	public int remainingVolume; 
	public int cancelledVolume; 
	public String user; 
	public BookSide side;  
	public boolean isQuote; 
	public String id;  

	
	
	public String toString(){
		return "Product: "+ product + "Price: " + price + "OriginalVolume: " + originalVolume
				+ "RemainingVolume: " + remainingVolume + "CancelledVolume: " +cancelledVolume +  "User: " + user + "BookSide: " + side +  "IsQuote: " + isQuote + "Id: " + id;
	}

	

}
