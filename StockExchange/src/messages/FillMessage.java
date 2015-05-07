package messages;
import messages.BookSideClass.BookSide;
import price.Price;
import DePaul.StockExchange.*;

/**
 * The FillMessage class encapsulates data related to the fill (trade) of an order or quote-side. FillMessage
 * objects should be immutable. FillMessage data elements are the same as the CancelMessage 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class FillMessage extends Message implements Comparable<FillMessage>{
	
	public FillMessage(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidMessageArgumentException {
		super(user, product, price, volume, details, side, id);
	}

	@Override
	public int compareTo(FillMessage fm) {
		if(fm != null)
			return this.getPrice().compareTo(fm.getPrice());
		else 
			return -1;
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", getUser(), getProduct(),
				getPrice(), getVolume(), getDetails(), getSide(), getId());
	}

}
