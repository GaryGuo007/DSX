package DePaul.StockExchange;

public class InvalidPublisherDataException extends Exception {
	public InvalidPublisherDataException() {
		super();
	}
	
	public InvalidPublisherDataException(String message) {
		super(message);
	}
}
