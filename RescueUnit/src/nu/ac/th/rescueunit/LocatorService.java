package nu.ac.th.rescueunit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class LocatorService extends Service {
	public static final String BROADCAST 
		= "nu.ac.th.rescueunit.LocatorService.BROADCAST";
	public static final String MESSAGE = "Position has updated";

	private LocalBroadcastManager broadcaster;
	private ILocator mILocator;
	private LocatorListener mLocatorListener;
	
	@Override
	public void onCreate() {
		super.onCreate();
		broadcaster = LocalBroadcastManager.getInstance(this);
		mLocatorListener = new LocatorListener() {
			@Override
			public void onLocationUpdated(Position position) {
				onPositionReceived(position);
			}
		};
		mILocator = new GPS(mLocatorListener, new Position(), getApplicationContext());
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mILocator.startLocatePosition();
		return START_STICKY;
	}

	private void onPositionReceived(Position position) {
		ApplicationSharedPreference.storePosition(this, position);
		sendLocalBroadCast();
	}
	
	private void sendLocalBroadCast() {
		Intent intent = new Intent(BROADCAST);
	    intent.putExtra(MESSAGE, "");
	    broadcaster.sendBroadcast(intent);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
