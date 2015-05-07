package messages;

import messages.BookSideClass.BookSide;
import price.Price;
import DePaul.StockExchange.*;

public class Message {
	private String user;
	private String product;
	private Price price;
	private int volume;
	private String details;
	private BookSide side;
	public String id;

	/**
	 * The Message class encapsulates data related to the cancellation and fill(trade) of 
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
	
	public Message(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidMessageArgumentException {
		setUser(user);
		setProduct(product);
		setPrice(price);
		setVolume(volume);
		setDetails(details);
		setSide(side);
		setId(id);
	}
		
	public String getUser() {
		return user;
	}

	private void setUser(String user) throws InvalidMessageArgumentException {
		if(user == null || user.isEmpty()) throw new InvalidMessageArgumentException("Invalid argument: user"); 
		this.user = user;
	}

	public String getProduct() {
		return product;
	}

	private void setProduct(String product) throws InvalidMessageArgumentException {
		if(product == null || product.isEmpty()) throw new InvalidMessageArgumentException("Invalid argument: product"); 
		this.product = product;
	}

	public Price getPrice() {
		return price;
	}

	private void setPrice(Price price)  throws InvalidMessageArgumentException{
		if(price == null) throw new InvalidMessageArgumentException("Invalid argument: price"); 
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	private void setVolume(int volume)  throws InvalidMessageArgumentException{
		if(volume < 0) throw new InvalidMessageArgumentException("Invalid argument: Your volume is negative!"); 
		this.volume = volume;
	}

	public String getDetails() {
		return details;
	}


	private void setDetails(String details)  throws InvalidMessageArgumentException{
		if(details == null) throw new InvalidMessageArgumentException("Invalid argument: details"); 
		this.details = details;
	}

	public BookSide getSide() {
		return side;
	}

//	private void setSide(BookSide side)  throws InvalidMessageArgumentException{
//		if(side != BookSide.BUY && side != BookSide.SELL) throw new InvalidMessageArgumentException("Invalid argument: side"); 
//		this.side = side;
//	}
	
	private void setSide(BookSide side) {
		this.side = side;
	}

	public String getId() {
		return id;
	}

	private void setId(String id)  throws InvalidMessageArgumentException{
		if(id == null || id.isEmpty()) throw new InvalidMessageArgumentException("Invalid argument: id"); 
		this.id = id;
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", user, product,
				price, volume, details, side, id);
	}


}
