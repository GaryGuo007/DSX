package depaul.stockexchange.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import depaul.stockexchange.*;
import depaul.stockexchange.book.InvalidMarketStateException;
import depaul.stockexchange.book.NoSuchProductException;
import depaul.stockexchange.book.ProductService;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.tradable.Order;
import depaul.stockexchange.tradable.Quote;
import depaul.stockexchange.tradable.TradableDTO;

public class UserCommandService {
	/*
	A HashMap<String, Long> to hold user name and connection id pairs. Initially this “connected user ids” HashMap
	should be empty.
	*/
	HashMap<String, Long> connectedUserIds = new HashMap<String, Long>();
	
	/*
	· A HashMap<String, User> to hold user name and user object pairs. Initially this“connected users” HashMap should be
	empty.
	*/
	HashMap<String, User> connectedUsers = new HashMap<String, User>();
	
	/*
	· A HashMap<String, Long> to hold user name and connection-time pairs (connection time is stored as a long). Initially
	this“connected time” HashMap should be empty.
    */
	HashMap<String, Long> connectedTime = new HashMap<String, Long>();
	
	/*
	 * This is a utility method that will be used by many of the
methods in this class to verify the integrity of the user name and connection id passed in with many of the method calls
found here.
	 */
	
	private void verifyUser(String userName, long connId) throws InvalidConnectionIdException, UserNotConnectedException {
		if (!connectedUserIds.containsKey(userName)){
			throw new UserNotConnectedException("This user is not actually connected.");
		}
		if (connId != connectedUserIds.get(userName)){
			throw new InvalidConnectionIdException("This user is not actually connected.");
		}
		
	}
	
	/*
	 * This method will connect the user to the trading system.
	 */
	public synchronized long connect(User user) throws AlreadyConnectedException{
		if (connectedUserIds.containsKey(user)){
			throw new AlreadyConnectedException("This user is alreday connected.");
		}
		connectedUserIds.put(user, System.nanoTime());
		return 0;
		
	}
    /*
     * This method will disconnect the user from the
trading system.
     */
	public synchronized void disConnect(String userName, long connId){
		verifyUser(userName, connId);
		connectedUserIds.remove(userName, value);
		connectedUsers.remove(userName, value);
		connectedTime.remove(userName, value);
		
	}
	
	/*
	 * Forwards the call of “getBookDepth” to
the ProductService.
	 */
	public String[][] getBookDepth(String userName, long connId, String product) throws DataValidationException, NoSuchProductException, InvalidConnectionIdException, UserNotConnectedException{
		verifyUser(userName, connId);
		return  ProductService.getInstance().getBookDepth(product);

		
		
	}
	/*
	 * Forwards the call of “getMarketState” to the
ProductService.
	 */
	public String getMarketState(String userName, long connId) throws InvalidConnectionIdException, UserNotConnectedException{
		verifyUser(userName, connId);

		return ProductService.getInstance().getMarketState().toString();
		
	}
	/*
	 * Forwards the call of “getOrdersWithRemainingQty” to the ProductService.
	 */
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName, long connId, String product) throws InvalidConnectionIdException, UserNotConnectedException, DataValidationException, NoSuchProductException{
		verifyUser(userName, connId);

		return ProductService.getInstance().getOrdersWithRemainingQty (userName, product);
		
	}
	/*
	 * This method should return a sorted list of the
available stocks on this system, received from the ProductService.
	 */
	public ArrayList<String> getProducts(String userName, long connId){
		verifyUser(userName, connId);
		ArrayList<String> h = ProductService.getProductList();
		Collections.sort(h); 
		return h;
		
	}
	/*
	 * This method will create an order object using the data passed in, and will forward the
order to the ProductService’s “submitOrder” method.
	 */
	public String submitOrder(String userName, long connId, String product, Price price, int volume, BookSide side){
		verifyUser(userName, connId);
		Order subOrder = new Order(name, product, price, volume, side);
		String orderId = ProductService.submitOrder();
		return orderId;
		
	}
	/*
	 * This
method will forward the provided information to the ProductService’s “submitOrderCancel” method.
	 */
	public void submitOrderCancel(String userName, long connId, String product, BookSide side, String orderId){
		verifyUser(userName, connId);
		return ProductService.getInstance().submitOrderCancel(product, side, orderId);

	}
	/*
	 * This method will create a quote object using the data passed in, and will forward the quote to the
ProductService’s “submitQuote” method.
	 */
	public void submitQuote(String userName, long connId, String product, Price bPrice, int bVolume, Price sPrice, int
			sVolume){
		verifyUser(userName, connId);
		Quote QuoteObject = new Quote(name, product, buy price, buy volume, sell price, sellVolume);
		ProductService.getInstance().submitQuote(QuoteObject);
	}
	/*
	 * This method will forward the provided
data to the ProductService’s “submitQuoteCancel” method.
	 */
	public void submitQuoteCancel(String userName, long connId, String product) throws InvalidConnectionIdException, UserNotConnectedException, InvalidMarketStateException, DataValidationException, NoSuchProductException{
		verifyUser(userName, connId);
		ProductService.getInstance().submitQuoteCancel( userName, product );
	}
	
	/*
	 * This method will forward the
subscription request to the CurrentMarketPublisher.
	 */
	public void subscribeCurrentMarket(String userName, long connId, String product){
		verifyUser(userName, connId);
		CurrentMarketPublisher.subscribe(connectedUsers.get(userName), product);
	}
	/*
	 * This method will forward the
subscription request to the LastSalePublisher.
	 */
	public void subscribeLastSale(String userName, long connId, String product){
		verifyUser(userName, connId);
		LastSalePublisher.subscribe(connectedUsers.get(userName),product);
	}
	/*
	 * This method will forward the subscription
request to the MessagePublisher.
	 */
	public void subscribeMessages(String userName, long conn, String product){
		verifyUser(userName, connId);
		MessagePublisher.subscribe(connectedUsers.get(userName),product);
	}
	/*
	 * This method will forward the subscription
request to the TickerPublisher.
	 */
	public void subscribeTicker(String userName, long conn, String product){
		verifyUser(userName, connId);
		TickerPublisher.subscribe(connectedUsers.get(userName),product);
	}
	
	/*
	 * This method will forward the unsubscribe
request to the CurrentMarketPublisher.
	 */
	
	public void unSubscribeCurrentMarket(String userName, long conn, String product){
		verifyUser(userName, connId);
		CurrentMarketPublisher.subscribe(connectedUsers.get(userName),product);
	}
	/*
	 * This method will forward the unsubscribe
request to the LastSalePublisher.
	 */
	public void unSubscribeLastSale(String userName, long conn, String product){
		verifyUser(userName, connId);
		LastSalePublisher.subscribe(connectedUsers.get(userName),product);
	}
	
	/*
	 * method will forward the un-subscribe
request to the TickerPublisher.
	 */
	public void unSubscribeTicker(String userName, long conn, String product) {
		verifyUser(userName, connId);
		TickerPublisher.subscribe(connectedUsers.get(userName),product);
	}
	/*
	 * This method will forward the unsubscribe
request to the MessagePublisher
	 */
	public void unSubscribeMessages(String userName, long conn, String product){
		verifyUser(userName, connId);
		MessagePublisher.subscribe(connectedUsers.get(userName),product);
	}
	
	
	
	
	
	
	
}
