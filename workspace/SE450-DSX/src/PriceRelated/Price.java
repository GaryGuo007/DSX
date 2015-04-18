package PriceRelated;

public class Price {
	
	private long price = 0;
	private boolean isMarket;
	
	public long getPrice() {return price;}
	
    public boolean getIsMarket(){return isMarket;}
	Price() {isMarket= true;}
	
	Price(long price){
		this.price = price;
	}
	
	public Price add(Price p){
		long newPrice = this.getPrice() + p.getPrice();
		return new Price(newPrice);
		
		if(p == null || isMarket == true || p.getIsMarket()==true)
			throw new InvalidPriceOperation("");
	}
    
	public Price subtract(Price p){
		
		
		if(p == null || isMarket == true || p.getIsMarket()==true)
			throw new InvalidPriceOperation("");
		return p2;
		
	}
    
	public Price multiply(int p){
		
		if(p == null || isMarket == true || p.getIsMarket()==true)
			throw new InvalidPriceOperation("");
		
	}
    
	public int compareTo(Price p){
		
	}
    
	public boolean greaterOrEqual(Price p){
		
	}
	
	public boolean greaterThan(Price p){
		
	}
	
	public boolean lessOrEqual(Price p){
		
	}
	
	public boolean lessThan(Price p) {
		
	}
	
	public boolean equals(Price p){
		
	}
	
	public boolean isMarket() {
		return false;
		
	}
	
	public boolean isNegative(){
		
	}
	
	public String toString(){
		
	}

}
