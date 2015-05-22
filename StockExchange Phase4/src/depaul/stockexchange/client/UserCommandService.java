package depaul.stockexchange.client;

import java.util.ArrayList;
import java.util.HashMap;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.price.Price;
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
	public synchronized long connect(User user){
		return 0;
		
	}
    /*
     * This method will disconnect the user from the
trading system.
     */
	public synchronized void disConnect(String userName, long connId){
		
	}
	
	/*
	 * Forwards the call of “getBookDepth” to
the ProductService.
	 */
	public String[][] getBookDepth(String userName, long connId, String product){
		return null;
		
	}
	/*
	 * Forwards the call of “getMarketState” to the
ProductService.
	 */
	public String getMarketState(String userName, long connId){
		return userName;
		
	}
	/*
	 * Forwards the call of “getOrdersWithRemainingQty” to the ProductService.
	 */
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName, long connId, String product){
		return null;
		
	}
	/*
	 * This method should return a sorted list of the
available stocks on this system, received from the ProductService.
	 */
	public ArrayList<String> getProducts(String userName, long connId){
		return null;
		
	}
	/*
	 * This method will create an order object using the data passed in, and will forward the
order to the ProductService’s “submitOrder” method.
	 */
	public String submitOrder(String userName, long connId, String product, Price price, int volume, BookSide side){
		return null;
		
	}
	/*
	 * This
method will forward the provided information to the ProductService’s “submitOrderCancel” method.
	 */
	public void submitOrderCancel(String userName, long connId, String product, BookSide side, String orderId){
		
	}
	/*
	 * This method will create a quote object using the data passed in, and will forward the quote to the
ProductService’s “submitQuote” method.
	 */
	public void submitQuote(String userName, long connId, String product, Price bPrice, int bVolume, Price sPrice, int
			sVolume){
		
	}
	/*
	 * This method will forward the provided
data to the ProductService’s “submitQuoteCancel” method.
	 */
	public void submitQuoteCancel(String userName, long connId, String product){
		
	}
	
	/*
	 * This method will forward the
subscription request to the CurrentMarketPublisher.
	 */
	public void subscribeCurrentMarket(String userName, long connId, String product){
		
	}
	/*
	 * This method will forward the
subscription request to the LastSalePublisher.
	 */
	public void subscribeLastSale(String userName, long connId, String product){
		
	}
	/*
	 * This method will forward the subscription
request to the MessagePublisher.
	 */
	public void subscribeMessages(String userName, long conn, String product){
		
	}
	/*
	 * This method will forward the subscription
request to the TickerPublisher.
	 */
	public void subscribeTicker(String userName, long conn, String product){
		
	}
	
	/*
	 * This method will forward the unsubscribe
request to the CurrentMarketPublisher.
	 */
	
	public void unSubscribeCurrentMarket(String userName, long conn, String product){
		
	}
	/*
	 * This method will forward the unsubscribe
request to the LastSalePublisher.
	 */
	public void unSubscribeLastSale(String userName, long conn, String product){
		
	}
	
	/*
	 * method will forward the un-subscribe
request to the TickerPublisher.
	 */
	public void unSubscribeTicker(String userName, long conn, String product) {
		
	}
	/*
	 * This method will forward the unsubscribe
request to the MessagePublisher
	 */
	public void unSubscribeMessages(String userName, long conn, String product){
		
	}
	
	
	
	
	
	
	
}
