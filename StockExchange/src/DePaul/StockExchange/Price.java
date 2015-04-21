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
package DePaul.StockExchange;

/**
 *
 * @author jimliu
 */
public final class Price implements Comparable<Price> {

    private final long priceValue;
    private final boolean market;

    // ## Construction

    // package-visible constructor
    // Creates a Price object representing the provided value. 
    // Remember, a long value passed in of 1499 represents $14.99, 
    // 12850 represents $128.50, etc.
    Price(long value) {
        this.priceValue = value;
        this.market = false;
    }

    // package-visible constructor
    // Creates a Price object representing Market price.
    Price() {
        this.priceValue = 0;
        this.market = true;
    }

    /** 
    * The add method of the Price object. 
    *   Add the value of the Price object passed in to the current Price object’s value  
    *   and return a new Price object representing that sum.
    * @param  p
    *   the Price object passed in to add with the current Price
    * @throws InvalidPriceOperationException 
    *   These methods should throw InvalidPriceOperation 
    *   if either Price is a Market Price, or the Price passed in is null.
    */ 
    public Price add(Price p) throws InvalidPriceOperationException {
        if (p == null || this.isMarket() || p.isMarket()) {
            throw new InvalidPriceOperationException("Price is a Market Price, or the Price passed in is null.");
        }

        long newValue = this.priceValue + p.priceValue;
        return PriceFactory.makeLimitPrice(newValue);
    }


    // Subtract the value of the Price object passed in from the current Price object’s value 
    // and return a new Price object representing that difference.
    public Price subtract(Price p) throws InvalidPriceOperationException {
        if (p == null || this.isMarket() || p.isMarket()) {
            throw new InvalidPriceOperationException("Price is a Market Price, or the Price passed in is null.");
        }

        long newValue = this.priceValue + p.priceValue;
        return PriceFactory.makeLimitPrice(newValue);
    }

    // Multiply the value passed in by the current Price object’s value 
    // and returns a new Price object representing that product.
    public Price multiply(int p) throws InvalidPriceOperationException {
        if (this.isMarket()) {
            throw new InvalidPriceOperationException("Price is a Market Price, or the Price passed in is null.");
        }
        long newValue = this.priceValue * p;
        return PriceFactory.makeLimitPrice(newValue);
    }

    @Override
    // Standard compareTo logic.
    public int compareTo(Price p) {
        if (this.priceValue < p.priceValue) {
            return -1;
        }
        if (this.priceValue > p.priceValue) {
            return 1;
        }
        return 0;
    }

    // Return true if the current Price object is greater than 
    // or equal to the Price object passed in.
    // If either the current Price object or the Price object passed in is a “market” price, return false.
    public boolean greaterOrEqual(Price p) {
        return !(this.isMarket() || p.isMarket() || this.priceValue < p.priceValue);
    }

    // Return true if the current Price object is greater than the Price object passed in.
    // If either the current Price object or the Price object passed in is a “market” price, return false.
    public boolean greaterThan(Price p) {
        return !(this.isMarket() || p.isMarket() || this.priceValue <= p.priceValue);
    }

    public boolean lessOrEqual(Price p) {
        return !(this.isMarket() || p.isMarket() || this.priceValue > p.priceValue);
    }

    public boolean lessThan(Price p) {
        return !(this.isMarket() || p.isMarket() || this.priceValue >= p.priceValue);
    }

    public boolean equals(Price p) {
        return !(this.isMarket() || p.isMarket() || this.priceValue != p.priceValue);
    }

    public boolean isMarket() {
        return this.market;
    }

    public boolean isNegative() {
        return (this.priceValue < 0 && !this.isMarket());
    }

    @Override
    public String toString() {
        if (this.isMarket()) {
            return "MKT";
        }
        return String.format("$%,.2f", (double) priceValue / 100.0);
    }
}
