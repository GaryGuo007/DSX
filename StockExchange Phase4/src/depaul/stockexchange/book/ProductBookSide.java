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
import depaul.stockexchange.price.Price;
import depaul.stockexchange.publishers.MessagePublisher;
import depaul.stockexchange.publishers.NotSubscribedException;
import depaul.stockexchange.tradable.Tradable;
import depaul.stockexchange.tradable.TradableDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Owns a list of the “booked” Tradable objects, organized by Price and by time
 * of arrival. An individual ProductBookSide represents the Buy side or the Sell
 * side. Each ProductBookSide also owns a TradeProcessor object.
 *
 * @author jimliu
 */
public class ProductBookSide {

    /**
     * The “side” that this ProductBookSide represents – BUY or SELL.
     */
    private BookSide side;

    /**
     * Set the “side” that this ProductBookSide represents
     *
     * @param side The side
     */
    private void setBookSide(BookSide side)
            throws DataValidationException {
        if (side == null) {
            throw new DataValidationException("Invalid argument: Side cannot be null");
        }
        this.side = side;
    }

    /**
     * A collection of book entries for this side, represented in a way that
     * organizes the book entries by Price, and then by time of arrival.
     */
    private HashMap<Price, ArrayList<Tradable>> bookEntries
            = new HashMap<>();

    /**
     * This TradeProcessor will be used to execute trades against this book side
     */
    private TradeProcessor tradeProcessor;

    /**
     * A reference back to the ProductBook object that this ProductBookSide
     * belongs to.
     */
    private ProductBook parent;

    /**
     * Set the product book
     *
     * @param book The ProductBook object that this ProductBookSide belongs to.
     * @throws DataValidationException If the ProductBook object is null
     */
    private void setProductBook(ProductBook book) throws DataValidationException {
        if (book == null) {
            throw new DataValidationException("The product book parsed in can't be null.");
        }
        this.parent = book;
    }

    /**
     * Constructor
     *
     * @param book A reference to the ProductBook object that this
     * ProductBookSide belongs to
     * @param side A side indicator (BUY or SELL) that specifies the “side” that
     * this ProductBookSide represents
     * @throws DataValidationException If the book is null
     */
    public ProductBookSide(ProductBook book, BookSide side)
            throws DataValidationException {
        this.setProductBook(book);
        this.setBookSide(side);
        this.tradeProcessor = new TradeProcessorPriceTimeImpl(this);
    }

