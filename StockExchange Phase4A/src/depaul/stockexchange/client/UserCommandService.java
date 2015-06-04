package depaul.stockexchange.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import depaul.stockexchange.*;
import depaul.stockexchange.book.*;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.publishers.*;
import depaul.stockexchange.tradable.*;

/**
 * The UserCommand class (this class can go in the same package as �User�
 * interface) acts as a fa�ade between a user and the trading system. This class
 * should be a Singleton, as there is only one User Command Service that all
 * users will work with.
 *
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author Junmin Liu
 */
public class UserCommandService {
    /*
     * A HashMap<String, Long> to hold user name and connection id pairs.
     * Initially this connected user ids HashMap should be empty.
     */

    private final HashMap<String, Long> connectedUserIds = new HashMap<>();

    /*
     * A HashMap<String, User> to hold user name and user object pairs.
     * Initially this connected users HashMap should be empty.
     */
    private final HashMap<String, User> connectedUsers = new HashMap<>();

    /*
     * A HashMap<String, Long> to hold user name and connection-time pairs
     * (connection time is stored as a long). Initially this connected time 
     * HashMap should be empty.
     */
    private final HashMap<String, Long> connectedTime = new HashMap<>();

    private volatile static UserCommandService instance;
    
    private UserCommandService() {
        
    }

    public static UserCommandService getInstance() {
        if (instance == null) {
            synchronized (UserCommandService.class) {
                if (instance == null) {
                    instance = new UserCommandService();
                }
            }
        }
        return instance;
    }

