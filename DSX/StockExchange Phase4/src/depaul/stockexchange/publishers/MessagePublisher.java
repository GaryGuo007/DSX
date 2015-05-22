package depaul.stockexchange.publishers;

import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.client.User;
import depaul.stockexchange.messages.CancelMessage;
import depaul.stockexchange.messages.FillMessage;
import depaul.stockexchange.messages.MarketMessage;
import java.util.HashSet;

/**
 * A class to publish market message to subscribers
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class MessagePublisher extends PublisherBase {

    private volatile static MessagePublisher instance;
    
    private MessagePublisher() {}
    
    /**
     * Get the singleton instance
     * @return 
     *      The instance of MessagePublisher class
     */
    public static MessagePublisher getInstance() {
        if (instance == null) {
            synchronized(MessagePublisher.class) {
                if (instance == null) {
                    instance = new MessagePublisher();
                }
            }
        }
        return instance;
    }
    
    /**
     * Find the User object in the HashSet for the specified stock symbol
     * whose user name matches the user name passed in
     * @param product
     *      A String stock symbol
     * @param username
     *      A String username of user
     * @return
     *      Return the user object that found.
     *      Return null if can't find any
     * @throws DataValidationException 
     *      If product is null or empty
     *      If username is null or empty
     */
    private User findSubscriber(String product, String username) 
            throws DataValidationException {
        
        // product can't be null or empty
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("Product can't be null or empty.");
        }
        // product can't be null or empty
        if (Utils.isNullOrEmpty(username)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        
        HashSet<User> subscribers = subscriptions.get(product);
        
        User user = null;
        // Find the User object in the HashSet for the specified stock symbol  
        // whose user name matches the user name passed in
        if (subscribers != null) {
            for (User u : subscribers) {
                if (username.equals(u.getUserName())) {
                    user = u;
                    break;
                }
            }
        }
        return user;
    }
    
    /**
     * Push a cancel message to the user who subscribe this product
     * @param cm
     *      The cancel message
     * @throws DataValidationException
     *      If the cancel message is null
     */
    public synchronized void publishCancel(CancelMessage cm) 
            throws DataValidationException {
        if (cm == null) {
            throw new DataValidationException("CancelMessage can't be null.");
        }
        
        User user = findSubscriber(cm.getProduct(), cm.getUser());
        if (user != null) {
            user.acceptMessage(cm);
        }
    }

    
    /**
     * Push a fill message to the user who subscribe this product
     * @param fm
     *      The fill message
     * @throws DataValidationException
     *      If the fill message is null
     * @throws NotSubscribedException 
     *      If the user has not subscribed the product
     */
    public synchronized void publishFill(FillMessage fm) 
            throws DataValidationException, NotSubscribedException {
        if (fm == null) {
            throw new DataValidationException("FillMessage can't be null.");
        }
        
        User user = findSubscriber(fm.getProduct(), fm.getUser());
        if (user != null) {
            user.acceptMessage(fm);
        }
    }
    
    /**
     * Push market message to all subscribed users
     * @param mm
     *      The market message
     * @throws DataValidationException 
     *      If the market message is null
     */
    public synchronized void publishMarketMessage(MarketMessage mm) 
            throws DataValidationException {
        if (mm == null) {
            throw new DataValidationException("MarketMessage can't be null.");
        }
        
        // all subscribed Users 
        // regardless of the stock symbol they are interested in 
        for (HashSet<User> subscribers : subscriptions.values()) {
            for (User u : subscribers) {
                u.acceptMarketMessage(mm.toString());
            }
        }
    }
}
