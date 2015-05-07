package publishers;
import java.util.ArrayList;
import java.util.HashMap;

import client.User;
import depaulStockExchange.InvalidPriceOperation;
import price.*;

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
	private volatile static LastSalePublisher Instance;
	private static HashMap<String, ArrayList<User>> HashMap;
	
	private LastSalePublisher (){
		HashMap = new HashMap<String, ArrayList<User>>();
	}
	
	public static LastSalePublisher  getInstance(){
		if(Instance == null){
			synchronized (LastSalePublisher.class){
				if(Instance == null)
					Instance = new LastSalePublisher();				
			}
		}
		return Instance;
	}
	
	public synchronized void subscribe(User u, String product)throws AlreadySubscribedException{
			
		if (HashMap.containsKey(product)){
			ArrayList<User> arrayList = HashMap.get(product);
			if(arrayList != null){
				if(arrayList.contains(u)){
					throw new AlreadySubscribedException("It has been subscribed");
				}
				else{
					arrayList.add(u);
				}
			}
		}		
		else if (!HashMap.containsKey(product)) {
			ArrayList<User> arrayList = new ArrayList<User>();			
			HashMap.put(product, arrayList);	
			arrayList.add(u);
		}	
	}
	
    public synchronized void unSubscribe(User u, String product)throws NotSubscribedException{
    	 ArrayList<User> arrayList = HashMap.get(product);
    	 if (arrayList != null){
    		 if(arrayList.contains(u)){
    			 arrayList.remove(u);
    		 }
    		 else{
    			 throw new NotSubscribedException("It hasn't been subscribed");
    		 }
    	 }
	}
    
    public synchronized void publishLastSale(String product, Price price, int v) throws InvalidPriceOperation {
    	
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
	
    	
    	if (HashMap.containsKey(product)){
			ArrayList<User> arrayList = HashMap.get(product);
			Price p = price;
			if(p == null){
				p = PriceFactory.makeLimitPrice(0);
			}	
			if(arrayList != null){
				for(User user: arrayList){								
					user.acceptLastSale(product, p, v);
				}										
				TickerPublisher.getInstance().publishTicker(product, price);					
			}
    	}
    }
  }


	
	
		


