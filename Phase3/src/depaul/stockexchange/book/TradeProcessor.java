package depaul.stockexchange.book;

import java.util.HashMap;

import depaul.stockexchange.messages.*;
import depaul.stockexchange.tradable.*;

public interface TradeProcessor {
	public HashMap<String, FillMessage> doTrade(Tradable trd);

}
