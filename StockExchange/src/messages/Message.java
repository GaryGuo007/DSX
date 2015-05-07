package messages;

import messages.CancelMessage.BookSide;
import price.Price;
import DePaul.StockExchange.InvalidTradableValue;

public class Message {
	private String user;
	private String product;
	private Price price;
	private int volume;
	private String details;
	private BookSide side;
	public String id;

	public Message(String user, String product, Price price, 
			int volume, String details, BookSide side, String id) 
					throws InvalidTradableValue {
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

	private void setUser(String user) throws InvalidTradableValue {
		if(user == null || user.isEmpty() || user == "") throw new InvalidTradableValue("Invalid argument: user"); 
		this.user = user;
	}

	public String getProduct() {
		return product;
	}

	private void setProduct(String product) throws InvalidTradableValue {
		if(product == null || product.isEmpty() || product == "") throw new InvalidTradableValue("Invalid argument: product"); 
		this.product = product;
	}

	public Price getPrice() {
		return price;
	}

	private void setPrice(Price price)  throws InvalidTradableValue{
		if(price == null) throw new InvalidTradableValue("Invalid argument: price"); 
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	private void setVolume(int volume)  throws InvalidTradableValue{
		if(volume < 0) throw new InvalidTradableValue("Invalid argument: Your volume is negative!"); 
		this.volume = volume;
	}

	public String getDetails() {
		return details;
	}


	private void setDetails(String details)  throws InvalidTradableValue{
		if(details == null) throw new InvalidTradableValue("Invalid argument: details"); 
		this.details = details;
	}

	public BookSide getSide() {
		return side;
	}

	private void setSide(BookSide side)  throws InvalidTradableValue{
		if(side != BookSide.BUY && side != BookSide.SELL) throw new InvalidTradableValue("Invalid argument: side"); 
		this.side = side;
	}

	public String getId() {
		return id;
	}

	private void setId(String id)  throws InvalidTradableValue{
		if(id == null || id.isEmpty() || id == "") throw new InvalidTradableValue("Invalid argument: id"); 
		this.id = id;
	}
	
	public String toString() {
		return String.format("User: %s, Product: %s, Price: %s, Volume: %s, "
				+ "Details: %s, Side: %s, Id: %s", user, product,
				price, volume, details, side, id);
	}


}
