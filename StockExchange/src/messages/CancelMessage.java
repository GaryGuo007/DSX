package messages;
import messages.BookSide;
import price.*;
<<<<<<< HEAD
//import DePaul.StockExchange.*;
=======
>>>>>>> origin/master

/**
 * The CancelMessage class encapsulates data related to the cancellation of an order or quote-side by a
 * user, or by the trading system. CancelMessage objects should be immutable.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public class CancelMessage extends Message implements Comparable<CancelMessage>{
	
	public CancelMessage(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidMessageArgumentException {
		super(user, product, price, volume, details, side, id);
	}
	
	@Override
	public int compareTo(CancelMessage cm) {
		if(cm != null) 
			return this.getPrice().compareTo(cm.getPrice());
		else 
			return -1;
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", getUser(), getProduct(),
				getPrice(), getVolume(), getDetails(), getSide(), getId());
	}
}
