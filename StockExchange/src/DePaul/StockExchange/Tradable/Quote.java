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
import DePaul.StockExchange.Tradable.QuoteSide;
import DePaul.StockExchange.Price.Price;

/**
 *
 * @author jimliu
 */
public class Quote {

    private String user;
    private String product;
    private QuoteSide buyQuoteSide;
    private QuoteSide sellQuoteSide;

    public Quote(String userName, String productSymbol, Price buyPrice,
                    int buyVolume, Price sellPrice, int sellVolume) 
            throws InvalidTradableValue {
        this.setUserName(userName);
        this.setProduct(productSymbol);
        this.setBuyQuoteSide(userName, productSymbol, buyPrice, buyVolume);
        this.setSellQuoteSide(userName, productSymbol, sellPrice, sellVolume);
    }

    private void setBuyQuoteSide(String userName, String productSymbol,
                    Price sidePrice, int volume) throws InvalidTradableValue {
        this.buyQuoteSide = new QuoteSide(userName, productSymbol, sidePrice,
                                volume, "BUY");
    }

    private void setSellQuoteSide(String userName, String productSymbol,
                    Price sidePrice, int volume) throws InvalidTradableValue {
        this.sellQuoteSide = new QuoteSide(userName, productSymbol, sidePrice,
                                volume, "SELL");
    }

    private void setUserName(String userName) {
        this.user = userName;
    }

    public String getUserName() {
        return this.user;
    }

    private void setProduct(String productSymbol) {
        this.product = productSymbol;
    }

    public String getProduct() {
        return this.product;
    }
    
    private QuoteSide getBuyQuoteSide() {
        return this.buyQuoteSide;
    }
    private QuoteSide getSellQuoteSide() {
        return this.sellQuoteSide;
    }

    public QuoteSide getQuoteSide(String sideIn) {
        if ("SELL".equals(sideIn)) {
            return new QuoteSide(this.getSellQuoteSide());
        }
        return new QuoteSide(this.getBuyQuoteSide());
    }

    @Override
    public String toString() {
        return String.format("%s quote: %s %s - %s", this.getUserName(), 
                this.getProduct(), this.getBuyQuoteSide(), this.getSellQuoteSide());
    }
}
