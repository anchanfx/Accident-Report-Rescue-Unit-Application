package nu.ac.th.rescueunit;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class MissionReportService extends IntentService {
	public static final String MISSION_REPORT_DATA = "nu.ac.th.rescueunit.missionreportdata";
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.MissionReportService.BROADCAST";
	public static final String ACKNOWLEDGE_DATA_COLLECTION = "AcknowledgeDataCollection";
	
	private LocalBroadcastManager broadcaster;
	private IServerConnector connector;
	
	private MissionReport missionReport;
	
	public MissionReportService() {
		super("MissionReportService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		broadcaster = LocalBroadcastManager.getInstance(this);
		connector = new TCP_IP();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		missionReport = (MissionReport)intent.getSerializableExtra(MISSION_REPORT_DATA);
		AcknowledgeDataCollection acknowledgeDataCollection = null;
		
		try {
			acknowledgeDataCollection = connector.reportMission(missionReport);
			notifyUser(acknowledgeDataCollection);
		} catch (ApplicationException e) {
			// report fail, do something?
		}
	}
	
	private void notifyUser(AcknowledgeDataCollection acknowledgeDataCollection) {
		sendLocalBroadCast(acknowledgeDataCollection);
	}
	
	private void sendLocalBroadCast(AcknowledgeDataCollection acknowledgeDataCollection) {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(ACKNOWLEDGE_DATA_COLLECTION, acknowledgeDataCollection);
	    broadcaster.sendBroadcast(intent);
	}
}
