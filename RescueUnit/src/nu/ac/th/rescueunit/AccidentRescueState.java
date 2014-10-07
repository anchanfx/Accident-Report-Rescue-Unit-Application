package nu.ac.th.rescueunit;

import java.util.Date;

public class AccidentRescueState {
	private Date assignDateTime;
	private Date unitDateTime;
	private int state;
	
	public AccidentRescueState() {
		this(null, null, RescueState.PENDING);
	}
	
	public AccidentRescueState(Date assignDateTime, Date unitDateTime, int state) {
		super();
		this.assignDateTime = assignDateTime;
		this.unitDateTime = unitDateTime;
		this.state = state;
	}

	public Date getAssignDateTime() {
		return assignDateTime;
	}

	public void setAssignDateTime(Date assignDateTime) {
		this.assignDateTime = assignDateTime;
	}

	public Date getUnitDateTime() {
		return unitDateTime;
	}

	public void setUnitDateTime(Date unitDateTime) {
		this.unitDateTime = unitDateTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
