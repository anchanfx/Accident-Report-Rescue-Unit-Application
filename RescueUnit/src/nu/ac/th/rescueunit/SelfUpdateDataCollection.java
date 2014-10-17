package nu.ac.th.rescueunit;

import java.util.Date;

public class SelfUpdateDataCollection {
	private SelfUpdateData mRescuData;
	
	public SelfUpdateDataCollection() {
		this(new SelfUpdateData(), new Date());
	}
	
	public SelfUpdateDataCollection(SelfUpdateData rescuData) {
		this(rescuData, new Date());
	}

	public SelfUpdateDataCollection(SelfUpdateData rescuData, Date date) {
		super();
		mRescuData = rescuData;
	}

	public SelfUpdateData getRescuData() {
		return mRescuData;
	}

	public void setAccidentData(SelfUpdateData rescuData) {
		mRescuData = rescuData;
	}
}