package depaul.stockexchange.publishers;

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
     * @throws InvalidPublisherDataException 
     *      If product is null or empty
     *      If username is null or empty
     */
    private User findSubscriber(String product, String username) 
            throws InvalidPublisherDataException {
        
        // product can't be null or empty
        if (Utils.isNullOrEmpty(product)) {
            throw new InvalidPublisherDataException("Product can't be null or empty.");
        }
        // product can't be null or empty
        if (Utils.isNullOrEmpty(username)) {
            throw new InvalidPublisherDataException("Username can't be null or empty.");
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
     * @throws InvalidPublisherDataException
     *      If the cancel message is null
     */
    public synchronized void publishCancel(CancelMessage cm) 
            throws InvalidPublisherDataException {
        if (cm == null) {
            throw new InvalidPublisherDataException("CancelMessage can't be null.");
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
     * @throws InvalidPublisherDataException
     *      If the fill message is null
     * @throws NotSubscribedException 
     *      If the user has not subscribed the product
     */
    public synchronized void publishFill(FillMessage fm) 
            throws InvalidPublisherDataException, NotSubscribedException {
        if (fm == null) {
            throw new InvalidPublisherDataException("FillMessage can't be null.");
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
     * @throws InvalidPublisherDataException 
     *      If the market message is null
     */
    public synchronized void publishMarketMessage(MarketMessage mm) 
            throws InvalidPublisherDataException {
        if (mm == null) {
            throw new InvalidPublisherDataException("MarketMessage can't be null.");
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
