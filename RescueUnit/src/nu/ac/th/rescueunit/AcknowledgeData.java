package nu.ac.th.rescueunit;

public class AcknowledgeData {
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
