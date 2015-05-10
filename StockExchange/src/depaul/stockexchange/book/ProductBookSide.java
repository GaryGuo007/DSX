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

public class ProductBookSide {
	
	BookSide side;
	private HashMap<Price, ArrayList<Tradable>> bookEntries = new HashMap< Price, ArrayList<Tradable>>();
	
	
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName){
		new ArrayList<TradableDTO>;
		bookEntries.
		
		return null;
	}
	
	synchronized ArrayList<Tradable> getEntriesAtTopOfBook(){
		if (bookEntries == null)
			return null;
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		Collections.sort(sorted); // Sort them
		if (side == BookSide.BUY) {Collections.reverse(sorted);} // Reverse them
		
        return null;
	  }
	public synchronized String[] getBookDepth(){
		if (bookEntries.isEmpty()) {
			String[] ns = new String[];
			
			//make a new String array of size one, add the String “<Empty>” to it, and return that array.
			return new String[]{"<Empty>"};
			}
		
		return null;
		
	}
	synchronized ArrayList<Tradable> getEntriesAtPrice(Price price) {
		if(!bookEntries.containsKey(price)) return null;
		return bookEntries.get(price);
	}
	
	public synchronized boolean hasMarketPrice() {
		if (!bookEntries.containsKey(getMarketPrice())) return true;
		return false;
	}
	public synchronized boolean hasOnlyMarketPrice() {
		if(bookEntries.remove(getMarketPrice())==null) return true;
		return false;
	}
	public synchronized Price topOfBookPrice() {
		if(bookEntries.isEmpty()) return null;
		
		return null;
	}
	
	public synchronized int topOfBookVolume() {
		if(bookEntries.isEmpty())
			return 0;
		int sum;
		return sum;
	}
	
	public synchronized boolean isEmpty() {
		if(bookEntries.isEmpty()) return true;

		return false;
	}
	
	public synchronized void cancelAll() {
		bookEntries.clear();
		submitOrderCancel(String orderId)
		submitQuoteCancel(String userName)
		
	}
	public synchronized TradableDTO removeQuote(String user) {
		bookEntries.remove(price);
	}
	
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
	


