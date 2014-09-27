package nu.ac.th.rescueunit;

import org.json.JSONException;
import org.json.JSONObject;

public class AcknowledgeDataCollectionConverter {

	public static AcknowledgeDataCollection fromJSON(JSONObject jsonObject) throws ApplicationException  {
		AcknowledgeDataCollection acknowledgeDataCollection = null;
		AcknowledgeData acknowledgeInfo = null;
		
		try {
			JSONObject jsonObject_AcknowledgeInfo = 
					jsonObject.getJSONObject(JSONKeys.JSON_ACKNOWLEDGE_DATA);
			
			acknowledgeInfo = new AcknowledgeData(
					jsonObject_AcknowledgeInfo.getString(JSONKeys.MESSAGE));
		} catch (JSONException e) {
			throw new ApplicationException();
		} catch (NullPointerException e) {
			throw new ApplicationException();
		}
		
		acknowledgeDataCollection = new AcknowledgeDataCollection(acknowledgeInfo);
		
		return acknowledgeDataCollection;
	}
}
