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
package depaul.stockexchange.messages;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;

/**
 * The common part of message classes
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public abstract class MessageBase {
    
    /**
     * The String username of the user whose order or quote-side. 
     * Cannot be null or empty.
     */
    protected String user;
    
    /**
     * The string stock symbol that the order or quote-side was 
     * submitted for (“IBM”, “GE”, etc.). 
     * Cannot be null or empty.
     */
    protected String product;
    
    /**
     * The price specified in the order or quote-side. 
     * Cannot be null.
     */
    protected Price price;
    
    /**
     * The quantity of the order or quote-side. 
     * Cannot be negative.
     */
    protected int volume;
    
    /**
     * A text description of the order. 
     * Cannot be null.
     */
    protected String details;

    /**
     * The side (BUY/SELL) of the order or quote-side. 
     * Must be a valid side.
     */
    protected BookSide side;
    
    /**
     * The String identifier of the order or quote-side. 
     * Cannot be null.
     */
    public String id;
    
    
    public MessageBase(String user, String product, Price price, int volume, 
            String details, BookSide side, String id) 
                throws DataValidationException {
        this.setUser(user);
        this.setProduct(product);
        this.setPrice(price);
        this.setVolume(volume);
        this.setDetails(details);
        this.setSide(side);
        this.setId(id);
    }
    
    /**
     * The String username of the user whose order or quote-side.
     * @return username
     */
    public String getUser() {
        return user;
    }
    
    /**
     * Set the username of the user
     * @param username 
     *      The String username of the user whose order or quote-side. 
     * @throws DataValidationException
     *      if username passed in is null or empty
     */
    protected void setUser(String username) 
            throws DataValidationException {
        if (Utils.isNullOrEmpty(username)) {
            throw new DataValidationException("The username passed in "
                    + "can't be null or empty.");
        }
        this.user = username;
    }
    
    /**
     * The string stock symbol that the order or quote-side was 
     * submitted for (“IBM”, “GE”, etc.).
     * @return 
     *      The string stock symbol
     */
    public String getProduct() {
        return this.product;
    }
    
    /**
     * Set the stock symbol
     * @param product
     *      The string stock symbol that the order or quote-side was 
     *      submitted for (“IBM”, “GE”, etc.).
     * @throws DataValidationException 
     *      If the product passed in is null or empty
     */
    protected void setProduct(String product) 
            throws DataValidationException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("The product passed in "
                    + "can't be null or empty.");
        }
        this.product = product;
    }
    
    /**
     * The price specified in the order or quote-side. 
     * @return 
     *      The price
     */
    public Price getPrice() {
        return this.price;
    }
    
    /**
     * Set the price of order or quote-side
     * @param price
     *      The price specified in the order or quote-side. 
     * @throws DataValidationException 
     *      If the price passed in is null
     */
    protected void setPrice(Price price) 
            throws DataValidationException {
        if (price == null) {
            throw new DataValidationException("The price passed in "
                    + "can't be null.");
        }
        this.price = price;
    }
    
    /**
     * The quantity of the order or quote-side
     * @return 
     *      The quantity
     */
    public int getVolume() {
        return this.volume;
    }
    
    /**
     * Set the quantity of the order or quote-side
     * @param volume 
     *      The quantity of the order or quote-side
     * @throws DataValidationException 
     *      If the volume passed in is negative
     */
    public void setVolume(int volume) 
            throws DataValidationException {
        if (volume < 0) {
            throw new DataValidationException("The volume passed in "
                    + "can't be negative: " + volume);
        }
        this.volume = volume;
    }
    
    /**
     * A text description of the order.
     * @return 
     *      The text description
     */
    public String getDetails() {
        return this.details;
    }
    
    /**
     * Set the text description of the 
     * @param details
     *      A text description of the order.
     * @throws DataValidationException 
     *      If the details passed in is null
     */
    public void setDetails(String details) 
            throws DataValidationException {
        if (details == null) {
            throw new DataValidationException("The details passed in "
                    + "can't be null.");
        }
        this.details = details;
    }
    
    /**
     * The side (BUY/SELL) of the order or quote-side. 
     * @return 
     *      The side
     */
    public BookSide getSide() {
        return this.side;
    }
    
    /**
     * Set the side (BUY/SELL) of the order or quote-side. 
     * @param side 
     *      The side (BUY/SELL) of the order or quote-side.
     */
    public void setSide(BookSide side) {
        this.side = side;
    }
    
    /**
     * The String identifier of the order or quote-side.
     * @return 
     *      The id
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Set the id
     * @param id
     *      The String identifier of the order or quote-side.
     * @throws DataValidationException 
     *      If the id passed in is null
     */
    protected void setId(String id) throws DataValidationException {
        if (id == null) {
            throw new DataValidationException("The id passed in "
                    + "can't be null.");
        }
        this.id = id;
    }
}
