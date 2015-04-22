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
 * The Tradable interface will be used as the common generic type for anything 
 * representing a BUY or SELL request that can be traded in our system.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public interface Tradable {

	/*
	 * Returns the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the
	 * Tradable works with.
	 */
	String getProduct();

	/*
	 * Returns the price of the Tradable.
	 */
	Price getPrice();

	/*
	 * Returns the original volume (i.e., the original quantity) of the
	 * Tradable.
	 */
	int getOriginalVolume();

	/*
	 * Returns the remaining volume (i.e., the remaining quantity) of the
	 * Tradable..
	 */
	int getRemainingVolume();

	/*
	 * Returns the cancelled volume (i.e., the cancelled quantity) of the
	 * Tradable.
	 */
	int getCancelledVolume();

	/*
	 * Sets the Tradable's cancelled quantity to the value passed in. This
	 * method should throw an exception if the value is invalid (i.e., if the
	 * value is negative, or if the requested cancelled volume plus the current
	 * remaining volume exceeds the original volume).
	 */
	void setCancelledVolume(int newCancelledVolume) throws InvalidTradableValue;

	/*
	 * Sets the Tradable's remaining quantity to the value passed in. This
	 * method should throw an exception if the value is invalid (i.e., if the
	 * value is negative, or if the requested remaining volume plus the current
	 * cancelled volume exceeds the original volume).
	 */
	void setRemainingVolume(int newRemainingVolume) throws InvalidTradableValue;

	/*
	 * Returns the User id associated with the Tradable.
	 */
	String getUser();

	/*
	 * Returns the side ("BUY"/"SELL") of the Tradable.
	 */
	String getSide();

	/*
	 * Returns true if the Tradable is part of a Quote, returns false if not
	 * (i.e., false if it's part of an order)
	 */
	boolean isQuote();

	/*
	 * Returns the Tradable "id" the value each tradable is given once it is
	 * received by the system.
	 */
	String getId();
}
