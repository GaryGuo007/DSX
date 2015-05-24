package depaul.stockexchange.client;

import java.sql.Timestamp;
import java.util.ArrayList;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.book.InvalidMarketStateException;
import depaul.stockexchange.book.NoSuchProductException;
import depaul.stockexchange.book.OrderNotFoundException;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.publishers.AlreadySubscribedException;
import depaul.stockexchange.publishers.NotSubscribedException;
import depaul.stockexchange.tradable.Tradable;
import depaul.stockexchange.tradable.TradableDTO;
import depaul.stockexchange.gui.*;
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
		try{
			
		}
		catch{
			
		}
		
		
		UserDisplayManager.updateLastSale(product, price, volume);
		Position.updateLastSale(product, price);

	}
	/*
	 * This method will display the Fill Message in the market display and will
forward the data to the Position object:
	 */
	public void acceptMessage(FillMessage fm){
		try{
			
		}
		catch{
			
		}
		
		Object Timestamp = new Timestamp(System.currentTimeMillis());
		String summary = Timestamp + fm.getSide() + fm.g;
		UserDisplayManager.updateMarketActivity(summary);
		Position.updatePosition(stockSymbol, fillPrice, side, fillVolume)
	}
	/*
	 * This method will display the Cancel Message in the market display:
	 */
	public void acceptMessage(CancelMessage cm){
		try{
			
		}
		catch{
			
		}
		
		Object Timestamp = new Timestamp(System.currentTimeMillis());
		String summary = ;
		UserDisplayManager.updateMarketActivity(summary);

	}
	/*
	 * This method will display the Market Message in the market
display:
	 */
	
	public void acceptMarketMessage(String message){
		try{
			
		}
		catch{
			
		}
		
		
		UserDisplayManager.updateMarketState(message);

	}
	
	/*
	 * This method will display the Ticker data in the
market display:
	 */
	public void acceptTicker(String product, Price price, char direction){
		try{
			
		}
		catch{
			
		}
		
		UserDisplayManager.updateTicker(product, price, direction);
	}
	/*
	 * This method
will display the Current Market data in the market display:
	 */
	public void acceptCurrentMarket(String product, Price bPrice, int bVolume, Price sPrice, int sVolume){
		try{
			
		}
		catch{
			
		}
		
		UserDisplayManager.updateMarketData(product, bPrice, bVolume, sPrice, sVolume);
	}
	
	/*
	 * This method will connect the user to the trading system
	 */
	void connect(){
		 UserCommandService.getInstance().connect(this.userName, connectionId);

	}
	
	/*
	 * This method will disconnect the user to the trading system.
	 */
	void disConnect(){
		 UserCommandService.getInstance().disConnect(userName, connectionId);

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
    		throw new DataValidationException("Product should not be null.");
    	}
    	if (price == null) {
    		throw new DataValidationException("Price should not be null");
    	}
    	if (volume <= 0) {
    		throw new DataValidationException("Volume should not less than 0.");
    	}
    	if (side == null) {
    		throw new DataValidationException("Side should not be null");
    	}
    	return null;
    }
	
	/*
	 * This method forwards the order cancel request
to the user command service as follows
	 */
	void submitOrderCancel(String product, BookSide side, String orderId) throws InvalidConnectionIdException, UserNotConnectedException, InvalidMarketStateException, DataValidationException, NoSuchProductException, OrderNotFoundException{
		 UserCommandService.getInstance().submitOrderCancel(userName, connectionId, product, side, orderId);

	}
	
	/*
	 * This method forwards the new
quote request to the user command service as follows:
	 */
	
	void submitQuote(String product, Price bPrice, int bVolume, Price sPrice, int sVolume) throws DataValidationException, InvalidMarketStateException, NoSuchProductException, NotSubscribedException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().submitQuote(userName, connectionId, product, bPrice, bVolume, sPrice, sVolume);

	}
	
	/*
	 * This method forwards the quote cancel request to the user command service
as follows:
	 */
	void submitQuoteCancel(String product) throws InvalidConnectionIdException, UserNotConnectedException, InvalidMarketStateException, DataValidationException, NoSuchProductException{
		 UserCommandService.getInstance().submitQuoteCancel(userName, connectionId, product);

	}
	
	/*
	 * This method forwards the current market subscription to the user
command service as follows:
	 */
	
	void subscribeCurrentMarket(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeCurrentMarket(userName, connectionId, product);

	}
	
	/*
	 * This method forwards the last sale subscription to the user command service
as follows:
	 */
	
	void subscribeLastSale(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeLastSale(userName, connectionId, product);

	}
	/*
	 * This method forwards the message subscription to the user command service
as follows:
	 */
	void subscribeMessages(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeMessages(userName, connectionId, product);

	}
	
	/*
	 * This method forwards the ticker subscription to the user command service as
follows:
	 */
	void subscribeTicker(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeTicker(userName, connectionId, product);

	}
	/*
	 * Returns the value of the all Sock the User owns (has bought but not sold).
	 */
	
	Price getAllStockValue(){
		return Position.getInstance().getAllStockValue();
		
	}
	
	/*
	 * Returns the difference between cost of all stock purchases and stock sales.
	 */
	Price getAccountCosts(){
		return Position.getInstance().getAccountCosts();
		
	}
	/*
	 * Returns the difference between current value of all stocks owned and the account costs.
	 */
	
	Price getNetAccountValue(){
		return Position.getInstance().getNetAccountValue();
		
	}
	
	/*
	 * Allows the User object to submit a Book Depth request for the specified
stock. To do this, return the results of a call to the user command service’s “getBookDepth” method passing this user’s
user name, connection id, and the String product passed into this method as the parameters to that method.
	 */
	String[][] getBookDepth(String product) throws DataValidationException, NoSuchProductException, InvalidConnectionIdException, UserNotConnectedException{
		return UserCommandService.getInstance().getBookDepth(userName, connectionId, product);
		
	}
	
	/*
	 * Allows the User object to query the market state (OPEN, PREOPEN, CLOSED). To do this,
return the results of a call to the user command service’s “getMarketState” method passing this user’s user name,
connection id as the parameters to that method.
	 */
	String getMarketState() throws InvalidConnectionIdException, UserNotConnectedException{
		return UserCommandService.getInstance().getMarketState(userName, connectionId);
		
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
		return Position.getInstance().getStockPositionValue(product);
		
	}
	
	/*
	 * Returns the value of the specified stock that this user owns.
	 */
	int getStockPositionVolume(String product) {
		return Position.getInstance().getStockPositionVolume(product);
		
	}
	/*
	 * Returns a list of all the Stocks the user owns.
	 */
	ArrayList<String> getHoldings(){
		return Position.getInstance().getHoldings();
		
	}
	
	/*
	 * Gets a list of DTO’s containing information on
all Orders for this user for the specified product with remaining volume.
	 */
	ArrayList<TradableDTO> getOrdersWithRemainingQty(String product) throws InvalidConnectionIdException, UserNotConnectedException, DataValidationException, NoSuchProductException{
		
		return UserCommandService.getInstance().getOrdersWithRemainingQty(userName, connectionId, product);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
