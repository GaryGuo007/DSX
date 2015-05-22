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
package depaul.stockexchange.book;

import depaul.stockexchange.messages.FillMessage;
import depaul.stockexchange.tradable.Tradable;
import java.util.HashMap;

/**
 * An interface that defines the functionality needed to “execute” 
 * the actual trades between Tradable objects in this book side. 
 * 
 * @author jimliu
 */
public interface TradeProcessor {
    
    /**
     * This TradeProcessor method will be called when it has been determined 
     * that a Tradable (i.e., a Buy Order, a Sell QuoteSide, etc.) 
     * can trade against the content of the book.
     * 
     * @param trd
     *      A Tradable (i.e., a Buy Order, a Sell QuoteSide, etc.) 
     * @return 
     *      A HashMap<String, FillMessage> containing String trade identifiers 
     *      (the key) and a Fill Message object (the value).
     */
    public HashMap<String,FillMessage>doTrade(Tradable trd);
}
