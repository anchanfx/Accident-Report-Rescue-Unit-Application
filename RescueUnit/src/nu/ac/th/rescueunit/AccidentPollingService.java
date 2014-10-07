package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.ApplicationSettings.Notification.VIBRATE_PATTERN;
import static nu.ac.th.rescueunit.NotificationID.INCOMING_ACCIDENT;
import static nu.ac.th.rescueunit.NotificationID.MISSION_REPORT;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class AccidentPollingService extends Service {
	public static final String POLL_ACCIDENT_DATA = "POLL ACCIDENT DATA";
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.AccidentPollingService.BROADCAST";
	public static final String MESSAGE = "MESSAGE";
	
	private static final int POLLING_INTERVAL = 10;
	
	private Thread thread;
	private PollAccidentListener threadListener;
	private BroadcastReceiver missionReportBroadcastReceiver;
	
	private Intent detailActivityIntent;
	private PendingIntent detailActivityPendingIntent;
	private Intent pendingReportIntent;
	
	private NotificationManager notificationManager;
	private long[] notificationVibratePattern;
	private LocalBroadcastManager broadcaster;
	
	private AccidentPollingRequestData pollAccidentRequestData;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		pollAccidentRequestData = new AccidentPollingRequestData("2");
						 //= new AccidentPollingData(IMEI.getDeviceIMEI(this));
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationVibratePattern = VIBRATE_PATTERN;
		broadcaster = LocalBroadcastManager.getInstance(this);
		createAnoymouseInnerType();
		LocalBroadcastManager.getInstance(this)
			.registerReceiver((missionReportBroadcastReceiver),
					new IntentFilter(MissionReportService.BROADCAST));
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		thread = new Thread(new PollAccident(
				threadListener, POLLING_INTERVAL, pollAccidentRequestData, new TCP_IP()));
		thread.start();
		return START_STICKY;
	}

	private void createAnoymouseInnerType() {
		missionReportBroadcastReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				AcknowledgeDataCollection acknowledgeDataCollection = 
						(AcknowledgeDataCollection)intent
						.getSerializableExtra(MissionReportService.ACKNOWLEDGE_DATA_COLLECTION);
				processReportAcknowledge(acknowledgeDataCollection);
			}
		};
		
		threadListener = new PollAccidentListener() {
			@Override
			public void onDataReceived(AccidentPollingData accidentPollingData) {
				processReceivedData(accidentPollingData);
			}
		};
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
	
	private void processReceivedData(AccidentPollingData accidentPollingData) {
		// TODO Logic to check wether to send and set state = pending for incoming accident or not?
		reportPendingState(accidentPollingData);
		notifyIncomingAccident(accidentPollingData);
		sendLocalBroadCast(accidentPollingData);
	}
	
	
	
	private void reportPendingState(AccidentPollingData accidentPollingData) {
		AccidentData accidentData = accidentPollingData.getAccidentData();
		MissionReport pending = new MissionReport(
				pollAccidentRequestData.getImei(),
				accidentData.getAccidentID(), 
				RescueState.PENDING, 
				accidentPollingData.getAssignDate(),
				ApplicationTime.newDateInstance(), 
				RescueState.DEFAULT_MESSAGE);
		
		pendingReportIntent = new Intent(this, MissionReportService.class);
		pendingReportIntent.putExtra(MissionReportService.MISSION_REPORT_DATA, pending);
		startService(pendingReportIntent);
	}
	
	private void notifyIncomingAccident(AccidentPollingData accidentPollingData) {
		AccidentData accidentData = accidentPollingData.getAccidentData();
		detailActivityIntent = new Intent(this, DetailActivity.class);
		detailActivityIntent.putExtra(DetailActivity.ACCIDENT_DATA, accidentData);
		detailActivityPendingIntent = PendingIntent.getActivity(this, 0, detailActivityIntent, 0);
		
		Position position = accidentData.getPosition();
		AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
		
		ApplicationNotificationParameter param
			= new ApplicationNotificationParameter(this,
					"Incoming Accident!", 
					"Callcenter wants you!", 
					"AT=" + accidentPollingData.getAssignDate().toString(), 
					accidentData.getAccidentID() + "\n" +
							position.toString() + "\n" + 
							additionalInfo.toString(),
					detailActivityPendingIntent, 
					INCOMING_ACCIDENT);
		ApplicationNotification.sendNotification(param);
	}
	
	private void sendLocalBroadCast(AccidentPollingData accidentPollingData) {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
}
