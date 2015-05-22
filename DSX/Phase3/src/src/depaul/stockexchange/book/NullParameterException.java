package depaul.stockexchange.book;


@SuppressWarnings("serial")
public class NullParameterException extends Exception{
	
	public NullParameterException() {
        super();
    }
	 public NullParameterException(String message) {
	        super(message);
	    }

}
