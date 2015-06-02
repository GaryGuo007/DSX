package depaul.stockexchange.client;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
public class TradableUserData {
	 /**
     * The String username of the user whose order or quote-side. 
     * Cannot be null or empty.
     */
    protected String userName;
    
    /**
     * The string stock symbol that the order or quote-side was 
     * submitted for 
     * Cannot be null or empty.
     */
    protected String stockSymbol;
    
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
    
	
	public TradableUserData(String userName, String stockSymbol, BookSide side, String id)
		throws DataValidationException {
	        this.setUserName(userName);
	        this.setStockSymbol(stockSymbol);
	        this.setSide(side);
	        this.setId(id);
	    }
	
	
		  /**
	     * The String username of the user whose order or quote-side.
	     * @return userName
	     */
	    public String getUserName() {
	        return userName;
	    }
	    
	    /**
	     * The string stock symbol that the order or quote-side was 
	     * submitted for (â€œIBMâ€�, â€œGEâ€�, etc.).
	     * @return 
	     *      The string stock symbol
	     */
	    public String getStockSymbol() {
	        return stockSymbol;
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
	     * The String identifier of the order or quote-side.
	     * @return 
	     *      The id
	     */
	    public String getId() {
	        return this.id;
	    }
	    

	    /**
	     * Set the username of the user
	     * @param username 
	     *      The String username of the user whose order or quote-side. 
	     * @throws DataValidationException
	     *      if username passed in is null or empty
	     */
	private void setUserName(String userName) throws DataValidationException {
        if (Utils.isNullOrEmpty(userName)) {
            throw new DataValidationException("The username passed in "
                    + "can't be null or empty.");
        }
        this.userName = userName;
    }
	 /**
     * Set the stock symbol
     * @param product
     *      The string stock symbol that the order or quote-side was 
     *      submitted for (â€œIBMâ€�, â€œGEâ€�, etc.).
     * @throws DataValidationException 
     *      If the product passed in is null or empty
     */
	private void setStockSymbol(String stockSymbol) throws DataValidationException {
        if (Utils.isNullOrEmpty(stockSymbol)) {
            throw new DataValidationException("The product passed in "
                    + "can't be null or empty.");
        }
        this.stockSymbol = stockSymbol;
    }
    
	 /**
     * Set the side (BUY/SELL) of the order or quote-side. 
     * @param side 
     *      The side (BUY/SELL) of the order or quote-side.
     */
	private void setSide(BookSide side) {
		this.side = side;
    }

	
	/**
     * Set the id
     * @param id
     *      The String identifier of the order or quote-side.
     * @throws DataValidationException 
     *      If the id passed in is null
     */
	private void setId(String id) throws DataValidationException {
		if (id == null) {
            throw new DataValidationException("The id passed in " + "can't be null.");
        }
        this.id = id;
    }
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("User %s,", userName));
        sb.append(String.format(", %s", side));
        sb.append(String.format(" %s", stockSymbol));
        sb.append(String.format("(%s)", id));
        return sb.toString();	
    }
	
}
