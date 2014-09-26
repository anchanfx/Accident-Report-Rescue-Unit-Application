package nu.ac.th.rescueunit;

public class SelfUpdateData {
	private Position mPosition;
	private RescueUnitStatus mStatus;
	private IMEI mImei;
	
	public SelfUpdateData() {
		this(new Position(), new RescueUnitStatus(),new IMEI());
	}

	public SelfUpdateData(Position position, RescueUnitStatus rescueStatus, IMEI imei) {
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
	
	public IMEI getImei(){
		return mImei;
	}
	
	public void setImei(IMEI imei){
		mImei = imei;
	}
}	
