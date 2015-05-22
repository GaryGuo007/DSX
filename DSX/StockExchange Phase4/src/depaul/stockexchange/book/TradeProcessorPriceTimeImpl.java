package depaul.stockexchange.book;
/**
The TradeProcessorPriceTimeImpl interface is the interface that a class that will act as a trading processor
needs to implement. This Tradeprocessor implementer contains the functionality needed to “execute”
actual trades between Tradable objects in this book side using a price-time algorithm (orders are traded in
order of price, then in order of arrival).
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
import java.security.acl.NotOwnerException;
import java.util.ArrayList;
import java.util.HashMap;

import depaul.stockexchange.DataValidationException;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.tradable.*;

/**
The TradeProcessorPriceTimeImpl interface is the interface that a class that will act as a trading processor
needs to implement. This Tradeprocessor implementer contains the functionality needed to “execute”
actual trades between Tradable objects in this book side using a price-time algorithm (orders are traded in
order of price, then in order of arrival).
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
import java.util.ArrayList;
import java.util.HashMap;

import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.Price;
import depaul.stockexchange.tradable.*;




public class TradeProcessorPriceTimeImpl implements TradeProcessor {
	
	/*
	 * Constructor – This constructor needs to accept a ProductBookSide parameter – a reference to the
book side this TradeProcessor belongs to.
	 */
	public TradeProcessorPriceTimeImpl(ProductBookSide productBookSideIn){
		  book = productBookSideIn;
	}
	

//	public HashMap<String, FillMessage> doTrade(Tradable trd){
//		throw new UnsupportedOperationException("Not supported yet.");
//	}

	
	private HashMap<String, FillMessage> fillMessages = new HashMap<String, FillMessage>();
	private ProductBookSide book;
	/*
	 * This method will be used at various times when executing a trade.
	 */
	private String makeFillKey(FillMessage fm) {
		String key = fm.getUser() + fm.getId() + fm.getPrice();
		return key;
	}
	
	/*
	 * This Boolean method checks the content of the “fillMessages” HashMap to see if the FillMessage passed in is a fill
message for an existing known trade or if it is for a new previously unrecorded trade.
	 */
	private boolean isNewFill(FillMessage fm) {
		String fillKey = this.makeFillKey(fm);
		if(!fillMessages.keySet().contains(fillKey)) {
			return true;
		}
		FillMessage oldFill = fillMessages.get(fillKey);
		if(!oldFill.getSide().equals(fm.getSide())){ 
			return true;
			}
		if(!oldFill.getId().equals(fm.getId())){
			return true;
		}
		return false;
		}
	
	
	/*
	 * This method should add a FillMessage either to the “fillMessages” HashMap if it is a new trade, or should update
an existing fill message is another part of an existing trade.
	 */
	/////////////////////////////////////////
	private void addFillMessage(FillMessage fm) throws NotOwnerException, BadParameterException {
		//String key = this.makeFillKey(fm);
		String result = makeFillKey(fm);
		if (isNewFill(fm)){
		  
		  fillMessages.put(result, fm);
		}
		else{
			String fillKey = makeFillKey(fm);
		    FillMessage oldFill = fillMessages.get(fillKey);
		    oldFill.setFillVolume(oldFill.getVolume() + fm.getVolume());
		    oldFill.setDetails(fm.getDetails());
	    }
	}
	
	
	/*
	 * This TradeProcessor method will be called when it has been determined that a Tradable (i.e., a Buy Order, a
Sell QuoteSide, etc.) can trade against the content of the book. The return value from this function will be a
HashMap<String, FillMessage> containing String trade identifiers (the key) and a Fill Message object (the
value).
	 */
	////throws 
	public HashMap<String, FillMessage> doTrade(Tradable trd) throws DataValidationException {
		fillMessages = new HashMap<String, FillMessage>();
		ArrayList<Tradable> tradedOut = new ArrayList<Tradable>();
		ArrayList<Tradable> entriesAtPrice = book.getEntriesAtTopOfBook();
		Price tPrice;
		
		
		for(Tradable t: entriesAtPrice)
		{
			if(trd.getRemainingVolume()== 0)
			{
				//Follow the "NO" path
				for(Tradable x: tradedOut)
				{
					entriesAtPrice.remove(x);
				}
				
				if(entriesAtPrice.isEmpty())
				{
					book.clearIfEmpty(book.topOfBookPrice());
				}
				return fillMessages;
			}
	
			
		
			else{
		
				if(trd.getRemainingVolume() >= t.getRemainingVolume())
				{
				//Follow the "Yes" path	
					tradedOut.add(t);
					if(t.getPrice().isMarket())
					{
						tPrice = trd.getPrice();
					}
					else
					{
						tPrice = t.getPrice();
					}
					FillMessage fmT= new FillMessage(t.getUser(),t.getProduct(),tPrice,t.getRemainingVolume(),"leaving 0",t.getSide(),t.getId());
					addFillMessage(fmT);
					FillMessage fmTrd= new FillMessage(trd.getUser(),trd.getProduct(),tPrice,t.getRemainingVolume(),"leaving "+(trd.getRemainingVolume()-t.getRemainingVolume()),trd.getSide(),trd.getId());
					addFillMessage(fmTrd);
					trd.setRemainingVolume(trd.getRemainingVolume()-t.getRemainingVolume());
					t.setRemainingVolume(0);
					book.addOldEntry(t);
					}
				else 
				{
					int remaninder = t.getRemainingVolume() - trd.getRemainingVolume();
					if(t.getPrice().isMarket())
					{
						tPrice = trd.getPrice();
					}
					else
					{ 
						tPrice = t.getPrice();
					}
					FillMessage fmT= new FillMessage(t.getUser(),t.getProduct(),tPrice,trd.getRemainingVolume(),"leaving "+remaninder,t.getSide(),t.getId());
					addFillMessage(fmT);
					FillMessage fmTrd= new FillMessage(trd.getUser(),trd.getProduct(),tPrice,trd.getRemainingVolume(),"leaving 0",trd.getSide(),trd.getId());
					addFillMessage(fmTrd);
					trd.setRemainingVolume(0);
					t.setRemainingVolume(remaninder);
					book.addOldEntry(trd);
				 }
			}
		}
		
         for(Tradable t: tradedOut){
        	// HashMap<String, FillMessage> entriesAtPrice;
			entriesAtPrice.remove(t);
         }
         
         if(entriesAtPrice.isEmpty()){
        	 book.clearIfEmpty(book.topOfBookPrice());
         }
         return fillMessages;
	}
}




