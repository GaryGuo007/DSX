package DePaul.StockExchange.Messages;
/**
 * The FillMessage class encapsulates data related to the fill (trade) of an order or quote-side. FillMessage
 * objects should be immutable. FillMessage data elements are the same as the CancelMessage 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class FillMessage implements Comparable<FillMessageMessage>{
	/*
	 * The String username of the user whose order or quote-side was filled. Cannot be null
or empty.
	 */
	private String user;
	/*
	 * The string stock symbol that the filled order or quote-side was submitted for
(“IBM”, “GE”, etc.). Cannot be null or empty.
	 */
	private String product;
	/*
	 * The price that the order or quote-side was filled at. Cannot be null.
	 */
	private Price price;
	/*
	 * The quantity of the order or quote-side that was filled. Cannot be negative.
	 */
	private int volume;
	/*
	 * A text description of the fill (trade). Cannot be null.
	 */
	private String details;
	/*
	 * The side (BUY/SELL) of the filled order or quote-side. Must be a valid side.
	 */
	private BookSide side;
	/*
	 * The String identifier of the filled order or quote-side. Cannot be null.
	 */
	public String id;
	
public String toString() {
	System.out.print("User: " + user + "," + "Product: " + product + "," + "Price: " +
			  price +"," + "Volume: " + volume + "," + "Details: " + details + "," + "Side: " +
						side);	
	}

}
