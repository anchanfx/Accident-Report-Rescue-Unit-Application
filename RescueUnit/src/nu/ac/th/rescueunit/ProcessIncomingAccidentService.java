package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.NotificationID.INCOMING_ACCIDENT;
import static nu.ac.th.rescueunit.NotificationID.MISSION_REPORT;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v4.content.LocalBroadcastManager;

public class ProcessIncomingAccidentService extends IntentService {
	public static final String ACCIDENT_POLLING_DATA 
		= "nu.ac.th.rescueunit.accidentPollingData";
	public static final String ACCIDENT_POLLING_REQUEST_DATA 
	= "nu.ac.th.rescueunit.accidentPollingRequestData";
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.ProcessIncomingAccidentService.BROADCAST";
	public static final String MESSAGE = "Message";
	
	private LocalBroadcastManager broadcaster;
	private BroadcastReceiver missionReportBroadcastReceiver;
	private ApplicationDbHelper db;
	
	private Intent detailActivityIntent;
	private PendingIntent detailActivityPendingIntent;
	
	private AccidentPollingRequestData accidentPollingRequestData;
	private AccidentPollingData accidentPollingData;
	private AccidentData accidentData;
	private AccidentRescueState accidentRescueState;
	private AccidentWithState accidentWithState;
	
	public ProcessIncomingAccidentService() {
		super("ProcessIncomingAccidentService");
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		broadcaster = LocalBroadcastManager.getInstance(this);
		db = new ApplicationDbHelper(this);
		
		missionReportBroadcastReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				AcknowledgeDataCollection acknowledgeDataCollection = 
						(AcknowledgeDataCollection)intent
						.getSerializableExtra(MissionReportService.ACKNOWLEDGE_DATA_COLLECTION);
				processReportAcknowledge(acknowledgeDataCollection);
			}
		};
		LocalBroadcastManager.getInstance(this)
			.registerReceiver((missionReportBroadcastReceiver),
				new IntentFilter(MissionReportService.BROADCAST));
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		accidentPollingData = (AccidentPollingData)intent
				.getSerializableExtra(ACCIDENT_POLLING_DATA);
		accidentPollingRequestData = (AccidentPollingRequestData)intent
				.getSerializableExtra(ACCIDENT_POLLING_REQUEST_DATA);
		
		accidentData = accidentPollingData.getAccidentData();
		accidentRescueState = new AccidentRescueState(
				accidentPollingData.getAssignDate(),
				ApplicationTime.newDateInstance(),
				RescueState.PENDING);
		accidentWithState = new AccidentWithState(accidentData, accidentRescueState);
		
		try {
			db.addAccidentData(accidentData);
		} catch (SQLiteConstraintException e) {
			// Constraint Fail
			// Assume duplication on AccidentData
		}
		
		db.addAccidentRescueState(accidentData.getAccidentID(), accidentRescueState);
		
		reportPendingState(accidentPollingData, accidentRescueState);
		notifyIncomingAccident(accidentWithState);
		
		// Check Accident Duplication
			// IF NO
				// INSERT ACCIDENT + PENDING STATE
				// REPORT
			// IF YES
				// Check latest rescue state of this specific accident
					// IF EQUAL TO RED STATE
						// INSERT NEW PENDING STATE
						// REPORT
					// IF NO
						// DO NOTHING
		stopSelf();
	}
	
	private void processReportAcknowledge(AcknowledgeDataCollection acknowledgeDataCollection) {
		PendingIntent contentIntent = PendingIntent.getActivity(
			    getApplicationContext(),
			    0,
			    new Intent(),
			    PendingIntent.FLAG_UPDATE_CURRENT);
		String message = acknowledgeDataCollection.getAcknowledgeInfo().getMessage();
		
		ApplicationNotificationParameter param 
			= new ApplicationNotificationParameter(this,
				"Pending Mission Report Result", 
				"Pending Mission Report Result", 
				message, 
				"",
				contentIntent, 
				MISSION_REPORT);
		ApplicationNotification.sendNotification(param);
	}
	
	private void reportPendingState(
			AccidentPollingData accidentPollingData,
			AccidentRescueState accidentRescueState) {
		AccidentData accidentData = accidentPollingData.getAccidentData();
		MissionReport pending = new MissionReport(
				accidentPollingRequestData.getImei(),
				accidentData.getAccidentID(), 
				accidentRescueState.getState(), 
				accidentRescueState.getAssignDateTime(),
				accidentRescueState.getDateTime(), 
				RescueState.DEFAULT_MESSAGE);
		
		Intent pendingReportIntent = new Intent(this, MissionReportService.class);
		pendingReportIntent.putExtra(MissionReportService.MISSION_REPORT_DATA, pending);
		startService(pendingReportIntent);
	}
	
	private void notifyIncomingAccident(AccidentWithState accidentWithState) {
		AccidentData accidentData = accidentWithState.getAccidentData();
		AccidentRescueState accidentRescueState = accidentWithState.getAccidentRescueState();
		detailActivityIntent = new Intent(this, DetailActivity.class);
		detailActivityIntent.putExtra(DetailActivity.ACCIDENT_DATA, accidentData);
		detailActivityPendingIntent = PendingIntent.getActivity(this, 0, detailActivityIntent, 0);
		
		Position position = accidentData.getPosition();
		AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
		
		ApplicationNotificationParameter param
			= new ApplicationNotificationParameter(this,
					"Incoming Accident!", 
					"Callcenter wants you!", 
					"AT=" + accidentRescueState.getAssignDateTime().toString(), 
					accidentData.getAccidentID() + "\n" +
							position.toString() + "\n" + 
							additionalInfo.toString(),
					detailActivityPendingIntent, 
					INCOMING_ACCIDENT);
		ApplicationNotification.sendNotification(param);
	}
	
	private void sendLocalBroadCast() {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
}
