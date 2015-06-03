/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package depaul.stockexchange.gui;

//import depaul.stockexchange.client.Position;
import depaul.stockexchange.client.User;
import depaul.stockexchange.price.*;

/**
 * @author Xin Guo
 * @author Yuancheng Zhang
 * @author Junmin Liu
 */
public class UserDisplayManager {

	private User user;
	private MarketDisplay marketDisplay;

	private volatile static UserDisplayManager instance;

	public static UserDisplayManager getInstance() {
		if (instance == null) {
			synchronized (UserDisplayManager.class) {
				if (instance == null) {
					instance = new UserDisplayManager();
				}
			}
		}
		System.out.println("no p.");
		return instance;
	}

	public UserDisplayManager() {
	}

	public UserDisplayManager(User u) {
		user = u;
		marketDisplay = new MarketDisplay(u, this);
	}

	public void showMarketDisplay() throws Exception {
		marketDisplay.setVisible(true);
	}

	public void updateMarketData(String product, Price bp, int bv, Price sp,
			int sv) {
		marketDisplay.updateMarketData(product, bp, bv, sp, sv);
	}

	public void updateLastSale(String product, Price p, int v) {
		marketDisplay.updateLastSale(product, p, v);
	}

	public void updateTicker(String product, Price p, char direction) {
		marketDisplay.updateTicker(product, p, direction);
	}

	public String updateMarketActivity(String activityText) {
		marketDisplay.updateMarketActivity(activityText);
		return activityText;
	}

	public void updateMarketState(String message) {
		marketDisplay.updateMarketState(message);
	}
}
