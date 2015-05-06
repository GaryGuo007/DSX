package DePaul.StockExchange.Messages;
import DePaul.StockExchange.Price.*;
import DePaul.StockExchange.*;

/**
 * The CancelMessage class encapsulates data related to the cancellation of an order or quote-side by a
 * user, or by the trading system. CancelMessage objects should be immutable.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public class CancelMessage implements Comparable<CancelMessage>{
	public enum BookSide {
	    BUY, SELL
	}
	private String user;
	private String product;
	private Price price;
	private int volume;
	private String details;
	private BookSide side;
	public String id;

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
	public CancelMessage(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidTradableValue {
		this.setUser(user);
		this.setProduct(product);
		this.setPrice(price);
		this.setVolume(volume);
		this.setDetails(details);
		this.setSide(side);
		this.setId(id);
	}
		
	public String getUser() {
		return user;
	}

	private void setUser(String user) {
		this.user = user;
	}

	public String getProduct() {
		return product;
	}

	private void setProduct(String product) {
		this.product = product;
	}

	public Price getPrice() {
		return price;
	}

	private void setPrice(Price price) {
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	private void setVolume(int volume) {
		this.volume = volume;
	}

	public String getDetails() {
		return details;
	}


	private void setDetails(String details) {
		this.details = details;
	}

	public BookSide getSide() {
		return side;
	}

	private void setSide(BookSide side) {
		this.side = side;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	
	@Override
	public int compareTo(CancelMessage cm) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", user, product,
				price, volume, details, side, id);
	}
}
