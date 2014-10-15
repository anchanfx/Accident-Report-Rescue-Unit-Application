package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.Compatibility_PHP_JAVA.intToBoolean;
import static nu.ac.th.rescueunit.Compatibility_PHP_JAVA.timeStampInPhpToJava;

import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class AccidentPollingDataConverter {
	
	public static AccidentPollingData fromJSON(JSONObject jsonObject) throws ApplicationException {
		AccidentPollingData accidentPollingData = null;
		AccidentData accidentData = null;
		Position position = null;
		AdditionalInfo additionalInfo = null;
		Date date = null;
		Date serverDate = null;
		Date assignDate = null;
		
		try {	
			JSONObject jsonObject_AccidentData = 
					jsonObject.getJSONObject(JSONKeys.JSON_ACCIDENT_DATA);
			JSONObject jsonObject_Position =
					jsonObject_AccidentData.getJSONObject(JSONKeys.JSON_POSITION);
			JSONObject jsonObject_AdditionalInfo =
					jsonObject_AccidentData.getJSONObject(JSONKeys.JSON_ADDITIONAL_INFO);
			
			
			boolean trafficBlocked = intToBoolean(
					jsonObject_AdditionalInfo.getInt(JSONKeys.TRAFFIC_BLOCKED));
			boolean resolve = intToBoolean(
					jsonObject_AccidentData.getInt(JSONKeys.RESOLVE));
			
			Long timeStamp = timeStampInPhpToJava(
					jsonObject_AccidentData.getLong(JSONKeys.DATE_TIME));
			Long serverTimeStamp = timeStampInPhpToJava(
					jsonObject_AccidentData.getLong(JSONKeys.SERVER_DATE_TIME));
			Long assignTimeStamp = timeStampInPhpToJava(
					jsonObject.getLong(JSONKeys.ASSIGN_DATE_TIME));
			
			int accidentID = jsonObject_AccidentData.getInt(JSONKeys.ACCIDENT_ID);
			
			position = new Position(
					jsonObject_Position.getDouble(JSONKeys.LATITUDE),
					jsonObject_Position.getDouble(JSONKeys.LONGITUDE));
			
			additionalInfo = new AdditionalInfo(
					jsonObject_AdditionalInfo.getString(JSONKeys.ACCIDENT_TYPE),
					jsonObject_AdditionalInfo.getInt(JSONKeys.AMOUNT_OF_INJURED),
					jsonObject_AdditionalInfo.getInt(JSONKeys.AMOUNT_OF_DEAD),
					trafficBlocked,
					jsonObject_AdditionalInfo.getString(JSONKeys.MESSAGE));
			
			date = ApplicationTime.constructDate(timeStamp);
			serverDate = ApplicationTime.constructDate(serverTimeStamp);
			assignDate = ApplicationTime.constructDate(assignTimeStamp);
			
			accidentData = new AccidentData(accidentID, position, additionalInfo, date, serverDate, resolve);
			accidentPollingData = new AccidentPollingData(accidentData, assignDate);
		} catch (JSONException e) {
			throw new ApplicationException();
		} catch (NullPointerException e) {
			throw new ApplicationException();
		}
		
		return accidentPollingData;
	}
}
