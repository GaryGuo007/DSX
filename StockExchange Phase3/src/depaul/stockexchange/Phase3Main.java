package depaul.stockexchange;


import depaul.stockexchange.*;
import depaul.stockexchange.book.*;
import depaul.stockexchange.client.*;
import depaul.stockexchange.messages.*;
import depaul.stockexchange.publishers.*;
import depaul.stockexchange.price.*;
import depaul.stockexchange.tradable.*;

import java.util.ArrayList;


// HERE you should add any imports for your classes that you need to make this class compile.
// You will need imports for User, Price, PriceFactory, CancelMessage, FillMessageOrder, 
// ProductServiceQuote, CurrentMarketPublisher, LastSalePublisher, MessagePublisher,
// TickerPublisher. Order, Quote & TradableDTO

// Where you see BookSide.BUY, replace that with YOUR representation of BUY (string, enum, etc).
// Where you see BookSide.SELL, replace that with YOUR representation of SELL (string, enum, etc).

// Where you see MarketState.CLOSED, replace that with YOUR representation of CLOSED (string, enum, etc).
// Where you see MarketState.PREOPEN, replace that with YOUR representation of PREOPEN (string, enum, etc).
// Where you see MarketState.OPEN, replace that with YOUR representation of OPEN (string, enum, etc).

public class Phase3Main {

    private static User u1, u2;

    public static void main(String[] args) {

        u1 = new UserImpl("REX");
        u2 = new UserImpl("ANN");

        String stock = "GOOG";
        setupSubscriptions(stock);

        doTestSet1(stock);
        doTestSet2(stock);
        doTestSet3(stock);
        doTestSet4(stock);
        
        System.out.println("DONE!");

    }

