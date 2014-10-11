package nu.ac.th.rescueunit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class SelfUpdateConverter {
	
	/* Doesn't convert Date into JSON Object because of using server time instead
	 * So Date is not sent to server
	 */
	public static JSONObject toJSON(SelfDataCollection reportDataCollection) {
		JSONObject holder = new JSONObject();
			JSONObject jsonObject_RescueData = new JSONObject();
				JSONObject jsonObject_Position = new JSONObject();
				JSONObject jsonObject_RescueStatus = new JSONObject();
				JSONObject jsonObject_Imei = new JSONObject();
			//JSONObject jsonObject_Date = new JSONObject();
		
		SelfUpdateData rescuData = reportDataCollection.getRescuData();	
		//Date date = reportDataCollection.getDate();
		
		try {
			/*AccidentData*/
			/*Position*/
			jsonObject_Position.put(JSONKeys.LATITUDE, 
					rescuData.getPosition().getLatitude());
			jsonObject_Position.put(JSONKeys.LONGITUDE, 
					rescuData.getPosition().getLongitude());
			
			/*RescueStatus*/
			jsonObject_RescueStatus.put(JSONKeys.STATUS, 
					rescuData.getStatus().getStatus());
			/*IMEI*/
			jsonObject_Imei.put(JSONKeys.IMEI, 
					rescuData.getImei());
			
			/*Date*/
			//jsonObject_Date.put(JSONKeys.TIME, date.getTime());
			
			jsonObject_RescueData.put(JSONKeys.JSON_POSITION, jsonObject_Position);
			jsonObject_RescueData.put(JSONKeys.JSON_RESCUE_STATUS, jsonObject_RescueStatus);			
			holder.put(JSONKeys.JSON_RESCUE_DATA,jsonObject_RescueData);
			//holder.put(JSONKeys.JSON_OBJECT_DATE, jsonObject_Date);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return holder;
	}
	
	@Deprecated
	public static List<NameValuePair> toNameValuePairs(SelfDataCollection reportDataCollection) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		SelfUpdateData rescueReportData = reportDataCollection.getRescuData();
		
		// rescueReportData
		// Position
		nameValuePairs.add(
				new BasicNameValuePair(JSONKeys.LATITUDE, 
						String.valueOf(rescueReportData.getPosition().getLatitude())));
		nameValuePairs.add(
				new BasicNameValuePair(JSONKeys.LONGITUDE, 
						String.valueOf(rescueReportData.getPosition().getLongitude())));
		// RescueStatus
		nameValuePairs.add(
				new BasicNameValuePair(JSONKeys.STATUS, 
						String.valueOf(rescueReportData.getStatus().getStatus())));
		//IMEI
		nameValuePairs.add(
				new BasicNameValuePair(JSONKeys.IMEI, 
						String.valueOf(rescueReportData.getImei())));

		

		return nameValuePairs;
	}
}
