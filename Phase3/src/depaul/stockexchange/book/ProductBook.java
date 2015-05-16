package depaul.stockexchange.book;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.tradable.*;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.*;
import depaul.stockexchange.publishers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/**
 * A ProductBook object maintains the Buy and Sell sides of a stock’s “book”. Many of the functions owned
by the ProductBook class are designed to simply pass along that same call to either the Buy or Sell side of
the book. Recall that a stock’s “book” holds the buy and sell orders and quote sides for a stock that are not
yet tradable. The buy-side of the book contains all buy-side orders and quote sides that are not yet
tradable in descending order. The sell-side of the book contains all buy-side orders and quote sides that
are not yet tradable in ascending order.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class ProductBook {
	private HashSet<String> userQuotes = new HashSet<>();
	private HashMap<Price, ArrayList<Tradable>> oldEntries = new HashMap< Price, ArrayList<Tradable>>();
	
	
	
	
	/*
	 * This method should return an ArrayList containing any orders for the specified user that have remaining
quantity.
	 */
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName){
		ArrayList<TradableDTO> a = new ArrayList<TradableDTO>;
		//BUY  a.getOrdersWithRemainingQty(userName);
		//SELL a.getOrdersWithRemainingQty(userName);
		return a;
		
	}
	
	/*
	 * This method id designed to determine if it is too late to cancel an order (meaning it has already been traded
out or cancelled).
	 */
	public synchronized static void checkTooLateToCancel(String orderId) {
		if (oldEntries.containsValue(orderId)){
			CancelMessage()
		}
		else throw new OrderNotFoundException("The requested order could not be found.");
	}
	
	/*
	 * This method is should return a 2-dimensional array of Strings that contain the prices and volumes at all prices
present in the buy and sell sides of the book.
	 */
	public synchronized String[ ][ ] getBookDepth(){
		String[][] bd = new String[2][];
		bd[0]= buy-side ProductBookSide.getBookDepth();
		bd[1]= sell-side ProductBookSide.getBookDepth();
		return bd;
		
	}
	
	/*
	 * This method should create a MarketDataDTO containing the best buy side price and volume, and the best sell
side price an volume.
	 */
	public synchronized MarketDataDTO getMarketData() {
		bestBuySide price = buy-side ProductBookSide.topOfBookPrice();
		bestSellSide price = sell-side ProductBookSide.topOfBookPrice();
		best buy side volume = buy-side ProductBookSide’s “topOfBookVolume ();
		best sell side volume = sell-side ProductBookSide.topOfBookVolume();
		new MarketDataDTO
		return null;
		
	}
	
	/*
	 * This method should add the Tradable passed in to the “oldEntries” HashMap.
	 */
	public synchronized void static addOldEntry(Tradable t) {
		if (oldEntries.containsKey(t))
			oldEntries.put(t, ArrayList<Tradable>);
		cancelledVolume = t.getRemainingVolume();
		t.setRemainingVolume(0);
		oldEntries.get(oldEntries).add(t);  //?
		
	}
	/*
	 * This method will “Open” the book for trading. Any resting Order and QuoteSides that are immediately
tradable upon opening should be traded.
	 */
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
	
	
	/*
	 * This method will “Close” the book for trading.
	 */
	public synchronized void closeMarket() {
		BookSide.BUY.cancelAll();
		BookSide.SELL.cancelAll();
		updateCurrentMarket();
		
		
	}
	
	/*
	 * This method will cancel the Order specified by the provided orderId on the specified side.
	 */
	public synchronized void cancelOrder(BookSide side, String orderId) {
		BookSide.BUY.submitOrderCancel(orderId);
		updateCurrentMarket();
		
	}
	
	/*
	 * This method will cancel the specified user’s Quote on the both the BUY and SELL sides.
	 */
	public synchronized void cancelQuote(String userName) {
		submitQuoteCancel(userName)
		submitQuoteCancel(userName)
		updateCurrentMarket();
	}
	
	/*
	 * This method should add the provided Quote’s sides to the Buy and Sell ProductSideBooks.
	 */
	public synchronized void addToBook(Quote q) {
		ubmitOrderCancel(orderId);
		updateCurrentMarket();
	}
	
	/*
	 * This method should add the provided Order to the appropriate ProductSideBook.
	 */
	public synchronized void addToBook(Order o) {
		o.addToBook(Side, Tradable)
		o.updateCurrentMarket()
	}
	
	/*
	 * This method needs to determine if the “market” for this stock product has been updated by some market
action.
	 */
	public synchronized void updateCurrentMarket() {
		String s = The Buy-side ProductBookSide’s “topOfBookPrice()” + the Buy-side ProductBookSide’s topOfBookVolume()
		+ the Sell-side ProductBookSide’s “topOfBookPrice()” + the Sell-side ProductBookSide’s
		topOfBookVolume().
		if(lastCurrentMarket!= s) {
			
		}
		
	}
	
	/*
	 * This method will take a HashMap of FillMessages passed in and determine from the information it contains
what the Last Sale price is.
	 */
	private synchronized Price determineLastSalePrice(HashMap<String, FillMessage> fills) {
		ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
		Collections.sort(msgs);
		//Return the Price of that first Fill Message in that sorted Arraylist.
		
	}
	/*
	 * This method will take a HashMap of FillMessages passed in and determine from the information it contains
what the Last Sale quantity (volume) is.
	 */
	private synchronized int determineLastSaleQuantity(HashMap<String, FillMessage> fills) {
		ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
		Collections.sort(msgs);
		//return the fill volume of the first Fill Message in that sorted Arraylist.		
	}
	
	/*
	 * This method is a key part of the trading system. This method deals with the addition of Tradables to the
Buy/Sell ProductSideBook and handles the results of any trades the result from that addition.
	 */
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
