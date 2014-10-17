package nu.ac.th.rescueunit;

import java.util.ArrayList;
import java.util.List;

public class RescueState {
	public static final String DEFAULT_MESSAGE = "Report Automatically Sent By System";
	public static final int ABANDON = -100;
	public static final int REJECT = -1;
	public static final int PENDING = 0;
	public static final int ACCEPT = 1;
	public static final int COMPLETE = 100;
	
	public static final List<RescueState> LIST_OF_RESCUE_STATE_IN_PROCESS = generateInProcessState();
	private int code;
	private String name;
	
	
	public RescueState(int code, String name) {
		super();
		this.code = code;
		this.name = name;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	private static final List<RescueState> generateInProcessState() {
		List<RescueState> listOfRescueState = new ArrayList<RescueState>();
		listOfRescueState.add(new RescueState(COMPLETE, "Complete"));
		listOfRescueState.add(new RescueState(ABANDON, "Abandon"));
		
		return listOfRescueState;
	}
}
