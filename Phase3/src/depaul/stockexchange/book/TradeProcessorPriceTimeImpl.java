
package depaul.stockexchange.book;

import java.util.ArrayList;
import java.util.HashMap;

import depaul.stockexchange.messages.*;
import depaul.stockexchange.tradable.*;

public class TradeProcessorPriceTimeImpl {
	
	private HashMap<String, FillMessage> fillMessages = new HashMap<String, FillMessage>();
	
	
	
	private String makeFillKey(FillMessage fm) {
		String key = fm.getUser() + fm.getId() + fm.getPrice();
		return key;
		
	}
	private boolean isNewFill(FillMessage fm) {
		String key = this.makeFillKey(fm);
		if(!fillMessages.containsKey(fm)) {
			return true;
		}
		/*
		 * Get the FillMessage from the “fillMessage” HashMap by using the String key you just generated as the key to
the HashMap. Save the resulting FillMessage in a temporary variable (i.e., “oldFill”)
		 */
		oldFill = key.
		if(oldFill.getSide()!= fillMessages.getSide()) return true;
		if(oldFill.getId() != fillMessages.getId()) return true;
		return false;
		
	}
	private void addFillMessage(FillMessage fm) {
		String key =	this.makeFillKey(fm);
		if (this.isNewFill(fm) == true)
		  fillMessages.put(key, fm);
		else
			key = this.makeFillKey(fm);
		   /*
		    * use that key to get the fill message from
the “fillMessages” HashMap.
		    */
		
		    fillMessages.get(fm);
		    /*
		     * Update the fill volume of that fill message to be the existing FillMessage's volume PLUS the fill volume of
the fill message passed in.
		     */
		    fillMessages.
		    /*
		     * Update the details of that fill message to be the details of the fill message passed in.
· Note – you will need to change the “set” methods for the FillMessage’s fill volume and details data
members to “public” so they can be externally modified.
		     */
			
	}
	public HashMap<String, FillMessage> doTrade(Tradable trd){
		fillMessages = new HashMap<String, FillMessage>();
		ArrayList<Tradable> tradedOut = new ArrayList<Tradable>();
		ArrayList<Tradable> entriesAtPrice = book.getEntriesAtTopOfBook();
		for(each Tradable “t” in the “entriesAtPrice” ArrayList){
			if(trd.getRemainingVolume()== 0){
				if(trd.getRemainingVolume()>=t.getRemainingVolume()){
					tradedOut.add(t);
					if(t.isMKT){
						Price tPrice = trd.getPrice();
					}
					else tPrice = t.getPrice();
				}
				else {
					int remaninder = t.getRemaindingVolume()-trd.getRemainingVolume();
					if(t.isMKT){
						tPrice = trd.getPrice();
					}
					else tPrice = t.getPrice();
				}
			}
		}
		
		FillMessage tFill = new FillMessage(t.getUser(), t.getProduct(), tPrice, t.getRemainingVolume(), "leaving " + 0 , t.getSide(), t.getId());
		t.addFillMessage(fillMessages);
		FillMessage trdFill =
				new FillMessage (trd.getUser(), t.getProduct(), tPrice, t.getRemainingVolume(), "leaving " + (trd.getRemainingVolume() - t.getRemainingVolume()),
				trd.getSide(), trd.getId());
		trd.addFillMessage(fillMessages);
		trd.setRemainingVolume(trd.getRemainingVolume() - t.getRemainingVolume());
		t.getRemainningVolume() = 0;
		addOldEntry(t);
		for (!entriesAtPrice.isEmpty()){//for(Are there more Tradables in the “entriesAtPrice” ArrayList
			if(trd.getRemainingVolume()== 0){
				if(trd.getRemainingVolume()>=t.getRemainingVolume()){
					tradedOut.add(t);
					if(t.isMKT){
						tPrice = trd.getPrice();
					}
					else tPrice = t.getPrice();
				}
				else {
					int remaninder = t.getRemaindingVolume() - trd.getRemainingVolume();
					if(t.isMKT){
						Price tPrice = trd.getPrice();
					}
					else tPrice = t.getPrice();
				}
			}
		}
		FillMessage tFill = new FillMessage(t.getUser(), t.getProduct(), tPrice, t.getRemainingVolume(), "leaving " + 0 , t.getSide(), t.getId());
		t.addFillMessage(fillMessages);
		FillMessage trdFill =
				new FillMessage (trd.getUser(), t.getProduct(), tPrice, t.getRemainingVolume(), "leaving " + (trd.getRemainingVolume() - t.getRemainingVolume()),
				trd.getSide(), trd.getId());
		trd.addFillMessage(fillMessages);
		
		trd.setRemainingVolume(0);
		t.setRemainingVolume(remainder);
		addOldEntry(trd);
		
		entriesAtPrice.remove(tradeOut);
		if(entriesAtPrice.isEmpty()) clearIfEmpty(topOfBookPrice());

		return fillMessages;
		
	}
	

}
