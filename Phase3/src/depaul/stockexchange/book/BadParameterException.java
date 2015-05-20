package depaul.stockexchange.book;


@SuppressWarnings("serial")
public class BadParameterException extends Exception{
	
	public BadParameterException() {
        super();
    }
	 public BadParameterException(String message) {
	        super(message);
	    }

}
