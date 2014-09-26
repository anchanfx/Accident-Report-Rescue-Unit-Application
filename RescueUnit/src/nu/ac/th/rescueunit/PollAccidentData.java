package nu.ac.th.rescueunit;

import java.io.Serializable;

public class PollAccidentData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 262718990056476428L;
	String imei;

	public PollAccidentData(String imei) {
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
