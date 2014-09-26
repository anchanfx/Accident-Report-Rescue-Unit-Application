package nu.ac.th.rescueunit;

public class RescueUnitStatus {
	private static final String Status = "UNKNOWN";
	
	private String rStatus;
	
	public RescueUnitStatus(){
		this(Status);
	}
	
	public RescueUnitStatus(String status) {
		this.rStatus = status;
	}
	
	public String getStatus(){
		return rStatus;
	}
	
	public void setStatus(String status){
		rStatus = status;
	}
}
