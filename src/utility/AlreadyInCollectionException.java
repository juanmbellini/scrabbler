package utility;

@SuppressWarnings("serial")
public class AlreadyInCollectionException extends RuntimeException {
	
	public AlreadyInCollectionException() {
		super();
	}
	
	public AlreadyInCollectionException(String message) {
		super(message);
	}

}
