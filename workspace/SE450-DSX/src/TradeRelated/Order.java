package TradeRelated;

import PriceRelated.Price;

public class Order {
	String userName;
	String stock symbol;
	String id;
	String side;
	Price Price;
	String originalVolume;
	String remainingOrderVolume;
	String cancelledOrderVolume;
	
	public constructor Order(String userName, String productSymbol, Price orderPrice, int originalVolume, String side);
	String id = new ID; 
	ID = userName + productSymbol + orderPrice + System.nanoTime();
	
	public String toString(){
		return userName + "order: " + side +  originalVolume + "productSymbol" + "at " + Price  + "ID:" + id;
	}
}
