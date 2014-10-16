package nu.ac.th.rescueunit;

import java.io.Serializable;

public class AccidentWithState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6297464040877144966L;
	private AccidentData accidentData;
	private AccidentRescueState rescueState;
	
	public AccidentWithState(AccidentData accidentData,
			AccidentRescueState accidentRescueState) {
		super();
		this.accidentData = accidentData;
		this.rescueState = accidentRescueState;
	}
	
	public AccidentData getAccidentData() {
		return accidentData;
	}
	
	public void setAccidentData(AccidentData accidentData) {
		this.accidentData = accidentData;
	}
	
	public AccidentRescueState getAccidentRescueState() {
		return rescueState;
	}
	
	public void setAccidentRescueState(AccidentRescueState accidentRescueState) {
		this.rescueState = accidentRescueState;
	}
	
	@Override
	public String toString() {
	    return accidentData.toString() + "\n" + rescueState.toString();
	}
}
