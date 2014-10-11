package nu.ac.th.rescueunit;

import java.util.Date;

public class SelfDataCollection {
	private SelfUpdateData mRescuData;
	
	public SelfDataCollection() {
		this(new SelfUpdateData(), new Date());
	}
	
	public SelfDataCollection(SelfUpdateData rescuData) {
		this(rescuData, new Date());
	}

	public SelfDataCollection(SelfUpdateData rescuData, Date date) {
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