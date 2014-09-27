package nu.ac.th.rescueunit;

import org.json.JSONException;
import org.json.JSONObject;

public class AccidentPolllingDataConverter {

	public static JSONObject toJSON(AccidentPollingData accidentPollingData) throws ApplicationException {
		JSONObject holder = new JSONObject();
		try {
			/*IMEI*/
			holder.put(JSONKeys.IMEI, accidentPollingData.getImei());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		return holder;
	}
}
