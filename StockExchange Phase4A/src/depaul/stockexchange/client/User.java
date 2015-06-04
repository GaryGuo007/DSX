package depaul.stockexchange.client;

import java.util.ArrayList;

import depaul.stockexchange.*;
import depaul.stockexchange.book.InvalidMarketStateException;
import depaul.stockexchange.book.NoSuchProductException;
import depaul.stockexchange.book.OrderNotFoundException;
import depaul.stockexchange.messages.CancelMessage;
import depaul.stockexchange.messages.FillMessage;
import depaul.stockexchange.price.InvalidPriceOperation;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.publishers.AlreadySubscribedException;
import depaul.stockexchange.publishers.NotSubscribedException;
import depaul.stockexchange.tradable.TradableDTO;

/**
 * The User interface contains the method declarations that any class that
 * wishes to be a â€œUserâ€� of the DSX trading system must implement.
 *
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author junmin Liu
 *
 */
public interface User {

    /**
     * The username of this user
     *
     * @return This will return the String username of this user
     */
    String getUserName();

    /**
     * This info is used by Users to track stock sales and volumes and is
     * sometimes displayed in a GUI.
     *
     * @param product A String stock symbol (, etc)
     * @param p A Price object holding the value of the last sale (trade) of
     * that stock
     * @param v The quantity (volume) of that last sale
     */
    void acceptLastSale(String product, Price p, int v);

    /**
     * This is like a receipt sent to the user to document the details when an
     * order or quote-side of theirs trades.
     *
     * @param fm A FillMessage object which contains information related to an
     * order or quote trade.
     */
    void acceptMessage(FillMessage fm);

    /**
     * This is like a receipt sent to the user to document the details when an
     * order or quote-side of theirs is canceled.
     *
     * @param cm A CancelMessage object which contains information related to an
     * order or quote cancel.
     */
    void acceptMessage(CancelMessage cm);

    /**
     * This will accept a String which contains market information related to a
     * Stock Symbol they are interested in.
     *
     * @param message A String which contains market information related to a
     * Stock Symbol they are interested in.
     */
    void acceptMarketMessage(String message);

    /**
     * This info is used by to track stock price movement, and is sometimes
     * displayed in a GUI.
     *
     * @param product A stock symbol (, etc)
     * @param p A Price object holding the value of the last sale (trade) of
     * that stock
     * @param direction A âce represents an increase or decrease in the Stock's
     * price.
     */
    void acceptTicker(String product, Price p, char direction);

    /**
     * This info is used by to update their market display screen so that they
     * are always looking at the most current market data. These values as a
     * group tell the user the For example: AMZN: BUY 220@12.80 and SELL
     * 100@12.85.
     *
     * @param product A String stock symbol (, etc.)
     * @param bp A Price object holding the current BUY side price for that
     * stock
     * @param bv An int holding the current BUY side volume (quantity)
     * @param sp A Price object holding the current SELL side price for that
     * stock
     * @param sv An int holding the current SELL side volume (quantity)
     */
    void acceptCurrentMarket(String product, Price bp, int bv, Price sp, int sv);

    //some need to add thrown exception!
    /**
     * Instructs a User object to connect to the trading system.
     *
     * @throws AlreadyConnectedException
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     */
    void connect() throws AlreadyConnectedException, InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Instructs a User object to disconnect from the trading system.
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     */
    void disConnect() throws InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Requests the opening of the market display if the user is connected.
     *
     * @throws UserNotConnectedException
     * @throws Exception
     */
    void showMarketDisplay() throws UserNotConnectedException, Exception;

    /**
     * Allows the User object to submit a new Order request
     */
    String submitOrder(String product, Price price, int volume, BookSide side) throws DataValidationException;

    /**
     * Allows the User object to submit a new Order Cancel request
     *
     * @throws OrderNotFoundException
     * @throws NoSuchProductException
     * @throws DataValidationException
     * @throws InvalidMarketStateException
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     */
    void submitOrderCancel(String product, BookSide side, String orderId) throws InvalidConnectionIdException, UserNotConnectedException, InvalidMarketStateException, DataValidationException, NoSuchProductException, OrderNotFoundException;

    /**
     * Allows the User object to submit a new Quote request
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     * @throws NotSubscribedException
     * @throws NoSuchProductException
     * @throws InvalidMarketStateException
     * @throws DataValidationException
     */
    void submitQuote(String product, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws DataValidationException, InvalidMarketStateException, NoSuchProductException, NotSubscribedException, InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Allows the User object to submit a new Quote Cancel request
     *
     * @throws NoSuchProductException
     * @throws DataValidationException
     * @throws InvalidMarketStateException
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     */
    void submitQuoteCancel(String product) throws InvalidConnectionIdException, UserNotConnectedException, InvalidMarketStateException, DataValidationException, NoSuchProductException;

    /**
     * Allows the User object to subscribe for Current Market for the specified
     * Stock.
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     * @throws DataValidationException
     * @throws AlreadySubscribedException
     */
    void subscribeCurrentMarket(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Allows the User object to subscribe for Last Sale for the specified
     * Stock.
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     * @throws DataValidationException
     * @throws AlreadySubscribedException
     */
    void subscribeLastSale(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Allows the User object to subscribe for Messages for the specified Stock.
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     * @throws DataValidationException
     * @throws AlreadySubscribedException
     */
    void subscribeMessages(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Allows the User object to subscribe for Ticker for the specified Stock.
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     * @throws DataValidationException
     * @throws AlreadySubscribedException
     *
     */
    void subscribeTicker(String product) throws AlreadySubscribedException, DataValidationException, InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Returns the value of the all Sock the User owns (has bought but not sold)
     *
     * @throws InvalidPriceOperation
     */
    Price getAllStockValue() throws InvalidPriceOperation;

    /**
     * Returns the difference between cost of all stock purchases and stock
     * sales
     */
    Price getAccountCosts();

    /**
     * Returns the difference between current value of all stocks owned and the
     * account costs
     *
     * @throws InvalidPriceOperation
     */
    Price getNetAccountValue() throws InvalidPriceOperation;

    /**
     * Allows the User object to submit a Book Depth request for the specified
     * stock.
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     * @throws NoSuchProductException
     * @throws DataValidationException
     */
    String[][] getBookDepth(String product) throws DataValidationException, NoSuchProductException, InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Allows the User object to query the market state (OPEN, PREOPEN, CLOSED).
     *
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     */
    String getMarketState() throws InvalidConnectionIdException, UserNotConnectedException;

    /**
     * Returns a list of order id’s for the orders this user has submitted.
     */
    ArrayList<TradableUserData> getOrderIds();

    /**
     * Returns a list of the stock products available in the trading system.
     */
    ArrayList<String> getProductList();

    /**
     * Returns the value of the specified stock that this user owns
     *
     * @throws InvalidPriceOperation
     */
    Price getStockPositionValue(String sym) throws InvalidPriceOperation;

    /**
     * Returns the volume of the specified stock that this user owns
     */
    int getStockPositionVolume(String product);

    /**
     * Returns a list of all the Stocks the user owns
     */
    ArrayList<String> getHoldings();

    /**
     * Gets a list of DTO’s containing information on all Orders for this user
     * for the specified product with remaining volume.
     *
     * @throws NoSuchProductException
     * @throws DataValidationException
     * @throws UserNotConnectedException
     * @throws InvalidConnectionIdException
     */
    ArrayList<TradableDTO> getOrdersWithRemainingQty(String product) throws InvalidConnectionIdException, UserNotConnectedException, DataValidationException, NoSuchProductException;

}
