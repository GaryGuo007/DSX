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
package depaul.stockexchange.tradable;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.price.Price;

/**
 * QuoteSide represents the price and volume for one side (BUY or SELL) of a Quote. 
 * One side of a Quote (the QuoteSide object) represents the price 
 * and volume of certain stock that the user is willing to BUY or SELL shares.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public final class QuoteSide extends TradableImplement implements Tradable {

    /**
     * The constructor should also set the QuoteSide�s remaining volume to the original volume value,
     * since all QuoteSides start with the remaining volume equal to the original volume. 
     * 
     * NOTE, the QuoteSide�s �id� should be set in the constructor to the following combined String:
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
                    int originalVolume, BookSide side) 
            throws InvalidTradableValue {
        this.setPrice(sidePrice);
        this.setUser(userName);
        this.setProduct(productSymbol);
        this.setSide(side);
        this.setOriginalVolume(originalVolume);
        this.setRemainingVolume(originalVolume);
        this.setCancelledVolume(0);
        this.buildId();
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
     */
    @Override
    protected void buildId() {
        this.id = String.format("%s%s%s", this.getUser(), 
                this.getProduct(), System.nanoTime());
    }

    /**
     * Set the QuoteSide's Price.
     * @param price
     * 		the order price (e.g. $257.09)
     * @throws InvalidTradableValue
     * 		If the price is market price, throws this exception.
     */
    @Override
    protected void setPrice(Price price) throws InvalidTradableValue {
        if (price.isMarket()) {
            throw new InvalidTradableValue("Invalid price, "
                    + "Quotes can only use limit prices.");
        }
        this.price = price;
    }

    /**
     * Returns true if the Tradable is part of a Quote, 
     * returns false if not (i.e., false if it�s part of an order)
     * @return 
     */
    @Override
    public boolean isQuote() {
        return true;
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