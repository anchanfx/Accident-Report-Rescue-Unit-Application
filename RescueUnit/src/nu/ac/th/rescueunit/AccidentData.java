package nu.ac.th.rescueunit;

import java.io.Serializable;

public class AccidentData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2375933561887027304L;
	private int accidentID;
	private Position mPosition;
	private AdditionalInfo mAdditionalInfo;
	
	public AccidentData() {
		this(0, new Position(), new AdditionalInfo());
	}

	public AccidentData(int accidentID, Position position, AdditionalInfo additionalInfo) {
		super();
		this.accidentID = accidentID;
		mPosition = position;
		mAdditionalInfo = additionalInfo;
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
}	
