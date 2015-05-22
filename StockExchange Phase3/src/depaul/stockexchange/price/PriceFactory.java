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
package depaul.stockexchange.price;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;

/**
 * A factory help to create price object. Price objects should not be created by
 * classes across the entire application. This would couple other classes to the
 * specifics of Price object creation. Instead, a globally accessible Factory
 * called PriceFactory should perform the creation of Price objects (i.e., using
 * the Factory design pattern).
 *
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author Junmin Liu
 */
public final class PriceFactory {
    /*
     * The cent change to dollar by multiply it.
     */

    final static double MULTIPLIER = 100.0;
    final static double ROUNDING = 0.000001;
    /*
     * FlyWeight Pattern: Share pool for same price value.
     */
    private static HashMap<Long, Price> prices = new HashMap<Long, Price>();
    private static Price marketPrice = new Price();

    /**
     * Creates a (limit) price object representing the value held in the
     * provided String value.
     *
     * @param value
     * @throws InvalidPriceOperationException
     */
    public static Price makeLimitPrice(String value)
            throws InvalidPriceOperation {
        value = value.replaceAll("[$,]", "");
        double d = 0;
        if (isNumeric(value)) {
            d = Double.parseDouble(value);
        } else {
            throw new InvalidPriceOperation(
                    "The Price value must be a numeric format");
        }

        return PriceFactory.makeLimitPrice((long) (d * MULTIPLIER + ROUNDING));
    }

    /**
     * Creates a (limit) price object representing the value held in the
     * provided long value. This long value is the price in cents. A value of
     * 1499 represents a price of $14.99
     *
     * @param value
     */
    public static Price makeLimitPrice(long value) {
        if (!prices.containsKey(value)) {
            Price p = new Price(value);
            prices.put(value, p);
            return p;
        } else {
            return prices.get(value);
        }
    }

    /**
     * Creates a market price object.
     *
     */
    public static Price makeMarketPrice() {
        return marketPrice;
    }

    /*
     * Check a string is a valid numeric format
     * 
     * @param str
     */
    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
