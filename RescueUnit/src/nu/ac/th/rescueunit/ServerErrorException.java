package nu.ac.th.rescueunit;

public class ServerErrorException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1964312185510092973L;
	public static final int NO_CONNECTION = R.string.no_connection;
	public static final int NO_RESPOND = R.string.no_respond;		// Has connection but fail to receive respond from server
	
	public ServerErrorException() {
		super();
	}

	public ServerErrorException(int stringId) {
		super(stringId);
	}
}
