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
 * A Quote represents the prices and volumes of certain stock that 
 * the user is willing to BUY or SELL shares.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class Quote {

    private String user;
    private String product;
    private QuoteSide buyQuoteSide;
    private QuoteSide sellQuoteSide;

    /**
     * Creates an Quote object. 
     * The userName and productSymbol parameters should be used to set the corresponding Quote data members. 
     * The buyPrice and buyVolume should be used (along with the username, 
     * productSymbol and a BUY side indicator) to create the buy-side QuoteSide object. 
     * The sellPrice and sellVolume should be used (along with the username, 
     * productSymbol and a SELL side indicator) to create the sell-side QuoteSide object.
     * 
     * @param userName
     * 		the userName of the corresponding Quote data members
     * @param productSymbol
     * 		the productSymbol of the corresponding Quote data members
     * @param buyPrice
     * 		the price of buy-side QuoteSide
     * @param buyVolume
     * 		the volume of buy-side QuoteSide
     * @param sellPrice
     * 		the price of sell-side QuoteSide
     * @param sellVolume
     * 		the volume of sell-side QuoteSide
     * @throws InvalidTradableValue
     */
    public Quote(String userName, String productSymbol, Price buyPrice,
                    int buyVolume, Price sellPrice, int sellVolume) 
            throws InvalidTradableValue {
        this.setUserName(userName);
        this.setProduct(productSymbol);
        this.setBuyQuoteSide(userName, productSymbol, buyPrice, buyVolume);
        this.setSellQuoteSide(userName, productSymbol, sellPrice, sellVolume);
    }
    
    /**
     * Creates an buy-side Quote object. 
     */
    private void setBuyQuoteSide(String userName, String productSymbol,
                    Price sidePrice, int volume) throws InvalidTradableValue {
        this.buyQuoteSide = new QuoteSide(userName, productSymbol, sidePrice,
                                volume, "BUY");
    }

    /**
     * Creates an sell-side Quote object.
     */
    private void setSellQuoteSide(String userName, String productSymbol,
                    Price sidePrice, int volume) throws InvalidTradableValue {
        this.sellQuoteSide = new QuoteSide(userName, productSymbol, sidePrice,
                                volume, "SELL");
    }

    /**
     * Sets the Quote's user name.
     */
    private void setUserName(String userName) {
        this.user = userName;
    }

    /**
     * Returns the Quote�s user name.
     */
    public String getUserName() {
        return this.user;
    }

    /**
     * Sets the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the Quote works with.
     */
    private void setProduct(String productSymbol) {
        this.product = productSymbol;
    }

    /**
     * Returns the Quote�s product symbol.
     */
    public String getProduct() {
        return this.product;
    }
    
    /**
     * Returns the buy-side QuoteSide object.
     */
    private QuoteSide getBuyQuoteSide() {
        return this.buyQuoteSide;
    }
    
    /**
     * Returns the sell-side QuoteSide object.
     */
    private QuoteSide getSellQuoteSide() {
        return this.sellQuoteSide;
    }

    /**
     * Returns a copy of the BUY or SELL QuoteSide object, 
     * depending upon which side is specified in the �sideIn� parameter.
     * @param sideIn
     * 		"BUY" or "SELL" side
     * @return
     * 		If sideIn is "BUY", return a buy-side QuoteSide object, and vice verse.
     * @exception InvalidTradableValue
     *          If the side is neither "BUY" nor "SELL", throws an exception.
     */
    public QuoteSide getQuoteSide(String sideIn) throws InvalidTradableValue  {
        if (!"SELL".equals(sideIn) && !"BUY".equals(sideIn)) {
            throw new InvalidTradableValue("Invalid Side: " + sideIn); 
        }
        if ("SELL".equals(sideIn)) {
            return new QuoteSide(this.getSellQuoteSide());
        }
        return new QuoteSide(this.getBuyQuoteSide());
    }

    /**
     * Generates a String representing the key values in this quote. 
     * Recommended String value generated by your toString:
     * USER2 quote: GE $21.56 x 100 (Original Vol: 100, CXL'd Vol: 0) [USER2GE1756426242224751]
     *  - $21.62 x 100 (Original Vol: 100, CXL'd Vol: 0) [USER2GE1756426242242844]
     */
    @Override
    public String toString() {
        return String.format("%s quote: %s %s - %s", this.getUserName(), 
                this.getProduct(), this.getBuyQuoteSide(), this.getSellQuoteSide());
    }
}
