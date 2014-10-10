package nu.ac.th.rescueunit;

public class SelfUpdateData {
	private Position mPosition;
	private RescueUnitStatus mStatus;
	private String mImei;
	
	public SelfUpdateData() {
		this(new Position(), new RescueUnitStatus(), "");
	}

	public SelfUpdateData(Position position, RescueUnitStatus rescueStatus, String imei) {
		super();
		mPosition = position;
		mStatus = rescueStatus;
		mImei = imei;
	}

	public Position getPosition() {
		return mPosition;
	}

	public void setPosition(Position position) {
		mPosition = position;
	}

	public RescueUnitStatus getStatus() {
		return mStatus;
	}

	public void setStatus(RescueUnitStatus status) {
		mStatus = status;
	}

	public String getImei() {
		return mImei;
	}

	public void setImei(String imei) {
		mImei = imei;
	}

	
}	
