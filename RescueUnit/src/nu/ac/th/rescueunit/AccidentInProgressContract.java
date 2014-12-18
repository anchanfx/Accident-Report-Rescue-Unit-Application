package nu.ac.th.rescueunit;

import android.provider.BaseColumns;

public class AccidentInProgressContract {
	public AccidentInProgressContract() {}
	
	public static abstract class AccidentInProgressScheme implements BaseColumns {
		public static final String TABLE_NAME = "AccidentInProgress";
		
		public static final String COLUMN_ACCIDENT_ID = "AccidentID";
	}
}
