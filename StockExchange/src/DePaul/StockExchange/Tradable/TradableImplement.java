package DePaul.StockExchange.Tradable;

import DePaul.StockExchange.InvalidPriceOperation;
import DePaul.StockExchange.Price.Price;


public class TradableImplement  implements Tradable{
	
	private String userName;
	private String productSymbol;
	private String id;
	private String side;
	private Price price;
	private String originalVolume;
	private String remainingVolume;
	private int cancelledVolume;
	private boolean quote; 
	
	
	public TradableImplement( String userName,String productSymbol, Price price, int originalVolume, String side, String id, boolean quote)throws InvalidPriceOperation
	{
		setUserName(userName);
		setProductSymbol(productSymbol);
		setPrice(price);
		setOriginalVolume(originalVolume);
		setSide(side);
		setId(id);
		setQuote(quote);
	}
	
	private void setQuote(boolean quote) {
		this.quote = quote;
	}

	private void setUserName(String userNameIn)throws InvalidPriceOperation
	{
		if(userNameIn==null)
			throw new InvalidPriceOperation(" NULL value passed in setUserName. ");
		if(userNameIn.length()<=0||userNameIn.length()>40)
			throw new InvalidPriceOperation("The bad parameter passed in setUserName: "+userNameIn);
		this.userName = userNameIn.toUpperCase().trim();
	}
	
    private void setProductSymbol( String productSymbol)throws InvalidPriceOperation
     {
    	if(productSymbol==null)
			throw new InvalidPriceOperation(" NULL value passed in setProductSymbol. ");
		if(productSymbol.length()<=0||productSymbol.length()>40)
			throw new InvalidPriceOperation("The bad parameter passed insetProductSymbol: "+productSymbol);
    	 this.productSymbol=productSymbol.toUpperCase().trim();
     }
    
    private void setPrice(Price price) throws InvalidPriceOperation
    {
    	if(price==null)
			throw new InvalidPriceOperation(" NULL value passed in setPrice. ");
	
    	this.price= price;
    }
    
    private void setOriginalVolume( int originalVolume) throws InvalidPriceOperation
    {
    	
		if(originalVolume<=0)
			throw new InvalidPriceOperation("The bad parameter passed in setOriginalVolume: "+originalVolume);
    	this.originalVolume=Integer.toString(originalVolume);
    	this.remainingVolume= Integer.toString(originalVolume);
    }
    
   private void setSide( String side) throws InvalidPriceOperation
   {
	   if(side==null)
			throw new InvalidPriceOperation(" NULL value passed in setSide. ");
		if(!side.toUpperCase().trim().equals("BUY")&&!side.toUpperCase().trim().equals("SELL"))
			throw new InvalidPriceOperation("The bad parameter passed in setSide: "+side);
	   this.side=side.toUpperCase().trim();
   }
    private void setId( String id) throws InvalidPriceOperation
    {
    	if(id==null)
			throw new InvalidPriceOperation(" NULL value passed in setId. ");
		if(id.length()<=0)
			throw new InvalidPriceOperation("The bad parameter passed insetId: "+side);
    	this.id=id;
    }
    
    
    
    
    
	public String getProduct() {
		
		return this.productSymbol;
	}

	
	public Price getPrice() {
		
		return this.price;
	}

	@Override
	public int getOriginalVolume() {
		
		return Integer.parseInt(this.originalVolume);
	}

	@Override
	public int getRemainingVolume() {
		
		return Integer.parseInt(this.remainingVolume);
	}

	@Override
	public int getCancelledVolume() {
		
		return this.cancelledVolume;
	}

	@Override
	public void setCancelledVolume(int newCancelledVolume) throws InvalidPriceOperation{
		
		//this should be changed with the Exception by myself
		if(newCancelledVolume<0||newCancelledVolume+getRemainingVolume()>this.getOriginalVolume())
		{
		         new InvalidPriceOperation( newCancelledVolume <0?"newRemainingVolume is passed in setRemainingVolume is negative":
				"Requested new Cancelled Volume ("+newCancelledVolume+") plus the remaining Volume ("+getRemainingVolume()+") exceeds the tradable's Original Volume ("+getOriginalVolume()+")");

		}
		this.cancelledVolume= newCancelledVolume;
		
	}

	@Override
	public void setRemainingVolume(int newRemainingVolume) throws Exception 
	{
		//this should be changed with the Exception by myself
				if(newRemainingVolume<0||this.getCancelledVolume()+newRemainingVolume>this.getOriginalVolume())
				{
					throw new InvalidPriceOperation( newRemainingVolume <0?"newRemainingVolume is passed in setRemainingVolume is negative":
						"Requested new Remaining Volume ("+newRemainingVolume+") plus the Cancelled Volume ("+getCancelledVolume()+") exceeds the tradable's Original Volume ("+getOriginalVolume()+")");
				}
				this.remainingVolume= Integer.toString(newRemainingVolume);
	}
	
	

	@Override
	public String getUser() {
		
		return this.userName;
	}

	@Override
	public String getSide() {
		
		return this.side;
	}

	@Override
	public boolean isQuote() {
		
		return this.quote;
	}

	@Override
	public String getId() {
		
		return this.id;
	}

}
