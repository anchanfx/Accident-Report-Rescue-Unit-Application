package nu.ac.th.rescueunit;

import java.io.Serializable;

public class AcknowledgeData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4120448741157992980L;
	private String message;

	
	public AcknowledgeData() {
		this("");
	}

	public AcknowledgeData(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
