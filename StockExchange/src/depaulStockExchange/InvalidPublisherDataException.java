package depaulStockExchange;

public class InvalidPublisherDataException extends Exception {
	static final long serialVersionUID = 1;
	public InvalidPublisherDataException() {
		super();
	}
	
	public InvalidPublisherDataException(String message) {
		super(message);
	}
}
