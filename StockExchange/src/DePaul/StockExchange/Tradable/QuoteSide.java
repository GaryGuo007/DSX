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
                    int originalVolume, String side) throws InvalidVolumeValueException {
        this.setId(userName, productSymbol);
        this.setPrice(sidePrice);
        this.setUser(userName);
        this.setProduct(productSymbol);
        this.setOriginalVolume(originalVolume);
        this.setRemainingVolume(originalVolume);
        this.setSide(side);
    }

    public QuoteSide(QuoteSide qs) throws InvalidVolumeValueException {
        this.setId(qs.getUser(), qs.getProduct());
        this.setPrice(qs.getPrice());
        this.setUser(qs.getUser());
        this.setProduct(qs.getProduct());
        this.setOriginalVolume(qs.getOriginalVolume());
        this.setRemainingVolume(qs.getOriginalVolume());
        this.setSide(qs.getSide());
    }

    private void setId(String userName, String productSymbol) {
        this.id = String.format("%s%s%s", userName, productSymbol, System.nanoTime());
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private void setProduct(String productSymbol) {
        this.product = productSymbol;
    }

    public void setOriginalVolume(int originalVolume) {
        this.originalVolume = originalVolume;
    }

    public void setSide(String side) {
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
        return String.format("%sx%s (Original Vol: %s, CXL'd Vol: %s) [%s]", 
                this.getPrice(), this.getRemainingVolume(), this.getOriginalVolume(), 
                this.getCancelledVolume(), this.getId());
    }
}
