package publishers;

import client.User;

/**
 * The LastSalePublisher should implement the “Singleton” design pattern, as we only want to have a single
instance of the LastSalePublisher. Besides the subscribe/unsubscribe functionality common to all
publishers, the LastSalePublisher will need a method that the components of the trading system will call
to send the last sales out – “publishLastSale”. The “publishLastSale” callers will provide a String stock
symbol, a Price holding the last sale price, and an “int” holding the last sale volume a parameter to the
method. The “publishLastSale” method needs to be “synchronized” (covered later in the course), as it
makes use of the subscriptions HashMap - so that should be temporarily locked during use:
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class LastSalePublisher {
	/**
	 * For each User object in the HashSet or ArrayList for the specified stock symbol (i.e., the product), do
the following:
     */
	public synchronized void subscribe(User u, String product) throws AlreadySubscribedException{
		
	}
	
	public synchronized void unSubscribe(User u, String product) throws NotSubscribedException{
		
	}

	public synchronized void publishLastSale(String product, Price p, int v){
		/*
		 * The String stock symbol passed into “publishLastSale” (i.e., the product).
		 */
		
		/*
		 * The last sale price passed into “publishLastSale” (If this is null, a Price object representing $0.00 should be used, do NOT send null to the users)
		 */
		
		/*
		 * The last sale volume passed into “publishLastSale”.
		 */
		
		/**
		 * Then, after the “for” loop, make a call to the TickerPublisher’s “publishTicker” method, passing it
the “product” symbol String, and the Price “p” that were passed into this method.
		 */
	}

}
