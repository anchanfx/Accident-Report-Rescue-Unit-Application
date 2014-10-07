package nu.ac.th.rescueunit;

import java.util.Date;

public class AccidentRescueState {
	private Date assignDateTime;
	private Date dateTime;
	private int state;
	
	public AccidentRescueState() {
		this(null, null, RescueState.PENDING);
	}
	
	public AccidentRescueState(Date assignDateTime, Date dateTime, int state) {
		super();
		this.assignDateTime = assignDateTime;
		this.dateTime = dateTime;
		this.state = state;
	}

	public Date getAssignDateTime() {
		return assignDateTime;
	}

	public void setAssignDateTime(Date assignDateTime) {
		this.assignDateTime = assignDateTime;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
