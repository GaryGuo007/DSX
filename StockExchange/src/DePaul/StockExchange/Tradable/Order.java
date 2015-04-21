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

import DePaul.StockExchange.InvalidVolumeValueException;
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
            int originalVolume, String side) throws Exception {
        this.id = userName + productSymbol + 
                orderPrice.toString() + System.nanoTime();
        this.price = orderPrice;
        this.user = userName;
        this.product = productSymbol;
        this.originalVolume = originalVolume;
        this.remainingVolume = originalVolume;
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
    public void setCancelledVolume(int newCancelledVolume) throws InvalidVolumeValueException {
        if (newCancelledVolume < 0 || (newCancelledVolume + this.remainingVolume > this.originalVolume)) {
            throw new InvalidVolumeValueException("The value is negative, or the requested cancelled volume plus the current remaining volume exceeds the original volume."); 
        }
        this.cancelledVolume = newCancelledVolume;
    }

    @Override
    public void setRemainingVolume(int newRemainingVolume) throws InvalidVolumeValueException {
        if (newRemainingVolume < 0 || (newRemainingVolume + this.cancelledVolume > this.originalVolume)) {
            throw new InvalidVolumeValueException("he value is negative, or the requested remaining volume plus the current cancelled volume exceeds the original volume."); 
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
        return String.format("%s order: %s %s %s at %s (Original Vol: %s, CXL'd Vol: %s), ID: %s", 
                user, side, remainingVolume, product, price, originalVolume, cancelledVolume, id);
    }

}