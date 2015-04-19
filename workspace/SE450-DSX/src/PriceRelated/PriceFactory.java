package PriceRelated;
import java.util.Hashtable;


public class PriceFactory {
	private static Hashtable<Long, Price> PriceHash = new Hashtable<Long, Price>();
	private static Price a = new Price();
	//private static Price 
	
	
	public static Price build(long v){
		if(!PriceHash.containsKey(v)){
		   Price p = new Price(v);
		   PriceHash.put(v, p);
		   return p;
		}
		else return PriceHash.get(v);
	}
	
	
	public static Price makeLimitPrice(String value){
		long v = (long)( Double.parseDouble(value.replaceAll("[$,]", ""))*100.0);
		Price p = build(v);
		return p;
	}
	
	public static Price makeLimitPrice(long value){
		Price p = build(value);
		return p;
		
	}
	
	
    public static Price makeMarketPrice(){
    	
    	return a;
		
	}
	
	

}
