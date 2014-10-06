package nu.ac.th.rescueunit;

import java.io.Serializable;
import java.util.Date;

public class MissionReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8647277689058953051L;
	private String imei;
	private int accidentID;
	private int rescueState;
	private Date date;
	private String message;
	
	public MissionReport(String imei, int accidentID, int rescueState, String message) {
		this(imei, accidentID, rescueState, new Date(), message);
	}

	public MissionReport(String imei, int accidentID, int rescueState, Date date, String message) {
		this.imei = imei;
		this.accidentID = accidentID;
		this.rescueState = rescueState;
		this.message = message;
		this.date = date;
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
