package depaul.stockexchange.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;




//import javafx.scene.Parent;
import depaul.stockexchange.BookSide;
import depaul.stockexchange.Utils;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.*;
import depaul.stockexchange.publishers.*;
import depaul.stockexchange.tradable.*;
/**
 * A ProductBookSide object maintains the content of one side (Buy or Sell) of a Stock (product) �book�.
Recall that a stock�s �book� holds the buy and sell orders and quote sides for a stock that are not yet
tradable. The buy-side of the book contains all buy-side orders and quote sides that are not yet tradable
in descending order. The sell-side of the book contains all buy-side orders and quote sides that are not yet
tradable in ascending order.
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class ProductBookSide {
	
	BookSide side;
	private HashMap<Price, ArrayList<Tradable>> bookEntries = new HashMap< Price, ArrayList<Tradable>>();
	@SuppressWarnings("unused")
	private TradeProcessor tradeProcessor;
	private ProductBook parent;
	
	private void setBookSide(BookSide side){
		this.side = side;
	}
	
	
	private void setProductBook(ProductBook book) throws DataValidationException{
		if (book == null) throw new DataValidationException("The product book parsed in can't be null.");
	    this.parent = book;
	}
	
	public ProductBookSide(ProductBook book, BookSide side) throws DataValidationException{
		this.setProductBook(book);
		this.setBookSide(side);
		this.tradeProcessor = new TradeProcessorPriceTimeImpl(this);
	}
	
	
	/* This method will generate and return an ArrayList of TradableDTO�s containing information on all the orders in
this ProductBookSide that have remaining quantity for the specified user.
	*/
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName) throws Exception{
		ArrayList<TradableDTO> tradableObjects = new ArrayList<TradableDTO>();
		for(int i = 0; i < bookEntries.size(); i++){
			//Tradable t;
			if(userName == null || userName.isEmpty()) throw new NosuchProductException("User name doesn't exist.");
		}
		
		ArrayList<Tradable> orders = bookEntries.get(userName);
		if (orders != null){
			for(Tradable order: orders){
			   if (order.getUser() == userName && order.getRemainingVolume() > 0){
				   TradableDTO dto = new TradableDTO(order);
				   tradableObjects.add(dto);
			   }
			}
		}
		return tradableObjects;
	}
	
	//this method should be �package-visible� so no �public� or �private� keyword
	/*
	 * This method should return an ArrayList of the Tradables that are at the best price in the �bookEntries�
HashMap.
	 */
	synchronized ArrayList<Tradable> getEntriesAtTopOfBook(){
		if (bookEntries.isEmpty())
			return null;
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		Collections.sort(sorted); // Sort them
		if (side == BookSide.BUY) {Collections.reverse(sorted);} // Reverse them
		
        return bookEntries.get(sorted.get(0));
	  }
	/*
	 * This method should return an array of Strings, where each index holds a �Price x Volume� String.
	 */
	public synchronized String[] getBookDepth(){
		if (bookEntries.isEmpty()) {return new String[]{"<Empty>"};}
		String [] array = new String[bookEntries.size()];
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		Collections.sort(sorted); // Sort them
		if (side == BookSide.BUY) {Collections.reverse(sorted);} // Reverse them
		
		String s;
		for(int i = 0; i < sorted.size(); i++){
			ArrayList<Tradable> a = bookEntries.get(sorted.get(i));//All is a tradable arrayList, and sorted.get(i) is price
			int sum = 0;
			for(int j = 0; j < a.size(); j++){
				sum += a.get(j).getRemainingVolume();
			}
			s = sorted.get(i) + " X " + sum;
			array[i] = s;
		}
		return array;
		
	}
	
	/*
	 * This method should return all the Tradables in this book side at the specified price.
	 */
	synchronized ArrayList<Tradable> getEntriesAtPrice(Price price) {
		if(!bookEntries.containsKey(price)) return null;
		return bookEntries.get(price);
	}
	/*
	 * This method should return true if the product book (the �bookEntries� HashMap) contains a Market Price
	 */
	public synchronized boolean hasMarketPrice() {
		if (!bookEntries.containsKey(hasMarketPrice())) return true;
		return false;
	}
	
	/*
	 * This method should return true if the ONLY Price in this product�s book is a Market Price
	 */
	public synchronized boolean hasOnlyMarketPrice() {
		if(bookEntries.size()==1 && bookEntries.remove(hasMarketPrice())==null) return true;
		return false;
	}
	
	/*
	 * This method should return the best Price in the book side.
	 */
	public synchronized Price topOfBookPrice() {
		if(bookEntries.isEmpty()) return null;
		else{
			ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
			Collections.sort(sorted); // Sort them
			if (side == BookSide.BUY) {Collections.reverse(sorted);}// Reverse them
			 return sorted.get(0);
		}
		
	}
	/*
	 * This method should return the volume associated with the best Price in the book side.
	 */
	public synchronized int topOfBookVolume() {
		if(bookEntries.isEmpty())
			return 0;
		else{
			ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
			Collections.sort(sorted); // Sort them
			if (side == BookSide.BUY) {Collections.reverse(sorted);}// Reverse them
		int sum = 0;
		for(int i = 0; i< sorted.size(); i++){
			ArrayList<Tradable> array = bookEntries.get(sorted.get(i));
		for (int j = 0; j < sorted.size(); j++){
			sum+=array.get(j).getRemainingVolume();
		}
		}
		return sum;
		}
	}
	/*
	 * Returns true if the product book (the �bookEntries� HashMap) is empty, false otherwise.
	 */
	public synchronized boolean isEmpty() {
		if(bookEntries.isEmpty()) return true;
           return false;
	}
	
	
	private ArrayList<Tradable> getAllTradables(){
		ArrayList<Tradable> all = new ArrayList<>();
		for(Price price: bookEntries.keySet()){
			ArrayList<Tradable> tradables = bookEntries.get(price);
			for(Tradable t : tradables){
				all.add(t);
			}
		}
		return all;
	}
	/*
	 * This method should cancel every Order or QuoteSide at every price in the book
	 */
	public synchronized void cancelAll() throws Exception{
		/*
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet());
		Collections.sort(sorted);
		ArrayList<Tradable> tradables = getAllTradables();
		//if (side == BookSide.BUY) {Collections.reverse(sorted);}// Reverse them
		for(Tradable t: tradables){
//			ArrayList<Tradable> a = bookEntries.get(sorted.get(i));
//			for(int j = 0; j < a.size(); j++){
		//bookEntries.clear();
		submitOrderCancel(t.getId());
		submitQuoteCancel(t.getId());
			}
		}
	*/
	HashMap<Price, ArrayList<Tradable>> bookCopy = new HashMap<Price, ArrayList<Tradable>>(bookEntries);
	for(Price p : bookCopy.keySet()){
		ArrayList<Tradable> list = new ArrayList<Tradable>(bookCopy.get(p));
		  for(Tradable t: list){
			if(t.isQuote()){
				submitOrderCancel(t.getUser());
			}
			else submitQuoteCancel(t.getId());
		}
	}
	}	
	/*
	 * This method should search the book (the �bookEntries� HashMap) for a Quote from the specified user
	 */
	public synchronized TradableDTO removeQuote(String user) throws Exception {
		if(Utils.isNullOrEmpty(user)) throw new DataValidationException("User can't be null or empty.");
		Tradable tradableFound = null;
		for(Price price: bookEntries.keySet()){
			ArrayList<Tradable> tradables = bookEntries.get(price);
			for(Tradable t: tradables){
				if(user.equals(t.getUser())){
					tradableFound = t;
					tradables.remove(t);
					break;
			}
		}
		if(tradableFound != null && tradables.isEmpty()){
			bookEntries.remove(price);
			break;
		}
		}
	    if(tradableFound != null){
	    	return new TradableDTO(tradableFound);
	    }
	
		return null;
		}

	/*
	 * This method should cancel the Order (if possible) that has the specified identifier. This method should search
the book at all prices (the �bookEntries� HashMap) for the Order with the specified identifier.df
	 */
	@SuppressWarnings("null")
	public synchronized void submitOrderCancel(String orderId) throws DataValidationException, OrderNotFoundException {
		if(Utils.isNullOrEmpty(orderId)) throw new DataValidationException("OrderId can't be null or Empty");
		//String orderId = bookEntries.containsValue(orderId);
		Tradable tradableFound = null;
		for (Price price: bookEntries.keySet()){
			ArrayList<Tradable> tradables= bookEntries.get(price);
			for(Tradable t: tradables){
				if(orderId.equals(t.getId())){
					tradableFound = t;
					tradables.remove(t);
					break;
				}
			}
	
		
		if(tradableFound != null && tradables.isEmpty()){
			bookEntries.remove(price);
			break;
			}
		}
		if(tradableFound != null){
			String details = "Order" + tradableFound.getSide() + "- Side Cancelled";
			parent.publishCancel(tradableFound, details);
			addOldEntry(tradableFound);
		}
		else parent.checkTooLateToCancel(tradableFound.getId());
	}
	/*
	 * This method should cancel the QuoteSide (if possible) that has the specified userName.
	 */
	public synchronized void submitQuoteCancel(String userName) throws Exception {
		if(Utils.isNullOrEmpty(userName)) throw new DataValidationException("Username can't be null or empty.");
		TradableDTO dto = removeQuote(userName);
		if(dto == null){return;}
		else{
			String details = "Quote" + dto.side + "- Side Cancelled";
			CancelMessage cm = new CancelMessage(dto.user, dto.product, dto.price, dto.remainingVolume, details, dto.side, dto.id);;
			MessagePublisher.getInstance().publishCancel(cm);
			}
	}
	/*
	 * This method should add the Tradable passed in to the �parent� product book�s �old entries� list.
	 */
	public void addOldEntry(Tradable t) throws DataValidationException{
		if(t == null) throw new DataValidationException("The tradable can't be null.");
		parent.addOldEntry(t);
	}
	