    private static void setupSubscriptions(String stockSymbol) {
        System.out.println("S1) Subscribe 2 test users (ANN, REX) for CurrentMarket, LastSale, Ticker & Messages (no output expected)");
        try {
            CurrentMarketPublisher.getInstance().subscribe(u1, stockSymbol);
            CurrentMarketPublisher.getInstance().subscribe(u2, stockSymbol);
            LastSalePublisher.getInstance().subscribe(u1, stockSymbol);
            LastSalePublisher.getInstance().subscribe(u2, stockSymbol);
            TickerPublisher.getInstance().subscribe(u1, stockSymbol);
            TickerPublisher.getInstance().subscribe(u2, stockSymbol);
            MessagePublisher.getInstance().subscribe(u1, stockSymbol);
            MessagePublisher.getInstance().subscribe(u2, stockSymbol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void doTestSet1(String stockSymbol) {
        try {
            System.out.println("TS1.1) Change Market State to PREOPEN then to OPEN. ANN & REX receive PREOPEN and OPEN Market messages");
            ProductService.getInstance().setMarketState(MarketState.PREOPEN);
            ProductService.getInstance().setMarketState(MarketState.OPEN);
            System.out.println();

            System.out.println("TS1.2) User " + u1.getUserName() + " cancels a non-existent order - should result in an exception");
            ProductService.getInstance().submitOrderCancel(stockSymbol, BookSide.BUY, "ABC123");
            System.out.println("Did not catch non-existent Stock error!");
        } catch (Exception ex) {
            System.out.println("Exception properly caught error: " + ex.getMessage());
            System.out.println();
        }

        try {
            System.out.println("TS1.3) User " + u1.getUserName() + " cancels a non-existent quote - No exception should be thrown");
            ProductService.getInstance().submitQuoteCancel(u1.getUserName(), stockSymbol);
            System.out.println("Did not catch non-existent quote problem!!");
            System.out.println();
        } catch (Exception ex) {
            System.out.println("Caught Exception as expected: " + ex.getMessage());
            System.out.println();
        }

        try {
            System.out.println("TS1.4) Try to create a bad product - null String");
            ProductService.getInstance().createProduct(null);
            System.out.println("Did not catch bad product - null String problem!!");
            System.out.println();
        } catch (Exception ex) {
            System.out.println("Caught Exception on bad product: " + ex.getMessage());
            System.out.println();
        }

        try {
            System.out.println("TS1.5) User " + u1.getUserName() + " enters order on non-existent stock");
            ProductService.getInstance().submitOrder(new Order(u1.getUserName(), "X11", PriceFactory.makeLimitPrice("$641.10"), 111, BookSide.BUY));
            System.out.println("Did not catch non-existent Stock error!");
        } catch (Exception ex) {
            System.out.println("Caught Exception on order for bad class: " + ex.getMessage());
            System.out.println();
        }

        try {
            System.out.println("TS1.6) User " + u1.getUserName() + " enters quote on non-existent stock");
            ProductService.getInstance().submitQuote(new Quote(u1.getUserName(), "X11", PriceFactory.makeLimitPrice("$641.10"), 120, PriceFactory.makeLimitPrice("$641.15"), 150));
            System.out.println("Did not catch non-existent Stock error!");
        } catch (Exception ex) {
            System.out.println("Caught Exception on quote for bad class: " + ex.getMessage());
            System.out.println();
        }
        System.out.println("------------------");
    }

    private static void doTestSet2(String stockSymbol) {
        System.out.println("TS2.1) Check the initial Market State (should say OPEN): " + ProductService.getInstance().getMarketState() + "\n");

        System.out.println("TS2.2) Create a new Stock product in our Trading System: " + stockSymbol);
        try {
            ProductService.getInstance().createProduct(stockSymbol);
            System.out.println("Product " + stockSymbol + " successfully created!");
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when creating product " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.3) Query the ProductService for all Stock products (should say [" + stockSymbol + "]): "
                + ProductService.getInstance().getProductList() + "\n");

        System.out.println("TS2.4) Change Market State to Closed and verify the Market State");
        try {
            ProductService.getInstance().setMarketState(MarketState.CLOSED);
            System.out.println("Product State: " + ProductService.getInstance().getMarketState());
        } catch (Exception ex) {
            System.out.println("Set market State caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.5) Change Market State to PreOpen and verify the Market State");
        try {
            ProductService.getInstance().setMarketState(MarketState.PREOPEN);
            System.out.println("Product State: " + ProductService.getInstance().getMarketState());
        } catch (Exception ex) {
            System.out.println("Set market State caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.6) Change Market State to Open and verify the Market State");
        try {
            ProductService.getInstance().setMarketState(MarketState.OPEN);
            System.out.println("Product State: " + ProductService.getInstance().getMarketState());
        } catch (Exception ex) {
            System.out.println("Set market State caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.7) User " + u1.getUserName() + " enters a quote, REX & ANN receive Current Market updates for GOOG [120@$641.10 - 150@$641.15]: ");
        try {
            ProductService.getInstance().submitQuote(
                    new Quote(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.10"), 120, PriceFactory.makeLimitPrice("$641.15"), 150));
            System.out.println("Submitting a Quote was successful!");
        } catch (Exception ex) {
            System.out.println("Submitting a Quote caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.8) Verify Quote is in Book [Should see buy & sell side as $641.10 x 120 and : $641.15 x 150]");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.9) Get MarketDataDTO for " + stockSymbol + "(Your format might vary but the data content should be the same)");
        try {
            System.out.println(ProductService.getInstance().getMarketData(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting market data for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.10) Cancel the Quote, REX receives BUY and SELL side cancel messages, REX & ANN receive Current Market updated:");
        try {
            ProductService.getInstance().submitQuoteCancel(u1.getUserName(), stockSymbol);
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting market data for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.11) Verify Quote is NOT in Book: ");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS2.12) Get MarketDataDTO for " + stockSymbol);
        try {
            System.out.println(ProductService.getInstance().getMarketData(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting market data for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("------------------");
    }

    private static void doTestSet3(String stockSymbol) {

        System.out.println("TS3.1) Change Market State to Closed then PreOpen the Market State. Rex & ANN should receive one market message for each state");
        try {
            ProductService.getInstance().setMarketState(MarketState.CLOSED);
            ProductService.getInstance().setMarketState(MarketState.PREOPEN);
        } catch (Exception ex) {
            System.out.println("Set market State caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.2) User " + u2.getUserName() + " enters a quote, REX and ANN receive Current Market messages: ");
        try {
            ProductService.getInstance().submitQuote(new Quote(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.10"), 120, PriceFactory.makeLimitPrice("$641.15"), 150));
        } catch (Exception ex) {
            System.out.println("Submitting a Quote caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.3) User " + u1.getUserName() + " enters several BUY orders, REX and ANN receive 5 Current Market updates each: ");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.10"), 111, BookSide.BUY));
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.11"), 222, BookSide.BUY));
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.12"), 333, BookSide.BUY));
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.13"), 444, BookSide.BUY));
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.14"), 555, BookSide.BUY));
        } catch (Exception ex) {
            System.out.println("Submitting Orders caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.4) Verify Book [should be 5 BUY entries and 1 sell entry]: ");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.5) User " + u2.getUserName() + " enters several Sell orders - no Current Market received - none of the orders improves the market: ");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.16"), 111, BookSide.SELL));
            ProductService.getInstance().submitOrder(
                    new Order(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.17"), 222, BookSide.SELL));
            ProductService.getInstance().submitOrder(
                    new Order(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.18"), 333, BookSide.SELL));
            ProductService.getInstance().submitOrder(
                    new Order(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.19"), 444, BookSide.SELL));
            ProductService.getInstance().submitOrder(
                    new Order(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.20"), 555, BookSide.SELL));
        } catch (Exception ex) {
            System.out.println("Submitting Orders caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.6) Verify Book [should be 5 BUY entries and 6 sell entries]: ");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.7) User " + u1.getUserName() + " enters a BUY order that won't trade as market is in PREOPEN. Rex & Ann receive Current Market updates: ");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("$641.15"), 105, BookSide.BUY));
        } catch (Exception ex) {
            System.out.println("Submitting an Order caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.8) Change Market State to OPEN State...Trade should occur.");
        System.out.println("     ANN & REX should receive OPEN Market Message");
        System.out.println("     ANN & REX each receive a Fill Msg, Current Market Msg, Last Sale Msg, & Ticker Msg:");
        try {
            ProductService.getInstance().setMarketState(MarketState.OPEN);
        } catch (Exception ex) {
            System.out.println("Set market State caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.9) Verify Resulting Book [should be 5 buy-side entries, 6 sell-side entries]: ");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.10) User " + u1.getUserName() + " enters a big MKT BUY order to trade with all the SELL side:");
        System.out.println("        ANN receives 6 Fill Messages, a Current Market, a Last Sale & a Ticker");
        System.out.println("        REX receives 1 Fill Message, a Current Market, a Last Sale & a Ticker, and a cancel for the 40 remaining MKT order quantity not traded");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeMarketPrice(), 1750, BookSide.BUY));
        } catch (Exception ex) {
            System.out.println("Submitting an Order caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.11) Verify Resulting Book [should be 5 buy-side entries, NO sell-side entries]: ");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.12) Get Orders with Remaining Quantity: ");
        ArrayList<TradableDTO> ords;
        try {
            ords = ProductService.getInstance().getOrdersWithRemainingQty(u1.getUserName(), stockSymbol);
            for (TradableDTO dto : ords) {
                System.out.println(dto);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting  Orders With Remaining Qty: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.13) Change Market State to CLOSED State. Both users should get a Market message.");
        System.out.println("        REX receives 5 Cancel Messages, and a Current Market Update.");
        System.out.println("        ANN receives 1 Cancel Message, and a Current Market Update.");
        try {
            ProductService.getInstance().setMarketState(MarketState.CLOSED);
        } catch (Exception ex) {
            System.out.println("Set market State caused an unexpected exception: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS3.14) Verify Book: ");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();
        System.out.println("------------------");
    }

    private static void doTestSet4(String stockSymbol) {
        System.out.println("TS4.1) Change Market State to PREOPEN then to OPEN. ANN & REX receive Market PREOPEN and OPEN messages:");
        try {
            ProductService.getInstance().setMarketState(MarketState.PREOPEN);
            ProductService.getInstance().setMarketState(MarketState.OPEN);
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when setting market state " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS4.2) User " + u1.getUserName() + " enters a BUY order, ANN & REX receive Current Market message:");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice(64130), 369, BookSide.BUY));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when entering order " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS4.3) User " + u2.getUserName() + " enters a SELL order.");
        System.out.println("    ANN & REX receive 1 Fill Message each, as well as a Current Market, a Last Sale & a Ticker message:");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice(64130), 369, BookSide.SELL));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when entering order " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS4.4) User " + u1.getUserName() + " enters a MKT BUY order. REX receives cancel message because there is no market to trade with:");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeMarketPrice(), 456, BookSide.BUY));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when entering order " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS4.5) User " + u1.getUserName() + " enters a BUY order, ANN & REX receive Current Market message:");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u1.getUserName(), stockSymbol, PriceFactory.makeLimitPrice("641.1"), 151, BookSide.BUY));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when entering order " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS4.6) User " + u2.getUserName() + " enters a SELL order to trade.");
        System.out.println("    ANN & REX receive 1 Fill Message each, as well as a Current Market, a Last Sale & a Ticker message:");
        try {
            ProductService.getInstance().submitOrder(
                    new Order(u2.getUserName(), stockSymbol, PriceFactory.makeLimitPrice(64110), 51, BookSide.SELL));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when entering order " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS4.7) Change Market State to CLOSED State...Both users should get a Market message, many Cancel Messages, and a Current Market Update.");
        try {
            ProductService.getInstance().setMarketState(MarketState.CLOSED);
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when setting market state " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();

        System.out.println("TS4.8) Verify Book: ");
        try {
            printOutBD(ProductService.getInstance().getBookDepth(stockSymbol));
        } catch (Exception ex) {
            System.out.println("Unexpected Exception occurred when getting book depth for " + stockSymbol + ": " + ex.getMessage());
        }
        System.out.println();
    }

    private static void printOutBD(String[][] bd) {
        String[] buy = bd[0];
        String[] sell = bd[1];
        System.out.println("Buy Side:");
        for (String s : buy) {
            System.out.println("\t" + s);
        }
        System.out.println("Sell Side:");
        for (String s : sell) {
            System.out.println("\t" + s);
        }
    }

    //////////////////////////////////////////
    static class UserImpl implements User {

        private final String uname;

        public UserImpl(String u) {
            uname = u;
        }

        @Override
        public String getUserName() {
            return uname;
        }

        @Override
        public void acceptLastSale(String product, Price p, int v) {
            System.out.println("User " + getUserName() + " Received Last Sale for " + product + " " + v + "@" + p);
        }

        @Override
        public void acceptMessage(FillMessage fm) {
            System.out.println("User " + getUserName() + " Received Fill Message: " + fm);
        }

        @Override
        public void acceptMessage(CancelMessage cm) {
            System.out.println("User " + getUserName() + " Received Cancel Message: " + cm);
        }

        @Override
        public void acceptMarketMessage(String message) {
            System.out.println("User " + getUserName() + " Received Market Message: " + message);
        }

        @Override
        public void acceptTicker(String product, Price p, char direction) {
            System.out.println("User " + getUserName() + " Received Ticker for " + product + " " + p + " " + direction);
        }

        @Override
        public void acceptCurrentMarket(String product, Price bp, int bv, Price sp, int sv) {
            System.out.println("User " + getUserName() + " Received Current Market for " + product + " " + bv + "@" + bp + " - " + sv + "@" + sp);
        }
    }
}
/*
S1) Subscribe 2 test users (ANN, REX) for CurrentMarket, LastSale, Ticker & Messages (no output expected)

TS1.1) Change Market State to PREOPEN then to OPEN. ANN & REX receive PREOPEN and OPEN Market messages
User ANN Received Market Message: PREOPEN
User REX Received Market Message: PREOPEN
User ANN Received Market Message: OPEN
User REX Received Market Message: OPEN

TS1.2) User REX cancels a non-existent order - should result in an exception
Exception properly caught error: Product GOOG does not exist

TS1.3) User REX cancels a non-existent quote - No exception should be thrown
Caught Exception as expected: Product GOOG does not exist

TS1.4) Try to create a bad product - null String
Caught Exception on bad product: Product cannot be null

TS1.5) User REX enters order on non-existent stock
Caught Exception on order for bad class: Product X11 does not exist

TS1.6) User REX enters quote on non-existent stock
Caught Exception on quote for bad class: Product X11 does not exist

------------------
TS2.1) Check the initial Market State (should say OPEN): OPEN

TS2.2) Create a new Stock product in our Trading System: GOOG
Product GOOG successfully created!

TS2.3) Query the ProductService for all Stock products (should say [GOOG]): [GOOG]

TS2.4) Change Market State to Closed and verify the Market State
User ANN Received Market Message: CLOSED
User REX Received Market Message: CLOSED
User REX Received Current Market for GOOG 0@$0.00 - 0@$0.00
User ANN Received Current Market for GOOG 0@$0.00 - 0@$0.00
Product State: CLOSED

TS2.5) Change Market State to PreOpen and verify the Market State
User ANN Received Market Message: PREOPEN
User REX Received Market Message: PREOPEN
Product State: PREOPEN

TS2.6) Change Market State to Open and verify the Market State
User ANN Received Market Message: OPEN
User REX Received Market Message: OPEN
Product State: OPEN

TS2.7) User REX enters a quote, REX & ANN receive Current Market updates for GOOG [120@$641.10 - 150@$641.15]: 
User REX Received Current Market for GOOG 120@$641.10 - 150@$641.15
User ANN Received Current Market for GOOG 120@$641.10 - 150@$641.15
Submitting a Quote was successful!

TS2.8) Verify Quote is in Book [Should see buy & sell side as $641.10 x 120 and : $641.15 x 150]
Buy Side:
	$641.10 x 120
Sell Side:
	$641.15 x 150

TS2.9) Get MarketDataDTO for GOOG(Your format might vary but the data content should be the same)
GOOG 120@$641.10 x 150@$641.15

TS2.10) Cancel the Quote, REX receives BUY and SELL side cancel messages, REX & ANN receive Current Market updated:
User REX Received Cancel Message: User: REX, Price: $641.10, Volume: 120, Details: Quote BUY-Side Cancelled, Side: BUY
User REX Received Cancel Message: User: REX, Price: $641.15, Volume: 150, Details: Quote SELL-Side Cancelled, Side: SELL
User REX Received Current Market for GOOG 0@$0.00 - 0@$0.00
User ANN Received Current Market for GOOG 0@$0.00 - 0@$0.00

TS2.11) Verify Quote is NOT in Book: 
Buy Side:
	<Empty>
Sell Side:
	<Empty>

TS2.12) Get MarketDataDTO for GOOG
GOOG 0@$0.00 x 0@$0.00

------------------
TS3.1) Change Market State to Closed then PreOpen the Market State. Rex & ANN should receive one market message for each state
User ANN Received Market Message: CLOSED
User REX Received Market Message: CLOSED
User ANN Received Market Message: PREOPEN
User REX Received Market Message: PREOPEN

TS3.2) User ANN enters a quote, REX and ANN receive Current Market messages: 
User REX Received Current Market for GOOG 120@$641.10 - 150@$641.15
User ANN Received Current Market for GOOG 120@$641.10 - 150@$641.15

TS3.3) User REX enters several BUY orders, REX and ANN receive 5 Current Market updates each: 
User REX Received Current Market for GOOG 231@$641.10 - 150@$641.15
User ANN Received Current Market for GOOG 231@$641.10 - 150@$641.15
User REX Received Current Market for GOOG 222@$641.11 - 150@$641.15
User ANN Received Current Market for GOOG 222@$641.11 - 150@$641.15
User REX Received Current Market for GOOG 333@$641.12 - 150@$641.15
User ANN Received Current Market for GOOG 333@$641.12 - 150@$641.15
User REX Received Current Market for GOOG 444@$641.13 - 150@$641.15
User ANN Received Current Market for GOOG 444@$641.13 - 150@$641.15
User REX Received Current Market for GOOG 555@$641.14 - 150@$641.15
User ANN Received Current Market for GOOG 555@$641.14 - 150@$641.15

TS3.4) Verify Book [should be 5 BUY entries and 1 sell entry]: 
Buy Side:
	$641.14 x 555
	$641.13 x 444
	$641.12 x 333
	$641.11 x 222
	$641.10 x 231
Sell Side:
	$641.15 x 150

TS3.5) User ANN enters several Sell orders - no Current Market received - none of the orders improves the market: 

TS3.6) Verify Book [should be 5 BUY entries and 6 sell entries]: 
Buy Side:
	$641.14 x 555
	$641.13 x 444
	$641.12 x 333
	$641.11 x 222
	$641.10 x 231
Sell Side:
	$641.15 x 150
	$641.16 x 111
	$641.17 x 222
	$641.18 x 333
	$641.19 x 444
	$641.20 x 555

TS3.7) User REX enters a BUY order that won't trade as market is in PREOPEN. Rex & Ann receive Current Market updates: 
User REX Received Current Market for GOOG 105@$641.15 - 150@$641.15
User ANN Received Current Market for GOOG 105@$641.15 - 150@$641.15

TS3.8) Change Market State to OPEN State...Trade should occur.
     ANN & REX should receive OPEN Market Message
     ANN & REX each receive a Fill Msg, Current Market Msg, Last Sale Msg, & Ticker Msg:
User ANN Received Market Message: OPEN
User REX Received Market Message: OPEN
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.15, Fill Volume: 105, Details: leaving 45, Side: SELL
User REX Received Fill Message: User: REX, Product: GOOG, Fill Price: $641.15, Fill Volume: 105, Details: leaving 0, Side: BUY
User REX Received Current Market for GOOG 555@$641.14 - 45@$641.15
User ANN Received Current Market for GOOG 555@$641.14 - 45@$641.15
User REX Received Last Sale for GOOG 105@$641.15
User ANN Received Last Sale for GOOG 105@$641.15
User REX Received Ticker for GOOG $641.15  
User ANN Received Ticker for GOOG $641.15  

TS3.9) Verify Resulting Book [should be 5 buy-side entries, 6 sell-side entries]: 
Buy Side:
	$641.14 x 555
	$641.13 x 444
	$641.12 x 333
	$641.11 x 222
	$641.10 x 231
Sell Side:
	$641.15 x 45
	$641.16 x 111
	$641.17 x 222
	$641.18 x 333
	$641.19 x 444
	$641.20 x 555

TS3.10) User REX enters a big MKT BUY order to trade with all the SELL side:
        ANN receives 6 Fill Messages, a Current Market, a Last Sale & a Ticker
        REX receives 1 Fill Message, a Current Market, a Last Sale & a Ticker, and a cancel for the 40 remaining MKT order quantity not traded
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.16, Fill Volume: 111, Details: leaving 0, Side: SELL
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.19, Fill Volume: 444, Details: leaving 0, Side: SELL
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.17, Fill Volume: 222, Details: leaving 0, Side: SELL
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.18, Fill Volume: 333, Details: leaving 0, Side: SELL
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.15, Fill Volume: 45, Details: leaving 0, Side: SELL
User REX Received Fill Message: User: REX, Product: GOOG, Fill Price: $641.15, Fill Volume: 1710, Details: leaving 40, Side: BUY
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.20, Fill Volume: 555, Details: leaving 0, Side: SELL
User REX Received Current Market for GOOG 555@$641.14 - 0@$0.00
User ANN Received Current Market for GOOG 555@$641.14 - 0@$0.00
User REX Received Last Sale for GOOG 1710@$641.20
User ANN Received Last Sale for GOOG 1710@$641.20
User REX Received Ticker for GOOG $641.20 ↑
User ANN Received Ticker for GOOG $641.20 ↑
User REX Received Cancel Message: User: REX, Price: MKT, Volume: 40, Details: Cancelled, Side: BUY

TS3.11) Verify Resulting Book [should be 5 buy-side entries, NO sell-side entries]: 
Buy Side:
	$641.14 x 555
	$641.13 x 444
	$641.12 x 333
	$641.11 x 222
	$641.10 x 231
Sell Side:
	<Empty>

TS3.12) Get Orders with Remaining Quantity: 
Product: GOOG, Price: $641.14, OriginalVolume: 555, RemainingVolume: 555, CancelledVolume: 0, User: REX, Side: BUY, IsQuote: false, Id: REXGOOG$641.14347028135786894
Product: GOOG, Price: $641.13, OriginalVolume: 444, RemainingVolume: 444, CancelledVolume: 0, User: REX, Side: BUY, IsQuote: false, Id: REXGOOG$641.13347028134314419
Product: GOOG, Price: $641.12, OriginalVolume: 333, RemainingVolume: 333, CancelledVolume: 0, User: REX, Side: BUY, IsQuote: false, Id: REXGOOG$641.12347028133524094
Product: GOOG, Price: $641.11, OriginalVolume: 222, RemainingVolume: 222, CancelledVolume: 0, User: REX, Side: BUY, IsQuote: false, Id: REXGOOG$641.11347028129483929
Product: GOOG, Price: $641.10, OriginalVolume: 111, RemainingVolume: 111, CancelledVolume: 0, User: REX, Side: BUY, IsQuote: false, Id: REXGOOG$641.10347028128738644

TS3.13) Change Market State to CLOSED State. Both users should get a Market message.
        REX receives 5 Cancel Messages, and a Current Market Update.
        ANN receives 1 Cancel Message, and a Current Market Update.
User ANN Received Market Message: CLOSED
User REX Received Market Message: CLOSED
User REX Received Cancel Message: User: REX, Price: $641.14, Volume: 555, Details: BUY Order Cancelled, Side: BUY
User REX Received Cancel Message: User: REX, Price: $641.13, Volume: 444, Details: BUY Order Cancelled, Side: BUY
User REX Received Cancel Message: User: REX, Price: $641.12, Volume: 333, Details: BUY Order Cancelled, Side: BUY
User REX Received Cancel Message: User: REX, Price: $641.11, Volume: 222, Details: BUY Order Cancelled, Side: BUY
User ANN Received Cancel Message: User: ANN, Price: $641.10, Volume: 120, Details: Quote BUY-Side Cancelled, Side: BUY
User REX Received Cancel Message: User: REX, Price: $641.10, Volume: 111, Details: BUY Order Cancelled, Side: BUY
User REX Received Current Market for GOOG 0@$0.00 - 0@$0.00
User ANN Received Current Market for GOOG 0@$0.00 - 0@$0.00

TS3.14) Verify Book: 
Buy Side:
	<Empty>
Sell Side:
	<Empty>

------------------
TS4.1) Change Market State to PREOPEN then to OPEN. ANN & REX receive Market PREOPEN and OPEN messages:
User ANN Received Market Message: PREOPEN
User REX Received Market Message: PREOPEN
User ANN Received Market Message: OPEN
User REX Received Market Message: OPEN

TS4.2) User REX enters a BUY order, ANN & REX receive Current Market message:
User REX Received Current Market for GOOG 369@$641.30 - 0@$0.00
User ANN Received Current Market for GOOG 369@$641.30 - 0@$0.00

TS4.3) User ANN enters a SELL order.
    ANN & REX receive 1 Fill Message each, as well as a Current Market, a Last Sale & a Ticker message:
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.30, Fill Volume: 369, Details: leaving 0, Side: SELL
User REX Received Fill Message: User: REX, Product: GOOG, Fill Price: $641.30, Fill Volume: 369, Details: leaving 0, Side: BUY
User REX Received Current Market for GOOG 0@$0.00 - 0@$0.00
User ANN Received Current Market for GOOG 0@$0.00 - 0@$0.00
User REX Received Last Sale for GOOG 369@$641.30
User ANN Received Last Sale for GOOG 369@$641.30
User REX Received Ticker for GOOG $641.30 ↑
User ANN Received Ticker for GOOG $641.30 ↑

TS4.4) User REX enters a MKT BUY order. REX receives cancel message because there is no market to trade with:
User REX Received Cancel Message: User: REX, Price: MKT, Volume: 456, Details: Cancelled, Side: BUY

TS4.5) User REX enters a BUY order, ANN & REX receive Current Market message:
User REX Received Current Market for GOOG 151@$641.10 - 0@$0.00
User ANN Received Current Market for GOOG 151@$641.10 - 0@$0.00

TS4.6) User ANN enters a SELL order to trade.
    ANN & REX receive 1 Fill Message each, as well as a Current Market, a Last Sale & a Ticker message:
User REX Received Fill Message: User: REX, Product: GOOG, Fill Price: $641.10, Fill Volume: 51, Details: leaving 100, Side: BUY
User ANN Received Fill Message: User: ANN, Product: GOOG, Fill Price: $641.10, Fill Volume: 51, Details: leaving 0, Side: SELL
User REX Received Current Market for GOOG 100@$641.10 - 0@$0.00
User ANN Received Current Market for GOOG 100@$641.10 - 0@$0.00
User REX Received Last Sale for GOOG 51@$641.10
User ANN Received Last Sale for GOOG 51@$641.10
User REX Received Ticker for GOOG $641.10 ↓
User ANN Received Ticker for GOOG $641.10 ↓

TS4.7) Change Market State to CLOSED State...Both users should get a Market message, many Cancel Messages, and a Current Market Update.
User ANN Received Market Message: CLOSED
User REX Received Market Message: CLOSED
User REX Received Cancel Message: User: REX, Price: $641.10, Volume: 100, Details: BUY Order Cancelled, Side: BUY
User REX Received Current Market for GOOG 0@$0.00 - 0@$0.00
User ANN Received Current Market for GOOG 0@$0.00 - 0@$0.00

TS4.8) Verify Book: 
Buy Side:
	<Empty>
Sell Side:
	<Empty>

DONE!
*/