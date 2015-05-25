package depaul.stockexchange.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.book.InvalidMarketStateException;
import depaul.stockexchange.book.NoSuchProductException;
import depaul.stockexchange.book.OrderNotFoundException;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.InvalidPriceOperation;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.publishers.AlreadySubscribedException;
import depaul.stockexchange.publishers.NotSubscribedException;
import depaul.stockexchange.tradable.Tradable;
import depaul.stockexchange.tradable.TradableDTO;
import depaul.stockexchange.gui.*;

/**
 *The UserImpl class (this class can go in the same package as �User� interface) is our application�s
implementation of the �User� interface. This represents a �real� user in the trading system; many of these
objects can be active in our system.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public class UserImpl implements User{
	/*
	 * 1. A String to hold their user name
2. A long value to hold their �connection id� � provided to them when they connect to the system.
3. A String list of the stocks available in the trading system. The user fills this list once connected based upon data
received from the trading system.
4. A list of TradableUserData objects that contains information on the orders this user has submitted (needed for
cancelling).
5. A reference to a Position object (part of this assignment) which holds the values of the users stocks, costs, etc.
6. A reference to a UserDisplayManager object (part of this assignment) that acts as a fa�ade between the user
and the market display.
	 */
	String userName;
	long connectionId;
	List[] stocks ;
	ArrayList<Tradable> TradableUserData = new ArrayList<Tradable>();
	
	//not sure
	private Position position;
	//
	private UserDisplayManager userDisplay;
		
	
	
	
	
	
	
	//private UserImpl(){};
	public UserImpl(String string) {
		// TODO Auto-generated constructor stub
	}
	/*
	 * This method should return the String username of this user.
	 */
	public String getUserName(){
		return userName;
		
	}
	/*
	 * This method should call the user display manager�s
updateLastSale method, passing the same 3 parameters that were passed in.
	 */
	public void acceptLastSale(String product, Price price, int volume){
		try{
			UserDisplayManager.getInstance().updateLastSale(product, price, volume);
			Position.getInstance().updateLastSale(product, price);
		}
		catch(Exception e){
			   System.out.println(e.getMessage());	
		}
		
		
		

	}
	/*
	 * This method will display the Fill Message in the market display and will
forward the data to the Position object:
	 */
	public void acceptMessage(FillMessage fm){
		try{
			Object Timestamp = new Timestamp(System.currentTimeMillis());
			String summary = "{"+Timestamp +"} Fill Message: " + fm.getSide() + " " + fm.getVolume() + " " + fm.getProduct() + " " + fm.getPrice() + " " + fm.getDetails() + " " +"[Tradable Id: " + fm.getId() + "]";
			UserDisplayManager.getInstance().updateMarketActivity(summary);
			Position.getInstance().updatePosition(fm.getProduct(), fm.getPrice(), fm.getSide(), fm.getVolume());
		}
		catch(Exception e){
			   System.out.println(e.getMessage());	
		}
		
	}
	/*
	 * This method will display the Cancel Message in the market display:
	 */
	public void acceptMessage(CancelMessage cm){
		try{
			Object Timestamp = new Timestamp(System.currentTimeMillis());
			String summary = "{"+Timestamp +"} Cancel Message: " + cm.getSide() + " " + cm.getVolume() + " " + cm.getProduct() + " " + cm.getPrice() + " " + cm.getDetails() + " " +"[Tradable Id: " + cm.getId() + "]";
			UserDisplayManager.getInstance().updateMarketActivity(summary);

		}
		catch(Exception e){
			   System.out.println(e.getMessage());	
		}
		
		
	}
	/*
	 * This method will display the Market Message in the market
display:
	 */
	
	public void acceptMarketMessage(String message){
		try{
			UserDisplayManager.getInstance().updateMarketState(message);

		}
		catch(Exception e){
		   System.out.println(e.getMessage());	
		}
		
		
		//UserDisplayManager.getInstance().updateMarketState(message);

	}
	
	/*
	 * This method will display the Ticker data in the
market display:
	 */
	public void acceptTicker(String product, Price price, char direction){
		try{
			UserDisplayManager.getInstance().updateTicker(product, price, direction);
		}
		catch(Exception e){
			   System.out.println(e.getMessage());	
		}
		
	}
	/*
	 * This method
will display the Current Market data in the market display:
	 */
	public void acceptCurrentMarket(String product, Price bPrice, int bVolume, Price sPrice, int sVolume){
		try{
			UserDisplayManager.getInstance().updateMarketData(product, bPrice, bVolume, sPrice, sVolume);
		}
		catch(Exception e){
			   System.out.println(e.getMessage());	
		}
		
	}
	
	/*
	 * This method will connect the user to the trading system
	 */
	public void connect() throws AlreadyConnectedException, InvalidConnectionIdException, UserNotConnectedException{
		connectionId = UserCommandService.getInstance().connect(this);
		stocks = UserCommandService.getInstance().getProducts(userName, connectionId);
	}
	
	/*
	 * This method will disconnect the user to the trading system.
	 */
	public void disConnect() throws InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().disConnect(userName, connectionId);
	}
	
	/*
	 * This method qwill activate the market display.
	 */
	
	public void showMarketDisplay() throws Exception{
		if(stocks == null){
			throw new UserNotConnectedException("");
		}
		if(TradableUserData == null){
			// not sure
			UserDisplayManager marketDisplay = new UserDisplayManager();
		}
		UserDisplayManager.getInstance().showMarketDisplay();
	}
	
	/*
	 * This method forwards the new order
	request to the user command service and saves the resulting order id.
	 */
	/**
     * Allows the User object to submit a new
Order request
     */
    public String submitOrder(String product, Price price, int volume, BookSide side) throws DataValidationException {
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
	public void submitOrderCancel(String product, BookSide side, String orderId) throws InvalidConnectionIdException, UserNotConnectedException, InvalidMarketStateException, DataValidationException, NoSuchProductException, OrderNotFoundException{
		 UserCommandService.getInstance().submitOrderCancel(userName, connectionId, product, side, orderId);

	}
	
	/*
	 * This method forwards the new
quote request to the user command service as follows:
	 */
	
	public void submitQuote(String product, Price bPrice, int bVolume, Price sPrice, int sVolume) throws DataValidationException, InvalidMarketStateException, NoSuchProductException, NotSubscribedException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().submitQuote(userName, connectionId, product, bPrice, bVolume, sPrice, sVolume);

	}
	
	/*
	 * This method forwards the quote cancel request to the user command service
as follows:
	 */
	public void submitQuoteCancel(String product) throws InvalidConnectionIdException, UserNotConnectedException, InvalidMarketStateException, DataValidationException, NoSuchProductException{
		 UserCommandService.getInstance().submitQuoteCancel(userName, connectionId, product);

	}
	
	/*
	 * This method forwards the current market subscription to the user
command service as follows:
	 */
	
	public void subscribeCurrentMarket(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeCurrentMarket(userName, connectionId, product);

	}
	
	/*
	 * This method forwards the last sale subscription to the user command service
as follows:
	 */
	
	public void subscribeLastSale(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeLastSale(userName, connectionId, product);

	}
	/*
	 * This method forwards the message subscription to the user command service
as follows:
	 */
	public void subscribeMessages(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeMessages(userName, connectionId, product);

	}
	
	/*
	 * This method forwards the ticker subscription to the user command service as
follows:
	 */
	public void subscribeTicker(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException{
		 UserCommandService.getInstance().subscribeTicker(userName, connectionId, product);

	}
	/*
	 * Returns the value of the all Sock the User owns (has bought but not sold).
	 */
	
	public Price getAllStockValue() throws InvalidPriceOperation{
		return Position.getInstance().getAllStockValue();
		
	}
	
	/*
	 * Returns the difference between cost of all stock purchases and stock sales.
	 */
	public Price getAccountCosts(){
		return Position.getInstance().getAccountCosts();
		
	}
	/*
	 * Returns the difference between current value of all stocks owned and the account costs.
	 */
	
	public Price getNetAccountValue(){
		return Position.getInstance().getNetAccountValue();
		
	}
	
	/*
	 * Allows the User object to submit a Book Depth request for the specified
stock. To do this, return the results of a call to the user command service�s �getBookDepth� method passing this user�s
user name, connection id, and the String product passed into this method as the parameters to that method.
	 */
	public String[][] getBookDepth(String product) throws DataValidationException, NoSuchProductException, InvalidConnectionIdException, UserNotConnectedException{
		return UserCommandService.getInstance().getBookDepth(userName, connectionId, product);
		
	}
	
	/*
	 * Allows the User object to query the market state (OPEN, PREOPEN, CLOSED). To do this,
return the results of a call to the user command service�s �getMarketState� method passing this user�s user name,
connection id as the parameters to that method.
	 */
	public String getMarketState() throws InvalidConnectionIdException, UserNotConnectedException{
		return UserCommandService.getInstance().getMarketState(userName, connectionId);
		
	}
	/*
	 * Returns a list of order id�s (a data member) for the orders this user has
submitted.
	 */
	public ArrayList< TradableUserData> getOrderIds(){
		return 
		
	}
	
	/*
	 * Returns a list of stocks (a data member) available in the trading system.
	 */
	public ArrayList<String> getProductList(){
		return userName.
		
	}
	
	/*
	 * Returns the value of the specified stock that this user owns.
	 */
	public Price getStockPositionValue(String product) throws InvalidPriceOperation {
		return Position.getInstance().getStockPositionValue(product);
		
	}
	
	/*
	 * Returns the value of the specified stock that this user owns.
	 */
	public int getStockPositionVolume(String product) {
		return Position.getInstance().getStockPositionVolume(product);
		
	}
	/*
	 * Returns a list of all the Stocks the user owns.
	 */
	public ArrayList<String> getHoldings(){
		return Position.getInstance().getHoldings();
		
	}
	
	/*
	 * Gets a list of DTO�s containing information on
all Orders for this user for the specified product with remaining volume.
	 */
	public ArrayList<TradableDTO> getOrdersWithRemainingQty(String product) throws InvalidConnectionIdException, UserNotConnectedException, DataValidationException, NoSuchProductException{
		
		return UserCommandService.getInstance().getOrdersWithRemainingQty(userName, connectionId, product);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
