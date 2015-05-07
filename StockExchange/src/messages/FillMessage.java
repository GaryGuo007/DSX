package messages;

import messages.CancelMessage.BookSide;
import price.Price;
//import DePaul.StockExchange.InvalidTradableValue;

/**
 * The FillMessage class encapsulates data related to the fill (trade) of an order or quote-side. FillMessage
 * objects should be immutable. FillMessage data elements are the same as the CancelMessage 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class FillMessage extends Message implements Comparable<FillMessage>{
	public enum BookSide {BUY, SELL}
	
	public FillMessage(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidMessageArgumentException {
		super(user, product, price, volume, details, side, id);
	}
	public int compareTo(FillMessage fm) {
		if(this.getPrice() < fm.getPrice())
		return 0;
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", getUser(), getProduct(),
				getPrice(), getVolume(), getDetails(), getSide(), getId());
	}

}
