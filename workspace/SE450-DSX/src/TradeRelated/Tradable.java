package TradeRelated;

import PriceRelated.Price;
import PriceRelated.PriceFactory;

public interface Tradable {
	String getProduct();
	Price getPrice(); 
	int getOriginalVolume();
	int getRemainingVolume(); 
	int getCancelledVolume(); 
	void setCancelledVolume(int newCancelledVolume); 
	void setRemainingVolume(int newRemainingVolume);
	String getUser();
	String getSide(); 
	boolean isQuote(); 
	String getId();
}
