package nu.ac.th.rescueunit;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class MissionReportService extends IntentService {
	public static final String MISSION_REPORT_DATA = "nu.ac.th.rescueunit.missionreportdata";
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.MissionReportService.BROADCAST";
	public static final String ACKNOWLEDGE_DATA_COLLECTION = "AcknowledgeDataCollection";
	public static final String MISSION_REPORT = "MissionReport";
	
	private AvailableStatusController availableStatusController;
	private ApplicationDbHelper db;
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
		db = new ApplicationDbHelper(this);
		availableStatusController = new AvailableStatusController(this);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		missionReport = (MissionReport)intent.getSerializableExtra(MISSION_REPORT_DATA);
		AcknowledgeDataCollection acknowledgeDataCollection = null;
		AccidentRescueState accidentRescueState = new AccidentRescueState(missionReport.getAssignDate(),
				missionReport.getDate(), missionReport.getRescueState()); 
		try {
			acknowledgeDataCollection = connector.reportMission(missionReport);
			db.addAccidentRescueState(missionReport.getAccidentID(), accidentRescueState);
			availableStatusController.onStatusChange(missionReport.getAccidentID(), missionReport.getRescueState());
			sendLocalBroadCast(acknowledgeDataCollection);
		} catch (ApplicationException e) {
			// report fail, do something?
		}
		
		stopSelf();
	}
	
	private void sendLocalBroadCast(AcknowledgeDataCollection acknowledgeDataCollection) {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(ACKNOWLEDGE_DATA_COLLECTION, acknowledgeDataCollection);
	    intent.putExtra(MISSION_REPORT, missionReport);
	    broadcaster.sendBroadcast(intent);
	}
}