/*
 * This method should add the Tradable passed in to the book (the �bookEntries� HashMap).
 */
	public synchronized void addToBook(Tradable trd) {
		if(!bookEntries.containsKey(trd.getPrice())){
		ArrayList<Tradable> array = new ArrayList<Tradable>();
		bookEntries.put(trd.getPrice(), array);
		
		}
		else{bookEntries.get(trd.getPrice());}
		
	}
	
	/*
	 * This method will attempt a trade the provided Tradable against entries in this ProductBookSide.
	 */
	public HashMap<String, FillMessage> tryTrade(Tradable trd) throws InvalidPublisherDataException, NotSubscribedException, InvalidMessageDataException{
		HashMap<String, FillMessage> allFills = new HashMap<String, FillMessage>();
		if(side.equals("BUY"))
			trySellAgainstBuySideTrade(trd);
		else if (side.equals("SELL"))
			tryBuyAgainstSellSideTrade(trd);
		for(int i = 0; i < allFills.size(); i++ ){
			FillMessage aFillMessage = allFills.get(allFills);
			MessagePublisher.getInstance().publishFill(aFillMessage);
		}
		return allFills;
	}
	/*
	 * This method will try to fill the SELL side Tradable passed in against the content of the book.
	 */
	public synchronized HashMap<String, FillMessage> trySellAgainstBuySideTrade(Tradable trd) throws InvalidMessageDataException{
		HashMap<String, FillMessage> allFills = new HashMap<String, FillMessage>();
		HashMap<String, FillMessage> fillMsgs = new HashMap<String, FillMessage>();
		//(trd.getRemainingVolume()> 0  && !bookEntries.isEmpty() && trd.getPrice() <= this.side.topOfBookPrice())
	
		while(trd.getRemainingVolume() > 0  && !bookEntries.isEmpty() && trd.getPrice().isMarket()) 
		{
			HashMap<String, FillMessage> doTrade = new HashMap<String, FillMessage>();
			fillMsgs = mergeFills(fillMsgs, doTrade);
			allFills.putAll(fillMsgs);
		}
		return allFills;
	}
	
	/*
	 * This method is designed to merge multiple fill messages together into one consistent list.
	 */
	//I haven't finish the getFillVolume() and setFillVolume() in MessageBase
	private HashMap<String, FillMessage> mergeFills(HashMap<String, FillMessage> existing, 
			HashMap<String, FillMessage> newOnes) throws InvalidMessageDataException{
		if (existing.isEmpty()){return new HashMap<String, FillMessage>(newOnes);}
		else{HashMap<String, FillMessage> results = new HashMap<>(existing);
		for (String key : newOnes.keySet()) { // For each Trade Id key in the �newOnes� HashMap
			if (!existing.containsKey(key)) { // If the �existing� HashMap does not have that key�
			results.put(key, newOnes.get(key)); // �then simply add this entry to the �results� HashMap
			} else { // Otherwise, the �existing� HashMap does have that key � we need to update the data
			FillMessage fm = results.get(key); // Get the FillMessage from the �results� HashMap
			// NOTE � for the below, you will need to make these 2 FillMessage methods �public�!
			fm.setFillVolume(newOnes.get(key).getFillVolume()); // Update the fill volume
			fm.setDetails(newOnes.get(key).getDetails()); // Update the fill details
			}
			}
			return results;
		}
	}
	
	/*
	 * This method will try to fill the BUY side Tradable passed in against the content of the book.
	 */
	public synchronized HashMap<String, FillMessage> tryBuyAgainstSellSideTrade(Tradable trd) throws InvalidMessageDataException{
		HashMap<String, FillMessage> allFills = new HashMap<String, FillMessage>();
		HashMap<String, FillMessage> fillMsgs = new HashMap<String, FillMessage>();
		
		//(trd.getRemainingVolume() > 0 && !bookEntries.isEmpty() && trd.getPrice() >= trd.topOfBookPrice())
		while( trd.getRemainingVolume() > 0 && !bookEntries.isEmpty() && trd.getPrice().isMarket()) {
			HashMap<String, FillMessage> doTrade = new HashMap<String, FillMessage>();
			fillMsgs = mergeFills(fillMsgs, doTrade);
			allFills.putAll(fillMsgs);
		}
		return allFills;

	}
	
/*
 * This method will remove an key/value pair from the book (the �bookEntries� HashMap) if the ArrayList
associated with the Price passed in is empty.
 */
	public synchronized void clearIfEmpty(Price p) {
		if (bookEntries.get(p).isEmpty())
		bookEntries.remove(p);
	}
	
	/*
	 * This method is design to remove the Tradable passed in from the book (when it has been traded or cancelled).
	 */
	public synchronized void removeTradable(Tradable t) {
		ArrayList<Tradable> entries = bookEntries.get(t.getPrice());
		if(entries == null) return;
		else {  
			 boolean n = entries.remove(t);
		     if (n == false) return;
		     else if (entries.isEmpty()){
		    	 clearIfEmpty(t.getPrice());
		     }
		
		}
	}
	

}
	


