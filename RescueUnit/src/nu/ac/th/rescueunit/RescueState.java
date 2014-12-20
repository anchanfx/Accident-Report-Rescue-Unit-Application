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
	
	private static final String ABANDON_STR = "Abandon";
	private static final String REJECT_STR = "Reject";
	private static final String PENDING_STR = "Pending";
	private static final String ACCEPT_STR = "Accept";
	private static final String COMPLETE_STR = "Complete";
	
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
		listOfRescueState.add(new RescueState(COMPLETE, COMPLETE_STR));
		listOfRescueState.add(new RescueState(ABANDON, ABANDON_STR));
		
		return listOfRescueState;
	}
	
	public static String stateNumberToString(int stateNumber) {
		String stateString = "";
		
		switch (stateNumber) {
			case ABANDON:
				stateString = ABANDON_STR;
				break;
			case REJECT:
				stateString = REJECT_STR;
				break;
			case PENDING:
				stateString = PENDING_STR;
				break;
			case ACCEPT:
				stateString = ACCEPT_STR;
				break;
			case COMPLETE:
				stateString = COMPLETE_STR;
				break;
			default:
				break;
		}
		
		return stateString;
	}
}
