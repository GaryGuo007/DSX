package tradable;


import depaulStockExchange.InvalidTradableValue;
import price.Price;

/**
 * A factory help to create price object.
 * 
 * @author      Xin Guo
 * @author      Yuancheng Zhang
 * @author      Junmin Liu
 */
public class TradableImpleFactory {
	
    /**
     * Build a tradable implement.
     * It could be a Order or be a QuoteSide
     * 
     * @param userName
     *          the user name (e.g. "ARI")
     * @param productSymbol
     *          the product symbol (e.g. "FB")
     * @param orderPrice
     *          the QuoteSides price (e.g. $257.09)
     * @param originalVolume
     *          the original volume (i.e., the original quantity)
     * @param side
     *          "BUT" or "SELL" side
     * @param isQuote
     *          Is it a QuoteSide class or Order class
     *          true: QuoteOrder Class
     *          false: Order Class
     * @return
     *          A implement of Tradable interface
     * @throws InvalidTradableValue 
     *          If one or more parameter(s) is(are) invalid, there will be an
     *          exception.
     */
    public static Tradable build(String userName,String productSymbol, 
            Price orderPrice, int originalVolume, String side, boolean isQuote) 
                 throws InvalidTradableValue
    {
        if (isQuote) {
            return new QuoteSide(userName, productSymbol, orderPrice, 
                    originalVolume, side);
        }
        return new Order(userName, productSymbol, orderPrice, 
                originalVolume, side);
    }

	

}