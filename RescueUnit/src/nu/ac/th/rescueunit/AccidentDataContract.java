package nu.ac.th.rescueunit;

import android.provider.BaseColumns;

public class AccidentDataContract {
	
	public AccidentDataContract() {}
	
	public static abstract class AccidentDataScheme implements BaseColumns {
		public static final String TABLE_NAME = "AccidentData";
		
		public static final String COLUMN_ID = "ID";
		public static final String COLUMN_LONGITUDE = "Longitude";
		public static final String COLUMN_LATITUDE = "Latitude";
		public static final String COLUMN_ACCIDENT_TYPE = "AccidentType";
		public static final String COLUMN_AMOUNT_OF_DEAD = "AmountOfDead";
		public static final String COLUMN_AMOUNT_OF_INJURED = "AmountOfInjured";
		public static final String COLUMN_TRAFFIC_BLOCKED = "TrafficBlocked";
		public static final String COLUMN_MESSAGE = "Message";
		public static final String COLUMN_DATE_TIME = "DateTime";
		public static final String COLUMN_SERVER_DATE_TIME = "ServerDateTime";
		public static final String COLUMN_RESOLVE = "Resolve";
	}
}
