package client;

import messages.CancelMessage;
import messages.FillMessage;
import price.Price;
/**
 * The "User" interface contains the method declarations that any class that wishes to be a "User" of the
 * DSX trading system must implement. Though we will use this User type in this phase, we will not actually
 * have any implementers -- only the interface is needed. Implementers will come in a later phase. To keep
 * this interface separate from your other classes, this interface should be put into its own package (i.e.,
 * "client"). More classes will be added to that package later in the project.
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author junmin Liu
 *
 */
public interface User {
	
	/*
	 * This will return the String username of this user
	 */
	String getUserName();
	
	/*
	 * This will accept a String stock symbol ("IBM", "GE", etc), 
	 * a Price object holding the value of the last sale (trade) of that stock, 
	 * and the quantity (volume) of that last sale. 
	 * This info is used by "Users" to track stock sales 
	 * and volumes and is sometimes displayed in a GUI.
	 */
	void acceptLastSale(String product, Price p, int v);
	
	/*
	 * This will accept a FillMessage object which contains information related to 
	 * an order or quote trade. This is like a receipt sent to the user to document
	 * the details when an order or quote-side of theirs trades.
	 */
	void acceptMessage(FillMessage fm);
	
	/*
	 * This will accept a CancelMessage object which contains information related to 
	 * an order or quote cancel. This is like a receipt sent to the user to document
	 * the details when an order or quote-side of theirs is canceled.
	 */
	void acceptMessage(CancelMessage cm);
	
	/*
	 * This will accept a String which contains market information related to 
	 * a Stock Symbol they are interested in.
	 */
	void acceptMarketMessage(String message);
	
	/*
	 * This will accept a stock symbol ("IBM", "GE", etc), a Price object holding the value of 
	 * the last sale (trade) of that stock, and a "char" indicator of whether the "ticker" price 
	 * represents an increase or decrease in the Stock's price. This info is used by "users" to 
	 * track stock price movement, and is sometimes displayed in a GUI.
	 */
	void acceptTicker(String product, Price p, char direction);
	
	/*
	 * This will accept a String stock symbol ("IBM", "GE", etc.), a Price object holding 
	 * the current BUY side price for that stock, an int holding the current BUY side volume (quantity), 
	 * a Price object holding the current SELL side price for that stock, and an int holding 
	 * the current SELL side volume (quantity). These values as a group tell the user 
	 * the "current market" for a stock. For example: AMZN: BUY 220@12.80 and SELL 100@12.85.
	 * This info is used by "Users" to update their market display screen 
	 * so that they are always looking at the most current market data.
	 */
	void acceptCurrentMarket(String product, Price bp, int bv, Price sp, int sv);
	

}
