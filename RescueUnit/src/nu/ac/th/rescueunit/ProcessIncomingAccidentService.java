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
	public static final String MESSAGE = "Accident in DB has changed";
	
	private LocalBroadcastManager broadcaster;
	private BroadcastReceiver missionReportBroadcastReceiver;
	private ApplicationDbHelper db;
	
	private Intent startActivityIntent;
	private PendingIntent startActivityPendingIntent;
	
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

		reportPendingState(accidentPollingData, accidentRescueState);
		
		// Check Accident Duplication
			// IF NO
				// INSERT ACCIDENT + PENDING STATE
				// REPORT
			// IF YES
				// Check latest rescue state of this specific accident
					// IF EQUAL TO RED STATE or EMPTY
						// INSERT NEW PENDING STATE
						// REPORT
					// IF NO
						// DO NOTHING
	}
	
	private void reportPendingState(AccidentPollingData accidentPollingData, 
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
	
	/**
	 * Acknowledge Received from report pending state back to server
	 * @param acknowledgeDataCollection
	 */
	private void processReportAcknowledge(AcknowledgeDataCollection acknowledgeDataCollection) {
		/*PendingIntent contentIntent = PendingIntent.getActivity(
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
		
		ApplicationNotification.sendNotification(param);*/
		LocalBroadcastManager.getInstance(this).unregisterReceiver(missionReportBroadcastReceiver);
		sendLocalBroadCast();
		notifyIncomingAccident(accidentWithState);

		stopSelf();
	}
	
	/**
	 * Alert Notification for new Accident or old Accident with pending state??
	 * @param accidentWithState
	 */
	private void notifyIncomingAccident(AccidentWithState accidentWithState) {
		AccidentData accidentData = accidentWithState.getAccidentData();
		AccidentRescueState accidentRescueState = accidentWithState.getAccidentRescueState();
		startActivityIntent = new Intent(this, MainActivity.class);
		//startActivityIntent.putExtra(DetailActivity.ACCIDENT_WITH_STATE, accidentWithState);
		startActivityPendingIntent = PendingIntent.getActivity(this, 0, startActivityIntent, 0);
		
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
					startActivityPendingIntent, 
					INCOMING_ACCIDENT);
		ApplicationNotification.sendNotification(param);
	}
	
	/**
	 * Send broadcast for notifying Data in DB has changed
	 */
	private void sendLocalBroadCast() {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
}
