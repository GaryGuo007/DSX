package publishers;

import price.Price;
import tradable.Tradable;
/**
 * The MarketDataDTO class is based upon the “Data Transfer Object” pattern. This DTO will be used to
 * encapsulate a set of data elements that detail the values that make up the current market. This
 * information is needed by many components of our trading system so rather then pass “real” market
 * data objects from the trading system, or pass each of the 5 data elements individually, this DTO will be
 * used to facilitate the data transfer. This class will work with the publishers so it should be put into a
 * package (i.e., “publishers”) that you will soon add the publishers to.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class MarketDataDTO {
	/*
	 * The stock product (i.e., IBM) that these market data elements describe.
	 */
	public String product;
	/*
	 * The current BUY side price of the Stock
	 */
	public Price buyPrice;
	/*
	 * The current BUY side volume (quantity) of the Stock
	 */
			
	public int buyVolume;
	/*
	 * The current SELL side price of the Stock
	 */
	 public Price sellPrice;
	 /*
	  * The current SELL side volume (quantity) of the Stock
	  */
	 public int sellVolume;
	 
public MarketDataDTO(String product, Price buyPrice, int buyVolume, Price sellPrice, 
		int sellVolume) {
	        this.product = product;
	        this.buyPrice = buyPrice;
	        this.sellPrice = sellPrice;
	        this.buyVolume = buyVolume;
	        this.sellVolume = sellVolume;
	       
	    }

//	    public MarketDataDTO(MarketData d) throws Exception {
//	        this.product = d.getProduct();
//	        this.price = 
//	        this.buyVolume = d.buylVolume();
//	        this.sellVolume = d.getsellVolume();
//	        
//	    } 
	    public String toString() {
			return String.format(getClass().getName()+ "Product: %s, buyPrice: %s, buyVolume: %s, sellPrice: %s, sellVolume: %s "
					, getProduct(),getbuyPrice(), getbuyVolume(), getsellPrice(), getsellVolume());
		}
	

}
