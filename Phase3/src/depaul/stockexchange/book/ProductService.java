package depaul.stockexchange.book;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.tradable.*;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.publishers.*;
import depaul.stockexchange.price.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * The ProductService is the Façade to the entities that make up the Products (Stocks), and the Product
Books (“booked” tradables on the Buy and Sell side).
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class ProductService {
	private HashMap<String, ProductBook> allBooks = new HashMap< String, ProductBook >(){
		String = stockSymbol;
		ProductBook = productBook;
	
	  //MarketState = CLOSED;
	}
	
	/*
	 * This method will return a List of TradableDTOs containing any orders with remaining quantity for the user and the
stock specified.
	 */
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName, String product){
		
		
		allBooks.get(product).getOrdersWithRemainingQty(userName);
		
		return getOrdersWithRemainingQty(userName, product);
		
	}
	
	/*
	 * This method will return a List of MarketDataDTO containing the best buy price/volume and sell price/volume for
the specified stock product.
	 */
	public synchronized MarketDataDTO getMarketData(String product) {
		allBooks.get(product).getMarketData();
		
		return getMarketData(product);
		
	}
	
	/*
	 * This method should simply return the current product state (OPEN, PREOPEN, CLOSED) value.
	 */
	public synchronized MarketState getMarketState() {
		return getMarketState();
		
	}
	
	/*
	 * This method should first check that the String product symbol is a real stock symbol (check if it is a key in the
	“allBooks” HashMap”) If not throw an exception (i.e., “NoSuchProductException”).
	 */
	public synchronized String[][] getBookDepth(String product) throws NosuchProductException{
		if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(product).getBookDepth();
		//Return the String[][] array that method returns.
		return getBookDepth(product);
		
	}
	
	/*
	 * This method should simply return an Arraylist containing all the keys in the “allBooks” HashMap (return new
ArrayList<String>(allBooks.keySet());).
	 */
	public synchronized ArrayList<String> getProductList(){
		return new ArrayList<String>(allBooks.keySet());;
		
	}
	
	/*
	 * This method should update the market state to the new value passed in.
	 */
	public synchronized void setMarketState(MarketState ms) {
		if (ms == null) //
		throw new InvalidMarketStateTransition("The transition has problem. ");
		
		if (ms.equals("OPEN"))
			allBooks.get(getProductList()).openMarket();
		if (ms.equals("CLOSED")) 
			allBooks.get(getProductList()).closeMarket();
	}
	
	/*
	 * This method will create a new stock product that can be used for trading.
	 */
	public synchronized void createProduct(String product) {
		if (product == null || product.isEmpty())
			throw new DataValidationException("");
		else if(allBooks.entrySet().contains(product)) 
			throw new ProductAlreadyExistsException("");
		else {
			ProductBook pd = new ProductBook();
			allBooks.put(product, pd);
		}
	}
	
	/*
	 * This method should forward the provided Quote to the appropriate product book.
	 */
	public synchronized void submitQuote(Quote q) {
		if (product.getMarketData() =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(q).addToBook(q);
		//Return the String[][] array that method returns.
		return getBookDepth(product);
		
	}
	
	/*
	 * This method should forward the provided Order to the appropriate product book.
	 */
	public synchronized String submitOrder(Order o) {
		if (o.getMarketState() =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (getMarketState() =="PREOPEN" && o.isMarket())  throw new InvalidMarketStateException("You cannot submit MKT orders during PREOPEN");
		else if (!allBooks.containsKey(o)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(key).addToBook(o);

		return o.getId();
		
	}
	
	/*
	 * This method should forward the provided Order Cancel to the appropriate product book.
	 */
	public synchronized void submitOrderCancel(String product, BookSide side, String orderId) {
		if (product.getMarketState() =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(product).cancelOrder(side, orderId);

	}
	
	/*
	 * This method should forward the provided Quote Cancel to the appropriate product book.
	 */
	public synchronized void submitQuoteCancel(String userName, String product) {
		if (getMarketState() =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(product).cancelQuote(product);

	}
	

}
