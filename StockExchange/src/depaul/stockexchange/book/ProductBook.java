package depaul.stockexchange.book;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.tradable.*;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.*;
import depaul.stockexchange.publishers.*;

import java.util.ArrayList;
import java.util.HashMap;



public class ProductBook {
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName){
		ArrayList<TradableDTO> a = new ArrayList<TradableDTO>;
		//BUY  a.getOrdersWithRemainingQty(userName);
		//SELL a.getOrdersWithRemainingQty(userName);
		return a;
		
	}
	public synchronized void checkTooLateToCancel(String orderId) {
		
	}
	public synchronized String[ ][ ] getBookDepth(){
		return null;
		
	}
	public synchronized MarketDataDTO getMarketData() {
		return null;
		
	}
	
	
	public synchronized void addOldEntry(Tradable t) {
		
	}
	public synchronized void openMarket() {
		
	}
	public synchronized void closeMarket() {
		
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
