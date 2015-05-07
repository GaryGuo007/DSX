package DePaul.StockExchange;

/**
 * An exception class to tell user the argument of message is invalid
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public class InvalidMessageArgumentException extends Exception {
	public InvalidMessageArgumentException() {
		super();
	}
	
	public InvalidMessageArgumentException(String message) {
		super(message);
	}
}
