package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.Compatibility_PHP_JAVA.intToBoolean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nu.ac.th.rescueunit.AccidentDataContract.AccidentDataScheme;
import nu.ac.th.rescueunit.AccidentRescueStateContract.AccidentRescueStateScheme;
import nu.ac.th.rescueunit.AccidentInProgressContract.AccidentInProgressScheme;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	private static final String SQL_CREATE_TABLE_ACCIDENT_IN_PROGRESS =
			"CREATE TABLE IF NOT EXISTS " + AccidentInProgressScheme.TABLE_NAME + "(" +
				AccidentInProgressScheme.COLUMN_ACCIDENT_ID + " INT PRIMARY KEY" +
				")";
	
	private static final String SQL_DELETE_TABLE_ACCIDENT_DATA = 
			"DROP TABLE IF EXISTS " + AccidentDataScheme.TABLE_NAME;
	private static final String SQL_DELETE_TABLE_ACCIDENT_RESCUE_STATE = 
			"DROP TABLE IF EXISTS " + AccidentRescueStateScheme.TABLE_NAME;
	private static final String SQL_DELETE_TABLE_ACCIDENT_IN_PROGRESS = 
			"DROP TABLE IF EXISTS " + AccidentInProgressScheme.TABLE_NAME;
	
	private static final String SQL_QUERY_ACCIDENTS_WITH_SATE = 
			"SELECT  *, MAX(" 
						+ AccidentRescueStateScheme.TABLE_NAME + "." 
						+ AccidentRescueStateScheme.COLUMN_DATE_TIME + ") AS DATE_TIME_MAX" +
					" FROM " + AccidentDataScheme.TABLE_NAME + 
					" INNER JOIN " + AccidentRescueStateScheme.TABLE_NAME + 
					" ON " + AccidentDataScheme.COLUMN_ID + "=" + AccidentRescueStateScheme.COLUMN_ACCIDENT_ID +
					" GROUP BY " + AccidentDataScheme.COLUMN_ID + 
					" ORDER BY DATE_TIME_MAX DESC";
	
	private static final String SQL_QUERY_SPECIFIC_ACCIDENT_WITH_SATE = 
			"SELECT  *, MAX(" 
						+ AccidentRescueStateScheme.TABLE_NAME + "." 
						+ AccidentRescueStateScheme.COLUMN_DATE_TIME + ") AS DATE_TIME_MAX" +
					" FROM " + AccidentDataScheme.TABLE_NAME + 
					" INNER JOIN " + AccidentRescueStateScheme.TABLE_NAME + 
					" ON " + AccidentDataScheme.COLUMN_ID + "=" + AccidentRescueStateScheme.COLUMN_ACCIDENT_ID +
					" WHERE " + AccidentDataScheme.COLUMN_ID + "=?" +
					" GROUP BY " + AccidentDataScheme.COLUMN_ID + 
					" ORDER BY DATE_TIME_MAX DESC";
	
	private static final String SQL_QUERY_ACCIDENT_IN_PROGRESS = 
			"SELECT  *" +
					" FROM " + AccidentInProgressScheme.TABLE_NAME;
	
	public ApplicationDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_ACCIDENT_DATA);
		db.execSQL(SQL_CREATE_TABLE_ACCIDENT_RESCUE_STATE);
		db.execSQL(SQL_CREATE_TABLE_ACCIDENT_IN_PROGRESS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_TABLE_ACCIDENT_DATA);
		db.execSQL(SQL_DELETE_TABLE_ACCIDENT_RESCUE_STATE);
		db.execSQL(SQL_DELETE_TABLE_ACCIDENT_IN_PROGRESS);
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
	
	public void addAccidentInProgress(int accidentID) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(AccidentInProgressScheme.COLUMN_ACCIDENT_ID, accidentID);
		
		db.insertOrThrow(AccidentInProgressScheme.TABLE_NAME, null, values);
	}
	
	public void removeAccidentInProgress(int accidentID) {
		SQLiteDatabase db = getWritableDatabase();
		
		db.delete(AccidentInProgressScheme.TABLE_NAME, 
				AccidentInProgressScheme.COLUMN_ACCIDENT_ID + "=?", 
				new String[]{Integer.toString(accidentID)});
	}
	
	public boolean isAccidentInProgressEmpty() {
		SQLiteDatabase db = getWritableDatabase();
		boolean empty = true;
	    Cursor cursor = db.rawQuery(SQL_QUERY_ACCIDENT_IN_PROGRESS, null);
	    int rowCount = cursor.getCount();
	    
	    if(rowCount > 0) {
	    	empty = false;
	    }
	    
		return empty;
	}
	
	// getSpecificAccidentWithState can be used to determine whether accident
	public boolean isAccidentInAccidentData(int accidentID) {
		SQLiteDatabase db = this.getReadableDatabase();
		boolean isIn = false;
		Cursor cursor = db.query(AccidentDataScheme.TABLE_NAME, 
					null, 
					AccidentDataScheme.COLUMN_ID + " = ?", 
					new String[] {String.valueOf(accidentID)}, 
					null, 
					null,  
					null);
		int rowCount = cursor.getCount();
		
		if(rowCount > 0) {
			isIn = true;
	    }
		
		return isIn;
	}
	
	public AccidentWithState getSpecificAccidentWithState(int accidentID) {
		SQLiteDatabase db = this.getReadableDatabase();
		AccidentWithState accidentWithState = null;
		Cursor cursor = db.rawQuery(SQL_QUERY_SPECIFIC_ACCIDENT_WITH_SATE, 
				new String [] {String.valueOf(accidentID)});
	 
	    if (cursor.moveToFirst()) {
        	/*AccidentData accidentData = new AccidentData();
        	AccidentRescueState accidentRescueState = new AccidentRescueState();
        	accidentData.setAccidentID(cursor.getInt(0));
        	accidentData.setPosition(new Position(cursor.getDouble(2), cursor.getDouble(1)));
        	accidentData.setAdditionalInfo(
        			new AdditionalInfo(cursor.getString(3), 
        							   cursor.getInt(4), 
        							   cursor.getInt(5), 
        							   intToBoolean(cursor.getInt(6)), 
        							   cursor.getString(7)));
        	accidentData.setDateTime(ApplicationTime.constructDate(cursor.getLong(8)));
        	accidentData.setServerDateTime(ApplicationTime.constructDate(cursor.getLong(9)));
        	accidentData.setResolve(intToBoolean(cursor.getInt(10)));
        	accidentRescueState.setDateTime(ApplicationTime.constructDate(cursor.getLong(12)));
        	accidentRescueState.setState(cursor.getInt(13));
        	accidentRescueState.setAssignDateTime(ApplicationTime.constructDate(cursor.getLong(14)));
            accidentWithState = new AccidentWithState(accidentData, accidentRescueState);*/
            accidentWithState = buildAccidentWithStateFromCursor(cursor);
	    }
		
		return accidentWithState;
	}
	
	public List<AccidentWithState> getAccidentWithState() {
		SQLiteDatabase db = this.getReadableDatabase();
		AccidentWithState accidentWithState = null;
		List<AccidentWithState> listOfAccidentWithStates = new ArrayList<AccidentWithState>();
	    Cursor cursor = db.rawQuery(SQL_QUERY_ACCIDENTS_WITH_SATE, null);
	 
	    if (cursor.moveToFirst()) {
	        do {
	        	/*AccidentData accidentData = new AccidentData();
	        	AccidentRescueState accidentRescueState = new AccidentRescueState();
	        	accidentData.setAccidentID(cursor.getInt(0));
	        	accidentData.setPosition(new Position(cursor.getDouble(2), cursor.getDouble(1)));
	        	accidentData.setAdditionalInfo(
	        			new AdditionalInfo(cursor.getString(3), 
	        							   cursor.getInt(4), 
	        							   cursor.getInt(5), 
	        							   intToBoolean(cursor.getInt(6)), 
	        							   cursor.getString(7)));
	        	accidentData.setDateTime(ApplicationTime.constructDate(cursor.getLong(8)));
	        	accidentData.setServerDateTime(ApplicationTime.constructDate(cursor.getLong(9)));
	        	accidentData.setResolve(intToBoolean(cursor.getInt(10)));
	        	accidentRescueState.setDateTime(ApplicationTime.constructDate(cursor.getLong(12)));
	        	accidentRescueState.setState(cursor.getInt(13));
	        	accidentRescueState.setAssignDateTime(ApplicationTime.constructDate(cursor.getLong(14)));
	            AccidentWithState accidentWithState = new AccidentWithState(accidentData, accidentRescueState);*/
	            accidentWithState = buildAccidentWithStateFromCursor(cursor);
	            listOfAccidentWithStates.add(accidentWithState);
	        } while (cursor.moveToNext());
	    }
		
		return listOfAccidentWithStates;
	}
	
	private AccidentWithState buildAccidentWithStateFromCursor(Cursor cursor) {
		AccidentWithState accidentWithState = null;
		AccidentData accidentData = new AccidentData();
    	AccidentRescueState accidentRescueState = new AccidentRescueState();
    	
    	accidentData.setAccidentID(cursor.getInt(0));
    	accidentData.setPosition(new Position(cursor.getDouble(2), cursor.getDouble(1)));
    	accidentData.setAdditionalInfo(
    			new AdditionalInfo(cursor.getString(3), 
    							   cursor.getInt(4), 
    							   cursor.getInt(5), 
    							   intToBoolean(cursor.getInt(6)), 
    							   cursor.getString(7)));
    	accidentData.setDateTime(ApplicationTime.constructDate(cursor.getLong(8)));
    	accidentData.setServerDateTime(ApplicationTime.constructDate(cursor.getLong(9)));
    	accidentData.setResolve(intToBoolean(cursor.getInt(10)));
    	accidentRescueState.setDateTime(ApplicationTime.constructDate(cursor.getLong(12)));
    	accidentRescueState.setState(cursor.getInt(13));
    	accidentRescueState.setAssignDateTime(ApplicationTime.constructDate(cursor.getLong(14)));
        accidentWithState = new AccidentWithState(accidentData, accidentRescueState);
        
        return accidentWithState;
	}
}
