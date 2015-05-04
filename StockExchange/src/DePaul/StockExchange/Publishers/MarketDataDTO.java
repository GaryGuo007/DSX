package DePaul.StockExchange.Publishers;

import DePaul.StockExchange.Price.Price;
import DePaul.StockExchange.Tradable.Tradable;

public class MarketDataDTO {
	public String product;
	public Price buyPrice;
	public int buyVolume;
	 public Price sellPrice;
	 public int sellVolume;
	 
public MarketDataDTO(String product, Price buyPrice, int buyVolume, Price sellPrice, 
		int sellVolume) {
	        this.product = product;
	        this.price = 
	        this.buyVolume = buyVolume;
	        this.sellVolume = sellVolume;
	       
	    }

	    public MarketDataDTO(MarketData d) throws Exception {
	        this.product = d.getProduct();
	        this.price = 
	        this.buyVolume = d.buylVolume();
	        this.sellVolume = d.getsellVolume();
	        
	    } 
	 public String toString() {
		 StringBuilder sb = new StringBuilder();
	        sb.append(String.format("Product: %s," product ));
	        sb.append(String.format(", buyVolume: %s", buyPrice: %s, buyVolume, buyPrice));
	        sb.append(String.format(", sellVolume: %s, sellPrice: %s", sellVolume, sellPrice));
	        return sb.toString();	
		}
	

}
