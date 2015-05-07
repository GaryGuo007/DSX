package depaulStockExchange;

public class NotSubscribedException extends Exception {
	public NotSubscribedException() {
		super();
	}
	
	public NotSubscribedException(String message) {
		super(message);
	}
}
