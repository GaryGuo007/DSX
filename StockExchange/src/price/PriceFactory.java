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
package price;
import DePaul.StockExchange.InvalidPriceOperation;
import java.util.Hashtable;

/**
 * A factory help to create price object.
 * Price objects should not be created by classes across the entire application. 
 * This would couple other classes to the specifics of Price object creation. 
 * Instead, a globally accessible Factory called PriceFactory should perform 
 * the creation of Price objects 
 * (i.e., using the Factory design pattern).
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class PriceFactory {

    private static final Hashtable<Long, Price> prices = new Hashtable<>();
    private static final Price marketPrice = new Price();
    /**
     * Creates a (limit) price object representing the 
     * value held in the provided String value.
     * 
     * @param value
     * 		Desired value of price in Sting format.
     * @return PriceFactory 
     * 		Return the price object representing the 
     * 		value held in the provided String value.
     * @throws InvalidPriceOperation
     * 		If either Price is a Market Price, or the Price passed in is null, 
     * 		throws InvalidPriceOperation.
     */
    public static Price makeLimitPrice(String value) throws InvalidPriceOperation {
        double d = 0;
        try
        {
            d = Double.parseDouble(value.replaceAll("[$,]", ""));
        }
        //if invalid value was entered
        catch(NumberFormatException ne)
        {
            throw new InvalidPriceOperation("InvalidPriceOperationException");
        }
        return PriceFactory.makeLimitPrice((long) (d * 100.0));
    }

    /**
     * Creates a (limit) price object representing the value held in the
     * provided long value. This long value is the price in cents. 
     * A value of 1499 represents a price of $14.99.
     * 
     * @param value
     * 		Desired value of price in long format.
     * 		This long value is the price in cents. 
     * 		A value of 1499 represents a price of $14.99.
     * @return p
     * 		Return the price object representing the value held 
     * 		in the provided long value.
     * @throws InvalidPriceOperation
     * 		If either Price is a Market Price, or the Price passed in is null, 
     * 		throws InvalidPriceOperation.
     */
    public static Price makeLimitPrice(long value) throws InvalidPriceOperation {
        Price p = prices.get(value);
        if (p == null) {  
            p = new Price(value);
            prices.put(value, p);
        } 
        return p;
    }

    /**
     * Creates a market price object
     * 
     * @return  marketPrice
     * 		Return the market price object.
     * @see     Price
     */
    public static Price makeMarketPrice() {
        return marketPrice;
    }
}

