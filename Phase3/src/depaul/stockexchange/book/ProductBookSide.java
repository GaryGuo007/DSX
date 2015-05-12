package depaul.stockexchange.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.scene.Parent;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.*;
import depaul.stockexchange.publishers.*;
import depaul.stockexchange.tradable.*;
/**
 * A ProductBookSide object maintains the content of one side (Buy or Sell) of a Stock (product) “book”.
Recall that a stock’s “book” holds the buy and sell orders and quote sides for a stock that are not yet
tradable. The buy-side of the book contains all buy-side orders and quote sides that are not yet tradable
in descending order. The sell-side of the book contains all buy-side orders and quote sides that are not yet
tradable in ascending order.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class ProductBookSide {
	
	BookSide side;
	private HashMap<Price, ArrayList<Tradable>> bookEntries = new HashMap< Price, ArrayList<Tradable>>();
	
	/* This method will generate and return an ArrayList of TradableDTO’s containing information on all the orders in
this ProductBookSide that have remaining quantity for the specified user.
	*/
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName) throws NosuchProductException{
		ArrayList<TradableDTO> tradableObjects = new ArrayList<TradableDTO>();
		for(int i = 0; i < bookEntries.size(); i++){
			Tradable t;
			if(userName == null || userName.isEmpty()) throw new NosuchProductException("User name doesn't exist.");
			else if (t.getUser() == userName && t.getRemainingVolume() > 0){
			TradableDTO dto = new TradableDTO(t);
			tradableObjects.add(dto);
		}
		
		}
		return tradableObjects;
	}
	
	//this method should be “package-visible” so no “public” or “private” keyword
	/*
	 * This method should return an ArrayList of the Tradables that are at the best price in the “bookEntries”
HashMap.
	 */
	synchronized ArrayList<Tradable> getEntriesAtTopOfBook(){
		if (bookEntries.isEmpty())
			return null;
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		Collections.sort(sorted); // Sort them
		if (side == BookSide.BUY) {Collections.reverse(sorted);} // Reverse them
		
        return bookEntries.get(sorted.get(0));
	  }
	/*
	 * This method should return an array of Strings, where each index holds a “Price x Volume” String.
	 */
	public synchronized String[] getBookDepth(){
		if (bookEntries.isEmpty()) {return new String[]{"<Empty>"};}
		String [] array = new String[bookEntries.size()];
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		Collections.sort(sorted); // Sort them
		if (side == BookSide.BUY) {Collections.reverse(sorted);} // Reverse them
		
		String s;
		for(int i = 0; i < sorted.size(); i++){
			ArrayList<Tradable> a = bookEntries.get(sorted.get(i));//All is a tradable arrayList, and sorted.get(i) is price
			int sum = 0;
			for(int j = 0; j < a.size(); j++){
				sum += a.get(j).getRemainingVolume();
			}
			s = sorted.get(i) + " X " + sum;
			array[i] = s;
		}
		return array;
		
	}
	
	/*
	 * This method should return all the Tradables in this book side at the specified price.
	 */
	synchronized ArrayList<Tradable> getEntriesAtPrice(Price price) {
		if(!bookEntries.containsKey(price)) return null;
		return bookEntries.get(price);
	}
	/*
	 * This method should return true if the product book (the “bookEntries” HashMap) contains a Market Price
	 */
	public synchronized boolean hasMarketPrice() {
		if (!bookEntries.containsKey(hasMarketPrice())) return true;
		return false;
	}
	
	/*
	 * This method should return true if the ONLY Price in this product’s book is a Market Price
	 */
	public synchronized boolean hasOnlyMarketPrice() {
		if(bookEntries.size()==1 && bookEntries.remove(hasMarketPrice())==null) return true;
		return false;
	}
	
	/*
	 * This method should return the best Price in the book side.
	 */
	public synchronized Price topOfBookPrice() {
		if(bookEntries.isEmpty()) return null;
		else{
			ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
			Collections.sort(sorted); // Sort them
			if (side == BookSide.BUY) {Collections.reverse(sorted);}// Reverse them
			 return sorted.get(0);
		}
		
	}
	/*
	 * This method should return the volume associated with the best Price in the book side.
	 */
	public synchronized int topOfBookVolume() {
		if(bookEntries.isEmpty())
			return 0;
		else{
			ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
			Collections.sort(sorted); // Sort them
			if (side == BookSide.BUY) {Collections.reverse(sorted);}// Reverse them
		int sum = 0;
		for(int i = 0; i< sorted.size(); i++){
			ArrayList<Tradable> array = bookEntries.get(sorted.get(i));
		for (int j = 0; j < sorted.size(); j++){
			sum+=array.get(j).getRemainingVolume();
		}
		}
		return sum;
		}
	}
	/*
	 * Returns true if the product book (the “bookEntries” HashMap) is empty, false otherwise.
	 */
	public synchronized boolean isEmpty() {
		if(bookEntries.isEmpty()) return true;
           return false;
	}
	/*
	 * This method should cancel every Order or QuoteSide at every price in the book
	 */
	public synchronized void cancelAll() {
		bookEntries.clear();
		submitOrderCancel(String orderId)
		submitQuoteCancel(String userName)
		
	}
	/*
	 * This method should search the book (the “bookEntries” HashMap) for a Quote from the specified user
	 */
	public synchronized TradableDTO removeQuote(String user) {
		bookEntries.remove(price);
		Tradable t;
		TradableDTO dto = new TradableDTO(t);
		return dto;
		
	}
	/*
	 * This method should cancel the Order (if possible) that has the specified identifier. This method should search
the book at all prices (the “bookEntries” HashMap) for the Order with the specified identifier.df
	 */
	public synchronized void submitOrderCancel(String orderId) {
		String orderId = bookEntries.containsValue(orderId)
		addOldEntry(Tradable)
		bookEntries.remove(price)
		Parent.ProductBook.checkTooLateToCancel(orderId)
	}
	public synchronized void submitQuoteCancel(String userName) {
		if (bookEntries.removeQuote(username)==null); return;
		MessagePublisher.getInstance().publishCancel(orderId);
	}
	public void addOldEntry(Tradable t) {
		ProductBook.addOldEntry();
	}
	public synchronized void addToBook(Tradable trd) {
		ArrayList<Tradable> a = bookEntries.get(trd);
		a.add(trd);
		
	}
	public HashMap<String, FillMessage> tryTrade(Tradable trd){
		HashMap<String, FillMessage>allFills = new HashMap<String, FillMessage>;
		if(side.equals("BUY"))
			trySellAgainstBuySideTrade(trd);
		else if (side.equals("SELL"))
			tryBuyAgainstSellSideTrade(trd);
		
		MessagePublisher.getInstance().publishFill(aFillMessage);
		return allFills;
	}
	
	public synchronized HashMap<String, FillMessage> trySellAgainstBuySideTrade(Tradable trd){
		HashMap<String, FillMessage> allFills = new HashMap<String, FillMessage>();
		HashMap<String, FillMessage> fillMsgs = new HashMap<String, FillMessage>();
		while () {
			HashMap<String, FillMessage> doTrade = new HashMap<String, FillMessage>;
			fillMsgs = mergeFills(fillMsgs, doTrade);
			allFills.putAll(fillMsgs);
		}
		return allFills;
	}
	private HashMap<String, FillMessage> mergeFills(HashMap<String, FillMessage> existing, HashMap<String, FillMessage> newOnes{
		return new HashMap<String, FillMessage>(newOnes);
		HashMap<String, FillMessage> results = new HashMap<>(existing);
		for (String key : newOnes.keySet()) { // For each Trade Id key in the “newOnes” HashMap
			if (!existing.containsKey(key)) { // If the “existing” HashMap does not have that key…
			results.put(key, newOnes.get(key)); // …then simply add this entry to the “results” HashMap
			} else { // Otherwise, the “existing” HashMap does have that key – we need to update the data
			FillMessage fm = results.get(key); // Get the FillMessage from the “results” HashMap
			// NOTE – for the below, you will need to make these 2 FillMessage methods “public”!
			fm.setFillVolume(newOnes.get(key).getFillVolume()); // Update the fill volume
			fm.setDetails(newOnes.get(key).getDetails()); // Update the fill details
			}
			}
			return results;
	}
	public synchronized HashMap<String, FillMessage> tryBuyAgainstSellSideTrade(Tradable trd){
		HashMap<String, FillMessage> allFills = new HashMap<String, FillMessage>();
		HashMap<String, FillMessage> fillMsgs = new HashMap<String, FillMessage>();
		while() {
			HashMap<String, FillMessage> doTrade = new HashMap<String, FillMessage>;
			fillMsgs = mergeFills(fillMsgs, doTrade);
			allFills.putAll(fillMsgs);
		}
		return allFills;

	}
	public synchronized void clearIfEmpty(Price p) {
		if (bookEntries.get(p).isEmpty())
		bookEntries.remove(p);
		
	}
	public synchronized void removeTradable(Tradable t) {
		ArrayList<Tradable> entries = bookEntries.get(t.getPrice());
		if(entries == null) return;
		else {  
			 boolean n = entries.remove(t);
		     if (n==false) return;
		     else if{
		    	 entries
		    	 clearIfEmpty
		     }
		
		}
	}
	

}
	


