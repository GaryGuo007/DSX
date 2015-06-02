package depaul.stockexchange.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.price.InvalidPriceOperation;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.price.PriceFactory;

/**
 * The Position class (this class can go in the same package as “User”
 * interface) is used to hold an individual users profit and loss information,
 * including how much they have spent buying stock, how much they gained or lost
 * selling stock, and the value of the stock they currently own.
 * 
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author Junmin Liu
 */
public final class Position {
	private HashMap<String, Integer> holdings = new HashMap<String, Integer>();
	/*
	 * This will keep a running balance between the “money out” for stock
	 * purchases, and the “money in” for stock sales. This should be initialized
	 * to a (value) price object with a value of “$0.00”.
	 */
	private Price accountCosts = new Price(0);
	/*
	 * A HashMap<String, Price> to store the “last sales” of the stocks this
	 * user owns. Last sales indicate the current value of the stocks they own.
	 * Initially this will be empty.
	 */
	private HashMap<String, Price> lastSales = new HashMap<>();
	
	public Position() { };

//	private volatile static Position instance;
//
//	public static Position getInstance() {
//		if (instance == null) {
//			synchronized (Position.class) {
//				if (instance == null) {
//					instance = new Position();
//				}
//			}
//		}
//		return instance;
//	}

	/*
	 * This method will update the holdings list and the account costs when some
	 * market activity occurs.
	 */

	// logic bugs here
	public void updatePosition(String product, Price price, BookSide side,
			int volume) throws InvalidPriceOperation {
		int adjustedVolume = 0;
		Price totalPrice;
		if (side.equals(BookSide.BUY)) {
			adjustedVolume = volume;
		} else if (side.equals(BookSide.SELL)) {
			adjustedVolume = volume * (-1);
		}

		if (!holdings.containsKey(product)) {
			holdings.put(product, adjustedVolume);
		} else {
			if (holdings.get(product) + adjustedVolume == 0) {
				holdings.remove(product);
			} else {
				holdings.put(product, holdings.get(product) + adjustedVolume);
			}
		}
		
		totalPrice = price.multiply(volume);

		if (side.equals(BookSide.BUY)) {
			accountCosts = accountCosts.subtract(totalPrice);
		} else if (side.equals(BookSide.SELL)) {
			accountCosts = accountCosts.add(totalPrice);
		}
	}

	/*
	 * This method should insert the last sale for the specified stock into the
	 * “last sales” HashMap (product parameter is the key, Price parameter is
	 * the value).
	 */
	public void updateLastSale(String product, Price price) {
		lastSales.put(product, price);
	}

	/*
	 * This method will return the volume of the specified stock this user owns.
	 */
	public int getStockPositionVolume(String product) {
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
			throws InvalidPriceOperation {
		Price priceIn;
		if (!holdings.containsKey(product)) {
			priceIn = PriceFactory.makeLimitPrice(0);
			return priceIn;
		} else if (lastSales.get(product) == null) {
			priceIn = PriceFactory.makeLimitPrice(0);
			return priceIn;
		}

		return (lastSales.get(product)).multiply(holdings.get(product));

		// return this.getStockPositionValue(product);

	}

	/*
	 * This method simply returns the “account costs” data member.
	 */
	public Price getAccountCosts() {
		return accountCosts;
	}

	/*
	 * This method should return the total current value of all stocks this user
	 * owns.
	 */
	public Price getAllStockValue() throws InvalidPriceOperation {
		Price sum = new Price(0);
		for (String p : holdings.keySet()) {
			sum.add(getStockPositionValue(p));
		}
		return sum;
	}

	/*
	 * This method should return the total current value of all stocks this user
	 * owns PLUS the account costs.
	 */
	public Price getNetAccountValue() throws InvalidPriceOperation {
		// Price sum = null;
		// for(String p: holdings.keySet()){
		// sum.add(getStockPositionValue(p));
		// }
		return getAccountCosts().add(getAllStockValue());
	}
}
