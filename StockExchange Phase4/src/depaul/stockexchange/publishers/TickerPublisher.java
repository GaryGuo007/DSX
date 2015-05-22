package depaul.stockexchange.publishers;

import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.client.User;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.price.PriceFactory;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A publisher class to publish ticker to subscribers
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class TickerPublisher extends PublisherBase {

    private volatile static TickerPublisher instance;
    
    /**
     * The most recent ticker value for each stock symbol 
     * so it can determine if the stock price have 
     * moved up, down, or stayed the same. 
     */
    private final HashMap<String, Price> mostRecentTickers = new HashMap();
    
    private TickerPublisher() {}
    
    /**
     * Get the singleton instance
     * @return 
     *      The instance of TickerPublisher class
     */
    public static TickerPublisher getInstance() {
        if (instance == null) {
            synchronized(TickerPublisher.class) {
                if (instance == null) {
                    instance = new TickerPublisher();
                }
            }
        }
        return instance;
    }
    
    /**
     * Publish a ticker
     * @param product
     *      A String stock symbol
     * @param p
     *      The last sale price
     * @throws DataValidationException 
     *      If the product is empty or null
     */
    public synchronized void publishTicker(String product, Price p) 
            throws DataValidationException {
        
        // Check if the product is null or empty
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The String stock symbol "
                    + "passed in can't be null or empty.");
        }
        
        // If the last sale price is null, 
        // a Price object representing $0.00 should be used
        if (p == null) {
            p = PriceFactory.makeLimitPrice(0);
        }
        
        // Get previous ticker value for this product
        Price previousPrice = mostRecentTickers.get(product);
        // If can't get the previous ticker value, assume it's $0.00
        char direction;
        if (previousPrice == null) {
            previousPrice = PriceFactory.makeLimitPrice(0);
            direction = ' ';
        } else {
            if (p.greaterThan(previousPrice)) {
                direction = (char) 8593; //'↑' 
            } else if (p.lessThan(previousPrice)) {
                direction = (char) 8595; //' ↓'
            } else {
                direction = '=';
            }
            
        }

        // set the current price as last sale price
        mostRecentTickers.put(product, p);
        
        // get all subscribers
        HashSet<User> subscribers = super.getSubscribers(product);

        // if there is no subscribers, no needs to notifiy anybody
        if (subscribers == null || subscribers.isEmpty()) {
            return;
        }
        
        // For each subscriber
        for (User u : subscribers) {
            // call the user object's "acceptLastSale"
            u.acceptTicker(product, p, direction);
        }
    }
}
