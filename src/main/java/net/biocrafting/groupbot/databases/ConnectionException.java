package net.biocrafting.groupbot.databases;

public class ConnectionException extends Exception {
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 7053543063992649662L;
	
	public ConnectionException(String message) {
		super(message);
	}
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
