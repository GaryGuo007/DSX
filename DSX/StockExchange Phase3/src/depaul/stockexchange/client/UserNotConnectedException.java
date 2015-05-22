package depaul.stockexchange.client;

@SuppressWarnings("serial")
public class UserNotConnectedException extends Exception{
	
	public UserNotConnectedException() {
        super();
    }
	 public UserNotConnectedException(String message) {
	        super(message);
	    }

}
