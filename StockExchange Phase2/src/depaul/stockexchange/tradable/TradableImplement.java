package depaul.stockexchange.tradable;

import depaul.stockexchange.InvalidTradableValue;
import depaul.stockexchange.price.Price;

//TradableImplement


/**
 * An abstract class that helps to implement the common parts of Order class 
 * and QuoteSide class.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */

public abstract class TradableImplement implements Tradable {
    protected String user;
    protected String product;
    protected String id;
    protected String side;
    protected Price price;
    protected int originalVolume;
    protected int remainingVolume;
    protected int cancelledVolume;

    

    /**
     * Set the tradable's Price.
     * 
     * @param price
     * 		the tradable price (e.g. $257.09)
     */
    protected void setPrice(Price price) throws InvalidTradableValue {
        if (price == null) {
            throw new InvalidTradableValue("The price passed in can't be null;"); 
        }
        this.price = price;
    }
    
    /**
     * Sets the Tradable's user name.
     * 
     * @param userName
     * 		the user name (e.g. �REX�)
     * @throws InvalidTradableValue
     * 		If the user name is either null or an empty string, throws an exception. 
     */
    protected void setUser(String userName) throws InvalidTradableValue {
        if (userName == null || "".equals(userName)) {
            throw new InvalidTradableValue("Invalid user name: " + userName); 
        }
        this.user = userName;
    }
    
    /**
     * Sets the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the Order works with.
     * 
     * @param productSymbol
     * 		the product (stock) symbol (e.g. �AMZN�)
     * @throws InvalidTradableValue
     * 		If the product symbol is either null or an empty string, throws an exception. 
     */
    protected void setProduct(String productSymbol) throws InvalidTradableValue {
        if (productSymbol == null || "".equals(productSymbol)) {
            throw new InvalidTradableValue("Invalid product symbol: " 
                    + productSymbol); 
        }
        this.product = productSymbol;
    }
    
    /**
     * Sets the side ("BUY"/"SELL").
     * 
     * @param side
     * 		"BUY" side or "SELL" side
     * @throws InvalidTradableValue
     * 		If the side is neither "BUY" nor "SELL", throws an exception.
     */
    protected void setSide(String side) throws InvalidTradableValue {
        if (!"SELL".equals(side) && !"BUY".equals(side)) {
            throw new InvalidTradableValue("Invalid Side: " + side); 
        }
        this.side = side;
    }
    
    /**
     * Sets the original volume (i.e., the original quantity) of the Order.
     * 
     * @param originalVolume
     * 		the original volume (i.e., the original quantity) of the Order
     * @throws InvalidTradableValue
     * 		If the original volume is set as zero or negative number, throws an exception.
     */
    protected void setOriginalVolume(int originalVolume) 
            throws InvalidTradableValue {
        if (originalVolume <= 0) {
            throw new InvalidTradableValue("Invalid Order Volume: " 
                    + originalVolume); 
        }
        this.originalVolume = originalVolume;
    }
    
    /**
     * The id is generated by other parameters, 
     * this method is used to generate id string.
     * This method should be overrided by QuoteSide class and Order class.
     */
    protected abstract void buildId();
    
    /**
     * Returns the product symbol (i.e., IBM, GOOG, AAPL, etc.) that the Tradable works with.
     */
    @Override
    public String getProduct() {
        return this.product;
    }

    /**
     * Returns the price of the Tradable.
     */
    @Override
    public Price getPrice() {
        return this.price;
    }

    /**
     * Returns the original volume (i.e., the original quantity) of the Tradable.
     */
    @Override
    public int getOriginalVolume() {
        return this.originalVolume;
    }

    /**
     * Returns the remaining volume (i.e., the remaining quantity) of the Tradable.
     */
    @Override
    public int getRemainingVolume() {
        return this.remainingVolume;
    }
    
    /**
     * Returns the cancelled volume (i.e., the cancelled quantity) of the Tradable.
     */
    @Override
    public int getCancelledVolume() {
        return this.cancelledVolume;
    }
    
    /**
     * Sets the Tradable's cancelled quantity to the value passed in. 
     * This method should throw an exception if the value is invalid 
     * (i.e., if the value is negative, or if the requested cancelled 
     * volume plus the current remaining volume exceeds the original volume).
     * 
     * @param newCancelledVolume
     * 		A new Tradable's cancelled quantity to the value passed in. 
     * @throws InvalidTradableValue
     * 		If the value is invalid (i.e., if the value is negative, 
     * 		or if the requested cancelled volume plus the current remaining volume 
     * 		exceeds the original volume), throw an exception.
     */
    @Override
    public void setCancelledVolume(int newCancelledVolume) throws InvalidTradableValue {
        if (newCancelledVolume < 0) {
            throw new InvalidTradableValue("Invalid Cancelled Volume: " + newCancelledVolume);
        }
        if (newCancelledVolume + this.remainingVolume > this.originalVolume) {
            String errorMessage = String.format("Requested new Cancelled Volume "
                    + "(%s) plus the Remaining Volume (%s) exceeds the "
                    + "tradable's Original Volume (%s)"
                    , newCancelledVolume, this.remainingVolume, this.originalVolume);
            throw new InvalidTradableValue(errorMessage);
        }
        this.cancelledVolume = newCancelledVolume;
    }

    /**
     * Sets the Tradable�s remaining quantity to the value passed in. 
     * This method should throw an exception if the value is invalid 
     * (i.e., if the value is negative, or if the requested remaining 
     * volume plus the current cancelled volume exceeds the original volume).
     * 
     * @param newRemainingVolume
     * 		A new Tradable�s remaining quantity to the value passed in.
     * @throws InvalidTradableValue
     * 		If the value is invalid (i.e., if the value is negative, 
     * 		or if the requested cancelled volume plus the current remaining volume 
     * 		exceeds the original volume), throw an exception.
     */
    @Override
    public void setRemainingVolume(int newRemainingVolume) 
            throws InvalidTradableValue {
        if (newRemainingVolume < 0) {
            throw new InvalidTradableValue("Invalid Remaining Volume: " 
                    + newRemainingVolume);
        }
        if (newRemainingVolume + this.cancelledVolume > this.originalVolume) {
            String errorMessage = String.format("Requested new Remaining Volume "
                    + "(%s) plus the Cancelled Volume (%s) exceeds the "
                    + "tradable's Original Volume (%s)"
                    , newRemainingVolume, this.cancelledVolume, this.originalVolume);
            throw new InvalidTradableValue(errorMessage);
        }
        this.remainingVolume = newRemainingVolume;
    }

    /**
     * Returns the User id associated with the Tradable.
     */
    @Override
    public String getUser() {
        return this.user;
    }
    
    /**
     * Returns the side ("BUY"/"SELL") of the Tradable.
     */
    @Override
    public String getSide() {
        return this.side;
    }

    /**
     * Returns true if the Tradable is part of a Quote, 
     * returns false if not (i.e., false if it�s part of an order)
     */
    @Override
    public abstract boolean isQuote();
    
    /**
     * Returns the Tradable �id� � the value each tradable is given once it is received by the system.
     * @return 
     * @return  
     */
    @Override
    public String getId() {
        return this.id;
    }

}
