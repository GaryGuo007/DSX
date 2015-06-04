package depaul.stockexchange.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.book.InvalidMarketStateException;
import depaul.stockexchange.book.NoSuchProductException;
import depaul.stockexchange.book.OrderNotFoundException;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.InvalidPriceOperation;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.publishers.AlreadySubscribedException;
import depaul.stockexchange.publishers.NotSubscribedException;
import depaul.stockexchange.tradable.Tradable;
import depaul.stockexchange.tradable.TradableDTO;
import depaul.stockexchange.gui.*;
import depaul.stockexchange.price.PriceFactory;

/**
 * The UserImpl class (this class can go in the same package as �User�
 * interface) is our application�s implementation of the �User� interface. This
 * represents a real user in the trading system; many of these objects can be
 * active in our system.
 *
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author Junmin Liu
 */
public class UserImpl implements User {
    /*
     * 1. A String to hold their user name 2. A long value to hold their
     * connection id provided to them when they connect to the system. 3. A
     * String list of the stocks available in the trading system. The user fills
     * this list once connected based upon data received from the trading
     * system. 4. A list of TradableUserData objects that contains information
     * on the orders this user has submitted (needed for cancelling). 5. A
     * reference to a Position object (part of this assignment) which holds the
     * values of the users stocks, costs, etc. 6. A reference to a
     * UserDisplayManager object (part of this assignment) that acts as a faade
     * between the user and the market display.
     */

    private String userName;
    private long connId;
    private ArrayList<String> stocks;
    private ArrayList<TradableUserData> tradUserData;
    private Position position;
    private UserDisplayManager userDisplay;

