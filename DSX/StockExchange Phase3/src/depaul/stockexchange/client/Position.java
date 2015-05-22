package depaul.stockexchange.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import depaul.stockexchange.BookSide;
import depaul.stockexchange.price.Price;

public class Position {
	HashMap<String, Integer> holdings = new HashMap<>();
	/*
	 * This will keep a running balance between the “money out” for
	stock purchases, and the “money in” for stock sales. This should be initialized to a (value) price object with a value of
	“$0.00”.
	 */
	Price accountCosts;
	/*
	 * A HashMap<String, Price> to store the “last sales” of the stocks this user owns. Last sales indicate the current value of
	 * the stocks they own. Initially this will be empty.
	 */
	HashMap<String, Price> lastSales = new HashMap<>();
	
	/*
	 * This method will update the
holdings list and the account costs when some market activity occurs.
	 */
	public void updatePosition(String product, Price price, BookSide side, int volume){
		int  adjustedVolume;
		if(BookSide.BUY){
			 adjustedVolume = volume;
		 }
		 else if (BookSide.SELL){
			 adjustedVolume = -volume;
		 }
		
		
			 
		 if(!holdings.containsKey(product)){
			 holdings.put(product, adjustedVolume);
		 }
		 else{
			 holdings.put(volume, );
		 }
		 
		 
		 if(BookSide.BUY)
		 accountCosts = accountCosts.subtract(totalPrice);
		 else if (BookSide.SELL)
		 accountCosts = accountCosts.add(totalPrice);
		
	}
	
	/*
	 * This method should insert the last sale for the specified stock
into the “last sales” HashMap (product parameter is the key, Price parameter is the value).
	 */
	public void updateLastSale(String product, Price price){
			lastSales.put(product, price);
	}
	
	/*
	 * This method will return the volume of the specified stock this user
owns.
	 */
	public int getStockPositionVolume(String product){
		if(!holdings.containsKey(product))
		    return 0;
		return holdings.getStockPositionVolume(product).
	}
	/*
	 * This method will return a sorted ArrayList of Strings containing the stock
symbols this user owns.
	 */
	public ArrayList<String> getHoldings(){
		ArrayList<String> h = new ArrayList<>(holdings.keySet());
		Collections.sort(h);
		return h;
		
	}
	
	/*
	 * This method will return the current value of the stock symbol
passed in that is owned by the user.
	 */
	public Price getStockPositionValue(String product){
		Price priceIn;
		if(!holdings.containsKey(product)) 
			{
			priceIn = 0.00;
			return priceIn;
			}
		else if (lastSales.get(product)==null){
			lastSales.put(product, 0.00);
		}
		holdingsOfUser.
		return ;
		return this.getStockPositionValue(product);
	}
	/*
	 * This method simply returns the “account costs” data member.
	 */
	public Price getAccountCosts(){
		return this.getAccountCosts();
	}
	/*
	 * This method should return the total current value of all stocks this user owns.
	 */
	public Price getAllStockValue(){
		return this.getAllStockValue();
		
	}
	/*
	 * This method should return the total current value of all stocks this user owns PLUS
the account costs.
	 */
	public Price getNetAccountValue(){
		return this.getNetAccountValue();
		
	}

}