    /**
     * This method will generate and return an ArrayList of TradableDTO’s
     * containing information on all the orders in this ProductBookSide that
     * have remaining quantity for the specified user.
     *
     * @param userName
     * @return
     */
    public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName)
            throws DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("Username can't be null or empty.");
        }

        ArrayList<TradableDTO> tradables = new ArrayList<>();

        // Get the Orders in the book (in “the “bookEntries” HashMap) for the specified userName
        for (ArrayList<Tradable> orders : bookEntries.values()) {
            // for every Order in the book (in “the “bookEntries” HashMap) 
            // for the specified userName that has remaining volume > 0
            for (Tradable order : orders) {
                if (userName.equals(order.getUser()) && order.getRemainingVolume() > 0) {
                    tradables.add(new TradableDTO(order));
                }
            }
        }

        return tradables;
    }

    /**
     * Create a sorted ArrayList of the prices in this book
     *
     * @return
     */
    private ArrayList<Price> sortedPrices() {
        ArrayList<Price> sorted = new ArrayList<>(bookEntries.keySet()); // Get prices 
        Collections.sort(sorted); // Sort them
        if (side == BookSide.BUY) {
            Collections.reverse(sorted); // Reverse them 
        }
        return sorted;
    }

    /**
     * This method should return an ArrayList of the Tradables that are at the
     * best price in the “bookEntries” HashMap.
     *
     * @return Get the first Price in that ArrayList
     */
    synchronized ArrayList<Tradable> getEntriesAtTopOfBook() {
        if (bookEntries.isEmpty()) {
            return null;
        }
    	Price top = topOfBookPrice();
        if (top == null) 
            return null;
        return bookEntries.get(top);
    }

    /**
     * This method should return an array of Strings, where each index holds a
     * “Price x Volume” String.
     *
     * @return An array of Strings
     */
    public synchronized String[] getBookDepth() {
        if (bookEntries.isEmpty()) {
            return new String[]{"<Empty>"};
        }

        String[] bookDepth = new String[bookEntries.size()];

        ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet());
        Collections.sort(sorted);
        if (side == BookSide.BUY) {
            Collections.reverse(sorted);
        }
        for (int i = 0; i < sorted.size(); i++) {
            int total = 0;
            Price price = sorted.get(i);
            ArrayList<Tradable> tradables = bookEntries.get(price);
            for (Tradable t : tradables) {
                total += t.getRemainingVolume();
            }
            bookDepth[i] = String.format("%s x %d", price, total);
        }

        return bookDepth;
    }

    /**
     * This method should return all the Tradables in this book side at the
     * specified price.
     *
     * @param price
     * @return
     */
    synchronized ArrayList<Tradable> getEntriesAtPrice(Price price)
            throws DataValidationException {
        if (price == null) {
            throw new DataValidationException("Price can't be null or empty.");
        }

        if (!bookEntries.containsKey(price)) {
            return null;
        }
        return bookEntries.get(price);
    }

    /*publicsynchronizedbooleanhasMarketPrice()
     This method should return true if the product book (the “bookEntries” HashMap) contains a Market Price 
     (if a MarketPrice is a key in the “bookEntries” HashMap you return true, otherwise false).
     */
    /**
     * This method should return true if the product book (the “bookEntries”
     * HashMap) contains a Market Price.
     *
     * @return if a MarketPrice is a key in the “bookEntries” HashMap you return
     * true, otherwise false
     */
    public synchronized boolean hasMarketPrice() {
        for (Price price : bookEntries.keySet()) {
            if (price.isMarket()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method should return true if the ONLY Price in this product’s book
     * is a Market Price.
     *
     * @return if there is only one key in the “bookEntries” HashMap and it is a
     * Market Price, you return true, otherwise false
     */
    public synchronized boolean hasOnlyMarketPrice() {
        return bookEntries.size() == 1
                && hasMarketPrice();
    }

    /**
     * This method should return the best Price in the book side.
     *
     * @return If the “bookEntries” HashMap is empty, then return null.
     * Otherwise, create a sorted ArrayList of Prices. Then return the first
     * Price in that list.
     */
    public synchronized Price topOfBookPrice() {
        if (bookEntries.isEmpty()) {
            return null;
        }
        ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet());
        Collections.sort(sorted);
        if (side == BookSide.BUY) {
            Collections.reverse(sorted);
        }
        return sorted.get(0);
    }

    /**
     * This method should return the volume associated with the best Price in
     * the book side.
     *
     * @return If the “bookEntries” HashMap is empty, then return zero.
     * Otherwise, sum up all the remaining volume values for the top price
     */
    public synchronized int topOfBookVolume() {
        Price price = this.topOfBookPrice();
        // the “bookEntries” HashMap is empty
        if (price == null) {
            return 0;
        }

        ArrayList<Tradable> tradables = bookEntries.get(price);
        int total = 0;
        for (Tradable t : tradables) {
            total += t.getRemainingVolume();
        }
        return total;
    }

    /*
     publicsynchronizedbooleanisEmpty()
     Returns true if the product book (the “bookEntries” HashMap) is empty, false otherwise.
     */
    /**
     * Check if the product book is empty
     *
     * @return If the product book (the “bookEntries” HashMap) is empty, return
     * true, Otherwise false
     */
    public synchronized boolean isEmpty() {
        return bookEntries.isEmpty();
    }

    /**
     * This method should cancel every Order or QuoteSide at every price in the
     * book
     *
     */
    public synchronized void cancelAll()
            throws DataValidationException, OrderNotFoundException {
        HashMap<Price, ArrayList<Tradable>> bookCopy = new HashMap<>(bookEntries);
        for (Price p : bookCopy.keySet()) {
            ArrayList<Tradable> list = new ArrayList<>(bookCopy.get(p));
            for (Tradable t : list) {
                if (t.isQuote()) {
                    submitQuoteCancel(t.getUser());
                } else {
                    submitOrderCancel(t.getId());
                }
            }
        }
    }

    /**
     * This method should search the book for a Quote from the specified user.
     * Note, if the Quote was the last Tradable in the ArrayList of Tradables at
     * that price, remove the price entry from the “bookEntries” HashMap
     *
     * @param user The specified user
     * @return Return the DTO from the method
     */
    public synchronized TradableDTO removeQuote(String user)
            throws DataValidationException {
        if (Utils.isNullOrEmpty(user)) {
            throw new DataValidationException("user can't be null or empty.");
        }

        Tradable tradableFound = null;
        for (Price price : bookEntries.keySet()) {
            ArrayList<Tradable> tradables = bookEntries.get(price);
            for (Tradable t : tradables) {
                if (!t.isQuote()) {
                    continue;
                }
                if (user.equals(t.getUser())) {
                    tradableFound = t;
                    tradables.remove(t);
                    break; //a user can only have one Quote
                }
            }
            // Found the tradable, 
            // then check if the tradable found is the last one.
            if (tradableFound != null && tradables.isEmpty()) {
                bookEntries.remove(price);
                break; 
            }
        }

        if (tradableFound != null) {
            return new TradableDTO(tradableFound);
        }

        return null;
    }

    /**
     * This method should cancel the Order (if possible) that has the specified
     * identifier.
     *
     * @param orderId The specified identifier.
     */
    public synchronized void submitOrderCancel(String orderId)
            throws DataValidationException, OrderNotFoundException {
        if (Utils.isNullOrEmpty(orderId)) {
            throw new DataValidationException("orderId can't be null or empty.");
        }

        Tradable tradableFound = null;
        for (Price price : bookEntries.keySet()) {
            ArrayList<Tradable> tradables = bookEntries.get(price);
            for (Tradable t : tradables) {
                if (t.isQuote()) {
                    continue;
                }
                if (orderId.equals(t.getId())) {
                    tradableFound = t;
                    tradables.remove(t);
                    break; //a user can only have one Quote
                }
            }
            // Found the tradable, 
            // then check if the tradable found is the last one.
            if (tradableFound != null && tradables.isEmpty()) {
                bookEntries.remove(price);
                break; 
            }
        }

        if (tradableFound != null) {
            // Publish a Cancel message
            String details = "Order " + tradableFound.getSide() + "-Side Cancelled";
            parent.publishCancel(tradableFound, details);

            addOldEntry(tradableFound);
        } else { // If NO order is found with the specified Id, then it was already filled or cancelled.
            parent.checkTooLateToCancel(tradableFound.getId());
        }
    }

    /**
     * This method should cancel the QuoteSide (if possible) that has the
     * specified userName.
     *
     * @param userName The specified userName
     * @throws DataValidationException
     */
    public synchronized void submitQuoteCancel(String userName)
            throws DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("userName can't be null or empty.");
        }
        TradableDTO dto = removeQuote(userName);
        if (dto != null) {
            String details = "Quote " + dto.side + "-Side Cancelled";
            CancelMessage cm = new CancelMessage(dto.user, dto.product, dto.price,
                    dto.remainingVolume, details, dto.side, dto.id);
            MessagePublisher.getInstance().publishCancel(cm);
        }
    }

    /**
     * This method should add the Tradable passed in to the “parent” product
     * book’s “old entries” list.
     *
     * @param t The Tradable to be added
     * @throws DataValidationException If t is null
     */
    public void addOldEntry(Tradable t)
            throws DataValidationException {
        if (t == null) {
            throw new DataValidationException("The tradable can't be null.");
        }

        parent.addOldEntry(t);
    }

    /**
     * This method should add the Tradable passed in to the book (the
     * “bookEntries” HashMap).
     *
     * @param trd The Tradable passed in
     * @throws DataValidationException If trd is null
     */
    public synchronized void addToBook(Tradable trd)
            throws DataValidationException {
        if (trd == null) {
            throw new DataValidationException("The tradable can't be null.");
        }

        if (trd.getPrice() == null) {
            throw new DataValidationException("The price of tradable can't be null.");
        }
        
        Price price = trd.getPrice();

        ArrayList<Tradable> tradables = bookEntries.get(price);
        if (tradables == null) { // can't find by price, add it
            tradables = new ArrayList<>();
            bookEntries.put(price, tradables);
        }
        tradables.add(trd);
    }

    /**
     * This method will attempt a trade the provided Tradable against entries in
     * this ProductBookSide.
     *
     * @param trd the provided Tradable
     * @return
     * @throws DataValidationException If tradable is
     * null
     */
    public HashMap<String, FillMessage> tryTrade(Tradable trd)
            throws DataValidationException, NotSubscribedException {
        if (trd == null) {
            throw new DataValidationException("The tradable can't be null.");
        }


        HashMap<String, FillMessage> allFills = null;

        if (side == BookSide.BUY) {
            allFills = trySellAgainstBuySideTrade(trd);
        } else if (side == BookSide.SELL) {
            allFills = tryBuyAgainstSellSideTrade(trd);
        }

        for (FillMessage fm : allFills.values()) {
            MessagePublisher.getInstance().publishFill(fm);
        }

        return allFills;
    }

    /**
     * This method will try to fill the SELL side Tradable passed in against the
     * content of the book.
     *
     * @param trd The SELL side Tradable
     * @return
     *
     * @throws DataValidationException
     */
    public HashMap<String, FillMessage> trySellAgainstBuySideTrade(Tradable trd)
            throws DataValidationException {
        if (trd == null) {
            throw new DataValidationException("The tradable can't be null.");
        }

        if (trd.getPrice() == null) {
            throw new DataValidationException("The price of tradable can't be null.");
        }
        
        if(trd.getSide() != BookSide.SELL) {
            throw new DataValidationException("The BookSide is not a sell side.");
        }

        HashMap<String, FillMessage> allFills = new HashMap<>();
        HashMap<String, FillMessage> fillMsgs = new HashMap<>();
        // @todo: verify the loop
        while (trd.getRemainingVolume() > 0 && !isEmpty() && 
                (trd.getPrice().isMarket() || trd.getPrice().lessOrEqual(topOfBookPrice()))) {
            fillMsgs = mergeFills(fillMsgs, tradeProcessor.doTrade(trd));
        }
        allFills.putAll(fillMsgs);
        return allFills;
    }

    /**
     * This method is designed to merge multiple fill messages together into one
     * consistent list. When one user’s Order of QuoteSide trades against
     * multiple Tradable objects at the same price – only ONE fill should be
     * sent (for the total traded volume).
     *
     * @param existing The existing Order of QuoteSide trades
     * @param newOnes The new multiple Tradable objects
     * @return The results after merged
     */
    private HashMap<String, FillMessage> mergeFills(
            HashMap<String, FillMessage> existing,
            HashMap<String, FillMessage> newOnes) throws DataValidationException {
        if (existing == null) {
            throw new DataValidationException("The tradable can't be null.");
        }

        if (newOnes == null) {
            throw new DataValidationException("The price of tradable can't be null.");
        }

        if (existing.isEmpty()) {
            return new HashMap<String, FillMessage>(newOnes);
        }

        HashMap<String, FillMessage> results = new HashMap<>(existing);
        // @Todo: check if the value is null
        for (String key : newOnes.keySet()) { // For each Trade Id key in the “newOnes” HashMap
            if (!existing.containsKey(key)) { // If the “existing” HashMap does not have that key...
                results.put(key, newOnes.get(key)); // ...then simply add this entry to the “results” HashMap
            } else { // Otherwise, the “existing” HashMap does have that key – we need to update the data
                FillMessage fm = results.get(key); // Get the FillMessage from the “results” HashMap
                // NOTE – for the below, you will need to make these 2 FillMessage methods “public”!
                fm.setVolume(newOnes.get(key).getVolume()); // Update the fill volume 
                fm.setDetails(newOnes.get(key).getDetails()); // Update the fill details
            }
        }
        return results;
    }

    /**
     * This method will try to fill the BUY side Tradable passed in against the
     * content of the book.
     *
     * @param trd The BUY side Tradable
     * @return
     */
    public synchronized HashMap<String, FillMessage>
            tryBuyAgainstSellSideTrade(Tradable trd) throws DataValidationException {
        if (trd == null) {
            throw new DataValidationException("The tradable can't be null.");
        }

        if (trd.getPrice() == null) {
            throw new DataValidationException("The price of tradable can't be null.");
        }

        if(trd.getSide() != BookSide.BUY) {
            throw new DataValidationException("The BookSide is not a buy side.");
        }

        HashMap<String, FillMessage> allFills = new HashMap<>();
        HashMap<String, FillMessage> fillMsgs = new HashMap<>();

        // @todo: verify the loop
        while (trd.getRemainingVolume() > 0 && !isEmpty() && 
                (trd.getPrice().isMarket() || trd.getPrice().greaterOrEqual(topOfBookPrice()))) {
            HashMap<String, FillMessage> someMsgs = this.tradeProcessor.doTrade(trd);
            fillMsgs = mergeFills(fillMsgs, someMsgs);
        }
        allFills.putAll(fillMsgs);
        return allFills;
    }

    /**
     * This method will remove an key/value pair from the book if the ArrayList
     * associated with the Price passed in is empty.
     *
     * @param p The price passed in
     */
    public synchronized void clearIfEmpty(Price p) throws DataValidationException {
        if (p == null) {
            throw new DataValidationException("The price can't be null.");
        }

        // Make sure we have this price in the book
        if (bookEntries.containsKey(p)) {
            ArrayList<Tradable> ts = bookEntries.get(p);
            if (ts.isEmpty()) { // check if it's empty
                bookEntries.remove(p);
            }
        }
    }

    /**
     * This method is design to remove the Tradable passed in from the book
     *
     * @param t The tradable object
     */
    public synchronized void removeTradable(Tradable t) throws DataValidationException {
        if (t == null) {
            throw new DataValidationException("The tradable can't be null.");
        }
        if (t.getPrice() == null) {
            throw new DataValidationException("The price of tradable can't be null.");
        }
        ArrayList<Tradable> entries = bookEntries.get(t.getPrice());
        if (entries == null) {
            return;
        }
        if (entries.remove(t)) {
            this.clearIfEmpty(t.getPrice());
        }
    }
}
