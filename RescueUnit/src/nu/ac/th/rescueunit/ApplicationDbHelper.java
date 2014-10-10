package nu.ac.th.rescueunit;

import java.util.Date;

import nu.ac.th.rescueunit.AccidentDataContract.AccidentDataScheme;
import nu.ac.th.rescueunit.AccidentRescueStateContract.AccidentRescueStateScheme;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ApplicationDbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "RescueUnit.db";
	public static final int DATABASE_VERSION = 1;
	
	private static final String SQL_CREATE_TABLE_ACCIDENT_DATA =
			"CREATE TABLE IF NOT EXISTS " + AccidentDataScheme.TABLE_NAME + "(" +
				AccidentDataScheme.COLUMN_ID + " INT PRIMARY KEY," +
				AccidentDataScheme.COLUMN_LONGITUDE + " DOUBLE," +
				AccidentDataScheme.COLUMN_LATITUDE + " DOUBLE," +
				AccidentDataScheme.COLUMN_ACCIDENT_TYPE + " VARCHAR(255)," +
				AccidentDataScheme.COLUMN_AMOUNT_OF_DEAD + " INT," +
				AccidentDataScheme.COLUMN_AMOUNT_OF_INJURED + " INT," +
				AccidentDataScheme.COLUMN_TRAFFIC_BLOCKED + " BOOLEAN," +
				AccidentDataScheme.COLUMN_MESSAGE + " VARCHAR(255)," +
				AccidentDataScheme.COLUMN_DATE_TIME + " INT," +
				AccidentDataScheme.COLUMN_SERVER_DATE_TIME + " INT," +
				AccidentDataScheme.COLUMN_RESOLVE + " BOOLEAN" +
				")";
	private static final String SQL_CREATE_TABLE_ACCIDENT_RESCUE_STATE =
			"CREATE TABLE IF NOT EXISTS " + AccidentRescueStateScheme.TABLE_NAME + "(" +
				AccidentRescueStateScheme.COLUMN_ACCIDENT_ID + " INT," +
				AccidentRescueStateScheme.COLUMN_DATE_TIME + " INT," +
				AccidentRescueStateScheme.COLUMN_RESCUE_STATE + " INT," +
				AccidentRescueStateScheme.COLUMN_ASSGIN_DATE_TIME + " INT," +
				"PRIMARY KEY("+ AccidentRescueStateScheme.COLUMN_ACCIDENT_ID + ", " + 
					AccidentRescueStateScheme.COLUMN_DATE_TIME + 
					")" +
				")";
	
	private static final String SQL_DELETE_TABLE_ACCIDENT_DATA = 
			"DROP TABLE IF EXISTS " + AccidentDataScheme.TABLE_NAME;
	private static final String SQL_DELETE_TABLE_ACCIDENT_RESCUE_STATE = 
			"DROP TABLE IF EXISTS " + AccidentRescueStateScheme.TABLE_NAME;
	
	public ApplicationDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_ACCIDENT_DATA);
		db.execSQL(SQL_CREATE_TABLE_ACCIDENT_RESCUE_STATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_TABLE_ACCIDENT_DATA);
		db.execSQL(SQL_DELETE_TABLE_ACCIDENT_RESCUE_STATE);
		onCreate(db);
	}

	public void addAccidentData(AccidentData accidentData) {
		SQLiteDatabase db = getWritableDatabase();
		
		Position position = accidentData.getPosition();
		AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
		Date date = accidentData.getDateTime();
		Date serverDate = accidentData.getServerDateTime();
		
		ContentValues values = new ContentValues();
		values.put(AccidentDataScheme.COLUMN_ID, accidentData.getAccidentID());
		values.put(AccidentDataScheme.COLUMN_LONGITUDE, position.getLongitude());
		values.put(AccidentDataScheme.COLUMN_LATITUDE, position.getLatitude());
		values.put(AccidentDataScheme.COLUMN_ACCIDENT_TYPE, additionalInfo.getAccidentType());
		values.put(AccidentDataScheme.COLUMN_AMOUNT_OF_DEAD, additionalInfo.getAmountOfDead());
		values.put(AccidentDataScheme.COLUMN_AMOUNT_OF_INJURED, additionalInfo.getAmountOfInjured());
		values.put(AccidentDataScheme.COLUMN_TRAFFIC_BLOCKED, additionalInfo.isTrafficBlocked());
		values.put(AccidentDataScheme.COLUMN_MESSAGE, additionalInfo.getMessage());
		values.put(AccidentDataScheme.COLUMN_DATE_TIME, date.getTime());
		values.put(AccidentDataScheme.COLUMN_SERVER_DATE_TIME, serverDate.getTime());
		values.put(AccidentDataScheme.COLUMN_RESOLVE, accidentData.isResolve());
		
		db.insertOrThrow(AccidentDataScheme.TABLE_NAME, null, values);
	}
	
	public void addAccidentRescueState(int accidentId, AccidentRescueState accidentRescueState) {
		SQLiteDatabase db = getWritableDatabase();
		
		Date date = accidentRescueState.getDateTime();
		Date assignDate = accidentRescueState.getAssignDateTime();
		
		ContentValues values = new ContentValues();
		values.put(AccidentRescueStateScheme.COLUMN_ACCIDENT_ID, 
				accidentId);
		values.put(AccidentRescueStateScheme.COLUMN_DATE_TIME, 
				date.getTime());
		values.put(AccidentRescueStateScheme.COLUMN_RESCUE_STATE, 
				accidentRescueState.getState());
		values.put(AccidentRescueStateScheme.COLUMN_ASSGIN_DATE_TIME, 
				assignDate.getTime());
		
		db.insertOrThrow(AccidentRescueStateScheme.TABLE_NAME, null, values);
	}
}
