package PriceRelated;

public class Quote {
	String userName;
	String productSymbol;
	String buyQuoteSide;
	String sellQuoteSide;
	
	public constructor Quote(String userName, String productSymbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume);
	
	public String getUserName(){
	return userName;
	}
	
	public String getProduct (){
		return productSymbol;
		public QuoteSide getQuoteSide (BookSide sideIn) {//– Returns a copy of the BUY or SELL QuoteSide object, depending
		//upon which side is specified in the “sideIn” parameter.
		return 
		}
		
		public String toString(){
			return userName + "order: " + side +  originalVolume + "productSymbol" + "at " + Price  + "ID:" + id;

		}
	}
}
