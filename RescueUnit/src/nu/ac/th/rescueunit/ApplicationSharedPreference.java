package nu.ac.th.rescueunit;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationSharedPreference {
	private static final long DEFAULT_LONG = 0;
	private static final boolean DEFAULT_BOOLEAN = true;
	
	private static final String SAVED_LONGITUDE = "Longitude";
	private static final String SAVED_LATITUDE = "Latitude";
	private static final String SAVED_ONLINE = "Online";
	private static final String SAVED_AVAILABLE = "Available";
	
	public static void storeStatus(Context context, RescueUnitStatus rescueUnitStatus) {
		SharedPreferences sharedPreferences = loadSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SAVED_ONLINE, rescueUnitStatus.getOnline());
		editor.putBoolean(SAVED_AVAILABLE, rescueUnitStatus.getAvailable());
		editor.commit();
		
	}
	
	public static RescueUnitStatus getStatus(Context context) {
		SharedPreferences sharedPreferences = loadSharedPreferences(context);
		boolean online = sharedPreferences.getBoolean(SAVED_ONLINE, DEFAULT_BOOLEAN);
		boolean available = sharedPreferences.getBoolean(SAVED_AVAILABLE, DEFAULT_BOOLEAN);
		
		return new RescueUnitStatus(online, available);
	}

	public static void storePosition(Context context, Position position) {
		SharedPreferences sharedPreferences = loadSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(SAVED_LONGITUDE, Double.doubleToRawLongBits(position.getLongitude()));
		editor.putLong(SAVED_LATITUDE, Double.doubleToRawLongBits(position.getLatitude()));
		editor.commit();
	}
	
	public static Position getPosition(Context context) {
		SharedPreferences sharedPreferences = loadSharedPreferences(context);
		long longitude = sharedPreferences.getLong(SAVED_LONGITUDE, DEFAULT_LONG);
		long latitude = sharedPreferences.getLong(SAVED_LATITUDE, DEFAULT_LONG);
		
		return new Position(Double.longBitsToDouble(latitude), Double.longBitsToDouble(longitude));
	}
	
	private static SharedPreferences loadSharedPreferences(Context context) {
		return context.getSharedPreferences(
				context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
	}
}
