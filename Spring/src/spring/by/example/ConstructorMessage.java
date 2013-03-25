package spring.by.example;

public class ConstructorMessage {
	private String message = null;

	/**
	 * Constructor
	 */
	public ConstructorMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}