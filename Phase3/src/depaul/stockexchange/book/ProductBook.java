package depaul.stockexchange.book;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.tradable.*;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.*;
import depaul.stockexchange.publishers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class ProductBook {
	private HashSet<String> userQuotes = new HashSet<>();
	private HashMap<Price, ArrayList<Tradable>> oldEntries = new HashMap< Price, ArrayList<Tradable>>();
	
	
	
	
	
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName){
		ArrayList<TradableDTO> a = new ArrayList<TradableDTO>;
		//BUY  a.getOrdersWithRemainingQty(userName);
		//SELL a.getOrdersWithRemainingQty(userName);
		return a;
		
	}
	public synchronized void checkTooLateToCancel(String orderId) {
		if (oldEntries.containsValue(orderId)){
			CancelMessage()
		}
		else throw new OrderNotFoundException("The requested order could not be found.");
	}
	public synchronized String[ ][ ] getBookDepth(){
		String[][] bd = new String[2][];
		bd[0]= buy-side ProductBookSide.getBookDepth();
		bd[1]= sell-side ProductBookSide.getBookDepth();
		return bd;
		
	}
	public synchronized MarketDataDTO getMarketData() {
		bestBuySide price = buy-side ProductBookSide.topOfBookPrice();
		bestSellSide price = sell-side ProductBookSide.topOfBookPrice();
		best buy side volume = buy-side ProductBookSide’s “topOfBookVolume ();
		best sell side volume = sell-side ProductBookSide.topOfBookVolume();
		new MarketDataDTO
		return null;
		
	}
	
	
	public synchronized void addOldEntry(Tradable t) {
		if (oldEntries.containsKey(t))
			oldEntries.put(t, ArrayList<Tradable>);
		cancelledVolume = t.getRemainingVolume();
		t.setRemainingVolume(0);
		oldEntries.get(oldEntries).add(t);  //?
		
	}
	public synchronized void openMarket() {
		Price buyPrice =  BookSide.BUY.topOfBookPrice();
		Price sellPrice =  BookSide.SELL.topOfBookPrice();
		if (buyPrice == null || sellPrice == null) return;
		while(buyPrice >= sellPrice || buyPrice.isMarket() || sellPrice.isMarket()){
			topOfBuySide = new ArrayList<Tradable>>;
			BookSide.BUY.getEntiresAtPrice();
			allFills = new HashMap<String, FillMessage>;//Leave this as null for now
			toRemove = new ArrayList<Tradable>>;
					
			for (each Tradable “t” in the “topOfBuySide” ArrayList){
				
				allFills = BookSide.SELL.tryTrade(t);
			    if(t.getRemainingVolume() == 0)
			    toRemove.//add “t” to the “toRemove” ArrayList.
				}
			for (each Tradable “t” in the “toRemove” ArrayList,){
				BookSide.BUY.removeTradable(t);
			}
			updateCurrentMarket()
			Price lastSalePrice = determineLastSalePrice(allFills);
			int lastSaleVolume = determineLastSaleQuantity(allFills);
			//Now publish a Last Sale message passing this book’s product symbol, the lastSalePrice, and the lastSaleVolume
			Price buyPrice = BookSide.BUY.topOfBookPrice();
			Price sellPrice = BookSide.SELL.topOfBookPrice();
		    if (buyPrice == null || sellPrice == null) break;
		}
	}
	public synchronized void closeMarket() {
		BookSide.BUY.cancelAll();
		BookSide.SELL.cancelAll();
		updateCurrentMarket();
		
		
	}
	public synchronized void cancelOrder(BookSide side, String orderId) {
		
	}
	public synchronized void cancelQuote(String userName) {
		submitQuoteCancel(userName)
		submitQuoteCancel(userName)
		updateCurrentMarket();
	}
	public synchronized void addToBook(Quote q) {
		ubmitOrderCancel(orderId);
		updateCurrentMarket();
	}
	public synchronized void addToBook(Order o) {
		o.addToBook(Side, Tradable)
		o.updateCurrentMarket()
	}
	public synchronized void updateCurrentMarket() {
		String s = The Buy-side ProductBookSide’s “topOfBookPrice()” + the Buy-side ProductBookSide’s topOfBookVolume()
		+ the Sell-side ProductBookSide’s “topOfBookPrice()” + the Sell-side ProductBookSide’s
		topOfBookVolume().
		if(lastCurrentMarket!= s) {
			
		}
		
	}
	private synchronized Price determineLastSalePrice(HashMap<String, FillMessage> fills) {
		ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
		Collections.sort(msgs);
		//Return the Price of that first Fill Message in that sorted Arraylist.
		
	}
	private synchronized int determineLastSaleQuantity(HashMap<String, FillMessage> fills) {
		ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
		Collections.sort(msgs);
		//return the fill volume of the first Fill Message in that sorted Arraylist.		
	}
	private synchronized void addToBook(BookSide side, Tradable trd) {
		if (MarketState.PREOPEN) {
			side.addToBook(trd); return;
		}
			HashMap<String, FillMessage> allFills = null;
			if(side.values()=="BUY")
				if(side.values()=="SELL")

		if(!(allFills== null && allFills.isEmpty()))
			allFills.updateCurrentMarket();
		
		lastSalePrice = determineLastSalePrice(allFills);
		if (trd.getRemainingVolume() > 0) {
			if (trd.getPrice().isMarket()) {
				Cancelled = CancelMessage(trd);
			}
			else {
				addToBook();
			}
		}
	}
	

}
