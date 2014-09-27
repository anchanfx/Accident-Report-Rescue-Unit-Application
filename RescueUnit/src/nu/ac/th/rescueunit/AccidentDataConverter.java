package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.Compatibility_PHP_JAVA.convertBooleanInPhpToJava;

import org.json.JSONException;
import org.json.JSONObject;

public class AccidentDataConverter {
	
	public static AccidentData fromJSON(JSONObject jsonObject) throws ApplicationException {
		AccidentData accidentData = null;
		Position position = null;
		AdditionalInfo additionalInfo = null;
		int accidentID = 0;
		
		try {
			JSONObject jsonObject_AccidentData = 
					jsonObject.getJSONObject(JSONKeys.JSON_ACCIDENT_DATA);
			JSONObject jsonObject_Position =
					jsonObject_AccidentData.getJSONObject(JSONKeys.JSON_POSITION);
			JSONObject jsonObject_AdditionalInfo =
					jsonObject_AccidentData.getJSONObject(JSONKeys.JSON_ADDITIONAL_INFO);
			
			accidentID = jsonObject_AccidentData.getInt(JSONKeys.ACCIDENT_ID);
			position = new Position(
					jsonObject_Position.getDouble(JSONKeys.LATITUDE),
					jsonObject_Position.getDouble(JSONKeys.LONGITUDE));
			additionalInfo = new AdditionalInfo(
					jsonObject_AdditionalInfo.getString(JSONKeys.ACCIDENT_TYPE),
					jsonObject_AdditionalInfo.getInt(JSONKeys.AMOUNT_OF_INJURED),
					jsonObject_AdditionalInfo.getInt(JSONKeys.AMOUNT_OF_DEAD),
					convertBooleanInPhpToJava(
							jsonObject_AdditionalInfo.getInt(JSONKeys.TRAFFIC_BLOCKED)),
					jsonObject_AdditionalInfo.getString(JSONKeys.MESSAGE));
			
		} catch (JSONException e) {
			throw new ApplicationException();
		} catch (NullPointerException e) {
			throw new ApplicationException();
		}
		
		accidentData = new AccidentData(accidentID, position, additionalInfo);
		
		return accidentData;
	}
}
