package DePaul.StockExchange.Messages;
/**
 * The CancelMessage class encapsulates data related to the cancellation of an order or quote-side by a
 * user, or by the trading system. CancelMessage objects should be immutable.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class CancelMessage implements Comparable<CancelMessage>{
	/*
	 * The String username of the user whose order or quote-side is being cancelled. Cannot
be null or empty.
	 */
	private String user;
	/*
	 * The string stock symbol that the cancelled order or quote-side was submitted for
(“IBM”, “GE”, etc.). Cannot be null or empty.
	 */
	private String product;
	/*
	 * The price specified in the cancelled order or quote-side. Cannot be null.
	 */
	private Price price;
	/*
	 * The quantity of the order or quote-side that was cancelled. Cannot be negative.
	 */
	private int volume;
	/*
	 * A text description of the cancellation. Cannot be null.
	 */
	private String details;
	/*
	 * The side (BUY/SELL) of the cancelled order or quote-side. Must be a valid side.
	 */
	private BookSide side;
	/*
	 * The String identifier of the cancelled order or quote-side. Cannot be null.
	 */
	public String id;
	
	
	public String toString() {
		System.out.print("User: " + user + "," + "Product: " + product + "," + "Price: " +
	  price +"," + "Volume: " + volume + "," + "Details: " + details + "," + "Side: " +
				side + "," + "Id: " + id);
	}
}
