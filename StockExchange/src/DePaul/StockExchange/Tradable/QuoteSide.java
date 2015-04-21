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
 *
 * @author jimliu
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

    private void setId(String userName, String productSymbol) {
        this.id = String.format("%s%s%s", userName, 
                productSymbol, System.nanoTime());
    }

    public void setPrice(Price price) throws InvalidTradableValue {
        if (price.isMarket()) {
            throw new InvalidTradableValue("Invalid price, "
                    + "Quotes can only use limit prices.");
        }
        this.price = price;
    }

    public void setUser(String userName) throws InvalidTradableValue {
        if (userName == null || "".equals(userName)) {
            throw new InvalidTradableValue("Invalid user name: " + userName); 
        }
        this.user = user;
    }

    private void setProduct(String productSymbol) throws InvalidTradableValue {
        if (productSymbol == null || "".equals(productSymbol)) {
            throw new InvalidTradableValue("Invalid product symbol: " 
                    + productSymbol); 
        }
        this.product = productSymbol;
    }

    public void setOriginalVolume(int originalVolume)
            throws InvalidTradableValue {
        if (originalVolume <= 0) {
            throw new InvalidTradableValue("Invalid " + this.side 
                    + "-Side Volume: " + originalVolume); 
        }
        this.originalVolume = originalVolume;
    }

    public void setSide(String side) throws InvalidTradableValue {
        if (!"SELL".equals(side) && !"BUY".equals(side)) {
            throw new InvalidTradableValue("Invalid Side: " + side); 
        }
        this.side = side;
    }

    @Override
    public String getProduct() {
        return this.product;
    }

    @Override
    public Price getPrice() {
        return this.price;
    }

    @Override
    public int getOriginalVolume() {
        return this.originalVolume;
    }

    @Override
    public int getRemainingVolume() {
        return this.remainingVolume;
    }

    @Override
    public int getCancelledVolume() {
        return this.cancelledVolume;
    }

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

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getSide() {
        return this.side;
    }

    @Override
    public boolean isQuote() {
        return true;
    }

    @Override
    public String getId() {
        return this.id;
    }


    @Override
    public String toString() {
        return String.format("%sx%s (Original Vol: %s, CXL'd Vol: %s) [%s]", 
                this.getPrice(), this.getRemainingVolume(), 
                this.getOriginalVolume(), 
                this.getCancelledVolume(), this.getId());
    }
}
