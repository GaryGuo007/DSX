package messages;
import messages.BookSideClass.BookSide;
import price.Price;
<<<<<<< HEAD
//import DePaul.StockExchange.InvalidTradableValue;
=======
import DePaul.StockExchange.*;
>>>>>>> c424e88ecbd7a9d5f0e57a883ce7a4d5557ef0bb

/**
 * The FillMessage class encapsulates data related to the fill (trade) of an order or quote-side. FillMessage
 * objects should be immutable. FillMessage data elements are the same as the CancelMessage 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class FillMessage extends Message implements Comparable<FillMessage>{
<<<<<<< HEAD
	public enum BookSide {BUY, SELL}
=======
>>>>>>> c424e88ecbd7a9d5f0e57a883ce7a4d5557ef0bb
	
	public FillMessage(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidMessageArgumentException {
		super(user, product, price, volume, details, side, id);
	}
<<<<<<< HEAD
	public int compareTo(FillMessage fm) {
		if(this.getPrice() < fm.getPrice())
=======

	@Override
	public int compareTo(FillMessage fm) {
<<<<<<< HEAD
>>>>>>> c424e88ecbd7a9d5f0e57a883ce7a4d5557ef0bb
		return 0;
=======
		if(fm != null)
			return this.getPrice().compareTo(fm.getPrice());
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
