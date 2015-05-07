package publishers;
import java.util.HashMap;
import price.*;
import client.User;
/**
 * The TickerPublisher should implement the “Singleton” design pattern, as we only want to have a single
instance of the TickerPublisher. Besides the subscribe/unsubscribe functionality common to all publishers,
the TickerPublisher will need a method that the components of the trading system will call to send the last sales out – “publishTicker”. The “publishTicker” callers will provide a String stock symbol, and a Price
holding the last sale price a parameter to the method. The “publishTicker” method needs to be
“synchronized” (covered later in the course), as it makes use of the subscriptions HashMap - so that
should be temporarily locked during use:
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class TickerPublisher {
	public synchronized void publishTicker(String product, Price p){
		/**
		 * Determine if the new trade Price for the provided stock symbol (product) is greater than (up), less
than (down), or the same (equal) as the previous trade price seen for that stock. If the price has
moved up, the up-arrow character '↑' ((char) 8593) should be sent to the user. If the price has
moved down, the down-arrow character ' ↓' ((char) 8595) should be sent to the user. If the price
is the same as the previous price, the equals character ' =' should be sent to the user. If there is no
previous price for the specified stock, the space character ' ' should be sent to the user. These
symbols will be used in the next step.
		 */
		
		/**
		 * Then, for each User object in the HashSet or ArrayList for the specified stock symbol (i.e., the
product), do the following:
		 */
		
		/*
		 * The String stock symbol passed into “publishTicker” (i.e., the product).
		 */
		
		/*
		 * The price passed into “publishTicker” (If this is null, a Price object representing $0.00
           should be used, do NOT send null to the users)
         */
		
		/*
		 * The characted previously determined that represents the movement of the stock
(↑,↓,=,<space>).
		 */
	}

}
