package depaul.stockexchange.book;

@SuppressWarnings("serial")
public class NoSuchProductException extends Exception {

    public NoSuchProductException(String message) {
        super(message);
    }
}
