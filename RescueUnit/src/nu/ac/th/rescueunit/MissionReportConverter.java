package nu.ac.th.rescueunit;

import org.json.JSONException;
import org.json.JSONObject;

public class MissionReportConverter {
	
	public static JSONObject toJSON(MissionReport missionReport) throws ApplicationException {
		JSONObject holder = new JSONObject();
			JSONObject jsonMissionReport = new JSONObject();
		try {
			/*MissionReport*/
			jsonMissionReport.put(JSONKeys.IMEI, missionReport.getImei());
			jsonMissionReport.put(JSONKeys.ACCIDENT_ID, missionReport.getAccidentID());
			jsonMissionReport.put(JSONKeys.RESCUE_STATE, missionReport.getRescueState());
			jsonMissionReport.put(JSONKeys.MESSAGE, missionReport.getMessage());
			
			holder.put(JSONKeys.JSON_MISSION_REPORT, jsonMissionReport);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		return holder;
	}
}
