package nu.ac.th.rescueunit;

import org.json.JSONException;
import org.json.JSONObject;

public class AcknowledgeDataCollectionConverter {

	public static AcknowledgeDataCollection fromJSON(JSONObject jsonObject) throws ApplicationException  {
		AcknowledgeDataCollection acknowledgeDataCollection = null;
		AcknowledgeInfo acknowledgeInfo = null;
		
		try {
			JSONObject jsonObject_AcknowledgeInfo = 
					jsonObject.getJSONObject(JSONKeys.JSON_OBJECT_ACKNOWLEDGE_INFO);
			
			acknowledgeInfo = new AcknowledgeInfo(
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
