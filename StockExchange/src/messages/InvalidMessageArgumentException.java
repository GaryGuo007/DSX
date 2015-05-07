package messages;

public class InvalidMessageArgumentException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMessageArgumentException(String message){
		super(message);
	}

}
