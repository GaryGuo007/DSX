package depaul.stockexchange.publishers;

import depaul.stockexchange.DataValidationException;
import java.util.HashSet;
import depaul.stockexchange.client.User;
import depaul.stockexchange.price.PriceFactory;

/**
 * A publisher used to publish current market
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class CurrentMarketPublisher extends PublisherBase {
    private volatile static CurrentMarketPublisher instance;
    
    private CurrentMarketPublisher() {}
    
    /**
     * Get the singleton instance
     * @return 
     *      The instance of CurrentMarketPublisher class
     */
    public static CurrentMarketPublisher getInstance() {
        if (instance == null) {
            synchronized(CurrentMarketPublisher.class) {
                if (instance == null) {
                    instance = new CurrentMarketPublisher();
                }
            }
        }
        return instance;
    }
    
    /**
     * The components of the trading system will call to send the market updates out 
     * @param md
     *      A “MarketDataDTO” reference as a parameter to 
     *      the method that contains the market update data. 
     * @throws DataValidationException 
     *      If the data of MarketDataDTO is invalid
     */
    public synchronized void publishCurrentMarket(MarketDataDTO md) 
            throws DataValidationException {
        if (md == null) {
            throw new DataValidationException("The market data passed in "
                    + "can't be null.");
        }
        
        // Check if the product is null or empty
        if (md.product == null || md.product.isEmpty()) {
            throw new DataValidationException("The String stock symbol "
                    + "taken from the MarketDataDTO passed in "
                    + "can't be null or empty.");
        }
        
        // Check if the BUY side volume is negative
        if (md.buyVolume < 0) {
            throw new DataValidationException("The BUY side volume taken "
                    + "from the MarketDataDTO passed in can't be negative.");
        }
        
        // Check if the SELL side volume is negative
        if (md.sellVolume < 0) {
            throw new DataValidationException("The SELL side volume taken "
                    + "from the MarketDataDTO passed in can't be negative.");
        }
        
        // If the BUY side price is null, 
        // a Price object representing $0.00 should be used
        if (md.buyPrice == null) {
            md.buyPrice = PriceFactory.makeLimitPrice(0);
        }
        
        // If the SELL side price is null, 
        // a Price object representing $0.00 should be used
        if (md.sellPrice == null) {
            md.sellPrice = PriceFactory.makeLimitPrice(0);
        }
        
        // get all subscribers
        HashSet<User> subscribers = super.getSubscribers(md.product);

        // if there is no subscribers, no needs to notifiy anybody
        if (subscribers == null || subscribers.isEmpty()) {
            return;
        }

        // For each subscriber
        for (User u : subscribers) {
            // call the user object's "acceptCurrentMarket"
            u.acceptCurrentMarket(md.product, md.buyPrice, md.buyVolume, 
                    md.sellPrice, md.sellVolume);
        }
    }
}
