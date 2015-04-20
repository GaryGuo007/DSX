package TradeRelated;

import PriceRelated.Price;
import PriceRelated.PriceFactory;

public class Quote implements Tradable {
	String userName;
	String productSymbol;
	String buyQuoteSide;
	String sellQuoteSide;
	
	
	public Quote(String userName, String productSymbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume){
		
	}
	
	public QuoteSides getQuoteSide (String bs) {
	
	}

	
	public String getUserName(){
		return userName;
	 }
	
	public String getProduct (){
		return productSymbol;
	}
	public QuoteSide getQuoteSide (BookSide sideIn) {
		return  QuoteSide;
		}
	
		public String toString(){
			return userName + "quote: " + productSymbol +" "+buyQuoteSide.getPrice().toString()+" * "+ buyQuoteSide.getOriginalVolume()+
					"(Original Vol: " +	buyQuoteSide.getOriginalVolume() + " , CXL'd Vol: "+ buyQuoteSide.getCancelledVolume()+" ) " + "[" + userName +id + "]-" + sellQuoteSide.getPrice().toString() + " * "+buyQuoteSide.getOriginalVolume()+
					"(Original Vol: " +	sellQuoteSide.getOriginalVolume() + " , CXL'd Vol: "+ sellQuoteSide.getCancelledVolume()+" ) " + "[" + userName + id+"]";

		}
	}

