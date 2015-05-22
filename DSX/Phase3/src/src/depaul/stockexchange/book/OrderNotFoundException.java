package depaul.stockexchange.book;


@SuppressWarnings("serial")
public class OrderNotFoundException extends Exception{
	
	public OrderNotFoundException() {
        super();
    }
	 public OrderNotFoundException(String message) {
	        super(message);
	    }

}