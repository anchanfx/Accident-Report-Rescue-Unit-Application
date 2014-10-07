package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.NotificationID.INCOMING_ACCIDENT;
import static nu.ac.th.rescueunit.ApplicationSettings.*;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class AccidentPollingService extends Service {
	public static final String POLL_ACCIDENT_DATA = "POLL ACCIDENT DATA";
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.AccidentPollingService.BROADCAST";
	public static final String MESSAGE = "MESSAGE";
	
	private static final int POLLING_INTERVAL = 10;
	
	private Thread thread;
	private PollAccidentListener threadListener;
	
	private Intent detailActivityIntent;
	private PendingIntent detailActivityPendingIntent;
	private Intent acceptMissionIntent;
	private PendingIntent acceptMissionPendingIntent;
	
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
		pollAccidentRequestData = new AccidentPollingRequestData("1");
						 //= new AccidentPollingData(IMEI.getDeviceIMEI(this));
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationVibratePattern = NOTIFICATION_VIBRATE_PATTERN;
		broadcaster = LocalBroadcastManager.getInstance(this);
		threadListener = new PollAccidentListener() {
			@Override
			public void onDataReceived(AccidentPollingData accidentPollingData) {
				processReceivedData(accidentPollingData);
			}
		};
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		thread = new Thread(new PollAccident(
				threadListener, POLLING_INTERVAL, pollAccidentRequestData, new TCP_IP()));
		thread.start();
		return START_STICKY;
	}

	private void processReceivedData(AccidentPollingData accidentPollingData) {
		prepareIntents(accidentPollingData);
		sendNotification(accidentPollingData);
		sendLocalBroadCast(accidentPollingData);
	}
	
	private void prepareIntents(AccidentPollingData accidentPollingData) {
		AccidentData accidentData = accidentPollingData.getAccidentData();
		
		MissionReport accept = new MissionReport(pollAccidentRequestData.getImei(),
				accidentData.getAccidentID(), RescueState.ACCEPT, ApplicationTime.newDateInstance(), "");
		
		detailActivityIntent = new Intent(this, DetailActivity.class);
		detailActivityIntent.putExtra(DetailActivity.ACCIDENT_DATA, accidentData);
		detailActivityPendingIntent = PendingIntent.getActivity(this, 0, detailActivityIntent, 0);
		
		acceptMissionIntent = new Intent(this, MissionReportService.class);
		acceptMissionIntent.putExtra(MissionReportService.MISSION_REPORT_DATA, accept);
		acceptMissionPendingIntent = PendingIntent.getService(this, 0, acceptMissionIntent, 0);
	}
	
	private void sendNotification(AccidentPollingData accidentPollingData) {
		AccidentData accidentData = accidentPollingData.getAccidentData();
		
		
		Position position = accidentData.getPosition();
		AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
		
		Notification noti = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_launcher)
			.setTicker("Incoming Accident!")
			.setContentTitle("Callcenter wants you!")
			.setContentText(accidentPollingData.getAssignDate().toString())
			.setContentIntent(detailActivityPendingIntent)
			/** Begin Area : Need API 4.1 or higer to work! **/
			.setStyle(new NotificationCompat.BigTextStyle()
				.bigText(accidentData.getAccidentID() + "\n" +
							position.toString() + "\n" + 
							additionalInfo.toString()))
			.addAction(R.drawable.ic_launcher,"Accept", acceptMissionPendingIntent)
            .addAction(R.drawable.ic_launcher,"Reject", detailActivityPendingIntent)
            /** End Area : Need API 4.1 or higer to work! **/
		    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
	    	.setVibrate(notificationVibratePattern)
	    	.setAutoCancel(true)
	        .build();
	   
	   notificationManager.notify(INCOMING_ACCIDENT, noti);
	}
	
	private void sendLocalBroadCast(AccidentPollingData accidentPollingData) {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
}