    public UserImpl(String userName) {
        setUserName(userName);
        setStocks(new ArrayList<>());
        setTradUserData(new ArrayList<TradableUserData>());
        setPosition(new Position());
        //setUserDisplay(UserDisplayManager.getInstance());
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    private long getConnId() {
        return connId;
    }

    private ArrayList<String> getStocks() {
        return stocks;
    }

    private ArrayList<TradableUserData> getTradUserData() {
        return tradUserData;
    }

    private Position getPosition() {
        return position;
    }

    private UserDisplayManager getUserDisplay() {
        return userDisplay;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private void setConnId(long connId) {
        this.connId = connId;
    }

    private void setStocks(ArrayList<String> stocks) {
        this.stocks = stocks;
    }

    private void setTradUserData(ArrayList<TradableUserData> tradUserData) {
        this.tradUserData = tradUserData;
    }

    private void setPosition(Position position) {
        this.position = position;
    }

    private void setUserDisplay() {
        this.userDisplay = new UserDisplayManager(this);
    }

    /*
     * This method should call the user display managers updateLastSale method,
     * passing the same 3 parameters that were passed in.
     */
    @Override
    public void acceptLastSale(String product, Price price, int volume) {
        try {
            userDisplay.updateLastSale(product, price, volume);
            position.updateLastSale(product, price);
        } catch (Exception e) {
            System.out.println("Error occured while accept last sale from UserImpl." + e.getMessage());
        }
    }

    /*
     * This method will display the Fill Message in the market display and will
     * forward the data to the Position object:
     */
    @Override
    public void acceptMessage(FillMessage fm) {
        try {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            String summary = String.format("{%s} Fill Message: %s %s %s at %s %s [Tradable Id: %s]",
                    timeStamp, fm.getSide(), fm.getVolume(), fm.getProduct(),
                    fm.getPrice(), fm.getDetails(), fm.getId());
            userDisplay.updateMarketActivity(summary);
            position.updatePosition(fm.getProduct(), fm.getPrice(), fm.getSide(), fm.getVolume());
        } catch (Exception e) {
            System.out.println("Error occured while accept fill message from UserImpl." + e.getMessage());
        }
    }

    /*
     * This method will display the Cancel Message in the market display:
     */
    @Override
    public void acceptMessage(CancelMessage cm) {
        try {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            String summary = String.format("{%s} Cancel Message: %s %s %s at %s %s [Tradable Id: %s]",
                    timeStamp, cm.getSide(), cm.getVolume(), cm.getProduct(),
                    cm.getPrice(), cm.getDetails(), cm.getId());
            userDisplay.updateMarketActivity(summary);
        } catch (Exception e) {
            System.out.println("Error occured while accept cancel message from UserImpl." + e.getMessage());
        }
    }

    /*
     * This method will display the Market Message in the market display:
     */
    @Override
    public void acceptMarketMessage(String message) {
        try {
            userDisplay.updateMarketState(message);
        } catch (Exception e) {
            System.out.println("Error occured while accept market message from UserImpl." + e.getMessage());
        }
    }

    /*
     * This method will display the Ticker data in the market display:
     */
    @Override
    public void acceptTicker(String product, Price price, char direction) {
        try {
            userDisplay.updateTicker(product, price, direction);
        } catch (Exception e) {
            System.out.println("Error occured while accept ticker from UserImpl." + e.getMessage());
        }
    }

    /*
     * This method will display the Current Market data in the market display:
     */
    @Override
    public void acceptCurrentMarket(String product, Price bPrice, int bVolume,
            Price sPrice, int sVolume) {
        try {
            userDisplay.updateMarketData(product, bPrice, bVolume, sPrice, sVolume);
        } catch (Exception e) {
            System.out.println("Error occured while accept current market from UserImpl." + e.getMessage());
        }
    }

    /*
     * This method will connect the user to the trading system
     */
    @Override
    public void connect() throws Exception {
        setConnId(UserCommandService.getInstance().connect(this));
        setStocks(UserCommandService.getInstance().getProducts(getUserName(), getConnId()));
    }

    /*
     * This method will disconnect the user to the trading system.
     */
    @Override
    public void disConnect() throws Exception {
        UserCommandService.getInstance().disConnect(getUserName(), getConnId());
    }

    /*
     * This method qwill activate the market display.
     */
    @Override
    public void showMarketDisplay() throws Exception {
        if (getStocks() == null) {
            throw new UserNotConnectedException("The user has not connected.");
        }
        if (getUserDisplay() == null) {
            setUserDisplay();
        }
        getUserDisplay().showMarketDisplay();
    }

    /*
     * This method forwards the new order request to the user command service
     * and saves the resulting order id.
     */
    /**
     * Allows the User object to submit a new Order request
     *
     */
    @Override
    public String submitOrder(String product, Price price, int volume, BookSide side)
            throws Exception {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("Product should not be null.");
        }
        if (price == null) {
            throw new DataValidationException("Price should not be null");
        }
        if (volume <= 0) {
            throw new DataValidationException("Volume should not less than 0.");
        }
        if (side == null) {
            throw new DataValidationException("Side should not be null");
        }
        String id = UserCommandService.getInstance().submitOrder(getUserName(), getConnId(), product, price, volume, side);
        TradableUserData t = new TradableUserData(getUserName(), product, side, id);
        tradUserData.add(t);
        return id;
    }

    /*
     * This method forwards the order cancel request to the user command service
     * as follows
     */
    @Override
    public void submitOrderCancel(String product, BookSide side, String orderId)
            throws Exception {
        UserCommandService.getInstance().submitOrderCancel(getUserName(),
                getConnId(), product, side, orderId);

    }

    /*
     * This method forwards the new quote request to the user command service as
     * follows:
     */
    @Override
    public void submitQuote(String product, Price bPrice, int bVolume,
            Price sPrice, int sVolume) throws Exception {
        UserCommandService.getInstance().submitQuote(getUserName(), getConnId(),
                product, bPrice, bVolume, sPrice, sVolume);
    }

    /*
     * This method forwards the quote cancel request to the user command service
     * as follows:
     */
    @Override
    public void submitQuoteCancel(String product)
            throws Exception {
        UserCommandService.getInstance().submitQuoteCancel(getUserName(),
                getConnId(), product);
    }

    /*
     * This method forwards the current market subscription to the user command
     * service as follows:
     */
    @Override
    public void subscribeCurrentMarket(String product)
            throws Exception {
        UserCommandService.getInstance().subscribeCurrentMarket(getUserName(),
                getConnId(), product);

    }

    /*
     * This method forwards the last sale subscription to the user command
     * service as follows:
     */
    @Override
    public void subscribeLastSale(String product)
            throws Exception {
        UserCommandService.getInstance().subscribeLastSale(getUserName(),
                getConnId(), product);

    }

    /*
     * This method forwards the message subscription to the user command service
     * as follows:
     */
    @Override
    public void subscribeMessages(String product)
            throws Exception {
        UserCommandService.getInstance().subscribeMessages(getUserName(),
                getConnId(), product);

    }

    /*
     * This method forwards the ticker subscription to the user command service
     * as follows:
     */
    @Override
    public void subscribeTicker(String product)
            throws Exception {
        UserCommandService.getInstance().subscribeTicker(getUserName(),
                getConnId(), product);

    }

    /*
     * Returns the value of the all Sock the User owns (has bought but not
     * sold).
     */
    @Override
    public Price getAllStockValue() {
        Price price = PriceFactory.makeLimitPrice(0);
        try {
            price = getPosition().getAllStockValue();
        } catch(Exception ex) {
            System.out.printf("Failed to getStockPositionValue from UserImpl." + ex.getMessage());
        }
        return price;

    }

    /*
     * Returns the difference between cost of all stock purchases and stock
     * sales.
     */
    @Override
    public Price getAccountCosts() {
        return getPosition().getAccountCosts();

    }

    /*
     * Returns the difference between current value of all stocks owned and the
     * account costs.
     */
    @Override
    public Price getNetAccountValue() {
        return getPosition().getNetAccountValue();

    }

    /*
     * Allows the User object to submit a Book Depth request for the specified
     * stock. To do this, return the results of a call to the user command
     * services getBookDepth method passing this users user name, connection
     * id, and the String product passed into this method as the parameters to
     * that method.
     */
    @Override
    public String[][] getBookDepth(String product)
            throws Exception {
        return UserCommandService.getInstance().getBookDepth(getUserName(),
                getConnId(), product);
    }

    /*
     * Allows the User object to query the market state (OPEN, PREOPEN, CLOSED).
     * To do this, return the results of a call to the user command services
     * getMarketState method passing this user�s user name, connection id as
     * the parameters to that method.
     */
    @Override
    public String getMarketState() throws Exception {
        return UserCommandService.getInstance().getMarketState(getUserName(),
                getConnId());
    }

    /*
     * Returns a list of order ids (a data member) for the orders this user has
     * submitted.
     */
    @Override
    public ArrayList<TradableUserData> getOrderIds() {
        return getTradUserData();
    }

    /*
     * Returns a list of stocks (a data member) available in the trading system.
     */
    @Override
    public ArrayList<String> getProductList() {
        return getStocks();
    }

    /*
     * Returns the value of the specified stock that this user owns.
     */
    @Override
    public Price getStockPositionValue(String product) {
        Price price = PriceFactory.makeLimitPrice(0);
        try {
            price = getPosition().getStockPositionValue(product);
        } catch(Exception ex) {
            System.out.printf("Failed to getStockPositionValue from UserImpl." + ex.getMessage());
        }
        return price;
    }

    /*
     * Returns the value of the specified stock that this user owns.
     */
    @Override
    public int getStockPositionVolume(String product) {
        int volume = 0;
        try {
            volume = getPosition().getStockPositionVolume(product);
        } catch(Exception ex) {
            System.out.printf("Failed to getStockPositionVolume from UserImpl." + ex.getMessage());
        }
        return volume;
    }

    /*
     * Returns a list of all the Stocks the user owns.
     */
    @Override
    public ArrayList<String> getHoldings() {
        return getPosition().getHoldings();
    }

    /*
     * Gets a list of DTOs containing information on all Orders for this user
     * for the specified product with remaining volume.
     */
    @Override
    public ArrayList<TradableDTO> getOrdersWithRemainingQty(String product)
            throws Exception {
        return UserCommandService.getInstance().getOrdersWithRemainingQty(
                getUserName(), getConnId(), product);
    }
}
