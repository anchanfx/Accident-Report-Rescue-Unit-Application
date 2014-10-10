package nu.ac.th.rescueunit;

public class AccidentWithState {
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
}
