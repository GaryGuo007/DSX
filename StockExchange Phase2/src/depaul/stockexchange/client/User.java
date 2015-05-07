package depaul.stockexchange.client;

import depaul.stockexchange.messages.CancelMessage;
import depaul.stockexchange.messages.FillMessage;
import depaul.stockexchange.price.Price;

/**
 * The “User” interface contains the method declarations that any class 
 * that wishes to be a “User” of the DSX trading system must implement.
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author junmin Liu
 *
 */
public interface User {
	
    /**
     * The username of this user
     * @return 
     * This will return the String username of this user
     */
    String getUserName();
    
    /**
     * This info is used by “Users” to track stock sales 
     * and volumes and is sometimes displayed in a GUI.
     * @param product
     *      A String stock symbol (“IBM, “GE”, etc)
     * @param p
     *      A Price object holding the value of the last sale (trade) of that stock
     * @param v 
     *      The quantity (volume) of that last sale
     */
    void acceptLastSale(String product, Price p, int v);
    
    /**
     * This is like a receipt sent to the user to document 
     * the details when an order or quote-side of theirs trades.
     * @param fm 
     *      A FillMessage object which contains
     *      information related to an order or quote trade.
     */
    void acceptMessage(FillMessage fm);

    /**
     * This is like a receipt sent to the user to document
     * the details when an order or quote-side of theirs is canceled.
     * @param cm 
     *      A CancelMessage object which contains
     *      information related to an order or quote cancel.
     */
    void acceptMessage(CancelMessage cm);

    /**
     * This will accept a String which contains market
     * information related to a Stock Symbol they are interested in.
     * @param message 
     *      A String which contains market
     *      information related to a Stock Symbol they are interested in.
     */
    void acceptMarketMessage(String message);


    /**
     * This info is used by “users” to track stock price movement, 
     * and is sometimes displayed in a GUI.
     * @param product
     *      A stock symbol (“IBM, “GE”, etc)
     * @param p
     *      A Price object holding the value of the last sale (trade) of that stock
     * @param direction 
     *      A “char” indicator of whether the “ticker” price represents 
     *      an increase or decrease in the Stock’s price.
     */
    void acceptTicker(String product, Price p, char direction);

	
    /**
     * This info is used by “Users” to update their market display screen 
     * so that they are always looking at the most current market data.
     * These values as a group tell the user the “current market” for a stock. 
     * For example: AMZN: BUY 220@12.80 and SELL 100@12.85.
     * @param product
     *      A String stock symbol (“IBM, “GE”, etc.)
     * @param bp
     *      A Price object holding the current BUY side price for that stock
     * @param bv
     *      An int holding the current BUY side volume (quantity)
     * @param sp
     *      A Price object holding the current SELL side price for that stock
     * @param sv 
     *      An int holding the current SELL side volume (quantity)
     */
    void acceptCurrentMarket(String product, Price bp, int bv, Price sp, int sv);
	

}
