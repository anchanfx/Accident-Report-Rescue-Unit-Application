package nu.ac.th.rescueunit;

import android.provider.BaseColumns;

public class AccidentRescueStateContract {
	public AccidentRescueStateContract() {}
	
	public static abstract class AccidentRescueStateScheme implements BaseColumns {
		public static final String TABLE_NAME = "AccidentRescueState";
		
		public static final String COLUMN_ACCIDENT_ID = "AccidentID";
		public static final String COLUMN_DATE_TIME = "DateTime";
		public static final String COLUMN_RESCUE_STATE = "RescueState";
		public static final String COLUMN_ASSGIN_DATE_TIME = "AssignDateTime";
	}
}
