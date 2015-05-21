/*
 * Copyright 2015 jimliu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package depaul.stockexchange.book;

import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.Utils;
import depaul.stockexchange.messages.CancelMessage;
import depaul.stockexchange.messages.FillMessage;
import depaul.stockexchange.messages.MarketState;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.price.PriceFactory;
import depaul.stockexchange.publishers.CurrentMarketPublisher;
import depaul.stockexchange.publishers.LastSalePublisher;
import depaul.stockexchange.publishers.MarketDataDTO;
import depaul.stockexchange.publishers.MessagePublisher;
import depaul.stockexchange.publishers.NotSubscribedException;
import depaul.stockexchange.tradable.Order;
import depaul.stockexchange.tradable.Quote;
import depaul.stockexchange.tradable.QuoteSide;
import depaul.stockexchange.tradable.Tradable;
import depaul.stockexchange.tradable.TradableDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A ProductBook object maintains the Buy and Sell sides of a stock’s “book”.
 *
 * the list of not-yet-traded (i.e., “booked”) Orders and Quote Sides
 * (Tradables) for the Buy side and for the Sell side. To represent this, the
 * ProductBook will own two ProductBookSide objects – one for the BUY side and
 * one for the SELL side.
 *
 * @author jimliu
 */
public class ProductBook {

    /**
     * The String stock symbol that this book represents (i.e., MSFT, IBM, AAPL,
     * etc).
     */
    private String product;

