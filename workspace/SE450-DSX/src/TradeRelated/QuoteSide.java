package TradeRelated;
import PriceRelated.PriceFactory;
import PriceRelated.Price;
public class QuoteSide implements Tradable {
	String userName;
	String stockSymbol;
	String id;
	String side;
	Price Price;
	String originalVolume;
	String remainingOrderVolume;
	String cancelledOrderVolume;
	
	public QuoteSide(QuoteSide qs){
		
	}
	
	public String getProduct(){
		return stockSymbol;
	}
	
	public int getRemainingVolume(){
		return remainingOrderVolume;
	}
	
	public setRemainingVolume(){
		
	}
	
}
