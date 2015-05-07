package messages;


public enum MarketState {
	//public enum MarketState {OPEN, PREOPEN, CLOSED};

	OPEN("OPEN"),PREOPEN(""),CLOSED("CLOSED");

	private final String marketState;
	
	
	//need to finish this part
	private Market(String ms){
		marketState = ms;
	}
	
	public String getMarketState(){
		return marketState;
	}
	
	
	public String toString(){
		return "Market state:" + marketState;
	}

}
