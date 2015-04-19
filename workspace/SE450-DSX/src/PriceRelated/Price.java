package PriceRelated;

public class Price {
	
	private long price;
	private boolean isMarket;
	
	public long getPrice() {return price;}
	
    public boolean getIsMarket(){return isMarket;}
	Price() {isMarket= true;}
	
	Price(long price){
		this.price = price;
	}
	
	public Price add(Price p) throws InvalidPriceOperation{
		long newPrice = this.getPrice() + p.getPrice();
		
		
		if(p == null || isMarket == true || p.getIsMarket()==true)
			throw new InvalidPriceOperation(""); 
		return new Price(newPrice);
	}
    
	public Price subtract(Price p) throws InvalidPriceOperation{
		Price p2 = null;
		
		if(p == null || isMarket == true || p.getIsMarket()==true)
			throw new InvalidPriceOperation("");
		return p2;
		
	}
    
	public Price multiply(int p) throws InvalidPriceOperation{
		Price p2 = null;
		if(isMarket == false) p2 = PriceFactory.makeLimitPrice(price*p);
		if(isMarket == true) throw new InvalidPriceOperation("");
		return p2;
		
	}
    
	public int compareTo(Price p) throws InvalidPriceOperation{
		int result = -2;
		if(p == null || isMarket == true || p.getIsMarket()==true)
			throw new InvalidPriceOperation("");
		if(price < p.getPrice()) return 1;
		else if (price > p.getPrice()) return -1;
		else if (price == p.getPrice()) return 0;
		return result;
		
	}
    
	public boolean greaterOrEqual(Price p) throws InvalidPriceOperation{
		if(p == null || isMarket == true || p.getIsMarket() == true)
			throw new InvalidPriceOperation("");
		
		if(price <= p.getPrice()) return true;
		return false;
		
		
	}
	
	public boolean greaterThan(Price p) throws InvalidPriceOperation{
		if(p == null || isMarket == true || p.getIsMarket() == true)
			throw new InvalidPriceOperation("");
		if(price < p.getPrice()) return true;
		return false;
		
		
	}
	
	public boolean lessOrEqual(Price p) throws InvalidPriceOperation{
		if(p == null || isMarket == true || p.getIsMarket() == true)
			throw new InvalidPriceOperation("");
		if(price >= p.getPrice()) return true;
		return false;
	}
	
	public boolean lessThan(Price p) throws InvalidPriceOperation{
		if(p == null || isMarket == true || p.getIsMarket() == true)
			throw new InvalidPriceOperation("");
		
		if(price > p.getPrice()) return true;
		return false;
	}
	
	public boolean equals(Price p) throws InvalidPriceOperation{
		if(p == null || isMarket == true || p.getIsMarket() == true)
			throw new InvalidPriceOperation("");
		
		if(price == p.getPrice()) return true;
		return false;
	}
	
	public boolean isMarket() {
		if(isMarket == true) return true;
		return false;
		
	}
	
	public boolean isNegative(){
		if(isMarket) return false;
		if(price < 0) return true;
		return false;
		
	}
	
	public String toString(){
		if(isMarket) return "MKT";
		else{
			Long payment = this.price;
			String output = String.format("$%, .2f", payment/100.0);
			return output;
		}
		
	}

}
