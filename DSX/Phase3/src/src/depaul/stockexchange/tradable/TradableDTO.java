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
 * The TradableDTO class is based upon the�Data Transfer Object design pattern. 
 * The TradableDTO will act as a data holder that holds selected data values 
 * from a Tradable object. 
 * 
 * @author      Junmin Liu
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 */
public class TradableDTO {

    public String product;
    public Price price;
    public int originalVolume;
    public int remainingVolume;
    public int cancelledVolume;
    public String user;
    public BookSide side;
    public boolean isQuote;
    public String id;
    
    /**
     * @param productSymbol
     * 		the product (i.e., IBM, GOOG, AAPL, etc.) that the Tradable works with
     * @param p
     * 		the price of the Tradable
     * @param origVolume
     * 		the original volume (i.e., the original quantity) of the Tradable
     * @param remainVolume
     * 		the remaining volume (i.e., the remaining quantity) of the Tradable
     * @param cancelVolume
     * 		the cancelled volume (i.e., the cancelled quantity) of the Tradable
     * @param userName
     * 		the User id associated with the Tradable
     * @param s
     * 		the side (BUY/SELL) of the Tradable.
     * @param isQ
     * 		set to true if the Tradable is part of a Quote, false if not 
     * 		(i.e., false if it's part of an order)
     * @param Id
     * 		the Tradable id the value each tradable is given once it is received by the system
     */
    public TradableDTO(String productSymbol, Price p, int origVolume, int remainVolume,
            int cancelVolume, String userName, BookSide s, boolean isQ, String Id) {
        this.product = productSymbol;
        this.price = p;
        this.originalVolume = origVolume;
        this.remainingVolume = remainVolume;
        this.cancelledVolume = cancelVolume;
        this.user = userName;
        this.side = s;
        this.isQuote = isQ;
        this.id = Id;
    }

    public TradableDTO(Tradable t) throws Exception {
        this.product = t.getProduct();
        this.price = t.getPrice();
        this.originalVolume = t.getOriginalVolume();
        this.remainingVolume = t.getRemainingVolume();
        this.cancelledVolume = t.getCancelledVolume();
        this.user = t.getUser();
        this.side = t.getSide();
        this.isQuote = t.isQuote();
        this.id = t.getId();
    }

    /**
     * Generates a recommended String value representing the key values. 
     * Product: GE, Price: $21.59, OriginalVolume: 250, RemainingVolume: 250, 
     * CancelledVolume: 0, User: USER1, Side: BUY, IsQuote: false, 
     * Id: USER1GE$21.591243459303459784
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Product: %s, Price: %s", product, price.toString()));
        sb.append(String.format(", OriginalVolume: %s, RemainingVolume: %s", originalVolume, remainingVolume));
        sb.append(String.format(", CancelledVolume: %s, User: %s", cancelledVolume, user));
        sb.append(String.format(", Side: %s, IsQuote: %b, Id: %s", side, isQuote, id));
        return sb.toString();
    }
}

