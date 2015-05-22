
package gui;

import client.User;
import price.Price;


public class MarketDisplay {
    private User user;
    private boolean visible;
    private final UserDisplayManager outer;

    public MarketDisplay(User u, final UserDisplayManager outer) {
        this.outer = outer;
        user = u;
    }

    public void setVisible(boolean value) {
        visible = value;
        System.out.println(user.getUserName() + "'s Market Display is in 'Visible' mode");
    }

    public void updateMarketData(String product, Price bp, int bv, Price sp, int sv) {
        System.out.format("[%s's GUI], Update Market Data: %s: %s@%d -- %s@%d%n", user.getUserName(), product, bp, bv, sp, sv);
    }

    public void updateLastSale(String product, Price p, int v) {
        System.out.format("[%s's GUI], Update Last Sale: %s: %s@%d%n", user.getUserName(), product, p, v);
    }

    public void updateTicker(String product, Price p, char direction) {
        System.out.format("[%s's GUI], Update Ticker: %s: %s %c%n", user.getUserName(), product, p, direction);
    }

    public void updateMarketActivity(String activity) {
        System.out.format("[%s's GUI], Update Market Activity: %s%n", user.getUserName(), activity);
    }

    public void updateMarketState(String state) {
        System.out.format("[%s's GUI], Update Market State: %s%n", user.getUserName(), state);
    }
    
}
