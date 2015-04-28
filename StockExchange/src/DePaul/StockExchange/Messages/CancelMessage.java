package DePaul.StockExchange.Messages;

public class CancelMessage implements Comparable<CancelMessage>{
	private String user;
	private String product;
	private Price price;
	private int volume;
	private String details;
	private BookSide side;
	public String id;
	
	
	public String toString() {
		
	}
}
