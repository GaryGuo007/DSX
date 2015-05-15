package depaul.stockexchange.publishers;

import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.client.User;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.price.PriceFactory;
import java.util.HashSet;

/**
 * A publisher used to publish last sale
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class LastSalePublisher extends PublisherBase {
	
    private volatile static LastSalePublisher instance;
    
    private LastSalePublisher() {}
    
    /**
     * Get the singleton instance
     * @return 
     *      The instance of LastSalePublisher class
     */
    public static LastSalePublisher getInstance() {
        if (instance == null) {
            synchronized(LastSalePublisher.class) {
                if (instance == null) {
                    instance = new LastSalePublisher();
                }
            }
        }
        return instance;
    }
    
    /**
     * 
     * @param product
     *      A String stock symbol
     * @param p
     *      A Price holding the last sale price
     * @param v
     *      An “int” holding the last sale volume a parameter to the method
     * @throws DataValidationException 
     *      If the product is null or empty
     *      If the volume is negative
     */
    public synchronized void publishLastSale(String product, Price p, int v) 
            throws DataValidationException {
        
        // Check if the product is null or empty
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The String stock symbol "
                    + "passed in can't be null or empty.");
        }
        
        // Check if the BUY side volume is negative
        if (v < 0) {
            throw new DataValidationException("The last sale volume "
                    + "passed in can't be negative.");
        }
        
        // If the last sale price is null, 
        // a Price object representing $0.00 should be used
        if (p == null) {
            p = PriceFactory.makeLimitPrice(0);
        }
        
        // get all subscribers
        HashSet<User> subscribers = super.getSubscribers(product);

        // if there is no subscribers, no needs to notifiy anybody
        if (subscribers == null || subscribers.isEmpty()) {
            return;
        }
        
        // For each subscriber
        for (User u : subscribers) {
            // call the user object's "acceptLastSale"
            u.acceptLastSale(product, p, v);
        }
    }

}
