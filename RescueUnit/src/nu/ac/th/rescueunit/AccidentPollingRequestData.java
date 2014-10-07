package nu.ac.th.rescueunit;

import java.io.Serializable;


public class AccidentPollingRequestData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5373145929224251408L;
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
