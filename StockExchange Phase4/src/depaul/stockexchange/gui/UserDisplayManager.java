/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package depaul.stockexchange.gui;

import depaul.stockexchange.client.User;
import depaul.stockexchange.price.InvalidPriceOperation;
import depaul.stockexchange.price.Price;

/**
 *
 * @author hieldc
 */
public class UserDisplayManager {

    private User user;
    private MarketDisplay marketDisplay;

    public UserDisplayManager(User u) {
        user = u;
        marketDisplay = new MarketDisplay(u);
    }

    public void showMarketDisplay() throws Exception {
        marketDisplay.setVisible(true);
    }

    public void updateMarketData(String product, Price bp, int bv, Price sp, int sv) throws Exception {
        marketDisplay.updateMarketData(product, bp, bv, sp, sv);
    }
    
    public void updateLastSale(String product, Price p, int v) throws InvalidPriceOperation {
        marketDisplay.updateLastSale(product, p, v);
    }
    
    public void updateTicker(String product, Price p, char direction) {
        marketDisplay.updateTicker(product, p, direction);
    }
    
    public void updateMarketActivity(String activityText) {
        marketDisplay.updateMarketActivity(activityText);
    }
    
    public void updateMarketState(String message) {
        marketDisplay.updateMarketState(message);
    }
}
