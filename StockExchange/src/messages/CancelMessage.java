package messages;
import messages.BookSideClass.BookSide;
import price.*;
import DePaul.StockExchange.*;

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
					throws InvalidMessageArgumentException, InvalidTradableValue {
		super(user, product, price, volume, details, side, id);
	}
	
	@Override
	public int compareTo(CancelMessage cm) {
<<<<<<< HEAD
<<<<<<< HEAD
		if (cm == null) return -1;
=======
>>>>>>> c424e88ecbd7a9d5f0e57a883ce7a4d5557ef0bb
		return 0;
=======
		if(cm != null) 
			return this.getPrice().compareTo(cm.getPrice());
		else 
			return -1;
>>>>>>> c10b1cc68d93e03c425ddd2cd49f5a2f63762415
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", getUser(), getProduct(),
				getPrice(), getVolume(), getDetails(), getSide(), getId());
	}
}
