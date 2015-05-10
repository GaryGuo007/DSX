
package depaul.stockexchange.book;

import java.util.HashMap;

import depaul.stockexchange.messages.*;
import depaul.stockexchange.tradable.*;

public class TradeProcessorPriceTimeImpl {
	
	private HashMap<String, FillMessage> fillMessages = new HashMap<String, FillMessage>();
	
	
	
	private String makeFillKey(FillMessage fm) {
		String key = fm.getUser()+fm.getId()+fm.getPrice();
		return key;
		
	}
	private boolean isNewFill(FillMessage fm) {
		String key = fm.makeFillKey(FillMessage);
		return false;
		
	}
	private void addFillMessage(FillMessage fm) {
		if (fm.isNewFill(FillMessage)==true)
		   key =	makeFillKey(FillMessage fm)
		else
			key = makeFillKey(FillMessage fm)
			
	}
	public HashMap<String, FillMessage> doTrade(Tradable trd){
		return HashMap<String, FillMessage> ;
		
	}
	

}
