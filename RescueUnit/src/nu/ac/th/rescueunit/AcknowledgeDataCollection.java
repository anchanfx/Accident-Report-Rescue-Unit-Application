package nu.ac.th.rescueunit;

import java.io.Serializable;

public class AcknowledgeDataCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2125341207912507355L;
	private AcknowledgeData mAcknowledgeInfo;
	
	
	public AcknowledgeDataCollection() {
		this(new AcknowledgeData());
	}

	public AcknowledgeDataCollection(AcknowledgeData acknowledgeInfo) {
		super();
		mAcknowledgeInfo = acknowledgeInfo;
	}
	
	public AcknowledgeData getAcknowledgeInfo() {
		return mAcknowledgeInfo;
	}
}
