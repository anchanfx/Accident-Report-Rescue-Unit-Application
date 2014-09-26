package nu.ac.th.rescueunit;

import org.json.JSONException;
import org.json.JSONObject;

public class PolllAccidentDataConverter {

	public static JSONObject toJSON(PollAccidentData pollAccidentData) throws ApplicationException {
		JSONObject holder = new JSONObject();
		try {
			/*IMEI*/
			holder.put(JSONKeys.IMEI, pollAccidentData.getImei());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		return holder;
	}
}
