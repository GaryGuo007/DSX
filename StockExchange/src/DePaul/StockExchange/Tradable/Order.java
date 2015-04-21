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
    @todo: check the paremeter
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
    
    void setId(String userName, String productSymbol, Price orderPrice) {
        this.id = String.format("%s%s%s%s", userName, productSymbol, 
                orderPrice, System.nanoTime());
    }
    
    void setPrice(Price orderPrice) {
        this.price = orderPrice;
    }
    
    void setUser(String userName) throws InvalidTradableValue {
        if (userName == null || "".equals(userName)) {
            throw new InvalidTradableValue("Invalid user name: " + userName); 
        }
        this.user = userName;
    }
    
    void setProduct(String productSymbol) throws InvalidTradableValue {
        if (productSymbol == null || "".equals(productSymbol)) {
            throw new InvalidTradableValue("Invalid product symbol: " 
                    + productSymbol); 
        }
        this.product = productSymbol;
    }
    
    void setOriginalVolume(int originalVolume) 
            throws InvalidTradableValue {
        if (originalVolume <= 0) {
            throw new InvalidTradableValue("Invalid Order Volume: " 
                    + originalVolume); 
        }
        this.originalVolume = originalVolume;
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
        return false;
    }

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