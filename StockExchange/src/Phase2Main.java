package driver;

// HERE you should add any imports for your classes that you need to make this class compile.


public class Phase2Main {

    private static User u1, u2, u3, u4;

    public static void main(String[] args) {

        performLegacyTests();

        makeTestUsers();
        testCurrentMarketPublisher();
        testTickerPublisher();
        testLastSalePublisher();
        testMessagePublisher();

    }

    private static void performLegacyTests() {

        Price p1 = PriceFactory.makeLimitPrice("15.00");
        Price p2 = PriceFactory.makeLimitPrice("$15.00");
        Price p3 = PriceFactory.makeLimitPrice("15");

        // Insure Flyweight functionality
        System.out.println("1) The Strings '15.00 and '$15.00' and '15' should all result in the same Price object holding a long of 1500");
        boolean r = p1 == p2;
        System.out.println("   Is 'p1'(15.00) the same Price object as 'p2' ($15.00): " + r + " (" + (r ? "CORRECT" : "ERROR") + ")");
        r = p1 == p3;
        System.out.println("   Is 'p1'(15.00) the same Price object as 'p3' (15): " + r + " (" + (r ? "CORRECT" : "ERROR") + ")");
        r = p2 == p3;
        System.out.println("   Is 'p2'($15.00) the same Price object as 'p3' (15): " + r + " (" + (r ? "CORRECT" : "ERROR") + ")");

        r = p1 == p1;
        System.out.println("   Is 'p1'(15.00) the same Price object as 'p1' (15.00): " + r + " (" + (r ? "CORRECT" : "ERROR") + ")");
        r = p2 == p2;
        System.out.println("   Is 'p2'($15.00) the same Price object as 'p2' ($15.00): " + r + " (" + (r ? "CORRECT" : "ERROR") + ")");
        r = p3 == p3;
        System.out.println("   Is 'p3'(15) the same Price object as 'p3' (15): " + r + " (" + (r ? "CORRECT" : "ERROR") + ")");

        System.out.println("   Done with legacy tests\n");
    }

    private static void makeTestUsers() {
        u1 = new UserImpl("REX");
        u2 = new UserImpl("ANN");
        u3 = new UserImpl("OWL");
        u4 = new UserImpl("BEN");
    }
    
