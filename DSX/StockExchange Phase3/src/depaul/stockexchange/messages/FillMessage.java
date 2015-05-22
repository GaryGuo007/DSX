package depaul.stockexchange.messages;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.price.Price;

/**
 * The FillMessage class encapsulates data related to the fill (trade) 
 * of an order or quote-side. 
 * FillMessage objects should be immutable. 
 * FillMessage data elements are the same as the CancelMessage 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class FillMessage extends MessageBase 
                            implements Comparable<FillMessage> {

    public FillMessage(String user, String product, Price price, int volume, 
            String details, BookSide side, String id) 
                throws DataValidationException {
        super(user, product, price, volume, details, side, id);
    }

    /**
     * Standard compareTo logic.
     * Assume the value of market price is 0.
     * 
     * @param message
     *      The price passed in to compare with current price
     */
    @Override
    public int compareTo(FillMessage message) {
        if (message == null) {
            return -1;
        }
        return this.price.compareTo(message.price);
    }
    
    /**
     * Displays the information content of the object.
     * @return 
     */
    @Override
    public String toString() {
        return String.format("User: %s, Product: %s, Price: %s, "
                + "Volume: %d, Details: %s, Side: %s", 
                this.getUser(), this.getProduct(), this.getPrice(),
                this.getVolume(), this.getDetails(), this.getSide());
    }

	public void setFillVolume(int i) {
		// TODO Auto-generated method stub
		
	}

}
