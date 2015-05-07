package publishers;

public class AlreadySubscribedException extends Exception{
	static final long serialVersionUID = 1;
	public AlreadySubscribedException() {
		super();
	}
	public AlreadySubscribedException(String message) {
		super(message);
	}
}
