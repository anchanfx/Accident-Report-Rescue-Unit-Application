package nu.ac.th.rescueunit;

import java.io.Serializable;

public class AdditionalInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8617645823707927566L;
	private static final String DEFAULT_TYPE = "UNKNOWN";
	private static final int DEFAULT_AMOUNT = 0;
	private static final boolean DEFAULT_TRAFFIC_BLOCKED = false;
	private static final String DEFAULT_MESSAGE = "";
	
	private String accidentType;
	private int amountOfInjured;
	private int amountOfDead;
	private boolean trafficBlocked;
	private String message;
			
	public AdditionalInfo() {
		this(DEFAULT_TYPE, DEFAULT_AMOUNT, DEFAULT_AMOUNT, DEFAULT_TRAFFIC_BLOCKED, DEFAULT_MESSAGE);	
	}
	
	public AdditionalInfo(String accidentType,
			int amountOfInjured, int amountOfDead,
			boolean trafficBlocked, String message) {
		super();
		this.accidentType = accidentType;
		this.amountOfInjured = amountOfInjured;
		this.amountOfDead = amountOfDead;
		this.trafficBlocked = trafficBlocked;
		this.message = message;
	}

	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	public int getAmountOfInjured() {
		return amountOfInjured;
	}
	public void setAmountOfInjured(int amountOfInjured) {
		this.amountOfInjured = amountOfInjured;
	}
	public int getAmountOfDead() {
		return amountOfDead;
	}
	public void setAmountOfDead(int amountOfDead) {
		this.amountOfDead = amountOfDead;
	}
	public boolean isTrafficBlocked() {
		return trafficBlocked;
	}
	public void setTrafficBlocked(boolean trafficBlocked) {
		this.trafficBlocked = trafficBlocked;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.accidentType + " : " + 
				this.amountOfInjured + " : " + 
				this.amountOfDead + " : " + 
				this.trafficBlocked + " : " + 
				this.message;
	}
	
}
