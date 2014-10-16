package nu.ac.th.rescueunit;

import org.json.JSONException;
import org.json.JSONObject;

public class SelfUpdateDataConverter {
	
	/* Doesn't convert Date into JSON Object because of using server time instead
	 * So Date is not sent to server
	 */
	public static JSONObject toJSON(SelfUpdateData selfUpdateData) {
		JSONObject holder = new JSONObject();
			JSONObject jsonObject_RescueUnitData = new JSONObject();
				JSONObject jsonObject_Position = new JSONObject();
				JSONObject jsonObject_RescueStatus = new JSONObject();
				
		try {
			/*SelfUpdateData*/
			/*Position*/
			jsonObject_Position.put(JSONKeys.LATITUDE, 
					selfUpdateData.getPosition().getLatitude());
			jsonObject_Position.put(JSONKeys.LONGITUDE, 
					selfUpdateData.getPosition().getLongitude());
			
			/*RescueStatus*/
			jsonObject_RescueStatus.put(JSONKeys.STATUS_ONLINE, 
					selfUpdateData.getStatus().getOnline());
			jsonObject_RescueStatus.put(JSONKeys.STATUS_AVAILABLE, 
					selfUpdateData.getStatus().getAvailable());
			
			/*IMEI*/
			jsonObject_RescueUnitData.put(JSONKeys.IMEI, selfUpdateData.getImei());
			
			jsonObject_RescueUnitData.put(JSONKeys.JSON_POSITION, jsonObject_Position);
			jsonObject_RescueUnitData.put(JSONKeys.JSON_RESCUE_STATUS, jsonObject_RescueStatus);			
			holder.put(JSONKeys.JSON_RESCUE_UNIT_DATA,jsonObject_RescueUnitData);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return holder;
	}
}
