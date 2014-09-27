package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.ApplicationSettings.NOTIFICATION_VIBRATE_PATTERN;
import static nu.ac.th.rescueunit.NotificationID.MissionReport;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class MissionReportService extends IntentService {
	public static final String MISSION_REPORT_DATA = "MISSION REPORT DATA";
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.MissionReportService.BROADCAST";
	public static final String MESSAGE = "MESSAGE";
	
	private Intent intent;
	private PendingIntent pIntent;
	
	private NotificationManager notificationManager;
	private long[] notificationVibratePattern;
	private LocalBroadcastManager broadcaster;
	private IServerConnector connector;
	
	private MissionReport missionReport;
	
	public MissionReportService() {
		super("MissionReportService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		intent = new Intent(this, MainActivity.class);
		pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		
		notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationVibratePattern = NOTIFICATION_VIBRATE_PATTERN;
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
		sendNotification(acknowledgeDataCollection);
		sendLocalBroadCast(acknowledgeDataCollection);
	}
	
	private void sendNotification(AcknowledgeDataCollection acknowledgeDataCollection) {
		String message = acknowledgeDataCollection.getAcknowledgeInfo().getMessage();
		
		Notification noti = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_launcher)
			.setTicker("Mission Report Result")
			.setContentTitle("Callcenter wants you!")
			.setContentText(message)
			.setContentIntent(pIntent)
		    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
	    	.setVibrate(notificationVibratePattern)
	    	.setAutoCancel(true)
	        .build();
	   
	   notificationManager.notify(MissionReport, noti);
	}
	
	private void sendLocalBroadCast(AcknowledgeDataCollection acknowledgeDataCollection) {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
}
