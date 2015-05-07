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
package price;

import depaulStockExchange.InvalidPriceOperation;


/**
 * The Price class will be used to represent the various prices
 * used throughout the application.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public final class Price implements Comparable<Price> {

    private final long priceValue;
    private final boolean market;

    /** 
     * Creates a Price object representing the provided value.
     * Remember, a long value passed in of 1499 represents $14.99, 
     * 12850 represents $128.50, etc.
     *
     * @param value
     *      The value of price, a long value passed in of 1499 represents $14.99, 
     *      12850 represents $128.50, etc.
     */
    Price(long value) {
        this.priceValue = value;
        this.market = false;
    }

    /** 
     * Creates a Price object representing Market price.
     */
    Price() {
        this.priceValue = 0;
        this.market = true;
    }
    
    /**
     * Get the value of price.
     * 
     * @return 
     *      the value of price.
     */
    private long getPriceValue() {
        return this.priceValue;
    }

    /** 
    * The add method of the Price object. 
    * Add the value of the Price object passed in to the current Price object's value  
    * and return a new Price object representing that sum.
    * 
    * @param  p
    *       The Price object passed in to add with the current Price
    * @return
    *       Return a new Price object representing that sum.
    * @throws InvalidPriceOperation 
    *       If either Price is a Market Price, or the Price passed in is null, 
    *       throws InvalidPriceOperation.
    */ 
    public Price add(Price p) throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("The Price passed in can't be null.");
        }
        if (this.isMarket() || p.isMarket()) {
            throw new InvalidPriceOperation("Can't add with a Market Price.");
        }
        
        long newValue;
        try {
            newValue = Math.addExact(this.getPriceValue(), p.priceValue);
        }
        catch(ArithmeticException ae) {
            throw new InvalidPriceOperation("The result is overflow. " 
                    + ae.getMessage());
        }
        return PriceFactory.makeLimitPrice(newValue);
    }

    /**
     * Subtract the value of the Price object passed in 
     * from the current Price object's value 
     * 
     * @param p
     *  The Price object passed in to subtract with the current Price
     * @return
     *  Return a new Price object representing that difference.
     * @throws InvalidPriceOperation 
     *  If either Price is a Market Price, or the Price passed in is null, 
     *  throws InvalidPriceOperation.
     */
    public Price subtract(Price p) throws InvalidPriceOperation {
        if (p == null) {
            throw new InvalidPriceOperation("The Price passed in can't be null.");
        }
        if (this.isMarket() || p.isMarket()) {
            throw new InvalidPriceOperation("Can't add with a Market Price.");
        }

        long newValue = this.getPriceValue() - p.priceValue;
        return PriceFactory.makeLimitPrice(newValue);
    }

    /**
     * Multiply the value passed in by the current Price object's value
     * 
     * @param p
     *      The value passed in to multiply with the current Price
     * @return
     *      Returns a new Price object representing that product.
     * @throws InvalidPriceOperation 
     *      If either Price is a Market Price, or the Price passed in is null, 
     *      throws InvalidPriceOperation.
     */
    public Price multiply(int p) throws InvalidPriceOperation {
        if (this.isMarket()) {
            throw new InvalidPriceOperation("Price is a Market Price, "
                    + "or the Price passed in is null.");
        }
        long newValue;
        try {
            newValue = Math.multiplyExact(this.getPriceValue(), p);
        }
        catch(ArithmeticException ae) {
            throw new InvalidPriceOperation("The result is overflow. " 
                    + ae.getMessage());
        }
        return PriceFactory.makeLimitPrice(newValue);
    }

    /**
     * Standard compareTo logic.
     * Assume the value of market price is 0.
     * 
     * @param p
     *      The price passed in to compare with current price
     */
    @Override
    public int compareTo(Price p) {
        if (this.getPriceValue() < p.getPriceValue()) {
            return -1;
        }
        if (this.getPriceValue() > p.getPriceValue()) {
            return 1;
        }
        return 0;
    }

     /**
     * Check if the current Price object is greater than 
     * or equal to the Price object passed in.
     * 
     * @param p
     *      The Price object passed in to compare with the current Price
     * @return 
     *      Return true if the current Price object is greater than or equal to 
     *      the Price object passed in. If either the current Price object or 
     *      the Price object passed in is a "market" price, return false.
     */
    public boolean greaterOrEqual(Price p) {
        return !(this.isMarket() || p.isMarket() || 
                this.getPriceValue() < p.getPriceValue());
    }

    /**
     * Check if the current Price object is 
     * greater than the Price object passed in.
     * 
     * @param p
     *      The Price object passed in to compare with the current Price
     * @return 
     *      Return true if the current Price object is greater than 
     *      the Price object passed in. If either the current Price object 
     *      or the Price object passed in is a "market" price, return false.
     */
    public boolean greaterThan(Price p) {
        return !(this.isMarket() || p.isMarket() || 
                this.getPriceValue() <= p.getPriceValue());
    }

    /**
     * Check if the current Price object is less than 
     * or equal to the Price object passed in.
     * 
     * @param p
     *      The Price object passed in to compare with the current Price.
     * @return 
     *      Return true if the current Price object is less than or equal 
     *      to the Price object passed in. If either the current Price object 
     *      or the Price object passed in is a "market" price, return false.
     */
    public boolean lessOrEqual(Price p) {
        return !(this.isMarket() || p.isMarket() 
                || this.getPriceValue() > p.getPriceValue());
    }

    /**
     * Check if the current Price object is greater 
     * than the Price object passed in.
     * 
     * @param p
     *      The Price object passed in to compare with the current Price.
     * @return 
     *      Return true if the current Price object is greater than 
     *      the Price object passed in. If either the current Price object 
     *      or the Price object passed in is a "market" price, return false.
     */
    public boolean lessThan(Price p) {
        return !(this.isMarket() || p.isMarket() 
                || this.getPriceValue() >= p.getPriceValue());
    }

    /**
     * Check if the Price object passed in holds the same value 
     * as the current Price object
     * @param p
     *      The Price object passed in to compare with the current Price.
     * @return 
     *      Return true if the Price object passed in holds the same value 
     *      as the current Price object. If either the current Price object 
     *      or the Price object passed in is a "market" price, return false.
     */
    public boolean equals(Price p) {
        return !(this.isMarket() || p.isMarket() 
                || this.getPriceValue() != p.getPriceValue());
    }

    /**
     * Check if the Price is a market price.
     * 
     * @return 
     *      Return true if the Price is a market price, 
     *      return false if not.
     */
    public boolean isMarket() {
        return this.market;
    }

    /**
     * Check if the Price is negative.
     * 
     * @return 
     *      Return true if the Price is negative, return false if the Price 
     *      is zero or positive. If the Price is a "market" price, return false.
     */
    public boolean isNegative() {
        return (this.getPriceValue() < 0 && !this.isMarket());
    }

    /**
     * Return the String format of Price.
     * 
     * @return 
     *      - Positive value examples: $0.49, $0.75, $10.50, $10,000.50, 
     *        $2,500,000.00
     *      - Negative value examples: $-0.49, $-0.75, $-10.50, $-10,000.50, 
     *        $-2,500,000.00
     *      - Return the String "MKT" for market prices.
     */
    @Override
    public String toString() {
        if (this.isMarket()) {
            return "MKT";
        }
        return String.format("$%,.2f", (double) priceValue / 100.0);
    }
}
