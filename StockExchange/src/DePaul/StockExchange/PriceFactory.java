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
import java.util.Hashtable;

/**
 *
 * @author jimliu
 */
public abstract class PriceFactory {

    private static Hashtable<Long, Price> prices = new Hashtable<Long, Price>();
    private static Price marketPrice = new Price();

    public static Price makeLimitPrice(String value) throws InvalidPriceOperationException {
        double d = 0;
        try
        {
            d = Double.parseDouble(value.replaceAll("[$,]", ""));
        }
        //if invalid value was entered
        catch(NumberFormatException ne)
        {
            throw new InvalidPriceOperationException("InvalidPriceOperationException");
        }
        return PriceFactory.makeLimitPrice((long) (d * 100.0));
    }

    public static Price makeLimitPrice(long value) throws InvalidPriceOperationException {
        Price p = prices.get(value);
        if (p == null) {  
            p = new Price(value);
            prices.put(value, p);
        } 
        return p;
    }

    public static Price makeMarketPrice() {
        return marketPrice;
    }
}

