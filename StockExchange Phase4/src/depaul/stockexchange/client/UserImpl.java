package depaul.stockexchange.client;

import java.util.ArrayList;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.tradable.Tradable;
import depaul.stockexchange.tradable.TradableDTO;

public class UserImpl {
	/*
	 * 1. A String to hold their user name
2. A long value to hold their “connection id” – provided to them when they connect to the system.
3. A String list of the stocks available in the trading system. The user fills this list once connected based upon data
received from the trading system.
4. A list of TradableUserData objects that contains information on the orders this user has submitted (needed for
cancelling).
5. A reference to a Position object (part of this assignment) which holds the values of the users stocks, costs, etc.
6. A reference to a UserDisplayManager object (part of this assignment) that acts as a façade between the user
and the market display.
	 */
	String userName;
	long connectionId;
	ArrayList<Tradable> stocksAvailable = new ArrayList<Tradable>();
	ArrayList<Tradable> TradableUserData = new ArrayList<Tradable>();
	
	/*
	 * This method should return the String username of this user.
	 */
	public String getUserName(){
		return userName;
		
	}
	/*
	 * This method should call the user display manager’s
updateLastSale method, passing the same 3 parameters that were passed in.
	 */
	public void acceptLastSale(String product, Price price, int volume){
		
	}
	/*
	 * This method will display the Fill Message in the market display and will
forward the data to the Position object:
	 */
	public void acceptMessage(FillMessage fm){
		
	}
	/*
	 * This method will display the Cancel Message in the market display:
	 */
	public void acceptMessage(CancelMessage cm){
		
	}
	/*
	 * This method will display the Market Message in the market
display:
	 */
	
	public void acceptMarketMessage(String message){
		
	}
	
	/*
	 * This method will display the Ticker data in the
market display:
	 */
	public void acceptTicker(String product, Price price, char direction){
		
	}
	/*
	 * This method
will display the Current Market data in the market display:
	 */
	public void acceptCurrentMarket(String product, Price bPrice, int bVolume, Price sPrice, int sVolume){
		
	}
	
	/*
	 * This method will connect the user to the trading system
	 */
	void connect(){
		
	}
	
	/*
	 * This method will disconnect the user to the trading system.
	 */
	void disConnect(){
		
	}
	
	/*
	 * This method qwill activate the market display.
	 */
	
	void showMarketDisplay(){
		
	}
	
	/*
	 * This method forwards the new order
	request to the user command service and saves the resulting order id.
	 */
	/**
     * Allows the User object to submit a new
Order request
     */
    String submitOrder(String product, Price price, int volume, BookSide side) throws DataValidationException {
    	if (Utils.isNullOrEmpty(product)) {
    		throw new DataValidationException("Product is null.");
    	}
    	if (price == null) {
    		throw new DataValidationException();
    	}
    	if (volume <= 0) {
    		throw new DataValidationException();
    	}
    	if (side == null) {
    		throw new DataValidationException();
    	}
    	return null;
    }
	
	/*
	 * This method forwards the order cancel request
to the user command service as follows
	 */
	void submitOrderCancel(String product, BookSide side, String orderId){
		
	}
	
	/*
	 * This method forwards the new
quote request to the user command service as follows:
	 */
	
	void submitQuote(String product, Price bPrice, int bVolume, Price sPrice, int sVolume){
		
	}
	
	/*
	 * This method forwards the quote cancel request to the user command service
as follows:
	 */
	void submitQuoteCancel(String product){
		
	}
	
	/*
	 * This method forwards the current market subscription to the user
command service as follows:
	 */
	
	void subscribeCurrentMarket(String product){
		
	}
	
	/*
	 * This method forwards the last sale subscription to the user command service
as follows:
	 */
	
	void subscribeLastSale(String product){
		
	}
	/*
	 * This method forwards the message subscription to the user command service
as follows:
	 */
	void subscribeMessages(String product){
		
	}
	
	/*
	 * This method forwards the ticker subscription to the user command service as
follows:
	 */
	void subscribeTicker(String product){
		
	}
	/*
	 * Returns the value of the all Sock the User owns (has bought but not sold).
	 */
	
	Price getAllStockValue(){
		return null;
		
	}
	
	/*
	 * Returns the difference between cost of all stock purchases and stock sales.
	 */
	Price getAccountCosts(){
		return null;
		
	}
	/*
	 * Returns the difference between current value of all stocks owned and the account costs.
	 */
	
	Price getNetAccountValue(){
		return null;
		
	}
	
	/*
	 * Allows the User object to submit a Book Depth request for the specified
stock. To do this, return the results of a call to the user command service’s “getBookDepth” method passing this user’s
user name, connection id, and the String product passed into this method as the parameters to that method.
	 */
	String[][] getBookDepth(String product){
		return null;
		
	}
	
	/*
	 * Allows the User object to query the market state (OPEN, PREOPEN, CLOSED). To do this,
return the results of a call to the user command service’s “getMarketState” method passing this user’s user name,
connection id as the parameters to that method.
	 */
	String getMarketState(){
		return userName;
		
	}
	/*
	 * Returns a list of order id’s (a data member) for the orders this user has
submitted.
	 */
	ArrayList< TradableUserData> getOrderIds(){
		return null;
		
	}
	
	/*
	 * Returns a list of stocks (a data member) available in the trading system.
	 */
	ArrayList<String> getProductList(){
		return null;
		
	}
	
	/*
	 * Returns the value of the specified stock that this user owns.
	 */
	Price getStockPositionValue(String product) {
		return null;
		
	}
	
	/*
	 * Returns the value of the specified stock that this user owns.
	 */
	int getStockPositionVolume(String product) {
		return 0;
		
	}
	/*
	 * Returns a list of all the Stocks the user owns.
	 */
	ArrayList<String> getHoldings(){
		return null;
		
	}
	
	/*
	 * Gets a list of DTO’s containing information on
all Orders for this user for the specified product with remaining volume.
	 */
	ArrayList<TradableDTO> getOrdersWithRemainingQty(String product){
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
