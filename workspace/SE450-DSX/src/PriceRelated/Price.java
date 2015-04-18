package PriceRelated;

public class Price {
	
	long price = 0;
	
	public long getPrice() {
		return price;
	}
	
	protect Price() {
		
	}
	
	protect Price(long price){
		this.price = price;
	}
	
	public Price add(Price p){
		long newPrice = this.getPrice() + p.getPrice();
		return new Price(newPrice);
	}
    
	public Price subtract(Price p){
		
	}
    
	public Price multiply(int p){
		
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
