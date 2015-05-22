package depaul.stockexchange.book;

@SuppressWarnings("serial")
public class InvalidMarketStateTransition extends Exception{
	
	public InvalidMarketStateTransition() {
        super();
    }
	 public InvalidMarketStateTransition(String message) {
	        super(message);
	    }

}
