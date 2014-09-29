package nu.ac.th.rescueunit;

import java.io.Serializable;
import java.util.Date;

public class AccidentData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2375933561887027304L;
	private int accidentID;
	private Position mPosition;
	private AdditionalInfo mAdditionalInfo;
	private Date mDateTime;
	
	public AccidentData() {
		this(0, new Position(), new AdditionalInfo(), new Date());
	}

	public AccidentData(int accidentID, Position position, AdditionalInfo additionalInfo, Date dateTime) {
		super();
		this.accidentID = accidentID;
		mPosition = position;
		mAdditionalInfo = additionalInfo;
		mDateTime = dateTime;
	}

	public int getAccidentID() {
		return accidentID;
	}

	public void setAccidentID(int accidentID) {
		this.accidentID = accidentID;
	}

	public Position getPosition() {
		return mPosition;
	}

	public void setPosition(Position position) {
		mPosition = position;
	}

	public AdditionalInfo getAdditionalInfo() {
		return mAdditionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo additionalInfo) {
		mAdditionalInfo = additionalInfo;
	}

	public Date getDateTime() {
		return mDateTime;
	}

	public void setDateTime(Date dateTime) {
		mDateTime = dateTime;
	}
}	
