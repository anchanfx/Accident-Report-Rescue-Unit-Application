package nu.ac.th.rescueunit;


public class AccidentPollingRequestData {
	String imei;

	public AccidentPollingRequestData(String imei) {
		super();
		this.imei = imei;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
