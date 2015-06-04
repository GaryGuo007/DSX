package depaul.stockexchange.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.Utils;
import depaul.stockexchange.price.InvalidPriceOperation;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.price.PriceFactory;

/**
 * The Position class (this class can go in the same package as User interface)
 * is used to hold an individual users profit and loss information, including
 * how much they have spent buying stock, how much they gained or lost selling
 * stock, and the value of the stock they currently own.
 *
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author Junmin Liu
 */
public final class Position {

    private final HashMap<String, Integer> holdings = new HashMap<>();
    /*
     * This will keep a running balance between the money out for stock
     * purchases, and the money in for stock sales. This should be initialized
     * to a (value) price object with a value of $0.00.
     */
    private Price accountCosts = PriceFactory.makeLimitPrice(0);
    /*
     * A HashMap<String, Price> to store the last sales of the stocks this
     * user owns. Last sales indicate the current value of the stocks they own.
     * Initially this will be empty.
     */
    private final HashMap<String, Price> lastSales = new HashMap<>();

    Position() {
    }


    /*
     * This method will update the holdings list and the account costs when some
     * market activity occurs.
     */
    // logic bugs here
    public void updatePosition(String product, Price price, 
            BookSide side, int volume) 
            throws DataValidationException, InvalidPriceOperation {
        if (side == null) {
            throw new DataValidationException("Side can't be null.");
        }
        
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("Product can't be null or empty.");
        }
        
        if (volume < 0) {
            throw new DataValidationException("volume can't be negative.");
        }
        
        int adjustedVolume = 0;
        if (side.equals(BookSide.BUY)) {
            adjustedVolume = volume;
        } else if (side.equals(BookSide.SELL)) {
            adjustedVolume = -volume;
        }

        if (!holdings.containsKey(product)) {
            holdings.put(product, adjustedVolume);
        } else {
            int newVolume = holdings.get(product) + adjustedVolume;
            if (newVolume == 0) {
                holdings.remove(product);
            } else {
                holdings.put(product, newVolume);
            }
        }

        Price totalPrice = price.multiply(volume);

        if (side.equals(BookSide.BUY)) {
            accountCosts = accountCosts.subtract(totalPrice);
        } else if (side.equals(BookSide.SELL)) {
            accountCosts = accountCosts.add(totalPrice);
        }
    }

    /*
     * This method should insert the last sale for the specified stock into the
     * last sales HashMap (product parameter is the key, Price parameter is
     * the value).
     */
    public void updateLastSale(String product, Price price) 
            throws DataValidationException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("Product can't be null or empty.");
        }
        lastSales.put(product, price);
    }

    /*
     * This method will return the volume of the specified stock this user owns.
     */
    public int getStockPositionVolume(String product) throws DataValidationException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("Product can't be null or empty.");
        }
        if (!holdings.containsKey(product)) {
            return 0;
        }
        return holdings.get(product);
    }

    /*
     * This method will return a sorted ArrayList of Strings containing the
     * stock symbols this user owns.
     */
    public ArrayList<String> getHoldings() {
        ArrayList<String> h = new ArrayList<>(holdings.keySet());
        Collections.sort(h);
        //Collections.reverse(h); //Need this one? I'm not sure.
        return h;

    }

    /*
     * This method will return the current value of the stock symbol passed in
     * that is owned by the user.
     */
    public Price getStockPositionValue(String product)
            throws InvalidPriceOperation, DataValidationException {
        if (Utils.isNullOrEmpty(product)) {
            throw new DataValidationException("Product can't be null or empty.");
        }
        if (!holdings.containsKey(product)) {
            return PriceFactory.makeLimitPrice(0);
        }
        Price price = lastSales.get(product);
        int volume = holdings.get(product);
        if (price == null) {
            price = PriceFactory.makeLimitPrice(0);
        }

        return price.multiply(volume);
    }

    /*
     * This method simply returns the account costs data member.
     */
    public Price getAccountCosts() {
        return accountCosts;
    }

    /*
     * This method should return the total current value of all stocks this user
     * owns.
     */
    public Price getAllStockValue() throws InvalidPriceOperation, DataValidationException {
        Price sum = PriceFactory.makeLimitPrice(0);
        for (String p : holdings.keySet()) {
            sum.add(getStockPositionValue(p));
        }
        return sum;
    }

    /*
     * This method should return the total current value of all stocks this user
     * owns PLUS the account costs.
     */
    public Price getNetAccountValue() throws InvalidPriceOperation, DataValidationException {
        return getAccountCosts().add(getAllStockValue());
    }
}
