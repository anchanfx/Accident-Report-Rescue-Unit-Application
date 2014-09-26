package nu.ac.th.rescueunit;

public class ApplicationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8639327542189645446L;
	public static int UNKNOWN = R.string.unknown;
	
	private int stringId;
	
	public ApplicationException() { 
		this(UNKNOWN); 
	}
	
	public ApplicationException(int stringId) {
		this.stringId = stringId;
	}

	public int getStringId() {
		return stringId;
	}

	public void setStringId(int stringId) {
		this.stringId = stringId;
	}
}
