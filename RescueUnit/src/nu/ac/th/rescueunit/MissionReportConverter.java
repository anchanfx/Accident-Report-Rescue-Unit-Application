package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.Compatibility_PHP_JAVA.timeStampInJavaToPhp;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class MissionReportConverter {
	
	public static JSONObject toJSON(MissionReport missionReport) throws ApplicationException {
		JSONObject holder = new JSONObject();
			JSONObject jsonMissionReport = new JSONObject();
		
		Date date = missionReport.getDate();
		
		try {
			/*MissionReport*/
			jsonMissionReport.put(JSONKeys.IMEI, missionReport.getImei());
			jsonMissionReport.put(JSONKeys.ACCIDENT_ID, missionReport.getAccidentID());
			jsonMissionReport.put(JSONKeys.RESCUE_STATE, missionReport.getRescueState());
			jsonMissionReport.put(JSONKeys.DATE_TIME, timeStampInJavaToPhp(date.getTime()));
			jsonMissionReport.put(JSONKeys.MESSAGE, missionReport.getMessage());
			
			holder.put(JSONKeys.JSON_MISSION_REPORT, jsonMissionReport);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		return holder;
	}
}
