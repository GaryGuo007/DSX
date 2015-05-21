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

import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.messages.MarketMessage;
import depaul.stockexchange.messages.MarketState;
import depaul.stockexchange.publishers.MarketDataDTO;
import depaul.stockexchange.publishers.MessagePublisher;
import depaul.stockexchange.publishers.NotSubscribedException;
import depaul.stockexchange.tradable.Order;
import depaul.stockexchange.tradable.Quote;
import depaul.stockexchange.tradable.TradableDTO;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Façade to the Product Books 
 * (“booked” Tradable objects on the Buy and Sell side).
 * 
 * @author jimliu
 */
public class ProductService {
    
    private volatile static ProductService instance;
    private HashMap<String, ProductBook> allBooks = new HashMap<  >();
    private MarketState currentMarketState = MarketState.CLOSED;

    private ProductService() {}
    
    /**
     * Get the singleton instance
     * @return 
     *      The instance of TickerPublisher class
     */
    public static ProductService getInstance() {
        if (instance == null) {
            synchronized(ProductService.class) {
                if (instance == null) {
                    instance = new ProductService();
                }
            }
        }
        return instance;
    }
    
    /**
     * This method will return a List of TradableDTOs containing any orders 
     * with remaining quantity for the user and the stock specified. 
     * @param userName
     * @param product
     *      The String stock symbol
     * @return
     *      A List of TradableDTOs
     * @throws DataValidationException 
     *      If the username/product is null or empty
     * @throws NoSuchProductException 
     *      If can't find the product
     */
    public synchronized ArrayList<TradableDTO> 
        getOrdersWithRemainingQty(String userName, String product) 
                throws DataValidationException, NoSuchProductException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("The username could not be null or empty.");
        }
        
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The product could not be null or empty.");
        }
        
        ProductBook book = allBooks.get(product);
        if (book == null) {
            throw new NoSuchProductException("Could not find the product by "
                    + "the String stock symbol passed in: " + product);
        }
        return book.getOrdersWithRemainingQty(userName);
    }
    
    /**
     * This method will return a List of MarketDataDTO containing the best buy 
     * price/volume and sell price/volume for the specified stock product.
     * @return 
     *      A List of MarketDataDTO
     * @throws DataValidationException 
     *      If product is null or empty
     * @throws NoSuchProductException 
     *      If can't find the product
     */
    public synchronized MarketDataDTO getMarketData(String product) 
            throws DataValidationException, NoSuchProductException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The product could not be null or empty.");
        }
        
        ProductBook book = allBooks.get(product);
        
        if (book == null) {
            throw new NoSuchProductException("Could not find the product by "
                    + "the String stock symbol passed in: " + product);
        }
        
        return book.getMarketData();
        
    }
    
    /**
     * This method should simply return the current product state
     * (OPEN, PREOPEN, CLOSED) value.
     * @return 
     *      The current product state
     */
    public synchronized MarketState getMarketState() {
        return currentMarketState;
    }
    
    /**
     * This method is should return a 2-dimensional array of Strings 
     * that contain the prices and volumes at all prices present 
     * in the buy and sell sides of the book. 
     * @param product
     *      The String stock symbol
     * @return
     *      A 2-dimensional array of Strings
     * @throws DataValidationException 
     *      If product is null or empty
     * @throws NoSuchProductException 
     *      If can't find the product
     */
    public synchronized String[][] getBookDepth(String product) 
            throws DataValidationException, NoSuchProductException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The product could not be null or empty.");
        }
        
        ProductBook book = allBooks.get(product);
        
        if (book == null) {
            throw new NoSuchProductException("Could not find the product by "
                    + "the String stock symbol passed in: " + product);
        }
        return book.getBookDepth();
    }
    
    /**
     * This method should simply return an Arraylist 
     * containing all the keys in the “allBooks” HashMap
     * @return 
     *      An Arraylist 
     */
    public synchronized ArrayList<String> getProductList() {
        return new ArrayList<>(allBooks.keySet());
    }
    
    /**
     * This method should update the market state to the new value passed in. 
     * The market state has only certain transitions that are legal: 
     * CLOSED -> PREOPEN -> OPEN -> CLOSED. 
     * @param ms 
     *      The market state
     * @throws InvalidMarketStateTransition
     */
    public synchronized void setMarketState(MarketState ms) 
            throws InvalidMarketStateTransition, NotSubscribedException, DataValidationException, OrderNotFoundException {
        if (ms == null) {
            throw new DataValidationException("MarketState can't be null.");
        }
        if (this.currentMarketState == MarketState.CLOSED 
                && ms != MarketState.PREOPEN) {
            String action = ms == MarketState.CLOSED ? "close" : "open";
            throw new InvalidMarketStateTransition("You could not " + action 
                    + " a closed market state.");
        }

        if (this.currentMarketState == MarketState.PREOPEN 
                && ms != MarketState.OPEN) {
            String action = ms == MarketState.CLOSED ? "close" : "reopen";
            throw new InvalidMarketStateTransition("You could not " + action 
                    + " a reopened market state.");
        }
        
        if (this.currentMarketState == MarketState.OPEN 
                && ms != MarketState.CLOSED) {
            String action = ms == MarketState.OPEN ? "open" : "reopen";
            throw new InvalidMarketStateTransition("You could not " + action 
                    + " a opened market state.");
        }
        
        currentMarketState = ms;
        
        MarketMessage mm = new MarketMessage(ms);
        MessagePublisher.getInstance().publishMarketMessage(mm);
        
        for (ProductBook pb : allBooks.values()) {
            if (ms == MarketState.OPEN) {
                pb.openMarket();
            } else if (ms == MarketState.CLOSED) {
                pb.closeMarket();
            }
        }
    }
    
    /**
     * This method will create a new stock product that can be used for trading.
     * @param product 
     *      The stock symbol string
     * @throws DataValidationException 
     *      If the stock symbol is null or empty
     * @throws ProductAlreadyExistsException 
     *       If the “allBooks” HashMap already has an entry for that stock symbol
     */
    public synchronized void createProduct(String product)
            throws DataValidationException, ProductAlreadyExistsException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The product could not be null or empty.");
        }
        
        if (allBooks.containsKey(product)) {
            throw new ProductAlreadyExistsException("The product with this name (" +
                    product + ") had been created.");
        }
        
        allBooks.put(product, new ProductBook(product));
    }
    
    /**
     * This method should forward the provided Quote to 
     * the appropriate product book.
     * @param q 
     *      The provided quote
     * @throws InvalidMarketStateException
     *      If the market is CLOSED
     * @throws DataValidationException
     *      If the provided quote is null
     * @throws NoSuchProductException
     *      Can't find the product book with the product symbol
     */
    public synchronized void submitQuote(Quote q) 
            throws InvalidMarketStateException, DataValidationException, 
                    NoSuchProductException, NotSubscribedException {
        if (q == null) {
            throw new DataValidationException("The provided quote could not be null");
        }
        
        if (currentMarketState == MarketState.CLOSED) {
            throw new InvalidMarketStateException("The market is CLOSED");
        }
        
        ProductBook book = allBooks.get(q.getProduct());
        if (book == null) {
            throw new NoSuchProductException("Could not find the product book "
                    + "with the product symbol (" + q.getProduct() + ")"
                    + "contained within the Quote object");
        }
        book.addToBook(q);
    }
    
    /**
     * This method should forward the provided Order to the appropriate product book.
     * @param o
     *      The provided Order
     * @return
     * @throws InvalidMarketStateException
     * @throws DataValidationException
     * @throws NoSuchProductException 
     */
    public synchronized String submitOrder(Order o) 
            throws InvalidMarketStateException, DataValidationException, 
                    NoSuchProductException, NotSubscribedException {
        if (o == null) {
            throw new DataValidationException("The provided order could not be null");
        }
        
        if (currentMarketState == MarketState.CLOSED) {
            throw new InvalidMarketStateException("The market is CLOSED");
        }
        
        if (currentMarketState == MarketState.PREOPEN 
                && o.getPrice().isMarket()) {
            throw new InvalidMarketStateException("You cannot submit MKT "
                    + "orders during PREOPEN.");
        }
        
        ProductBook book = allBooks.get(o.getProduct());
        if (book == null) {
            throw new NoSuchProductException("Could not find the product book "
                    + "with the product symbol (" + o.getProduct() + ")"
                    + "contained within the Order object");
        }
        book.addToBook(o);      
        
        return o.getId();
    }
    
    
    /**
     * This method should forward the provided Order Cancel 
     * to the appropriate product book.
     * @param product
     * @param side
     * @param orderId 
     */
    public synchronized void submitOrderCancel(String product,
            BookSide side,String orderId) throws InvalidMarketStateException, 
                DataValidationException, NoSuchProductException, OrderNotFoundException {
        
        if (side == null) {
            throw new DataValidationException("Bookside can't be null.");
        }
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The provided product could not "
                    + "be null or empty");
        }
        
        if (Utils.isNullOrEmpty(orderId)) {
            throw new DataValidationException("The provided orderId could not "
                    + "be null or empty");
        }
        
        if (currentMarketState == MarketState.CLOSED) {
            throw new InvalidMarketStateException("The market is CLOSED");
        }
        
        ProductBook book = allBooks.get(product);
        if (book == null) {
            throw new NoSuchProductException("Could not find the product book "
                    + "with the product symbol (" + product + ")"
                    + "contained within the Order object");
        }
        book.cancelOrder(side, orderId);
        
    }

    
    /**
     * This method should forward the provided Quote Cancel to 
     * the appropriate product book.
     * @param userName
     * @param product
     * @throws InvalidMarketStateException
     * @throws DataValidationException
     * @throws NoSuchProductException 
     */
    public synchronized void submitQuoteCancel(String userName, String product) 
            throws InvalidMarketStateException, 
                DataValidationException, NoSuchProductException {
        
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The provided product could not "
                    + "be null or empty");
        }
        
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("The provided userName could not "
                    + "be null or empty");
        }
        
        if (currentMarketState == MarketState.CLOSED) {
            throw new InvalidMarketStateException("The market is CLOSED");
        }
        
        ProductBook book = allBooks.get(product);
        if (book == null) {
            throw new NoSuchProductException("Could not find the product book "
                    + "with the product symbol (" + product + ")"
                    + "contained within the Order object");
        }
        book.cancelQuote(userName);
    }

}