    /**
     * Set the product
     *
     * @param product The String stock symbol that this book represents (i.e.,
     * MSFT, IBM, AAPL, etc).
     * @throws DataValidationException If product is empty or null
     */
    private void setProduct(String product) throws DataValidationException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The product can't be null or empty.");
        }
        this.product = product;
    }

    /**
     * A ProductBookSide that maintains the Buy side of this book.
     */
    private ProductBookSide buySide;

    /**
     * A ProductBookSide that maintains the Sell side of this book.
     */
    private ProductBookSide sellSide;

    /**
     * A String that will hold the toString results of the latest Market Data
     * values (the prices and the volumes at the top of the buy and sell sides).
     */
    private String lastCurrentMarket;

    /**
     * A list of the current quotes in this book for each user.
     */
    private HashSet<String> userQuotes = new HashSet<>();

    /**
     * A list of “old” Tradables (those that have been completely traded or
     * cancelled) organized by price and by user.
     */
    private HashMap<Price, ArrayList<Tradable>> oldEntries = new HashMap<>();

    /**
     * Constructor
     *
     * @param product The String stock symbol this book represents
     * @throws DataValidationException If the product is empty or null
     */
    public ProductBook(String product) throws DataValidationException {
        this.setProduct(product);
        this.buySide = new ProductBookSide(this, BookSide.BUY);
        this.sellSide = new ProductBookSide(this, BookSide.SELL);
    }

    /**
     * This method should return an ArrayList containing any orders for the
     * specified user that have remaining quantity.
     *
     * @param userName The specified user
     * @return An ArrayList of orders
     * @throws DataValidationException If username is null or empty
     */
    public synchronized ArrayList<TradableDTO>
            getOrdersWithRemainingQty(String userName) throws DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }

        ArrayList<TradableDTO> tradables = new ArrayList<>();
        tradables.addAll(this.buySide.getOrdersWithRemainingQty(userName));
        tradables.addAll(this.sellSide.getOrdersWithRemainingQty(userName));

        return tradables;
    }

    /**
     * Create and publish a Cancel Message by the order
     *
     * @param trade The order to be canceled
     * @param details The cancel details
     * @throws DataValidationException
     * @throws DataValidationException
     */
    void publishCancel(Tradable trade, String details)
            throws DataValidationException, DataValidationException {
        CancelMessage cm = new CancelMessage(trade.getUser(),
                trade.getProduct(), trade.getPrice(),
                trade.getRemainingVolume(), details,
                trade.getSide(), trade.getId());
        MessagePublisher.getInstance().publishCancel(cm);
    }

    /**
     * This method id designed to determine if it is too late to cancel an order
     * (meaning it has already been traded out or cancelled).
     *
     * @param orderId The id of order
     * @throws DataValidationException If id is null or empty
     * @throws OrderNotFoundException
     */
    public synchronized void checkTooLateToCancel(String orderId)
            throws DataValidationException, OrderNotFoundException {
        if (Utils.isNullOrEmpty(orderId)) {
            throw new DataValidationException("Order Id can't be null or empty.");
        }

        Tradable order = null;

        for (Price price : oldEntries.keySet()) {
            for (Tradable t : oldEntries.get(price)) {
                if (orderId.equals(t.getId())) {
                    order = t;
                    break;
                }
            }
            if (order != null) {
                break;
            }
        }

        // Can't find the order
        if (order == null) {
            throw new OrderNotFoundException("The requested order ("
                    + orderId + ") could not be found.");
        }

        publishCancel(order, "Too Late to Cancel");
    }

    /**
     * This method is should return a 2-dimensional array of Strings that
     * contain the prices and volumes at all prices present in the buy and sell
     * sides of the book.
     *
     * @return A 2-dimensional array of Strings
     */
    public synchronized String[][] getBookDepth() {
        String[][] bd = new String[2][];
        bd[0] = this.buySide.getBookDepth();
        bd[1] = this.sellSide.getBookDepth();

        return bd;
    }

    /**
     * This method should create a MarketDataDTO containing the best buy side
     * price and volume, and the best sell side price an volume.
     *
     * @return A MarketDataDTO containing the best buy/side side price and
     * volume
     */
    public synchronized MarketDataDTO getMarketData() {
        Price buyPrice = this.buySide.topOfBookPrice();
        if (buyPrice == null) {
            buyPrice = PriceFactory.makeLimitPrice(0);
        }
        Price sellPrice = this.sellSide.topOfBookPrice();
        if (sellPrice == null) {
            sellPrice = PriceFactory.makeLimitPrice(0);
        }

        int buyVolume = this.buySide.topOfBookVolume();
        int sellVolume = this.sellSide.topOfBookVolume();

        return new MarketDataDTO(this.product, buyPrice, buyVolume,
                sellPrice, sellVolume);
    }

    /**
     * This method should add the Tradable passed in to the “oldEntries”
     * HashMap.
     *
     * @param t The tradable
     * @throws DataValidationException If the tradable is null
     */
    public synchronized void addOldEntry(Tradable t) throws DataValidationException {
        if (t == null) {
            throw new DataValidationException("The tradable can't be null.");
        }
        if (t.getPrice() == null) {
            throw new DataValidationException("The price of tradable can't be null.");
        }

        Price p = t.getPrice();
        if (!oldEntries.containsKey(p)) {
            oldEntries.put(p, new ArrayList<Tradable>());
        }

        t.setRemainingVolume(0);
        t.setCancelledVolume(t.getRemainingVolume());

        oldEntries.get(p).add(t);
    }

    /**
     * This method will “Open” the book for trading.
     */
    public synchronized void openMarket() throws NotSubscribedException, DataValidationException {
        Price buyPrice = this.buySide.topOfBookPrice();
        Price sellPrice = this.sellSide.topOfBookPrice();

        // If either of these Prices is null then one of these sides has no 
        // Tradables so there will be no opening trade,
        if (buyPrice == null || sellPrice == null) {
            return;
        }

        while (buyPrice.greaterOrEqual(sellPrice) || buyPrice.isMarket()
                || sellPrice.isMarket()) {
            ArrayList<Tradable> topOfBuySide = buySide.getEntriesAtPrice(buyPrice);
            HashMap<String, FillMessage> allFills = null;

            ArrayList<Tradable> toRemove = new ArrayList<>();
            for (Tradable t : topOfBuySide) {
                allFills = this.sellSide.tryTrade(t);
                if (t.getRemainingVolume() == 0) {
                    toRemove.add(t);
                }
            }

            for (Tradable t : toRemove) {
                this.buySide.removeTradable(t);
            }

            updateCurrentMarket();

            if (allFills != null && !allFills.isEmpty()) {
                Price lastSalePrice = determineLastSalePrice(allFills);
                int lastSaleVolume = determineLastSaleQuantity(allFills);
                LastSalePublisher.getInstance().publishLastSale(product, lastSalePrice, lastSaleVolume);
            }
            
            buyPrice = buySide.topOfBookPrice();
            sellPrice = sellSide.topOfBookPrice();

            // If either of these Prices is null then one of these sides 
            // has no Tradables so there will be no more trades
            if (buyPrice == null || sellPrice == null) {
                break;
            }
        }
    }

    /**
     * This method will “Close” the book for trading.
     */
    public synchronized void closeMarket()
            throws DataValidationException, OrderNotFoundException {
        this.buySide.cancelAll();
        this.sellSide.cancelAll();
        updateCurrentMarket();
    }

    /**
     * This method will cancel the Order specified by the provided orderId on
     * the specified side.
     *
     * @param side The provided orderId
     * @param orderId The specified side
     * @throws DataValidationException If orderId is null or empty
     */
    public synchronized void cancelOrder(BookSide side, String orderId)
            throws DataValidationException, OrderNotFoundException {
        if (side == null) {
            throw new DataValidationException("The provided BookSide can't be null.");
        }
        if (Utils.isNullOrEmpty(orderId)) {
            throw new DataValidationException("The provided Order id can't be null or empty.");
        }
        ProductBookSide bookSide = side == BookSide.BUY ? buySide : sellSide;
        bookSide.submitOrderCancel(orderId);
        updateCurrentMarket();
    }

    /**
     * This method will cancel the specified user’s Quote on the both the BUY
     * and SELL sides.
     *
     * @param userName The specified user's username
     * @throws DataValidationException
     */
    public synchronized void cancelQuote(String userName)
            throws DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }
        buySide.submitQuoteCancel(userName);
        sellSide.submitQuoteCancel(userName);
        updateCurrentMarket();
    }

    /**
     * This method should add the provided Quote’s sides to the Buy and Sell
     * ProductSideBooks.
     *
     * @param q
     */
    public synchronized void addToBook(Quote q)
            throws DataValidationException, NotSubscribedException {
        if (q == null) {
            throw new DataValidationException("Quote couldn't be null.");
        }

        QuoteSide buyQuoteSide = q.getQuoteSide(BookSide.BUY);
        QuoteSide sellQuoteSide = q.getQuoteSide(BookSide.SELL);

        Price buyPrice = buyQuoteSide.getPrice();
        Price sellPrice = sellQuoteSide.getPrice();
        Price zeroPrice = PriceFactory.makeLimitPrice(0);
        if (sellPrice.lessOrEqual(buyPrice)) {
            throw new DataValidationException("That is an illegal Quote "
                    + "- Sell price must be greater than Buy price.");
        }

        if (buyPrice.lessOrEqual(zeroPrice)
                || sellPrice.lessOrEqual(zeroPrice)) {
            throw new DataValidationException("The price of the BUY side is "
                    + "less than or equal to zero. That is an illegal Quote price.");
        }

        if (buyQuoteSide.getOriginalVolume() <= 0
                || sellQuoteSide.getOriginalVolume() <= 0) {
            throw new DataValidationException("the volume of the BUY or SELL side "
                    + "is less than or equal to zero. That is an illegal Quote volume.");
        }

        if (userQuotes.contains(q.getUserName())) {
            this.buySide.removeQuote(q.getUserName());
            this.sellSide.removeQuote(q.getUserName());
            updateCurrentMarket();
        }

        addToBook(BookSide.BUY, buyQuoteSide);
        addToBook(BookSide.SELL, sellQuoteSide);

        userQuotes.add(q.getUserName());
        updateCurrentMarket();
    }

    /**
     * This method should add the provided Order to the appropriate
     * ProductSideBook.
     *
     * @param o The provided Order
     * @throws DataValidationException
     * @throws NotSubscribedException
     */
    public synchronized void addToBook(Order o)
            throws DataValidationException, NotSubscribedException {
        if (o == null) {
            throw new DataValidationException("The provided Order cound not be null.");
        }
        addToBook(o.getSide(), o);
        updateCurrentMarket();
    }

    /**
     * This method needs to determine if the “market” for this stock product has
     * been updated by some market action.
     */
    public synchronized void updateCurrentMarket() throws DataValidationException {
        Price buyPrice = this.buySide.topOfBookPrice();
        Price sellPrice = this.sellSide.topOfBookPrice();
        if (buyPrice == null) {
            buyPrice = PriceFactory.makeLimitPrice(0);
        }
        if (sellPrice == null) {
            sellPrice = PriceFactory.makeLimitPrice(0);
        }

        String s = String.format("%s%s%s%s", buyPrice,
                this.buySide.topOfBookVolume(), sellPrice,
                this.sellSide.topOfBookVolume());

        if (!s.equals(lastCurrentMarket)) {
            MarketDataDTO dto = new MarketDataDTO(this.product, buyPrice,
                    this.buySide.topOfBookVolume(), sellPrice,
                    this.sellSide.topOfBookVolume());
            CurrentMarketPublisher.getInstance().publishCurrentMarket(dto);
            lastCurrentMarket = s;
        }
    }

    /**
     * This method will take a HashMap of FillMessages passed in and determine
     * from the information it contains what the Last Sale price is.
     *
     * @param fills A HashMap of FillMessages
     * @return The Last Sale price
     * @throws DataValidationException If fills is null or empty
     */
    private synchronized Price
            determineLastSalePrice(HashMap<String, FillMessage> fills)
            throws DataValidationException {
        if (fills == null || fills.isEmpty()) {
            throw new DataValidationException("The fill messages passed in "
                    + "could not be null or empty");
        }

        ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
        Collections.sort(msgs);
        Collections.reverse(msgs);
        return msgs.get(0).getPrice();
    }

    /**
     * This method will take a HashMap of FillMessages passed in and determine
     * from the information it contains what the Last Sale quantity (volume) is.
     * A HashMap of FillMessages
     *
     * @return The Last Sale quantity
     * @throws DataValidationException If fills is null or empty
     */
    private synchronized int
            determineLastSaleQuantity(HashMap<String, FillMessage> fills)
            throws DataValidationException {
        if (fills == null || fills.isEmpty()) {
            throw new DataValidationException("The fill messages passed in "
                    + "could not be null or empty");
        }

        ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
        Collections.sort(msgs);
        Collections.reverse(msgs);
        return msgs.get(0).getVolume();
    }

    /**
     * This method deals with the addition of Tradables to the Buy/Sell
     * ProductSideBook and handles the results of any trades the result from
     * that addition.
     *
     * @param side The Buy/Sell side
     * @param trd The tradable object
     * @throws DataValidationException The tradable is null
     */
    private synchronized void addToBook(BookSide side, Tradable trd)
            throws DataValidationException, NotSubscribedException {
        if (trd == null) {
            throw new DataValidationException("The tradable could not be null");
        }

        ProductBookSide bookSide = (side == BookSide.BUY ? buySide : sellSide);

        if (ProductService.getInstance().getMarketState() == MarketState.PREOPEN) {
            bookSide.addToBook(trd);
            return;
        }

        HashMap<String, FillMessage> allFills = null;
        ProductBookSide oppositeBookSide = (side == BookSide.SELL ? buySide : sellSide);
        allFills = oppositeBookSide.tryTrade(trd);

        if (allFills != null && !allFills.isEmpty()) {
            updateCurrentMarket();
            int traded = trd.getOriginalVolume() - trd.getRemainingVolume();
            Price lastSalePrice = this.determineLastSalePrice(allFills);

            LastSalePublisher.getInstance()
                    .publishLastSale(this.product, lastSalePrice, traded);
        }

        if (trd.getRemainingVolume() > 0) {
            if (trd.getPrice().isMarket()) {
                CancelMessage cm = new CancelMessage(trd.getUser(), trd.getProduct(), 
                        trd.getPrice(), trd.getRemainingVolume(), "Cancelled", trd.getSide(), trd.getId());
                MessagePublisher.getInstance().publishCancel(cm);
            } else {
                bookSide.addToBook(trd);
            }
        }
    }

}
