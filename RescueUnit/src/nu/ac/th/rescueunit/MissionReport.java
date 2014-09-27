package nu.ac.th.rescueunit;

public class MissionReport {
	private String imei;
	private int accidentID;
	private int rescueState;
	private String message;
	
	public MissionReport(String imei, int accidentID, int rescueState,
			String message) {
		super();
		this.imei = imei;
		this.accidentID = accidentID;
		this.rescueState = rescueState;
		this.message = message;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getAccidentID() {
		return accidentID;
	}

	public void setAccidentID(int accidentID) {
		this.accidentID = accidentID;
	}

	public int getRescueState() {
		return rescueState;
	}

	public void setRescueState(int rescueState) {
		this.rescueState = rescueState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
