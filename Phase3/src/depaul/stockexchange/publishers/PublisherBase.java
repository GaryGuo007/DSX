/*
 * Copyright 2015 jimliu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package depaul.stockexchange.publishers;
import depaul.stockexchange.Utils;

import java.util.HashMap;
import java.util.HashSet;

import depaul.stockexchange.client.User;

/**
 * The common part of the Publisher classes
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public abstract class PublisherBase {
    
    //
    protected HashMap<String, HashSet<User>> subscriptions = new HashMap<String, HashSet<User>>();
    
    /**
     * Users subscribe for data. 
     * @param u
     *      A “User” reference (a reference to themselves)
     * @param product
     *      The String stock symbol they are interested.
     * @throws AlreadySubscribedException
     *      If the user is already subscribed for the stock.
     * @throws InvalidPublisherDataException 
     *      User can't be null
     *      Product can't be null or empty
     */
    public synchronized void subscribe(User u, String product) 
            throws AlreadySubscribedException, InvalidPublisherDataException {
        // user can't be null
        if (u == null) {
            throw new InvalidPublisherDataException("User can't be null.");
        }
        
        // product can't be null or empty
        if (Utils.isNullOrEmpty(product)) {
            throw new InvalidPublisherDataException("Product can't be null or empty.");
        }
        
        HashSet<User> subscribers = subscriptions.get(product);
        
        // check if user has already subscribed
        if (subscribers != null && subscribers.contains(u)) {
            throw new AlreadySubscribedException("The user has already "
                    + "subscribed for this stock.");
        }
        
        // if there is no HashSet with this product, create a new one
        if (subscribers == null) {
            subscribers = new HashSet<User>();
            subscriptions.put(product, subscribers);
        }
        
        // Add the user to the subscriber list
        subscribers.add(u);
    }
    
    
    /**
     * Users un-subscribe for data. 
     * @param u
     *      A “User” reference (a reference to themselves)
     * @param product
     *      The String stock symbol they wish to un-subscribe from.
     * @throws NotSubscribedException
     *      If the user is not subscribed for the stock.
     * @throws InvalidPublisherDataException 
     *      User can't be null
     *      Product can't be null or empty
     */
    public synchronized void unSubscribe(User u, String product) 
            throws NotSubscribedException, InvalidPublisherDataException {
        // user can't be null
        if (u == null) {
            throw new InvalidPublisherDataException("User can't be null.");
        }
        
        // product can't be null or empty
        if (Utils.isNullOrEmpty(product)) {
            throw new InvalidPublisherDataException("Product can't be null or empty.");
        }
        
        HashSet<User> subscribers = subscriptions.get(product);
        
        // check if user has not subscribed
        if (subscribers == null || !subscribers.contains(u)) {
            throw new NotSubscribedException("The user is not "
                    + "subscribed for the stock.");
        }
        
        // Remove the user from the subscriber list
        subscribers.remove(u);
    }
    
    /**
     * Get subscribers by product
     * @param product
     *      A String stock symbol
     * @return
     * @throws InvalidPublisherDataException 
     */
    protected HashSet<User> getSubscribers(String product) 
            throws InvalidPublisherDataException {
        // product can't be null or empty
        if (Utils.isNullOrEmpty(product)) {
            throw new InvalidPublisherDataException("Product can't be null or empty.");
        }
        
        return subscriptions.get(product);
    }
    
}
