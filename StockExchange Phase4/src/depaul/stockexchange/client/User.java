package depaul.stockexchange.client;

import java.util.ArrayList;

import depaul.stockexchange.*;
import depaul.stockexchange.messages.CancelMessage;
import depaul.stockexchange.messages.FillMessage;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.tradable.TradableDTO;

/**
 * The â€œUserâ€� interface contains the method declarations that any class 
 * that wishes to be a â€œUserâ€� of the DSX trading system must implement.
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
     * This info is used by â€œUsersâ€� to track stock sales 
     * and volumes and is sometimes displayed in a GUI.
     * @param product
     *      A String stock symbol (, etc)
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
     * This info is used by to track stock price movement, 
     * and is sometimes displayed in a GUI.
     * @param product
     *      A stock symbol (, etc)
     * @param p
     *      A Price object holding the value of the last sale (trade) of that stock
     * @param direction 
     *      A âce represents 
     *      an increase or decrease in the Stock's price.
     */
    void acceptTicker(String product, Price p, char direction);

	
    /**
     * This info is used by to update their market display screen 
     * so that they are always looking at the most current market data.
     * These values as a group tell the user the 
     * For example: AMZN: BUY 220@12.80 and SELL 100@12.85.
     * @param product
     *      A String stock symbol (, etc.)
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
	
    
    
    //some need to add thrown exception!
    
    /**
     * Instructs a User object to connect to the trading system.
     */
    void connect();
    
    /**
     * Instructs a User object to disconnect from the trading system.
     */
    void disConnect();
    
    /**
     * Requests the opening of the market display if the user is connected.
     */
    void showMarketDisplay();
    /**
     * Allows the User object to submit a new
Order request
     */
    String submitOrder(String product, Price price, int volume, BookSide side) throws DataValidationException;
    /**
     * Allows the User object to submit a new Order
Cancel request
     */
    void submitOrderCancel(String product, BookSide side, String orderId);
    /**
     * Allows the User
object to submit a new Quote request
     */
    void submitQuote(String product, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume);
    /**
     * Allows the User object to submit a new Quote Cancel request
     */
    void submitQuoteCancel(String product);
    /**
     * Allows the User object to subscribe for Current Market for the specified
Stock.
     */
    void subscribeCurrentMarket(String product);
    /**
     * Allows the User object to subscribe for Last Sale for the specified Stock.
     */
    void subscribeLastSale(String product);
    /**
     * Allows the User object to subscribe for Messages for the specified Stock.
     */
    void subscribeMessages(String product);
    /**
     * Allows the User object to subscribe for Ticker for the specified Stock.
     *
     */
    void subscribeTicker(String product);
    /**
     * Returns the value of the all Sock the User owns (has bought but not sold)
     */
    Price getAllStockValue();
    /**
     * Returns the difference between cost of all stock purchases and stock sales
     */
    Price getAccountCosts();
    /**
     * Returns the difference between current value of all stocks owned and the account costs
     */
    Price getNetAccountValue();
    /**
     * Allows the User object to submit a Book Depth request for the specified stock.
     */
    String[][] getBookDepth(String product);
    /**
     * Allows the User object to query the market state (OPEN, PREOPEN, CLOSED).
     */
    String getMarketState();
    /**
     * Returns a list of order id’s for the orders this user has submitted.
     */
    ArrayList<TradableUserData> getOrderIds();
    /**
     * Returns a list of the stock products available in the trading system.
     */
    ArrayList<String> getProductList();
    /**
     * Returns the value of the specified stock that this user owns
     */
    Price getStockPositionValue(String sym);
    /**
     * Returns the volume of the specified stock that this user owns
     */
    int getStockPositionVolume(String product);
    /**
     * Returns a list of all the Stocks the user owns
     */
    ArrayList<String> getHoldings();
    /**
     * Gets a list of DTO’s containing information on
all Orders for this user for the specified product with remaining volume.
     */
    ArrayList<TradableDTO> getOrdersWithRemainingQty(String product);
    

}
