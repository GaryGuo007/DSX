package messages;
import price.Price;
import DePaul.StockExchange.Price.*;
import DePaul.StockExchange.*;

/**
 * The CancelMessage class encapsulates data related to the cancellation of an order or quote-side by a
 * user, or by the trading system. CancelMessage objects should be immutable.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public class CancelMessage extends Message implements Comparable<CancelMessage>{
	public enum BookSide {
	    BUY, SELL
	}
	public CancelMessage(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidMessageArgumentException {
		super(user, product, price, volume, details, side, id);
	}
	/**
	 * The CancelMessage class encapsulates data related to the cancellation of 
	 * an order or quote-side by a user, or by the trading system. 
	 * CancelMessage objects should be immutable.
	 * 
	 * @param user
	 * 		The String username of the user whose order or quote-side is being cancelled. 
	 * 		Cannot be null or empty.
	 * @param product
	 * 		The string stock symbol that the cancelled order or 
	 * 		quote-side was submitted for ("IBM", "GE", etc.). Cannot be null or empty.
	 * @param price
	 * 		The price specified in the cancelled order or quote-side. Cannot be null.
	 * @param volume
	 * 		The quantity of the order or quote-side that was cancelled. Cannot be negative.
	 * @param details
	 * 		A text description of the cancellation. Cannot be null.
	 * @param side
	 * 		The side (BUY/SELL) of the cancelled order or quote-side. Must be a valid side.
	 * @param id
	 * 		The String identifier of the cancelled order or quote-side. Cannot be null.
	 * @throws InvalidTradableValue
	 */
	
	
	@Override
	public int compareTo(CancelMessage cm) {
		try{
			
		}
		catch( ){
			
		}
		return 0;
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", getUser(), getProduct(),
				getPrice(), getVolume(), getDetails(), getSide(), getId());
	}
}
