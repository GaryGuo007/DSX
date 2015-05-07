package publishers;

import price.Price;
import client.*;

/**
 * The CurrentMarketPublisher should implement the "Singleton" design pattern,
 * as we only want to have a single instance of the CurrentMarketPublisher.
 * Besides the subscribe/unsubscribe functionality common to all publishers, the
 * CurrentMarketPublisher will need a method that the components of the trading
 * "publishCurrentMarket" callers will provide a "MarketDataDTO" reference as a
 * parameter to the method that contains the market update data. The
 * "publishCurrentMarket" method needs to be "synchronized" (covered later in
 * the course), as it makes use of the subscriptions HashMap - so that should be
 * temporarily locked during use:
 * 
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author Junmin Liu
 */
public final class CurrentMarketPublisher extends Subscriber {
	
	private static CurrentMarketPublisher publishCurrentMarket;
	
	public static CurrentMarketPublisher getCurrentMarketPublisher() throws Exception {
		// TODO Auto-generated constructor stub
		if (publishCurrentMarket == null)
			publishCurrentMarket = new CurrentMarketPublisher();
		return publishCurrentMarket;
	}
		
	public synchronized void publishCurrentMarket(MarketDataDTO md) {
		
		/**
		 * For each User object in the HashSet or ArrayList for the specified
		 * stock symbol (the stock symbol can be found in the MarketDataDTO), do
		 * the following:
		 */

		/*
		 * The String stock symbol taken from the MarketDataDTO passed in.
		 */
		
		

		/*
		 * The BUY side price taken from the MarketDataDTO passed in. (If this
		 * is null, a Price object representing $0.00 should be used, do NOT
		 * send null to the users)
		 */

		/*
		 * The BUY side volume taken from the MarketDataDTO passed in.
		 */

		/*
		 * The SELL side price taken from the MarketDataDTO passed in. (If this
		 * is null, a Price object representing $0.00 should be used, do NOT
		 * send null to the users)
		 */

		/*
		 * The SELL side volume taken from the MarketDataDTO passed in.
		 */
	}

}
