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
 * QuoteSide represents the price and volume for one side (BUY or SELL) of a Quote. 
 * One side of a Quote (the QuoteSide object) represents the price 
 * and volume of certain stock that the user is willing to BUY or SELL shares.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public final class QuoteSide implements Tradable {
    private String user;
    private String product;
    private String id;
    private String side;
    private Price price;
    private int originalVolume;
    private int remainingVolume;
    private int cancelledVolume;

    /**
     * The constructor should also set the QuoteSide’s remaining volume to the original volume value,
     * since all QuoteSides start with the remaining volume equal to the original volume. 
     * 
     * NOTE, the QuoteSide’s “id” should be set in the constructor to the following combined String:
     * id = the user name + the product symbol + the current time in nanoseconds**.
     * 
     * @param userName
     * 		the user name (e.g. "ARI")
     * @param productSymbol
     * 		the product symbol (e.g. "FB")
     * @param sidePrice
     * 		the QuoteSides price (e.g. $257.09)
     * @param originalVolume
     * 		the original volume (i.e., the original quantity) of the QuoteSides
     * @param side
     * 		"BUT" or "SELL" side
     * @throws InvalidTradableValue
     * 		If the price is market price, throws an exception.
     * 		If the user name is either null or an empty string, throws an exception. 
     * 		If the product symbol is either null or an empty string, throws an exception. 
     * 		If the original volume is set as zero or negative number, throws an exception.
     * 		If the side is neither "BUY" nor "SELL", throws an exception.
     * 		If the value is invalid (i.e., if the value is negative, 
     * 		or if the requested cancelled volume plus the current remaining volume 
     * 		exceeds the original volume), throw an exception.
     */
    public QuoteSide(String userName, String productSymbol, Price sidePrice,
                    int originalVolume, String side) 
            throws InvalidTradableValue {
        this.setPrice(sidePrice);
        this.setUser(userName);
        this.setProduct(productSymbol);
        this.setSide(side);
        this.setId(userName, productSymbol);
        this.setOriginalVolume(originalVolume);
        this.setRemainingVolume(originalVolume);
        this.setCancelledVolume(0);
    }

    public QuoteSide(QuoteSide qs) {
        this.id = qs.getId();
        this.user = qs.getUser();
        this.product = qs.getProduct();
        this.side = qs.getSide();
        this.price = qs.getPrice();
        this.originalVolume = qs.getOriginalVolume();
        this.cancelledVolume = qs.getCancelledVolume();
        this.remainingVolume = qs.getRemainingVolume();
    }

    /**
     * Sets the QuoteSide's "id".
     * NOTE, the QuoteSide's "id" should be set in the constructor to the following combined String:
     * id = the user name + the product symbol + the order price + the current time in nanoseconds**
     * 
     * @param userName
     * 		the user name (e.g. “REX”)
     * @param productSymbol
     * 		the product (stock) symbol (e.g. “AMZN”)
     * @param orderPrice
     * 		the order price (e.g. $257.09)
     */
    private void setId(String userName, String productSymbol) {
        this.id = String.format("%s%s%s", userName, 
                productSymbol, System.nanoTime());
    }

    /**
     * Set the QuoteSide's Price.
     * @param price
     * 		the order price (e.g. $257.09)
     * @throws InvalidTradableValue
     * 		If the price is market price, throws this exception.
     */
    public void setPrice(Price price) throws InvalidTradableValue {
        if (price.isMarket()) {
            throw new InvalidTradableValue("Invalid price, "
                    + "Quotes can only use limit prices.");
        }
        this.price = price;
    }

    /**
     * Sets the QuoteSide's user name.
     * 
     * @param userName
     * 		the user name (e.g. “REX”)
     * @throws InvalidTradableValue
     * 		If the user name is either null or an empty string, throws an exception. 
     */
    public void setUser(String userName) throws InvalidTradableValue {
        if (userName == null || "".equals(userName)) {
            throw new InvalidTradableValue("Invalid user name: " + userName); 
        }
        this.user = userName;
    }

    /**
     * Sets the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the QuoteSide works with.
     * 
     * @param productSymbol
     * 		the product (stock) symbol (e.g. “AMZN”)
     * @throws InvalidTradableValue
     * 		If the product symbol is either null or an empty string, throws an exception. 
     */
    private void setProduct(String productSymbol) throws InvalidTradableValue {
        if (productSymbol == null || "".equals(productSymbol)) {
            throw new InvalidTradableValue("Invalid product symbol: " 
                    + productSymbol); 
        }
        this.product = productSymbol;
    }

    /**
     * Sets the original volume (i.e., the original quantity) of the QuoteSide.
     * 
     * @param originalVolume
     * 		the original volume (i.e., the original quantity) of the QuoteSide
     * @throws InvalidTradableValue
     * 		If the original volume is set as zero or negative number, throws an exception.
     */
    public void setOriginalVolume(int originalVolume)
            throws InvalidTradableValue {
        if (originalVolume <= 0) {
            throw new InvalidTradableValue("Invalid " + this.side 
                    + "-Side Volume: " + originalVolume); 
        }
        this.originalVolume = originalVolume;
    }

    /**
     * Sets the side ("BUY"/"SELL") of the QuoteSide.
     * 
     * @param side
     * 		"BUY" side or "SELL" side
     * @throws InvalidTradableValue
     * 		If the side is neither "BUY" nor "SELL", throws an exception.
     */
    public void setSide(String side) throws InvalidTradableValue {
        if (!"SELL".equals(side) && !"BUY".equals(side)) {
            throw new InvalidTradableValue("Invalid Side: " + side); 
        }
        this.side = side;
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
     * Sets the Tradable’s cancelled quantity to the value passed in. 
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
        return true;
    }

    /**
     * Returns the Tradable “id” – the value each tradable is given once it is received by the system.
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Generates a String representing the key values in this order. 
     * Recommended String value generated by your toString:
     * USER1 order: BUY 250 GE at $21.59 (Original Vol: 250, CXL'd Vol: 0), 
     * ID: USER1GE$21.591684416944495943
     */
    @Override
    public String toString() {
        return String.format("%sx%s (Original Vol: %s, CXL'd Vol: %s) [%s]", 
                this.getPrice(), this.getRemainingVolume(), 
                this.getOriginalVolume(), 
                this.getCancelledVolume(), this.getId());
    }
}
