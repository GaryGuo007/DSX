package messages;
/**
 * The MarketMessage class encapsulates data related to the “state” of the market. The possible market
 * states are: CLOSED, PREOPEN, and OPEN. Only these values are legal market states.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class MarketMessage {
	/*
	 * This data element should hold a market state value (one of CLOSED,
PREOPEN, and OPEN). How you represent [MARKET STATE] is up to you (String, constants, enumerated type,
etc). This value should be set in the MarketMessage constructor (so it needs to be passed into the
constructor).
	 */
	private MarketState state;
	
	public MarketMessage(MarketState state) throws InvalidArgumentException{
		if(state != MarketState.OPEN || state != MarketState.PREOPEN || state != MarketState.CLOSED){
			throw new InvalidArgumentException("Invalid: market state");
		}
		this.state = state;
	}
	
	public MarketState getState(){
		return state;
	}
	
	public String toString(){
		return "Market state = " + getState();
	}

}
