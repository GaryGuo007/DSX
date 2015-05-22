package depaul.stockexchange.messages;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.price.Price;

/**
 * The CancelMessage class encapsulates data related to the cancellation 
 * of an order or quote-side by a user, or by the trading system. 
 * CancelMessage objects should be immutable.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class CancelMessage extends MessageBase 
                            implements Comparable<CancelMessage> {

    public CancelMessage(String user, String product, Price price, int volume, 
            String details, BookSide side, String id) 
                throws InvalidMessageDataException {
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
    public int compareTo(CancelMessage message) {
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
                + "Volume: %d, Details: %s, Side: %s, Id: %s", 
                this.getUser(), this.getProduct(), this.getPrice(),
                this.getVolume(), this.getDetails(), this.getSide(),
                this.getId());
    }
}
