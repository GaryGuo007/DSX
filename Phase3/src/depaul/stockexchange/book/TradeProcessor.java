package depaul.stockexchange.book;

import java.util.HashMap;

import depaul.stockexchange.messages.*;
import depaul.stockexchange.tradable.*;
/**
 * The TradeProcessor is an interface that defines the functionality needed to “execute” the actual trades
between Tradable objects in this book side. There is only one method on this interface:
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public interface TradeProcessor {
	/**
	 * This TradeProcessor method will be called when it has been determined that a Tradable (i.e., a Buy Order, a Sell
QuoteSide, etc.) can trade against the content of the book. The return value from this function will be a
HashMap<String, FillMessage> containing String trade identifiers (the key) and a Fill Message object (the value).
	 * @param trd
	 * @return
	 */
	
	public HashMap<String, FillMessage> doTrade(Tradable trd);

}
