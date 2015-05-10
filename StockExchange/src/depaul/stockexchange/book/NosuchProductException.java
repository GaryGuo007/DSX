package depaul.stockexchange.book;

@SuppressWarnings("serial")
public class NosuchProductException extends Exception {

    public NosuchProductException(String message) {
        super(message);
    }
}
