package nu.ac.th.rescueunit;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationSharedPreference {
	private static final String DEFAULT_POSITION = "0.0";
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
		editor.putString(SAVED_LONGITUDE, Double.toString(position.getLongitude()));
		editor.putString(SAVED_LATITUDE, Double.toString(position.getLatitude()));
		editor.commit();
		
	}
	
	public static Position getPosition(Context context) {
		SharedPreferences sharedPreferences = loadSharedPreferences(context);
		String longitude = sharedPreferences.getString(SAVED_LONGITUDE, DEFAULT_POSITION);
		String latitude = sharedPreferences.getString(SAVED_LATITUDE, DEFAULT_POSITION);
		
		return new Position(Double.valueOf(latitude), Double.valueOf(longitude));
	}
	
	private static SharedPreferences loadSharedPreferences(Context context) {
		return context.getSharedPreferences(
				context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
	}
}
