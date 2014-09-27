package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.NotificationID.AccidentReceived;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class AccidentPollingService extends Service {
	public static final String POLL_ACCIDENT_DATA = "POLL ACCIDENT DATA";
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.AccidentPollingService.BROADCAST";
	public static final String MESSAGE = "MESSAGE";
	
	private static final int POLLING_INTERVAL = 7;
	
	private Thread thread;
	private PollAccidentListener threadListener;
	private NotificationManager notificationManager;
	private Intent intent;
	private PendingIntent pendingIntent;
	private long[] notificationVibratePattern;
	private LocalBroadcastManager broadcaster;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		intent = new Intent(this, MainActivity.class);
		pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationVibratePattern = new long[]{50, 100, 50, 100, 50, 100, 50, 500};
		broadcaster = LocalBroadcastManager.getInstance(this);
		threadListener = new PollAccidentListener() {
			@Override
			public void onDataReceived(AccidentData accidentData) {
				processReceivedData(accidentData);
			}
		};
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		thread = new Thread(
				new PollAccident(threadListener, POLLING_INTERVAL, 
						(PollAccidentData)intent.getSerializableExtra(POLL_ACCIDENT_DATA), new TCP_IP()));
		thread.start();
		return START_STICKY;
	}

	private void processReceivedData(AccidentData accidentData) {
		sendNotification(accidentData);
		sendLocalBroadCast(accidentData);
	}
	
	private void sendNotification(AccidentData accidentData) {
		Position position = accidentData.getPosition();
		AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
		
		Notification noti = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_launcher)
			.setTicker("Incoming Accident!")
			.setContentTitle("Callcenter wants you!")
			.setContentText(position.toString())
			.setContentIntent(pendingIntent)
			/** Begin Area : Need API 4.1 or higer to work! **/
			.setStyle(new NotificationCompat.BigTextStyle()
				.bigText(accidentData.getAccidentID() + "\n" +
							position.toString() + "\n" + 
							additionalInfo.toString()))
			.addAction (R.drawable.ic_launcher,"Accept", pendingIntent)
            .addAction (R.drawable.ic_launcher,"Reject", pendingIntent)
            /** End Area : Need API 4.1 or higer to work! **/
		    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
	    	.setVibrate(notificationVibratePattern)
	    	.setAutoCancel(false)
	        .build();
	   
	   notificationManager.notify(AccidentReceived, noti);
	}
	
	private void sendLocalBroadCast(AccidentData accidentData) {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
}
