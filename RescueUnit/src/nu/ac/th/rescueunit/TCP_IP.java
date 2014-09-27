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
	public AcknowledgeDataCollection updateRescueUnit(ReportDataCollection reportDataCollection) throws ApplicationException {
		JSONObject jsonObject = ReportDataCollectionConverter.toJSON(reportDataCollection);
		HttpResponse httpResponse = jsonRequest(UPDATE_RESCUEUNIT_URL, jsonObject);
		
		JSONObject jsonObject_AcknowledgeDataCollection = httpResponse_to_JSONObject(httpResponse);

		AcknowledgeDataCollection acknowledgeDataCollection = 
				AcknowledgeDataCollectionConverter.fromJSON(jsonObject_AcknowledgeDataCollection);
		
		return acknowledgeDataCollection;
	}

	@Override
	public AccidentData pollAccident(AccidentPollingData pollAccidentData) throws ApplicationException {
		JSONObject jsonObject = AccidentPolllingDataConverter.toJSON(pollAccidentData);
		HttpResponse httpResponse = jsonRequest(POLL_ACCIDENT_URL, jsonObject);
		JSONObject jsonObject_AccidentData = httpResponse_to_JSONObject(httpResponse);

		AccidentData accidentData = AccidentDataConverter.fromJSON(jsonObject_AccidentData);
		
		return accidentData;
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
