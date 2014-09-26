package nu.ac.th.rescueunit;

public class AcknowledgeDataCollection {

	private AcknowledgeInfo mAcknowledgeInfo;
	
	
	public AcknowledgeDataCollection() {
		this(new AcknowledgeInfo());
	}

	public AcknowledgeDataCollection(AcknowledgeInfo acknowledgeInfo) {
		super();
		mAcknowledgeInfo = acknowledgeInfo;
	}
	
	public AcknowledgeInfo getAcknowledgeInfo() {
		return mAcknowledgeInfo;
	}
}
