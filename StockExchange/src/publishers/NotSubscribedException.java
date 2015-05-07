package publishers;

public class NotSubscribedException extends Exception{
	static final long serialVersionUID = 1;
	public NotSubscribedException() {
		super();
	}
	public NotSubscribedException(String message) {
		super(message);
	}

}
