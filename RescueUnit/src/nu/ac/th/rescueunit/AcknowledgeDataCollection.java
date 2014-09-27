package nu.ac.th.rescueunit;

public class AcknowledgeDataCollection {

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
