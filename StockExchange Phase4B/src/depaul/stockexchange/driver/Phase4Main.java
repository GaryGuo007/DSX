package depaul.stockexchange.driver;
// HERE you should add any imports for your classes that you need to make this class compile.

import depaul.stockexchange.BookSide;
import java.util.ArrayList;

//import depaul.stockexchange.*;
import depaul.stockexchange.book.*;
import depaul.stockexchange.client.*;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.price.*;

public class Phase4Main {

    private static ArrayList<User> users = new ArrayList<User>();
    private static int testCount = 1;

    public static void main(String[] args) {

        try {
            System.out.println("A) Add some stocks to the Trading System: IBM, CBOE, GOOG, AAPL, GE, T");
            String[] products = {"IBM", "CBOE", "GOOG", "AAPL", "GE", "T"};
            for (String prod : products) {
                ProductService.getInstance().createProduct(prod);
            }
            System.out.println();

            System.out.println("B) Create 3 users: REX, ANN, RAJ");

            users.add(new UserImpl("REX"));
            users.add(new UserImpl("ANN"));
            users.add(new UserImpl("RAJ"));
            System.out.println();

            System.out.println("C) Connect users REX, ANN, RAJ to the trading system");
            for (int i = 0; i < 3; i++) {
                users.get(i).connect();
            }
            System.out.println();

            System.out.println("D) Subscribe users REX, ANN, RAJ to Current Market, Last Sale, Ticker, and Messages for all Stocks");
            for (int i = 0; i < 3; i++) {
                for (String prod : products) {
                    users.get(i).subscribeCurrentMarket(prod);
                    users.get(i).subscribeLastSale(prod);
                    users.get(i).subscribeMessages(prod);
                    users.get(i).subscribeTicker(prod);
                }
                users.get(i).showMarketDisplay();
            }
            System.out.println();

            System.out.println("E) User REX queries market state");
            System.out.println(users.get(0).getUserName() + ": Market State Query: " + users.get(0).getMarketState());
            System.out.println();

            System.out.println("F) Put the market in PREOPEN state");
            ProductService.getInstance().setMarketState(MarketState.PREOPEN);
            System.out.println();

            System.out.println("G) User ANN queries market state");
            System.out.println(users.get(1).getUserName() + ": Market State Query: " + users.get(0).getMarketState());
            System.out.println();

            System.out.println("H) Put the market in OPEN state");
            ProductService.getInstance().setMarketState(MarketState.OPEN);
            System.out.println();

            System.out.println("I) User RAJ queries market state");
            System.out.println(users.get(2).getUserName() + ": Market State Query: " + users.get(0).getMarketState());
            System.out.println();

            runTests(1, "GOOG");
            runTests(2, "IBM");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void runTests(int runCount, String stock) {
        try {

            System.out.println(runCount + ".1) User REX submits an order for " + stock + ", BUY 100@40.00");
            String rexO1 = users.get(0).submitOrder(stock, PriceFactory.makeLimitPrice("$40.00"), 100, BookSide.BUY);
            System.out.println();

            System.out.println(runCount + ".2) User ANN submits a quote for " + stock + ", 100@40.00 x 100@40.50");
            users.get(1).submitQuote(stock, PriceFactory.makeLimitPrice("$40.00"), 100, PriceFactory.makeLimitPrice("$40.50"), 100);
            System.out.println();

            System.out.println(runCount + ".3) User RAJ submits an Order for " + stock + ", SELL 135@40.50");
            String o2 = users.get(2).submitOrder(stock, PriceFactory.makeLimitPrice("$40.50"), 135, BookSide.SELL);
            System.out.println();

            System.out.println(runCount + ".4) User REX does a Book Depth query for " + stock);
            System.out.println("Book Depth: " + users.get(0).getBookDepth(stock)[0][0] + " -- " + users.get(0).getBookDepth(stock)[1][0]);
            System.out.println();

            System.out.println(runCount + ".5) User REX does a query for their orders with remaining quantity for " + stock);
            System.out.println(users.get(0).getUserName() + " Orders With Remaining Qty: " + users.get(0).getOrdersWithRemainingQty(stock));
            System.out.println();

            System.out.println(runCount + ".6) User ANN does a query for their orders with remaining quantity for " + stock);
            System.out.println(users.get(1).getUserName() + " Orders With Remaining Qty: " + users.get(1).getOrdersWithRemainingQty(stock));
            System.out.println();

            System.out.println(runCount + ".7) User RAJ does a query for their orders with remaining quantity for " + stock);
            System.out.println(users.get(2).getUserName() + " Orders With Remaining Qty: " + users.get(2).getOrdersWithRemainingQty(stock));
            System.out.println();

            System.out.println(runCount + ".8) User REX cancels their order");
            users.get(0).submitOrderCancel(stock, BookSide.BUY, rexO1);
            System.out.println();

            System.out.println(runCount + ".9) User ANN cancels their quote");
            users.get(1).submitQuoteCancel(stock);
            System.out.println();

            System.out.println(runCount + ".10) User RAJ cancels their order");
            users.get(2).submitOrderCancel(stock, BookSide.SELL, o2);
            System.out.println();

            System.out.println(runCount + ".11) Display position values for all users");
            for (User u : users) {
                System.out.print(u.getUserName() + " Stock Value: " + u.getAllStockValue());
                System.out.print(", Account Costs: " + u.getAccountCosts());
                System.out.println(", Net Value: " + u.getNetAccountValue());
            }
            System.out.println();

            System.out.println(runCount + ".12) User REX enters an order for " + stock + ", BUY 100@$10.00");
            users.get(0).submitOrder(stock, PriceFactory.makeLimitPrice("$10.00"), 100, BookSide.BUY);
            System.out.println();

            System.out.println(runCount + ".13) User ANN enters a quote for " + stock + ", 100@$10.00 x 100@10.10");
            users.get(1).submitQuote(stock, PriceFactory.makeLimitPrice("$10.00"), 100, PriceFactory.makeLimitPrice("$10.10"), 100);
            System.out.println();

            System.out.println(runCount + ".14) User RAJ enters an order for " + stock + ", SELL 150@$10.00 - results in a trade");
            String[][] st = users.get(2).getBookDepth(stock);

            users.get(2).submitOrder(stock, PriceFactory.makeLimitPrice("$10.00"), 150, BookSide.SELL);
            System.out.println();

            System.out.println(runCount + ".15) User REX does a Book Depth query for " + stock);
            System.out.println("IBM Book Depth: " + users.get(0).getBookDepth(stock)[0][0] + " -- " + users.get(0).getBookDepth(stock)[1][0]);
            System.out.println();

            System.out.println(runCount + ".16) User REX enters a market order for " + stock + ", SELL 75@MKT - results in a trade");
            users.get(0).submitOrder(stock, PriceFactory.makeMarketPrice(), 75, BookSide.BUY);
            System.out.println();

            System.out.println(runCount + ".17) User ANN does a Book Depth query for " + stock);
            System.out.println("IBM Book Depth: " + users.get(1).getBookDepth(stock)[0][0] + " -- " + users.get(1).getBookDepth(stock)[1][0]);
            System.out.println();

            System.out.println(runCount + ".18) User ANN cancels her quote for " + stock);
            users.get(1).submitQuoteCancel(stock);
            System.out.println();

            System.out.println(runCount + ".19) Show stock holdings for all users");
            System.out.println(users.get(0).getUserName() + " Holdings: " + users.get(0).getHoldings());
            System.out.println(users.get(1).getUserName() + " Holdings: " + users.get(1).getHoldings());
            System.out.println(users.get(2).getUserName() + " Holdings: " + users.get(2).getHoldings());
            System.out.println();

            System.out.println(runCount + ".20) Show order Id's  for all users");
            System.out.println(users.get(0).getUserName() + " Orders: " + users.get(0).getOrderIds());
            System.out.println(users.get(1).getUserName() + " Orders: " + users.get(1).getOrderIds());
            System.out.println(users.get(2).getUserName() + " Orders: " + users.get(2).getOrderIds());
            System.out.println();

            System.out.println(runCount + ".21) Show positions for all users");
            for (User u : users) {
                System.out.print(u.getUserName() + " Stock Value: " + u.getAllStockValue());
                System.out.print(", Account Costs: " + u.getAccountCosts());
                System.out.println(", Net Value: " + u.getNetAccountValue());
            }
            System.out.println();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
