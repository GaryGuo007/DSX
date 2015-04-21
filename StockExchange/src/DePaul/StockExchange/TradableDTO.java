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

