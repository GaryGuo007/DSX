package publishers;

import java.util.HashMap;
import java.util.HashSet;

import client.User;

public class Subscriber {
	public static HashMap<String, HashSet<User>> subscriptions = new HashMap<String, HashSet<User>>();
	
	public synchronized void subscribe(User u, String product) throws AlreadySubscribedException { 
		if(subscriptions.get(product).contains(u)) throw new AlreadySubscribedException("The User has already subscribed this product.");
		subscriptions.get(product).add(u);
	}
	public synchronized void unSubscribe(User u, String product) throws NotSubscribedException { 
		if(!subscriptions.get(product).contains(u)) throw new NotSubscribedException("The User did not subscribed this product.");
		subscriptions.get(product).remove(u);
	}

}
