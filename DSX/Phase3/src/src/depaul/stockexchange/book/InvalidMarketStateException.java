package depaul.stockexchange.book;


@SuppressWarnings("serial")
public class InvalidMarketStateException extends Exception{
	
	public InvalidMarketStateException() {
        super();
    }
	 public InvalidMarketStateException(String message) {
	        super(message);
	    }

}