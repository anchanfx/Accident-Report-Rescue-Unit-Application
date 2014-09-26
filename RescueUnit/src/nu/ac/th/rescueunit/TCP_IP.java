package nu.ac.th.rescueunit;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

public class TCP_IP implements IServerConnector {
	private static final String SERVER_URL = 
			"http://nuaccrepo.mywebcommunity.org/ReportServer/RescueUpdate.php";
	
	@Override
	public AcknowledgeDataCollection sendReport(ReportDataCollection reportDataCollection) throws ApplicationException {
		JSONObject jsonObject = ReportDataCollectionConverter.toJSON(reportDataCollection);
		HttpResponse httpResponse = HttpProcessor.jsonRequest(SERVER_URL, jsonObject);
		
		JSONObject jsonObject_AcknowledgeDataCollection = 
				HttpProcessor.httpResponse_to_JSONObject(httpResponse);

		AcknowledgeDataCollection acknowledgeDataCollection = 
				AcknowledgeDataCollectionConverter.fromJSON(jsonObject_AcknowledgeDataCollection);
		
		return acknowledgeDataCollection;
	}
}
