package TradeRelated;

import PriceRelated.PriceFactory;
import PriceRelated.Price;

public class Order implements Tradable{
	String userName;
	String stockSymbol;
	String id;
	String side;
	Price Price;
	String originalVolume;
	String remainingOrderVolume;
	String cancelledOrderVolume;
	
	public String getProduct(){
		return stockSymbol;
	}
	
	public int getRemainingVolume(){
		return remainingOrderVolume;
	}
	
	public setRemainingVolume(){
		
	}
	
	public  Order(String userName, String productSymbol, Price orderPrice, int originalVolume, BookSide side){
	String id = new ID; 
	ID = userName + productSymbol + orderPrice + System.nanoTime();
	}
	public String toString(){
		return userName + "order: " + side +  originalVolume + "productSymbol" + "at " + Price  + "ID:" + id;
	}
}
