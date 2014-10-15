package nu.ac.th.rescueunit;

import java.io.Serializable;
import java.util.Date;

public class AccidentRescueState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6636004396212223399L;
	private Date assignDateTime;
	private Date dateTime;
	private int state;
	
	public AccidentRescueState() {
		this(new Date(), new Date(), RescueState.PENDING);
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
	
	@Override
	public String toString() {
		String str1 = "AssignDate : " + ApplicationTime.dateToString(this.assignDateTime) + "\n";
		String str2 = "State : " + this.state;
	    return  str1 + str2;
	}
}
