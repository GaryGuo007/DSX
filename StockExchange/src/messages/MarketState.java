package messages;

public enum MarketState {
	OPEN("OPEN"),PREOPEN(""),CLOSED("CLOSED");

	private final String marketState;
	
	
	//need to finish this part
	private Market(String ms){
		
	}
	
	public String getMarketState(){
		return marketState;
	}
	
	
	public String toString(){
		return "Market state:" + marketState;
	}

}
