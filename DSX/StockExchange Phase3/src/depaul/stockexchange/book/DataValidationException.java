package depaul.stockexchange.book;
@SuppressWarnings("serial")
public class DataValidationException extends Exception{
	
	public DataValidationException() {
        super();
    }
	 public DataValidationException(String message) {
	        super(message);
	    }

}
