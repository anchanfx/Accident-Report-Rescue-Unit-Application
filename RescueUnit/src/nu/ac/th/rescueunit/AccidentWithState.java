package nu.ac.th.rescueunit;

public class AccidentWithState {
	private AccidentData accidentData;
	private AccidentRescueState rescueState;
	
	public AccidentWithState(AccidentData accidentData,
			AccidentRescueState rescueState) {
		super();
		this.accidentData = accidentData;
		this.rescueState = rescueState;
	}
	
	public AccidentData getAccidentData() {
		return accidentData;
	}
	public void setAccidentData(AccidentData accidentData) {
		this.accidentData = accidentData;
	}
	public AccidentRescueState getRescueState() {
		return rescueState;
	}
	public void setRescueState(AccidentRescueState rescueState) {
		this.rescueState = rescueState;
	}
}
