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

package DePaul.StockExchange.Tradable;

import DePaul.StockExchange.InvalidTradableValue;
import DePaul.StockExchange.Price.Price;

/**
 * An Order represents a request from a user to BUY or SELL a specific quantity of 
 * certain stock either at a specified price, or at the current market price.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public class Order implements Tradable {
    private String user;
    private String product;
    private String id;
    private String side;
    private Price price;
    private int originalVolume;
    private int remainingVolume;
    private int cancelledVolume;

    /*
    @todo: check the parameters
    */
    
    /**
     * Creates an Order object. The 5 parameters passed in should be used to 
     * set the corresponding Order data members. The constructor should also 
     * set the Order’s remaining volume to the original volume value, since all 
     * Orders start with the remaining volume equal to the original volume.
     * 
     * NOTE, the Order’s “id” should be set in the constructor to the following combined String:
     * id = the user name + the product symbol + the order price + the current time in nanoseconds**.
     * 
     * @param userName
     * 		the user name (e.g. “REX”)
     * @param productSymbol
     * 		the product (stock) symbol (e.g. “AMZN”)
     * @param orderPrice
     * 		the order price (e.g. $257.09)
     * @param originalVolume
     * 		the original volume (i.e., the original quantity) of the Order
     * @param side
     * 		"BUT" or "SELL" side
     * @throws InvalidTradableValue
     *		If the value is invalid (i.e., if the value is negative, 
     * 		or if the requested cancelled volume plus the current remaining volume 
     * 		exceeds the original volume), throw an exception.
     */
    public Order(String userName, String productSymbol, Price orderPrice, 
            int originalVolume, String side) throws InvalidTradableValue {
        this.setPrice(orderPrice);
        this.setUser(userName);
        this.setProduct(productSymbol);
        this.setId(userName, productSymbol, orderPrice);
        this.setOriginalVolume(originalVolume);
        this.setRemainingVolume(originalVolume);
        this.side = side;
    }
    
    /**
     * Sets the Order's "id".
     * NOTE, the Order's "id" should be set in the constructor to the following combined String:
     * id = the user name + the product symbol + the order price + the current time in nanoseconds**
     * 
     * @param userName
     * 		the user name (e.g. “REX”)
     * @param productSymbol
     * 		the product (stock) symbol (e.g. “AMZN”)
     * @param orderPrice
     * 		the order price (e.g. $257.09)
     */
    void setId(String userName, String productSymbol, Price orderPrice) {
        this.id = String.format("%s%s%s%s", userName, productSymbol, 
                orderPrice, System.nanoTime());
    }
    
    /**
     * Set the Order's Price.
     * 
     * @param orderPrice
     * 		the order price (e.g. $257.09)
     */
    void setPrice(Price orderPrice) {
        this.price = orderPrice;
    }
    
    /**
     * Sets the Order's user name.
     * 
     * @param userName
     * 		the user name (e.g. “REX”)
     * @throws InvalidTradableValue
     * 		If the user name is either null or an empty string, throws this exception. 
     */
    void setUser(String userName) throws InvalidTradableValue {
        if (userName == null || "".equals(userName)) {
            throw new InvalidTradableValue("Invalid user name: " + userName); 
        }
        this.user = userName;
    }
    
    /**
     * Sets the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the Order works with.
     * 
     * @param productSymbol
     * 		the product (stock) symbol (e.g. “AMZN”)
     * @throws InvalidTradableValue
     * 		If the product symbol is either null or an empty string, throws this exception. 
     */
    void setProduct(String productSymbol) throws InvalidTradableValue {
        if (productSymbol == null || "".equals(productSymbol)) {
            throw new InvalidTradableValue("Invalid product symbol: " 
                    + productSymbol); 
        }
        this.product = productSymbol;
    }
    
    /**
     * Sets the original volume (i.e., the original quantity) of the Order.
     * 
     * @param originalVolume
     * 		the original volume (i.e., the original quantity) of the Order
     * @throws InvalidTradableValue
     * 		If the original volume is set as zero or negative number, throws this exception.
     */
    void setOriginalVolume(int originalVolume) 
            throws InvalidTradableValue {
        if (originalVolume <= 0) {
            throw new InvalidTradableValue("Invalid Order Volume: " 
                    + originalVolume); 
        }
        this.originalVolume = originalVolume;
    }
    
    /**
     * Returns the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the Tradable works with.
     */
    @Override
    public String getProduct() {
        return this.product;
    }

    /**
     * Returns the price of the Tradable.
     */
    @Override
    public Price getPrice() {
        return this.price;
    }

    /**
     * Returns the original volume (i.e., the original quantity) of the Tradable.
     */
    @Override
    public int getOriginalVolume() {
        return this.originalVolume;
    }

    /**
     * Returns the remaining volume (i.e., the remaining quantity) of the Tradable.
     */
    @Override
    public int getRemainingVolume() {
        return this.remainingVolume;
    }
    
    /**
     * Returns the cancelled volume (i.e., the cancelled quantity) of the Tradable.
     */
    @Override
    public int getCancelledVolume() {
        return this.cancelledVolume;
    }
    
    /**
     * Sets the Tradable's cancelled quantity to the value passed in. 
     * This method should throw an exception if the value is invalid 
     * (i.e., if the value is negative, or if the requested cancelled 
     * volume plus the current remaining volume exceeds the original volume).
     * 
     * @param newCancelledVolume
     * 		A new Tradable's cancelled quantity to the value passed in. 
     * @throws InvalidTradableValue
     * 		If the value is invalid (i.e., if the value is negative, 
     * 		or if the requested cancelled volume plus the current remaining volume 
     * 		exceeds the original volume), throw an exception.
     */
    @Override
    public void setCancelledVolume(int newCancelledVolume) throws InvalidTradableValue {
        if (newCancelledVolume < 0) {
            throw new InvalidTradableValue("Invalid Cancelled Volume: " + newCancelledVolume);
        }
        if (newCancelledVolume + this.remainingVolume > this.originalVolume) {
            String errorMessage = String.format("Requested new Cancelled Volume "
                    + "(%s) plus the Remaining Volume (%s) exceeds the "
                    + "tradable's Original Volume (%s)"
                    , newCancelledVolume, this.remainingVolume, this.originalVolume);
            throw new InvalidTradableValue(errorMessage);
        }
        this.cancelledVolume = newCancelledVolume;
    }

    /**
     * Sets the Tradable’s remaining quantity to the value passed in. 
     * This method should throw an exception if the value is invalid 
     * (i.e., if the value is negative, or if the requested remaining 
     * volume plus the current cancelled volume exceeds the original volume).
     * 
     * @param newRemainingVolume
     * 		A new Tradable’s remaining quantity to the value passed in.
     * @throws InvalidTradableValue
     * 		If the value is invalid (i.e., if the value is negative, 
     * 		or if the requested cancelled volume plus the current remaining volume 
     * 		exceeds the original volume), throw an exception.
     */
    @Override
    public void setRemainingVolume(int newRemainingVolume) 
            throws InvalidTradableValue {
        if (newRemainingVolume < 0) {
            throw new InvalidTradableValue("Invalid Remaining Volume: " 
                    + newRemainingVolume);
        }
        if (newRemainingVolume + this.cancelledVolume > this.originalVolume) {
            String errorMessage = String.format("Requested new Remaining Volume "
                    + "(%s) plus the Cancelled Volume (%s) exceeds the "
                    + "tradable's Original Volume (%s)"
                    , newRemainingVolume, this.cancelledVolume, this.originalVolume);
            throw new InvalidTradableValue(errorMessage);
        }
        this.remainingVolume = newRemainingVolume;
    }

    /**
     * Returns the User id associated with the Tradable.
     */
    @Override
    public String getUser() {
        return this.user;
    }
    
    /**
     * Returns the side ("BUY"/"SELL") of the Tradable.
     */
    @Override
    public String getSide() {
        return this.side;
    }

    /**
     * Returns true if the Tradable is part of a Quote, 
     * returns false if not (i.e., false if it’s part of an order)
     */
    @Override
    public boolean isQuote() {
        return false;
    }
    
    /**
     * Returns the Tradable “id” – the value each tradable is given once it is received by the system.
     */
    @Override
    public String getId() {
        return this.id;
    }


    @Override
    public String toString() {
        return String.format("%s order: %s %s %s at %s "
                + "(Original Vol: %s, CXL'd Vol: %s), ID: %s", 
                user, side, remainingVolume, product, 
                price, originalVolume, cancelledVolume, id);
    }

}