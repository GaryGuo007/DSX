package TradeRelated;

import PriceRelated.Price;

public interface Tradable {
	String getProduct(); // Returns the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the Tradable works with.
	Price getPrice(); //– Returns the price of the Tradable.
	int getOriginalVolume(); //– Returns the original volume (i.e., the original quantity) of the Tradable.
	int getRemainingVolume();// – Returns the remaining volume (i.e., the remaining quantity) of the Tradable.
	int getCancelledVolume(); //– Returns the cancelled volume (i.e., the cancelled quantity) of the Tradable.
	void setCancelledVolume(int newCancelledVolume); // – Sets the Tradable’s cancelled quantity to the value 
	//passed in. This method should throw an exception if the value is invalid (i.e., if the value is negative, or if the
	//requested cancelled volume plus the current remaining volume exceeds the original volume).
	void setRemainingVolume(int newRemainingVolume);// – Sets the Tradable’s remaining quantity to the value
	//passed in. This method should throw an exception if the value is invalid (i.e., if the value is negative, or if the
	//requested remaining volume plus the current cancelled volume exceeds the original volume).
	String getUser();//– Returns the User id associated with the Tradable.
	String getSide(); //– Returns the side (“BUY”/”SELL”) of the Tradable.
	boolean isQuote(); //– Returns true if the Tradable is part of a Quote, returns false if not (i.e., false if it’s part of an order)
	String getId();//– Returns the Tradable “id” – the value each tradable is given once it is received by the system.

}
