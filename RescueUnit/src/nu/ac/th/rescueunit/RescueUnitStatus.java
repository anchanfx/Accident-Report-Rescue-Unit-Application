package nu.ac.th.rescueunit;

public class RescueUnitStatus {
	private boolean online;
	private boolean available;
	
	public RescueUnitStatus(){
		this(true, true);
	}
	
	public RescueUnitStatus(boolean Online,boolean Available){
		online = Online;
		available = Available;
	}

	public boolean getOnline() {
		return online;
	}

	public void setOnline(boolean Online) {
		online = Online;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean Available) {
		available = Available;
	}
	
}
