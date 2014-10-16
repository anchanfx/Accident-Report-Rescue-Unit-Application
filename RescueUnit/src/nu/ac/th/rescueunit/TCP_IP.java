package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.HttpProcessor.*;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

public class TCP_IP implements IServerConnector {
	private static final String UPDATE_RESCUEUNIT_URL = 
			"http://nuaccrepo.mywebcommunity.org/ReportServer/updateRescueUnit.php";
	private static final String POLL_ACCIDENT_URL = 
			"http://nuaccrepo.mywebcommunity.org/ReportServer/pollAccident.php";
	private static final String REPORT_MISSION_URL = 
			"http://nuaccrepo.mywebcommunity.org/ReportServer/reportMission.php";
	
	@Override
	public AcknowledgeDataCollection updateRescueUnit(SelfUpdateData selfUpdateData) throws ApplicationException {
		JSONObject jsonObject = SelfUpdateDataConverter.toJSON(selfUpdateData);
		HttpResponse httpResponse = jsonRequest(UPDATE_RESCUEUNIT_URL, jsonObject);
		
		JSONObject jsonObject_AcknowledgeDataCollection = httpResponse_to_JSONObject(httpResponse);

		AcknowledgeDataCollection acknowledgeDataCollection = 
				AcknowledgeDataCollectionConverter.fromJSON(jsonObject_AcknowledgeDataCollection);
		
		return acknowledgeDataCollection;
	}

	@Override
	public AccidentPollingData pollAccident(AccidentPollingRequestData pollAccidentData) throws ApplicationException {
		JSONObject jsonObject = AccidentPolllingRequestDataConverter.toJSON(pollAccidentData);
		HttpResponse httpResponse = jsonRequest(POLL_ACCIDENT_URL, jsonObject);
		JSONObject jsonObject_AccidentData = httpResponse_to_JSONObject(httpResponse);

		AccidentPollingData accidentPollingData = AccidentPollingDataConverter.fromJSON(jsonObject_AccidentData);
		
		return accidentPollingData;
	}

	@Override
	public AcknowledgeDataCollection reportMission(MissionReport missionReport) throws ApplicationException {
		JSONObject jsonObject = MissionReportConverter.toJSON(missionReport);
		HttpResponse httpResponse = jsonRequest(REPORT_MISSION_URL, jsonObject);
		JSONObject jsonObject_AcknowledgeDataCollection = httpResponse_to_JSONObject(httpResponse);

		AcknowledgeDataCollection acknowledgeDataCollection = 
				AcknowledgeDataCollectionConverter.fromJSON(jsonObject_AcknowledgeDataCollection);
		
		return acknowledgeDataCollection;
	}

}
