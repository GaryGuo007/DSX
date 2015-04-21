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
public enum BookSide {

    BUY("BUY"),
    SELL("SELL");
    private final String bookside;  

    private BookSide(String side) throws RuntimeException {
        if (side.equals("BUY") || side.equals("SELL")) {
            bookside = side;
        } else {
            throw new RuntimeException("RuntimeException: MarketState can only be BUY or SELL.");
        }
    }

    public String getBookSide() {
        return bookside;
    }
}