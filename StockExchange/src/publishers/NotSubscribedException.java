package publishers;

public class NotSubscribedException extends Exception{
	static final long serialVersionUID = 1;
	public NotSubscribedException(String message) {
		super(message);
	}

}
