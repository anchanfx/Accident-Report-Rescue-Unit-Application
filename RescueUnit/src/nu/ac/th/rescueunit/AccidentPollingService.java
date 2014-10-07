package nu.ac.th.rescueunit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class AccidentPollingService extends Service {
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.AccidentPollingService.BROADCAST";
	public static final String MESSAGE = "MESSAGE";
	
	private static final int POLLING_INTERVAL = 10;
	
	private Thread thread;
	private PollAccidentListener threadListener;
	private LocalBroadcastManager broadcaster;
	
	private Intent processAccidentIntent;
	
	private AccidentPollingRequestData accidentPollingRequestData;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		accidentPollingRequestData = new AccidentPollingRequestData("2");
						 //= new AccidentPollingData(IMEI.getDeviceIMEI(this));
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
				threadListener, POLLING_INTERVAL, accidentPollingRequestData, new TCP_IP()));
		thread.start();
		return START_STICKY;
	}
	
	private void processReceivedData(AccidentPollingData accidentPollingData) {
		processAccidentIntent = new Intent(this, ProcessIncomingAccidentService.class);
		processAccidentIntent.putExtra(
				ProcessIncomingAccidentService.ACCIDENT_POLLING_DATA, 
				accidentPollingData);
		processAccidentIntent.putExtra(
				ProcessIncomingAccidentService.ACCIDENT_POLLING_REQUEST_DATA, 
				accidentPollingRequestData);
		
		startService(processAccidentIntent);
	}
	
	private void sendLocalBroadCast() {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
}
