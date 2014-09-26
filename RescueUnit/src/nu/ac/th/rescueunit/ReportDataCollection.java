package nu.ac.th.rescueunit;

import java.util.Date;

public class ReportDataCollection {
	private SelfUpdateData mRescuData;
	private Date mDate;
	
	public ReportDataCollection() {
		this(new SelfUpdateData(), new Date());
	}
	
	public ReportDataCollection(SelfUpdateData rescuData) {
		this(rescuData, new Date());
	}

	public ReportDataCollection(SelfUpdateData rescuData, Date date) {
		super();
		mRescuData = rescuData;
		mDate = date;
	}

	public SelfUpdateData getRescuData() {
		return mRescuData;
	}

	public void setAccidentData(SelfUpdateData rescuData) {
		mRescuData = rescuData;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		this.mDate = date;
	}
}