    private static void testCurrentMarketPublisher() {
        try {
            CurrentMarketPublisher.getInstance().subscribe(u1, "SBUX");
            CurrentMarketPublisher.getInstance().subscribe(u2, "IBM");
            CurrentMarketPublisher.getInstance().subscribe(u3, "AAPL");

            System.out.println("2) Publish Current Market for SBUX. Only user REX is subscribed, only REX gets a message:");
            MarketDataDTO mdo = new MarketDataDTO("SBUX", PriceFactory.makeLimitPrice("51.00"), 120, PriceFactory.makeLimitPrice("51.06"), 75);
            CurrentMarketPublisher.getInstance().publishCurrentMarket(mdo);
            System.out.println();

            System.out.println("3) Publish Current Market for IBM. Only user ANN is subscribed, only ANN gets a message:");
            MarketDataDTO mdo2 = new MarketDataDTO("IBM", PriceFactory.makeLimitPrice("205.85"), 300, PriceFactory.makeLimitPrice("205.98"), 220);
            CurrentMarketPublisher.getInstance().publishCurrentMarket(mdo2);
            System.out.println();

            System.out.println("4) Publish Current Market for GE. No user is subscribed, no user gets a message:");
            MarketDataDTO mdo3 = new MarketDataDTO("GE", PriceFactory.makeLimitPrice("22.70"), 40, PriceFactory.makeLimitPrice("22.78"), 110);
            CurrentMarketPublisher.getInstance().publishCurrentMarket(mdo3);
            System.out.println();

            CurrentMarketPublisher.getInstance().subscribe(u2, "SBUX");
            CurrentMarketPublisher.getInstance().subscribe(u3, "SBUX");
            CurrentMarketPublisher.getInstance().subscribe(u4, "SBUX");

            System.out.println("5) Publish Current Market for SBUX. Now all 4 users are subscribed so REX, ANN, OWL & BEN get a message");
            MarketDataDTO mdo4 = new MarketDataDTO("SBUX", PriceFactory.makeLimitPrice("51.04"), 225, PriceFactory.makeLimitPrice("51.12"), 117);
            CurrentMarketPublisher.getInstance().publishCurrentMarket(mdo4);
            System.out.println();

            CurrentMarketPublisher.getInstance().unSubscribe(u1, "SBUX");
            CurrentMarketPublisher.getInstance().unSubscribe(u2, "SBUX");
            CurrentMarketPublisher.getInstance().unSubscribe(u3, "SBUX");
            CurrentMarketPublisher.getInstance().unSubscribe(u4, "SBUX");

            System.out.println("6) Publish Current Market for SBUX. Now all 4 users are unsubscribed so no one get a message");
            MarketDataDTO mdo5 = new MarketDataDTO("SBUX", PriceFactory.makeLimitPrice("51.10"), 110, PriceFactory.makeLimitPrice("51.13"), 120);
            CurrentMarketPublisher.getInstance().publishCurrentMarket(mdo5);
            System.out.println("Done with Current Market tests\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testTickerPublisher() {
        try {
            TickerPublisher.getInstance().subscribe(u1, "SBUX");
            TickerPublisher.getInstance().subscribe(u2, "IBM");

            System.out.println("7) Publish Ticker for SBUX. Only user REX is subscribed, only REX gets a message (no arrows on message):");
            TickerPublisher.getInstance().publishTicker("SBUX", PriceFactory.makeLimitPrice("52.00"));
            System.out.println();

            System.out.println("8) Publish Ticker for IBM. Only user ANN is subscribed, only ANN gets a message (no arrows on message):");
            TickerPublisher.getInstance().publishTicker("IBM", PriceFactory.makeLimitPrice("204.85"));
            System.out.println();
            TickerPublisher.getInstance().unSubscribe(u1, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u2, "IBM");

            System.out.println("9) Publish Ticker for GE. No user is subscribed, no user gets a message:");
            TickerPublisher.getInstance().publishTicker("GE", PriceFactory.makeLimitPrice("22.70"));
            System.out.println();

            TickerPublisher.getInstance().subscribe(u1, "SBUX");
            TickerPublisher.getInstance().subscribe(u2, "SBUX");
            TickerPublisher.getInstance().subscribe(u3, "SBUX");
            TickerPublisher.getInstance().subscribe(u4, "SBUX");

            System.out.println("10) Publish Ticker for SBUX. Now all 4 users are subscribed so REX, ANN, OWL & BEN get a message (UP arrows on message):");
            TickerPublisher.getInstance().publishTicker("SBUX", PriceFactory.makeLimitPrice("52.10"));
            System.out.println();

            System.out.println("11) Publish Ticker for SBUX. Now all 4 users are subscribed so REX, ANN, OWL & BEN get a message (DOWN arrows on message):");
            TickerPublisher.getInstance().publishTicker("SBUX", PriceFactory.makeLimitPrice("51.90"));
            System.out.println(); 
            
            System.out.println("12) Publish Ticker for SBUX. Now all 4 users are subscribed so REX, ANN, OWL & BEN get a message (EQUALS sign on message):");
            TickerPublisher.getInstance().publishTicker("SBUX", PriceFactory.makeLimitPrice("51.90"));
            System.out.println();
            
            TickerPublisher.getInstance().unSubscribe(u1, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u2, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u3, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u4, "SBUX");

            System.out.println("13) Publish Ticker for SBUX. Now all 4 users are unsubscribed so no one get a message");
            TickerPublisher.getInstance().publishTicker("SBUX", PriceFactory.makeLimitPrice("52.12"));
            System.out.println("Done with Ticker tests\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testLastSalePublisher() {
        try {
            LastSalePublisher.getInstance().subscribe(u1, "SBUX");
            LastSalePublisher.getInstance().subscribe(u2, "IBM");
            TickerPublisher.getInstance().subscribe(u1, "SBUX");
            TickerPublisher.getInstance().subscribe(u2, "IBM");
            
            System.out.println("14) Publish Last Sale for SBUX. Only user REX is subscribed, only REX gets a message (Last Sale & Ticker with DOWN arrow):");
            LastSalePublisher.getInstance().publishLastSale("SBUX", PriceFactory.makeLimitPrice("51.00"), 120);
            System.out.println();

            System.out.println("15) Publish Last Sale for IBM. Only user ANN is subscribed, only ANN gets a message (Last Sale & Ticker with UP arrow):");
            LastSalePublisher.getInstance().publishLastSale("IBM", PriceFactory.makeLimitPrice("205.85"), 300);
            System.out.println();

            System.out.println("16) Publish Last Sale for GE. No user is subscribed, no user gets a message:");
            LastSalePublisher.getInstance().publishLastSale("GE", PriceFactory.makeLimitPrice("22.70"), 40);
            System.out.println();

            LastSalePublisher.getInstance().subscribe(u2, "SBUX");
            LastSalePublisher.getInstance().subscribe(u3, "SBUX");
            LastSalePublisher.getInstance().subscribe(u4, "SBUX");
            TickerPublisher.getInstance().subscribe(u2, "SBUX");
            TickerPublisher.getInstance().subscribe(u3, "SBUX");
            TickerPublisher.getInstance().subscribe(u4, "SBUX");
            
            System.out.println("17) Publish Last Sale for SBUX. Now all 4 users are subscribed so REX, ANN, OWL & BEN get a message (Last Sale & Ticker with EQUALS sign):");
            LastSalePublisher.getInstance().publishLastSale("SBUX", PriceFactory.makeLimitPrice("51.00"), 120);
            System.out.println();

            LastSalePublisher.getInstance().unSubscribe(u1, "SBUX");
            LastSalePublisher.getInstance().unSubscribe(u2, "SBUX");
            LastSalePublisher.getInstance().unSubscribe(u3, "SBUX");
            LastSalePublisher.getInstance().unSubscribe(u4, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u1, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u2, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u3, "SBUX");
            TickerPublisher.getInstance().unSubscribe(u4, "SBUX");
            
            System.out.println("18) Publish Last Sale for SBUX. Now all 4 users are unsubscribed so no one get a message");
            LastSalePublisher.getInstance().publishLastSale("SBUX", PriceFactory.makeLimitPrice("51.00"), 120);
            System.out.println("Done with Last Sale tests\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testMessagePublisher() {

        try {
            MessagePublisher.getInstance().subscribe(u1, "SBUX");
            MessagePublisher.getInstance().subscribe(u2, "SBUX");

            System.out.println("19) Send a CancelMessage to REX. REX is subscribed for SBUX messages so REX gets the message.");
            CancelMessage cm = new CancelMessage("REX", "SBUX", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishCancel(cm);
            System.out.println();
            
            System.out.println("20) Send a CancelMessage to REX. REX is NOT subscribed for IBM messages so REX does not get the message.");
            CancelMessage cm2 = new CancelMessage("REX", "IBM", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishCancel(cm2);
            System.out.println();
                        
            System.out.println("21) Send a CancelMessage to ANN. ANN is subscribed for SBUX messages so ANN gets the message.");
            CancelMessage cm3 = new CancelMessage("ANN", "SBUX", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishCancel(cm3);
            System.out.println();
            
            System.out.println("22) Send a CancelMessage to ANN. ANN is NOT subscribed for IBM messages so ANN does not get the message.");
            CancelMessage cm4 = new CancelMessage("ANN", "IBM", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishCancel(cm4);
            System.out.println();
            
            System.out.println("23) Send a FillMessage to REX. REX is subscribed for SBUX messages so REX gets the message.");
            FillMessage fm = new FillMessage("REX", "SBUX", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishFill(fm);
            System.out.println();
            
            System.out.println("24) Send a FillMessage to REX. REX is NOT subscribed for IBM messages so REX does not get the message.");
            FillMessage fm2 = new FillMessage("REX", "IBM", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishFill(fm2);
            System.out.println();
                        
            System.out.println("25) Send a FillMessage to ANN. ANN is subscribed for SBUX messages so ANN gets the message.");
            FillMessage fm3 = new FillMessage("ANN", "SBUX", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishFill(fm3);
            System.out.println();
            
            System.out.println("26) Send a FillMessage to ANN. ANN is NOT subscribed for IBM messages so ANN does not get the message.");
            FillMessage fm4 = new FillMessage("ANN", "IBM", PriceFactory.makeLimitPrice("52.00"), 140, "Cancelled By User", BookSide.BUY, "ABC123XYZ");
            MessagePublisher.getInstance().publishFill(fm4);
            System.out.println(); 
                      
            MessagePublisher.getInstance().unSubscribe(u2, "SBUX");
            System.out.println("27) Send a MarketMessage. REX is the only MessagePublisher subscriber so only REX gets the message.");
            MessagePublisher.getInstance().publishMarketMessage(new MarketMessage(GlobalConstants.MarketState.PREOPEN));
            System.out.println(); 
            
            MessagePublisher.getInstance().subscribe(u2, "SBUX");
            System.out.println("28) Send a MarketMessage. REX and ANN are MessagePublisher subscribers so REX & Ann get the message.");
            MessagePublisher.getInstance().publishMarketMessage(new MarketMessage(GlobalConstants.MarketState.OPEN));
            System.out.println(); 
            
            MessagePublisher.getInstance().unSubscribe(u1, "SBUX");
            MessagePublisher.getInstance().unSubscribe(u2, "SBUX");
            System.out.println("29) Send a MarketMessage. No users are MessagePublisher subscribers so No users get the message.");
            MessagePublisher.getInstance().publishMarketMessage(new MarketMessage(GlobalConstants.MarketState.CLOSED));
            System.out.println("Done with Message tests\n"); 

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    
    static class UserImpl implements User {

        private String uname;

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
