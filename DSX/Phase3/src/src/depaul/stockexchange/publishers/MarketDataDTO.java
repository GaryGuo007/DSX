package depaul.stockexchange.publishers;

import depaul.stockexchange.price.Price;
/**
 * This DTO will be used to encapsulate a set of data elements 
 * that detail the values that make up the current market. 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class MarketDataDTO {
    
    /**
     * The stock product (i.e., IBM) that these market data elements describe.
     */
    public String product;
    
    /**
     * The current BUY side price of the Stock.
     */	
    public Price buyPrice;
    
    
    /**
     * The current BUY side volume (quantity) of the Stock.
     */
    public int buyVolume;
    
    /**
     * The current SELL side price of the Stock.
     */
    public Price sellPrice;
    

    /**
     * The current SELL side volume (quantity) of the Stock.
     */
    public int sellVolume;
	 
    public MarketDataDTO(String product, Price buyPrice, int buyVolume, 
        Price sellPrice, int sellVolume) {
        this.product = product;
        this.buyPrice = buyPrice;
        this.buyVolume = buyVolume;
        this.sellPrice = sellPrice;
        this.sellVolume = sellVolume;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Product: %s,", product));
        sb.append(String.format(", buyVolume: %s, buyPrice: %s", buyVolume, buyPrice));
        sb.append(String.format(", sellVolume: %s, sellPrice: %s", sellVolume, sellPrice));
        return sb.toString();	
    }
	

}
