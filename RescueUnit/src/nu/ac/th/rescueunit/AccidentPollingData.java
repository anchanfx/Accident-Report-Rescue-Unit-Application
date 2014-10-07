package nu.ac.th.rescueunit;

import java.util.Date;

public class AccidentPollingData {
	private AccidentData accidentData;
	private Date assignDate;
	
	public AccidentPollingData(AccidentData accidentData, Date assignDateTime) {
		super();
		this.accidentData = accidentData;
		this.assignDate = assignDateTime;
	}

	public AccidentData getAccidentData() {
		return accidentData;
	}

	public void setAccidentData(AccidentData accidentData) {
		this.accidentData = accidentData;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDateTime) {
		this.assignDate = assignDateTime;
	}
}
