package nu.ac.th.rescueunit;

import java.io.Serializable;
import java.util.Date;

public class AccidentData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2375933561887027304L;
	private int accidentID;
	private Position position;
	private AdditionalInfo additionalInfo;
	private Date dateTime;
	private Date serverDateTime;
	private boolean resolve;
	
	public AccidentData() {
		this(0, new Position(), new AdditionalInfo(), new Date(), new Date(), false);
	}

	public AccidentData(int accidentID, Position position, AdditionalInfo additionalInfo,
			Date dateTime, Date serverDateTime, boolean resolve) {
		super();
		this.accidentID = accidentID;
		this.position = position;
		this.additionalInfo = additionalInfo;
		this.dateTime = dateTime;
		this.serverDateTime = serverDateTime;
		this.resolve = resolve;
	}

	public int getAccidentID() {
		return accidentID;
	}

	public void setAccidentID(int accidentID) {
		this.accidentID = accidentID;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public AdditionalInfo getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Date getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(Date serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public boolean isResolve() {
		return resolve;
	}

	public void setResolve(boolean resolve) {
		this.resolve = resolve;
	}
	
	@Override
	public String toString() {
		String str1 = "ID: " + this.accidentID + "\n";
		String str2 = "ReportTime: " + ApplicationTime.dateToString(this.dateTime);
	    return str1 + str2;
	}
}	
