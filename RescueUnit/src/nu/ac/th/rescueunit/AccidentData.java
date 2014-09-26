package nu.ac.th.rescueunit;

public class AccidentData {
	private Position mPosition;
	private AdditionalInfo mAdditionalInfo;
	
	public AccidentData() {
		this(new Position(), new AdditionalInfo());
	}

	public AccidentData(Position position, AdditionalInfo additionalInfo) {
		super();
		mPosition = position;
		mAdditionalInfo = additionalInfo;
	}

	public Position getPosition() {
		return mPosition;
	}

	public void setPosition(Position position) {
		mPosition = position;
	}

	public AdditionalInfo getAdditionalInfo() {
		return mAdditionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo additionalInfo) {
		mAdditionalInfo = additionalInfo;
	}
}	
