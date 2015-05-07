package publishers;
import client.User;
import messages.*;
//import DePaul.StockExchange.Message;
/**
 * The MessagePublisher should implement the �Singleton� design pattern. Besides the
subscribe/unsubscribe functionality common to all publishers, the MessagePublisher will need several
methods that the components of the trading system can call to send the messages out �
�publishCancel(CancelMessage cm)�, �publishFill(FillMessage fm)�, and
�publishMarketMessage(MarketMessage nn)�. Each of these methods needs to be �synchronized�
(covered later in the course), as they make use of the subscriptions HashMap - so that should be
temporarily locked during use:
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class MessagePublisher {
	public synchronized void subscribe(User u, String product) throws AlreadySubscribedException{
		
	}
	
	public synchronized void unSubscribe(User u, String product) throws NotSubscribedException{
		
	}
	/**
	 * Find the individual User object in the HashSet or ArrayList for the specified stock symbol whose user
name matches the user name found in the CancelMessage object passed into �publishCancel�. The
user name and stock symbol are found in the provided CancelMessage.
	 */
	
	/**
	 * Once found, call the User object�s �acceptMessage� passing the CancelMessage object that was
passed in.
	 */
	public synchronized void publishCel(CancelMessage cm) throws InvalidMessageArgumentException{
		
	}
	
	public synchronized void publishFill(FillMessage fm) throws InvalidMessageArgumentException{
	}	
		/**
		 * Find the individual User object in the HashSet or ArrayList for the specified stock symbol whose user
name matches the user name found in the FillMessage object passed into �publishFill�. The user
name and stock symbol are found in the provided FillMessage.
		 */
		
		/**
		 * Once found, call the User object�s �acceptMessage� passing the FillMessage object that was passed
into �publishFill�
		 */
		public synchronized void publishMarketMessage(MarketMessage mm){
			/**
			 * For all subscribed Users regardless of the stock symbol they are interested in, do the following:
			 */
			
			/*
			 * Call the User object�s �acceptMarketMessage� passing the String generated by calling the
�toString� method of the MarketMessage object passed in.
			 */
		}
	

}
