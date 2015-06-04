package depaul.stockexchange.messages;
/**
 * The MarketMessage class encapsulates data related to the �state� of the market. The possible market
 * states are: CLOSED, PREOPEN, and OPEN. Only these values are legal market states.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class MarketMessage {

    /**
     * This data element should hold a market state value
     * (one of CLOSED, PREOPEN, and OPEN). 
     */
    private MarketState state;
    
    public MarketMessage(MarketState state) {
        this.setState(state);
    }
    
    public MarketState getState() {
        return this.state;
    }

    private void setState(MarketState state) {
        this.state = state;
    }
    
    
    /**
     * Displays the information content of the object.
     * @return 
     */
    @Override
    public String toString() {
        return String.format("Market State: %s", 
                this.getState());
    }
}