    /*
     * This is a utility method that will be used by many of the methods in this
     * class to verify the integrity of the user name and connection id passed
     * in with many of the method calls found here.
     */
    private void verifyUser(String userName, long connId)
            throws InvalidConnectionIdException, 
                    UserNotConnectedException, DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (!connectedUserIds.containsKey(userName)) {
            throw new UserNotConnectedException(
                    "This user is not actually connected.");
        }
        if (connId != connectedUserIds.get(userName)) {
            throw new InvalidConnectionIdException(
                    "This user is not actually connected.");
        }
    }

    /*
     * This method will connect the user to the trading system.
     */
    public synchronized long connect(User user)
            throws AlreadyConnectedException, DataValidationException {
        if (user == null) {
            throw new DataValidationException("User can't be null.");
        }
        String userName = user.getUserName();
        if (connectedUserIds.containsKey(userName)) {
            throw new AlreadyConnectedException(
                    "This user has alreday connected.");
        }
        connectedUserIds.put(userName, System.nanoTime());
        connectedUsers.put(userName, user);
        connectedTime.put(userName, System.currentTimeMillis());
        return connectedUserIds.get(userName);
    }

    /*
     * This method will disconnect the user from the trading system.
     */
    public synchronized void disConnect(String userName, long connId)
            throws InvalidConnectionIdException, 
                    UserNotConnectedException, DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        verifyUser(userName, connId);
        connectedUserIds.remove(userName);
        connectedUsers.remove(userName);
        connectedTime.remove(userName);

    }

    /*
     * Forwards the call of getBookDepth to the ProductService.
     */
    public String[][] getBookDepth(String userName, long connId, String product)
            throws DataValidationException, NoSuchProductException,
            InvalidConnectionIdException, UserNotConnectedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, connId);
        return ProductService.getInstance().getBookDepth(product);
    }

    /*
     * Forwards the call of getMarketState to the ProductService.
     */
    public String getMarketState(String userName, long connId)
            throws InvalidConnectionIdException, UserNotConnectedException, DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        verifyUser(userName, connId);
        return ProductService.getInstance().getMarketState().toString();
    }

    /*
     * Forwards the call of getOrdersWithRemainingQty to the ProductService.
     */
    public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(
            String userName, long connId, String product)
            throws InvalidConnectionIdException, UserNotConnectedException,
            DataValidationException, NoSuchProductException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, connId);
        return ProductService.getInstance().getOrdersWithRemainingQty(userName,
                product);
    }

    /*
     * This method should return a sorted list of the available stocks on this
     * system, received from the ProductService.
     */
    public ArrayList<String> getProducts(String userName, long connId)
            throws InvalidConnectionIdException, 
                UserNotConnectedException, DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        verifyUser(userName, connId);
        ArrayList<String> h = ProductService.getInstance().getProductList();
        Collections.sort(h);
        return h;
    }

    /*
     * This method will create an order object using the data passed in, and
     * will forward the order to the ProductServices submitOrder method.
     */
    public String submitOrder(String userName, long connId, String product,
            Price price, int volume, BookSide side)
            throws InvalidMarketStateException, DataValidationException,
            NoSuchProductException, NotSubscribedException,
            InvalidConnectionIdException, UserNotConnectedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        if (price == null) {
            throw new DataValidationException("price can't be null.");
        }
        if (side == null) {
            throw new DataValidationException("side can't be null.");
        }
        if (volume < 0) {
            throw new DataValidationException("volume can't be negative.");
        }
        verifyUser(userName, connId);
        Order subOrder = new Order(userName, product, price, volume, side);
        String orderId = ProductService.getInstance().submitOrder(subOrder);
        return orderId;
    }

    /*
     * This method will forward the provided information to the ProductServices
     * submitOrderCancel method.
     */
    public void submitOrderCancel(String userName, long connId, String product,
            BookSide side, String orderId) throws InvalidConnectionIdException,
            UserNotConnectedException, InvalidMarketStateException,
            DataValidationException, NoSuchProductException,
            OrderNotFoundException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(orderId)) {
            throw new DataValidationException("orderId can't be null or empty.");
        }
        if (side == null) {
            throw new DataValidationException("side can't be null.");
        }
        verifyUser(userName, connId);
        ProductService.getInstance().submitOrderCancel(product, side, orderId);
    }

    /*
     * This method will create a quote object using the data passed in, and will
     * forward the quote to the ProductServices submitQuote method.
     */
    public void submitQuote(String userName, long connId, String product,
            Price bPrice, int bVolume, Price sPrice, int sVolume)
            throws DataValidationException, InvalidMarketStateException,
            NoSuchProductException, NotSubscribedException,
            InvalidConnectionIdException, UserNotConnectedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        if (bPrice == null) {
            throw new DataValidationException("Buy price can't be null.");
        }
        if (sPrice == null) {
            throw new DataValidationException("Sell price can't be null.");
        }
        if (bVolume < 0) {
            throw new DataValidationException("Buy volume can't be negative.");
        }
        if (sVolume < 0) {
            throw new DataValidationException("Sell volume can't be negative.");
        }
        verifyUser(userName, connId);
        Quote subQuote = new Quote(userName, product, bPrice, bVolume,
                sPrice, sVolume);
        ProductService.getInstance().submitQuote(subQuote);
    }

    /*
     * This method will forward the provided data to the ProductServices
     * submitQuoteCancel method.
     */
    public void submitQuoteCancel(String userName, long connId, String product)
            throws InvalidConnectionIdException, UserNotConnectedException,
            InvalidMarketStateException, DataValidationException,
            NoSuchProductException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, connId);
        ProductService.getInstance().submitQuoteCancel(userName, product);
    }

    /*
     * This method will forward the subscription request to the
     * CurrentMarketPublisher.
     */
    public void subscribeCurrentMarket(String userName, long connId,
            String product) throws AlreadySubscribedException,
            DataValidationException, InvalidConnectionIdException,
            UserNotConnectedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, connId);
        CurrentMarketPublisher.getInstance().subscribe(
                connectedUsers.get(userName), product);
    }

    /*
     * This method will forward the subscription request to the
     * LastSalePublisher.
     */
    public void subscribeLastSale(String userName, long connId, String product)
            throws AlreadySubscribedException, DataValidationException,
            InvalidConnectionIdException, UserNotConnectedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, connId);
        LastSalePublisher.getInstance().subscribe(connectedUsers.get(userName),
                product);
    }

    /*
     * This method will forward the subscription request to the
     * MessagePublisher.
     */
    public void subscribeMessages(String userName, long conn, String product)
            throws AlreadySubscribedException, DataValidationException,
            InvalidConnectionIdException, UserNotConnectedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, conn);
        MessagePublisher.getInstance().subscribe(connectedUsers.get(userName),
                product);
    }

    /*
     * This method will forward the subscription request to the TickerPublisher.
     */
    public void subscribeTicker(String userName, long conn, String product)
            throws AlreadySubscribedException, DataValidationException,
            InvalidConnectionIdException, UserNotConnectedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, conn);
        TickerPublisher.getInstance().subscribe(connectedUsers.get(userName),
                product);
    }

    /*
     * This method will forward the unsubscribe request to the
     * CurrentMarketPublisher.
     */
    public void unSubscribeCurrentMarket(String userName, long conn,
            String product) throws AlreadySubscribedException,
            DataValidationException, InvalidConnectionIdException,
            UserNotConnectedException, NotSubscribedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, conn);
        CurrentMarketPublisher.getInstance().unSubscribe(
                connectedUsers.get(userName), product);
    }

    /*
     * This method will forward the unsubscribe request to the
     * LastSalePublisher.
     */
    public void unSubscribeLastSale(String userName, long conn, String product)
            throws AlreadySubscribedException, DataValidationException,
            InvalidConnectionIdException, UserNotConnectedException, NotSubscribedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, conn);
        LastSalePublisher.getInstance().unSubscribe(connectedUsers.get(userName),
                product);
    }

    /*
     * method will forward the un-subscribe request to the TickerPublisher.
     */
    public void unSubscribeTicker(String userName, long conn, String product)
            throws AlreadySubscribedException, DataValidationException,
            InvalidConnectionIdException, UserNotConnectedException, NotSubscribedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, conn);
        TickerPublisher.getInstance().unSubscribe(connectedUsers.get(userName),
                product);
    }

    /*
     * This method will forward the unsubscribe request to the MessagePublisher
     */
    public void unSubscribeMessages(String userName, long conn, String product)
            throws AlreadySubscribedException, DataValidationException,
            InvalidConnectionIdException, UserNotConnectedException, NotSubscribedException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("product can't be null or empty.");
        }
        verifyUser(userName, conn);
        MessagePublisher.getInstance().unSubscribe(connectedUsers.get(userName),
                product);
    }

}
