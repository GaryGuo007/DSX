package depaul.stockexchange.book;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.tradable.*;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.publishers.*;
import depaul.stockexchange.price.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductService {
	private HashMap<String, ProductBook> allBooks = new HashMap< String, ProductBook >(){
		String = stockSymbol;
		ProductBook = productBook;
	
	  //MarketState = CLOSED;
	}
	
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName, String product){
		
		
		allBooks.get(product).getOrdersWithRemainingQty(userName);
		
		return getOrdersWithRemainingQty(userName, product);
		
	}
	public synchronized MarketDataDTO getMarketData(String product) {
		allBooks.get(product).getMarketData();
		
		return getMarketData(product);
		
	}
	public synchronized MarketState getMarketState() {
		return getMarketState();
		
	}
	public synchronized String[][] getBookDepth(String product){
		if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(product).getBookDepth();
		//Return the String[][] array that method returns.
		return getBookDepth(product);
		
	}
	public synchronized ArrayList<String> getProductList(){
		return new ArrayList<String>(allBooks.keySet());;
		
	}
	public synchronized void setMarketState(MarketState ms) {
		if (ms == null) //
		throw new InvalidMarketStateTransition("The transition has problem. ");
		
		if (ms.equals("OPEN"))
			allBooks.get(getProductList()).openMarket();
		if (ms.equals("CLOSED")) 
			allBooks.get(getProductList()).closeMarket();
	}
	
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
	public synchronized void submitQuote(Quote q) {
		if (marketState =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(q).addToBook(q);
		//Return the String[][] array that method returns.
		return getBookDepth(product);
		
	}
	public synchronized String submitOrder(Order o) {
		if (marketState =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (marketState =="PREOPEN" && o.isMarket())  throw new InvalidMarketStateException("You cannot submit MKT orders during PREOPEN");
		else if (!allBooks.containsKey(o)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(key).addToBook(o);

		return o.getId();
		
	}
	public synchronized void submitOrderCancel(String product, BookSide side, String orderId) {
		if (MarketState =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(product).cancelOrder(side, orderId);

	}
	public synchronized void submitQuoteCancel(String userName, String product) {
		if (marketState =="CLOSED")  throw new InvalidMarketStateException("The market is CLOSED.");
		else if (!allBooks.containsKey(product)) throw new NosuchProductException("This product is not a real stock symbol.");
		else allBooks.get(product).cancelQuote(product);

	}
	

}
