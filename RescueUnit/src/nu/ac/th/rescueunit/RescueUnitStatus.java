package nu.ac.th.rescueunit;

public class RescueUnitStatus {
	private static final boolean Status = false;
	
	private boolean rStatus;
	
	public RescueUnitStatus(){
		this(Status);
	}
	
	public RescueUnitStatus(Boolean status) {
		this.rStatus = status;
	}
	
	public boolean getStatus(){
		return rStatus;
	}
	
	public void setStatus(Boolean status){
		rStatus = status;
	}
}
