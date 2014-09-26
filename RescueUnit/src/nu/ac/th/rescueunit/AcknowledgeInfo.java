package nu.ac.th.rescueunit;

public class AcknowledgeInfo {
	private String message;

	
	public AcknowledgeInfo() {
		this("");
	}

	public AcknowledgeInfo(String message) {
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
